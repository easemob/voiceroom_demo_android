package io.agora.chatroom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.agora.baseui.BaseListFragment;
import io.agora.baseui.adapter.RoomBaseRecyclerViewAdapter;
import io.agora.baseui.general.callback.OnResourceParseCallback;
import io.agora.buddy.tool.LogToolsKt;
import io.agora.buddy.tool.ThreadManager;
import io.agora.chatroom.R;
import io.agora.chatroom.adapter.ChatroomInviteAdapter;
import io.agora.chatroom.general.net.HttpManager;
import io.agora.chatroom.model.ChatroomInviteViewModel;
import tools.ValueCallBack;
import tools.bean.VMemberBean;
import tools.bean.VRoomUserBean;

public class ChatroomInviteHandsFragment extends BaseListFragment<VMemberBean> implements ChatroomInviteAdapter.onActionListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private ChatroomInviteViewModel handsViewModel;
    private List<VMemberBean> dataList = new ArrayList<>();
    private ChatroomInviteAdapter adapter;
    private int pageSize = 10;
    private String cursor = "";
    private itemCountListener listener;
    private String roomId;
    private static final String TAG = "ChatroomInviteHandsFragment";
    private Map<String,Boolean> map = new HashMap<>();
    private boolean isRefreshing = false;
    private boolean isLoadingNextPage = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        adapter = (ChatroomInviteAdapter) mListAdapter;
        adapter.setEmptyView(R.layout.chatroom_no_data_layout);
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
        handsViewModel = new ViewModelProvider(this).get(ChatroomInviteViewModel.class);
        handsViewModel.getInviteObservable().observe(this,response ->{
            parseResource(response, new OnResourceParseCallback<VRoomUserBean>() {
                @Override
                public void onSuccess(@Nullable VRoomUserBean data) {
                    if (data != null){
                        cursor = data.getCursor();
                        int total = data.getTotal();
                        if (isRefreshing){
                            adapter.setData(data.getMembers());
                        }else {
                            adapter.addData(data.getMembers());
                        }
                        if (null != listener)
                            listener.getItemCount(total);
                        finishRefresh();
                        isRefreshing = false;
                    }
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
                                pullData();
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
                handsViewModel.getInviteList(getActivity(),roomId,pageSize,cursor);
                isLoadingNextPage = false;
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        handsViewModel.getInviteList(getActivity(),roomId,pageSize,cursor);
        for (VMemberBean vMemberBean : dataList) {
            if (map.containsKey(vMemberBean.getUid())){
                adapter.setInvited(vMemberBean.getUid(), Boolean.TRUE.equals(map.get(vMemberBean.getUid())));
            }
        }
    }

    @Override
    protected RecyclerView initRecyclerView() {
        return findViewById(R.id.list);
    }

    @Override
    protected RoomBaseRecyclerViewAdapter<VMemberBean> initAdapter() {
        RoomBaseRecyclerViewAdapter adapter = new ChatroomInviteAdapter();
        return adapter;
    }

    @Override
    public void onItemActionClick(View view, int position,String uid) {
        HttpManager.getInstance(getActivity()).invitationMic(roomId, uid, new ValueCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean var1) {
                LogToolsKt.logE("onActionClick Invite onSuccess " + uid, TAG);
                ThreadManager.getInstance().runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setInvited(uid,true);
                        map.put(uid,true);
                    }
                });
            }

            @Override
            public void onError(int code, String desc) {
                LogToolsKt.logE("onActionClick Invite onError " + code + " "+ desc, TAG);
            }
        });
    }

    @Override
    public void onRefresh() {
        reset();
        isRefreshing = true;
        handsViewModel.getInviteList(getActivity(),roomId,pageSize,cursor);
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    protected void finishRefresh() {
        if(refreshLayout != null && refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    private void reset(){
        adapter.clearData();
        cursor = "";
    }


    public interface itemCountListener{
        void getItemCount(int count);
    }

    public void setItemCountChangeListener(itemCountListener listener){
        this.listener = listener;
    }
}
