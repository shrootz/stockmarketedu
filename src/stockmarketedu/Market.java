package stockmarketedu;

import java.util.ArrayList;

public abstract class Market {
	private boolean openMarket;
	private ArrayList<Stock> market;
	public void openMarket(){
		openMarket = true;
	}
	public void closeMarket(){
		openMarket = false;
	}
	public abstract void addStock();
	
	//if the query returns anything invalid, update the stock to be invalid and sets the stock price to zero
	//basically it is useless for the person to have in their portfolio 
	public abstract void updateStock();

		
}
