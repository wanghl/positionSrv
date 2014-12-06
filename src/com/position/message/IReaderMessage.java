package com.position.message;

public interface IReaderMessage {
	
	public static final int MESSAGE_TYPE_TIME = 6 ; // 时间校验报文
	public static final int MESSAGE_TYPE_HEARTBEAT = 5 ; //心跳报文
	public static final int MESSAGE_TYPE_IDMESSAGE = 4 ; //上传标签信息报文
	
	public void parse(byte[] buffer) ;

}
