package io.agora.secnceui.ui.ainoise

import android.text.TextUtils
import androidx.core.view.isVisible
import com.google.android.material.textview.MaterialTextView
import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.buddy.tool.ResourcesTools
import io.agora.config.ConfigConstants
import io.agora.secnceui.R
import io.agora.secnceui.bean.AINSModeBean
import io.agora.secnceui.bean.AINSSoundsBean
import io.agora.secnceui.databinding.ItemChatroomAgoraAinsBinding
import io.agora.secnceui.databinding.ItemChatroomAinsAuditionBinding
import io.agora.secnceui.databinding.ItemChatroomAnisContentBinding
import io.agora.secnceui.databinding.ItemChatroomAnisTitleBinding

class RoomAINSModeViewHolder(binding: ItemChatroomAgoraAinsBinding) :
    BaseRecyclerViewAdapter.BaseViewHolder<ItemChatroomAgoraAinsBinding, AINSModeBean>(binding) {
    override fun binding(data: AINSModeBean?, selectedIndex: Int) {
        data?.let {
            mBinding.mtNoiseSuppressionName.text = it.anisName
            mBinding.mtChatroomHigh.setOnClickListener { view ->
                onItemChildClick(ConfigConstants.AINSMode.AINS_High, view)
            }
            mBinding.mtChatroomMedium.setOnClickListener { view ->
                onItemChildClick(ConfigConstants.AINSMode.AINS_Medium, view)
            }
            mBinding.mtChatroomOff.setOnClickListener { view ->
                onItemChildClick(ConfigConstants.AINSMode.AINS_Off, view)
            }
//            mBinding.mtChatroomHigh.tag = AINSModeType.High
//            mBinding.mtChatroomMedium.tag = AINSModeType.Medium
//            mBinding.mtChatroomOff.tag = AINSModeType.Off
            when (it.anisMode) {
                ConfigConstants.AINSMode.AINS_High -> {
                    setViewHighlight(mBinding.mtChatroomHigh)
                    resetViewDefault(mBinding.mtChatroomMedium)
                    resetViewDefault(mBinding.mtChatroomOff)
                }
                ConfigConstants.AINSMode.AINS_Medium -> {
                    setViewHighlight(mBinding.mtChatroomMedium)
                    resetViewDefault(mBinding.mtChatroomHigh)
                    resetViewDefault(mBinding.mtChatroomOff)
                }
                ConfigConstants.AINSMode.AINS_Off -> {
                    setViewHighlight(mBinding.mtChatroomOff)
                    resetViewDefault(mBinding.mtChatroomHigh)
                    resetViewDefault(mBinding.mtChatroomMedium)
                }
            }
        }
    }

    private fun resetViewDefault(textView: MaterialTextView) {
        textView.setBackgroundResource(R.drawable.bg_rect_radius4_grey)
        textView.setTextColor(ResourcesTools.getColor(context.resources, R.color.dark_grey_color_979CBB))
    }

    private fun setViewHighlight(textView: MaterialTextView) {
        textView.setBackgroundResource(R.drawable.bg_rect_stoke4_blue)
        textView.setTextColor(ResourcesTools.getColor(context.resources, R.color.main_color_156EF3))
    }
}

class RoomAINSSoundsViewHolder(binding: ItemChatroomAinsAuditionBinding) :
    BaseRecyclerViewAdapter.BaseViewHolder<ItemChatroomAinsAuditionBinding, AINSSoundsBean>(binding) {
    override fun binding(data: AINSSoundsBean?, selectedIndex: Int) {
        data?.let {
            mBinding.mtChatroomAinsName.text = it.soundName
            mBinding.mtChatroomAins.setOnClickListener { view ->
                onItemChildClick(ConfigConstants.AINSMode.AINS_High, view)
            }
            mBinding.mtChatroomAinsNone.setOnClickListener { view ->
                onItemChildClick(ConfigConstants.AINSMode.AINS_Off, view)
            }
            if (TextUtils.isEmpty(it.soundSubName)) {
                mBinding.mtChatroomAinsSubName.text = ""
                mBinding.mtChatroomAinsSubName.isVisible = false
            } else {
                mBinding.mtChatroomAinsSubName.text = it.soundSubName
                mBinding.mtChatroomAinsSubName.isVisible = true
            }
            when (it.soundMode) {
                ConfigConstants.AINSMode.AINS_High -> {
                    setViewHighlight(mBinding.mtChatroomAins)
                    resetViewDefault(mBinding.mtChatroomAinsNone)
                }
                ConfigConstants.AINSMode.AINS_Off -> {
                    setViewHighlight(mBinding.mtChatroomAinsNone)
                    resetViewDefault(mBinding.mtChatroomAins)
                }
                else -> {
                    resetViewDefault(mBinding.mtChatroomAinsNone)
                    resetViewDefault(mBinding.mtChatroomAins)
                }
            }
        }
    }

    private fun resetViewDefault(textView: MaterialTextView) {
        textView.setBackgroundResource(R.drawable.bg_rect_radius4_grey)
        textView.setTextColor(ResourcesTools.getColor(context.resources, R.color.dark_grey_color_979CBB))
    }

    private fun setViewHighlight(textView: MaterialTextView) {
        textView.setBackgroundResource(R.drawable.bg_rect_stoke4_blue)
        textView.setTextColor(ResourcesTools.getColor(context.resources, R.color.main_color_156EF3))
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