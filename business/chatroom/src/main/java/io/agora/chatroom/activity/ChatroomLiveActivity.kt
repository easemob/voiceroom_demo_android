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
import io.agora.baseui.adapter.OnItemClickListener
import io.agora.baseui.general.callback.OnResourceParseCallback
import io.agora.baseui.general.net.Resource
import io.agora.buddy.tool.logE
import io.agora.chatroom.controller.RtcRoomController
import io.agora.chatroom.databinding.ActivityChatroomBinding
import io.agora.chatroom.general.constructor.RoomInfoConstructor
import io.agora.chatroom.general.repositories.ProfileManager
import io.agora.chatroom.model.ChatroomViewModel
import io.agora.chatroom.ui.*
import io.agora.config.ConfigConstants
import io.agora.config.RouterParams
import io.agora.config.RouterPath
import io.agora.secnceui.R
import io.agora.secnceui.bean.MicInfoBean
import io.agora.secnceui.ui.mic.RoomMicConstructor
import io.agora.secnceui.widget.primary.MenuItemClickListener
import io.agora.secnceui.widget.top.OnLiveTopClickListener
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import tools.DefaultValueCallBack
import tools.ValueCallBack
import tools.bean.VRoomBean
import tools.bean.VRoomInfoBean
import java.util.concurrent.atomic.AtomicBoolean

@Route(path = RouterPath.ChatroomPath)
class ChatroomLiveActivity : BaseUiActivity<ActivityChatroomBinding>(), EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks {

    companion object {
        const val RC_PERMISSIONS = 101
    }

    private lateinit var roomViewModel: ChatroomViewModel
    private lateinit var roomTopViewDelegate: RoomTopViewDelegate
    private lateinit var audio2DViewDelegate: Room2DMicViewDelegate
    private lateinit var audio3DViewDelegate: Room3DMicViewDelegate

    private val chatroomType by lazy {
        intent.getIntExtra(RouterParams.KEY_CHATROOM_TYPE, ConfigConstants.Common_Chatroom)
    }

    private val roomBean: VRoomBean.RoomsBean? by lazy {
        intent.getSerializableExtra(RouterParams.KEY_CHATROOM_INFO) as VRoomBean.RoomsBean?
    }

    private var roomInfoBean: VRoomInfoBean? = null

    override fun getViewBinding(inflater: LayoutInflater): ActivityChatroomBinding {
        return ActivityChatroomBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        roomViewModel = ViewModelProvider(this)[ChatroomViewModel::class.java]
        roomTopViewDelegate = RoomTopViewDelegate(this, binding.cTopView)
        initListeners()
        initView()
        initData()
        requestAudioPermission()
    }

    private fun initData() {
        roomBean?.let {
            roomViewModel.getDetails(this, it.room_id ?: "")
        }
        // 房间详情
        roomViewModel.roomDetailObservable.observe(this) { response: Resource<VRoomInfoBean> ->
            parseResource(response, object : OnResourceParseCallback<VRoomInfoBean>() {

                override fun onSuccess(data: VRoomInfoBean?) {
                    "roomDetailObservable onSuccess".logE()
                    data?.let { vRoomInfo ->
                        roomInfoBean = vRoomInfo
                        roomTopViewDelegate.onRoomDetails(vRoomInfo)
                        if (this@ChatroomLiveActivity::audio2DViewDelegate.isInitialized) {
                            audio2DViewDelegate.onRoomDetails(vRoomInfo)
                        }
                        if (this@ChatroomLiveActivity::audio3DViewDelegate.isInitialized) {
                            audio3DViewDelegate.onRoomDetails(vRoomInfo)
                        }
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
            audio2DViewDelegate = Room2DMicViewDelegate(this, binding.rvChatroom2dMicLayout)
            binding.rvChatroom2dMicLayout.isVisible = true
            binding.rvChatroom3dMicLayout.isVisible = false
            binding.rvChatroom2dMicLayout.onItemClickListener(
                object : OnItemClickListener<MicInfoBean> {
                    override fun onItemClick(data: MicInfoBean, view: View, position: Int, viewType: Long) {
                        audio2DViewDelegate.onUserMicClick(data, view, position, viewType)
                    }
                },
                object : OnItemClickListener<MicInfoBean> {
                    override fun onItemClick(data: MicInfoBean, view: View, position: Int, viewType: Long) {
                        audio2DViewDelegate.onBotMicClick(data, view, position, viewType)
                    }
                }
            ).setUpAdapter(
                RoomMicConstructor.builderDefault2dMicList(),
                RoomMicConstructor.builderDefault2dBotMicList(this)
            )
        } else {
            audio3DViewDelegate = Room3DMicViewDelegate(this, binding.rvChatroom3dMicLayout)
            binding.rvChatroom2dMicLayout.isVisible = false
            binding.rvChatroom3dMicLayout.isVisible = true
            binding.rvChatroom3dMicLayout.onItemClickListener(
                object : OnItemClickListener<MicInfoBean> {
                    override fun onItemClick(data: MicInfoBean, view: View, position: Int, viewType: Long) {
                        audio3DViewDelegate.onUserMicClick(data, view, position, viewType)
                    }
                },
                object : OnItemClickListener<MicInfoBean> {
                    override fun onItemClick(data: MicInfoBean, view: View, position: Int, viewType: Long) {
                        audio3DViewDelegate.onBotMicClick(data, view, position, viewType)
                    }
                },
            ).setUpMicInfoMap(RoomMicConstructor.builderDefault3dMicMap(this))
        }
        binding.cTopView.setOnLiveTopClickListener(object : OnLiveTopClickListener {
            override fun onClickBack(view: View) {
                roomTopViewDelegate.onClickBank(finishBack = {
                    finish()
                })
            }

            override fun onClickRank(view: View) {
                roomInfoBean?.let {
                    roomTopViewDelegate.onClickRank(it)
                }
            }

            override fun onClickNotice(view: View) {
                roomInfoBean?.let {
                    roomTopViewDelegate.onClickNotice(it)
                }
            }

            override fun onClickSoundSocial(view: View) {
                roomInfoBean?.let {
                    roomTopViewDelegate.onClickSoundSocial(it)
                }
            }
        })
//        roomBean?.let {
//            binding.chatroomGiftView.init(it.room_id ?: "")
//            binding.messageView.init(it.room_id ?: "")
//        }
        binding.chatBottom.setMenuItemOnClickListener(object : MenuItemClickListener {
            override fun onChatExtendMenuItemClick(itemId: Int, view: View?) {
                when (itemId) {
                    R.id.extend_item_eq -> {
                        roomTopViewDelegate.onAudioSettingsDialog(finishBack = {
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
        RtcRoomController.get().leaveChannel()
        roomBean?.room_id?.let {
            roomViewModel.leaveRoom(this, it)
        }
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
        roomBean?.let {
            roomViewModel.initSdkJoin(it)
        }
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