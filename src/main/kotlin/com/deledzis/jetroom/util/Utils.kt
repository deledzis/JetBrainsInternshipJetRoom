package com.deledzis.jetroom.util

import com.deledzis.jetroom.model.TodoData
import com.deledzis.jetroom.model.FileProvider
import com.deledzis.jetroom.view.resources
import kotlinx.serialization.SerializationException
import java.text.MessageFormat
import java.text.SimpleDateFormat
import java.util.*

fun readFileContent(): String? {
    return if (FileProvider.file.canRead()) {
        FileProvider.file.readText()
    } else {
        null
    }
}

fun writeFileContent(content: String): Boolean {
    return if (FileProvider.file.canWrite()) {
        FileProvider.file.writeText(content)
        true
    } else {
        false
    }
}

fun parseJsonToData(jsonString: String): TodoData? {
    return try {
        FileProvider.json.parse(TodoData.serializer(), jsonString)
    } catch (e: SerializationException) {
        null
    }
}

fun dataToJson(data: TodoData): String? {
    return try {
        FileProvider.json.stringify(TodoData.serializer(), data)
    } catch (e: SerializationException) {
        null
    }
}

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}

fun localized(key: String, vararg params: Any): String = MessageFormat.format(resources.getString(key), *params)