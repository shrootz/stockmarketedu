package stockmarketedu;

import java.io.UnsupportedEncodingException;
import java.util.Date;
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
		alreadySentStudentEmails = new ArrayList<String>();
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
		
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        String msgBody = "Please go to http://1-dot-stockmarketedu-1294.appspot.com/student.jsp and login with your email to get started!";

		for(String email: allEmails) {
	        try {
	            Message msg = new MimeMessage(session);
	            msg.setFrom(new InternetAddress("email@stockmarketedu.appspotmail.com", "Your Teacher"));
	            
	            msg.addRecipient(Message.RecipientType.TO,
	                             new InternetAddress(email, "Student"));
	            msg.setSubject("You've been invited to a StockMarketEdu class!");
	            msg.setText(msgBody);
	            msg.setContent(msgBody, "text/html; charset=utf-8");
	            msg.setSentDate(new Date());
	            Transport.send(msg);
	            System.out.println("Message sent to " + email);
	        } catch (AddressException e) {
	            System.out.println(e.getStackTrace());
	        } catch (MessagingException e) {
	            System.out.println(e.getStackTrace());
	        } catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
}
