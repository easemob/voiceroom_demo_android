package custormgift;

import bean.ChatMessageData;
import io.agora.chat.ChatMessage;

/**
 * 定义接收到的消息类型
 */
public interface OnCustomMsgReceiveListener {
    /**
     * 接收到礼物消息
     * @param message
     */
    void onReceiveGiftMsg(ChatMessageData message);

    /**
     * 接收到点赞消息
     * @param message
     */
    void onReceivePraiseMsg(ChatMessageData message);

    /**
     * 接收到普通消息
     * @param message
     */
    void onReceiveNormalMsg(ChatMessageData message);

}
