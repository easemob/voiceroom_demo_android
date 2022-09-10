package io.agora.chatroom.ui

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import io.agora.secnceui.bean.AudienceBean
import io.agora.secnceui.bean.ChatroomInfoBean
import io.agora.secnceui.bean.enum.EnumAvatar
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
                ownerAvatar = EnumAvatar.Man5,
                chatroomName = "Chatroom-0728-001",
                topGifts = mutableListOf<AudienceBean>(
                    AudienceBean("1", "", EnumAvatar.Man5),
                    AudienceBean("2", "", EnumAvatar.Man8),
                    AudienceBean("3", "", EnumAvatar.Women5),
                ),
                audiencesCount = 10,
                giftCount = 100,
                watchCount = 200,
            )
        )
    }

    fun onTextUpdate(@ChatroomLiveTopView.ChatroomTopType type: Int, text: String) {
        iChatroomLiveTopView?.get()?.onTextUpdate(type, text)
    }

    fun onImageUpdate(@ChatroomLiveTopView.ChatroomTopType type: Int, @DrawableRes avatarRes: Int) {
        iChatroomLiveTopView?.get()?.onImageUpdate(type, avatarRes)
    }
}