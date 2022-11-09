package com.easemob.secnceui.widget.top

interface IRoomLiveTopView {
    /**头部初始化*/
    fun onChatroomInfo(chatroomInfo: com.easemob.secnceui.bean.RoomInfoBean)

    fun onRankMember(topRankUsers: List<com.easemob.secnceui.bean.RoomRankUserBean>)

    /**需要特殊处理*/
    fun subMemberCount(){}

    fun onUpdateMemberCount(count:Int){}
    fun onUpdateWatchCount(count: Int){}
    fun onUpdateGiftCount(count: Int){}
}