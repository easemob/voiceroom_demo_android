package custorm;

import android.text.TextUtils;
import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCustomMessageBody;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.ChatMessageData;
import bean.ChatroomConstants;
import manager.ChatroomHelper;

/**
 * 自定义消息的帮助类（目前主要用于聊天室中礼物，点赞及弹幕消息）。
 * 用法如下：
 * （1）初始化 {@link #init()}，添加消息监听，根据业务需求，选择合适的地方初始化。
 * （2）设置聊天室信息 {@link #setChatRoomInfo(String)} 设置聊天室的id，用于筛选聊天室消息
 * （3）设置自定义消息监听{@link #setOnCustomMsgReceiveListener(OnCustomMsgReceiveListener)}
 *      用于接收不同的自定义消息类型（目前仅礼物，点赞及弹幕消息）。
 * （4）发送自定义消息：
 *      a、如果自定义消息类型与library相同，且所传参数相同或者相近，可以直接调用如下方法：
 *      {@link #sendGiftMsg(String,String,String,int,String,String,OnMsgCallBack)},
 *      {@link #sendPraiseMsg(int, OnMsgCallBack)},
 *      {@link #sendGiftMsg(Map, OnMsgCallBack)},
 *      {@link #sendPraiseMsg(Map, OnMsgCallBack)},
 *      b、如果有其他自定义消息类型，可以调用如下方法：
 *      {@link #sendCustomMsg(String, Map, OnMsgCallBack)},
 *      {@link #sendCustomMsg(String, EMMessage.ChatType, String, Map, OnMsgCallBack)}。
 * （5）自定义消息类型枚举{@link CustomMsgType} 定义了礼物，点赞及弹幕消息类型（以event区分）
 */
public class CustomMsgHelper implements EMMessageListener {
    private static CustomMsgHelper instance;
    private CustomMsgHelper(){}

    private String chatRoomId;
    private OnCustomMsgReceiveListener listener;
    private ArrayList<ChatMessageData> AllGiftList = new ArrayList<>();
    private ArrayList<ChatMessageData> AllNormalList = new ArrayList<>();

    public static CustomMsgHelper getInstance() {
        if(instance == null) {
            synchronized (CustomMsgHelper.class) {
                if(instance == null) {
                    instance = new CustomMsgHelper();
                }
            }
        }
        return instance;
    }

    /**
     * 根据业务要求，放在application或者其他需要初始化的地方
     */
    public void init() {
        EMClient.getInstance().chatManager().addMessageListener(this);
    }

    /**
     * 设置聊天室id
     * @param chatRoomId
     */
    public void setChatRoomInfo(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    /**
     * 设置接收消息的监听
     * @param listener
     */
    public void setOnCustomMsgReceiveListener(OnCustomMsgReceiveListener listener) {
        this.listener = listener;
    }

    /**
     * 移除监听（在页面中初始化后，记得在onDestroy()生命周期中移除）
     */
    public void removeListener() {
        EMClient.getInstance().chatManager().removeMessageListener(this);

    }

    @Override
    public void onMessageReceived(List<EMMessage> messages) {
        for (EMMessage message : messages) {
            if (message.getType() == EMMessage.Type.TXT){
                AllNormalList.add(ChatroomHelper.getInstance().parseChatMessage(message));
                if(listener != null) {
                    listener.onReceiveNormalMsg(ChatroomHelper.getInstance().parseChatMessage(message));
                }
            }
            // 先判断是否自定义消息
            if(message.getType() != EMMessage.Type.CUSTOM) {
                continue;
            }
            EMCustomMessageBody body = (EMCustomMessageBody) message.getBody();
            String event = body.event();
            CustomMsgType msgType = getCustomMsgType(event);

            if(msgType == null) {
                continue;
            }
            switch (msgType) {
                case CHATROOM_INVITE_SITE:
                    if(listener != null) {
                        listener.onReceiveInviteSite(ChatroomHelper.getInstance().parseChatMessage(message));
                    }
                    break;
                case CHATROOM_APPLY_SITE:
                    if(listener != null) {
                        listener.onReceiveApplySite(ChatroomHelper.getInstance().parseChatMessage(message));
                    }
                    break;
                case CHATROOM_CANCEL_APPLY_SITE:
                    if(listener != null) {
                        listener.onReceiveCancelApplySite(ChatroomHelper.getInstance().parseChatMessage(message));
                    }
                    break;
                case CHATROOM_INVITE_REFUSED_SITE:
                    if(listener != null) {
                        listener.onReceiveInviteRefusedSite(ChatroomHelper.getInstance().parseChatMessage(message));
                    }
                    break;
                case CHATROOM_DECLINE_APPLY:
                    if(listener != null) {
                        listener.onReceiveDeclineApply(ChatroomHelper.getInstance().parseChatMessage(message));
                    }
                    break;

            }
            // 再排除单聊
            if(message.getChatType() != EMMessage.ChatType.GroupChat && message.getChatType() != EMMessage.ChatType.ChatRoom) {
                continue;
            }
            String username = message.getTo();
            // 判断是否同一个聊天室或者群组
            if(!TextUtils.equals(username, chatRoomId)) {
                continue;
            }
            // 判断是否是自定消息，然后区分礼物，点赞及弹幕消息
            // 如果event为空，则不处理
            if(TextUtils.isEmpty(event)) {
                continue;
            }
            // 最后返回各自的消息类型
            switch (msgType) {
                case CHATROOM_GIFT:
                    AllGiftList.add(ChatroomHelper.getInstance().parseChatMessage(message));
                    if(listener != null) {
                        listener.onReceiveGiftMsg(ChatroomHelper.getInstance().parseChatMessage(message));
                    }
                    break;
                case CHATROOM_PRAISE:
                    if(listener != null) {
                        listener.onReceivePraiseMsg(ChatroomHelper.getInstance().parseChatMessage(message));
                    }
                    break;
                case CHATROOM_SYSTEM:
                    AllNormalList.add(ChatroomHelper.getInstance().parseChatMessage(message));
                    if (listener != null){
                        listener.onReceiveSystem(ChatroomHelper.getInstance().parseChatMessage(message));
                    }
                    break;
                case CHATROOM_UPDATE_ROBOT_VOLUME:
                    if (listener != null){
                        listener.voiceRoomUpdateRobotVolume(ChatroomHelper.getInstance().parseChatMessage(message));
                    }
            }
        }
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageRead(List<EMMessage> list) {

    }

    @Override
    public void onMessageDelivered(List<EMMessage> list) {

    }

    @Override
    public void onMessageRecalled(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }

    public ArrayList<ChatMessageData> getGiftData(String chatRoomId){
        ArrayList<ChatMessageData> data = new ArrayList<>();
        for (ChatMessageData chatMessageData : AllGiftList) {
            if (TextUtils.equals(chatRoomId,chatMessageData.getConversationId())){
                data.add(chatMessageData);
            }
        }
        AllGiftList.removeAll(data);
        return data;
    }

    public ArrayList<ChatMessageData> getNormalData(String chatRoomId){
        ArrayList<ChatMessageData> data = new ArrayList<>();
        for (ChatMessageData chatMessageData : AllNormalList) {
            if (TextUtils.equals(chatRoomId,chatMessageData.getConversationId())){
                data.add(chatMessageData);
            }
        }
        AllNormalList.removeAll(data);
        return data;
    }

    public void clear(){
        AllGiftList.clear();
        AllNormalList.clear();
    }

    public void addSendText(ChatMessageData data){
        AllNormalList.add(data);
    }

    /**
     * 发送礼物消息
     * @param nickName 用户名称
     * @param portrait 用户头像
     * @param giftId   礼物id
     * @param num      礼物数量
     * @param price    礼物金额
     * @param giftName 礼物名称
     * @param callBack 发送消息成功或者失败的回调
     */
    public void sendGiftMsg(String nickName,String portrait,String giftId,int num,String price,String giftName, OnMsgCallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put(MsgConstant.CUSTOM_GIFT_KEY_ID, giftId);
        params.put(MsgConstant.CUSTOM_GIFT_KEY_NUM, String.valueOf(num));
        params.put(MsgConstant.CUSTOM_GIFT_NAME,giftName);
        params.put(MsgConstant.CUSTOM_GIFT_PRICE,price);
        params.put(MsgConstant.CUSTOM_GIFT_USERNAME,nickName);
        params.put(MsgConstant.CUSTOM_GIFT_PORTRAIT,portrait);
        sendGiftMsg(params, callBack);
    }

    public void sendSystemMsg(String nickName,String portrait, OnMsgCallBack callBack) {
        Map<String, String> params = new HashMap<>();
        params.put(ChatroomConstants.MSG_KEY_MEMBER_ADD, "member_add");
        params.put(MsgConstant.CUSTOM_GIFT_USERNAME,nickName);
        params.put(MsgConstant.CUSTOM_GIFT_PORTRAIT,portrait);
        sendSystemMsg(params, callBack);
    }


    public void sendSystemMsg(Map<String, String> params, final OnMsgCallBack callBack){
        if(params.size() <= 0) {
            return;
        }
        sendCustomMsg(CustomMsgType.CHATROOM_SYSTEM.getName(), params, callBack);
    }

    /**
     * 发送礼物消息(多参数)
     * @param params
     * @param callBack
     */
    public void sendGiftMsg(Map<String, String> params, final OnMsgCallBack callBack) {
        if(params.size() <= 0) {
            return;
        }
        sendCustomMsg(CustomMsgType.CHATROOM_GIFT.getName(), params, callBack);
    }

    /**
     * 发送点赞消息
     * @param num
     * @param callBack
     */
    public void sendPraiseMsg(int num, OnMsgCallBack callBack) {
        if(num <= 0) {
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put(MsgConstant.CUSTOM_PRAISE_KEY_NUM, String.valueOf(num));
        sendPraiseMsg(params, callBack);
    }

    /**
     * 发送点赞消息(多参数)
     * @param params
     * @param callBack
     */
    public void sendPraiseMsg(Map<String, String> params, final OnMsgCallBack callBack) {
        if(params.size() <= 0) {
            return;
        }
        sendCustomMsg(CustomMsgType.CHATROOM_PRAISE.getName(), params, callBack);
    }



    /**
     * 发送自定义消息
     * @param event
     * @param params
     * @param callBack
     */
    public void sendCustomMsg(String event, Map<String, String> params, final OnMsgCallBack callBack) {
        sendCustomMsg(chatRoomId, EMMessage.ChatType.ChatRoom, event, params, callBack);
    }

    /**
     * 发送自定义消息
     * @param to
     * @param chatType
     * @param event
     * @param params
     * @param callBack
     */
    public void sendCustomMsg(String to, EMMessage.ChatType chatType, String event, Map<String, String> params, final OnMsgCallBack callBack) {
        final EMMessage sendMessage = EMMessage.createSendMessage(EMMessage.Type.CUSTOM);
        EMCustomMessageBody body = new EMCustomMessageBody(event);
        body.setParams(params);
        sendMessage.addBody(body);
        sendMessage.setTo(to);
        sendMessage.setChatType(chatType);
        sendMessage.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                if(callBack != null) {
                    AllGiftList.add(ChatroomHelper.getInstance().parseChatMessage(sendMessage));
                    callBack.onSuccess(ChatroomHelper.getInstance().parseChatMessage(sendMessage));
                }
            }

            @Override
            public void onError(int i, String s) {
                if(callBack != null) {
                    callBack.onError(sendMessage.getMsgId(), i, s);
                }
            }

            @Override
            public void onProgress(int i, String s) {
                if(callBack != null) {
                    callBack.onProgress(i, s);
                }
            }
        });
        EMClient.getInstance().chatManager().sendMessage(sendMessage);
    }

    /**
     * 获取礼物消息中礼物的id
     * @param msg
     * @return
     */
    public String getMsgGiftId(ChatMessageData msg) {
        if(!isGiftMsg(msg)) {
            return null;
        }

        Map<String, String> params = getCustomMsgParams(msg);
        Log.e("getMsgGiftId","getMsgGiftId_1: "+params.toString());
        if(params.containsKey(MsgConstant.CUSTOM_GIFT_KEY_ID)) {
            Log.e("getMsgGiftId",params.get(MsgConstant.CUSTOM_GIFT_KEY_ID));
            return params.get(MsgConstant.CUSTOM_GIFT_KEY_ID);
        }
        Log.e("getMsgGiftId","getMsgGiftId_3");
        return null;
    }

    /**
     * 获取礼物消息中礼物的数量
     * @param msg
     * @return
     */
    public int getMsgGiftNum(ChatMessageData msg) {
        if(!isGiftMsg(msg)) {
            return 0;
        }
        Map<String, String> params = getCustomMsgParams(msg);
        if(params.containsKey(MsgConstant.CUSTOM_GIFT_KEY_NUM)) {
            String num = params.get(MsgConstant.CUSTOM_GIFT_KEY_NUM);
            if(TextUtils.isEmpty(num)) {
                return 0;
            }
            try {
                return Integer.valueOf(num);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 获取点赞消息中点赞的数目
     * @param msg
     * @return
     */
    public int getMsgPraiseNum(ChatMessageData msg) {
        if(!isPraiseMsg(msg)) {
            return 0;
        }
        Map<String, String> params = getCustomMsgParams(msg);
        if(params.containsKey(MsgConstant.CUSTOM_PRAISE_KEY_NUM)) {
            String num = params.get(MsgConstant.CUSTOM_PRAISE_KEY_NUM);
            if(TextUtils.isEmpty(num)) {
                return 0;
            }
            try {
                return Integer.valueOf(num);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }


    /**
     * 判断是否是礼物消息
     * @param msg
     * @return
     */
    public boolean isGiftMsg(ChatMessageData msg) {
        return getCustomMsgType(getCustomEvent(msg)) == CustomMsgType.CHATROOM_GIFT;
    }

    /**
     * 判断是否是点赞消息
     * @param msg
     * @return
     */
    public boolean isPraiseMsg(ChatMessageData msg) {
        return getCustomMsgType(getCustomEvent(msg)) == CustomMsgType.CHATROOM_PRAISE;
    }


    /**
     * 获取自定义消息中的event字段
     * @param message
     * @return
     */
    public String getCustomEvent(ChatMessageData message) {
        if(message == null) {
            return null;
        }
        if(!message.getType().equals("custom")) {
            return null;
        }
        return message.getEvent();
    }

    /**
     * 获取自定义消息中的参数
     * @param message
     * @return
     */
    public Map<String, String> getCustomMsgParams(ChatMessageData message) {
        if(message == null) {
            return null;
        }
        if(!message.getType().equals("custom")) {
            return null;
        }
        return message.getCustomParams();
    }

    /**
     * 获取自定义消息中的ext参数
     * @param message
     * @return
     */
    public Map<String, Object> getCustomMsgExt(ChatMessageData message) {
        if(message == null) {
            return null;
        }
        if(!message.getType().equals("custom")) {
            return null;
        }
        return message.getExt();
    }

    public String getCustomVolume(ChatMessageData message){
        if(message == null) {
            return null;
        }
        if(!message.getType().equals("custom")) {
            return null;
        }
        if (message.getCustomParams().containsKey("volume")){
            return message.getCustomParams().get("volume");
        }
        return "";
    }

    /**
     * 获取自定义消息类型
     * @param event
     * @return
     */
    public CustomMsgType getCustomMsgType(String event) {
        if(TextUtils.isEmpty(event)) {
            return null;
        }
        return CustomMsgType.fromName(event);
    }
}
