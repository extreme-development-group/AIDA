package frame.ChatFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HeadPortrait extends JButton {
    public static void main(String[] args) throws IOException {
        //JPanel panel=new SingleText("res/Icon/11111.png","mike","2019.6.30","hello!",1);
        //JPanel Member=new MemberText("res/Icon/11111.png","11",0);
        //EmojiMenu emojiMenu=new EmojiMenu(new ChatWithFriends());
        JFrame frame = new JFrame("圆形按钮");
        frame.getContentPane().setBackground(Color.white);
        frame.getContentPane().setLayout(new FlowLayout());
        //frame.getContentPane().add(Member);
        frame.setSize(200, 200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private int width;
    private int height;
    private int border;


    public HeadPortrait(int width, int height, Image image){
        this.width = width;
        this.height = height;
        border=1;
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setSize(width, height);
        BufferedImage avatarImage = (BufferedImage)image;
        // 透明底的图片
        BufferedImage formatAvatarImage = new BufferedImage(width, width, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D graphics = formatAvatarImage.createGraphics();
        //把图片切成一个圓
        {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //留一个像素的空白区域，这个很重要，画圆的时候把这个覆盖
            //图片是一个圆型
            Ellipse2D.Double shape = new Ellipse2D.Double(border , border, width-2*border, width-2*border);
            //需要保留的区域
            graphics.setClip(shape);
            graphics.drawImage(avatarImage,border, border, width-2*border, width-2*border, null);
            graphics.dispose();
        }
        //在圆图外面再画一个圆
        {
            //新创建一个graphics，这样画的圆不会有锯齿
            graphics = formatAvatarImage.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //画笔是4.5个像素，BasicStroke的使用可以查看下面的参考文档
            //使画笔时基本会像外延伸一定像素，具体可以自己使用的时候测试
            Stroke s = new BasicStroke(2F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            graphics.setStroke(s);
            graphics.setColor(new Color(128,255,255));
            graphics.drawOval(border , border, width-border*2, width-border*2);
            graphics.dispose();
        }
        setIcon(new ImageIcon(formatAvatarImage));
    }
}
