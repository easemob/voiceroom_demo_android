package io.agora.secnceui.ainoise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.baseui.adapter.OnItemClickListener
import io.agora.baseui.dialog.BaseSheetDialog
import io.agora.secnceui.R
import io.agora.secnceui.bean.AINSModeBean
import io.agora.secnceui.bean.AINSSoundsBean
import io.agora.secnceui.bean.SoundSelectionBean
import io.agora.secnceui.databinding.DialogChatroomAinsBinding
import io.agora.secnceui.databinding.ItemChatroomAgoraAinsBinding
import io.agora.secnceui.databinding.ItemChatroomAinsAuditionBinding

class ChatroomAINSSheetDialog constructor() : BaseSheetDialog<DialogChatroomAinsBinding>() {

    companion object{
        const val KEY_AINS_MODE = "ains_mode"
    }

    private var anisModeAdapter: BaseRecyclerViewAdapter<ItemChatroomAgoraAinsBinding, AINSModeBean, ChatroomAINSModeViewHolder>? =
        null
    private var anisSoundsAdapter: BaseRecyclerViewAdapter<ItemChatroomAinsAuditionBinding, AINSSoundsBean, ChatroomAINSSoundsViewHolder>? =
        null

    private val anisModeList = mutableListOf<AINSModeBean>()

    private val anisSoundsList = mutableListOf<AINSSoundsBean>()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogChatroomAinsBinding {
        return DialogChatroomAinsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setCanceledOnTouchOutside(false)
        arguments?.apply {
            val anis = getInt(KEY_AINS_MODE)
            anisModeList.addAll(ChatroomAINSConstructor.builderDefaultAINSList(view.context,anis))
        }
        anisSoundsList.addAll(ChatroomAINSConstructor.builderDefaultSoundList(view.context))
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
        anisModeAdapter = BaseRecyclerViewAdapter(anisModeList, object : OnItemClickListener<AINSModeBean> {

            override fun onItemClick(data: AINSModeBean, view: View, position: Int, viewType: Long) {
                val tag = view.tag
                if (tag is Int) {
                    if (data.anisMode == tag) {
                        return
                    } else {
                        data.anisMode = tag
                        anisSoundsAdapter?.notifyItemChanged(position)
                    }
                    Toast.makeText(view.context, "AINS Mode $tag", Toast.LENGTH_SHORT).show()
                }

            }
        }, ChatroomAINSModeViewHolder::class.java)

        val anisIntroduceHeaderAdapter = BaseRecyclerViewAdapter(mutableListOf<String>().apply {
            add(getString(R.string.chatroom_agora_ains))
        }, ChatroomAINSTitleViewHolder::class.java)

        val anisIntroduceContentAdapter = BaseRecyclerViewAdapter(mutableListOf<String>().apply {
            add(getString(R.string.chatroom_ains_introduce))
        }, ChatroomAINSContentViewHolder::class.java)

        val anisSoundsHeaderAdapter = BaseRecyclerViewAdapter(mutableListOf<String>().apply {
            add(getString(R.string.chatroom_agora_ains_supports_sounds))
        }, ChatroomAINSTitleViewHolder::class.java)

        anisSoundsAdapter = BaseRecyclerViewAdapter(
            anisSoundsList, object : OnItemClickListener<AINSSoundsBean> {

                override fun onItemClick(data: AINSSoundsBean, view: View, position: Int, viewType: Long) {
                    val tag = view.tag
                    if (tag is Int) {
                        if (data.soundsType == tag) {
                            return
                        } else {
                            data.soundsType = tag
                            anisSoundsAdapter?.notifyItemChanged(position)
                        }
                        Toast.makeText(view.context, "AINS Sound $tag", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            ChatroomAINSSoundsViewHolder::class.java
        )
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