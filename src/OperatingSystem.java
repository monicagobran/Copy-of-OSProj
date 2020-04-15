import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class OperatingSystem {

	public static ArrayList<Thread> ProcessTable;

//	public static int activeProcess= 0;
	// system calls:
	// 1- Read from File
	@SuppressWarnings("unused")
	public static String readFile(String name) {
		String Data = "";
		File file = new File(name);
		try {
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()) {
				Data += scan.nextLine() + "\n";
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return Data;
	}

	// 2- Write into file
	@SuppressWarnings("unused")
	public static void writefile(String name, String data) {
		try {
			BufferedWriter BW = new BufferedWriter(new FileWriter(name));
			BW.write(data);
			BW.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	// 3- print to console
	@SuppressWarnings("unused")
	public static void printText(String text) {

		System.out.println(text);

	}

	// 4- take input

	@SuppressWarnings("unused")
	public static String TakeInput() {
		Scanner in = new Scanner(System.in);
		String data = in.nextLine();
		return data;

	}

	private static void createProcess(int processID) {
		Process p = new Process(processID);
		ProcessTable.add(p);
		Process.setProcessState(p, ProcessState.Ready);

//		p.start();

	}

	public static void FCFS() {
		ArrayList<Process> Table = new ArrayList<Process>();
		ArrayList<Process> reaadytable = new ArrayList<Process>();
		ArrayList<Process> OrderTable = new ArrayList<Process>();

		for (int i = 0; i < ProcessTable.size(); i++) {
			Table.add((Process) ProcessTable.get(i));
		}

		for (int i = 0; i < Table.size(); i++) {
			if (Table.get(i).getProcessState(Table.get(i)) == ProcessState.Ready) {
				reaadytable.add(Table.get(i));
			}
		}
		OrderTable = Order(reaadytable);
		for (int i = 0; i < OrderTable.size(); i++) {
			while (OrderTable.get(i).isAlive()) {
				// stop other threads
				// disable interruptions for the current thread
				// notify next thread when the current thread is done
			}

		}
	}

	public static <T> ArrayList<T> Order(ArrayList<T> list) {

		ArrayList<T> newList = new ArrayList<T>();
		for (T element : list) {

			if (!newList.contains(element)) {

				newList.add(element);
			}
		}
		return newList;
	}

	public static void main(String[] args) {
		ProcessTable = new ArrayList<Thread>();

		createProcess(1);
		createProcess(2);
		createProcess(3);
		createProcess(4);
		createProcess(5);
		FCFS();
	}

}

//testing git
