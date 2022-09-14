package io.agora.chatroom

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

class ChatroomApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ARouter.init(this)
    }
}