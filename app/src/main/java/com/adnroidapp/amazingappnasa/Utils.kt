package com.adnroidapp.amazingappnasa

import android.icu.util.Calendar
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

fun Fragment.toast(string: String) {
    Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
}

@RequiresApi(Build.VERSION_CODES.N)
fun getDate(numberDay: Int): String {
    val date = GregorianCalendar()

    date.add(Calendar.DAY_OF_MONTH, numberDay)
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(date.time)
}