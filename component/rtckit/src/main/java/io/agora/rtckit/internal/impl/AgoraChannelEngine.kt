package io.agora.rtckit.internal.impl

import io.agora.rtc2.Constants
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngineEx
import io.agora.rtckit.open.config.RtcChannelConfig
import io.agora.rtckit.internal.base.RtcBaseChannelEngine

/**
 * @author create by zhangwei03
 *
 * agora 频道管理
 */
internal class AgoraChannelEngine : RtcBaseChannelEngine<RtcEngineEx>() {
    private var channelConfig: RtcChannelConfig? = null

    override fun joinChannel(channelConfig: RtcChannelConfig): Boolean {
        this.channelConfig = channelConfig
        return checkJoinChannel(channelConfig)
    }

    private fun checkJoinChannel(config: RtcChannelConfig): Boolean {
        val status =
            engine?.joinChannel(config.appToken, config.roomId, "", config.userId)

        if (status != IRtcEngineEventHandler.ErrorCode.ERR_OK) {
            listener?.onError(status ?: IRtcEngineEventHandler.ErrorCode.ERR_FAILED, "join channel error!")
            return false
        }
        engine?.apply {
            if (config.broadcaster) {
                setClientRole(Constants.CLIENT_ROLE_BROADCASTER)
            } else {
                setClientRole(Constants.CLIENT_ROLE_AUDIENCE)
            }
            enableLocalAudio(config.audioEnabled)
        }
        return true
    }

    override fun refreshChannel() {
        //todo
    }

    override fun leaveChannel() {
        engine?.leaveChannel()
    }

    override fun switchRole(broadcast: Boolean) {

    }
}