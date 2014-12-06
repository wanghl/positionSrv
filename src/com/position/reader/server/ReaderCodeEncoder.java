package com.position.reader.server;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class ReaderCodeEncoder  implements ProtocolEncoder {
	Logger log = Logger.getLogger(ReaderCodeEncoder.class);

	@Override
	public void dispose(IoSession paramIoSession) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void encode(IoSession paramIoSession, Object paramObject, ProtocolEncoderOutput paramProtocolEncoderOutput) throws Exception {
		// TODO Auto-generated method stub
		 byte[] b = (byte[]) paramObject ;
		 IoBuffer buf = IoBuffer.allocate(b.length);
		 buf.put(b);
		 buf.flip() ;
		paramProtocolEncoderOutput.write(buf);
		
	}

}
