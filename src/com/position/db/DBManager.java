package com.position.db;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.sql.SQLException;
import java.util.List;

import org.apache.mina.core.session.IoSession;

import com.position.message.ReaderMessage;
import com.position.reader.server.RelationData;
import com.position.util.MD5Util;

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
	
	public void sotreTcpConnectionChanges(IoSession session,int connectType) throws Exception
	{
		String ip ;
		Integer port ;
		if ( connectType == 0)
		{
			
		InetSocketAddress inetSocketAddress = (InetSocketAddress) session.getRemoteAddress();
		ip = inetSocketAddress.getAddress().getHostAddress() ;
		port = inetSocketAddress.getPort()  ;
		
		}
		else
		{
			ip = session.getLocalAddress().toString().split(":")[0] ;
			ip = ip.substring(1, ip.length()) ;
			port = Integer.parseInt(session.getLocalAddress().toString().split(":")[1] );
		}
		String sql ;
		if ( session.isClosing() ||  (session.isClosing() && session.isConnected()))
		{
			sql = "update rfid_tcpipconnect set closetime=now() where inip='" + ip + "'"
					+ " and inport=" + port ;
			DBOperator.createInstance().saveOrUpdate(sql, null) ;
		}
		else
		{
			sql = "insert into rfid_tcpipconnect (objuid,inip,inport,connectType) values(?,?,?,?)" ;
			DBInstance db = new DBInstance() ;
			db.putValue("objuid", MD5Util.getObjuid());
			db.putValue("inip", ip);
			db.putValue("inport", port);
			db.putValue("connectType", connectType);
			DBOperator.createInstance().saveOrUpdate(sql, db) ;
		}
		
		

	}
	
	
	public void saveTagsChangeLog(ReaderMessage message)
	{
		//Object tagUid = RelationData.getInstance().getCardInfo(message.getTagId()).getValue("objuid") ;
		//Object triggerUid = RelationData.getInstance().getTrigger(message.getNewTrigger())
	}
	

}
