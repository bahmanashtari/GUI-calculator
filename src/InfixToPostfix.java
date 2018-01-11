import java.util.*;

/**
 * The InfixToPostfix class takes an infix mathematical expression,
 * and uses the "Shunting-yard algorithm" to convert it into a post-fix expression. 
 * The token expression will only be made of the symbols (, ), +, -, /, %, *, ^, 
 * non-negative integers, and variable names (which will be made of only lower-case letters)
 * 
 * @author Bahman Ashtari
 * @version April 14, 2015
 */
public class InfixToPostfix {
	
	//CLASS CONSTANT
	private static final String OPERATOR = ("+-*/^%");
	private static final String DIGITS = ("0123456789");
	private static final String LETTERS = ("abcdefghijklmnopqrstuvwxyz");

	//FIELD
	private Deque<String> postfixQ;

	/**
	 * CONSTRUCTOR
	 * Takes a mathematical infix expression and converts it into
	 * a postfix expression
	 * @throws IllegalArgumentException for all exceptions
	 * @param String s representing a mathematical infix notation
	 * It should only be made of the symbols (, ), +, -, /, %, *, ^, 
	 * non-negative integers, and variable names (which should be made of only lower-case letters)
	 */
	public InfixToPostfix(String s) {
		try {
			Queue<String> infixQ = tokenizeInfixString(s);
			this.postfixQ = shuntingyard(infixQ);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
		// debug
		//System.out.println(this.postfixQ);
	}

	/*
	 * Tokenize a string containing OPERATOR, DIGITS, OR LETTERS
	 * into a queue of strings in the same order for all the elements
	 */
	private Queue<String> tokenizeInfixString(String s) {
		Queue<String> infixQ = new LinkedList<String>();
		Stack<String> accumulator = new Stack<String>(); //Holds numbers and letters

		int i = 0; 
		while (i < s.length()) { 
			char ch = s.charAt(i); 
			if (Character.isWhitespace(ch)) //ignoring spaces
				i++;
			else if (OPERATOR.contains(ch + "") || ch == '(' || ch == ')') { //operators and ()s go to result queue 
				infixQ.add(ch + "");
				i++;
			}
			else if (DIGITS.contains(ch + "")) { //digits go to result queue
				while (i < s.length() && DIGITS.contains(s.charAt(i) + "")) {
					accumulator.push(s.charAt(i) + ""); //accumulating the numbers that are together as a single number
					i++;
				}
				stackToQueue(accumulator, infixQ); //the number now goes into the result queue as a single number
			} else if (LETTERS.contains(ch + "")) {
				while (i < s.length() && LETTERS.contains(s.charAt(i) + "")) {
					accumulator.push(s.charAt(i) + "");
					i++;
				}
				stackToQueue(accumulator, infixQ);
			} else
				throw new IllegalArgumentException();
		}
		return infixQ;
	}

	private void stackToQueue(Stack<String> accumulator, Queue<String> infixQ) {
		String temp = "";
		
		while (!accumulator.isEmpty())
			temp = accumulator.pop() + temp;
		infixQ.add(temp);
	}


	private Deque<String> shuntingyard(Queue<String> infixQ) {
		Map<String, Integer> precedence = new TreeMap<String, Integer>();
		precedence.put("+", 2);
		precedence.put("-", 2);
		precedence.put("*", 3);
		precedence.put("/", 3);
		precedence.put("%", 3);
		precedence.put("^", 4);

		Deque<String> tempPostfixQ = new LinkedList<String>();
		Stack<String> tempStack = new Stack<String>();

		try {
			while (!infixQ.isEmpty()) {
				String token = infixQ.remove();

				if (OPERATOR.contains(token)) {
					if (!tempStack.isEmpty() && !tempStack.peek().equals("(")) {
						while ( !tempStack.isEmpty() && !token.equals("^") && precedence.get(token) <= precedence.get(tempStack.peek())) {
							tempPostfixQ.add(tempStack.pop());
						}
						while ( !tempStack.isEmpty() && token.equals("^") && precedence.get(token) < precedence.get(tempStack.peek())) {
							tempPostfixQ.add(tempStack.pop());
						}
					}
					tempStack.push(token);
				} else if (DIGITS.contains(token.charAt(0) + "") || LETTERS.contains(token.charAt(0) + ""))
					tempPostfixQ.add(token);
				else if (token.equals("("))
					tempStack.push(token);
				else if (token.equals(")")) {
					while (!tempStack.isEmpty() && !tempStack.peek().equals("(")) {
						tempPostfixQ.add(tempStack.pop());
					}
					tempStack.pop();
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
		while ( !tempStack.isEmpty())
			tempPostfixQ.add(tempStack.pop());
		
		return tempPostfixQ;
	}
	
	/**
	 * The Iterator method constructs a new iterator object
	 * to iterate through a postfix object
	 * @return an iterator for postfix object
	 */
	public Iterator<String> iterator() {
		return new PostfixIterator(postfixQ);
	}

	// MAIN METHOD
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		InfixToPostfix tokenize = new InfixToPostfix("2 + 3 * 4 * 5 + 6");
	}
}
