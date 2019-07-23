package com.dcmmanagesystem.util.DicomUtils;

import java.io.*;


public class CommandUtil {

    public static void exec_thread(String command) {
        try {
            final Process p = Runtime.getRuntime().exec(command);
            new Thread(new Runnable() {

                @Override
                public void run() {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(p.getInputStream()));
                    try {
                        while (br.readLine() != null)
                            ;
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            BufferedReader br = null;
            br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            p.waitFor();
            br.close();
            p.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String exec(String command) {
        String returnString = "";
        Process pro = null;
        Runtime runTime = Runtime.getRuntime();
        if (runTime == null) {
            System.err.println("Create runtime false!");
        }
        try {
            pro = runTime.exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            PrintWriter output = new PrintWriter(new OutputStreamWriter(pro.getOutputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                System.out.println(line);
//                System.out.println();
                returnString = returnString + line + "\n";
            }
            pro.waitFor();
            input.close();
            output.close();
            pro.destroy();
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
        return returnString;
    }

    public static String execNonRes(String command) {
        String returnString = "";
        Process pro = null;
        Runtime runTime = Runtime.getRuntime();
        if (runTime == null) {
            System.err.println("Create runtime false!");
        }
        try {
            pro = runTime.exec(command);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return returnString;
    }

    // ,String username, String password
    public static String exec2(String shellCommand, String username, String password) {
        String returnString = "";
        Process pro = null;
        Runtime runTime = Runtime.getRuntime();
        if (runTime == null) {
            System.err.println("Create runtime false!");
        }
        try {
            // , username, password
            pro = runTime.exec(new String[]{"sudo", "sh", shellCommand, username, password});
            BufferedReader input = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            PrintWriter output = new PrintWriter(new OutputStreamWriter(pro.getOutputStream()));
            String line;
            System.out.println("CommandUtil . exec2");
            while ((line = input.readLine()) != null) {
                System.out.println(line);
                returnString = returnString + line + "\n";
            }
            input.close();
            output.close();
            pro.destroy();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return returnString;
    }

    public static String exec3(String shellCommand, String username, String password) {
        String returnString = "";
        Process pro = null;
        Runtime runTime = Runtime.getRuntime();
        if (runTime == null) {
            System.err.println("Create runtime false!");
        }
        try {
            // , username, password
            pro = runTime.exec(new String[]{"sh", "-c", "sudo", "sh", shellCommand, username, password});
            BufferedReader input = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            PrintWriter output = new PrintWriter(new OutputStreamWriter(pro.getOutputStream()));
            String line;
            System.out.println("CommandUtil . exec3");
            while ((line = input.readLine()) != null) {
                System.out.println(line);
                returnString = returnString + line + "\n";
            }
            input.close();
            output.close();
            pro.destroy();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return returnString;
    }

    public static String exec4(String shellCommand, String username, String password) {
        try {
            ProcessBuilder pb = new ProcessBuilder("sudo", "sh", shellCommand, username, password);
            Process ps = pb.start();
            System.out.println("开始执行脚本文件....");
            try {
                ps.waitFor();
            } catch (InterruptedException e) {
                System.out.println("执行脚本超时100秒....");
            }
            //获取输出
            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            ps.destroy();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "exec4";
    }


//
//    public static String exec5(String username, String password) {
//        String returnString = "";
//        Process pro = null;
//        Runtime runTime = Runtime.getRuntime();
//        if (runTime == null) {
//            System.err.println("Create runtime false!");
//        }
//        try {
//            // , username, password
//            File file = new File(PropKit.get("system.useradd.path"));//进入根目录
//            pro = runTime.exec("sudo sh "+PropKit.get("system.useradd") + " " + username +" "+ password, null, file);
////	        	pro = runTime.exec("sudo sh" +shellCommand +" " + username +" " + password);
//            int result = pro.waitFor();
//            BufferedReader input = new BufferedReader(new InputStreamReader(pro.getInputStream()));
//            PrintWriter output = new PrintWriter(new OutputStreamWriter(pro.getOutputStream()));
//            String line;
//
//            System.out.println("CommandUtil . exec5");
//            while ((line = input.readLine()) != null) {
//                System.out.println(line);
//                returnString = returnString + line + "\n";
//            }
//            System.out.println(result);
//            input.close();
//            output.close();
//            pro.destroy();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return returnString;
//    }
//
//    public static void exec6(String username, String password) {
//        try {
//
//            ProcessBuilder pb = new ProcessBuilder("sudo useradd "+username,
//                    "sudo mkdir /home/" +username, "sudo passwd " + password,
//                    "sudo chown username /home/"+username,
//                    "sudo chgrp username /home/"+username,
//                    "sudo adduser "+username, "sudo adduser "+username+" sudo");
//            pb.redirectErrorStream();
//            Process process = pb.start();
//            InputStream inputStream = process.getInputStream();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(
//                    inputStream));
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//            process.waitFor();
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//    }

}

