package personal_data;

import javax.swing.*;



import java.awt.*;
import java.awt.event.*;
public class ModifyInfo extends JFrame{
	JButton b2 = new JButton("保存");
	JButton b3 = new JButton("关闭");
	JButton close;
	JButton minimize;
	Point pressedPoint;
	public ModifyInfo() {
		init();
		setVisible(true);
	}
	void init() {
		setLayout(null);
		this.setUndecorated(true);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(378,500);//原来592
		
		getContentPane().setBackground(Color.WHITE);
		Container c = getContentPane();
	    
		JPanel jp1 = new JPanel();
		jp1.setBounds(0,0,386,28);
		jp1.setBackground(Color.LIGHT_GRAY);
		c.add(jp1);
		JPanel jp2 = new JPanel();
		jp2.setBounds(0, 465, 378, 35);
		jp2.setBackground(Color.LIGHT_GRAY);
		c.add(jp2);
		//
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

		// 关闭按钮
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
		jp1.setLayout(null);
		close = new JButton("");
		close.setMargin(new Insets(0, 0, 0, 0));
		close.setBounds(354, 0, 28, 28);// 386x30
		close.setContentAreaFilled(false); // set don't draw message area
		close.setBorderPainted(false); // set don't draw border
		close.setFocusPainted(false);
		close.setToolTipText("关闭");
		close.setIcon(new ImageIcon("src/closebutton_normal.png"));
		close.setRolloverIcon(new ImageIcon("src/closebutton_hover.png"));
		close.setPressedIcon(new ImageIcon("src/closebutton_down.png"));
		close.addActionListener(new ExitNowFrameListenter(this));
		jp1.add(close);

		// 最小化按钮
		minimize = new JButton();
		minimize.setMargin(new Insets(0, 0, 0, 0));
		minimize.setBounds(326, 0, 28, 28);//28x28
		minimize.setContentAreaFilled(false);
		minimize.setBorderPainted(false);
		minimize.setFocusPainted(false);
		minimize.setToolTipText("最小化");
		minimize.setIcon(new ImageIcon("src/minbutton_normal.png"));
		minimize.setRolloverIcon(new ImageIcon("src/minbutton_hover.png"));
		minimize.setPressedIcon(new ImageIcon("src/minbutton_down.png"));
		minimize.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setExtendedState(JFrame.ICONIFIED);
			}
		});
		jp1.add(minimize);
		
		JLabel qq = new JLabel();
		qq.setIcon(new ImageIcon("src/qq.png"));
		jp1.add(qq);
		qq.setBounds(0,5, 30, 23);
		JLabel modify = new JLabel("编辑资料");
		modify.setBounds(30, 5, 100, 23);
		jp1.add(modify);
		//
		
		JLabel l1 = new JLabel("昵称");
		l1.setBounds(20,40,50,20);
		c.add(l1);
		JTextField t1= new JTextField();
		t1.setBounds(80,40,100,20);
		c.add(t1);
		
		JLabel l2 = new JLabel("性别");
		l2.setBounds(200,40,50,20);
		c.add(l2);
		String sex[] = {"男","女"};
		JComboBox<String> combo1 = new JComboBox<>(sex);
		combo1.setBounds(260,40,100,20);
		c.add(combo1);
		
		JLabel l3 = new JLabel("生日");
		l3.setBounds(20,70,50,20);
		c.add(l3);
		JTextField t3= new JTextField();
		t3.setBounds(80,70,100,20);
		c.add(t3);
		
		JLabel l4 = new JLabel("血型");
		l4.setBounds(200,70,50,20);
		c.add(l4);
		String bloodtype[] = {"A","B","AB","O"};
		JComboBox<String> combo2 = new JComboBox<>(bloodtype);
		combo2.setBounds(260,70,100,20);
		c.add(combo2);
		
		JLabel l5 = new JLabel("职业");
		l5.setBounds(20,100,50,20);
		c.add(l5);
		JTextField t5= new JTextField();
		t5.setBounds(80,100,220,20);
		c.add(t5);
		
		JLabel l6 = new JLabel("家乡");
		l6.setBounds(20,130,50,20);
		c.add(l6);
		JTextField t6= new JTextField();
		t6.setBounds(80,130,220,20);
		c.add(t6);
		
		JLabel l7 = new JLabel("所在地");
		l7.setBounds(20,160,50,20);
		c.add(l7);
		JTextField t7= new JTextField();
		t7.setBounds(80,160,220,20);
		c.add(t7);
		
		JLabel l8 = new JLabel("公司");
		l8.setBounds(20,190,50,20);
		c.add(l8);
		JTextField t8= new JTextField();
		t8.setBounds(80,190,220,20);
		c.add(t8);
		
		JLabel l9 = new JLabel("邮箱");
		l9.setBounds(20,220,50,20);
		c.add(l9);
		JTextField t9= new JTextField();
		t9.setBounds(80,220,220,20);
		c.add(t9);
		
		JLabel l10 = new JLabel("个性签名");
		l10.setBounds(20,250,80,20);
		c.add(l10);
		JTextArea t10= new JTextArea(3,15);
		t10.setLineWrap(true);
		t10.setBounds(80,250,220,60);
		c.add(t10);
		
		JButton b1 = new JButton("更换头像");
		b1.setBounds(20, 320, 100, 30);
		c.add(b1);
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 进行逻辑处理即可
				//System.out.println("触发了事件");
				new changeAvatar();
			}
		});
		
		
		b2.setBounds(220, 350, 60, 30);
		c.add(b2);
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 进行逻辑处理即可
				//System.out.println("触发了事件");
				dispose();
			}
		});
		
		b3.setBounds(290, 350, 60, 30);
		c.add(b3);
		b3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 进行逻辑处理即可
				//System.out.println("触发了事件");
				dispose();
			}
		});
	}
}
