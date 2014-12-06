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
import com.position.reader.server.CardPool;

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
				value.put("userid", entry.getValue().get("cardid")) ;
				value.put("positionx", entry.getValue().get("positionx")) ;
				value.put("positiony", entry.getValue().get("positiony")) ;
				value.put("positionz", entry.getValue().get("positionz")) ;
				
				set.add(value) ;
				
			}
			
			jsonMap.put("RFID", set); 
			
			//JSONObject.
			
			if ( !  SessionManager.getInstance().getSession().isClosing() && SessionManager.getInstance().getSession().isConnected())
			{
				
				SessionManager.getInstance().getSession().write(( JSON.toJSON(jsonMap).toString()+"\n\n" ).getBytes()) ;
				
				//System.out.println( JSON.toJSON(jsonMap).toString() + "\n\n"  ) ;
				
			}
		}
	}
	
	
}
