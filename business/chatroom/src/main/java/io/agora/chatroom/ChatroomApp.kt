package io.agora.chatroom

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.opensource.svgaplayer.SVGAParser
import com.opensource.svgaplayer.utils.log.ILogger
import com.opensource.svgaplayer.utils.log.SVGALogger

class ChatroomApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ARouter.init(this)
        SVGAParser.shareParser().init(this)
        // 默认情况下，SVGA 内部不会输出任何 log，所以需要手动设置为 true
        SVGALogger.setLogEnabled(true)
    }
}