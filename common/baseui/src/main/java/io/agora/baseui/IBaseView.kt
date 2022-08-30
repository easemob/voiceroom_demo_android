package io.agora.baseui

import androidx.lifecycle.LifecycleOwner

interface IBaseView : LifecycleOwner {

    fun showLoading()

    fun dismissLoading()
}