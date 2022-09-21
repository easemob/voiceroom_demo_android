package io.agora.secnceui.annotation

import androidx.annotation.IntDef

/**
 * 降噪开启/关闭
 */
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef(AINSSoundType.AINS, AINSSoundType.None,AINSSoundType.Unknown)
annotation class AINSSoundType {
    companion object {
        // 有降噪试听
        const val AINS = 0

        // 无降噪试听
        const val None = 1

        // 默认，没有试听
        const val Unknown = -1
    }
}
