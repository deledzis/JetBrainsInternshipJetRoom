package com.deledzis.jetroom.view.printer

class CommandLinePrinterFactory : PrinterFactory() {
    override fun getPrinter(): IPrinter = CommandLinePrinter()
}

class CommandLinePrinter : IPrinter {
    override fun printString(string: String) {
        print(string)
    }

    override fun printLine(string: String) {
        println(string)
    }

    override fun printBlock(strings: List<String>) {
        strings.forEach { printLine(it) }
    }
}