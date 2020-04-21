import java.util.ArrayList;

public class PrintSem {
	boolean available;
	ArrayList<Process> printQueue;
	
	public PrintSem() {
		available = true;
		printQueue = new ArrayList<Process>();
	}
	
	public void semPrintWait(Process p) {
		//check if available make available = true and break
		if(available) {
			available =false;
		}		
		//if not available, add to queue, suspend, change state
		else {
			printQueue.add(p);
			p.suspend();
			p.setProcessState(p,ProcessState.Waiting);
		}		
	}
	
	public void semPrintPost() {
		//if there is no process in queue, available = true
		if(printQueue.isEmpty()) {
			available= true;
		}
		//if there is a process in queue, move first to ready queue, change state, mark as suspended(interupted =true)
		else {
			Process p = printQueue.remove(0);
			OperatingSystem.reaadytable.add(p);
			Process.setProcessState(p, ProcessState.Ready);
			p.interrupted=true;
			
		}
	}

}
