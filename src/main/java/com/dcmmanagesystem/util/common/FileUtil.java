package com.dcmmanagesystem.util.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;

public class FileUtil {

    /**
     * 在basePath下保存上传的文件夹
     *
     * @param basePath
     * @param files
     */
    public static void saveMultiFile(String basePath, MultipartFile[] files) {
        if (files == null || files.length == 0) {
            return;
        }
        if (basePath.endsWith("/")) {
            basePath = basePath.substring(0, basePath.length() - 1);
        }
        for (MultipartFile file : files) {
            String filePath = basePath + "/" + file.getOriginalFilename();
            System.out.println(filePath);
            makeDir(filePath);
            File dest = new File(filePath);
            try {
                file.transferTo(dest);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    /***
     * MultipartFile文件保存
     * @param filePath
     * @param file
     */
    public static void saveMultiFile(String filePath, MultipartFile file) {
        if (file == null) {
            return;
        }
        System.out.println(filePath);
        makeDir(filePath);
        File dest = new File(filePath);
        try {
            file.transferTo(dest);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void fileCopy(String src, String dest) {
        File srcFile = new File(src);
        File destFile = new File(dest);
        if (!srcFile.exists())
            return;
        try {
            makeDir(dest);
            int byteRead = 0;
            int byteCount = 0;
            FileInputStream fileInputStream = new FileInputStream(src);
            FileOutputStream fileOutputStream = new FileOutputStream(dest);
            byte[] data = new byte[1024];
            while ((byteRead = fileInputStream.read(data)) != -1) {
                byteCount = byteRead + byteCount;
                fileOutputStream.write(data, 0, byteRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 确保目录存在，不存在则创建
     *
     * @param filePath
     */
    private static void makeDir(String filePath) {
        if (filePath.lastIndexOf('/') > 0) {
            String dirPath = filePath.substring(0, filePath.lastIndexOf('/'));
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
    }

    /***
     *确保目录存在，不存在则创建
     * @param filePath
     */
    public static void makePath(String filePath) {
        File file = new File(filePath);
        if (!file.exists())
            file.mkdirs();
    }

    public static void main(String[] args) {
        String dest="D:/home/jh/dcm4che/data/Dcm_Image/1_常州医疗/901720313/20101020/745901827803004420.dcm";
        String src="D:\\工作\\work\\dcm4che3Demo\\assets\\test6.dcm";
        File srcFile=new File(src);
        File destFile=new File(dest);
       /* try {
            Files.copy(srcFile.toPath(), destFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        FileUtil.fileCopy(src,dest);
    }
}
