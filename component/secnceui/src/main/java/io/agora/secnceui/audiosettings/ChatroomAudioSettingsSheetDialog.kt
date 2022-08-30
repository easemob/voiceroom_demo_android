package io.agora.secnceui.audiosettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.agora.baseui.dialog.BaseSheetDialog
import io.agora.secnceui.databinding.DialogChatroomAudioSettingBinding

class ChatroomAudioSettingsSheetDialog(val audioSettingsListener: OnClickAudioSettingsListener) :
    BaseSheetDialog<DialogChatroomAudioSettingBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): DialogChatroomAudioSettingBinding {
        return DialogChatroomAudioSettingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        dialog?.setCanceledOnTouchOutside(false)

        binding?.apply {
            setOnApplyWindowInsets(root)
            mtBottomSheetSoundEffectArrow.setOnClickListener {
                audioSettingsListener.onSoundEffect()
            }
            mtBottomSheetNoiseSuppressionArrow.setOnClickListener {
                audioSettingsListener.onNoiseSuppression()
            }
            mtBottomSheetSpatialAudioArrow.setOnClickListener {
                audioSettingsListener.onSpatialAudio()
            }
        }
    }

    interface OnClickAudioSettingsListener {

        fun onSoundEffect()

        fun onNoiseSuppression()

        fun onSpatialAudio()
    }
}