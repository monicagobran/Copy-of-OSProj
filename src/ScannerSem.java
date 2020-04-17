import java.util.ArrayList;

public class ScannerSem {
	public boolean available;
	public ArrayList<Process> readInputQueue;

	public ScannerSem() {
		this.available = true;
		this.readInputQueue = new ArrayList<Process>();
	}

	public void semScannerWait(Process p) {
		if (this.available) {
			// the resource is available, so the process can take it right away
			this.available = false;
		} else {
			// the resource is busy, so push the process to the blocked queue
			p.suspend();
			Process.setProcessState(p, ProcessState.Waiting);
			this.readInputQueue.add(p);
		}

	}

	public void semScannerPost() {
		// if the blocked queue is empty set the semaphore to true
		// else take a process from the queue
		// set the state of the process to ready
		// put in the ready queue
		// organise with scheduler to resume the process not to run it from the
		// beginning

		if (this.readInputQueue.isEmpty()) {
			this.available = true;
		} else {
			Process waiting = this.readInputQueue.remove(0);
			Process.setProcessState(waiting, ProcessState.Ready);
			waiting.interrupted = true;
			// TODO add the process to the ready queue
		}

	}

}
