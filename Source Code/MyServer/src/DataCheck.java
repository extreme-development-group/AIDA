import user.User;
import config.UserInfo.FriendsOrGroups;

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
                userAvatar = resultSet.getString("user_avatar");
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
                String fAvatar = resultSet.getString("friend_avatar");
                String fNickName = resultSet.getString("friend_nickname");
                String fSignature = resultSet.getString("friend_signature");
                String fStatus = ServerListener.getClientUser().containsKey(fId) ? "在线" : "离线";
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
                String gAvatar = resultSet.getString("group_avatar");
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
                String gAvatar = resultSet.getString("group_avatar");
                groups.add(new FriendsOrGroups(gId, gName, gAvatar, "", ""));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("获取群信息失败 " + e.getMessage());
        }
        return groups;
    }

    // 获取user_id用户的好友
    public static Vector<String> getFriendMember(String user_id) {
        String sql = "select friend_id from aida_useruser where user_id = " + user_id;
        // 与数据库创建连接
        DataBaseConnection dataCon = new DataBaseConnection();

        // 最终结果Vector数组
        Vector<String> friends = new Vector<String>();

        // 利用该sql语句查询，返回ResultSet结果集
        ResultSet resultSet = dataCon.getFromDataBase(sql);
        try {
            while (resultSet.next()) {
                friends.add(resultSet.getString("friend_id"));
            }
            // 关闭连接
            dataCon.close();
        } catch (SQLException e) {
            System.out.println("查询成员ID列表失败：" + e.getMessage());
        }
        return friends;
    }

    // 查询群中所有成员的ID
    public static Vector<String> getGroupMember(String groupId) {
        // 与数据库创建连接
        DataBaseConnection dataCon = new DataBaseConnection();

        // 最终结果Vector数组
        Vector<String> member = new Vector<String>();

        // 自身为群主
        String sql = "select * from aida_group where group_id = " + groupId;
        // 利用该sql语句查询，返回ResultSet结果集
        ResultSet resultSet = dataCon.getFromDataBase(sql);
        resultSet = dataCon.getFromDataBase(sql);
        try {
            while (resultSet.next()) {
                member.add("0```" + resultSet.getString("group_master") + "```demo");
            }
        } catch (SQLException e) {
            System.out.println("获取群信息失败 " + e.getMessage());
        }
        sql = "select aida_groupuser.user_id, aida_user.user_nickname from aida_groupuser, aida_user where aida_groupuser.user_id=aida_user.user_id and group_id = " + groupId;
        resultSet = dataCon.getFromDataBase(sql);
        try {
            while (resultSet.next()) {
                member.add("2```" + resultSet.getString("user_id") +
                        "```" + resultSet.getString("user_nickname"));
            }
            // 关闭连接
            resultSet.close();
            dataCon.close();
        } catch (SQLException e) {
            System.out.println("查询成员ID列表失败：" + e.getMessage());
        }
        return member;
    }
}
