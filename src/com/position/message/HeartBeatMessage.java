package com.position.message;

/**
 * @author whlzcy
 * 
 * 阅读器心跳报文解析器。
 *
 */
public class HeartBeatMessage extends ReaderMessage implements IReaderMessage {


	@Override
	public void parse(byte[] buffer) {
		// TODO Auto-generated method stub
		setCommandCode((byte) 5); // command code
		
		byte[] readerId = new byte[2] ;
		readerId[0] = buffer[14] ;
		readerId[1] = buffer[15] ;
		
		setReaderAddress(readerId);
		
		
	}
	
	public static HeartBeatMessage createNewInstance(byte[] buffer)
	{
		HeartBeatMessage message = new HeartBeatMessage() ;
		message.parse(buffer);
		return message ;
	}
	
	private HeartBeatMessage() {} ;
	

}
