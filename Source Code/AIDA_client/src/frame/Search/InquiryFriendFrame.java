//7.1 15:45 第四版
//7.1 11:45 第三版
package frame.Search;

import client.InteractWithServer;
import config.Tools;
import config.UserInfo;
import frame.ChatFrame.ChatWithFriends;
import frame.ChatFrame.ScrollBarUI;
import frame.MainInterface.DragWindowListener;
import frame.MainInterface.UI_Close;
import frame.MainInterface.UI_MainInterface;
import frame.MainInterface.UI_Minimize;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//import java.awt.FlowLayout;
//import java.awt.event.FocusAdapter;
//import java.awt.event.FocusEvent;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


//import javax.swing.JScrollPane;


public class InquiryFriendFrame extends JFrame {


	//public List<User> result=new ArrayList<>();
	private JPanel contentPane;
	private JTextField userIdField;
	private JPanel resultPanel;
	public JButton btnNewButton;
	private JScrollPane scrollPane;
	private JButton closeButton;
	private JButton minimizeButton;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InquiryFriendFrame frame = new InquiryFriendFrame(null);
				/*
				 * 	JButton demo = new JButton("close");
					demo.setBounds(0,0,30,30);
					demo.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							System.exit(0);

						}
					});
					frame.add(demo);
				 */


					frame.setVisible(true);


					//JFrame demo = new JFrame();
					//demo.setBounds(500, 500, 500, 500);

					//demo.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public InquiryFriendFrame(UI_MainInterface now) {
		this.setTitle("查找");
		//this.setUndecorated(true);

		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(700, 370);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		// 关闭按钮
		closeButton = new UI_Close();
		closeButton.setBounds(670, 0, 30, 30);
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InquiryFriendFrame.this.dispose();
			}
		});
		add(closeButton);
		// 最小化按钮
		minimizeButton = new UI_Minimize();
		minimizeButton.setBounds(640, 0, 30, 30);
		minimizeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setExtendedState(JFrame.ICONIFIED);
			}
		});
		add(minimizeButton);

		this.contentPane = new JPanel();
		this.contentPane.setBounds(0, 0, 700, 100);
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.contentPane.setLayout(null);
		this.contentPane.setBackground(new Color(40,138,221));

		this.userIdField = new JTextField("请输入账号/群号");
		this.userIdField.setBounds(70, 35, 360, 30);
		this.userIdField.setBackground(Color.WHITE);
		this.userIdField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		this.userIdField.setBorder(null);
		this.userIdField.setForeground(new Color(211, 211, 211));
		this.userIdField.setColumns(10);
		this.userIdField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if(userIdField.getText().equals("请输入账号/群号")) {
					userIdField.setText("");
					userIdField.setForeground(Color.black);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(userIdField.getText().trim().equals("")) {
					userIdField.setText("请输入账号/群号");
					userIdField.setForeground(Color.GRAY);
				}
			}
		});
		contentPane.add(userIdField);
		DragWindowListener adapter = new DragWindowListener();
		contentPane.addMouseMotionListener(adapter);
		contentPane.addMouseListener(adapter);


		//overViewLabel
		JLabel overViewLabel = new JLabel("");
		overViewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		overViewLabel.setBounds(12, 80, 130, 18);
		contentPane.add(overViewLabel);

		this.add(contentPane);


		resultPanel = new JPanel();
		resultPanel.setBackground(Color.white);
		resultPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		resultPanel.setPreferredSize(new Dimension(700,0));
		resultPanel.setLocation(0,100);
		resultPanel.setBackground(new Color(128,255,255));

		scrollPane =new JScrollPane(resultPanel);
		scrollPane.setBounds(0, 100, 700, 270);
		scrollPane.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(243,249,253)));
		scrollPane.setPreferredSize(new Dimension(700, 270));
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy((JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED));
		scrollPane.getVerticalScrollBar().setUI(new ScrollBarUI());
		scrollPane.getVerticalScrollBar().setUnitIncrement(15);
		scrollPane.setBackground(new Color(128,255,255));
		this.add(scrollPane);

		btnNewButton = new JButton("查 询");
		btnNewButton.setBounds(505, 35, 110, 30);
		btnNewButton.setBackground(new Color(22,155,213));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("微软雅黑",Font.BOLD,16));
		btnNewButton.setBorderPainted(false); // set don't draw border
		btnNewButton.setFocusPainted(false);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resultPanel.removeAll();
				resultPanel.validate();
				resultPanel.repaint();
				if (userIdField.getText().equals("")){
					JOptionPane.showMessageDialog(InquiryFriendFrame.this, "搜索为空！", "错误", JOptionPane.WARNING_MESSAGE);
				}else {
					Vector<UserInfo.FriendsOrGroups> friendsOrGroups = InteractWithServer.getFriendsOrGroup(userIdField.getText());
					System.out.println(friendsOrGroups.size());
					overViewLabel.setText("已查找到"+friendsOrGroups.size()+"结果");
					for (int i =0;i<friendsOrGroups.size();i++){
						String uId=friendsOrGroups.get(i).getId();
						Image uHead= Tools.base64StringToImage(friendsOrGroups.get(i).getAvatar());
						String uName = friendsOrGroups.get(i).getName();
						String uStatus=friendsOrGroups.get(i).getStatus();
						String uSignature = friendsOrGroups.get(i).getSignature();


						System.out.println("----------- 个人信息 --------------");
						System.out.println("ID：" + uId);
						System.out.println("昵称：" + uName);
						System.out.println("状态：" + uStatus);
						System.out.println("----------- END --------------");


						searchMemberPanel searchMemberPanel=new searchMemberPanel(uId,uHead,uName,uStatus,uSignature,now);
						if ((i+1)%3==0){
							resultPanel.setPreferredSize(new Dimension(700, (int)resultPanel.getPreferredSize().getHeight()+55));
						}
						resultPanel.add(searchMemberPanel);
					}
				}

			}
		});
		this.contentPane.add(btnNewButton);
		resultPanel.setPreferredSize(new Dimension(700,0));
//
//		Image image= null;
//		try {
//			image = ImageIO.read(new File("res/Avatar/head-test.JPG"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		int height=0;
//		for(int i=0; i<20; i++) {
//
//			searchMemberPanel searchMemberPanel=new searchMemberPanel("111",image,"111","zaixian",now);
//			resultPanel.add(searchMemberPanel);
//			if (i%3==0) {
//				resultPanel.setPreferredSize(new Dimension(700, (int)resultPanel.getPreferredSize().getHeight()+55));
//				System.out.println((int)resultPanel.getPreferredSize().getHeight());
//			}
//		}

		this.setVisible(true);
	}
}
