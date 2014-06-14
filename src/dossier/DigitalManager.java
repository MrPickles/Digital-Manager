package dossier;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/**
 * @name DigitalManager
 * @author Andrew Liu
 * @version March 2013
 * @school Richard Montgomery High School
 * @IDE Eclipse
 * 
 *      This class outlines the graphical user interface with which a user can
 *      choose to view, add, remove, and add Employees and the associated
 *      variables present in the Employee class. Upon opening and closing, the
 *      DigitalManager object will read and write all of the employee
 *      information to a specified text file to enable data saving for future
 *      use.
 */
@SuppressWarnings("serial")
public class DigitalManager extends JFrame implements ActionListener {

	/**
	 * the width (in pixels) of the GUI
	 */
	public static final int WIDTH = 600;

	/**
	 * the height (in pixels) of the GUI
	 */
	public static final int HEIGHT = 400;

	/**
	 * the maximum number of lines for any text box in the GUI
	 */
	public static final int NUMBER_OF_LINES = 10;

	/**
	 * the maximum characters per line for any text box in the GUI
	 */
	public static final int CHARACTERS_PER_LINE = 40;

	/**
	 * the instance of the Main Menu
	 */
	private JPanel mainMenu = new JPanel();

	/**
	 * the JButton section of the Employee List Menu
	 */
	private JPanel employeeListButtonMenu = new JPanel();

	/**
	 * the JButton section of the Management Menu
	 */
	private JPanel employeeManagementButtonMenu = new JPanel();

	/**
	 * the current menu that the GUI is displaying
	 */
	private JPanel currentPanel;

	/**
	 * the content pane of the GUI
	 */
	private Container view = getContentPane();

	/**
	 * an instance of the Digital Manager object (the class constructor is
	 * private as to only allow one instance of the DigitalManager object)
	 */
	private static DigitalManager manager;

	/**
	 * an instance of an employee the user has currently selected
	 */
	private static Employee currentEmployee;

	/**
	 * the list of all the employees stored by the DigitalManager object
	 */
	private static ArrayList<Employee> employeeList = new ArrayList<Employee>();

	/**
	 * the file-path of the text file that stores the employee information that
	 * the program uses
	 */
	private static String filepath;

	/**
	 * @Precondition none
	 * @Postcondition instantiates the DigitalManager if not done already,
	 *                returns the only instance of DigitalManager
	 * @return instance of the DigitalManager
	 */
	public static DigitalManager getInstance() {
		if (manager == null) {
			manager = new DigitalManager();
		}
		return manager;
	}

	/**
	 * @Precondition none
	 * @Postcondition creates DigitalManager GUI, GUI displays main menu, calls
	 *                readFromFile method
	 */
	private DigitalManager() {
		setTitle("Digital Manager");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowCloser());

		// setting up the main menu
		JPanel buttonMenu = new JPanel();
		buttonMenu.setLayout(new FlowLayout());
		JButton add = new JButton("Add New Employee");
		add.addActionListener(this);
		buttonMenu.add(add);
		JButton manage = new JButton("View Current Employees");
		manage.addActionListener(this);
		buttonMenu.add(manage);
		JButton exit = new JButton("Save and Exit");
		exit.addActionListener(this);
		buttonMenu.add(exit);

		JTextArea greetings = new JTextArea(NUMBER_OF_LINES,
				CHARACTERS_PER_LINE);
		greetings
				.setText("Welcome to Digital Manager, the program that allows you "
						+ "to\nkeep track of all your employees and manage their tasks!"
						+ "\n\nTo get started, click on the \"Add New Employee\" button\nor "
						+ "go to the Employee List if you already have saved data");
		JPanel subpanel = new JPanel();
		subpanel.setLayout(new FlowLayout());
		subpanel.add(greetings);

		JLabel heading = new JLabel("Main Menu");
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new FlowLayout());
		headerPanel.add(heading);

		mainMenu.setLayout(new BorderLayout());
		mainMenu.add(buttonMenu, BorderLayout.SOUTH);
		mainMenu.add(subpanel, BorderLayout.CENTER);
		mainMenu.add(headerPanel, BorderLayout.NORTH);
		view.add(mainMenu);

		// setting up the button part of the list menu
		employeeListButtonMenu.setLayout(new FlowLayout());
		JButton select = new JButton("Select Employee");
		select.addActionListener(this);
		employeeListButtonMenu.add(select);
		JButton returnToMainMenu = new JButton("Return to Main Menu");
		returnToMainMenu.addActionListener(this);
		employeeListButtonMenu.add(returnToMainMenu);

		// setting up the button part of employee menu
		employeeManagementButtonMenu.setLayout(new FlowLayout());
		JButton remove = new JButton("Remove Employee");
		remove.addActionListener(this);
		employeeManagementButtonMenu.add(remove);
		JButton edit = new JButton("Edit Employee");
		edit.addActionListener(this);
		employeeManagementButtonMenu.add(edit);
		JButton back = new JButton("Back to Employee List");// back to list menu
		back.addActionListener(this);
		employeeManagementButtonMenu.add(back);
		view.add(mainMenu);
		currentPanel = mainMenu;
		readFromFile();
	}// end default constructor

	/**
	 * @Precondition user has a valid file path to the text file that stores
	 *               employee data
	 * @Postcondition employee data read from text file and stored into
	 *                DigitalManager object
	 */
	private static void readFromFile() {
		filepath = JOptionPane
				.showInputDialog("Enter the file path of the file you wish to read from:");
		try {
			File f = new File(filepath);
			Scanner sc = new Scanner(f);
			while (sc.hasNextLine()) {
				String name = sc.nextLine();
				String title = sc.nextLine();
				ArrayList<Task> unfinishedTasks = new ArrayList<Task>();
				String task = sc.nextLine();// skip blank line
				task = sc.nextLine();// start of current tasks; blank if none
				if (!task.equals("")) {
					while (!task.equals("")) {
						Task newTask = new Task(task);
						unfinishedTasks.add(newTask);
						task = sc.nextLine();// blank line
					}// end while
				} else {
					task = sc.nextLine();// blank line
				}// end if-else
				task = sc.nextLine();// start of completed tasks; blank if none
				ArrayList<Task> finishedTasks = new ArrayList<Task>();
				if (!task.equals("")) {
					while (!task.equals("")) {
						Task newTask = new Task(task, true);
						finishedTasks.add(newTask);
						task = sc.nextLine();// blank line
					}// end while
				} else {
					task = sc.nextLine();// blank line
				}// end if-else
				ArrayList<Task> taskList = new ArrayList<Task>();
				for (Task t : unfinishedTasks) {
					taskList.add(t);
				}// end for
				for (Task t : finishedTasks) {
					taskList.add(t);
				}// end for
				Employee newEmployee = new Employee(name, title, taskList);
				employeeList.add(newEmployee);
			}// end while
			sc.close();
		} catch (FileNotFoundException e) {
			JOptionPane
					.showMessageDialog(null,
							"File not found! The Digital Manager will continue without saving.");
			filepath = "";
		} catch (NullPointerException e) {
			JOptionPane
					.showMessageDialog(null,
							"Incorrect syntax! The Digital Manager will continue without saving.");
			employeeList = new ArrayList<Employee>();// refresh list
		}// end try-catch-catch
	}// end readFromFile

	/**
	 * @Precondition none
	 * @Postcondition current content pane of the GUI removed, static JPanel
	 *                variable currentPanel changed to be the copy of the new
	 *                main menu, GUI displays main menu
	 */
	private void goToMainMenu() {
		view.remove(currentPanel);
		view.add(mainMenu);
		currentPanel = mainMenu;
		view.revalidate();
		view.repaint();
	}// end goToMainMenu

	/**
	 * @Precondition none
	 * @Postcondition employeeList has an additional entry as chosen by the
	 *                user, employee has a valid name as described by
	 *                isValidName
	 */
	private void addEmployee() {
		String name = JOptionPane.showInputDialog("Enter Employee Name:");
		if (name == null) {
			return;
		}// end if
		while (!isValidName(name)) {
			JOptionPane
					.showMessageDialog(null,
							"Invalid input; name can only use letters and must be unique.");
			name = JOptionPane.showInputDialog("Enter Employee Name:");
		}// end while
		String title = JOptionPane.showInputDialog("Enter Employee Title:");
		if (title == null) {
			return;
		}// end if
		while (title.length() < 1) {
			JOptionPane.showMessageDialog(null,
					"Invalid input; you must enter something!");
			title = JOptionPane.showInputDialog("Enter Employee Title:");
		}// end while
		Employee newEmployee = new Employee(name, title);
		employeeList.add(newEmployee);
	}// end addEmployee

	/**
	 * @Precondition none
	 * @Postcondition checks whether the specified name is a unique employee
	 *                name and has no numbers
	 * @param name
	 * @return whether the specified name is acceptable to be added to the
	 *         employeeList
	 */
	private static boolean isValidName(String name) {
		if (!name.matches("\\D+")) {// \D+ is one or more non-number characters
			return false;
		}// end if
		for (Employee e : employeeList) {
			if (name.equals(e.getName())) {
				return false;
			}// end if
		}// end for
		return true;
	}// end isValidName

	/**
	 * @Precondition employeeList has entries
	 * @Postcondition employeeList entries sorted into alphabetical order by
	 *                name
	 */
	private static void alphabetize() {
		ArrayList<Employee> temp = new ArrayList<Employee>();
		while (employeeList.size() > 0) {
			Employee first = employeeList.get(0);
			for (Employee e : employeeList) {
				if (e.getName().compareTo(first.getName()) < 0) {
					first = e;
				}// end if
			}// end for
			temp.add(first);
			employeeList.remove(employeeList.indexOf(first));
		}// end while
		employeeList = temp;
	}// end alphabetize

	/**
	 * @Precondition there are registered employees, employeeList has entries
	 * @Postcondition current content pane of the GUI removed, currentPanel
	 *                changed to be the copy of the employee list menu, GUI
	 *                displays employee list menu
	 */
	private void goToEmployeeListMenu() {
		if (employeeList.size() == 0) {
			JOptionPane.showMessageDialog(null, "You have no employees!");
			return;
		}// end if
		alphabetize();
		String text = "Current Employees:";
		int index = 1;
		for (Employee e : employeeList) {
			text += ("\n\t" + index + ") " + e.getName());
			index++;
		}// end for
		JTextArea infoTextBox = new JTextArea(NUMBER_OF_LINES,
				CHARACTERS_PER_LINE);
		infoTextBox.setText(text);
		JScrollPane infoScrollBox = new JScrollPane(infoTextBox);
		infoScrollBox
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		infoScrollBox
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		JPanel subpanel = new JPanel();
		subpanel.setLayout(new FlowLayout());
		subpanel.add(infoScrollBox);

		JLabel heading = new JLabel("Employee List");
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new FlowLayout());
		headerPanel.add(heading);

		JPanel newPanel = new JPanel();
		newPanel.setLayout(new BorderLayout());
		newPanel.add(employeeListButtonMenu, BorderLayout.SOUTH);
		newPanel.add(subpanel, BorderLayout.CENTER);
		newPanel.add(headerPanel, BorderLayout.NORTH);

		view.remove(currentPanel);
		view.add(newPanel);
		currentPanel = newPanel;
		view.revalidate();
		view.repaint();
	}// end goToEmployeeListMenu

	/**
	 * @Precondition there are registered employees, employeeList has entries,
	 *               currentEmployee has the employee the user wishes to remove
	 * @Postcondition selected employee removed from the employeeList
	 */
	private void removeEmployee() {
		employeeList.remove(employeeList.indexOf(currentEmployee));
		JOptionPane.showMessageDialog(null, "Employee removed!");
		goToMainMenu();
	}// end removeEmployee

	/**
	 * @Precondition employeeList has entries, user provides valid input
	 * @Postcondition currentEmployee matches the user-specified employee
	 * @return whether the method successfully achieved the postcondition
	 */
	private static boolean selectEmployee() {
		boolean endLoop;
		do {
			String inputNumber = JOptionPane
					.showInputDialog("Please enter the corresponding number of the employee that you wish to manage.");
			if (inputNumber == null) {
				return false;
			}// end if
			endLoop = isInIndexRange(inputNumber);
			if (!endLoop) {
				JOptionPane
						.showMessageDialog(
								null,
								"Invalid input: the number you enter must be one of the listed employee numbers.");
			}// end if
		} while (!endLoop); // end do-while
		return true;
	}// end selectEmployee

	/**
	 * @Precondition selectEmployee method called
	 * @Postcondition user input check to see if the number entered represents a
	 *                listed employee.
	 * @param index
	 * @return whether the user entered valid input
	 */
	private static boolean isInIndexRange(String index) {
		if (!index.matches("\\d+")) {// \d+ is one or more number characters
			return false;
		}// end if
		int indexAsInt = Integer.parseInt(index);
		if (indexAsInt < 1) {
			return false;
		} else if (indexAsInt > employeeList.size()) {
			return false;
		} else {
			currentEmployee = employeeList.get(indexAsInt - 1);
			return true;
		}// end if-else-if-else
	}// end isInIndexRange

	/**
	 * @Precondition static variable currentEmployee contains the employee the
	 *               user wishes to edit
	 * @Postcondition current content pane of the GUI removed, currentPanel
	 *                changed to be the copy of the management menu, GUI
	 *                displays management menu
	 */
	private void goToManageMenu() {
		JTextArea infoTextBox = new JTextArea(NUMBER_OF_LINES,
				CHARACTERS_PER_LINE);
		infoTextBox.setText(currentEmployee.toString());
		JScrollPane infoScrollBox = new JScrollPane(infoTextBox);
		infoScrollBox
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		infoScrollBox
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		JPanel subpanel = new JPanel();
		subpanel.setLayout(new FlowLayout());
		subpanel.add(infoScrollBox);

		JLabel heading = new JLabel("Employee Information");
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new FlowLayout());
		headerPanel.add(heading);

		JPanel newPanel = new JPanel();
		newPanel.setLayout(new BorderLayout());
		newPanel.add(employeeManagementButtonMenu, BorderLayout.SOUTH);
		newPanel.add(subpanel, BorderLayout.CENTER);
		newPanel.add(headerPanel, BorderLayout.NORTH);

		view.remove(currentPanel);
		view.add(newPanel);
		currentPanel = newPanel;
		view.revalidate();
		view.repaint();
	}// end goToManageMenu

	/**
	 * @Precondition there are registered employees, employeeList has entries,
	 *               currentEmployee has the employee the user wishes to remove
	 * @Postcondition employee's name and title changed or kept the same as
	 *                specified by the user; employee's tasks added or completed
	 *                as specified by the user
	 */
	private void editEmployee() {
		employeeList.remove(employeeList.indexOf(currentEmployee));
		int choice = JOptionPane.showConfirmDialog(null,
				"Do you wish to edit the employee's name?");
		if (choice == JOptionPane.YES_OPTION) {
			changeName();
		}// end if
		if (choice == JOptionPane.YES_OPTION || choice == JOptionPane.NO_OPTION) {
			choice = JOptionPane.showConfirmDialog(null,
					"Would you like to edit the employee's title?");
			if (choice == JOptionPane.YES_OPTION) {
				changeTitle();
			}// end if
			if (choice == JOptionPane.YES_OPTION
					|| choice == JOptionPane.NO_OPTION) {
				choice = JOptionPane.showConfirmDialog(null,
						"Do you want to give this employee a new task?");
				if (choice == JOptionPane.YES_OPTION) {
					addTask();
				}// end if
				if (choice == JOptionPane.YES_OPTION
						|| choice == JOptionPane.NO_OPTION) {
					choice = JOptionPane
							.showConfirmDialog(null,
									"Do you want mark one of this employee's tasks as complete?");
					if (choice == JOptionPane.YES_OPTION) {
						completeTask();
					}// end if
				}// end if
			}// end if
		}// end if
		employeeList.add(currentEmployee);
		JOptionPane.showMessageDialog(null, "Changes saved!");
		goToEmployeeListMenu();
	}// end editEmployee

	/**
	 * @Precondition none
	 * @Postcondition employee's name changed to what the user specifies,
	 *                employee has a valid name as described by isValidName
	 */
	private void changeName() {
		String name = JOptionPane.showInputDialog("Enter New Employee Name:");
		if (name == null) {
			return;
		}// end if
		while (!isValidName(name)) {
			JOptionPane
					.showMessageDialog(null,
							"Invalid input; name can only use letters and must be unique.");
			name = JOptionPane.showInputDialog("Enter New Employee Name:");
			if (name == null) {
				return;
			}// end if
		}// end while
		currentEmployee.setName(name);
	}// end changeName

	/**
	 * @Precondition none
	 * @Postcondition employee's title changed to whatever the user specifies
	 */
	private void changeTitle() {
		String title = JOptionPane.showInputDialog("Enter Employee Title:");
		if (title == null) {
			return;
		}// end if
		while (title.length() < 1) {
			JOptionPane.showMessageDialog(null,
					"Invalid input; you must enter something!");
			title = JOptionPane.showInputDialog("Enter Employee Title:");
			if (title == null) {
				return;
			}// end if
		}// end while
		currentEmployee.setTitle(title);
	}// end changeTitle

	/**
	 * @Precondition none
	 * @Postcondition new Task object created and added to currentEmployee
	 */
	private void addTask() {
		String task = JOptionPane.showInputDialog("Enter New Task:");
		if (task == null) {
			return;
		}// end if
		Task myTask = new Task(task);
		ArrayList<Task> currentTasks = currentEmployee.getTasks();
		currentTasks.add(myTask);
		currentEmployee.setTasks(currentTasks);
		int choice = JOptionPane.showConfirmDialog(null,
				"Do you want to add another task?");
		if (choice == JOptionPane.YES_OPTION) {
			addTask();
		}// end if
	}// end addTask

	/**
	 * @Precondition Employee has 'incomplete' tasks
	 * @Postcondition selected tasks have their completion status changed from
	 *                'false' to 'true.'
	 */
	private void completeTask() {
		ArrayList<String> unfinishedTaskList = new ArrayList<String>();
		for (Task t : currentEmployee.getTasks()) {
			if (!t.isComplete()) {
				unfinishedTaskList.add(t.getMemo());
			}// end if
		}// end for
		if (unfinishedTaskList.size() == 0) {
			JOptionPane.showMessageDialog(null,
					"There are no unfinished tasks!");
			return;
		}// end if
		String message = "~Select a Task to Complete~";
		int index = 1;
		for (String s : unfinishedTaskList) {
			message += ("\n\t" + index + ") " + s);
			index++;
		}// end for
		message += "\n\tSelect the corresponding number of the task you wish to complete.";
		String choice = JOptionPane.showInputDialog(message);
		if (choice == null) {
			return;
		}// end if
		int choiceInt = -1;
		while (choiceInt == -1) {
			try {
				if (!choice.matches("\\d+")) {// \d+ is at least one number
					throw new Exception();
				}// end if
				choiceInt = Integer.parseInt(choice);
				if ((choiceInt < 1) || (choiceInt > unfinishedTaskList.size())) {
					choiceInt = -1;
					throw new Exception();
				}// end if
			} catch (Exception e) {
				JOptionPane
						.showMessageDialog(
								null,
								"Invalid input; your choice must be a number corresponding to an unfinished task.");
				choice = JOptionPane.showInputDialog(message);
			}// end try-catch
		}// end while
		Task editedTask = new Task(unfinishedTaskList.get(choiceInt - 1));
		ArrayList<Task> revisedTaskList = new ArrayList<Task>();
		for (Task t : currentEmployee.getTasks()) {
			if (!editedTask.getMemo().equals(t.getMemo())) {
				revisedTaskList.add(t);
			}// end if
		}
		editedTask.setComplete(true);
		revisedTaskList.add(editedTask);
		currentEmployee.setTasks(revisedTaskList);
		if (unfinishedTaskList.size() > 1) {
			int repeat = JOptionPane.showConfirmDialog(null,
					"Complete another task?");
			if (repeat == JOptionPane.YES_OPTION) {
				completeTask();
			}// end if
		}// end if
	}// end completeTask

	/**
	 * @Precondition none
	 * @Postcondition Employee data saved into text file given user provided
	 *                valid file path, program is terminated, the algorithm for
	 *                formatting the text file is described in the dossier
	 */
	private void exit() {
		try {
			File f = new File(filepath);
			PrintWriter output;
			output = new PrintWriter(f);
			for (Employee e : employeeList) {
				output.println(e.getName());
				output.println(e.getTitle());
				output.println();// blank line to separate title and tasks
				ArrayList<Task> unfinishedTaskList = new ArrayList<Task>();
				ArrayList<Task> finishedTaskList = new ArrayList<Task>();
				for (Task t : e.getTasks()) {
					if (!t.isComplete()) {
						unfinishedTaskList.add(t);
					} else {
						finishedTaskList.add(t);
					}// end if-else
				}// end for
				if (unfinishedTaskList.size() == 0) {
					output.println();
				} else {
					for (Task t : unfinishedTaskList) {
						output.println(t.getMemo());
					}// end for
				}// end if-else
				output.println();// blank line to separate completed tasks
				if (finishedTaskList.size() == 0) {
					output.println();
				} else {
					for (Task t : finishedTaskList) {
						output.println(t.getMemo());
					}// end for
				}// end if-else
				output.println();// blank line to separate employees
			}// end for
			output.close();
			JOptionPane.showMessageDialog(null, "Information saved to file.");
		} catch (Exception e1) {
			JOptionPane
					.showMessageDialog(null,
							"File not found! The Digital Manager will exit without saving.");
		}// end try-catch
		System.exit(0);
	}// end exit

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String s = arg0.getActionCommand();// message from the clicked button
		switch (s) {
		case "Add New Employee":
			addEmployee();
			break;
		case "View Current Employees":
		case "Back to Employee List":
			goToEmployeeListMenu();
			break;
		case "Save and Exit":
			exit();
			break;
		case "Select Employee":
			boolean employeeSelected = selectEmployee();
			if (employeeSelected) {
				goToManageMenu();
			}// end if
			break;
		case "Return to Main Menu":
			goToMainMenu();
			break;
		case "Remove Employee":
			removeEmployee();
			break;
		case "Edit Employee":
			editEmployee();
			break;
		default:
			System.err.println("Fatal Error!");
			System.exit(1);
		}// end switch
	}// end actionPerformed
}// end DigitalManager class