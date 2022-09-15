package io.agora.chatroom.model;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.json.JSONException;

import io.agora.baseui.general.net.Resource;
import io.agora.chat.ChatClient;
import io.agora.chatroom.general.livedatas.SingleSourceLiveData;
import io.agora.chatroom.general.repositories.LoginRepository;
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

    public void LoginFromServer(Context context){
        try {
           String device = ChatClient.getInstance().getDeviceInfo().getString("deviceid");
           Log.e("LoginFromServer"," device: "+device);
           loginObservable.setSource(mRepository.login(context,device));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
