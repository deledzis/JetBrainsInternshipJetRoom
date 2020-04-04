package com.deledzis.jetroom.model

import com.deledzis.jetroom.util.Consts.DATE_FORMAT
import com.deledzis.jetroom.util.Consts.L_LAST_EDIT
import com.deledzis.jetroom.util.getCurrentDateTime
import com.deledzis.jetroom.util.localized
import com.deledzis.jetroom.util.toString
import kotlinx.serialization.Serializable

@Serializable
data class TodoData(
    var count: Int,
    val items: MutableList<TodoItem>
)

@Serializable
data class TodoItem(
    val message: String,
    var isDone: Boolean = false,
    val createTime: String,
    var editTime: String? = null
) {
    fun toggleDone() {
        isDone = !isDone
        editTime = getCurrentDateTime().toString(format = DATE_FORMAT)
    }

    private fun getOutputTitle() = "${if (isDone) "[X]" else "[ ]"} - $message"

    private fun getOutputSubtitle() =
        localized(L_LAST_EDIT, editTime ?: createTime)

    override fun toString() = "${getOutputTitle()}\n${getOutputSubtitle()}\n"
}