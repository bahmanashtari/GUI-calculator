import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * calculator GUI
 * this gui calculator calculates mathematical expressions
 * including variable calculation
 * it also give tool tips to user its buttons and evaluation result on its text field
 * by hovering the mouse over them
 * @author Bahman Ashtari
 *
 */
public class Calculator extends JFrame {

	// DATA FIELD
//NUMBERS
	 JButton jbtNum1;
     JButton jbtNum2;
     JButton jbtNum3;
     JButton jbtNum4;
     JButton jbtNum5;
     JButton jbtNum6;
     JButton jbtNum7;
     JButton jbtNum8;
     JButton jbtNum9;
     JButton jbtNum0;
     JButton jbtEqual; //prints equal sign 
//OPERATIONS, EVALUATOR,and PARANTHESIS
     JButton jbtAdd;
     JButton jbtSub;
     JButton jbtMult;
     JButton jbtDiv;
     JButton jbtMod;
     JButton jbtPow;	
     JButton jbtEval; //evaluates 
     JButton jbtLeftPar;
     JButton jbtRightPar;
//BACKSPACE, VARIABLES
     JButton jbtBackSpace;
     JButton jbtX;
     JButton jbtY;
     JButton jbtZ;
//JFRAME
     JFrame frame;
//JPANEL
    
     JPanel mainPanel;
     JPanel numPanel; //number buttons
     JPanel oprPanel; //operation buttons
     JPanel varPanel; //variable buttons
//JTEXTFIELD
     JTextField textField;
     //to reset textField after evaluation
     boolean newCycle;
    
    // CONSTRUCTOR
    public Calculator() {
    	newCycle = true;
    	frame = new JFrame("Calculator");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(500, 200);
    	frame.setLayout(new BorderLayout());
    	//TextField
    	JPanel textPanel = new JPanel(new GridLayout(0, 1)); 
    	textField = new JTextField();
    	textField.setHorizontalAlignment(JTextField.RIGHT);
    	textField.setEditable(false);
    	textPanel.add(textField);
    	frame.add(textPanel, BorderLayout.NORTH);
    	//OPERATIONS PANEL
    	oprPanel = new JPanel(new GridLayout(4, 3, 10, 10));
    	oprPanel.add(jbtAdd = new JButton("+"));
        oprPanel.add(jbtSub = new JButton("-"));
        oprPanel.add(jbtMult = new JButton("*"));
        oprPanel.add(jbtDiv = new JButton("/"));
        oprPanel.add(jbtMod = new JButton("%"));
        oprPanel.add(jbtPow = new JButton("^"));	
        oprPanel.add(jbtEval = new JButton("="));//evaluates 
        oprPanel.add(jbtLeftPar = new JButton("("));
        oprPanel.add(jbtRightPar = new JButton(")"));
    	//NUMBERS PANEL
    	numPanel = new JPanel(new BorderLayout());
    	JPanel northNumPanel = new JPanel(new GridLayout(3, 3, 10, 10));
    	northNumPanel.add(jbtNum1 = new JButton("1"));
        northNumPanel.add(jbtNum2 = new JButton("2"));
        northNumPanel.add(jbtNum3 = new JButton("3"));
        northNumPanel.add(jbtNum4 = new JButton("4"));
        northNumPanel.add(jbtNum5 = new JButton("5"));
        northNumPanel.add(jbtNum6 = new JButton("6"));
        northNumPanel.add(jbtNum7 = new JButton("7"));
        northNumPanel.add(jbtNum8 = new JButton("8"));
        northNumPanel.add(jbtNum9 = new JButton("9"));
        JPanel southNumPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        southNumPanel.add(jbtNum0 = new JButton("0"));
        southNumPanel.add(jbtBackSpace = new JButton("Del"));
        jbtBackSpace.setToolTipText("This button is a back space");        
        numPanel.add(northNumPanel, BorderLayout.NORTH);
        numPanel.add(southNumPanel, BorderLayout.SOUTH);
    	//VARIABLES PANEL
        varPanel = new JPanel(new GridLayout(4, 0, 10, 10));

        varPanel.add(jbtX = new JButton("x"));
        jbtX.setBackground(Color.YELLOW);
        jbtX.setToolTipText("Variable can be used to form equation or retrive variable value");
        
        varPanel.add(jbtY = new JButton("y"));
        jbtY.setBackground(Color.GREEN);
        jbtY.setToolTipText("Variable can be used to form equation or retrive variable value");
        
        varPanel.add(jbtZ = new JButton("z"));
        jbtZ.setBackground(Color.CYAN); 
        jbtZ.setToolTipText("Variable can be used to form equation or retrive variable value");
        
        
        varPanel.add(jbtEqual = new JButton("print \"=\""));
        jbtEqual.setToolTipText("This button inserts an equal sign in the text area"
        					  + "it can be used to form equation or retrive variable value");
    	//MainPanel
    	mainPanel= new JPanel(new BorderLayout()); 
    	mainPanel.add(oprPanel, BorderLayout.EAST);
    	mainPanel.add(numPanel, BorderLayout.CENTER);
    	mainPanel.add(varPanel, BorderLayout.WEST);
    	frame.add(mainPanel, BorderLayout.SOUTH);
    	frame.setVisible(true);
    	
    	//ACTIONlISTENERS
    	//NUMBERS
    	 jbtNum1.addActionListener(new ListenOne());
         jbtNum2.addActionListener(new ListenTwo());
         jbtNum3.addActionListener(new ListenThree());
         jbtNum4.addActionListener(new ListenFour());
         jbtNum5.addActionListener(new ListenFive());
         jbtNum6.addActionListener(new ListenSix());
         jbtNum7.addActionListener(new ListenSeven());
         jbtNum8.addActionListener(new ListenEight());
         jbtNum9.addActionListener(new ListenNine());
         jbtNum0.addActionListener(new ListenZero());
         jbtEqual.addActionListener(new ListenEqual()); //prints equal sign 
    //OPERATIONS, EVALUATOR,and PARANTHESIS
         jbtAdd.addActionListener(new ListenAdd());
         jbtSub.addActionListener(new ListenSub());
         jbtMult.addActionListener(new ListenMult());
         jbtDiv.addActionListener(new ListenDiv());
         jbtMod.addActionListener(new ListenMod());
         jbtPow.addActionListener(new ListenPow());	
         jbtEval.addActionListener(new ListenEval()); //evaluates 
         jbtLeftPar.addActionListener(new ListenLeftPar());
         jbtRightPar.addActionListener(new ListenRightPar());
    //BACKSPACE, VARIABLES
         jbtBackSpace.addActionListener(new ListenBackSpace());
         jbtX.addActionListener(new ListenX());
         jbtY.addActionListener(new ListenY());
         jbtZ.addActionListener(new ListenZ());
    }

// ACTIONLISTENER CLASSES
    class ListenOne implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	if(newCycle) {
        		textField.setText("1");
        		newCycle = false;
        	}
        	else {
	            String display = textField.getText();
	            textField.setText(display + "1");
        	}
        }
    }
    
    class ListenTwo implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
        	if(newCycle) {
        		textField.setText("2");
        		newCycle = false;
        	}
        	else {
	            String display = textField.getText();
	            textField.setText(display + "2");
        	}
        }
    }
    
    class ListenThree implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
        	if(newCycle) {
        		textField.setText("3");
        		newCycle = false;
        	}
        	else {
	            String display = textField.getText();
	            textField.setText(display + "3");
        	}
        }
    }
    
    class ListenFour implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
        	if(newCycle) {
        		textField.setText("4");
        		newCycle = false;
        	}
        	else {
	            String display = textField.getText();
	            textField.setText(display + "4");
        	}
        }
    }
    
    class ListenFive implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
        	if(newCycle) {
        		textField.setText("5");
        		newCycle = false;
        	}
        	else {
	            String display = textField.getText();
	            textField.setText(display + "5");
        	}
        }
    }
    
    class ListenSix implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
        	if(newCycle) {
        		textField.setText("6");
        		newCycle = false;
        	}
        	else {
	            String display = textField.getText();
	            textField.setText(display + "6");
        	}
        }
    }
    
    class ListenSeven implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
        	if(newCycle) {
        		textField.setText("7");
        		newCycle = false;
        	}
        	else {
	            String display = textField.getText();
	            textField.setText(display + "7");
        	}
        }
    }
    
    class ListenEight implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
        	if(newCycle) {
        		textField.setText("8");
        		newCycle = false;
        	}
        	else {
	            String display = textField.getText();
	            textField.setText(display + "8");
        	}
        }
    }
    
    class ListenNine implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
        	if(newCycle) {
        		textField.setText("9");
        		newCycle = false;
        	}
        	else {
	            String display = textField.getText();
	            textField.setText(display + "9");
        	}
        }
    }
    
    class ListenZero implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
        	if(newCycle) {
        		textField.setText("0");
        		newCycle = false;
        	}
        	else {
	            String display = textField.getText();
	            textField.setText(display + "0");
        	}
        }
    }
    
    class ListenEqual implements ActionListener { //prints equal sign
        public void actionPerformed(ActionEvent e) {
            String display = textField.getText();
            textField.setText(display + "=");
        }
    }
    
    class ListenAdd implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String display = textField.getText();
            textField.setText(display + "+");
        }
    }
    
    class ListenSub implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String display = textField.getText();
            textField.setText(display + "-");
        }
    }
    
    class ListenMult implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String display = textField.getText();
            textField.setText(display + "*");
        }
    }
    
    class ListenDiv implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String display = textField.getText();
            textField.setText(display + "/");
        }
    }
    
    class ListenMod implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String display = textField.getText();
            textField.setText(display + "%");
        }
    }
    
    class ListenPow implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String display = textField.getText();
            textField.setText(display + "^");
        }
    }
    
    class ListenEval implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String display = textField.getText();
            try {
	            if( ! display.isEmpty()) {
	            	Evaluator calEvaluator = new Evaluator();
	            	//calling evaluate method in calEvaluator object and passing the result into textField
	            	textField.setText(calEvaluator.evaluate(display)); 
	            	//showing big numbers by hovering mouse over text box
	            	textField.setToolTipText(textField.getText());
	            	newCycle = true;
	            }
	            else
	            	return;
            } catch(Exception e1) {
            	textField.setText("Error");
            }
        }
    }
    
    class ListenLeftPar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String display = textField.getText();
            textField.setText(display + "(");
        }
    }
    
    class ListenRightPar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String display = textField.getText();
            textField.setText(display + ")");
        }
    }
    
    class ListenBackSpace implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	String display = textField.getText();
        	if( ! display.isEmpty())
        		textField.setText(display.substring(0, display.length()-1));
        	else
        		return;
        }
    }
    
    class ListenX implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
        	if(newCycle) {
        		textField.setText("x");
        		newCycle = false;
        	}
        	else {
	            String display = textField.getText();
	            textField.setText(display + "x");
        	}
        }
    }
    
    class ListenY implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
        	if(newCycle) {
        		textField.setText("y");
        		newCycle = false;
        	}
        	else {
	            String display = textField.getText();
	            textField.setText(display + "y");
        	}
        }
    }
    
    class ListenZ implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
        	if(newCycle) {
        		textField.setText("z");
        		newCycle = false;
        	}
        	else {
	            String display = textField.getText();
	            textField.setText(display + "z");
        	}
        }
    }
}
