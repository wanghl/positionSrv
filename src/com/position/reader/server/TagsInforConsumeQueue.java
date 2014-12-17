package com.position.reader.server;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;


public class TagsInforConsumeQueue {
	
	private ConcurrentLinkedQueue<Map<String,Object>> queue = new ConcurrentLinkedQueue<Map<String,Object>>() ;
	
	private final static TagsInforConsumeQueue INSTANCE = new TagsInforConsumeQueue() ;
	
	private TagsInforConsumeQueue(){
		
	}
	
	public boolean isEmpty()
	{
		return queue.isEmpty();
	}
	
	public static TagsInforConsumeQueue getInstance()
	{
		
		return INSTANCE ;
	}
	
	public Map<String,Object> takeOne()
	{
		return queue.poll() ;
	}
	
	public void addOne(Map<String ,Object> map)
	{
		queue.add(map);
	}
	
	public void addAll(Collection<Map<String,Object>> list){
		
		queue.addAll(list); 
	}
	
	

}
