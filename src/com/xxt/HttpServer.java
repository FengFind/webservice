package com.xxt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;

public class HttpServer {
	int iport;
	static int oldPort=0;
	String ifile;
	ServerSocket serverSocket=null;
	/**
	 * WEB_ROOT��HTML�������ļ���ŵ�Ŀ¼. �����WEB_ROOTΪ����Ŀ¼�µ�webrootĿ¼
	 */
	//public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";
	public static  String WEB_ROOT = "";

	// �رշ�������
	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

	public HttpServer(int iport, String ifile) {
		this.iport=iport;
		System.out.println(ifile);
		WEB_ROOT=ifile.replaceAll("\\\\","\\\\\\\\");
		if(!ifile.endsWith("\\")){
			WEB_ROOT+="\\\\";
		}
		System.out.println(WEB_ROOT);
	}

	

	public  void await() {
		
		System.out.println(iport);
		try {
			//�������׽��ֶ���
			if(serverSocket==null){
				if(oldPort!=iport){
					serverSocket = new ServerSocket(iport, 1, InetAddress.getByName("127.0.0.1"));
					oldPort=iport;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
        
		// ѭ���ȴ�һ������
		while (true) {
			Socket socket = null;
			InputStream input = null;
			OutputStream output = null;
			try {
				//�ȴ����ӣ����ӳɹ��󣬷���һ��Socket����
				socket = serverSocket.accept();
				input = socket.getInputStream();
				output = socket.getOutputStream();

				// ����Request���󲢽���
				Request request = new Request(input);
				request.parse();
				
				// ����Ƿ��ǹرշ�������
				if (request.getUri().equals(SHUTDOWN_COMMAND)) {
					break;
				}

				// ���� Response ����
				Response response = new Response(output);
				response.setRequest(request);
				response.sendStaticResource();

				// �ر� socket ����
				socket.close();

			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}
	
}
