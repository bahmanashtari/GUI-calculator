import java.math.*;
import java.util.*;

/**
* Evaluator objects are calculators with ability to
* evaluate mathematical expressions with big integer numbers
* in form of variable = expression
* it keeps track of variables' value in a map
* input can be any mathematical expression with integers 
* variable must be in lower case letters 
* saves/updates variable's value in map
* spaces are not allowed between variable names or numbers
* @author Bahman Ashtari
* @version May 11, 2015
*/
public class Evaluator {
	
	//****UNIT TEST
	public static void main(String[] args) {
		Evaluator test = new Evaluator();
		System.out.println("Normal claculations");
		System.out.println(test.evaluate("2+3").equals("5"));
		System.out.println(test.evaluate("2 +3").equals("5"));
		System.out.println(test.evaluate("2+ 3").equals("5"));
		System.out.println(test.evaluate("12 % 2").equals("0"));
		System.out.println();
		System.out.println("Big integer claculations");
		System.out.println(test.evaluate("276565324 + 3988765789876875887").equals("3988765790153441211"));
		System.out.println(test.evaluate("2345^10").equals("5028384401634493639598396494140625"));
		System.out.println();
		System.out.println("Variable storing and claculations");
		System.out.println(test.evaluate("speed = 100").equals("100"));
		System.out.println(test.evaluate("time=20").equals("20"));
		System.out.println(test.evaluate("x = 3").equals("3"));
		System.out.println(test.evaluate("x%2").equals("1"));
		System.out.println(test.evaluate("x %3").equals("0"));
		System.out.println(test.evaluate("y=98").equals("98"));
		System.out.println(test.evaluate("z = y/(x+1)").equals("24"));
		System.out.println(test.evaluate("(y^x)/z").equals("39216"));
		System.out.println();
		System.out.println("Error and exception");
		System.out.print(test.evaluate("x y= 9"));
		System.out.print(test.evaluate("xy = 9 9"));
		System.out.print(test.evaluate("x + k"));
		System.out.print(test.evaluate("x ="));
		System.out.print(test.evaluate("x + y =9"));
	}

	// CLASS CONSTANT
	private static final String OPERATORS = "+-/*%^";
	
	// FIELD
	private Map<String, String> var; //maps variable names to values
	
	// CONSTRUCTOR
	/**
	 * Evaluator objects are calculators with ability to
	 * evaluate mathematical expressions with big integer numbers
	 *  in form of variable = expression
	 * it keeps track of variables' value in a map
	 */
	public Evaluator() {
		var = new TreeMap<String, String>();
	}
	
	/**
	 * evaluate mathematical expressions with big integer numbers
	 * in form of variable = expression 
	 * prints Error for all exception types
	 * @param input can be any mathematical expression with 
	 * integers and lower case letters
	 * @return the result of the expression as a string and 
	 * saves/updates variable's value in map
	 */
	public String evaluate(String input) {

		String variable = "";
		String expressionRes = "";
		
		try {
			if(input.contains("=")) { //if input is a type of equation
				int i = 0;
				while(input.charAt(i) != '=') { //parsing variable
					char c = input.charAt(i);
					variable = variable + (c);  
					i++;
				}
				//remove leading and ending white spaces
				variable = variable.trim();
				if( ! legalVariableName(variable)) //not empty and LowerCase is legal
					throw new IllegalArgumentException();
				
				//everything after the equal sign is expected to be expression otherwise the IToP will catch the error
				String expression = input.substring(input.indexOf("=")+1).trim();
				if( ! legalExpression(expression))
					throw new IllegalArgumentException();
				
				expressionRes = eval(expression);
				
				this.var.put(variable, expressionRes); //updating the map
			} 
			else {
				if( ! legalExpression(input.trim()))
					throw new IllegalArgumentException();
				
				expressionRes = eval(input);
			}
		} catch (Exception e) {
			System.out.println("Error");
			//throw new IllegalArgumentException("Error");
		}
		return expressionRes;
	}
	
	private String eval(String str) {
		//empty string is not acceptable expression
		if(str.trim().isEmpty()) 
			throw new IllegalArgumentException();
		
		//remove leading and ending white spaces
		str = str.trim(); 
		str = replaceVariable(str);
		
		InfixToPostfix temp = new InfixToPostfix(str);
		Iterator<String> itr = temp.iterator();
		//this deque stores all the elements to be evaluated 
		Deque<String> evalDeque = new ArrayDeque<String>();
		
		while(itr.hasNext()) { 
			String nextToken = itr.next();
			//if its digit
			if(Character.isDigit(nextToken.charAt(0)))
				evalDeque.addLast(nextToken);
			//if its an operator
			else { 
				BigInteger bigTemp = null;
				//get the first two numbers
				BigInteger num1 = new BigInteger(evalDeque.removeLast()); 
				BigInteger num2 = new BigInteger(evalDeque.removeLast());
				switch(nextToken) {
				case "+": {
					bigTemp = num2.add(num1);
					break;
				}
				case "-": {
					bigTemp = num2.subtract(num1);
					break;
				}
				case "*": {
					bigTemp = num2.multiply(num1);
					break;
				}
				case "/": {
					bigTemp = num2.divide(num1);
					break;
				}
				case "%": {
					bigTemp = num2.mod(num1);
					break;
				}
				case "^": {
					bigTemp = num2.pow(num1.intValue()); //integer value for exponent
					break;
				}
				}
				evalDeque.addLast(bigTemp.toString()); //adding the result back to evaluation stack
			}
		}
		String result = removeBraces(evalDeque.toString());
		return result; //returning the value left in the stack without braces
	}
	
	private String replaceVariable(String str) {
		//to accumulate the characters of variable names
		String variableNameAccumulator = ""; 
		String result = "";
		
		for(int i=0; i<str.length(); i++) {
			char c = str.charAt(i);
			
			if(Character.isLetter(c)) {
				// ex; speed + value, avoid mapping operation to be skipped when reached end of string
				if((str.length()-1) == i) { // when looking at the very last character 
					variableNameAccumulator += (c+"");
					result += var.get(variableNameAccumulator);
					variableNameAccumulator = ""; // empty out variableName after get its value from map
				}
				else
					variableNameAccumulator += (c+"");
			} 								 
			//operator and right parenthesis
			else if(OPERATORS.contains(c+"") || c == ')') {  
				if(var.containsKey(variableNameAccumulator)) {
					result += var.get(variableNameAccumulator);
					variableNameAccumulator = ""; //empty out variableName after get its value from map  
				}
				result += c;
			}
			//digits and left parenthesis 
			else if(Character.isDigit(c) || c == '(') 
				result += c;
		}
		return result;
	}
	
	private boolean legalExpression(String str) {
		Stack<String> temp = new Stack<String>();
		for(int i=0; i<str.length(); i++) {
			temp.push(str.charAt(i)+"");
			if(str.charAt(i) == ' ') {
				temp.pop();  //flushing out the space we just read from string out of stack
				String prev = temp.pop();
				String next = (str.charAt(i+1) + "");
				if(next == " ")
					;
				else if( ! OPERATORS.contains(prev) && ! OPERATORS.contains(next))
					return false;
			}
		}
		return true;
	}
	
	private Stack<String> addAll(String trim) {
		Stack<String> res = new Stack<String>();
		for(int i=0; i<trim.length(); i++) {
			res.push(trim.charAt(i)+"");
		}
		return res;
	}

	private boolean legalVariableName(String str) {
		for(int i=0; i<str.length(); i++) { // illegal variable names ex; value+2
			if( ! Character.isLetter(str.charAt(i)))
				return false;
		}
		String temp = str.toLowerCase();
		return ( ! (str.isEmpty()) && (str.equals(temp)) );
	}
		
	/*private String removeSpaces(String str) {
		String res = "";
		for(int i=0; i<str.length(); i++) {
			char c = str.charAt(i);
			if(c == ' ') 
				;
			else
				res += (c+"");
		}
		return res;
	}*/
	
	private String removeBraces(String str) {
		String res = "";
		for(int i=0; i<str.length(); i++) {
			char c = str.charAt(i);
			if(c == '[' || c == ']') 
				;
			else
				res += (c+"");
		}
		return res;
	}
}
