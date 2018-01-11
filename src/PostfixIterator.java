import java.util.Deque;
import java.util.Iterator;
/**
 * The PostfixIterator implements the Iterator class and
 * allows to iterate through postfix objects
 * @author Bahman Ashtari
 */
class PostfixIterator implements Iterator<String>{
	private Deque<String> postfixQ;
	
	public PostfixIterator(Deque<String> postfixQ) {
			this.postfixQ = postfixQ;
	}
		
	public String next() {
		return this.postfixQ.removeFirst();
	}
											
	public boolean hasNext() {				
		return this.postfixQ.size() > 0;	
	}	
}			
		