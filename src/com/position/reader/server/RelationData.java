package com.position.reader.server;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.position.db.DBInstance;
import com.position.db.DBOperator;

public class RelationData {

	private static final Logger log = Logger.getLogger(RelationData.class);

	private ConcurrentHashMap<String, DBInstance> cardInfo = new ConcurrentHashMap<String, DBInstance>();
	private ConcurrentHashMap<String, DBInstance> trigger = new ConcurrentHashMap<String, DBInstance>();
	private ConcurrentHashMap<String, DBInstance> reader = new ConcurrentHashMap<String, DBInstance>();
	private ConcurrentHashMap paras = new ConcurrentHashMap() ;
	
	private static boolean isFulled = false ;

	private static final RelationData INSTANCE = new RelationData();

	private RelationData() {
	}
	
	public void updateCardInfo(ConcurrentHashMap<String,DBInstance> map)
	{
		this.cardInfo  = map ; 
	}
	
	public void updateTrigger(ConcurrentHashMap<String,DBInstance> map)
	{
		this.trigger = map  ;
	}
	
	public void updateReader(ConcurrentHashMap<String,DBInstance> map)
	{
		this.reader = map  ;
	}
	
	public void updateParas(ConcurrentHashMap map)
	{
		this.paras = map ;
	}
	

	public static RelationData getInstance() {
		return INSTANCE;
	}
	
	public  Set<Entry<String ,DBInstance>> getCardInfoEntry()
	{
		
		return cardInfo.entrySet();   
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
	
	public DBInstance getReaderById(String key)
	{
		return reader.get(key) ;
	}
	
	public void setReaderInfor(String readerId ,DBInstance db )
	{
		reader.put(readerId, db) ;
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
			
			list = dbOperator.query("select * from rfid_reader", null) ;
			for( DBInstance db: list)
			{
				reader.put(db.getValue("readerid").toString(), db) ;
			}
			this.isFulled = true ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("���ݳ�ʼ��ʧ��" + e);
		}
	}
	

}
