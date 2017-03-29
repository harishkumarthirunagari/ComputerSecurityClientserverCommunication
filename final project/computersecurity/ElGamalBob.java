import java.io.*;
import java.net.*;
//import java.security.*;
import java.math.BigInteger;

public class ElGamalBob
{
	private static boolean verifySignature(	BigInteger y, BigInteger g, BigInteger p, BigInteger a, BigInteger b, String message)
	{
		BigInteger result1 = (y.modPow(a, p).multiply(a.modPow(b, p))).mod(p);//.
		BigInteger m = new BigInteger(message.getBytes());
		BigInteger result2 = g.modPow(m, p);
		return result1.equals(result2);
		
	}

	public static void main(String[] args) throws Exception 
	{
		int port = 7999;
		ServerSocket s = new ServerSocket(port);
		System.out.println("Waiting for Alice to Connect");
		Socket client = s.accept();
		System.out.println("Connection Esatblished With Bob");
		ObjectInputStream oinput;
		oinput = new ObjectInputStream(client.getInputStream());
        BigInteger y,g,p,a,b;
        boolean verify;
        String message;
		// read public key
		y = (BigInteger)oinput.readObject();
		 g = (BigInteger)oinput.readObject();
		 p = (BigInteger)oinput.readObject();

		// read message from input stream
		message = (String)oinput.readObject();

		// read signature
		 a = (BigInteger)oinput.readObject();
		 b = (BigInteger)oinput.readObject();

		 verify = verifySignature(y, g, p, a, b, message);

		System.out.println("Message revied"+message);

		if (verify == true)
			System.out.println("Signature verified.");
		else
			System.out.println("Signature verification failed.");

		s.close();
	}
}