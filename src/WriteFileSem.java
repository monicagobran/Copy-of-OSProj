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
			System.out.println("The writeFile resource is available");
			// the resource is available, so the process can take it right away
			this.available = false;
			System.out.println("The writeFile resource is now taken");
		} else {
			// the resource is busy, so push the process to the blocked queue
			System.out.println("The writeFile resource is busy");
			p.suspend();
			System.out.println("The process is suspended id: " + p.processID);
			Process.setProcessState(p, ProcessState.Waiting);
			System.out.println("The process state is now : " + p.status);
			this.writeIntoFileQueue.add(p);
			System.out.println("The size of the write file queue is: " + this.writeIntoFileQueue.size());
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
			System.out.println("the write file resource is now available and the blocked queue is empty");
			this.available = true;
		} else {
			System.out.println("There are processes int the blocked queue with size: " + this.writeIntoFileQueue.size());
			Process waiting = this.writeIntoFileQueue.remove(0);
			System.out.println("The process in turn is: " + waiting.processID);
			Process.setProcessState(waiting, ProcessState.Ready);
			System.out.println("The process state is now: " + waiting.status);
			waiting.interrupted = true;
			System.out.println("The process interrupted state is: " + waiting.interrupted);
			//TODO remove the next line
			OperatingSystem.ProcessTable.add(waiting);
			System.out.println("the process is added to the ready queue of size: " + OperatingSystem.ProcessTable.size());
			
			// TODO add the process to the ready queue
			OperatingSystem.reaadytable.add(waiting);
			System.out.println("the process is added to the ready queue of size: " + OperatingSystem.reaadytable.size());
		}

	}

}
