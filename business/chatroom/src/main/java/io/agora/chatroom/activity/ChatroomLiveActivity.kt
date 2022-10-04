package io.agora.chatroom.activity

import android.Manifest
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnTouchListener
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import bean.ChatMessageData
import com.alibaba.android.arouter.facade.annotation.Route
import custormgift.OnMsgCallBack
import io.agora.baseui.BaseUiActivity
import io.agora.baseui.adapter.OnItemClickListener
import io.agora.buddy.tool.GsonTools.toBean
import io.agora.buddy.tool.ToastTools
import io.agora.buddy.tool.logE
import io.agora.chat.ChatClient
import io.agora.chatroom.R
import io.agora.chatroom.bean.RoomKitBean
import io.agora.chatroom.controller.RtcRoomController
import io.agora.chatroom.databinding.ActivityChatroomBinding
import io.agora.chatroom.general.constructor.RoomInfoConstructor.convertByRoomDetailInfo
import io.agora.chatroom.general.constructor.RoomInfoConstructor.convertByRoomInfo
import io.agora.chatroom.general.net.HttpManager
import io.agora.chatroom.general.repositories.ProfileManager
import io.agora.chatroom.model.ChatroomViewModel
import io.agora.chatroom.ui.RoomGiftViewDelegate
import io.agora.chatroom.ui.RoomHandsViewDelegate
import io.agora.chatroom.ui.RoomObservableViewDelegate
import io.agora.config.ConfigConstants
import io.agora.config.RouterParams
import io.agora.config.RouterPath
import io.agora.secnceui.bean.MicInfoBean
import io.agora.secnceui.ui.mic.RoomMicConstructor
import io.agora.secnceui.widget.barrage.ChatroomMessagesView
import io.agora.secnceui.widget.primary.MenuItemClickListener
import io.agora.secnceui.widget.top.OnLiveTopClickListener
import manager.ChatroomConfigManager
import manager.ChatroomMsgHelper
import org.json.JSONException
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import tools.ValueCallBack
import tools.bean.VRMicBean
import tools.bean.VRUserBean
import tools.bean.VRoomBean
import tools.bean.VRoomInfoBean

@Route(path = RouterPath.ChatroomPath)
class ChatroomLiveActivity : BaseUiActivity<ActivityChatroomBinding>(), EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks, ChatroomConfigManager.ChatroomListener,
    RoomObservableViewDelegate.OnRoomViewDelegateListener {

    companion object {
        const val RC_PERMISSIONS = 101
    }

    /**room viewModel*/
    private lateinit var roomViewModel: ChatroomViewModel
    private lateinit var giftViewDelegate: RoomGiftViewDelegate
    private lateinit var handsDelegate: RoomHandsViewDelegate

    /**
     * 代理头部view以及麦位view
     */
    private lateinit var roomObservableDelegate: RoomObservableViewDelegate

    /**创建房间or房间内请求详情数据*/
    private var roomInfoBean: VRoomInfoBean? = null

    /**房间基础*/
    private val roomKitBean = RoomKitBean()
    private var password : String? = "";

    override fun getViewBinding(inflater: LayoutInflater): ActivityChatroomBinding {
        return ActivityChatroomBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        roomViewModel = ViewModelProvider(this)[ChatroomViewModel::class.java]
        giftViewDelegate = RoomGiftViewDelegate.getInstance(this,binding.chatroomGiftView)
        handsDelegate = RoomHandsViewDelegate.getInstance(this,binding.chatBottom)
        initListeners()
        initData()
        initView()
        requestAudioPermission()
    }

    private fun initData() {
        val roomBean = intent.getSerializableExtra(RouterParams.KEY_CHATROOM_INFO) as VRoomBean.RoomsBean?
        roomInfoBean = intent.getSerializableExtra(RouterParams.KEY_CHATROOM_DETAILS_INFO) as VRoomInfoBean?
        password = intent.getStringExtra(RouterParams.KEY_CHATROOM_JOIN_PASSWORD).toString()
        if (roomBean == null && roomInfoBean == null) {
            ToastTools.show(this, "roomInfo is null!")
            finish()
        } else if (roomInfoBean != null) {
            // 详情进入数据全
            roomInfoBean?.room?.let { roomDetail ->
                roomKitBean.convertByRoomDetailInfo(roomDetail)
                handsDelegate.onRoomDetails(roomDetail.room_id,roomDetail.owner.uid)
                giftViewDelegate.onRoomDetails(roomDetail.room_id,roomDetail.owner.uid)
            }
        } else {
            // 房间列表进入，需请求详情
            roomBean?.let { roomInfo ->
                roomKitBean.convertByRoomInfo(roomInfo)
                handsDelegate.onRoomDetails(roomBean.room_id,roomBean.ownerUid)
                roomViewModel.getDetails(this, roomKitBean.roomId)
                giftViewDelegate.onRoomDetails(roomBean.room_id,roomBean.owner.uid)
            }
        }
        ChatroomMsgHelper.getInstance().init(roomKitBean.chatroomId)

    }

    private fun initListeners() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _: View?, insets: WindowInsetsCompat ->
            val inset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.clMain.setPaddingRelative(0, inset.top, 0, inset.bottom)
            WindowInsetsCompat.CONSUMED
        }
        binding.clMain.setOnTouchListener(OnTouchListener { v, event ->
            reset()
            false
        })
        binding.messageView.setMessageViewListener(object : ChatroomMessagesView.MessageViewListener {
            override fun onItemClickListener(message: ChatMessageData?) {
            }

            override fun onListClickListener() {
              reset()
            }
        })
    }

    private fun initView() {
        binding.chatBottom.initMenu(roomKitBean.roomType)
        if (roomKitBean.roomType == ConfigConstants.RoomType.Common_Chatroom) { // 普通房间
            binding.likeView.likeView.setOnClickListener { binding.likeView.addFavor() }
            binding.chatroomGiftView.init(roomKitBean.chatroomId)
            binding.messageView.init(roomKitBean.chatroomId,roomKitBean.ownerId)
            binding.rvChatroom2dMicLayout.isVisible = true
            binding.rvChatroom3dMicLayout.isVisible = false
            roomObservableDelegate =
                RoomObservableViewDelegate(this, roomKitBean, binding.cTopView, binding.rvChatroom2dMicLayout)
            binding.rvChatroom2dMicLayout.onItemClickListener(
                object : OnItemClickListener<MicInfoBean> {
                    override fun onItemClick(data: MicInfoBean, view: View, position: Int, viewType: Long) {
                        roomObservableDelegate.onUserMicClick(data)
                    }
                },
                object : OnItemClickListener<MicInfoBean> {
                    override fun onItemClick(data: MicInfoBean, view: View, position: Int, viewType: Long) {
                        if (roomKitBean.isOwner) {
                            roomObservableDelegate.onBotMicClick(
                                RtcRoomController.get().isUseBot,
                                getString(R.string.chatroom_open_bot_prompt)
                            )
                        }
                    }
                }
            ).setUpAdapter(RtcRoomController.get().isUseBot)
        } else { // 空间音效房间
            binding.likeView.isVisible = false
            binding.rvChatroom2dMicLayout.isVisible = false
            binding.rvChatroom3dMicLayout.isVisible = true
            roomObservableDelegate =
                RoomObservableViewDelegate(this, roomKitBean, binding.cTopView, binding.rvChatroom3dMicLayout)
            binding.rvChatroom3dMicLayout.onItemClickListener(
                object : OnItemClickListener<MicInfoBean> {
                    override fun onItemClick(data: MicInfoBean, view: View, position: Int, viewType: Long) {
                        roomObservableDelegate.onUserMicClick(data)
                    }
                },
                object : OnItemClickListener<MicInfoBean> {
                    override fun onItemClick(data: MicInfoBean, view: View, position: Int, viewType: Long) {
                        if (roomKitBean.isOwner) {
                            roomObservableDelegate.onBotMicClick(
                                RtcRoomController.get().isUseBot,
                                getString(R.string.chatroom_open_bot_prompt)
                            )
                        }
                    }
                },
            ).setUpMicInfoMap(RoomMicConstructor.builderDefault3dMicMap(this))
        }
        // 头部 如果是创建房间进来有详情
        roomObservableDelegate.onRoomDetails(roomInfoBean)
        roomObservableDelegate.onRoomViewDelegateListener = this
        binding.cTopView.setOnLiveTopClickListener(object : OnLiveTopClickListener {
            override fun onClickBack(view: View) {
                roomObservableDelegate.onExitRoom(getString(R.string.chatroom_exit), finishBack = {
                    finish()
                })
            }

            override fun onClickRank(view: View) {
                roomObservableDelegate.onClickRank()
            }

            override fun onClickNotice(view: View) {
                roomObservableDelegate.onClickNotice(roomInfoBean)
            }

            override fun onClickSoundSocial(view: View) {
                roomObservableDelegate.onClickSoundSocial(roomInfoBean)
            }
        })
        binding.chatBottom.setMenuItemOnClickListener(object : MenuItemClickListener {
            override fun onChatExtendMenuItemClick(itemId: Int, view: View?) {
                when (itemId) {
                    io.agora.secnceui.R.id.extend_item_eq -> {
                        roomObservableDelegate.onAudioSettingsDialog(finishBack = {
                            finish()
                        })
                    }
                    io.agora.secnceui.R.id.extend_item_mic -> {
                        ToastTools.show(this@ChatroomLiveActivity,"点击mic",Toast.LENGTH_LONG)
                    }
                    io.agora.secnceui.R.id.extend_item_hand_up -> {
                        "extend_item_hand_up isOwner:${handsDelegate.isOwner}".logE("onChatExtendMenuItemClick")
                        if (handsDelegate.isOwner){
                            if (this@ChatroomLiveActivity::handsDelegate.isInitialized) {
                                handsDelegate.showOwnerHandsDialog()
                                binding.chatBottom.setShowHandStatus(true,false)
                            }
                        }else{
                            if (this@ChatroomLiveActivity::handsDelegate.isInitialized) {
                                handsDelegate.showMemberHandsDialog()
                            }
                        }
                    }
                    io.agora.secnceui.R.id.extend_item_gift -> {
                        giftViewDelegate.showGiftDialog()
                    }
                }
            }

            override fun onInputViewFocusChange(focus: Boolean) {
                checkFocus(focus)
            }

            override fun onInputLayoutClick() {
                checkFocus(false)
            }

            override fun onEmojiClick(isShow: Boolean) {

            }

            override fun onSendMessage(content: String?) {
                ChatroomMsgHelper.getInstance().sendTxtMsg(content,
                    ProfileManager.getInstance().profile.name, object : OnMsgCallBack() {
                        override fun onSuccess(message: ChatMessageData?) {
                            binding.messageView.refresh()
                            hideKeyboard()
                        }

                        override fun onError(messageId: String?, code: Int, error: String?) {

                        }
                    })
            }
        })
        ChatroomConfigManager.getInstance().setChatRoomListener(this)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        if (window.attributes.softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (currentFocus != null) {
                imm.hideSoftInputFromWindow(
                    currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0) {
            return false
        }
        return super.onKeyDown(keyCode, event);
    }

    override fun finish() {
        RtcRoomController.get().destroy()
        roomViewModel.leaveRoom(this, roomKitBean.roomId)
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
        roomViewModel.initSdkJoin(roomKitBean,password)
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


    private fun reset() {
        if (roomKitBean.roomType == ConfigConstants.RoomType.Common_Chatroom){
            binding.chatBottom.hideExpressionView()
            binding.chatBottom.showInput()
            binding.likeView.isVisible = true
            binding.chatBottom.hindViewChangeIcon()
            hideKeyboard()
            binding.clMain.isFocusable = true
        }
    }

    override fun receiveTextMessage(roomId: String?, message: ChatMessageData?) {
        binding.messageView.refresh()
    }

    override fun receiveGift(roomId: String?, message: ChatMessageData?) {
        binding.chatroomGiftView.refresh()
    }

    override fun receiveApplySite(roomId: String?, message: ChatMessageData?) {
        Log.e("liveActivity", "receiveApplySite")
        binding.chatBottom.setShowHandStatus(handsDelegate.isOwner,true)
    }

    override fun roomAttributesDidUpdated(roomId: String?, attributeMap: MutableMap<String, String>?, fromId: String?) {
        super.roomAttributesDidUpdated(roomId, attributeMap, fromId)
        "roomAttributesDidUpdated roomId:$roomId  fromId:$fromId attributeMap:$attributeMap".logE("roomAttributesDid")
        if (isFinishing) return
        if (!TextUtils.equals(roomKitBean.chatroomId, roomId)) return
        attributeMap?.let {
            if (roomKitBean.roomType == ConfigConstants.RoomType.Common_Chatroom) { // 普通房间
                binding.rvChatroom2dMicLayout.receiverAttributeMap(it)
            }else{
                binding.rvChatroom3dMicLayout.receiverAttributeMap(it)
            }
        }
        for (entry in attributeMap!!.entries) {
            try {
                val json = attributeMap[entry.key]
                Log.e("attributeMap", "key: $json");
                val attribute = toBean(json, VRMicBean::class.java)
                attribute.let {
                    if ( attribute!!.member.chat_uid.equals(ProfileManager.getInstance().profile.chat_uid)){
                        binding.chatBottom.setEnableHand(false)
                    }else{
                        binding.chatBottom.setEnableHand(true)
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    private fun checkFocus(focus:Boolean){
        binding.likeView.isVisible = !focus
    }

    override fun roomAttributesDidRemoved(roomId: String?, keyList: List<String>?, fromId: String?) {
        super.roomAttributesDidRemoved(roomId, keyList, fromId)
        "roomAttributesDidRemoved roomId:$roomId  fromId:$fromId keyList:$keyList".logE("roomAttributesDid")
    }

    override fun onTokenWillExpire() {
        HttpManager.getInstance(this).loginWithToken(
            ChatClient.getInstance().deviceInfo.getString("deviceid"),
            ProfileManager.getInstance().profile.portrait,object : ValueCallBack<VRUserBean>{
                override fun onSuccess(bean: VRUserBean?) {
                    ChatroomConfigManager.getInstance().renewToken(bean!!.im_token)
                }

                override fun onError(code: Int, desc: String?) {
                    "onError: $code  desc: $desc".logE("onTokenWillExpire")
                }

            })
    }

    override fun receiveDeclineApply(roomId: String?, message: ChatMessageData?) {

    }
    // 麦位管理请求上麦成功回调，更改小手状态
    override fun onSubmitMicResponse() {

    }
}