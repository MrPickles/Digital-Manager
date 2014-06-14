package prototype;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Andrew Liu
 * 
 *         This is the prototype version of the Digital Manager. This program
 *         only uses the console for input and output, and the prototype is
 *         purposefully not robust and badly documented. The purpose of this
 *         prototype is simply to show the logic path of the actual Digital
 *         Manager.
 * 
 */
public class Prototype {

	private String name;
	private String title;
	private ArrayList<String> tasksToDo = new ArrayList<String>();
	private ArrayList<String> tasksDone = new ArrayList<String>();
	private static ArrayList<Prototype> employeeList = new ArrayList<Prototype>();
	public static Scanner scInt = new Scanner(System.in); // to read ints
	public static Scanner scString = new Scanner(System.in); // to read Strings

	public Prototype() {
		name = "John Doe";
		title = "Supreme Commander";
	}

	public Prototype(String name, String title) {
		this.name = name;
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public ArrayList<String> getTasksToDo() {
		return tasksToDo;
	}

	public ArrayList<String> getTasksDone() {
		return tasksDone;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTasksToDo(ArrayList<String> tasksToDo) {
		this.tasksToDo = tasksToDo;
	}

	public void setTasksDone(ArrayList<String> tasksDone) {
		this.tasksDone = tasksDone;
	}

	public static void goToMainMenu() {
		System.out.print("\n~Main Menu~\n1) Add New Employee"
				+ "\n2) View and Manage Employee Information"
				+ "\n3) Exit\nPick the corresponding number: ");
		int choice = scInt.nextInt();
		System.out.println();
		switch (choice) {
		case 1:
			addEmployee();
			break;
		case 2:
			goToManageMenu();
			break;
		case 3:
			exit();
			break;
		default:
			System.err.println("Fatal Error!");
			System.exit(1);
		}
	}

	public static void goToManageMenu() {
		alphabetize();
		System.out.println("\nEmployee List:");
		for (Prototype p : employeeList) {
			System.out
					.println(employeeList.indexOf(p) + 1 + ") " + p.getName());
		}
		System.out
				.print("\n~Management Menu~\nA) Remove Employee\nB) Edit Employee\nC) View Employee Information\nPick Letter: ");
		String choice1 = scString.nextLine().toLowerCase();
		System.out.println();
		switch (choice1) {
		case "a":
			removeEmployee();
			break;
		case "b":
			editEmployee();
			break;
		case "c":
			viewEmployee();
			break;
		default:
			System.err.println("Fatal Error!");
			System.exit(1);
		}
	}

	public static void addEmployee() {
		System.out.print("Enter employee name: ");
		String name = scString.nextLine();
		while (!isValidName(name)) {
			System.out.println("Not valid; try again!");
			System.out.print("Enter name: ");
			name = scString.nextLine();
		}
		System.out.print("Enter employee title: ");
		String title = scString.nextLine();
		Prototype newEmployee = new Prototype(name, title);
		employeeList.add(newEmployee);
		goToMainMenu();
	}

	public static void removeEmployee() {
		System.out.print("Pick employee number: ");
		int choice = scInt.nextInt();
		if ((choice > employeeList.size()) || (choice < 1)) {
			System.err.println("Fatal Error! Number not on list!");
			System.exit(1);
		}
		employeeList.remove(choice - 1);
		goToMainMenu();
	}

	public static void editEmployee() {
		System.out.print("Pick employee number: ");
		int choice = scInt.nextInt();
		Prototype temp;
		if ((choice > employeeList.size()) || (choice < 1)) {
			System.err.println("Fatal Error! Number not on list!");
			System.exit(1);
		}
		temp = employeeList.get(choice - 1);
		employeeList.remove(choice - 1);
		System.out.print("Edit name? [y/n]");
		String choice1 = scString.nextLine();
		if (choice1.toLowerCase().equals("y")) {
			String name = "";
			do {
				System.out.print("Enter name: ");
				name = scString.nextLine();
				if (!isValidName(name)) {
					System.out.println("Not valid; try again!");
				}
			} while (!isValidName(name));
			temp.setName(name);
		}
		System.out.print("Edit title? [y/n]");
		choice1 = scString.nextLine();
		if (choice1.toLowerCase().equals("y")) {
			System.out.print("\nEnter title: ");
			String title = scString.nextLine();
			temp.setTitle(title);
		}
		System.out.print("Add task? [y/n]");
		choice1 = scString.nextLine();
		while (choice1.toLowerCase().equals("y")) {
			temp.addTask();
			System.out.print("Add another task? [y/n]");
			choice1 = scString.nextLine();
		}
		System.out.print("Complete task? [y/n]");
		choice1 = scString.nextLine();
		while (choice1.toLowerCase().equals("y")) {
			if (temp.getTasksToDo().size() == 0) {
				System.out.println("No tasks to complete!");
				break;
			}
			temp.completeTask();
			System.out.print("Complete another task? [y/n]");
			choice1 = scString.nextLine();
		}
		employeeList.add(temp);
		goToMainMenu();
	}

	public static void viewEmployee() {
		System.out.print("Pick employee number: ");
		int choice = scInt.nextInt();
		if ((choice > employeeList.size()) || (choice < 1)) {
			System.err.println("Fatal Error! Number not on list!");
			System.exit(1);
		}
		Prototype employee = employeeList.get(choice - 1);
		System.out.println("Name: " + employee.getName());
		System.out.println("Title: " + employee.getTitle());
		System.out.println("Tasks to do:");
		int taskCounter = 1;
		if (employee.getTasksToDo().size() == 0) {
			System.out.println("\tNone!");
		} else {
			for (String s : employee.getTasksToDo()) {
				System.out.println("\t" + taskCounter + ") " + s);
				taskCounter++;
			}
		}
		System.out.println("Completed tasks:");
		taskCounter = 1;
		if (employee.getTasksDone().size() == 0) {
			System.out.println("\tNone!");
		} else {
			for (String s : employee.getTasksDone()) {
				System.out.println("\t" + taskCounter + ") " + s);
				taskCounter++;
			}
		}
		goToMainMenu();
	}

	public static void exit() {
		System.out.println("Good bye!");
		System.exit(0);
	}

	public void addTask() {
		System.out.print("Type task: ");
		String task = scString.nextLine();
		ArrayList<String> temp = this.getTasksToDo();
		temp.add(task);
		this.setTasksToDo(temp);
	}

	public void completeTask() {
		ArrayList<String> taskList = this.getTasksToDo();
		int index = 1;
		System.out.println("Tasks:");
		for (String s : taskList) {
			System.out.println("\t" + index + ") " + s);
			index++;
		}
		System.out.print("Select task number: ");
		int choice = scInt.nextInt();
		if ((choice > taskList.size()) || (choice < 1)) {
			System.err.println("Fatal Error! Number not on list!");
			System.exit(1);
		}
		ArrayList<String> completedTaskList = this.getTasksDone();
		completedTaskList.add(taskList.get(choice - 1));
		taskList.remove(choice - 1);
		this.setTasksToDo(taskList);
		this.setTasksDone(completedTaskList);
	}

	/**
	 * Puts the employees in alphabetical order.
	 */
	public static void alphabetize() {
		ArrayList<Prototype> temp = new ArrayList<Prototype>();
		while (employeeList.size() > 0) {
			Prototype first = employeeList.get(0);
			for (Prototype p : employeeList) {
				if (p.getName().compareTo(first.getName()) < 0) {
					first = p;
				}
			}
			temp.add(first);
			employeeList.remove(employeeList.indexOf(first));
		}
		employeeList = temp;
	}

	/**
	 * Conditions for being valid are that the name is unique, has only letters,
	 * and has at least one character.
	 */
	public static boolean isValidName(String name) {
		/*
		 * unique, letters only, length > 0
		 */
		if (!name.matches("\\D+")) {
			return false;
		}
		for (Prototype p : employeeList) {
			if (name.equals(p.getName())) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		System.out
				.println("Welcome to the prototype version of the Digital Manager.");
		goToMainMenu();
	}
}
