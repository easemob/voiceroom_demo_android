package io.agora.secnceui.ui.mic

import io.agora.secnceui.bean.MicInfoBean

/**
 * @author create by zhangwei03
 */
interface IRoomMicView {
    fun updateAdapter(micInfoList: List<MicInfoBean>, isBotActive: Boolean)

    /**开关机器人*/
    fun activeBot(active: Boolean)

    /**音量指示*/
    fun updateVolume(index: Int, volume: Int)

    /**机器人音量指示*/
    fun updateBotVolume(speakerType: Int, volume: Int)

    fun receiverAttributeMap(newMicMap: Map<Int, MicInfoBean>)

    /**
     * 交换麦位
     */
    fun exchangeMic(from: Int, to: Int)

    /**是否在麦位上,-1 不在*/
    fun findMicByUid(uid: String): Int
}