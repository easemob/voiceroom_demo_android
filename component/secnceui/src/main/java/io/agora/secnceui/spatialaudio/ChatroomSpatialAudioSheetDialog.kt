package io.agora.secnceui.spatialaudio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import io.agora.baseui.dialog.BaseSheetDialog
import io.agora.secnceui.databinding.DialogChatroomSpatialAudioBinding

class ChatroomSpatialAudioSheetDialog constructor() : BaseSheetDialog<DialogChatroomSpatialAudioBinding>() {

    companion object {
        const val KEY_SPATIAL_OPEN = "key_spatial_open"
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): DialogChatroomSpatialAudioBinding {
        return DialogChatroomSpatialAudioBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setCanceledOnTouchOutside(false)
        arguments?.apply {
            val spatialOpen = getBoolean(KEY_SPATIAL_OPEN)
            binding?.mcbBottomBlueBotSpatialAudio?.isChecked = spatialOpen
        }
        binding?.apply {
            setOnApplyWindowInsets(root)
            ivBottomSheetBack.setOnClickListener {
                onHandleOnBackPressed()
            }
            pbBottomSheetBlueBotAttenuationFactor.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    mtBlueBotAttenuationFactor.text = progress.toString()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }

            })
            pbBottomSheetRedBotVolume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    mtRedBotAttenuationFactor.text = progress.toString()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            })
        }
    }
}