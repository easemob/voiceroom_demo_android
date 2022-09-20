package io.agora.rtckit.internal

import android.content.Context
import io.agora.buddy.tool.logE
import io.agora.rtc2.*
import io.agora.rtc2.internal.EncryptionConfig
import io.agora.rtckit.annotation.SoundSelection
import io.agora.rtckit.internal.base.RtcBaseAudioEngine
import io.agora.rtckit.internal.base.RtcBaseDeNoiseEngine
import io.agora.rtckit.internal.base.RtcBaseSoundEffectEngine
import io.agora.rtckit.internal.base.RtcBaseSpatialAudioEngine
import io.agora.rtckit.internal.impl.AgoraAudioEngine
import io.agora.rtckit.internal.impl.AgoraRtcDeNoiseEngine
import io.agora.rtckit.internal.impl.AgoraRtcSoundEffectEngine
import io.agora.rtckit.internal.impl.AgoraRtcSpatialAudioEngine
import io.agora.rtckit.open.IRtcValueCallback
import io.agora.rtckit.open.config.RtcChannelConfig
import io.agora.rtckit.open.config.RtcInitConfig
import io.agora.rtckit.open.status.RtcErrorStatus

/**
 * @author create by zhangwei03
 *
 *  agora 引擎，创建 client, 获取BaseEngine
 */
internal class AgoraRtcClientEx : RtcBaseClientEx<RtcEngineEx>() {

    private var eventHandler: AgoraRtcEventHandler? = null
    private var context: Context? = null
    private var initConfig: RtcInitConfig? = null

    override fun createClient(
        context: Context, config: RtcInitConfig, rtcClientListener: IRtcClientListener
    ): RtcEngineEx? {
        this.context = context
        this.initConfig = config
        this.rtcListener = rtcClientListener
        this.eventHandler = AgoraRtcEventHandler(rtcListener)
        if (!checkCreate()) return rtcEngine
        return rtcEngine
    }

    private fun checkCreate(): Boolean {
        if (rtcEngine != null || context == null) {
            return false
        }
        synchronized(AgoraRtcClientEx::class.java) {
            if (rtcEngine != null) return false
            //初始化RTC
            val config = RtcEngineConfig()
            config.mContext = context
            config.mAppId = initConfig?.appId
            config.mEventHandler = eventHandler
            config.mChannelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING
            config.mAudioScenario = Constants.AUDIO_SCENARIO_CHATROOM
            config.mLogConfig  = RtcEngineConfig.LogConfig().apply {
                level = Constants.LogLevel.getValue(Constants.LogLevel.LOG_LEVEL_ERROR)
            }

            try {
                rtcEngine = RtcEngineEx.create(config) as RtcEngineEx?
            } catch (e: Exception) {
                e.printStackTrace()
                "rtc engine init error:${e.message}".logE(TAG)
                return false
            }
            rtcEngine?.apply {
                // 设置场景
                setAudioProfile(Constants.AUDIO_PROFILE_SPEECH_STANDARD, Constants.AUDIO_SCENARIO_CHATROOM)
                //设置加密方式
                val encryptionConfig = EncryptionConfig()
                encryptionConfig.encryptionMode = EncryptionConfig.EncryptionMode.AES_128_XTS
                enableEncryption(true, encryptionConfig)
                enableWirelessAccelerate(true)
                enableAudio()
                enableDualStreamMode(false)
                enableAudioVolumeIndication(4000, 3, false)
            }
            return true
        }
    }

    override fun joinChannel(config: RtcChannelConfig, joinCallback: IRtcValueCallback<Boolean>) {
        checkJoinChannel(config, joinCallback)
    }

    private fun checkJoinChannel(config: RtcChannelConfig, joinCallback: IRtcValueCallback<Boolean>): Boolean {
        if (config.roomId.isEmpty() || config.userId < 0) {
            val errMsg = "join channel error roomId or rtcUid illegal!(roomId:${config.roomId},rtcUid:${config.userId})"
            errMsg.logE(TAG)
            rtcListener?.onError(RtcErrorStatus(IRtcEngineEventHandler.ErrorCode.ERR_FAILED, errMsg))
            return false
        }
        val options = ChannelMediaOptions()
        if (config.broadcaster) {
            rtcEngine?.setClientRole(Constants.CLIENT_ROLE_BROADCASTER)
        } else {
            rtcEngine?.setClientRole(Constants.CLIENT_ROLE_AUDIENCE)
        }
        when (config.soundType) {
            SoundSelection.SocialChat -> {
                rtcEngine?.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING)
                rtcEngine?.setAudioProfile(Constants.AUDIO_PROFILE_MUSIC_HIGH_QUALITY)
                rtcEngine?.setAudioScenario(Constants.AUDIO_SCENARIO_GAME_STREAMING)
            }
            else -> {
                // TODO: 最佳音效设置
                rtcEngine?.setChannelProfile(Constants.CHANNEL_PROFILE_COMMUNICATION)
            }
        }
        val rtcConnection = RtcConnection(config.roomId, config.userId)
        val status =
            rtcEngine?.joinChannelEx(config.appToken, rtcConnection, options, object : IRtcEngineEventHandler() {
                override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
                    joinCallback.onSuccess(true)
                    "onJoinChannelSuccess joinChannelEx channel:$channel,uid:$uid,elapsed:$elapsed".logE(TAG)
                }

                override fun onLeaveChannel(stats: RtcStats?) {
                    "onLeaveChannel joinChannelEx stats:$stats".logE(TAG)
                }
            })
        if (status != IRtcEngineEventHandler.ErrorCode.ERR_OK) {
            val errorMsg = "join channel error status not ERR_OK!"
            errorMsg.logE(TAG)
            joinCallback.onError(status ?: IRtcEngineEventHandler.ErrorCode.ERR_FAILED, errorMsg)
            return false
        }
        return true
    }

    override fun leaveChannel() {
        rtcEngine?.leaveChannel()
    }

    override fun createAudioEngine(): RtcBaseAudioEngine<RtcEngineEx> {
        return AgoraAudioEngine()
    }

    override fun createDeNoiseEngine(): RtcBaseDeNoiseEngine<RtcEngineEx>? {
        return AgoraRtcDeNoiseEngine()
    }

    override fun createSoundEffectEngine(): RtcBaseSoundEffectEngine<RtcEngineEx>? {
        return AgoraRtcSoundEffectEngine()
    }

    override fun createSpatialAudioEngine(): RtcBaseSpatialAudioEngine<RtcEngineEx>? {
        return AgoraRtcSpatialAudioEngine()
    }

    override fun destroy() {
        rtcEngine?.leaveChannel()
        eventHandler?.destroy()
        context = null
        super.destroy()
    }
}