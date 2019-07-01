import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class UI_MainInterface extends JFrame {
    // upPanel
    private JPanel upPanel, choosePanel;
    private JButton minimizeButton, closeButton;
    private RoundHeadPortrait headPortrait;
    private JLabel nickname;
    private JTextField signature;
    private JButton friendsButton, groupsButton;
    private JLabel divLine1, divLine2, divLine3;
    // friendListPanel
    private JPanel friendListPanel;
    // friendPanel
    // groupListPanel
    private JPanel groupListPanel;
    // groupPanel

    UI_MainInterface() {
        setTitle("NickName"+" -- 在线");
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

        setSize(upPanel.getWidth(), 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init() {
        initupPanel();
        initListPanel();

        add(upPanel);
        add(friendListPanel);
        add(groupListPanel);
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
        try {
            headPortrait = new RoundHeadPortrait(70, 70, new Color(128, 255, 255),"res/MainInterface/headPortrait.jpg");
        } catch (Exception e) {
            System.out.println("Get headPortrait ERROR!");
        }
        headPortrait.setLocation(20, 40);
        upPanel.add(headPortrait);
        // 昵称
        nickname = new JLabel("昵称");
        nickname.setBounds(105, 50, 100, 16);
        nickname.setFont(new Font("微软雅黑", Font.BOLD, 15));
        nickname.setForeground(Color.WHITE);
        upPanel.add(nickname);
        // 个性签名
        signature = new JTextField("个性签名");
        signature.setBounds(105, 80, 130, 16);
        signature.setFont(new Font("微软雅黑", Font.PLAIN, 14));
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
//                signature.setEditable(false);
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
                friendListPanel.setVisible(true);
                friendsButton.setIcon(new ImageIcon("res/MainInterface/icon_contacts_selected.png"));
                friendsButton.setRolloverIcon(new ImageIcon("res/MainInterface/icon_contacts_selected.png"));
                // 另一弹起
                groupListPanel.setVisible(false);
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
                groupListPanel.setVisible(true);
                groupsButton.setIcon(new ImageIcon("res/MainInterface/icon_group_selected.png"));
                groupsButton.setRolloverIcon(new ImageIcon("res/MainInterface/icon_group_selected.png"));
                // 另一弹起
                friendListPanel.setVisible(false);
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

    private void initListPanel() {
        friendListPanel = new listPanel();
        friendListPanel.add(new friendPanel());

        groupListPanel = new listPanel();
        groupListPanel.add(new groupPanel());
        groupListPanel.setVisible(false);
    }

    public static void main(String[] args) {
        UI_MainInterface demo = new UI_MainInterface();
    }

}

// listPanel
class listPanel extends JPanel {
    listPanel() {
        setBounds(0, 165, 250, 400);
        setBackground(new Color(238, 255, 250));
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    }
}

// friendPanel
class friendPanel extends JPanel {
    private RoundHeadPortrait headPortrait;
    private JLabel nickname, status, signature;
    friendPanel() {
        setPreferredSize(new Dimension(250, 50));
        setBackground(new Color(230, 230, 230, 200));
        setLayout(null);
        init();
    }

    private void init() {
        // 头像
        try {
            headPortrait = new RoundHeadPortrait(40, 40, new Color(128, 255, 255), "res/MainInterface/headPortrait.jpg");
        } catch (Exception e) {
            System.out.println();
        }
        headPortrait.setLocation(5,5);
        add(headPortrait);
        // 昵称
        nickname = new JLabel("昵称");
        nickname.setBounds(55, 8, 100, 16);
        nickname.setFont(new Font("微软雅黑", Font.BOLD, 14));
        add(nickname);
        // 个性签名
        signature = new JLabel("个性签名");
        signature.setBounds(55, 28, 130, 14);
        signature.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        signature.setForeground(Color.gray);
        add(signature);
        // 状态
        status = new JLabel("在线");
        status.setBounds(200, nickname.getY(), 30, 16);
        status.setFont(new Font("微软雅黑", Font.BOLD, 14));
        status.setForeground(Color.BLACK);
        add(status);
    }
}
// groupPanel
class groupPanel extends JPanel {
    private RoundHeadPortrait headPortrait;
    private JLabel groupName;
    groupPanel() {
        setPreferredSize(new Dimension(250, 50));
        setBackground(new Color(230, 230, 230, 200));
        setLayout(null);
        init();
    }

    private void init() {
        // 头像
        try {
            headPortrait = new RoundHeadPortrait(40, 40, new Color(128, 255, 255), "res/MainInterface/headPortrait.jpg");
        } catch (Exception e) {
            System.out.println();
        }
        headPortrait.setLocation(5,5);
        add(headPortrait);
        // 群名
        groupName = new JLabel("群名");
        groupName.setBounds(55, 8, 100, 16);
        groupName.setFont(new Font("微软雅黑", Font.BOLD, 14));
        add(groupName);
    }
}

// 最小化按钮
class UI_Minimize extends JButton {

    UI_Minimize() {
        setMargin(new Insets(0, 0, 0, 0)); // 消除按钮外边距
        setContentAreaFilled(false); // 不绘制内容区域（透明背景）
        setBorderPainted(false); // 去除边框
        setFocusPainted(false); // 不绘制焦点状态
        setToolTipText("最小化");
        Image normal = new ImageIcon("./res/Login/minbutton_normal.png").getImage();
        setIcon(new ImageIcon(normal.getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        Image hover = new ImageIcon("./res/Login/minbutton_hover.png").getImage();
        setRolloverIcon(new ImageIcon(hover.getScaledInstance(30, 30 ,Image.SCALE_DEFAULT)));
        Image down = new ImageIcon("./res/Login/minbutton_down.png").getImage();
        setPressedIcon(new ImageIcon(down.getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
    }

}

// 关闭按钮
class UI_Close extends JButton {

    UI_Close() {
        setMargin(new Insets(0, 0, 0, 0)); // 消除按钮外边距
        setContentAreaFilled(false); // 不绘制内容区域（透明背景）
        setBorderPainted(false); // 去除边框
        setFocusPainted(false); // 不绘制焦点状态
        setToolTipText("关闭");
        Image normal = new ImageIcon("./res/Login/closebutton_normal.png").getImage();
        setIcon(new ImageIcon(normal.getScaledInstance(30 , 30, Image.SCALE_DEFAULT)));
        Image hover = new ImageIcon("./res/Login/closebutton_hover.png").getImage();
        setRolloverIcon(new ImageIcon(hover.getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        Image down = new ImageIcon("./res/Login/closebutton_down.png").getImage();
        setPressedIcon(new ImageIcon(down.getScaledInstance(30, 30 , Image.SCALE_DEFAULT)));
    }
}

// 显示边框的头像框
class RoundHeadPortrait extends JButton {
    private int width;
    private int height;
    private Color borderColor;

    RoundHeadPortrait(int width, int height, Color borderColor,String filepath) throws IOException {
        this.borderColor = borderColor;
        this.width = width;
        this.height = height;
        int border = 2;
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setSize(width, height);
        BufferedImage avatarImage = ImageIO.read(new File(filepath));
        // 透明底的图片
        BufferedImage formatAvatarImage = new BufferedImage(width, width, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D graphics = formatAvatarImage.createGraphics();
        //把图片切成一个圓
        {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //留一个像素的空白区域，这个很重要，画圆的时候把这个覆盖
            //图片是一个圆型
            Ellipse2D.Double shape = new Ellipse2D.Double(border, border, width-2*border, width-2*border);
            //需要保留的区域
            graphics.setClip(shape);
            graphics.drawImage(avatarImage, border, border, width-2*border, width-2*border, null);
            graphics.dispose();
        }
        //在圆图外面再画一个圆
        {
            //新创建一个graphics，这样画的圆不会有锯齿
            graphics = formatAvatarImage.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //使画笔时基本会像外延伸一定像素，具体可以自己使用的时候测试
            Stroke s = new BasicStroke(border, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            graphics.setStroke(s);
            graphics.setColor(borderColor);
            graphics.drawOval(border, border, width-2*border, width-2*border);
            graphics.dispose();
        }
        setIcon(new ImageIcon(formatAvatarImage));
    }
}

// 实现可拖动的事件监听
class DragWindowListener extends MouseAdapter {
    private int offsetX, offsetY;
    private boolean isCanMove;

    public DragWindowListener() {
        isCanMove = true;
    }

    public void setCanMove(boolean isCanMove) {
        this.isCanMove = isCanMove;
    }

    public void mouseDragged(MouseEvent e) {
        // requires JDK 1.6 or above
        if (isCanMove) {
            SwingUtilities.getRoot((Component) e.getSource()).setLocation(e.getXOnScreen() - offsetX,
                    e.getYOnScreen() - offsetY);
        }
    }

    public void mousePressed(MouseEvent e) {
        offsetX = e.getX();
        offsetY = e.getY();
    }

    public void mouseClicked(MouseEvent e) {
        SwingUtilities.getRoot((Component) e.getSource()).requestFocus();
        super.mouseClicked(e);
    }
}