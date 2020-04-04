package com.deledzis.jetroom.controller

import com.deledzis.jetroom.model.Repository
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class ListManipulatorImplTest {
    companion object {
        var listManipulatorImpl: ListManipulatorImpl? = null

        @BeforeAll
        @JvmStatic
        fun init() {
            listManipulatorImpl = ListManipulatorImpl()
        }

        @AfterAll
        @JvmStatic
        fun clear() {
            listManipulatorImpl = null
            Repository.setData(mutableListOf())
        }
    }

    @Test
    fun addNewTask() {
        val countBefore = Repository.data.count
        val result = listManipulatorImpl!!.addNewTask("test", {}, {})
        val countAfter = Repository.data.count

        if (result != null) {
            assertEquals(countBefore + 1, countAfter)
        } else {
            assertEquals(countBefore, countAfter)
        }
    }

    @Test
    fun removeTask() {
        Repository.addItem("new", {}, {})
        val countBefore = Repository.data.count
        val result = listManipulatorImpl!!.removeTask(1, {}, {})
        val countAfter = Repository.data.count

        if (result) {
            assertEquals(countBefore - 1, countAfter)
        } else {
            assertEquals(countBefore, countAfter)
        }
    }

    @Test
    fun toggleTaskDoneStatus() {
        Repository.addItem("new", {}, {})
        val idx = Repository.data.count - 1
        assertEquals(false, Repository.data.items[idx].isDone)

        val result = listManipulatorImpl!!.toggleTaskDoneStatus(idx, {}, {})
        if (result) {
            assertEquals(true, Repository.data.items[idx].isDone)
        } else {
            assertEquals(false, Repository.data.items[idx].isDone)
        }

        val status = Repository.data.items[idx].isDone
        val result2 = listManipulatorImpl!!.toggleTaskDoneStatus(idx, {}, {})
        if (result) {
            assertEquals(!status, Repository.data.items[idx].isDone)
        } else {
            assertEquals(status, Repository.data.items[idx].isDone)
        }
    }
}