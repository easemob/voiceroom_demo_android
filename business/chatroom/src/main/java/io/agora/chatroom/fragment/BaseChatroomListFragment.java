package io.agora.chatroom.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import io.agora.baseui.BaseListFragment;
import io.agora.baseui.adapter.RoomBaseRecyclerViewAdapter;
import io.agora.baseui.general.callback.OnResourceParseCallback;
import io.agora.baseui.general.enums.Status;
import io.agora.baseui.general.net.Resource;
import io.agora.chatroom.R;
import io.agora.chatroom.adapter.ChatroomListAdapter;
import tools.bean.VRoomBean;


public class BaseChatroomListFragment<T> extends BaseListFragment<T> implements SwipeRefreshLayout.OnRefreshListener{
   private SwipeRefreshLayout swipeRefreshLayout;
   private RecyclerView recyclerView;


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

   /**
    //     * Parse Resource<T>
    //     * @param response
    //     * @param callback
    //     * @param <T>
    //     */
    public <T> void parseResource(Resource<T> response, @NonNull OnResourceParseCallback<T> callback) {
        if(response == null) {
            return;
        }
        if(response.status == Status.SUCCESS) {
            callback.onHideLoading();
            callback.onSuccess(response.data);
        }else if(response.status == Status.ERROR) {
            callback.onHideLoading();
            if(!callback.hideErrorMsg) {
                Log.e("parseResource ",response.getMessage());
            }
            callback.onError(response.errorCode, response.getMessage());
        }else if(response.status == Status.LOADING) {
            callback.onLoading(response.data);
        }
    }
}
