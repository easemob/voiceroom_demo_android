package io.agora.sencedemo

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.launcher.ARouter
import io.agora.baseui.BaseUiActivity
import io.agora.config.ConfigConstants
import io.agora.config.RouterParams
import io.agora.config.RouterPath
import io.agora.sencedemo.databinding.ActivityMainBinding

class MainActivity : BaseUiActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.mbStartChatroom.setOnClickListener {
            ARouter.getInstance()
                .build(RouterPath.ChatroomPath)
                .withInt(RouterParams.KEY_CHATROOM_TYPE, ConfigConstants.Common_Chatroom)
                .navigation()
        }
        binding.mbStart3dChatroom.setOnClickListener {
            ARouter.getInstance()
                .build(RouterPath.ChatroomPath)
                .withInt(RouterParams.KEY_CHATROOM_TYPE, ConfigConstants.Spatial_Chatroom)
                .navigation()
        }
    }

    override fun getViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }
}