package io.agora.secnceui.rank

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import io.agora.baseui.dialog.BaseSheetDialog
import io.agora.buddy.tool.ViewTools
import io.agora.secnceui.R
import io.agora.secnceui.databinding.DialogChatroomContributionAndAudienceBinding

class ChatroomContributionAndAudienceSheetDialog constructor(private val fragmentActivity: FragmentActivity) :
    BaseSheetDialog<DialogChatroomContributionAndAudienceBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogChatroomContributionAndAudienceBinding {
        return DialogChatroomContributionAndAudienceBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            setOnApplyWindowInsets(root)
        }
        initFragmentAdapter()
    }

    private fun initFragmentAdapter() {
        val adapter = ChatroomRankFragmentAdapter(fragmentActivity)
        binding?.apply {
            vpRankLayout.adapter = adapter
            val tabMediator = TabLayoutMediator(tabRankLayout, vpRankLayout) { tab, position ->
                val customView =
                    LayoutInflater.from(root.context).inflate(R.layout.view_chatroom_rank_tab_item, tab.view, false)
                val tabText = customView.findViewById<TextView>(R.id.mtTabText)
                tab.customView = customView
                if (position == 0) {
                    tabText.text = getString(R.string.chatroom_contribution_ranking)
                    onTabLayoutSelected(tab)
                } else {
                    tabText.text = getString(R.string.chatroom_audience_list)
                    onTabLayoutUnselected(tab)
                }

            }
            tabMediator.attach()
            tabRankLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    onTabLayoutSelected(tab)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    onTabLayoutUnselected(tab)
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })
        }
    }

    private fun onTabLayoutSelected(tab: TabLayout.Tab?) {
        tab?.customView?.let {
            val tabText = it.findViewById<TextView>(R.id.mtTabText)
            tabText.setTextColor(ViewTools.getColor(resources, R.color.dark_grey_color_040925))
            tabText.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            val tabTip = it.findViewById<View>(R.id.vTabTip)
            tabTip.visibility = View.VISIBLE
        }
    }

    private fun onTabLayoutUnselected(tab: TabLayout.Tab?) {
        tab?.customView?.let {
            val tabText = it.findViewById<TextView>(R.id.mtTabText)
            tabText.setTextColor(ViewTools.getColor(resources, R.color.dark_grey_color_6C7192))
            tabText.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
            val tabTip = it.findViewById<View>(R.id.vTabTip)
            tabTip.visibility = View.GONE
        }
    }
}