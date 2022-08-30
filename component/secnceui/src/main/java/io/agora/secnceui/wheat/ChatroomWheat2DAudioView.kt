package io.agora.secnceui.wheat

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import io.agora.secnceui.R
import io.agora.secnceui.bean.ChatroomWheatSeatType
import io.agora.secnceui.bean.ChatroomWheatUserRole
import io.agora.secnceui.bean.ChatroomWheatUserStatus
import io.agora.secnceui.bean.SeatInfoBean
import io.agora.secnceui.databinding.ViewChatroom2dAudioWheatBinding
import io.agora.secnceui.wheat.adapter.ChatroomWheatSeatAdapter
import io.agora.secnceui.wheat.adapter.ChatroomWheatViewHolder

class ChatroomWheat2DAudioView : ConstraintLayout {

    companion object {
        val testWheatSeatList = mutableListOf<SeatInfoBean>().apply {
            add(
                SeatInfoBean(
                    index = 0,
                    name = "Susan Stark",
                    avatar = "",
                    wheatSeatType = ChatroomWheatSeatType.Normal,
                    userRole = ChatroomWheatUserRole.Owner,
                    userStatus = ChatroomWheatUserStatus.Speaking,
                    rotImage = 0
                )
            )
            add(SeatInfoBean(index = 1))
            add(SeatInfoBean(index = 2, wheatSeatType = ChatroomWheatSeatType.Mute))
            add(SeatInfoBean(index = 3, wheatSeatType = ChatroomWheatSeatType.Lock))
            add(
                SeatInfoBean(
                    index = 4,
                    name = "Jim Scofield",
                    wheatSeatType = ChatroomWheatSeatType.Normal,
                    userRole = ChatroomWheatUserRole.Guest,
                    userStatus = ChatroomWheatUserStatus.Mute
                )
            )
            add(SeatInfoBean(index = 5))
            add(
                SeatInfoBean(
                    index = 6,
                    wheatSeatType = ChatroomWheatSeatType.Normal,
                    userRole = ChatroomWheatUserRole.Robot,
                    userStatus = ChatroomWheatUserStatus.Speaking,
                    name = "Agora Blue...",
                    rotImage = R.drawable.icon_seat_blue_robot,
                    isBotOpen = true
                )
            )
            add(
                SeatInfoBean(
                    index = 7, wheatSeatType = ChatroomWheatSeatType.Normal,
                    userRole = ChatroomWheatUserRole.Robot,
                    userStatus = ChatroomWheatUserStatus.Idle,
                    name = "Agora Red...",
                    rotImage = R.drawable.icon_seat_red_robot
                )
            )
        }
    }

    private lateinit var binding: ViewChatroom2dAudioWheatBinding

    private var wheatSeatAdapter: ChatroomWheatSeatAdapter? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context, attrs, defStyleAttr, defStyleRes
    ) {
        init(context)
    }

    private fun init(context: Context) {
        val root = View.inflate(context, R.layout.view_chatroom_2d_audio_wheat, this)
        binding = ViewChatroom2dAudioWheatBinding.bind(root)
    }

    fun setUpAdapter(seatInfoList: List<SeatInfoBean>) {
        wheatSeatAdapter = ChatroomWheatSeatAdapter(seatInfoList, null, ChatroomWheatViewHolder::class.java)
        binding.rvChatroomWheatSeat.apply {
            layoutManager = GridLayoutManager(context, 4)
            adapter = wheatSeatAdapter
        }
    }
}