package org.physionet.wfdb.examples;
import org.physionet.wfdb.Wrsamp;

public class WrsampEx1 {

	public static void main(String[] args) {
		// Simple test of for the class Reads first 10 seconds of ECG
		String dir="/home/ikaro/workspace/wfdb-java/src/org/physionet/wfdb/examples/";
		String inFile= dir + "test.txt";
		String out_dir=System.getProperty("user.dir");
		String outFile="data";
		Wrsamp wrsampexec = new Wrsamp();
		wrsampexec.setArgumentValue(Wrsamp.Arguments.file,inFile);
		wrsampexec.setArgumentValue(Wrsamp.Arguments.outputFile,outFile);
		wrsampexec.execToString();
		System.out.println("Done executing,  file save at:");
		System.out.println(out_dir + System.getProperty("file.separator") 
				+ outFile + ".dat");
	}
	
}
