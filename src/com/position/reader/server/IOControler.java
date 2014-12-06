package com.position.reader.server;


import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class IOControler extends IoFilterAdapter {
	
	private static final Logger log = Logger.getLogger(IOControler.class) ;

	public void sessionOpened(NextFilter nextFilter, IoSession session) throws Exception {
		
		InetSocketAddress inetSocketAddress = (InetSocketAddress) session.getRemoteAddress();
		log.info("收到新的连接请求，IP：" + inetSocketAddress.getAddress().getHostAddress() + " 端口:" + inetSocketAddress.getPort());

		IoBuffer ioBuffer = IoBuffer.allocate(37);
		ioBuffer.setAutoExpand(true);
		ioBuffer.setAutoShrink(true) ;
		session.setAttribute("messageBuffer" , ioBuffer) ;
		
		nextFilter.sessionOpened(session);

	}

	public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
		
		//System.out.println(message) ;
		
		nextFilter.messageReceived(session, message);
	}
	
	public void sessionIdle(IoFilter.NextFilter nextFilter, IoSession session, IdleStatus status) throws Exception {
		nextFilter.sessionIdle(session, status);
	}



}
