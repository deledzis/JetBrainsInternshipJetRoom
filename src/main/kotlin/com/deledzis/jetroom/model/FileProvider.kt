package com.deledzis.jetroom.model

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.io.File
import java.io.IOException

object FileProvider {
    private const val FILE_NAME = "todo-list.json"

    val file = File(FILE_NAME)

    val json = Json(JsonConfiguration.Stable.copy(prettyPrint = true))

    init {
        try {
            file.createNewFile()
        } catch (e: IOException) {
            error("CRITICAL: File read Error $FILE_NAME")
        } catch (e: SecurityException) {
            error("CRITICAL: File access Error $FILE_NAME")
        }
    }
}