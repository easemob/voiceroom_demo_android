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
import io.agora.chatroom.databinding.ActivityChatroom3dSpatialBinding
import io.agora.chatroom.ui.ChatroomLiveTopViewModel
import io.agora.config.ARouterPath
import io.agora.secnceui.R
import io.agora.secnceui.annotation.SoundSelectionType
import io.agora.secnceui.bean.*
import io.agora.secnceui.rank.ChatroomContributionAndAudienceSheetDialog
import io.agora.secnceui.widget.dialog.CommonFragmentAlertDialog
import io.agora.secnceui.soundselection.ChatroomSocialChatSheetDialog
import io.agora.secnceui.soundselection.ChatroomSoundSelectionConstructor
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
        binding.cTopView.setOnLiveTopClickListener(object : OnLiveTopClickListener {
            override fun onClickView(view: View, action: Int) {
                when (action) {
                    ChatroomLiveTopView.ClickAction.BACK -> {
                        onHandleOnBackPressed()
                    }
                    ChatroomLiveTopView.ClickAction.RANK -> {
                        ChatroomContributionAndAudienceSheetDialog(this@ChatroomLive3DActivity).show(
                            supportFragmentManager,
                            "ContributionAndAudienceSheetDialog"
                        )
                    }
                    ChatroomLiveTopView.ClickAction.NOTICE -> {
                        CommonSheetContentDialog()
                            .titleText(getString(io.agora.secnceui.R.string.chatroom_notice))
                            .contentText(getString(R.string.chatroom_first_enter_room_notice_tips))
                            .show(supportFragmentManager, "mtContentSheet")

                    }
                    ChatroomLiveTopView.ClickAction.SOCIAL -> {
                        val curSoundSelection = ChatroomSoundSelectionConstructor.builderCurSoundSelection(
                            this@ChatroomLive3DActivity,
                            SoundSelectionType.Karaoke
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

    override fun getViewBinding(inflater: LayoutInflater): ActivityChatroom3dSpatialBinding {
        return ActivityChatroom3dSpatialBinding.inflate(inflater)
    }

    private fun test() {
        binding.rvChatroomWheat3dSeat.onItemClickListener(object : OnItemClickListener<SeatInfoBean> {
            override fun onItemClick(data: SeatInfoBean, view: View, position: Int, viewType: Long) {
                Toast.makeText(this@ChatroomLive3DActivity, "click ${data.name}", Toast.LENGTH_SHORT).show()
            }
        })
        chatroomLiveTopViewModel.initChatroomInfo()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
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