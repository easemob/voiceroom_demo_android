package io.agora.chatroom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import java.util.ArrayList;
import java.util.List;
import io.agora.baseui.BaseListFragment;
import io.agora.baseui.adapter.RoomBaseRecyclerViewAdapter;
import io.agora.baseui.general.callback.OnResourceParseCallback;
import io.agora.chatroom.R;
import io.agora.chatroom.adapter.ChatroomInviteAdapter;
import io.agora.chatroom.model.ChatroomInviteViewModel;
import tools.bean.VMemberBean;
import tools.bean.VRoomUserBean;

public class ChatroomInviteHandsFragment extends BaseListFragment<VMemberBean> implements ChatroomInviteAdapter.onActionListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private ChatroomInviteViewModel handsViewModel;
    private List<VMemberBean> dataList = new ArrayList<>();
    private ChatroomInviteAdapter adapter;
    private int count;
    private int pageSize = 10;
    private String cursor = "";
    private String total;
    private itemCountListener listener;
    private String roomId;

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
                        count = dataList.size();
                        cursor = data.getCursor();
                        int total = data.getTotal();
                        for (VMemberBean user : data.getMembers()) {
                            if (null != dataList && !dataList.contains(user)){
                                dataList.addAll(data.getMembers());
                                adapter.addData(count,dataList);
                            }
                        }
                        if (null != listener)
                            listener.getItemCount(total);
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
    }

    @Override
    protected void initData() {
        super.initData();
        handsViewModel.getInviteList(getActivity(),roomId,pageSize,cursor);
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
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onRefresh() {

    }

    public interface itemCountListener{
        void getItemCount(int count);
    }

    public void setItemCountChangeListener(itemCountListener listener){
        this.listener = listener;
    }
}
