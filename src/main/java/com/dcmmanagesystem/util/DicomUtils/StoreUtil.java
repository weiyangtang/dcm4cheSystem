package com.dcmmanagesystem.util.DicomUtils;

import com.dcmmanagesystem.model.DcmServer;

/**
 * storescp接收文件,storescu发送文件的工具类
 */
public class StoreUtil {

//    @Autowired
//    static DcmServer dcmServer;


    private StoreUtil() {
    }//防止new

    //开启监控端口
    public static int getPort(int port) {
        String cmd = "lsof -i:";
        for (int newPort = port; newPort < 99999; newPort++) {
            String cmdstr = cmd + newPort;
//            String res = cmdUtil.excuteCmd(cmdstr, true);
            String res = CommandUtil.exec(cmdstr);
//            System.out.println(newPort + "\n" + res);
            if (res == null || res.trim().equals(""))
                return newPort;
        }
        return -1;
    }

    /***
     *判断端口是否开放
     * @param port
     * @return:true未被占用
     */
    public static boolean openPort(int port) {
        String cmd = "lsof -i:";
        String cmdstr = cmd + port;
        String res = CommandUtil.exec(cmdstr);
        if (res == null || res.trim().equals(""))
            return true;
        return false;
    }

    /*
     * 开启接收模式
     * */
    public static void storeScp(DcmServer dcmServer) {
        System.out.println(dcmServer.getPort());
        int port = getPort(dcmServer.getPort());
        dcmServer.setPort(port);
        String scpCmd = dcmServer.getBinPath() + "storescp -b " + dcmServer.getAet() + ":" + port + "  --filepath " + dcmServer.getFileFormat() + "  --directory " + dcmServer.getLocalAcceptDirPath();
        System.out.println(scpCmd);
//        cmdUtil.excuteCmd(scpCmd);
        new Thread(new Runnable() {
            @Override
            public void run() {
//                cmdUtil.excuteCmd(scpCmd);
                CommandUtil.exec(scpCmd);
            }
        }).start();
//        CommandUtil.exec(scpCmd);
//        CommandUtil.execNonRes(scpCmd);
//        CommandUtil.exec(scpCmd);
    }

    /*
     * 发送dcm文件
     * */
    public static void storeScu(DcmServer dcmServer, String dcmFilePath) {
        //storescu -c STORESCP@localhost:11112  G:\work\dcm_data\a.dcm
        String scuCmd = dcmServer.getBinPath() + "storescu -c " + dcmServer.getAet() + "@localhost" + ":" + dcmServer.getPort() + " " + dcmFilePath;
        System.out.println(scuCmd);
//        cmdUtil.excuteCmd(scuCmd);
        CommandUtil.exec(scuCmd);
//        CommandUtil.execNonRes(scuCmd);//异步发送dcm文件,测试数据:发送191份dcm文件,除去2份有问题的文件外全部发送成功
    }


    public static void main(String[] args) {
        int port = getPort(3306);
        System.out.println(port);
    }

}
