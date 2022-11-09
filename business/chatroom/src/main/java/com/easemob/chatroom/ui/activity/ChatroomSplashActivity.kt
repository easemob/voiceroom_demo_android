package com.easemob.chatroom.ui.activity

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alibaba.android.arouter.launcher.ARouter
import com.easemob.baseui.BaseUiActivity
import com.easemob.baseui.BaseUiTool
import com.easemob.baseui.general.callback.OnResourceParseCallback
import com.easemob.buddy.tool.ResourcesTools
import com.easemob.buddy.tool.ThreadManager
import com.easemob.chatroom.databinding.ActivityChatroomSplashBinding
import com.easemob.chatroom.general.repositories.ProfileManager
import com.easemob.chatroom.model.LoginViewModel
import com.easemob.config.RouterPath
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

        val startTime = SystemClock.elapsedRealtime()
        val loginViewModel: LoginViewModel = BaseUiTool.getViewModel(LoginViewModel::class.java, this)
        loginViewModel.loginObservable.observe(this) { response ->
            parseResource(response, object : OnResourceParseCallback<VRUserBean?>(true) {
                override fun onSuccess(data: VRUserBean?) {
                    ProfileManager.getInstance().profile = data
                    val interval = SystemClock.elapsedRealtime() - startTime
                    ThreadManager.getInstance().runOnMainThreadDelay({
                        initSplashPage()
                    }, (SPLASH_DELAYED - interval).toInt())
                }
            })
        }
        loginViewModel.loginFromServer(this)
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