package server;

import config.ChatVerify;
import config.PureInfo;
import database.DataCheck;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.Socket;

public class Verification implements Runnable {
    private Socket userSocket;

    Verification(Socket userSocket) {
        this.userSocket = userSocket;
    }

    private Object process(Object obj) {
        // 获取对象类型
        String objName = obj.getClass().getSimpleName();
        System.out.println("当前处理对象类型为 " + objName);

        // 返回结果对象
        Object result = null;

        switch (objName) {
            case "ChatVerify":
                // 登陆验证
                ChatVerify loginVerify = (ChatVerify) obj;
                if(ServerListener.getClientUser().containsKey(loginVerify.getUserId())) {
                    // 重复登陆
                    result = "Repeat_login";
                } else {
                    result = DataCheck.isLoginSuccess(loginVerify.getUserId(), loginVerify.getUserPassword());

                    // 为登录成功的用户创建聊天线程
                    if (result != null && (Boolean) result == true) {
                        System.out.println("登录成功，为该用户创建一个聊天线程");
                        new Thread(new InteractWithClient(loginVerify.getUserId())).start();
                    }
                }
                break;
            case "PureInfo":
                // 修改资料
                result = DataCheck.editInfo( (PureInfo)obj );
                break;
            case "String":
                // 获取字符串内容
                String field = obj.toString();
                System.out.println(field);
                // 如果为获取信息
                if (field.startsWith("getUserInfo")) {
                    field = field.replace("getUserInfo", "");
                    result = DataCheck.getUserInfo(field);

                } else if (field.startsWith("getGroupMembers")) {
                    // 获取群成员
                    field = field.replace("getGroupMembers", "");
                    result = DataCheck.getGroupMember(field);

                } else if (field.startsWith("getFriendsOrGroups")) {
                    // 搜索好友 TODO
                    System.out.println("in search");
                    field = field.replace("getFriendsOrGroups", "");
                    String info[] = field.split("```");
                    result = DataCheck.getSearchResult(info[0], info[1]);
                } else if (field.startsWith("addFriends")) {
                    // 添加好友或群，自身id```目标id```好友或群
                    field = field.replace("addFriends", "");
                    String info[] = field.split("```");
                    if(info[2].equals("0"))
                        // 0加好友1加群
                        result = DataCheck.addFriend(info[0], info[1]);
                    else
                        result = DataCheck.addGroup(info[0], info[1]);

                } else if (field.startsWith("deleteFriends")) {
                    // 删除好友或退出群
                    field = field.replace("deleteFriends", "");
                    String info[] = field.split("```");
                    if(info[2].equals("0"))
                        // 0加好友1加群
                        result = DataCheck.deleteFriend(info[0], info[1]);
                    else
                        result = DataCheck.exitGroup(info[0], info[1]);

                } else if (field.startsWith("register")) {
                    // 注册
                    field = field.replace("register", "");
                    String info[] = field.split("```");
                    result = DataCheck.register(info[0], info[1]);

                } else if(field.startsWith("getFriendInfo")) {
                    // 获取指定用户简要资料
                    field = field.replace("getFriendInfo", "");
                    result = DataCheck.getPureInfo(field);

                } else if(field.startsWith("createGroup")) {
                    // 创建群聊, 用户id```群名
                    field = field.replace("createGroup", "");
                    String info[] = field.split("```");
                    result = DataCheck.createGroup(info[0], info[1]);
                } else if (field.startsWith("deleteGroupMember")) {
                    // 踢出群成员
                    field = field.replace("deleteGroupMember", "");
                    String info[] = field.split("```");
                    result = DataCheck.deleteGrouopMember(info[0], info[1]);
                } else if (field.startsWith("getUserIP")) {
                    // 获取指定用户的网络地址信息, 返回InetAddress对象
                    field = field.replace("getUserIP", "");
                    return ServerListener.getUserIP(field);
                }
                break;
        }

        return result;
    }

    // 接收用户套接字传过来的对象，处理，回传结果
    @Override
    public void run() {
        try {
            // 对象输入输出流
            ObjectInputStream in = new ObjectInputStream(userSocket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(userSocket.getOutputStream());

            // 接收一个对象流
            Object obj = in.readObject();

            // 获取处理结果
            Object result = process(obj);

            // 返回给客户端这个处理结果
            out.writeObject(result);

            // 关闭所有流
            in.close();
            out.close();
            userSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {

        }
    }
}
