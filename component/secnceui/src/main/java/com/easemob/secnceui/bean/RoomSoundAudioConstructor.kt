package com.easemob.secnceui.bean

import com.easemob.buddy.tool.ResourcesTools
import com.easemob.config.ConfigConstants

/**
 * @author create by zhangwei03
 */
object RoomSoundAudioConstructor {

    // https://accktvpic.oss-cn-beijing.aliyuncs.com/pic/meta/demo/fulldemochat/aisound/01CreateRoomCommonChatroom/CN/01-01-B-CN.wav
    private const val BASE_URL = "https://accktvpic.oss-cn-beijing.aliyuncs.com/pic/meta/demo/fulldemochat/aisound"

    private const val CN = "CN"
    private const val EN = "EN"
    private const val CreateCommonRoom = "/01CreateRoomCommonChatroom%1\$s"
    private const val CreateSpatialRoom = "/02CeateRoomSpaticalChatroom%1\$s"
    private const val SocialChat = "/03SoundSelectionSocialChat%1\$s"
    private const val Karaoke = "/04SoundSelectionKaraoke%1\$s"
    private const val GamingBuddy = "/05SoundSelectionGamingBuddy%1\$s"
    private const val ProfessionalBroadcaster = "/06SoundProfessionalBroadcaster%1\$s"
    private const val AINSIntroduce = "/07AINSIntroduce%1\$s"
    //AI噪⾳ 08AINSTVSound/CN/High/08-01-B-CN-High.wav  1./08AINSTVSound/CN/High 2./08-01-B 3.CN 4.High.wav
    private const val AINSSound = "%1\$s%2\$s-%3\$s-%4\$s"
    private const val AINS_TVSound = "/08AINSTVSound" // 电视噪⾳
    private const val AINS_KitchenSound = "/09AINSKitchenSound" //厨房噪⾳
    private const val AINS_StreetSound = "/10AINStreetSound" //街道噪⾳
    private const val AINS_MachineSound = "/11AINSRobotSound" //机器噪⾳
    private const val AINS_OfficeSound = "/12AINSOfficeSound" //办公室噪⾳
    private const val AINS_HomeSound = "/13AINSHomeSound" //家庭噪⾳
    private const val AINS_ConstructionSound = "/14AINSConstructionSound" //装修噪⾳
    private const val AINS_AlertSound = "/15AINSAlertSound" //提示⾳/音乐
    private const val AINS_ApplauseSound = "/16AINSApplause" //鼓掌声
    private const val AINS_WindSound = "/17AINSWindSound" //风燥
    private const val AINS_MicPopFilterSound = "/18AINSMicPopFilter" //喷⻨
    private const val AINS_AudioFeedback = "/19AINSAudioFeedback" //啸叫
    private const val AINS_MicrophoneFingerRub = "/20ANISMicrophoneFingerRubSound" //玩⼿机时⼿指摩擦⻨克⻛
    private const val AINS_MicrophoneScreenTap = "/21ANISScreenTapSound" //玩⼿机时⼿指敲击屏幕


    private const val CreateCommonRoomSoundId = 100
    private const val CreateSpatialRoomSoundId = 200
    private const val SocialChatSoundId = 300
    private const val KaraokeSoundId = 400
    private const val GamingBuddySoundId = 500
    private const val ProfessionalBroadcasterSoundId = 600
    private const val AINSIntroduceSoundId = 700
    private const val AINSSoundId = 800

    /**
     * 新房间创建欢迎语料
     */
    val createRoomSoundAudioMap: Map<Int, List<com.easemob.secnceui.bean.SoundAudioBean>> by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        mutableMapOf(
            ConfigConstants.RoomType.Common_Chatroom to
                    if (ResourcesTools.getIsZh()) mutableListOf(
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateCommonRoomSoundId + 1,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateCommonRoomUrl(
                                "/CN/01-01-B-CN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateCommonRoomSoundId + 2,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateCommonRoomUrl(
                                "/CN/01-02-R-CN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBoth,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateCommonRoomSoundId + 3,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateCommonRoomUrl(
                                "/CN/01-03-B&R-CN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateCommonRoomSoundId + 4,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateCommonRoomUrl(
                                "/CN/01-04-B-CN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateCommonRoomSoundId + 5,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateCommonRoomUrl(
                                "/CN/01-05-R-CN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateCommonRoomSoundId + 6,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateCommonRoomUrl(
                                "/CN/01-06-B-CN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateCommonRoomSoundId + 7,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateCommonRoomUrl(
                                "/CN/01-07-R-CN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateCommonRoomSoundId + 8,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateCommonRoomUrl(
                                "/CN/01-08-B-CN.wav"
                            )
                        )
                    )
                    else mutableListOf(
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateCommonRoomSoundId + 1,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateCommonRoomUrl(
                                "/EN/01-01-B-EN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateCommonRoomSoundId + 2,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateCommonRoomUrl(
                                "/EN/01-02-R-EN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBoth,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateCommonRoomSoundId + 3,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateCommonRoomUrl(
                                "/EN/01-03-B&R-EN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateCommonRoomSoundId + 4,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateCommonRoomUrl(
                                "/EN/01-04-B-EN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateCommonRoomSoundId + 5,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateCommonRoomUrl(
                                "/EN/01-05-R-EN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateCommonRoomSoundId + 6,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateCommonRoomUrl(
                                "/EN/01-06-B-EN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateCommonRoomSoundId + 7,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateCommonRoomUrl(
                                "/EN/01-07-R-EN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateCommonRoomSoundId + 8,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateCommonRoomUrl(
                                "/EN/01-08-B-EN.wav"
                            )
                        )
                    ),
            ConfigConstants.RoomType.Spatial_Chatroom to
                    if (ResourcesTools.getIsZh()) mutableListOf(
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 1,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/CN/02-01-B-CN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 2,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/CN/02-02-R-CN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBoth,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 3,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/CN/02-03-B&R-CN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 4,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/CN/02-04-B-CN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 5,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/CN/02-05-R-CN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 6,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/CN/02-06-B-CN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 7,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/CN/02-07-R-CN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 8,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/CN/02-08-B-CN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 9,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/CN/02-09-R-CN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 10,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/CN/02-10-B-CN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 11,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/CN/02-11-R-CN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 12,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/CN/02-12-B-CN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 13,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/CN/02-13-R-CN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 14,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/CN/02-14-B-CN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 15,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/CN/02-15-R-CN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 16,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/CN/02-16-B-CN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 17,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/CN/02-17-R-CN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 18,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/CN/02-18-B-CN.wav"
                            )
                        )
                    )
                    else mutableListOf(
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 1,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/EN/02-01-B-EN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 2,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/EN/02-02-R-EN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBoth,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 3,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/EN/02-03-B&R-EN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 4,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/EN/02-04-B-EN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 5,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/EN/02-05-R-EN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 6,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/EN/02-06-B-EN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 7,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/EN/02-07-R-EN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 8,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/EN/02-08-B-EN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 9,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/EN/02-09-R-EN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 10,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/EN/02-10-B-EN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 11,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/EN/02-11-R-EN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 12,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/EN/02-12-B-EN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 13,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/EN/02-13-R-EN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 14,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/EN/02-14-B-EN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 15,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/EN/02-15-R-EN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 16,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/EN/02-16-B-EN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 17,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/EN/02-17-R-EN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoomSoundId + 18,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getCreateSpatialRoomUrl(
                                "/EN/02-18-B-EN.wav"
                            )
                        )
                    )
        )
    }

    /**
     * 最佳音效语料
     */
    val soundSelectionAudioMap: Map<Int, List<com.easemob.secnceui.bean.SoundAudioBean>> by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        mutableMapOf(
            ConfigConstants.SoundSelection.Social_Chat to
                    if (ResourcesTools.getIsZh()) mutableListOf(
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.SocialChatSoundId + 1,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getSocialChatUrl("/CN/03-01-B-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.SocialChatSoundId + 2,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getSocialChatUrl("/CN/03-02-R-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.SocialChatSoundId + 3,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getSocialChatUrl("/CN/03-03-B-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.SocialChatSoundId + 4,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getSocialChatUrl("/CN/03-04-R-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.SocialChatSoundId + 5,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getSocialChatUrl("/CN/03-05-B-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.SocialChatSoundId + 6,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getSocialChatUrl("/CN/03-06-R-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.SocialChatSoundId + 7,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getSocialChatUrl("/CN/03-07-B-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.SocialChatSoundId + 8,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getSocialChatUrl("/CN/03-08-R-CN.wav")
                        )
                    )
                    else mutableListOf(
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.SocialChatSoundId + 1,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getSocialChatUrl("/EN/03-01-B-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.SocialChatSoundId + 2,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getSocialChatUrl("/EN/03-02-R-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.SocialChatSoundId + 3,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getSocialChatUrl("/EN/03-03-B-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.SocialChatSoundId + 4,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getSocialChatUrl("/EN/03-04-R-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.SocialChatSoundId + 5,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getSocialChatUrl("/EN/03-05-B-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.SocialChatSoundId + 6,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getSocialChatUrl("/EN/03-06-R-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.SocialChatSoundId + 7,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getSocialChatUrl("/EN/03-07-B-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.SocialChatSoundId + 8,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getSocialChatUrl("/EN/03-08-R-EN.wav")
                        ),
                    ),
            ConfigConstants.SoundSelection.Karaoke to
                    if (ResourcesTools.getIsZh()) mutableListOf(
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.KaraokeSoundId + 1,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getKaraokeUrl("/CN/04-01-B-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.KaraokeSoundId + 2,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getKaraokeUrl("/CN/04-02-B-CN.wav")
                        ),
                    )
                    else mutableListOf(
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.KaraokeSoundId + 1,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getKaraokeUrl("/EN/04-01-B-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.KaraokeSoundId + 2,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getKaraokeUrl("/EN/04-02-B-EN.wav")
                        )
                    ),
            ConfigConstants.SoundSelection.Gaming_Buddy to
                    if (ResourcesTools.getIsZh()) mutableListOf(
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.GamingBuddySoundId + 1,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getGamingBuddyUrl("/CN/05-01-B-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.GamingBuddySoundId + 2,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getGamingBuddyUrl("/CN/05-02-R-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.GamingBuddySoundId + 3,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getGamingBuddyUrl("/CN/05-03-B-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.GamingBuddySoundId + 4,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getGamingBuddyUrl("/CN/05-04&05-R-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.GamingBuddySoundId + 6,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getGamingBuddyUrl("/CN/05-06-B-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.GamingBuddySoundId + 7,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getGamingBuddyUrl("/CN/05-07-R-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.GamingBuddySoundId + 8,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getGamingBuddyUrl("/CN/05-08-B-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.GamingBuddySoundId + 9,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getGamingBuddyUrl("/CN/05-09-R-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.GamingBuddySoundId + 10,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getGamingBuddyUrl("/CN/05-10-B-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.GamingBuddySoundId + 11,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getGamingBuddyUrl("/CN/05-11-R-CN.wav")
                        ),
                    )
                    else mutableListOf(
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.GamingBuddySoundId + 1,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getGamingBuddyUrl("/EN/05-01-B-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.GamingBuddySoundId + 2,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getGamingBuddyUrl("/EN/05-02-R-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.GamingBuddySoundId + 3,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getGamingBuddyUrl("/EN/05-03-B-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.GamingBuddySoundId + 4,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getGamingBuddyUrl("/EN/05-04-R-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.GamingBuddySoundId + 5,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getGamingBuddyUrl("/EN/05-05-R-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.GamingBuddySoundId + 6,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getGamingBuddyUrl("/EN/05-06-B-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.GamingBuddySoundId + 7,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getGamingBuddyUrl("/EN/05-07-R-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.GamingBuddySoundId + 8,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getGamingBuddyUrl("/EN/05-08-B-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.GamingBuddySoundId + 9,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getGamingBuddyUrl("/EN/05-09-R-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.GamingBuddySoundId + 10,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getGamingBuddyUrl("/EN/05-10-B-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.GamingBuddySoundId + 11,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getGamingBuddyUrl("/EN/05-11-R-EN.wav")
                        ),
                    ),
            ConfigConstants.SoundSelection.Professional_Broadcaster to
                    if (ResourcesTools.getIsZh()) mutableListOf(
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.ProfessionalBroadcasterSoundId + 1,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getProfessionalBroadcasterUrl(
                                "/CN/06-01-R-CN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.ProfessionalBroadcasterSoundId + 2,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getProfessionalBroadcasterUrl(
                                "/CN/06-02-R-CN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.ProfessionalBroadcasterSoundId + 3,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getProfessionalBroadcasterUrl(
                                "/CN/06-03-R-CN.wav"
                            )
                        ),
                    )
                    else mutableListOf(
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.ProfessionalBroadcasterSoundId + 1,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getProfessionalBroadcasterUrl(
                                "/EN/06-01-R-EN.wav"
                            )
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.ProfessionalBroadcasterSoundId + 2,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getProfessionalBroadcasterUrl(
                                "/EN/06-02-R-EN.wav"
                            )
                        ),
                    )
        )
    }

    /**
     * AI 降噪开关讲解语料
     */
    val anisIntroduceAudioMap: Map<Int, List<com.easemob.secnceui.bean.SoundAudioBean>> by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        mutableMapOf(
            ConfigConstants.AINSMode.AINS_High to
                    if (ResourcesTools.getIsZh()) mutableListOf(
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 1,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/CN/Share/07-01-R-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 2,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/CN/High/07-02-B-CN-High.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 3,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/CN/High/07-03-R-CN-High.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 4,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/CN/Share/07-04-B-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 5,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/CN/Share/07-05-R-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 6,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/CN/Share/07-06-B-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 7,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/CN/Share/07-07-R-CN.wav")
                        )
                    )
                    else mutableListOf(
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 1,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/EN/Share/07-01-R-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 2,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/EN/High/07-02-B-EN-High.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 3,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/EN/High/07-03-R-EN-High.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 4,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/EN/Share/07-04-B-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 5,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/EN/Share/07-05-R-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 6,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/EN/Share/07-06-B-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 7,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/EN/Share/07-07-R-EN.wav")
                        )
                    ),
            ConfigConstants.AINSMode.AINS_Medium to
                    if (ResourcesTools.getIsZh()) mutableListOf(
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 1,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/CN/Share/07-01-R-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 2,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/CN/Medium/07-02-B-CN-Medium.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 3,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/CN/Medium/07-03-R-CN-Medium.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 4,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/CN/Share/07-04-B-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 5,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/CN/Share/07-05-R-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 6,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/CN/Share/07-06-B-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 7,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/CN/Share/07-07-R-CN.wav")
                        )
                    )
                    else mutableListOf(
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 1,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/EN/Share/07-01-R-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 2,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/EN/Medium/07-02-B-EN-Medium.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 3,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/EN/Medium/07-03-R-EN-Medium.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 4,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/EN/Share/07-04-B-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 5,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/EN/Share/07-05-R-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 6,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/EN/Share/07-06-B-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 7,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/EN/Share/07-07-R-EN.wav")
                        )
                    ),
            ConfigConstants.AINSMode.AINS_Off to
                    if (ResourcesTools.getIsZh()) mutableListOf(
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 1,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/CN/Share/07-01-R-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 2,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/CN/None/07-02-B-CN-None.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 3,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/CN/None/07-03-R-CN-None.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 4,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/CN/Share/07-04-B-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 5,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/CN/Share/07-05-R-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 6,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/CN/Share/07-06-B-CN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 7,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/CN/Share/07-07-R-CN.wav")
                        )
                    )
                    else mutableListOf(
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 1,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/EN/Share/07-01-R-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 2,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/EN/07-02-B-EN-None.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 3,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/EN/07-03-R-EN-None.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 4,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/EN/Share/07-04-B-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 5,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/EN/Share/07-05-R-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotBlue,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 6,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/EN/Share/07-06-B-EN.wav")
                        ),
                        com.easemob.secnceui.bean.SoundAudioBean(
                            ConfigConstants.BotSpeaker.BotRed,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduceSoundId + 7,
                            com.easemob.secnceui.bean.RoomSoundAudioConstructor.getANISIntroduceUrl("/EN/Share/07-07-R-EN.wav")
                        )
                    )
        )


    }

    /**
     * AI 降噪14种语料
     */
    val AINSSoundMap: Map<Int, com.easemob.secnceui.bean.SoundAudioBean> by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        mutableMapOf(
            ConfigConstants.AINSSoundType.AINS_TVSound to com.easemob.secnceui.bean.SoundAudioBean(
                ConfigConstants.BotSpeaker.BotBlue,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSSoundId + 1,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_TVSound,
                    "/08-01-B",
                    ConfigConstants.AINSMode.AINS_Off
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_TVSound,
                    "/08-01-B",
                    ConfigConstants.AINSMode.AINS_High
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_TVSound,
                    "/08-01-B",
                    ConfigConstants.AINSMode.AINS_Medium
                )
            ),
            ConfigConstants.AINSSoundType.AINS_KitchenSound to com.easemob.secnceui.bean.SoundAudioBean(
                ConfigConstants.BotSpeaker.BotBlue,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSSoundId + 2,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_KitchenSound,
                    "/09-01-B",
                    ConfigConstants.AINSMode.AINS_Off
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_KitchenSound,
                    "/09-01-B",
                    ConfigConstants.AINSMode.AINS_High
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_KitchenSound,
                    "/09-01-B",
                    ConfigConstants.AINSMode.AINS_Medium
                )
            ),
            ConfigConstants.AINSSoundType.AINS_StreetSound to com.easemob.secnceui.bean.SoundAudioBean(
                ConfigConstants.BotSpeaker.BotBlue,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSSoundId + 3,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_StreetSound,
                    "/10-01-B",
                    ConfigConstants.AINSMode.AINS_Off
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_StreetSound,
                    "/10-01-B",
                    ConfigConstants.AINSMode.AINS_High
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_StreetSound,
                    "/10-01-B",
                    ConfigConstants.AINSMode.AINS_Medium
                )
            ),
            ConfigConstants.AINSSoundType.AINS_MachineSound to com.easemob.secnceui.bean.SoundAudioBean(
                ConfigConstants.BotSpeaker.BotBlue,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSSoundId + 4,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_MachineSound,
                    "/11-01-B",
                    ConfigConstants.AINSMode.AINS_Off
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_MachineSound,
                    "/11-01-B",
                    ConfigConstants.AINSMode.AINS_High
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_MachineSound,
                    "/11-01-B",
                    ConfigConstants.AINSMode.AINS_Medium
                )
            ),
            ConfigConstants.AINSSoundType.AINS_OfficeSound to com.easemob.secnceui.bean.SoundAudioBean(
                ConfigConstants.BotSpeaker.BotBlue,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSSoundId + 5,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_OfficeSound,
                    "/12-01-B",
                    ConfigConstants.AINSMode.AINS_Off
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_OfficeSound,
                    "/12-01-B",
                    ConfigConstants.AINSMode.AINS_High
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_OfficeSound,
                    "/12-01-B",
                    ConfigConstants.AINSMode.AINS_Medium
                )
            ),
            ConfigConstants.AINSSoundType.AINS_HomeSound to com.easemob.secnceui.bean.SoundAudioBean(
                ConfigConstants.BotSpeaker.BotBlue,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSSoundId + 6,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_HomeSound,
                    "/13-01-B",
                    ConfigConstants.AINSMode.AINS_Off
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_HomeSound,
                    "/13-01-B",
                    ConfigConstants.AINSMode.AINS_High
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_HomeSound,
                    "/13-01-B",
                    ConfigConstants.AINSMode.AINS_Medium
                )
            ),
            ConfigConstants.AINSSoundType.AINS_ConstructionSound to com.easemob.secnceui.bean.SoundAudioBean(
                ConfigConstants.BotSpeaker.BotBlue,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSSoundId + 7,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_ConstructionSound,
                    "/14-01-B",
                    ConfigConstants.AINSMode.AINS_Off
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_ConstructionSound,
                    "/14-01-B",
                    ConfigConstants.AINSMode.AINS_High
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_ConstructionSound,
                    "/14-01-B",
                    ConfigConstants.AINSMode.AINS_Medium
                )
            ),
            ConfigConstants.AINSSoundType.AINS_AlertSound to com.easemob.secnceui.bean.SoundAudioBean(
                ConfigConstants.BotSpeaker.BotBlue,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSSoundId + 8,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_AlertSound,
                    "/15-01-B",
                    ConfigConstants.AINSMode.AINS_Off
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_AlertSound,
                    "/15-01-B",
                    ConfigConstants.AINSMode.AINS_High
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_AlertSound,
                    "/15-01-B",
                    ConfigConstants.AINSMode.AINS_Medium
                )
            ),
            ConfigConstants.AINSSoundType.AINS_ApplauseSound to com.easemob.secnceui.bean.SoundAudioBean(
                ConfigConstants.BotSpeaker.BotBlue,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSSoundId + 9,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_ApplauseSound,
                    "/16-01-B",
                    ConfigConstants.AINSMode.AINS_Off
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_ApplauseSound,
                    "/16-01-B",
                    ConfigConstants.AINSMode.AINS_High
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_ApplauseSound,
                    "/16-01-B",
                    ConfigConstants.AINSMode.AINS_Medium
                )
            ),
            ConfigConstants.AINSSoundType.AINS_WindSound to com.easemob.secnceui.bean.SoundAudioBean(
                ConfigConstants.BotSpeaker.BotBlue,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSSoundId + 10,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_WindSound,
                    "/17-01-B",
                    ConfigConstants.AINSMode.AINS_Off
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_WindSound,
                    "/17-01-B",
                    ConfigConstants.AINSMode.AINS_High
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_WindSound,
                    "/17-01-B",
                    ConfigConstants.AINSMode.AINS_Medium
                )
            ),
            ConfigConstants.AINSSoundType.AINS_MicPopFilterSound to com.easemob.secnceui.bean.SoundAudioBean(
                ConfigConstants.BotSpeaker.BotBlue,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSSoundId + 11,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_MicPopFilterSound,
                    "/18-01-B",
                    ConfigConstants.AINSMode.AINS_Off
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_MicPopFilterSound,
                    "/18-01-B",
                    ConfigConstants.AINSMode.AINS_High
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_MicPopFilterSound,
                    "/18-01-B",
                    ConfigConstants.AINSMode.AINS_Medium
                )
            ),
            ConfigConstants.AINSSoundType.AINS_AudioFeedback to com.easemob.secnceui.bean.SoundAudioBean(
                ConfigConstants.BotSpeaker.BotBlue,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSSoundId + 12,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_AudioFeedback,
                    "/19-01-B",
                    ConfigConstants.AINSMode.AINS_Off
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_AudioFeedback,
                    "/19-01-B",
                    ConfigConstants.AINSMode.AINS_High
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_AudioFeedback,
                    "/19-01-B",
                    ConfigConstants.AINSMode.AINS_Medium
                )
            ),
            ConfigConstants.AINSSoundType.AINS_MicrophoneFingerRub to com.easemob.secnceui.bean.SoundAudioBean(
                ConfigConstants.BotSpeaker.BotBlue,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSSoundId + 13,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_MicrophoneFingerRub,
                    "/20-01-B",
                    ConfigConstants.AINSMode.AINS_Off
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_MicrophoneFingerRub,
                    "/20-01-B",
                    ConfigConstants.AINSMode.AINS_High
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_MicrophoneFingerRub,
                    "/20-01-B",
                    ConfigConstants.AINSMode.AINS_Medium
                )
            ),
            ConfigConstants.AINSSoundType.AINS_MicrophoneScreenTap to com.easemob.secnceui.bean.SoundAudioBean(
                ConfigConstants.BotSpeaker.BotBlue,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSSoundId + 14,
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_MicrophoneScreenTap,
                    "/21-01-B",
                    ConfigConstants.AINSMode.AINS_Off
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_MicrophoneScreenTap,
                    "/21-01-B",
                    ConfigConstants.AINSMode.AINS_High
                ),
                com.easemob.secnceui.bean.RoomSoundAudioConstructor.getAINSSoundUrl(
                    com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINS_MicrophoneScreenTap,
                    "/21-01-B",
                    ConfigConstants.AINSMode.AINS_Medium
                )
            ),
        )
    }

    private fun getCreateCommonRoomUrl(audioPath: String): String {
        return com.easemob.secnceui.bean.RoomSoundAudioConstructor.BASE_URL + String.format(com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateCommonRoom, audioPath)
    }

    private fun getCreateSpatialRoomUrl(audioPath: String): String {
        return com.easemob.secnceui.bean.RoomSoundAudioConstructor.BASE_URL + String.format(com.easemob.secnceui.bean.RoomSoundAudioConstructor.CreateSpatialRoom, audioPath)
    }

    private fun getSocialChatUrl(audioPath: String): String {
        return com.easemob.secnceui.bean.RoomSoundAudioConstructor.BASE_URL + String.format(com.easemob.secnceui.bean.RoomSoundAudioConstructor.SocialChat, audioPath)
    }

    private fun getKaraokeUrl(audioPath: String): String {
        return com.easemob.secnceui.bean.RoomSoundAudioConstructor.BASE_URL + String.format(com.easemob.secnceui.bean.RoomSoundAudioConstructor.Karaoke, audioPath)
    }

    private fun getGamingBuddyUrl(audioPath: String): String {
        return com.easemob.secnceui.bean.RoomSoundAudioConstructor.BASE_URL + String.format(com.easemob.secnceui.bean.RoomSoundAudioConstructor.GamingBuddy, audioPath)
    }

    private fun getProfessionalBroadcasterUrl(audioPath: String): String {
        return com.easemob.secnceui.bean.RoomSoundAudioConstructor.BASE_URL + String.format(com.easemob.secnceui.bean.RoomSoundAudioConstructor.ProfessionalBroadcaster, audioPath)
    }

    private fun getANISIntroduceUrl(audioPath: String): String {
        return com.easemob.secnceui.bean.RoomSoundAudioConstructor.BASE_URL + String.format(com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSIntroduce, audioPath)
    }

    private fun getAINSSoundUrl(audioPathPrefix: String, audioPath: String, ainsMode: Int): String {
        val local = if (ResourcesTools.getIsZh()) com.easemob.secnceui.bean.RoomSoundAudioConstructor.CN else com.easemob.secnceui.bean.RoomSoundAudioConstructor.EN

        val audioPathP = when (ainsMode) {
            ConfigConstants.AINSMode.AINS_High -> "$audioPathPrefix/$local/High"
            ConfigConstants.AINSMode.AINS_Medium -> "$audioPathPrefix/$local/Medium"
            else -> "$audioPathPrefix/$local/None"
        }
        val ainsPath = when (ainsMode) {
            ConfigConstants.AINSMode.AINS_High -> "High.wav"
            ConfigConstants.AINSMode.AINS_Medium -> "Medium.wav"
            else -> "None.wav"
        }
        //AI噪⾳ 08AINSTVSound/CN/High/08-01-B-CN-High.wav  1./08AINSTVSound 2.CN 3.high 4.08-01-B 5.CN 6.High
        return com.easemob.secnceui.bean.RoomSoundAudioConstructor.BASE_URL + String.format(com.easemob.secnceui.bean.RoomSoundAudioConstructor.AINSSound,audioPathP, audioPath, local, ainsPath)
    }
}