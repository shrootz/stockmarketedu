package stockmarketedu;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Entity;

public class Class {
	private ArrayList<Student> myClass;
	private ArrayList<Stock> stocksAllowed;
	private final String accessCode;
	private double initialMoney;
	public Class(String accessCode){
		//Students need to enter this randomly generated access code to be added to the class
		this.accessCode = accessCode;
		myClass = new ArrayList<Student>();
		stocksAllowed = new ArrayList<Stock>();
	}
	
	public Class() {
		this.accessCode = "0";
		myClass = new ArrayList<Student>();
		stocksAllowed = new ArrayList<Stock>();
	}
	
	public String getAccessCode() {
		return accessCode;
	}
	
	public ArrayList<Stock> getStocksAllowed(){
		return stocksAllowed;
	}
	
	public void addStock(String symbol){
	    Market globalMarket = Market.getInstance();
	    System.out.println(globalMarket);
		stocksAllowed.add(globalMarket.getStock(symbol));
	}
	
	public ArrayList<Student> getMyClass() {
		return myClass;
	}
	public void addStudent(Student student) {
		this.myClass.add(student);
	}
	public double getInitialMoney() {
		return initialMoney;
	}
	public void setInitialMoney(double d) {
		this.initialMoney = d;
	}
	
}
