package com.easemob.chatroom.ui.fragment;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.easemob.buddy.tool.LogToolsKt;
import com.easemob.chatroom.R;
import com.easemob.chatroom.general.net.ChatroomHttpManager;
import com.easemob.chatroom.model.ChatroomRaisedViewModel;
import com.easemob.chatroom.ui.adapter.ChatroomRaisedAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easemob.baseui.BaseListFragment;
import com.easemob.baseui.adapter.RoomBaseRecyclerViewAdapter;
import com.easemob.baseui.general.callback.OnResourceParseCallback;
import com.easemob.buddy.tool.ThreadManager;
import com.easemob.buddy.tool.ToastTools;
import tools.ValueCallBack;
import tools.bean.VRMicListBean;

public class ChatroomRaisedHandsFragment extends BaseListFragment<VRMicListBean.ApplyListBean> implements ChatroomRaisedAdapter.onActionListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private ChatroomRaisedViewModel handsViewModel;
    private ChatroomRaisedAdapter adapter;
    private List<VRMicListBean.ApplyListBean> dataList = new ArrayList<>();
    private int pageSize = 10;
    private String cursor = "";
    private itemCountListener listener;
    private String roomId;
    private static final String TAG = "ChatroomRaisedHandsFragment";
    private Map<String,Boolean> map = new HashMap<>();
    private boolean isRefreshing = false;
    private boolean isLoadingNextPage = false;
    private View emptyView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        emptyView = getLayoutInflater().inflate(R.layout.chatroom_no_data_layout, container,false);
        TextView textView = emptyView.findViewById(R.id.content_item);
        textView.setText(getString(R.string.chatroom_empty_raised_hands));
        LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        emptyView.setLayoutParams(params);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.chatroom_hands_list_layout;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        recyclerView = findViewById(R.id.list);
        refreshLayout = findViewById(R.id.swipeLayout);
        adapter = (ChatroomRaisedAdapter) mListAdapter;
        if (emptyView == null){
            adapter.setEmptyView(R.layout.chatroom_no_data_layout);
        }else {
            adapter.setEmptyView(emptyView);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        reset();
    }

    @Override
    public void initArgument() {
        super.initArgument();
        if (getArguments() != null && getArguments().containsKey("roomId"))
            roomId = getArguments().getString("roomId");
    }

    @Override
    protected void initViewModel() {
        super.initViewModel();
        handsViewModel = new ViewModelProvider(this).get(ChatroomRaisedViewModel.class);
        handsViewModel.getRaisedObservable().observe(this,response -> {
            parseResource(response, new OnResourceParseCallback<VRMicListBean>() {
                @Override
                public void onSuccess(@Nullable VRMicListBean data) {
                    if (data != null){
                        int total = data.getTotal();
                        cursor = data.getCursor();
                        if (isRefreshing){
                            adapter.setData(data.getApply_list());
                        }else {
                            adapter.addData(data.getApply_list());
                        }
                        if (null != listener)
                            listener.getItemCount(total);
                        finishRefresh();
                        isRefreshing = false;
                        if (adapter.getData() != null){
                            for (VRMicListBean.ApplyListBean applyListBean : adapter.getData()) {
                                if (map.containsKey(applyListBean.getMember().getUid())){
                                    adapter.setAccepted(applyListBean.getMember().getUid(), Boolean.TRUE.equals(map.get(applyListBean.getMember().getUid())));
                                }
                            }
                        }
                    }
                }

                @Override
                public void onError(int code, String message) {
                    super.onError(code, message);
                }
            });
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        adapter.setOnActionListener(this);
        refreshLayout.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastVisibleItemPosition = lm.findLastVisibleItemPosition();
                int totalCount = lm.getItemCount();
                if (lastVisibleItemPosition == totalCount - 1 && !isLoadingNextPage && !isRefreshing) {
                    // 在前面addLoadItem后，itemCount已经变化
                    // 增加一层判断，确保用户是滑到了正在加载的地方，才加载更多
                    int findLastVisibleItemPosition = lm.findLastVisibleItemPosition();
                    if (findLastVisibleItemPosition == lm.getItemCount() - 1) {
                        ThreadManager.getInstance().runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                isLoadingNextPage = true;
                                if (!TextUtils.isEmpty(cursor)){
                                    pullData();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void pullData() {
        ThreadManager.getInstance().runOnMainThread(new Runnable() {
            @Override
            public void run() {
                handsViewModel.getRaisedList(getActivity(), roomId,pageSize,cursor);
                isLoadingNextPage = false;
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected RecyclerView initRecyclerView() {
        return findViewById(R.id.list);
    }

    @Override
    protected RoomBaseRecyclerViewAdapter<VRMicListBean.ApplyListBean> initAdapter() {
        RoomBaseRecyclerViewAdapter adapter = new ChatroomRaisedAdapter();
        return adapter;
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onRefresh() {
        reset();
    }

    protected void finishRefresh() {
        if(refreshLayout != null && refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    public void reset(){
        cursor = "";
        isRefreshing = true;
        handsViewModel.getRaisedList(getActivity(), roomId,pageSize,cursor);
    }

    @Override
    public void onItemActionClick(View view,int index,String uid) {
        ChatroomHttpManager.getInstance().applySubmitMic(roomId, uid, index, new ValueCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean var1) {
                LogToolsKt.logE("onActionClick apply onSuccess " + uid, TAG);
                ThreadManager.getInstance().runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setAccepted(uid,true);
                        map.put(uid,true);
                        if (getActivity() != null)
                        ToastTools.show(getActivity(),getString(R.string.chatroom_agree_success), Toast.LENGTH_SHORT);
                    }
                });
            }

            @Override
            public void onError(int code, String desc) {
                LogToolsKt.logE("onActionClick apply onError " + code + " "+ desc, TAG);
                if (getActivity() != null)
                ToastTools.show(getActivity(),getString(R.string.chatroom_agree_fail), Toast.LENGTH_SHORT);
            }
        });
    }

    public interface itemCountListener{
        void getItemCount(int count);
    }

    public void setItemCountChangeListener(itemCountListener listener){
        this.listener = listener;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        map.clear();
    }
}
