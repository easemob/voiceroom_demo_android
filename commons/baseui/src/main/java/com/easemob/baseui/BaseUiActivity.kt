package com.easemob.baseui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import com.easemob.baseui.interfaces.IParserSource
import com.easemob.buddy.tool.logE

abstract class BaseUiActivity<B : ViewBinding> : AppCompatActivity(), IParserSource {
    lateinit var binding: B

    private var loadingDialog: AlertDialog? = null

    open fun showLoading(cancelable: Boolean) {
        if (loadingDialog == null) {
            loadingDialog = AlertDialog.Builder(this).setView(R.layout.view_base_loading).create().apply {
               // 背景修改成透明
                window?.decorView?.setBackgroundColor(Color.TRANSPARENT)
            }
        }
        loadingDialog?.setCancelable(cancelable)
        loadingDialog?.show()
    }

    open fun dismissLoading() {
        loadingDialog?.dismiss()
    }

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
     * set titleText Style
     * @param view textView
     * @param type Typeface {NORMAL, BOLD, ITALIC, BOLD_ITALIC}
     */
    open fun setTextStyle(view: TextView?, type: Int) {
        if (null != view && type >= 0 && type <= 3) view.setTypeface(null, type)
    }
}