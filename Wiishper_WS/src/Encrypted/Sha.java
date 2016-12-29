package Encrypted;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha 
{
	 public static String hash256(String data) throws NoSuchAlgorithmException 
	 {
		 MessageDigest md = MessageDigest.getInstance("SHA-256");
	     md.update(data.getBytes());
	     return bytesToHex(md.digest());
	  }
	  public static String bytesToHex(byte[] bytes) 
	  {
		  StringBuffer result = new StringBuffer();
	      for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
	      return result.toString();
	  }
	  public static String decodeHas256(String planPassword, String hashedPassword)
	  {
		  MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        md.update(planPassword.getBytes());

	        byte byteData[] = md.digest();

	        //convert the byte to hex format method 1
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }

			return hashedPassword;
	  }
}
