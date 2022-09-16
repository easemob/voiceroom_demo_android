package io.agora.chatroom.activity

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter
import io.agora.baseui.BaseUiActivity
import io.agora.baseui.general.callback.OnResourceParseCallback
import io.agora.chatroom.ChatroomDataTestManager
import io.agora.chatroom.databinding.ActivityChatroomSplashBinding
import io.agora.chatroom.general.repositories.ProfileManager
import io.agora.chatroom.model.ChatroomViewModel
import io.agora.chatroom.model.LoginViewModel
import io.agora.config.RouterPath
import manager.ChatroomConfigManager
import tools.bean.VRUserBean
import java.util.*


class ChatroomSplashActivity : BaseUiActivity<ActivityChatroomSplashBinding>() {

    companion object {
        const val SPLASH_DELAYED = 1500L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initListeners()
        if (isZh(this)) {
            binding.mtChatroom.letterSpacing = 0.42f
        } else {
            binding.mtChatroom.letterSpacing = -0.05f
        }
        ChatroomConfigManager.getInstance().initRoomConfig(this);
        //测试假数据
        val roomViewModel: ChatroomViewModel = ViewModelProvider(this)[ChatroomViewModel::class.java]
        ChatroomDataTestManager.getInstance().setRoomListData(this, roomViewModel)

        val loginViewModel: LoginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        loginViewModel.loginObservable.observe(this) { response ->
            parseResource(response, object : OnResourceParseCallback<VRUserBean?>(true) {
                override fun onSuccess(data: VRUserBean?) {
                    ProfileManager.getInstance().profile = data
                }
            })
        }
        loginViewModel.LoginFromServer(this)
        Handler(Looper.getMainLooper()).postDelayed(Runnable { initSplashPage() }, SPLASH_DELAYED)
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

    private fun isZh(context: Context): Boolean {
        val locale: Locale = context.resources.configuration.locale
        val language: String = locale.language
        return language.endsWith("zh")
    }
}