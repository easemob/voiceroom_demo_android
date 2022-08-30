package io.agora.secnceui.spatialaudio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.agora.baseui.dialog.BaseSheetDialog
import io.agora.secnceui.databinding.DialogChatroomSpatialAudioBinding

class ChatroomSpatialAudioSheetDialog : BaseSheetDialog<DialogChatroomSpatialAudioBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): DialogChatroomSpatialAudioBinding {
        return DialogChatroomSpatialAudioBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setCanceledOnTouchOutside(false)
        binding?.apply {
            setOnApplyWindowInsets(root)
            ivBottomSheetBack.setOnClickListener {
                onHandleOnBackPressed()
            }
        }
    }
}