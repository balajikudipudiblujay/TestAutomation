package global.reusables;

import global.reusables.ExcelUtils;
import global.reusables.GenericMethods;

import java.util.HashMap;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;


public class ReadingMail {

	/**
	 * @param args
	 */
	static String MessageBody=null;
	/*public static String check(String host, String storeType, String user,
		      String password,int RowNo,HashMap<String, String> ScenarioDetailsHashMap) */
	public static String check(String host, String storeType, String user,
		      String password) 
		   	   
	{
		      try {

		      //create properties field
	Properties properties = new Properties();
	properties.put("mail.imaps.auth", true);
	properties.put("mail.imaps.host", host); 
	properties.put("mail.imaps.port", "993");
	properties.put("mail.store.protocol", "imaps");
    properties.put("mail.imaps.starttls.enable", "true");
	  Session emailSession = Session.getDefaultInstance(properties);  
	  // emailSession.setDebug(true);
	   //2) create the POP3 store object and connect with the pop server  
	   Store store =  emailSession.getStore(storeType);  
	   store.connect(user, password);  
	

	//create the folder object and open it
	//Folder emailFolder = store.getFolder("INBOX");
	 //  emailFolder.open(Folder.READ_ONLY);
	Folder sp = store.getFolder("Drafts");
	//Folder emailFolder=	sp.getFolder("KEF");
	sp.open(Folder.READ_ONLY);
	// retrieve the messages from the folder in an array and print it
	Message[] messages = sp.getMessages();
	int j= messages.length;
    System.out.println("messages.length---" + j);
	//System.out.println("-------------out--------------------");
	 Message messag = messages[messages.length-1];
	/* System.out.println("Email Number " + (13));
	  System.out.println("Subject: " + messag.getSubject());
	   System.out.println("Text: " +  messag.getContent().toString());*/
	  
	   	System.out.println("true");
		   for (int i = j-1;  i >=0;i--) {
			   if(messages[i].getSubject()!= null && messages[i].getSubject().equalsIgnoreCase("TLI# Request")){
		   System.out.println("---------------in------------------");
	   System.out.println("Email Number " + (i + 1));
	   System.out.println("Subject: " + messages[i].getSubject());
	   //System.out.println("From: " + message.getFrom()[0]);
	   System.out.println("Text: " +  messages[i].getContent().toString());
       MessageBody=messages[i].getContent().toString();
   /*if(MessageBody.contains(ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap)))
     */ break;
	  
	  	}
	
	   	}
		   System.out.println("MessageBody==="+MessageBody);
	//close the store and folder objects
	   	sp.close(false);
	store.close();

	} catch (NoSuchProviderException e) {
	   e.printStackTrace();
	} catch (MessagingException e) {
	   e.printStackTrace();
	} catch (Exception e) {
	   e.printStackTrace();
	}
	 return MessageBody;
		   }
   
/*public static void main(String[] args) {

String host = "outlook.office365.com";
String mailStoreType = "imaps";
	String username = "masthan.gorre@kewill.com";
	String password = "";

	check(host, mailStoreType, username, password);

	}
*/
}




