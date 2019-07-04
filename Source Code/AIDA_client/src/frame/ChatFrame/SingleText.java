package frame.ChatFrame;

import config.Tools;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class SingleText extends JPanel {
    public static int LEFT=0;
    public static int RIGHT=1;
    private HeadPortrait avatar;
    private JPanel textPanel;
    private JLabel idLabel;
    private JTextPane textPane;
    public SingleText(Image userHeadPic,String username,String time,String text,int side) {
//        side = 1;
        avatar=new HeadPortrait(40,40,userHeadPic);

        idLabel=new JLabel();
        idLabel.setText(username+"  "+time);
        idLabel.setFont(new Font("微软雅黑",Font.PLAIN,10));
        idLabel.setPreferredSize(new Dimension(200,20));
        if (side==1){
            idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        }


        textPane=new JTextPane();
        textPane.setEditable(false);
        textPane.setFont(new Font("微软雅黑",Font.PLAIN,20));
        if (side==1){
            //设置靠右显示
            SimpleAttributeSet set = new SimpleAttributeSet();
            StyleConstants.setAlignment(set,StyleConstants.ALIGN_RIGHT);
            StyledDocument demo = textPane.getStyledDocument();
            demo.setParagraphAttributes(0,104,set,false);
        }else {
            SimpleAttributeSet set = new SimpleAttributeSet();
            StyleConstants.setAlignment(set,StyleConstants.ALIGN_LEFT);
            StyledDocument demo = textPane.getStyledDocument();
            demo.setParagraphAttributes(0,104,set,false);
        }
        textPane.setBackground(new Color(243,249,253));

        textPanel=new JPanel();
        textPanel.setLayout(new BorderLayout(0,0));
        textPanel.add(idLabel,BorderLayout.NORTH);
        textPanel.add(textPane,BorderLayout.CENTER);
        textPanel.setBackground(new Color(243,249,253));

        this.setLayout(new BorderLayout(10,5));
        if (side==0){
            this.add(avatar,BorderLayout.WEST);
            this.add(textPanel,BorderLayout.CENTER);
        }else if (side==1){
            this.add(avatar,BorderLayout.EAST);
            this.add(textPanel,BorderLayout.CENTER);
        }

        String chatText=text;
        System.out.println(text);
        String rec[] =chatText.split("#");
        for (String str:rec){
            if (str.length()==2){
                System.out.println("icon");
                textPane.insertIcon(Tools.setIcon("res/Emoji/EMOJI-"+str+".png",30,30));
            }else {
                try {
                    System.out.println(str);
                    textPane.getStyledDocument().insertString(textPane.getText().length(), str, null);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }

        this.setBackground(new Color(243,249,253));
        this.setPreferredSize(new Dimension(500,60));
    }
}
