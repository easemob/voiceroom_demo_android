package io.agora.rtckit.core.listener

import io.agora.rtckit.core.RtcInitConfig

interface IRtcVirtualService {

    fun init(config: RtcInitConfig, listener: IRtcServiceListener)

    fun onAudioEvent()

    fun joinChannel()

    fun destroy()
}