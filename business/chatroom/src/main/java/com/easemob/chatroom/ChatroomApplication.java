package com.easemob.chatroom;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.easemob.chatroom.general.net.ChatroomHttpManager;
import com.easemob.chatroom.general.repositories.ProfileManager;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.EMLog;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGASoundManager;
import com.opensource.svgaplayer.utils.log.SVGALogger;

import http.VRRequestApi;

import com.easemob.chatroom.general.interfaceOrImplement.UserActivityLifecycleCallbacks;

import org.json.JSONException;

import manager.ChatroomConfigManager;
import manager.ChatroomHelper;
import tools.ValueCallBack;
import tools.bean.VRUserBean;

public class ChatroomApplication extends Application {
    private static ChatroomApplication instance;
    private UserActivityLifecycleCallbacks mLifecycleCallbacks = new UserActivityLifecycleCallbacks();
    private ChatConnectionListener connectionListener;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ARouter.init(this);
        ChatroomConfigManager.getInstance().initRoomConfig(this, BuildConfig.im_app_key);
        VRRequestApi.get().setBaseUrl(BuildConfig.server_host);
        registerActivityLifecycleCallbacks();
        SVGAParser.Companion.shareParser().init(this);
        SVGALogger.INSTANCE.setLogEnabled(true);
        SVGASoundManager.INSTANCE.init();
        connectionListener = new ChatConnectionListener();
        EMClient.getInstance().addConnectionListener(connectionListener);
    }

    public Context getAppContext(){
        return instance;
    }

    private void registerActivityLifecycleCallbacks() {
        this.registerActivityLifecycleCallbacks(mLifecycleCallbacks);
    }

    public static ChatroomApplication getInstance() {
        return instance;
    }

    public UserActivityLifecycleCallbacks getLifecycleCallbacks() {
        return mLifecycleCallbacks;
    }

    private static class ChatConnectionListener implements EMConnectionListener {

        @Override
        public void onConnected() {

        }

        @Override
        public void onDisconnected(int errorCode) {

        }

        @Override
        public void onTokenExpired() {
            refreshToken();
        }

        @Override
        public void onTokenWillExpire() {
            refreshToken();
        }
    }

    private static void refreshToken(){
        VRUserBean vrUserBean =  ProfileManager.getInstance().getProfile();
        if (vrUserBean != null && !TextUtils.isEmpty(vrUserBean.getChat_uid()) &&
                !TextUtils.isEmpty(vrUserBean.getIm_token()) ){
            ChatroomHttpManager.getInstance().refreshToken(vrUserBean.getChat_uid(), getDeviceId(), vrUserBean.getIm_token(), new ValueCallBack<VRUserBean>() {
                @Override
                public void onSuccess(VRUserBean bean) {
                    ProfileManager.getInstance().clear();
                    ProfileManager.getInstance().setProfile(bean);
                }

                @Override
                public void onError(int code, String desc) {
                    EMLog.d("refreshToken","onError: " + code + " " + desc);
                }
            });
        }


    }

    private static String getDeviceId(){
        String device = "";
        try {
            device = EMClient.getInstance().getDeviceInfo().getString("deviceid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return device;
    }
}
