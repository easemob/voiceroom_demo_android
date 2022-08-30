package io.agora.rtckit.core.impl

import android.content.Context
import io.agora.rtckit.core.RtcChannelConfig
import io.agora.rtckit.core.RtcInitConfig
import io.agora.rtckit.core.channel.RtcBaseChannel
import io.agora.rtckit.core.listener.IRtcServiceListener
import io.agora.buddy.tool.logE
import io.agora.rtckit.core.listener.IRtcVirtualService

class RtcVirtualServiceImpl : IRtcVirtualService {

    private var initConfig = RtcInitConfig()
    private var rtcConfig: RtcChannelConfig? = null

    private var context: Context? = null
    private var rtcServiceListener: IRtcServiceListener? = null

    private val rtcChannel: RtcBaseChannel<*> by lazy {
        AgoraRtcChannel().apply {
            if (rtcServiceListener != null) {
                init(RtcInitConfig(), rtcServiceListener!!)
            } else {
                "create rtc service error：IRtcServiceListener must be not null!".logE()
            }
        }
    }

    override fun init(config: RtcInitConfig, listener: IRtcServiceListener) {
        this.rtcServiceListener = listener
    }

    override fun onAudioEvent() {
        rtcChannel.getAudioEngine()?.apply {

        }
    }


    override fun joinChannel() {
        rtcChannel.joinChannel(RtcChannelConfig())
    }

    override fun destroy() {

    }
}