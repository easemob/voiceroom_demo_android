package io.agora.buddy.tool

import android.app.Activity
import android.content.ContextWrapper
import android.content.res.Resources
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.Nullable
import androidx.core.content.res.ResourcesCompat

object ViewTools {
    fun getActivityFromView(view: View?): Activity? {
        if (null != view) {
            var context = view.context
            while (context is ContextWrapper) {
                if (context is Activity) {
                    return context
                }
                context = context.baseContext
            }
        }
        return null
    }

    @ColorInt
    fun getColor(resources: Resources, @ColorRes id: Int, theme: Resources.Theme? = null): Int {
        return ResourcesCompat.getColor(resources, id, theme)
    }
}