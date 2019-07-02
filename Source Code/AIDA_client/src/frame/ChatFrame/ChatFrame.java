package frame.ChatFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ChatFrame extends JFrame {
    protected JTextArea input;
    protected int height;
    protected JPanel chatPanel;
    protected JScrollPane scrollPane;
    public void addMessage(Image userHeadPic, String userName, String sendTime, String message, int side){
        height=height+60;
        chatPanel.setPreferredSize(new Dimension(500,height));
        chatPanel.add(new SingleText(userHeadPic,userName,sendTime,message,side));
        scrollPane.getViewport().setViewPosition(new Point(0,chatPanel.getHeight()));
    }

}
