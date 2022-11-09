package com.easemob.secnceui.ui.spatialaudio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.easemob.baseui.dialog.BaseSheetDialog
import com.easemob.secnceui.databinding.DialogChatroom3dSpatialMicTutorialBinding

class Room3DSpatialMicTutorialSheetDialog constructor(): BaseSheetDialog<DialogChatroom3dSpatialMicTutorialBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): DialogChatroom3dSpatialMicTutorialBinding {
        return DialogChatroom3dSpatialMicTutorialBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            setOnApplyWindowInsets(root)
        }
    }
}