package com.position.reader.server;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.position.db.DBInstance;
import com.position.db.DBOperator;

public class RelationData {

	private static final Logger log = Logger.getLogger(RelationData.class);

	private ConcurrentHashMap<String, DBInstance> cardInfo = new ConcurrentHashMap<String, DBInstance>();
	private ConcurrentHashMap<String, DBInstance> trigger = new ConcurrentHashMap<String, DBInstance>();
	private Map paras = new HashMap() ;
	
	private static boolean isFulled = false ;

	private static final RelationData INSTANCE = new RelationData();

	private RelationData() {
	}

	public static RelationData getInstance() {
		return INSTANCE;
	}
	
	public Object getParas(Object key)
	{
		return paras.get(key) ;
	}
	
	public DBInstance getCardInfo(String key)
	{
		return cardInfo.get(key) ;
	}
	
	public DBInstance getTrigger(String key)
	{
		return trigger.get(key) ;
	}
	
	public static boolean isFill()
	{
		return isFulled ;
	}

	public void fill() {
		try {
			DBOperator dbOperator = DBOperator.createInstance() ;
			List<DBInstance> list = dbOperator .query("select * from rfid_cardinfor", null);
			for (DBInstance db : list) {
				cardInfo.put(db.getValue("physicalid").toString(), db);
			}
			list =dbOperator.query("select * from rfid_trigger", null);
			for (DBInstance db : list) {
				trigger.put(db.getValue("triggerid").toString(), db);
			}
			
			list = dbOperator.query("select * from rfid_paras", null); 
			for( DBInstance db: list)
			{
				paras.put(db.getValue("paras_key"), db.getValue("paras_value")) ;
			}
			this.isFulled = true ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("数据初始化失败" + e);
		}
	}

}
