import java.util.Scanner;
//MAIN UNIT TEST
public class Main {
	
	public static void main(String[] args) {
	
		//this is the gui runner
		//Calculator guiCalculator = new Calculator();
		
		// INTRODUCTION
		System.out.println("Welcome to MyCalculator.");
		System.out.println("This Calculator evaluates mathematical expressions and big integers.");
		System.out.println("Every character and string must be in lowercase");
		Evaluator cmdLineCalculator = new Evaluator();
		Scanner console = new Scanner(System.in);
		
		System.out.println(">");
		while (console.hasNextLine()) {
			//taking input, passing it into the eval object's method, printing the result
			System.out.println(cmdLineCalculator.evaluate(console.nextLine()));
			System.out.println(">");
		}
		console.close();
	}
}
