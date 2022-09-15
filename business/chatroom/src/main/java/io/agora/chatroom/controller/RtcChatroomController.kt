package io.agora.chatroom.controller

import android.app.Application
import io.agora.chatroom.BuildConfig
import io.agora.rtckit.open.IRtcKitListener
import io.agora.rtckit.open.RtcKitManager
import io.agora.rtckit.open.config.RtcChannelConfig
import io.agora.rtckit.open.config.RtcInitConfig
import io.agora.rtckit.open.event.RtcChannelEvent
import io.agora.rtckit.open.status.*

/**
 * @author create by zhangwei03
 */
class RtcChatroomController : IRtcKitListener {
    private lateinit var rtcManger: RtcKitManager

    /**机器人小蓝rtcManager*/
    private var blueRtcManger: RtcKitManager? = null

    /**机器人小红rtcManager*/
    private var redRtcManger: RtcKitManager? = null

    fun initMain(appContext: Application) {
        rtcManger = RtcKitManager.initRTC(appContext, RtcInitConfig(BuildConfig.agora_app_id), this)
    }

    /**激活机器人，只有房主可以激活*/
    fun activeBot(appContext: Application, active: Boolean) {
        blueRtcManger?.destroy()
        redRtcManger?.destroy()
        if (active) {
            blueRtcManger = RtcKitManager.initRTC(appContext, RtcInitConfig(BuildConfig.agora_app_id), this)
            redRtcManger = RtcKitManager.initRTC(appContext, RtcInitConfig(BuildConfig.agora_app_id), this)
        } else {
            blueRtcManger = null
            redRtcManger = null
        }
    }

    fun joinChannel(roomId: String, userId: Int) {
        rtcManger.operateChannel(RtcChannelEvent.JoinChannel(RtcChannelConfig(BuildConfig.agora_app_token, roomId, userId)))
    }

    override fun onNetworkStatus(netWorkStatus: RtcNetWorkStatus) {

    }

    override fun onAudioStatus(audioChangeStatus: RtcAudioChangeStatus) {
    }

    override fun onUserJoin(userId: String) {
    }

    override fun onChannelStatus(userStatus: RtcChannelStatus) {
    }

    override fun onError(rtcErrorStatus: RtcErrorStatus) {
    }

    override fun onAudioVolumeIndication(volumeIndicationStatus: RtcAudioVolumeIndicationStatus) {
    }
}