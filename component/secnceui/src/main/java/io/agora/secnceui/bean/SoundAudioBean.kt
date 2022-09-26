package io.agora.secnceui.bean

import io.agora.secnceui.annotation.SoundSpeakerType

/**
 * @author create by zhangwei03
 *
 * 语聊脚本
 */
data class SoundAudioBean constructor(
    @SoundSpeakerType val speakerType: Int, // 音效播放类型，
    var soundId: Int,
    var audioUrl: String, // 语聊url
) : BaseRoomBean
