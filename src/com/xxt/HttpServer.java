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
	int ifile;
	/**
	 * WEB_ROOT��HTML�������ļ���ŵ�Ŀ¼. �����WEB_ROOTΪ����Ŀ¼�µ�webrootĿ¼
	 */
	public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";

	// �رշ�������
	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

	public HttpServer(int iport, int ifile) {
		this.iport=iport;
		this.ifile=ifile;
	}

	public static void main(String[] args) {
		/*HttpServer server = new HttpServer();
		//�ȴ���������
		server.await();*/
	}

	public void await() {
		ServerSocket serverSocket = null;
		int port = this.iport;
		try {
			//�������׽��ֶ���
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
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
