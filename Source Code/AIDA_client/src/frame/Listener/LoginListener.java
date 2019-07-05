package frame.Listener;

import client.ChatThread;
import client.InteractWithServer;
import frame.ChatFrame.ChatFrame;
import frame.ChatFrame.ChatWithFriends;
import frame.MainInterface.UI_Login;
import frame.MainInterface.UI_MainInterface;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class LoginListener implements ActionListener{
    private JFrame now;
    private JTextField userId;
    private JPasswordField passwd;
    private JCheckBox isRemeberPasswd,isAutoLogin;
    private UI_Login login;
    static public User user;
    public LoginListener(JFrame now, JTextField userId, JPasswordField passwd, JCheckBox isRemeberPasswd, JCheckBox isAutoLogin){
        this.now=now;
        this.userId=userId;
        this.passwd=passwd;
        this.isRemeberPasswd=isRemeberPasswd;
        this.isAutoLogin=isAutoLogin;
        this.login=login;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("try login");
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 获取文本框内容
                String userIdString = userId.getText().trim();
                String userPasswordString = String.valueOf(passwd.getPassword()).trim();
                // 获取登录设置
                boolean isAutoLog = isAutoLogin.isSelected();
                boolean isRemeber = isRemeberPasswd.isSelected();
                if (isAutoLog) {
                    isRemeber = true;
                }
                ((UI_Login)now).loading();
                // 验证用户或密码是否正确
                Object isLoginSuccess = InteractWithServer.isLogin(userIdString, userPasswordString);
                System.out.println("当前登录状态：" + isLoginSuccess);
                if (isLoginSuccess != null) {
                    String loginResult = isLoginSuccess.toString();
                    if (loginResult.equals("true")) {
                        if (isRemeber) {
                            try {
                                FileOutputStream out = new FileOutputStream("res/UserInfo.uif");
                                for(int i=0;i<userIdString.length();i++){
                                    char t = userIdString.charAt(i);
                                    out.write(t);
                                }
                                //out.write(userIdString.getBytes());
                                out.write('\n');
                                for(int i=0;i<userPasswordString.length();i++){
                                    char t = userPasswordString.charAt(i);
                                    out.write(t);
                                }
                                //out.write(userPasswordString.getBytes());
                                out.write('\n');
                                if (isAutoLog) {
                                    out.write('1');
                                } else {
                                    out.write('0');
                                }
                                out.close();
                            } catch (Exception e) {
                                System.out.println("ListenterClass/actionPerformed Error " + e);
                            }
                        }
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                user = InteractWithServer.getUserInfo(userIdString);
                            }
                        });
                        t.start();
                        // 创建线程接入聊天端口
                        new Thread(new ChatThread(userIdString)).start();
                        try {
                            t.join();
                            new UI_MainInterface(user);
                            now.dispose();
                        } catch (IOException | InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    } else if (loginResult.equals("Repeat_login")) {
                        JOptionPane.showMessageDialog(now, "重复登录");
                        ((UI_Login) now).backtoLoginPanel();
                    } else {
                        JOptionPane.showMessageDialog(now, "您的登陆信息有误", "登陆失败", JOptionPane.WARNING_MESSAGE);
                        ((UI_Login) now).backtoLoginPanel();

                    }
                } else {
                    JOptionPane.showMessageDialog(now, "与服务器建立连接失败");
                    ((UI_Login) now).backtoLoginPanel();
                }
            }
        }).start();

    }
}