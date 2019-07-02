package config;
import java.io.Serializable;

public final class ChatVerify implements Serializable {
    private String userId;
    private String userPassword;
    public ChatVerify(String userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPassword() {
        return userPassword;
    }
}