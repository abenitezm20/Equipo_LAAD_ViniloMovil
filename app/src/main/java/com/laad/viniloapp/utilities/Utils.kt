package com.laad.viniloapp.utilities

import android.content.Context
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.laad.viniloapp.R
import java.net.MalformedURLException
import java.net.URISyntaxException
import java.net.URL
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

        fun showToast(context: Context, text: String) {
            val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
            toast.view?.findViewById<TextView>(android.R.id.message)
                ?.setTextColor(ContextCompat.getColor(context, R.color.white))
            toast.show()
        }
    }

}