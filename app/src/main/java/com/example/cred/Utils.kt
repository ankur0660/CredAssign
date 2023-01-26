package com.example.cred

fun <T> removeSlice(list: MutableList<T>, from: Int, end: Int) {
    for (i in from..end.coerceAtMost(list.size - 1)) {
        list.removeAt(i)
    }
}