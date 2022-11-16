package com.easemob.buddy.basic

import com.easemob.buddy.event.ActionEvent
import com.easemob.buddy.event.ReceiveEvent
import java.util.*

abstract class BasicReceiver : Observable() {

    abstract fun name(): String

    abstract fun action(event: ActionEvent)

    open fun handleReceiveEvent(event: ReceiveEvent) {
        setChanged()
        notifyObservers(event)
    }
}