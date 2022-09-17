package io.agora.chatroom.activity

import android.Manifest
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import io.agora.baseui.BaseUiActivity
import io.agora.baseui.general.callback.OnResourceParseCallback
import io.agora.baseui.general.net.Resource
import io.agora.buddy.tool.logE
import io.agora.chatroom.controller.RtcChatroomController
import io.agora.chatroom.databinding.ActivityChatroomBinding
import io.agora.chatroom.general.constructor.ChatroomInfoConstructor
import io.agora.chatroom.model.ChatroomViewModel
import io.agora.chatroom.ui.ChatroomTopViewHelper
import io.agora.chatroom.ui.ChatroomWheatViewHelper
import io.agora.config.ConfigConstants
import io.agora.config.RouterParams
import io.agora.config.RouterPath
import io.agora.secnceui.R
import io.agora.secnceui.ui.wheat.ChatroomWheatConstructor
import io.agora.secnceui.widget.primary.MenuItemClickListener
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import tools.bean.VRoomBean
import tools.bean.VRoomBean.RoomsBean
import tools.bean.VRoomInfoBean

@Route(path = RouterPath.ChatroomPath)
class ChatroomLiveActivity : BaseUiActivity<ActivityChatroomBinding>(), EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks {

    companion object {
        const val RC_PERMISSIONS = 101
    }

    private lateinit var roomViewModel: ChatroomViewModel

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
        roomViewModel = ViewModelProvider(this)[ChatroomViewModel::class.java]
        initListeners()
        initView()
        initData()
        requestAudioPermission()
    }

    private fun initData() {
        roomBean?.let {
            roomViewModel.getDetails(this, it.room_id ?: "")
        }

        roomViewModel.roomDetailObservable.observe(this) { response: Resource<VRoomInfoBean> ->
            parseResource(response, object : OnResourceParseCallback<VRoomInfoBean>() {

                override fun onSuccess(data: VRoomInfoBean?) {
                    "roomDetailObservable onSuccess".logE()
                    data?.let {
                        binding.cTopView.onChatroomInfo(ChatroomInfoConstructor.convertTopUiBean(it))
                        val seatInfoList = ChatroomInfoConstructor.convertMicUiBean(it)
                        val botInfo = ChatroomInfoConstructor.convertMicBotUiBean(it)
                        binding.rvChatroomWheat2dSeat.updateAdapter(seatInfoList, mutableListOf(botInfo))
                    }
                }

                override fun onError(code: Int, message: String?) {
                    "roomDetailObservable onError $code,message $message".logE()
                }
            })
        }

    }

    private fun initListeners() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _: View?, insets: WindowInsetsCompat ->
            val inset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.clMain.setPaddingRelative(0, inset.top, 0, inset.bottom)
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun initView() {
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
        binding.cTopView.setOnLiveTopClickListener(
            ChatroomTopViewHelper.createTopViewClickListener(this, finishBack = {
                finish()
            }, false)
        )
//        roomBean?.let {
//            binding.chatroomGiftView.init(it.room_id ?: "")
//            binding.messageView.init(it.room_id ?: "")
//        }
        binding.chatBottom.setMenuItemOnClickListener(object : MenuItemClickListener {
            override fun onChatExtendMenuItemClick(itemId: Int, view: View?) {
                when (itemId) {
                    R.id.extend_item_eq -> {
                        ChatroomTopViewHelper.showAudioSettingsDialog(this@ChatroomLiveActivity, finishBack = {
                            finish()
                        }, false)
                    }
                    else -> {

                    }
                }
            }

            override fun onInputViewFocusChange(focus: Boolean) {
            }

            override fun onEmojiClick() {
            }

            override fun onSendMessage(content: String?) {

            }

        })
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0) {
            return false
        }
        return super.onKeyDown(keyCode, event);
    }

    override fun finish() {
        RtcChatroomController.get().leaveChannel()
        super.finish()
    }

    @AfterPermissionGranted(RC_PERMISSIONS)
    private fun requestAudioPermission() {
        val perms = arrayOf(Manifest.permission.RECORD_AUDIO)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            onPermissionGrant()
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(PermissionRequest.Builder(this, RC_PERMISSIONS, *perms).build())
        }
    }

    private fun onPermissionGrant() {
        RtcChatroomController.get().initMain(application)
        RtcChatroomController.get().joinChannel(roomBean?.room_id ?: "1111", 111)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        "onPermissionsGranted requestCode$requestCode $perms".logE()
        onPermissionGrant()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        "onPermissionsDenied $perms ".logE()
    }

    override fun onRationaleAccepted(requestCode: Int) {
        "onRationaleAccepted requestCode$requestCode ".logE()
        if (requestCode == RC_PERMISSIONS) {
            onPermissionGrant()
        }
    }

    override fun onRationaleDenied(requestCode: Int) {
        "onRationaleDenied requestCode$requestCode ".logE()
    }
}