package config;

import java.io.Serializable;

public class PureInfo implements Serializable {
     private String userId;
     private String userAvatar;
     private String userNickName;
     private String userSignature;
     private String userSex;
     private String userBirthday;
     private String userHometown;
     private String userCompany;
     private String userAddress;
     private String userEmail;
     private String userRegistertime;
     public PureInfo(String userId, String userAvatar, String userNickName, String userSignature,
                     String userSex, String userBirthday, String userHometown, String userCompany,
                     String userAddress, String userEmail, String userRegistertime) {
         this.userId = userId;
         this.userAvatar = userAvatar;
         this.userNickName = userNickName;
         this.userSignature = userSignature;
         this.userSex = userSex;
         this.userBirthday = userBirthday;
         this.userHometown = userHometown;
         this.userCompany = userCompany;
         this.userAddress = userAddress;
         this.userEmail = userEmail;
         this.userRegistertime = userRegistertime;
     }

    public String getUserId() {
        return userId;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public String getuserSignature() {
        return userSignature;
    }

    public String getUserSex() {
        return userSex;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public String getUserHometown() {
        return userHometown;
    }

    public String getUserCompany() {
        return userCompany;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserRegistertime() {
        return userRegistertime;
    }
}
