package com.position.message;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.xml.crypto.Data;

import org.apache.log4j.Logger;

import com.position.db.DBInstance;
import com.position.reader.server.TagsInforConsumeQueue;

public class TagMessage extends ReaderMessage implements IReaderMessage {

	private static final Logger log = Logger.getLogger(TagMessage.class);

	@Override
	public void parse(byte[] buffer) {
		// TODO Auto-generated method stub
		log.info("============= 开始解析卡数据 ================");
		LinkedList<Map<String,Object>> linkedList = new LinkedList<Map<String,Object>>() ;
		Map<String ,Object> fristOne = new HashMap<String,Object>() ;
		fristOne.put("updatetime", new Date()) ;
		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
		byte[] subTag = new byte[20];
		// read message head
		byte[] head = new byte[10];
		byteBuffer.get(head);
		// get message body length

		int low = head[8] & 0xff;
		int height = head[9] & 0xff;

		int messageLength = low + height;
		log.info("报文长度 ： " + messageLength);
		byte[] messageBody = new byte[17];

		// read message body

		byteBuffer.get(messageBody);

		setFrameType(messageBody[2]); // frame
		setCommandCode((byte) 4); // command code
		// reader address
		byte[] address = new byte[2];
		address[0] = messageBody[4];
		address[1] = messageBody[5];
		setReaderAddress(address);
		
		fristOne.put("readerId", getReaderId())  ;
		
		// tag count
		setTagCount(messageBody[6]);
		int tags = messageBody[6] & 0xff;
		log.info("包含 " + tags + " 条卡信息");
		// tag id
		tagId = Integer.toHexString(messageBody[8] & 0xff) + Integer.toHexString(messageBody[9] & 0xff) + Integer.toHexString(messageBody[10] & 0xff) + Integer.toHexString(messageBody[11] & 0xff);
		tagId = Integer.parseInt(tagId, 16) + "";
		setTagId(tagId);
		
		fristOne.put("tagId", tagId) ;
		
		log.info("卡号 ：" + tagId);
		
		log.info("读头ID：" +  (address[1] & 0xff ));
		
		
		// trigger id
		String triggerid = Integer.toHexString(messageBody[13] & 0xff) + Integer.toHexString(messageBody[14] & 0xff);
		
		setNewTrigger(Integer.parseInt(triggerid, 16));
		log.info("新触发器ID：" + getNewTrigger());
		triggerid = Integer.toHexString(messageBody[15] & 0xff) + Integer.toHexString(messageBody[16] & 0xff);
		setOriTrigger(Integer.parseInt(triggerid, 16));
		log.info("旧触发器ID：" + getOriTrigger());
		
		fristOne.put("newTrigger", getNewTrigger());
		fristOne.put("oldTrigger", getOriTrigger()) ;
		
		putTag(tagId ,Integer.parseInt(triggerid, 16)) ;
		// ByteBuffer msgByteBuffer = ByteBuffer.wrap(messageBody) ;
		// msgByteBuffer.position(27) ;
		// msgByteBuffer.compact() ;
		// byteBuffer.compact() ;
		Map<String ,Object > subOne = null;
		for (int i = 0; i < tags - 1; i++) {
			try {
				byteBuffer.get(subTag);
				tagId = Integer.toHexString(subTag[11] & 0xff) + Integer.toHexString(subTag[12] & 0xff) + Integer.toHexString(subTag[13] & 0xff) + Integer.toHexString(subTag[14] & 0xff);
				tagId = Integer.parseInt(tagId, 16) + "";
				log.info("|--- 子卡号:" + tagId);
				
				triggerid = Integer.toHexString(subTag[16] & 0xff) + Integer.toHexString(subTag[17] & 0xff);

				setNewTrigger(Integer.parseInt(triggerid, 16));
				log.info("|--- 子卡新触发器ID：" + getNewTrigger());
				triggerid = Integer.toHexString(subTag[18] & 0xff) + Integer.toHexString(subTag[19] & 0xff);
				setOriTrigger(Integer.parseInt(triggerid, 16));
				log.info("|--- 子卡旧触发器ID：" + getOriTrigger());
				
				putTag(tagId ,Integer.parseInt(triggerid, 16)) ;
				
				subOne = new HashMap<String ,Object>() ;
				subOne.put("updatetime", new Date()) ;
				subOne.put("readerId", getReaderId()) ;
				subOne.put("tagId", tagId); 
				subOne.put("newTrigger", getNewTrigger()) ;
				subOne.put("oldTrigger", getOriTrigger()) ;
				
				byte state = subTag[4] ;
				subOne.put("batteryValue", (byte)((state >> 7) & 0x1) ) ;
				subOne.put("scanTimes", subTag[5] & 0xff) ;
				linkedList.addLast(subOne);
				
				
			} catch (Exception e) {
				continue;
			}
		}
		byteBuffer.position(byteBuffer.position() + 4) ;
		byte state = byteBuffer.get() ; // 状态字节 ；bit7 位表示电池电量
		
		
		setBatteryValue( (byte)((state >> 7) & 0x1) ) ;
		
		fristOne.put("batteryValue", getBatteryValue()) ;
		fristOne.put("scanTimes", byteBuffer.get() & 0xff) ;
		
		linkedList.addFirst(fristOne);
		
		TagsInforConsumeQueue.getInstance().addAll(linkedList);
		
		
	}
	
	

	public static TagMessage createNewInstance(byte[] buffer) {
		TagMessage message = new TagMessage();
		message.parse(buffer);
		return message;
	}

	private TagMessage() {
	};
	
	
	public static void main(String[] argvs)
	{
		Byte b = ( 0x03 & 0xff) ;
		
		 byte[] array = new byte[8];  
	        for (int i = 7; i >= 0; i--) {  
	            array[i] = (byte)(b & 1);  
	            b = (byte) (b >> 1);  
	        }  
	        
	}
	

}
