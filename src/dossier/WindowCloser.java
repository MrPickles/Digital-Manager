package dossier;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

/**
 * @name WindowCloser
 * @author Andrew Liu
 * @version March 2013
 * @school Richard Montgomery High School
 * @IDE Eclipse
 * 
 *      This class handles the case where the user prematurely exits out of the
 *      program. It will warn that no data will be saved and give the user the
 *      option not to close the program.
 * 
 */
public class WindowCloser extends WindowAdapter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
	 */
	public void windowClosing(WindowEvent e) {
		int exitChoice = JOptionPane
				.showConfirmDialog(null,
						"Are you sure you want to exit? Any unsaved data will be lost.");
		if (exitChoice == JOptionPane.YES_OPTION) {
			System.exit(0);// will only exit if user confirms
		}// end if
	}// end windowClosing
}// end WindowCloser class