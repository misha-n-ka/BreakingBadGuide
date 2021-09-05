package com.breakingBadGuide.utils

import java.text.SimpleDateFormat
import java.util.*

class CustomDateFormat {

    companion object {

        // formatting Date to String (Pattern -> Constants.BBPatternTo)
        fun formatDateTime(date: Date?): String {
            if (date == null) return "-"
            val calendar = Calendar.getInstance().apply {
                time = date
            }
            val primaryLocale = Locale.getDefault()
            val formatter = SimpleDateFormat(Constants.BBPatternTo, primaryLocale)

            return formatter.format(calendar.time)
        }

        // parsing Date from character.birthday (Pattern -> Constants.BBPatternFrom)
        fun parseDateTime(date: String, pattern: String): Date? {
            lateinit var sdf: SimpleDateFormat
            return try {
                sdf = SimpleDateFormat(pattern, Locale.getDefault())
                sdf.parse(date)
            } catch (e: Exception) {
                null
            }
        }
    }
}