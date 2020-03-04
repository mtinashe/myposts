package com.mtinashe.myposts.utils

object StringUtils {
    fun capitalize(str: String): String? {
        return if (str == null || str.isEmpty()) {
            str
        } else str.substring(0, 1).toUpperCase() + str.substring(1)
    }
}