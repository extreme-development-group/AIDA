package frame.UserInfoFrame;

import config.PureInfo;
import config.Tools;
import frame.ChatFrame.HeadPortrait;
import frame.MainInterface.UI_MainInterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Enumeration;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;

public class Info extends JFrame{
	JPanel userPanel;
	JPanel infoPanel;
	//JButton b1 = new JButton("编辑资料");
	Point pressedPoint;
	public UI_MainInterface mainInterface;

	private static void InitGlobalFont(Font font) {
		  FontUIResource fontRes = new FontUIResource(font);
		  for (Enumeration<Object> keys = UIManager.getDefaults().keys();
		  keys.hasMoreElements(); ) {
		  Object key = keys.nextElement();
		  Object value = UIManager.get(key);
		  if (value instanceof FontUIResource) {
		  UIManager.put(key, fontRes);
		  }
		  }
	}
	public Info(PureInfo pureInfo, UI_MainInterface mainInterface){
		this.userPanel = new JPanel();
		this.infoPanel = new JPanel();
		this.mainInterface=mainInterface;
		int setWidth=50;
		int setGrap=100;
		Font font = new Font("微软雅黑", Font.BOLD, 15);
		//Container c = getContentPane();
//		InitGlobalFont();
		this.setUndecorated(true);
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) { //鼠标按下事件
				pressedPoint = e.getPoint(); //记录鼠标坐标
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) { // 鼠标拖拽事件
				Point point = e.getPoint();// 获取当前坐标
				Point locationPoint = getLocation();// 获取窗体坐标
				int x = locationPoint.x + point.x - pressedPoint.x;// 计算移动后的新坐标
				int y = locationPoint.y + point.y - pressedPoint.y;
				setLocation(x, y);// 改变窗体位置
			}
		});
		setTitle("个人资料");
		//c.setLayout(new BorderLayout());
		setLayout(null);
		setSize(360, 522);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		userPanel.setBounds(0, 0, 360, 180);
		userPanel.setBackground(new Color(40,138,221));
		this.add(userPanel);
		userPanel.setLayout(null);
		//添加左右两个面板
		infoPanel.setBounds(0, 180, 360, 492);

		//添加关闭和最小化按钮
		class ExitNowFrameListenter implements ActionListener {
			private JFrame now;

			public ExitNowFrameListenter(JFrame now) {
				this.now = now;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				now.dispose();
			}
		}
		JButton close;
		JButton minimize;
		close = new JButton("");
		close.setMargin(new Insets(0, 0, 0, 0));
		close.setBounds(330, 0, 30, 30);// 386x30
		close.setContentAreaFilled(false); // set don't draw message area
		close.setBorderPainted(false); // set don't draw border
		close.setFocusPainted(false);
		close.setToolTipText("关闭");
		close.setIcon(new ImageIcon("res/PersonInfo/closebutton_normal.png"));
		close.setRolloverIcon(new ImageIcon("res/PersonInfo/closebutton_hover.png"));
		close.setPressedIcon(new ImageIcon("res/PersonInfo/closebutton_down.png"));
		close.addActionListener(new ExitNowFrameListenter(this));
		userPanel.add(close);

		// 最小化按钮
		minimize = new JButton();
		minimize.setMargin(new Insets(0, 0, 0, 0));
		minimize.setBounds(300, 0, 30, 30);//28x28
		minimize.setContentAreaFilled(false);
		minimize.setBorderPainted(false);
		minimize.setFocusPainted(false);
		minimize.setToolTipText("最小化");
		minimize.setIcon(new ImageIcon("res/PersonInfo/minbutton_normal.png"));
		minimize.setRolloverIcon(new ImageIcon("res/PersonInfo/minbutton_hover.png"));
		minimize.setPressedIcon(new ImageIcon("res/PersonInfo/minbutton_down.png"));
		minimize.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setExtendedState(JFrame.ICONIFIED);
			}
		});
		userPanel.add(minimize);
		//jp1.setBackground(Color.BLUE);
		
		Image image =Tools.base64StringToImage(pureInfo.getUserAvatar());
		HeadPortrait headPortrait=new HeadPortrait(100,100,image);
		//ImageIcon ico=new ImageIcon("src/bg.jpg");
		//ico.setImage(ico.getImage().getScaledInstance(50,20,Image.SCALE_DEFAULT));
		//userAvatar.setIcon(ico);
		headPortrait.setBounds(30,40,100,100);
		userPanel.add(headPortrait);

		JLabel userName = new JLabel(pureInfo.getUserNickName());
		userName.setOpaque(false);
		userName.setBorder(null);
		userName.setBounds(180,60,100,30);
		userPanel.add(userName);
		userName.setFont(new Font("微软雅黑",Font.BOLD,20));
		userName.setForeground(Color.white);
		//userName.setBackground(Color.LIGHT_GRAY);

		JTextArea signature = new JTextArea(pureInfo.getuserSignature());
		signature.setEditable(false);
		signature.setLineWrap(true);
		signature.setOpaque(false);
		signature.setFont(new Font("微软雅黑",Font.PLAIN,13));
		signature.setBounds(180, 100, 200, 50);
		userPanel.add(signature);//492


		//右面板添加元素
		infoPanel.setLayout(null);
		infoPanel.setBackground(new Color(223,238,250));
		JLabel l1 = new JLabel("UID");
		l1.setFont(new Font("微软雅黑",Font.BOLD,15));
		l1.setBounds(setWidth,20,30, 30);//坐标位置，宽和高
		JTextField userID = new JTextField(10);
		userID.setText(pureInfo.getUserId());
		userID.setBorder(null);
		userID.setOpaque(false);
		userID.setEditable(false);
		userID.setBounds(setGrap, 20, 150, 30);
		infoPanel.add(l1);
		infoPanel.add(userID);
		//JButton b1 = new JButton("编辑资料");
		if(pureInfo.getUserId().equals(mainInterface.userInfo.getUserId())){
			//编辑资料
			JButton modify = new JButton();
			modify.setMargin(new Insets(0, 0, 0, 0));
			modify.setBounds(660, 0, 30, 30);//28x28
			modify.setContentAreaFilled(false);
			modify.setBorderPainted(false);
			modify.setFocusPainted(false);
			modify.setToolTipText("编辑资料");
			modify.setIcon(new ImageIcon("res/PersonInfo/modify.png"));
			modify.setRolloverIcon(new ImageIcon("res/PersonInfo/cover_modify.png"));
			//modify.setPressedIcon(new ImageIcon("src/minbutton_down.png"));
			modify.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//setExtendedState(JFrame.ICONIFIED);
					ModifyInfo m = new ModifyInfo(pureInfo,Info.this.mainInterface);
					Info.this.dispose();
				}
			});
			infoPanel.add(modify);
			modify.setBounds(280, 20, 30, 30);
		}
//		b1.setBounds(230,20,100,30);
//		infoPanel.add(b1);
//		b1.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// 进行逻辑处理即可
//				//System.out.println("触发了事件");
//				ModifyInfo m = new ModifyInfo();
////				m.init();
////				m.setVisible(true);
//			}
//		});
		//
		JLabel l2 = new JLabel("性别");
		l2.setBounds(setWidth,60,30,30);
		l2.setFont(font);
		//l2.setOpaque(true);
		infoPanel.add(l2);
		//
		JTextField userSex = new JTextField(5);
		userSex.setText(pureInfo.getUserSex());
		userSex.setEditable(false);
		userSex.setOpaque(false);
		userSex.setBounds(setGrap,60,30,30);
		JTextField userBirth = new JTextField(5);
		userBirth.setText("1月1日");
		userBirth.setOpaque(false);
		userBirth.setEditable(false);
		userBirth.setBounds(200,60,60,30);
		JTextField t2_4 = new JTextField(5);
//		t2_4.setText("水瓶");
//		t2_4.setEditable(false);
//		t2_4.setBounds(210,60,50,30);
//		JTextField t2_5 = new JTextField(5);
//		t2_5.setText("兔");
//		t2_5.setEditable(false);
//		t2_5.setBounds(270,60,50,30);
		userSex.setBorder(null);userBirth.setBorder(null);//t2_4.setBorder(null);t2_5.setBorder(null);
		infoPanel.add(l2);
		infoPanel.add(userSex);infoPanel.add(userBirth);//infoPanel.add(t2_4);infoPanel.add(t2_5);

		JLabel l3 = new JLabel("家乡");
		l3.setBounds(setWidth,100,30,30);
		l3.setFont(font);
		JTextField country = new JTextField(10);
		country.setText("中国");
		country.setBounds(setGrap, 100, 200, 30);
		country.setEditable(false);
		country.setOpaque(false);
		country.setBorder(null);
		infoPanel.add(l3);
		infoPanel.add(country);

		JLabel l4 = new JLabel("公司");
		l4.setBounds(setWidth,140,30,30);
		l4.setFont(font);
		JTextField userCompany = new JTextField(10);
		userCompany.setText(pureInfo.getUserCompany());
		userCompany.setBounds(setGrap, 140, 200, 30);
		userCompany.setEditable(false);
		userCompany.setOpaque(false);
		userCompany.setBorder(null);
		infoPanel.add(l4);
		infoPanel.add(userCompany);

		JLabel l5 = new JLabel("注册时间");
		l5.setBounds(setWidth,180,60,30);
		l5.setFont(font);
		JTextField registerTime = new JTextField(10);
		registerTime.setText(pureInfo.getUserRegistertime());
		registerTime.setEditable(false);
		registerTime.setBorder(null);
		registerTime.setOpaque(false);
		registerTime.setBounds(setGrap+20, 180, 200, 30);
		infoPanel.add(l5);
		infoPanel.add(registerTime);

		JLabel l6 = new JLabel("地址");
		l6.setBounds(setWidth,220,30,30);
		l6.setFont(font);
		JTextField userAddress = new JTextField(10);
		userAddress.setText(pureInfo.getUserAddress());
		userAddress.setEditable(false);
		userAddress.setOpaque(false);
		userAddress.setBounds(setGrap, 220, 200, 30);
		userAddress.setBorder(null);
		infoPanel.add(l6);
		infoPanel.add(userAddress);

		JLabel l7 = new JLabel("邮箱");
		l7.setBounds(setWidth,260,30,30);
		l7.setFont(font);
		JTextField userEmail = new JTextField(10);
		userEmail.setText(pureInfo.getUserEmail());
		userEmail.setEditable(false);
		userEmail.setBounds(setGrap, 260, 200, 30);
		userEmail.setBorder(null);
		userEmail.setOpaque(false);
		infoPanel.add(l7);
		infoPanel.add(userEmail);
		this.add(infoPanel);
		this.setVisible(true);
	}
}
