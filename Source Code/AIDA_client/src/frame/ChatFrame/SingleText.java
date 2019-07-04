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
        textPane.setFont(new Font("微软雅黑",Font.BOLD,16));
        textPane.setLayout(null);


        if (side==1){
            //设置靠右显示
            SimpleAttributeSet set = new SimpleAttributeSet();
            StyleConstants.setAlignment(set,StyleConstants.ALIGN_RIGHT);
            StyledDocument demo = textPane.getStyledDocument();
            demo.setParagraphAttributes(0,104,set,false);
        }
        textPane.setBackground(new Color(243,249,253));
        String chatText=text;
        System.out.println(text);
        if (text.startsWith("#/Image")){
            String image = text.replace("#/Image","");
            textPane.insertIcon(new ImageIcon(Tools.base64StringToImage(image).getScaledInstance(70,70,Image.SCALE_FAST)));
            textPane.setPreferredSize(new Dimension(460,70));
        }else {
            String rec[] =chatText.split("#");
            for (String str:rec){
                if (str.startsWith("/Emoji")){
                    String emoji = str.replace("/Emoji","");
//                    System.out.println("icon");
                    textPane.setCaretPosition(textPane.getStyledDocument().getLength());
                    textPane.insertIcon(Tools.setIcon("res/Emoji/EMOJI-"+emoji+".png",30,30));
                }else {
                    try {
                        textPane.setCaretPosition(textPane.getStyledDocument().getLength());
//                        System.out.println(textPane.getCaretPosition() + " " +textPane.getText().length()+" "+str);
                        textPane.getStyledDocument().insertString(textPane.getStyledDocument().getLength(), str, null);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        textPane.setSize(new Dimension(460,textPane.getPreferredSize().height));
        System.out.println(textPane.getPreferredSize().height);
//        textPane.setPreferredSize(new Dimension(460,Height));
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
        this.setBackground(new Color(243,249,253));
        this.setPreferredSize(new Dimension(500,textPane.getPreferredSize().height+20));
    }
}
