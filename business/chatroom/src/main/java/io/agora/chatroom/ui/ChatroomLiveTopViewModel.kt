package io.agora.chatroom.ui

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.agora.baseui.general.net.Resource
import io.agora.chatroom.general.livedatas.SingleSourceLiveData
import io.agora.chatroom.general.repositories.ChatroomRepository
import io.agora.secnceui.bean.ChatroomInfoBean
import io.agora.secnceui.bean.ChatroomUserInfoBean
import io.agora.secnceui.widget.top.ChatroomLiveTopView
import io.agora.secnceui.widget.top.IChatroomLiveTopView
import tools.bean.VRoomInfoBean
import java.lang.ref.WeakReference

class ChatroomLiveTopViewModel : ViewModel() {

    private var iChatroomLiveTopView: WeakReference<IChatroomLiveTopView>? = null

    private val repository: ChatroomRepository by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        ChatroomRepository.getInstance()
    }

    private val roomObservable: SingleSourceLiveData<Resource<VRoomInfoBean>> by lazy {
        SingleSourceLiveData<Resource<VRoomInfoBean>>()
    }

    fun setIChatroomLiveTopView(chatroomTopView: IChatroomLiveTopView) {
        iChatroomLiveTopView = WeakReference(chatroomTopView)
    }

    fun initChatroomInfo() {
        // test
        iChatroomLiveTopView?.get()?.onChatroomInfo(

            ChatroomInfoBean(
                ownerName = "Susan Stark",
                ownerAvatar = "avatar9",
                chatroomName = "Chatroom-0728-001",
                topGifts = mutableListOf(
                    ChatroomUserInfoBean("1", "", "", "avatar5"),
                    ChatroomUserInfoBean("2", "", "", "avatar6"),
                    ChatroomUserInfoBean("3", "", "", "avatar7"),
                ),
                audiencesCount = 10,
                giftCount = 100,
                watchCount = 200,
            )
        )
    }


    fun getRoomInfoObservable(): LiveData<Resource<VRoomInfoBean>> {
        return roomObservable
    }


    fun getRoomInfo(roomId: String) {
        roomObservable.setSource(repository.getRoomInfo(roomId))
    }

    fun getRoomRepository(): ChatroomRepository = repository

    /**
     * 清理注册信息
     */
    fun clearRegisterInfo() {
        roomObservable.value = null
    }

    fun onTextUpdate(@ChatroomLiveTopView.ChatroomTopType type: Int, text: String) {
        iChatroomLiveTopView?.get()?.onTextUpdate(type, text)
    }

    fun onImageUpdate(@ChatroomLiveTopView.ChatroomTopType type: Int, @DrawableRes avatarRes: Int) {
        iChatroomLiveTopView?.get()?.onImageUpdate(type, avatarRes)
    }
}