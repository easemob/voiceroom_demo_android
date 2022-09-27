package io.agora.secnceui.widget.mic

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import io.agora.buddy.tool.ResourcesTools
import io.agora.config.ConfigConstants
import io.agora.secnceui.R
import io.agora.secnceui.annotation.MicStatus
import io.agora.secnceui.bean.MicInfoBean
import io.agora.secnceui.databinding.ViewChatroom3dMicBinding

/**
 * @author create by zhangwei03
 *
 * 3d麦位
 */
class Room3DMicView : ConstraintLayout {

    private lateinit var mBinding: ViewChatroom3dMicBinding

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context, attrs, defStyleAttr, defStyleRes
    ) {
        init(context)
    }

    private var arrowAnim: AnimationDrawable? = null

    private fun init(context: Context) {
        val root = View.inflate(context, R.layout.view_chatroom_3d_mic, this)
        mBinding = ViewChatroom3dMicBinding.bind(root)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        arrowAnim = mBinding.ivMicArrowAnim.background as AnimationDrawable?
        mBinding.ivMicArrowAnim.rotation = 360f
        arrowAnim?.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        arrowAnim?.stop()
        arrowAnim = null
    }

    fun binding(micInfo: MicInfoBean) {
        mBinding.apply {
            if (micInfo.userInfo == null) { // 没人
                ivMicInnerIcon.isVisible = true
                mtMicUsername.text = micInfo.index.toString()
                mtMicUsername.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                when (micInfo.micStatus) {
                    MicStatus.ForceMute -> {
                        ivMicTag.isVisible = false
                        ivMicInnerIcon.setImageResource(R.drawable.icon_chatroom_mic_mute)
                    }
                    MicStatus.CloseForceMute -> {
                        ivMicInnerIcon.setImageResource(R.drawable.icon_chatroom_mic_close)
                        ivMicTag.isVisible = true
                        ivMicTag.setImageResource(R.drawable.icon_chatroom_mic_mute_tag)
                    }
                    else -> {
                        ivMicTag.isVisible = false
                        ivMicInnerIcon.setImageResource(R.drawable.icon_chatroom_mic_add)
                    }
                }
            } else { // 有人
                ivMicInnerIcon.isVisible = false
                ivMicInfo.setImageResource(
                    ResourcesTools.getDrawableId(ivMicInfo.context, micInfo.userInfo?.userAvatar ?: "")
                )
                mtMicUsername.text = micInfo.userInfo?.username ?: ""
                if (micInfo.ownerTag) {
                    mtMicUsername.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.icon_chatroom_mic_owner_tag, 0, 0, 0
                    )
                } else {
                    mtMicUsername.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                }
                when (micInfo.micStatus) {
                    MicStatus.Mute,
                    MicStatus.ForceMute -> {
                        ivMicTag.isVisible = true
                        ivMicTag.setImageResource(R.drawable.icon_chatroom_mic_mute_tag)
                    }
                    else -> {
                        ivMicTag.isVisible = false
                    }
                }
            }
            // 用户音量
            when (micInfo.audioVolumeType) {
                ConfigConstants.VolumeType.Volume_None -> ivMicTag.isVisible = false
                ConfigConstants.VolumeType.Volume_Low -> {
                    ivMicTag.isVisible = true
                    ivMicTag.setImageResource(R.drawable.icon_chatroom_mic_open1)
                }
                ConfigConstants.VolumeType.Volume_Medium -> {
                    ivMicTag.isVisible = true
                    ivMicTag.setImageResource(R.drawable.icon_chatroom_mic_open2)
                }
                ConfigConstants.VolumeType.Volume_High -> {
                    ivMicTag.isVisible = true
                    ivMicTag.setImageResource(R.drawable.icon_chatroom_mic_open3)
                }
                ConfigConstants.VolumeType.Volume_Max -> {
                    ivMicTag.isVisible = true
                    ivMicTag.setImageResource(R.drawable.icon_chatroom_mic_open4)
                }
                else -> {

                }
            }

        }
    }

    fun changeAngle(angle: Float) {
        val layoutParams: LayoutParams = mBinding.ivMicArrowAnim.layoutParams as LayoutParams
        layoutParams.circleAngle = angle
        mBinding.ivMicArrowAnim.rotation = angle
        mBinding.ivMicArrowAnim.layoutParams = layoutParams
    }
}