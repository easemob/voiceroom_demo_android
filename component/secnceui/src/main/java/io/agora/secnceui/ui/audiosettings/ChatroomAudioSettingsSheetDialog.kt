package io.agora.secnceui.ui.audiosettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.agora.baseui.dialog.BaseFixedHeightSheetDialog
import io.agora.secnceui.R
import io.agora.secnceui.annotation.AINSModeType
import io.agora.secnceui.annotation.SoundSelectionType
import io.agora.secnceui.bean.ChatroomAudioSettingsBean
import io.agora.secnceui.databinding.DialogChatroomAudioSettingBinding

class ChatroomAudioSettingsSheetDialog constructor(private val audioSettingsListener: OnClickAudioSettingsListener) :
    BaseFixedHeightSheetDialog<DialogChatroomAudioSettingBinding>() {

    companion object {
        const val KEY_AUDIO_SETTINGS_INFO = "audio_settings"
    }

    private var audioSettingsInfo: ChatroomAudioSettingsBean? = null

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): DialogChatroomAudioSettingBinding {
        return DialogChatroomAudioSettingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.apply {
            audioSettingsInfo = get(KEY_AUDIO_SETTINGS_INFO) as ChatroomAudioSettingsBean?
        }
        binding?.apply {
            setOnApplyWindowInsets(clContent)
            audioSettingsInfo?.let { audioInfo ->
                mcbBottomSheetAgoraBot.isChecked = audioInfo.botOpen
                pbChatroomBotVolume.progress = audioInfo.botVolume
                mtChatroomBotVolume.text = audioInfo.botVolume.toString()
                mtBottomSheetSoundEffectArrow.text =
                    ChatroomAudioSettingsConstructor.getSoundEffectName(view.context, audioInfo.soundSelection)
                mtBottomSheetNoiseSuppressionArrow.text =
                    ChatroomAudioSettingsConstructor.getAINSName(view.context, audioInfo.anisMode)
                mtBottomSheetSpatialAudioArrow.text =
                    if (audioInfo.spatialOpen) view.context.getString(R.string.chatroom_open) else view.context.getString(R.string.chatroom_off)
                mtBottomSheetSoundEffectArrow.setOnClickListener {
                    audioSettingsListener.onSoundEffect(audioInfo.soundSelection)
                }
                mtBottomSheetNoiseSuppressionArrow.setOnClickListener {
                    audioSettingsListener.onNoiseSuppression(audioInfo.anisMode)
                }
                mtBottomSheetSpatialAudioArrow.setOnClickListener {
                    audioSettingsListener.onSpatialAudio(audioInfo.spatialOpen)
                }
            }
        }
    }

    interface OnClickAudioSettingsListener {

        fun onSoundEffect(@SoundSelectionType soundSelection: Int)

        fun onNoiseSuppression(@AINSModeType ainsModeType: Int)

        fun onSpatialAudio(isOpen: Boolean)
    }
}