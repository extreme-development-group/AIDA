package frame.Search;

import frame.MainInterface.DragWindowListener;
import frame.MainInterface.ScrollBarUI;
import frame.MainInterface.UI_Close;
import frame.MainInterface.UI_Minimize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;



public class AddFriends extends JFrame{
	private JButton closeButton, minimizeButton;
	private JPanel contentPane;
	private JPanel verifyPane;
	private JButton confirmB;
	private JTextArea verifyArea;
	private Avatar avatar;
	private JLabel nameL,genderL,addressL;
	//private JTextField Verification;
	private int count = 0;//计数
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddFriends frame = new AddFriends();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public AddFriends() throws IOException {
		// 实现可拖动
		DragWindowListener adpter = new DragWindowListener();
		this.addMouseListener(adpter);
		this.addMouseMotionListener(adpter);
		this.setTitle("添加好友");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width - 400) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - 400) / 2, 400, 300);
		this.setUndecorated(true);

		// 关闭按钮
		closeButton = new UI_Close();
		closeButton.setBounds(370, 0, 30, 30);
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		add(closeButton);
		// 最小化按钮
		minimizeButton = new UI_Minimize();
		minimizeButton.setBounds(340, 0, 30, 30);
		minimizeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setExtendedState(JFrame.ICONIFIED);
			}
		});
		add(minimizeButton);

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

		avatar = new Avatar(80, 80, "res/MainInterface/qq_logo.png");
		avatar.setLocation(22, 70);
		//this.avatar.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("source/avatar.jpg").getScaledInstance(70, 70, Image.SCALE_DEFAULT)));
		//this.avatar.setBackground(Color.WHITE);
		this.contentPane.add(avatar);
		
		this.nameL = new JLabel();
		this.nameL.setBounds(20,160,100,20);
		
		this.nameL.setForeground(new Color(0,0,0));
		this.nameL.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		this.nameL.setText("姓名："+"字符串");
		this.contentPane.add(nameL);
		//this.contentPane.validate();
		
		this.genderL = new JLabel();
		this.genderL.setBounds(20,180,100,20);
		
		this.genderL.setForeground(new Color(0,0,0));
		this.genderL.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		this.genderL.setText("性别："+"字符串");
		this.contentPane.add(genderL);
		
		
		
		this.addressL = new JLabel();
		this.addressL.setBounds(20,200,100,20);
		
		this.addressL.setForeground(new Color(0,0,0));
		this.addressL.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		this.addressL.setText("地址："+"字符串");
		this.contentPane.add(addressL);
		
		
		
		
		this.verifyArea = new JTextArea("请输入内容",4,30);
		this.verifyArea.setLineWrap(true);
		this.verifyArea.setForeground(Color.GRAY);
		this.verifyArea.setFont(new Font("微软雅黑",Font.PLAIN,16));
		this.verifyArea.setBackground(Color.WHITE);
		this.verifyArea.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if(verifyArea.getText().equals("请输入内容")) {
					verifyArea.setText("");
					verifyArea.setForeground(Color.black);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(verifyArea.getText().trim().equals("")) {
					verifyArea.setText("请输入内容");
					verifyArea.setForeground(Color.GRAY);
				}
			}
		});
		JScrollPane jsp=new JScrollPane(this.verifyArea); 
		jsp.setBounds(157,70, 200, 100);
		jsp.setBorder(null);
		jsp.getVerticalScrollBar().setUI(new ScrollBarUI());
		//this.verifyArea.setBounds(70, 35, 360, 30);
		//this.verifyArea.setBackground(Color.WHITE);
		//this.verifyArea.setFont(new Font("寰蒋闆呴粦", Font.PLAIN, 15));
		//this.verifyArea.setBorder(null);
		//this.verifyArea.setForeground(new Color(211, 211, 211));
		this.verifyPane.add(jsp);
		//this.verifyArea.setColumns(10);
//		this.verifyArea.addMouseListener(new MouseAdapter() {
//			public void mouseClicked(MouseEvent e) {
//				verifyArea.setForeground(new Color(34, 49, 72));
//
//				if(count ==0)
//				{
//					verifyArea.setText("");
//				}
//				count ++ ;
//
//			}
//		});
	
		this.confirmB = new JButton("完成");
		this.confirmB.setFont(new Font("微软雅黑", Font.BOLD, 16));
		//this.confirmB.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("source/瀹屾垚.png").getScaledInstance(104, 36, Image.SCALE_DEFAULT)));
		this.confirmB.setBounds(280, 250, 104, 36);
		this.verifyPane.add(confirmB);
		
	}
}