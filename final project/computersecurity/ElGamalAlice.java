import java.io.*;
import java.net.*; 
import java.security.*;
import java.math.BigInteger;

public class ElGamalAlice
{
	private static BigInteger computeY(BigInteger p, BigInteger g, BigInteger d) 
	{
		BigInteger y = g.modPow(d, p);
		return y;
	}
  
	
	private static BigInteger computeK(BigInteger p)
	{
		SecureRandom rnd = new SecureRandom();
		int numBits = 1024;
		BigInteger k = new BigInteger(numBits, rnd);
		BigInteger p_1 = p.subtract(BigInteger.ONE); 
		
		
		while(!k.gcd(p_1).equals(BigInteger.ONE)) 
		
		{
			k = new BigInteger(numBits, rnd);
		}
		
		return k;

	}
	
	private static BigInteger computeA(BigInteger p, BigInteger g, BigInteger k) //computing A
	{
		BigInteger a = g.modPow(k, p);
		return a;

	}

	private static BigInteger computeB(	String message, BigInteger d, BigInteger a, BigInteger k, BigInteger p)
	{
		BigInteger m = new BigInteger(message.getBytes()); 
		BigInteger pMinusOne = p.subtract(BigInteger.ONE); 
		BigInteger p1 = pMinusOne;
		BigInteger x0 = BigInteger.ZERO;
		BigInteger x1 = BigInteger.ONE;
		BigInteger x2 = k;
		BigInteger temp, temp2, temp3;
		
		while(!x2.equals(BigInteger.ZERO))
		{
			temp = p1.divide(x2);//p1/x2
			temp2 = p1.subtract(x2.multiply(temp));
			p1 = x2;
			x2 = temp2;
			temp3 = x0.subtract(x1.multiply(temp));
			x0 = x1;
			x1 = temp3;
		}
		
		BigInteger b = x0.multiply(m.subtract(d.multiply(a))).mod(pMinusOne);
		
		
		return b;

	}

	public static void main(String[] args) throws Exception 
	{
		SecureRandom Random;
		System.out.println("Connecting to Client");
		String message = "The quick brown fox jumps over the lazy dog.";
		String host = "cse01.cse.unt.edu";
		int port = 7999;
		Socket s = new Socket(host, port);
		ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
		BigInteger y, g, p; 
		BigInteger d; 

		int m = 1024; 
		Random = new SecureRandom(); 

		
		p = new BigInteger(m, 16, Random);
		
		// Create a randomly generated BigInteger of length mStrength-1
		g = new BigInteger(m-1, Random);
		d = new BigInteger(m-1, Random);

		y = computeY(p, g, d);

		//public and private key created

		BigInteger k = computeK(p);
		BigInteger a = computeA(p, g, k);
		BigInteger b = computeB(message, d, a, k, p);

		// send public key
		os.writeObject(y);
		os.writeObject(g);
		os.writeObject(p);
               //Writing message to BOB
		
		os.writeObject(message);
		
		// send signature
		os.writeObject(a);
		os.writeObject(b);
		
		s.close();
	}
}