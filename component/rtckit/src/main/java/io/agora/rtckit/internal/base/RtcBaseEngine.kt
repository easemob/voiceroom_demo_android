package io.agora.rtckit.internal.base

import io.agora.rtckit.internal.IRtcClientListener

/**
 * @author create by zhangwei03
 *
 * base engine eg,audioEngine,audio
 */
internal abstract class RtcBaseEngine<T> {

    protected var engine: T? = null
    protected var listener: IRtcClientListener? = null

    fun attach(client: T?, listener: IRtcClientListener?) {
        this.engine = client
        this.listener = listener
    }

    fun detach() {
        this.engine = null
        this.listener = null
    }
}