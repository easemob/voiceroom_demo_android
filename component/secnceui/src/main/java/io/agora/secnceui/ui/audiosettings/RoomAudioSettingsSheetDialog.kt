package io.agora.secnceui.ui.audiosettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.SeekBar
import androidx.core.view.isVisible
import io.agora.baseui.dialog.BaseFixedHeightSheetDialog
import io.agora.buddy.tool.doOnProgressChanged
import io.agora.secnceui.R
import io.agora.secnceui.annotation.AINSModeType
import io.agora.secnceui.annotation.RoomType
import io.agora.secnceui.annotation.SoundSelectionType
import io.agora.secnceui.bean.RoomAudioSettingsBean
import io.agora.secnceui.constants.ScenesConstant.DISABLE_ALPHA
import io.agora.secnceui.constants.ScenesConstant.ENABLE_ALPHA
import io.agora.secnceui.databinding.DialogChatroomAudioSettingBinding

class RoomAudioSettingsSheetDialog constructor(private val audioSettingsListener: OnClickAudioSettingsListener) :
    BaseFixedHeightSheetDialog<DialogChatroomAudioSettingBinding>() {

    companion object {
        const val KEY_AUDIO_SETTINGS_INFO = "audio_settings"
    }

    private val audioSettingsInfo: RoomAudioSettingsBean by lazy {
        arguments?.getSerializable(KEY_AUDIO_SETTINGS_INFO) as RoomAudioSettingsBean
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): DialogChatroomAudioSettingBinding {
        return DialogChatroomAudioSettingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            setOnApplyWindowInsets(clContent)
            if (audioSettingsInfo.roomType == RoomType.CommonRoom) {
                mtSpatialAudio.isVisible = false
                mtSpatialAudioArrow.isVisible = false
            }
            if (audioSettingsInfo.enable) {
                mcbAgoraBot.alpha = ENABLE_ALPHA
                pbAgoraBotVolume.alpha = ENABLE_ALPHA
            } else {
                mcbAgoraBot.alpha = DISABLE_ALPHA
                pbAgoraBotVolume.alpha = DISABLE_ALPHA
            }
            mcbAgoraBot.isEnabled = audioSettingsInfo.enable
            pbAgoraBotVolume.isEnabled = audioSettingsInfo.enable

            mcbAgoraBot.isChecked = audioSettingsInfo.botOpen
            pbAgoraBotVolume.progress = audioSettingsInfo.botVolume
            mtAgoraBotVolumeValue.text = audioSettingsInfo.botVolume.toString()
            mtBestSoundEffectArrow.text =
                RoomAudioSettingsConstructor.getSoundEffectName(view.context, audioSettingsInfo.soundSelection)
            mtNoiseSuppressionArrow.text =
                RoomAudioSettingsConstructor.getAINSName(view.context, audioSettingsInfo.anisMode)
            mtSpatialAudioArrow.text = view.context.getString(R.string.chatroom_off)

            mcbAgoraBot.setOnCheckedChangeListener { button, isChecked ->
                audioSettingsListener.onBotCheckedChanged(button, isChecked)
            }
            mtBestSoundEffectArrow.setOnClickListener {
                audioSettingsListener.onSoundEffect(audioSettingsInfo.soundSelection, audioSettingsInfo.enable)
            }
            mtNoiseSuppressionArrow.setOnClickListener {
                audioSettingsListener.onNoiseSuppression(audioSettingsInfo.anisMode, audioSettingsInfo.enable)
            }
            mtSpatialAudioArrow.setOnClickListener {
                audioSettingsListener.onSpatialAudio(audioSettingsInfo.spatialOpen, audioSettingsInfo.enable)
            }
            pbAgoraBotVolume.doOnProgressChanged { seekBar, progress, fromUser ->
                mtAgoraBotVolumeValue.text = progress.toString()
                audioSettingsListener.onBotVolumeChange(seekBar, progress, fromUser)
            }
        }
    }

    interface OnClickAudioSettingsListener {

        /**机器人开关*/
        fun onBotCheckedChanged(buttonView: CompoundButton, isChecked: Boolean)

        /**机器人音量*/
        fun onBotVolumeChange(seekBar: SeekBar?, progress: Int, fromUser: Boolean)

        /**最佳音效*/
        fun onSoundEffect(@SoundSelectionType soundSelection: Int, isEnable: Boolean)

        /**AI降噪*/
        fun onNoiseSuppression(@AINSModeType ainsModeType: Int, isEnable: Boolean)

        /**空间音频*/
        fun onSpatialAudio(isOpen: Boolean, isEnable: Boolean)
    }
}