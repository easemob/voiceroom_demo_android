package io.agora.chatroom.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import http.VRHttpClientManager
import io.agora.secnceui.bean.SeatInfoBean
import io.agora.secnceui.widget.top.IChatroomLiveTopView
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference

class ChatroomLiveWheatModel : ViewModel() {

    private var iChatroomLiveTopView: WeakReference<IChatroomLiveTopView>? = null

    fun setIChatroomLiveTopView(chatroomTopView: IChatroomLiveTopView) {
        iChatroomLiveTopView = WeakReference(chatroomTopView)
    }

    private val _seatList: MutableLiveData<List<SeatInfoBean>> = MutableLiveData<List<SeatInfoBean>>()

    fun seatList(): LiveData<List<SeatInfoBean>> = _seatList

    /**麦位信息列表列表*/
    fun getChatroomInfo() {

    }

    /**提交上麦申请*/
    fun requestOnStage(roomId: String, index: Int) {}

    /**撤销上麦申请*/
    fun cancelOnStage(roomId: String) {}

    /**闭麦/关麦*/
    fun muteAudio(roomId: String) {}

    /**取消闭麦/关麦*/
    fun unMuteAudio(roomId: String) {}

    /**下麦*/
    fun offStage() {}

    /**禁言指定麦位、取消指定麦位禁言*/
    fun muteSeat(index: Int, muted: Boolean) {

    }

    /**交换麦位*/
    fun exchangeSeat(oldIndex: Int, newIndex: Int) {

    }

    /**踢用户下麦*/
    fun suspendAudio(uid: String, index: Int) {

    }

    /**锁麦*/
    fun lockOnStage(index: Int) {

    }

    /**取消锁麦*/
    fun unlockOnStage(index: Int) {

    }

    /**邀请上麦*/
    fun inviteOnStage() {}

    /**房主同意上麦申请*/
    fun acceptAudienceRequest() {

    }

    /**房主拒绝上麦申请*/
    fun rejectAudienceRequest() {}

    /**聊天室成员同意上麦邀请*/
    fun acceptOwnerInvite() {}

    /**观众拒绝群主邀请上麦*/
    fun rejectOwnerInvite() {}
}