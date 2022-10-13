package io.agora.secnceui.widget.top

import io.agora.secnceui.bean.RoomInfoBean
import io.agora.secnceui.bean.RoomRankUserBean

interface IRoomLiveTopView {
    /**头部初始化*/
    fun onChatroomInfo(chatroomInfo: RoomInfoBean)

    fun onRankMember(topRankUsers: List<RoomRankUserBean>)

    /**需要特殊处理*/
    fun subMemberCount(){}

    fun onUpdateMemberCount(count:Int){}
    fun onUpdateWatchCount(count: Int){}
    fun onUpdateGiftCount(count: Int){}
}