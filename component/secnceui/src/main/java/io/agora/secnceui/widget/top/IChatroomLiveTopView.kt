package io.agora.secnceui.widget.top

import io.agora.secnceui.annotation.ChatroomTopType
import io.agora.secnceui.bean.RoomInfoBean

interface IChatroomLiveTopView {
    /**头部初始化*/
    fun onChatroomInfo(chatroomInfo: RoomInfoBean)

    /**头部 text 更新*/
    fun onTextUpdate(@ChatroomTopType type: Int, text: String)

    /**头部 image 更新*/
    fun onImageUpdate(@ChatroomTopType type: Int, avatar: String)
}