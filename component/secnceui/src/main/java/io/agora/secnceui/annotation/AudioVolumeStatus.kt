package io.agora.secnceui.annotation

import androidx.annotation.IntDef
import io.agora.config.ConfigConstants

/**
 * 音量大小
 */
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef(
    AudioVolumeStatus.Unknown,
    AudioVolumeStatus.None,
    AudioVolumeStatus.Low,
    AudioVolumeStatus.Medium,
    AudioVolumeStatus.High,
    AudioVolumeStatus.Max,
)
annotation class AudioVolumeStatus {

    companion object {
        const val Unknown = ConfigConstants.Volume_Unknown

        const val None = ConfigConstants.Volume_None

        const val Low = ConfigConstants.Volume_Low

        const val Medium = ConfigConstants.Volume_Medium

        const val High = ConfigConstants.Volume_High

        const val Max = ConfigConstants.Volume_Max
    }
}

