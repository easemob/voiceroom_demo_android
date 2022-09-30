package io.agora.config

/**
 * @author create by zhangwei03
 */
object ConfigConstants {

    //--------房间类型 start--------
    object RoomType {
        const val Common_Chatroom = 0
        const val Spatial_Chatroom = 1
    }
    //--------房间类型 end--------

    //--------音效类型 start--------
    object SoundSelection {
        const val Social_Chat = 1
        const val Karaoke = 2
        const val Gaming_Buddy = 3
        const val Professional_Broadcaster = 4
    }
    //--------音效类型 end--------

    object SoundSelectionText {
        const val Social_Chat = "Social Chat"
        const val Karaoke = "Karaoke"
        const val Gaming_Buddy = "Gaming Buddy"
        const val Professional_Broadcaster = "Professional broadcaster"
    }

    //--------AI 降噪模式 start--------
    object AINSMode {
        const val AINS_High = 0
        const val AINS_Medium = 1
        const val AINS_Off = 2
        const val AINS_Unknown = -1
    }
    //--------AI 降噪模式 end--------

    //--------AI 机器人播放类型 start--------
    object BotSpeaker {
        const val BotBlue = 0 // 机器人小蓝
        const val BotRed = 1 // 机器人小兰
        const val BotBoth = 2 // 两个机器人一起播放
    }

    //--------AI 降噪模式 end--------

    //--------音量大小 start--------
    object VolumeType {
        const val Volume_Unknown = -1
        const val Volume_None = 0
        const val Volume_Low = 1
        const val Volume_Medium = 2
        const val Volume_High = 3
        const val Volume_Max = 4
    }
    //--------音量大小 end--------

    //--------AI 降噪-14种噪音试听 start--------
    object AINSSoundType {
        const val AINS_TVSound = 1 // 电视噪
        const val AINS_KitchenSound = 2 //厨房噪⾳
        const val AINS_StreetSound = 3 //街道噪⾳
        const val AINS_MachineSound = 4 //机器噪⾳
        const val AINS_OfficeSound = 5 //办公室噪⾳
        const val AINS_HomeSound = 6 //家庭噪⾳
        const val AINS_ConstructionSound = 7 //装修噪⾳
        const val AINS_AlertSound = 8 //提示⾳/音乐
        const val AINS_ApplauseSound = 9 //鼓掌声
        const val AINS_WindSound = 10 //风燥
        const val AINS_MicPopFilterSound = 11 //喷⻨
        const val AINS_AudioFeedback = 12 //啸叫
        const val AINS_MicrophoneFingerRub = 13 //玩⼿机时⼿指摩擦⻨克⻛
        const val AINS_MicrophoneScreenTap = 14 //玩⼿机时⼿指敲击屏幕
    }
    //--------AI 降噪-14种噪音试听 end--------
}