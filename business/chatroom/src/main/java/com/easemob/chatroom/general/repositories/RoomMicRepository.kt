package com.easemob.chatroom.general.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.easemob.baseui.general.callback.ResultCallBack
import com.easemob.baseui.general.net.Resource
import com.easemob.chatroom.general.net.ChatroomHttpManager
import tools.ValueCallBack
import tools.bean.VRMicBean
import tools.bean.VRMicListBean

/**
 * @author create by zhangwei03
 */
class RoomMicRepository : BaseRepository() {

    // 获取上麦申请列表
    fun getApplyMicList(
        context: Context,
        roomId: String,
        cursor: String,
        limit: Int
    ): LiveData<Resource<VRMicListBean>> {
        val resource = object : NetworkOnlyResource<VRMicListBean>() {
            override fun createCall(callBack: ResultCallBack<LiveData<VRMicListBean>>) {
                ChatroomHttpManager.getInstance()
                    .getApplyMicList(roomId, limit, cursor, object : ValueCallBack<VRMicListBean> {
                        override fun onSuccess(data: VRMicListBean) {
                            callBack.onSuccess(createLiveData(data))
                        }

                        override fun onError(code: Int, desc: String) {
                            callBack.onError(code, desc)
                        }
                    })
            }
        }
        return resource.asLiveData()
    }

    // 撤销上麦申请
    fun cancelSubmitMic(context: Context, roomId: String): LiveData<Resource<Boolean>> {
        val resource = object : NetworkOnlyResource<Boolean>() {
            override fun createCall(callBack: ResultCallBack<LiveData<Boolean>>) {
                ChatroomHttpManager.getInstance().cancelSubmitMic(roomId, object : ValueCallBack<Boolean> {
                    override fun onSuccess(data: Boolean) {
                        callBack.onSuccess(createLiveData(data))
                    }

                    override fun onError(code: Int, desc: String) {
                        callBack.onError(code, desc)
                    }
                })
            }
        }
        return resource.asLiveData()
    }

    // 获取麦位信息
    fun getMicInfo(context: Context, roomId: String): LiveData<Resource<VRMicBean>> {
        val resource = object : NetworkOnlyResource<VRMicBean>() {
            override fun createCall(callBack: ResultCallBack<LiveData<VRMicBean>>) {
                ChatroomHttpManager.getInstance().getMicInfo(roomId, object : ValueCallBack<VRMicBean> {
                    override fun onSuccess(data: VRMicBean) {
                        callBack.onSuccess(createLiveData(data))
                    }

                    override fun onError(code: Int, desc: String) {
                        callBack.onError(code, desc)
                    }
                })
            }
        }
        return resource.asLiveData()
    }

    // 关麦
    fun closeMic(context: Context, roomId: String, micIndex: Int): LiveData<Resource<Pair<Int, Boolean>>> {
        val resource = object : NetworkOnlyResource<Pair<Int, Boolean>>() {
            override fun createCall(callBack: ResultCallBack<LiveData<Pair<Int, Boolean>>>) {
                ChatroomHttpManager.getInstance().closeMic(roomId, micIndex, object : ValueCallBack<Boolean> {
                    override fun onSuccess(data: Boolean) {
                        callBack.onSuccess(createLiveData(Pair(micIndex, data)))
                    }

                    override fun onError(code: Int, desc: String) {
                        callBack.onError(code, desc)
                    }
                })
            }
        }
        return resource.asLiveData()
    }

    // 取消关麦
    fun cancelCloseMic(context: Context, roomId: String, micIndex: Int): LiveData<Resource<Pair<Int, Boolean>>> {
        val resource = object : NetworkOnlyResource<Pair<Int, Boolean>>() {
            override fun createCall(callBack: ResultCallBack<LiveData<Pair<Int, Boolean>>>) {
                ChatroomHttpManager.getInstance().cancelCloseMic(roomId, micIndex, object : ValueCallBack<Boolean> {
                    override fun onSuccess(data: Boolean) {
                        callBack.onSuccess(createLiveData(Pair(micIndex, data)))
                    }

                    override fun onError(code: Int, desc: String) {
                        callBack.onError(code, desc)
                    }
                })
            }
        }
        return resource.asLiveData()
    }

    // 下麦
    fun leaveMicMic(context: Context, roomId: String, micIndex: Int): LiveData<Resource<Pair<Int, Boolean>>> {
        val resource = object : NetworkOnlyResource<Pair<Int, Boolean>>() {
            override fun createCall(callBack: ResultCallBack<LiveData<Pair<Int, Boolean>>>) {
                ChatroomHttpManager.getInstance().leaveMic(roomId, micIndex, object : ValueCallBack<Boolean> {
                    override fun onSuccess(data: Boolean) {
                        callBack.onSuccess(createLiveData(Pair(micIndex, data)))
                    }

                    override fun onError(code: Int, desc: String) {
                        callBack.onError(code, desc)
                    }
                })
            }
        }
        return resource.asLiveData()
    }

    // 禁言指定麦位
    fun muteMic(context: Context, roomId: String, micIndex: Int): LiveData<Resource<Pair<Int, Boolean>>> {
        val resource = object : NetworkOnlyResource<Pair<Int, Boolean>>() {
            override fun createCall(callBack: ResultCallBack<LiveData<Pair<Int, Boolean>>>) {
                ChatroomHttpManager.getInstance().muteMic(roomId, micIndex, object : ValueCallBack<Boolean> {
                    override fun onSuccess(data: Boolean) {
                        callBack.onSuccess(createLiveData(Pair(micIndex, data)))
                    }

                    override fun onError(code: Int, desc: String) {
                        callBack.onError(code, desc)
                    }
                })
            }
        }
        return resource.asLiveData()
    }

    // 取消指定麦位禁言
    fun cancelMuteMic(context: Context, roomId: String, micIndex: Int): LiveData<Resource<Pair<Int, Boolean>>> {
        val resource = object : NetworkOnlyResource<Pair<Int, Boolean>>() {
            override fun createCall(callBack: ResultCallBack<LiveData<Pair<Int, Boolean>>>) {
                ChatroomHttpManager.getInstance().cancelMuteMic(roomId, micIndex, object : ValueCallBack<Boolean> {
                    override fun onSuccess(data: Boolean) {
                        callBack.onSuccess(createLiveData(Pair(micIndex, data)))
                    }

                    override fun onError(code: Int, desc: String) {
                        callBack.onError(code, desc)
                    }
                })
            }
        }
        return resource.asLiveData()
    }

    // 踢用户下麦
    fun kickMic(
        context: Context,
        roomId: String,
        userId: String,
        micIndex: Int
    ): LiveData<Resource<Pair<Int, Boolean>>> {
        val resource = object : NetworkOnlyResource<Pair<Int, Boolean>>() {
            override fun createCall(callBack: ResultCallBack<LiveData<Pair<Int, Boolean>>>) {
                ChatroomHttpManager.getInstance().kickMic(roomId, userId, micIndex, object : ValueCallBack<Boolean> {
                    override fun onSuccess(data: Boolean) {
                        callBack.onSuccess(createLiveData(Pair(micIndex, data)))
                    }

                    override fun onError(code: Int, desc: String) {
                        callBack.onError(code, desc)
                    }
                })
            }
        }
        return resource.asLiveData()
    }

    // 用户拒绝上麦申请
    fun rejectMicInvitation(context: Context, roomId: String): LiveData<Resource<Boolean>> {
        val resource = object : NetworkOnlyResource<Boolean>() {
            override fun createCall(callBack: ResultCallBack<LiveData<Boolean>>) {
                ChatroomHttpManager.getInstance().rejectMicInvitation(roomId, object : ValueCallBack<Boolean> {
                    override fun onSuccess(data: Boolean) {
                        callBack.onSuccess(createLiveData(data))
                    }

                    override fun onError(code: Int, desc: String) {
                        callBack.onError(code, desc)
                    }
                })
            }
        }
        return resource.asLiveData()
    }

    // 锁麦
    fun lockMic(context: Context, roomId: String, micIndex: Int): LiveData<Resource<Pair<Int, Boolean>>> {
        val resource = object : NetworkOnlyResource<Pair<Int, Boolean>>() {
            override fun createCall(callBack: ResultCallBack<LiveData<Pair<Int, Boolean>>>) {
                ChatroomHttpManager.getInstance().lockMic(roomId, micIndex, object : ValueCallBack<Boolean> {
                    override fun onSuccess(data: Boolean) {
                        callBack.onSuccess(createLiveData(Pair(micIndex, data)))
                    }

                    override fun onError(code: Int, desc: String) {
                        callBack.onError(code, desc)
                    }
                })
            }
        }
        return resource.asLiveData()
    }

    // 取消锁麦
    fun cancelLockMic(context: Context, roomId: String, micIndex: Int): LiveData<Resource<Pair<Int, Boolean>>> {
        val resource = object : NetworkOnlyResource<Pair<Int, Boolean>>() {
            override fun createCall(callBack: ResultCallBack<LiveData<Pair<Int, Boolean>>>) {
                ChatroomHttpManager.getInstance().cancelLockMic(roomId, micIndex, object : ValueCallBack<Boolean> {
                    override fun onSuccess(data: Boolean) {
                        callBack.onSuccess(createLiveData(Pair(micIndex, data)))
                    }

                    override fun onError(code: Int, desc: String) {
                        callBack.onError(code, desc)
                    }
                })
            }
        }
        return resource.asLiveData()
    }

    // 邀请上麦
    fun invitationMic(context: Context, roomId: String, uid: String): LiveData<Resource<Boolean>> {
        val resource = object : NetworkOnlyResource<Boolean>() {
            override fun createCall(callBack: ResultCallBack<LiveData<Boolean>>) {
                ChatroomHttpManager.getInstance().invitationMic(roomId, uid, object : ValueCallBack<Boolean> {
                    override fun onSuccess(data: Boolean) {
                        callBack.onSuccess(createLiveData(data))
                    }

                    override fun onError(code: Int, desc: String) {
                        callBack.onError(code, desc)
                    }
                })
            }
        }
        return resource.asLiveData()
    }

    // 同意上麦申请
    fun applySubmitMic(context: Context, roomId: String, uid: String, micIndex: Int): LiveData<Resource<Boolean>> {
        val resource = object : NetworkOnlyResource<Boolean>() {
            override fun createCall(callBack: ResultCallBack<LiveData<Boolean>>) {
                ChatroomHttpManager.getInstance().applySubmitMic(roomId, uid, micIndex, object : ValueCallBack<Boolean> {
                    override fun onSuccess(data: Boolean) {
                        callBack.onSuccess(createLiveData(data))
                    }

                    override fun onError(code: Int, desc: String) {
                        callBack.onError(code, desc)
                    }
                })
            }
        }
        return resource.asLiveData()
    }

    // 拒绝上麦申请
    fun rejectSubmitMic(context: Context, roomId: String, uid: String): LiveData<Resource<Boolean>> {
        val resource = object : NetworkOnlyResource<Boolean>() {
            override fun createCall(callBack: ResultCallBack<LiveData<Boolean>>) {
                ChatroomHttpManager.getInstance().rejectSubmitMic(roomId, uid, object : ValueCallBack<Boolean> {
                    override fun onSuccess(data: Boolean) {
                        callBack.onSuccess(createLiveData(data))
                    }

                    override fun onError(code: Int, desc: String) {
                        callBack.onError(code, desc)
                    }
                })
            }
        }
        return resource.asLiveData()
    }
}