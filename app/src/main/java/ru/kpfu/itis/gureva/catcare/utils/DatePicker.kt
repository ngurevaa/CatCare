package ru.kpfu.itis.gureva.catcare.utils

import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.CompositeDateValidator
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date

object DatePicker {
    private const val FROM = "01.01.1970"
    const val MILLISECONDS_PER_DAY = 86_400_000;

    fun getDatePicker(date: Date, from: String = this.FROM): MaterialDatePicker<Long> {
        val validator1: CalendarConstraints.DateValidator = DateValidatorPointBackward.now()
        val validator2 = DateValidatorPointForward.from(SimpleDateFormat(Formatter.DATE_WITHOUT_TIME).parse(from).time)

        return MaterialDatePicker.Builder.datePicker()
            .setSelection(date.time)
            .setCalendarConstraints(
                CalendarConstraints.Builder().setValidator(
                    CompositeDateValidator.allOf(listOf(validator1, validator2))
                ).build()
            )
            .build()
    }
}
