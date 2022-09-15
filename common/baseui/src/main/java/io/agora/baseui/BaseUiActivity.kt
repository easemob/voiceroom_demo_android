package io.agora.baseui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import io.agora.baseui.general.callback.OnResourceParseCallback
import io.agora.baseui.general.enums.Status
import io.agora.baseui.general.net.Resource
import io.agora.buddy.tool.logE

abstract class BaseUiActivity<B : ViewBinding> : AppCompatActivity() {
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

    /**
     * Parse Resource<T>
     * @param response
     * @param callback
     * @param <T>
    </T></T> */
    fun <T> parseResource(response: Resource<T>?, callback: OnResourceParseCallback<T>) {
        if (response == null) {
            return
        }
        if (response.status === Status.SUCCESS) {
            callback.onHideLoading()
            callback.onSuccess(response.data)
        } else if (response.status === Status.ERROR) {
            callback.onHideLoading()
            if (!callback.hideErrorMsg) {
                Log.e("parseResource ", response.message)
            }
            callback.onError(response.errorCode, response.message)
        } else if (response.status === Status.LOADING) {
            callback.onLoading(response.data)
        }
    }
}