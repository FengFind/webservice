package com.xxt;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Window {
	private static TrayIcon trayIcon = null;
	static SystemTray tray = SystemTray.getSystemTray();
	private static String port="";
	private static String file="";
	private volatile static HttpServer server=null;
	//���������ô���
	static JFrame frame=new JFrame();
	/*
	 * �������ķ�����
	 */
	public static void addComponentdToPane(Container pane) {

		JButton buttonStart,buttonStop;	//����������������ť
		JLabel lable1,lable2;           //������ǩ����
		JTextField textFile;            //�����ı������
		JTextField textPort;            //�����ı������
		JPanel topPane=new JPanel(); 	//�������ñ�ǩ���ı�������
		//����Ϊ���������
		topPane.setLayout(new GridBagLayout()); 
		JPanel bottomPane=new JPanel(); //�������ð�ť�����

		//���������������
		FlowLayout flowLayout=new FlowLayout();
		flowLayout.setHgap(80);  		//�����ˮƽ����
		flowLayout.setVgap(20);			//����䴹ֱ����
		bottomPane.setLayout(flowLayout);

		//��ǩһ
		GridBagConstraints conLable1=new GridBagConstraints();  //����Լ������
		conLable1.fill=GridBagConstraints.NONE;				  //���������Ȼ��С
		lable1=new JLabel("�˿ں�");
		conLable1.weightx=0.2;									//ˮƽ����Ȩ��
		conLable1.gridx=0;                                       //��һ��
		conLable1.gridy=0;										//��һ��
		conLable1.anchor=GridBagConstraints.LINE_END;			//��ǩ���Ҷ���
		topPane.add(lable1,conLable1);

		//��ǩ��
		GridBagConstraints conLable2=new GridBagConstraints();  //����Լ������
		conLable2.fill=GridBagConstraints.NONE;				  //���������Ȼ��С
		lable2=new JLabel("·��");

		conLable2.gridx=0;                                       //��һ��
		conLable2.gridy=1;										//��һ��
		conLable2.anchor=GridBagConstraints.LINE_END;			//��ǩ���Ҷ���
		topPane.add(lable2,conLable2);

		//port�ı���
		GridBagConstraints conLablePort=new GridBagConstraints();  //����Լ������
		conLablePort.fill=GridBagConstraints.HORIZONTAL;				  //���ˮƽ��չ��������Ԫ��
		textPort=new JTextField();
		conLablePort.weighty=0.8;									//��ֱ����Ȩ��
		conLablePort.weightx=0.5;									//ˮƽ����Ȩ��
		conLablePort.gridx=1;                                       //�ڶ���
		conLablePort.gridy=0;										//�ڶ���
		conLablePort.insets=new Insets(10,0,10,20);
		topPane.add(textPort,conLablePort);

		//file�ı���
		GridBagConstraints conLableText=new GridBagConstraints();  //����Լ������
		conLableText.fill=GridBagConstraints.HORIZONTAL;				  //���ˮƽ��չ��������Ԫ��
		textFile=new JTextField();
		conLableText.weighty=0.5;									//��ֱ����Ȩ��
		conLableText.gridx=1;                                       //�ڶ���
		conLableText.gridy=1;										//��һ��
		conLableText.insets=new Insets(0,0,0,20);
		topPane.add(textFile,conLableText);

		buttonStart=new JButton("����");

		buttonStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				port=textPort.getText();
				file=textFile.getText();
				int iport=Integer.parseInt(port);
				new Thread(new   Runnable(){
					@Override
					public void run() {
						if(server==null){
							HttpServer server= new HttpServer(iport,file);
							server.await();
						}
					}

				}).start();
				System.out.println("������");
			}
		});
		buttonStop=new JButton("ֹͣ");
		bottomPane.add(buttonStart);
		bottomPane.add(buttonStop);

		pane.add(topPane,BorderLayout.CENTER);
		pane.add(bottomPane,BorderLayout.PAGE_END);

	}
	/*
	 * �������沢��ʾ
	 */
	public static void createView() {

		frame.addWindowListener(new WindowAdapter() { // ���ڹر��¼�
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			};

			public void windowIconified(WindowEvent e) { // ������С���¼�

				frame.setVisible(false);
				Window.miniTray();

			}

		});

		frame.setLocation(500, 500);		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�����������
		addComponentdToPane(frame.getContentPane());
		//��ʾ����
		frame.pack();
		frame.setVisible(true);

	}
	 private static void miniTray() {  //������С��������������

		  ImageIcon trayImg = new ImageIcon("D://mouse.png");//����ͼ��

		  PopupMenu pop = new PopupMenu();  //���������һ��˵�
		  MenuItem show = new MenuItem("��ʾ������");
		  MenuItem exit = new MenuItem("�˳�");

		  show.addActionListener(new ActionListener() {

		   public void actionPerformed(ActionEvent e) { // ���»�ԭ��

		    tray.remove(trayIcon);
		    frame.setVisible(true);
		    frame.setExtendedState(JFrame.NORMAL);
		    frame.toFront();
		   }

		  });

		  exit.addActionListener(new ActionListener() { // �����˳���

		     public void actionPerformed(ActionEvent e) {

		      tray.remove(trayIcon);
		      System.exit(0);

		     }

		    });

		  pop.add(show);
		  pop.add(exit);

		  trayIcon = new TrayIcon(trayImg.getImage(), "web������", pop);
		  trayIcon.setImageAutoSize(true);

		  trayIcon.addMouseListener(new MouseAdapter() {

		   public void mouseClicked(MouseEvent e) { // �����˫���¼�

		    if (e.getClickCount() == 2) {

		     tray.remove(trayIcon); // ��ȥ����ͼ��
		     frame.setVisible(true);
		     frame.setExtendedState(JFrame.NORMAL); // ��ԭ����
		     frame.toFront();
		    }

		   }

		  });

		  try {

		   tray.add(trayIcon);

		  } catch (AWTException e1) {
		   // TODO Auto-generated catch block
		   e1.printStackTrace();
		  }

		 }
			public static void main(String[] args) {

				Window.createView();

			}


		}

