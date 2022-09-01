package io.agora.secnceui.ainoise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.baseui.dialog.BaseSheetDialog
import io.agora.secnceui.R
import io.agora.secnceui.bean.AINSModeBean
import io.agora.secnceui.bean.AINSModeType
import io.agora.secnceui.bean.AINSSoundType
import io.agora.secnceui.bean.AINSSoundsBean
import io.agora.secnceui.databinding.DialogChatroomNoiseSuppressionBinding
import io.agora.secnceui.databinding.ItemChatroomAgoraAinsBinding
import io.agora.secnceui.databinding.ItemChatroomAinsAuditionBinding

class ChatroomNoiseSuppressionSheetDialog constructor(): BaseSheetDialog<DialogChatroomNoiseSuppressionBinding>() {

    private var anisModeAdapter: BaseRecyclerViewAdapter<ItemChatroomAgoraAinsBinding, AINSModeBean, ChatroomAINSViewHolder>? =
        null
    private var anisSoundsAdapter: BaseRecyclerViewAdapter<ItemChatroomAinsAuditionBinding, AINSSoundsBean, ChatroomAINSSoundsViewHolder>? =
        null

    private val anisModeList = mutableListOf<AINSModeBean>().apply {
        add(AINSModeBean("You AINS", AINSModeType.High))
        add(AINSModeBean("Agora Blue Bot AINS", AINSModeType.Medium))
        add(AINSModeBean("Agora Red Bot AINS", AINSModeType.Off))
    }

    private val anisSoundsList = mutableListOf<AINSSoundsBean>().apply {
        add(AINSSoundsBean("TV Sound", "", AINSSoundType.AINS))
        add(AINSSoundsBean("Kitchen Sound"))
        add(AINSSoundsBean("Street Sound", "Ex. Bird, car, subway sounds"))
        add(AINSSoundsBean("Machine Sound", "Ex. Fan, air conditioner, vacuum cleaner, printer sounds"))
        add(AINSSoundsBean("Office Sound", "Ex. Keyboard tapping, mouse clicking sounds"))
        add(AINSSoundsBean("Home Sound", "Ex. Door closing, chair squeaking, baby crying sounds"))
        add(AINSSoundsBean("Construction Sound", "Ex. Knocking sound"))
        add(AINSSoundsBean("Alert Sound / Music"))
        add(AINSSoundsBean("Applause"))
        add(AINSSoundsBean("Wind Sound"))
        add(AINSSoundsBean("Mic Pop Filter"))
        add(AINSSoundsBean("Audio Feedback"))
        add(AINSSoundsBean("Microphone Finger Rub Sound"))
        add(AINSSoundsBean("Screen Tap Sound"))
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogChatroomNoiseSuppressionBinding {
        return DialogChatroomNoiseSuppressionBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setCanceledOnTouchOutside(false)
        binding?.apply {
            setOnApplyWindowInsets(root)
            ivBottomSheetBack.setOnClickListener {
                onHandleOnBackPressed()
            }
            initAdapter(rvNoiseSuppression)
        }
    }

    private fun initAdapter(recyclerView: RecyclerView) {
        val anisModeHeaderAdapter = BaseRecyclerViewAdapter(mutableListOf<String>().apply {
            add(getString(R.string.chatroom_ains_settings))
        }, ChatroomAINSTitleViewHolder::class.java)
        anisModeAdapter = BaseRecyclerViewAdapter(anisModeList, ChatroomAINSViewHolder::class.java)
        val anisIntroduceHeaderAdapter = BaseRecyclerViewAdapter(mutableListOf<String>().apply {
            add(getString(R.string.chatroom_agora_ains))
        }, ChatroomAINSTitleViewHolder::class.java)
        val anisIntroduceContentAdapter = BaseRecyclerViewAdapter(mutableListOf<String>().apply {
            add(getString(R.string.chatroom_ains_introduce))
        }, ChatroomAINSContentViewHolder::class.java)
        val anisSoundsHeaderAdapter = BaseRecyclerViewAdapter(mutableListOf<String>().apply {
            add(getString(R.string.chatroom_agora_ains_supports_sounds))
        }, ChatroomAINSTitleViewHolder::class.java)
        anisSoundsAdapter = BaseRecyclerViewAdapter(anisSoundsList, ChatroomAINSSoundsViewHolder::class.java)
        val config = ConcatAdapter.Config.Builder().setIsolateViewTypes(true).build()
        val concatAdapter = ConcatAdapter(
            config,
            anisModeHeaderAdapter, anisModeAdapter,
            anisIntroduceHeaderAdapter, anisIntroduceContentAdapter,
            anisSoundsHeaderAdapter, anisSoundsAdapter,
        )
        recyclerView.layoutManager = LinearLayoutManager(context)
        context?.let {
            recyclerView.addItemDecoration(
                MaterialDividerItemDecoration(it, MaterialDividerItemDecoration.HORIZONTAL).apply {
                    dividerColor = ResourcesCompat.getColor(it.resources, R.color.divider_color_F6F6F6, null)
                }
            )
        }
        recyclerView.adapter = concatAdapter
    }
}