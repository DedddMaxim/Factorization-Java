// Java program to illustrate the BorderLayout
package labSwing;

import javax.swing.*;

/**
* Main (entry point)
* @author Tyufyakov Maxim
* @version 1.0
*/
class GFG {
	
	/**
	* Driver code
	* @since 1.0
	* @param args arguments
	*/
	public static void main(String[] args)
	{
		// calling the constructor
		MainPage mainPage = new MainPage();
		
		// Application name 
		mainPage.setTitle("Факторизация");
		// Function to close the operation of JFrame.
		mainPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Function to set size of JFrame.
		mainPage.setSize(500, 250);
		// Function to set visible status of JFrame.
		mainPage.setVisible(true);
	}
}








