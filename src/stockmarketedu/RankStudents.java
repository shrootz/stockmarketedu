package stockmarketedu;

import java.util.ArrayList;

//the UI needs button, based on which option is selected, the different algorithms are run to rank the 
//students: RankByMoney, RankByProfitPerShare, RankByProfitableSale
public interface RankStudents {
	public ArrayList<Student> returnRanking(ArrayList<Student> students);
}
