package io.agora.chatroom.ui

import androidx.lifecycle.ViewModel
import io.agora.secnceui.bean.AudienceBean
import io.agora.secnceui.bean.ChatroomInfoBean
import io.agora.secnceui.widget.top.ChatroomLiveTopView
import io.agora.secnceui.widget.top.IChatroomLiveTopView
import java.lang.ref.WeakReference

class ChatroomLiveTopViewModel : ViewModel() {

    private var iChatroomLiveTopView: WeakReference<IChatroomLiveTopView>? = null

    fun setIChatroomLiveTopView(chatroomTopView: IChatroomLiveTopView) {
        iChatroomLiveTopView = WeakReference(chatroomTopView)
    }

    fun initChatroomInfo() {
        // test
        iChatroomLiveTopView?.get()?.onChatroomInfo(
            ChatroomInfoBean(
                ownerName = "Susan Stark",
                chatroomName = "Chatroom-0728-001",
                topGifts = mutableListOf<AudienceBean>().apply {
                    add(AudienceBean("1"))
                    add(AudienceBean("2"))
                    add(AudienceBean("3"))
                },
                audiencesCount = 10,
                giftCount = 100,
                watchCount = 200,
            )
        )
    }

    fun onTextUpdate(@ChatroomLiveTopView.ChatroomTopType type: Int, text: String) {
        iChatroomLiveTopView?.get()?.onTextUpdate(type, text)
    }

    fun onImageUpdate(@ChatroomLiveTopView.ChatroomTopType type: Int, imageUrl: String) {
        iChatroomLiveTopView?.get()?.onImageUpdate(type, imageUrl)
    }
}