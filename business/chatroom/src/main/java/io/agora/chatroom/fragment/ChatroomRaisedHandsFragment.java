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
import io.agora.chatroom.adapter.ChatroomRaisedAdapter;
import io.agora.chatroom.model.ChatroomRaisedViewModel;
import manager.ChatroomMsgHelper;
import tools.bean.VRMicListBean;

public class ChatroomRaisedHandsFragment extends BaseListFragment<VRMicListBean.ApplyListBean> implements ChatroomRaisedAdapter.onActionListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private ChatroomRaisedViewModel handsViewModel;
    private ChatroomRaisedAdapter adapter;
    private List<VRMicListBean.ApplyListBean> dataList = new ArrayList<>();
    private int count;
    private int pageSize = 10;
    private String cursor = "";
    private int total;
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
        adapter = (ChatroomRaisedAdapter) mListAdapter;
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
        handsViewModel = new ViewModelProvider(this).get(ChatroomRaisedViewModel.class);
        handsViewModel.getRaisedObservable().observe(this,response -> {
            parseResource(response, new OnResourceParseCallback<VRMicListBean>() {
                @Override
                public void onSuccess(@Nullable VRMicListBean data) {
                    if (data != null){
                        count = dataList.size();
                        cursor = data.getCursor();
                        total = data.getTotal();
                        if (null != dataList && !dataList.containsAll(data.getApply_list())){
                            dataList.addAll(data.getApply_list());
                            adapter.addData(count,dataList);
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
        handsViewModel.getRaisedList(getActivity(), roomId,pageSize,cursor);
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

    }

    @Override
    public void onItemActionClick(View view,int position) {

    }

    public interface itemCountListener{
        void getItemCount(int count);
    }

    public void setItemCountChangeListener(itemCountListener listener){
        this.listener = listener;
    }
}
