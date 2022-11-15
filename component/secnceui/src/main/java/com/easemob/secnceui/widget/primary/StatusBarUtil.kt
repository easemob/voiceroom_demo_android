package com.easemob.secnceui.widget.primary

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

/**
 * @author Lin
 * @date 2021/12/23
 * @function
 */

private const val STATUSBAR_DEFAULT_HEIGHT = 24 //状态栏的高度一般为24~25dp，小米8se的刘海为40dp
/**
 * 获取状态栏高度，返回值单位为px
 */
val Context.statusBarHeight: Int
    get() {
        val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resId > 0) { resources.getDimensionPixelSize(resId) } else {
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                STATUSBAR_DEFAULT_HEIGHT.toFloat(), Resources.getSystem().displayMetrics
            ).toInt()
        }
    }