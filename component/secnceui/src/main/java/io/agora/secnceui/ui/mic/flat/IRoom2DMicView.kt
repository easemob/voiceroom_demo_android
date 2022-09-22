package io.agora.secnceui.ui.mic.flat

import io.agora.secnceui.bean.MicInfoBean

/**
 * @author create by zhangwei03
 */
interface IRoom2DMicView {
    fun updateAdapter(micInfoList: List<MicInfoBean>, isBotActive: Boolean)

    fun activeBot(active: Boolean)
}