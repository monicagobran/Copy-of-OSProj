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
		reaadytable.add(p);

		// p.start();

	}

	@SuppressWarnings({ "deprecation", "static-access" })
	public void FCFS() {

		ArrayList<Process> OrderTable = new ArrayList<Process>();

		int j = 0;
		OrderTable = Order(this.reaadytable);
		int i = 0;
		while (!OrderTable.isEmpty()) {
			System.out.println(
					OrderTable.get(i).processID + " is " + OrderTable.get(i).getProcessState(OrderTable.get(i)));
			if (OrderTable.get(i).status == ProcessState.Waiting || OrderTable.get(i).interrupted) {

				OrderTable.get(i).resume();
				OrderTable.get(i).setProcessState(OrderTable.get(i), ProcessState.Ready);
				System.out.println(
						OrderTable.get(i).processID + " is " + OrderTable.get(i).getProcessState(OrderTable.get(i)));

			} else if (OrderTable.get(i).status == ProcessState.Ready) {

				OrderTable.get(i).start();
				OrderTable.get(i).setProcessState(OrderTable.get(i), ProcessState.Running);
				System.out.println(
						OrderTable.get(i).processID + " is " + OrderTable.get(i).getProcessState(OrderTable.get(i)));

			}

			while (OrderTable.get(i).isAlive()) {
				// stop other threads
				while ((!(j == i)) && (j <= i)) {
					OrderTable.get(j).setProcessState(OrderTable.get(j), ProcessState.Waiting);
					System.out.println(OrderTable.get(i).processID + " is "
							+ OrderTable.get(i).getProcessState(OrderTable.get(i)));
					OrderTable.get(j).suspend();
					j++;

				}

			}
			// remove not alive
			OrderTable.remove(i);

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
		createProcess(1);
		createProcess(2);
		createProcess(3);
		createProcess(4);
		createProcess(5);
		o.FCFS();

		// to test without FCFS
		while (!ProcessTable.isEmpty()) {
			for (int i = 0; i < ProcessTable.size(); i++) {
				if (ProcessTable.size() > i) {
					Process p = (Process) ProcessTable.get(i);
					if (!ProcessTable.isEmpty() && p.status.equals(ProcessState.Ready) && p.interrupted) {
						System.out.println("Resuming Process " + p.processID);
						p.status = ProcessState.Running;
						p.interrupted = false;
						p.resume();
					}
				}
			}
			boolean terminate = true;
			for (int i = 0; i < ProcessTable.size(); i++) {
				Process p = (Process) ProcessTable.get(i);
				if (!ProcessTable.isEmpty() && !p.status.equals(ProcessState.Terminated)) {
					terminate = false;
				}
			}
			if (terminate)
				break;
		}

	}

}

//testing git
