package io.agora.chatroom.general.repositories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import io.agora.baseui.general.callback.ResultCallBack;
import io.agora.baseui.general.net.Resource;
import io.agora.chatroom.general.net.ChatroomHttpManager;
import tools.ValueCallBack;
import tools.bean.VRUserBean;

public class LoginRepository extends BaseRepository {

    public LiveData<Resource<VRUserBean>> login(Context context,String deviceId,String avatar) {
        return new NetworkOnlyResource<VRUserBean>() {
            @Override
            protected void createCall(@NonNull ResultCallBack<LiveData<VRUserBean>> callBack) {
                ChatroomHttpManager.getInstance(context).loginWithToken(deviceId,avatar,new ValueCallBack<VRUserBean>() {
                    @Override
                    public void onSuccess(VRUserBean value) {
                        callBack.onSuccess(createLiveData(value));
                    }

                    @Override
                    public void onError(int error, String errorMsg) {

                    }
                });
            }
        }.asLiveData();
    }


}
