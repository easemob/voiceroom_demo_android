package io.agora.secnceui.wheat.adapter

import androidx.core.view.isVisible
import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.buddy.tool.dp
import io.agora.secnceui.R
import io.agora.secnceui.bean.ChatroomWheatSeatType
import io.agora.secnceui.bean.ChatroomWheatUserRole
import io.agora.secnceui.bean.ChatroomWheatUserStatus
import io.agora.secnceui.bean.SeatInfoBean
import io.agora.secnceui.databinding.ItemChatroomCommonSeatBinding

class ChatroomWheatViewHolder(binding: ItemChatroomCommonSeatBinding) :
    BaseRecyclerViewAdapter.BaseViewHolder<ItemChatroomCommonSeatBinding, SeatInfoBean>(binding) {
    override fun binding(data: SeatInfoBean?, selectedIndex: Int) {
        data?.let {
            when (it.wheatSeatType) {
                ChatroomWheatSeatType.Idle -> {
                    mBinding.ivSeatInfo.apply {
                        setBackgroundResource(R.drawable.bg_oval_white30)
                        setImageResource(R.drawable.icon_seat_add)
                    }
                    mBinding.ivSeatMic.isVisible = false
                    mBinding.mtSeatInfoName.apply {
                        text = it.index.toString()
                        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                    }
                }
                ChatroomWheatSeatType.Mute -> {
                    mBinding.ivSeatInfo.apply {
                        setBackgroundResource(R.drawable.bg_oval_white30)
                        setImageResource(R.drawable.icon_seat_mic)
                    }
                    mBinding.ivSeatMic.isVisible = false
                    mBinding.mtSeatInfoName.apply {
                        text = it.index.toString()
                        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                    }
                }
                ChatroomWheatSeatType.Lock -> {
                    mBinding.ivSeatInfo.apply {
                        setBackgroundResource(R.drawable.bg_oval_white30)
                        setImageResource(R.drawable.icon_seat_close)
                    }
                    mBinding.ivSeatMic.isVisible = false
                    mBinding.mtSeatInfoName.apply {
                        text = it.index.toString()
                        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                    }
                }
                else -> {
                    // 有人在麦位
                    setNormalWheatView(it)
                }
            }
        }
    }

    private fun setNormalWheatView(seatInfo: SeatInfoBean) {
        mBinding.mtSeatInfoName.text = seatInfo.name

        mBinding.ivSeatInfo.apply {

            // todo avatar
            when (seatInfo.userRole) {
                ChatroomWheatUserRole.Robot -> {
                    setBackgroundResource(R.drawable.bg_oval_white)
                    setImageResource(seatInfo.rotImage)
                    val contentPadding = 10.dp.toInt()
                    setContentPadding(contentPadding,contentPadding,contentPadding,contentPadding)
                    mBinding.mtSeatInfoName.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.icon_seat_robot_tag, 0, 0, 0
                    )
                    if (seatInfo.isBotOpen){

                    }
                }
                ChatroomWheatUserRole.Owner -> {
                    setBackgroundResource(R.drawable.bg_oval_white30)
                    setImageResource(0)
                    mBinding.mtSeatInfoName.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.icon_seat_owner_tag, 0, 0, 0
                    )
                }
                else -> {
                    setBackgroundResource(R.drawable.bg_oval_white30)
                    setImageResource(0)
                    mBinding.mtSeatInfoName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                }
            }
            when (seatInfo.userStatus) {
                ChatroomWheatUserStatus.None -> {
                    mBinding.ivSeatMic.isVisible = false
                }
                ChatroomWheatUserStatus.Idle -> {
                    mBinding.ivSeatMic.isVisible = true
                    mBinding.ivSeatMic.setImageResource(R.drawable.icon_seat_on_mic0)
                }
                ChatroomWheatUserStatus.Mute -> {
                    mBinding.ivSeatMic.isVisible = true
                    mBinding.ivSeatMic.setImageResource(R.drawable.icon_seat_off_mic)
                }
                else -> {
                    // speaking
                    mBinding.ivSeatMic.isVisible = true
                    mBinding.ivSeatMic.setImageResource(R.drawable.icon_seat_on_mic1)
                }
            }
        }
    }
}