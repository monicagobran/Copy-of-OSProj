//import java.util.concurrent.Semaphore;

public class Process extends Thread {

	public int processID;
	ProcessState status = ProcessState.New;
	public boolean interrupted;

	public Process(int m) {
		processID = m;
	}

	@Override
	public void run() {
this.status = ProcessState.Running;
		switch (processID) {
		case 1:
			process1();
			break;
		case 2:
			process2();
			break;
		case 3:
			process3();
			break;
		case 4:
			process4();
			break;
		case 5:
			process5();
			break;
		}

	}

	private void process1() {
		System.out.println("process1");
		OperatingSystem.printTextSemaphore.semPrintWait(this);
		OperatingSystem.printText("Enter File Name: ");
		OperatingSystem.printTextSemaphore.semPrintPost();
		
		OperatingSystem.readInputSemaphore.semScannerWait(this);
		String filename = OperatingSystem.TakeInput();
		OperatingSystem.readInputSemaphore.semScannerPost();
		
		OperatingSystem.readFileSemaphore.semReadWait(this);
		String data = OperatingSystem.readFile(filename);
		OperatingSystem.readFileSemaphore.semReadPost();
		
		OperatingSystem.printTextSemaphore.semPrintWait(this);
		OperatingSystem.printText(data);
		OperatingSystem.printTextSemaphore.semPrintPost();
		
		setProcessState(this, ProcessState.Terminated);
	}

	private void process2() {
		System.out.println("process2");
		OperatingSystem.printTextSemaphore.semPrintWait(this);
		OperatingSystem.printText("Enter File Name: ");
		OperatingSystem.printTextSemaphore.semPrintPost();

		OperatingSystem.readInputSemaphore.semScannerWait(this);
		String filename = OperatingSystem.TakeInput();
		OperatingSystem.readInputSemaphore.semScannerPost();

		OperatingSystem.printTextSemaphore.semPrintWait(this);
		OperatingSystem.printText("Enter Data: ");
		OperatingSystem.printTextSemaphore.semPrintPost();		

		OperatingSystem.readInputSemaphore.semScannerWait(this);
		String data = OperatingSystem.TakeInput();
		OperatingSystem.readInputSemaphore.semScannerPost();
		
		OperatingSystem.writeFsemaphore.semWriteFileWait(this);
		OperatingSystem.writefile(filename, data);
		OperatingSystem.writeFsemaphore.semWriteFilePost();
		
		setProcessState(this, ProcessState.Terminated);
	}

	private void process3() {
		System.out.println("process3");
		int x = 0;
		OperatingSystem.printTextSemaphore.semPrintWait(this);
		while (x < 301) {
			
			
			OperatingSystem.printText(x + "\n");
				
			
			x++;
		}
		OperatingSystem.printTextSemaphore.semPrintPost();
		
		setProcessState(this, ProcessState.Terminated);
	}

	private void process4() {
		System.out.println("process4");
		int x = 500;
		
		OperatingSystem.printTextSemaphore.semPrintWait(this);
		while (x < 1001) {			
			OperatingSystem.printText(x + "\n");					
			x++;
		}
		OperatingSystem.printTextSemaphore.semPrintPost();
		
		setProcessState(this, ProcessState.Terminated);
	}

	private void process5() {
		System.out.println("process5");

		OperatingSystem.printTextSemaphore.semPrintWait(this);
		OperatingSystem.printText("Enter LowerBound: ");
		OperatingSystem.printTextSemaphore.semPrintPost();

		OperatingSystem.readInputSemaphore.semScannerWait(this);
		String lower = OperatingSystem.TakeInput();
		OperatingSystem.readInputSemaphore.semScannerPost();

		OperatingSystem.printTextSemaphore.semPrintWait(this);
		OperatingSystem.printText("Enter UpperBound: ");
		OperatingSystem.printTextSemaphore.semPrintPost();

		OperatingSystem.readInputSemaphore.semScannerWait(this);
		String upper = OperatingSystem.TakeInput();
		OperatingSystem.readInputSemaphore.semScannerPost();
		
		int lowernbr = Integer.parseInt(lower);
		int uppernbr = Integer.parseInt(upper);
		String data = "";

		while (lowernbr <= uppernbr) {
			data += lowernbr++ + "\n";
		}
		OperatingSystem.writeFsemaphore.semWriteFileWait(this);
		OperatingSystem.writefile("P5.txt", data);
		OperatingSystem.writeFsemaphore.semWriteFilePost();
		
		setProcessState(this, ProcessState.Terminated);
	}

	public static void setProcessState(Process p, ProcessState s) {
		p.status = s;
		if (s == ProcessState.Terminated) {
			System.out.println("Terminating Process "+ p.processID);
			OperatingSystem.ProcessTable.remove(OperatingSystem.ProcessTable.indexOf(p));
		}
	}

	public static ProcessState getProcessState(Process p) {
		return p.status;
	}
}
