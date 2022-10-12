package io.agora.secnceui.widget.top

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import io.agora.buddy.tool.ResourcesTools
import io.agora.buddy.tool.ScreenTools
import io.agora.buddy.tool.dp
import io.agora.config.ConfigConstants
import io.agora.secnceui.R
import io.agora.secnceui.annotation.ChatroomTopType
import io.agora.secnceui.bean.RoomInfoBean
import io.agora.secnceui.bean.RoomRankUserBean
import io.agora.secnceui.constants.ScenesConstant
import io.agora.secnceui.databinding.ViewChatroomLiveTopBinding

class RoomLiveTopView : ConstraintLayout, View.OnClickListener, IRoomLiveTopView {

    private lateinit var binding: ViewChatroomLiveTopBinding

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) {
        init(context)
    }

    private lateinit var roomInfo: RoomInfoBean

    private var onLiveTopClickListener: OnLiveTopClickListener? = null

    fun setOnLiveTopClickListener(onLiveTopClickListener: OnLiveTopClickListener) {
        this.onLiveTopClickListener = onLiveTopClickListener
    }

    private fun init(context: Context) {
        val root = View.inflate(context, R.layout.view_chatroom_live_top, this)
        binding = ViewChatroomLiveTopBinding.bind(root)
        binding.ivChatroomBack.setOnClickListener(this)
        binding.llChatroomMemberRank.setOnClickListener(this)
//        binding.mtChatroomMembers.setOnClickListener(this)
        binding.mtChatroomNotice.setOnClickListener(this)
        binding.mtChatroomAgoraSound.setOnClickListener(this)

    }

    fun setTitleMaxWidth(activity: Activity) {
        val layoutParams: ViewGroup.LayoutParams = binding.llTitle.layoutParams
        layoutParams.width = ScreenTools.getScreenWidth(activity) - 220.dp.toInt()
        binding.llTitle.layoutParams = layoutParams
    }

    override fun onChatroomInfo(chatroomInfo: RoomInfoBean) {
        this.roomInfo = chatroomInfo
        binding.apply {
            mtChatroomOwnerName.text = chatroomInfo.owner?.username
            mtChatroomName.text = chatroomInfo.chatroomName
            mtChatroomMembers.text = chatroomInfo.memberCount.toString()
            mtChatroomGifts.text = chatroomInfo.giftCount.toString()
            mtChatroomWatch.text = chatroomInfo.watchCount.toString()
            // 普通房间显示 最佳音效
            if (chatroomInfo.roomType == ConfigConstants.RoomType.Common_Chatroom) {
                mtChatroomAgoraSound.isVisible = true
                mtChatroomAgoraSound.text = when (chatroomInfo.soundSelection) {
                    ConfigConstants.SoundSelection.Karaoke -> root.context.getString(R.string.chatroom_karaoke)
                    ConfigConstants.SoundSelection.Gaming_Buddy -> root.context.getString(R.string.chatroom_gaming_buddy)
                    ConfigConstants.SoundSelection.Professional_Broadcaster -> root.context.getString(R.string.chatroom_professional_broadcaster)
                    else -> root.context.getString(R.string.chatroom_social_chat)
                }
            } else {
                mtChatroomAgoraSound.isVisible = false
            }

            // 房主头像
            binding.ivChatroomOwner.setImageResource(
                ResourcesTools.getDrawableId(
                    binding.ivChatroomOwner.context,
                    chatroomInfo.owner?.userAvatar ?: ScenesConstant.DefaultAvatar
                )
            )
            val topGifts = chatroomInfo.topRankUsers
            if (topGifts.isNullOrEmpty()) {
                llChatroomMemberRank.isVisible = false
            } else {
                llChatroomMemberRank.isVisible = true
                topGifts.forEachIndexed { index, audienceBean ->
                    val resId = ResourcesTools.getDrawableId(llChatroomMemberRank.context, audienceBean.userAvatar)
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

    override fun onRankMember(topGifts: List<RoomRankUserBean>) {
        binding.apply {
            if (topGifts.isNullOrEmpty()) {
                llChatroomMemberRank.isVisible = false
            } else {
                llChatroomMemberRank.isVisible = true
                topGifts.forEachIndexed { index, audienceBean ->
                    val resId = ResourcesTools.getDrawableId(llChatroomMemberRank.context, audienceBean.userAvatar)
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
    override fun onImageUpdate(@ChatroomTopType type: Int, avatar: String) {
        val avatarRes = ResourcesTools.getDrawableId(binding.ivChatroomOwner.context, avatar)
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

    override fun addOrSubMemberCount(add: Boolean) {
        super.addOrSubMemberCount(add)
        if (this::roomInfo.isInitialized) {
            if (add) {
                roomInfo.memberCount += 1
            } else {
                roomInfo.memberCount -= 1
            }
            binding.mtChatroomMembers.text = roomInfo.memberCount.toString()
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            // 返回
            binding.ivChatroomBack -> onLiveTopClickListener?.onClickBack(v)
            // 排行榜
            binding.llChatroomMemberRank,
            binding.mtChatroomMembers -> onLiveTopClickListener?.onClickRank(v)
            // 公告
            binding.mtChatroomNotice -> onLiveTopClickListener?.onClickNotice(v)
            //音效
            binding.mtChatroomAgoraSound -> onLiveTopClickListener?.onClickSoundSocial(v)
        }
    }
}