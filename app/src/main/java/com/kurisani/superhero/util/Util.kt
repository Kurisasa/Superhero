package com.kurisani.superhero.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.kurisani.superhero.SuperheroApplication

object Util {

    fun isLoggedIn(): Boolean {
        var loggedIn = false
        val authToken = "auth"
        if (authToken != null) {
            loggedIn = true
        }

        return loggedIn
    }

    /**
     * Check to see if context is valid.
     *
     * @param context The app context.
     * @return True if context is valid, otherwise, return false.
     */
    fun isContextInvalid(context: Context): Boolean {
        return (context as? Activity)?.let { isActivityInvalid(it) } ?: false
    }

    /**
     * Check to see if activity is valid, and is not finishing or destroyed.
     *
     * @param activity The activity to check.
     * @return True if activity is valid, otherwise, return false.
     */
    fun isActivityInvalid(activity: Activity?): Boolean {
        if (activity == null) {
            return true
        }
        return activity.isFinishing || activity.isDestroyed
    }

    /**
     * Check to see if fragment is valid, and its parent activity is not finishing or destroyed.
     *
     * @param fragment The fragment.
     * @return True if fragment context is valid, otherwise, return false.
     */
    fun isFragmentInvalid(fragment: Fragment): Boolean {
        if (fragment.activity != null) {
            val activity = fragment.activity
            return isActivityInvalid(activity)
        }
        return false
    }

    /**
     * Check to see if app is online or not.
     *
     * @return True if app is online, otherwise, return false.
     */
    fun isOnline(): Boolean {
        val connectivityManager = SuperheroApplication.instance!!.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return null != activeNetwork && activeNetwork.isConnected
    }

    fun capitalize(value: String): String {
        if (TextUtils.isEmpty(value)) {
            return ""
        }
        val first = value[0]
        return if (Character.isUpperCase(first)) {
            value
        } else {
            Character.toUpperCase(first) + value.substring(1)
        }
    }

    /**
     * Dismiss keyboard.
     */
    fun dismissKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (activity.currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!
                    .applicationWindowToken, 0
            )
        }
    }
}