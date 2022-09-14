package io.agora.chatroom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import io.agora.baseui.BaseListFragment;
import io.agora.baseui.adapter.RoomBaseRecyclerViewAdapter;
import io.agora.chatroom.R;
import io.agora.chatroom.adapter.ChatroomListAdapter;


public class BaseChatRoomListFragment<T> extends BaseListFragment<T> implements SwipeRefreshLayout.OnRefreshListener{
   private SwipeRefreshLayout swipeRefreshLayout;
   private RecyclerView recyclerView;

   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      return super.onCreateView(inflater, container, savedInstanceState);
   }

   @Override
   protected int getLayoutId() {
      return R.layout.fragment_room_list_layout;
   }

   @Override
   protected void initView(Bundle savedInstanceState) {
      super.initView(savedInstanceState);
      swipeRefreshLayout = findViewById(R.id.swipeLayout);
      recyclerView = findViewById(R.id.recycler);
   }

   @Override
   protected void initListener() {
      super.initListener();
      swipeRefreshLayout.setOnRefreshListener(this);
   }

   @Override
   protected void initData() {
      super.initData();
   }

   @Override
   protected RecyclerView initRecyclerView() {
      return findViewById(R.id.recycler);
   }

   @Override
   protected RoomBaseRecyclerViewAdapter<T> initAdapter() {
      RoomBaseRecyclerViewAdapter adapter = new ChatroomListAdapter();
      return adapter;
   }

   @Override
   public void onItemClick(View view, int position) {

   }

   @Override
   public void onRefresh() {

   }

   protected void finishRefresh() {
      if(swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
         swipeRefreshLayout.setRefreshing(false);
      }
   }

}
