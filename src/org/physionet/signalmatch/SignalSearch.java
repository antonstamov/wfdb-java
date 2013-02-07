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

		PhysioNetSignal sg= new PhysioNetSignal(0,"100","mitdb");
		sg.loadPhysicalData();
		double[][] data=sg.getPhysicalData();
		for(int i=0;i<data.length;i++){
			for(int k=0;k<data[0].length;k++){
				System.out.print(i+" =" +data[i][k] +" ");	
			}
			System.out.println("");
		}

		/*s
		String db="mitdb";
		String signalType="V5";
		PhysioNetDB pnDBMap=new PhysioNetDB(db);	
		ArrayList<PhysioNetRecord> recList = pnDBMap.getDBRecordList();
		ArrayList<PhysioNetSignal> list;
		int count=0;
		for(PhysioNetRecord thisRec: recList){
			thisRec.setSignalList(signalType);
			list = thisRec.getSignalList();
			if(list.size()>0)
				list.get(0).loadPhysicalData();
			break;
		}
		 */
	}

}
