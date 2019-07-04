package frame.UserInfoFrame;

import client.InteractWithServer;
import config.PureInfo;
import config.Tools;
import frame.MainInterface.UI_MainInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;


import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class ModifyInfo extends JFrame{
	JButton b2 = new JButton("保存");
	JButton b3 = new JButton("关闭");
	JButton close;
	JButton minimize;
	Point pressedPoint;
	PureInfo pureInfo;
	public String imageFilePath="";
	public UI_MainInterface mainInterface;
	public ModifyInfo(PureInfo pureInfo, UI_MainInterface mainInterface) {
		this.pureInfo=pureInfo;
		this.mainInterface=mainInterface;
		init();
		setVisible(true);
	}
	void init() {
		setLayout(null);
		this.setUndecorated(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(378,350);//原来592
		setLocationRelativeTo(null);
		Font font = new Font("微软雅黑",Font.PLAIN,13);
		
		getContentPane().setBackground(Color.WHITE);
		Container c = getContentPane();
		c.setBackground(new Color(223,238,250));
	    
		JPanel jp1 = new JPanel();
		jp1.setBounds(0,0,386,28);
		jp1.setBackground(new Color(40,138,221));
		c.add(jp1);
		JPanel jp2 = new JPanel();
		jp2.setBounds(0, 465, 378, 35);
		jp2.setBackground(new Color(223,238,250));
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
		close.setIcon(new ImageIcon("res/PersonInfo/closebutton_normal.png"));
		close.setRolloverIcon(new ImageIcon("res/PersonInfo/closebutton_hover.png"));
		close.setPressedIcon(new ImageIcon("res/PersonInfo/closebutton_down.png"));
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
		minimize.setIcon(new ImageIcon("res/PersonInfo/minbutton_normal.png"));
		minimize.setRolloverIcon(new ImageIcon("res/PersonInfo/minbutton_hover.png"));
		minimize.setPressedIcon(new ImageIcon("res/PersonInfo/minbutton_down.png"));
		minimize.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setExtendedState(JFrame.ICONIFIED);
			}
		});
		jp1.add(minimize);

		JLabel modify = new JLabel("编辑资料");
		modify.setFont(font);
		modify.setForeground(Color.white);
		modify.setBounds(30, 5, 100, 23);
		jp1.add(modify);
		//
		
		JLabel l1 = new JLabel("昵称");
		l1.setFont(font);
		l1.setBounds(20,40,50,20);
		c.add(l1);
		JTextField userName= new JTextField(pureInfo.getUserNickName());
		userName.setBackground(Color.white);
		userName.setBorder(null);
		userName.setBounds(80,40,100,20);
		c.add(userName);

		JLabel l2 = new JLabel("性别");
		l2.setFont(font);
		l2.setBounds(200,40,50,20);
		c.add(l2);
		String sex[] = {"男","女"};
		JComboBox<String> userSex = new JComboBox<>(sex);
		if (pureInfo.getUserSex().equals("男")){
			userSex.setSelectedItem("男");
		}else {
			userSex.setSelectedItem("女");
		}
		userSex.setBorder(null);
		userSex.setBounds(260,40,100,20);
		c.add(userSex);
		
		JLabel l3 = new JLabel("生日");
		l3.setFont(font);
		l3.setBounds(20,70,50,20);
		c.add(l3);
		JTextField userBirth= new JTextField(pureInfo.getUserBirthday());
		userBirth.setBorder(null);
		userBirth.setBackground(Color.white);
		userBirth.setBounds(80,70,100,20);
		c.add(userBirth);
		
		JLabel l6 = new JLabel("家乡");
		l6.setFont(font);
		l6.setBounds(20,130,50,20);
		c.add(l6);
		JTextField userCountry= new JTextField(pureInfo.getUserHometown());
		userCountry.setBackground(Color.white);
		userCountry.setBorder(null);
		userCountry.setBounds(80,130,260,20);
		c.add(userCountry);
		
		JLabel l7 = new JLabel("所在地");
		l7.setFont(font);
		l7.setBounds(20,160,50,20);
		c.add(l7);
		JTextField userAddress= new JTextField(pureInfo.getUserAddress());
		userAddress.setBorder(null);
		userAddress.setBackground(Color.white);
		userAddress.setBounds(80,160,260,20);
		c.add(userAddress);
		
		JLabel l8 = new JLabel("公司");
		l8.setBounds(20,190,50,20);
		l8.setFont(font);
		c.add(l8);
		JTextField userCompany= new JTextField(pureInfo.getUserCompany());
		userCompany.setBackground(Color.white);
		userCompany.setBorder(null);
		userCompany.setBounds(80,190,260,20);
		c.add(userCompany);
		
		JLabel l9 = new JLabel("邮箱");
		l9.setBounds(20,220,50,20);
		l9.setFont(font);
		c.add(l9);
		JTextField userEmail= new JTextField(pureInfo.getUserEmail());
		userEmail.setBackground(Color.white);
		userEmail.setBorder(null);
		userEmail.setBounds(80,220,260,20);
		c.add(userEmail);
		
		JLabel l10 = new JLabel("个性签名");
		l10.setBounds(20,250,80,20);
		l10.setFont(font);
		c.add(l10);
		JTextArea userSignature= new JTextArea(3,15);
		userSignature.setBackground(Color.white);
		userSignature.setBorder(null);
		userSignature.setText(pureInfo.getuserSignature());
		userSignature.setBounds(80,250,260,40);
		c.add(userSignature);
		
		JButton b1 = new JButton("更换头像");
		b1.setBounds(50, 300, 100, 30);
		b1.setBorder(null);
		b1.setBackground(new Color(40,138,221));
		b1.setForeground(Color.white);
		c.add(b1);
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
                int returnVal=chooser.showOpenDialog(ModifyInfo.this);
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
					imageFilePath=filepath;
				}
			}
		});
		
		
		b2.setBounds(220, 300, 100, 30);
		b2.setForeground(Color.white);
		b2.setBorder(null);
		b2.setBackground(new Color(40,138,221));
		c.add(b2);
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 进行逻辑处理即可
				if(imageFilePath.equals("")){
					pureInfo =new PureInfo(pureInfo.getUserId(),
							pureInfo.getUserAvatar(),
							userName.getText(),
							userSignature.getText(),
							(String)userSex.getSelectedItem(),
							userBirth.getText(),
							userCountry.getText(),
							userCompany.getText(),
							userAddress.getText(),
							userEmail.getText(),
							pureInfo.getUserRegistertime()
					);
				}else {
					pureInfo=new PureInfo(pureInfo.getUserId(),
							Tools.getImageBinary(imageFilePath),
							userName.getText(),
							userSignature.getText(),
							(String)userSex.getSelectedItem(),
							userBirth.getText(),
							userCountry.getText(),
							userCompany.getText(),
							userAddress.getText(),
							userEmail.getText(),
							pureInfo.getUserRegistertime()
					);
				}
				if (InteractWithServer.modifyUserInfo(pureInfo)){
					new Info(pureInfo,mainInterface);
					mainInterface.modify(pureInfo);
					dispose();
				}else {
					JOptionPane.showMessageDialog(ModifyInfo.this, "修改失败！", "错误", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
}
