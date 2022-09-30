package io.agora.chatroom.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import io.agora.buddy.tool.ToastTools;
import io.agora.chat.ChatClient;
import io.agora.chat.ChatRoom;
import io.agora.chatroom.R;
import io.agora.chatroom.adapter.ChatroomListAdapter;
import io.agora.chatroom.general.repositories.ProfileManager;
import io.agora.chatroom.model.PageViewModel;
import io.agora.chatroom.model.ChatroomViewModel;
import io.agora.config.RouterParams;
import io.agora.config.RouterPath;
import io.agora.secnceui.widget.encryption.ChatroomEncryptionInputDialog;
import manager.ChatroomConfigManager;
import manager.ChatroomMsgHelper;
import tools.bean.VRUserBean;
import tools.bean.VRoomBean;
import tools.bean.VRoomInfoBean;

public class ChatroomListFragment extends BaseChatroomListFragment<VRoomBean.RoomsBean> {
    private ChatroomListAdapter listAdapter;
    private ChatroomViewModel chatroomViewModel;
    private static final int pageSize = 10;
    private VRoomBean.RoomsBean roomBean;
    private itemCountListener listener;
    private String Cursor = "";
    private int index = 0;
    private List<VRoomBean.RoomsBean> dataList = new ArrayList<>();
    private String mPassWord;
    private int position;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        Log.e("chatroomViewModel"," current fragment: " + this);
        listAdapter = (ChatroomListAdapter) mListAdapter;
        listAdapter.setEmptyView(R.layout.chatroom_no_data_layout);
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
        chatroomViewModel.getDataList(getActivity(),pageSize,position-1,Cursor);
        Log.e("chatroomViewModel"," onResume " + this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("chatroomViewModel"," onPause " + this);
    }

    @Override
    protected void initViewModel() {
        super.initViewModel();
        chatroomViewModel = new ViewModelProvider(this).get(ChatroomViewModel.class);
        chatroomViewModel.getRoomObservable().observe(this, response ->{
            parseResource(response, new OnResourceParseCallback<>() {
                @Override
                public void onSuccess(@Nullable VRoomBean data) {
                    Cursor = data.getCursor();
                    dataList.addAll(data.getRooms());
                    listAdapter.setData(dataList);
                    if (dataList.size() > 0){
                        index = dataList.size()-1;
                    }
                    if (null != listener)
                        listener.getItemCount(data.getTotal());
                    finishRefresh();
                }
            });
        });

        chatroomViewModel.getCheckPasswordObservable().observe(this,response -> {
            parseResource(response, new OnResourceParseCallback<VRoomInfoBean>() {
                @Override
                public void onSuccess(@Nullable VRoomInfoBean data) {
                    goChatroomPage(roomBean,mPassWord,data);
                }

                @Override
                public void onError(int code, String message) {
                    super.onError(code, message);
                    ToastTools.show(getActivity(),"check password failed", Toast.LENGTH_SHORT);
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

    private void goChatroomPage(VRoomBean.RoomsBean roomBean,String password,VRoomInfoBean bean) {
        ARouter.getInstance()
                .build(RouterPath.ChatroomPath)
                .withSerializable(RouterParams.KEY_CHATROOM_INFO, roomBean)
                .withSerializable(RouterParams.KEY_CHATROOM_DETAILS_INFO,bean)
                .withString(RouterParams.KEY_CHATROOM_JOIN_PASSWORD,password)
                .navigation();
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
                .setOnClickListener(new ChatroomEncryptionInputDialog.OnClickBottomListener() {
                    @Override
                    public void onCancelClick() {}

                    @Override
                    public void onConfirmClick(@NonNull String password) {
                        mPassWord = password;
                        chatroomViewModel.checkPassword(getActivity(), roomBean.getRoom_id(), password);
                    }
                })
                .show(getActivity().getSupportFragmentManager(), "encryptionInputDialog");
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        chatroomViewModel.getDataList(getActivity(),pageSize,position,Cursor);
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
