package com.easemob.buddy.tool

import android.app.Activity
import android.widget.Toast
import com.easemob.buddy.tool.internal.InternalToast

/**
 * @author create by zhangwei03
 */
object ToastTools {

    @JvmStatic
    fun show(context: Activity, msg: String, duration: Int = Toast.LENGTH_SHORT) {
        show(context, msg, InternalToast.COMMON, duration)
    }

    @JvmStatic
    fun showTips(context: Activity, msg: String, duration: Int = Toast.LENGTH_SHORT) {
        show(context, msg, InternalToast.TIPS, duration)
    }

    @JvmStatic
    fun showError(context: Activity, msg: String, duration: Int = Toast.LENGTH_SHORT) {
        show(context, msg, InternalToast.ERROR, duration)
    }

    @JvmStatic
    private fun show(
        context: Activity,
        msg: String,
        toastType: Int = InternalToast.COMMON,
        duration: Int = Toast.LENGTH_SHORT
    ) {
        CoroutineUtil.execMain {
            InternalToast.init(context.application)
            InternalToast.show(msg, toastType, duration)
        }
    }
}