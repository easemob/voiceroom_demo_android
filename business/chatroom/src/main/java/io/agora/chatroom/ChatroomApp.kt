package io.agora.chatroom

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import manager.ChatroomConfigManager

class ChatroomApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ARouter.init(this)
        ChatroomConfigManager.getInstance().initRoomConfig(this)
    }
}