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
import com.googlecode.objectify.annotation.Id;

import java.util.ArrayList;
import java.util.logging.Logger;

@Entity
public class Supervisor{
	@Id Long id;
	private String name;
	private String email; //gmail account login <- does this need to be stored here?
	private final String accessCode;
	private Class myClass;
	private ArrayList<String> allStudentEmails;
	private ArrayList<String> alreadySentStudentEmails;
	private static final Logger _logger = Logger.getLogger(CronController.class.getName());

	public Supervisor(){
		//this.name = name;
		accessCode = UUID.randomUUID().toString();
		myClass = new Class(accessCode);
		allStudentEmails = new ArrayList<String>();
	}
	

	//type email into a text box, button to add email
	//when button is pressed call this method
	//can also take in a block of emails and parse here.
	public void addEmail(String email){
		if(email.equals(this.email))
			return;
		if(allStudentEmails.contains(email))
			return;
		allStudentEmails.add(email);
	}
	
	//set the cash amount with a button
	public void setInitialCash(double cashMoney){
		if(cashMoney > Double.MAX_VALUE/10){
			myClass.setInitialMoney(Double.MAX_VALUE/10);
		}
		else{
			myClass.setInitialMoney(cashMoney);
		}
	}
	
	public ArrayList<Student> rank(RankStudents rankingStrategy){
		return rankingStrategy.returnRanking(myClass.getMyClass());
	}
	
	public String getEmail() {
		return email;
	}
	
	public ArrayList<String> getStudentEmails() {
		return allStudentEmails;
	}
	
	public void setEmail(String e) {
		email = e;
	}

	public Class getClassroom(){
		return myClass;
	}
	//finish sign up button. this method will be called - send email to all the students
	public void sendInvitations(){
		ArrayList<String> allEmails = new ArrayList<String>();
		for(String s :allStudentEmails){//alreadySentStudentEmails
			if(!alreadySentStudentEmails.contains(s)){
				allEmails.add(s);
				alreadySentStudentEmails.add(s);
			}
		}
		//allows for more students to be added without re-sending the same email
	    Properties props = new Properties();
	    Session session = Session.getDefaultInstance(props, null);

	    String msgBody = "Come join our Stock Market Challenge!\n Use this accessCode"
	    		+ this.accessCode + "to join my class"; 
	    //  need a google app engine link to send 
	    
	      try {
	          Message msg = new MimeMessage(session);
	          msg.setFrom(new InternetAddress("noreply@stockmarketedu.appspotmail.com", "Your Teacher"));
	          for (String s : allEmails){
	        	  msg.addRecipient(Message.RecipientType.CC,
	                         new InternetAddress(s));
	          }
	          msg.setSubject("You're doing the StockMarketEdu challenge");
	          msg.setText(msgBody);
	          Transport.send(msg);
	
	      }   catch (AddressException e) {
	    	  _logger.info(e.getMessage());
	          // ...
	      }   catch (MessagingException e) {
	    	  _logger.info(e.getMessage());
	          // ...
	      }   catch (UnsupportedEncodingException e) {
	    	  _logger.info(e.getMessage());	    	  
		 }
	}
}
