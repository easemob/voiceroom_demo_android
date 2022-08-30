package io.agora.secnceui.wheat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import io.agora.baseui.adapter.OnItemClickListener
import io.agora.baseui.dialog.BaseSheetDialog
import io.agora.buddy.tool.dp
import io.agora.secnceui.R
import io.agora.secnceui.bean.SeatManagerBean
import io.agora.secnceui.databinding.DialogChatroomSeatManagerBinding
import io.agora.secnceui.wheat.adapter.ChatroomSeatManagerAdapter
import io.agora.secnceui.wheat.adapter.ChatroomSeatManagerViewHolder

class ChatroomSeatManagerSheetDialog : BaseSheetDialog<DialogChatroomSeatManagerBinding>() {

    companion object {

        const val KEY_SEAT_INFO = "seat_info"
    }

    private var adapter: ChatroomSeatManagerAdapter? = null

    private val seatManagerList = mutableListOf<SeatManagerBean>().apply {
        add(SeatManagerBean("Invite"))
        add(SeatManagerBean("Mute"))
        add(SeatManagerBean("Lock"))
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): DialogChatroomSeatManagerBinding {
        return DialogChatroomSeatManagerBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ChatroomSeatManagerAdapter(seatManagerList, object : OnItemClickListener<SeatManagerBean> {
            override fun onItemClick(data: SeatManagerBean, view: View, position: Int, viewType: Long) {
                Toast.makeText(context, "$data", Toast.LENGTH_LONG).show()
            }
        }, ChatroomSeatManagerViewHolder::class.java)
        arguments?.apply {
//            val seatInfo: SeatInfoBean = get(KEY_SEAT_INFO) as SeatInfoBean
//            binding?.apply {
//                mtChatroomSeatName.text = seatInfo.name
//                mtChatroomSeatTag.isVisible = seatInfo.isHost
//            }
        }
        binding?.apply {
            addMargin(view)
            setOnApplyWindowInsets(root)

            val itemDecoration =
                MaterialDividerItemDecoration(root.context, MaterialDividerItemDecoration.HORIZONTAL).apply {
                    dividerColor = ResourcesCompat.getColor(root.context.resources, R.color.divider_color_EFF4FF, null)
                    dividerThickness = 1.dp.toInt()
                    dividerInsetStart = 20.dp.toInt()
                    dividerInsetEnd = 20.dp.toInt()
                }
            rvChatroomSeatManager.addItemDecoration(itemDecoration)
            rvChatroomSeatManager.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            rvChatroomSeatManager.adapter = adapter
        }
    }

    private fun addMargin(view: View) {
        val layoutParams: FrameLayout.LayoutParams = view.layoutParams as FrameLayout.LayoutParams
        val marginHorizontal = 15.dp.toInt()
        val marginVertical = 24.dp.toInt()
        layoutParams.setMargins(marginHorizontal, 0, marginHorizontal, marginVertical)
        view.layoutParams = layoutParams
    }
}
