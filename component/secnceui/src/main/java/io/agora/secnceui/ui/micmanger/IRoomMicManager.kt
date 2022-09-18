package io.agora.secnceui.ui.micmanger

import io.agora.secnceui.bean.MicInfoBean

/**
 * @author create by zhangwei03
 *
 * 麦位管理
 */
interface IRoomMicManager {

    fun micMap(): Map<Int, MicInfoBean>

    /**交换麦位不需要下麦*/
    fun changeMic(oldIndex: Int, newIndex: Int)

    /**禁麦根据麦位来判断*/
    fun muteMic(index: Int, muted: Boolean)

    /**打开麦位*/
    fun openMic(index: Int)

    /**关闭麦位*/
    fun closeMic(index: Int)
}