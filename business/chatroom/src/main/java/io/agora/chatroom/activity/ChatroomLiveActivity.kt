package io.agora.chatroom.activity

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import io.agora.baseui.BaseUiActivity
import io.agora.baseui.BaseUiTool
import io.agora.baseui.general.net.Resource
import io.agora.buddy.tool.logD
import io.agora.chatroom.databinding.ActivityChatroomBinding
import io.agora.chatroom.ui.ChatroomLiveTopViewModel
import io.agora.chatroom.ui.ChatroomTopViewHelper
import io.agora.chatroom.ui.ChatroomWheatViewHelper
import io.agora.config.ConfigConstants
import io.agora.config.RouterParams
import io.agora.config.RouterPath
import io.agora.secnceui.ui.wheat.ChatroomWheatConstructor
import tools.bean.VRoomBean
import tools.bean.VRoomInfoBean

@Route(path = RouterPath.ChatroomPath)
class ChatroomLiveActivity : BaseUiActivity<ActivityChatroomBinding>() {

    private lateinit var chatroomLiveTopViewModel: ChatroomLiveTopViewModel

    private val chatroomType by lazy {
        intent.getIntExtra(RouterParams.KEY_CHATROOM_TYPE, ConfigConstants.Common_Chatroom)
    }

    private val roomBean: VRoomBean.RoomsBean? by lazy {
        intent.getSerializableExtra(RouterParams.KEY_CHATROOM_INFO) as VRoomBean.RoomsBean?
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
        initData()
    }

    private fun initData() {
        roomBean?.let {
            chatroomLiveTopViewModel.getRoomInfo(this,it.room_id ?: "")
        }
        chatroomLiveTopViewModel.getRoomInfoObservable().observe(this,
            Observer { response: Resource<VRoomInfoBean> ->
                val roomInfoBean = response.data
                "$roomInfoBean".logD()
            })
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
            }, false)
        )
//        roomBean?.let {
//            binding.chatroomGiftView.init(it.room_id ?: "")
//            binding.messageView.init(it.room_id ?: "")
//        }
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
                ChatroomWheatViewHelper.createSeatClickListener(this),
                ChatroomWheatViewHelper.createBotClickListener(this),
            ).setUpSeatInfoMap(ChatroomWheatConstructor.builder3dSeatMap(this))
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