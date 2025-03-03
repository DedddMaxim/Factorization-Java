package labSwing;

import java.awt.*;
import java.math.BigInteger;

import javax.swing.*;

/**
* Input and output widget
* @author Tyufyakov Maxim
* @version 1.0
*/
public class DataWidget extends JPanel {
	/**
	* ID
	* @since 1.0
	*/
	private static final long serialVersionUID = 1L;
	
	/**
	* Input Text Field
	* @since 1.0
	*/
	private JTextField inputField = new JTextField();
	
	/**
	* get input text
	* @return input text
	* @since 1.0
	*/
	public String getInputData() {
		
		return inputField.getText();
	}
	
	/**
	* Output Text Field
	* @since 1.0
	*/
	private JTextField outputField = new JTextField();
	
	/**
	* output answer
	* @param factors dividers
	* @since 1.0
	*/
	public void setOutputData(BigInteger[] factors) {
		String text = "";
		for(BigInteger factor : factors) {
			text += factor.toString() + "; ";
		}
		outputField.setText(text);
	}

	/**
	* interface constructor
	* @since 1.0
	*/
	public DataWidget() {
		
		// Creating widget
		JPanel textFieldPanel = new JPanel();
		// set the layout (vertical)
		textFieldPanel.setLayout(new BoxLayout(textFieldPanel, BoxLayout.Y_AXIS));	
		
		// input field
		inputField.setColumns(15);
		
		// output field
		outputField.setColumns(15);
		outputField.setEditable(false);
		
		// add fields in widget
		textFieldPanel.add(new Label("Введите целое число не меньше 2:"));
		textFieldPanel.add(inputField);
		textFieldPanel.add(new Label("Ответ:"));
		textFieldPanel.add(outputField);
				
		add(textFieldPanel);
	}	
}
