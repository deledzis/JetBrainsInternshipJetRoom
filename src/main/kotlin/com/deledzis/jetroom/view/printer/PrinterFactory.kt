package com.deledzis.jetroom.view.printer

abstract class PrinterFactory {
    abstract fun getPrinter(): IPrinter

    companion object {
        inline fun <reified T : IPrinter> create(): PrinterFactory =
            when (T::class) {
                CommandLinePrinter::class   -> CommandLinePrinterFactory()
                else                        -> throw IllegalArgumentException()
            }
    }
}