package br.com.spaceflight.core.util.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(dateString: String): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
        .withZone(ZoneId.from(ZoneOffset.UTC))
    with(formatter) {
        val date: Instant = Instant.parse(dateString)
        return this.format(date)
    }
}