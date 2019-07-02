package frame.ChatFrame;

import client.ChatThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

class SendFriend implements ActionListener {
    private JTextArea message;
    private String mName;
    private String fid;
    private ChatFrame now;
    private boolean isGroup;
    private Image mHeadPic;

    public SendFriend(Image mHeadPic,String mName, String fid, boolean isGroup,JTextArea message,ChatFrame now) {
        this.mName = mName;
        this.fid = fid;
        this.isGroup = isGroup;
        this.mHeadPic=mHeadPic;
        this.now=now;
        this.message=message;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 发送消息
        if(this.message.getText().trim().length()!=0){
            now.addMessage(mHeadPic,mName, new Date().toString(), this.message.getText(), 1);
            ChatThread.getDataStream().send(this.message.getText(), fid, isGroup);
            this.message.setText("");
        }
        else
        {JOptionPane.showMessageDialog(now, "发送消息不能为空，请重新输入");}
    }

    public void setMessage(JTextArea message) {
        this.message = message;
    }

    public void setNow(ChatWithFriends now) {
        this.now = now;
    }
}