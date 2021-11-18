package br.com.spaceflight.core.util.extensions

import android.os.Build
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*


fun formatDate(dateString: String): String {
    val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        DateTimeFormatter.ofPattern("dd/MM/uuuu")
            .withLocale(Locale("pt", "br"))
            .withZone(ZoneId.from(ZoneOffset.UTC))
    } else {
        try {
            val formatter = SimpleDateFormat("dd/MM/uuuu", Locale("pt", "br"))
            dateString.format(formatter)
        } catch (ep: ParseException) {
            throw ParseException(ep.message.toString(), ep.errorOffset)
        }
        TODO("VERSION.SDK_INT < O")
    }
    with(formatter) {
        val date: Instant = Instant.parse(dateString)
        return this.format(date)
    }
}