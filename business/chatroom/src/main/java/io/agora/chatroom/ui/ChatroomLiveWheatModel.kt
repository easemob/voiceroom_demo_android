package io.agora.chatroom.ui

import androidx.lifecycle.ViewModel
import io.agora.secnceui.widget.top.IChatroomLiveTopView
import java.lang.ref.WeakReference

class ChatroomLiveWheatModel : ViewModel() {

    private var iChatroomLiveTopView: WeakReference<IChatroomLiveTopView>? = null

    fun setIChatroomLiveTopView(chatroomTopView: IChatroomLiveTopView) {
        iChatroomLiveTopView = WeakReference(chatroomTopView)
    }

    fun initChatroomInfo(){
        // test

    }
}