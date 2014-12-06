package com.position.reader.server;

import java.nio.ByteBuffer;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;


public class ReaderDeCoder extends CumulativeProtocolDecoder {
	
	@Override
	protected boolean doDecode(IoSession arg0, IoBuffer arg1, ProtocolDecoderOutput arg2) throws Exception {

		ByteBuffer readerBuffer = ByteBuffer.allocate(arg1.limit()) ;
		
		while( arg1.hasRemaining())
		{
			 readerBuffer.put((byte) arg1.get() ) ;
		

		}
		byte[] b = readerBuffer.array() ;
		StringBuffer sb = new StringBuffer() ;
		for ( int i=0 ;i < b.length; i++)
		{
			int v = b[i] & 0xff;
			String hv =Integer.toHexString(v) ;
			if (hv.length() < 2)
			{
				sb.append(0);
			}
			sb.append( hv ) ;
		}
		// 校验收到的消息长度，处理断包
		if( Integer.toHexString(b[0] & 0xff).equals("ff"))
		{
			if ( ( b[13] &0xff) == 5)
			{
				System.out.println( sb.toString() ) ;
			}
			int length = getMessageLength(b) ;
			if ( (length + 10 ) == b.length ) 
			{
				arg2.write(b);
			}
			else if ( (length + 10) > b.length)
			{
				System.out.println("断包 ： "+ sb.toString()) ;
				IoBuffer ioBuffer = (IoBuffer) arg0.getAttribute("messageBuffer") ;
				ioBuffer.put(b) ;
				int afterLength = getMessageLength(ioBuffer.array());
				if ( ( afterLength + 10 ) == ioBuffer.array().length )
				{
					arg2.write(ioBuffer.array());
					ioBuffer.clear() ;
				}
				arg0.setAttribute("messageBuffer" , ioBuffer) ;
				return false ;
			}
		}
		else
		{
			System.out.println("断包 ： +++++ "+ sb.toString()) ;
			IoBuffer ioBuffer = (IoBuffer) arg0.getAttribute("messageBuffer") ;
			ioBuffer.put(b);
			arg2.write(ioBuffer.array());
		}
		
		return true ;
	}
	
	private int getMessageLength(byte[] b)
	{
		int low = b[8] &  0xff ;
		int height = b[9] &  0xff ;
		return low + height ; 
		
	}
	

}
