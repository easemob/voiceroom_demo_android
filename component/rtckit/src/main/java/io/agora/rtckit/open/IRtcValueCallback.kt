package io.agora.rtckit.open

/**
 * @author create by zhangwei03
 */
interface IRtcValueCallback<T> {
    fun onSuccess(value: T) {}
    fun onError(code: Int, message: String?) {}
}