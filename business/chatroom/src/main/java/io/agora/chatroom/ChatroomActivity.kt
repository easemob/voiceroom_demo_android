package io.agora.chatroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.alibaba.android.arouter.facade.annotation.Route
import io.agora.baseui.BaseUiActivity
import io.agora.chatroom.databinding.ActivityChatroomBinding
import io.agora.config.ARouterPath
import io.agora.secnceui.widget.dialog.CommonFragmentAlertDialog
import io.agora.secnceui.widget.dialog.CommonSheetAlertDialog
import io.agora.secnceui.anis.ChatroomNoiseSuppressionSheetDialog
import io.agora.secnceui.audiosettings.ChatroomAudioSettingsSheetDialog
import io.agora.secnceui.soundselection.ChatroomSoundSelectionSheetDialog
import io.agora.secnceui.spatialaudio.ChatroomSpatialAudioSheetDialog
import io.agora.secnceui.wheat.ChatroomSeatManagerSheetDialog
import io.agora.secnceui.wheat.ChatroomWheat2DAudioView
import io.agora.secnceui.widget.dialog.CommonSheetContentDialog

@Route(path = ARouterPath.ChatroomPath)
class ChatroomActivity : BaseUiActivity<ActivityChatroomBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initListeners()
        binding.apply {
            mtAudioSettings.setOnClickListener {
                ChatroomAudioSettingsSheetDialog(object :
                    ChatroomAudioSettingsSheetDialog.OnClickAudioSettingsListener {
                    override fun onSoundEffect() {
                        ChatroomSoundSelectionSheetDialog()
                            .show(supportFragmentManager, "mtSoundSelection")
                    }

                    override fun onNoiseSuppression() {
                        ChatroomNoiseSuppressionSheetDialog()
                            .show(supportFragmentManager, "mtAnis")
                    }

                    override fun onSpatialAudio() {
                        ChatroomSpatialAudioSheetDialog()
                            .show(supportFragmentManager, "mtSpatialAudio")
                    }

                }).show(supportFragmentManager, "mtAudioSettings")
            }
            mtSeatManager.setOnClickListener {
                ChatroomSeatManagerSheetDialog().show(supportFragmentManager, "mtSeatManager")
            }
            mtAlertSheet.setOnClickListener {
                CommonSheetAlertDialog()
                    .contentText(getString(io.agora.secnceui.R.string.chatroom_ask_upstage))
                    .leftText(getString(io.agora.secnceui.R.string.chatroom_cancel))
                    .rightText(getString(io.agora.secnceui.R.string.chatroom_submit))
                    .setOnClickListener(object : CommonSheetAlertDialog.OnClickBottomListener {
                        override fun onConfirmClick() {
                            Toast.makeText(baseContext, "onConfirmClick", Toast.LENGTH_LONG).show()
                        }

                        override fun onCancelClick() {
                            Toast.makeText(baseContext, "onCancelClick", Toast.LENGTH_LONG).show()
                        }
                    })
                    .show(supportFragmentManager, "mtCommonSheet")
            }
            mtCenterDialog.setOnClickListener {
                CommonFragmentAlertDialog()
                    .titleText("Prompt")
                    .contentText("Exit the chatroom and recreate a new one?")
                    .leftText(getString(io.agora.secnceui.R.string.chatroom_cancel))
                    .rightText(getString(io.agora.secnceui.R.string.chatroom_submit))
                    .setOnClickListener(object : CommonFragmentAlertDialog.OnClickBottomListener {
                        override fun onConfirmClick() {
                            Toast.makeText(baseContext, "onConfirmClick", Toast.LENGTH_LONG).show()
                        }

                        override fun onCancelClick() {
                            Toast.makeText(baseContext, "onCancelClick", Toast.LENGTH_LONG).show()
                        }
                    })
                    .show(supportFragmentManager, "mtCenterDialog")
            }
            mtContentSheet.setOnClickListener {
                CommonSheetContentDialog()
                    .titleText(getString(io.agora.secnceui.R.string.chatroom_notice))
                    .contentText(getString(io.agora.secnceui.R.string.chatroom_first_enter_room_notice_tips))
                    .show(supportFragmentManager, "mtContentSheet")
            }
            mcbChatroomWheat.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    rvChatroomWheat2dSeat.isVisible = false
                    rvChatroomWheat3dSeat.isVisible = true
                } else {
                    rvChatroomWheat2dSeat.isVisible = true
                    rvChatroomWheat3dSeat.isVisible = false
                }
            }
            rvChatroomWheat2dSeat.setUpAdapter(ChatroomWheat2DAudioView.testWheatSeatList)
        }
    }

    private fun initListeners() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v: View?, insets: WindowInsetsCompat ->
            val inset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.clMain.setPaddingRelative(0, inset.top, 0, inset.bottom)
            WindowInsetsCompat.CONSUMED
        }
    }

    override fun getViewBinding(inflater: LayoutInflater): ActivityChatroomBinding {
        return ActivityChatroomBinding.inflate(inflater)
    }
}