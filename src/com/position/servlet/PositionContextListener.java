package com.position.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.position.reader.server.PositionSocketManager;

public class PositionContextListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
		PositionSocketManager.getInstance().getIoAcceptor().dispose(); 
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
