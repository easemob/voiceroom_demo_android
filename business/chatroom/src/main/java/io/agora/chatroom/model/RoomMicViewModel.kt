package io.agora.chatroom.model

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.agora.baseui.general.net.Resource
import io.agora.chatroom.general.livedatas.SingleSourceLiveData
import io.agora.chatroom.general.repositories.RoomMicRepository
import tools.bean.VRMicListBean
import tools.bean.VRoomUserBean

/**
 * @author create by zhangwei03
 */
class RoomMicViewModel constructor(application: Application) : AndroidViewModel(application) {

    private val mRepository by lazy { RoomMicRepository() }

    private val _applyMicListObservable: SingleSourceLiveData<Resource<VRMicListBean>> = SingleSourceLiveData()
    private val _submitMicObservable: SingleSourceLiveData<Resource<Boolean>> = SingleSourceLiveData()
    private val _cancelSubmitMicObservable: SingleSourceLiveData<Resource<Boolean>> = SingleSourceLiveData()
    private val _micInfoObservable: SingleSourceLiveData<Resource<VRMicBean>> = SingleSourceLiveData()
    private val _closeMicObservable: SingleSourceLiveData<Resource<Boolean>> = SingleSourceLiveData()
    private val _cancelCloseMicObservable: SingleSourceLiveData<Resource<Pair<Int, Boolean>>> = SingleSourceLiveData()
    private val _leaveMicObservable: SingleSourceLiveData<Resource<Pair<Int, Boolean>>> = SingleSourceLiveData()
    private val _muteMicObservable: SingleSourceLiveData<Resource<Pair<Int, Boolean>>> = SingleSourceLiveData()
    private val _cancelMuteMicObservable: SingleSourceLiveData<Resource<Pair<Int, Boolean>>> = SingleSourceLiveData()
    private val _exChangeMicObservable: SingleSourceLiveData<Resource<Triple<Int, Int, Boolean>>> = SingleSourceLiveData()
    private val _kickMicObservable: SingleSourceLiveData<Resource<Pair<Int, Boolean>>> = SingleSourceLiveData()
    private val _rejectMicInvitationObservable: SingleSourceLiveData<Resource<Boolean>> = SingleSourceLiveData()
    private val _lockMicObservable: SingleSourceLiveData<Resource<Pair<Int, Boolean>>> = SingleSourceLiveData()
    private val _cancelLockMicObservable: SingleSourceLiveData<Resource<Pair<Int, Boolean>>> = SingleSourceLiveData()
    private val _invitationMicObservable: SingleSourceLiveData<Resource<Boolean>> = SingleSourceLiveData()
    private val _applySubmitMicObservable: SingleSourceLiveData<Resource<Boolean>> = SingleSourceLiveData()
    private val _rejectSubmitMicObservable: SingleSourceLiveData<Resource<Boolean>> = SingleSourceLiveData()

    fun applyMicListObservable(): LiveData<Resource<VRMicListBean>> = _applyMicListObservable
    fun submitMicObservable(): LiveData<Resource<Boolean>> = _submitMicObservable
    fun cancelSubmitMicObservable(): LiveData<Resource<Boolean>> = _cancelSubmitMicObservable
    fun micInfoObservable(): LiveData<Resource<VRMicBean>> = _micInfoObservable
    fun closeMicObservable(): LiveData<Resource<Boolean>> = _closeMicObservable
    fun cancelCloseMicObservable(): LiveData<Resource<Pair<Int, Boolean>>> = _cancelCloseMicObservable
    fun leaveMicObservable(): LiveData<Resource<Pair<Int, Boolean>>> = _leaveMicObservable
    fun muteMicObservable(): LiveData<Resource<Pair<Int, Boolean>>> = _muteMicObservable
    fun cancelMuteMicObservable(): LiveData<Resource<Pair<Int, Boolean>>> = _cancelMuteMicObservable
    fun exChangeMicObservable(): LiveData<Resource<Triple<Int, Int, Boolean>>> = _exChangeMicObservable
    fun kickMicObservable(): LiveData<Resource<Pair<Int, Boolean>>> = _kickMicObservable
    fun rejectMicInvitationObservable(): LiveData<Resource<Boolean>> = _rejectMicInvitationObservable
    fun lockMicObservable(): LiveData<Resource<Pair<Int, Boolean>>> = _lockMicObservable
    fun cancelLockMicObservable(): LiveData<Resource<Pair<Int, Boolean>>> = _cancelLockMicObservable
    fun invitationMicObservable(): LiveData<Resource<Boolean>> = _invitationMicObservable
    fun applySubmitMicObservable(): LiveData<Resource<Boolean>> = _applySubmitMicObservable
    fun rejectSubmitMicObservable(): LiveData<Resource<Boolean>> = _rejectSubmitMicObservable

    // 获取上麦申请列表
    fun getApplyMicList(context: Context, roomId: String, cursor: String, micIndex: Int) {
        _applyMicListObservable.setSource(mRepository.getApplyMicList(context, roomId, cursor, micIndex))
    }

    // 提交上麦申请
    fun submitMic(context: Context, roomId: String, micIndex: Int) {
        _submitMicObservable.setSource(mRepository.submitMic(context, roomId, micIndex))
    }

    // 撤销上麦申请
    fun cancelSubmitMic(context: Context, roomId: String) {
        _cancelSubmitMicObservable.setSource(mRepository.cancelSubmitMic(context, roomId))
    }

    // 获取麦位信息
    fun getMicInfo(context: Context, roomId: String) {
        _micInfoObservable.setSource(mRepository.getMicInfo(context, roomId))
    }

    // 关麦
    fun closeMic(context: Context, roomId: String, micIndex: Int) {
        _closeMicObservable.setSource(mRepository.closeMic(context, roomId, micIndex))
    }

    // 取消关麦
    fun cancelCloseMic(context: Context, roomId: String, micIndex: Int) {
        _cancelCloseMicObservable.setSource(mRepository.cancelCloseMic(context, roomId, micIndex))
    }

    // 下麦
    fun leaveMicMic(context: Context, roomId: String, micIndex: Int) {
        _leaveMicObservable.setSource(mRepository.leaveMicMic(context, roomId, micIndex))
    }

    // 禁言指定麦位
    fun muteMic(context: Context, roomId: String, micIndex: Int) {
        _muteMicObservable.setSource(mRepository.muteMic(context, roomId, micIndex))
    }

    // 取消指定麦位禁言
    fun cancelMuteMic(context: Context, roomId: String, micIndex: Int) {
        _cancelMuteMicObservable.setSource(mRepository.cancelMuteMic(context, roomId, micIndex))
    }

    // 交换麦位
    fun exChangeMic(context: Context, roomId: String, from: Int, to: Int) {
        _exChangeMicObservable.setSource(mRepository.exChangeMic(context, roomId, from, to))
    }

    // 踢用户下麦
    fun kickMic(context: Context, roomId: String, userId: String, micIndex: Int) {
        _kickMicObservable.setSource(mRepository.kickMic(context, roomId, userId, micIndex))
    }

    // 用户拒绝上麦申请
    fun rejectMicInvitation(context: Context, roomId: String) {
        _rejectMicInvitationObservable.setSource(mRepository.rejectMicInvitation(context, roomId))
    }

    // 锁麦
    fun lockMic(context: Context, roomId: String, micIndex: Int) {
        _lockMicObservable.setSource(mRepository.lockMic(context, roomId, micIndex))
    }

    // 取消锁麦
    fun cancelLockMic(context: Context, roomId: String, micIndex: Int) {
        _cancelLockMicObservable.setSource(mRepository.cancelLockMic(context, roomId, micIndex))
    }

    //  邀请上麦
    fun invitationMic(context: Context, roomId: String, userId: String) {
        _invitationMicObservable.setSource(mRepository.invitationMic(context, roomId, userId))
    }

    // 同意上麦申请
    fun applySubmitMic(context: Context, roomId: String, userId: String, micIndex: Int) {
        _applySubmitMicObservable.setSource(mRepository.applySubmitMic(context, roomId, userId, micIndex))
    }

    // 拒绝上麦申请
    fun rejectSubmitMic(context: Context, roomId: String, userId: String) {
        _rejectSubmitMicObservable.setSource(mRepository.rejectSubmitMic(context, roomId, userId))
    }
}