package com.example.todomvvmkotolin.util

class Event<out T>(private val content: T) {
    var hasBeenHandled = false
        private set // 外部からの参照はできるが書き込みはできない

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}