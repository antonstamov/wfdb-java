package org.physionet.wfdb;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class rdann {

	private String recordName;
	private String recordNameArgument = "";

	private String annotator;
	private String annotatorArgument = "";
	private wfdbTime startTime;
	private wfdbTime stopTime;
	private Integer num;
	private ArrayList<String> type;
	private ArrayList<String> subType;
	private boolean header;
	private boolean alternateTimeFormat;
	private PrintText printText;

	private String headerLine = "";
	private String stopTimeArgument;
	private String headerArgument;
	
	public enum PrintText {
		XML,
		CSV;
	}
	
	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub
		rdann rdannexec = new rdann();
		rdannexec.setRecordName("mitdb/100");
		rdannexec.setAnnotator("atr");
		rdannexec.setHeader(true);
		rdannexec.setStopTime(new wfdbTime("10s"));
		System.out.println(rdannexec.exec());
	}

	/**
	 * @return the recordName
	 */
	public String getRecordName() {
		return recordName;
	}

	/**
	 * @param recordName
	 *            the recordName to set
	 */
	public void setRecordName(String recordName) {
		this.recordName = recordName;
		this.recordNameArgument = " -r " + recordName;
	}

	/**
	 * @return the startTime
	 */
	public wfdbTime getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(wfdbTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the stopTime
	 */
	public wfdbTime getStopTime() {
		return stopTime;
	}

	/**
	 * @param stopTime
	 *            the stopTime to set
	 */
	public void setStopTime(wfdbTime stopTime) {
		this.stopTime = stopTime;
		this.stopTimeArgument = " -t " + stopTime;
	}

	/**
	 * @return the num
	 */
	public Integer getNum() {
		return num;
	}

	/**
	 * @param num
	 *            the num to set
	 */
	public void setNum(Integer num) {
		this.num = num;
	}

	/**
	 * @return the type
	 */
	public ArrayList<String> getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(ArrayList<String> type) {
		this.type = type;
	}

	/**
	 * @return the subType
	 */
	public ArrayList<String> getSubType() {
		return subType;
	}

	/**
	 * @param subType
	 *            the subType to set
	 */
	public void setSubType(ArrayList<String> subType) {
		this.subType = subType;
	}
	
	/**
	 * @return the printText
	 */
	public PrintText getPrintText() {
		return printText;
	}

	/**
	 * @param printText the printText to set
	 */
	public void setPrintText(PrintText printText) {
		this.printText = printText;
	}
	
	/**
	 * @return the header
	 */
	public boolean isHeader() {
		return header;
	}

	/**
	 * @param header
	 *            the header to set
	 */
	public void setHeader(boolean header) {
		this.header = header;
		headerArgument = " -v ";
	}

	/**
	 * @return the alternateTimeFormat
	 */
	public boolean isAlternateTimeFormat() {
		return alternateTimeFormat;
	}

	/**
	 * @param alternateTimeFormat
	 *            the alternateTimeFormat to set
	 */
	public void setAlternateTimeFormat(boolean alternateTimeFormat) {
		this.alternateTimeFormat = alternateTimeFormat;
	}

	/**
	 * @return the annotator
	 */
	public String getAnnotator() {
		return annotator;
	}

	/**
	 * @param annotator the annotator to set
	 */
	public void setAnnotator(String annotator) {
		this.annotator = annotator;
		this.annotatorArgument = " -a " + annotator;
	}

	private String buildCommand() {
		String command = "/afs/ecg.mit.edu/software/wfdb/amd64_linux26/current/bin/rdann";
		
		// Add command line arguments
		command += recordNameArgument;
		command += annotatorArgument;
		command += stopTimeArgument;
		command += headerArgument;
		System.out.println(command);
		return(command);
		
	}
	
	public String exec() throws IOException, InterruptedException {
		String results = "";
		String tmp = null;
		Runtime runtime = Runtime.getRuntime();
		String exec_str = buildCommand();
		try {
			Process myProcess = runtime.exec(exec_str);

			// Stream for stdout
			InputStream instream = myProcess.getInputStream();
			BufferedInputStream bufstream = new BufferedInputStream(instream);
			InputStreamReader inread = new InputStreamReader(bufstream);
			BufferedReader bufferedreader = new BufferedReader(inread);

			// Stream for stderr
			InputStream errstream = myProcess.getErrorStream();
			BufferedInputStream buferrstream = new BufferedInputStream(errstream);
			InputStreamReader errread = new InputStreamReader(buferrstream);
			BufferedReader errreader = new BufferedReader(errread);

			// Read output
			if(header) {
				// Read a line of output to remove the header
				headerLine = bufferedreader.readLine();
			}
			while ((tmp = bufferedreader.readLine()) != null) {
				results += tmp + "\n";
			}
			// Error
			try {
				if (myProcess.waitFor() != 0) {
					System.err.println("Error executing: ");
					System.err.println(exec_str);
					System.err.println("Error value: " + myProcess.exitValue());
					String errString = "";
					while ((tmp = errreader.readLine()) != null) {
						errString += tmp + "\n";
					}
					System.err.println(errString);
				}
			} catch (InterruptedException e) {
				System.err.println("InterruptedException:" + e.getMessage());
				throw(e);
			} finally {
				// close streams
				bufferedreader.close();
				inread.close();
				bufstream.close();
				instream.close();
			}
		} catch (IOException e) {
			System.err.println("IOException:" + e.getMessage());
			throw(e);
		}
		return headerLine + "\n" + results;
	}
}
