package com.deledzis.jetroom.model

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TodoDataTest {
    var data: TodoData? = null

    @BeforeEach
    fun init() {
        data = TodoData(
            count = 1,
            items = mutableListOf(
                TodoItem(
                    message = "asd",
                    isDone = false,
                    createTime = "04 апреля 2020, 05:01:34",
                    editTime = "05 апреля 2020, 12:24:34"
                )
            )
        )
    }

    @AfterEach
    fun clear() {
        data = null
    }

    @Test
    fun getCount() {
        assertEquals(1, data!!.count)
    }

    @Test
    fun setCount() {
        assertEquals(1, data!!.count)
        data!!.count++
        assertEquals(2, data!!.count)
    }

    @Test
    fun getItems() {
        assertEquals(mutableListOf(
            TodoItem(
                message = "asd",
                isDone = false,
                createTime = "04 апреля 2020, 05:01:34",
                editTime = "05 апреля 2020, 12:24:34"
            )
        ), data!!.items)
    }
}