package io.agora.chatroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.textview.MaterialTextView;
import java.util.Objects;

import io.agora.baseui.adapter.RoomBaseRecyclerViewAdapter;
import io.agora.chatroom.R;
import io.agora.secnceui.bean.CustomerUsageBean;
import io.agora.secnceui.bean.SoundSelectionBean;
import io.agora.secnceui.utils.DeviceUtils;

public class ChatroomSoundSelectionAdapter extends RoomBaseRecyclerViewAdapter<SoundSelectionBean> {
    private static int selectedPosition = -1;
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener listener;

    public ChatroomSoundSelectionAdapter(Context context){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ViewHolder<SoundSelectionBean> getViewHolder(ViewGroup parent, int viewType) {
        return new soundViewHold(inflater.inflate(R.layout.chatroom_sound_selection_item, null));
    }

    @Override
    public int getItemCount() {
        return getData().size();
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
    }

    public class soundViewHold extends ViewHolder<SoundSelectionBean>{
        private ConstraintLayout item;
        private MaterialTextView sound_name;
        private MaterialTextView sound_desc;
        private LinearLayout layout;
        private AppCompatImageView icon;
        private Context context;

        public soundViewHold(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            item = itemView.findViewById(R.id.item);
            sound_name = itemView.findViewById(R.id.sound_name);
            sound_desc = itemView.findViewById(R.id.sound_desc);
            layout = itemView.findViewById(R.id.llSoundCustomerUsage);
            icon = itemView.findViewById(R.id.ivSoundSelected);
        }

        @Override
        public void setData(SoundSelectionBean bean, int position) {
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        listener.OnItemClick(position,bean);
                    }
                }
            });
            sound_name.setText(bean.getSoundName());
            sound_desc.setText(bean.getSoundIntroduce());
            for (CustomerUsageBean customerUsageBean : Objects.requireNonNull(bean.getCustomer())) {
                ImageView imageView = new ImageView(context);
                imageView.setImageResource(customerUsageBean.getAvatar());
                ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(context,null);
                marginLayoutParams.setMargins(0,0,DeviceUtils.dp2px(context,10),0);
                imageView.setLayoutParams(marginLayoutParams);
                ViewGroup.LayoutParams params = imageView.getLayoutParams();
                params.width = DeviceUtils.dp2px(context,20);
                params.height = DeviceUtils.dp2px(context,20);
                imageView.setLayoutParams(params);
                layout.addView(imageView);
            }
            if(selectedPosition == position) {
                icon.setVisibility(View.VISIBLE);
            }else {
                icon.setVisibility(View.GONE);
            }
        }
    }
    public interface OnItemClickListener{
        void OnItemClick(int position, SoundSelectionBean bean);
    }

    public void SetOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
