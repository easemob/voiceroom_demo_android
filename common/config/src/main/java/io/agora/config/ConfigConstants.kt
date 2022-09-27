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

    //--------AI 机器人 start--------
    const val Speaker_Bot_Blue = 0 // 机器人小蓝
    const val Speaker_Bot_Red = 1 // 机器人小兰
    const val Speaker_Bot_Both = 2 // 两个机器人一起播放
    //--------AI 降噪模式 end--------

    //--------语聊出现时机 start--------
    const val Audio_Create_Common_Room = 1 // 新房间创建欢迎语-普通房间
    const val Audio_Create_Spatial_Room = 2 // 新房间创建欢迎语-空间⾳频房间
    const val Audio_Sound_Social_Chat = 3 // 最佳⾳效推流-陌⽣⼈社交+语聊房
    const val Audio_Sound_Karaoke = 4 // 最佳⾳效推流-K歌
    const val Audio_Sound_Gaming_Buddy = 5 // 最佳⾳效推流-游戏陪玩
    const val Audio_Sound_Professional_Broadcaster = 6 // 最佳⾳效推流-主播声卡⾼⾳质
    const val Audio_ANIS_Switch = 7 // AI降噪-切换AI降噪开关讲解语料
    //--------语聊出现时机 end--------

    //--------音量大小 start--------
    const val Volume_Unknown = -1
    const val Volume_None = 0
    const val Volume_Low = 1
    const val Volume_Medium = 2
    const val Volume_High = 3
    const val Volume_Max = 4
    //--------音量大小 end--------
}