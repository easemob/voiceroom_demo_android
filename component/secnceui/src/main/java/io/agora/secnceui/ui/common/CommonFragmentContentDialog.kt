package io.agora.secnceui.ui.common

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import io.agora.baseui.dialog.BaseFragmentDialog
import io.agora.buddy.tool.dp
import io.agora.secnceui.databinding.DialogCenterFragmentContentBinding

/**
 * 中间弹框，确认/取消按钮
 */
class CommonFragmentContentDialog constructor() : BaseFragmentDialog<DialogCenterFragmentContentBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): DialogCenterFragmentContentBinding {
        return DialogCenterFragmentContentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setCanceledOnTouchOutside(false)
        mBinding?.apply {
            if (!TextUtils.isEmpty(contentText)) {
                mtContent.text = contentText
            }
            if (!TextUtils.isEmpty(submitText)) {
                mbSubmit.text = submitText
            }
            mbSubmit.setOnClickListener {
                clickListener?.onConfirmClick()
                dismiss()
            }
        }
    }

    private fun addMargin(view: View) {
        val layoutParams: FrameLayout.LayoutParams = view.layoutParams as FrameLayout.LayoutParams
        val marginHorizontal = 38.dp.toInt()
        val marginVertical = 24.dp.toInt()
        layoutParams.setMargins(marginHorizontal, 0, marginHorizontal, marginVertical)
        view.layoutParams = layoutParams
    }

    private var contentText: String = ""
    private var submitText: String = ""
    private var clickListener: OnClickBottomListener? = null

    fun contentText(contentText: String) = apply {
        this.contentText = contentText
    }

    fun submitText(submitText: String) = apply {
        this.submitText = submitText
    }

    fun setOnClickListener(clickListener: OnClickBottomListener) = apply {
        this.clickListener = clickListener
    }

    interface OnClickBottomListener {
        /**
         * 点击确定按钮事件
         */
        fun onConfirmClick()
    }
}