package frame.MainInterface;

import client.ChatThread;
import client.InteractWithServer;
import frame.Listener.LoginListener;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class UI_Login extends JFrame {
    // upPanel
    private JPanel upPanel;
    private JButton closeButton, minimizeButton;
    private JLabel bannerLabel;
    // downPanel
    private JPanel downPanel;
    private JButton loginButton;
    private RoundHeadPortrait headPortrait;
    private JTextField userID;
    private JPasswordField passwd;
    private JCheckBox rememberPasswd, autoLogin;
    private JLabel register;
    // registerPanel
    private JPanel registerPanel;
    private JTextField nickname;
    private JPasswordField inputPsk, confirmPsk;
    private JButton registerButton, goback;
    // loadingPanel
    private JPanel loadingPanel;
    private JLabel loadingText;
    static public User user;

    public UI_Login() {

        setTitle("登录窗口");
        setIconImage(Toolkit.getDefaultToolkit().createImage("./res/Login/qq_logo.png")); // 设置小图标
        setUndecorated(true); // 消除外边框
        Container c = this.getContentPane(); // 获取容器
        c.setBackground(new Color(238, 255, 250)); // 设置容器背景
        setResizable(false); // 不可调整大小
        setLayout(null);

        init();

        // 初始不显示注册面板
        upPanel.setVisible(true);
        downPanel.setVisible(true);
        registerPanel.setVisible(false);
        loadingPanel.setVisible(false);

        // 实现可拖动
        DragWindowListener adpter = new DragWindowListener();
        addMouseListener(adpter);
        addMouseMotionListener(adpter);

        setSize(upPanel.getWidth(), upPanel.getHeight()+downPanel.getHeight());
        try {
            FileInputStream in = new FileInputStream("res/UserInfo.uif");
            int t;
            String username = "";
            String userPasswd = "";
            while ((t = in.read()) != -1) {
                if (t == '\n')
                    break;
                username = username + (char) t;
            }
            if (!username.equals("")) {
                while ((t = in.read()) != -1) {
                    if (t == '\n')
                        break;
                    userPasswd = userPasswd + (char) t;
                }
                userID.setForeground(Color.BLACK);
                userID.setText(username);
                passwd.setEchoChar('•');
                passwd.setForeground(Color.BLACK);
                passwd.setText(userPasswd);
                t = (char) in.read();
                rememberPasswd.setSelected(true);
                if (t == '1') {
                    autoLogin.setSelected(true);
                    // todo something auto login
                }
            }
            in.close();
        } catch (Exception e) {
        }
        //等待后续开发
        autoLogin.setSelected(false);
        autoLogin.setEnabled(false);
        setLocationRelativeTo(null); // 窗口居中
        setVisible(true);
    }

    private void init() {
        initupPanel();
        initdownPanel();
        initregisterPanel();
        initloadingPanel();

        add(upPanel);
        add(downPanel);
        add(registerPanel);
        add(loadingPanel);
    }

    // 初始化upPanel
    private void initupPanel() {
        // 上半部分
        upPanel = new JPanel();
        upPanel.setBounds(0, 0, 430, 180);
        upPanel.setBackground(new Color(0, 125, 255));
        upPanel.setLayout(null);
        // 关闭按钮
        closeButton = new UI_Close();
        closeButton.setBounds(upPanel.getWidth()-30, 0, 30, 30);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        upPanel.add(closeButton);
        // 最小化按钮
        minimizeButton = new UI_Minimize();
        minimizeButton.setBounds(upPanel.getWidth()-60, 0, 30, 30);
        minimizeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setExtendedState(JFrame.ICONIFIED);
            }
        });
        upPanel.add(minimizeButton);
        // AIDA标语
        bannerLabel = new JLabel("AIDA");
        bannerLabel.setBounds(155, 60, 120, 60);
        bannerLabel.setFont(new Font("黑体", Font.PLAIN, 60));
        upPanel.add(bannerLabel);
    }

    // 初始化downPanel
    private void initdownPanel() {
        // 下半部分
        downPanel = new JPanel();
        downPanel.setBounds(0, upPanel.getHeight(), upPanel.getWidth(), 150);
        downPanel.setBackground(new Color(238, 255, 250));
        downPanel.setLayout(null);
        // 头像框
        try {
            headPortrait = new RoundHeadPortrait(80, 80, Color.black, "./res/Login/headPortrait_80x80.png");
//            headPortrait = new RoundHeadPortrait(80, 80, new ImageIcon("./res/Login/headPortrait_80x80.png"));
        } catch (Exception e) {
            System.out.println("Can't get headPortrait.");
        }
        headPortrait.setLocation(40, 20);
        downPanel.add(headPortrait);
        // 账号输入框
        userID = new JTextField("账号");
        userID.setBounds(130, 20, 190, 30);
        userID.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        userID.setForeground(Color.GRAY);
        userID.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                if(userID.getText().equals("账号")) {
                    userID.setForeground(Color.BLACK);
                    userID.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if(userID.getText().trim().equals("")) {
                    userID.setForeground(Color.GRAY);
                    userID.setText("账号");
                }
            }
        });
        downPanel.add(userID);
        // 密码输入框
        passwd = new JPasswordField("密码");
        passwd.setEchoChar((char)0);
        passwd.setBounds(userID.getX(), userID.getY()+30, userID.getWidth(), userID.getHeight());
        passwd.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        passwd.setForeground(Color.GRAY);
        passwd.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if(String.valueOf(passwd.getPassword()).equals("密码")) {
                    passwd.setForeground(Color.BLACK);
                    passwd.setEchoChar('•');
                    passwd.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if(String.valueOf(passwd.getPassword()).equals("")) {
                    passwd.setForeground(Color.GRAY);
                    passwd.setEchoChar((char)0);
                    passwd.setText("密码");
                }
            }
        });
        downPanel.add(passwd);
        // 记住密码
        rememberPasswd = new JCheckBox("记住密码");
        rememberPasswd.setBounds(passwd.getX(), passwd.getY()+passwd.getHeight()+4, 80, 15);
        rememberPasswd.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        rememberPasswd.setFocusPainted(false);
        rememberPasswd.setOpaque(false); // 取消不透明
        rememberPasswd.setIcon(new ImageIcon("res/Login/checkbox_normal.png"));
        rememberPasswd.setRolloverIcon(new ImageIcon("res/Login/checkbox_hover.png"));
        rememberPasswd.setPressedIcon(new ImageIcon("res/Login/checkbox_press.png"));
        rememberPasswd.setSelectedIcon(new ImageIcon("res/Login/checkbox_tick_normal.png"));
        rememberPasswd.setRolloverSelectedIcon(new ImageIcon("res/Login/checkbox_tick_highlight.png"));
        rememberPasswd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!rememberPasswd.isSelected())
                    autoLogin.setSelected(false);
            }
        });
        downPanel.add(rememberPasswd);
        // 自动登录
        autoLogin = new JCheckBox("自动登录");
        autoLogin.setBounds(passwd.getX()+passwd.getWidth()-80, rememberPasswd.getY(), 80, 15);
        autoLogin.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        autoLogin.setFocusPainted(false);
        autoLogin.setOpaque(false); // 取消不透明
        autoLogin.setIcon(new ImageIcon("res/Login/checkbox_normal.png"));
        autoLogin.setRolloverIcon(new ImageIcon("res/Login/checkbox_hover.png"));
        autoLogin.setPressedIcon(new ImageIcon("res/Login/checkbox_press.png"));
        autoLogin.setSelectedIcon(new ImageIcon("res/Login/checkbox_tick_normal.png"));
        autoLogin.setRolloverSelectedIcon(new ImageIcon("res/Login/checkbox_tick_highlight.png"));
        autoLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(autoLogin.isSelected())
                    rememberPasswd.setSelected(true);
            }
        });

        downPanel.add(autoLogin);
        // 注册
        register = new JLabel("注册账号");
        register.setFont(new Font("微软雅黑", Font.BOLD,14));
        register.setForeground(new Color(2, 167, 240));
        register.setBounds(userID.getX()+userID.getWidth()+10, userID.getY()+5, 80, 16);
        register.addMouseListener(new MouseAdapter() {
            // 点击注册后窗口切换动画
            public void mouseClicked(MouseEvent e) {
                toRegisterPanel();
                super.mouseClicked(e);
            }

            public void mouseEntered(MouseEvent e) {
                register.setForeground(new Color(2, 200, 240));
                super.mouseEntered(e);
            }

            public void mouseExited(MouseEvent e) {
                register.setForeground(new Color(2, 167, 240));
                super.mouseExited(e);
            }
        });
        downPanel.add(register);
        // 登录按钮
        loginButton = new JButton("登录");
        loginButton.setBounds(userID.getX(), rememberPasswd.getY()+rememberPasswd.getHeight()+7, 190, 30);
        loginButton.setFocusPainted(false); // 去除选中时文字焦点
        loginButton.setBackground(new Color(9, 163, 220));
        LoginListener loginListener=new LoginListener(this,userID,passwd,rememberPasswd,autoLogin);
        loginButton.addActionListener(loginListener);
        downPanel.add(loginButton);
    }

    // 初始化registerPanel
    private void initregisterPanel() {
        // 注册面板
        registerPanel = new JPanel();
        registerPanel.setBounds(0, 110, 430, 250);
        registerPanel.setBackground(new Color(238, 255, 250));
        registerPanel.setLayout(null);
        // 输入昵称
        nickname = new JTextField("昵称");
        nickname.setBounds(70, 40, 300, 25);
        nickname.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        nickname.setForeground(Color.GRAY);
        nickname.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                if(nickname.getText().equals("昵称")) {
                    nickname.setForeground(Color.BLACK);
                    nickname.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if(nickname.getText().trim().equals("")) {
                    nickname.setForeground(Color.GRAY);
                    nickname.setText("昵称");
                }
            }
        });
        registerPanel.add(nickname);
        // 输入密码
        inputPsk = new JPasswordField("密码");
        inputPsk.setBounds(70, 85, 300, 25);
        inputPsk.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        inputPsk.setForeground(Color.GRAY);
        inputPsk.setEchoChar((char)0);
        inputPsk.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if(String.valueOf(inputPsk.getPassword()).equals("密码")) {
                    inputPsk.setForeground(Color.BLACK);
                    inputPsk.setEchoChar('•');
                    inputPsk.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if(String.valueOf(inputPsk.getPassword()).equals("")) {
                    inputPsk.setForeground(Color.GRAY);
                    inputPsk.setEchoChar((char)0);
                    inputPsk.setText("密码");
                }
            }
        });
        registerPanel.add(inputPsk);
        // 确认密码
        confirmPsk = new JPasswordField("确认密码");
        confirmPsk.setBounds(70, 130, 300, 25);
        confirmPsk.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        confirmPsk.setForeground(Color.GRAY);
        confirmPsk.setEchoChar((char)0);
        confirmPsk.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if(String.valueOf(confirmPsk.getPassword()).equals("确认密码")) {
                    confirmPsk.setForeground(Color.BLACK);
                    confirmPsk.setEchoChar('•');
                    confirmPsk.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if(String.valueOf(confirmPsk.getPassword()).equals("")) {
                    confirmPsk.setForeground(Color.GRAY);
                    confirmPsk.setEchoChar((char)0);
                    confirmPsk.setText("确认密码");
                }
            }
        });
        registerPanel.add(confirmPsk);
        // 注册按钮
        registerButton = new JButton("注册");
        registerButton.setBounds(130, 170, 190, 30);
        registerButton.setFocusPainted(false); // 去除选中时文字焦点
        registerButton.setBackground(new Color(9, 163, 220));
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String uID=InteractWithServer.register(nickname.getText(),String.valueOf(inputPsk.getPassword()));
                System.out.println(uID);
                if (!uID.equals("")){
                    System.out.println("try login");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // 获取文本框内容
                            String userIdString = uID;
                            String userPasswordString = String.valueOf(inputPsk.getPassword()).trim();
                            UI_Login.this.loading();
                            // 验证用户或密码是否正确
                            Object isLoginSuccess = InteractWithServer.isLogin(userIdString, userPasswordString);
                            System.out.println("当前登录状态：" + isLoginSuccess);
                            if (isLoginSuccess != null) {
                                String loginResult = isLoginSuccess.toString();
                                if (loginResult.equals("true")) {
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
                                        UI_Login.this.dispose();
                                    } catch (IOException | InterruptedException ex) {
                                        ex.printStackTrace();
                                    }
                                } else if (loginResult.equals("Repeat_login")) {
                                    JOptionPane.showMessageDialog(UI_Login.this, "重复登录");
                                    UI_Login.this.backtoLoginPanel();
                                } else {
                                    JOptionPane.showMessageDialog(UI_Login.this, "您的登陆信息有误", "登陆失败", JOptionPane.WARNING_MESSAGE);
                                    UI_Login.this.backtoLoginPanel();

                                }
                            } else {
                                JOptionPane.showMessageDialog(UI_Login.this, "与服务器建立连接失败");
                                UI_Login.this.backtoLoginPanel();
                            }
                        }
                    }).start();
                }else {
                    JOptionPane.showMessageDialog(UI_Login.this, "注册失败", "错误", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        registerPanel.add(registerButton);
        // 返回按钮
        goback = new JButton();
        goback.setBounds(0, 0, 30 , 30);
        goback.setFocusPainted(false);
        goback.setContentAreaFilled(false);
        goback.setBorderPainted(false);
        goback.setIcon(new ImageIcon("res/Login/left_30x30.png"));
        goback.setRolloverIcon(new ImageIcon("res/Login/left_hover.png"));
        goback.setPressedIcon(new ImageIcon("res/Login/left_down.png"));
        goback.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toLoginPanel();
            }
        });
        upPanel.add(goback);
        goback.setVisible(false);
    }

    // 初始化loadingPanel
    private void initloadingPanel() {
        loadingPanel = new JPanel();
        loadingPanel.setBounds(0, upPanel.getHeight(), upPanel.getWidth(), 150);
        loadingPanel.setBackground(new Color(238, 255, 250));
        loadingPanel.setLayout(null);

        loadingText = new JLabel("正在登陆中...");
        loadingText.setFont(new Font("微软雅黑", Font.BOLD, 14));
        loadingText.setBounds(170, 120, 100, 16);
        loadingPanel.add(loadingText);

    }

    // 切换到注册面板
    private void toRegisterPanel() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                downPanel.setVisible(false);
                for(int i=1; i<=70; i++) {
                    try{
                        Thread.sleep(2);
                    } catch (Exception e) {
                        System.out.println();
                    }
                    upPanel.setSize(430, 180-i);
                    bannerLabel.setLocation(155, (upPanel.getHeight()-bannerLabel.getHeight())/2);
                }
                goback.setVisible(true);
                registerPanel.setVisible(true);
            }
        });
        t.start();
    }

    // 切换到初始登录界面
    public void toLoginPanel() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                registerPanel.setVisible(false);
                for(int i=1; i<=70; i++) {
                    try {
                        Thread.sleep(2);
                    } catch (Exception e) {
                        System.out.println();
                    }
                    upPanel.setSize(430, 110+i);
                    bannerLabel.setLocation(155, (upPanel.getHeight()-bannerLabel.getHeight())/2);
                }
                downPanel.setVisible(true);
                goback.setVisible(false);
            }
        });
        t.start();
    }

    // 加载返回登录
    public void backtoLoginPanel() {
        loadingPanel.setVisible(false);
        headPortrait.setLocation(40, 20);
        downPanel.add(headPortrait);
        downPanel.setVisible(true);
    }

    // 登陆中
    public void loading() {
        // 先将头像框加到loadingPanel
        loadingPanel.add(headPortrait);
        // 判断是从注册跳转而来还是直接登录
        if(upPanel.getHeight() == 110) {
            // 从注册跳转
            Thread t = new Thread(new Runnable() {
                public void run() {
                    goback.setVisible(false);
                    registerPanel.setVisible(false);
                    for(int i=1; i<=70; i++) {
                        try{
                            Thread.sleep(2);
                        } catch (Exception e) {
                            System.out.println();
                        }
                        upPanel.setSize(430, 110+i);
                        bannerLabel.setLocation(155, (upPanel.getHeight()-bannerLabel.getHeight())/2);
                    }
                    headPortrait.setLocation(175, 20);
                    loadingPanel.setVisible(true);
                }
            });
            t.start();
        } else {
            // 从登录跳转
            Thread t = new Thread(new Runnable() {
                public void run() {
                    downPanel.setVisible(false);
                    loadingPanel.setVisible(true);
                    headPortrait.setLocation(175, 20);
//
//                    //
//                    try{
//                        Thread.sleep(1000);
//                    } catch (Exception e) {
//                        System.out.println();
//                    }
                }
            });
            t.start();
        }
    }

    public static void main(String[] args) {
        JFrame demo = new UI_Login();
    }
}

