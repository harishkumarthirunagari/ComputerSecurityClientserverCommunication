import java.math.BigInteger;
import java.security.*;
import java.util.*;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

public class first {

	public static void main(String[] args) 
	{
		first f= new first(); 	//creating object MD5andSHA
		System.out.println("Enter the String :");
		Scanner s=new Scanner(System.in);//reading input string
		String message=s.nextLine();
		System.out.println("Message is "+message);			
		System.out.println("MD5 hash:"+f.MD5(message));	//printing both hash values for md5 and sha			
		System.out.println("SHA hash:"+f.SHA(message));				
	}
	
	// This function is for MD5 hashing
	public String  MD5(String sent)
	{		
		MessageDigest md=null;								
		try{
		md = MessageDigest.getInstance("MD5");}	//creating md5 instance			                 
		catch(Exception e){
			e.printStackTrace();}
		
		byte[] md5value = md.digest(sent.getBytes());//creating digest value from message.getBytes
		BigInteger big=new BigInteger(1, md5value);//creating bigInteger
	       String dig= String.format("%032X"+ "", big);	// big1 has the hashed value for the digest which should be formated to be able to print	
		return dig;
	}
	
	public String SHA(String sent)
{
		MessageDigest md=null;
		try{
			md = MessageDigest.getInstance("SHA");} //creating sha instance
		catch(Exception e){
			e.printStackTrace();}
		
		byte[] md5value = md.digest(sent.getBytes());//creating digest value from message.getBytes
		BigInteger big1=new BigInteger(1, md5value);//creating bigInteger
	        String dige= String.format("%032X", big1); // big1 has the hashed value for the digest which should be formated to be able to print
		return dige;
	
	
	}
	
	
}
