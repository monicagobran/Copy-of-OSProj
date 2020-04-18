import java.util.ArrayList;

public class WriteFileSem {

	public boolean available;
	public ArrayList<Process> writeIntoFileQueue;

	public WriteFileSem() {
		this.available = true;
		this.writeIntoFileQueue = new ArrayList<Process>();
	}

	public void semWriteFileWait(Process p) {
		if (this.available) {
			// the resource is available, so the process can take it right away
			this.available = false;
		} else {
			// the resource is busy, so push the process to the blocked queue
			p.suspend();
			Process.setProcessState(p, ProcessState.Waiting);
			this.writeIntoFileQueue.add(p);
		}

	}

	public void semWriteFilePost() {
		// if the blocked queue is empty set the semaphore to true
		// else take a process from the queue
		// set the state of the process to ready
		// put in the ready queue
		// organise with scheduler to resume the process not to run it from the
		// beginning

		if (this.writeIntoFileQueue.isEmpty()) {
			this.available = true;
		} else {
			Process waiting = this.writeIntoFileQueue.remove(0);
			Process.setProcessState(waiting, ProcessState.Ready);
			waiting.interrupted = true;
			// TODO add the process to the ready queue
		}

	}

}
