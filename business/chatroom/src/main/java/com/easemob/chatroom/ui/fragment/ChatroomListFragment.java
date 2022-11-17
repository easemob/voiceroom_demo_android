package com.easemob.chatroom.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.easemob.chatroom.R;
import com.easemob.chatroom.general.repositories.ProfileManager;
import com.easemob.chatroom.model.ChatroomViewModel;
import com.easemob.chatroom.ui.adapter.ChatroomListAdapter;
import com.alibaba.android.arouter.launcher.ARouter;

import com.easemob.baseui.adapter.RoomBaseRecyclerViewAdapter;
import com.easemob.baseui.general.callback.OnResourceParseCallback;
import com.easemob.buddy.tool.ThreadManager;
import com.easemob.buddy.tool.ToastTools;
import com.easemob.config.RouterParams;
import com.easemob.config.RouterPath;
import com.easemob.secnceui.widget.encryption.ChatroomEncryptionInputDialog;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import manager.ChatroomHelper;
import tools.bean.VRUserBean;
import tools.bean.VRoomBean;

public class ChatroomListFragment extends BaseChatroomListFragment<VRoomBean.RoomsBean> {
    private ChatroomListAdapter listAdapter;
    private ChatroomViewModel chatroomViewModel;
    private static final int pageSize = 10;
    private VRoomBean.RoomsBean roomBean;
    private itemCountListener listener;
    private String Cursor = "";
    private String mPassWord;
    private int position;
    private boolean isRefreshing = false;
    private boolean isLoadingNextPage = false;
    private boolean isEnd;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        Log.e("chatroomViewModel"," current fragment: " + this);
        listAdapter = (ChatroomListAdapter) mListAdapter;
        listAdapter.setEmptyView(R.layout.chatroom_no_data_layout);

        float offsetPx = getResources().getDimension(R.dimen.space_84dp);
        BottomOffsetDecoration bottomOffsetDecoration = new BottomOffsetDecoration((int) offsetPx);
        mRecyclerView.addItemDecoration(bottomOffsetDecoration);
    }

    @Override
    protected RoomBaseRecyclerViewAdapter<VRoomBean.RoomsBean> initAdapter() {
        RoomBaseRecyclerViewAdapter adapter = new ChatroomListAdapter();
        return adapter;
    }

    @Override
    protected void initArgument() {
        super.initArgument();
        if (null != getArguments())
            position = getArguments().getInt("position",0);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != getArguments())
            position = getArguments().getInt("position",0);
        reset();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void initViewModel() {
        super.initViewModel();
        chatroomViewModel = new ViewModelProvider(this).get(ChatroomViewModel.class);
        chatroomViewModel.getRoomObservable().observe(this, response ->{
            parseResource(response, new OnResourceParseCallback<>() {
                @Override
                public void onSuccess(@Nullable VRoomBean data) {
                    if (data != null){
                        Cursor = data.getCursor();
                        if (TextUtils.isEmpty(Cursor)){
                            isEnd = true;
                        }
                        if (isRefreshing){
                            listAdapter.setData(data.getRooms());
                        }else {
                            listAdapter.addData(data.getRooms());
                        }
                        if (null != listener)
                            listener.getItemCount(data.getTotal());
                        finishRefresh();
                        isRefreshing = false;
                    }
                }
            });
        });

        chatroomViewModel.getCheckPasswordObservable().observe(this,response -> {
            parseResource(response, new OnResourceParseCallback<Boolean>() {
                @Override
                public void onSuccess(@Nullable Boolean isCheck) {
                    if (Boolean.TRUE.equals(isCheck)){
                        goChatroomPage(roomBean,mPassWord);
                    }else {
                        dismissLoading();
                        ToastTools.show(requireActivity(),getString(R.string.chatroom_check_password), Toast.LENGTH_SHORT);
                    }
                }

                @Override
                public void onError(int code, String message) {
                    super.onError(code, message);
                    dismissLoading();
                    ToastTools.show(requireActivity(),getString(R.string.chatroom_check_password), Toast.LENGTH_SHORT);
                }
            });
        });

    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                                    if (!isEnd){
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
                Log.e("ListFragment1","Cursor: " + Cursor);
                chatroomViewModel.getDataList(getActivity(),pageSize,position-1,Cursor);
                isLoadingNextPage = false;
            }
        });
    }


    @Override
    public void onItemClick(View view, int position) {
        roomBean = listAdapter.getItem(position);
        checkPrivate();
    }

    private void goChatroomPage(VRoomBean.RoomsBean roomBean) {
        ARouter.getInstance()
                .build(RouterPath.ChatroomPath)
                .withSerializable(RouterParams.KEY_CHATROOM_INFO, roomBean)
                .navigation();
        dismissLoading();
    }

    private void goChatroomPage(VRoomBean.RoomsBean roomBean,String password) {
        ARouter.getInstance()
                .build(RouterPath.ChatroomPath)
                .withSerializable(RouterParams.KEY_CHATROOM_INFO, roomBean)
                .withString(RouterParams.KEY_CHATROOM_JOIN_PASSWORD,password)
                .navigation();
        dismissLoading();
    }

    private void checkPrivate(){
        ThreadManager.getInstance().runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (roomBean.isIs_private()){
                    showDialog();
                }else {
                    goChatroomPage(roomBean);
                }
            }
        });

    }

    public void showDialog(){
        new ChatroomEncryptionInputDialog()
                .leftText(getActivity().getString(R.string.chatroom_cancel))
                .rightText(getActivity().getString(R.string.chatroom_confirm))
                .setDialogCancelable(true)
                .setOnClickListener(new ChatroomEncryptionInputDialog.OnClickBottomListener() {
                    @Override
                    public void onCancelClick() {
                        dismissLoading();
                    }

                    @Override
                    public void onConfirmClick(@NonNull String password) {
                        mPassWord = password;
                        chatroomViewModel.checkPassword(getActivity(), roomBean.getRoom_id(), password);
                        showLoading(false);
                    }
                })
                .show(getActivity().getSupportFragmentManager(), "encryptionInputDialog");
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        reset();
        Log.e("ChatroomListFragment","onRefresh" + listAdapter.getItemCount());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public interface itemCountListener{
        void getItemCount(int count);
    }

    public void SetItemCountChangeListener(itemCountListener listener){
        this.listener = listener;
    }

    private void reset(){
        isRefreshing = true;
        Cursor = "";
        chatroomViewModel.getDataList(getActivity(),pageSize,position-1,Cursor);
    }

    static class BottomOffsetDecoration extends RecyclerView.ItemDecoration {
        private int mBottomOffset;

        public BottomOffsetDecoration(int bottomOffset) {
            mBottomOffset = bottomOffset;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int dataSize = state.getItemCount();
            int position = parent.getChildAdapterPosition(view);
            if (dataSize > 0 && position == dataSize - 1) {
                outRect.set(0, 0, 0, mBottomOffset);
            } else {
                outRect.set(0, 0, 0, 0);
            }

        }
    }

}
