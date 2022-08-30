package io.agora.rtckit.core.pipe

import android.view.SurfaceView
import io.agora.rtckit.core.RtcInitConfig
import io.agora.rtckit.core.impl.RtcVirtualServiceImpl
import io.agora.rtckit.core.listener.IRtcServiceListener
import io.agora.rtckit.core.listener.IRtcVirtualService

/**
 * rtc&&im 事件分发管理分发
 */
class ChatroomRtcFacade {

    private var virtualService: IRtcVirtualService? = null
    lateinit var initConfig: RtcInitConfig
    var rtcPipeListener: IRtcPipeListener? = null

    companion object {

        fun init(initConfig: RtcInitConfig, rtcPipeListener: IRtcPipeListener): ChatroomRtcFacade {
            val chatroomRtcPipe = ChatroomRtcFacade()
            chatroomRtcPipe.rtcPipeListener = rtcPipeListener
            chatroomRtcPipe.initConfig = initConfig
            chatroomRtcPipe.createService()
            return chatroomRtcPipe
        }
    }

    private fun createService() {
        virtualService = RtcVirtualServiceImpl()
        virtualService?.init(initConfig, object : IRtcServiceListener {
            override fun onStatus() {
             rtcPipeListener?.onStatus()
            }

            override fun onAudioStatus() {
                rtcPipeListener?.onAudioStatus()
            }


            override fun onUserJoinChannel(channel: String, uid: String, view: SurfaceView?) {
                rtcPipeListener?.onUserJoinChannel(channel,uid,view)
            }

            override fun onError() {
                rtcPipeListener?.onError()
            }

        })
    }


    fun joinChannel() {
        virtualService?.joinChannel()
    }

    fun operationAudio() {
        virtualService?.onAudioEvent()
    }

    fun destroy() {
        rtcPipeListener = null
        virtualService?.destroy()
        virtualService = null
    }
}