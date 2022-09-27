package io.agora.chatroom.general.repositories;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import io.agora.baseui.general.callback.ResultCallBack;
import io.agora.baseui.general.net.Resource;
import io.agora.chatroom.general.net.HttpManager;
import tools.ValueCallBack;
import tools.bean.VRMicListBean;
import tools.bean.VRoomUserBean;

public class ChatroomHandsRepository extends BaseRepository{

    public LiveData<Resource<VRMicListBean>> getRaisedList(Context context, String roomId, int pageSize, String cursor) {
        return new NetworkOnlyResource<VRMicListBean>() {
            @Override
            protected void createCall(@NonNull ResultCallBack<LiveData<VRMicListBean>> callBack) {
                HttpManager.getInstance(context).getApplyMicList(roomId, pageSize, cursor, new ValueCallBack<VRMicListBean>() {
                    @Override
                    public void onSuccess(VRMicListBean var1) {
                        callBack.onSuccess(createLiveData(var1));
                    }

                    @Override
                    public void onError(int code, String desc) {
                        callBack.onError(code,desc);
                    }
                });
            }
        }.asLiveData();
    }

    public LiveData<Resource<VRoomUserBean>> getInvitedList(Context context, String roomId, int pageSize, String cursor) {
        return new NetworkOnlyResource<VRoomUserBean>() {
            @Override
            protected void createCall(@NonNull ResultCallBack<LiveData<VRoomUserBean>> callBack) {
                HttpManager.getInstance(context).getRoomMembers(roomId, pageSize, cursor, new ValueCallBack<VRoomUserBean>() {
                    @Override
                    public void onSuccess(VRoomUserBean var1) {
                        callBack.onSuccess(createLiveData(var1));
                    }

                    @Override
                    public void onError(int code, String desc) {
                        callBack.onError(code,desc);
                    }
                });
            }
        }.asLiveData();
    }
}
