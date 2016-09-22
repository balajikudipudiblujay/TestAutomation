package global.reusables;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtil {
	static String dirpath=System.getProperty("user.dir");
	static String propertiesPath=dirpath+"\\Configurations\\base.properties";
	static String propertiesPathLogs=dirpath+"\\Configurations\\log4j.properties";
	static String reportsPath=dirpath+"\\Configurations";
	static String reportsPathCentral="\\\\fileserver\\Repository\\QualityAssurance\\Automation_QA";
	static Properties prop=new Properties();
	static String from="";
	static String password="";
	
    final static String mailData="";
    EncryptionUtil encr=new EncryptionUtil();
    
   public static void main(String[] args) throws Exception {
    	//sendMail();
	   sendMail("Test Mail");
    }

    /**
     * This method will send mails with attachment based on data fetched from base.properties file.
     * @throws Exception
     */
    public static void sendMail() throws Exception{


    	FileReader reader=new FileReader(propertiesPath);
    	File reports=new File(reportsPath);
    	//System.out.println("report paths"+reportsPath);
    	File[] listofFiles=reports.listFiles();
    	for (int i = 0; i < listofFiles.length; i++) {
    		if(listofFiles[i].isFile()){
    			//System.out.println("File" +listofFiles[i].getName());
    		}else if(listofFiles[i].isDirectory()){
    			//System.out.println("Directory " +listofFiles[i].getName());
    		}
    	}

    	prop.load(reader);
    	String toAdd=prop.getProperty("toAdd");
    	String encPwd=prop.getProperty("MailPassword");
    	from=prop.getProperty("from");
    	
    	password=EncryptionUtil.decrypt(encPwd);
    	
    	final String fromMail=from;
    	String tomail=toAdd;
    	String ccmail="";
    	System.out.println("From "+fromMail+" to "+toAdd+"     password "+password);
    	String[] to=tomail.split(",");
    	prop.put("mail.smtp.auth", true);
    	prop.put("mail.smtp.starttls.enable", true);
    	prop.put("mail.transport.protocol", "smtp");
    	prop.put("mail.smtp.host", "smtp.office365.com");
    	prop.put("mail.smtp.port", "587");

    	InternetAddress[] addressTo = new InternetAddress[to.length];
    	for (int i = 0; i < to.length; i++)
    	{
    		try 
    		{
    			addressTo[i] = new InternetAddress(to[i]);
    		} 
    		catch (AddressException e) 
    		{
    			System.out.println("Improper mail address..Please check");
    			e.printStackTrace();
    		}
    	}
    	Session session = Session.getInstance(prop,
    			new javax.mail.Authenticator() {
    		protected PasswordAuthentication getPasswordAuthentication() {
    			return new PasswordAuthentication(fromMail, password);
    		}
    	});

    	try {
    		MimeMessage mesg=new MimeMessage(session);
    		mesg.setFrom(new InternetAddress(fromMail));

    		mesg.setRecipients(RecipientType.TO, addressTo);
    		mesg.setRecipients(RecipientType.CC, ccmail);
    		mesg.setSubject("KF 4.0 - PICT Scenario Execution Results");

    		Multipart multipar=new MimeMultipart();
    		BodyPart messageBodyPart=new MimeBodyPart();
    		multipar.addBodyPart(messageBodyPart);
    		messageBodyPart.setContent("<h1>sending html mail check</h1> ","text/html");
    		
    		String template = "Dear All," + '\n' + '\n' +  "Please find the enclosed attachments of Scenario Results along with the Scenario Status below."+ '\n'+ '\n' +
			"{0} "+ '\n' + '\n'
    				+ "Note:Reports are best viewed in Firefox browser."+'\n'+
    				"Open the Zip file extract the files in to local system, Execution Reports are placed in Today's date & time folder."+'\n'+
    				"Open \"summaryReport.html\" file with FF browser, navigate further by clicking on hyperlink provided for \"Status\" column."+'\n'+'\n'+'\n'


    				    				+ "Regards,"+'\n'
    				    				+"Automation Team"+'\n';
    		String rep=GenerateReports.ScenarioReports();
    		String message = MessageFormat.format(template,rep );
    		System.out.println("message===="+message);
    		messageBodyPart.setText(message);
    		try {
    			if(reportsPath!=null){
    				addAttachment(multipar, reportsPath+"/Reports.zip");
    			}
    			else{
    				System.out.println("No Attachments are present in Report Folder");
    			}	
    		} catch (Exception e) {
    			e.printStackTrace();
    			System.out.println("No Attachments are present in Report Folder");
    		}

    		mesg.setContent(multipar);
    		Transport.send(mesg);
    		System.out.println("Mail sent Succefully");

    	} catch (MessagingException mx) {
    		mx.printStackTrace();
    	}
    
    }
    
    public static void sendMail(String subject) throws Exception{
	FileReader reader=new FileReader(propertiesPathLogs);
	File reports=new File(reportsPath);
	//System.out.println("report paths"+reportsPath);
	File[] listofFiles=reports.listFiles();
	for (int i = 0; i < listofFiles.length; i++) {
		if(listofFiles[i].isFile()){
			//System.out.println("File" +listofFiles[i].getName());
		}else if(listofFiles[i].isDirectory()){
			//System.out.println("Directory " +listofFiles[i].getName());
		}
	}

	prop.load(reader);
	String toAdd=prop.getProperty("toAdd");
	String encPwd=prop.getProperty("MailPassword");
	from=prop.getProperty("from");
	password=EncryptionUtil.decrypt(encPwd);
	
	final String fromMail=from;
	String tomail=toAdd;
	String ccmail="";
	System.out.println("From "+fromMail+" to "+toAdd+"     password "+password);
	String[] to=tomail.split(",");
	prop.put("mail.smtp.auth", true);
	prop.put("mail.smtp.starttls.enable", true);
	prop.put("mail.transport.protocol", "smtp");
	prop.put("mail.smtp.host", "smtp.office365.com");
	prop.put("mail.smtp.port", "587");

	InternetAddress[] addressTo = new InternetAddress[to.length];
	for (int i = 0; i < to.length; i++)
	{
		try 
		{
			addressTo[i] = new InternetAddress(to[i]);
		} 
		catch (AddressException e) 
		{
			System.out.println("Improper mail address..Please check");
			e.printStackTrace();
		}
	}
	Session session = Session.getInstance(prop,
			new javax.mail.Authenticator() {
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(fromMail, password);
		}
	});

	try {
		MimeMessage mesg=new MimeMessage(session);
		mesg.setFrom(new InternetAddress(fromMail));

		mesg.setRecipients(RecipientType.TO, addressTo);
		mesg.setRecipients(RecipientType.CC, ccmail);
		mesg.setSubject(subject);

		Multipart multipar=new MimeMultipart();
		BodyPart messageBodyPart=new MimeBodyPart();
		multipar.addBodyPart(messageBodyPart);
	
		messageBodyPart.setText(
				"Dear All," + '\n' + '\n'

				+ "Please find the enclosed Scenario log file in attachment."+ '\n'+ '\n'+ '\n'+ '\n'
				
				
				+ "Regards,"+'\n'
				+"Automation Team"+'\n'
		);
		try {
			if(prop.getProperty("log4j.appender.R.File")!=null){
				
				addAttachment(multipar, prop.getProperty("log4j.appender.R.File"));
				
			}
			else{
				System.out.println("else-No Attachments are present in Report Folder");
			}	
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No Attachments are present in Report Folder");
		}

		mesg.setContent(multipar);
		Transport.send(mesg);
		System.out.println("Mail sent Succefully");

	} catch (MessagingException mx) {
		mx.printStackTrace();
	}
}
    
    /**
	 * This method will add attachment.
	 * @param multipart Multipart is a container that holds multiple body parts.
	 * @param filename Attachment file name.
	 * @throws MessagingException
	 */
	public static void addAttachment(Multipart multipart,String filename) throws MessagingException{
		BodyPart messageBodyPart=new MimeBodyPart();
		DataSource source= new FileDataSource(filename);
		messageBodyPart.setDataHandler(new DataHandler(source));
		
		messageBodyPart.setFileName(filename);
		multipart.addBodyPart(messageBodyPart);
	}
	
}
