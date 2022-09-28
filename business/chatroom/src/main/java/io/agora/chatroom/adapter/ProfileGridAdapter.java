package io.agora.chatroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

import io.agora.chatroom.R;
import io.agora.chatroom.bean.ProfileBean;

public class ProfileGridAdapter extends BaseAdapter {
   private Context context;
   private LayoutInflater inflater;
   private List<ProfileBean> data = new ArrayList<>();
   private OnItemClickListener listener;
   private int selectedPosition = -1;

   public ProfileGridAdapter(Context context){
      this.context = context;
      this.inflater = LayoutInflater.from(context);
      getDefaultAvatar(context);
   }

   public List<ProfileBean> getDefaultAvatar(Context context) {
      ProfileBean bean;
      for(int i = 1; i <= 18; i++){
         bean = new ProfileBean();
         String name = "avatar"+i;
         int resId = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
         bean.setAvatarResource(resId);
         bean.setAvatarName(name);
         data.add(bean);
      }
      return data;
   }

   @Override
   public int getCount() {
      return data.size();
   }

   @Override
   public Object getItem(int position) {
      return data.get(position);
   }

   @Override
   public long getItemId(int position) {
      return position;
   }

   @Override
   public View getView(int position, View convertView, ViewGroup parent) {
      View view =  inflater.inflate(R.layout.chatroom_profile_avatar_item, null);
      ShapeableImageView imageView = view.findViewById(R.id.avatar);
      ConstraintLayout item = view.findViewById(R.id.item_layout);
      ImageView check = view.findViewById(R.id.icon_check);
      item.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            if (listener != null){
               listener.OnItemClick(position,data.get(position));
            }
         }
      });
      imageView.setImageResource(data.get(position).getAvatarResource());
      if(selectedPosition == position) {
         check.setEnabled(false);
         item.setEnabled(false);
      }else {
         check.setEnabled(true);
         item.setEnabled(true);
         data.get(position).setChecked(false);
      }
      return view;
   }

   public interface OnItemClickListener{
      void OnItemClick(int position,ProfileBean bean);
   }

   public void SetOnItemClickListener(OnItemClickListener listener){
      this.listener = listener;
   }

   public void setSelectedPosition(int position) {
      this.selectedPosition = position;
      notifyDataSetChanged();
   }

}
