package ru.hse.smart_control.utility

import android.app.Activity
import android.content.res.Configuration
import ru.hse.smart_control.R

object ThemeUtils {
    fun onActivityCreateSetTheme(activity: Activity) {
        when (activity.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                activity.setTheme(R.style.AppTheme_Light)
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                activity.setTheme(R.style.AppTheme_Dark)
            }
        }
    }
}