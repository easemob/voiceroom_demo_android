package io.agora.secnceui.annotation

import androidx.annotation.IntDef

/**
 * 降噪等级
 */
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef(AINSModeType.High, AINSModeType.Medium, AINSModeType.Off)
annotation class AINSModeType {
    companion object {
        const val High = 0

        const val Medium = 1

        const val Off = 2
    }
}
