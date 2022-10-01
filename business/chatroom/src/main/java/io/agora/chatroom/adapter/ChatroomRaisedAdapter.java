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
import tools.bean.VRMicListBean;

public class ChatroomRaisedAdapter extends RoomBaseRecyclerViewAdapter<VRMicListBean.ApplyListBean> {
    private onActionListener listener;

    @Override
    public RoomBaseRecyclerViewAdapter.ViewHolder<VRMicListBean.ApplyListBean> getViewHolder(ViewGroup parent, int viewType) {
        return new raisedViewHolder(LayoutInflater.from (parent.getContext()).inflate (R.layout.chatroom_hands_item_raised, parent, false));
    }


    public class raisedViewHolder extends ViewHolder<VRMicListBean.ApplyListBean> {
        private ShapeableImageView avatar;
        private MaterialTextView name;
        private MaterialTextView action;
        @Override
        public void initView(View itemView) {
            super.initView(itemView);
            avatar = itemView.findViewById(R.id.ivAudienceAvatar);
            name = itemView.findViewById(R.id.mtAudienceUsername);
            action = itemView.findViewById(R.id.mtAudienceAction);
        }

        public raisedViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void setData(VRMicListBean.ApplyListBean item, int position) {
            int resId = 0;
                try {
                    resId = mContext.getResources().getIdentifier(item.getMember().getPortrait(), "drawable", mContext.getPackageName());
                }catch (Exception e){
                    Log.e("getResources()", e.getMessage());
                }
                if (resId != 0){
                    avatar.setImageResource(resId);
                }
                name.setText(item.getMember().getName());
                action.setText(mContext.getString(R.string.chatroom_accept));
                action.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null)
                            listener.onItemActionClick(view,item.getMic_index(),item.getMember().getUid());
                    }
                });
            }
        }

    public void setOnActionListener(onActionListener listener){
        this.listener = listener;
    }

    public interface onActionListener{
        void onItemActionClick(View view,int index,String uid);
    }
}
