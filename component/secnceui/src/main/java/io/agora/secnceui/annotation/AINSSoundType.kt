package io.agora.secnceui.annotation

import androidx.annotation.IntDef
import io.agora.config.ConfigConstants

/**
 * 降噪开启/关闭
 */
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef(AINSSoundType.AINS, AINSSoundType.None,AINSSoundType.Unknown)
annotation class AINSSoundType {
    companion object {
        // 有降噪试听
        const val AINS = ConfigConstants.ANIS_ON

        // 无降噪试听
        const val None = ConfigConstants.ANIS_None

        // 默认，没有试听
        const val Unknown = ConfigConstants.ANIS_Unknown
    }
}
