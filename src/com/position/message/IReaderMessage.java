package com.position.message;

public interface IReaderMessage {
	
	public static final int MESSAGE_TYPE_TIME = 6 ; // ʱ��У�鱨��
	public static final int MESSAGE_TYPE_HEARTBEAT = 5 ; //��������
	public static final int MESSAGE_TYPE_IDMESSAGE = 4 ; //�ϴ���ǩ��Ϣ����
	
	public void parse(byte[] buffer) ;

}
