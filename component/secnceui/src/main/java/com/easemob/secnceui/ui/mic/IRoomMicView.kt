package com.easemob.secnceui.ui.mic

import com.easemob.secnceui.bean.MicInfoBean

/**
 * @author create by zhangwei03
 */
interface IRoomMicView {

    /**初始化麦位数据*/
    fun onInitMic(micInfoList: List<MicInfoBean>, isBotActive: Boolean)

    /**开关机器人*/
    fun activeBot(active: Boolean)

    /**音量指示*/
    fun updateVolume(index: Int, volume: Int)

    /**机器人音量指示*/
    fun updateBotVolume(speakerType: Int, volume: Int)

    fun receiverAttributeMap(newMicMap: Map<Int, com.easemob.secnceui.bean.MicInfoBean>)

    /**是否在麦位上,-1 不在*/
    fun findMicByUid(uid: String): Int

    fun myRtcUid(): Int
}