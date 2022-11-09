package io.agora.chatroom.ui.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import io.agora.baseui.BaseUiActivity
import io.agora.buddy.basic.BasicReceiver
import java.util.*

abstract class BaseUiObserverActivity<B : ViewBinding> : BaseUiActivity<B>(), Observer {

    private val receivers: MutableList<BasicReceiver> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onRegisterReceiver().forEach {
            it.addObserver(this)
            receivers.add(it)
        }
    }

    protected abstract fun onRegisterReceiver(): MutableList<BasicReceiver>

    override fun update(observable: Observable?, arg: Any?) {

    }

    override fun onDestroy() {
        super.onDestroy()
        onUnRegisterReceiver()
    }

    @Synchronized
    private fun onUnRegisterReceiver() {
        receivers.forEach {
            it.deleteObservers()
        }
        receivers.clear()
    }
}