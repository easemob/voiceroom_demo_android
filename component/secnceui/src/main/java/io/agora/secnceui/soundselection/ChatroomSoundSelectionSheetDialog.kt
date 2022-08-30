package io.agora.secnceui.soundselection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.baseui.adapter.OnItemClickListener
import io.agora.baseui.dialog.BaseSheetDialog
import io.agora.secnceui.R
import io.agora.secnceui.bean.CustomerUsageBean
import io.agora.secnceui.bean.SoundSelectionBean
import io.agora.secnceui.databinding.DialogChatroomSoundSelectionBinding
import io.agora.secnceui.databinding.ItemChatroomSoundSelectionBinding

class ChatroomSoundSelectionSheetDialog : BaseSheetDialog<DialogChatroomSoundSelectionBinding>() {

    private var soundSelectionAdapter: BaseRecyclerViewAdapter<ItemChatroomSoundSelectionBinding, SoundSelectionBean, ChatroomSoundSelectionViewHolder>? =
        null

    private val soundSelectionList = mutableListOf<SoundSelectionBean>().apply {
        add(
            SoundSelectionBean(
                soundTitle = "Current Sound Selection",
                soundName = "Social Chat",
                soundIntroduce = "This scenario focuses on echo cancellation, noise reduction in a multi-person chat setting, creating a quiet chat atmosphere",
                isCurrentUsing = true,
                isShowHint = false,
                customer = mutableListOf<CustomerUsageBean>().apply {
                    add(CustomerUsageBean("ya", R.drawable.icon_chatroom_ya_launcher))
                    add(CustomerUsageBean("soul", R.drawable.icon_chatroom_soul_launcher))
                }
            )
        )
        add(
            SoundSelectionBean(soundTitle = "Other Sound Selection",
                soundName = "Karaoke",
                soundIntroduce = "The scene deals with the coordination of your voice and the musical accompaniment through high sound quality and echo cancellation to ensure the best karaoke experience",
                isCurrentUsing = false,
                isShowHint = false,
                customer = mutableListOf<CustomerUsageBean>().apply {
                    add(CustomerUsageBean("ya", R.drawable.icon_chatroom_ya_launcher))
                    add(CustomerUsageBean("soul", R.drawable.icon_chatroom_soul_launcher))
                })
        )
        add(
            SoundSelectionBean(soundTitle = "",
                soundName = "Gaming Buddy",
                soundIntroduce = "This scene focuses on the coordination of human voice and in-game sound during live, interactive game sessions",
                isCurrentUsing = false,
                isShowHint = false,
                customer = mutableListOf<CustomerUsageBean>().apply {
                    add(CustomerUsageBean("ya", R.drawable.icon_chatroom_ya_launcher))
                    add(CustomerUsageBean("soul", R.drawable.icon_chatroom_soul_launcher))
                })
        )
    }

    private val footerList = mutableListOf<String>().apply {
        add("")
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): DialogChatroomSoundSelectionBinding {
        return DialogChatroomSoundSelectionBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setCanceledOnTouchOutside(false)
        binding?.apply {
            setOnApplyWindowInsets(root)
            ivBottomSheetBack.setOnClickListener {
                onHandleOnBackPressed()
            }
            initAdapter(rvBottomSheetSoundSelection)
        }

    }

    private fun initAdapter(recyclerView: RecyclerView) {
        soundSelectionAdapter =
            BaseRecyclerViewAdapter(soundSelectionList, object : OnItemClickListener<SoundSelectionBean> {
                override fun onItemClick(data: SoundSelectionBean, view: View, position: Int, viewType: Long) {
                    Toast.makeText(context, "$data", Toast.LENGTH_LONG).show()
                    if (!data.isCurrentUsing) {
                        data.isShowHint = true
                        soundSelectionAdapter?.notifyItemChanged(position)
                    }
                }
            }, ChatroomSoundSelectionViewHolder::class.java)
        val footerAdapter = BaseRecyclerViewAdapter(footerList, ChatroomSoundSelectionFooterViewHolder::class.java)
        val config = ConcatAdapter.Config.Builder().setIsolateViewTypes(true).build()
        val concatAdapter = ConcatAdapter(config, soundSelectionAdapter, footerAdapter)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = concatAdapter
    }
}