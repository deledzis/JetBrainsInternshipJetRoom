package com.deledzis.jetroom.util

import com.deledzis.jetroom.model.FileProvider
import com.deledzis.jetroom.model.TodoData
import com.deledzis.jetroom.model.TodoItem
import com.deledzis.jetroom.util.Consts.DATE_FORMAT
import com.deledzis.jetroom.util.Consts.L_WELCOME
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.io.File
import java.util.*

class UtilsKtTest {
    companion object {
        var file: File? = null

        @BeforeAll
        @JvmStatic
        fun init() {
            file = File("todo-list.json")
        }

        @AfterAll
        @JvmStatic
        fun clear() {
            file = null
        }
    }

    @Test
    fun testReadFileContent() {
        val data = TodoData(
            count = 2, items = mutableListOf(
                TodoItem(
                    "test",
                    createTime = "test"
                ),
                TodoItem(
                    "test",
                    createTime = "test"
                )
            )
        )
        val json = FileProvider.json.stringify(TodoData.serializer(), data)
        file!!.writeText(json)

        val expected = "{\n" +
                "    \"count\": 2,\n" +
                "    \"items\": [\n" +
                "        {\n" +
                "            \"message\": \"test\",\n" +
                "            \"isDone\": false,\n" +
                "            \"createTime\": \"test\",\n" +
                "            \"editTime\": null\n" +
                "        },\n" +
                "        {\n" +
                "            \"message\": \"test\",\n" +
                "            \"isDone\": false,\n" +
                "            \"createTime\": \"test\",\n" +
                "            \"editTime\": null\n" +
                "        }\n" +
                "    ]\n" +
                "}"

        assertEquals(expected, readFileContent())
    }

    @Test
    fun testWriteFileContent() {
        writeFileContent("Test Content")

        assertEquals("Test Content", file!!.readText())
    }

    @Test
    fun testParseJsonToData() {
        val expected = TodoData(
            count = 1, items = mutableListOf(
                TodoItem(
                    message = "asd",
                    isDone = false,
                    createTime = "04 апреля 2020, 05:01:34",
                    editTime = null
                )
            )
        )
        val jsonStr = "{\n" +
                "    \"count\": 1,\n" +
                "    \"items\": [\n" +
                "        {\n" +
                "            \"message\": \"asd\",\n" +
                "            \"isDone\": false,\n" +
                "            \"createTime\": \"04 апреля 2020, 05:01:34\",\n" +
                "            \"editTime\": null\n" +
                "        }\n" +
                "    ]\n" +
                "}"
        assertEquals(expected, parseJsonToData(jsonStr))
    }

    @Test
    fun testDataToJson() {
        val data = TodoData(
            count = 1, items = mutableListOf(
                TodoItem(
                    message = "asd",
                    isDone = false,
                    createTime = "04 апреля 2020, 05:01:34",
                    editTime = null
                )
            )
        )
        val expected = "{\n" +
                "    \"count\": 1,\n" +
                "    \"items\": [\n" +
                "        {\n" +
                "            \"message\": \"asd\",\n" +
                "            \"isDone\": false,\n" +
                "            \"createTime\": \"04 апреля 2020, 05:01:34\",\n" +
                "            \"editTime\": null\n" +
                "        }\n" +
                "    ]\n" +
                "}"
        assertEquals(expected, dataToJson(data))
    }

    @Test
    fun testDataToString() {
        val cal1 = Calendar.getInstance().apply {
            set(2020, 3, 4, 3, 15, 30)
        }
        val cal2 = Calendar.getInstance().apply {
            set(1998, 3, 17, 22, 7, 5)
        }
        val expected1 = "04 апреля 2020, 03:15:30"
        val expected2 = "17 April 1998, 22:07:05"
        assertAll(
            "dates",
            { assertEquals(expected1, cal1.time.toString(format = DATE_FORMAT, locale = Locale("ru"))) },
            { assertEquals(expected2, cal2.time.toString(format = DATE_FORMAT, locale = Locale("en"))) }
        )
    }

    @Test
    fun testLocalized() {
        if (Locale.getDefault().language == Locale.forLanguageTag("ru").language) {
            assertEquals("Добро пожаловать в To-Do List!", localized(L_WELCOME))
        } else {
            assertEquals("Welcome to the To-Do List!", localized(L_WELCOME))
        }
    }
}