package io.agora.secnceui.widget.top

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.IntDef
import androidx.constraintlayout.widget.ConstraintLayout
import io.agora.secnceui.R
import io.agora.secnceui.databinding.ViewChatroomTopBinding

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@IntDef(
    ChatroomTopType.Owner, ChatroomTopType.ChatroomName, ChatroomTopType.Members, ChatroomTopType.Gifts,
    ChatroomTopType.Watches, ChatroomTopType.Notices, ChatroomTopType.Sound, ChatroomTopType.Back, ChatroomTopType.Rank,
    ChatroomTopType.RankNo1, ChatroomTopType.RankNo2, ChatroomTopType.RankNo3
)
annotation class ChatroomTopType {
    companion object {
        /**聊天室房主*/
        const val Owner = 0

        /**聊天室名称*/
        const val ChatroomName = 1

        /**聊天室名称*/
        const val Members = 2

        /**礼物数*/
        const val Gifts = 3

        /**观看数*/
        const val Watches = 4

        /**公告*/
        const val Notices = 5

        /**音效*/
        const val Sound = 6

        /**返回*/
        const val Back = 7

        /**排行榜*/
        const val Rank = 8

        /**排行榜No.1*/
        const val RankNo1 = 9

        /**排行榜No.2*/
        const val RankNo2 = 10

        /**排行榜No.3*/
        const val RankNo3 = 11
    }
}

class ChatroomTopView : ConstraintLayout, View.OnClickListener, IChatroomTopView {

    private lateinit var binding: ViewChatroomTopBinding

    private var onChildClickListener: OnChildClickListener? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context, attrs, defStyleAttr, defStyleRes
    ) {
        init(context)
    }

    private fun init(context: Context) {
        val root = View.inflate(context, R.layout.view_chatroom_top, this)
        binding = ViewChatroomTopBinding.bind(root)

        binding.ivChatroomBack.setOnClickListener(this)
        binding.llChatroomMemberRank.setOnClickListener(this)
        binding.mtChatroomMembers.setOnClickListener(this)
        binding.mtChatroomNotice.setOnClickListener(this)
        binding.mtChatroomAgoraSound.setOnClickListener(this)
    }

    /**设置头像*/
    override fun setImageSrc(@ChatroomTopType type: Int, imageUrl: String) {
        when (type) {
            ChatroomTopType.Owner -> {
                // 房主头像
            }
            else -> {}
        }
    }

    /**设置文本*/
    override fun setText(@ChatroomTopType type: Int, text: String) {
        when (type) {
            // 房主名称
            ChatroomTopType.Owner -> binding.mtChatroomOwnerName.text = text
            // 聊天室名称
            ChatroomTopType.ChatroomName -> binding.mtChatroomName.text = text
            // 礼物数
            ChatroomTopType.Gifts -> binding.mtChatroomGifts.text = text
            // 观看数
            ChatroomTopType.Watches -> binding.mtChatroomWatch.text = text
            // 公告
            ChatroomTopType.Notices -> binding.mtChatroomNotice.text = text
            else -> {}
        }
    }

    fun setOnChildClickListener(onChildClickListener: OnChildClickListener) {
        this.onChildClickListener = onChildClickListener
    }

    override fun onClick(v: View?) {
        when (v) {
            // 返回
            binding.ivChatroomBack -> onChildClickListener?.onClick(v, ChatroomTopType.Back)
            // 排行榜
            binding.llChatroomMemberRank, binding.mtChatroomMembers ->
                onChildClickListener?.onClick(v, ChatroomTopType.Rank)
            // 公告
            binding.mtChatroomNotice -> onChildClickListener?.onClick(v, ChatroomTopType.Notices)
            //音效
            binding.mtChatroomAgoraSound -> onChildClickListener?.onClick(v, ChatroomTopType.Sound)
        }
    }

    interface OnChildClickListener {
        fun onClick(v: View?, @ChatroomTopType type: Int)
    }
}

interface IChatroomTopView {
    fun setText(@ChatroomTopType type: Int, text: String)

    fun setImageSrc(@ChatroomTopType type: Int, imageUrl: String)
}