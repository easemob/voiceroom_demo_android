package io.agora.chatroom.controller

import android.content.Context
import io.agora.buddy.tool.logE
import io.agora.chatroom.BuildConfig
import io.agora.rtckit.open.IRtcKitListener
import io.agora.rtckit.open.IRtcValueCallback
import io.agora.rtckit.open.RtcKitListenerImpl
import io.agora.rtckit.open.RtcKitManager
import io.agora.rtckit.open.config.RtcChannelConfig
import io.agora.rtckit.open.config.RtcInitConfig
import io.agora.rtckit.open.event.RtcDeNoiseEvent
import io.agora.rtckit.open.event.RtcSoundEffectEvent
import io.agora.rtckit.open.status.*
import io.agora.secnceui.annotation.AINSModeType
import io.agora.secnceui.annotation.AINSUser
import io.agora.secnceui.bean.AINSModeBean
import io.agora.secnceui.bean.AINSSoundsBean
import tools.ValueCallBack
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author create by zhangwei03
 */
class RtcRoomController : IRtcKitListener {

    companion object {

        private const val TAG = "RtcRoomController"

        @JvmStatic
        fun get() = InstanceHelper.sSingle

        private const val joinStart = 1
        private const val joinSuccess = 2
        private const val joinError = 3
    }

    object InstanceHelper {
        val sSingle = RtcRoomController()
    }

    private val rtcChannelConfig by lazy {
        RtcChannelConfig(BuildConfig.agora_app_token)
    }

    private var rtcManger: RtcKitManager? = null

    /**机器人小蓝rtcManager*/
    private var blueManger: RtcKitManager? = null

    /**机器人小红rtcManager*/
    private var redManger: RtcKitManager? = null

    /**机器人是否激活*/
    private var botActivated = AtomicBoolean(false)

    /**降噪*/
    private var anisMode = AtomicInteger(AINSModeType.Medium)

    fun botActivated(): Boolean = botActivated.get()

    fun anisMode(): Int = anisMode.get()

    fun setAnisMode(@AINSModeType anisMode: Int) {
        this.anisMode.set(anisMode)
    }

    /**加入rtc频道*/
    fun joinChannel(
        context: Context,
        roomId: String,
        userId: Int,
        broadcaster: Boolean = false,
        joinCallback: ValueCallBack<Boolean>
    ) {
        rtcManger = RtcKitManager.initRTC(context, RtcInitConfig(BuildConfig.agora_app_id), this)
        rtcChannelConfig.roomId = roomId
        rtcChannelConfig.userId = userId
        rtcChannelConfig.broadcaster = broadcaster
        rtcManger?.joinChannel(rtcChannelConfig, object : IRtcValueCallback<Boolean> {
            override fun onSuccess(value: Boolean) {
                // 默认开启降噪
                val event = when (anisMode.get()) {
                    AINSModeType.Off -> RtcDeNoiseEvent.CloseEvent()
                    AINSModeType.High -> RtcDeNoiseEvent.HeightEvent()
                    else -> RtcDeNoiseEvent.MediumEvent()
                }
                rtcManger?.operateDeNoise(event)
                joinCallback.onSuccess(value)
            }
        })
    }

    /**激活机器人，只有房主可以激活*/
    fun activeBot(context: Context, active: Boolean, joinChannelCallback: ValueCallBack<Boolean>) {
        blueManger?.destroy()
        redManger?.destroy()
        if (active) {
            val blueJoinStatus = AtomicInteger(joinStart)
            val redJoinStatus = AtomicInteger(joinStart)
            blueManger = RtcKitManager.initRTC(context, RtcInitConfig(BuildConfig.agora_app_id), RtcKitListenerImpl())
            redManger = RtcKitManager.initRTC(context, RtcInitConfig(BuildConfig.agora_app_id), RtcKitListenerImpl())

            val channelConfigBlue = rtcChannelConfig.copy().apply {
                userId = 101
            }
            blueManger?.joinChannel(channelConfigBlue, object : IRtcValueCallback<Boolean> {
                override fun onSuccess(value: Boolean) {
                    if (value) {
                        blueJoinStatus.set(joinSuccess)
                    } else {
                        blueJoinStatus.set(joinError)
                    }
                    checkJoinResult(blueJoinStatus, redJoinStatus, joinChannelCallback)
                }
            })
            val channelConfigRed = rtcChannelConfig.copy().apply {
                userId = 102
            }
            redManger?.joinChannel(channelConfigRed, object : IRtcValueCallback<Boolean> {
                override fun onSuccess(value: Boolean) {
                    if (value) {
                        redJoinStatus.set(joinSuccess)
                    } else {
                        redJoinStatus.set(joinError)
                    }
                    checkJoinResult(blueJoinStatus, redJoinStatus, joinChannelCallback)
                }
            })
        } else {
            blueManger?.leaveChannel()
            redManger?.leaveChannel()
        }
    }

    private fun checkJoinResult(
        blueJoinStatus: AtomicInteger, redJoinStatus: AtomicInteger, joinChannelCallback: ValueCallBack<Boolean>
    ) {
        if (blueJoinStatus.get() == joinSuccess && redJoinStatus.get() == joinSuccess) {
            joinChannelCallback.onSuccess(true)
        } else if (blueJoinStatus.get() == joinError && redJoinStatus.get() == joinError) {
            joinChannelCallback.onSuccess(false)
        } else {
            "blue bot status:${blueJoinStatus.get()},red bot status:${redJoinStatus.get()}".logE(TAG)
        }
    }

    fun deNoise(anisModeBean: AINSModeBean) {
        val event = when (anisModeBean.anisMode) {
            AINSModeType.Off -> RtcDeNoiseEvent.CloseEvent()
            AINSModeType.High -> RtcDeNoiseEvent.HeightEvent()
            else -> RtcDeNoiseEvent.MediumEvent()
        }
        val manager: RtcKitManager? = when (anisModeBean.anisUser) {
            AINSUser.BlueBot -> blueManger
            AINSUser.RedBot -> redManger
            else -> rtcManger
        }
        manager?.operateDeNoise(event)
    }

    fun playEffect(anisSoundsBean: AINSSoundsBean) {
        // test
        blueManger?.operateSoundEffect(
            RtcSoundEffectEvent.PlayEffectEvent(
                1,
                "https://webdemo.agora.io/ding.mp3",
                1,
                true
            )
        )
    }

    fun destroy() {
        rtcManger?.leaveChannel()
        rtcManger?.destroy()
    }

    override fun onConnectionStateChanged(state: Int, reason: Int) {

    }

    override fun onNetworkStatus(netWorkStatus: RtcNetWorkStatus) {

    }

    override fun onAudioStatus(audioChangeStatus: RtcAudioChangeStatus) {
    }

    override fun onUserJoin(userId: Int) {
    }


    override fun onLeaveChannel(userId: Int) {

    }

    override fun onError(rtcErrorStatus: RtcErrorStatus) {
    }

    override fun onAudioVolumeIndication(volumeIndicationStatus: RtcAudioVolumeIndicationStatus) {
    }
}