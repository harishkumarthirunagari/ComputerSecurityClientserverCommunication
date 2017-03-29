



import java.io.*;
import java.net.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import javax.crypto.Cipher;
public class RSAbob {

	public static void main(String[] args) throws Exception {
        //creating variable,streams and socket initalizations
	    int port = 7999;
		ServerSocket s = new ServerSocket(port);
		System.out.println("Waiting For Clients");
		Socket client = s.accept();
		System.out.println("Connection Esablished with ALice");
		ObjectInputStream Alicepuk;
		ObjectOutputStream Bobpuk;
		KeyPairGenerator genkeypair;
		KeyPair keypair;
		RSAPublicKey Bpuk;
		RSAPrivateKey Bpk;
		RSAPublicKey Apuk;
		
        //creating ouput and input streams which are connected to input and outputstreams
		Alicepuk = new ObjectInputStream(client.getInputStream());
		Bobpuk = new ObjectOutputStream(client.getOutputStream());
		
		System.out.println("Generate Bob's key pairs private key and public key");
		genkeypair = KeyPairGenerator.getInstance("RSA");//instantiating RSA algorithm key pair
	    genkeypair.initialize(1024, new SecureRandom()); 
	    keypair = genkeypair.genKeyPair();//generating keypair
	    Bpuk= (RSAPublicKey) keypair.getPublic();//BOB's public key
	    Bpk = (RSAPrivateKey)keypair.getPrivate();//Bob's private key
	    
	   
	    Bobpuk.writeObject(Bpuk); //sending Bob's public key to Alice so that Alice can encypt using this key
	    
	    Apuk = (RSAPublicKey) Alicepuk.readObject(); //Reading Alice public Key
	    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");//creating cipher context
	    
	    int choice = Alicepuk.readInt();//depending on choice confidentiality or intergrityofconfidentialy
	    
		    switch(choice)
			{
			  case 1:
				
				  System.out.println("Confidentialty");
				  
				  System.out.println("decrypting message by Bob's private key");//decrypting message by bobs public key
				  byte[] in1 = (byte[]) Alicepuk.readObject();
				  cipher.init(Cipher.DECRYPT_MODE,Bpk);
   			      byte[] plainttext = cipher.doFinal(in1);
				  System.out.println("The plaintext is: " + new String(plainttext));
				  break;
				    
				  
			  case 2:
				  
				  System.out.println("Integrity/Authentication");
			
				  System.out.println("decrypting message by Alice public key");//decrypting message by ALice Public Key
				  byte[] int2 = (byte[]) Alicepuk.readObject();
				  cipher.init(Cipher.DECRYPT_MODE, Apuk);
   			      byte[] plaintext = cipher.doFinal(int2);
				  System.out.println("The plaintext is: " + new String(plaintext));
				  break;
			}
		s.close(); 
		System.out.println("all Streams closed");
		}
		
	}
 


