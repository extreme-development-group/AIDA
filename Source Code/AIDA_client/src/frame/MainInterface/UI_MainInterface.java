package frame.MainInterface;

import client.InteractWithServer;
import frame.ChatFrame.ChatWithFriends;
import frame.ChatFrame.ChatWithGroup;
import frame.ChatFrame.GetAvatar;
import frame.ChatFrame.HeadPortrait;
import user.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicMenuItemUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UI_MainInterface extends JFrame {
    // upPanel
    private JPanel upPanel, choosePanel;
    private JButton minimizeButton, closeButton;
    private JButton headPortrait;
    private JLabel nickname;
    private JTextField signature;
    private JButton friendsButton, groupsButton;
    private JLabel divLine1, divLine2, divLine3;
    // friendListPanel
    private JPanel friendListPanel;
    private listScrollPanel friendListJSP;
    // friendPanel
    // groupListPanel
    private JPanel groupListPanel;
    private listScrollPanel groupListJSP;
    // groupPanel
    //user information
    public User userInfo;
    public static HashMap<String, friendPanel> friend;
    public static HashMap<String, groupPanel> group;
    public static HashMap<String, ChatWithFriends> withFriend;
    public static HashMap<String, ChatWithGroup> withGroup;

    public UI_MainInterface(String userIdString) throws IOException {
        //信息载入
        userInfo= InteractWithServer.getUserInfo(userIdString);
        // 调试
        System.out.println("----------- 个人信息 --------------");
        System.out.println("ID：" + userInfo.getUserId());
        System.out.println("昵称：" + userInfo.getUserName());
        System.out.println("Email：" + userInfo.getUserEmail());
        System.out.println("性别：" + userInfo.getUserSex());
        System.out.println("生日：" + userInfo.getUserBirthday());
        System.out.println("头像：" + userInfo.getUserAvatar());
        System.out.println("个性签名：" + userInfo.getUserSignatrue());
        System.out.println("注册时间：" + userInfo.getUserRegistertime());
        System.out.print("好友列表 :");
        for (int i = 0; i < userInfo.getFriends().size(); i++)
            System.out.print(userInfo.getFriends().get(i).getName() + " ");
        System.out.print("\n群列表 ： ");
        for (int i = 0; i < userInfo.getGroups().size(); i++)
            System.out.print(userInfo.getGroups().get(i).getName() + " ");
        System.out.println("\n----------- END --------------");




        setIconImage(Toolkit.getDefaultToolkit().createImage("res/MainInterface/qq_logo.png"));
        setUndecorated(true);
        setResizable(false);
        Container c = this.getContentPane(); // 获取容器
        c.setBackground(new Color(238, 255, 250)); // 设置容器背景
        setLayout(null);

        init();

        DragWindowListener adapter = new DragWindowListener();
        addMouseListener(adapter);
        addMouseMotionListener(adapter);

        setSize(upPanel.getWidth(), upPanel.getHeight()+400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static HashMap<String, friendPanel> getFriend() {
        return friend;
    }

    public static void setFriend(HashMap<String, friendPanel> friend) {
        UI_MainInterface.friend = friend;
    }

    public static HashMap<String, groupPanel> getGroup() {
        return group;
    }

    public static void setGroup(HashMap<String, groupPanel> group) {
        UI_MainInterface.group = group;
    }
    public static HashMap<String, ChatWithFriends> getFriendChat() {
        return withFriend;
    }

    public static HashMap<String, ChatWithGroup> getGroupChat() {
        return withGroup;
    }

    private void init() throws IOException {
        initupPanel();
        initListPanel();

        add(upPanel);
        add(friendListJSP);
        add(groupListJSP);
    }

    private void initupPanel() {
        // 上半部分
        upPanel = new JPanel();
        upPanel.setBounds(0, 0, 250, 165);
        upPanel.setBackground(new Color(2, 167, 240));
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
        // 头像框
        //
        //设置头像
        //
        try {
            headPortrait = new RoundHeadPortrait(70, 70, new Color(128, 255, 255), "res/MainInterface/headPortrait.jpg");
        } catch (Exception e) {
            System.out.println("Get headPortrait ERROR!");
        }
        headPortrait.setLocation(20, 40);
        upPanel.add(headPortrait);
        // 昵称
        nickname = new JLabel(userInfo.getUserName());
        nickname.setBounds(105, 50, 100, 16);
        nickname.setFont(new Font("微软雅黑", Font.BOLD, 14));
        nickname.setForeground(Color.WHITE);
        upPanel.add(nickname);
        // 个性签名
        signature = new JTextField(userInfo.getUserSignatrue());
        signature.setBounds(105, 80, 130, 16);
        signature.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        signature.setBorder(null);
        signature.setForeground(Color.black);
        signature.setBackground(null);
        signature.setEnabled(false); // 设为不可编辑有bug，直接禁用
        signature.setDisabledTextColor(Color.black);
        signature.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // 双击编辑个性签名
                if(e.getClickCount() == 2) {
                    if(!signature.isEnabled()) {
                        signature.setEnabled(true);
                        signature.setBackground(Color.white);
                        signature.requestFocus();
                        signature.selectAll();
                    }
                }
                super.mouseClicked(e);
            }
        });
        signature.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                // nothing to do
            }
            // 失去焦点不可编辑
            public void focusLost(FocusEvent e) {
                // 点击别的窗体失去焦点时，不加这两行仍未选中状态
                signature.setSelectionStart(0);
                signature.setSelectionEnd(0);
                signature.setBackground(null);
                signature.setEnabled(false);
                if(signature.getText().equals("")) {
                    signature.setText("个性签名");
                }
            }
        });
        upPanel.add(signature);
        // 选择面板
        choosePanel = new JPanel();
        choosePanel.setBounds(0, 130, 250, 35);
        choosePanel.setBackground(new Color(242,242,242));
        choosePanel.setLayout(null);
        // 好友按钮
        friendsButton = new JButton();
        friendsButton.setBounds(0, 1, 125, 33);
        friendsButton.setContentAreaFilled(false);
        friendsButton.setFocusPainted(false);
        friendsButton.setBorderPainted(false);
        friendsButton.setOpaque(false);
//        friendsButton.setIcon(new ImageIcon("res/MainInterface/icon_contacts_normal.png"));
//        friendsButton.setRolloverIcon(new ImageIcon("res/MainInterface/icon_contacts_hover.png"));
        // 初始即为按下状态
        friendsButton.setIcon(new ImageIcon("res/MainInterface/icon_contacts_selected.png"));
        friendsButton.setRolloverIcon(new ImageIcon("res/MainInterface/icon_contacts_selected.png"));
        friendsButton.setPressedIcon(new ImageIcon("res/MainInterface/icon_contacts_selected.png"));
        friendsButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // 保持按下
                friendListJSP.setVisible(true);
                friendsButton.setIcon(new ImageIcon("res/MainInterface/icon_contacts_selected.png"));
                friendsButton.setRolloverIcon(new ImageIcon("res/MainInterface/icon_contacts_selected.png"));
                // 另一弹起
                groupListJSP.setVisible(false);
                groupsButton.setIcon(new ImageIcon("res/MainInterface/icon_group_normal.png"));
                groupsButton.setRolloverIcon(new ImageIcon("res/MainInterface/icon_group_hover.png"));
                super.mouseClicked(e);
            }
        });
        choosePanel.add(friendsButton);
        // 群聊按钮
        groupsButton = new JButton();
        groupsButton.setBounds(126, 1, 124, 33);
        groupsButton.setContentAreaFilled(false);
        groupsButton.setFocusPainted(false);
        groupsButton.setBorderPainted(false);
        groupsButton.setOpaque(false);
        groupsButton.setIcon(new ImageIcon("res/MainInterface/icon_group_normal.png"));
        groupsButton.setRolloverIcon(new ImageIcon("res/MainInterface/icon_group_hover.png"));
        groupsButton.setPressedIcon(new ImageIcon("res/MainInterface/icon_group_selected.png"));
        groupsButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // 保持按下
                groupListJSP.setVisible(true);
                groupsButton.setIcon(new ImageIcon("res/MainInterface/icon_group_selected.png"));
                groupsButton.setRolloverIcon(new ImageIcon("res/MainInterface/icon_group_selected.png"));
                // 另一弹起
                friendListJSP.setVisible(false);
                friendsButton.setIcon(new ImageIcon("res/MainInterface/icon_contacts_normal.png"));
                friendsButton.setRolloverIcon(new ImageIcon("res/MainInterface/icon_contacts_hover.png"));
                super.mouseClicked(e);
            }
        });
        choosePanel.add(groupsButton);
        // 添加分割线
        divLine1 = new JLabel(); // 中
        divLine1.setBounds(125, 0, 1, 35);
        divLine1.setOpaque(true);
        divLine1.setBackground(new Color(121, 121, 121));
        choosePanel.add(divLine1);
        divLine2 = new JLabel(); // 上
        divLine2.setBounds(0, 0, 250, 1);
        divLine2.setOpaque(true);
        divLine2.setBackground(new Color(121, 121, 121));
        choosePanel.add(divLine2);
        divLine3 = new JLabel(); // 下
        divLine3.setBounds(0, 34, 250, 1);
        divLine3.setOpaque(true);
        divLine3.setBackground(new Color(121, 121, 121));
        choosePanel.add(divLine3);
        // choosePanel end
        upPanel.add(choosePanel);
    }

    private void initListPanel() throws IOException {
        friendListPanel = new listPanel();
        friendListJSP = new listScrollPanel(friendListPanel);
        add(friendListJSP);
        Image image= ImageIO.read(new File("res/Avatar/head-test.JPG"));

        for(int i=0; i<this.userInfo.getFriends().size(); i++) {
            friendListPanel.setPreferredSize(new Dimension(250, 50*i));
            friendPanel friendPanel= new friendPanel(this.userInfo.getFriends().get(i).getId(),
                    image,this.userInfo.getFriends().get(i).getName(),
                    this.userInfo.getFriends().get(i).getSignature(),
                    "在线",this);
            friendListPanel.add(friendPanel);
        }

        groupListPanel = new listPanel();
        groupListJSP = new listScrollPanel(groupListPanel);
        add(groupListJSP);

        for(int i=0; i<this.userInfo.getGroups().size(); i++) {
            groupListPanel.setPreferredSize(new Dimension(250, 50*i));
            groupPanel group=new groupPanel(this.userInfo.getGroups().get(i).getId(),
                    image,
                    this.userInfo.getGroups().get(i).getName(),
                    this.userInfo.getGroups().get(i).getSignature(),this);
            groupListPanel.add(group);
        }
        groupListJSP.setVisible(false);
    }

    public static void main(String[] args) throws IOException {
        UI_MainInterface demo = new UI_MainInterface("111");
    }

}

// friendPanel


