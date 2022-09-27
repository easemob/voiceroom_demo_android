package io.agora.secnceui.ui.ainoise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.baseui.adapter.OnItemChildClickListener
import io.agora.baseui.dialog.BaseFixedHeightSheetDialog
import io.agora.buddy.tool.ToastTools
import io.agora.buddy.tool.ResourcesTools
import io.agora.config.ConfigConstants
import io.agora.secnceui.R
import io.agora.secnceui.bean.AINSModeBean
import io.agora.secnceui.bean.AINSSoundsBean
import io.agora.secnceui.databinding.DialogChatroomAinsBinding
import io.agora.secnceui.databinding.ItemChatroomAgoraAinsBinding
import io.agora.secnceui.databinding.ItemChatroomAinsAuditionBinding

class RoomAINSSheetDialog constructor(
    private val isEnable: Boolean = true,
    private val anisModeCallback: (AINSModeBean) -> Unit,
    private val anisSoundCallback: (AINSSoundsBean) -> Unit
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

    private val anisMode by lazy {
        arguments?.getInt(KEY_AINS_MODE, ConfigConstants.AINSMode.AINS_Medium) ?: ConfigConstants.AINSMode.AINS_Medium
    }

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
        anisModeList.addAll(RoomAINSConstructor.builderDefaultAINSList(view.context, anisMode))
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
                data: AINSModeBean?, extData: Any?, view: View, position: Int, itemViewType: Long
            ) {
                data?.let { anisMode ->
                    if (extData is Int) {
                        if (anisMode.anisMode == extData) {
                            return
                        } else {
                            anisMode.anisMode = extData
                            anisModeAdapter?.notifyItemChanged(position)
                            anisModeCallback.invoke(anisMode)
                        }
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
                    data: AINSSoundsBean?, extData: Any?, view: View, position: Int, itemViewType: Long
                ) {
                    data?.let { anisSound ->
                        if (isEnable) {
                            if (extData is Int) {
                                if (anisSound.soundMode == extData) {
                                    return
                                } else {
                                    val selectedIndex = anisSoundsAdapter?.selectedIndex ?: -1
                                    if (selectedIndex >= 0) {
                                        anisSoundsAdapter?.dataList?.get(selectedIndex)?.soundMode =
                                            ConfigConstants.AINSMode.AINS_Unknown
                                        anisSoundsAdapter?.notifyItemChanged(selectedIndex)
                                    }
                                    anisSoundsAdapter?.selectedIndex = position
                                    anisSound.soundMode = extData
                                    anisSoundsAdapter?.notifyItemChanged(position)
                                    anisSoundCallback.invoke(anisSound)
                                }
                            }
                        } else {
                            ToastTools.show(view.context, getString(R.string.chatroom_only_host_can_change_anis))
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
                    dividerColor = ResourcesTools.getColor(it.resources, R.color.divider_color_F6F6F6)
                }
            )
        }
        recyclerView.adapter = concatAdapter
    }
}