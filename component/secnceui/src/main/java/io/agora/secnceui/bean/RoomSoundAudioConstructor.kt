package io.agora.secnceui.bean

import io.agora.buddy.tool.ResourcesTools
import io.agora.secnceui.annotation.SoundAudioType
import io.agora.secnceui.annotation.SoundSpeakerType

/**
 * @author create by zhangwei03
 */
object RoomSoundAudioConstructor {

    // https://accktvpic.oss-cn-beijing.aliyuncs.com/pic/meta/demo/fulldemochat/01CreateRoomCommonChatroom/CN/01-01-B-CN.wav

    private const val BASE_URL = "https://accktvpic.oss-cn-beijing.aliyuncs.com/pic/meta/demo/fulldemochat"

    private const val CN = "CN"
    private const val EN = "EN"
    private const val CreateCommonRoom = "/01CreateRoomCommonChatroom%1\$s"
    private const val CreateSpatialRoom = "/02CeateRoomSpaticalChatroom%1\$s"

    private const val CreateCommonRoomSoundId = 100
    private const val CreateSpatialRoomSoundId = 200

    val soundAudioMap: MutableMap<Int, List<SoundAudioBean>> by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        mutableMapOf(
            SoundAudioType.CreateCommonRoom to mutableListOf<SoundAudioBean>().apply {
                if ((ResourcesTools.getIsZh())) {
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBlue, CreateCommonRoomSoundId + 1, getCreateCommonRoomUrl("/CN/01-01-B-CN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotRed, CreateCommonRoomSoundId + 2, getCreateCommonRoomUrl("/CN/01-02-R-CN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBoth, CreateCommonRoomSoundId + 3, getCreateCommonRoomUrl("/CN/01-03-B&R-CN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBlue, CreateCommonRoomSoundId + 4, getCreateCommonRoomUrl("/CN/01-04-B-CN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotRed, CreateCommonRoomSoundId + 5, getCreateCommonRoomUrl("/CN/01-05-R-CN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBlue, CreateCommonRoomSoundId + 6, getCreateCommonRoomUrl("/CN/01-06-B-CN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotRed, CreateCommonRoomSoundId + 7, getCreateCommonRoomUrl("/CN/01-07-R-CN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBlue, CreateCommonRoomSoundId + 8, getCreateCommonRoomUrl("/CN/01-08-B-CN.wav")
                        )
                    )
                } else {
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBlue, CreateCommonRoomSoundId + 1, getCreateCommonRoomUrl("/EN/01-01-B-EN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotRed, CreateCommonRoomSoundId + 2, getCreateCommonRoomUrl("/EN/01-02-R-EN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBoth, CreateCommonRoomSoundId + 3, getCreateCommonRoomUrl("/EN/01-03-B&R-EN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBlue, CreateCommonRoomSoundId + 4, getCreateCommonRoomUrl("/EN/01-04-B-EN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotRed, CreateCommonRoomSoundId + 5, getCreateCommonRoomUrl("/EN/01-05-R-EN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBlue, CreateCommonRoomSoundId + 6, getCreateCommonRoomUrl("/EN/01-06-B-EN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotRed, CreateCommonRoomSoundId + 7, getCreateCommonRoomUrl("/EN/01-07-R-EN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBlue, CreateCommonRoomSoundId + 8, getCreateCommonRoomUrl("/EN/01-08-B-EN.wav")
                        )
                    )
                }
            },
            SoundAudioType.CreateSpatialRoom to mutableListOf<SoundAudioBean>().apply {
                if ((ResourcesTools.getIsZh())) {
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBlue, CreateSpatialRoomSoundId + 1, getCreateSpatialRoomUrl("/CN/02-01-B-CN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotRed, CreateSpatialRoomSoundId + 2, getCreateSpatialRoomUrl("/CN/02-02-R-CN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBoth, CreateSpatialRoomSoundId + 3, getCreateSpatialRoomUrl("/CN/02-03-B&R-CN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBlue, CreateSpatialRoomSoundId + 4, getCreateSpatialRoomUrl("/CN/02-04-B-CN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotRed, CreateSpatialRoomSoundId + 5, getCreateSpatialRoomUrl("/CN/02-05-R-CN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBlue, CreateSpatialRoomSoundId + 6, getCreateSpatialRoomUrl("/CN/02-06-B-CN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotRed, CreateSpatialRoomSoundId + 7, getCreateSpatialRoomUrl("/CN/02-07-R-CN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBlue, CreateSpatialRoomSoundId + 8, getCreateSpatialRoomUrl("/CN/02-08-B-CN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotRed, CreateSpatialRoomSoundId + 9, getCreateSpatialRoomUrl("/CN/02-09-R-CN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBlue, CreateSpatialRoomSoundId + 10, getCreateSpatialRoomUrl("/CN/02-10-B-CN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotRed, CreateSpatialRoomSoundId + 11, getCreateSpatialRoomUrl("/CN/02-11-R-CN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBlue, CreateSpatialRoomSoundId + 12, getCreateSpatialRoomUrl("/CN/02-12-B-CN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotRed, CreateSpatialRoomSoundId + 13, getCreateSpatialRoomUrl("/CN/02-13-R-CN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBlue, CreateSpatialRoomSoundId + 14, getCreateSpatialRoomUrl("/CN/02-14-B-CN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotRed, CreateSpatialRoomSoundId + 15, getCreateSpatialRoomUrl("/CN/02-15-R-CN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBlue, CreateSpatialRoomSoundId + 16, getCreateSpatialRoomUrl("/CN/02-16-B-CN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotRed, CreateSpatialRoomSoundId + 17, getCreateSpatialRoomUrl("/CN/02-17-R-CN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBlue, CreateSpatialRoomSoundId + 18, getCreateSpatialRoomUrl("/CN/02-18-B-CN.wav")
                        )
                    )
                } else {
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBlue, CreateSpatialRoomSoundId + 1, getCreateSpatialRoomUrl("/EN/02-01-B-EN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotRed, CreateSpatialRoomSoundId + 2, getCreateSpatialRoomUrl("/EN/02-02-R-EN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBoth, CreateSpatialRoomSoundId + 3, getCreateSpatialRoomUrl("/EN/02-03-B&R-EN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBlue, CreateSpatialRoomSoundId + 4, getCreateSpatialRoomUrl("/EN/02-04-B-EN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotRed, CreateSpatialRoomSoundId + 5, getCreateSpatialRoomUrl("/EN/02-05-R-EN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBlue, CreateSpatialRoomSoundId + 6, getCreateSpatialRoomUrl("/EN/02-06-B-EN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotRed, CreateSpatialRoomSoundId + 7, getCreateSpatialRoomUrl("/EN/02-07-R-EN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBlue, CreateSpatialRoomSoundId + 8, getCreateSpatialRoomUrl("/EN/02-08-B-EN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotRed, CreateSpatialRoomSoundId + 9, getCreateSpatialRoomUrl("/EN/02-09-R-EN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBlue, CreateSpatialRoomSoundId + 10, getCreateSpatialRoomUrl("/EN/02-10-B-EN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotRed, CreateSpatialRoomSoundId + 11, getCreateSpatialRoomUrl("/EN/02-11-R-EN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBlue, CreateSpatialRoomSoundId + 12, getCreateSpatialRoomUrl("/EN/02-12-B-EN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotRed, CreateSpatialRoomSoundId + 13, getCreateSpatialRoomUrl("/EN/02-13-R-EN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBlue, CreateSpatialRoomSoundId + 14, getCreateSpatialRoomUrl("/EN/02-14-B-EN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotRed, CreateSpatialRoomSoundId + 15, getCreateSpatialRoomUrl("/EN/02-15-R-EN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBlue, CreateSpatialRoomSoundId + 16, getCreateSpatialRoomUrl("/EN/02-16-B-EN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotRed, CreateSpatialRoomSoundId + 17, getCreateSpatialRoomUrl("/EN/02-17-R-EN.wav")
                        )
                    )
                    add(
                        SoundAudioBean(
                            SoundSpeakerType.BotBlue, CreateSpatialRoomSoundId + 18, getCreateSpatialRoomUrl("/EN/02-18-B-EN.wav")
                        )
                    )
                }
            },
        )
    }

    private fun getCreateCommonRoomUrl(audioPath: String): String {
        return BASE_URL + String.format(CreateCommonRoom, audioPath)
    }

    private fun getCreateSpatialRoomUrl(audioPath: String): String {
        return BASE_URL + String.format(CreateSpatialRoom, audioPath)
    }
}