package config;
import java.awt.*;
import java.io.Serializable;
import java.util.Vector;

public class UserInfo  implements Serializable {
    protected String userId;
    protected String userAvatar;
    protected String userNickName;
    protected String userSignatrue;
    protected String userSex;
    protected String userBirthday;
    protected String userHometown;
    protected String userCompany;
    protected String userAddress;
    protected String userEmail;
    protected String userRegistertime;
    protected Vector<FriendsOrGroups> friends = new Vector<FriendsOrGroups>();
    protected Vector<FriendsOrGroups> groups = new Vector<FriendsOrGroups>();
    public static class FriendsOrGroups implements Serializable {
        private String id;
        private String name;
        private String avatar;
        private String signature;
        private String status;
        public FriendsOrGroups(String id, String name, String avatar, String signature, String status) {
            this.id = id;
            this.name = name;
            this.avatar = avatar;
            this.signature = signature;
            this.status = status;
        }
        public String getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public String getAvatar() {
            return avatar;
        }
        public String getSignature() {
            return signature;
        }
        public String getStatus() {
            return status;
        }
    }
}
