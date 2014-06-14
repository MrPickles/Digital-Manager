package dossier;

import java.util.ArrayList;

/**
 * @name Employee
 * @author Andrew Liu
 * @version March 2013
 * @school Richard Montgomery High School
 * @IDE Eclipse
 * 
 *      This class outlines the Employee object that the Digital Manager will
 *      use. The employee will have a name, title, and task, all of which the
 *      Digital Manager can view and edit.
 */
public class Employee {

	/**
	 * the name of the Employee
	 */
	private String name;

	/**
	 * the title of the Employee
	 */
	private String title;

	/**
	 * the list of Tasks that the Employee has
	 */
	private ArrayList<Task> tasks = new ArrayList<Task>();

	/**
	 * @Preconditions none
	 * @Postconditions Employee object created with empty values
	 */
	public Employee() {
		name = "";
		title = "";
	}// end default constructor

	/**
	 * @Preconditions name is valid as defined by the DigitalManager class
	 * @Postconditions Employee object created with the specified name and title
	 *                 and with no tasks
	 * @param name
	 * @param title
	 */
	public Employee(String name, String title) {
		this.name = name;
		this.title = title;
	}// end constructor

	/**
	 * @Preconditions name is valid as defined by the DigitalManager class
	 * @Postconditions Employee object with the specified name, title, and tasks
	 * @param name
	 * @param title
	 * @param tasks
	 */
	public Employee(String name, String title, ArrayList<Task> tasks) {
		this.name = name;
		this.title = title;
		this.tasks = tasks;
	}// end constructor

	/**
	 * @Precondition none
	 * @Postcondition none
	 * @return Employee object's name
	 */
	public String getName() {
		return name;
	}// end getName

	/**
	 * @Precondition none
	 * @Postcondition none
	 * @return Employee object's title
	 */
	public String getTitle() {
		return title;
	}// end getTitle

	/**
	 * @Precondition none
	 * @Postcondition none
	 * @return Employee object's tasks
	 */
	public ArrayList<Task> getTasks() {
		return tasks;
	}// end getTasks

	/**
	 * @Precondition name is valid as defined by the DigitalManager class
	 * @Postcondition Employee object has the specified name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}// end setName

	/**
	 * @Precondition none
	 * @Postcondition Employee object has the specified title
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}// end setTitle

	/**
	 * @Precondition none
	 * @Postcondition Employee object has the specified tasks
	 * @param tasks
	 */
	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}// end setTasks

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String textToReturn = "";
		textToReturn += ("\nName: " + this.getName());
		textToReturn += ("\nTitle: " + this.getTitle());
		textToReturn += "\nTasks To Do:";
		int toDoTaskNum = 1;// labels each task sequentially
		for (Task t : this.getTasks()) {
			if (!t.isComplete()) {
				textToReturn += ("\n\t" + toDoTaskNum + ") " + t.getMemo());
				toDoTaskNum++;
			}// end if
		}// end for-each
		if (toDoTaskNum == 1) {
			textToReturn += "\n\tNone!";
		}// end if
		textToReturn += "\nCompleted Tasks:";
		int doneTaskNum = 1;// labels each task sequentially
		for (Task t : this.getTasks()) {
			if (t.isComplete()) {
				textToReturn += ("\n\t" + doneTaskNum + ") " + t.getMemo());
				doneTaskNum++;
			}// end if
		}// end for-each
		if (doneTaskNum == 1) {
			textToReturn += "\n\tNone!";
		}// end if
		return textToReturn;
	}// endToString
}// end Employee class
