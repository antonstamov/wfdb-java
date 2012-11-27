
This is a collection of Java wrappers to the WFDB Application Software. 
The wrappers call compiled WFDB native code, supported only on Windows, Mac, and Linux (32 and 64 bits).


Current supported classes:


 
For headless build, run:

java -jar /lib64/eclipse/plugins/org.eclipse.equinox.launcher_1.3.0.v20120522-1813.jar -application org.eclipse.ant.core.antRunner -buildfile /home/joe/workspace/wfdb-java-app/build.xml

Alternatively, setup Eclipse to automatically generate JAR file.
For instructions on how to create an Ant buildfile see:
http://help.eclipse.org/indigo/index.jsp?topic=%2Forg.eclipse.jdt.doc.isv%2Fguide%2Fjdt_apt_building_with_apt.htm


Test:

java -cp wfdb.jar org.physionet.wfdb.physiobank.PhysioNetDB
java -cp wfdbapp.jar org.physionet.wfdb.examples.RdsampEx1