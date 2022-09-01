package io.agora.secnceui.widget.wheat

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import io.agora.buddy.tool.dp
import io.agora.secnceui.R
import io.agora.secnceui.bean.ChatroomWheatSeatType
import io.agora.secnceui.bean.ChatroomWheatUserRole
import io.agora.secnceui.bean.ChatroomWheatUserStatus
import io.agora.secnceui.bean.SeatInfoBean
import io.agora.secnceui.databinding.ViewChatroom3dSeatBinding

class Chatroom3DSeatView : ConstraintLayout {

    private lateinit var mBinding: ViewChatroom3dSeatBinding

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context, attrs, defStyleAttr, defStyleRes
    ) {
        init(context)
    }

    private fun init(context: Context) {
        val root = View.inflate(context, R.layout.view_chatroom_3d_seat, this)
        mBinding = ViewChatroom3dSeatBinding.bind(root)
    }

    private fun binding(seatInfo: SeatInfoBean) {
        when (seatInfo.wheatSeatType) {
            ChatroomWheatSeatType.Idle -> {
                mBinding.ivSeatInfo.apply {
                    setBackgroundResource(R.drawable.bg_oval_white30)
                    setImageResource(R.drawable.icon_seat_add)
                }
                mBinding.ivSeatMic.isVisible = false
                mBinding.mtSeatInfoName.apply {
                    text = seatInfo.index.toString()
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
                    text = seatInfo.index.toString()
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
                    text = seatInfo.index.toString()
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                }
            }
            else -> {
                // 有人在麦位
                setNormalWheatView(seatInfo)
            }
        }
    }

    private fun setNormalWheatView(seatInfo: SeatInfoBean) {
        mBinding.mtSeatInfoName.text = seatInfo.name

        // todo avatar
        when (seatInfo.userRole) {
            ChatroomWheatUserRole.Robot -> {
                setBackgroundResource(R.drawable.bg_oval_white)
                mBinding.ivSeatInfo.setImageResource(seatInfo.rotImage)
                val contentPadding = 10.dp.toInt()
                mBinding.ivSeatInfo.setContentPadding(contentPadding, contentPadding, contentPadding, contentPadding)
                mBinding.mtSeatInfoName.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.icon_seat_robot_tag, 0, 0, 0
                )
            }
            ChatroomWheatUserRole.Owner -> {
                setBackgroundResource(R.drawable.bg_oval_white30)
                mBinding.ivSeatInfo.setImageResource(0)
                mBinding.mtSeatInfoName.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.icon_seat_owner_tag, 0, 0, 0
                )
            }
            else -> {
                setBackgroundResource(R.drawable.bg_oval_white30)
                mBinding.ivSeatInfo.setImageResource(0)
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