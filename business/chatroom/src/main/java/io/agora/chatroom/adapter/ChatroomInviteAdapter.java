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
import tools.bean.VRoomUserBean;

public class ChatroomInviteAdapter extends RoomBaseRecyclerViewAdapter<VRoomUserBean.UsersBean> {
    private onActionListener listener;

    @Override
    public RoomBaseRecyclerViewAdapter.ViewHolder<VRoomUserBean.UsersBean> getViewHolder(ViewGroup parent, int viewType) {
        return new inviteViewHolder(LayoutInflater.from (parent.getContext()).inflate (R.layout.chatroom_hands_item_raised, parent, false));
    }

    public class inviteViewHolder extends ViewHolder<VRoomUserBean.UsersBean> {
        private ShapeableImageView avatar;
        private MaterialTextView name;
        private MaterialTextView action;

        public inviteViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void setData(VRoomUserBean.UsersBean item, int position) {
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
            action.setText(mContext.getString(R.string.chatroom_invite));
            action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                        listener.onItemActionClick(view,position,item.getUid());
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
        void onItemActionClick(View view,int position,String uid);
    }




}
