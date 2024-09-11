package com.golive.godrive.btppublic.util

import android.app.Activity
import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.LifecycleOwner
import com.google.common.io.CharStreams
import com.sap.cloud.mobile.foundation.logging.LoggingService
import com.sap.cloud.mobile.foundation.mobileservices.SDKInitializer
import com.sap.cloud.mobile.foundation.mobileservices.ServiceListener
import com.sap.cloud.mobile.foundation.mobileservices.ServiceResult
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.Locale

object Util {

    fun isEmptyString(s1: String?): Boolean {
        return s1 == null || s1.trim().isEmpty()
    }

    fun openSoftKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    fun hideSoftKeyboard(activity: Activity) {
        val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        if (inputManager != null && activity.currentFocus != null && activity.currentFocus!!.windowToken != null) {
            inputManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    fun stringParams(base: String, vararg params: String): String {
        var result = base
        for (i in params.indices) {
            result = result.replace("%${i + 1}", params[i])
        }
        return result
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        if (view.windowToken != null && imm != null) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }



    fun disableEnableControls(enable: Boolean, vg: ViewGroup) {
        if (vg != null) {
            for (i in 0 until vg.childCount) {
                val child = vg.getChildAt(i)
                if (child != null) {
                    child.isEnabled = enable
                    if (child is ViewGroup) {
                        disableEnableControls(enable, child)
                    }
                }
            }
        }
    }

    fun getItemBeforeHyphen(word: String): String {
        var wordDivided = word
        val splitCharacter = "-"
        val splitCharacter2 = " - "
        if (wordDivided.contains(splitCharacter2)) {
            val parts = wordDivided.split(splitCharacter2).toTypedArray()
            if (parts != null && parts.isNotEmpty()) {
                wordDivided = parts[0].trim()
            }
        } else if (wordDivided.contains(splitCharacter)) {
            val parts = wordDivided.split(splitCharacter).toTypedArray()
            if (parts != null && parts.isNotEmpty()) {
                wordDivided = parts[0].trim()
            }
        }
        return wordDivided
    }

    fun getItemBeforeChar(word: String, charStr: String): String {
        var wordDivided = word
        val splitCharacter = charStr

        if (wordDivided.contains(splitCharacter)) {
            val parts = wordDivided.split(splitCharacter).toTypedArray()
            if (parts != null && parts.isNotEmpty()) {
                wordDivided = parts[0].trim()
            }
        }
        return wordDivided
    }

    fun getItemAfterChar(word: String, charStr: String): String {
        var wordDivided = word
        val splitCharacter = charStr

        if (wordDivided.contains(splitCharacter)) {
            val parts = wordDivided.split(splitCharacter).toTypedArray()
            if (parts != null && parts.isNotEmpty()) {
                wordDivided = parts[0].trim()
            }
        }
        return wordDivided
    }

    fun getItemAfterHyphen(word: String): String {
        var wordDivided = word
        val splitCharacter = "-"
        val splitCharacter2 = " - "
        if (wordDivided.contains(splitCharacter2)) {
            val parts = wordDivided.split(splitCharacter2).toTypedArray()
            if (parts != null && parts.size > 1) {
                wordDivided = parts[1].trim()
            }
        } else if (wordDivided.contains(splitCharacter)) {
            val parts = wordDivided.split(splitCharacter).toTypedArray()
            if (parts != null && parts.size > 1) {
                wordDivided = parts[1].trim()
            }
        }
        return wordDivided
    }

    fun hexToByteArray(s: String): ByteArray {
        val len = s.length
        val data = ByteArray(len / 2)

        var i = 0
        while (i < len) {
            data[i / 2] = ((Character.digit(s[i], 16) shl 4) + Character.digit(s[i + 1], 16)).toByte()
            i += 2
        }

        return data
    }


    fun toTitleCase(str: String): String {
        if (str.isEmpty()) {
            return str
        }

        return str.split("\\s+")
            .map { it.substring(0, 1).toUpperCase(Locale.getDefault()) + it.substring(1) }
            .joinToString(" ")
    }

    fun dpToPixel(context: Context, dip: Float): Int {
        val r = context.resources
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dip,
            r.displayMetrics
        )
        return px.toInt()
    }

    fun resizeAndConverBase64(context: Context, uri: Uri): String? {
        var encodedString: String? = null
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val `in` = BitmapFactory.decodeStream(inputStream)
            val scaleW = 500
            val out = Bitmap.createScaledBitmap(`in`, scaleW, (scaleW * `in`.height) / `in`.width, false)
            val bo = ByteArrayOutputStream()
            out.compress(Bitmap.CompressFormat.JPEG, 60, bo)
            `in`.recycle()
            out.recycle()
            encodedString = Base64.encodeToString(bo.toByteArray(), Base64.DEFAULT)
        } catch (e: Exception) {
        }
        return encodedString
    }

    fun isEqual(s1: String?, s2: String?): Boolean {
        return s1 != null && s2 != null && s1 == s2
    }

    fun isEqualsIgnoreCase(s1: String?, s2: String?): Boolean {
        return s1 != null && s2 != null && s1.equals(s2, ignoreCase = true)
    }

    fun getAssetString(context: Context, filename: String): String? {
        val mg: AssetManager = context.resources.assets
        var `is`: InputStream? = null
        var retval: String? = null
        try {
            `is` = mg.open(filename)
            retval = CharStreams.toString(InputStreamReader(`is`, "UTF-8"))
        } catch (ex: IOException) {
        } finally {
            if (`is` != null) {
                try {
                    `is`.close()
                } catch (e: Exception) {
                }
            }
        }
        return retval
    }

    fun money(amount: String): String {
        return try {
            money(amount.trim().toDouble())
        } catch (e: Exception) {
            money(0.0)
        }
    }


    fun money(d: Double): String {
        var retval = "" + d
        var start_counter: Int
        if (retval.endsWith(".0") || retval.indexOf(".00") != -1 || retval.indexOf(".0E") != -1) {
            retval = retval.substring(0, retval.indexOf("."))
            start_counter = 3 - retval.length % 3
        } else if (retval.indexOf(".") != -1) {
            val dot = retval.indexOf(".")
            if (dot == retval.length - 2) {
                retval += "0"
            } else if (dot < retval.length - 3) {

                retval = retval.substring(0, dot + 3)
            }
            retval = retval.replace(".", ",")
            start_counter = 3 - retval.indexOf(",") % 3
        } else {
            start_counter = 3 - retval.length % 3
        }
        val tmp = retval

        retval = ""
        var i = 0
        while (i < tmp.length) {
            start_counter = (start_counter + 1) % 3
            val c = tmp[i]
            retval += c
            if (start_counter % 3 == 0 && i != tmp.length - 1 && (i < tmp.indexOf(",") - 1 || tmp.indexOf(",") == -1)) {
                retval += "."
            }
            if (c == ',') {
                retval += tmp.substring(i + 1)
                break
            }
            i++
        }
        return retval
    }

    fun cleanZero(input: String?): String? {
        var input = input
        while (input != null && input.length > 1) {
            if (input[0] == '0' && input[1] != '.') {
                input = input.substring(1)
            } else {
                break
            }
        }

        while (input != null && input.length > 1 && input.indexOf(".") != -1) {
            if (input[input.length - 1] == '0' && input[input.length - 2] != '.') {
                input = input.substring(0, input.length - 1)
            } else {
                break
            }
        }
        if (input != null && input.endsWith(".0")) {
            input = input.substring(0, input.length - 2)
        }
        if (input != null && input.startsWith(".")) {
            input = "0$input"
        }
        return input
    }
/*
    fun sendLogEvent(context: Context, eventName: String, screenName: String){
        val sharedPreferences = context?.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val userId = sharedPreferences?.getString("userId", "")
        var firebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(context)
        firebaseAnalytics.setAnalyticsCollectionEnabled(true)
        val bundle = Bundle()
        bundle.putString("userId", userId)
        bundle.putString("screenName", screenName)
        firebaseAnalytics.logEvent(eventName, bundle)
    }

 */
    fun uploadLog(lifecycleOwner: LifecycleOwner){
        SDKInitializer.getService(LoggingService::class)?.also { logging ->
            logging.upload(owner = lifecycleOwner, listener = object : ServiceListener<Boolean> {
                override fun onServiceDone(result: ServiceResult<Boolean>){

                }
            })
        }
    }


}