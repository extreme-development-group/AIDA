package frame.Search;

import client.InteractWithServer;
import config.Tools;
import frame.ChatFrame.ChatWithFriends;
import frame.ChatFrame.HeadPortrait;
import frame.Listener.deleteListener;
import frame.MainInterface.MyMenuItem;
import frame.MainInterface.MyPopupMenu;
import frame.MainInterface.UI_MainInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class searchMemberPanel extends JPanel{
    private JButton headPortrait;
    private JLabel nickname, status;
    private Color originColor, hoverColor;

    public String getFid() {
        return fid;
    }

    //    private UserInfo userinfo;
    private String fid,fOnline;
    // just for test
    private String fName,fSigniture;
    public String getfName() {
        return fName;
    }
    private Image fHead;
    private JButton addFriends;
    private UI_MainInterface now;

    public searchMemberPanel(String fid,Image fHead,String fName,String fOnline,String fSignature,UI_MainInterface now){
        fid = fid;
        originColor = new Color(245, 245, 245);
        hoverColor = new Color(255, 255, 255);
        this.fid =fid;
        this.fHead=fHead;
        this.fName=fName;
        this.fOnline=fOnline;
        this.now=now;
        this.fSigniture=fSignature;

        setPreferredSize(new Dimension(223, 50));
        setBackground(originColor);
        setLayout(null);
        init();

    }

    private void init(){
        // 头像
        headPortrait = new HeadPortrait(35,35,fHead);
        headPortrait.setLocation(5,5);
        add(headPortrait);
        // 昵称
        nickname = new JLabel(fName);
        nickname.setBounds(65, 8, 100, 16);
        nickname.setFont(new Font("微软雅黑", Font.BOLD, 14));
        add(nickname);
        // 个性签名
        status = new JLabel(fid+"("+fOnline+")");
        status.setBounds(65, 28, 100, 14);
        status.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        status.setForeground(Color.gray);
        add(status);
        //搜索按钮
        addFriends = new JButton();
        addFriends.setBounds(160, 5, 35, 35);
        addFriends.setContentAreaFilled(false);
        addFriends.setBorderPainted(false);
        addFriends.setFocusPainted(false);
        addFriends.setIcon(setIcon("res/Icon/addfriends.png", 40, 40));
        addFriends.setRolloverIcon(setIcon("res/Icon/addfriends_hover.png", 40, 40));
        addFriends.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean result = false;
                        if (fOnline.equals("在线")||fOnline.equals("离线")) {
                            if (InteractWithServer.addFriends(now.userInfo.getUserId(), fid, true)) {
                                now.addfriend(fid, fHead, fName, fSigniture, fOnline);
                            } else {
                                JOptionPane.showMessageDialog(now, "添加好友失败！", "提示", JOptionPane.WARNING_MESSAGE);
                            }
                        } else {
                            if (InteractWithServer.addFriends(now.userInfo.getUserId(), fid, false)) {
                                now.addGroup(fid, fHead, fName);
                            } else {
                                JOptionPane.showMessageDialog(now, "添加群失败！", "提示", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    }
                }).start();


            }
        });
        add(addFriends);

    }

    public String getfOnline() {
        return fOnline;
    }

    public void setfOnline(String fOnline) {
        this.fOnline = fOnline;
        status.setText(fOnline);
    }
    private ImageIcon setIcon(String filepath,int x,int y){
        ImageIcon imageIcon = new ImageIcon(filepath);    // Icon由图片文件形成
        Image image = imageIcon.getImage();                         // 但这个图片太大不适合做Icon//    为把它缩小点，先要取出这个Icon的image ,然后缩放到合适的大小
        Image smallImage = image.getScaledInstance(x,y,Image.SCALE_FAST);//    再由修改后的Image来生成合适的Icon
        return new ImageIcon(smallImage);//   最后设置它为按钮的图片
    }

}
