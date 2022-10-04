package io.agora.chatroom;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

import io.agora.chatroom.general.interfaceOrImplement.UserActivityLifecycleCallbacks;
import manager.ChatroomConfigManager;

public class ChatroomApplication extends Application {
    private static ChatroomApplication instance;
    private UserActivityLifecycleCallbacks mLifecycleCallbacks = new UserActivityLifecycleCallbacks();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ARouter.init(this);
        ChatroomConfigManager.getInstance().initRoomConfig(this);
        registerActivityLifecycleCallbacks();
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
