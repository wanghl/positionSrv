package com.position.message.sender;


import java.util.Timer;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketConnector;


public class SenderHandler extends IoHandlerAdapter {
	
	private static final Logger log = Logger.getLogger(SenderHandler.class) ;
	
	 private static NioSocketConnector connector;
     private Timer timer  ;
     public SenderHandler(NioSocketConnector connector ,Timer timer) {
          this.connector = connector;
          this.timer = timer ; 
     }
 	@Override
     public void sessionCreated(IoSession session) throws Exception {
 		
 		SocketSessionConfig cfg = (SocketSessionConfig) session.getConfig();
		cfg.setKeepAlive(true);
		cfg.setSoLinger(0);
		
 	}


	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		log.error(cause);
	}
	
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		    /*for (;;)
		    {
		    	Thread.sleep(1000);
		    	session.write("sb中文123".getBytes()) ;
		    	System.out.println("sss") ;
		    }*/
		
	}
	
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		//log.info(status);
		session.close(false) ;
	}
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		//timer.cancel(); 
		
		reConnection();
	}
	
	public static void reConnection()
	{
		while(true)
		{
			
			log.info("与服务端连接断开，2秒后重连");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ConnectFuture cfu = connector.connect() ;
			cfu.awaitUninterruptibly();
			if ( cfu.isConnected())
			{
				SessionManager.getInstance().putSession(cfu.getSession());

			break ; 
			}
		
		}
	}
	


}
