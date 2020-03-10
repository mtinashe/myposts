package com.mtinashe.myposts.utils

import java.util.Locale

object StringUtils {
    fun capitalize(str: String): String? {
        return if (str.isEmpty()) {
            str
        } else str.substring(0, 1).toUpperCase(Locale.ROOT) + str.substring(1)
    }
}
