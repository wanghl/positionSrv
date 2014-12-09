package com.position.reader.server;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.position.db.DBInstance;
import com.position.db.DBManager;
import com.position.message.IReaderMessage;
import com.position.message.MessageBodyFactory;
import com.position.message.MessageResponse;
import com.position.message.ReaderMessage;


public class PositionHandler extends IoHandlerAdapter {
	
	private static final Logger log = Logger.getLogger(PositionHandler.class) ;

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
	
		log.error(cause);
	}
	
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		byte[] readerBuffer = (byte[]) message ;
		StringBuffer sb = new StringBuffer() ;
		for ( int i=0 ;i < readerBuffer.length; i++)
		{
			int v = readerBuffer[i] & 0xff;
			String hv =Integer.toHexString(v) ;
			if (hv.length() < 2)
			{
				sb.append(0);
			}
			sb.append( hv ) ;
		}
		log.info("开始解析" + sb.toString());
		
		MessageBodyFactory factory = new MessageBodyFactory();
		ReaderMessage messageBody = factory.getMessageBody(readerBuffer);
		if ( messageBody == null )
			return  ;
		switch (messageBody.getCommandCode()) {

		case IReaderMessage.MESSAGE_TYPE_TIME:
		//	log.info("校时报文(Reader --> Server)：" + sb.toString());
			MessageResponse.getInstance().sendTimeCheckResponse(session, messageBody);

		case IReaderMessage.MESSAGE_TYPE_IDMESSAGE :
		//	log.info("上传标签数据(Reader --> Server):" + sb.toString());
			Map<String ,Map> tagsMap = messageBody.getTagsMap() ;
			DBManager.newInstance().saveTagsChangeLog(messageBody);
			Map map ;
			DBManager dbManager = DBManager.newInstance();
			DBInstance triggerInstance  ;
			DBInstance cardInstance ;
			for(Entry<String,Map> entry : tagsMap.entrySet())
			{
			    triggerInstance = RelationData.getInstance().getTrigger(entry.getValue().get("triggerid").toString()) ;
				cardInstance = RelationData.getInstance().getCardInfo(entry.getValue().get("physicalid").toString());
				map = new HashMap() ;
				if ( triggerInstance != null && cardInstance != null)
				{
					
					map.put("cardid", cardInstance.getValue("cardid")) ;
					map.put("positionx", triggerInstance.getValue("positionx")) ;
					map.put("positiony", triggerInstance.getValue("positiony")) ;
					map.put("positionz", triggerInstance.getValue("positionz")) ;
					map.put("updatetime", new Date()) ;
					CardPool.getInstance().put(cardInstance.getValue("physicalid").toString(), map);
				}
				
			}
			MessageResponse.getInstance().sendIDMessageResponse(session ,messageBody);
			

		}
			

	}
	
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		//log.info(status);
	}
	



}
