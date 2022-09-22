package io.agora.config

/**
 * @author create by zhangwei03
 */
object ConfigConstants {

    //--------房间类型 start--------
    const val Common_Chatroom = 0
    const val Spatial_Chatroom = 1
    //--------房间类型 end--------

    //--------音效类型 start--------
    const val Social_Chat = 1
    const val Karaoke = 2
    const val Gaming_Buddy = 3
    const val Professional_Broadcaster = 4
    //--------音效类型 end--------

    //--------AI 降噪开关 start--------
    const val ANIS_Unknown = -1
    const val ANIS_ON = 0
    const val ANIS_None = 1
    //--------AI 降噪开关 end--------

    //--------AI 降噪模式 start--------
    const val ANIS_High = 0
    const val ANIS_Medium = 1
    const val ANIS_Off = 2
    //--------AI 降噪模式 end--------
}