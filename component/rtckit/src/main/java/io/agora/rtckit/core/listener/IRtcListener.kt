package io.agora.rtckit.core.listener

import android.view.SurfaceView
import androidx.annotation.IntDef

interface IChatroomRtcListener {

    fun onStart()
    fun onSuccess()
    fun onUserJoin(roomId: String, userId: String, surfaceView: SurfaceView)
    fun onUserLeave(roomId: String, userId: String)
    fun onError(code: Int, msg: String)
    fun onFirstLocalAudioFrame(elapsed: Int)
    fun onAudioChangeStatus(audioChangeStatus: RtcChangeStatus)
    fun onAudioStatistic(audioStatistic: SSARtcStatistic)
    fun onDeviceStatistic(deviceStatistic: SSARtcDeviceStatistic)
    fun onNetWorkStatus(netWorkStatus: SSARtcNetWorkStatus)
}

sealed class RtcChangeStatus {

    var userId = ""
    var enabled = false

    constructor(userId: String, enabled: Boolean) {
        this.userId = userId
        this.enabled = enabled
    }

    class LocalChange(enabled: Boolean, userId: String) : RtcChangeStatus(userId, enabled)

    class MutedChange(enabled: Boolean, userId: String) : RtcChangeStatus(userId, enabled)

    class RemoteChange(enabled: Boolean, userId: String) : RtcChangeStatus(userId, enabled)
}

class SSARtcDeviceStatistic {
}

class SSARtcNetWorkStatus {

    var userId = ""

    //上行网络
    @RtcNetWorkStatus
    var txQuality = 0

    //下行网络
    @RtcNetWorkStatus
    var rxQuality = 0
}

class SSARtcStatistic {
    var userId = ""
    var fps = 0
    var bps = 0
}

@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef(
    RtcNetWorkStatus.QualityUnknown,
    RtcNetWorkStatus.QualityExcellent,
    RtcNetWorkStatus.QualityGood,
    RtcNetWorkStatus.QualityPoor,
    RtcNetWorkStatus.QualityBad,
    RtcNetWorkStatus.QualityVBad,
    RtcNetWorkStatus.QualityDown,
    RtcNetWorkStatus.QualityUnsupported,
    RtcNetWorkStatus.QualityDetecting
)
annotation class RtcNetWorkStatus {

    companion object {

        /** The network quality is unknown. */
        const val QualityUnknown = 0

        /**  The network quality is excellent. */
        const val QualityExcellent = 1

        /** The network quality is quite good, but the bitrate may be slightly lower than excellent. */
        const val QualityGood = 2

        /** Users can feel the communication slightly impaired. */
        const val QualityPoor = 3

        /** Users can communicate only not very smoothly. */
        const val QualityBad = 4

        /** The network quality is so bad that users can hardly communicate. */
        const val QualityVBad = 5

        /** The network is disconnected and users cannot communicate at all. */
        const val QualityDown = 6

        /** Users cannot detect the network quality. (Not in use.) */
        const val QualityUnsupported = 7

        /** Detecting the network quality. */
        const val QualityDetecting = 8
    }
}



