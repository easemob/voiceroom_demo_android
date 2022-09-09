package io.agora.rtckit.internal

import android.content.Context
import io.agora.buddy.tool.logE
import io.agora.rtc2.Constants
import io.agora.rtc2.RtcEngineConfig
import io.agora.rtc2.RtcEngineEx
import io.agora.rtc2.internal.EncryptionConfig
import io.agora.rtckit.open.config.RtcInitConfig
import io.agora.rtckit.internal.base.*
import io.agora.rtckit.internal.impl.*
import io.agora.rtckit.middle.IRtcMiddleServiceListener

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
        context: Context,
        initConfig: RtcInitConfig,
        middleServiceListener: IRtcMiddleServiceListener
    ): RtcEngineEx? {
        this.context = context
        this.initConfig = initConfig

        rtcListener =  RtcServiceHelper.createListener(middleServiceListener)
        eventHandler = AgoraRtcEventHandler(rtcListener)
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
            config.mAudioScenario = Constants.AUDIO_SCENARIO_CHORUS

            rtcEngine = RtcEngineEx.create(config) as RtcEngineEx?
            eventHandler?.init(context, rtcEngine)

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

    override fun createAudioEngine(): RtcBaseAudioEngine<RtcEngineEx> {
        return AgoraAudioEngine()
    }

    override fun createChannelEngine(): RtcBaseChannelEngine<RtcEngineEx>? {
        return AgoraChannelEngine()
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
        context = null
        super.destroy()
    }
}