package frame.ChatFrame;

import javax.swing.*;
import java.awt.*;

public class SingleText extends JPanel {
    public static int LEFT=0;
    public static int RIGHT=1;
    private HeadPortrait avatar;
    private JPanel textPanel;
    private JLabel idLabel;
    private JLabel textLabel;
    public SingleText(Image userHeadPic,String username,String time,String text,int side) {
        avatar=new HeadPortrait(40,40,userHeadPic);

        idLabel=new JLabel();
        idLabel.setText(username+"  "+time);
        idLabel.setFont(new Font("微软雅黑",Font.PLAIN,10));
        idLabel.setPreferredSize(new Dimension(200,20));
        if (side==1){
            idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        }


        textLabel=new JLabel();
        textLabel.setText(text);
        textLabel.setFont(new Font("微软雅黑",Font.PLAIN,20));
        if (side==1){
            textLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        }

        textPanel=new JPanel();
        textPanel.setLayout(new BorderLayout(0,0));
        textPanel.add(idLabel,BorderLayout.NORTH);
        textPanel.add(textLabel,BorderLayout.CENTER);
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
        this.setPreferredSize(new Dimension(500,60));
    }
}
