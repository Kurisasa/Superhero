package com.kurisani.superhero.util

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import es.dmoral.toasty.Toasty

object AlertUtil {

    fun toast(context: Context?, message: String) {
        if (context == null) {
            return
        }
        Handler(Looper.getMainLooper()).post {
            Toasty.normal(
                context,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun toastSuccess(context: Context?, message: String) {
        if (context == null) {
            return
        }
        Handler(Looper.getMainLooper()).post {
            Toasty.success(
                context,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun toastError(context: Context?, message: String) {
        if (context == null) {
            return
        }
        Handler(Looper.getMainLooper()).post {
            Toasty.error(
                context,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun toastInfo(context: Context?, message: String) {
        if (context == null) {
            return
        }
        Handler(Looper.getMainLooper()).post {
            Toasty.info(
                context,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun toastWarning(context: Context?, message: String) {
        if (context == null) {
            return
        }
        Handler(Looper.getMainLooper()).post {
            Toasty.warning(
                context,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}