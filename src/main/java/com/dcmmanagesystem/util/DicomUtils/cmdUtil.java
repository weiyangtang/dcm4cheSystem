package com.dcmmanagesystem.util.DicomUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class cmdUtil {

    //cmdstr为命令参数
    //在win10可用,Linux待改进
    public static boolean excuteCmd(String cmdstr) {
        String[] cmd = new String[]{
                "cmd.exe", "/C", cmdstr};
        BufferedReader br = null;
        String line = null;
        String returnString = null;

        Runtime runtime = Runtime.getRuntime();
        try {
            Process pro = runtime.exec(cmd);
            StringBuffer sbOut = new StringBuffer(1000);
            br = new BufferedReader(new InputStreamReader(pro.getInputStream()));

            while ((line = br.readLine()) != null) {
                System.out.println(line);
                returnString = returnString + line + "\n";
                String[] sourceStrArray = line.split(":");
                if (sourceStrArray.length > 1) {
                    System.out.println(sourceStrArray[0] + "=" + sourceStrArray[1]);
                    if (sourceStrArray[0].equals("Status") && sourceStrArray[1].equals("SUCCESS")) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Excute Error:" + e);
            return false;
        }
    }

    public static String excuteCmd(String cmdstr, boolean returnResult) {
        String[] cmd = new String[]{
                "cmd.exe", "/C", cmdstr};
        BufferedReader br = null;
        String line = null;
        String returnString = "";

        Runtime runtime = Runtime.getRuntime();
        try {
            Process pro = runtime.exec(cmd);
            StringBuffer sbOut = new StringBuffer(1000);
            br = new BufferedReader(new InputStreamReader(pro.getInputStream()));

            while ((line = br.readLine()) != null) {
//                System.out.println(line);/**/
                returnString = returnString + line + "\n";
            }
            return returnString;

        } catch (Exception e) {
            System.out.println("Excute Error:" + e);
            return "error";
        }


    }
}
