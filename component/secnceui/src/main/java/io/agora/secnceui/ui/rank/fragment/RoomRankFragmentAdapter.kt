package io.agora.secnceui.ui.rank.fragment

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class RoomRankFragmentAdapter constructor(
    fragmentActivity: FragmentActivity,
) : FragmentStateAdapter(fragmentActivity) {

    companion object {
        const val PAGE_CONTRIBUTION_RANKING = 0
        const val PAGE_AUDIENCE_LIST = 1
    }

    private val fragments: SparseArray<Fragment> = SparseArray()

    init {
        with(fragments) {
            put(
                PAGE_CONTRIBUTION_RANKING,
                RoomContributionRankingFragment.getInstance()
            )
            put(
                PAGE_AUDIENCE_LIST,
                RoomAudienceListFragment.getInstance()
            )
        }
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return fragments.size()
    }
}