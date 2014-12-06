package com.position.message.sender;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineDecoder;

public class PositionProtocolCodeFactory implements ProtocolCodecFactory{

	public ProtocolDecoder getDecoder(IoSession iosession) throws Exception {
		// TODO Auto-generated method stub
		return  new TextLineDecoder(); 
	}

	public ProtocolEncoder getEncoder(IoSession iosession) throws Exception {
		// TODO Auto-generated method stub
		return new PositionProtocolCodeEncoder();
	}

}
