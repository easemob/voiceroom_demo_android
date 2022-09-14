package io.agora.chatroom.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import java.util.List;
import io.agora.baseui.general.callback.OnResourceParseCallback;
import io.agora.baseui.general.net.Resource;
import io.agora.chatroom.R;
import io.agora.chatroom.adapter.ChatroomListAdapter;
import io.agora.chatroom.model.PageViewModel;
import io.agora.chatroom.model.RoomViewModel;
import tools.bean.VRoomBean;

public class ChatRoomListFragment extends BaseChatRoomListFragment<VRoomBean.RoomsBean> {
    private ChatroomListAdapter listAdapter;
    private RoomViewModel roomViewModel;
    private PageViewModel pageViewModel;

    public ChatRoomListFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        listAdapter = (ChatroomListAdapter) mListAdapter;
        listAdapter.setEmptyView(R.layout.chatroom_no_data_layout);
    }

    @Override
    protected void initViewModel() {
        super.initViewModel();
        roomViewModel = new ViewModelProvider(this).get(RoomViewModel.class);
        roomViewModel.getRoomObservable().observe(this,response ->{
            parseResource(response, new OnResourceParseCallback<List<VRoomBean.RoomsBean>>() {
                @Override
                public void onSuccess(@Nullable List<VRoomBean.RoomsBean> data) {
                    Log.e("viewModel","6 -+- " + data.size());
                    listAdapter.setData(data);
                    roomViewModel.clearRegisterInfo();
                }
            });
        });

        pageViewModel = new ViewModelProvider(mContext).get(PageViewModel.class);
        pageViewModel.getPageSelect().observe(this, page -> {
            Log.e("viewModel","getPageSelect -+- " + page);
            if(page == 0) {
                roomViewModel.getAllDataList();
            }else if (page > 0){
                roomViewModel.getDataList(page);
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
        roomViewModel.getAllDataList();
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    public void onItemClick(View view, int position) {
        VRoomBean.RoomsBean roomBean = listAdapter.getItem(position);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Parse Resource<T>
     * @param <T>
     * @param response
     * @param callback
     */
    public <T> void parseResource(Resource<List<VRoomBean.RoomsBean>> response, @NonNull OnResourceParseCallback<List<VRoomBean.RoomsBean>> callback) {
        if(mContext != null) {
            mContext.parseResource(response, callback);
        }
    }

}
