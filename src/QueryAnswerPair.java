public class QueryAnswerPair {
	
	Query query;
	Answer answer;
	
	QueryAnswerPair(Query query, Answer answer) {
		this.query = query;
		this.answer = answer;
	}
	
	public String toString() {
		return "(" + this.query + "," + "[" + this.answer + "]" + ")";
	}
}