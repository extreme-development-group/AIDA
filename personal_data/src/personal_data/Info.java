package personal_data;

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

	void createFrame() {
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
		close.setIcon(new ImageIcon("src/closebutton_normal.png"));
		close.setRolloverIcon(new ImageIcon("src/closebutton_hover.png"));
		close.setPressedIcon(new ImageIcon("src/closebutton_down.png"));
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
		minimize.setIcon(new ImageIcon("src/minbutton_normal.png"));
		minimize.setRolloverIcon(new ImageIcon("src/minbutton_hover.png"));
		minimize.setPressedIcon(new ImageIcon("src/minbutton_down.png"));
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
		Icon icon = new ImageIcon("src/bg.jpg");
		left1.setIcon(icon);
		jp1.add(left1);
		left1.setOpaque(true);
		jp1.add(left1);
		
		JLabel left2 = new JLabel();
		//ImageIcon ico=new ImageIcon("src/bg.jpg");
		//ico.setImage(ico.getImage().getScaledInstance(50,20,Image.SCALE_DEFAULT));
		//left2.setIcon(ico);
		left2.setBounds(40,390,70,70);
		left2.setIcon(icon);
		jp1.add(left2);
		
		JLabel left3 = new JLabel("昵称");
		left3.setOpaque(true);
		left3.setBounds(150,410,100,30);
		//left3.setBackground(Color.LIGHT_GRAY);
		jp1.add(left3);
		
		//右面板添加元素
		jp2.setLayout(null);
		JLabel l1 = new JLabel("QQ");
		l1.setBounds(20,20,30, 30);//坐标位置，宽和高
		l1.setIcon(new ImageIcon("src/qq.png"));
		JTextField t1 = new JTextField(10);
		t1.setText("123");
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
		modify.setIcon(new ImageIcon("src/modify.png"));
		modify.setRolloverIcon(new ImageIcon("src/cover_modify.png"));
		//modify.setPressedIcon(new ImageIcon("src/minbutton_down.png"));
		modify.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//setExtendedState(JFrame.ICONIFIED);
				ModifyInfo m = new ModifyInfo();
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
		l2.setIcon(new ImageIcon("src/info.png"));
		//l2.setOpaque(true);
		jp2.add(l2);
		//
		JTextField t2_1 = new JTextField(5);
		t2_1.setText("男");
		t2_1.setEditable(false);
		t2_1.setBounds(60,60,30,30);
		JTextField t2_2 = new JTextField(5);
		t2_2.setEditable(false);
		t2_2.setText("0岁");
		t2_2.setBounds(100,60,30,30);
		JTextField t2_3 = new JTextField(5);
		t2_3.setText("1月1日");
		t2_3.setEditable(false);
		t2_3.setBounds(140,60,60,30);
		JTextField t2_4 = new JTextField(5);
		t2_4.setText("水瓶");
		t2_4.setEditable(false);
		t2_4.setBounds(210,60,50,30);
		JTextField t2_5 = new JTextField(5);
		t2_5.setText("兔");
		t2_5.setEditable(false);
		t2_5.setBounds(270,60,50,30);
		t2_1.setBorder(null);t2_2.setBorder(null);t2_3.setBorder(null);t2_4.setBorder(null);t2_5.setBorder(null);
		jp2.add(l2);
		jp2.add(t2_1);jp2.add(t2_2);jp2.add(t2_3);jp2.add(t2_4);jp2.add(t2_5);
		
		JLabel l3 = new JLabel("家乡");
		l3.setBounds(20,100,30,30);
		JTextField t3 = new JTextField(10);
		t3.setText("中国");
		t3.setBounds(60, 100, 200, 30);
		t3.setEditable(false);
		t3.setBorder(null);
		jp2.add(l3);
		jp2.add(t3);
		
		JLabel l4 = new JLabel("公司");
		l4.setBounds(20,140,30,30);
		JTextField t4 = new JTextField(10);
		t4.setBounds(60, 140, 200, 30);
		t4.setEditable(false);
		t4.setBorder(null);
		jp2.add(l4);
		jp2.add(t4);
		
		JLabel l5 = new JLabel("血型");
		l5.setBounds(20,180,30,30);
		JTextField t5 = new JTextField(10);
		t5.setEditable(false);
		t5.setBorder(null);
		t5.setBounds(60, 180, 50, 30);
		jp2.add(l5);
		jp2.add(t5);
		
		JLabel l6 = new JLabel("地址");
		l6.setBounds(20,220,30,30);
		JTextField t6 = new JTextField(10);
		t6.setEditable(false);
		t6.setBounds(60, 220, 200, 30);t6.setBorder(null);
		jp2.add(l6);
		jp2.add(t6);
		
		JLabel l7 = new JLabel("邮箱");
		l7.setBounds(20,260,30,30);
		JTextField t7 = new JTextField(10);
		t7.setEditable(false);
		t7.setBounds(60, 260, 200, 30);t7.setBorder(null);
		jp2.add(l7);
		jp2.add(t7);
	}
	public static void main(String[] args) {
		Info info = new Info();
		info.createFrame();
		info.setVisible(true);
	}
}
