package dossier;

/**
 * @name Task
 * @author Andrew Liu
 * @version March 2013
 * @school Richard Montgomery High School
 * @IDE Eclipse
 * 
 *      This class outlines the structure for an employee's task. The Task
 *      object has the actual assignment (memo) and a state of being either
 *      completed or incomplete, which is stored in a boolean.
 * 
 */
public class Task {

	/**
	 * the memo given by the Task
	 */
	private String memo;
	
	/**
	 * completion status of the Task
	 */
	private boolean isComplete;

	/**
	 * @Precondition none
	 * @Postcondition Task object created with empty values
	 */
	public Task() {
		memo = "";
		isComplete = false;
	}// end default constructor

	/**
	 * @Precondition none
	 * @Postcondition Task object created with specified memo
	 * @param memo
	 */
	public Task(String memo) {
		this.memo = memo;
		this.isComplete = false;
	}// end constructor

	/**
	 * @Precondition none
	 * @Postcondition Task object created with specified memo and completion
	 *                status
	 * @param memo
	 * @param isComplete
	 */
	public Task(String memo, boolean isComplete) {
		this.memo = memo;
		this.isComplete = isComplete;
	}// end constructor

	/**
	 * @Precondition none
	 * @Postcondition none
	 * @return Task object's memo
	 */
	public String getMemo() {
		return memo;
	}// end getMemo

	/**
	 * @Precondition none
	 * @Postcondition none
	 * @return Task object's completion status
	 */
	public boolean isComplete() {
		return isComplete;
	}// end isComplete

	/**
	 * @Precondition none
	 * @Postcondition Task object has the specified memo
	 * @param memo
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}// end setMemo

	/**
	 * @Precondition none
	 * @Postcondition Task object has the specified completion status
	 * @param isComplete
	 */
	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}// end setComplete
}// end Task class