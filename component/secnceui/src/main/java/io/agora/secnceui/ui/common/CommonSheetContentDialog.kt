package io.agora.secnceui.ui.common

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.agora.baseui.dialog.BaseSheetDialog
import io.agora.secnceui.databinding.DialogBottomSheetContentBinding

/**
 * content dialog 类似聊天室公告
 */
class CommonSheetContentDialog constructor(): BaseSheetDialog<DialogBottomSheetContentBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): DialogBottomSheetContentBinding {
        return DialogBottomSheetContentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            if (!TextUtils.isEmpty(titleText)){
                mtTitle.text = titleText
            }
            if (!TextUtils.isEmpty(contentText)){
                mtContent.text = contentText
            }
        }
    }

    private var titleText: String = ""
    private var contentText: String = ""

    fun titleText(titleText: String) = apply {
        this.titleText = titleText
    }

    fun contentText(contentText: String) = apply {
        this.contentText = contentText
    }
}