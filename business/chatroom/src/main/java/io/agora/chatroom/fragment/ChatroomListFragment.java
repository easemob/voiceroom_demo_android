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

import java.util.List;
import io.agora.CallBack;
import io.agora.baseui.general.ThreadManager;
import io.agora.baseui.general.callback.OnResourceParseCallback;
import io.agora.baseui.general.enums.Status;
import io.agora.baseui.general.net.Resource;
import io.agora.chat.ChatClient;
import io.agora.chatroom.R;
import io.agora.chatroom.adapter.ChatroomListAdapter;
import io.agora.chatroom.general.repositories.ProfileManager;
import io.agora.chatroom.model.PageViewModel;
import io.agora.chatroom.model.ChatroomViewModel;
import io.agora.config.RouterParams;
import io.agora.config.RouterPath;
import tools.bean.VRUserBean;
import tools.bean.VRoomBean;

public class ChatroomListFragment extends BaseChatroomListFragment<VRoomBean.RoomsBean> {
    private ChatroomListAdapter listAdapter;
    private ChatroomViewModel chatroomViewModel;
    private PageViewModel pageViewModel;
    private static final int pageSize = 10;
    private VRoomBean.RoomsBean roomBean;

    public ChatroomListFragment(){

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
        chatroomViewModel = new ViewModelProvider(this).get(ChatroomViewModel.class);
        chatroomViewModel.getRoomObservable().observe(this, response ->{
            parseResource(response, new OnResourceParseCallback<List<VRoomBean.RoomsBean>>() {
                @Override
                public void onSuccess(@Nullable List<VRoomBean.RoomsBean> data) {
                    Log.e("viewModel"," -+- " + data.size());
                    listAdapter.addData(data);
                    chatroomViewModel.clearRegisterInfo();
                }
            });
        });

        pageViewModel = new ViewModelProvider(mContext).get(PageViewModel.class);
        pageViewModel.getPageSelect().observe(this, page -> {
            Log.e("viewModel","getPageSelect -+- " + page);
            chatroomViewModel.getDataList(mContext,pageSize,page);
        });

        chatroomViewModel.getJoinObservable().observe(this,response ->{
            parseResource(response, new OnResourceParseCallback<Boolean>(true) {
                @Override
                public void onSuccess(@Nullable Boolean data) {
                    if (ChatClient.getInstance().isLoggedIn()){
                        ARouter.getInstance()
                                .build(RouterPath.ChatroomPath)
                                .withInt(RouterParams.KEY_CHATROOM_TYPE, roomBean.getType())
                                .withSerializable(RouterParams.KEY_CHATROOM_INFO, roomBean)
                                .navigation();
                    }
                }

                @Override
                public void onError(int code, String message) {
                    Log.e("ChatroomListFragment","Join onError" + "code: "+ code + " desc: " + message);
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
    }

    @Override
    public void onItemClick(View view, int position) {
        roomBean = listAdapter.getItem(position);
        if (!ChatClient.getInstance().isLoggedIn()){
            VRUserBean data = ProfileManager.getInstance().getProfile();
            Log.d("ChatroomListFragment","chat_uid: " + data.getChat_uid());
            Log.d("ChatroomListFragment","im_token: " + data.getIm_token());
            ChatClient.getInstance().loginWithAgoraToken(data.getChat_uid(), data.getIm_token(), new CallBack() {
                @Override
                public void onSuccess() {
                    Log.d("ChatroomListFragment","Login success");
                    checkPrivate();
                }

                @Override
                public void onError(int i, String s) {
                    Log.e("ChatroomListFragment","Login onError" + "code: "+i + " desc: " + s);
                }
            });
        }else {
            checkPrivate();
        }
    }

    private void checkPrivate(){
        ThreadManager.getInstance().runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (roomBean.isIs_private()){
                    // TODO: 2022/9/15  //弹窗 输入密码
                }else {
                    chatroomViewModel.joinRoom(getActivity(),roomBean.getRoom_id());
                }
            }
        });

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
     * @param response
     * @param callback
     * @param <T>
     */
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
