

import java.io.*;
//import java.math.BigInteger;
import java.net.*;
import java.security.*;
import java.util.Scanner;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;


import javax.crypto.Cipher;


public class RSAalice {
	
	
	public static void main(String[] args) throws Exception {
		//initalizing variables, streams, and socket innital parameters
		Scanner input;
		Socket s;
		System.out.println("ALICE SIDE");
		System.out.println("Choose one security mechanism");
		System.out.println("Confidentiality           : Press 1");
	    System.out.println("integrity & Authentication: Press 2");
	    
	    input = new Scanner(System.in);
	    String host = "cse01.cse.unt.edu";
		int port = 7999;
		System.out.println("Alice Trying To Connect to Bob");
		s = new Socket(host, port);
		System.out.println("ALice succesfully connected to Bob");
		ObjectOutputStream Bobpuk;//outputstream to bob
		ObjectInputStream Alicepuk;//input stream to alice
		KeyPairGenerator genkeypair;//creating object for genkeypair
		KeyPair keypair;//creating keypair to read public and private key
		RSAPublicKey SAlicepuk;//System ALice public keyS
		RSAPrivateKey SAlicepk;//System Alice private key
		RSAPublicKey Bobpukey;//bobs public key
		
		
		
		Bobpuk = new ObjectOutputStream(s.getOutputStream());//output stream
		Alicepuk = new ObjectInputStream(s.getInputStream());//input stream
		
		
		/* Generate Alice's key pairs (private key and public key)*/
		genkeypair = KeyPairGenerator.getInstance("RSA");//instanting keypairGenerator
		genkeypair.initialize(1024 ,new SecureRandom());
	    keypair = genkeypair.genKeyPair();//key pair initalzation
	    SAlicepuk = (RSAPublicKey) keypair.getPublic();//getting Alice public key
	    SAlicepk = (RSAPrivateKey)keypair.getPrivate();//getting Bobs public Key
	  
	    
	  
	    Bobpuk.writeObject(SAlicepuk);//sending Alice public key
	    
	    //take bobs public key for encrypting
	    Bobpukey = (RSAPublicKey) Alicepuk.readObject();
	    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	    
	    int choice= input.nextInt();//reading user choice
	   
		    switch(choice)
			{
			  case 1:
				

				  System.out.println("Confidentiality");
				  System.out.println("encipher by Bob's public key ");
				  Scanner data = new Scanner(System.in);
				  System.out.println("Enter Message for Bob");
				  String x= data.nextLine();
				  cipher.init(Cipher.ENCRYPT_MODE, Bobpukey);
   			      byte[] cipher1 = cipher.doFinal(x.getBytes());//cipher text context
				  System.out.println("The ciphertext is: " + cipher1);
				  Bobpuk.writeInt(1);
				  Bobpuk.writeObject(cipher1);//writing cipher object to Bob
				  Bobpuk.flush();
				  Bobpuk.close();
				  break;
				  
			  case 2:
				  System.out.println("Integrity/Authentication");
				  System.out.println("encipher by ALice key");
				  Scanner data1 = new Scanner(System.in);
				  System.out.println("Enter Message for Bob");
				  String message= data1.nextLine();
				  cipher.init(Cipher.ENCRYPT_MODE, SAlicepk);
   			      byte[] cipherText2 = cipher.doFinal(message.getBytes());
				  System.out.println("The ciphertext is: " + cipherText2);
				  Bobpuk.writeInt(2);
				  Bobpuk.writeObject(cipherText2);//writing cipher object to bob
				  Bobpuk.flush();
				  Bobpuk.close();
				  break;
				  
			}
		    s.close();
		    input.close();
		    System.out.println("ALL streams closed");
		}

	    
	    
	}

