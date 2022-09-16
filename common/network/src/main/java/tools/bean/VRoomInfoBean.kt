package tools.bean

/**
 * @author create by zhangwei03
 *
 */
data class VRoomInfoBean constructor(
    val mic_info: List<VRoomMicInfo> = mutableListOf(),
    val room: VRoomDetail? = null
)

data class VRoomMicInfo constructor(
    val index: Int,
    val status: Int,
    val user: VRoomUser
)

data class VRoomDetail constructor(
    val allowed_free_join_mic: Boolean = false,
    val announcement: String? = null,
    val channel_id: String? = null,
    val chatroom_id: String? = null,
    val click_count: Int = 0,
    val is_private: Boolean = false,
    val member_count: Int = 0,
    val member_list: List<VRoomMember>? = null,
    val name: String? = null,
    val owner: VRoomOwner? = null,
    val ranking_list: List<VRoomRanking>? = null,
    val room_id: String? = null,
    val type: Int = 0
)

data class VRoomUser constructor(
    val chat_uid: String,
    val name: String,
    val portrait: String,
    val uid: String
)

data class VRoomMember constructor(
    val chat_uid: String,
    val name: String,
    val portrait: String,
    val uid: String
)

data class VRoomOwner constructor(
    val chat_uid: String,
    val name: String,
    val portrait: String,
    val uid: String
)

data class VRoomRanking constructor(
    val amount: Int,
    val name: String,
    val portrait: String
)