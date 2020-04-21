import java.util.ArrayList;

public class ReadFileSem {
	
	boolean available;
	ArrayList<Process> readFileQueue;
	
	public ReadFileSem() {
		available = true;
		readFileQueue = new ArrayList<Process>();
	}
	
	public void semReadWait(Process p) {
		//check if available make available = true and break
		if(available) {
			available =false;
		}		
		//if not available, add to queue, suspend, change state
		else {
			readFileQueue.add(p);
			p.suspend();
			p.setProcessState(p,ProcessState.Waiting);
		}		
	}
	
	public void semReadPost() {
		//if there is no process in queue, available = true
		if(readFileQueue.isEmpty()) {
			available= true;
		}
		//if there is a process in queue, move first to ready queue, change state, mark as suspended(interupted =true)
		else {
			Process p = readFileQueue.remove(0);
			OperatingSystem.reaadytable.add(p);
			Process.setProcessState(p, ProcessState.Ready);
			p.interrupted=true;
			
		}
		
	}
	
	

}
