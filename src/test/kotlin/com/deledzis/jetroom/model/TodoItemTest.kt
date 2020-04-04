package com.deledzis.jetroom.model

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TodoItemTest {
    var item: TodoItem? = null

    @BeforeEach
    fun init() {
        item = TodoItem(
            message = "asd",
            isDone = false,
            createTime = "04 апреля 2020, 05:01:34",
            editTime = "05 апреля 2020, 12:24:34"
        )
    }

    @AfterEach
    fun clear() {
        item = null
    }

    @Test
    fun toggleDone() {
        item!!.toggleDone()
        assertEquals(true, item!!.isDone)

        item!!.toggleDone()
        assertEquals(false, item!!.isDone)
    }

    @Test
    fun testToString() {
        assertEquals("[ ] - asd\n" + "Последнее изменение: 05 апреля 2020, 12:24:34\n", item!!.toString())

        item!!.toggleDone()
        assertNotEquals("[X] - asd\n" + "Последнее изменение: 05 апреля 2020, 12:24:34\n", item!!.toString())
    }

    @Test
    fun getMessage() {
        assertEquals("asd", item!!.message)
    }

    @Test
    fun isDone() {
        assertEquals(false, item!!.isDone)
    }

    @Test
    fun setDone() {
        assertEquals(false, item!!.isDone)
        item!!.isDone = true
        assertEquals(true, item!!.isDone)

        item!!.isDone = false
        assertEquals(false, item!!.isDone)
    }

    @Test
    fun getCreateTime() {
        assertEquals("04 апреля 2020, 05:01:34", item!!.createTime)
    }

    @Test
    fun getEditTime() {
        assertEquals("05 апреля 2020, 12:24:34", item!!.editTime)
    }

    @Test
    fun setEditTime() {
        assertEquals("05 апреля 2020, 12:24:34", item!!.editTime)
        item!!.editTime = "15 мая 2020, 12:24:34"
        assertEquals("15 мая 2020, 12:24:34", item!!.editTime)
    }
}