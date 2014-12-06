package com.position.message;

import java.nio.ByteBuffer;


public class TimeMessage extends ReaderMessage implements IReaderMessage {
	
	@Override
	public void parse(byte[] buffer) {
		// TODO Auto-generated method stub
		
		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer) ;
		byte[] head = new byte[10] ;
		byte[] body = new byte[6] ;
		
		byteBuffer.get(head) ;
		byteBuffer.get(body) ;
		
		setFrameType(body[3]) ; //Ö¡Ñ¡Ïî
		setCommandCode((byte)6) ;  // ÃüÁî´úÂë
		
		byte[] address = new byte[2] ;
		address[0] = body[4] ;
		address[1] = body[5] ;
		setReaderAddress(address) ;
		
		
		
		
	}
	
	public static TimeMessage createNewInstance(byte[] buffer)
	{
		TimeMessage message = new TimeMessage() ;
		message.parse(buffer);
		return message ;
	}
	
	private TimeMessage() {super();} ;
	

}
