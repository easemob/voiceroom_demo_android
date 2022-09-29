package io.agora.secnceui.ui.mic.flat

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.google.android.material.divider.MaterialDividerItemDecoration
import io.agora.baseui.adapter.OnItemChildClickListener
import io.agora.baseui.adapter.OnItemClickListener
import io.agora.buddy.tool.ResourcesTools
import io.agora.buddy.tool.dp
import io.agora.secnceui.R
import io.agora.secnceui.annotation.MicClickAction
import io.agora.secnceui.bean.*
import io.agora.secnceui.databinding.ViewChatroom2dMicLayoutBinding
import io.agora.secnceui.ui.mic.IRoomMicView
import io.agora.secnceui.ui.mic.RoomMicConstructor

class Room2DMicLayout : ConstraintLayout, IRoomMicView {

    private lateinit var binding: ViewChatroom2dMicLayoutBinding

    private var room2DMicAdapter: Room2DMicAdapter? = null
    private var room2DMicBotAdapter: Room2DBotMicAdapter? = null

    private var onMicClickListener: OnItemClickListener<MicInfoBean>? = null
    private var onBotMicClickListener: OnItemClickListener<MicInfoBean>? = null

    fun onItemClickListener(
        onMicClickListener: OnItemClickListener<MicInfoBean>,
        onBotMicClickListener: OnItemClickListener<MicInfoBean>
    ) = apply {
        this.onMicClickListener = onMicClickListener
        this.onBotMicClickListener = onBotMicClickListener
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context, attrs, defStyleAttr, defStyleRes
    ) {
        init(context)
    }

    private fun init(context: Context) {
        val root = View.inflate(context, R.layout.view_chatroom_2d_mic_layout, this)
        binding = ViewChatroom2dMicLayoutBinding.bind(root)
    }

    fun setUpAdapter(isUseBot: Boolean) {
        room2DMicAdapter =
            Room2DMicAdapter(
                RoomMicConstructor.builderDefault2dMicList(),
                onMicClickListener,
                Room2DMicViewHolder::class.java
            )
        room2DMicBotAdapter =
            Room2DBotMicAdapter(
                RoomMicConstructor.builderDefault2dBotMicList(context, isUseBot),
                null,
                object : OnItemChildClickListener<BotMicInfoBean> {

                    // convert
                    override fun onItemChildClick(
                        data: BotMicInfoBean?, extData: Any?, view: View, position: Int, itemViewType: Long
                    ) {
                        if (extData is MicInfoBean) {
                            onBotMicClickListener?.onItemClick(extData, view, position, itemViewType)
                        }
                    }
                },
                Room2DBotMicViewHolder::class.java
            )

        val config = ConcatAdapter.Config.Builder().setIsolateViewTypes(true).build()
        val concatAdapter = ConcatAdapter(config, room2DMicAdapter, room2DMicBotAdapter)
        val gridLayoutManager = GridLayoutManager(context, 4).apply {
            spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == (concatAdapter.itemCount) - 1) {
                        2
                    } else 1
                }
            }
        }
        binding.rvChatroomMicLayout.apply {
            addItemDecoration(MaterialDividerItemDecoration(context, MaterialDividerItemDecoration.VERTICAL).apply {
                dividerThickness = 32.dp.toInt()

                dividerColor = ResourcesTools.getColor(context.resources, io.agora.baseui.R.color.transparent)
            })
            layoutManager = gridLayoutManager
            adapter = concatAdapter
        }
    }

    override fun updateAdapter(micInfoList: List<MicInfoBean>, isBotActive: Boolean) {
        room2DMicAdapter?.submitListAndPurge(micInfoList)
        room2DMicBotAdapter?.activeBot(isBotActive)
    }

    override fun activeBot(active: Boolean) {
        room2DMicBotAdapter?.activeBot(active)
    }

    override fun updateVolume(index: Int, volume: Int) {

    }

    override fun updateBotVolume(speakerType: Int, volume: Int) {
        room2DMicBotAdapter?.updateVolume(speakerType, volume)
    }

    override fun updateMicStatusByAction(index: Int, @MicClickAction action: Int) {
        room2DMicAdapter?.updateMicStatusByAction(index, action)
    }

    override fun exchangeMic(from: Int, to: Int) {

    }

    override fun findMicByUid(uid: String): Int {
        room2DMicAdapter?.dataList?.forEachIndexed { index, micInfoBean ->
            if (TextUtils.equals(micInfoBean.userInfo?.userId, uid)) {
                return index
            }
        }
        return -1
    }
}