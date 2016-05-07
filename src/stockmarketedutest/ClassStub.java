package stockmarketedutest;

import java.util.ArrayList;
import java.util.List;

public class ClassStub {
	private ArrayList<Student> myClass;
	private ArrayList<StockStub> stocksAllowed;
	private final String accessCode;
	private double initialMoney;
	public ClassStub(String accessCode){
		//Students need to enter this randomly generated access code to be added to the class
		this.accessCode = accessCode;
		myClass = new ArrayList<Student>();
		stocksAllowed = new ArrayList<StockStub>();
	}
	
	public String getAccessCode() {
		return accessCode;
	}
	
	public ArrayList<StockStub> getStocksAllowed(){
		return stocksAllowed;
	}
	
	public void addStock(String symbol, MarketStub m){
	    MarketStub globalMarket = m;
	    //System.out.println(globalMarket);
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
