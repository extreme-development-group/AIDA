package client;

import config.ChatVerify;
import config.PureInfo;
import config.ServerInfo;
import config.UserInfo;
import user.User;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

public final class InteractWithServer {

    // 与服务器验证端口建立连接，并使用对象流传输数据
    private static Object postToServer(Object obj) {
        Object result = null;
        try {
            // 建立连接
            Socket sc = new Socket(ServerInfo.SERVER_IP, ServerInfo.VERIFY_PORT);

            // 创建对象输入输出流
            ObjectOutputStream out = new ObjectOutputStream(sc.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(sc.getInputStream());

            // 写对象到服务端
            out.writeObject(obj);

            // 获取返回信息
            result = in.readObject();

            // 关闭流
            sc.close();
            in.close();
            out.close();
        } catch (ClassNotFoundException | IOException e) {
            JOptionPane.showMessageDialog(null, "在与服务器验证交互中出现了异常，请重新登录", "错误", JOptionPane.WARNING_MESSAGE);
            System.out.println("在与服务器验证交互中出现了异常：" + e.getMessage());
            System.exit(0);
        }
        return result;
    }

    // 验证登录用户名或密码是否正确，发送构造的身份信息到服务器
    public static Object isLogin(String userId, String userPassword) {
        // 构造身份信息
        ChatVerify userInfo = new ChatVerify(userId, userPassword);

        // 返回服务器产生的结果
        return postToServer(userInfo);
    }

    // 通过ID获取用户的信息，包括个人资料以及好友列表群列表
    public static User getUserInfo(String userId) {
        User userInfo = null;
        String fieldString = "getUserInfo" + userId;
        userInfo = (User) postToServer(fieldString);
        return userInfo;
    }
    //注册
    public static String register(String userName, String password) {
        String userID = null;
        String fieldString = "register" + userName + "```" + password;
        userID = (String) postToServer(fieldString);
        return userID;
    }
    //得到搜索好友列表
    public static Vector<UserInfo.FriendsOrGroups> getFriendsOrGroup(String fid,String uID){
        Vector<UserInfo.FriendsOrGroups> friendsOrGroups=null;
        String fieldString = "getFriendsOrGroups" + fid +  "```" + uID;
        friendsOrGroups = (Vector<UserInfo.FriendsOrGroups>) postToServer(fieldString);
        return friendsOrGroups;
    }


    //申请好友信息
    public static PureInfo getFriendInfo(String fid){
        PureInfo pureInfo=null;
        String fieldString ="getFriendInfo" + fid;
        pureInfo =(PureInfo)postToServer(fieldString);
        return pureInfo;
    }
    //提交修改信息
    public static Boolean modifyUserInfo(PureInfo pureInfo){
        Boolean isSuccess = false;
        isSuccess = (Boolean)postToServer(pureInfo);
        return isSuccess;
    }
    //创建群聊
    public static UserInfo.FriendsOrGroups createGroup(String uid,String gName){
        UserInfo.FriendsOrGroups friendsOrGroups = null;
        String fieldString = "createGroup"+uid+"```"+gName;
        friendsOrGroups=(UserInfo.FriendsOrGroups)postToServer(fieldString);
        return friendsOrGroups;
    }
    //删除群成员
    public static Boolean deleteGroupMember(String gid,String uid){
        Boolean isSuccess = false;
        String fieldString = "deleteGroupMember"+gid+"```"+uid;
        isSuccess = (Boolean)postToServer(fieldString);
        return isSuccess;
    }
    //设置为管理员
    public static Boolean setAdmin(String gid,String uid){
        Boolean isSuccess=false;
        String fieldString = "setAdmin"+gid+"```"+uid;
        isSuccess = (Boolean)postToServer(fieldString);
        return isSuccess;
    }

    //添加好友
    public static Boolean addFriends(String mid,String fid,boolean isFriend){
        boolean isSuccess = false;
        String fieldString = "";
        if (isFriend){
            fieldString = "addFriends" + mid + "```" + fid + "```"+ "0";
        }else {
            fieldString = "addFriends" + mid + "```" + fid + "```"+ "1";
        }
        isSuccess = (boolean)postToServer(fieldString);
        return isSuccess;
    }
    //删除好友
    public static Boolean deleteFriends(String mid,String fid,boolean isFriend){
        boolean isSuccess = false;
        String fieldString = "";
        if (isFriend){
            fieldString = "deleteFriends" + mid + "```" + fid + "```"+ "0";
        }else {
            fieldString = "deleteFriends" + mid + "```" + fid + "```"+ "1";
        }
        isSuccess = (boolean)postToServer(fieldString);
        return isSuccess;
    }

    // 通过交互双方id获取历史聊天记录
    public static Vector<String> getChatRecord(String fromid, String toId,Boolean isGroup) {
        String sendString;
        if (isGroup){
            sendString = "getChatRecord" + fromid + "```" + toId + "```" + "1";
        }else {
            sendString = "getChatRecord" + fromid + "```" + toId + "```" + "0";
        }
        return (Vector<String>) postToServer(sendString);
    }
    // 通过群ID向服务器发送请求，获取群所有成员ID
    public static  Vector<UserInfo.FriendsOrGroups> getGroupMembers(String groupId) {
        Vector<UserInfo.FriendsOrGroups> friendsOrGroups=null;
        String fieldString = "getGroupMembers" + groupId;
        friendsOrGroups = (Vector<UserInfo.FriendsOrGroups>) postToServer(fieldString);
        return friendsOrGroups;
    }

    // 向服务器发送请求更改个性签名
    public static void setMyTrades(String myId, String content) {
        postToServer("setMyTrades```" + myId + "```" + content);
    }


}
