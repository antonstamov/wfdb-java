package org.physionet.wfdb.examples;
import java.io.PrintStream;
import java.util.Properties;

import org.physionet.wfdb.rdsamp;

public class rdsampEx1 {

    public static void main(String[] args) {
    	
    	//Create an RDSAMP object
    	rdsamp myRdsamp=new rdsamp(); 	
    	
    	
    	//Execute the command to get the output
    	System.out.println("Executing..");
    	String results=myRdsamp.exec();
    	System.out.println(results);
    	System.out.println("Done");
    }
	
}
