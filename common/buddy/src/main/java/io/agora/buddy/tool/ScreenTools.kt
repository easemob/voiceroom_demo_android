package io.agora.buddy.tool

import android.app.Activity
import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat

/**
 * @author create by zhangwei03
 */
object ScreenTools {

    /**
     * 屏幕高度 px
     */
    fun getScreenHeight(context: Activity): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = context.windowManager.currentWindowMetrics
            windowMetrics.bounds.height()
        } else {
            val displayMetrics: DisplayMetrics = context.resources.displayMetrics
            displayMetrics.heightPixels
        }
    }

    /**
     * 屏幕宽度 px
     */
    fun getScreenWidth(context: Activity): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = context.windowManager.currentWindowMetrics
            windowMetrics.bounds.width()
        } else {
            val displayMetrics: DisplayMetrics = context.resources.displayMetrics
            displayMetrics.widthPixels
        }
    }
}