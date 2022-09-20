package io.agora.buddy.tool

import android.content.Context
import android.widget.Toast

/**
 * @author create by zhangwei03
 */
object ToastTools {

    @JvmStatic
    fun show(context: Context, msg: String, duration: Int = Toast.LENGTH_SHORT) {
        if (ThreadManager.getInstance().isMainThread) {
            Toast.makeText(context, msg, duration).show()
        } else {
            ThreadManager.getInstance().runOnMainThread {
                Toast.makeText(context, msg, duration).show()
            }
        }
    }
}