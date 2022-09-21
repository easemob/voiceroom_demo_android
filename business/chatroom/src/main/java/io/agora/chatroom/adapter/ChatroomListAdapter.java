package io.agora.chatroom.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import io.agora.baseui.adapter.RoomBaseRecyclerViewAdapter;
import io.agora.chatroom.R;
import tools.bean.VRoomBean;


public class ChatroomListAdapter extends RoomBaseRecyclerViewAdapter<VRoomBean.RoomsBean> {

    @Override
    public ViewHolder<VRoomBean.RoomsBean> getViewHolder(ViewGroup parent, int viewType) {
        return new RoomListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.fragment_room_item_layout, parent, false));
    }

    public class RoomListViewHolder extends ViewHolder<VRoomBean.RoomsBean> {
        private ConstraintLayout item_layout;
        private ConstraintLayout title_layout;
        private TextView title;
        private TextView roomName;
        private TextView ownerName;
        private TextView roomCount;
        private TextView enter;
        private ShapeableImageView ownerAvatar;
        private ImageView icon;

        public RoomListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        @Override
        public void initView(View itemView) {
            super.initView(itemView);
            item_layout = findViewById(R.id.room_item_layout);
            title_layout = findViewById(R.id.room_title_layout);
            title = findViewById(R.id.private_title);
            icon = findViewById(R.id.icon_private);
            roomName = findViewById(R.id.room_name);
            ownerAvatar = findViewById(R.id.owner_avatar);
            ownerName = findViewById(R.id.owner_name);
            roomCount = findViewById(R.id.room_count);
            enter = findViewById(R.id.enter);
        }

        @Override
        public void setDataList(List<VRoomBean.RoomsBean> data, int position) {
            Log.e("setData","position : " + position);
            itemType( data.get(position).getType());
            showPrivate(data.get(position).isIs_private(),data.get(position).getType());
            roomName.setText(data.get(position).getName());
            ownerName.setText(data.get(position).getOwner().getName());
            roomCount.setText(mContext.getString(R.string.room_list_count,String.valueOf(data.get(position).getMember_count())));
        }

        @Override
        public void setData(VRoomBean.RoomsBean item, int position) {

        }

        private void itemType(int type){
            switch (type){
                case 1:
                    item_layout.setBackgroundResource(R.drawable.bg_chatroom_list_type_nomal);
                    break;
                case 2:
                    item_layout.setBackgroundResource(R.drawable.bg_chatroom_list_type_3d);
                    break;
                case 3:
                    item_layout.setBackgroundResource(R.drawable.bg_chatroom_list_type_official);
                    break;
                default:
                    item_layout.setBackgroundResource(R.drawable.bg_chatroom_list_type_nomal);
            }
        }

        private void showPrivate(boolean isShow,int type){
            if (isShow){
                title_layout.setVisibility(View.VISIBLE);
                if (type == 2){
                    icon.setBackgroundResource(R.drawable.icon_official);
                    title.setText(mContext.getString(R.string.room_list_title_official));
                    return;
                }
                title.setText(mContext.getString(R.string.room_list_title_private));
            }else {
                title_layout.setVisibility(View.GONE);
            }
        }
    }


}


