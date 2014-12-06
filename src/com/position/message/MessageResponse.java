package com.position.message;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.position.util.CRC16Utils;

public class MessageResponse {

	private static final Logger log = Logger.getLogger(MessageResponse.class);

	private MessageResponse() {

	};

	public static MessageResponse getInstance() {
		return new MessageResponse();
	}

	public void sendIDMessageResponse(IoSession session, ReaderMessage tagMessage) {
		try{
		ByteBuffer byteBuffer = ByteBuffer.allocate(17);
		byteBuffer.put((byte) 0xff);
		byteBuffer.put((byte) 0xff);
		byteBuffer.put((byte) 0xff);
		byteBuffer.put((byte) 0xff);

		byteBuffer.put((byte) 0xDB);
		byteBuffer.put((byte) 0x97);

		byteBuffer.put((byte) 0xC1);
		byteBuffer.put((byte) 0x02);
		byteBuffer.put((byte) 0x00);
		byteBuffer.put((byte) 0x07);

		// make check code

		byte[] crc = new byte[5];

		crc[0] = 0x40;
		crc[1] = 0x04;
		crc[2] = tagMessage.getReaderAddress()[0];
		crc[3] = tagMessage.getReaderAddress()[1];
		crc[4] = tagMessage.getTagCount();

		String checkCode = CRC16Utils.getCrc(crc);
		System.out.println(checkCode);
		if ( checkCode.length() == 3)
		{
			byteBuffer.put((byte) Integer.parseInt("0" + checkCode.charAt(0), 16));
			byteBuffer.put((byte) Integer.parseInt(checkCode.substring(1, 3), 16));
			
		}
		else
		{
			byteBuffer.put((byte) Integer.parseInt(checkCode.substring(0, 2), 16));
			byteBuffer.put((byte) Integer.parseInt(checkCode.substring(2, 4), 16));
		}

		byteBuffer.put((byte) 0x40);
		byteBuffer.put((byte) 0x04);
		byteBuffer.put(tagMessage.getReaderAddress()[0]);
		byteBuffer.put(tagMessage.getReaderAddress()[1]);
		byteBuffer.put(tagMessage.getTagCount());

		byte[] readerBuffer = byteBuffer.array();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < readerBuffer.length; i++) {
			int v = readerBuffer[i] & 0xff;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				sb.append(0);
			}
			sb.append(hv);
		}

		log.info("卡信息应答(Server --> Reader) : " + sb.toString());

		session.write(byteBuffer.array());
		}catch(Exception e)
		{
			e.printStackTrace(); 
			log.error("组卡信息应答报文出错，放弃操作。");
		}

	}

	public void sendTimeCheckResponse(IoSession session, ReaderMessage message) throws ParseException {
		try {
			ByteBuffer byteBuffer = ByteBuffer.allocate(20);
			byteBuffer.put((byte) 0xff);
			byteBuffer.put((byte) 0xff);
			byteBuffer.put((byte) 0xff);
			byteBuffer.put((byte) 0xff);

			byteBuffer.put((byte) 0x0A);
			byteBuffer.put((byte) 0x3A);

			byteBuffer.put((byte) 0xC1);
			byteBuffer.put((byte) 0x02);
			byteBuffer.put((byte) 0x00);
			byteBuffer.put((byte) 0x0A);

			/*
			 * byteBuffer.put((byte) 0x95); byteBuffer.put((byte) 0xB8);
			 * byteBuffer.put((byte) 0x40); byteBuffer.put((byte) 0x06);
			 * byteBuffer.put((byte) 0x00); byteBuffer.put((byte) 0x37);
			 * byteBuffer.put((byte) 0x54); byteBuffer.put((byte) 0x73);
			 * byteBuffer.put((byte) 0x2D); byteBuffer.put((byte) 0xB4);
			 */

			String unixHexTime = Long.toHexString(getUnixTime());
			// 用 帧选项 命令代码 和整个时间生成一个校验码 。。。。这个协议尼玛谁弄的？SB搞这么复杂干嘛？？

			byte[] b = new byte[8];
			b[0] = (byte) 0x40;
			b[1] = (byte) 0x06;
			b[2] = message.getReaderAddress()[0]; // (byte) 0x00 ;
			b[3] = message.getReaderAddress()[1]; // (byte) 0x37 ;
			b[4] = (byte) Integer.parseInt(unixHexTime.substring(0, 2), 16);
			b[5] = (byte) Integer.parseInt(unixHexTime.substring(2, 4), 16);
			b[6] = (byte) Integer.parseInt(unixHexTime.substring(4, 6), 16);
			b[7] = (byte) Integer.parseInt(unixHexTime.substring(6, 8), 16);

			String checkCode = CRC16Utils.getCrc(b);
			byteBuffer.put((byte) Integer.parseInt(checkCode.substring(0, 2), 16));
			byteBuffer.put((byte) Integer.parseInt(checkCode.substring(2, 4), 16));

			byteBuffer.put((byte) 0x40);
			byteBuffer.put((byte) 0x06);
			byteBuffer.put(message.getReaderAddress()[0]);
			byteBuffer.put(message.getReaderAddress()[1]);

			byteBuffer.put((byte) Integer.parseInt(unixHexTime.substring(0, 2), 16));
			byteBuffer.put((byte) Integer.parseInt(unixHexTime.substring(2, 4), 16));
			byteBuffer.put((byte) Integer.parseInt(unixHexTime.substring(4, 6), 16));
			byteBuffer.put((byte) Integer.parseInt(unixHexTime.substring(6, 8), 16));
			byte[] readerBuffer = byteBuffer.array();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < readerBuffer.length; i++) {
				int v = readerBuffer[i] & 0xff;
				String hv = Integer.toHexString(v);
				if (hv.length() < 2) {
					sb.append(0);
				}
				sb.append(hv);
			}

			// System.out.println( "====================" + sb.toString()) ;*/
			// byteBuffer.flip() ;
			log.info("校时报文回复(Server --> Reader) :" + sb.toString());
			session.write(byteBuffer.array());

		} catch (Exception e) {
			log.error("组校时应答报文出错，操作放弃。");
		}

	}

	private long getUnixTime() throws ParseException {
		Timestamp appointTime = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = df.parse(String.valueOf(appointTime));
		return date.getTime() / 1000;

	}

	public static void main(String[] argvs)

	{
		System.out.println("71f".substring(1,3));
	}

}
