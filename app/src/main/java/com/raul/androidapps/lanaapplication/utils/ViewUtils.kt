package com.raul.androidapps.lanaapplication.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.ImageView
import androidx.annotation.StringRes
import androidx.core.widget.ImageViewCompat
import com.raul.androidapps.lanaapplication.extensions.safe
import java.lang.ref.WeakReference


@Suppress("unused")
class ViewUtils {

    fun setTintInImageView(view: ImageView, color: Int) {
        ImageViewCompat.setImageTintList(view, ColorStateList.valueOf(color))
    }

    fun showAlertDialog(
        activity: WeakReference<Activity>, allowCancelWhenTouchingOutside: Boolean,
        title: String? = null, message: String? = null,
        positiveButton: String? = null, callbackPositive: ((m: Unit?) -> Any?)? = null,
        negativeButton: String? = null, callbackNegative: ((m: Unit?) -> Any?)? = null
    ) {
        showAlertDialogInternal(
            activity,
            allowCancelWhenTouchingOutside,
            title,
            message,
            positiveButton,
            callbackPositive,
            negativeButton,
            callbackNegative
        )
    }

    fun showAlertDialogWithResourceIds(
        activity: WeakReference<Activity>, allowCancelWhenTouchingOutside: Boolean,
        @StringRes title: Int, @StringRes message: Int,
        @StringRes positiveButton: Int? = null, callbackPositive: ((m: Unit?) -> Any?)? = null,
        @StringRes negativeButton: Int? = null, callbackNegative: ((m: Unit?) -> Any?)? = null
    ) {
        showAlertDialogInternal(
            activity,
            allowCancelWhenTouchingOutside,
            title,
            message,
            positiveButton,
            callbackPositive,
            negativeButton,
            callbackNegative
        )
    }

    private fun showAlertDialogInternal(
        activity: WeakReference<Activity>, allowCancelWhenTouchingOutside: Boolean,
        title: Any?, message: Any?,
        positiveButton: Any? = null, callbackPositive: ((m: Unit?) -> Any?)? = null,
        negativeButton: Any? = null, callbackNegative: ((m: Unit?) -> Any?)? = null
    ) {
        activity.safe {
            AlertDialog.Builder(this).apply {
                title?.let { setTitleInDialog(it, this) }
                message?.let { setMessageInDialog(it, this) }
                positiveButton?.let {
                    setPositiveButtonInDialog(it, this, callbackPositive)
                }
                negativeButton?.let {
                    setNegativeButtonInDialog(it, this, callbackNegative)
                }
                setCancelable(allowCancelWhenTouchingOutside)
                create()?.show()
            }
        }
    }

    private fun setTitleInDialog(title: Any, builder: AlertDialog.Builder) {
        when (title) {
            is String -> builder.setTitle(title)
            is Int -> builder.setTitle(title)
        }
    }

    private fun setMessageInDialog(message: Any, builder: AlertDialog.Builder) {
        when (message) {
            is String -> builder.setMessage(message)
            is Int -> builder.setMessage(message)
        }
    }

    private fun setPositiveButtonInDialog(
        text: Any,
        builder: AlertDialog.Builder,
        callbackPositive: ((m: Unit?) -> Any?)?
    ) {
        when (text) {
            is String -> {
                builder.setPositiveButton(text) { _, _ ->
                    callbackPositive?.invoke(null)
                }
            }
            is Int -> {
                builder.setPositiveButton(text) { _, _ ->
                    callbackPositive?.invoke(null)
                }
            }
        }
    }

    private fun setNegativeButtonInDialog(
        text: Any,
        builder: AlertDialog.Builder,
        callbackNegative: ((m: Unit?) -> Any?)?
    ) {
        when (text) {
            is String -> {
                builder.setNegativeButton(text) { _, _ ->
                    callbackNegative?.invoke(null)
                }
            }
            is Int -> {
                builder.setNegativeButton(text) { _, _ ->
                    callbackNegative?.invoke(null)
                }
            }
        }
    }

    fun needToSetThemeAsDark(backgroundColor: Int): Boolean {

        var red = Color.red(backgroundColor) / 255.0
        red = if (red < 0.03928) red / 12.92 else Math.pow((red + 0.055) / 1.055, 2.4)
        var green = Color.green(backgroundColor) / 255.0
        green = if (green < 0.03928) green / 12.92 else Math.pow((green + 0.055) / 1.055, 2.4)
        var blue = Color.blue(backgroundColor) / 255.0
        blue = if (blue < 0.03928) blue / 12.92 else Math.pow((blue + 0.055) / 1.055, 2.4)
        return (0.2126 * red + 0.7152 * green + 0.0722 * blue).toFloat() < 0.5
    }


}