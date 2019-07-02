import frame.DragWindowListener;
import frame.RoundHeadPortrait;
import frame.UI_Minimize;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
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
    private listScrollPanel friendListJSP;
    // friendPanel
    // groupListPanel
    private JPanel groupListPanel;
    private listScrollPanel groupListJSP;
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

        setSize(upPanel.getWidth(), upPanel.getHeight()+400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init() {
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

    private void initListPanel() {
        friendListPanel = new listPanel();
        friendListJSP = new listScrollPanel(friendListPanel);
        add(friendListJSP);

        for(int i=0; i<5; i++) {
            friendListPanel.setPreferredSize(new Dimension(250, 50*i));
            friendListPanel.add(new friendPanel());
        }

        groupListPanel = new listPanel();
        groupListJSP = new listScrollPanel(groupListPanel);
        add(groupListJSP);

        for(int i=0; i<15; i++) {
            groupListPanel.setPreferredSize(new Dimension(250, 50*i));
            groupListPanel.add(new groupPanel());
        }

        groupListJSP.setVisible(false);
    }

    public static void main(String[] args) {
        UI_MainInterface demo = new UI_MainInterface();
    }

}

// listPanel
class listPanel extends JPanel {
    listPanel() {
        setLocation(0, 165);
        setPreferredSize(new Dimension(250, 397)); // jscrollpanel有3像素未知区域。。。
        setBackground(new Color(238, 255, 250));
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    }
}

// listScrollPanel
class listScrollPanel extends JScrollPane {
    listScrollPanel(JPanel jp) {
        super(jp);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        getVerticalScrollBar().setUI(new ScrollBarUI());
        getVerticalScrollBar().setUnitIncrement(15);
        setBounds(0, 165, 250, 400);
        setBorder(null);
    }
}

// friendPanel
class friendPanel extends JPanel {
    private RoundHeadPortrait headPortrait;
    private JLabel nickname, status, signature;
    private Color originColor, hoverColor;
    friendPanel() {
        originColor = new Color(245, 245, 245);
        hoverColor = new Color(255, 255, 255);

        setPreferredSize(new Dimension(250, 50));
        setBackground(originColor);
        setLayout(null);
        init();

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
                super.mouseEntered(e);
            }
            public void mouseExited(MouseEvent e) {
                setBackground(originColor);
                super.mouseExited(e);
            }
        });
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
    private Color originColor, hoverColor;
    groupPanel() {
        originColor = new Color(245, 245, 245);
        hoverColor = new Color(255, 255, 255);

        setPreferredSize(new Dimension(250, 50));
        setBackground(originColor);
        setLayout(null);
        init();

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
                super.mouseEntered(e);
            }
            public void mouseExited(MouseEvent e) {
                setBackground(originColor);
                super.mouseExited(e);
            }
        });
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

// 滚动条样式
class ScrollBarUI extends BasicScrollBarUI {
    // 更改滑道颜色与滚动条宽度
    protected void configureScrollBarColors() {
        // 滑道
        trackColor = new Color(0, 0, 0, 0);

        // 滚动条宽度
        scrollBarWidth = 10;
    }

    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        super.paintTrack(g, c, trackBounds);
    }

    // 更改滚动条内部样式(滑块颜色,滑块宽度,把手颜色等)
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        // 重写父类方法，如果不加这一句无法拖动滑动条
        g.translate(thumbBounds.x, thumbBounds.y);

        // 设置把手颜色
        g.setColor(Color.black);

        // 消除锯齿
        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.addRenderingHints(rh);

        // 设置半透明效果
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));

        // 填充圆角矩形
        g2.fillRoundRect(0, 0, 10, thumbBounds.height - 1, 15, 15);
    }

    // 隐藏向下点击的按钮
    protected JButton createIncreaseButton(int orientation) {
        JButton button = new JButton();

        // 使按钮不显示
        button.setVisible(false);
        return button;
    }

    // 隐藏向上点击的按钮
    protected JButton createDecreaseButton(int orientation) {
        JButton button = new JButton();
        button.setVisible(false);
        return button;
    }
}