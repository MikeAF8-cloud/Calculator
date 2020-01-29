package calculator;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;

public class Calculator implements ActionListener{

	private String firstValue;
	private String secondValue;
	
	private String operation;
	private boolean toggleOp;
	
	DecimalFormat df = new DecimalFormat("0.############");
	
	private final static String[] operations = new String[] {"+", "-", "*", "/"};
	private final static String[] calcFunc = new String[] {"CE", "C", "<-", "="};
	private final static String[] numConv = new String[] {".", "%", "+/-"};
	private final static String[] operationsAdv = new String[] {"x^2", "sqrt(x)", "1/x"};
	
	private JFrame frame;

	private final JPanel panel = new JPanel();
	private JTextField textField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Calculator window = new Calculator();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	public Calculator() {
		this.operation = "0";
		initialize();
	}

	private void createGui() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.BOLD, 20));
		frame.setBounds(100, 100, 500, 691);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}
	
	private void createTextField() {
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.BOLD, 50));
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setBounds(10, 10, 466, 94);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		textField.setText("0");
	}
	
	private boolean checkNull(String text) {
		return (text.isBlank() || text.equals("0"));
	}
	
	private boolean checkInt(double ans) {
		return (ans == Math.floor(ans) && !Double.isInfinite(ans));
	}
	
	private void addDigit(String num) {
		String text = textField.getText();
		
		if (checkNull(text) || this.toggleOp == true) {
			this.toggleOp = false;
			textField.setText(num);
		} else if (text.length() < 12) {
			textField.setText(text + num);					
		}
	}
	
	private void doArithmetic() {
		double ans = 0;
		switch (operation) {
			case "+":
				ans = Double.parseDouble(this.secondValue) + Double.parseDouble(textField.getText());
				break;
			case "-":
				ans = Double.parseDouble(this.secondValue) - Double.parseDouble(textField.getText());
				break;
			case "*":
				ans = Double.parseDouble(this.secondValue) * Double.parseDouble(textField.getText());
				break;
			case "/":
				ans = Double.parseDouble(this.secondValue) / Double.parseDouble(textField.getText());
				break;
			default:
				ans = Double.parseDouble(this.textField.getText());
		}
		
		if(checkInt(ans)) {
			textField.setText(String.valueOf(Math.round(ans)));
		} else {
			textField.setText(df.format(ans));
		}
		
		this.secondValue = textField.getText();
	}
	
	private void calcSetting(String command) {
		switch(command) {
			case "C":
				this.secondValue = "";
				this.toggleOp = false;
				this.operation = "0";
				textField.setText("0");
				break;
			case "CE":
				textField.setText("0");
				break;
			case "<-":
				int textLength = textField.getText().length();
				try {
					if (textLength != 1) {
						textField.setText(textField.getText(0, textLength - 1));
					} else {
						textField.setText("0");
					}
					
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				break;
			case "=":
				doArithmetic();
				this.toggleOp = false;
				this.operation = "0";
				break;
		}
	}
	
	private void calcConv(String command) {
		switch(command) {
			case ".":
				if (!textField.getText().contains(".")) {
					textField.setText(textField.getText() + ".");				
				}
				break;
			case "+/-":
				double text = Double.parseDouble(textField.getText());
				text *= -1;
				if(checkInt(text)) {
					textField.setText(String.valueOf(Math.round(text)));
				} else {
					textField.setText(df.format(text));
				}
				break;
			case "%":
				double num = Double.parseDouble(textField.getText());
				if (num != 0) {
					num /= 100;
					textField.setText(String.valueOf(num));
				}
				break;
		}
	}
	
	private void doAdvArithmetic(String command) {
		double ans = Double.parseDouble(textField.getText());
		switch (command) {
			case "x^2":
				ans *= ans;
				break;
			case "sqrt(x)":
				ans = Math.sqrt(ans);
				break;
			case "1/x":
				ans = 1 / ans;
				break;
		}

		if(checkInt(ans)) {
			textField.setText(String.valueOf(Math.round(ans)));
		} else {
			textField.setText(df.format(ans));
		}
	}
	
	private void calcFunctions() {		
		JButton btn_clearEverything = new JButton("CE");
		btn_clearEverything.setActionCommand("CE");
		btn_clearEverything.addActionListener(this);
		btn_clearEverything.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_clearEverything.setBounds(128, 129, 113, 79);
		frame.getContentPane().add(btn_clearEverything);
		
		JButton btn_clear = new JButton("C");
		btn_clear.setActionCommand("C");
		btn_clear.addActionListener(this);
		btn_clear.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_clear.setBounds(246, 129, 113, 79);
		frame.getContentPane().add(btn_clear);
		
		JButton btn_backspace = new JButton("<-");
		btn_backspace.setActionCommand("B");
		btn_backspace.addActionListener(this);
		btn_backspace.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_backspace.setBounds(363, 129, 113, 79);
		frame.getContentPane().add(btn_backspace);
		
		JButton btn_equals = new JButton("=");
		btn_equals.setActionCommand("=");
		btn_equals.addActionListener(this);
		btn_equals.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_equals.setBounds(363, 562, 113, 79);
		frame.getContentPane().add(btn_equals);

	}
	
	private void calcNumbers() {
		JButton btn_0 = new JButton("0");
		btn_0.addActionListener(this);
		btn_0.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_0.setBounds(128, 562, 113, 79);
		btn_0.setActionCommand("0");
		frame.getContentPane().add(btn_0);
		
		JButton btn_1 = new JButton("1");
		btn_1.addActionListener(this);
		btn_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_1.setBounds(10, 473, 113, 79);
		btn_1.setActionCommand("1");
		frame.getContentPane().add(btn_1);
		
		JButton btn_2 = new JButton("2");
		btn_2.addActionListener(this);
		btn_2.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_2.setBounds(128, 473, 113, 79);
		btn_2.setActionCommand("2");
		frame.getContentPane().add(btn_2);
		
		JButton btn_3 = new JButton("3");
		btn_3.addActionListener(this);
		btn_3.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_3.setBounds(246, 473, 113, 79);
		btn_3.setActionCommand("3");
		frame.getContentPane().add(btn_3);
		
		JButton btn_4 = new JButton("4");
		btn_4.addActionListener(this);
		btn_4.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_4.setBounds(10, 387, 113, 79);
		btn_4.setActionCommand("4");
		frame.getContentPane().add(btn_4);
		
		JButton btn_5 = new JButton("5");
		btn_5.addActionListener(this);
		btn_5.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_5.setBounds(128, 387, 113, 79);
		btn_5.setActionCommand("5");
		frame.getContentPane().add(btn_5);
		
		JButton btn_6 = new JButton("6");
		btn_6.addActionListener(this);
		btn_6.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_6.setBounds(246, 387, 113, 79);
		btn_6.setActionCommand("6");
		frame.getContentPane().add(btn_6);
		
		JButton btn_7 = new JButton("7");
		btn_7.addActionListener(this);
		btn_7.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_7.setBounds(10, 300, 113, 79);
		btn_7.setActionCommand("7");
		frame.getContentPane().add(btn_7);
		
		JButton btn_8 = new JButton("8");
		btn_8.addActionListener(this);
		btn_8.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_8.setBounds(128, 300, 113, 79);
		btn_8.setActionCommand("8");
		frame.getContentPane().add(btn_8);
		
		JButton btn_9 = new JButton("9");
		btn_9.addActionListener(this);
		btn_9.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_9.setBounds(246, 300, 113, 79);
		btn_9.setActionCommand("9");
		frame.getContentPane().add(btn_9);
	}
	
	private void calcBasicArithmetic() {
		JButton btn_divide = new JButton("/");
		btn_divide.addActionListener(this);
		btn_divide.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_divide.setActionCommand("/");
		btn_divide.setBounds(363, 214, 113, 79);
		frame.getContentPane().add(btn_divide);
		
		JButton btn_multiply = new JButton("x");
		btn_multiply.addActionListener(this);
		btn_multiply.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_multiply.setActionCommand("*");
		btn_multiply.setBounds(363, 300, 113, 79);
		frame.getContentPane().add(btn_multiply);
		
		JButton btn_minus = new JButton("-");
		btn_minus.addActionListener(this);
		btn_minus.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_minus.setActionCommand("-");
		btn_minus.setBounds(363, 387, 113, 79);
		frame.getContentPane().add(btn_minus);
		
		JButton btn_plus = new JButton("+");
		btn_plus.addActionListener(this);
		btn_plus.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_plus.setActionCommand("+");
		btn_plus.setBounds(363, 473, 113, 79);
		frame.getContentPane().add(btn_plus);
	}
	
	private void calcNumberConv() {
		JButton btn_signFlip = new JButton("+/-");
		btn_signFlip.setActionCommand("+/-");
		btn_signFlip.addActionListener(this);
		btn_signFlip.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_signFlip.setBounds(10, 562, 113, 79);
		frame.getContentPane().add(btn_signFlip);
		
		JButton btn_decimal = new JButton(".");
		btn_decimal.setActionCommand(".");
		btn_decimal.addActionListener(this);
		btn_decimal.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_decimal.setBounds(246, 562, 113, 79);
		frame.getContentPane().add(btn_decimal);
		
		JButton btn_percent = new JButton("%");
		btn_percent.setActionCommand("%");
		btn_percent.addActionListener(this);
		btn_percent.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_percent.setBounds(10, 129, 113, 79);
		frame.getContentPane().add(btn_percent);
	}
	
	private void calcAdvArithmetic() {
		JButton btn_squared = new JButton("x^2");
		btn_squared.setActionCommand("x^2");
		btn_squared.addActionListener(this);
		btn_squared.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_squared.setBounds(128, 214, 113, 79);
		frame.getContentPane().add(btn_squared);
		
		JButton btn_sqrt = new JButton("sqrt(x)");
		btn_sqrt.setActionCommand("sqrt(x)");
		btn_sqrt.addActionListener(this);
		btn_sqrt.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_sqrt.setBounds(247, 214, 113, 79);
		frame.getContentPane().add(btn_sqrt);
		
		JButton btn_reciprocal = new JButton("1/x");
		btn_reciprocal.setActionCommand("1/x");
		btn_reciprocal.addActionListener(this);
		btn_reciprocal.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_reciprocal.setBounds(10, 214, 113, 79);
		frame.getContentPane().add(btn_reciprocal);
	}
	
	private void createButtons() {
		calcFunctions();
		calcNumbers();
		calcBasicArithmetic();
		calcNumberConv();
		calcAdvArithmetic();
	}

	private void initialize() {
		createGui();
		createTextField();
		createButtons();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().length() == 1 && Character.isDigit(e.getActionCommand().charAt(0))) {
			addDigit(e.getActionCommand());
		} else if (Arrays.stream(operations).anyMatch(e.getActionCommand()::equals)) {
			this.toggleOp = true;
			doArithmetic();
			this.operation = e.getActionCommand();
		} else if (Arrays.stream(calcFunc).anyMatch(e.getActionCommand()::equals)) {
			calcSetting(e.getActionCommand());
		} else if (Arrays.stream(numConv).anyMatch(e.getActionCommand()::equals)) {
			calcConv(e.getActionCommand());
		} else if (Arrays.stream(operationsAdv).anyMatch(e.getActionCommand()::equals)) {
			doAdvArithmetic(e.getActionCommand());
		}
		
	}
}
