package io.agora.secnceui.widget.gift;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import io.agora.baseui.adapter.RoomBaseRecyclerViewAdapter;
import io.agora.secnceui.R;
import io.agora.secnceui.bean.GiftBean;


public class GiftListAdapter extends RoomBaseRecyclerViewAdapter<GiftBean> {
    private int selectedPosition = -1;

    @Override
    public GiftViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new GiftViewHolder(LayoutInflater.from(mContext).inflate(R.layout.chatroom_gift_list_item_layout, parent, false));
    }

    private class GiftViewHolder extends ViewHolder<GiftBean> {
        private ImageView ivGift;
        private TextView tvGiftName;
        private TextView price;

        public GiftViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void initView(View itemView) {
            ivGift = findViewById(R.id.iv_gift);
            tvGiftName = findViewById(R.id.tv_gift_name);
            price = findViewById(R.id.price);
        }

        @Override
        public void setData(GiftBean item, int position) {
            Log.e("GiftListAdapter","setData: " + position);
            ivGift.setImageResource(item.getResource());
            tvGiftName.setText(item.getName());

            if(selectedPosition == position) {
                item.setChecked(true);
                itemView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_gift_selected_shape));
            }else {
                item.setChecked(false);
                itemView.setBackground(null);
            }
        }
    }


    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
    }


}
