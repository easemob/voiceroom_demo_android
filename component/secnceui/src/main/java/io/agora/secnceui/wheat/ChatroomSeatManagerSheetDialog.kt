package io.agora.secnceui.wheat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import io.agora.baseui.adapter.OnItemClickListener
import io.agora.baseui.dialog.BaseSheetDialog
import io.agora.buddy.tool.GlideTools
import io.agora.buddy.tool.dp
import io.agora.secnceui.R
import io.agora.secnceui.annotation.WheatSeatType
import io.agora.secnceui.annotation.WheatUserRole
import io.agora.secnceui.annotation.WheatUserStatus
import io.agora.secnceui.bean.*
import io.agora.secnceui.databinding.DialogChatroomSeatManagerBinding
import io.agora.secnceui.wheat.flat.ChatroomSeatManagerAdapter
import io.agora.secnceui.wheat.flat.ChatroomSeatManagerViewHolder

class ChatroomSeatManagerSheetDialog constructor(): BaseSheetDialog<DialogChatroomSeatManagerBinding>() {

    companion object {
        const val KEY_SEAT_INFO = "seat_info"
    }

    private var seatManagerAdapter: ChatroomSeatManagerAdapter? = null

    private val seatManagerList = mutableListOf<SeatManagerBean>()

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): DialogChatroomSeatManagerBinding {
        return DialogChatroomSeatManagerBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.apply {
            val seatInfo: SeatInfoBean? = get(KEY_SEAT_INFO) as? SeatInfoBean
            seatInfo?.let {
                bindingSeatInfo(it)
                seatManagerList.addAll(ChatroomWheatConstructor.builderOwnerSeatMangerList(view.context, it))
//                seatManagerList.addAll(ChatroomWheatConstructor.builderGuestSeatMangerList(view.context, it))
            }
        }
        seatManagerAdapter = ChatroomSeatManagerAdapter(seatManagerList, object : OnItemClickListener<SeatManagerBean> {
            override fun onItemClick(data: SeatManagerBean, view: View, position: Int, viewType: Long) {
                Toast.makeText(context, data.name, Toast.LENGTH_SHORT).show()
            }
        }, ChatroomSeatManagerViewHolder::class.java)
        binding?.apply {
            setOnApplyWindowInsets(root)

            val itemDecoration =
                MaterialDividerItemDecoration(root.context, MaterialDividerItemDecoration.HORIZONTAL).apply {
                    dividerColor = ResourcesCompat.getColor(root.context.resources, R.color.divider_color_979797, null)
                    dividerThickness = 1.dp.toInt()
                }
            rvChatroomSeatManager.addItemDecoration(itemDecoration)
            rvChatroomSeatManager.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            rvChatroomSeatManager.adapter = seatManagerAdapter
        }
    }

    private fun bindingSeatInfo(seatInfo: SeatInfoBean) {
        binding?.mtChatroomSeatTag?.isVisible = seatInfo.userRole == WheatUserRole.Owner
        binding?.apply {
            when (seatInfo.wheatSeatType) {
                WheatSeatType.Idle,
                WheatSeatType.Mute -> {
                    ivSeatInnerIcon.isVisible = true
                    if (seatInfo.wheatSeatType == WheatSeatType.Idle) {
                        ivSeatInnerIcon.setImageResource(R.drawable.icon_seat_add)
                    } else {
                        ivSeatInnerIcon.setImageResource(R.drawable.icon_seat_mic)
                    }
                    ivSeatMic.isVisible = false
                    mtSeatInfoName.text = seatInfo.index.toString()
                }
                WheatSeatType.Lock,
                WheatSeatType.LockMute -> {
                    ivSeatInnerIcon.isVisible = true
                    ivSeatInnerIcon.setImageResource(R.drawable.icon_seat_close)
                    ivSeatMic.isVisible = seatInfo.wheatSeatType == WheatSeatType.LockMute
                    mtSeatInfoName.text = seatInfo.index.toString()
                }
                else -> {
                    mtSeatInfoName.text = seatInfo.name
                    ivSeatInnerIcon.isVisible = false
                    GlideTools.loadImage(ivSeatInfo.context, seatInfo.avatar, ivSeatInfo)
                    ivSeatMic.apply {
                        when (seatInfo.userStatus) {
                            WheatUserStatus.None -> {
                                isVisible = false
                            }
                            WheatUserStatus.Idle -> {
                                isVisible = true
                                setImageResource(R.drawable.icon_seat_on_mic0)
                            }
                            WheatUserStatus.Mute,
                            WheatUserStatus.ForceMute -> {
                                isVisible = true
                                setImageResource(R.drawable.icon_seat_off_mic)
                            }
                            else -> {
                                // speaking
                                isVisible = true
                                setImageResource(R.drawable.icon_seat_on_mic1)
                            }
                        }
                    }
                }
            }
        }
    }
}
