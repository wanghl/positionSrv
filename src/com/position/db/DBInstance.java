package com.position.db;

import java.util.HashMap;
import java.util.Map;

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

}
