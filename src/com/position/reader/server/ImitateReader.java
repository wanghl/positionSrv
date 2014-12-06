package com.position.reader.server;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.mina.core.buffer.IoBuffer;

public class ImitateReader {
	private final static String MESSAGE = "ffffffff83d3c10200cf7338c00400370a1f0000c66c0e324f324fffffffff030354770d801f0000c64e0b324f324fffffffff030354770d811f0000c6660c324f324fffffffff030454770d811f0000c64710324f324fffffffff030454770d821f0000c67911324f324fffffffff030354770d811f0000c61612324f324fffffffff030354770d801f0000c65706324f324fffffffff030454770d821f0000c65308324f324fffffffff030454770d811f0000c61515324f324fffffffff030354770d811f0000c65810324f324fffffffff030454770d81";
	static IoBuffer buffer = IoBuffer.allocate(217);

	static {

		buffer.setAutoExpand(false);
		for( int i =0 ; i < MESSAGE.length() ; i++)
		{
			if ( i *2 + 2 <= MESSAGE.length())
			{
				System.out.println(MESSAGE.substring(i *2 , i*2 +2)) ;
				buffer.put(  (byte)Integer.parseInt(MESSAGE.substring(i *2 , i*2 +2),16));
				//buffer.putObject(arg0)
			}
		
		}
	}
	
	public  static void main(String[] argvs)
	{
		try {
			Socket socket = new Socket("127.0.0.1",8807) ;
			while ( true )
			{
				Thread.sleep(1000);
				
				socket.getOutputStream().write(buffer.array());
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
