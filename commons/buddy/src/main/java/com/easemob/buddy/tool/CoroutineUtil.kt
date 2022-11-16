package com.easemob.buddy.tool

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author create by zhangwei03
 */
/**
 * 协程调用
 */
object CoroutineUtil {

    fun execMain(code: suspend () -> Unit) = CoroutineScope(Dispatchers.Main).launch {
        code.invoke()
    }

    fun execIO(code: suspend () -> Unit) = CoroutineScope(Dispatchers.IO).launch {
        code.invoke()
    }
}