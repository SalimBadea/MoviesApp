package com.creditfins.moviesApp.helper

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import androidx.annotation.StringRes
import com.creditfins.moviesApp.R
import java.util.*
import java.util.regex.Pattern

object Utils {
    fun setWindowLayoutForDialog(dialog: Dialog, width: Double, height: Double) {
        val window = dialog.window
        if (window != null) {
            val display = dialog.context.resources.displayMetrics

            val displayWidth = display.widthPixels
            val displayHeight = display.heightPixels

            window.setLayout((displayWidth * width).toInt(), (displayHeight * height).toInt())
            window.setGravity(Gravity.CENTER)
        }
    }

    fun isNotConnected(t: Throwable): Boolean {
        return null == t.message || t.message!!.contains("No address associated with hostname")
                || t.message!!.contains("connect timed out")
                || t.message!!.contains("failed to connect to")
                || t.message!!.contains("timeout")
                || t.message!!.contains("ECONNRESET")
    }

    fun share(context: Context, @StringRes shareBody: Int) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(
            Intent.EXTRA_SUBJECT, context.getString(R.string.app_name)
        )
        sharingIntent.putExtra(
            Intent.EXTRA_TEXT,
            String.format(
                context.getString(shareBody),
                context.getString(R.string.share_link) + context.packageName
            )
        )
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"))
    }

    fun hideSoftInput(view: View?) {
        view?.let { v ->
            val imm =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    fun isValidArabicLanguage(text: String): Boolean {
        val re = Regex("[A-Za-z]")
        return re.containsMatchIn(text)
    }

    fun isValidEnglishLanguage(text: String): Boolean {
        val re = Regex("[ا-ىءإأؤئيًٌٍَُِّ]")
        return re.containsMatchIn(text)
    }

    fun isValidEmail(email: String): Boolean {
        val EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        val pattern = Pattern.compile(
            EMAIL_PATTERN,
            Pattern.CASE_INSENSITIVE
        )
        val matcher = pattern.matcher(email)
        return !matcher.matches()
    }

    fun isValidPhoneNumberEgypt(s: CharSequence): Boolean {
        val firstThreeNumber = StringBuilder()
        if (s.length > 3) {
            for (i in 0..3) {
                firstThreeNumber.append(s[i])
            }
        }
        return TextUtils.isEmpty(s)
                || (!firstThreeNumber.toString().contains("010")
                && !firstThreeNumber.toString().contains("011")
                && !firstThreeNumber.toString().contains("012")
                && !firstThreeNumber.toString().contains("015"))
                || s.length != 11
    }

    /**
     * show and hide password
     * use this method when you want show error and eye beside each other
     *
     * @param show     before use this method show is false
     * @param editText password input
     * @param image    change imageResource background from show to hide and Reverse
     */
    fun showPassword(show: Boolean, editText: EditText, image: ImageButton) {
        if (show) {
            editText.transformationMethod = null
            editText.setSelection(editText.length())
            image.setImageResource(R.drawable.ic_visibility)
        } else {
            editText.transformationMethod = PasswordTransformationMethod()
            editText.setSelection(editText.length())
            image.setImageResource(R.drawable.ic_visibility_off)
        }

    }

    /**
     *  return date upper than 2 hour
     *  @param date is date for user was chosen with format 'yyyy-MM-dd HH:mm:ss'
     */
    fun timeWasChosen(date: String): Boolean {
        val year = date.substring(0, 4).toInt()
        val month = date.substring(5, 7).toInt()
        val day = date.substring(8, 10).toInt()
        val hour = date.substring(11, 13).toInt()
        val minute = date.substring(14, 16).toInt()
        val second = date.substring(17, 19).toInt()
        val calender = Calendar.getInstance()
        val now = calender.timeInMillis
        calender.set(year, month - 1, day, hour, minute, second)
        val wasChosen = calender.timeInMillis
        val lessThanTwoHour = wasChosen - now
        return lessThanTwoHour > 7200000
    }
}