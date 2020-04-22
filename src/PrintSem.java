import java.util.ArrayList;

public class PrintSem {
	boolean available;
	ArrayList<Process> printQueue;

	public PrintSem() {
		available = true;
		printQueue = new ArrayList<Process>();
	}

	public void semPrintWait(Process p) {
		// check if available make available = true and break
		System.out.println("printer is needed by process " + p.processID);
		if (available) {
			available = false;
			System.out.println("Printer taken by process " + p.processID);
		}
		// if not available, add to queue, suspend, change state
		else {
			printQueue.add(p);

			p.setProcessState(p, ProcessState.Waiting);
			System.out.println("Process " + p.processID + " waiting for printer");
			p.suspend();
		}
	}

	public void semPrintPost() {
		// if there is no process in queue, available = true
		System.out.println("Printer released ");
		if (printQueue.isEmpty()) {
			available = true;
		}
		// if there is a process in queue, move first to ready queue, change state, mark
		// as suspended(interupted =true)
		else {
			Process p = printQueue.remove(0);
			OperatingSystem.reaadytable.add(p);
			Process.setProcessState(p, ProcessState.Ready);
			p.interrupted = true;
			System.out.println("process " + p.processID + " released and added to ready queue");

		}
	}

}
