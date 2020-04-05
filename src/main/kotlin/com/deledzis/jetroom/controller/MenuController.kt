package com.deledzis.jetroom.controller

import com.deledzis.jetroom.model.Paginator
import com.deledzis.jetroom.model.Repository
import com.deledzis.jetroom.util.Consts
import com.deledzis.jetroom.util.Consts.L_CMD_FORBID_IN_MORE_MODE
import com.deledzis.jetroom.util.Consts.L_CMD_ONLY_IN_MORE_MODE
import com.deledzis.jetroom.util.Consts.L_ENTER_MORE_MODE
import com.deledzis.jetroom.util.Consts.L_EXIT_MORE_MODE
import com.deledzis.jetroom.util.Consts.L_HELP
import com.deledzis.jetroom.util.Consts.L_SHOW_HELP
import com.deledzis.jetroom.util.Consts.L_UNKNOWN_COMMAND
import com.deledzis.jetroom.util.localized
import com.deledzis.jetroom.view.IView

class MenuController(private val view: IView) : IMenuHandler {
    private val listManipulator by lazy { ListManipulatorImpl() }

    private val listPrintPreparer by lazy { ListPrintPreparerImpl() }

    override fun handleUserInput(input: String) {
        if (input == "exit") return

        if (input.isEmpty()) {
            handleNext()
            return
        }

        if (input == "ls" || input.startsWith("ls ") || input == "list" || input.startsWith("list ")) {
            if (!handleLs(input = input)) {
                view.providePrinter().printLine(
                    "${localized(L_CMD_FORBID_IN_MORE_MODE)} ${localized(
                        L_EXIT_MORE_MODE
                    )}"
                )
            }
            return
        }

        if (input == "m" || input.startsWith("m ") || input == "more" || input.startsWith("more ")) {
            if (!handleMore(input = input)) {
                view.providePrinter().printLine(
                    "${localized(L_CMD_FORBID_IN_MORE_MODE)} ${localized(
                        L_EXIT_MORE_MODE
                    )}"
                )
            }
            return
        }

        if (input == "n" || input == "next") {
            if (!handleNext()) {
                view.providePrinter().printLine(
                    "${localized(L_CMD_ONLY_IN_MORE_MODE)} ${localized(
                        L_ENTER_MORE_MODE
                    )}"
                )
            }
            return
        }

        if (input == "p" || input == "prev") {
            if (!handlePrev()) {
                view.providePrinter().printLine(
                    "${localized(L_CMD_ONLY_IN_MORE_MODE)} ${localized(
                        L_ENTER_MORE_MODE
                    )}"
                )
            }
            return
        }

        if (input.startsWith('+')) {
            if (!handleAdd(input = input)) {
                view.providePrinter().printLine(
                    "${localized(L_CMD_FORBID_IN_MORE_MODE)} ${localized(
                        L_EXIT_MORE_MODE
                    )}"
                )
            }
            return
        }

        if (input.startsWith('-')) {
            handleRemove(input = input)
            return
        }

        if (input.startsWith("d ") || input.startsWith("done ")) {
            handleDone(input = input)
            return
        }

        if (input == "q" || input == "quit") {
            if (!handleQuit()) {
                view.providePrinter().printLine(
                    "${localized(L_CMD_ONLY_IN_MORE_MODE)} ${localized(
                        L_ENTER_MORE_MODE
                    )}"
                )
            }
            return
        }

        if (input.startsWith("help") || input == "h") {
            handleHelp()
            return
        }

        view.providePrinter().printLine(
            "${localized(L_UNKNOWN_COMMAND)} ${localized(
                L_SHOW_HELP
            )}"
        )
    }

    private fun handleLs(input: String): Boolean {
        return if (!Paginator.inPaginationMode) {
            Repository.filterNotDoneEnabled = input.contains("--not-done")
            printList()
            true
        } else {
            false
        }
    }

    private fun handleMore(input: String): Boolean {
        return if (!Paginator.inPaginationMode) {
            Repository.filterNotDoneEnabled = input.contains("--not-done")
            enterPaginationMode()
            true
        } else {
            false
        }
    }

    private fun handleNext(): Boolean {
        return if (Paginator.inPaginationMode) {
            nextPage()
            true
        } else {
            false
        }
    }

    private fun handlePrev(): Boolean {
        return if (Paginator.inPaginationMode) {
            prevPage()
            true
        } else {
            false
        }
    }

    private fun handleAdd(input: String): Boolean {
        return if (!Paginator.inPaginationMode) {
            val result = listManipulator.addNewTask(
                input.substring(1).trim(),
                onJsonifyError = { view.providePrinter().printLine(localized(Consts.L_CANT_ADD_TASK)) },
                onWriteError = { view.providePrinter().printLine(localized(Consts.L_CANT_WRITE_FILE)) }
            )
            if (result != null) {
                view.providePrinter().printLine(
                    "${localized(Consts.L_ADDED_TASK, result)}\n${getListStateInfoText()}"
                )
            }
            true
        } else {
            false
        }
    }

    private fun handleRemove(input: String): Boolean {
        val result = listManipulator.removeTask(
            input.substringAfter("-").trim().toIntOrNull() ?: -1,
            onJsonifyError = { view.providePrinter().printLine(localized(Consts.L_CANT_REMOVE_TASK)) },
            onWriteError = { view.providePrinter().printLine(localized(Consts.L_CANT_WRITE_FILE)) }
        )
        if (!result) {
            view.providePrinter().printLine(localized(Consts.L_TASK_NOT_FOUND))
        } else {
            view.providePrinter().printLine("${localized(Consts.L_REMOVED_TASK)} ${getListStateInfoText()}.")
            if (Paginator.inPaginationMode) {
                Paginator.currentPage = 0
                view.providePrinter().printBlock(listPrintPreparer.preparePageToPrint())
            }
        }
        return true
    }

    private fun handleDone(input: String): Boolean {
        val result = listManipulator.toggleTaskDoneStatus(
            input.substringAfter(" ").toIntOrNull() ?: -1,
            onJsonifyError = { view.providePrinter().printLine(localized(Consts.L_CANT_UPDATE_TASK)) },
            onWriteError = { view.providePrinter().printLine(localized(Consts.L_CANT_WRITE_FILE)) }
        )
        if (!result) {
            view.providePrinter().printLine(localized(Consts.L_TASK_NOT_FOUND))
        } else {
            view.providePrinter().printLine("${localized(Consts.L_UPDATED_TASK)} ${getListStateInfoText()}")
            if (Paginator.inPaginationMode) {
                view.providePrinter().printBlock(listPrintPreparer.preparePageToPrint())
            }
        }
        return true
    }

    private fun handleQuit(): Boolean {
        return if (Paginator.inPaginationMode) {
            exitPaginationMode()
            true
        } else {
            false
        }
    }

    private fun handleHelp(): Boolean {
        showHelp()
        return true
    }

    private fun printList() {
        view.providePrinter().printBlock(listPrintPreparer.prepareListToPrint())
    }

    private fun enterPaginationMode() {
        Paginator.enterPaginationMode()
        view.providePrinter().printBlock(listPrintPreparer.preparePageToPrint())
    }

    private fun exitPaginationMode() {
        Paginator.exitPaginationMode()
    }

    private fun nextPage() {
        val result = Paginator.nextPage()
        view.providePrinter().printBlock(result)
    }

    private fun prevPage() {
        val result = Paginator.previousPage()
        view.providePrinter().printBlock(result)
    }

    private fun showHelp() {
        view.providePrinter().printLine(localized(L_HELP))
    }

    private fun getListStateInfoText(): String {
        return if (Repository.data.count > 0) {
            "${localized(
                Consts.L_TASKS_COUNT,
                Repository.data.count,
                Repository.data.items.filter { !it.isDone }.size
            )} ${localized(Consts.L_SHOW_LIST)}"
        } else {
            localized(Consts.L_EMPTY_LIST)
        }
    }
}