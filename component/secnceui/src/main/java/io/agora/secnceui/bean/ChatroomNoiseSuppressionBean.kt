package io.agora.secnceui.bean

import androidx.annotation.IntDef

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@IntDef(AINSModeType.High, AINSModeType.Medium, AINSModeType.Off)
annotation class AINSModeType {
    companion object {
        const val High = 0

        const val Medium = 1

        const val Off = 2
    }
}

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@IntDef(AINSSoundType.AINS, AINSSoundType.None)
annotation class AINSSoundType {
    companion object {
        const val AINS = 0

        const val None = 1
    }
}

data class AINSModeBean(
    val anisName: String = "",
    @AINSModeType val anisType: Int = AINSModeType.Off
) : BaseChatroomBean

data class AINSSoundsBean(
    val soundName: String = "",
    val soundSubName: String = "",
    @AINSSoundType val soundsType: Int = AINSSoundType.None
) : BaseChatroomBean