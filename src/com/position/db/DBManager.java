package com.position.db;

import java.sql.SQLException;
import java.util.List;

public class DBManager {
	
	private DBManager(){} ;
	
	public static DBManager newInstance()
	{
		return new DBManager() ;
	}
	
	public void insertTcpConnectRecord()
	{
		
	}
	
	public DBInstance getTriggerbyId(String id) throws SQLException
	{
		String sql = "select * from rfid_trigger where triggerid='" + id + "'" ;
		List<DBInstance> list = DBOperator.createInstance().query(sql, null) ;
		if ( list.isEmpty() )
		{
			return null ;
		}
		else
		{
			return list.get(0) ;
		}
	}
	public DBInstance getCardbyId(String id) throws SQLException
	{
		String sql = "select * from rfid_cardinfor where physicalid='" + id + "'" ;
		List<DBInstance> list = DBOperator.createInstance().query(sql, null) ;
		if ( list.isEmpty() )
		{
			return null ;
		}
		else
		{
			return list.get(0) ;
		}
	}

}
