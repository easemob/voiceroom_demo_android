package io.agora.rtckit.core.impl

import io.agora.rtckit.core.RtcChannelConfig
import io.agora.rtckit.core.RtcInitConfig
import io.agora.rtckit.core.channel.RtcBaseChannel
import io.agora.rtckit.core.engine.RtcBaseAudioEngine
import io.agora.rtckit.core.listener.IRtcServiceListener
import io.agora.rtc2.RtcEngineEx

class AgoraRtcChannel : RtcBaseChannel<RtcEngineEx>() {

    protected var rtcEngine: AgoraAudioEngine? = null

    override fun init(config: RtcInitConfig, listener: IRtcServiceListener) {

        this.listener = listener
    }

    override fun joinChannel(config: RtcChannelConfig) {
        getAudioEngine()?.let {
            rtcEngine = it as AgoraAudioEngine
            rtcClient = it.joinChannel(config)
        }
    }

    override fun initAudioEngine(): RtcBaseAudioEngine<RtcEngineEx> {
        return AgoraAudioEngine()
    }

    override fun destroy() {
        rtcEngine?.destroy()
        super.destroy()
        rtcEngine = null
    }
}