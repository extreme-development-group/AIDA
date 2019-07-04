package frame.MainInterface;

import client.InteractWithServer;
import config.Tools;
import frame.ChatFrame.ChatWithGroup;
import frame.ChatFrame.HeadPortrait;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

// groupPanel
public class groupPanel extends JPanel {

    private JButton headPortrait;
    private JLabel groupName;
    private Color originColor, hoverColor;
    //    private UserInfo userinfo;
    private String groupID; // groupid
    // just for test
    private String fName,fSignature;
    private Image fHead;
    private boolean isOnline;
    private UI_MainInterface now;

    groupPanel(String id,Image fHead,String fName,UI_MainInterface now) {
        this.groupID = id;
        this.fHead=fHead;
        this.fName=fName;
        this.now=now;

        originColor = new Color(223, 238, 250);
        hoverColor = new Color(207, 221, 232);

        setPreferredSize(new Dimension(250, 50));
        setBackground(originColor);
        setLayout(null);
        init();
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    groupPanel.this.setBackground(hoverColor);
                    if (!now.withGroup.containsKey(groupID)){
                        now.withGroup.put(groupID,new ChatWithGroup(now.userInfo.getUserId(),now.userInfo.getUserName(),groupID,fName, Tools.base64StringToImage(now.userInfo.getUserAvatar())));
                    }else {
                        now.withGroup.get(groupID).requestFocus();
                    }
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
            public void mouseReleased(MouseEvent e) {
                if (e.getButton()==e.BUTTON3){
                    MyPopupMenu groupPopupMenu = new MyPopupMenu();
                    MyMenuItem enterItem = new MyMenuItem("进入群聊");
                    enterItem.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if (!now.withGroup.containsKey(groupID)){
                                now.withGroup.put(groupID,new ChatWithGroup(now.userInfo.getUserId(),now.userInfo.getUserName(),groupID,fName,Tools.base64StringToImage(now.userInfo.getUserAvatar())));
                            }else {
                                now.withGroup.get(groupID).requestFocus();
                            }
                        }
                    });
                    MyMenuItem exitItem = new MyMenuItem("退出群聊");
                    exitItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (InteractWithServer.deleteFriends(now.userInfo.getUserId(),groupID,false)){
                                System.out.println("删除" + groupID );
                                // 流式布局自动位移
                                groupPanel.this.setVisible(false);
                                now.groupListPanel.setPreferredSize(new Dimension((int)now.groupListPanel.getPreferredSize().getWidth(),
                                        (int)now.groupListPanel.getPreferredSize().getWidth()-50));
                            }else {
                                JOptionPane.showMessageDialog(now, "退出群聊失败！", "提示", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    });
                    groupPopupMenu.add(enterItem);
                    groupPopupMenu.add(exitItem);
                    groupPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                    super.mouseReleased(e);
                }
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

    private void init() {
        // 头像
        try {
            headPortrait = new HeadPortrait(40,40,fHead);
        } catch (Exception e) {
            System.out.println();
        }
        headPortrait.setLocation(5,5);
        add(headPortrait);
        // 群名
        groupName = new JLabel(fName+"("+groupID+")");
        groupName.setBounds(55, 8, 180, 32);
        groupName.setFont(new Font("微软雅黑", Font.BOLD, 14));
        add(groupName);
    }
}
