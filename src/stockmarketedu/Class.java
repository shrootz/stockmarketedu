package stockmarketedu;

import java.util.ArrayList;

import com.googlecode.objectify.annotation.Entity;

@Entity
public class Class {
	private ArrayList<Student> myClass;
	private ArrayList<Stock> stocksAllowed;
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
	
	public ArrayList<Stock> getStocksAllowed(){
		return stocksAllowed;
	}
	
	public void addStock(Stock stock){
		stocksAllowed.add(stock);
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
