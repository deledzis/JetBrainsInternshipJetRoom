package com.deledzis.jetroom.model

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.io.File

internal class FileProviderTest {

    @Test
    fun getFile() {
        val file = File("todo-list.json")

        assertAll(
            "file checks",
            { assertEquals(file, FileProvider.file) },
            { assertEquals(file.absoluteFile, FileProvider.file.absoluteFile) },
            { assertEquals(file.name, FileProvider.file.name) },
            { assertEquals(file.path, FileProvider.file.path) },
            { assertEquals(file.absolutePath, FileProvider.file.absolutePath) },
            { assertEquals(file.parent, FileProvider.file.parent) },
            { assertEquals(file.parentFile, FileProvider.file.parentFile) },
            { assertEquals(file.freeSpace, FileProvider.file.freeSpace) },
            { assertEquals(file.totalSpace, FileProvider.file.totalSpace) },
            { assertEquals(file.usableSpace, FileProvider.file.usableSpace) },
            { assertEquals(file.canRead(), FileProvider.file.canRead()) },
            { assertEquals(file.canWrite(), FileProvider.file.canWrite()) },
            { assertEquals(file.canExecute(), FileProvider.file.canExecute()) }
        )
    }

    @Test
    fun getJson() {
        val item = TodoItem(
            message = "asd",
            isDone = false,
            createTime = "04 апреля 2020, 05:01:34",
            editTime = "05 апреля 2020, 12:24:34"
        )

        val jsonStr = "{\n" +
                "   \"message\": \"asd\",\n" +
                "   \"isDone\": false,\n" +
                "   \"createTime\": \"04 апреля 2020, 05:01:34\",\n" +
                "   \"editTime\": null\n" +
                "}\n"

        val json = Json(JsonConfiguration.Stable.copy(prettyPrint = true))

        assertAll(
            "json checks",
            { assertEquals(json.stringify(TodoItem.serializer(), item), FileProvider.json.stringify(
                TodoItem.serializer(), item)) },
            { assertEquals(json.parse(TodoItem.serializer(), jsonStr), FileProvider.json.parse(
                TodoItem.serializer(), jsonStr)) }
        )
    }
}