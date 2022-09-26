package io.agora.chatroom.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alibaba.android.arouter.launcher.ARouter
import io.agora.baseui.BaseUiActivity
import io.agora.baseui.BaseUiTool
import io.agora.baseui.general.callback.OnResourceParseCallback
import io.agora.buddy.tool.ResourcesTools
import io.agora.chatroom.databinding.ActivityChatroomSplashBinding
import io.agora.chatroom.general.repositories.ProfileManager
import io.agora.chatroom.model.LoginViewModel
import io.agora.config.RouterPath
import tools.bean.VRUserBean

class ChatroomSplashActivity : BaseUiActivity<ActivityChatroomSplashBinding>() {

    companion object {
        const val SPLASH_DELAYED = 1500L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initListeners()
        if (ResourcesTools.isZh(this)) {
            binding.mtChatroom.letterSpacing = 0.42f
        } else {
            binding.mtChatroom.letterSpacing = -0.05f
        }

        val loginViewModel: LoginViewModel = BaseUiTool.getViewModel(LoginViewModel::class.java, this)
        loginViewModel.loginObservable.observe(this) { response ->
            parseResource(response, object : OnResourceParseCallback<VRUserBean?>(true) {
                override fun onSuccess(data: VRUserBean?) {
                    Log.e("loginViewModel", "onSuccess")
                    ProfileManager.getInstance().profile = data
                    initSplashPage()
                }
            })
        }
        loginViewModel.LoginFromServer(this)
    }

    private fun initSplashPage() {
        ARouter.getInstance().build(RouterPath.ChatroomListPath).navigation()
        finish()
    }

    override fun getViewBinding(inflater: LayoutInflater): ActivityChatroomSplashBinding {
        return ActivityChatroomSplashBinding.inflate(inflater)
    }

    private fun initListeners() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _: View?, insets: WindowInsetsCompat ->
            val inset = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.clRoot.setPaddingRelative(0, inset.top, 0, inset.bottom)
            WindowInsetsCompat.CONSUMED
        }
    }
}