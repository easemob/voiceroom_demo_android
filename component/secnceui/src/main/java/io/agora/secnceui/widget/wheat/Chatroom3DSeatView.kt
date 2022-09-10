package io.agora.secnceui.widget.wheat

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.opensource.svgaplayer.SVGADrawable
import com.opensource.svgaplayer.SVGAParser
import com.opensource.svgaplayer.SVGAVideoEntity
import io.agora.buddy.tool.logD
import io.agora.secnceui.R
import io.agora.secnceui.annotation.WheatSeatType
import io.agora.secnceui.annotation.WheatUserRole
import io.agora.secnceui.annotation.WheatUserStatus
import io.agora.secnceui.bean.SeatInfoBean
import io.agora.secnceui.databinding.ViewChatroom3dSeatBinding
import java.io.File

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

    private val svgaParser by lazy {
        SVGAParser.shareParser()
    }

    private fun init(context: Context) {
        val root = View.inflate(context, R.layout.view_chatroom_3d_seat, this)
        mBinding = ViewChatroom3dSeatBinding.bind(root)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        svgaParser.decodeFromAssets("chatroom_3d_arrow_one.svga", object : SVGAParser.ParseCompletion {
            override fun onComplete(videoItem: SVGAVideoEntity) {
                "ParseCompletion onComplete".logD()
                val drawable = SVGADrawable(videoItem)
                mBinding.svgaSeatArrow.setImageDrawable(drawable)
                mBinding.svgaSeatArrow.startAnimation()
            }

            override fun onError() {
                "ParseCompletion onError".logD()
            }

        }, object : SVGAParser.PlayCallback {
            override fun onPlay(file: List<File>) {
                "PlayCallback onPlay".logD()
            }
        })
        mBinding.svgaSeatArrow.rotation = 360f
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
            else -> {
                setNormalWheatView(seatInfo)
            }
        }
    }

    private fun setNormalWheatView(seatInfo: SeatInfoBean) {
        mBinding.mtSeatInfoName.text = seatInfo.name
        mBinding.ivSeatInnerIcon.isVisible = false
        when (seatInfo.userRole) {
            WheatUserRole.Owner -> {
                mBinding.ivSeatInfo.apply {
                    setBackgroundResource(R.drawable.bg_oval_white30)
                    setImageResource(seatInfo.avatar.avatar)
                }
                mBinding.mtSeatInfoName.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.icon_seat_owner_tag, 0, 0, 0
                )
            }
            else -> {
                mBinding.ivSeatInfo.apply {
                    setBackgroundResource(R.drawable.bg_oval_white30)
                    setImageResource(seatInfo.avatar.avatar)
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


    fun changeAngle(angle: Float) {
        val layoutParams: ConstraintLayout.LayoutParams = mBinding.svgaSeatArrow.layoutParams as LayoutParams
        layoutParams.circleAngle = angle
        mBinding.svgaSeatArrow.rotation = angle

        mBinding.svgaSeatArrow.layoutParams = layoutParams
    }

}