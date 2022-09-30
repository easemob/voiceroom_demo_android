package io.agora.secnceui.constants

object ScenesConstant {
    const val KeyMic0 = "mic_0"
    const val KeyMic1 = "mic_1"
    const val KeyMic2 = "mic_2"
    const val KeyMic3 = "mic_3"
    const val KeyMic4 = "mic_4"
    const val KeyMic5 = "mic_5"
    const val KeyMic6 = "mic_6"
    const val KeyMic7 = "mic_7"

    val micMap: MutableMap<String, Int> by lazy {
        mutableMapOf(
            KeyMic0 to 0,
            KeyMic1 to 1,
            KeyMic2 to 2,
            KeyMic3 to 3,
            KeyMic4 to 4,
            KeyMic5 to 5,
            KeyMic6 to 6,
            KeyMic6 to 7
        )
    }

    const val ENABLE_ALPHA = 1.0f
    const val DISABLE_ALPHA = 0.4f

    const val DefaultAvatar = "avatar1"
}