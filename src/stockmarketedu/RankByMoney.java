package stockmarketedu;

import java.util.ArrayList;

public class RankByMoney implements RankStudents{
	private ArrayList<Student> allStudents;
	
	public RankByMoney(ArrayList<Student> allStudents){
		this.allStudents = allStudents;
	}
	
	@Override
	public ArrayList<Student> returnRanking() {
		return allStudents;
	}

}
