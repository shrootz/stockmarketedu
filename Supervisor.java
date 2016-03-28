package stockmarketedu;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.ArrayList;


public class Supervisor{
	private final String accessCode;
	private Class myClass;
	public Supervisor(){
		accessCode = UUID.randomUUID().toString();
		myClass = new Class(accessCode);
	}

	public void sendInvitations(){
		ArrayList<Student> students = myClass.getMyClass(); // send emails to all the students
	    Properties props = new Properties();
	    Session session = Session.getDefaultInstance(props, null);

	    String msgBody = "Come join our Stock Market Challenge!"; //  I don't know what link to send 
	    
	    for (Student s : students){
	      try {
	          Message msg = new MimeMessage(session);
	          msg.setFrom(new InternetAddress("noreply@stockmarketedu.com", "Your Teacher"));
	          msg.addRecipient(Message.RecipientType.TO,
	                         new InternetAddress(s.getEmail(), s.getName()));
	          msg.setSubject("You're doing the StockMarketEdu challenge");
	          msg.setText(msgBody);
	          Transport.send(msg);

	      }   catch (AddressException e) {
	          // ...
	      }   catch (MessagingException e) {
	          // ...
	      }   catch (UnsupportedEncodingException e) {
		 }
	   }
	}
}
