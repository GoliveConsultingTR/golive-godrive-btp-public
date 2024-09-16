package com.golive.godrive.btppublic.app

import android.app.AlertDialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.golive.godrive.btppublic.R
import com.golive.godrive.btppublic.service.ApiError

import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nordan.dialog.Animation
import com.nordan.dialog.DialogType
import com.nordan.dialog.NordanAlertDialog


open class BaseActivity() : AppCompatActivity() {

    private var currentToast: Toast? = null
    private var handler = Handler()

    companion object {
        private var topActivity: BaseActivity? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handler = Handler()
        topActivity = this
    }

    fun yesNo(
        title: String?,
        message: String?,
        btnYesName: String?,
        btnNoName: String?,
        btnCancelName: String?,
        yes: Runnable?,
        no: Runnable?
    ): NordanAlertDialog.Builder {

        val dialogTitle = title ?: getString(R.string.log_level_info)
        val dialogMessage = message ?: ""
        val dialog = NordanAlertDialog.Builder(this)
        dialog.setTitle(dialogTitle)
        dialog.setAnimation(Animation.SLIDE)
        dialog.setMessage(dialogMessage)
        dialog.setDialogType(DialogType.INFORMATION)
        dialog.setPositiveBtnText(getString(R.string.ok))

        if (btnYesName != null) {
            dialog.setPositiveBtnText(getString(R.string.ok))
            dialog.onPositiveClicked {
                yes?.run()
            }
        }
        if (btnCancelName != null) {
            dialog.setNegativeBtnText(btnCancelName)
            dialog.onNegativeClicked {
                no?.run()
            }
        }
        dialog.build().show()
        return dialog
    }

    fun toast(resId: Int) {
        toast(getString(resId))
    }

    fun toast(message: String) {
        currentToast?.cancel()
        currentToast = Toast.makeText(this, message, Toast.LENGTH_LONG)
        currentToast?.show()
    }





    fun info(message: String?, ok: Runnable?) {
        info(null, message, ok)
    }

    fun info(title: String?, message: String?, ok: Runnable?) {
        val dialogTitle = title ?: getString(R.string.log_level_info)
        val dialogMessage = message ?: ""
        NordanAlertDialog.Builder(this)
            .setDialogType(DialogType.INFORMATION)
            .setAnimation(Animation.SLIDE)
            .isCancellable(false)
            .setTitle(dialogTitle)
            .setMessage(dialogMessage)
            .setPositiveBtnText(getString(R.string.ok))
            .onPositiveClicked {
                ok?.run()
            }
            .build().show()
    }

    fun input(message: String, defaultMessage: String, textResponse: TextResponse) {
        val editText = EditText(this)
        val builder = MaterialAlertDialogBuilder(this)
        builder.setMessage(message)
        builder.setView(editText)
        builder.setCancelable(false)
        builder.setPositiveButton(getString(R.string.ok)) { dialogInterface, _ ->
            dialogInterface.dismiss()
            textResponse.textEnteredAndClosed(editText.text.toString())
        }
        builder.setNegativeButton(getString(R.string.cancel)) { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        val dialog = builder.create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(resources.getColor(R.color.app_color_green))
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(resources.getColor(R.color.app_color_darkGray))
        }
        dialog.show()
    }


    fun error(message: String) {
        error(message, null)
    }

    fun error(error: ApiError) {
        error(error, null)
    }

    fun error(error: ApiError, ok: Runnable?) {
        error(error.text?.takeIf { it.isNotEmpty() } ?:getString(R.string.found_error), ok)
    }



    fun error(message: String, ok: Runnable?) {

        val dialog = NordanAlertDialog.Builder(this)
        dialog.setTitle(getString(R.string.log_level_error))
        dialog.setMessage(message)
        dialog.setDialogType(DialogType.ERROR)
        dialog.isCancellable(false)
        dialog.setPositiveBtnText(getString(R.string.ok))
        dialog.onPositiveClicked {
            ok?.run()
        }
        dialog.build().show()
    }

    interface TextResponse {
        fun textEnteredAndClosed(text: String?)
    }



    interface OptionSelectedCallback {
        fun onSelect(text: String?, selectedOption: Int)
    }

    fun success(title: String?, message: String?, ok: Runnable?) {
        val dialogTitle = title ?: getString(R.string.success)
        val dialogMessage = message ?: ""
        NordanAlertDialog.Builder(this)
            .setDialogType(DialogType.SUCCESS)
            .setAnimation(Animation.SLIDE)
            .isCancellable(false)
            .setTitle(dialogTitle)
            .setMessage(dialogMessage)
            .setPositiveBtnText(getString(R.string.ok))
            .onPositiveClicked {
                ok?.run()
            }
            .build().show()
    }


    fun warning(title: String?, message: String?, ok: Runnable?) {
        val dialogTitle = title ?: getString(R.string.warning)
        val dialogMessage = message ?: ""
        NordanAlertDialog.Builder(this)
            .setDialogType(DialogType.WARNING)
            .setAnimation(Animation.SLIDE)
            .isCancellable(false)
            .setTitle(dialogTitle)
            .setMessage(dialogMessage)
            .setPositiveBtnText(getString(R.string.ok))
            .onPositiveClicked {
                ok?.run()
            }
            .build().show()
    }




    private fun convertImplicitIntentToExplicitIntent(implicitIntent: Intent, context: Context): Intent? {
        val packageManager = context.packageManager
        val resolveInfoList = packageManager.queryIntentServices(implicitIntent, 0)
        if (resolveInfoList == null || resolveInfoList.size != 1) {
            return null
        }
        val resolveInfo = resolveInfoList[0]
        val componentName = ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name)
        val explicitIntent = Intent(implicitIntent)
        explicitIntent.component = componentName
        return explicitIntent
    }


}