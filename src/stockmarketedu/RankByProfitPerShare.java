package stockmarketedu;

import java.util.ArrayList;

public class RankByProfitPerShare implements RankStudents{
	private ArrayList<Student> allStudents;
	
	public RankByProfitPerShare(ArrayList<Student> allStudents){
		this.allStudents = allStudents;
	}
	
	@Override
	public ArrayList<Student> returnRanking() {
		return allStudents;
	}

}
