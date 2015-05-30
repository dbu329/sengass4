public class QueryAnswerList {
	
	Query query;
	Answer answer;
	
	QueryAnswerList(Query query, Answer answer) {
		this.query = query;
		this.answer = answer;
	}
	
	public String toString() {
		return "(" + this.query + "," + "[" + this.answer + "]" + ")";
	}
}