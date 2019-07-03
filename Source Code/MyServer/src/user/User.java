package user;

import java.util.Vector;
import config.UserInfo;

public class User extends UserInfo {
    public User(String userId, String userAvatar, String userNickName, String userSignatrue,
                String userSex, String userBirthday, String userHometown, String userCompany,
                String userAddress, String userEmail, String userRegistertime, Vector<FriendsOrGroups> friends,
                Vector<FriendsOrGroups> groups) {
        this.userId = userId;
        this.userAvatar = userAvatar;
        this.userNickName = userNickName;
        this.userSignatrue = userSignatrue;
        this.userSex = userSex;
        this.userBirthday = userBirthday;
        this.userHometown = userHometown;
        this.userCompany = userCompany;
        this.userAddress = userAddress;
        this.userEmail = userEmail;
        this.userRegistertime = userRegistertime;
        this.friends = friends;
        this.groups = groups;
    }
    public String  getUserId(){
        return userId;
    }
    public String  getUserAvatar(){
        return userAvatar;
    }
    public String  getUserName(){
        return userNickName;
    }
    public String  getUserSignatrue(){
        return userSignatrue;
    }
    public String  getUserSex(){
        return userSex;
    }
    public String  getUserBirthday(){
        return userBirthday;
    }
    public String  getUserHometown(){
        return userHometown;
    }
    public String  getUserCompany(){
        return userCompany;
    }
    public String  getUserAddress(){
        return userAddress;
    }
    public String  getUserRegistertime(){
        return userRegistertime;
    }
    public String  getUserEmail(){
        return userRegistertime;
    }
    public Vector<FriendsOrGroups>  getFriends(){
        return friends;
    }
    public Vector<FriendsOrGroups>  getGroups(){
        return groups;
    }
}
