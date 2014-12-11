package com.position.message.sender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimerTask;

import org.apache.mina.core.session.IoSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.position.db.DBInstance;
import com.position.reader.server.CardPool;
import com.position.reader.server.RelationData;

public class SenderTask extends TimerTask {
	/*
	public IoSession session ;
	
	public SenderTask(IoSession s)
	{
		this.session = s; 
	}
*/
	@Override
	public void run() {
		// TODO Auto-generated method stub..
		
		if ( SessionManager.getInstance().getSession().isClosing()
				&& SessionManager.getInstance().getSession().isConnected())
		{
			SenderHandler.reConnection();
		}
		
		//System.out.println( "session closed " + SessionManager.getInstance().getSession().isClosing()  + "  " + SessionManager.getInstance().getSession().isConnected()) ;

		if ( CardPool.getInstance().getTags().isEmpty() )
		{
			return ;
		}
		else
		{

			Map value = new HashMap() ;
			
			Map jsonMap = new HashMap() ;
			
			List set  = new ArrayList() ;
			
			Map<Object, Map> map = CardPool.getInstance().getTags() ;
			
			for( Entry<Object ,Map> entry : map.entrySet())
			{
				value = new HashMap() ;
				value.put("cardid", entry.getKey()) ;
				value.put("userid", ( entry.getValue().get("cardid") == null  ||  entry.getValue().get("cardid").equals("")) ? "-1" : entry.getValue().get("cardid") ) ;
				value.put("positionx", ( entry.getValue().get("positionx") == null ||  entry.getValue().get("positionx").equals("")) ? "-1" : entry.getValue().get("positionx") ) ;
				value.put("positiony", ( entry.getValue().get("positiony") == null ||  entry.getValue().get("positiony").equals("")) ? "-1" : entry.getValue().get("positiony") ) ;
				value.put("positionz",( entry.getValue().get("positionz") == null ||  entry.getValue().get("positionz").equals("")) ? "-1" : entry.getValue().get("positionz") ) ;
				value.put("glassesid", (entry.getValue().get("glassesid") == null ||  entry.getValue().get("glassesid").equals("")) ? "-1" : entry.getValue().get("glassesid")) ;
				
				set.add(value) ;
				
			}
			
			String transMode = RelationData.getInstance().getParas("transMode").toString() ;
			if ( Integer.parseInt( transMode ) == 1)
			{
				Object positionBase = RelationData.getInstance().getParas("positionBase") ;
				for ( Entry<String ,DBInstance> entry : RelationData.getInstance().getCardInfoEntry())
				{
					if ( CardPool.getInstance().get(entry.getKey()) == null )
					{
						value = new HashMap(); 
						value.put("cardid", entry.getValue().getValue("physicalid")) ;
						value.put("userid", ( entry.getValue().getValue("cardid") == null ) ? "-1" : entry.getValue().getValue("cardid") ) ;
						value.put("glassesid", (entry.getValue().getValue("glassesid") == null ) ? "-1" : entry.getValue().getValue("glassesid")) ;
						value.put("positionx", positionBase) ;
						value.put("positiony", positionBase) ;
						value.put("positionz", positionBase) ;
						
						set.add(value) ;
					}
					
				}
			}
			
			jsonMap.put("RFID", set); 
			
			//System.out.println(JSON.toJSONString(jsonMap)) ;
			
			//JSONObject.
			
			if ( !  SessionManager.getInstance().getSession().isClosing() && SessionManager.getInstance().getSession().isConnected())
			{
				
				SessionManager.getInstance().getSession().write(( JSON.toJSON(jsonMap).toString()+"\n\n" ).getBytes()) ;
				
				//System.out.println( JSON.toJSON(jsonMap).toString() + "\n\n"  ) ;
				
			}
		}
	}
	
	
}
