package io.agora.chatroom.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import io.agora.buddy.tool.ToastTools;
import io.agora.chatroom.R;
import io.agora.chatroom.fragment.ChatroomHandsDialog;
import io.agora.chatroom.general.net.HttpManager;
import io.agora.chatroom.general.repositories.ProfileManager;
import io.agora.secnceui.ui.common.CommonSheetAlertDialog;
import io.agora.secnceui.widget.primary.ChatPrimaryMenuView;
import tools.ValueCallBack;

public class RoomHandsViewDelegate {
    private FragmentActivity activity;
    private ChatroomHandsDialog dialog;
    private String roomId;
    private ChatPrimaryMenuView chatPrimaryMenuView;
    private String owner;

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
        chatPrimaryMenuView.setHandStatus(false, ProfileManager.getInstance().getProfile().getUid().equals(owner));
    }

    public void showMemberHandsDialog(){
       new CommonSheetAlertDialog()
               .contentText(activity.getString(R.string.chatroom_request_speak))
               .rightText(activity.getString(R.string.chatroom_confirm))
               .leftText(activity.getString(R.string.chatroom_cancel))
               .setOnClickListener(new CommonSheetAlertDialog.OnClickBottomListener() {
                   @Override
                   public void onConfirmClick() {
                       HttpManager.getInstance(activity).submitMic(roomId, -1, new ValueCallBack<Boolean>() {
                           @Override
                           public void onSuccess(Boolean var1) {
                               ToastTools.show(activity,activity.getString(R.string.chatroom_mic_apply_success), Toast.LENGTH_SHORT);
                           }

                           @Override
                           public void onError(int code, String desc) {
                               ToastTools.show(activity,activity.getString(R.string.chatroom_mic_apply_fail,desc), Toast.LENGTH_SHORT);
                           }
                       });
                   }

                   @Override
                   public void onCancelClick() {

                   }
               })
               .show(activity.getSupportFragmentManager(), "room_hands_apply");
    }

}
