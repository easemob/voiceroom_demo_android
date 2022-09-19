package io.agora.rtckit.internal.impl

import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.Constants
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngineEx
import io.agora.rtckit.annotation.SoundSelection
import io.agora.rtckit.internal.base.RtcBaseChannelEngine
import io.agora.rtckit.open.config.RtcChannelConfig


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
        if (config.roomId.isEmpty() || config.userId < 0) {
            listener?.onError(
                IRtcEngineEventHandler.ErrorCode.ERR_FAILED,
                "join channel error roomId or rtcUid illegal!(roomId:${config.roomId} rtcUid:${config.userId})"
            )
            return false
        }
        listener?.onJoinChannelStart(config.roomId, config.userId)
        val options = ChannelMediaOptions()
        if (config.broadcaster) {
            engine?.setClientRole(Constants.CLIENT_ROLE_BROADCASTER)
        } else {
            engine?.setClientRole(Constants.CLIENT_ROLE_AUDIENCE)
        }
        when(config.soundType){
            SoundSelection.SocialChat ->{
                engine?.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING)
                engine?.setAudioProfile(Constants.AUDIO_PROFILE_MUSIC_HIGH_QUALITY)
                engine?.setAudioScenario(Constants.AUDIO_SCENARIO_GAME_STREAMING)
            }else ->{
            // TODO:
            engine?.setChannelProfile(Constants.CHANNEL_PROFILE_COMMUNICATION)
            }
        }
        val status = engine?.joinChannel(config.appToken, config.roomId, config.userId, options);

        if (status != IRtcEngineEventHandler.ErrorCode.ERR_OK) {
            listener?.onJoinChannelError(config.roomId,status ?: IRtcEngineEventHandler.ErrorCode.ERR_FAILED)
            return false
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