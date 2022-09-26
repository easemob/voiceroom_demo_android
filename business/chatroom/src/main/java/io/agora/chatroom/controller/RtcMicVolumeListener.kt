package io.agora.chatroom.controller

/**
 * @author create by zhangwei03
 *
 * 麦位音量监听
 */
abstract class RtcMicVolumeListener {

    /**
     * 模拟机器人音量显示
     */
    abstract fun onBotVolume(speaker: Int, finished: Boolean)
}