package com.position.message.sender;



import java.io.*;
import java.net.*;
import java.util.Date;

/**
 * @author whlzcy
 *
 * 模拟一个上级服务，测试用。用来测试本地发送的数据是否正确
 */
public class ImitateServer extends Thread {
	ServerSocket server = null;
	Socket sk = null;
	BufferedReader rdr = null;
	PrintWriter wtr = null;

	public ImitateServer() {

		try {
			server = new ServerSocket(9001);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {

		while (true) {
			System.out.println("Listenning...");
			try {
				// 每个请求交给一个线程去处理
				sk = server.accept();
				ServerThread th = new ServerThread(sk);
				th.start();
				sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {
		new ImitateServer().start();
	}

	class ServerThread extends Thread {

		Socket sk = null;

		public ServerThread(Socket sk) {
			this.sk = sk;
		}

		public void run() {
			try {
					
				wtr = new PrintWriter(sk.getOutputStream());
				rdr = new BufferedReader(new InputStreamReader(sk.getInputStream()));
				String tmp ;
				while ( ( tmp=rdr.readLine()) != null )
				{
					System.out.println( new Date()) ;
					System.out.println("从客户端来的信息：" + tmp);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
}
