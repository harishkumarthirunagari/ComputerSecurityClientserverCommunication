import java.io.*;
import java.net.*;
import java.security.*;

public class ProtectedServer
{
	public boolean authenticate(InputStream inStream) throws IOException, NoSuchAlgorithmException 
	{
		DataInputStream in = new DataInputStream(inStream);//creating input stream to server  
		System.out.println("check point 1");
      	String user = in.readUTF();
      	System.out.println("user read"+user);
		String pswd = lookupPassword(user);
		System.out.println("error3");
		long t1 =in.readLong();//reading timestamp1
		System.out.println("timestamp recived"+t1);
		long t2 =in.readLong();//rading timestamp2
		System.out.println("time stamp recived"+t2);
		double rand1 = in.readDouble();//reading random number 1
		System.out.println("random number recived"+rand1);
		double rand2 = in.readDouble();//reading random number 2
		System.out.println("random number recived"+rand2);
		int length;
		length = in.readInt();
		System.out.println("integer length recived"+length);
		byte[] digest = new byte[length];
        System.out.println("error8");
        if (length > 0) 
        in.readFully(digest,0,length);       
		byte[] digest2 = Protection.makeDigest(user, pswd, t1, rand1);//version 1 digest
        byte[] digestcheck =Protection.makeDigest(digest2, t2, rand2);//version 2 digest
        boolean flag;
         flag = MessageDigest.isEqual(digest, digestcheck); //verifiying if both digest are equal
        return flag;
		// IMPLEMENT THIS FUNCTION.
	}

	protected String lookupPassword(String user) { return "abc123"; }

	public static void main(String[] args) throws Exception 
	{
		int port = 7999;
		ServerSocket s = new ServerSocket(port);
		System.out.println("Waiting for Clients to Connect");
		Socket client = s.accept();
        System.out.println("connection established");
		ProtectedServer server = new ProtectedServer();
        System.out.println("check points 3");
		if (server.authenticate(client.getInputStream()))
		  System.out.println("Client logged in.");
		else
		  System.out.println("Client failed to log in.");

		s.close();
	}
}