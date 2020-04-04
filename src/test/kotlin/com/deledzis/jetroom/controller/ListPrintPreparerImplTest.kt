package com.deledzis.jetroom.controller

import com.deledzis.jetroom.model.Paginator
import com.deledzis.jetroom.model.Repository
import com.deledzis.jetroom.model.TodoItem
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import java.util.*

internal class ListPrintPreparerImplTest {
    companion object {
        var listPrintPreparerImpl: ListPrintPreparerImpl? = null

        @BeforeAll
        @JvmStatic
        fun init() {
            listPrintPreparerImpl = ListPrintPreparerImpl()
        }

        @AfterAll
        @JvmStatic
        fun clear() {
            listPrintPreparerImpl = null
            Repository.setData(mutableListOf())
        }
    }

    @BeforeEach
    fun initTest() {
        Repository.setData(listOf(
            TodoItem("test 1", isDone = true, createTime = "04 апреля 2020, 05:01:34"),
            TodoItem("test 2", isDone = true, createTime = "04 апреля 2020, 05:01:34"),
            TodoItem("test 3", isDone = true, createTime = "04 апреля 2020, 05:01:34")
        ))
        Paginator.enterPaginationMode()
    }

    @AfterEach
    fun clearTest() {
        Paginator.exitPaginationMode()
    }

    @Test
    fun testPrepareListToPrintWithFilterNotDoneDisabled() {
        Repository.filterNotDoneEnabled = false
        assertEquals(3, listPrintPreparerImpl!!.prepareListToPrint().size)
    }

    @Test
    fun testPrepareListToPrintWithFilterNotDoneEnabled() {
        Repository.filterNotDoneEnabled = true

        val res = listPrintPreparerImpl!!.prepareListToPrint()
        assertEquals(1, res.size)
        if (Locale.getDefault().language == Locale.forLanguageTag("ru").language) {
            assertEquals(true, res[0].startsWith("На данный момент список пуст."))
        } else {
            assertEquals(true, res[0].startsWith("To-Do list is empty."))
        }
    }

    @Test
    fun testPreparePageToPrintWithFilterNotDoneEnabled() {
        Repository.filterNotDoneEnabled = true

        val res = listPrintPreparerImpl!!.prepareListToPrint()
        assertEquals(1, res.size)
        if (Locale.getDefault().language == Locale.forLanguageTag("ru").language) {
            assertEquals(true, res[0].startsWith("На данный момент список пуст."))
        } else {
            assertEquals(true, res[0].startsWith("To-Do list is empty."))
        }
    }

    @Test
    fun testPreparePageToPrintWithFilterNotDoneDisabled() {
        Repository.filterNotDoneEnabled = false
        Paginator.currentPage = 1

        val res2 = listPrintPreparerImpl!!.preparePageToPrint()
        assertEquals(1, res2.size)
        if (Locale.getDefault().language == Locale.forLanguageTag("ru").language) {
            assertEquals(true, res2[0].startsWith("Вы достигли конца списка."))
        } else {
            assertEquals(true, res2[0].startsWith("You've reached the end."))
        }
    }

    @Test
    fun testPreparePageToPrintWithDataEnoughForSomePages() {
        Repository.setData(mutableListOf())
        for (i in 0 until 100) {
            Repository.addItem("added $i", {}, {})
        }

        Paginator.currentPage = 0
        assertEquals(true, listPrintPreparerImpl!!.preparePageToPrint()[0].startsWith("Страница 1"))

        Paginator.currentPage++
        assertEquals(true, listPrintPreparerImpl!!.preparePageToPrint()[0].startsWith("Страница 2"))

        Paginator.currentPage++
        assertEquals(true, listPrintPreparerImpl!!.preparePageToPrint()[0].startsWith("Страница 3"))

        Paginator.currentPage++
        assertEquals(true, listPrintPreparerImpl!!.preparePageToPrint()[0].startsWith("Страница 4"))

        Paginator.currentPage++
        assertEquals(true, listPrintPreparerImpl!!.preparePageToPrint()[0].startsWith("Страница 5"))

        Paginator.currentPage++
        Assertions.assertNotEquals(true, listPrintPreparerImpl!!.preparePageToPrint()[0].startsWith("Страница 6"))
    }
}