package frame.MainInterface;

import frame.ChatFrame.ChatWithGroup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

// groupPanel
public class groupPanel extends JPanel {

    private RoundHeadPortrait headPortrait;
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

        originColor = new Color(245, 245, 245);
        hoverColor = new Color(255, 255, 255);

        setPreferredSize(new Dimension(250, 50));
        setBackground(originColor);
        setLayout(null);
        init();
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    try {
                        now.withGroup.put(groupID,new ChatWithGroup(now.userInfo.getUserId(),now.userInfo.getUserName(),groupID,fName,fHead,0));
                    } catch (IOException ex) {
                        ex.printStackTrace();
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
                            System.out.println( groupID );
                        }
                    });
                    MyMenuItem exitItem = new MyMenuItem("退出群聊");
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
            headPortrait = new RoundHeadPortrait(40, 40, new Color(128, 255, 255), "res/MainInterface/headPortrait.jpg");
        } catch (Exception e) {
            System.out.println();
        }
        headPortrait.setLocation(5,5);
        add(headPortrait);
        // 群名
        groupName = new JLabel("群名"+groupID);
        groupName.setBounds(55, 8, 100, 16);
        groupName.setFont(new Font("微软雅黑", Font.BOLD, 14));
        add(groupName);
    }
}
