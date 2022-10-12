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
import io.agora.ValueCallBack;
import io.agora.chat.ChatClient;
import io.agora.chat.ChatOptions;
import io.agora.chat.ChatRoom;
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
                if (ChatListener != null)
                ChatListener.receiveGift(message.getConversationId(),message);
            }

            @Override
            public void onReceivePraiseMsg(ChatMessageData message) {

            }

            @Override
            public void onReceiveNormalMsg(ChatMessageData message) {
                if (ChatListener != null)
                ChatListener.receiveTextMessage(message.getConversationId(),message);
            }

            @Override
            public void onReceiveApplySite(ChatMessageData message) {
                Log.e("setOnCustomMsgReceiveListener","onReceiveApplySite");
                if (ChatListener != null)
                ChatListener.receiveApplySite(message.getConversationId(),message);
            }

            @Override
            public void onReceiveCancelApplySite(ChatMessageData message) {
                Log.e("setOnCustomMsgReceiveListener","onReceiveCancelApplySite");
                if (ChatListener != null)
                    ChatListener.receiveCancelApplySite(message.getConversationId(),message);
            }

            @Override
            public void onReceiveInviteSite(ChatMessageData message) {
                Log.e("setOnCustomMsgReceiveListener","onReceiveApplySite");
                if (ChatListener != null)
                ChatListener.receiveInviteSite(message.getConversationId(),message);
            }

            @Override
            public void onReceiveInviteRefusedSite(ChatMessageData message) {
                Log.e("setOnCustomMsgReceiveListener","onReceiveInviteRefusedSite");
                if (ChatListener != null)
                    ChatListener.receiveInviteRefusedSite(message.getConversationId(),message);
            }

            @Override
            public void onReceiveDeclineApply(ChatMessageData message) {
                Log.e("setOnCustomMsgReceiveListener","onReceiveApplySite");
                if (ChatListener != null)
                ChatListener.receiveDeclineApply(message.getConversationId(),message);
            }

            @Override
            public void onReceiveSystem(ChatMessageData message) {
                if (ChatListener != null)
                ChatListener.receiveSystem(message.getConversationId(),message);
            }
        });
    }

    public Context getContext() {
        return mContext;
    }

    private ChatOptions initChatOptions(Context context){
        ChatOptions options = new ChatOptions();
//        options.setAppKey("52117440#955012");
        options.setAppKey("81399972#1002901");
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
            if (ChatListener != null)
                ChatListener.onTokenWillExpire();
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

    public void login(String uid,String token){
        ChatClient.getInstance().loginWithAgoraToken(uid, token, new CallBack() {
            @Override
            public void onSuccess() {
                Log.d("ChatroomConfigManager","Login success");
            }

            @Override
            public void onError(int code, String msg) {
                Log.e("ChatroomConfigManager", "Login onError code:" + code + " desc: " + msg);
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
        //收到正常文本消息
        void receiveTextMessage(String roomId,ChatMessageData message);
        //收到礼物消息
        void receiveGift(String roomId, ChatMessageData message);
        //接收申请消息
        default void receiveApplySite(String roomId,ChatMessageData message){}
        //接收取消申请消息
        default void receiveCancelApplySite(String roomId,ChatMessageData message){}
        //接收邀请消息
        default void receiveInviteSite(String roomId,ChatMessageData message){}
        //接收拒绝邀请消息
        default void receiveInviteRefusedSite(String roomId,ChatMessageData message){}
        //接收拒绝申请消息
        default void receiveDeclineApply(String roomId,ChatMessageData message){}
        //用户加入房间 后面采用自定义消息
        default void userJoinedRoom(String roomId,String uid){}
        //聊天室公告更新
        default void announcementChanged(String roomId,String announcement){}
        //聊天室成员被踢出房间
        default void userBeKicked(String roomId,int reason){}
        //聊天室属性变更
        default void roomAttributesDidUpdated(String roomId,Map<String, String> attributeMap,String fromId){}
        //聊天室属性移除
        default void roomAttributesDidRemoved(String roomId,List<String> keyList,String fromId){}
        //token即将过期
        default void onTokenWillExpire(){}
        //收到系统消息
        default void receiveSystem(String roomId, ChatMessageData message){}
        //聊天室销毁
        default void chatroomDestroyed(String roomId){}
    }

    public void setChatRoomListener(ChatroomListener listener){
        this.ChatListener = listener;
    }

    public ChatRoomChangeListener chatroomListener = new ChatRoomChangeListener(){

        @Override
        public void onChatRoomDestroyed(String roomId, String roomName) {
            if (ChatListener != null)
                ChatListener.chatroomDestroyed(roomId);
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
        public void onAttributesRemoved(String chatRoomId, List<String> keyList, String from) {
            if (ChatListener != null)
            ChatListener.roomAttributesDidRemoved(chatRoomId,keyList,from);
        }
    };

    public void renewToken(String newToken){
        ChatClient.getInstance().renewToken(newToken);
    }

}
