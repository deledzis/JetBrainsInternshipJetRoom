package com.deledzis.jetroom.view

import com.deledzis.jetroom.controller.IMenuHandler
import com.deledzis.jetroom.controller.MenuController
import com.deledzis.jetroom.model.Repository
import com.deledzis.jetroom.util.Consts
import com.deledzis.jetroom.util.Consts.L_CANT_READ_FILE
import com.deledzis.jetroom.util.Consts.L_EMPTY_LIST
import com.deledzis.jetroom.util.Consts.L_SHOW_HELP
import com.deledzis.jetroom.util.Consts.L_SHOW_LIST
import com.deledzis.jetroom.util.Consts.L_TASKS_COUNT
import com.deledzis.jetroom.util.localized
import com.deledzis.jetroom.util.parseJsonToData
import com.deledzis.jetroom.util.readFileContent
import com.deledzis.jetroom.view.printer.CommandLinePrinter
import com.deledzis.jetroom.view.printer.IPrinter
import com.deledzis.jetroom.view.printer.PrinterFactory
import java.util.*

val resources: ResourceBundle by lazy { ResourceBundle.getBundle("strings", Locale.getDefault()) }

fun main() {
    JetRoom().run()
}

class JetRoom : IView {
    private val menuHandler: IMenuHandler by lazy { MenuController(this) }
    private val printer: IPrinter by lazy { PrinterFactory.create<CommandLinePrinter>().getPrinter() }

    override fun providePrinter(): IPrinter = printer

    fun run() {
        printer.printLine(localized(Consts.L_WELCOME))

        val fileContent = readFileContent()
        if (fileContent == null) {
            printer.printLine(localized(L_CANT_READ_FILE))
            return
        } else if (fileContent.isBlank()) {
            printer.printLine(localized(L_EMPTY_LIST))
        } else {
            val data = parseJsonToData(fileContent)
            if (data != null && !data.items.isNullOrEmpty()) {
                Repository.setData(data.items)
                printer.printLine("${localized(
                    L_TASKS_COUNT,
                    Repository.data.count,
                    Repository.data.items.filter { !it.isDone }.size
                )} ${localized(L_SHOW_LIST)}")
            } else {
                printer.printLine(localized(L_EMPTY_LIST))
            }
        }
        printer.printLine(localized(L_SHOW_HELP))

        var userInput = ""
        while (userInput.trim() != "exit") {
            printer.printString("> ")
            userInput = readLine() ?: ""
            menuHandler.handleUserInput(userInput.trim())
        }
    }
}