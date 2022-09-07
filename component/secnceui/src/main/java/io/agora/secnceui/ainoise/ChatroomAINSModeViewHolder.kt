package io.agora.secnceui.ainoise

import android.text.TextUtils
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.google.android.material.textview.MaterialTextView
import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.buddy.tool.ViewTools
import io.agora.secnceui.R
import io.agora.secnceui.annotation.AINSModeType
import io.agora.secnceui.annotation.AINSSoundType
import io.agora.secnceui.bean.AINSModeBean
import io.agora.secnceui.bean.AINSSoundsBean
import io.agora.secnceui.databinding.ItemChatroomAgoraAinsBinding
import io.agora.secnceui.databinding.ItemChatroomAinsAuditionBinding
import io.agora.secnceui.databinding.ItemChatroomAnisContentBinding
import io.agora.secnceui.databinding.ItemChatroomAnisTitleBinding

class ChatroomAINSModeViewHolder(binding: ItemChatroomAgoraAinsBinding) :
    BaseRecyclerViewAdapter.BaseViewHolder<ItemChatroomAgoraAinsBinding, AINSModeBean>(binding) {
    override fun binding(data: AINSModeBean?, selectedIndex: Int) {
        data?.let {
            mBinding.mtNoiseSuppressionName.text = it.anisName
            mBinding.mtChatroomHigh.setOnClickListener { view ->
                onItemChildClick(AINSModeType.High, view)
            }
            mBinding.mtChatroomMedium.setOnClickListener { view ->
                onItemChildClick(AINSModeType.Medium, view)
            }
            mBinding.mtChatroomOff.setOnClickListener { view ->
                onItemChildClick(AINSModeType.Off, view)
            }
//            mBinding.mtChatroomHigh.tag = AINSModeType.High
//            mBinding.mtChatroomMedium.tag = AINSModeType.Medium
//            mBinding.mtChatroomOff.tag = AINSModeType.Off
            when (it.anisMode) {
                AINSModeType.High -> {
                    setViewHighlight(mBinding.mtChatroomHigh)
                    resetViewDefault(mBinding.mtChatroomMedium)
                    resetViewDefault(mBinding.mtChatroomOff)
                }
                AINSModeType.Medium -> {
                    setViewHighlight(mBinding.mtChatroomMedium)
                    resetViewDefault(mBinding.mtChatroomHigh)
                    resetViewDefault(mBinding.mtChatroomOff)
                }
                AINSModeType.Off -> {
                    setViewHighlight(mBinding.mtChatroomOff)
                    resetViewDefault(mBinding.mtChatroomHigh)
                    resetViewDefault(mBinding.mtChatroomMedium)
                }
            }
        }
    }

    private fun resetViewDefault(textView: MaterialTextView) {
        textView.setBackgroundResource(R.drawable.bg_rect_radius4_grey)
        textView.setTextColor(ViewTools.getColor(context.resources, R.color.dark_grey_color_979CBB))
    }

    private fun setViewHighlight(textView: MaterialTextView) {
        textView.setBackgroundResource(R.drawable.bg_rect_stoke4_blue)
        textView.setTextColor(ViewTools.getColor(context.resources, R.color.main_color_156EF3))
    }
}

class ChatroomAINSSoundsViewHolder(binding: ItemChatroomAinsAuditionBinding) :
    BaseRecyclerViewAdapter.BaseViewHolder<ItemChatroomAinsAuditionBinding, AINSSoundsBean>(binding) {
    override fun binding(data: AINSSoundsBean?, selectedIndex: Int) {
        data?.let {
            mBinding.mtChatroomAinsName.text = it.soundName
            mBinding.ivChatroomAinsSounds.setOnClickListener { view ->
                onItemChildClick(AINSSoundType.Audition, view)
            }
            mBinding.mtChatroomAins.setOnClickListener { view ->
                onItemChildClick(AINSSoundType.AINS, view)
            }
            mBinding.mtChatroomAinsNone.setOnClickListener { view ->
                onItemChildClick(AINSSoundType.None, view)
            }
//            mBinding.ivChatroomAinsSounds.tag = AINSSoundType.Audition
//            mBinding.mtChatroomAins.tag = AINSSoundType.AINS
//            mBinding.mtChatroomAinsNone.tag = AINSSoundType.None
            if (TextUtils.isEmpty(it.soundSubName)) {
                mBinding.mtChatroomAinsSubName.text = ""
                mBinding.mtChatroomAinsSubName.isVisible = false
            } else {
                mBinding.mtChatroomAinsSubName.text = it.soundSubName
                mBinding.mtChatroomAinsSubName.isVisible = false
            }
            if (it.soundsType == AINSSoundType.AINS) {
                mBinding.ivChatroomAinsSounds.isInvisible = false
                setViewHighlight(mBinding.mtChatroomAins)
                resetViewDefault(mBinding.mtChatroomAinsNone)
            } else {
                mBinding.ivChatroomAinsSounds.isInvisible = true
                setViewHighlight(mBinding.mtChatroomAinsNone)
                resetViewDefault(mBinding.mtChatroomAins)
            }
        }
    }

    private fun resetViewDefault(textView: MaterialTextView) {
        textView.setBackgroundResource(R.drawable.bg_rect_radius4_grey)
        textView.setTextColor(ViewTools.getColor(context.resources, R.color.dark_grey_color_979CBB))
    }

    private fun setViewHighlight(textView: MaterialTextView) {
        textView.setBackgroundResource(R.drawable.bg_rect_stoke4_blue)
        textView.setTextColor(ViewTools.getColor(context.resources, R.color.main_color_156EF3))
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