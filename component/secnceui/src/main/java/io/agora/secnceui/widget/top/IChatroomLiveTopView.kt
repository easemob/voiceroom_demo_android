package io.agora.secnceui.widget.top

import io.agora.secnceui.bean.ChatroomInfoBean

interface IChatroomLiveTopView {
    /**头部初始化*/
    fun onChatroomInfo(chatroomInfo: ChatroomInfoBean)

    /**头部 text 更新*/
    fun onTextUpdate(@ChatroomLiveTopView.ChatroomTopType type: Int, text: String)

    /**头部 image 更新*/
    fun onImageUpdate(@ChatroomLiveTopView.ChatroomTopType type: Int, imageUrl: String)
}