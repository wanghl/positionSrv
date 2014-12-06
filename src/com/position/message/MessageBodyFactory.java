package com.position.message;

public class MessageBodyFactory {

	public ReaderMessage getMessageBody(byte[] buffer) {
		try {
			int cmd = buffer[13] & 0xff;
			if (cmd == 6) {
				return TimeMessage.createNewInstance(buffer);
			} else if (cmd == 5) {
				return HeartBeatMessage.createNewInstance(buffer);
			} else if (cmd == 4) {
				return TagMessage.createNewInstance(buffer);
			} else {
				return null;

			}
		} catch (Exception e) {

		}
		return null ;
	}

}
