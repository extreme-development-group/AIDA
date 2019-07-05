package client;

import config.Tools;
import frame.ChatFrame.ChatFrame;
import frame.ChatFrame.ChatWithFriends;
import frame.ChatFrame.ChatWithGroup;
import frame.MainInterface.UI_MainInterface;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public final class ChatExecute {
    //     message : 接收到的消息内容
    private static String message;
    //     fromId : 发送该消息的用户ID，用作显示对方信息
    private static String fromId;
    //     toId : 发送目标方ID，主要在群聊天中有效，用作消息应该展示在哪个群里面
    private static String toId;
    //     接收消息内容的标识信息
    private static String type;
    //    开始处理服务端发送已封装的消息内容
    public static void execute(String scMessage) {
        // 对接收到的消息内容进行解码
        String res[] = scMessage.split("```", 5);
        // 从服务端发送的内容解码之后长度为5，代表该消息为聊天内容
        if (res.length == 5) {
            type = res[1];
            fromId = res[2];
            toId = res[3];
            message = res[4];

            // 以ID为键，对应聊天面板为值的哈希映射
            HashMap<String, ChatWithFriends> fModel;
            HashMap<String, ChatWithGroup> gModel;

            // 接收到的消息是从好友发送来的
            if (type.equals("toFriend")) {

                fModel = UI_MainInterface.getFriendChat();
                System.out.println(scMessage);

                // 展示在对应好友聊天面板中
                // 若打开了聊天窗口
                if (fModel.containsKey(fromId)) {
                    fModel.get(fromId).addMessage(Tools.base64StringToImage(InteractWithServer.getFriendInfo(fromId).getUserAvatar()),UI_MainInterface.getFriend().get(fromId).getfName(), res[0], message,
                            0);
                }else {
                    UI_MainInterface.friend.get(fromId).setBackground(new Color(146,199,241));
                }
            }
            // 接收到的消息是从某个群发送来的
            else if (type.equals("toGroup")) {
                gModel = UI_MainInterface.getGroupChat();
                // 若打开了聊天窗口
                if (gModel.containsKey(toId)) {
                    // 聊天面板显示用户昵称
                    String fromString = UI_MainInterface.getFriend().containsKey(fromId)
                            ? UI_MainInterface.getFriend().get(fromId).getfName() : ("陌生人:" + fromId);
                    gModel.get(toId).addMessage(Tools.base64StringToImage(InteractWithServer.getFriendInfo(fromId).getUserAvatar()),fromString, res[0], message, 0);
                }else {
                    UI_MainInterface.group.get(toId).setBackground(new Color(146,199,241));
                }
            }
        } // 接收的内容是为了改变用户状态（在线/离线）
        else if (res.length == 3) {
//             res[0]:验证标识、res[1]:状态信息、res[2]:好友ID
            if (res[0].equals("OnlineSituation")) {
                if (UI_MainInterface.getFriend().containsKey(res[2])) {
                    System.out.println(res[0]);
                    System.out.println(UI_MainInterface.getFriend().get(res[2]).getfName());
                    UI_MainInterface.getFriend().get(res[2]).setfOnline(res[1]);
                }
            }
        }

    }
}
