package com.deledzis.jetroom.controller

interface IListPrintPreparer {
    fun prepareListToPrint(): List<String>

    fun preparePageToPrint(): List<String>
}