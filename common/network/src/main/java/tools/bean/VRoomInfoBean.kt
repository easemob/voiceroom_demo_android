package tools.bean

/**
 * @author create by zhangwei03
 *
 */
data class VRoomInfoBean constructor(
    val mic_info: List<VRoomMicInfo>? = null,
    val room: VRoomDetail? = null
)

data class VRoomMicInfo constructor(
    val index: Int = 0,
    val status: Int = -1,
    val user: VRoomUser? = null
)

data class VRoomDetail constructor(
    val allowed_free_join_mic: Boolean = false,
    val announcement: String? = null,
    val channel_id: String? = null,
    val chatroom_id: String? = null,
    val click_count: Int = 0,
    val is_private: Boolean = false,
    val member_count: Int = 0,
    val gift_amount: Int = 0,
    val member_list: List<VRoomUser>? = null,
    val name: String? = null,
    val owner: VRoomUser? = null,
    val ranking_list: List<VRoomRanking>? = null,
    val room_id: String? = null,
    val type: Int = 0
)

data class VRoomUser constructor(
    val chat_uid: String? = null,
    val name: String? = null,
    val portrait: String? = null,
    val uid: String? = null,
    val rtcUid: Int = 0
)

data class VRoomRanking constructor(
    val amount: Int = 0,
    val name: String? = null,
    val portrait: String? = null,
)