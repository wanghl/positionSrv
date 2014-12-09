package com.position.db;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.mina.core.session.IoSession;

import com.position.message.ReaderMessage;
import com.position.reader.server.CardPool;
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
	// 0  离开触发区域 1 进入触发区域 2 持续触发
	
	public void saveTagsChangeLog(ReaderMessage message) throws Exception
	{
		DBInstance db = new DBInstance() ;
		Object tagUid = RelationData.getInstance().getCardInfo(message.getTagId()).getValue("objuid") ;
		String newTrigger = Integer.toString(message.getNewTrigger()) ;
		Object triggerUid = RelationData.getInstance().getTrigger(newTrigger).getValue("objuid")  ;
		
		if ( tagUid == null || triggerUid == null )
			return ;
		
		Object positionBase = RelationData.getInstance().getParas("positionBase") ;
		Map tagMap = CardPool.getInstance().get(message.getTagId()) ;
		db.putValue("objuid", MD5Util.getObjuid());
		db.putValue("carduid", tagUid);
		db.putValue("triggeruid", triggerUid);
		
		if ( tagMap == null || tagMap.get("positionx") == positionBase)
		{
			db.putValue("newstate", 1);
			db.putValue("oldstate", 0);
		}
		else
		{
			db.putValue("newstate", 2);
			db.putValue("oldstate", 1);
		}
		
		DBOperator.createInstance().saveOrUpdate(db.buildInsertSql("rfid_cardstatechangelog"), db);
		
		//Object triggerUid = RelationData.getInstance().getTrigger(message.getNewTrigger())
	}
	

}
