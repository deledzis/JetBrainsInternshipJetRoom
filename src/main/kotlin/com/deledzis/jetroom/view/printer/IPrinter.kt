package com.deledzis.jetroom.view.printer

interface IPrinter {
    fun printString(string: String)

    fun printLine(string: String)

    fun printBlock(strings: List<String>)
}