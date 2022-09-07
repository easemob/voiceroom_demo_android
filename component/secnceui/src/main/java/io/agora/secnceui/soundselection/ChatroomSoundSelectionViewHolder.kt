package io.agora.secnceui.soundselection

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.buddy.tool.ViewTools
import io.agora.buddy.tool.dp
import io.agora.secnceui.R
import io.agora.secnceui.bean.SoundSelectionBean
import io.agora.secnceui.databinding.ItemChatroomSoundSelectionBinding
import io.agora.secnceui.databinding.ItemChatroomSoundSelectionFooterBinding

class ChatroomSoundSelectionViewHolder(binding: ItemChatroomSoundSelectionBinding) :
    BaseRecyclerViewAdapter.BaseViewHolder<ItemChatroomSoundSelectionBinding, SoundSelectionBean>(binding) {
    override fun binding(data: SoundSelectionBean?, selectedIndex: Int) {
        data?.let {
            if (it.isCurrentUsing) {
                mBinding.mtSoundSelectionCurrentName.isVisible = true
                mBinding.mtSoundSelectionCurrentName.text =
                    mBinding.root.context.getString(R.string.chatroom_current_sound_selection)
                mBinding.mcvSoundSelectionContent.strokeColor =
                    ViewTools.getColor(context.resources, R.color.main_color_009FFF)
                mBinding.ivSoundSelectionToggle.setImageResource(R.drawable.icon_chatroom_sound_listen)
                mBinding.ivSoundSelected.isVisible = true
                mBinding.llSoundSelectionTips.isVisible = false
            } else {
                mBinding.mtSoundSelectionCurrentName.text =
                    mBinding.root.context.getString(R.string.chatroom_other_sound_selection)
                // 第二个位置显示其他音效标题和提示
                mBinding.mtSoundSelectionCurrentName.isVisible = bindingAdapterPosition == 1
                mBinding.llSoundSelectionTips.isVisible = bindingAdapterPosition == 1
                mBinding.mcvSoundSelectionContent.strokeColor =
                    ViewTools.getColor(context.resources, R.color.divider_color_EFF4FF)
                mBinding.ivSoundSelectionToggle.setImageResource(R.drawable.icon_chatroom_sound_toggle)
                mBinding.ivSoundSelected.isVisible = false
            }
            mBinding.mtSoundSelectionName.text = it.soundName
            mBinding.mtSoundSelectionContent.text = it.soundIntroduce
            if (it.customer.isNullOrEmpty()) {
                mBinding.llSoundCustomerUsage.removeAllViews()
                mBinding.llSoundCustomerUsage.isInvisible = true
            } else {
                mBinding.llSoundCustomerUsage.removeAllViews()
                mBinding.llSoundCustomerUsage.isInvisible = false
                it.customer.forEach { customerBean ->
                    val customerImage = AppCompatImageView(context)
                    customerImage.setImageResource(customerBean.avatar)
                    val ivSize = 20.dp.toInt()
                    mBinding.llSoundCustomerUsage.addView(customerImage, LinearLayout.LayoutParams(ivSize, ivSize))
                    addCustomerMargin(customerImage)
                }
            }
            mBinding.root.setOnClickListener { view ->
                onItemClick(view)
            }
        }
    }

    private fun addCustomerMargin(view: View) {
        val layoutParams: LinearLayout.LayoutParams = view.layoutParams as LinearLayout.LayoutParams
        layoutParams.setMargins(0, 0, 10.dp.toInt(), 0)
        view.layoutParams = layoutParams
    }
}

class ChatroomSoundSelectionFooterViewHolder(binding: ItemChatroomSoundSelectionFooterBinding) :
    BaseRecyclerViewAdapter.BaseViewHolder<ItemChatroomSoundSelectionFooterBinding, String>(binding) {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun binding(data: String?, selectedIndex: Int) {
        mBinding.mtChatroomSoundSelectionMore.text =
            Html.fromHtml(context.getString(R.string.chatroom_sound_selection_more), Html.FROM_HTML_MODE_LEGACY)
    }
}