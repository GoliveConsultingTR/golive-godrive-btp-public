package com.golive.godrive.btppublic.app

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.golive.godrive.btppublic.R
import com.golive.godrive.btppublic.databinding.ActivitySplashBinding
import com.golive.godrive.btppublic.notification.PushService
import com.golive.godrive.btppublic.util.Util

import com.sap.cloud.mobile.flowv2.core.DialogHelper
import com.sap.cloud.mobile.flowv2.core.Flow
import com.sap.cloud.mobile.flowv2.core.FlowContextBuilder
import com.sap.cloud.mobile.flowv2.ext.FlowOptions
import com.sap.cloud.mobile.foundation.authentication.AppLifecycleCallbackHandler
import com.sap.cloud.mobile.foundation.configurationprovider.ConfigurationLoader
import com.sap.cloud.mobile.foundation.configurationprovider.ConfigurationLoaderCallback
import com.sap.cloud.mobile.foundation.configurationprovider.ConfigurationPersistenceException
import com.sap.cloud.mobile.foundation.configurationprovider.ConfigurationProvider
import com.sap.cloud.mobile.foundation.configurationprovider.ConfigurationProviderError
import com.sap.cloud.mobile.foundation.configurationprovider.DefaultPersistenceMethod
import com.sap.cloud.mobile.foundation.configurationprovider.FileConfigurationProvider
import com.sap.cloud.mobile.foundation.configurationprovider.ProviderIdentifier
import com.sap.cloud.mobile.foundation.configurationprovider.UserInputs
import com.sap.cloud.mobile.foundation.mobileservices.ApplicationStates
import com.sap.cloud.mobile.foundation.mobileservices.SDKInitializer.getService
import com.sap.cloud.mobile.foundation.model.AppConfig
import com.sap.cloud.mobile.foundation.remotenotification.ForegroundPushNotificationReady
import com.sap.cloud.mobile.foundation.remotenotification.PushRemoteMessage
import org.slf4j.LoggerFactory


class WelcomeActivity : AppCompatActivity() {
    private val fManager = this.supportFragmentManager
    private lateinit var binding: ActivitySplashBinding
    private val logger = LoggerFactory.getLogger(WelcomeActivity::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableStrictMode()
        window.statusBarColor = ContextCompat.getColor(this, R.color.app_color_white)
        //Util.sendLogEvent(this,"start_welcome_screen", WelcomeActivity::class.java.getSimpleName())
        logger.info("start_welcome_screen", WelcomeActivity::class.java.getSimpleName())
        if (intent.getStringExtra("mobileservices.notificationId") != null) {
            var message = PushRemoteMessage()
            message.alert = intent.getStringExtra("alert")
            message.notificationID = intent.getStringExtra("mobileservices.notificationId")
            message.title = "Alert Message"
            if (message != null) {
                var pushService: PushService = PushService()
                val service = getService(pushService.getPushService()::class)
                service!!.storeNotificationMessage(false, message, object : ForegroundPushNotificationReady {
                    override fun onConditionReady(): Boolean {
                        return !AppLifecycleCallbackHandler.getInstance().activity!!.javaClass.name.contains("WelcomeActivity")
                    }
                })
                if ((application as SAPWizardApplication).isApplicationUnlocked) {
                    finish()
                    return
                } else if (AppLifecycleCallbackHandler.getInstance().activity != null) {
                    if (AppLifecycleCallbackHandler.getInstance().activity !is WelcomeActivity) {
                        finish()
                    }
                }
            }
        }
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startConfigurationLoader()
    }
    private fun enableStrictMode(){
        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
    }
    private fun startConfigurationLoader() {
        val callback: ConfigurationLoaderCallback = object : ConfigurationLoaderCallback() {
            override fun onCompletion(providerIdentifier: ProviderIdentifier?, success: Boolean) {
                if (success) {
                    startOnboarding(this@WelcomeActivity)
                } else {
                    DialogHelper(application, R.style.OnboardingDefaultTheme_Dialog_Alert)
                        .showOKOnlyDialog(
                            fManager,
                            resources.getString(R.string.config_loader_complete_error_description),
                            null, null, null
                        )
                }

            }

            override fun onError(configurationLoader: ConfigurationLoader, providerIdentifier: ProviderIdentifier, userInputs: UserInputs, configurationProviderError: ConfigurationProviderError) {
                DialogHelper(application, R.style.OnboardingDefaultTheme_Dialog_Alert)
                    .showOKOnlyDialog(
                        fManager, String.format(resources.getString(
                            R.string.config_loader_on_error_description),
                            providerIdentifier.toString(), configurationProviderError.errorMessage
                        ),
                        null, null, null
                    )
                configurationLoader.processRequestedInputs(UserInputs())
            }

            override fun onInputRequired(configurationLoader: ConfigurationLoader, userInputs: UserInputs) {
                configurationLoader.processRequestedInputs(UserInputs())
            }
        }
        val providers = arrayOf<ConfigurationProvider>(FileConfigurationProvider(this, "sap_mobile_services"))
        this.runOnUiThread {
            val loader = ConfigurationLoader(this, callback, providers)
            loader.loadConfiguration()
        }
    }

    private fun prepareAppConfig(): AppConfig? {
        return try {
            val configData = DefaultPersistenceMethod.getPersistedConfiguration(this)
            AppConfig.createAppConfigFromJsonString(configData.toString())
        } catch (ex: ConfigurationPersistenceException) {
            DialogHelper(this, R.style.OnboardingDefaultTheme_Dialog_Alert)
                .showOKOnlyDialog(
                    fManager,
                    resources.getString(R.string.config_data_build_json_description),
                    null, null, null
                )
            null
        } catch (ex: Exception) {
            DialogHelper(this, R.style.OnboardingDefaultTheme_Dialog_Alert)
                .showOKOnlyDialog(
                    fManager,
                    ex.localizedMessage ?: resources.getString(R.string.error_unknown_app_config),
                    null, null, null
                )
            null
        }
    }

    internal fun startOnboarding(activity: Activity) {
        //Util.uploadLog(lifecycleOwner = this)
        val appConfig = prepareAppConfig() ?: return
        val flowContext =
            FlowContextBuilder()
                .setApplication(appConfig)
                .setMultipleUserMode(false)
                .setFlowActionHandler(WizardFlowActionHandler(activity.application as SAPWizardApplication))
                .setFlowStateListener(WizardFlowStateListener(activity.application as SAPWizardApplication))
                .setFlowOptions(FlowOptions(
                    needConfirmWhenDeleteRegistration = false,
                    excludeEula = true,
                    excludeEulaWhenCreateAccount = true,
                    fullScreen = false
                ))
                .build()
        Flow.start(activity, flowContext) { _, resultCode, _ ->
            if (resultCode == Activity.RESULT_OK) {
                (application as SAPWizardApplication).isApplicationUnlocked = true
                Intent(application, MainBusinessActivity::class.java).also {
                    it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    application.startActivity(it)
                }

                if (!ApplicationStates.isNetworkAvailable) {
                    (application as SAPWizardApplication).applyLockWipePolicy()
                }
            } else {
                startOnboarding(activity)
            }
        }
    }
}
