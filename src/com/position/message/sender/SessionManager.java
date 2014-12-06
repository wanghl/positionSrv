package com.position.message.sender;

import org.apache.mina.core.session.IoSession;

public class SessionManager {
	
	
	private final static SessionManager INSTANCE = new SessionManager() ;
	
	
	private SessionManager(){} ;
	
	private IoSession session ;
	
	
	public static SessionManager getInstance()
	{
		return INSTANCE ;
	}
	
	
	public void buildSessionManager(IoSession s)
	{
		if( session != null)
			throw new RuntimeException("SESSION已经初始化") ;
		
		this.session = s;
	}
	
	public  IoSession getSession()
	{
		return session ;
	}
	
	public  void putSession(IoSession s)
	{
		this.session = s ;
	}
	

}
