import java.io.*;
import java.net.*;
import java.security.*;
import javax.crypto.*;

public class CipherClient
{
	public static void main(String[] args) throws Exception 
	{
		System.out.println("Client Console");
		String message = "The quick brown fox jumps over the lazy dog.";
		String host = "cse01.cse.unt.edu";
		int port = 7999;
		SecureRandom random;//creating a secure random random
		Key key;//creating key object
		KeyGenerator k;
		 Cipher cipher;
		 CipherOutputStream ciphersend;
		 //creating new fileobject for key.txt to store key in key.txt
		File KeyFile = new File("key.txt");
		Socket s = new Socket(host, port);
		System.out.println("Connection Established with"+host);
        k = KeyGenerator.getInstance("DES");//Creating instance for DES
		random = new SecureRandom();
		ObjectOutputStream client,filestream;
		k.init(random);
		System.out.println("Generating DES KEY");
		key = k.generateKey();// generate a DES KEY
		System.out.println("Key Generated is"+key);//printing key
		filestream = new ObjectOutputStream(new FileOutputStream(KeyFile));//writing data to File key.txt
		filestream.writeObject(key);		//store file in a key and send it to output stream
		client = new ObjectOutputStream(s.getOutputStream());//creating output stream btwn client and server
		client.writeObject(key);//sending data to server
	    client.flush();
	    //createing instance for cipher text
	    System.out.println("Creating Cipher Text");
	    cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");//creating cipher context
	    ciphersend = new CipherOutputStream(s.getOutputStream(), cipher);//creating stream for cipher
		cipher.init(Cipher.ENCRYPT_MODE, key);
		//This sets the cipher object to Encrypt mode with the specified key k (a Key object)
		//hint
	    byte input[] = message.getBytes();
	    System.out.println("Plain Text Before Sending To Server:" + message);
	    
	    ciphersend.write(input);//sending message bytes through cipher bytes
	    filestream.close();
	    ciphersend.close();
	    s.close();
	    
	
	}
}