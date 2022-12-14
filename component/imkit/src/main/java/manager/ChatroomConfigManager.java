package manager;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.util.EMLog;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import bean.ChatMessageData;
import custorm.CustomMsgHelper;
import custorm.OnCustomMsgReceiveListener;

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

    public void initRoomConfig(Context context, String imKey) {
        this.mContext = context;
        EMOptions options = initChatOptions(context, imKey);
        if (!isMainProcess(context)) {
            Log.e(TAG, "enter the service process!");
            return;
        }
        EMClient.getInstance().init(context, options);
        registerListener();
    }

    private void registerListener(){
        ChatroomHelper.getInstance().setChatRoomConnectionListener(new OnChatroomConnectionListener() {
            @Override
            public void onConnected() {
                EMLog.i(TAG, "onConnected");
            }

            @Override
            public void onDisconnected(int error) {
                EMLog.i(TAG, "onDisconnected ="+error);
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

            @Override
            public void onTokenExpired() {
                try {
                    for (ChatroomListener listener : ChatroomConfigManager.this.messageListeners) {
                        listener.onTokenExpired();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ChatroomHelper.getInstance().setOnCustomMsgReceiveListener(new OnCustomMsgReceiveListener() {
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
                Log.e("setOnCustomMsgReceiveListener","onReceiveInviteSite");
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
                Log.e("setOnCustomMsgReceiveListener","onReceiveDeclineApply");
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

            @Override
            public void voiceRoomUpdateRobotVolume(ChatMessageData message) {
                try {
                    for (ChatroomListener listener : ChatroomConfigManager.this.messageListeners) {
                        listener.voiceRoomUpdateRobotVolume(message.getConversationId(),CustomMsgHelper.getInstance().getCustomVolume(message));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ChatroomHelper.getInstance().setChatRoomListener(new OnChatroomEventReceiveListener() {
            @Override
            public void onRoomDestroyed(String roomId) {
                try {
                    for (ChatroomListener listener : ChatroomConfigManager.this.messageListeners) {
                        listener.onRoomDestroyed(roomId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMemberJoined(String roomId, String name) {
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

    private EMOptions initChatOptions(Context context,String imKey){
        EMOptions options = new EMOptions();
        options.setAppKey(imKey);
        options.setAutoLogin(false);
        return options;
    }

    private boolean isMainProcess(Context context) {
        String processName;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            processName = getProcessNameByApplication();
        }else {
            processName = getProcessNameByReflection();
        }
        return context.getApplicationInfo().packageName.equals(processName);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private String getProcessNameByApplication() {
        return Application.getProcessName();
    }

    private String getProcessNameByReflection() {
        String processName = null;
        try {
            final Method declaredMethod = Class.forName("android.app.ActivityThread", false, Application.class.getClassLoader())
                    .getDeclaredMethod("currentProcessName", (Class<?>[]) new Class[0]);
            declaredMethod.setAccessible(true);
            final Object invoke = declaredMethod.invoke(null, new Object[0]);
            if (invoke instanceof String) {
                processName = (String) invoke;
            }
        } catch (Throwable e) {
        }
        return processName;
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
            ChatroomHelper.getInstance().removeChatRoomChangeListener();
            ChatroomHelper.getInstance().removeChatRoomConnectionListener();
            CustomMsgHelper.getInstance().removeListener();
        }
    }

}
