package com.position.reader.server;

import org.apache.mina.core.service.IoAcceptor;

public class PositionSocketManager {
	
	private final static PositionSocketManager INSTANCE = new PositionSocketManager() ;
	
	private IoAcceptor connector  ;
	
	private PositionSocketManager()
	{
		
	}
	
	public static PositionSocketManager getInstance()
	{
		return INSTANCE ;
	}
	
	public void buildManager(IoAcceptor connector )
	{
		this.connector = connector ;
	}
	
	public IoAcceptor getIoAcceptor()
	{
		return connector;
	}
	

}
