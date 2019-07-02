//7.1 11:45 ������

package frame.Search;

import java.awt.Color;

//6.30.18:00 �ڶ���

//package com.lsh.view;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
//import java.awt.FlowLayout;
//import java.awt.event.FocusAdapter;
//import java.awt.event.FocusEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.JButton;
//import javax.swing.JScrollPane;


public class InquiryFriendFrame extends JFrame {
	
	//public List<User> result=new ArrayList<>();
	public int nowPage = 0;
	private int sumPage;
	private JPanel contentPane;
	private JTextField userIdField;
	private JPanel resultPanel;
	public List<JPanel> cells = new ArrayList<>();
	public JButton searchBtn;
	public JLabel pageNumLabel, nextPageLabel;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InquiryFriendFrame frame = new InquiryFriendFrame();
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


	public InquiryFriendFrame() {
		this.setTitle("����");
		//this.setUndecorated(true);
		
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width - 700) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - 400) / 2, 700, 400);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		this.contentPane.setLayout(null);
		this.userIdField = new JTextField("�������˺�/Ⱥ��");
		this.userIdField.setBounds(70, 35, 360, 30);
		this.userIdField.setBackground(Color.WHITE);
		this.userIdField.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		this.userIdField.setBorder(null);
		this.userIdField.setForeground(new Color(211, 211, 211));
		this.contentPane.add(userIdField);
		this.userIdField.setColumns(10);
		this.userIdField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				userIdField.setForeground(new Color(34, 49, 72));
				userIdField.setText("");
			}

		});

		JButton btnNewButton = new JButton("�� ѯ");
		btnNewButton.setBounds(505, 35, 110, 30);
		contentPane.add(btnNewButton);
		//overViewLabel
		JLabel overViewLabel = new JLabel("���ҵ�0�� / 0��Ⱥ");
		overViewLabel.setFont(new Font("΢���ź�", Font.PLAIN, 13));

		overViewLabel.setBounds(12, 105, 130, 18);
		contentPane.add(overViewLabel);

		resultPanel = new JPanel();
		resultPanel.setBounds(0, 130, 695, 235);
		resultPanel.setBackground(Color.WHITE);
		resultPanel.setLayout(new GridLayout(3, 3));
		for (int i = 0; i < 9; i++) {
			JPanel cell = new JPanel();
			cell.setBackground(Color.WHITE);
			cell.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.gray));
			cell.setLayout(null);
			resultPanel.add(cell);
			cells.add(cell);
		}
		this.add(resultPanel);

		pageNumLabel = new JLabel("0/0ҳ| ", SwingConstants.RIGHT);
		pageNumLabel.setForeground(new Color(90, 90, 90));
		pageNumLabel.setBounds(580, 105, 60, 20);
		pageNumLabel.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		this.add(pageNumLabel);

		nextPageLabel = new JLabel("��һҳ");
		nextPageLabel.setForeground(new Color(90, 90, 90));
		nextPageLabel.setBounds(640, 105, 45, 20);
		nextPageLabel.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		nextPageLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

		nextPageLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (sumPage > nowPage) {
					nowPage++;
				} else if (sumPage == nowPage) {
					nowPage = 1;
				}
				//showResult();
			}
		});
		this.add(nextPageLabel);

		/*
		 * Container c = getContentPane(); c.setLayout(new GridLayout(3,3,5,5)); for
		 * (int i = 0;i<9;i++) { c.add(new JButton("��ť"+i)); }
		 * 
		 */
		// ��ѯ����Ӧ����
		/*
		 * �����1 ������ŵ����1�� ��������Ÿ����2 �Ѹ�����Ϣ�ŵ����2//�永��Ϊ�������½�һ���ࡣ��ʵ��չʾ��Ϣ��ͷ���ǳƺ��룩���Ӻ�����Ӧ��
		 * 
		 * �Ӻ�����Ӧ�� ����һ�¿� ͷ������ �Ա� ���ڵ� ��֤��Ϣ���ı�����򣬣���Ӱ�ť��ȷ����������󵯳�dialog�������ѷ���
		 */


	}
}
