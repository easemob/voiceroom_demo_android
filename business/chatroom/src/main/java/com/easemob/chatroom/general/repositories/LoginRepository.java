package com.easemob.chatroom.general.repositories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.easemob.chatroom.general.net.ChatroomHttpManager;

import com.easemob.baseui.general.callback.ResultCallBack;
import com.easemob.baseui.general.net.Resource;
import tools.ValueCallBack;
import tools.bean.VRUserBean;

public class LoginRepository extends BaseRepository {

    public LiveData<Resource<VRUserBean>> login(Context context,String deviceId,String avatar) {
        return new NetworkOnlyResource<VRUserBean>() {
            @Override
            protected void createCall(@NonNull ResultCallBack<LiveData<VRUserBean>> callBack) {
                ChatroomHttpManager.getInstance().loginWithToken(deviceId,avatar,new ValueCallBack<VRUserBean>() {
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

    public LiveData<Resource<VRUserBean>> login(Context context,String deviceId,String phoneNumber,String code,String avatar) {
        return new NetworkOnlyResource<VRUserBean>() {
            @Override
            protected void createCall(@NonNull ResultCallBack<LiveData<VRUserBean>> callBack) {
                ChatroomHttpManager.getInstance().loginWithToken(deviceId,avatar,new ValueCallBack<VRUserBean>() {
                    @Override
                    public void onSuccess(VRUserBean value) {
                        runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onSuccess(createLiveData(value));
                            }
                        });
                    }

                    @Override
                    public void onError(int error, String errorMsg) {

                    }
                });
            }
        }.asLiveData();
    }

    public LiveData<Resource<String>> getVerificationCode(String phoneNumber,String deviceId){
        return new NetworkOnlyResource<String>() {
            @Override
            protected void createCall(@NonNull ResultCallBack<LiveData<String>> callBack) {
                runOnMainThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }.asLiveData();
    }


}
