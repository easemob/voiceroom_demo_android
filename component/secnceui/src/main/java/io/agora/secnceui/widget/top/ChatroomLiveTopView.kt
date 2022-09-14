package io.agora.secnceui.widget.top

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import io.agora.buddy.tool.ViewTools
import io.agora.secnceui.R
import io.agora.secnceui.bean.ChatroomInfoBean
import io.agora.secnceui.databinding.ViewChatroomLiveTopBinding

class ChatroomLiveTopView : ConstraintLayout, View.OnClickListener, IChatroomLiveTopView {

    private lateinit var binding: ViewChatroomLiveTopBinding

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) {
        init(context)
    }

    @IntDef(ClickAction.BACK, ClickAction.RANK, ClickAction.NOTICE, ClickAction.SOCIAL)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class ClickAction {
        companion object {
            const val BACK = 0x00000100
            const val RANK = 0x00000200
            const val NOTICE = 0x00000300
            const val SOCIAL = 0x00000400
        }
    }

    @IntDef(
        ChatroomTopType.Owner, ChatroomTopType.ChatroomName, ChatroomTopType.Members, ChatroomTopType.Gifts,
        ChatroomTopType.Watches, ChatroomTopType.RankNo1, ChatroomTopType.RankNo2, ChatroomTopType.RankNo3
    )
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class ChatroomTopType {
        companion object {
            /**聊天室房主*/
            const val Owner = 0

            /**聊天室名称*/
            const val ChatroomName = 1

            /**聊天室观众数*/
            const val Members = 2

            /**礼物数*/
            const val Gifts = 3

            /**观看数*/
            const val Watches = 4

            /**排行榜No.1*/
            const val RankNo1 = 5

            /**排行榜No.2*/
            const val RankNo2 = 6

            /**排行榜No.3*/
            const val RankNo3 = 7
        }
    }

    private var onLiveTopClickListener: OnLiveTopClickListener? = null

    fun setOnLiveTopClickListener(onLiveTopClickListener: OnLiveTopClickListener) {
        this.onLiveTopClickListener = onLiveTopClickListener
    }

    private fun init(context: Context) {
        val root = View.inflate(context, R.layout.view_chatroom_live_top, this)
        binding = ViewChatroomLiveTopBinding.bind(root)

        binding.ivChatroomBack.setOnClickListener(this)
        binding.llChatroomMemberRank.setOnClickListener(this)
        binding.mtChatroomMembers.setOnClickListener(this)
        binding.mtChatroomNotice.setOnClickListener(this)
        binding.mtChatroomAgoraSound.setOnClickListener(this)
    }

    override fun onChatroomInfo(chatroomInfo: ChatroomInfoBean) {
        binding.apply {
            mtChatroomOwnerName.text = chatroomInfo.ownerName
            mtChatroomName.text = chatroomInfo.chatroomName
            mtChatroomMembers.text = chatroomInfo.audiencesCount.toString()
            mtChatroomGifts.text = chatroomInfo.giftCount.toString()
            mtChatroomWatch.text = chatroomInfo.watchCount.toString()
            // 房主头像
            binding.ivChatroomOwner.setImageResource(
                ViewTools.getDrawableId(
                    binding.ivChatroomOwner.context,
                    chatroomInfo.ownerAvatar
                )
            )
            val topGifts = chatroomInfo.topGifts
            if (topGifts.isNullOrEmpty()) {
                llChatroomMemberRank.isVisible = false
            } else {
                llChatroomMemberRank.isVisible = true
                topGifts.forEachIndexed { index, audienceBean ->
                    val resId = ViewTools.getDrawableId(llChatroomMemberRank.context, audienceBean.userAvatar)
                    when (index) {
                        0 -> {
                            ivChatroomMember1.isVisible = true
                            binding.ivChatroomMember1.setImageResource(resId)
                        }
                        1 -> {
                            ivChatroomMember2.isVisible = true
                            binding.ivChatroomMember2.setImageResource(resId)
                        }
                        2 -> {
                            ivChatroomMember3.isVisible = true
                            binding.ivChatroomMember3.setImageResource(resId)
                        }
                        else -> {
                            return
                        }
                    }
                }
            }
        }
    }

    /**设置头像*/
    override fun onImageUpdate(@ChatroomTopType type: Int, @DrawableRes avatarRes: Int) {
        when (type) {
            ChatroomTopType.Owner -> {
                // 房主头像
                binding.ivChatroomOwner.setImageResource(avatarRes)
            }
            ChatroomTopType.RankNo1 -> {
                binding.llChatroomMemberRank.isVisible = true
                binding.ivChatroomMember1.isVisible = true
                binding.ivChatroomMember1.setImageResource(avatarRes)
            }
            ChatroomTopType.RankNo2 -> {
                binding.llChatroomMemberRank.isVisible = true
                binding.ivChatroomMember2.isVisible = true
                binding.ivChatroomMember2.setImageResource(avatarRes)
            }
            ChatroomTopType.RankNo3 -> {
                binding.llChatroomMemberRank.isVisible = true
                binding.ivChatroomMember3.isVisible = true
                binding.ivChatroomMember3.setImageResource(avatarRes)
            }
            else -> {}
        }
    }

    /**设置文本*/
    override fun onTextUpdate(@ChatroomTopType type: Int, text: String) {
        when (type) {
            // 房主名称
            ChatroomTopType.Owner -> binding.mtChatroomOwnerName.text = text
            // 聊天室名称
            ChatroomTopType.ChatroomName -> binding.mtChatroomName.text = text
            // 观众人数
            ChatroomTopType.Members -> binding.mtChatroomMembers.text = text
            // 礼物数
            ChatroomTopType.Gifts -> binding.mtChatroomGifts.text = text
            // 观看数
            ChatroomTopType.Watches -> binding.mtChatroomWatch.text = text
            else -> {}
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            // 返回
            binding.ivChatroomBack -> onLiveTopClickListener?.onClickView(v, ClickAction.BACK)
            // 排行榜
            binding.llChatroomMemberRank,
            binding.mtChatroomMembers -> onLiveTopClickListener?.onClickView(v, ClickAction.RANK)
            // 公告
            binding.mtChatroomNotice -> onLiveTopClickListener?.onClickView(v, ClickAction.NOTICE)
            //音效
            binding.mtChatroomAgoraSound -> onLiveTopClickListener?.onClickView(v, ClickAction.SOCIAL)
        }
    }
}