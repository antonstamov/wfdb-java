package org.physionet.wfdb;

import java.io.IOException;

public class rdann extends wfdbexec {

	public rdann() {
		setExecName("rdann");
	}

	public static enum Arguments {
		//Define input arguments syntax is:
		// argumentName(number of parameters,is optional,WDFD command string)
		recordName(1, false, "-r"),
		startTime(1, true, "-f"),
		stopTime(1, true, "-t"),
		annotator(1, false, "-a"),
		channel(1, true, "-c"),
		printNum(1, true, "-n"),
		printType(1, true, "-p"),
		printSubType(1, true, "-s"),
		columnHeadings(0, true, "-v");

		public int parameters;
		public boolean optional;
		public String label;
		Arguments(int parameters, boolean optional, String label) {
			this.parameters = parameters;
			this.optional = optional;
			this.label = label;
		}
	}

	public void setArgumentValue(Arguments arg, String value) {
		if (arg.parameters > 0) {
			this.argumentValues.put(arg.name(), value);
		} else {
			this.argumentValues.put(arg.name(), "");
		}
		this.argumentLabels.put(arg.name(), arg.label);
	}
	
	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void main(String[] args) {
		// Simple test of for the class
		rdann rdannexec = new rdann();
		rdannexec.setArgumentValue(rdann.Arguments.stopTime, "10s");
		rdannexec.setArgumentValue(rdann.Arguments.recordName, "mitdb/100");
		System.out.println(rdannexec.exec());
	}
}
