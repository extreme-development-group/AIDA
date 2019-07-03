package frame.Search;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;



public class confirmAddFirend extends JFrame{
	private JPanel contentPane;
	private JPanel verifyPane;
	private JButton confirmB,unconfirmB;
	private JTextArea verifyArea;
	private Avatar avatar;
	private JLabel nameL,genderL,addressL;
	//private JTextField Verification;
	private int count = 0;//鏂囨湰鍩熺涓�娆¤鏁�
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					confirmAddFirend frame = new confirmAddFirend();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public confirmAddFirend() throws IOException {
		this.setTitle("好友申请");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width - 400) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - 400) / 2, 400, 300);
		this.contentPane = new JPanel();
		this.contentPane.setBounds(0, 0, 125, 300);
		this.contentPane.setBackground(new Color(135,236,250));
		this.contentPane.setOpaque(true);
		this.contentPane.setLayout(null);
		this.add(contentPane);
		
		this.verifyPane = new JPanel();
		this.verifyPane.setBounds(126, 0, 275, 300);
		this.verifyPane.setBackground(new Color(0,191,255));
		this.verifyPane.setOpaque(true);
		this.verifyPane.setLayout(null);
		this.add(verifyPane);

		avatar = new Avatar(80, 80, "./avatar.jpg");
		avatar.setLocation(22, 40);
		//this.avatar.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("source/avatar.jpg").getScaledInstance(70, 70, Image.SCALE_DEFAULT)));
		//this.avatar.setBackground(Color.WHITE);
		this.contentPane.add(avatar);
		
		this.nameL = new JLabel();
		this.nameL.setBounds(20,130,100,20);
		
		this.nameL.setForeground(new Color(0,0,0));
		this.nameL.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		this.nameL.setText("姓名："+"字符串");
		this.contentPane.add(nameL);
		//this.contentPane.validate();
		
		this.genderL = new JLabel();
		this.genderL.setBounds(20,150,100,20);
		
		this.genderL.setForeground(new Color(0,0,0));
		this.genderL.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		this.genderL.setText("性别："+"字符串");
		this.contentPane.add(genderL);
		
		
		
		this.addressL = new JLabel();
		this.addressL.setBounds(20,170,100,20);
		
		this.addressL.setForeground(new Color(0,0,0));
		this.addressL.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		this.addressL.setText("地址："+"字符串");
		this.contentPane.add(addressL);
		
		
		/*
		 * 
		 * this.verifyArea = new JTextArea("请输入内容",4,30);
		this.verifyArea.setLineWrap(true);
		this.verifyArea.setForeground(Color.GRAY);
		this.verifyArea.setFont(new Font("微软雅黑",Font.PLAIN,16));
		this.verifyArea.setBackground(Color.WHITE);
		JScrollPane jsp=new JScrollPane(this.verifyArea); 
		jsp.setBounds(157,40, 200, 100);
		//this.verifyArea.setBounds(70, 35, 360, 30);
		//this.verifyArea.setBackground(Color.WHITE);
		//this.verifyArea.setFont(new Font("寰蒋闆呴粦", Font.PLAIN, 15));
		//this.verifyArea.setBorder(null);
		//this.verifyArea.setForeground(new Color(211, 211, 211));
		this.verifyPane.add(jsp);
		//this.verifyArea.setColumns(10);
		this.verifyArea.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				verifyArea.setForeground(new Color(34, 49, 72));
				
				if(count ==0)
				{
					verifyArea.setText("");
				}
				count ++ ;
				
			}
		});
		 */
		
		this.verifyArea = new JTextArea("",4,30);
		this.verifyArea.setLineWrap(true);
		this.verifyArea.setForeground(Color.GRAY);
		this.verifyArea.setFont(new Font("微软雅黑",Font.PLAIN,16));
		this.verifyArea.setEditable(false);
		this.verifyArea.setBackground(Color.WHITE);
		JScrollPane jsp=new JScrollPane(this.verifyArea); 
		jsp.setBounds(157,40, 200, 100);
		this.verifyPane.add(jsp);


		
		
	
		this.confirmB = new JButton("同意");
		this.confirmB.setFont(new Font("微软雅黑", Font.BOLD, 16));
		//this.confirmB.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("source/瀹屾垚.png").getScaledInstance(104, 36, Image.SCALE_DEFAULT)));
		this.confirmB.setBounds(157, 200, 90, 36);
		this.verifyPane.add(confirmB);
		
		this.unconfirmB = new JButton("关闭");
		this.unconfirmB.setFont(new Font("微软雅黑", Font.BOLD, 16));
		//this.confirmB.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("source/瀹屾垚.png").getScaledInstance(104, 36, Image.SCALE_DEFAULT)));
		this.unconfirmB.setBounds(270, 200, 90, 36);
		this.verifyPane.add(unconfirmB);
		
		
		
	}
}