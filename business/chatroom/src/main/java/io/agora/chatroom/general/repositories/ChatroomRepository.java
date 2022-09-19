package io.agora.chatroom.general.repositories;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import java.util.List;
import io.agora.baseui.general.callback.ResultCallBack;
import io.agora.baseui.general.net.Resource;
import io.agora.chatroom.general.net.HttpManager;
import tools.ValueCallBack;
import tools.bean.VRoomBean;
import tools.bean.VRoomInfoBean;

public class ChatroomRepository extends BaseRepository {

    public LiveData<Resource<List<VRoomBean.RoomsBean>>> getRoomList(Context context,int pageSize,int type) {
        return new NetworkOnlyResource<List<VRoomBean.RoomsBean>>() {
            @Override
            protected void createCall(@NonNull ResultCallBack<LiveData<List<VRoomBean.RoomsBean>>> callBack) {
                HttpManager.getInstance(context).getRoomFromServer(pageSize, type, new ValueCallBack<List<VRoomBean.RoomsBean>>() {
                    @Override
                    public void onSuccess(List<VRoomBean.RoomsBean> bean) {
//                        Log.e("ChatroomRepository","getRoomList: " + bean.size());
                        callBack.onSuccess(createLiveData(bean));
                    }

                    @Override
                    public void onError(int code, String desc) {
                        callBack.onError(code,desc);
                    }
                });
            }
        }.asLiveData();
    }


    public LiveData<Resource<VRoomInfoBean>> getRoomInfo(Context context,String roomId) {
        return new NetworkOnlyResource<VRoomInfoBean>() {
            @Override
            protected void createCall(@NonNull ResultCallBack<LiveData<VRoomInfoBean>> callBack) {
                HttpManager.getInstance(context).getRoomDetails(roomId, new ValueCallBack<VRoomInfoBean>() {
                    @Override
                    public void onSuccess(VRoomInfoBean data) {
                        callBack.onSuccess(createLiveData(data));
                    }

                    @Override
                    public void onError(int code, String desc) {
                        callBack.onError(code,desc);
                    }
                });
            }
        }.asLiveData();
    }

    public LiveData<Resource<Boolean>> joinRoom(Context context,String roomId,String password) {
        return new NetworkOnlyResource<Boolean>() {
            @Override
            protected void createCall(@NonNull ResultCallBack<LiveData<Boolean>> callBack) {
                HttpManager.getInstance(context).joinRoom(roomId,password ,new ValueCallBack<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        callBack.onSuccess(createLiveData(data));
                    }

                    @Override
                    public void onError(int code, String desc) {
                        callBack.onError(code,desc);
                    }
                });
            }

        }.asLiveData();
    }

    public LiveData<Resource<Boolean>> leaveRoom(Context context, String roomId) {
        return new NetworkOnlyResource<Boolean>() {
            @Override
            protected void createCall(@NonNull ResultCallBack<LiveData<Boolean>> callBack) {
                HttpManager.getInstance(context).leaveRoom(roomId, new ValueCallBack<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        callBack.onSuccess(createLiveData(data));
                    }

                    @Override
                    public void onError(int code, String desc) {
                        callBack.onError(code, desc);
                    }
                });
            }

        }.asLiveData();
    }

}
