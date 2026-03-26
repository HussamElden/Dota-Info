package com.example.core

import sun.security.util.Debug

class Logger (
    private val tag: String,
    private val isDebug: Boolean=true
) {
    fun log(message: String) {
        if (isDebug) {
            printLogD(tag, message)
            return
        }
        // Crashlytics
    }

    companion object Factory {
        fun buildDebug(tag: String): Logger {
            return Logger(tag, true)
        }
        fun buildRelease(tag: String): Logger {
            return Logger(tag, false)
        }
    }
}
fun printLogD(tag: String, message: String){
    println("Debug: $tag: $message")
}