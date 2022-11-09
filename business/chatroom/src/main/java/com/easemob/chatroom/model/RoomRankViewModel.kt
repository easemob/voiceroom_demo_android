package com.easemob.chatroom.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.easemob.baseui.general.net.Resource
import com.easemob.chatroom.general.livedatas.SingleSourceLiveData
import com.easemob.chatroom.general.repositories.ChatroomHandsRepository
import tools.bean.VRGiftBean
import tools.bean.VRoomUserBean

/**
 * @author create by zhangwei03
 */
class RoomRankViewModel constructor() : ViewModel() {

    private val mRepository by lazy { ChatroomHandsRepository() }

    private val _giftsObservable: SingleSourceLiveData<Resource<VRGiftBean>> = SingleSourceLiveData()
    private val _membersObservable: SingleSourceLiveData<Resource<VRoomUserBean>> = SingleSourceLiveData()

    fun giftsObservable(): LiveData<Resource<VRGiftBean>> = _giftsObservable
    fun membersObservable(): LiveData<Resource<VRoomUserBean>> = _membersObservable

    /**
     * 获取榜单
     */
    fun getGifts(context: Context, roomId: String) {
        _giftsObservable.setSource(mRepository.getGifts(context, roomId))
    }

    /**
     * 获取用户列表
     */
    fun getMembers(context: Context, roomId: String, pageSize: Int, cursor: String) {
        _membersObservable.setSource(mRepository.getInvitedList(context, roomId, pageSize, cursor))
    }
}