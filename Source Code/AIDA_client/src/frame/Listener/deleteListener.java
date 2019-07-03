package frame.Listener;

import client.InteractWithServer;
import frame.MainInterface.friendPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 删除事件
public class deleteListener implements ActionListener {
    private JPanel parentPanel;
    public deleteListener(JPanel parent) {
        parentPanel = parent;
    }
    public void actionPerformed(ActionEvent e) {
        friendPanel temp = (friendPanel)parentPanel;
//        boolean result = InteractWithServer.deleteFriends(parentPanel.get)
        System.out.println("删除" + temp.getFid() );
        // 流式布局自动位移
        temp.setVisible(false);

    }
}
