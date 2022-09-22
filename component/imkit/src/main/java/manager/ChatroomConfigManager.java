package manager;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.Map;

import bean.ChatMessageData;
import custormgift.OnCustomMsgReceiveListener;
import io.agora.CallBack;
import io.agora.ChatRoomChangeListener;
import io.agora.ConnectionListener;
import io.agora.Error;
import io.agora.MessageListener;
import io.agora.ValueCallBack;
import io.agora.chat.ChatClient;
import io.agora.chat.ChatMessage;
import io.agora.chat.ChatOptions;
import io.agora.chat.ChatRoom;
import io.agora.chat.GroupReadAck;
import io.agora.util.EMLog;

public class ChatroomConfigManager {
    private static final String TAG = ChatroomConfigManager.class.getSimpleName();
    private static ChatroomConfigManager mInstance;
    private Context mContext;
    public ChatroomListener ChatListener;

    ChatroomConfigManager(){}

    public static ChatroomConfigManager getInstance() {
        if (mInstance == null) {
            synchronized (ChatroomConfigManager.class) {
                if (mInstance == null) {
                    mInstance = new ChatroomConfigManager();
                }
            }
        }
        return mInstance;
    }

    public void initRoomConfig(Context context){
        this.mContext = context;
        ChatOptions options = initChatOptions(context);
        if (!isMainProcess(context)){
            Log.e(TAG, "enter the service process!");
            return;
        }
        ChatClient.getInstance().init(context,options);
        ChatClient.getInstance().addConnectionListener(connectionListener);
        ChatClient.getInstance().chatroomManager().addChatRoomChangeListener(chatroomListener);

        ChatroomMsgHelper.getInstance().setOnCustomMsgReceiveListener(new OnCustomMsgReceiveListener() {
            @Override
            public void onReceiveGiftMsg(ChatMessageData message) {
                ChatListener.receiveGift(message.getConversationId(),message);
            }

            @Override
            public void onReceivePraiseMsg(ChatMessageData message) {

            }

            @Override
            public void onReceiveNormalMsg(ChatMessageData message) {
                ChatListener.receiveTextMessage(message.getConversationId(),message);
            }

            @Override
            public void onReceiveApplySite(ChatMessageData message) {
                ChatListener.receiveApplySite(message.getConversationId(),message);
            }

            @Override
            public void onReceiveInviteSite(ChatMessageData message) {
                ChatListener.receiveInviteSite(message.getConversationId(),message);
            }

            @Override
            public void onReceiveDeclineApply(ChatMessageData message) {
                ChatListener.receiveDeclineApply(message.getConversationId(),message);
            }
        });
    }

    public Context getContext() {
        return mContext;
    }

    private ChatOptions initChatOptions(Context context){
        ChatOptions options = new ChatOptions();
        options.setAppKey("52117440#955012");
        options.setAutoLogin(false);
        return options;
    }

    private boolean isMainProcess(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return context.getApplicationInfo().packageName.equals(appProcess.processName);
            }
        }
        return false;
    }

    private ConnectionListener connectionListener = new ConnectionListener() {
        @Override
        public void onConnected() {
            EMLog.i(TAG, "onConnected");
        }

        @Override
        public void onDisconnected(int error) {
            EMLog.i(TAG, "onDisconnected ="+error);
            if (error == Error.USER_REMOVED
                    || error == Error.USER_LOGIN_ANOTHER_DEVICE
                    || error == Error.SERVER_SERVICE_RESTRICTED
                    || error == Error.USER_KICKED_BY_CHANGE_PASSWORD
                    || error == Error.USER_KICKED_BY_OTHER_DEVICE) {

            }
        }

        @Override
        public void onTokenExpired() {
            int tokenExpired = Error.TOKEN_EXPIRED;
        }

        @Override
        public void onTokenWillExpire() {
            // TODO: 2022/9/20  重新获取token
        }
    };


    public void login(String uid,String token,CallBack callBack){
        ChatClient.getInstance().loginWithAgoraToken(uid, token, new CallBack() {
            @Override
            public void onSuccess() {
                callBack.onSuccess();
                Log.d("ChatroomConfigManager","Login success");
            }

            @Override
            public void onError(int code, String msg) {
                Log.e("ChatroomConfigManager", "Login onError code:" + code + " desc: " + msg);
                callBack.onError(code,msg);
            }
        });
    }

    public void joinRoom(String roomId,ValueCallBack<ChatRoom> callBack){
        ChatClient.getInstance().chatroomManager().joinChatRoom(roomId, new ValueCallBack<ChatRoom>() {
            @Override
            public void onSuccess(ChatRoom chatRoom) {
                Log.e("joinRoom","sdk join success");
                callBack.onSuccess(chatRoom);
            }

            @Override
            public void onError(int code, String desc) {
                callBack.onError(code,desc);
            }
        });
    }

    public interface ChatroomListener{
        void receiveTextMessage(String roomId,ChatMessageData message);
        void receiveGift(String roomId, ChatMessageData message);
        default void receiveApplySite(String roomId,ChatMessageData message){}
        default void receiveInviteSite(String roomId,ChatMessageData message){}
        default void receiveDeclineApply(String roomId,ChatMessageData message){}
        default void userJoinedRoom(String roomId,String uid){}
        default void announcementChanged(String roomId,String announcement){}
        default void userBeKicked(String roomId,int reason){}
        default void roomAttributesDidUpdated(String roomId,Map<String, String> attributeMap,String fromId){}
        default void roomAttributesDidRemoved(String roomId,Map<String, String> attributeMap,String fromId){}
    }

    public void setChatRoomListener(ChatroomListener listener){
        this.ChatListener = listener;
    }

    public ChatRoomChangeListener chatroomListener = new ChatRoomChangeListener(){

        @Override
        public void onChatRoomDestroyed(String s, String s1) {

        }

        @Override
        public void onMemberJoined(String s, String s1) {
            if (ChatListener != null)
            ChatListener.userJoinedRoom(s,s1);
        }

        @Override
        public void onMemberExited(String s, String s1, String s2) {

        }

        @Override
        public void onRemovedFromChatRoom(final int reason, final String roomId, final String roomName, final String participant) {
            if (ChatListener != null)
            ChatListener.userBeKicked(roomId,reason);
        }

        @Override
        public void onMuteListAdded(String s, List<String> list, long l) {

        }

        @Override
        public void onMuteListRemoved(String s, List<String> list) {

        }

        @Override
        public void onWhiteListAdded(String s, List<String> list) {

        }

        @Override
        public void onWhiteListRemoved(String s, List<String> list) {

        }

        @Override
        public void onAllMemberMuteStateChanged(String s, boolean b) {

        }

        @Override
        public void onAdminAdded(String s, String s1) {

        }

        @Override
        public void onAdminRemoved(String s, String s1) {

        }

        @Override
        public void onOwnerChanged(String s, String s1, String s2) {

        }

        @Override
        public void onAnnouncementChanged(String s, String s1) {
            if (ChatListener != null)
            ChatListener.announcementChanged(s,s1);
        }

        @Override
        public void onAttributesUpdate(String chatRoomId, Map<String, String> attributeMap, String from) {
            if (ChatListener != null)
            ChatListener.roomAttributesDidUpdated(chatRoomId,attributeMap,from);
        }

        @Override
        public void onAttributesRemoved(String chatRoomId, Map<String, String> attributeMap, String from) {
            if (ChatListener != null)
            ChatListener.roomAttributesDidRemoved(chatRoomId,attributeMap,from);
        }
    };

}
