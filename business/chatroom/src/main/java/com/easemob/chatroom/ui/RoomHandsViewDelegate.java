package com.easemob.chatroom.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;

import com.easemob.chatroom.R;
import com.easemob.chatroom.general.net.ChatroomHttpManager;

import java.util.Map;
import com.easemob.buddy.tool.ToastTools;
import com.easemob.chatroom.ui.dialog.ChatroomHandsDialog;
import com.easemob.chatroom.general.repositories.ProfileManager;
import com.easemob.secnceui.ui.common.CommonSheetAlertDialog;
import com.easemob.secnceui.widget.primary.ChatPrimaryMenuView;
import tools.ValueCallBack;

public class RoomHandsViewDelegate {
    private FragmentActivity activity;
    private ChatroomHandsDialog dialog;
    private String roomId;
    private ChatPrimaryMenuView chatPrimaryMenuView;
    private String owner;
    private boolean isRequest;

    RoomHandsViewDelegate(FragmentActivity activity,ChatPrimaryMenuView view){
        this.activity = activity;
        this.chatPrimaryMenuView = view;
    }

    public static RoomHandsViewDelegate getInstance(FragmentActivity activity, ChatPrimaryMenuView view){
        return new RoomHandsViewDelegate(activity,view);
    }

    public void onRoomDetails(String roomId,String owner){
        this.roomId = roomId;
        this.owner = owner;
        Log.e("onRoomDetails","owner: " + owner);
        Log.e("onRoomDetails","getUid: " + ProfileManager.getInstance().getProfile().getUid());
    }

    public boolean isOwner(){
        return ProfileManager.getInstance().getProfile().getUid().equals(owner);
    }

    public void showOwnerHandsDialog() {
        activity.getSupportFragmentManager();
        dialog = (ChatroomHandsDialog) activity.getSupportFragmentManager().findFragmentByTag("room_hands");
        if(dialog == null) {
            dialog = ChatroomHandsDialog.getNewInstance();
        }
        Bundle bundle = new Bundle();
        bundle.putString("roomId",roomId);
        dialog.setArguments(bundle);
        dialog.show(activity.getSupportFragmentManager(), "room_hands");
        chatPrimaryMenuView.setShowHandStatus(false, false);
    }

    public void update(int index){
        if (dialog != null){
            dialog.update(index);
        }
    }

    // 用户点击上台
    public void onUserClickOnStage(int micIndex) {
        if (isRequest) {
            ToastTools.show(activity, activity.getString(R.string.chatroom_mic_submit_sent), Toast.LENGTH_SHORT);
        }else {
            showMemberHandsDialog(micIndex);
        }
    }

    public void resetRequest(){
        isRequest = false;
    }

    public void showMemberHandsDialog(int micIndex){
       new CommonSheetAlertDialog()
               .contentText(isRequest? activity.getString(R.string.chatroom_cancel_request_speak):activity.getString(R.string.chatroom_request_speak))
               .rightText(activity.getString(R.string.chatroom_confirm))
               .leftText(activity.getString(R.string.chatroom_cancel))
               .setOnClickListener(new CommonSheetAlertDialog.OnClickBottomListener() {
                   @Override
                   public void onConfirmClick() {
                       if (isRequest){
                           ChatroomHttpManager.getInstance().cancelSubmitMic(roomId, new ValueCallBack<Boolean>() {
                               @Override
                               public void onSuccess(Boolean var1) {
                                   ToastTools.show(activity,activity.getString(R.string.chatroom_mic_cancel_apply_success), Toast.LENGTH_SHORT);
                                   chatPrimaryMenuView.setShowHandStatus(false,false);
                                   isRequest = false;
                               }

                               @Override
                               public void onError(int code, String desc) {
                                   ToastTools.show(activity,activity.getString(R.string.chatroom_mic_cancel_apply_fail), Toast.LENGTH_SHORT);
                               }
                           });
                       }else {
                           ChatroomHttpManager.getInstance().submitMic(roomId, micIndex, new ValueCallBack<Boolean>() {
                               @Override
                               public void onSuccess(Boolean var1) {
                                   ToastTools.show(activity,activity.getString(R.string.chatroom_mic_apply_success), Toast.LENGTH_SHORT);
                                   chatPrimaryMenuView.setShowHandStatus(false,true);
                                   isRequest = true;
                               }

                               @Override
                               public void onError(int code, String desc) {
                                   ToastTools.show(activity,activity.getString(R.string.chatroom_mic_apply_fail), Toast.LENGTH_SHORT);
                               }
                           });
                       }
                   }

                   @Override
                   public void onCancelClick() {

                   }
               })
               .show(activity.getSupportFragmentManager(), "room_hands_apply");
    }

    public void check(Map<String,String> map){
        if (dialog != null)
        dialog.check(map);
    }

}
