package io.agora.rtckit.core.channel

import io.agora.rtckit.core.RtcChannelConfig
import io.agora.rtckit.core.RtcInitConfig
import io.agora.rtckit.core.engine.RtcBaseAudioEngine
import io.agora.rtckit.core.engine.RtcBaseEngine
import io.agora.rtckit.core.listener.IRtcServiceListener

abstract class RtcBaseChannel<T> {

    protected var rtcClient: T? = null
    protected var listener: IRtcServiceListener? = null

    private val engineMap = HashMap<Class<*>, RtcBaseEngine<*>?>()

    abstract fun init(config: RtcInitConfig, listener: IRtcServiceListener)

    abstract fun joinChannel(config: RtcChannelConfig)

    abstract fun initAudioEngine(): RtcBaseAudioEngine<T>?

    fun getAudioEngine(): RtcBaseAudioEngine<T>? {
        var engine = engineMap[RtcBaseAudioEngine::class.java]
        if (engine == null) {
            engine = initAudioEngine()
            engine?.attach(rtcClient, listener)
            engineMap[RtcBaseAudioEngine::class.java] = engine
        }
        return engine as RtcBaseAudioEngine<T>?
    }

    open fun destroy() {
        this.rtcClient = null
        engineMap.forEach {
            it.value?.detach()
        }
        engineMap.clear()
        this.listener = null
    }
}