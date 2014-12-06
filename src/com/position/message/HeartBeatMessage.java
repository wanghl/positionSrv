package com.position.message;

public class HeartBeatMessage extends ReaderMessage implements IReaderMessage {


	@Override
	public void parse(byte[] buffer) {
		// TODO Auto-generated method stub
		setCommandCode((byte) 5); // command code
		
	}
	
	public static HeartBeatMessage createNewInstance(byte[] buffer)
	{
		HeartBeatMessage message = new HeartBeatMessage() ;
		message.parse(buffer);
		return message ;
	}
	
	private HeartBeatMessage() {} ;
	

}
