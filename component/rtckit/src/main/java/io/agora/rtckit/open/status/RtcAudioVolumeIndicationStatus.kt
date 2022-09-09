package io.agora.rtckit.open.status

/**
 * @author create by zhangwei03
 *
 * 用户音量提示回调。
 */
class RtcAudioVolumeIndicationStatus(
    val speakers: Array<RtcAudioVolumeInfo>? = null,
    val totalVolume: Int = 0,
) {

}

data class RtcAudioVolumeInfo(
    var uid: Int = 0,
    var volume: Int = 0,
    var vad: Int = 0,
    var voicePitch: Double = 0.0,
)