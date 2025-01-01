package com.example.ifataliku.widgets

import android.app.DatePickerDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.ifataliku.core.di.Utils
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DatePickerWidget(
    value: String,
    onValueChange: (String) -> Unit,
    pattern: String = "dd/MM/yyyy",
    minDate: Long= 0,
    widgetDisplayer: @Composable (onClick: () -> Unit) -> Unit = {
        IconButton(onClick = it) {
            Icon(
                imageVector = Icons.Filled.CalendarMonth,
                contentDescription = "calendar",
                tint = Color.White
            )
        }
    }
) {

    val formatter = DateTimeFormatter.ofPattern(pattern)
    val date = if (value.isNotBlank()) LocalDate.parse(value, formatter) else LocalDate.now()
    val dialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            onValueChange(LocalDate.of(year, month + 1, dayOfMonth).format(formatter))
        },
        date.year,
        date.monthValue - 1,
        date.dayOfMonth,
    )
    dialog.datePicker.minDate = minDate
    widgetDisplayer(dialog::show)
}