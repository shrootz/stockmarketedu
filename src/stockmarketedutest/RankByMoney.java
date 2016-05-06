package stockmarketedutest;

import java.util.ArrayList;

public class RankByMoney implements RankStudents{
	
	public RankByMoney(){
		
	}
	
	@Override
	public ArrayList<Student> returnRanking(ArrayList<Student> students) {
		ArrayList<Student> rankedStudents = new ArrayList<Student>(students);
		for(int i =0 ; i <rankedStudents.size(); i++){
			Student current = rankedStudents.get(i);
			StudentStub current_stub = (StudentStub) current;
			int pos = i; 
			for(int j = i-1; j>=0; j--){
				Student next = rankedStudents.get(j);
				StudentStub next_stub = (StudentStub) next;
				if(next_stub.getMoney() > current_stub.getMoney()){ //shift the current elements as long as you need to
					Student temp = rankedStudents.get(j);
					rankedStudents.set(j, rankedStudents.get(j+1));
					rankedStudents.set(j+1,temp);
					pos = j;
				}
				else
					break;
			}
			rankedStudents.set(pos,current);
		}
		return rankedStudents;
	}

}
