package io.agora.buddy.basic

import io.agora.buddy.event.ActionEvent
import io.agora.buddy.event.ReceiveEvent
import java.util.*

abstract class BasicReceiver : Observable() {

    abstract fun name(): String

    abstract fun action(event: ActionEvent)

    open fun handleReceiveEvent(event: ReceiveEvent) {
        setChanged()
        notifyObservers(event)
    }
}