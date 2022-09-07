package io.agora.chatroom

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alibaba.android.arouter.facade.annotation.Route
import io.agora.baseui.BaseUiActivity
import io.agora.baseui.BaseUiTool
import io.agora.baseui.adapter.OnItemClickListener
import io.agora.buddy.tool.logD
import io.agora.chatroom.databinding.ActivityChatroom3dSpatialBinding
import io.agora.chatroom.ui.ChatroomLiveTopViewModel
import io.agora.config.ARouterPath
import io.agora.secnceui.R
import io.agora.secnceui.ainoise.ChatroomAINSSheetDialog
import io.agora.secnceui.annotation.AINSModeType
import io.agora.secnceui.annotation.SoundSelectionType
import io.agora.secnceui.audiosettings.ChatroomAudioSettingsSheetDialog
import io.agora.secnceui.bean.*
import io.agora.secnceui.widget.dialog.CommonFragmentAlertDialog
import io.agora.secnceui.soundselection.ChatroomSocialChatSheetDialog
import io.agora.secnceui.soundselection.ChatroomSoundSelectionSheetDialog
import io.agora.secnceui.spatialaudio.ChatroomSpatialAudioSheetDialog
import io.agora.secnceui.widget.dialog.CommonSheetContentDialog
import io.agora.secnceui.widget.top.ChatroomLiveTopView
import io.agora.secnceui.widget.top.OnLiveTopClickListener

@Route(path = ARouterPath.Chatroom3DPath)
class ChatroomLive3DActivity : BaseUiActivity<ActivityChatroom3dSpatialBinding>() {

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
                            onHandleOnBackPressed()
                        }
                        ChatroomLiveTopView.ClickAction.RANK -> {
                            "排行榜".logD()
                            // TODO:
                            ChatroomAudioSettingsSheetDialog(object :
                                ChatroomAudioSettingsSheetDialog.OnClickAudioSettingsListener {

                                override fun onSoundEffect(@SoundSelectionType soundSelection: Int) {
                                    ChatroomSoundSelectionSheetDialog().apply {
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

    override fun getViewBinding(inflater: LayoutInflater): ActivityChatroom3dSpatialBinding {
        return ActivityChatroom3dSpatialBinding.inflate(inflater)
    }

    private fun test() {
        binding.rvChatroomWheat3dSeat.onItemClickListener(object :OnItemClickListener<SeatInfoBean>{
            override fun onItemClick(data: SeatInfoBean, view: View, position: Int, viewType: Long) {
                Toast.makeText(this@ChatroomLive3DActivity, "click ${data.name}", Toast.LENGTH_SHORT).show()
            }
        })
        chatroomLiveTopViewModel.initChatroomInfo()
    }

    override fun  onKeyDown(keyCode:Int , event: KeyEvent):Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    override fun onHandleOnBackPressed() {
        CommonFragmentAlertDialog()
            .titleText("Prompt")
            .contentText("Exit the chatroom?")
            .leftText(getString(io.agora.secnceui.R.string.chatroom_cancel))
            .rightText(getString(io.agora.secnceui.R.string.chatroom_submit))
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