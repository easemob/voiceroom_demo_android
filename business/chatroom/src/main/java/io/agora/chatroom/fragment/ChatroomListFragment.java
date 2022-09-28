package io.agora.chatroom.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import com.alibaba.android.arouter.launcher.ARouter;
import java.util.ArrayList;
import java.util.List;
import io.agora.CallBack;
import io.agora.ValueCallBack;
import io.agora.baseui.general.callback.OnResourceParseCallback;
import io.agora.buddy.tool.ThreadManager;
import io.agora.chat.ChatClient;
import io.agora.chat.ChatRoom;
import io.agora.chatroom.R;
import io.agora.chatroom.adapter.ChatroomListAdapter;
import io.agora.chatroom.general.repositories.ProfileManager;
import io.agora.chatroom.model.PageViewModel;
import io.agora.chatroom.model.ChatroomViewModel;
import io.agora.config.RouterParams;
import io.agora.config.RouterPath;
import manager.ChatroomConfigManager;
import tools.bean.VRUserBean;
import tools.bean.VRoomBean;

public class ChatroomListFragment extends BaseChatroomListFragment<VRoomBean.RoomsBean> {
    private ChatroomListAdapter listAdapter;
    private ChatroomViewModel chatroomViewModel;
    private PageViewModel pageViewModel;
    private static final int pageSize = 10;
    private VRoomBean.RoomsBean roomBean;
    private itemCountListener listener;
    private int mCurrentPage = 0;
    private String Cursor = "";
    private int index = 0;
    private List<VRoomBean.RoomsBean> dataList = new ArrayList<>();

    public ChatroomListFragment(){}

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
        chatroomViewModel = new ViewModelProvider(this).get(ChatroomViewModel.class);
        chatroomViewModel.getRoomObservable().observe(this, response ->{
            parseResource(response, new OnResourceParseCallback<>() {
                @Override
                public void onSuccess(@Nullable VRoomBean data) {
                    Log.e("chatroomViewModel"," onSuccess " + data.getRooms().size());
                    Cursor = data.getCursor();
                    dataList.addAll(data.getRooms());
                    Log.e("chatroomViewModel"," index " + index);
                    Log.e("chatroomViewModel"," dataList " + dataList.size());
                    listAdapter.setData(dataList);
                    if (dataList.size() > 0){
                        index = dataList.size()-1;
                    }
                    chatroomViewModel.clearRegisterInfo();
                    if (null != listener)
                        listener.getItemCount(data.getTotal());
                    finishRefresh();
                }
            });
        });

        pageViewModel = new ViewModelProvider(getActivity()).get(PageViewModel.class);
        pageViewModel.getPageSelect().observe(this, page -> {
            Log.e("pageViewModel","getPageSelect " + page);
            if (listAdapter.getData() != null && mCurrentPage != page && listAdapter.getData().size() >0){
                dataList.clear();
                listAdapter.clearData();
                index = 0;
                Log.e("pageViewModel","clearData ");
            }
            chatroomViewModel.getDataList(getActivity(),pageSize,page,Cursor);
            mCurrentPage = page;
            Log.e("pageViewModel","mCurrentPage " + mCurrentPage);
        });

    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    public void onItemClick(View view, int position) {
        roomBean = listAdapter.getItem(position);
        Log.e("onItemClick","getOwnerUid: " + roomBean.getOwnerUid());
        if (!ChatClient.getInstance().isLoggedIn()){
            VRUserBean data = ProfileManager.getInstance().getProfile();
            Log.d("ChatroomListFragment","chat_uid: " + data.getChat_uid());
            Log.d("ChatroomListFragment","im_token: " + data.getIm_token());
            ChatroomConfigManager.getInstance().login(data.getChat_uid(), data.getIm_token(), new CallBack() {
                @Override
                public void onSuccess() {
                    Log.d("ChatroomListFragment","Login success");
                    checkPrivate();
                }

                @Override
                public void onError(int code, String msg) {
                    Log.e("ChatroomListFragment", "Login onError code:" + code + " desc: " + msg);
                    // TODO: 2022/9/16  202
                    checkPrivate();
                }
            });
        }else {
            checkPrivate();
        }
    }

    private void goChatroomPage(VRoomBean.RoomsBean roomBean) {
        ARouter.getInstance()
                .build(RouterPath.ChatroomPath)
                .withSerializable(RouterParams.KEY_CHATROOM_INFO, roomBean)
                .navigation();
    }

    private void checkPrivate(){
        ThreadManager.getInstance().runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (roomBean.isIs_private()){
                    // TODO: 2022/9/15  //弹窗 输入密码
                    goChatroomPage(roomBean);
                }else {
                    ChatroomConfigManager.getInstance().joinRoom(roomBean.getChatroom_id(), new ValueCallBack<ChatRoom>() {
                        @Override
                        public void onSuccess(ChatRoom chatRoom) {
                            goChatroomPage(roomBean);
                        }

                        @Override
                        public void onError(int code, String desc) {
                            goChatroomPage(roomBean);
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        chatroomViewModel.getDataList(getActivity(),pageSize,mCurrentPage,Cursor);
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

}
