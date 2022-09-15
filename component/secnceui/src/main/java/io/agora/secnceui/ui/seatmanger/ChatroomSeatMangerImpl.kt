package io.agora.secnceui.ui.seatmanger

import io.agora.secnceui.bean.SeatInfoBean

/**
 * @author create by zhangwei03
 */
class ChatroomSeatMangerImpl :IChatroomSeatManager{

    override fun seatMap(): Map<Int, SeatInfoBean> {
        return mutableMapOf()
    }

    override fun changeSeat(oldIndex: Int, newIndex: Int) {

    }

    override fun muteSeat(index: Int, muted: Boolean) {

    }

    override fun openSeat(index: Int) {

    }

    override fun closeSeat(index: Int) {

    }
}