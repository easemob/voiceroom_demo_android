package io.agora.secnceui.ui.mic

import io.agora.secnceui.bean.MicInfoBean

/**
 * @author create by zhangwei03
 */
interface IRoomMicView {
    fun updateAdapter(micInfoList: List<MicInfoBean>, isBotActive: Boolean)

    fun activeBot(active: Boolean)

    /**音量指示*/
    fun updateVolume(index: Int, volume: Int)

    /**机器人音量指示*/
    fun updateBotVolume(speakerType: Int, volume: Int)
}