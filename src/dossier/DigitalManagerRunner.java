package dossier;

/**
 * @name DigitalManagerRunner
 * @author Andrew Liu
 * @version March 2013
 * @school Richard Montgomery High School
 * @IDE Eclipse
 * 
 *      This class runs the digital manager. It creates an instance of the
 *      DigitalManager object and activates the GUI.
 * 
 */
public class DigitalManagerRunner {

	/**
	 * @Precondition user knows the file path of the text file that will store
	 *               the program data, if he or she wishes to use the file I/O
	 *               and saving features
	 * @Postcondition Employee data saved to file given that a valid file path
	 *                was provided
	 * @param args
	 * 
	 *            Main method. Creates an instance of Digital Manager and opens
	 *            the GUI. For reference, the original file path used to test
	 *            the Digital Manager is
	 *            C:/Users/andrew2/Documents/digitalmanager.txt
	 */
	public static void main(String[] args) {
		DigitalManager manager = DigitalManager.getInstance();
		manager.setVisible(true);
	}// end main
}// end DigitalManagerRunner class