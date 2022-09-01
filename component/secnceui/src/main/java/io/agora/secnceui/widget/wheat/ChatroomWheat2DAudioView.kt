package io.agora.secnceui.widget.wheat

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.google.android.material.divider.MaterialDividerItemDecoration
import io.agora.baseui.adapter.OnItemClickListener
import io.agora.buddy.tool.dp
import io.agora.secnceui.R
import io.agora.secnceui.bean.*
import io.agora.secnceui.databinding.ViewChatroom2dAudioWheatBinding
import io.agora.secnceui.wheat.flat.ChatroomWheat2DBotViewHolder
import io.agora.secnceui.wheat.flat.ChatroomWheat2DSeatAdapter
import io.agora.secnceui.wheat.flat.ChatroomWheat2DSeatBotAdapter
import io.agora.secnceui.wheat.flat.ChatroomWheat2DViewHolder

class ChatroomWheat2DAudioView : ConstraintLayout {

    private lateinit var binding: ViewChatroom2dAudioWheatBinding

    private var wheat2DSeatAdapter: ChatroomWheat2DSeatAdapter? = null
    private var wheat2DSeatBotAdapter: ChatroomWheat2DSeatBotAdapter? = null

    private var onItemClickListener: OnItemClickListener<SeatInfoBean>? = null

    fun onItemClickListener(onItemClickListener: OnItemClickListener<SeatInfoBean>) = apply {
        this.onItemClickListener = onItemClickListener
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
        val root = View.inflate(context, R.layout.view_chatroom_2d_audio_wheat, this)
        binding = ViewChatroom2dAudioWheatBinding.bind(root)
    }

    fun setUpAdapter(seatInfoList: List<SeatInfoBean>,botSeatList:List<BotSeatInfoBean>) {
        wheat2DSeatAdapter =
            ChatroomWheat2DSeatAdapter(seatInfoList, onItemClickListener, ChatroomWheat2DViewHolder::class.java)
        wheat2DSeatBotAdapter =
            ChatroomWheat2DSeatBotAdapter(botSeatList, null, ChatroomWheat2DBotViewHolder::class.java)

        val config = ConcatAdapter.Config.Builder().setIsolateViewTypes(true).build()
        val concatAdapter = ConcatAdapter(config, wheat2DSeatAdapter, wheat2DSeatBotAdapter)
        val gridLayoutManager = GridLayoutManager(context, 4).apply {
            spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == (concatAdapter.itemCount) - 1) {
                        2
                    } else 1
                }
            }
        }
        binding.rvChatroomWheatSeat.apply {
            addItemDecoration(MaterialDividerItemDecoration(context, MaterialDividerItemDecoration.VERTICAL).apply {
                dividerThickness = 32.dp.toInt()
                dividerColor = ResourcesCompat.getColor(context.resources, io.agora.baseui.R.color.transparent, null)
            })
            layoutManager = gridLayoutManager
            adapter = concatAdapter
        }
    }
}