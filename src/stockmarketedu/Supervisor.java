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

import com.googlecode.objectify.annotation.Entity;

import java.util.ArrayList;

@Entity
public class Supervisor{
	private String name;
	private String email; //gmail account login <- does this need to be stored here?
	private final String accessCode;
	private Class myClass;
	private ArrayList<String> allStudentEmails;
	
	public Supervisor(String name){
		this.name = name;
		accessCode = UUID.randomUUID().toString();
		myClass = new Class(accessCode);
		allStudentEmails = new ArrayList<String>();
	}

	//type email into a text box, button to add email
	//when button is pressed call this method
	//can also take in a block of emails and parse here.
	public void addEmail(String email){
		allStudentEmails.add(email);
	}
	
	//set the cash amount with a button
	public void setInitialCash(float cashMoney){
		myClass.setInitialMoney(cashMoney);
	}

	//finish sign up button. this method will be called - send email to all the students
	public void sendInvitations(){
		ArrayList<String> allEmails = allStudentEmails;
		//allows for more students to be added without re-sending the same email
		allStudentEmails = new ArrayList<String>(); 
	    Properties props = new Properties();
	    Session session = Session.getDefaultInstance(props, null);

	    String msgBody = "Come join our Stock Market Challenge!\n Use this accessCode"
	    		+ this.accessCode + "to join" + this.name + "'s class"; 
	    //  need a google app engine link to send 
	    
	    for (String s : allEmails){
	      try {
	          Message msg = new MimeMessage(session);
	          msg.setFrom(new InternetAddress("noreply@stockmarketedu.com", "Your Teacher"));
	          msg.addRecipient(Message.RecipientType.TO,
	                         new InternetAddress(s));
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
	
	/*this assumes we create the students/populate the class from the supervisor
	public void sendInvitations(){
		ArrayList<Student> students = myClass.getMyClass(); // send emails to all the students
	    Properties props = new Properties();
	    Session session = Session.getDefaultInstance(props, null);

	    String msgBody = "Come join our Stock Market Challenge!\n Use this accessCode"
	    		+ this.accessCode + "to join" + this.name + "'s class"; //  I don't know what link to send 
	    
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
	*/
}
