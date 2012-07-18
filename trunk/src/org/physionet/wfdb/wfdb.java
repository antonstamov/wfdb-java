/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.physionet.wfdb;

import java.io.*;
import java.util.Map;

/**
 *
 * @author djscott
 */
public class wfdb {

    public static String extractLibrary(String libraryName) throws IOException {
        InputStream src = wfdb.class.getResourceAsStream("nativelibs/linux_x86_64/" + libraryName);
//        File exeTempFile = File.createTempFile(libraryName, "");
        File exeTempFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + libraryName);
        FileOutputStream out = new FileOutputStream(exeTempFile);
        byte[] temp = new byte[32768];
        int rc;
        while ((rc = src.read(temp)) > 0) {
            //System.out.println(rc);
            out.write(temp, 0, rc);
        }

        src.close();

        out.close();

        exeTempFile.deleteOnExit();
        System.out.println(exeTempFile + " is ready");
        return(exeTempFile.getAbsolutePath());
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String wfdbLibrary = extractLibrary("libwfdb.so");
        extractLibrary("libwfdb.so.10");
        
        //        InputStream src = wfdb.class.getResource("nativelibs/linux_x86_64/rdsamp").openStream();
        InputStream src = wfdb.class.getResourceAsStream("nativelibs/linux_x86_64/rdsamp");
        File exeTempFile = File.createTempFile("rdsamp", "");
        exeTempFile.setExecutable(true, true);
        FileOutputStream out = new FileOutputStream(exeTempFile);
        byte[] temp = new byte[32768];
        int rc;
        while ((rc = src.read(temp)) > 0) {
            //System.out.println(rc);
            out.write(temp, 0, rc);
        }

        src.close();

        out.close();

        //exeTempFile.deleteOnExit();

        ProcessBuilder launcher = new ProcessBuilder();
        launcher.redirectErrorStream(true);
        String results = "";

        Map<String, String> env = launcher.environment();
        //env.put("LD_LIBRARY_PATH", "/afs/ecg.mit.edu/software/wfdb/@sys/current/lib64");
        env.put("LD_LIBRARY_PATH", System.getProperty("java.io.tmpdir"));
        System.out.println("OS current temporary directory is " + System.getProperty("java.io.tmpdir"));
        System.out.println(exeTempFile.toString());
        launcher.command(exeTempFile.toString());
        Process p = launcher.start();
        BufferedReader output = new BufferedReader(new InputStreamReader(
                p.getInputStream()));
        String line;
        while ((line = output.readLine()) != null) {
            results += line + "\n";
        }
        p.waitFor();
        System.out.println(results);
    }
}
