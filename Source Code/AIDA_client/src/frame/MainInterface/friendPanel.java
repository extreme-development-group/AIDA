package frame.MainInterface;

import frame.ChatFrame.ChatWithFriends;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class friendPanel extends JPanel {
    private JButton headPortrait;
    private JLabel nickname, status, signature;
    private Color originColor, hoverColor;

    public String getFid() {
        return fid;
    }

    //    private UserInfo userinfo;
    private String fid,fOnline;
    // just for test
    private String fName;
    public String getfName() {
        return fName;
    }
    private String fSignature;
    private Image fHead;

    friendPanel(String id,Image fHead,String fName,String fSignature,String fOnline,UI_MainInterface now) throws IOException {
        fid = id;
        originColor = new Color(245, 245, 245);
        hoverColor = new Color(255, 255, 255);
        this.fid =id;
        this.fHead=fHead;
        this.fName=fName;
        this.fSignature=fSignature;
        this.fOnline=fOnline;

        setPreferredSize(new Dimension(250, 50));
        setBackground(originColor);
        setLayout(null);
        init();
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    now.withFriend.put(fid,new ChatWithFriends(now.userInfo.getUserId(),now.userInfo.getUserName(),fid,fName,fHead,fHead));
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        addMouseListener(new MouseAdapter() {
            // 右键弹出菜单
            // 鼠标释放弹出
            public void mouseReleased(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON3) {
                    MyPopupMenu friendPopupMenu = new MyPopupMenu();
                    MyMenuItem chatItem = new MyMenuItem("发送消息");
                    chatItem.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {

                        }
                    });
                    MyMenuItem deleteItem = new MyMenuItem("删除好友");
                    deleteItem.addActionListener(new deleteListener((JPanel)e.getComponent()));
                    MyMenuItem infoItem = new MyMenuItem("个人资料");
                    friendPopupMenu.add(chatItem);
                    friendPopupMenu.add(infoItem);
                    friendPopupMenu.add(deleteItem);
                    friendPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
                super.mouseClicked(e);
            }

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

    private void init() throws IOException {
        // 头像
        headPortrait = new RoundHeadPortrait(40, 40, new Color(128, 255, 255), "res/MainInterface/headPortrait.jpg");
        headPortrait.setLocation(5,5);
        add(headPortrait);
        // 昵称
        nickname = new JLabel(fName);
        nickname.setBounds(55, 8, 100, 16);
        nickname.setFont(new Font("微软雅黑", Font.BOLD, 14));
        add(nickname);
        // 个性签名
        signature = new JLabel(fSignature);
        signature.setBounds(55, 28, 130, 14);
        signature.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        signature.setForeground(Color.gray);
        add(signature);
        // 状态
        status = new JLabel(fOnline);
        status.setBounds(200, nickname.getY(), 30, 16);
        status.setFont(new Font("微软雅黑", Font.BOLD, 14));
        status.setForeground(Color.BLACK);
        add(status);
    }

    public String getfOnline() {
        return fOnline;
    }

    public void setfOnline(String fOnline) {
        this.fOnline = fOnline;
    }
}