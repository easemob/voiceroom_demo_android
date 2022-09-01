package io.agora.secnceui.spatialaudio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.agora.baseui.dialog.BaseSheetDialog
import io.agora.secnceui.databinding.DialogChatroom3dSpatialSeatTutorialBinding

class Chatroom3DSpatialSeatTutorialSheetDialog : BaseSheetDialog<DialogChatroom3dSpatialSeatTutorialBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): DialogChatroom3dSpatialSeatTutorialBinding {
        return DialogChatroom3dSpatialSeatTutorialBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            setOnApplyWindowInsets(root)
        }
    }
}