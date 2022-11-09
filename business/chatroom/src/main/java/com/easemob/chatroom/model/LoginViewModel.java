package com.easemob.chatroom.model;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.json.JSONException;

import com.easemob.baseui.general.net.Resource;
import io.agora.chat.ChatClient;
import com.easemob.chatroom.general.livedatas.SingleSourceLiveData;
import com.easemob.chatroom.general.repositories.LoginRepository;
import com.easemob.chatroom.general.repositories.ProfileManager;
import tools.bean.VRUserBean;


public class LoginViewModel extends AndroidViewModel {
    private SingleSourceLiveData<Resource<VRUserBean>> loginObservable;
    private LoginRepository mRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mRepository = new LoginRepository();
        loginObservable = new SingleSourceLiveData<>();
    }


    public SingleSourceLiveData<Resource<VRUserBean>> getLoginObservable(){
        return loginObservable;
    }

    public void loginFromServer(Context context){
        try {
           String device = ChatClient.getInstance().getDeviceInfo().getString("deviceid");
           String portrait = "";
           Log.e("LoginFromServer"," device: "+device);
            VRUserBean userBean = ProfileManager.getInstance().getProfile();
            if (userBean != null){
                 portrait = userBean.getPortrait();
            }
           loginObservable.setSource(mRepository.login(context,device,portrait));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
