package frame.ChatFrame;

import client.ChatThread;
import client.InteractWithServer;
import config.Tools;
import frame.Listener.SendFriend;
import frame.MainInterface.UI_MainInterface;
import frame.UserInfoFrame.ModifyInfo;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class ChatWithFriends extends ChatFrame {
    public static void main(String[] args) throws IOException {
        Image image= ImageIO.read(new File("res/Avatar/head-test.JPG"));
        ChatWithFriends chatWithFriends=new ChatWithFriends("1","Mike","2","Jack",image,image);

    }
    protected JButton friendsNameButton,minimize,closeButton,emojiButton,
            voiceButton,pictureButton,fileButton,phoneButton,cameraButton,sendButton;
    protected JPanel mainControlPanel,functionPanel,sendPanel,controlPanel,sysPanel;
    protected JScrollPane inputScrollPane;
    protected JTextPane inputText;
    private String fAvatarString,fName,fid,mid,mName;
    private int messageNum;
    private int height;
    private Image fHeadPic,mHeadPic;

    private ImageIcon setIcon(String filepath,int x,int y){
        ImageIcon imageIcon = new ImageIcon(filepath);    // Icon由图片文件形成
        Image image = imageIcon.getImage();                         // 但这个图片太大不适合做Icon//    为把它缩小点，先要取出这个Icon的image ,然后缩放到合适的大小
        Image smallImage = image.getScaledInstance(x,y,Image.SCALE_FAST);//    再由修改后的Image来生成合适的Icon
        return new ImageIcon(smallImage);//   最后设置它为按钮的图片
    }



    private void showEmojiMenu(){
        EmojiMenu emojiMenu=new EmojiMenu(this);
    }

    public ChatWithFriends(String mid,String mName,String fid,String fName,Image mHeadPic,Image fHeadPic){
        this.mid=mid;
        this.mName=mName;
        this.fid=fid;
        this.fName=fName;
        this.fAvatarString=fAvatarString;
        this.mHeadPic=mHeadPic;
        this.fHeadPic=fHeadPic;
        this.setIconImage(fHeadPic.getScaledInstance(40,40,Image.SCALE_SMOOTH));

        init();
        this.setTitle(fName);
        this.setLayout(new BorderLayout());
        this.add(mainControlPanel,BorderLayout.NORTH);
        this.add(scrollPane,BorderLayout.CENTER);
        this.add(controlPanel,BorderLayout.SOUTH);
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //addMessage("res/Avatar/head-Search.JPG","Mike",df.format(new Date()),"1111",0);
        this.setSize(500,520);
        setLocationRelativeTo(null);
        this.setVisible(true);


    }
    private void init(){

        //friends name button
        friendsNameButton=new JButton();
        friendsNameButton.setText(fName);
        friendsNameButton.setFont(new Font("微软雅黑",Font.BOLD,16));
        friendsNameButton.setForeground(Color.WHITE);
        friendsNameButton.setFocusPainted(false);
        friendsNameButton.setContentAreaFilled(false);
        friendsNameButton.setBorderPainted(false);
        Point origin=new Point();
        friendsNameButton.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point p = ChatWithFriends.this.getLocation();
                ChatWithFriends.this.setLocation(
                        e.getXOnScreen() - origin.x,
                        e.getYOnScreen() - origin.y);
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
        friendsNameButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                origin.x = e.getX();
                origin.y = e.getY();
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

        //minimize button mini frame
        minimize=new JButton();
        minimize.setPreferredSize(new Dimension(40,40));
        minimize.setMargin(new Insets(0, 0, 0, 0));
        minimize.setIcon(setIcon("res/Icon/mini.png",30,30));
        minimize.setRolloverIcon(setIcon("res/Icon/mini_selected.png",30,30));
        minimize.setPressedIcon(setIcon("res/Icon/mini_selected.png",30,30));
        minimize.setBorderPainted(false);
        minimize.setContentAreaFilled(false);
        minimize.setFocusPainted(false);
        minimize.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setExtendedState(JFrame.ICONIFIED);
            }
        });
        //close button  close the frame
        closeButton = new JButton();
        closeButton.setMargin(new Insets(0, 0, 0, 0));
        closeButton.setPreferredSize(new Dimension(40,40));
        closeButton.setContentAreaFilled(false); // set don't draw message area
        closeButton.setBorderPainted(false); // set don't draw border
        closeButton.setFocusPainted(false);
        closeButton.setIcon(setIcon("res/Icon/close.png",30,30));
        closeButton.setRolloverIcon(setIcon("res/Icon/close_selected.png",30,30));
        closeButton.setPressedIcon(setIcon("res/Icon/close_selected.png",30,30));
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UI_MainInterface.withFriend.remove(fid);
                ChatWithFriends.this.dispose();
            }
        });



        emojiButton=new JButton();
        emojiButton.setMargin(new Insets(0, 0, 0, 0));
        emojiButton.setPreferredSize(new Dimension(40,40));
        emojiButton.setContentAreaFilled(false); // set don't draw message area
        emojiButton.setBorderPainted(false); // set don't draw border
        emojiButton.setFocusPainted(false);
        emojiButton.setIcon(setIcon("res/Icon/smile.png",30,30));
        emojiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEmojiMenu();
            }
        });


        voiceButton=new JButton();
        voiceButton.setMargin(new Insets(0, 0, 0, 0));
        voiceButton.setPreferredSize(new Dimension(40,40));
        voiceButton.setContentAreaFilled(false); // set don't draw message area
        voiceButton.setBorderPainted(false); // set don't draw border
        voiceButton.setFocusPainted(false);
        voiceButton.setIcon(setIcon("res/Icon/audio.png",30,30));


        pictureButton=new JButton();
        pictureButton.setMargin(new Insets(0, 0, 0, 0));
        pictureButton.setPreferredSize(new Dimension(40,40));
        pictureButton.setContentAreaFilled(false); // set don't draw message area
        pictureButton.setBorderPainted(false); // set don't draw border
        pictureButton.setFocusPainted(false);
        pictureButton.setIcon(setIcon("res/Icon/image.png",30,30));
        pictureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                int returnVal=chooser.showOpenDialog(ChatWithFriends.this);
                chooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        if(f.getName().endsWith(".jpg")||f.getName().endsWith(".png")||f.isDirectory()){
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public String getDescription() {
                        return "图片(.jpg,.png)";
                    }
                });
                System.out.println("returnVal="+returnVal);
                String filepath;
                if (returnVal == JFileChooser.APPROVE_OPTION) {          //如果符合文件类型
                    filepath = chooser.getSelectedFile().getPath();      //获取绝对路径
                    System.out.println(filepath);
                    System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());  //输出相对路径
                    String message= "#/Image"+ Tools.getImageBinary(filepath);
                    ChatWithFriends.this.addMessage(mHeadPic,mName, new Date().toString(),message, 1);
                    ChatThread.getDataStream().send(message, fid, false);
                }
            }
        });


        fileButton=new JButton();
        fileButton.setMargin(new Insets(0, 0, 0, 0));
        fileButton.setPreferredSize(new Dimension(40,40));
        fileButton.setContentAreaFilled(false); // set don't draw message area
        fileButton.setBorderPainted(false); // set don't draw border
        fileButton.setFocusPainted(false);
        fileButton.setIcon(setIcon("res/Icon/folder.png",30,30));


        phoneButton=new JButton();
        phoneButton.setMargin(new Insets(0, 0, 0, 0));
        phoneButton.setPreferredSize(new Dimension(40,40));
        phoneButton.setContentAreaFilled(false); // set don't draw message area
        phoneButton.setBorderPainted(false); // set don't draw border
        phoneButton.setFocusPainted(false);
        phoneButton.setIcon(setIcon("res/Icon/phone.png",30,30));


        cameraButton=new JButton();
        cameraButton.setMargin(new Insets(0, 0, 0, 0));
        cameraButton.setPreferredSize(new Dimension(40,40));
        cameraButton.setContentAreaFilled(false); // set don't draw message area
        cameraButton.setBorderPainted(false); // set don't draw border
        cameraButton.setFocusPainted(false);
        cameraButton.setIcon(setIcon("res/Icon/camera.png",30,30));

        input=new JTextArea("");
//        input.setPreferredSize(new Dimension(500,100));
        input.setLineWrap(true);
        input.setWrapStyleWord(true);
        input.setBackground(new Color(128,255,255));
        input.setBorder(null);

        sendButton=new JButton();
        sendButton.setText("发送");
        //sendButton.setPreferredSize(new Dimension(80,30));
        sendButton.setBackground(new Color(22,155,213));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFont(new Font("微软雅黑",Font.BOLD,16));
        sendButton.setBorderPainted(false); // set don't draw border
        sendButton.setFocusPainted(false);
        sendButton.setBounds(400,0,80,30);
        SendFriend sendFriend=new SendFriend(mHeadPic,mName,fid,false,input,this);
        sendButton.addActionListener(sendFriend);

        mainControlPanel =new JPanel();
        chatPanel=new JPanel();
        functionPanel=new JPanel();
//        inputPanel=new JPanel();
        sendPanel=new JPanel();
        controlPanel=new JPanel();
        sysPanel=new JPanel();


        inputScrollPane = new JScrollPane(input);
        inputScrollPane.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, new Color(128,255,255)));
        inputScrollPane.setPreferredSize(new Dimension(500,90));
        inputScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        inputScrollPane.setVerticalScrollBarPolicy((JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED));
        inputScrollPane.getVerticalScrollBar().setUI(new ScrollBarUI());
        inputScrollPane.getVerticalScrollBar().setUnitIncrement(15);
        



        mainControlPanel.setPreferredSize(new Dimension(500,40));

        functionPanel.setPreferredSize(new Dimension(500,40));
//        inputPanel.setPreferredSize(new Dimension(500,100));
        sendPanel.setPreferredSize(new Dimension(500,40));

        sysPanel.setBackground(new Color(40,138,221));
        sysPanel.setLayout(new BorderLayout());
        sysPanel.add(minimize,BorderLayout.WEST);
        sysPanel.add(closeButton,BorderLayout.EAST);

        mainControlPanel.setLayout(new BorderLayout());
        mainControlPanel.setBackground(new Color(40,138,221));
        mainControlPanel.add(friendsNameButton,BorderLayout.CENTER);
        mainControlPanel.add(sysPanel,BorderLayout.EAST);

        chatPanel.setBackground(new Color(243,249,253));
        chatPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));

        scrollPane=new JScrollPane(chatPanel);

        scrollPane.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, new Color(243,249,253)));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy((JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED));
        scrollPane.getVerticalScrollBar().setUI(new ScrollBarUI());
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        chatPanel.setPreferredSize(new Dimension(500,0));


        Vector<String> record = InteractWithServer.getChatRecord(mid, fid,false);
        for (int i = 0; i < record.size(); i++) {
            /*
             * res[0] 消息发送时间 res[1] fromId res[2] toId res[3] message
             */

            String res[] = record.get(i).split("```", 4);
            System.out.println("From:"+res[1]+ "message"+res[3]);
            // 聊天面板显示用户昵称
            if (res.length == 4) {
                if (res[1].equals(fid)){
                    addMessage(fHeadPic,fName,res[0],res[3],0);
                }else if (res[1].equals(mid)){
                    addMessage(mHeadPic,mName,res[0],res[3],1);
                }
            }
        }
        scrollPane.getViewport().setViewPosition(new Point(0,chatPanel.getPreferredSize().height));




        functionPanel.setLayout(new FlowLayout(FlowLayout.LEADING,10,0));
        functionPanel.setBackground(Color.BLUE);
        functionPanel.add(emojiButton);
        functionPanel.add(voiceButton);
        functionPanel.add(pictureButton);
        functionPanel.add(fileButton);
        functionPanel.add(phoneButton);
        functionPanel.add(cameraButton);
        functionPanel.add(sendButton);
        functionPanel.setBackground(new Color(128,255,255));


//        inputPanel.setBackground(new Color(128,255,255));

        sendPanel.setLayout(null);
        sendPanel.add(sendButton);
        sendPanel.setBackground(new Color(128,255,255));

        controlPanel.setLayout(new BorderLayout());
        controlPanel.add(functionPanel,BorderLayout.NORTH);
        controlPanel.add(inputScrollPane,BorderLayout.CENTER);
        controlPanel.add(sendPanel,BorderLayout.SOUTH);

    }

    public int getMessageNum() {
        return messageNum;
    }

    public void setMessageNum(int messageNum) {
        this.messageNum = messageNum;
    }
}

