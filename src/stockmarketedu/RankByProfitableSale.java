package stockmarketedu;

import java.util.ArrayList;

public class RankByProfitableSale implements RankStudents{
	private ArrayList<Student> allStudents;
	
	public RankByProfitableSale(ArrayList<Student> allStudents){
		this.allStudents = allStudents;
	}
	
	@Override
	public ArrayList<Student> returnRanking() {
		return allStudents;
	}

}
