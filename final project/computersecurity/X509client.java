



import java.io.*;
//import java.math.BigInteger;
import java.net.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Scanner;

import javax.crypto.*;

public class X509client {

	public static void main(String[] args) throws Exception 
	{
		ObjectOutputStream outputStream;//creating output stream to send data to server
		InputStream inputStream;//input stream to read data from file
		String host = "cse01.cse.unt.edu";//setting socket infromation
		int port = 7999;
		System.out.println("Connecting to Server"+host);
		Socket s = new Socket(host, port);//connecting to host 
	    System.out.println("Connection Established");
		outputStream = new ObjectOutputStream(s.getOutputStream());//geting putput stream over socket
        inputStream= new FileInputStream("C:/Users/mangw/server.cer");//getting input stream over socket
        CertificateFactory cf = CertificateFactory.getInstance("X.509");// creating instance for certificate factory
        X509Certificate cert = (X509Certificate)cf.generateCertificate(inputStream);// reading certifcation content from .cer file using inputstream
        inputStream.close();
        
        try
        {
    	   cert.checkValidity();//Checking if the signature is valid
    	   System.out.println("The certificate is valid.");
        } catch (Exception e){
    	   System.out.println(e);   
        }
        
        System.out.println("Enter String:");
        Scanner in = new Scanner(System.in);
        String message= in.nextLine();
        //Get the public key from certifcate
        RSAPublicKey puk = (RSAPublicKey) cert.getPublicKey();//get the public key from certifcation file to encrypt the data
       
        
        //Encrypt message using public by creating cipher context
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");//creating the context for cipher
        cipher.init(Cipher.ENCRYPT_MODE, puk);
        byte[] cipherText = cipher.doFinal(message.getBytes());
        System.out.println("The ciphertext is: " + cipherText.toString());
        outputStream.writeObject(cipherText);
        outputStream.flush();
        outputStream.close();//closing all objects
	    s.close();
	    in.close();
	    inputStream.close();
	}



}
