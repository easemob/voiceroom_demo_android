package io.agora.secnceui.widget.gift;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import java.util.List;

import io.agora.secnceui.bean.GiftBean;


public class GiftFragmentAdapter extends FragmentStateAdapter {
    private OnVpFragmentItemListener listener;
    private List<GiftBean> list;

    public GiftFragmentAdapter(@NonNull FragmentActivity fragment) {
        super(fragment);
        list =  GiftRepository.getDefaultGifts(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        LiveGiftListFragment fragment = new LiveGiftListFragment();
        fragment.setOnItemSelectClickListener(new OnConfirmClickListener() {
            @Override
            public void onConfirmClick(View view, Object bean) {
                if(listener != null) {
                    listener.onVpFragmentItem(position, bean);
                }
            }
        });
        //添加参数
        return fragment;
    }

    @Override
    public int getItemCount() {
        return Math.round((list.size()/4)+0.5f);
    }

    public void setOnVpFragmentItemListener(OnVpFragmentItemListener listener) {
        this.listener = listener;
    }

    public interface OnVpFragmentItemListener {
        void onVpFragmentItem(int position, Object bean);
    }
}
