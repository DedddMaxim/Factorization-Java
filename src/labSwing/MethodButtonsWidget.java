package labSwing;

import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
* Choosing a method widget
* @author Tyufyakov Maxim
* @version 1.0
*/
public class MethodButtonsWidget extends JPanel {
	/**
	* ID
	* @since 1.0
	*/
	private static final long serialVersionUID = 1L;
	
	/**
	* buttons data
	* @since 1.0
	*/
	private ButtonGroup bGroup = new ButtonGroup();
	
	/**
	* get selected name
	* @since 1.0
	* @return button name
	*/
	public String getSelectButtonName() {
		
		// all buttons
		for (Enumeration<AbstractButton> buttons = bGroup.getElements();
				buttons.hasMoreElements();)
		{
			AbstractButton button = buttons.nextElement(); 
			// if selected
			if (button.isSelected()) return button.getName(); 
		}
		// isn't selected
		return "";
	}

	/**
	* interface constructor
	* @since 1.0
	*/
	public MethodButtonsWidget() {
		
		// method's buttons
		JRadioButton bPrime = new JRadioButton("Простой метод");
		bPrime.setName("bPrime");
		bPrime.setSelected(true);
		
		JRadioButton bPolard0 = new JRadioButton("Метод Полларда p-0");
		bPolard0.setName("bPolard0");
		
		JRadioButton bPolard1 = new JRadioButton("Метод Полларда p-1");
		bPolard1.setName("bPolard1");
		
		JRadioButton bBent = new JRadioButton("Метод Бента");
		bBent.setName("bBent");
		
		JRadioButton bPollardMonteCarlo = new JRadioButton("Метод Полларда-Монте-Карло");
		bPollardMonteCarlo.setName("bPollardMonteCarlo");
							
		// combining the buttons
		bGroup.add(bPrime);
		bGroup.add(bPolard0);
		bGroup.add(bPolard1);
		bGroup.add(bBent);
		bGroup.add(bPollardMonteCarlo);
				
		// Creating widget of radio buttons
		JPanel buttonGroupPanel = new JPanel();
		// set the layout (vertical)
		buttonGroupPanel.setLayout(new BoxLayout(buttonGroupPanel, BoxLayout.Y_AXIS));		
		
		// add buttons in widget
		buttonGroupPanel.add(bPrime);
		buttonGroupPanel.add(bPolard0);
		buttonGroupPanel.add(bPolard1);
		buttonGroupPanel.add(bBent);
		buttonGroupPanel.add(bPollardMonteCarlo);
				
		add(buttonGroupPanel);
	}
	
}
