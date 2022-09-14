package io.agora.secnceui.widget.wheat

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import io.agora.buddy.tool.ViewTools
import io.agora.secnceui.R
import io.agora.secnceui.annotation.WheatSeatType
import io.agora.secnceui.annotation.WheatUserRole
import io.agora.secnceui.annotation.WheatUserStatus
import io.agora.secnceui.bean.SeatInfoBean
import io.agora.secnceui.databinding.ViewChatroom2dSeatBinding

class Chatroom2DSeatView : ConstraintLayout {

    private lateinit var mBinding: ViewChatroom2dSeatBinding

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context, attrs, defStyleAttr, defStyleRes
    ) {
        init(context)
    }

    private fun init(context: Context) {
        val root = View.inflate(context, R.layout.view_chatroom_2d_seat, this)
        mBinding = ViewChatroom2dSeatBinding.bind(root)
    }

    fun binding(seatInfo: SeatInfoBean) {
        when (seatInfo.wheatSeatType) {
            WheatSeatType.Idle,
            WheatSeatType.Mute -> {
                mBinding.ivSeatInfo.setBackgroundResource(R.drawable.bg_oval_white30)
                mBinding.ivSeatInnerIcon.isVisible = true
                if (seatInfo.wheatSeatType == WheatSeatType.Idle) {
                    mBinding.ivSeatInnerIcon.setImageResource(R.drawable.icon_seat_add)
                } else {
                    mBinding.ivSeatInnerIcon.setImageResource(R.drawable.icon_seat_mic)
                }
                mBinding.ivSeatMic.isVisible = false
                mBinding.mtSeatInfoName.apply {
                    text = seatInfo.index.toString()
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                }
            }
            WheatSeatType.Lock,
            WheatSeatType.LockMute -> {
                mBinding.ivSeatInfo.setBackgroundResource(R.drawable.bg_oval_white30)
                mBinding.ivSeatInnerIcon.isVisible = true
                mBinding.ivSeatInnerIcon.setImageResource(R.drawable.icon_seat_close)
                mBinding.ivSeatMic.isVisible = seatInfo.wheatSeatType == WheatSeatType.LockMute
                mBinding.mtSeatInfoName.apply {
                    text = seatInfo.index.toString()
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                }
            }
            WheatSeatType.Inactive -> {
                mBinding.mtSeatRotActive.isVisible = true
                mBinding.ivSeatBotFloat.isVisible = true
                setNormalWheatView(seatInfo)
            }
            else -> {
                setNormalWheatView(seatInfo)
            }
        }
    }

    private fun setNormalWheatView(seatInfo: SeatInfoBean) {
        mBinding.mtSeatInfoName.text = seatInfo.name
        mBinding.ivSeatInnerIcon.isVisible = false
        val resId = ViewTools.getDrawableId(context, seatInfo.avatar)
        when (seatInfo.userRole) {
            WheatUserRole.Robot -> {
                mBinding.ivSeatInfo.apply {
                    setBackgroundResource(R.drawable.bg_oval_white)
                    setImageResource(resId)
                }
                mBinding.mtSeatInfoName.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.icon_seat_robot_tag, 0, 0, 0
                )
                mBinding.mtSeatRotActive.isVisible = true
            }
            WheatUserRole.Owner -> {
                mBinding.ivSeatInfo.apply {
                    setBackgroundResource(R.drawable.bg_oval_white30)
                    setImageResource(resId)
                }
                mBinding.mtSeatInfoName.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.icon_seat_owner_tag, 0, 0, 0
                )
            }
            else -> {
                mBinding.ivSeatInfo.apply {
                    setBackgroundResource(R.drawable.bg_oval_white30)
                    setImageResource(resId)
                }
                mBinding.mtSeatInfoName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            }
        }
        mBinding.ivSeatMic.apply {
            when (seatInfo.userStatus) {
                WheatUserStatus.None -> {
                    isVisible = false
                }
                WheatUserStatus.Idle -> {
                    isVisible = true
                    setImageResource(R.drawable.icon_seat_on_mic0)
                }
                WheatUserStatus.Mute,
                WheatUserStatus.ForceMute -> {
                    isVisible = true
                    setImageResource(R.drawable.icon_seat_off_mic)
                }
                else -> {
                    // speaking
                    isVisible = true
                    setImageResource(R.drawable.icon_seat_on_mic1)
                }
            }
        }
    }
}