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
                ownerAvatar = "https://img2.baidu.com/it/u=321296820,1912578985&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500",
                chatroomName = "Chatroom-0728-001",
                topGifts = mutableListOf<AudienceBean>().apply {
                    add(
                        AudienceBean(
                            "1",
                            "",
                            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Finews.gtimg.com%2Fnewsapp_bt%2F0%2F14107850129%2F1000.jpg&refer=http%3A%2F%2Finews.gtimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1664632002&t=d964378cad20d6a222f07ebf25dc30f9"
                        )
                    )
                    add(
                        AudienceBean(
                            "2",
                            "",
                            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F313c8011e400e28bb50a01ee269be1c4d5f19b04.jpg&refer=http%3A%2F%2Fi0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1664632002&t=d230e1a6283cb8dcd1d5a4e70424d978"
                        )
                    )
                    add(
                        AudienceBean(
                            "3",
                            "",
                            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.19yxw.com%2Fongame%2F202203%2F16%2F1647396870_6.jpeg&refer=http%3A%2F%2Fimg.19yxw.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1664632002&t=500c36dafde1a2886e3b06d7c135f82c"
                        )
                    )
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