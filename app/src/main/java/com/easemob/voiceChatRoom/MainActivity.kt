package com.easemob.voiceChatRoom

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.launcher.ARouter
import com.easemob.baseui.BaseUiActivity
import com.easemob.config.RouterPath
import com.easemob.voiceChatRoom.databinding.ActivityMainBinding

class MainActivity : BaseUiActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.mbStartChatroom.setOnClickListener {
            ARouter.getInstance()
                .build(RouterPath.ChatroomPath)
                .navigation()
        }
        binding.mbStart3dChatroom.setOnClickListener {
            ARouter.getInstance()
                .build(RouterPath.ChatroomPath)
                .navigation()
        }
    }

    override fun getViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }
}