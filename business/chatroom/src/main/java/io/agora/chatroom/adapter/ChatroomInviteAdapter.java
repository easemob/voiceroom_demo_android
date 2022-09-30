package io.agora.chatroom.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import io.agora.baseui.adapter.RoomBaseRecyclerViewAdapter;
import io.agora.chatroom.R;
import tools.bean.VMemberBean;

public class ChatroomInviteAdapter extends RoomBaseRecyclerViewAdapter<VMemberBean> {
    private onActionListener listener;

    @Override
    public RoomBaseRecyclerViewAdapter.ViewHolder<VMemberBean> getViewHolder(ViewGroup parent, int viewType) {
        return new inviteViewHolder(LayoutInflater.from (parent.getContext()).inflate (R.layout.chatroom_hands_item_raised, parent, false));
    }

    public class inviteViewHolder extends ViewHolder<VMemberBean> {
        private ShapeableImageView avatar;
        private MaterialTextView name;
        private MaterialTextView action;

        public inviteViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void setData(VMemberBean item, int position) {
            int resId = 0;
            try {
                resId = mContext.getResources().getIdentifier(item.getPortrait(), "drawable", mContext.getPackageName());
            }catch (Exception e){
                Log.e("getResources()", e.getMessage());
            }
            if (resId != 0){
                avatar.setImageResource(resId);
            }
            name.setText(item.getName());
            action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                        listener.onItemClick(view,position);
                }
            });
        }

        @Override
        public void initView(View itemView) {
            super.initView(itemView);
            avatar = itemView.findViewById(R.id.ivAudienceAvatar);
            name = itemView.findViewById(R.id.mtAudienceUsername);
            action = itemView.findViewById(R.id.mtAudienceAction);
        }
    }

    public void setOnActionListener(onActionListener listener){
        this.listener = listener;
    }

    public interface onActionListener{
        void onItemClick(View view,int position);
    }




}
