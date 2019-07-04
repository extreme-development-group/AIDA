package server;

import config.UserInfo;
import database.DataCheck;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.util.Vector;

public final class DataStream implements Runnable{
    private DataInputStream in;
    private DataOutputStream out;
    private String userId;
    private InetAddress clientInetAddress;

    // 利用与客户端连接的Socket对象创建数据输入输出流
    DataStream(Socket clientSocket, String userId) {
        this.clientInetAddress = clientSocket.getInetAddress();
        this.userId = userId;
        try {
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("创建聊天数据传输流失败：" + e.getMessage());
        }
        sendToAllFriends("OnlineSituation```在线```" + userId);
    }

    // 通知该用户的所有好友(一般为上线信息或离线信息)
    private void sendToAllFriends(String message) {
        System.out.println(message);
        //当前连接用户好友列表
        Vector<String> friends;
        friends = DataCheck.getFriendMember(userId);
        for (int i = 0; i < friends.size(); i++) {
            if (ServerListener.getClientUser().containsKey(friends.get(i))) {
                ServerListener.getClientUser().get(friends.get(i)).send(message);
            }
        }
    }

    // 接收客户端发送来的请求
    @Override
    public void run() {
        String scMessage;
        try {
            while (true) {
                scMessage = in.readUTF();
                // 处理消息
                execute(scMessage);
            }
        } catch (IOException e) {
            // 离线断开连接出错
            // 离线后删除用户在线信息
            ServerListener.getClientUser().remove(userId);

            // 通知所有好友离线情况
            sendToAllFriends("OnlineSituation```离线```" + userId);
            System.out.println("用户" + userId + "的离线，剩余在线人数 ：" + ServerListener.getClientUser().size());
        }
    }

    // 处理客户端所发送来的信息，将它转发给其他用户或群
    private void execute(String message) {
        String res[] = message.split("```", 4);
        if (res.length == 4) {
            /*
             * res[0]：发送标识 、res[1]：fromId 、res[2]：toId 、res[3]：消息内容
             */
            String type = res[0];
            String toId = res[2];
            message = new Date().toString() + "```" + message;
            if (type.equals("toFriend")) {
                // 发送给好友的消息
                if (ServerListener.getClientUser().containsKey(toId)) {
                    ServerListener.getClientUser().get(toId).send(message);
                    System.out.println(message);
                } else {
                    // 好友不在线的情况
                }
            } else if (type.equals("toGroup")) {
                // 群聊消息
                Vector<UserInfo.FriendsOrGroups> groupM = DataCheck.getGroupMember(toId);
                // 判断是否是群成员
                boolean isMember = false;
                for (int i = 0; i < groupM.size(); i++) {
                    if (groupM.get(i).getId().equals(userId)) {
                        isMember = true;
                        break;
                    }
                }
                if (!isMember) return;
                // 发给除了自己的其他人
                for (int i = 0; i < groupM.size(); i++) {
                    if (!groupM.get(i).getId().equals(userId)
                            && ServerListener.getClientUser().containsKey(groupM.get(i).getId())) {
                        ServerListener.getClientUser().get(groupM.get(i).getId()).send(message);
                    } else {
                        // 群成员不在线情况
                    }
                }
            }
        } else {
            System.out.println("收到的信息不规范：" + message);
        }
    }

    // 发送消息到连接的客户端
    private void send(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            System.out.println(userId + "发送到客户端失败：" + e.getMessage());
        }
    }

    public InetAddress getClientInetAddress() {
        System.out.println("In DataStream.getClientInetAddress：" + clientInetAddress);
        return clientInetAddress;
    }

}
