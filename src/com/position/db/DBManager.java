package com.position.db;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.position.message.ReaderMessage;
import com.position.reader.server.CardPool;
import com.position.reader.server.RelationData;
import com.position.util.MD5Util;

/**
 * @author whlzcy
 *
 * 数据库操作的封装
 */
public class DBManager {
	
	
	private static final Logger log = Logger.getLogger(DBManager.class ) ;
	
	private DBManager(){} ;
	
	public static DBManager newInstance()
	{
		return new DBManager() ;
	}
	
	public void saveReaderHeartBeat(String readerId) throws Exception 
	{
		DBInstance readerInstance = RelationData.getInstance().getReaderById(readerId) ;
		if ( readerInstance == null )
		{
			return ;
		}
		String sql = "update rfid_reader set heartbeat_time=now() where objuid='" + readerInstance.getValue("objuid").toString() + "'" ;
		DBOperator.createInstance().saveOrUpdate(sql, null);
		
		readerInstance.putValue("heartbeat_time", new Date());
		RelationData.getInstance().setReaderInfor(readerId, readerInstance);
		
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
			DBInstance db = new DBInstance() ;
			db.putValue("objuid", MD5Util.getObjuid());
			db.putValue("inip", ip);
			db.putValue("inport", port);
			db.putValue("connectType", connectType);
			DBOperator.createInstance().saveOrUpdate(db.buildInsertSql("rfid_tcpipconnect"),db) ;
		}
		
		

	}
	// 0  离开触发区域 1 进入触发区域 2 持续触发
	
	public void saveTagsChangeLog(ReaderMessage message) throws Exception
	{
		DBInstance db = new DBInstance() ;
		Object tagUid = RelationData.getInstance().getCardInfo(message.getTagId());
		if( tagUid == null){
			log.warn("编号为 " + message.getTagId() + " 的标签未注册");
			return ;
		}else{
			tagUid = RelationData.getInstance().getCardInfo(message.getTagId()).getValue("objuid") ;
		}
		String newTrigger = Integer.toString(message.getNewTrigger()) ;
		Object triggerUid = RelationData.getInstance().getTrigger(newTrigger) ;
		
		if ( triggerUid == null ){
			log.warn("编号为 " + newTrigger + " 的触发器器未注册");
			return ;
		}
		else
		{
			triggerUid = RelationData.getInstance().getTrigger(newTrigger).getValue("objuid")  ;
		}
		Object positionBase = RelationData.getInstance().getParas("positionBase") ;
		if ( positionBase == null )
			positionBase = 10000000 ;
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
