package stockmarketedu;

import java.util.ArrayList;

public class RankByProfitPerShare implements RankStudents{
	
	public RankByProfitPerShare(){
	}
	
	@Override
	public ArrayList<Student> returnRanking(ArrayList<Student> students) {
		ArrayList<Student> rankedStudents = new ArrayList<Student>(students);
		for(int i =0 ; i <rankedStudents.size(); i++){
			Student current = rankedStudents.get(i);
			int pos = i; 
			for(int j = i-1; j>=0; j--){
				if(rankedStudents.get(j).getMaxProfitPerShare() > current.getMaxProfitPerShare()){ //shift the current elements as long as you need to
					Student temp = rankedStudents.get(j);
					rankedStudents.get(j) = rankedStudents.get(j+1);
					rankedStudents.get(j+1) = temp;
					pos = j;
				}
				else
					break;
			}
			rankedStudents.get(pos) = current;
		}
		return rankedStudents;
	}

}
