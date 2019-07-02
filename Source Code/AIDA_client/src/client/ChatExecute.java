package client;

import frame.ChatFrame.ChatWithFriends;
import frame.MainInterface.UI_MainInterface;

import java.util.HashMap;

public final class ChatExecute {
    /**
     * @Fields message : 接收到的消息内容
     */
    private static String message;

    /**
     * @Fields fromId : 发送该消息的用户ID，用作显示对方信息
     */
    private static String fromId;

    /**
     * @Fields toId : 发送目标方ID，主要在群聊天中有效，用作消息应该展示在哪个群里面
     */
    private static String toId;

    /**
     * @Fields type : 接收消息内容的标识信息
     */
    private static String type;

    /**
     * @Title: execute
     * @Description:开始处理服务端发送已封装的消息内容
     * @param: scMessage
     *             接收到的消息内容
     * @return: void
     */
    public static void execute(String scMessage) {

    }
}
