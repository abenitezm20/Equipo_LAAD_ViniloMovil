package com.laad.viniloapp.utilities

import java.text.SimpleDateFormat
import java.util.Locale

class Utils {

    companion object {
        fun formatDate(dateString: String): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val date = dateFormat.parse(dateString)
            val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            return outputFormat.format(date)
        }
    }

}