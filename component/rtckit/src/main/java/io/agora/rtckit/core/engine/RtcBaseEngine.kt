package io.agora.rtckit.core.engine

import io.agora.rtckit.core.listener.IRtcServiceListener

/**
 * base 引擎
 */
abstract class RtcBaseEngine<T> {

    protected var client: T? = null
    protected var listener: IRtcServiceListener? = null

    fun attach(client: T?, listener: IRtcServiceListener?) {
        this.client = client
        this.listener = listener
    }

    fun detach() {
        this.client = null
        this.listener = null
    }
}