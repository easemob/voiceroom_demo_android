package io.agora.secnceui.ui.soundselection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import io.agora.baseui.dialog.BaseSheetDialog
import io.agora.buddy.tool.dp
import io.agora.secnceui.bean.CustomerUsageBean
import io.agora.secnceui.databinding.DialogChatroomSocialChatBinding

class RoomSocialChatSheetDialog constructor(private val onClickSocialChatListener: OnClickSocialChatListener) :
    BaseSheetDialog<DialogChatroomSocialChatBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): DialogChatroomSocialChatBinding {
        return DialogChatroomSocialChatBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            setOnApplyWindowInsets(root)
            mtSocialChatMore.setOnClickListener {
                onClickSocialChatListener.onMoreSound()
            }
            mtBottomSheetTitle.text = titleText
            mtSocialChatContent.text = contentText
            customers?.forEach { customerBean ->
                val customerImage = AppCompatImageView(root.context)
                customerImage.setImageResource(customerBean.avatar)
                val ivSize = 20.dp.toInt()
                llSocialChatCustomers.addView(customerImage, LinearLayout.LayoutParams(ivSize, ivSize))
                addCustomerMargin(customerImage)
            }
        }
    }

    private fun addCustomerMargin(view: View) {
        val layoutParams: LinearLayout.LayoutParams = view.layoutParams as LinearLayout.LayoutParams
        layoutParams.setMargins(0, 0, 10.dp.toInt(), 0)
        view.layoutParams = layoutParams
    }

    private var titleText: String = ""
    private var contentText: String = ""
    private var customers: List<CustomerUsageBean>? = null

    fun titleText(titleText: String) = apply {
        this.titleText = titleText
    }

    fun contentText(contentText: String) = apply {
        this.contentText = contentText
    }

    fun customers(customers: List<CustomerUsageBean>) = apply {
        this.customers = customers
    }

    interface OnClickSocialChatListener {

        fun onMoreSound()
    }
}