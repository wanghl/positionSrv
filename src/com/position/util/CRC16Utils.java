package com.position.util;

public class CRC16Utils {
	// CRC16 在线生成器   http://www.lammertbies.nl/comm/info/crc-calculation.html
	public static synchronized String getCrc(byte[] bytes) {
		int crc = 0x00; // initial value
		int polynomial = 0x1021;
		for (int index = 0; index < bytes.length; index++) {
			byte b = bytes[index];
			for (int i = 0; i < 8; i++) {
				boolean bit = ((b >> (7 - i) & 1) == 1);
				boolean c15 = ((crc >> 15 & 1) == 1);
				crc <<= 1;
				if (c15 ^ bit)
					crc ^= polynomial;
			}
		}
		crc &= 0xffff;
		System.out.println("======" +  crc ) ;
		return Integer.toHexString( crc ) ;
	}
	
	public static void main(String[] argvs)
	{
		
		String l = "5473315e" ;
		byte[] b = new byte[8] ;
		b[0] = (byte) 0x40 ;
		b[1] = (byte) 0x06 ;
		b[2] = (byte) 0x00 ;
		b[3] = (byte) 0x37 ;
		b[4] = (byte) Integer.parseInt(l.substring(0,2) , 16 )  ;
		b[5] = (byte)Integer.parseInt(l.substring(2,4) , 16 ) ;
		b[6] = (byte) Integer.parseInt(l.substring(4,6) , 16 ) ; 
		b[7] = (byte) Integer.parseInt(l.substring(6,8) , 16 ) ;
		
		System.out.println(  CRC16Utils.getCrc(b))  ;
	}

}
