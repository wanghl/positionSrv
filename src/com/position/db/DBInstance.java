package com.position.db;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class DBInstance {
	
	
	
	public Map<String ,Object> map = new HashMap<String ,Object>() ;
	
	public void putValue(String key ,Object object)
	{
		map.put(key, object) ;
	}
	
	public Object getValue(String key)
	{
		return map.get(key) ;
	}
	
	public String buildInsertSql(String tableName)
	{
		
		StringBuffer sql = new StringBuffer() ;
		StringBuffer values = new StringBuffer() ;
		
	     sql .append( "insert into " + tableName + "(" ) ;
	
		values.append(" values(") ;
		map.keySet().toString() ;
		
		for( Entry<String ,Object> entry : map.entrySet())
		{
			sql.append( entry.getKey()).append(",");
			
			values.append( "?").append(",");
		}
		
		String sqlField = sql.substring(0, sql.length() -1) + ")" ;
		String sqlValues = values.substring(0,values.length() -1) + ")" ;
		
		return sqlField + sqlValues ;
	}
	
	
}
