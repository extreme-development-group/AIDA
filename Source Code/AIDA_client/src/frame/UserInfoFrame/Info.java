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
	
	JPanel jp1 = new JPanel(),jp2 = new JPanel();
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
		this.mainInterface=mainInterface;
		//Container c = getContentPane();
		InitGlobalFont(new Font("微软雅黑", Font.PLAIN, 13));
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
		setSize(720, 522);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel jp = new JPanel();
		jp.setBounds(0, 0, 720, 30);
		jp.setBackground(Color.lightGray);
		add(jp);
		jp.setLayout(null);
		//添加左右两个面板
		jp1.setBounds(0, 30, 360, 492);
		jp2.setBounds(360, 30, 360, 492);

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
		close.setBounds(690, 0, 30, 30);// 386x30
		close.setContentAreaFilled(false); // set don't draw message area
		close.setBorderPainted(false); // set don't draw border
		close.setFocusPainted(false);
		close.setToolTipText("关闭");
		close.setIcon(new ImageIcon("res/PersonInfo/closebutton_normal.png"));
		close.setRolloverIcon(new ImageIcon("res/PersonInfo/closebutton_hover.png"));
		close.setPressedIcon(new ImageIcon("res/PersonInfo/closebutton_down.png"));
		close.addActionListener(new ExitNowFrameListenter(this));
		jp.add(close);

		// 最小化按钮
		minimize = new JButton();
		minimize.setMargin(new Insets(0, 0, 0, 0));
		minimize.setBounds(660, 0, 30, 30);//28x28
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
		jp.add(minimize);
		//jp1.setBackground(Color.BLUE);

		add(jp1);
		add(jp2);

		//左面板添加元素
		jp1.setLayout(null);
		JLabel left1 = new JLabel("背景");
		left1.setBounds(0, 0, 360, 360);
		Icon icon = new ImageIcon("res/PersonInfo/bg.jpg");
		left1.setIcon(icon);
		jp1.add(left1);
		left1.setOpaque(true);
		jp1.add(left1);
		Image image =Tools.base64StringToImage(pureInfo.getUserAvatar());
		HeadPortrait headPortrait=new HeadPortrait(70,70,image);
		//ImageIcon ico=new ImageIcon("src/bg.jpg");
		//ico.setImage(ico.getImage().getScaledInstance(50,20,Image.SCALE_DEFAULT));
		//userAvatar.setIcon(ico);
		headPortrait.setBounds(40,390,70,70);
		jp1.add(headPortrait);

		JLabel lefcountry = new JLabel(pureInfo.getUserNickName());
		lefcountry.setOpaque(true);
		lefcountry.setBounds(150,390,100,30);
		//lefcountry.setBackground(Color.LIGHT_GRAY);
		jp1.add(lefcountry);

		JTextArea signature = new JTextArea(pureInfo.getuserSignature());
		signature.setEditable(true);
		signature.setLineWrap(true);
		signature.setOpaque(false);
		signature.setBounds(150, 430, 200, 50);//492
		jp1.add(signature);
		//右面板添加元素
		jp2.setLayout(null);
		JLabel l1 = new JLabel("QQ");
		l1.setBounds(20,20,30, 30);//坐标位置，宽和高
		l1.setIcon(new ImageIcon("res/PersonInfo/qq.png"));
		JTextField t1 = new JTextField(10);
		t1.setText(pureInfo.getUserId());
		t1.setBorder(null);
		t1.setEditable(false);
		t1.setBounds(60, 20, 150, 30);
		jp2.add(l1);
		jp2.add(t1);
		//JButton b1 = new JButton("编辑资料");

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
		jp2.add(modify);
		modify.setBounds(280, 20, 30, 30);
//		b1.setBounds(230,20,100,30);
//		jp2.add(b1);
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
		JLabel l2 = new JLabel();
		l2.setBounds(20,60,30,30);
		l2.setIcon(new ImageIcon("res/PersonInfo/info.png"));
		//l2.setOpaque(true);
		jp2.add(l2);
		//
		JTextField userSex = new JTextField(5);
		userSex.setText(pureInfo.getUserSex());
		userSex.setEditable(false);
		userSex.setBounds(60,60,30,30);
		JTextField userBirth = new JTextField(5);
		userBirth.setText("1月1日");
		userBirth.setEditable(false);
		userBirth.setBounds(140,60,60,30);
		JTextField t2_4 = new JTextField(5);
//		t2_4.setText("水瓶");
//		t2_4.setEditable(false);
//		t2_4.setBounds(210,60,50,30);
//		JTextField t2_5 = new JTextField(5);
//		t2_5.setText("兔");
//		t2_5.setEditable(false);
//		t2_5.setBounds(270,60,50,30);
		userSex.setBorder(null);userBirth.setBorder(null);//t2_4.setBorder(null);t2_5.setBorder(null);
		jp2.add(l2);
		jp2.add(userSex);jp2.add(userBirth);//jp2.add(t2_4);jp2.add(t2_5);

		JLabel l3 = new JLabel("家乡");
		l3.setBounds(20,100,30,30);
		JTextField country = new JTextField(10);
		country.setText("中国");
		country.setBounds(60, 100, 200, 30);
		country.setEditable(false);
		country.setBorder(null);
		jp2.add(l3);
		jp2.add(country);

		JLabel l4 = new JLabel("公司");
		l4.setBounds(20,140,30,30);
		JTextField userCompany = new JTextField(10);
		userCompany.setText(pureInfo.getUserCompany());
		userCompany.setBounds(60, 140, 200, 30);
		userCompany.setEditable(false);
		userCompany.setBorder(null);
		jp2.add(l4);
		jp2.add(userCompany);

		JLabel l5 = new JLabel("注册时间");
		l5.setBounds(20,180,60,30);
		JTextField registerTime = new JTextField(10);
		registerTime.setText(pureInfo.getUserRegistertime());
		registerTime.setEditable(false);
		registerTime.setBorder(null);
		registerTime.setBounds(90, 180, 200, 30);
		jp2.add(l5);
		jp2.add(registerTime);

		JLabel l6 = new JLabel("地址");
		l6.setBounds(20,220,30,30);
		JTextField userAddress = new JTextField(10);
		userAddress.setText(pureInfo.getUserAddress());
		userAddress.setEditable(false);
		userAddress.setBounds(60, 220, 200, 30);userAddress.setBorder(null);
		jp2.add(l6);
		jp2.add(userAddress);

		JLabel l7 = new JLabel("邮箱");
		l7.setBounds(20,260,30,30);
		JTextField userEmail = new JTextField(10);
		userEmail.setText(pureInfo.getUserEmail());
		userEmail.setEditable(false);
		userEmail.setBounds(60, 260, 200, 30);userEmail.setBorder(null);
		jp2.add(l7);
		jp2.add(userEmail);
		this.setVisible(true);
	}
}
