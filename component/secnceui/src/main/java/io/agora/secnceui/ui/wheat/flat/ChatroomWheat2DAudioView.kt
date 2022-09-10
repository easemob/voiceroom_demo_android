package io.agora.secnceui.ui.wheat.flat

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.google.android.material.divider.MaterialDividerItemDecoration
import io.agora.baseui.adapter.OnItemChildClickListener
import io.agora.baseui.adapter.OnItemClickListener
import io.agora.buddy.tool.ViewTools
import io.agora.buddy.tool.dp
import io.agora.secnceui.R
import io.agora.secnceui.bean.*
import io.agora.secnceui.databinding.ViewChatroom2dAudioWheatBinding

class ChatroomWheat2DAudioView : ConstraintLayout {

    private lateinit var binding: ViewChatroom2dAudioWheatBinding

    private var wheat2DSeatAdapter: ChatroomWheat2DSeatAdapter? = null
    private var wheat2DSeatBotAdapter: ChatroomWheat2DSeatBotAdapter? = null

    private var onSeatClickListener: OnItemClickListener<SeatInfoBean>? = null
    private var onBotClickListener: OnItemClickListener<SeatInfoBean>? = null

    fun onItemClickListener(
        onItemClickListener: OnItemClickListener<SeatInfoBean>,
        onBotClickListener: OnItemClickListener<SeatInfoBean>
    ) = apply {
        this.onSeatClickListener = onItemClickListener
        this.onBotClickListener = onBotClickListener
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

    fun setUpAdapter(seatInfoList: List<SeatInfoBean>, botSeatList: List<BotSeatInfoBean>) {
        wheat2DSeatAdapter =
            ChatroomWheat2DSeatAdapter(seatInfoList, onSeatClickListener, ChatroomWheat2DViewHolder::class.java)
        wheat2DSeatBotAdapter =
            ChatroomWheat2DSeatBotAdapter(
                botSeatList, null, object : OnItemChildClickListener<BotSeatInfoBean> {

                    // convert
                    override fun onItemChildClick(
                        data: BotSeatInfoBean?,
                        extData: Any?,
                        view: View,
                        position: Int,
                        itemViewType: Long
                    ) {
                        super.onItemChildClick(data, extData, view, position, itemViewType)
                        if (extData is SeatInfoBean) {
                            onBotClickListener?.onItemClick(extData, view, position, itemViewType)
                        }
                    }
                }, ChatroomWheat2DBotViewHolder::class.java
            )

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

                dividerColor = ViewTools.getColor(context.resources, io.agora.baseui.R.color.transparent)
            })
            layoutManager = gridLayoutManager
            adapter = concatAdapter
        }
    }
}