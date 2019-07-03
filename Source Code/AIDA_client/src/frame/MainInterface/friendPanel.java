package frame.MainInterface;

import config.Tools;
import frame.ChatFrame.ChatWithFriends;
import frame.ChatFrame.HeadPortrait;
import frame.Listener.deleteListener;

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
    private UI_MainInterface now;

    friendPanel(String id,Image fHead,String fName,String fSignature,String fOnline,UI_MainInterface now){
        fid = id;
        originColor = new Color(245, 245, 245);
        hoverColor = new Color(255, 255, 255);
        this.fid =id;
        this.fHead=fHead;
        this.fName=fName;
        this.fSignature=fSignature;
        this.fOnline=fOnline;
        this.now=now;

        setPreferredSize(new Dimension(250, 50));
        setBackground(originColor);
        setLayout(null);
        init();

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String userId=now.userInfo.getUserId();
                    String userName=now.userInfo.getUserName();
                    Image mHeadImage= Tools.base64StringToImage(now.userInfo.getUserAvatar());
                    now.withFriend.put(fid,new ChatWithFriends(userId,userName,fid,fName,fHead,mHeadImage));
                }
                super.mouseClicked(e);
            }

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

    private void init(){
        // 头像
        headPortrait = new HeadPortrait(40,40,fHead);
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
        status.setText(fOnline);
    }

    public String getFAvatar() {
        // TODO
        return "头像暂无";
//        return fHead;
    }
}