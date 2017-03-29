import java.io.*;
import java.net.*;
import java.security.*;
import javax.crypto.*;

public class CipherServer
{
	public static void main(String[] args) throws Exception 
	{
		//creating variables 
		System.out.println("Waiting For Client");
		int port = 7999;
		ServerSocket server = new ServerSocket(port);
		Socket s = server.accept();
		System.out.println("Connection accepted to"+s);
		ObjectInputStream client;
		Key key;
		Cipher c1;
		CipherInputStream cipherIn;
		client = new ObjectInputStream(s.getInputStream());//Creating input stream with client on socket 
		key = (Key) client.readObject(); 
		
		c1 = Cipher.getInstance("DES/ECB/PKCS5Padding");
		c1.init(Cipher.DECRYPT_MODE, key);
		cipherIn= new CipherInputStream(s.getInputStream(),c1);
		int x = cipherIn.read();//reading cipher text from input stream 
		

		StringBuilder plaintext = new StringBuilder();
		// construct a string
		System.out.println("Reading Decrypted Cipher text");
		while( x != -1)
	     {	
			plaintext.append((char) x);//converting byte data to char data and appending to plain text
	    	x=cipherIn.read();
	    
	     }
	    
		
		System.out.println("The plaintext After Decrypting is:"+ plaintext.toString());
		
		client.close();
		cipherIn.close();
		server.close();
		

	}
}