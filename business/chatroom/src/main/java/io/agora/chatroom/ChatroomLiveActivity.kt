package io.agora.chatroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alibaba.android.arouter.facade.annotation.Route
import io.agora.baseui.BaseUiActivity
import io.agora.baseui.BaseUiTool
import io.agora.buddy.tool.logD
import io.agora.chatroom.databinding.ActivityChatroomBinding
import io.agora.chatroom.ui.ChatroomLiveTopViewModel
import io.agora.config.ARouterPath
import io.agora.secnceui.R
import io.agora.secnceui.widget.dialog.CommonFragmentAlertDialog
import io.agora.secnceui.ainoise.ChatroomNoiseSuppressionSheetDialog
import io.agora.secnceui.audiosettings.ChatroomAudioSettingsSheetDialog
import io.agora.secnceui.bean.CustomerUsageBean
import io.agora.secnceui.soundselection.ChatroomSocialChatSheetDialog
import io.agora.secnceui.soundselection.ChatroomSoundSelectionSheetDialog
import io.agora.secnceui.spatialaudio.ChatroomSpatialAudioSheetDialog
import io.agora.secnceui.widget.wheat.ChatroomWheat2DAudioView
import io.agora.secnceui.widget.dialog.CommonSheetContentDialog
import io.agora.secnceui.widget.top.ChatroomLiveTopView
import io.agora.secnceui.widget.top.OnLiveTopClickListener

@Route(path = ARouterPath.ChatroomPath)
class ChatroomLiveActivity : BaseUiActivity<ActivityChatroomBinding>() {

    private lateinit var chatroomLiveTopViewModel: ChatroomLiveTopViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initListeners()
        chatroomLiveTopViewModel = BaseUiTool.getViewModel(ChatroomLiveTopViewModel::class.java, this)
        initView()
        // test
        test()
    }

    private fun initListeners() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v: View?, insets: WindowInsetsCompat ->
            val inset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.clMain.setPaddingRelative(0, inset.top, 0, inset.bottom)
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun initView() {
        binding.cTopView.let {
            it.setOnLiveTopClickListener(object : OnLiveTopClickListener {
                override fun onClickView(view: View, action: Int) {
                    when (action) {
                        ChatroomLiveTopView.ClickAction.BACK -> {
                            CommonFragmentAlertDialog()
                                .titleText("Prompt")
                                .contentText("Exit the chatroom?")
                                .leftText(getString(io.agora.secnceui.R.string.chatroom_cancel))
                                .rightText(getString(io.agora.secnceui.R.string.chatroom_submit))
                                .setOnClickListener(object : CommonFragmentAlertDialog.OnClickBottomListener {
                                    override fun onConfirmClick() {
                                        Toast.makeText(baseContext, "onConfirmClick", Toast.LENGTH_LONG).show()
                                        finish()
                                    }

                                    override fun onCancelClick() {
                                        Toast.makeText(baseContext, "onCancelClick", Toast.LENGTH_LONG).show()
                                    }
                                })
                                .show(supportFragmentManager, "mtCenterDialog")
                        }
                        ChatroomLiveTopView.ClickAction.RANK -> {
                            "排行榜".logD()
                            // TODO:
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
                        ChatroomLiveTopView.ClickAction.NOTICE -> {
                            CommonSheetContentDialog()
                                .titleText(getString(io.agora.secnceui.R.string.chatroom_notice))
                                .contentText(getString(R.string.chatroom_first_enter_room_notice_tips))
                                .show(supportFragmentManager, "mtContentSheet")

                        }
                        ChatroomLiveTopView.ClickAction.SOCIAL -> {
                            ChatroomSocialChatSheetDialog(object :
                                ChatroomSocialChatSheetDialog.OnClickSocialChatListener {

                                override fun onMoreSound() {
                                    ChatroomSpatialAudioSheetDialog()
                                        .show(supportFragmentManager, "mtSpatialAudio")
                                }
                            }).contentText("This scenario focuses on echo cancellation, noise reduction in a multi-person chat setting, creating a quiet chat atmosphere")
                                .customers(mutableListOf<CustomerUsageBean>().apply {
                                    add(CustomerUsageBean("ya", R.drawable.icon_chatroom_ya_launcher))
                                    add(CustomerUsageBean("soul", R.drawable.icon_chatroom_soul_launcher))
                                })
                                .show(supportFragmentManager, "chatroomSocialChatSheetDialog")
                        }
                    }
                }
            })
            chatroomLiveTopViewModel.setIChatroomLiveTopView(it)
        }
    }

    override fun getViewBinding(inflater: LayoutInflater): ActivityChatroomBinding {
        return ActivityChatroomBinding.inflate(inflater)
    }

    private fun test() {
        binding.apply {
            rvChatroomWheat2dSeat.setUpAdapter(ChatroomWheat2DAudioView.testWheatSeatList)
        }
        chatroomLiveTopViewModel.initChatroomInfo()
    }
}