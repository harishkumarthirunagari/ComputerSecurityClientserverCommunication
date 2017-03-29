

import java.io.*;
import java.net.*;
import java.security.*;

import javax.crypto.*;

public class X509server {

	public static void main(String[] args) throws Exception 
	{   
		ObjectInputStream inputStream;
		KeyStore storeFile;
		PrivateKey pk;
		String aliasname="keyAlias";//Certifcate infromation 
        char[] password="123456".toCharArray();//password for keystore file
		int port = 7999;
		ServerSocket s = new ServerSocket(port);//socket infromation connection to socket
		System.out.println("Waiting For Clients");
		Socket s1 = s.accept();
		System.out.println("Client Accepted");
		inputStream= new ObjectInputStream(s1.getInputStream());//creating input stream over socket
		storeFile = KeyStore.getInstance("jks"); //getting key store instance
        storeFile.load(new FileInputStream("./keystore.jks"), password);// reading data from ketstore file
        pk = (PrivateKey)storeFile.getKey(aliasname, password);//load private key from keystore to decrypt the data
       
         //creating cipher context for decryption
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");//creating context for decryption
        byte[] cipher1 = (byte[]) inputStream.readObject();
		cipher.init(Cipher.DECRYPT_MODE, pk);//initalization of cipher for decryption
		byte[] plaintext = cipher.doFinal(cipher1);
		System.out.println("The plaintext is: " + new String(plaintext));
		s.close();
	}

}
