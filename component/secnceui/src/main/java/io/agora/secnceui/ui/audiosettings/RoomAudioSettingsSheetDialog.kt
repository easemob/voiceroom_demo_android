package io.agora.secnceui.ui.audiosettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.agora.baseui.dialog.BaseFixedHeightSheetDialog
import io.agora.secnceui.R
import io.agora.secnceui.annotation.AINSModeType
import io.agora.secnceui.annotation.SoundSelectionType
import io.agora.secnceui.bean.RoomAudioSettingsBean
import io.agora.secnceui.constants.ScenesConstant.DISABLE_ALPHA
import io.agora.secnceui.constants.ScenesConstant.ENABLE_ALPHA
import io.agora.secnceui.databinding.DialogChatroomAudioSettingBinding

class RoomAudioSettingsSheetDialog constructor(
    private val audioSettingsListener: OnClickAudioSettingsListener,
    private val isEnable: Boolean = true
) :
    BaseFixedHeightSheetDialog<DialogChatroomAudioSettingBinding>() {

    companion object {
        const val KEY_AUDIO_SETTINGS_INFO = "audio_settings"
    }

    private var audioSettingsInfo: RoomAudioSettingsBean? = null

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): DialogChatroomAudioSettingBinding {
        return DialogChatroomAudioSettingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.apply {
            audioSettingsInfo = get(KEY_AUDIO_SETTINGS_INFO) as RoomAudioSettingsBean?
        }
        binding?.apply {
            setOnApplyWindowInsets(clContent)
            if (isEnable) {
                mcbAgoraBot.alpha = ENABLE_ALPHA
                pbAgoraBotVolume.alpha = ENABLE_ALPHA
            } else {
                mcbAgoraBot.alpha = DISABLE_ALPHA
                pbAgoraBotVolume.alpha = DISABLE_ALPHA
            }
            mcbAgoraBot.isEnabled = isEnable
            pbAgoraBotVolume.isEnabled = isEnable

            audioSettingsInfo?.let { audioInfo ->
                mcbAgoraBot.isChecked = audioInfo.botOpen
                pbAgoraBotVolume.progress = audioInfo.botVolume
                mtAgoraBotVolumeValue.text = audioInfo.botVolume.toString()
                mtBestSoundEffectArrow.text =
                    RoomAudioSettingsConstructor.getSoundEffectName(view.context, audioInfo.soundSelection)
                mtNoiseSuppressionArrow.text =
                    RoomAudioSettingsConstructor.getAINSName(view.context, audioInfo.anisMode)
                mtSpatialAudioArrow.text = view.context.getString(R.string.chatroom_off)
                mtBestSoundEffectArrow.setOnClickListener {
                    audioSettingsListener.onSoundEffect(audioInfo.soundSelection, isEnable)
                }
                mtNoiseSuppressionArrow.setOnClickListener {
                    audioSettingsListener.onNoiseSuppression(audioInfo.anisMode, isEnable)
                }
                mtSpatialAudioArrow.setOnClickListener {
                    audioSettingsListener.onSpatialAudio(audioInfo.spatialOpen, isEnable)
                }
            }
        }
    }

    interface OnClickAudioSettingsListener {

        fun onSoundEffect(@SoundSelectionType soundSelection: Int, isEnable: Boolean)

        fun onNoiseSuppression(@AINSModeType ainsModeType: Int, isEnable: Boolean)

        fun onSpatialAudio(isOpen: Boolean, isEnable: Boolean)
    }
}