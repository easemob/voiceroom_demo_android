package io.agora.chatroom

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.launcher.ARouter
import io.agora.baseui.BaseUiActivity
import io.agora.chatroom.databinding.ActivityChatroomTestBinding
import io.agora.config.ARouterPath

class ChatroomTestActivity: BaseUiActivity<ActivityChatroomTestBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.mbStartChatroom.setOnClickListener {
            ARouter.getInstance().build(ARouterPath.ChatroomPath).navigation();
        }
    }

    override fun getViewBinding(inflater: LayoutInflater): ActivityChatroomTestBinding {
        return ActivityChatroomTestBinding.inflate(inflater)
    }
}