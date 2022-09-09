package io.agora.chatroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alibaba.android.arouter.facade.annotation.Route
import io.agora.baseui.BaseUiActivity
import io.agora.baseui.BaseUiTool
import io.agora.baseui.adapter.OnItemChildClickListener
import io.agora.baseui.adapter.OnItemClickListener
import io.agora.buddy.tool.logD
import io.agora.chatroom.databinding.ActivityChatroomBinding
import io.agora.chatroom.ui.ChatroomLiveTopViewModel
import io.agora.config.ARouterPath
import io.agora.secnceui.R
import io.agora.secnceui.ainoise.ChatroomAINSSheetDialog
import io.agora.secnceui.annotation.AINSModeType
import io.agora.secnceui.annotation.SoundSelectionType
import io.agora.secnceui.annotation.WheatSeatType
import io.agora.secnceui.audiosettings.ChatroomAudioSettingsSheetDialog
import io.agora.secnceui.bean.*
import io.agora.secnceui.widget.dialog.CommonFragmentAlertDialog
import io.agora.secnceui.soundselection.ChatroomSocialChatSheetDialog
import io.agora.secnceui.soundselection.ChatroomSoundSelectionConstructor
import io.agora.secnceui.soundselection.ChatroomSoundSelectionSheetDialog
import io.agora.secnceui.spatialaudio.ChatroomSpatialAudioSheetDialog
import io.agora.secnceui.wheat.ChatroomSeatManagerSheetDialog
import io.agora.secnceui.wheat.ChatroomWheatConstructor
import io.agora.secnceui.widget.dialog.CommonSheetAlertDialog
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
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _: View?, insets: WindowInsetsCompat ->
            val inset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.clMain.setPaddingRelative(0, inset.top, 0, inset.bottom)
            WindowInsetsCompat.CONSUMED
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onHandleOnBackPressed()
            }
        })
    }

    private fun initView() {
        binding.cTopView.setOnLiveTopClickListener(object : OnLiveTopClickListener {
            override fun onClickView(view: View, action: Int) {
                when (action) {
                    ChatroomLiveTopView.ClickAction.BACK -> {
                        onHandleOnBackPressed()
                    }
                    ChatroomLiveTopView.ClickAction.RANK -> {
                        "排行榜".logD()
                        // TODO:
                        ChatroomAudioSettingsSheetDialog(object :
                            ChatroomAudioSettingsSheetDialog.OnClickAudioSettingsListener {

                            override fun onSoundEffect(@SoundSelectionType soundSelection: Int) {
                                ChatroomSoundSelectionSheetDialog(object :
                                    ChatroomSoundSelectionSheetDialog.OnClickSoundSelectionListener {
                                    override fun onSoundEffect(soundSelection: SoundSelectionBean) {
                                        CommonFragmentAlertDialog()
                                            .titleText(getString(R.string.chatroom_prompt))
                                            .contentText(getString(R.string.chatroom_exit_and_create_one))
                                            .leftText(getString(R.string.chatroom_cancel))
                                            .rightText(getString(R.string.chatroom_confirm))
                                            .setOnClickListener(object :
                                                CommonFragmentAlertDialog.OnClickBottomListener {
                                                override fun onConfirmClick() {
                                                    finish()
                                                }

                                                override fun onCancelClick() {
                                                }
                                            })
                                            .show(supportFragmentManager, "mtCenterDialog")
                                    }

                                }).apply {
                                    arguments = Bundle().apply {
                                        putInt(
                                            ChatroomSoundSelectionSheetDialog.KEY_CURRENT_SELECTION,
                                            SoundSelectionType.Karaoke
                                        )
                                    }
                                }
                                    .show(supportFragmentManager, "mtSoundSelection")
                            }

                            override fun onNoiseSuppression(@AINSModeType ainsModeType: Int) {
                                ChatroomAINSSheetDialog().apply {
                                    arguments = Bundle().apply {
                                        putInt(ChatroomAINSSheetDialog.KEY_AINS_MODE, AINSModeType.High)
                                    }
                                }
                                    .show(supportFragmentManager, "mtAnis")
                            }

                            override fun onSpatialAudio(isOpen: Boolean) {
                                ChatroomSpatialAudioSheetDialog().apply {
                                    arguments = Bundle().apply {
                                        putBoolean(ChatroomSpatialAudioSheetDialog.KEY_SPATIAL_OPEN, isOpen)
                                    }
                                }
                                    .show(supportFragmentManager, "mtSpatialAudio")
                            }

                        }).apply {
                            arguments = Bundle().apply {
                                //test
                                val audioSettingsInfo = ChatroomAudioSettingsBean(
                                    botOpen = false,
                                    botVolume = 50,
                                    soundSelection = SoundSelectionType.GamingBuddy,
                                    anisMode = AINSModeType.Medium,
                                    spatialOpen = false
                                )
                                putSerializable(
                                    ChatroomAudioSettingsSheetDialog.KEY_AUDIO_SETTINGS_INFO,
                                    audioSettingsInfo
                                )
                            }
                        }.show(supportFragmentManager, "mtAudioSettings")
                    }
                    ChatroomLiveTopView.ClickAction.NOTICE -> {
                        CommonSheetContentDialog()
                            .titleText(getString(io.agora.secnceui.R.string.chatroom_notice))
                            .contentText(getString(R.string.chatroom_first_enter_room_notice_tips))
                            .show(supportFragmentManager, "mtContentSheet")

                    }
                    ChatroomLiveTopView.ClickAction.SOCIAL -> {
                        val curSoundSelection = ChatroomSoundSelectionConstructor.builderCurSoundSelection(
                            this@ChatroomLiveActivity,
                            SoundSelectionType.SocialChat
                        )
                        ChatroomSocialChatSheetDialog(object :
                            ChatroomSocialChatSheetDialog.OnClickSocialChatListener {

                            override fun onMoreSound() {
                                ChatroomSpatialAudioSheetDialog()
                                    .show(supportFragmentManager, "mtSpatialAudio")
                            }
                        }).contentText(curSoundSelection.soundIntroduce)
                            .customers(curSoundSelection.customer ?: mutableListOf())
                            .show(supportFragmentManager, "chatroomSocialChatSheetDialog")
                    }
                }
            }
        })
        chatroomLiveTopViewModel.setIChatroomLiveTopView(binding.cTopView)
    }

    override fun getViewBinding(inflater: LayoutInflater): ActivityChatroomBinding {
        return ActivityChatroomBinding.inflate(inflater)
    }

    private fun test() {
        binding.rvChatroomWheat2dSeat.onItemClickListener(object : OnItemClickListener<SeatInfoBean> {

            override fun onItemClick(data: SeatInfoBean, view: View, position: Int, viewType: Long) {
                super.onItemClick(data, view, position, viewType)
                // 普通用户,空闲位置
                if (data.wheatSeatType == WheatSeatType.Idle) {
                    CommonSheetAlertDialog()
                        .contentText(getString(R.string.chatroom_request_speak))
                        .rightText(getString(R.string.chatroom_confirm))
                        .leftText(getString(R.string.chatroom_cancel))
                        .setOnClickListener(object : CommonSheetAlertDialog.OnClickBottomListener {
                            override fun onConfirmClick() {

                            }

                            override fun onCancelClick() {
                            }

                        })
                        .show(supportFragmentManager, "SeatManagerSheetDialog11")
                } else {
                    ChatroomSeatManagerSheetDialog().apply {
                        arguments = Bundle().apply {
                            putSerializable(ChatroomSeatManagerSheetDialog.KEY_SEAT_INFO, data)
                        }
                        theme
                    }.show(supportFragmentManager, "SeatManagerSheetDialog")
                }
            }
        }, object : OnItemChildClickListener<BotSeatInfoBean> {

            override fun onItemChildClick(
                data: BotSeatInfoBean?, extData: Any?, view: View?, position: Int, itemViewType: Int
            ) {
                if (extData is SeatInfoBean) {
                    Toast.makeText(this@ChatroomLiveActivity, "${extData.name}", Toast.LENGTH_SHORT).show()
                }
            }
        }).setUpAdapter(
            ChatroomWheatConstructor.builder2dSeatList(),
            ChatroomWheatConstructor.builder2dBotSeatList()
        )
        chatroomLiveTopViewModel.initChatroomInfo()
    }

    private fun onHandleOnBackPressed() {
        CommonFragmentAlertDialog()
            .titleText(getString(R.string.chatroom_prompt))
            .contentText(getString(R.string.chatroom_exit))
            .leftText(getString(R.string.chatroom_cancel))
            .rightText(getString(R.string.chatroom_confirm))
            .setOnClickListener(object : CommonFragmentAlertDialog.OnClickBottomListener {
                override fun onConfirmClick() {
                    finish()
                }

                override fun onCancelClick() {
                }
            })
            .show(supportFragmentManager, "mtCenterDialog")
    }
}