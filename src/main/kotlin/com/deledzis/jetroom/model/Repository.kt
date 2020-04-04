package com.deledzis.jetroom.model

import com.deledzis.jetroom.util.Consts.DATE_FORMAT
import com.deledzis.jetroom.util.dataToJson
import com.deledzis.jetroom.util.getCurrentDateTime
import com.deledzis.jetroom.util.toString
import com.deledzis.jetroom.util.writeFileContent

object Repository {
    var filterNotDoneEnabled: Boolean = false

    val data: TodoData =
        TodoData(0, mutableListOf())

    fun getForListing() = data.items.filter {
        if (filterNotDoneEnabled) !it.isDone else true
    }.toMutableList()

    fun setData(items: List<TodoItem>) {
        data.items.clear()
        data.items.addAll(items)
        data.count = items.size
    }

    fun addItem(message: String, onJsonifyError: () -> Unit, onWriteError: () -> Unit): TodoItem? {
        var item: TodoItem? =
            TodoItem(
                message = message,
                createTime = getCurrentDateTime().toString(DATE_FORMAT)
            )
        data.items.add(item!!)
        data.count++

        saveItems(
            onJsonifyError = {
                data.items.remove(item!!)
                data.count--
                item = null
                onJsonifyError()
            },
            onWriteError = {
                data.items.remove(item!!)
                data.count--
                item = null
                onWriteError()
            }
        )

        return item
    }

    fun removeItem(index: Int, onJsonifyError: () -> Unit, onWriteError: () -> Unit): Boolean {
        return if ((index - 1) in 0 until data.count) {
            val item = data.items[index - 1]
            data.items.remove(item)
            data.count--

            saveItems(
                onJsonifyError = {
                    data.items.add(item)
                    data.count++
                    onJsonifyError()
                },
                onWriteError = {
                    data.items.add(item)
                    data.count++
                    onWriteError()
                }
            )
            true
        } else {
            false
        }
    }

    fun toggleDoneItem(index: Int, onJsonifyError: () -> Unit, onWriteError: () -> Unit): Boolean {
        return if ((index - 1) in 0 until data.count) {
            data.items[index - 1].toggleDone()

            saveItems(
                onJsonifyError = {
                    data.items[index - 1].toggleDone()
                    onJsonifyError()
                },
                onWriteError = {
                    data.items[index - 1].toggleDone()
                    onWriteError()
                }
            )
            true
        } else {
            false
        }
    }

    private fun saveItems(onJsonifyError: () -> Unit, onWriteError: () -> Unit) {
        val json = dataToJson(data)
        if (!json.isNullOrBlank()) {
            if (!writeFileContent(json)) { onWriteError() }
        } else {
            onJsonifyError()
        }
    }
}