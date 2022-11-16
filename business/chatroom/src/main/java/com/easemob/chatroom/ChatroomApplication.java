package com.easemob.chatroom;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGASoundManager;
import com.opensource.svgaplayer.utils.log.SVGALogger;

import http.VRRequestApi;

import com.easemob.chatroom.general.interfaceOrImplement.UserActivityLifecycleCallbacks;
import manager.ChatroomConfigManager;

public class ChatroomApplication extends Application {
    private static ChatroomApplication instance;
    private UserActivityLifecycleCallbacks mLifecycleCallbacks = new UserActivityLifecycleCallbacks();

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
//        CrashReport.initCrashReport(this, "baed12f146", false);
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
}