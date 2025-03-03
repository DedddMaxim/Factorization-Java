package labSwing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
* Main Page
* @author Tyufyakov Maxim
* @version 1.0
*/
public class MainPage extends JFrame {
	/**
	* ID
	* @since 1.0
	*/
	private static final long serialVersionUID = 1L;
	
	/**
	* method's buttons
	* @since 1.0
	*/
	private MethodButtonsWidget methodButtons = new MethodButtonsWidget();
	
	/**
	* Input and output widget
	* @since 1.0
	*/
	private DataWidget data = new DataWidget();
	
	/**
	* interface constructor
	* @since 1.0
	*/
	MainPage()
	{
		
		// Creating Object of JPanel class
		JPanel mainPanel = new JPanel();
		// set the layout (North, South, West, East)
		mainPanel.setLayout(new BorderLayout());

		// add a Top Label
		JLabel label = 
				new JLabel("Программа предназначена для разложения чисел на простые множители.", 
							SwingConstants.CENTER);
		mainPanel.add(label, BorderLayout.NORTH);

		// add calculate button
		JButton bCalc = new JButton("Факторизовать");
		bCalc.addActionListener(calculateListener);
		mainPanel.add(bCalc, BorderLayout.SOUTH);
				
		// add widget with methods
		mainPanel.add(methodButtons, BorderLayout.WEST);
		
		// add widget with methods
		mainPanel.add(data, BorderLayout.CENTER);

		// add the panel object which refer to the JPanel
		add(mainPanel);
	}
	
	/**
	* calculate button action
	* @since 1.0
	*/
	private ActionListener calculateListener = new ActionListener() {
		
		// on press
		@Override
		public void actionPerformed(ActionEvent e) {
			
			// try convert input
			BigInteger num = null;
			try {
				num = new BigInteger(data.getInputData());
				
				// get active method
				String buttonName = methodButtons.getSelectButtonName();
				
				// find method, output
				try {
					switch (buttonName) {
				
						case "bPrime": {
							data.setOutputData(Factorization.simple(num));
							break;
						}
						
						case "bPolard0": {
							data.setOutputData(Factorization.pollard_p0(num));
							break;
						}
						
						case "bPolard1": {
							data.setOutputData(Factorization.pollard_p1(num));
							break;
						}
						
						case "bBent": {
							data.setOutputData(Factorization.benta(num));
							break;
						}
						
						case "bPollardMonteCarlo": {
							data.setOutputData(Factorization.pollarda_monte_carlo(num));
							break;
						}
						
					}
				// factorization error
				} catch (UncorrectedDataException err) {
					// err message
					JOptionPane.showMessageDialog(
							MainPage.this,
							err.getMessage(),
		                    "Ошибка",
		                    JOptionPane.ERROR_MESSAGE
		                    );
				}
						
			}
			
			// convert err
			catch(NumberFormatException err) {
				// err message
				JOptionPane.showMessageDialog(
						MainPage.this,
						"Введено не целое число.",
	                    "Ошибка",
	                    JOptionPane.ERROR_MESSAGE
	                    );
				}
		}
	}; 
}
