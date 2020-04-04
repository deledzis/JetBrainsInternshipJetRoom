package com.deledzis.jetroom.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RepositoryTest {

    @BeforeEach
    fun init() {
        Repository.filterNotDoneEnabled = false
        Repository.setData(mutableListOf())
    }

    @Test
    fun testGetFilterNotDoneEnabled() {
        assertEquals(false, Repository.filterNotDoneEnabled)
    }

    @Test
    fun testSetFilterNotDoneEnabled() {
        assertEquals(false, Repository.filterNotDoneEnabled)
        Repository.filterNotDoneEnabled = true
        assertEquals(true, Repository.filterNotDoneEnabled)
    }

    @Test
    fun testGetData() {
        assertEquals(0, Repository.data.count)
        assertEquals(mutableListOf<TodoItem>(), Repository.data.items)
    }

    @Test
    fun testSetData() {
        assertEquals(0, Repository.data.count)
        assertEquals(mutableListOf<TodoItem>(), Repository.data.items)

        Repository.setData(listOf(
            TodoItem("test 1", createTime = "04 апреля 2020, 05:01:34"),
            TodoItem("test 2", createTime = "04 апреля 2020, 05:01:34"),
            TodoItem("test 3", createTime = "04 апреля 2020, 05:01:34")
        ))

        assertEquals(3, Repository.data.count)
        assertEquals(TodoItem("test 1", createTime = "04 апреля 2020, 05:01:34"), Repository.data.items[0])
        assertEquals(TodoItem("test 2", createTime = "04 апреля 2020, 05:01:34"), Repository.data.items[1])
        assertEquals(TodoItem("test 3", createTime = "04 апреля 2020, 05:01:34"), Repository.data.items[2])
    }

    @Test
    fun testGetForListing() {
        Repository.setData(listOf(
            TodoItem("test 1", createTime = "04 апреля 2020, 05:01:34"),
            TodoItem("test 2", isDone = true, createTime = "04 апреля 2020, 05:01:34"),
            TodoItem("test 3", createTime = "04 апреля 2020, 05:01:34")
        ))

        assertEquals(3, Repository.getForListing().size)

        Repository.filterNotDoneEnabled = true

        assertEquals(2, Repository.getForListing().size)
    }

    @Test
    fun testAddItem() {
        assertEquals(0, Repository.data.count)
        assertEquals(mutableListOf<TodoItem>(), Repository.data.items)

        Repository.addItem("new", {}, {})

        assertEquals(1, Repository.data.count)
        assertEquals("new", Repository.data.items[0].message)
    }

    @Test
    fun testRemoveItem() {
        Repository.setData(listOf(
            TodoItem("test 1", createTime = "04 апреля 2020, 05:01:34"),
            TodoItem("test 2", createTime = "04 апреля 2020, 05:01:34"),
            TodoItem("test 3", createTime = "04 апреля 2020, 05:01:34")
        ))

        assertEquals(3, Repository.data.count)
        assertEquals(TodoItem("test 1", createTime = "04 апреля 2020, 05:01:34"), Repository.data.items[0])
        assertEquals(TodoItem("test 2", createTime = "04 апреля 2020, 05:01:34"), Repository.data.items[1])
        assertEquals(TodoItem("test 3", createTime = "04 апреля 2020, 05:01:34"), Repository.data.items[2])

        Repository.removeItem(1, {}, {})

        assertEquals(2, Repository.data.count)
        assertEquals(TodoItem("test 2", createTime = "04 апреля 2020, 05:01:34"), Repository.data.items[0])
        assertEquals(TodoItem("test 3", createTime = "04 апреля 2020, 05:01:34"), Repository.data.items[1])
    }

    @Test
    fun testToggleDoneItem() {
        Repository.setData(listOf(
            TodoItem("test 1", isDone = true, createTime = "04 апреля 2020, 05:01:34"),
            TodoItem("test 2", createTime = "04 апреля 2020, 05:01:34"),
            TodoItem("test 3", isDone = true, createTime = "04 апреля 2020, 05:01:34")
        ))

        assertEquals(true, Repository.data.items[0].isDone)
        assertEquals(false, Repository.data.items[1].isDone)
        assertEquals(true, Repository.data.items[2].isDone)

        Repository.toggleDoneItem(1, {}, {})
        Repository.toggleDoneItem(2, {}, {})
        Repository.toggleDoneItem(3, {}, {})

        assertEquals(false, Repository.data.items[0].isDone)
        assertEquals(true, Repository.data.items[1].isDone)
        assertEquals(false, Repository.data.items[2].isDone)
    }
}