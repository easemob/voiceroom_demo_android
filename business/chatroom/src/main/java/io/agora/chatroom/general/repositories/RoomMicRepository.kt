package io.agora.chatroom.general.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import io.agora.baseui.general.callback.ResultCallBack
import io.agora.baseui.general.net.Resource
import io.agora.chatroom.general.net.HttpManager
import tools.ValueCallBack
import tools.bean.VRMicBean
import tools.bean.VRMicListBean
import tools.bean.VRoomBean
import tools.bean.VRoomBean.RoomsBean
import tools.bean.VRoomUserBean

/**
 * @author create by zhangwei03
 */
class RoomMicRepository : BaseRepository() {

    // 获取上麦申请列表
    fun getApplyMicList(context: Context, roomId: String,cursor: String, limit: Int): LiveData<Resource<VRMicListBean>> {
        val resource = object : NetworkOnlyResource<VRMicListBean>() {
            override fun createCall(callBack: ResultCallBack<LiveData<VRMicListBean>>) {
                HttpManager.getInstance(context).getApplyMicList(roomId, limit, cursor,object : ValueCallBack<VRMicListBean> {
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

    // 提交上麦申请
    fun submitMic(context: Context, roomId: String, micIndex: Int): LiveData<Resource<Boolean>> {
        val resource = object : NetworkOnlyResource<Boolean>() {
            override fun createCall(callBack: ResultCallBack<LiveData<Boolean>>) {
                HttpManager.getInstance(context).submitMic(roomId, micIndex, object : ValueCallBack<Boolean> {
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

    // 撤销上麦申请
    fun cancelSubmitMic(context: Context, roomId: String): LiveData<Resource<Boolean>> {
        val resource = object : NetworkOnlyResource<Boolean>() {
            override fun createCall(callBack: ResultCallBack<LiveData<Boolean>>) {
                HttpManager.getInstance(context).cancelSubmitMic(roomId, object : ValueCallBack<Boolean> {
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
    fun getMicInfo(context: Context, roomId: String): LiveData<Resource<VRoomUserBean>> {
        val resource = object : NetworkOnlyResource<VRoomUserBean>() {
            override fun createCall(callBack: ResultCallBack<LiveData<VRoomUserBean>>) {
                HttpManager.getInstance(context).getMicInfo(roomId, object : ValueCallBack<VRoomUserBean> {
                    override fun onSuccess(data: VRoomUserBean) {
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
    fun closeMic(context: Context, roomId: String, micIndex: Int): LiveData<Resource<Boolean>> {
        val resource = object : NetworkOnlyResource<Boolean>() {
            override fun createCall(callBack: ResultCallBack<LiveData<Boolean>>) {
                HttpManager.getInstance(context).closeMic(roomId, micIndex, object : ValueCallBack<Boolean> {
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

    // 取消关麦
    fun cancelCloseMic(context: Context, roomId: String, micIndex: Int): LiveData<Resource<Boolean>> {
        val resource = object : NetworkOnlyResource<Boolean>() {
            override fun createCall(callBack: ResultCallBack<LiveData<Boolean>>) {
                HttpManager.getInstance(context).cancelCloseMic(roomId, micIndex, object : ValueCallBack<Boolean> {
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

    // 下麦
    fun leaveMicMic(context: Context, roomId: String): LiveData<Resource<Boolean>> {
        val resource = object : NetworkOnlyResource<Boolean>() {
            override fun createCall(callBack: ResultCallBack<LiveData<Boolean>>) {
                HttpManager.getInstance(context).leaveMic(roomId, object : ValueCallBack<Boolean> {
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

    // 禁言指定麦位
    fun muteMic(context: Context, roomId: String, micIndex: Int): LiveData<Resource<String>> {
        val resource = object : NetworkOnlyResource<String>() {
            override fun createCall(callBack: ResultCallBack<LiveData<String>>) {
                HttpManager.getInstance(context).muteMic(roomId, micIndex, object : ValueCallBack<String> {
                    override fun onSuccess(data: String) {
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

    // 取消指定麦位禁言
    fun cancelMuteMic(context: Context, roomId: String, micIndex: Int): LiveData<Resource<String>> {
        val resource = object : NetworkOnlyResource<String>() {
            override fun createCall(callBack: ResultCallBack<LiveData<String>>) {
                HttpManager.getInstance(context).cancelMuteMic(roomId, micIndex, object : ValueCallBack<String> {
                    override fun onSuccess(data: String) {
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

    // 交换麦位
    fun exChangeMic(context: Context, roomId: String, from: Int, to: Int): LiveData<Resource<Boolean>> {
        val resource = object : NetworkOnlyResource<Boolean>() {
            override fun createCall(callBack: ResultCallBack<LiveData<Boolean>>) {
                HttpManager.getInstance(context).exChangeMic(roomId, from, to, object : ValueCallBack<Boolean> {
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

    // 踢用户下麦
    fun kickMic(context: Context, roomId: String, userId: String, micIndex: Int): LiveData<Resource<Boolean>> {
        val resource = object : NetworkOnlyResource<Boolean>() {
            override fun createCall(callBack: ResultCallBack<LiveData<Boolean>>) {
                HttpManager.getInstance(context).kickMic(roomId, userId, micIndex, object : ValueCallBack<Boolean> {
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

    // 用户拒绝上麦申请
    fun rejectMicInvitation(context: Context, roomId: String): LiveData<Resource<VRoomBean>> {
        val resource = object : NetworkOnlyResource<VRoomBean>() {
            override fun createCall(callBack: ResultCallBack<LiveData<VRoomBean>>) {
                HttpManager.getInstance(context).rejectMicInvitation(roomId, object : ValueCallBack<VRoomBean> {
                    override fun onSuccess(data: VRoomBean) {
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
    fun lockMic(context: Context, roomId: String, micIndex: Int): LiveData<Resource<Boolean>> {
        val resource = object : NetworkOnlyResource<Boolean>() {
            override fun createCall(callBack: ResultCallBack<LiveData<Boolean>>) {
                HttpManager.getInstance(context).lockMic(roomId, micIndex, object : ValueCallBack<Boolean> {
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

    // 取消锁麦
    fun cancelLockMic(context: Context, roomId: String): LiveData<Resource<Boolean>> {
        val resource = object : NetworkOnlyResource<Boolean>() {
            override fun createCall(callBack: ResultCallBack<LiveData<Boolean>>) {
                HttpManager.getInstance(context).cancelLockMic(roomId, object : ValueCallBack<Boolean> {
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

    // 邀请上麦
    fun invitationMic(context: Context, roomId: String, uid: String): LiveData<Resource<Boolean>> {
        val resource = object : NetworkOnlyResource<Boolean>() {
            override fun createCall(callBack: ResultCallBack<LiveData<Boolean>>) {
                HttpManager.getInstance(context).invitationMic(roomId, uid, object : ValueCallBack<Boolean> {
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
                HttpManager.getInstance(context).applySubmitMic(roomId, uid, micIndex, object : ValueCallBack<Boolean> {
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
                HttpManager.getInstance(context).rejectSubmitMic(roomId, uid, object : ValueCallBack<Boolean> {
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