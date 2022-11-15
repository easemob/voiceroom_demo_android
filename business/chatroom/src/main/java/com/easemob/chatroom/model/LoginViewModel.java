package com.easemob.chatroom.model;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.json.JSONException;

import com.easemob.baseui.general.net.Resource;
import com.easemob.chatroom.general.livedatas.SingleSourceLiveData;
import com.easemob.chatroom.general.repositories.LoginRepository;
import com.easemob.chatroom.general.repositories.ProfileManager;
import com.hyphenate.chat.EMClient;

import tools.bean.VRUserBean;


public class LoginViewModel extends AndroidViewModel {
    private final Context mContext;
    private LoginRepository mRepository;
    private SingleSourceLiveData<Resource<VRUserBean>> loginObservable;
    private SingleSourceLiveData<Resource<String>> verificationCodeObservable;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mContext = application.getApplicationContext();
        mRepository = new LoginRepository();
        loginObservable = new SingleSourceLiveData<>();
        verificationCodeObservable =  new SingleSourceLiveData<>();
    }


    public SingleSourceLiveData<Resource<VRUserBean>> getLoginObservable(){
        return loginObservable;
    }

    public LiveData<Resource<String>> getVerificationCodeObservable(){
        return verificationCodeObservable;
    }

    public void loginFromServer(){
        String device = getDeviceId();
        String portrait = "";
        Log.e("LoginFromServer"," device: "+device);
        VRUserBean userBean = ProfileManager.getInstance().getProfile();
        if (userBean != null){
            portrait = userBean.getPortrait();
        }
        loginObservable.setSource(mRepository.login(mContext,device,portrait));
    }

    public void loginFromServer(String phoneNumber,String code){
        String portrait = "";
        VRUserBean userBean = ProfileManager.getInstance().getProfile();
        if (userBean != null){
            portrait = userBean.getPortrait();
        }
        loginObservable.setSource(mRepository.login(mContext,getDeviceId(),phoneNumber,code,portrait));
    }

    /**
     * 获取短信验证码
     */
    public void postVerificationCode(String phoneNumber){
        verificationCodeObservable.setSource(mRepository.getVerificationCode(phoneNumber,getDeviceId()));
    }

    private String getDeviceId(){
        String device = "";
        try {
            device = EMClient.getInstance().getDeviceInfo().getString("deviceid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return device;
    }

}
