package com.position.reader.server;


import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.position.db.DBManager;

public class IOControler extends IoFilterAdapter {
	
	private static final Logger log = Logger.getLogger(IOControler.class) ;

	public void sessionOpened(NextFilter nextFilter, IoSession session) throws Exception {
		
		InetSocketAddress inetSocketAddress = (InetSocketAddress) session.getRemoteAddress();
		log.info("�յ��µ���������IP��" + inetSocketAddress.getAddress().getHostAddress() + " �˿�:" + inetSocketAddress.getPort());

		IoBuffer ioBuffer = IoBuffer.allocate(37);
		ioBuffer.setAutoExpand(true);
		ioBuffer.setAutoShrink(true) ;
		session.setAttribute("messageBuffer" , ioBuffer) ;
		
		DBManager.newInstance().sotreTcpConnectionChanges(session, 0);
		log.info(session);
		nextFilter.sessionOpened(session);

	}

	public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
		
		//System.out.println(message) ;
		
		nextFilter.messageReceived(session, message);
	}
	
	public void sessionIdle(IoFilter.NextFilter nextFilter, IoSession session, IdleStatus status) throws Exception {
		
		session.close(true) ;
		
		nextFilter.sessionIdle(session, status);
	}
	

	
	public void sessionClosed(IoFilter.NextFilter nextFilter, IoSession session) throws Exception {
		DBManager.newInstance().sotreTcpConnectionChanges(session, 0);
	//	session.close(false) ;
		nextFilter.sessionClosed(session);
	}
	
	public void exceptionCaught(IoFilter.NextFilter nextFilter, IoSession session, Throwable cause) throws Exception {
	//	session.close(false) ;
		cause.printStackTrace();
		log.error("please restart");
		nextFilter.exceptionCaught(session, cause);
	}



	



}
