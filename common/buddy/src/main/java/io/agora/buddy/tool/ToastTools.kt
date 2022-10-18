package io.agora.buddy.tool

import android.app.Activity
import android.content.Context
import android.widget.Toast

/**
 * @author create by zhangwei03
 */
object ToastTools {

    @JvmStatic
    fun show(context: Activity, msg: String, duration: Int = Toast.LENGTH_SHORT) {
        CoroutineUtil.execMain {
            // TODO:  add 系统window？
//            CustomToast.makeText(context, msg, duration).show()
            Toast.makeText(context, msg, duration).show()
        }
//        if (ThreadManager.getInstance().isMainThread) {
//            Toast.makeText(context, msg, duration).show()
//        } else {
//            ThreadManager.getInstance().runOnMainThread {
//                Toast.makeText(context, msg, duration).show()
//            }
//        }
    }
}