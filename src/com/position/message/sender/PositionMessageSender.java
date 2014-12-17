package com.position.message.sender;

import java.net.InetSocketAddress;
import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.position.reader.server.RelationData;

public class PositionMessageSender extends HttpServlet {
	private static final Logger log = Logger.getLogger(PositionMessageSender.class) ;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		Sender task = new Sender();
		Thread taskThread = new Thread(task);
		taskThread.start();

	}

	public class Sender implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			final NioSocketConnector connector = new NioSocketConnector();
			IoSession session = null;
			Timer timer = new Timer();
			connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new PositionProtocolCodeFactory()));
			connector.getFilterChain().addLast("logger", new PositionLoggingFilter());
			connector.setHandler(new SenderHandler(connector, timer));
			connector.setConnectTimeoutMillis(3000);
		//	connector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 2000);
			
			String serverIp ="127.0.0.1";
			Integer port =8807; 
			if ( RelationData.isFill() )
			{
				serverIp = (String) RelationData.getInstance().getParas("serverIp") ;
				port = Integer.parseInt( RelationData.getInstance().getParas("serverPort").toString() ) ;
			}
			log.info("Server IP" + serverIp);
			log.info("Server Port" + port); 
			connector.setDefaultRemoteAddress(new InetSocketAddress(serverIp, port));
			ConnectFuture future;
			for (;;) {
				try {

					future = connector.connect(new InetSocketAddress(serverIp, port));
					future.awaitUninterruptibly();
					session = future.getSession();
					
					SessionManager.getInstance().buildSessionManager(session);
					
					log.info("与服务端建立连接");
					break;
				} catch (RuntimeIoException e) {
					// System.err.println(&quot;Failed to connect.&quot;);
					log.error("连接服务器失败! 5秒后重连");
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

			SenderTask task = new SenderTask();
			
			Object sendIntervalValue = RelationData.getInstance().getParas("sendInterval") ;
			
			Integer sendInterval = 30 ;
			
			if ( sendIntervalValue != null)
			{
				sendInterval = Integer.parseInt( sendIntervalValue.toString() ) ;
			}
			
			timer.schedule(task, sendInterval, sendInterval);
			
			future.awaitUninterruptibly();
			// wait until the summation is done
			// session.getCloseFuture().awaitUninterruptibly();
			//connector.dispose();

		}

	}
}
