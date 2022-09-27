package io.agora.baseui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import io.agora.baseui.interfaces.IParserSource
import io.agora.buddy.tool.logE

abstract class BaseUiActivity<B : ViewBinding> : AppCompatActivity(), IParserSource {
    lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = getViewBinding(layoutInflater)
        if (binding == null) {
            "Inflate Error".logE()
            finish()
        } else {
            this.binding = binding
            super.setContentView(this.binding.root)
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    protected abstract fun getViewBinding(inflater: LayoutInflater): B?

    fun <T : ViewModel> getViewModel(viewModelClass: Class<T>, owner: ViewModelStoreOwner): T {
        return ViewModelProvider(owner, ViewModelProvider.NewInstanceFactory())[viewModelClass]
    }

    fun <T : ViewModel> getViewModel(
        viewModelClass: Class<T>,
        factory: ViewModelProvider.NewInstanceFactory,
        owner: ViewModelStoreOwner
    ): T {
        return ViewModelProvider(owner, factory)[viewModelClass]
    }
}