package com.position.util;

public class BitConverter {
	
	 public static int byteArrayToInt(byte[] bytes) {
         int value= 0;
         //�ɸ�λ����λ
         for (int i = 0; i < 4; i++) {
             int shift= (4 - 1 - i) * 8;
             value +=(bytes[i] & 0x000000FF) << shift;//����λ��
         }
         return value;
   }
	 
	 public static byte[] intToByteArray(int i) {   
         byte[] result = new byte[4];   
         //�ɸ�λ����λ
         result[0] = (byte)((i >> 24) & 0xFF);
         result[1] = (byte)((i >> 16) & 0xFF);
         result[2] = (byte)((i >> 8) & 0xFF); 
         result[3] = (byte)(i & 0xFF);
         return result;
       }
	 
	 public static void main(String[] argvs)
	 {
		 System.out.println( intToByteArray(0x119e)) ;
	 }

}
