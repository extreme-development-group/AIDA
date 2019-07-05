package database;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;
import config.PureInfo;
import config.UserInfo;
import server.ServerListener;
import user.User;
import config.UserInfo.FriendsOrGroups;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class DataCheck {

    // 登陆判断
    public static boolean isLoginSuccess(String userID, String userPass) {
        boolean isSuccess = false;
        try {
            // 查询该用户是否存在
            DataBaseConnection conn = new DataBaseConnection();
            String sql = "select * from aida_user where user_id = " + userID +
                    " and user_password = '" + userPass + "'";

            // 如果存在该用户，返回true
            isSuccess = conn.getFromDataBase(sql).next();

            // 关闭与服务器连接对象
            conn.close();
        } catch (SQLException e) {
            System.out.println("身份验证信息查询失败:" + e.getMessage());
        }
        return isSuccess;
    }

    // 拉取个人信息
    public static User getUserInfo(String userId) {
        // 用户个人信息
        String userAvatarPath = "";
        String userAvatar = "";
        String userNickName = "";
        String userSignature = "";
        String userSex = "";
        String userBirthday = "";
        String userHometown = "";
        String userCompany = "";
        String userAddress = "";
        String userEmail = "";
        String userRegistertime = "";
        Vector<FriendsOrGroups> friends;
        Vector<FriendsOrGroups> groups;
        User user = null;

        try {
            // 创建数据库连接
            DataBaseConnection dataCon = new DataBaseConnection();

            // 查询个人信息
            String sqlString = "select * from aida_user where user_id = " + userId;
            ResultSet resultSet = dataCon.getFromDataBase(sqlString);
            while (resultSet.next()) {
                userAvatarPath = resultSet.getString("user_avatar");
                userNickName = resultSet.getString("user_nickname");
                userSignature = resultSet.getString("user_signature");
                userSex = resultSet.getString("user_sex");
                userBirthday = resultSet.getString("user_birthday");
                userHometown = resultSet.getString("user_hometown");
                userCompany = resultSet.getString("user_company");
                userAddress = resultSet.getString("user_address");
                userEmail = resultSet.getString("user_email");
                userRegistertime = resultSet.getString("user_registertime");
            }
            resultSet.close();
            userAvatar = Imagebase64.getImageBinary("avatar/"+userAvatarPath);

            // 查询好友列表信息与群列表信息
            friends = getUserFriends(userId, dataCon);
            groups = getUserGroups(userId, dataCon);

            // 关闭数据库连接
            dataCon.close();
            // 创建对象
            user = new User(userId, userAvatar, userNickName, userSignature, userSex, userBirthday, userHometown,
                    userCompany, userAddress, userEmail, userRegistertime, friends, groups);
        } catch (SQLException e) {
            System.out.println("获取用户信息失败：" + e.getMessage());
        }
        return user;
    }

    // 获取好友信息
    private static Vector<FriendsOrGroups> getUserFriends(String userId, DataBaseConnection dataCon) {
        Vector<FriendsOrGroups> friends = new Vector<FriendsOrGroups>();

        // 查询好友信息
        String sqlString = "select * from view_useruser where user_id = " + userId;
        ResultSet resultSet = dataCon.getFromDataBase(sqlString);
        try {
            while (resultSet.next()) {;
                String fId = resultSet.getString("friend_id");
                String fAvatarPath = resultSet.getString("friend_avatar");
                String fNickName = resultSet.getString("friend_nickname");
                String fSignature = resultSet.getString("friend_signature");
                String fStatus = ServerListener.getClientUser().containsKey(fId) ? "在线" : "离线";
                String fAvatar = Imagebase64.getImageBinary("avatar/"+fAvatarPath);
                friends.add(new FriendsOrGroups(fId, fNickName, fAvatar, fSignature, fStatus));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("获取好友信息失败 " + e.getMessage());
        }
        return friends;
    }

    // 获取群组信息
    private static Vector<FriendsOrGroups> getUserGroups(String userId, DataBaseConnection dataCon) {
        Vector<FriendsOrGroups> groups = new Vector<FriendsOrGroups>();

        // 查询群信息
        String sqlString = "select * from view_groupuser where user_id = " + userId;
        ResultSet resultSet = dataCon.getFromDataBase(sqlString);
        try {
            while (resultSet.next()) {
                String gId = resultSet.getString("group_id");
                String gName = resultSet.getString("group_name");
                String gAvatarPath = resultSet.getString("group_avatar");
                String gAvatar = Imagebase64.getImageBinary("avatar/groupAvatar/"+gAvatarPath);
                groups.add(new FriendsOrGroups(gId, gName, gAvatar, "", ""));
            }
//            resultSet.close();
        } catch (SQLException e) {
            System.out.println("获取群信息失败 " + e.getMessage());
        }
        sqlString = "select * from aida_group where group_master = " + userId;
        resultSet = dataCon.getFromDataBase(sqlString);
        try {
            while (resultSet.next()) {
                String gId = resultSet.getString("group_id");
                String gName = resultSet.getString("group_name");
                String gAvatarPath = resultSet.getString("group_avatar");
                String gAvatar = Imagebase64.getImageBinary("avatar/groupAvatar/"+gAvatarPath);
                groups.add(new FriendsOrGroups(gId, gName, gAvatar, "", ""));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("获取群信息失败 " + e.getMessage());
        }
        return groups;
    }

    // 获取加了user_id用户的id
    public static Vector<String> getFriendMember(String user_id) {
        String sql = "select * from aida_useruser where friend_id = " + user_id;
        // 与数据库创建连接
        DataBaseConnection dataCon = new DataBaseConnection();

        // 最终结果Vector数组
        Vector<String> friends = new Vector<String>();

        // 利用该sql语句查询，返回ResultSet结果集
        ResultSet resultSet = dataCon.getFromDataBase(sql);
        try {
            while (resultSet.next()) {
                friends.add(resultSet.getString("user_id"));
            }
            // 关闭连接
            dataCon.close();
        } catch (SQLException e) {
            System.out.println("In getFriendMember：" + e.getMessage());
        }
        return friends;
    }

    // 查询群中所有成员的信息
    public static Vector<FriendsOrGroups> getGroupMember(String groupId) {
        System.out.println("In getGroupMember");
        // 与数据库创建连接
        DataBaseConnection dataCon = new DataBaseConnection();

        // 最终结果Vector数组
        Vector<FriendsOrGroups> member = new Vector<FriendsOrGroups>();

        // 自身为群主
        String sql = "select * from aida_group, aida_user where aida_user.user_id=aida_group.group_master" +
                " and group_id = " + groupId;
        // 利用该sql语句查询，返回ResultSet结果集
        ResultSet resultSet = dataCon.getFromDataBase(sql);
        try {
            while (resultSet.next()) {
                String user_id = resultSet.getString("user_id");
                String user_nickname = resultSet.getString("user_nickname");
                String user_avatarPath = resultSet.getString("user_avatar");
                String user_signature = resultSet.getString("user_signature");
                // 群主信息
                String user_avatar = Imagebase64.getImageBinary("avatar/"+user_avatarPath);
                member.add(new FriendsOrGroups(user_id, user_nickname, user_avatar, user_signature, "0"));
            }
        } catch (SQLException e) {
            System.out.println("获取群信息失败 " + e.getMessage());
        }
        sql = "select aida_groupuser.user_id, user_nickname,  user_avatar, user_signature " +
                "from aida_groupuser, aida_user where aida_groupuser.user_id=aida_user.user_id and group_id = " + groupId;
        resultSet = dataCon.getFromDataBase(sql);
        try {
            while (resultSet.next()) {
                String user_id = resultSet.getString("user_id");
                String user_nickname = resultSet.getString("user_nickname");
                String user_avatarPath = resultSet.getString("user_avatar");
                String user_signature = resultSet.getString("user_signature");
                // 除群主外群员信息
                String user_avatar = Imagebase64.getImageBinary("avatar/"+user_avatarPath);
                member.add(new FriendsOrGroups(user_id, user_nickname, user_avatar, user_signature, "2"));
            }
            // 关闭连接
            resultSet.close();
            dataCon.close();
        } catch (SQLException e) {
            System.out.println("查询成员ID列表失败：" + e.getMessage());
        }
        return member;
    }

    // 搜索, 过滤掉自身和自己管理的群
    public static Vector<FriendsOrGroups> getSearchResult(String sid, String uid) {
        Vector<FriendsOrGroups> results = new Vector<FriendsOrGroups>();

        DataBaseConnection dataCon = new DataBaseConnection();
        // 查询用户
        String sqlString = "select * from aida_user where user_id <> " + uid
                + " and user_id like '%" + sid + "%'";
        ResultSet resultSet = dataCon.getFromDataBase(sqlString);
        try {
            while (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                String userAvatarPath = resultSet.getString("user_avatar");
                String userNickName = resultSet.getString("user_nickname");
                String userSignature = resultSet.getString("user_signature");
                String userStatus = ServerListener.getClientUser().containsKey(userId) ? "在线" : "离线";
                String userAvatar = Imagebase64.getImageBinary("avatar/" + userAvatarPath);
                results.add(new FriendsOrGroups(userId, userNickName, userAvatar, userSignature, userStatus));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("获取好友信息失败 " + e.getMessage());
        }
        // 查询群
        sqlString = "select * from aida_group where group_master <> " + uid
                + " and group_id like '%" + sid + "%'";
        resultSet = dataCon.getFromDataBase(sqlString);
        try {
            while (resultSet.next()) {
                String gId = resultSet.getString("group_id");
                String gName = resultSet.getString("group_name");
                String gAvatarPath = resultSet.getString("group_avatar");
                String gAvatar = Imagebase64.getImageBinary("avatar/groupAvatar/"+gAvatarPath);
                results.add(new FriendsOrGroups(gId, gName, gAvatar, "", ""));
            }
            resultSet.close();
            dataCon.close();
        } catch (SQLException e) {
            System.out.println("获取信息失败 " + e.getMessage());
        }
        System.out.println(results.size());
        return results;
    }

    // 添加好友
    public static boolean addFriend(String userID, String fID) {
        System.out.println("In addFriend");
        DataBaseConnection dataCon = new DataBaseConnection();

        String sql = "insert into aida_useruser (user_id, friend_id) value (" +
                userID + "," + fID + ")";

        if(dataCon.updateDataBase(sql))
            return true;

        return false;
    }
    // 添加群， 不会添加到自己管理的群
    public static boolean addGroup(String userID, String gID) {
        System.out.println("In addGroup");
        DataBaseConnection dataCon = new DataBaseConnection();

        String sql = "insert into aida_groupuser (user_id, group_id) value (" +
                userID + "," + gID + ")";

        if(dataCon.updateDataBase(sql))
            return true;

        return false;
    }

    // 删除好友
    public static boolean deleteFriend(String userID, String fID) {
        System.out.println("In deleteFriend");
        DataBaseConnection dataCon = new DataBaseConnection();

        String sql = "delete from aida_useruser where user_id = " +
                userID + " and friend_id = " + fID;

        if(dataCon.updateDataBase(sql))
            return true;

        return false;
    }
    // 退出群
    public static boolean exitGroup(String userID, String gID) {
        System.out.println("In exitGroup");
        DataBaseConnection dataCon = new DataBaseConnection();

        // 先判断是否群主
        String sql = "select * from aida_group where group_master = " + userID;
        ResultSet resultSet = dataCon.getFromDataBase(sql);
        try {
            while (resultSet.next()) {
                return false;
            }
            resultSet.close();
            dataCon.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sql = "delete from aida_groupuser where user_id = " +
                userID + " and group_id = " + gID;

        if(dataCon.updateDataBase(sql))
            return true;

        return false;
    }

    // 注册
    public static String register(String nickname, String passwd) {
        String nextID = "";
        DataBaseConnection dataCon = new DataBaseConnection();
        // 获取注册的qq号
        String sql = "SELECT max(user_id) FROM oicq.aida_user;";
        ResultSet resultSet = dataCon.getFromDataBase(sql);
        try {
            while (resultSet.next()) {
                nextID = resultSet.getString("max(user_id)");
                nextID = String.valueOf(Integer.parseInt(nextID) + 1);
                System.out.println(nextID);
                }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("获取最大号码信息失败 " + e.getMessage());
        }
        // 新建用户
        sql = "insert into aida_user (\n" +
                "    user_id ,\n" +
                "    user_password ,\n" +
                "    user_nickname ,\n" +
                "    user_signature ,\n" +
                "    user_sex ,\n" +
                "    user_birthday,\n" +
                "    user_hometown,\n" +
                "    user_company ,\n" +
                "    user_address,\n" +
                "    user_email \n" +
                ") value (\n" +
                nextID + " ,\n" +
                "    \"" + passwd + "\" ,\n" +
                "    \"" + nickname +"\",\n" +
                "    \"这个人很懒，什么都没写\",\n" +
                "    \"男\",\n" +
                "    \"未设置\",\n" +
                "    \"未设置\",\n" +
                "    \"未设置\",\n" +
                "    \"未设置\",\n" +
                "    \"未设置\"\n" +
                ");";

        if(dataCon.updateDataBase(sql))
            return nextID;

        return "";
    }

    // 获取简要资料
    public static PureInfo getPureInfo(String id) {
        System.out.println("in getFriendInfo");
        PureInfo pureInfo = null;
        // 用户个人信息
        String userAvatarPath = "";
        String userAvatar = "";
        String userNickName = "";
        String userSignature = "";
        String userSex = "";
        String userBirthday = "";
        String userHometown = "";
        String userCompany = "";
        String userAddress = "";
        String userEmail = "";
        String userRegistertime = "";
        try {
            // 创建数据库连接
            DataBaseConnection dataCon = new DataBaseConnection();

            // 查询个人信息
            String sqlString = "select * from aida_user where user_id = " + id;
            ResultSet resultSet = dataCon.getFromDataBase(sqlString);
            while (resultSet.next()) {
                userAvatarPath = resultSet.getString("user_avatar");
                userNickName = resultSet.getString("user_nickname");
                userSignature = resultSet.getString("user_signature");
                userSex = resultSet.getString("user_sex");
                userBirthday = resultSet.getString("user_birthday");
                userHometown = resultSet.getString("user_hometown");
                userCompany = resultSet.getString("user_company");
                userAddress = resultSet.getString("user_address");
                userEmail = resultSet.getString("user_email");
                userRegistertime = resultSet.getString("user_registertime");
            }
            resultSet.close();
            userAvatar = Imagebase64.getImageBinary("avatar/"+userAvatarPath);

            // 关闭数据库连接
            resultSet.close();
            dataCon.close();
            // 创建对象
            pureInfo = new PureInfo(id, userAvatar, userNickName, userSignature, userSex, userBirthday, userHometown,
                    userCompany, userAddress, userEmail, userRegistertime);
        } catch (SQLException e) {
            System.out.println("获取用户信息失败：" + e.getMessage());
        }

        return pureInfo;
    }

    // 修改资料
    public static boolean editInfo(PureInfo info) {
        System.out.println("In editInfo");

        String avatarPath = info.getUserId() + ".jpg";
        try {
            // 存储头像
            BufferedImage bImage = (BufferedImage) Imagebase64.base64StringToImage(info.getUserAvatar());
            File f = new File( "avatar/" + avatarPath);
            // 若存在，先删除
            if (f.exists()) {
                f.delete();
                f.createNewFile();
            }
            ImageIO.write(bImage, "jpg", f);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println(avatarPath);
        System.out.println(info.getUserNickName());
        System.out.println(info.getuserSignature());
        System.out.println(info.getUserSex());
        System.out.println(info.getUserBirthday());
        System.out.println(info.getUserHometown());
        System.out.println(info.getUserCompany());
        System.out.println(info.getUserCompany());
        System.out.println(info.getUserAddress());
        System.out.println(info.getUserEmail());

        DataBaseConnection dataCon = new DataBaseConnection();
        String sql = "update aida_user set " +
                "user_avatar = \"" + avatarPath +
                "\", user_nickname = \"" + info.getUserNickName() +
                "\", user_signature = \"" + info.getuserSignature() +
                "\", user_sex = \"" + info.getUserSex() +
                "\", user_birthday = \"" + info.getUserBirthday() +
                "\", user_hometown = \"" + info.getUserHometown() +
                "\", user_company = \"" + info.getUserCompany() +
                "\", user_address = \"" + info.getUserAddress() +
                "\", user_email = \"" + info.getUserEmail() +
                "\" where (user_id = " + info.getUserId() +")";
        if(dataCon.updateDataBase(sql))
            return true;

        return false;
    }

    // 创建群聊
    public static UserInfo.FriendsOrGroups createGroup(String master, String groupName) {
        System.out.println("In createGroup");

        FriendsOrGroups group = null;

        String nextID = "";
        DataBaseConnection dataCon = new DataBaseConnection();

        // 获取创建的群号
        String sql = "SELECT max(group_id) FROM oicq.aida_group;";
        ResultSet resultSet = dataCon.getFromDataBase(sql);
        try {
            while (resultSet.next()) {
                nextID = resultSet.getString("max(group_id)");
                nextID = String.valueOf(Integer.parseInt(nextID) + 1);
                System.out.println("创建的群号" + nextID);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("获取最大群号信息失败 " + e.getMessage());
        }

        // 新建群
        sql = "insert into aida_group (\n" +
                "    group_id ,\n" +
                "    group_name ,\n" +
                "    group_master \n" +
                ") value (\n" +
                nextID + " ,\n" +
                "    \"" + groupName + "\",\n" +
                "    \"" + master + "\"\n" +
                ");";
        dataCon.updateDataBase(sql);

        // 建完群拉去返回信息
        sql = "select * from aida_group where group_id = " + nextID;
        resultSet = dataCon.getFromDataBase(sql);
        try {
            while (resultSet.next()) {
                String gId = resultSet.getString("group_id");
                String gName = resultSet.getString("group_name");
                String gAvatarPath = resultSet.getString("group_avatar");
                String gAvatar = Imagebase64.getImageBinary("avatar/groupAvatar/"+gAvatarPath);
                group = new FriendsOrGroups(gId, gName, gAvatar, "", "");
            }
            resultSet.close();
            dataCon.close();
        } catch (SQLException e) {
            System.out.println("获取群信息失败 " + e.getMessage());
        }

        return group;
    }

    // 踢出群聊
    public static boolean deleteGrouopMember(String gid, String uid) {
        System.out.println("In deleteGrouopMember");

        String sql = "delete from aida_groupuser where group_id=" + gid +" and user_id=" + uid ;
        DataBaseConnection dataCon = new DataBaseConnection();

        if (dataCon.updateDataBase(sql))
            return true;

        return false;
    }

    // 存消息记录
    public static boolean saveMessage(String time, String content, String fromid, String toid, boolean isGroup) {
        System.out.println("In saveMessage");

        String sql = "insert into aida_";

        if (isGroup) {
            sql += "group";
        }

        sql += "chathistory (message_time, message_content, message_fromid, message_toid)" +
                " value (\"" + time + "\", \"" + content + "\", " + fromid + ", " + toid +")";
        DataBaseConnection dataCon = new DataBaseConnection();

        if (dataCon.updateDataBase(sql))
            return true;
        return false;
    }

    // 历史记录
    public static Vector<String> getChatRecord(String uid, String fid, boolean isGroup) {
        System.out.println("In getChatRecord");
        Vector<String> chatRecord = new Vector<String>();
        String in_sql = null;

        // 好友历史记录, toid就是toid
        if(!isGroup) {
            in_sql = "select * from aida_chathistory where (message_fromid="+uid+" and message_toid="+fid+") or (" +
                    "message_fromid="+fid+" and message_toid="+uid+")";
        } else {
            // 群消息，toid是群号即此处fid
            in_sql = "select * from aida_groupchathistory where message_toid=" + fid;
        }
            // 按message_id倒序排序，最多查询50条
            String sql = "select * from (select * from (" + in_sql + " order by message_id DESC) temp limit 50 ) temp2 order by message_id";
            DataBaseConnection dataCon = new DataBaseConnection();
            ResultSet resultSet = dataCon.getFromDataBase(sql);
            try {
                while (resultSet.next()) {
                    String time = resultSet.getString("message_time");
                    String content = resultSet.getString("message_content");
                    String fromid = resultSet.getString("message_fromid");
                    String toid = resultSet.getString("message_toid");
                    // 发送给好友的消息
                    // message, 时间```fromid```toid```content
                    String message = time + "```" + fromid + "```" + toid + "```" + content;
                    chatRecord.add(message);
                }
            } catch (SQLException e) {
                System.out.println("获取历史消息失败 " + e.getMessage());
            }

        System.out.println("历史消息数量: " + chatRecord.size());
        return chatRecord;
    }
}

class Imagebase64 {
    static BASE64Encoder encoder = new BASE64Encoder();
    static BASE64Decoder decoder = new BASE64Decoder();

    public static void main(String[] args) {
        System.out.println(getImageBinary("avatar/2.jpg")); // image to base64

        Image a = base64StringToImage(getImageBinary("avatar/2.jpg")); // base64 to image
        JFrame demo = new JFrame("test");
        demo.setSize(200, 200);
        JButton db = new JButton();
        db.setSize(200, 200);
        db.setIcon(new ImageIcon(a));
        demo.getContentPane().add(db);
        demo.setVisible(true);
    }

    static String getImageBinary(String filePath) {
        File f = new File(filePath);
        try {
            BufferedImage bi = ImageIO.read(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            return encoder.encodeBuffer(bytes).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static Image base64StringToImage(String base64String) {
        Image demo = null;
        try {
            byte[] bytes = decoder.decodeBuffer(base64String);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            demo = ImageIO.read(bais);
            } catch (IOException e) {
            e.printStackTrace();
        }
        return demo;
    }
}
