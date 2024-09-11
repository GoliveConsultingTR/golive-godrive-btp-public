package com.golive.godrive.btppublic.app

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.golive.godrive.btppublic.R
import com.golive.godrive.btppublic.databinding.ActivityWelcomeBinding

import com.sap.cloud.mobile.flowv2.core.FlowStepFragment
import com.sap.cloud.mobile.foundation.mobileservices.SDKInitializer.getService
import com.sap.cloud.mobile.foundation.theme.ThemeDownloadService
import java.util.Locale


class WelcomeStepFragment : FlowStepFragment() {
    private lateinit var binding: ActivityWelcomeBinding
    private val logos: Pair<Bitmap?, Bitmap?> ? by lazy {
        var lightLogo: Bitmap? = null
        var darkLogo: Bitmap? = null
        var options = BitmapFactory.Options()
        options.inJustDecodeBounds = false

        getService(ThemeDownloadService::class)?.let { service ->
            service.getLightLogo()?.also { logo ->  lightLogo = BitmapFactory.decodeFile(logo.path, options)}
        }
        getService(ThemeDownloadService::class)?.let { service ->
            service.getDarkLogo()?.also { logo ->  darkLogo = BitmapFactory.decodeFile(logo.path, options)}
        }
        return@lazy Pair(lightLogo, darkLogo)
    }
    private lateinit var demoButton: Button
    private var selectedLanguageCode: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadLocale()
        val view = inflater.inflate(R.layout.activity_welcome, container, false)

        val agreeCheckBox = view.findViewById<CheckBox>(R.id.launchscreen_button_eula)
        val primaryButton = view.findViewById<Button>(R.id.launchscreen_button_primary)
        demoButton = view.findViewById<Button>(R.id.launchscreen_button_demo)

        val launchscreenScrollView = view.findViewById<ScrollView>(R.id.launchscreen_scrollview)
        val launchscreenContainer = view.findViewById<ConstraintLayout>(R.id.launchscreen_container)
        val checkboxLayout = view.findViewById<LinearLayout>(R.id.checkbox_Layout)
        val launchscreenEulaDescription = view.findViewById<TextView>(R.id.launchscreen_eula_description)
        val launchscreenFootnote = view.findViewById<TextView>(R.id.launchscreen_footnote)

        setLanguageButton()

        launchscreenFootnote.visibility = View.INVISIBLE
        primaryButton.setBackgroundColor(resources.getColor(R.color.app_color_green))
        demoButton.setTextColor(resources.getColor(R.color.active_item))
        primaryButton.isEnabled = false
        setAgreeButtonOnClickListener(agreeCheckBox, primaryButton)

        demoButton.setOnClickListener {
            showLanguageSelectionDialog()
        }
        primaryButton.setOnClickListener {
            stepDone(R.id.stepWelcome, popCurrent = true)
        }

        var eulaText = getString(R.string.eula_text)
        var kvkkText: String = getString(R.string.eula_text_kvkk)
        var privacyText: String = getString(R.string.eula_text_privacy)
        eulaText = eulaText.replace("%1", kvkkText)
        eulaText = eulaText.replace("%2", privacyText)
        var privacyPolicyUrl: String = ""
        var kvkkUrl: String = ""
        if (Locale.getDefault().language == "tr") {
            privacyPolicyUrl = "https://golive.com.tr/downloads/kvkk/gizlilik.pdf"
            kvkkUrl = "https://golive.com.tr/downloads/kvkk/KVKK.pdf"
        } else {
            privacyPolicyUrl = "https://golive.com.tr/downloads/kvkk/privacy.pdf"
            kvkkUrl = "https://golive.com.tr/downloads/kvkk/gdpr.pdf"
        }

        val ss = SpannableString(eulaText)
        val clickableSpanPrivacyPolicy = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(Uri.parse(privacyPolicyUrl), "application/pdf")
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                }
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
            }
        }
        val clickableSpanKVKK = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(Uri.parse(kvkkUrl), "application/pdf")
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                }
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
            }
        }

        ss.setSpan(clickableSpanPrivacyPolicy, eulaText.indexOf(privacyText), eulaText.indexOf(privacyText) + privacyText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ss.setSpan(clickableSpanKVKK, eulaText.indexOf(kvkkText), eulaText.indexOf(kvkkText) + kvkkText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        launchscreenEulaDescription.text = ss
        launchscreenEulaDescription.movementMethod = LinkMovementMethod.getInstance()
        launchscreenEulaDescription.highlightColor = Color.TRANSPARENT

        return view
    }

    private fun setAgreeButtonOnClickListener(agreeCheckBox: CheckBox, primaryButton: Button) {
        agreeCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            primaryButton.isEnabled = isChecked
        }
    }

    fun showLanguageSelectionDialog() {
        val listItems = arrayOf(
            getString(R.string.english),
            getString(R.string.arabic),
            getString(R.string.hindi),
            getString(R.string.german),
            getString(R.string.turkish)
        )

        val sharedPreferences = context?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val selectedLanguage = sharedPreferences?.getString("selectedLanguage", getString(R.string.turkish))

        val defaultLanguageIndex = listItems.indexOf(getLanguageName(selectedLanguage!!))
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.language)
            .setSingleChoiceItems(listItems, defaultLanguageIndex) { dialog, which ->
                val selectedLanguage = when (which) {
                    0 -> "en"
                    1 -> "ar"
                    2 -> "hi"
                    3 -> "de"
                    4 -> "tr"
                    else -> "en"
                }
                setLocale(selectedLanguage)
                saveSelectedLanguage(selectedLanguage)

                updateDemoButtonText(selectedLanguage)
                dialog.dismiss()

                startWelcomeActivity()
            }

        builder.show()
    }
    private fun setLanguageButton(){
        val displayName = getSystemLocale()
        val languageLabel = getString(R.string.language)
        val buttonText = "$languageLabel : $displayName"
        demoButton.text = buttonText
    }

    private fun getSystemLocale() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        LocaleList.getDefault().get(0).language.uppercase()
    } else {
        Locale.getDefault().language.uppercase()
    }

    private fun saveSelectedLanguage(languageCode: String) {
        val sharedPreferences = context?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString("selectedLanguage", getString(R.string.language_code))
        editor?.apply()
    }
    private fun updateDemoButtonText(languageCode: String) {
        val languageName = getString(R.string.language_code)
        val languageLabel = getString(R.string.language)
        val buttonText = "$languageLabel : $languageName"
        demoButton.text = buttonText
    }
    private fun startWelcomeActivity() {
        val intent = Intent(context, WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        context?.startActivity(intent)
    }
    private fun getLanguageName(languageCode: String): String {
        return when (languageCode) {
            "en" -> getString(R.string.english)
            "ar" -> getString(R.string.arabic)
            "hi" -> getString(R.string.hindi)
            "de" -> getString(R.string.german)
            "tr" -> getString(R.string.turkish)
            else -> getString(R.string.english)
        }
    }


    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        context?.resources?.updateConfiguration(config, context?.resources?.displayMetrics)

        val sharedPreferences = context?.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString("My_Lang", languageCode)
        editor?.apply()
    }

    fun loadLocale() {
        val sharedPreferences = context?.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val language = sharedPreferences?.getString("My_Lang", "")

        val defaultLanguage = if (language.isNullOrEmpty()) {
            getSystemLocale()
        } else {
            language
        }

        setLocale(defaultLanguage)
    }
}
