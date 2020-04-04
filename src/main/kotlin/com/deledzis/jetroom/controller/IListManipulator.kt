package com.deledzis.jetroom.controller

import com.deledzis.jetroom.model.TodoItem

interface IListManipulator {
    fun addNewTask(message: String, onJsonifyError: () -> Unit, onWriteError: () -> Unit): TodoItem?

    fun removeTask(index: Int, onJsonifyError: () -> Unit, onWriteError: () -> Unit): Boolean

    fun toggleTaskDoneStatus(index: Int, onJsonifyError: () -> Unit, onWriteError: () -> Unit): Boolean
}