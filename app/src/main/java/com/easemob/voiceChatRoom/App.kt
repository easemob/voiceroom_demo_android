package com.easemob.voiceChatRoom

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        ARouter.openDebug()
        ARouter.openLog()
        ARouter.init(this)
//        ChatroomConfigManager.getInstance().initRoomConfig(this)
    }
}