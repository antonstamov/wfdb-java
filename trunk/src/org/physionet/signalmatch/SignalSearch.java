/* ===========================================================
 * WFDB Java : Interface to WFDB Applications.
 *              
 * ===========================================================
 *
 * (C) Copyright 2012, by Ikaro Silva
 *
 * Project Info:
 *    Code: http://code.google.com/p/wfdb-java/
 *    WFDB: http://www.physionet.org/physiotools/wfdb.shtml
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *
 * Original Author:  Ikaro Silva
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * Check: http://code.google.com/p/wfdb-java/list
 */ 

//Very basic example of how to print 10 samples of a WFDB record
//into the standard output
package org.physionet.signalmatch;
import java.util.ArrayList;

import org.physionet.wfdb.Wfdbdesc;
import org.physionet.wfdb.physiobank.*;


public class SignalSearch {

	public static void main(String[] args) {

		String db="mitdb";
		String signalType="ECG2	V1";
		PhysioNetDB pnDBMap=new PhysioNetDB(db);	
		ArrayList<PhysioNetRecord> recList = pnDBMap.getDBRecordList();
		boolean tmpList;

		//Loop through all the records and select the ones containing the desired
		//signal type
		ArrayList<String> infoList=new ArrayList<String>();
		for(PhysioNetRecord thisRec: recList){
			tmpList=thisRec.getSignalStringList().toString().contains(signalType);
			if(tmpList){
				for(String k: thisRec.getWfdbdes())
					System.out.println(k);
				break;
			}

		}

		//PhysioNetRecord thisRec=proccessList.get(0);
		//System.out.println(wfdbdesc.execToString());

		/*
		Rdsamp rdsampexec = new Rdsamp();
		//rdsampexec.setArgumentValue(Rdsamp.Arguments.stopTime, "s120");
		rdsampexec.setArgumentValue(Rdsamp.PrintTimeFormatLabel.p);
		rdsampexec.setArgumentValue(Rdsamp.Arguments.recordName, db);
		System.out.println(rdsampexec.execToString());
		 */
	}

}
