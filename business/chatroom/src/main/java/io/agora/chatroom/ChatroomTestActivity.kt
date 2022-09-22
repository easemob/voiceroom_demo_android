package io.agora.chatroom

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import io.agora.baseui.BaseUiActivity
import io.agora.chatroom.databinding.ActivityChatroomTestBinding
import io.agora.config.RouterPath

// test
@Route(path = ChatroomTestActivity.PATH)
class ChatroomTestActivity : BaseUiActivity<ActivityChatroomTestBinding>() {
    companion object {
        const val PATH = "/chatroom/chatroomSplashActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.mbStartChatroom.setOnClickListener {
            ARouter.getInstance()
                .build(RouterPath.ChatroomPath)
                .navigation()
        }
        binding.mbStartChatroom3D.setOnClickListener {
            ARouter.getInstance()
                .build(RouterPath.ChatroomPath)
                .navigation()
        }
    }

    override fun getViewBinding(inflater: LayoutInflater): ActivityChatroomTestBinding {
        return ActivityChatroomTestBinding.inflate(inflater)
    }
}