package com.nineninenine.tksharedpref.utils

import android.app.Activity
import android.content.Intent
import android.os.Process

object AppRestartHelper {
    fun restartApp(activity: Activity) {
        val intent = Intent(activity, activity.javaClass)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        activity.startActivity(intent)
        activity.finish()
        Process.killProcess(Process.myPid())
    }
}
