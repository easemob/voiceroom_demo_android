package io.agora.chatroom

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alibaba.android.arouter.launcher.ARouter
import io.agora.baseui.BaseUiActivity
import io.agora.chatroom.databinding.ActivityChatroomSplashBinding
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
        Handler(Looper.getMainLooper()).postDelayed(Runnable { initSplashPage() }, SPLASH_DELAYED)
    }

    private fun initSplashPage() {
        ARouter.getInstance().build(ChatroomTestActivity.PATH).navigation()
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