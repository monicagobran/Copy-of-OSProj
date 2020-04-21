import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class OperatingSystem {

	public static ArrayList<Thread> ProcessTable;
	public static ArrayList<Process> reaadytable;
	public static WriteFileSem writeFsemaphore;
	public static ScannerSem readInputSemaphore;
	public static ReadFileSem readFileSemaphore;
	public static PrintSem printTextSemaphore;

	public OperatingSystem() {
		this.writeFsemaphore = new WriteFileSem();
		this.readInputSemaphore = new ScannerSem();
		this.readFileSemaphore = new ReadFileSem();
		this.printTextSemaphore = new PrintSem();
		this.reaadytable = new ArrayList<Process>();
	}

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
//TODO comment the next line
		p.start();

	}

	@SuppressWarnings("deprecation")
	public void FCFS() {
		ArrayList<Process> Table = new ArrayList<Process>();
		ArrayList<Process> OrderTable = new ArrayList<Process>();

		for (int i = 0; i < ProcessTable.size(); i++) {
			Table.add((Process) ProcessTable.get(i));
		}

		for (int i = 0; i < Table.size(); i++) {
			if (Table.get(i).getProcessState(Table.get(i)) == ProcessState.Ready) {
				this.reaadytable.add(Table.get(i));
			}
		}
		int j = 0;
		OrderTable = Order(this.reaadytable);

		for (int i = 0; i < OrderTable.size(); i++) {
			if (OrderTable.get(i).status == ProcessState.Waiting) {
				// System.out.println(OrderTable.get(i).status + " "+
				// OrderTable.get(i).processID);
				OrderTable.get(i).resume();
			} else if (OrderTable.get(i).status == ProcessState.Ready) {
				// System.out.println(OrderTable.get(i).status + " "+
				// OrderTable.get(i).processID);
				OrderTable.get(i).start();
			}

			while (OrderTable.get(i).isAlive()) {
				// stop other threads
				while ((!(j == i)) && (j <= i)) {
					OrderTable.get(j).setProcessState(OrderTable.get(j), ProcessState.Waiting);
					// System.out.println(OrderTable.get(j).status + " "+
					// OrderTable.get(j).processID);
					OrderTable.get(j).suspend();
					j++; // disable interruptions for the current thread??

				}

			} // remove not alive ??

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
		OperatingSystem o = new OperatingSystem();
		// TODO //createProcess(1);
		createProcess(2);
		// TODO //createProcess(3);
		// TODO //createProcess(4);
		createProcess(5);
		// TODO //o.FCFS();
	}

}

//testing git
