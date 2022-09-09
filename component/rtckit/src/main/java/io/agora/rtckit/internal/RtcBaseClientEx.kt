package io.agora.rtckit.internal

import android.content.Context
import io.agora.rtckit.open.config.RtcInitConfig
import io.agora.rtckit.constants.RtcKitConstant
import io.agora.rtckit.internal.base.*
import io.agora.rtckit.middle.IRtcMiddleServiceListener

internal abstract class RtcBaseClientEx<T> {

    companion object {
        const val TAG = "${RtcKitConstant.TAG_PREFIX} RtcBaseClientEx"
    }

    protected var rtcEngine: T? = null
    protected var rtcListener: IRtcClientListener? = null

    private val engineMap = HashMap<Class<*>, RtcBaseEngine<*>?>()

    /**创建rtc*/
    abstract fun createClient(
        context: Context,
        initConfig: RtcInitConfig,
        middleServiceListener: IRtcMiddleServiceListener
    ): T?

    /**创建频道管理引擎*/
    abstract fun createChannelEngine(): RtcBaseChannelEngine<T>?

    /**创建音频管理引擎*/
    abstract fun createAudioEngine(): RtcBaseAudioEngine<T>?

    /**创建AI降噪管理引擎*/
    abstract fun createDeNoiseEngine(): RtcBaseDeNoiseEngine<T>?

    /**创建音效管理引擎*/
    abstract fun createSoundEffectEngine(): RtcBaseSoundEffectEngine<T>?

    /**创建空间音频管理引擎*/
    abstract fun createSpatialAudioEngine(): RtcBaseSpatialAudioEngine<T>?

    fun getChannelEngine(): RtcBaseChannelEngine<T>? {
        var engine = engineMap[RtcBaseChannelEngine::class.java]
        if (engine == null) {
            engine = createChannelEngine()
            engine?.attach(rtcEngine, rtcListener)
            engineMap[RtcBaseChannelEngine::class.java] = engine
        }
        return engine as RtcBaseChannelEngine<T>?
    }

    fun getAudioEngine(): RtcBaseAudioEngine<T>? {
        var engine = engineMap[RtcBaseAudioEngine::class.java]
        if (engine == null) {
            engine = createAudioEngine()
            engine?.attach(rtcEngine, rtcListener)
            engineMap[RtcBaseAudioEngine::class.java] = engine
        }
        return engine as RtcBaseAudioEngine<T>?
    }

    fun getDeNoiseEngine(): RtcBaseDeNoiseEngine<T>? {
        var engine = engineMap[RtcBaseDeNoiseEngine::class.java]
        if (engine == null) {
            engine = createDeNoiseEngine()
            engine?.attach(rtcEngine, rtcListener)
            engineMap[RtcBaseDeNoiseEngine::class.java] = engine
        }
        return engine as RtcBaseDeNoiseEngine<T>?
    }

    fun getSoundEffectEngine(): RtcBaseSoundEffectEngine<T>? {
        var engine = engineMap[RtcBaseSoundEffectEngine::class.java]
        if (engine == null) {
            engine = createSoundEffectEngine()
            engine?.attach(rtcEngine, rtcListener)
            engineMap[RtcBaseSoundEffectEngine::class.java] = engine
        }
        return engine as RtcBaseSoundEffectEngine<T>?
    }

    fun getSpatialAudioEngine(): RtcBaseSpatialAudioEngine<T>? {
        var engine = engineMap[RtcBaseSpatialAudioEngine::class.java]
        if (engine == null) {
            engine = createSpatialAudioEngine()
            engine?.attach(rtcEngine, rtcListener)
            engineMap[RtcBaseSpatialAudioEngine::class.java] = engine
        }
        return engine as RtcBaseSpatialAudioEngine<T>?
    }


    open fun destroy() {
        this.rtcEngine = null
        engineMap.forEach {
            it.value?.detach()
        }
        engineMap.clear()
        this.rtcListener = null
    }
}