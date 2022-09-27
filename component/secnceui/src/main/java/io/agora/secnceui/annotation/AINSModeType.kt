package io.agora.secnceui.annotation

import androidx.annotation.IntDef
import io.agora.config.ConfigConstants

/**
 * 降噪等级
 */
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef(AINSModeType.High, AINSModeType.Medium, AINSModeType.Off)
annotation class AINSModeType {
    companion object {
        const val High = ConfigConstants.ANIS_High

        const val Medium = ConfigConstants.ANIS_Medium

        const val Off = ConfigConstants.ANIS_Off
    }
}
