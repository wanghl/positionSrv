package com.position.reader.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CardPool {
	
	private static final CardPool INSTANCE = new CardPool() ;
	private ConcurrentHashMap<Object ,Map> map  = new ConcurrentHashMap<Object ,Map>() ;
	
	public static CardPool getInstance()
	{
		return INSTANCE ;
	}
	
	private CardPool(){};
	
	public void put(String key ,Map value)
	{
		map.put(key, value) ;
	}
	
	public Map get(Object key)
	{
		return map.get(key) ;
	}
	
	public ConcurrentHashMap<Object ,Map> getTags()
	{
		return map ;
	}

}
