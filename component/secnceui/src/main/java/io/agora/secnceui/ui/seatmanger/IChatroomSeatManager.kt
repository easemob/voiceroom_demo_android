package io.agora.secnceui.ui.seatmanger

import io.agora.secnceui.bean.SeatInfoBean

/**
 * @author create by zhangwei03
 *
 * 麦位管理
 */
interface IChatroomSeatManager {

    fun seatMap(): Map<Int, SeatInfoBean>

    /**换做不需要下麦*/
    fun changeSeat(oldIndex: Int, newIndex: Int)

    /**禁麦根据座位来判断*/
    fun muteSeat(index: Int, muted: Boolean)

    /**打开座位*/
    fun openSeat(index: Int)

    /**关闭座位*/
    fun closeSeat(index: Int)
}