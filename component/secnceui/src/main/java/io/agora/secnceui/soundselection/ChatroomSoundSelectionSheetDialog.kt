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
import io.agora.baseui.dialog.BaseFixedHeightSheetDialog
import io.agora.secnceui.bean.SoundSelectionBean
import io.agora.secnceui.databinding.DialogChatroomSoundSelectionBinding
import io.agora.secnceui.databinding.ItemChatroomSoundSelectionBinding

class ChatroomSoundSelectionSheetDialog constructor(private val soundSelectionListener: OnClickSoundSelectionListener) :
    BaseFixedHeightSheetDialog<DialogChatroomSoundSelectionBinding>() {

    companion object {
        const val KEY_CURRENT_SELECTION = "current_selection"
    }

    private var soundSelectionAdapter: BaseRecyclerViewAdapter<ItemChatroomSoundSelectionBinding, SoundSelectionBean, ChatroomSoundSelectionViewHolder>? =
        null

    private val soundSelectionList = mutableListOf<SoundSelectionBean>()

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): DialogChatroomSoundSelectionBinding {
        return DialogChatroomSoundSelectionBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setCanceledOnTouchOutside(false)
        arguments?.apply {
            val currentSelection: Int = getInt(KEY_CURRENT_SELECTION)
            soundSelectionList.addAll(
                ChatroomSoundSelectionConstructor.builderSoundSelectionList(view.context, currentSelection)
            )
        }

        binding?.apply {
            setOnApplyWindowInsets(rvBottomSheetSoundSelection)
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
                    Toast.makeText(context, "$data", Toast.LENGTH_SHORT).show()
                    if (!data.isCurrentUsing) {
                        soundSelectionListener.onSoundEffect(data)
                    }
                }
            }, ChatroomSoundSelectionViewHolder::class.java)
        val footerList = mutableListOf<String>().apply { add("") }
        val footerAdapter = BaseRecyclerViewAdapter(footerList, ChatroomSoundSelectionFooterViewHolder::class.java)
        val config = ConcatAdapter.Config.Builder().setIsolateViewTypes(true).build()
        val concatAdapter = ConcatAdapter(config, soundSelectionAdapter, footerAdapter)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = concatAdapter
    }

    interface OnClickSoundSelectionListener {
        fun onSoundEffect(soundSelection: SoundSelectionBean)
    }
}