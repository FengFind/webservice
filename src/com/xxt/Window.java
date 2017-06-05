package com.xxt;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Window {
	static String port="";
	static String file="";
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
		conLablePort.weighty=0.5;									//ˮƽ����Ȩ��
		conLablePort.gridx=1;                                       //�ڶ���
		conLablePort.gridy=1;										//�ڶ���
		conLablePort.insets=new Insets(0,0,0,20);
		topPane.add(textPort,conLablePort);
		
		//file�ı���
		GridBagConstraints conLableText=new GridBagConstraints();  //����Լ������
		conLableText.fill=GridBagConstraints.HORIZONTAL;				  //���ˮƽ��չ��������Ԫ��
		textFile=new JTextField();
		conLableText.weightx=0.8;									//ˮƽ����Ȩ��
		conLableText.weighty=0.5;									//ˮƽ����Ȩ��
		conLableText.gridx=1;                                       //�ڶ���
		conLableText.gridy=0;										//��һ��
		conLableText.insets=new Insets(10,0,10,20);
		topPane.add(textFile,conLableText);
		
		buttonStart=new JButton("����");
		
		buttonStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	port=textPort.getText();
            	file=textFile.getText();
            	new Thread(new   Runnable(){

            		@Override
            		public void run() {
            			int iport=Integer.parseInt(port);
            			int ifile=Integer.parseInt(file);
            			HttpServer server = new HttpServer(iport,ifile);
            			//�ȴ���������
            			server.await();
            			
            		}
            		
            	}).start();
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
		//���������ô���
		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�����������
		addComponentdToPane(frame.getContentPane());
		//��ʾ����
		frame.pack();
		frame.setVisible(true);
		
		

	}
	
	public static void main(String[] args) {
		
		Window.createView();

	}
	

}

