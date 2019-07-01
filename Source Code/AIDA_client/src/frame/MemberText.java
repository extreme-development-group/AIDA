package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class MemberText extends JPanel {
    public static int OWNER=0;
    public static int ADMIN=1;
    public static int MEMBER=2;

    private HeadPortrait avatar;
    private JLabel idLabel;
    private JLabel statusLabel;
    private int status;
    private int userStatus;
    private int userID;
    private ChatWithGroup group;

    public MemberText(String avatarPath,String username,int userID,int status,ChatWithGroup group,int userStatus) throws IOException {
        this.userID=userID;
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.isMetaDown()){
                    showPopupMenu(e.getComponent(),e.getX(),e.getY());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        this.group=group;
        this.status=status;
        this.userStatus=userStatus;
        avatar=new HeadPortrait(30,30,avatarPath);
        idLabel=new JLabel();
        idLabel.setText(username);
        idLabel.setFont(new Font("微软雅黑",Font.PLAIN,10));
        idLabel.setPreferredSize(new Dimension(200,20));

        statusLabel =new JLabel();
        statusLabel.setPreferredSize(new Dimension(40,40));
        if (status==OWNER){
            statusLabel.setIcon(setIcon("res/Status/anchor-fill.png",30,30));
        }else if (status==ADMIN){
            statusLabel.setIcon(setIcon("res/Status/anchor-line.png",30,30));
        }
        this.setBackground(new Color(201,251,254));
        this.setLayout(new BorderLayout(10,5));
        this.add(avatar,BorderLayout.WEST);
        this.add(idLabel,BorderLayout.CENTER);
        this.add(statusLabel,BorderLayout.EAST);
        this.setPreferredSize(new Dimension(200,40));
    }
    private ImageIcon setIcon(String filepath,int x,int y){
        ImageIcon imageIcon = new ImageIcon(filepath);    // Icon由图片文件形成
        Image image = imageIcon.getImage();                         // 但这个图片太大不适合做Icon//    为把它缩小点，先要取出这个Icon的image ,然后缩放到合适的大小
        Image smallImage = image.getScaledInstance(x,y,Image.SCALE_FAST);//    再由修改后的Image来生成合适的Icon
        return new ImageIcon(smallImage);//   最后设置它为按钮的图片
    }
    private void showPopupMenu(Component invoker,int x,int y){
        JPopupMenu popupMenu=new JPopupMenu();
        JMenuItem setAdmin = new JMenuItem("设置为管理员");
        JMenuItem getOut=new JMenuItem("踢出群");
        if (userStatus==0){
            popupMenu.add(setAdmin);
            popupMenu.add(getOut);
        }else if (userStatus==1){
            popupMenu.add(getOut);
        }
        setAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                group.input.setText(userID+"setAdmin");
            }
        });
        getOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                group.input.setText(userID+"exit");
            }
        });
        popupMenu.show(invoker,x,y);
    }
}
