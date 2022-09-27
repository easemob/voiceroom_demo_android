package manager;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Map;

import bean.ChatMessageData;
import custormgift.CustomMsgHelper;
import custormgift.OnCustomMsgReceiveListener;
import custormgift.OnMsgCallBack;
import io.agora.CallBack;
import io.agora.chat.ChatClient;
import io.agora.chat.ChatMessage;
import io.agora.chat.ChatRoom;
import io.agora.chat.Conversation;
import io.agora.chat.CustomMessageBody;
import io.agora.chat.TextMessageBody;
import io.agora.util.EMLog;

public class ChatroomMsgHelper {
    private static ChatroomMsgHelper instance;
    private ChatroomMsgHelper(){}
    private String chatroomId;
    private ArrayList<ChatMessageData> data = new ArrayList<>();

    public static ChatroomMsgHelper getInstance() {
        if(instance == null) {
            synchronized (ChatroomMsgHelper.class) {
                if(instance == null) {
                    instance = new ChatroomMsgHelper();
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
        setCustomMsgListener();
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
        ChatMessage message = ChatMessage.createTextSendMessage(content, chatroomId);
        message.setAttribute("userName",nickName);
        message.setChatType(ChatMessage.ChatType.ChatRoom);
        message.setMessageStatusCallback(new CallBack() {
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
        ChatClient.getInstance().chatManager().sendMessage(message);
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
        Conversation conversation = ChatClient.getInstance().chatManager().getConversation(chatroomId, Conversation.ConversationType.ChatRoom, true);
        if (conversation != null){
            EMLog.e("getMessageData",conversation.getAllMessages().size() + "");
            for (ChatMessage allMessage : conversation.getAllMessages()) {
                if (allMessage.getBody() instanceof TextMessageBody ){
                    data.add(parseChatMessage(allMessage));
                }
            }
            return data;
        }else {
            return null;
        }
    }

    public ChatMessageData parseChatMessage(ChatMessage chatMessage){
        ChatMessageData chatMessageData = new ChatMessageData();
        chatMessageData.setForm(chatMessage.getFrom());
        chatMessageData.setTo(chatMessage.getTo());
        chatMessageData.setConversationId(chatMessage.conversationId());
        chatMessageData.setMessageId(chatMessage.getMsgId());
        if (chatMessage.getBody() instanceof TextMessageBody){
            chatMessageData.setType("text");
            chatMessageData.setContent(((TextMessageBody) chatMessage.getBody()).getMessage());
        }else if (chatMessage.getBody() instanceof CustomMessageBody){
            chatMessageData.setType("custom");
            chatMessageData.setEvent(((CustomMessageBody) chatMessage.getBody()).event());
            chatMessageData.setCustomParams(((CustomMessageBody) chatMessage.getBody()).getParams());
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

    public String getUserPortrait(ChatMessageData msg){
        String userPortrait = "";
        Map<String,String> ext = msg.getCustomParams();
        if (ext.containsKey("portrait")){
            userPortrait = ext.get("portrait");
        }
        return userPortrait;
    }


}
