package org.physionet.wfdb;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class wfdbTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testrdannExec() {
		rdann rdannexec = new rdann();
		rdannexec.setArgumentValue(rdann.Arguments.stopTime, "10s");
		rdannexec.setArgumentValue(rdann.Arguments.recordName, "mitdb/100");
		assertEquals(2, rdannexec.get_num_arguments());
		rdannexec.gen_exec_arguments();
		assertEquals("/afs/ecg.mit.edu/software/wfdb/amd64_linux26/current/bin/rdann -r mitdb/100 -t 10s ", rdannexec.get_command_line());
	}
	
	@Test
	public void testrdsampExec() {
		rdsamp rdsampexec = new rdsamp();
		rdsampexec.setArgumentValue(rdsamp.Arguments.stopTime, "10s");
		rdsampexec.setArgumentValue(rdsamp.Arguments.recordName, "mitdb/100");
		assertEquals(2, rdsampexec.get_num_arguments());
		rdsampexec.gen_exec_arguments();
		assertEquals("/afs/ecg.mit.edu/software/wfdb/amd64_linux26/current/bin/rdsamp -r mitdb/100 -t 10s ", rdsampexec.get_command_line());
	}

}
