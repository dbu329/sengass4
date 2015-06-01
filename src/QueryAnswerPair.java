import java.util.ArrayList;

public class QueryAnswerPair {
	
	Query query;
	ArrayList<Answer> answer;
	
	QueryAnswerPair(Query query, ArrayList<Answer> answer) {
		this.query = query;
		this.answer = answer;
	}
	
	public String toString() {
		return "(" + this.query + "," + "[" + this.answer + "]" + ")";
	}
}