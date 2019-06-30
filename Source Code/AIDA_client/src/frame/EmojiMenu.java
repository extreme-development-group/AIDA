package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EmojiMenu extends JFrame{
    public JPanel panel;
    private JButton b1,b2,b3,b4,b5,b6,b7,b8,b9;
    public ChatWithFriends chat;
    public EmojiMenu(ChatWithFriends chat){

        this.chat=chat;
        b1=new JButton();
        b1.setContentAreaFilled(false); // set don't draw message area
        b1.setBorderPainted(false); // set don't draw border
        b1.setFocusPainted(false);
        b1.setIcon(setIcon("res/Emoji/EMOJI-01.png",30,30));
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chat.input.setText(chat.input.getText()+"#1#");
                EmojiMenu.this.dispose();
            }
        });

        b2=new JButton();
        b2.setContentAreaFilled(false); // set don't draw message area
        b2.setBorderPainted(false); // set don't draw border
        b2.setFocusPainted(false);
        b2.setIcon(setIcon("res/Emoji/EMOJI-03.png",30,30));
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chat.input.setText(chat.input.getText()+"#2#");
                EmojiMenu.this.dispose();
            }
        });

        b3=new JButton();
        b3.setContentAreaFilled(false); // set don't draw message area
        b3.setBorderPainted(false); // set don't draw border
        b3.setFocusPainted(false);
        b3.setIcon(setIcon("res/Emoji/EMOJI-05.png",30,30));
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chat.input.setText(chat.input.getText()+"#3#");
                EmojiMenu.this.dispose();
            }
        });

        b4=new JButton();
        b4.setContentAreaFilled(false); // set don't draw message area
        b4.setBorderPainted(false); // set don't draw border
        b4.setFocusPainted(false);
        b4.setIcon(setIcon("res/Emoji/EMOJI-10.png",30,30));
        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chat.input.setText(chat.input.getText()+"#4#");
                EmojiMenu.this.dispose();
            }
        });

        b5=new JButton();
        b5.setContentAreaFilled(false); // set don't draw message area
        b5.setBorderPainted(false); // set don't draw border
        b5.setFocusPainted(false);
        b5.setIcon(setIcon("res/Emoji/EMOJI-17.png",30,30));
        b5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chat.input.setText(chat.input.getText()+"#5#");
                EmojiMenu.this.dispose();
            }
        });

        b6=new JButton();
        b6.setContentAreaFilled(false); // set don't draw message area
        b6.setBorderPainted(false); // set don't draw border
        b6.setFocusPainted(false);
        b6.setIcon(setIcon("res/Emoji/EMOJI-24.png",30,30));
        b6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chat.input.setText(chat.input.getText()+"#6#");
                EmojiMenu.this.dispose();
            }
        });

        b7=new JButton();
        b7.setContentAreaFilled(false); // set don't draw message area
        b7.setBorderPainted(false); // set don't draw border
        b7.setFocusPainted(false);
        b7.setIcon(setIcon("res/Emoji/EMOJI-25.png",30,30));
        b7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chat.input.setText(chat.input.getText()+"#7#");
                EmojiMenu.this.dispose();
            }
        });

        b8=new JButton();
        b8.setContentAreaFilled(false); // set don't draw message area
        b8.setBorderPainted(false); // set don't draw border
        b8.setFocusPainted(false);
        b8.setIcon(setIcon("res/Emoji/EMOJI-44.png",30,30));
        b8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chat.input.setText(chat.input.getText()+"#8#");
                EmojiMenu.this.dispose();
            }
        });

        b9=new JButton();
        b9.setContentAreaFilled(false); // set don't draw message area
        b9.setBorderPainted(false); // set don't draw border
        b9.setFocusPainted(false);
        b9.setIcon(setIcon("res/Emoji/EMOJI-48.png",30,30));
        b9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chat.input.setText(chat.input.getText()+"#9#");
                EmojiMenu.this.dispose();
            }
        });


        this.addMouseListener(new MouseListener() {
            int i=0;
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                i++;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (i>=2){
                    EmojiMenu.this.dispose();
                }
            }
        });
        panel= new JPanel(new GridLayout(3,3));
        panel.add(b1);
        panel.add(b2);
        panel.add(b3);
        panel.add(b4);
        panel.add(b5);
        panel.add(b6);
        panel.add(b7);
        panel.add(b8);
        panel.add(b9);
        panel.setPreferredSize(new Dimension(100,100));
        panel.setBackground(new Color(201,251,254));
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        this.add(panel);
        this.setSize(100,100);
        setLocation(chat.getLocation().x+10,chat.getLocation().y+240);
        this.setAlwaysOnTop(true);
        //setLocationRelativeTo(chat);
        this.setVisible(true);

    }

    private ImageIcon setIcon(String filepath,int x,int y){
        ImageIcon imageIcon = new ImageIcon(filepath);    // Icon由图片文件形成
        Image image = imageIcon.getImage();                         // 但这个图片太大不适合做Icon//    为把它缩小点，先要取出这个Icon的image ,然后缩放到合适的大小
        Image smallImage = image.getScaledInstance(x,y,Image.SCALE_FAST);//    再由修改后的Image来生成合适的Icon
        return new ImageIcon(smallImage);//   最后设置它为按钮的图片
    }

}
