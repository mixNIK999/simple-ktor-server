package me.nikoliukin.mikhail

import java.io.File
import java.time.Instant
import java.time.format.DateTimeFormatter

object RequestLog {
    private const val fileName = "requests.log"
    fun clearLog() {
        getFile().writeText("")
    }

    fun writeToLog(content: String) {
        getFile().appendText("${DateTimeFormatter.ISO_INSTANT.format(Instant.now())} $content\n")
    }

    fun getFile() = File(fileName)
}