package io.agora.secnceui.ainoise

import android.text.TextUtils
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.google.android.material.textview.MaterialTextView
import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.secnceui.R
import io.agora.secnceui.bean.AINSModeBean
import io.agora.secnceui.bean.AINSModeType
import io.agora.secnceui.bean.AINSSoundType
import io.agora.secnceui.bean.AINSSoundsBean
import io.agora.secnceui.databinding.ItemChatroomAgoraAinsBinding
import io.agora.secnceui.databinding.ItemChatroomAinsAuditionBinding
import io.agora.secnceui.databinding.ItemChatroomAnisContentBinding
import io.agora.secnceui.databinding.ItemChatroomAnisTitleBinding

class ChatroomAINSViewHolder(binding: ItemChatroomAgoraAinsBinding) :
    BaseRecyclerViewAdapter.BaseViewHolder<ItemChatroomAgoraAinsBinding, AINSModeBean>(binding) {
    override fun binding(data: AINSModeBean?, selectedIndex: Int) {
        data?.let {
            mBinding.mtNoiseSuppressionName.text = it.anisName
            when (it.anisType) {
                AINSModeType.High -> {
                    setViewHighlight(mBinding.mbChatroomHigh)
                    resetViewDefault(mBinding.mbChatroomMedium)
                    resetViewDefault(mBinding.mbChatroomOff)
                }
                AINSModeType.Medium -> {
                    setViewHighlight(mBinding.mbChatroomMedium)
                    resetViewDefault(mBinding.mbChatroomHigh)
                    resetViewDefault(mBinding.mbChatroomOff)
                }
                AINSModeType.Off -> {
                    setViewHighlight(mBinding.mbChatroomOff)
                    resetViewDefault(mBinding.mbChatroomHigh)
                    resetViewDefault(mBinding.mbChatroomMedium)
                }
            }
        }
    }

    private fun resetViewDefault(textView: MaterialTextView) {
        textView.setBackgroundResource(R.drawable.bg_rect_radius4_grey)
        textView.setTextColor(ResourcesCompat.getColor(context.resources, R.color.dark_grey_color_979CBB, null))
    }

    private fun setViewHighlight(textView: MaterialTextView) {
        textView.setBackgroundResource(R.drawable.bg_rect_stoke4_blue)
        textView.setTextColor(ResourcesCompat.getColor(context.resources, R.color.main_color_156EF3, null))
    }
}

class ChatroomAINSSoundsViewHolder(binding: ItemChatroomAinsAuditionBinding) :
    BaseRecyclerViewAdapter.BaseViewHolder<ItemChatroomAinsAuditionBinding, AINSSoundsBean>(binding) {
    override fun binding(data: AINSSoundsBean?, selectedIndex: Int) {
        data?.let {
            mBinding.mtChatroomAinsName.text = it.soundName
            if (TextUtils.isEmpty(it.soundSubName)) {
                mBinding.mtChatroomAinsSubName.text = ""
                mBinding.mtChatroomAinsSubName.isVisible = false
            } else {
                mBinding.mtChatroomAinsSubName.text = it.soundSubName
                mBinding.mtChatroomAinsSubName.isVisible = false
            }
            if (it.soundsType == AINSSoundType.AINS) {
                mBinding.ivChatroomAinsSounds.isInvisible = false
                setViewHighlight(mBinding.mbChatroomAins)
                resetViewDefault(mBinding.mbChatroomAinsNone)
            } else {
                mBinding.ivChatroomAinsSounds.isInvisible = true
                setViewHighlight(mBinding.mbChatroomAinsNone)
                resetViewDefault(mBinding.mbChatroomAins)
            }
        }
    }

    private fun resetViewDefault(textView: MaterialTextView) {
        textView.setBackgroundResource(R.drawable.bg_rect_radius4_grey)
        textView.setTextColor(ResourcesCompat.getColor(context.resources, R.color.dark_grey_color_979CBB, null))
    }

    private fun setViewHighlight(textView: MaterialTextView) {
        textView.setBackgroundResource(R.drawable.bg_rect_stoke4_blue)
        textView.setTextColor(ResourcesCompat.getColor(context.resources, R.color.main_color_156EF3, null))
    }
}

class ChatroomAINSTitleViewHolder(binding: ItemChatroomAnisTitleBinding) :
    BaseRecyclerViewAdapter.BaseViewHolder<ItemChatroomAnisTitleBinding, String>(binding) {
    override fun binding(data: String?, selectedIndex: Int) {
        data?.let {
            mBinding.mtChatroomAinsTitle.text = it
        }
    }
}

class ChatroomAINSContentViewHolder(binding: ItemChatroomAnisContentBinding) :
    BaseRecyclerViewAdapter.BaseViewHolder<ItemChatroomAnisContentBinding, String>(binding) {
    override fun binding(data: String?, selectedIndex: Int) {
        data?.let {
            mBinding.mtChatroomAinsContent.text = it
        }
    }
}