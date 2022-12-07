package manager;

import android.text.TextUtils;
import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMChatRoomChangeListener;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMCustomMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.EMLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bean.ChatMessageData;
import custorm.CustomMsgHelper;
import custorm.OnCustomMsgReceiveListener;
import custorm.OnMsgCallBack;

public class ChatroomHelper implements EMChatRoomChangeListener, EMConnectionListener {
    private static ChatroomHelper instance;
    private ChatroomHelper(){}
    private String chatroomId;
    private ArrayList<ChatMessageData> data = new ArrayList<>();
    public OnChatroomEventReceiveListener chatroomListener;
    public OnChatroomConnectionListener chatroomConnectionListener;
    private static final String TAG = "ChatroomHelper";

    public static ChatroomHelper getInstance() {
        if(instance == null) {
            synchronized (ChatroomHelper.class) {
                if(instance == null) {
                    instance = new ChatroomHelper();
                }
            }
        }
        return instance;
    }

    /**
     * 需要在详情页时候初始化，防止chatroomId为空或不正确
     * @param chatroomId
     */
    public void init(String chatroomId) {
        this.chatroomId = chatroomId;
        //设置消息监听
        setCustomMsgListener();
        //设置聊天室状态变化监听
        setChatRoomChangeListener();
        //设置连接监听
        setConnectionListener();
        //设置相关的房间信息
        CustomMsgHelper.getInstance().setChatRoomInfo(chatroomId);

    }


    public String getCurrentRoomId() {
        return this.chatroomId;
    }

    /**
     * 设置自定义消息监听
     */
    public void setCustomMsgListener() {
        CustomMsgHelper.getInstance().init();
    }

    public void setChatRoomChangeListener(){
        EMClient.getInstance().chatroomManager().addChatRoomChangeListener(this);
    }

    public void removeChatRoomChangeListener(){
        EMClient.getInstance().chatroomManager().removeChatRoomListener(this);
    }

    public void setConnectionListener(){
        EMClient.getInstance().addConnectionListener(this);
    }

    public void removeChatRoomConnectionListener(){
        EMClient.getInstance().removeConnectionListener(this);
    }

    public void setChatRoomListener(OnChatroomEventReceiveListener listener){
        this.chatroomListener = listener;
    }

    public void setChatRoomConnectionListener(OnChatroomConnectionListener listener){
        this.chatroomConnectionListener = listener;
    }

    /**
     * 移除自定义消息监听
     */
    public void removeCustomMsgListener() {
        CustomMsgHelper.getInstance().removeListener();
    }

    /**
     * 发送文本消息
     * @param content
     * @param callBack
     */
    public void sendTxtMsg(String content,String nickName, OnMsgCallBack callBack) {
        EMMessage message = EMMessage.createTextSendMessage(content, chatroomId);
        message.setAttribute("userName",nickName);
        message.setChatType(EMMessage.ChatType.ChatRoom);
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                CustomMsgHelper.getInstance().addSendText(parseChatMessage(message));
                callBack.onSuccess(parseChatMessage(message));
            }

            @Override
            public void onError(int i, String s) {
                callBack.onError(message.getMsgId(), i, s);
            }

            @Override
            public void onProgress(int i, String s) {
                callBack.onProgress(i, s);
            }
        });
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    /**
     * 发送礼物消息
     * @param giftId
     * @param num
     * @param nickName
     * @param callBack
     */
    public void sendGiftMsg(String nickName,String portrait,String giftId,int num,String price,String giftName, OnMsgCallBack callBack) {
        CustomMsgHelper.getInstance().sendGiftMsg(nickName,portrait,giftId,num,price,giftName ,new OnMsgCallBack() {
            @Override
            public void onSuccess(ChatMessageData message) {
                if(callBack != null) {
                    callBack.onSuccess(message);
                }
            }

            @Override
            public void onProgress(int i, String s) {
                super.onProgress(i, s);
                if(callBack != null) {
                    callBack.onProgress(i, s);
                }
            }

            @Override
            public void onError(String messageId, int code, String error) {
                if(callBack != null) {
                    callBack.onError(messageId, code, error);
                }
            }
        });
    }

    public ArrayList<ChatMessageData> getMessageData(String chatroomId){
        data.clear();
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(chatroomId, EMConversation.EMConversationType.ChatRoom, true);
        if (conversation != null){
            EMLog.e("getMessageData",conversation.getAllMessages().size() + "");
            for (EMMessage allMessage : conversation.getAllMessages()) {
                if (allMessage.getBody() instanceof EMTextMessageBody){
                    data.add(parseChatMessage(allMessage));
                }
            }
            return data;
        }else {
            return null;
        }
    }

    public ChatMessageData parseChatMessage(EMMessage chatMessage){
        ChatMessageData chatMessageData = new ChatMessageData();
        chatMessageData.setForm(chatMessage.getFrom());
        chatMessageData.setTo(chatMessage.getTo());
        chatMessageData.setConversationId(chatMessage.conversationId());
        chatMessageData.setMessageId(chatMessage.getMsgId());
        if (chatMessage.getBody() instanceof EMTextMessageBody){
            chatMessageData.setType("text");
            chatMessageData.setContent(((EMTextMessageBody) chatMessage.getBody()).getMessage());
        }else if (chatMessage.getBody() instanceof EMCustomMessageBody){
            chatMessageData.setType("custom");
            chatMessageData.setEvent(((EMCustomMessageBody) chatMessage.getBody()).event());
            chatMessageData.setCustomParams(((EMCustomMessageBody) chatMessage.getBody()).getParams());
        }
        chatMessageData.setExt(chatMessage.ext());
        return chatMessageData;
    }

    /**
     * 获取礼物消息中礼物的id
     * @param msg
     * @return
     */
    public String getMsgGiftId(ChatMessageData msg) {
        return CustomMsgHelper.getInstance().getMsgGiftId(msg);
    }

    /**
     * 获取礼物消息中礼物的数量
     * @param msg
     * @return
     */
    public int getMsgGiftNum(ChatMessageData msg) {
        return CustomMsgHelper.getInstance().getMsgGiftNum(msg);
    }

    /**
     * 获取点赞消息中点赞的数目
     * @param msg
     * @return
     */
    public int getMsgPraiseNum(ChatMessageData msg) {
        return CustomMsgHelper.getInstance().getMsgPraiseNum(msg);
    }


    /**
     * 判断是否是礼物消息
     * @param msg
     * @return
     */
    public boolean isGiftMsg(ChatMessageData msg) {
        return CustomMsgHelper.getInstance().isGiftMsg(msg);
    }

    /**
     * 判断是否是点赞消息
     * @param msg
     * @return
     */
    public boolean isPraiseMsg(ChatMessageData msg) {
        return CustomMsgHelper.getInstance().isPraiseMsg(msg);
    }


    public void setOnCustomMsgReceiveListener(OnCustomMsgReceiveListener listener) {
        CustomMsgHelper.getInstance().setOnCustomMsgReceiveListener(listener);
    }

    public String getUserName(ChatMessageData msg){
        String userName = "";
        Map<String,String> params = msg.getCustomParams();
        if (params.containsKey("userName")){
            userName = params.get("userName");
        }
        if (TextUtils.isEmpty(userName)){
            Map<String,Object> ext = msg.getExt();
            if (ext.containsKey("userName")){
                userName = (String) ext.get("userName");
            }
        }
        Log.e("Helper","getUserName: " + userName);
        return userName;
    }

    public String getSystemUserName(ChatMessageData msg){
        String jsonString = "";
        String userName = "";
        Map<String,String> params = msg.getCustomParams();
        if (params.containsKey("room_user")){
            jsonString = params.get("room_user");
            Log.e("getSystemUserName","jsonString: " + jsonString);
            if (!TextUtils.isEmpty(jsonString)){
                try {
                    assert jsonString != null;
                    JSONObject jsonObject = new JSONObject(jsonString);
                    userName = jsonObject.getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.e("getSystemUserName","userName: " + userName);
        return userName;
    }

    public String getUserPortrait(ChatMessageData msg){
        String userPortrait = "";
        Map<String,String> ext = msg.getCustomParams();
        if (ext.containsKey("portrait")){
            userPortrait = ext.get("portrait");
        }
        return userPortrait;
    }


    @Override
    public void onChatRoomDestroyed(String s, String s1) {
        if (chatroomListener != null)
            chatroomListener.onRoomDestroyed(s);
    }

    @Override
    public void onMemberJoined(String s, String s1) {
        if (chatroomListener != null)
            chatroomListener.onMemberJoined(s,s1);
    }

    @Override
    public void onMemberExited(String s, String s1, String s2) {
        if (chatroomListener != null)
            chatroomListener.onMemberExited(s,s1,s2);
    }

    @Override
    public void onRemovedFromChatRoom(int i, String roomId, String s1, String s2) {
        if (chatroomListener != null)
            chatroomListener.onKicked(roomId,i);
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
        if (chatroomListener != null)
            chatroomListener.onAnnouncementChanged(s,s1);
    }

    @Override
    public void onAttributesUpdate(String roomId, Map<String, String> attributeMap, String from) {
        if (chatroomListener != null)
            chatroomListener.onAttributesUpdate(roomId,attributeMap,from);
    }

    @Override
    public void onAttributesRemoved(String roomId, List<String> keyList, String from) {
        if (chatroomListener != null)
            chatroomListener.onAttributesRemoved(roomId,keyList,from);
    }

    @Override
    public void onConnected() {
        if (chatroomConnectionListener != null)
            chatroomConnectionListener.onConnected();
    }

    @Override
    public void onDisconnected(int code) {
        if (chatroomConnectionListener != null)
            chatroomConnectionListener.onDisconnected(code);
    }

    @Override
    public void onTokenExpired() {
        if (chatroomConnectionListener != null)
            chatroomConnectionListener.onTokenExpired();
    }

    @Override
    public void onTokenWillExpire() {
        if (chatroomConnectionListener != null)
            chatroomConnectionListener.onTokenWillExpire();
    }

    public void renewToken(String newToken){
        EMClient.getInstance().renewToken(newToken);
    }

    public void login(String uid,String token,EMCallBack callBack){
        EMClient.getInstance().loginWithToken(uid, token, new EMCallBack() {
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
        EMClient.getInstance().loginWithToken(uid, token, new EMCallBack() {
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

    public void logout(boolean unbind,EMCallBack callBack){
        EMClient.getInstance().logout(unbind, new EMCallBack() {
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

    public void logout(boolean unbind){
        EMClient.getInstance().logout(unbind, new EMCallBack() {
            @Override
            public void onSuccess() {
                EMLog.d(TAG,"logout onSuccess");
            }

            @Override
            public void onError(int code, String desc) {
                EMLog.e(TAG,"logout onError code: " + code + "  " + desc);
            }
        });
    }

    public void saveWelcomeMsg(String content,String nick){
        EMMessage message = EMMessage.createSendMessage(EMMessage.Type.TXT);
        message.setChatType(EMMessage.ChatType.ChatRoom);
        message.setTo(chatroomId);
        EMTextMessageBody textMessageBody = new EMTextMessageBody(content);
        message.setBody(textMessageBody);
        message.setAttribute("userName",nick);
        EMClient.getInstance().chatManager().saveMessage(message);
    }

}
