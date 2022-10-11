package io.agora.secnceui.ui.spatialaudio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.agora.baseui.dialog.BaseFixedHeightSheetDialog
import io.agora.buddy.tool.doOnProgressChanged
import io.agora.secnceui.R
import io.agora.secnceui.constants.ScenesConstant.DISABLE_ALPHA
import io.agora.secnceui.constants.ScenesConstant.ENABLE_ALPHA
import io.agora.secnceui.databinding.DialogChatroomSpatialAudioBinding

class RoomSpatialAudioSheetDialog constructor(private val isEnabled: Boolean = true) :
    BaseFixedHeightSheetDialog<DialogChatroomSpatialAudioBinding>() {

    companion object {
        const val KEY_SPATIAL_OPEN = "key_spatial_open"
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): DialogChatroomSpatialAudioBinding {
        return DialogChatroomSpatialAudioBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.attributes?.windowAnimations = R.style.BottomSheetDialogAnimation
        dialog?.setCanceledOnTouchOutside(false)
        arguments?.apply {
            val spatialOpen = getBoolean(KEY_SPATIAL_OPEN)
            binding?.mcbBlueBotSpatialAudio?.isChecked = spatialOpen
        }
        binding?.apply {
            ivBottomSheetBack.setOnClickListener {
                onHandleOnBackPressed()
            }
            if (isEnabled) {
                mcbBlueBotSpatialAudio.alpha = ENABLE_ALPHA
                pbBlueBotAttenuationFactor.alpha = ENABLE_ALPHA
                mcbBlueBotAirAbsorb.alpha = ENABLE_ALPHA
                mcbBlueBotVoiceBlur.alpha = ENABLE_ALPHA
                mcbRedBotSpatialAudio.alpha = ENABLE_ALPHA
                pbRedBotAttenuationFactor.alpha = ENABLE_ALPHA
                mcbRedBotAirAbsorb.alpha = ENABLE_ALPHA
                mcbRedBotVoiceBlur.alpha = ENABLE_ALPHA
            } else {
                mcbBlueBotSpatialAudio.alpha = DISABLE_ALPHA
                pbBlueBotAttenuationFactor.alpha = DISABLE_ALPHA
                mcbBlueBotAirAbsorb.alpha = DISABLE_ALPHA
                mcbBlueBotVoiceBlur.alpha = DISABLE_ALPHA
                mcbRedBotSpatialAudio.alpha = DISABLE_ALPHA
                pbRedBotAttenuationFactor.alpha = DISABLE_ALPHA
                mcbRedBotAirAbsorb.alpha = DISABLE_ALPHA
                mcbRedBotVoiceBlur.alpha = DISABLE_ALPHA
            }
            mcbBlueBotSpatialAudio.isEnabled = isEnabled
            pbBlueBotAttenuationFactor.isEnabled = isEnabled
            mcbBlueBotAirAbsorb.isEnabled = isEnabled
            mcbBlueBotVoiceBlur.isEnabled = isEnabled
            mcbRedBotSpatialAudio.isEnabled = isEnabled
            pbRedBotAttenuationFactor.isEnabled = isEnabled
            mcbRedBotAirAbsorb.isEnabled = isEnabled
            mcbRedBotVoiceBlur.isEnabled = isEnabled

            mcbBlueBotSpatialAudio.setOnCheckedChangeListener { buttonView, isChecked ->
                Toast.makeText(requireContext(), "setOnCheckedChangeListener", Toast.LENGTH_SHORT).show()
            }
            mcbBlueBotSpatialAudio.setOnClickListener {
                Toast.makeText(requireContext(), getString(R.string.chatroom_only_host_can_change_robot), Toast.LENGTH_SHORT).show()
            }

            pbBlueBotAttenuationFactor.doOnProgressChanged { _, progress, _ ->
                mtBlueBotAttenuationFactorValue.text = progress.toString()
            }
            pbRedBotAttenuationFactor.doOnProgressChanged { _, progress, _ ->
                mtRedBotAttenuationFactorValue.text = progress.toString()
            }
        }
    }
}