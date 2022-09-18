package io.agora.secnceui.ui.ainoise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.baseui.adapter.OnItemChildClickListener
import io.agora.baseui.dialog.BaseFixedHeightSheetDialog
import io.agora.buddy.tool.ViewTools
import io.agora.secnceui.R
import io.agora.secnceui.bean.AINSModeBean
import io.agora.secnceui.bean.AINSSoundsBean
import io.agora.secnceui.databinding.DialogChatroomAinsBinding
import io.agora.secnceui.databinding.ItemChatroomAgoraAinsBinding
import io.agora.secnceui.databinding.ItemChatroomAinsAuditionBinding

class RoomAINSSheetDialog constructor(
    private val isEnable: Boolean = true
) : BaseFixedHeightSheetDialog<DialogChatroomAinsBinding>() {

    companion object {
        const val KEY_AINS_MODE = "ains_mode"
    }

    private var anisModeAdapter: BaseRecyclerViewAdapter<ItemChatroomAgoraAinsBinding, AINSModeBean, RoomAINSModeViewHolder>? =
        null
    private var anisSoundsAdapter: BaseRecyclerViewAdapter<ItemChatroomAinsAuditionBinding, AINSSoundsBean, RoomAINSSoundsViewHolder>? =
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
        dialog?.window?.attributes?.windowAnimations = R.style.BottomSheetDialogAnimation
        dialog?.setCanceledOnTouchOutside(false)
        arguments?.apply {
            val anis = getInt(KEY_AINS_MODE)
            anisModeList.addAll(RoomAINSConstructor.builderDefaultAINSList(view.context, anis))
        }
        anisSoundsList.addAll(RoomAINSConstructor.builderDefaultSoundList(view.context))
        binding?.apply {
            setOnApplyWindowInsets(rvNoiseSuppression)
            ivBottomSheetBack.setOnClickListener {
                onHandleOnBackPressed()
            }
            initAdapter(rvNoiseSuppression)
        }
    }

    private fun initAdapter(recyclerView: RecyclerView) {
        val anisModeHeaderAdapter = BaseRecyclerViewAdapter(
            mutableListOf(getString(R.string.chatroom_ains_settings)),
            ChatroomAINSTitleViewHolder::class.java
        )
        anisModeAdapter = BaseRecyclerViewAdapter(anisModeList, null, object : OnItemChildClickListener<AINSModeBean> {

            override fun onItemChildClick(
                data: AINSModeBean?,
                extData: Any?,
                view: View,
                position: Int,
                itemViewType: Long
            ) {
                super.onItemChildClick(data, extData, view, position, itemViewType)
                data?.let {
                    if (isEnable){
                        if (extData is Int) {
                            if (it.anisMode == extData) {
                                return
                            } else {
                                data.anisMode = extData
                                anisModeAdapter?.notifyItemChanged(position)
                            }
                            Toast.makeText(view.context, "AINS Mode $extData", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(view.context, getString(R.string.chatroom_only_host_can_change_anis), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }, RoomAINSModeViewHolder::class.java)
        val anisIntroduceHeaderAdapter = BaseRecyclerViewAdapter(
            mutableListOf(getString(R.string.chatroom_agora_ains)),
            ChatroomAINSTitleViewHolder::class.java
        )
        val anisIntroduceContentAdapter = BaseRecyclerViewAdapter(
            mutableListOf(
                getString(R.string.chatroom_ains_introduce)
            ), ChatroomAINSContentViewHolder::class.java
        )
        val anisSoundsHeaderAdapter = BaseRecyclerViewAdapter(
            mutableListOf(getString(R.string.chatroom_agora_ains_supports_sounds)),
            ChatroomAINSTitleViewHolder::class.java
        )
        anisSoundsAdapter = BaseRecyclerViewAdapter(
            anisSoundsList, null, object : OnItemChildClickListener<AINSSoundsBean> {

                override fun onItemChildClick(
                    data: AINSSoundsBean?,
                    extData: Any?,
                    view: View,
                    position: Int,
                    itemViewType: Long
                ) {
                    super.onItemChildClick(data, extData, view, position, itemViewType)
                    data?.let {
                        if (extData is Int) {
                            if (it.soundsType == extData) {
                                return
                            } else {
                                data.soundsType = extData
                                anisSoundsAdapter?.notifyItemChanged(position)
                            }
                            Toast.makeText(view.context, "AINS Sound $extData", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            RoomAINSSoundsViewHolder::class.java
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
                    dividerColor = ViewTools.getColor(it.resources, R.color.divider_color_F6F6F6)
                }
            )
        }
        recyclerView.adapter = concatAdapter
    }
}