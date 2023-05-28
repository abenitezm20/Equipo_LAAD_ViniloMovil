package com.laad.viniloapp.utilities

import android.content.Context
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.laad.viniloapp.R
import java.text.SimpleDateFormat
import java.util.Locale

class Utils {

    companion object {
        fun formatDate(dateString: String): String {
            return formatDate("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "dd-MM-yyyy", dateString)
        }

        fun formatDateToDB(dateString: String): String {
            return formatDate("MM/dd/yyyy", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", dateString)
        }

        private fun formatDate(originalFormat: String, format: String, dateString: String): String {
            val dateFormat = SimpleDateFormat(originalFormat, Locale.getDefault())
            val date = dateFormat.parse(dateString)
            val outputFormat = SimpleDateFormat(format, Locale.getDefault())
            return outputFormat.format(date)
        }

        fun showToast(context: Context, text: String) {
            val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
            toast.view?.findViewById<TextView>(android.R.id.message)
                ?.setTextColor(ContextCompat.getColor(context, R.color.white))
            toast.show()
        }
    }

}