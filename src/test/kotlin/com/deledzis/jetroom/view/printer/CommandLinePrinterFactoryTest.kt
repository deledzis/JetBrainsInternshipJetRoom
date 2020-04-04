package com.deledzis.jetroom.view.printer

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class CommandLinePrinterFactoryTest {

    @Test
    fun getPrinter() {
        Assertions.assertTrue { CommandLinePrinterFactory().getPrinter() is CommandLinePrinter }
    }
}