package com.dcmmanagesystem.util.common;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Auther: tangweiyang
 * @Date: 2019/7/20 09:54
 * @Description: 在http协议下的通用文件下载、图片获取、文件上传
 */
public class FileHttpUtils {
    /***
     * 以二进制格式文件下载
     * @param response
     * @param filePath
     * @throws IOException
     */
    public static void downloadFile(HttpServletResponse response, String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists())
            return;
        else {
            /***
             * 二进制文件下载的header,content-type:application/octet-stream, 以附件的形式下载
             */
            //file.getPath().lastIndexOf(File.separator);
            String fileName = file.getName();
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "ISO-8859-1"));

            /**
             * 将file以二进制的形式读取，写入到HttpServletResponse的outputStream
             */
            FileInputStream fileInputStream = new FileInputStream(file);
            int len = fileInputStream.available();
            byte[] fileByte = new byte[len];
            fileInputStream.read(fileByte);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(fileByte);
            outputStream.flush();
        }
    }


}