package io.agora.chatroom

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.alibaba.android.arouter.facade.annotation.Route
import io.agora.baseui.BaseUiActivity
import io.agora.baseui.BaseUiTool
import io.agora.chatroom.databinding.ActivityChatroomBinding
import io.agora.chatroom.ui.ChatroomLiveTopViewModel
import io.agora.chatroom.ui.ChatroomTopViewHelper
import io.agora.chatroom.ui.ChatroomWheatViewHelper
import io.agora.config.ConfigConstants
import io.agora.config.RouterParams
import io.agora.config.RouterPath
import io.agora.secnceui.ui.wheat.ChatroomWheatConstructor

@Route(path = RouterPath.ChatroomPath)
class ChatroomLiveActivity : BaseUiActivity<ActivityChatroomBinding>() {

    private lateinit var chatroomLiveTopViewModel: ChatroomLiveTopViewModel

    private val chatroomType by lazy {
        intent.getIntExtra(RouterParams.KEY_CHATROOM_TYPE, ConfigConstants.Common_Chatroom)
    }

    override fun getViewBinding(inflater: LayoutInflater): ActivityChatroomBinding {
        return ActivityChatroomBinding.inflate(inflater)
    }

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
    }

    private fun initView() {
        binding.cTopView.setOnLiveTopClickListener(
            ChatroomTopViewHelper.createTopViewClickListener(this, finishBack = {
                finish()
            })
        )
        chatroomLiveTopViewModel.setIChatroomLiveTopView(binding.cTopView)
    }

    private fun test() {
        if (chatroomType == ConfigConstants.Common_Chatroom) {
            binding.rvChatroomWheat2dSeat.isVisible = true
            binding.rvChatroomWheat3dSeat.isVisible = false
            binding.rvChatroomWheat2dSeat.onItemClickListener(
                ChatroomWheatViewHelper.createSeatClickListener(this),
                ChatroomWheatViewHelper.createBotClickListener(this),
            ).setUpAdapter(
                ChatroomWheatConstructor.builder2dSeatList(),
                ChatroomWheatConstructor.builder2dBotSeatList(this)
            )
        } else {
            binding.rvChatroomWheat2dSeat.isVisible = false
            binding.rvChatroomWheat3dSeat.isVisible = true
            binding.rvChatroomWheat3dSeat.onItemClickListener(
                ChatroomWheatViewHelper.createSeatClickListener(this)
            )
        }

        chatroomLiveTopViewModel.initChatroomInfo()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0) {
            return false
        }
        return super.onKeyDown(keyCode, event);
    }
}