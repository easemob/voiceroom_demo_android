package io.agora.secnceui.widget.gift;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

import bean.ChatMessageData;
import custormgift.CustomMsgHelper;
import io.agora.secnceui.R;
import io.agora.secnceui.bean.GiftBean;
import io.agora.secnceui.utils.DeviceUtils;


public class ChatroomGiftView extends LinearLayout {
   private RecyclerView recyclerView;
   private String chatroomId;
   private GiftListAdapter adapter;
   private Context mContext;
   private Handler handler = new Handler();
   private Runnable task;
   private int delay = 3000;

   // 开启定时任务
   private void startTask() {
      stopTask();
      handler.postDelayed(task = new Runnable() {
         @Override
         public void run() {
            // 在这里执行具体的任务
            if (adapter.messages.size() > 0){
               adapter.removeAll();
            }
            Log.d("HandlerTask", "run");
            // 任务执行完后再次调用postDelayed开启下一次任务
            handler.postDelayed(this, delay);
         }
      }, delay);
   }

   // 停止定时任务
   private void stopTask() {
      if (task != null) {
         handler.removeCallbacks(task);
         task = null;
      }
   }


   public ChatroomGiftView(Context context) {
      super(context);
      init(context,null);
   }

   public ChatroomGiftView(Context context, @Nullable AttributeSet attrs) {
      super(context, attrs);
      init(context,attrs);
   }

   private void init(Context context, AttributeSet attrs){
      this.mContext = context;
      View view = LayoutInflater.from(mContext).inflate(R.layout.widget_gift_layout,this);
      recyclerView = view.findViewById(R.id.recycler_view);
   }

   public void init(String chatroomId){
      this.chatroomId = chatroomId;
      adapter = new GiftListAdapter(mContext, CustomMsgHelper.getInstance().getGiftData(chatroomId));
      LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
      recyclerView.setLayoutManager(linearLayoutManager);
      recyclerView.setAdapter(adapter);


      //设置item 间距
      DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
      GradientDrawable drawable = new GradientDrawable();
      drawable.setSize(0, (int) DeviceUtils.dp2px(getContext(), 6));
      itemDecoration.setDrawable(drawable);
      recyclerView.addItemDecoration(itemDecoration);

      recyclerView.setOnTouchListener(new OnTouchListener() {
         @Override
         public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
               case MotionEvent.ACTION_MOVE:
                  return true;
               default:
                  break;
            }
            return true;
         }
      });

      //设置item动画
      DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
      defaultItemAnimator.setAddDuration(800);
      defaultItemAnimator.setRemoveDuration(1000);
      recyclerView.setItemAnimator(defaultItemAnimator);

   }

   public void refresh(){
      adapter.refresh();
      recyclerView.smoothScrollToPosition(adapter.getItemCount()-1);
      clearTiming();
   }

   /**
    * 定时清理礼物列表信息
    */
   private void clearTiming() {
      if (getChildCount() > 0){
         startTask();
      }

   }

   /**
    * 移除所有礼物
    */
   private void removeAllGiftView() {
      adapter.removeAll();
   }

   private class GiftListAdapter extends RecyclerView.Adapter<GiftViewHolder>{
      private Context context;
      ArrayList<ChatMessageData> messages;

      GiftListAdapter(Context context, ArrayList<ChatMessageData> dataArrayList){
         this.context = context;
         messages = dataArrayList;
      }

      @Override
      public GiftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         return new GiftViewHolder(LayoutInflater.from(context).inflate(R.layout.widget_gift_item, parent, false));
      }

      @Override
      public void onBindViewHolder(@NonNull GiftViewHolder holder, int position) {
         final ChatMessageData message = messages.get(position);
         holder.avatar.setImageResource(R.drawable.avatar1);
         show(holder.icon,holder.name,message);
         holder.icon_count.setText("X"+CustomMsgHelper.getInstance().getMsgGiftNum(message));
      }

      public void show(ImageView icon,TextView name,ChatMessageData message){
         String gift_id = CustomMsgHelper.getInstance().getMsgGiftId(message);
         GiftBean giftBean = GiftRepository.getGiftById(context,gift_id);
         StringBuilder builder = new StringBuilder();
         if (null != giftBean){
            builder.append(message.getFrom()).append(":").append("\n").append("sent ").append(giftBean.getName());
            icon.setImageResource(giftBean.getResource());
            icon.setTag(System.currentTimeMillis());
         }
         SpannableString span = new SpannableString(builder.toString());
         name.setText(span);
      }

      @Override
      public int getItemCount() {
         Log.e("gift_view","messages.size()" + messages.size());
         return messages.size();
      }


      public void removeAll(){
         ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
               for (int i = 0; i < messages.size(); i++) {
                  notifyItemRemoved(i);
               }
               notifyItemRangeRemoved(0,messages.size());
               messages.clear();
            }
         });
      }

      public void refresh(){
         int positionStart = messages.size();
         Log.e("room_refresh" , "positionStart1 " + positionStart);
         messages.addAll(CustomMsgHelper.getInstance().getGiftData(chatroomId));
         Log.e("room_refresh",messages.size()+" positionStart: " + positionStart);
         if (messages.size() > 0){
            ((Activity)context).runOnUiThread(new Runnable() {
               @Override
               public void run() {
                  notifyItemRangeInserted(positionStart,messages.size());
               }
            });
         }
      }
   }

   public static class GiftViewHolder extends RecyclerView.ViewHolder{
      TextView name;
      ImageView icon;
      TextView icon_count;
      ShapeableImageView avatar;

      public GiftViewHolder(View itemView) {
         super(itemView);
         avatar = itemView.findViewById(R.id.avatar);
         name = itemView.findViewById(R.id.nick_name);
         icon = itemView.findViewById(R.id.icon);
         icon_count = itemView.findViewById(R.id.gift_count);
      }
   }
}


