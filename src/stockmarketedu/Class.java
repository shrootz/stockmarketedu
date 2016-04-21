package stockmarketedu;

import java.util.ArrayList;

import com.googlecode.objectify.annotation.Entity;

public class Class {
	private ArrayList<Student> myClass;
	private Market market;
	private final String accessCode;
	private float initialMoney;
	public Class(String accessCode){
		//Students need to enter this randomly generated access code to be added to the class
		this.accessCode = accessCode;
		myClass = new ArrayList<Student>();
	}
	public String getAccessCode() {
		return accessCode;
	}
	public Market getMarket() {
		return market;
	}
	public void setMarket(Market market) {
		this.market = market;
	}
	public ArrayList<Student> getMyClass() {
		return myClass;
	}
	public void addStudent(Student student) {
		this.myClass.add(student);
	}
	public float getInitialMoney() {
		return initialMoney;
	}
	public void setInitialMoney(float initialMoney) {
		this.initialMoney = initialMoney;
	}
	
}
