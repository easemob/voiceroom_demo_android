package io.agora.secnceui.ui.soundselection

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.baseui.adapter.OnItemChildClickListener
import io.agora.baseui.adapter.OnItemClickListener
import io.agora.baseui.dialog.BaseFixedHeightSheetDialog
import io.agora.buddy.tool.ToastTools
import io.agora.secnceui.R
import io.agora.secnceui.bean.SoundSelectionBean
import io.agora.secnceui.databinding.DialogChatroomSoundSelectionBinding
import io.agora.secnceui.databinding.ItemChatroomSoundSelectionBinding

class RoomSoundSelectionSheetDialog constructor(
    private val isEnable: Boolean = true,
    private val soundSelectionListener: OnClickSoundSelectionListener
) :
    BaseFixedHeightSheetDialog<DialogChatroomSoundSelectionBinding>() {

    companion object {
        const val KEY_CURRENT_SELECTION = "current_selection"
        const val KEY_IS_ENABLE = "is_enable"
    }

    private var soundSelectionAdapter: BaseRecyclerViewAdapter<ItemChatroomSoundSelectionBinding, SoundSelectionBean, RoomSoundSelectionViewHolder>? =
        null

    private val soundSelectionList = mutableListOf<SoundSelectionBean>()


    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): DialogChatroomSoundSelectionBinding {
        return DialogChatroomSoundSelectionBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.attributes?.windowAnimations = R.style.BottomSheetDialogAnimation
        dialog?.setCanceledOnTouchOutside(false)
        arguments?.apply {
            val currentSelection: Int = getInt(KEY_CURRENT_SELECTION)
            soundSelectionList.addAll(
                RoomSoundSelectionConstructor.builderSoundSelectionList(view.context, currentSelection)
            )
        }

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
                    if (isEnable) {
                        soundSelectionListener.onSoundEffect(data, data.isCurrentUsing)
                    } else {
                        activity?.let {
                            ToastTools.showTips(it, getString(R.string.chatroom_only_host_can_change_best_sound))
                        }
                    }
                }
            }, RoomSoundSelectionViewHolder::class.java)
        val footerList = mutableListOf(recyclerView.context.getString(R.string.chatroom_sound_selection_more))
        val footerAdapter = BaseRecyclerViewAdapter(footerList, null, object : OnItemChildClickListener<String> {
            override fun onItemChildClick(data: String?, extData: Any?, view: View, position: Int, itemViewType: Long) {
//                if (extData is String) {
//                    val intent = Intent()
//                        .setAction("android.intent.action.VIEW")
//                        .setData(Uri.parse(extData))
//                    startActivity(intent)
//                }
            }
        }, RoomSoundSelectionFooterViewHolder::class.java)
        val config = ConcatAdapter.Config.Builder().setIsolateViewTypes(true).build()
        val concatAdapter = ConcatAdapter(config, soundSelectionAdapter, footerAdapter)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = concatAdapter
    }

    interface OnClickSoundSelectionListener {
        fun onSoundEffect(soundSelection: SoundSelectionBean, isCurrentUsing: Boolean)
    }
}