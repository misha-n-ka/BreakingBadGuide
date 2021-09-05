package com.breakingBadGuide.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CustomDateFormatTest {

    @Test
    fun formatDateTimeIfDateIsNull_returnsMinus() {
        val result = CustomDateFormat.formatDateTime(null)
        assertThat(result).isEqualTo("-")
    }

    @Test
    fun parseAndFormatDateTime_returnsCorrectDateString() {
        val date = CustomDateFormat.parseDateTime("22-01-1999", "dd-MM-yyyy")
        val result = CustomDateFormat.formatDateTime(date)
        assertThat(result).isEqualTo("22 января 1999")
    }

    @Test
    fun parseAndFormatDateTimeWithNotValidPattern_returnsMinus() {
        val date = CustomDateFormat.parseDateTime("22-01-1999", "#$#(*Q@U$")
        val result = CustomDateFormat.formatDateTime(date)
        assertThat(result).isEqualTo("-")
    }

    @Test
    fun parseAndFormatDateTimeWithNotMatchPattern_returnsMinus() {
        val date = CustomDateFormat.parseDateTime("22-01-1999", "dd MM")
        val result = CustomDateFormat.formatDateTime(date)
        assertThat(result).isEqualTo("-")
    }

}