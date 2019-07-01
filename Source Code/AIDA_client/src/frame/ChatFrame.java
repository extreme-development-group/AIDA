package frame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ChatFrame extends JFrame {
    protected JTextArea input;
    protected int height;
    protected JPanel chatPanel;
    protected JScrollPane scrollPane;
    public void updateChat(String avatarPath,String userName,String sendTime,String message,int side) throws IOException {
        height=height+60;
        chatPanel.setPreferredSize(new Dimension(500,height));
        chatPanel.add(new SingleText(avatarPath,userName,sendTime,message,side));
    }
}
