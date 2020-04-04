package com.deledzis.jetroom.controller

import com.deledzis.jetroom.model.Repository
import com.deledzis.jetroom.model.TodoItem

class ListManipulatorImpl : IListManipulator {
    override fun addNewTask(message: String, onJsonifyError: () -> Unit, onWriteError: () -> Unit): TodoItem? {
        return Repository.addItem(
            message = message,
            onJsonifyError = onJsonifyError,
            onWriteError = onWriteError
        )
    }

    override fun removeTask(index: Int, onJsonifyError: () -> Unit, onWriteError: () -> Unit): Boolean {
        return Repository.removeItem(
            index = index,
            onJsonifyError = onJsonifyError,
            onWriteError = onWriteError
        )
    }

    override fun toggleTaskDoneStatus(index: Int, onJsonifyError: () -> Unit, onWriteError: () -> Unit): Boolean {
        return Repository.toggleDoneItem(
            index = index,
            onJsonifyError = onJsonifyError,
            onWriteError = onWriteError
        )
    }
}