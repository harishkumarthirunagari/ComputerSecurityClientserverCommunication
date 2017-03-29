import java.io.*;
import java.net.*;
import java.security.*;
import java.util.Date;

public class ProtectedClient
{
	public void sendAuthentication(String user, String password, OutputStream outStream) throws IOException, NoSuchAlgorithmException 
	{
		System.out.println("eror before  outputstream creation");
		DataOutputStream out = new DataOutputStream(outStream);//creating output stream to server
        //creating time stamp 1 and 2 for creating digest 2
		System.out.println("eror after outputstream creation");
   		Date date = new Date();
		long t1 = date.getTime();//getting time from date class
		long t2 = date.getTime();//getting time from date class
        double rand1 = Math.random();//creating a random number1
		double rand2 = Math.random();//creating a random number2
        byte[] d1 = Protection.makeDigest(user, password,t1,rand1);//version 1
        byte[] d2 = Protection.makeDigest(d1,t2,rand2);//version 2
        out.writeUTF(user);//writing user to server
        System.out.println("user sent"+user);
        out.flush();
        out.writeLong(t1);
        out.flush();
        System.out.println("time stamp written"+t1);
        out.writeLong(t2);
        out.flush();
        System.out.println("timestamp written"+t2);
        out.writeDouble(rand1);
        out.flush();
        System.out.println("random number written"+rand1);
		out.writeDouble(rand2);
		out.flush();
		System.out.println("random number written"+rand2);
		out.writeInt(d1.length);
		out.flush();
		System.out.println("length written d2"+d2.length);
        out.write(d2);
        out.flush();
      
      
		
	}

	public static void main(String[] args) throws Exception 
	{
		String host = "cse01.cse.unt.edu";
		int port = 7999;
		System.out.println("Connecting To Host"+host);
		String user = "George";
		String password = "abc123";
		Socket s = new Socket(host, port);
		
		System.out.println("connection establish");
		ProtectedClient client = new ProtectedClient();
	
		client.sendAuthentication(user, password, s.getOutputStream());

		s.close();
	}
}