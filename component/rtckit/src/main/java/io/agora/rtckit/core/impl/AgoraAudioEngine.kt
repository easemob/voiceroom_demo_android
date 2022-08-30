package io.agora.rtckit.core.impl

import io.agora.rtckit.core.RtcChannelConfig
import io.agora.rtckit.core.engine.RtcBaseAudioEngine
import io.agora.rtc2.RtcEngineEx

class AgoraAudioEngine : RtcBaseAudioEngine<RtcEngineEx>() {
    override fun startAudio() {
    }

    override fun stopAudio() {
    }

    override fun muteLocalAudio(mute: Boolean) {
    }

    override fun muteRemoteAudio(mute: Boolean) {
    }

    override fun createChannel() {
    }

    override fun joinChannel(config: RtcChannelConfig): RtcEngineEx? {
        return  null
    }

    override fun leaveChannel() {
    }

    override fun destroy() {
    }
}