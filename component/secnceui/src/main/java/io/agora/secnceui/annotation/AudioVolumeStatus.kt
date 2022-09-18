package io.agora.secnceui.annotation

import androidx.annotation.IntDef

/**
 * 音量大小
 */
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef(
    AudioVolumeStatus.None,
    AudioVolumeStatus.Low,
    AudioVolumeStatus.Middle,
    AudioVolumeStatus.High,
    AudioVolumeStatus.Max,
    AudioVolumeStatus.Mute,
)
annotation class AudioVolumeStatus {

    companion object {

        const val None = 0

        const val Low = 1

        const val Middle = 2

        const val High = 3

        const val Max = 4

        const val Mute = 5
    }
}

