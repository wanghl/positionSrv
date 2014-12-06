package com.position.message.sender;



import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.SocketSessionConfig;

public class PositionLoggingFilter extends IoFilterAdapter {
	
	private static final Logger log = Logger.getLogger(PositionLoggingFilter.class) ;
	
	public void sessionCreated(IoFilter.NextFilter nextFilter, IoSession session) throws Exception {
		
		nextFilter.sessionCreated(session);
	}

	@Override
	public void sessionOpened(NextFilter nextFilter, IoSession session) throws Exception {
		
		log.info("连接服务器成功");
		
		nextFilter.sessionOpened(session);
	}

	@Override
	public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
		
		nextFilter.messageReceived(session, message);
	}
	
	public void sessionIdle(IoFilter.NextFilter nextFilter, IoSession session, IdleStatus status) throws Exception {
		nextFilter.sessionIdle(session, status);
	}

	


	
	
}
