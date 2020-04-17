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
		// TODO semwait for print
		OperatingSystem.printText("Enter File Name: ");
		// TODO sempost for print
		OperatingSystem.readInputSemaphore.semScannerWait(this);
		String filename = OperatingSystem.TakeInput();
		OperatingSystem.readInputSemaphore.semScannerPost();
		// TODO semwait for readFile
		String data = OperatingSystem.readFile(filename);
		// TODO sempost for readFile
		// TODO semwait for print
		OperatingSystem.printText(data);
		// TODO sempost for print
		setProcessState(this, ProcessState.Terminated);
	}

	private void process2() {

		// TODO semwait for print
		OperatingSystem.printText("Enter File Name: ");
		// TODO sempost for print
		OperatingSystem.readInputSemaphore.semScannerWait(this);
		String filename = OperatingSystem.TakeInput();
		OperatingSystem.readInputSemaphore.semScannerPost();
		// TODO semwait for print
		OperatingSystem.printText("Enter Data: ");
		// TODO sempost for print
		// TODO semwait for readFile
		String data = OperatingSystem.TakeInput();
		// TODO sempost for readFile
		OperatingSystem.writeFsemaphore.semWriteFileWait(this);
		OperatingSystem.writefile(filename, data);
		OperatingSystem.writeFsemaphore.semWriteFilePost();
		setProcessState(this, ProcessState.Terminated);
	}

	private void process3() {
		int x = 0;
		while (x < 301) {
			// TODO semwait for print
			OperatingSystem.printText(x + "\n");
			// TODO sempost for print
			x++;
		}
		setProcessState(this, ProcessState.Terminated);
	}

	private void process4() {

		int x = 500;
		while (x < 1001) {
			// TODO semwait for print
			OperatingSystem.printText(x + "\n");
			// TODO sempost for print
			x++;
		}
		setProcessState(this, ProcessState.Terminated);
	}

	private void process5() {

		// TODO semwait for print
		OperatingSystem.printText("Enter LowerBound: ");
		// TODO sempost for print
		OperatingSystem.readInputSemaphore.semScannerWait(this);
		String lower = OperatingSystem.TakeInput();
		OperatingSystem.readInputSemaphore.semScannerPost();
		// TODO semwait for print
		OperatingSystem.printText("Enter UpperBound: ");
		// TODO sempost for print
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
			OperatingSystem.ProcessTable.remove(OperatingSystem.ProcessTable.indexOf(p));
		}
	}

	public static ProcessState getProcessState(Process p) {
		return p.status;
	}
}
