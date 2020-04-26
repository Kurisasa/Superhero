package com.kurisani.superhero

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.ComponentCallbacks2
import android.content.res.Configuration
import android.os.Bundle

/**
 * Class used to check the app's background and foreground flow.
 * Also can be used to check lifecycle flow of the app or the specific Activity.
 */
class SuperheroLifecycleCallback: Application.ActivityLifecycleCallbacks, ComponentCallbacks, ComponentCallbacks2 {

    private var activity: Activity? = null

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {
        this.activity = activity
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {}

    override fun onActivityDestroyed(activity: Activity) {
    }

    override fun onConfigurationChanged(newConfig: Configuration) {}

    override fun onLowMemory() {}

    /**
     * This method will only be called when the app goes in the background with memory level mentioned.
     *
     * @param level
     */
    override fun onTrimMemory(level: Int) {
    }
}