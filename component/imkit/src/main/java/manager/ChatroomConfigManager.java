package manager;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import bean.ChatMessageData;
import custormgift.CustomMsgHelper;
import custormgift.OnCustomMsgReceiveListener;
import io.agora.CallBack;
import io.agora.ConnectionListener;
import io.agora.Error;
import io.agora.chat.ChatClient;
import io.agora.chat.ChatOptions;
import io.agora.util.EMLog;

public class ChatroomConfigManager {
    private static final String TAG = ChatroomConfigManager.class.getSimpleName();
    private static ChatroomConfigManager mInstance;
    private Context mContext;
    private List<ChatroomListener> messageListeners = new CopyOnWriteArrayList();

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

        ChatroomMsgHelper.getInstance().setOnCustomMsgReceiveListener(new OnCustomMsgReceiveListener() {
            @Override
            public void onReceiveGiftMsg(ChatMessageData message) {
                try {
                    for (ChatroomListener listener : ChatroomConfigManager.this.messageListeners) {
                        listener.receiveGift(message.getConversationId(), message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onReceivePraiseMsg(ChatMessageData message) {

            }

            @Override
            public void onReceiveNormalMsg(ChatMessageData message) {
                try {
                    for (ChatroomListener listener : ChatroomConfigManager.this.messageListeners) {
                        listener.receiveTextMessage(message.getConversationId(), message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onReceiveApplySite(ChatMessageData message) {
                Log.e("setOnCustomMsgReceiveListener","onReceiveApplySite");
                try {
                    for (ChatroomListener listener : ChatroomConfigManager.this.messageListeners) {
                        listener.receiveApplySite(message.getConversationId(), message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onReceiveCancelApplySite(ChatMessageData message) {
                Log.e("setOnCustomMsgReceiveListener","onReceiveCancelApplySite");
                try {
                    for (ChatroomListener listener : ChatroomConfigManager.this.messageListeners) {
                        listener.receiveCancelApplySite(message.getConversationId(), message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onReceiveInviteSite(ChatMessageData message) {
                Log.e("setOnCustomMsgReceiveListener","onReceiveApplySite");
                try {
                    for (ChatroomListener listener : ChatroomConfigManager.this.messageListeners) {
                        listener.receiveInviteSite(message.getConversationId(), message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onReceiveInviteRefusedSite(ChatMessageData message) {
                Log.e("setOnCustomMsgReceiveListener","onReceiveInviteRefusedSite");
                try {
                    for (ChatroomListener listener : ChatroomConfigManager.this.messageListeners) {
                        listener.receiveInviteRefusedSite(message.getConversationId(), message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onReceiveDeclineApply(ChatMessageData message) {
                Log.e("setOnCustomMsgReceiveListener","onReceiveApplySite");
                try {
                    for (ChatroomListener listener : ChatroomConfigManager.this.messageListeners) {
                        listener.receiveDeclineApply(message.getConversationId(), message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onReceiveSystem(ChatMessageData message) {
                try {
                    for (ChatroomListener listener : ChatroomConfigManager.this.messageListeners) {
                        listener.receiveSystem(message.getConversationId(), message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ChatroomMsgHelper.getInstance().setChatRoomListener(new OnChatroomEventReceiveListener() {
            @Override
            public void onRoomDestroyed(String roomId) {
                try {
                    for (ChatroomListener listener : ChatroomConfigManager.this.messageListeners) {
                        listener.chatroomDestroyed(roomId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMemberJoined(String roomId, String name) {
                try {
                    for (ChatroomListener listener : ChatroomConfigManager.this.messageListeners) {
                        listener.userJoinedRoom(roomId,name);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMemberExited(String roomId, String name, String reason) {
                try {
                    for (ChatroomListener listener : ChatroomConfigManager.this.messageListeners) {
                        listener.onMemberExited(roomId, name, reason);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onKicked(String roomId, int reason) {
                try {
                    for (ChatroomListener listener : ChatroomConfigManager.this.messageListeners) {
                        listener.userBeKicked(roomId,reason);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAnnouncementChanged(String roomId, String announcement) {
                try {
                    for (ChatroomListener listener : ChatroomConfigManager.this.messageListeners) {
                        listener.announcementChanged(roomId,announcement);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAttributesUpdate(String roomId, Map<String, String> attributeMap, String from) {
                try {
                    for (ChatroomListener listener : ChatroomConfigManager.this.messageListeners) {
                        listener.roomAttributesDidUpdated(roomId,attributeMap,from);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAttributesRemoved(String roomId, List<String> keyList, String from) {
                try {
                    for (ChatroomListener listener : ChatroomConfigManager.this.messageListeners) {
                        listener.roomAttributesDidRemoved(roomId,keyList,from);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
            try {
                for (ChatroomListener listener : ChatroomConfigManager.this.messageListeners) {
                    listener.onTokenWillExpire();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    public void logout(boolean unbind,CallBack callBack){
        ChatClient.getInstance().logout(unbind, new CallBack() {
            @Override
            public void onSuccess() {
                callBack.onSuccess();
            }

            @Override
            public void onError(int i, String s) {
                callBack.onError(i,s);
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
        //用户离开房间
        default void onMemberExited(String roomId,String s1,String s2){}
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
        if (listener == null) {
            EMLog.d("ChatroomConfigManager", "setChatRoomListener: listener is null");
        } else {
            if (!this.messageListeners.contains(listener)) {
                EMLog.d("ChatroomConfigManager", "add message listener: " + listener);
                this.messageListeners.add(listener);
            }
        }
    }

    public void removeChatRoomListener(ChatroomListener listener){
        if (listener != null) {
            this.messageListeners.remove(listener);
            ChatroomMsgHelper.getInstance().removeChatRoomChangeListener();
            CustomMsgHelper.getInstance().removeListener();
        }
    }

    public void renewToken(String newToken){
        ChatClient.getInstance().renewToken(newToken);
    }

}
