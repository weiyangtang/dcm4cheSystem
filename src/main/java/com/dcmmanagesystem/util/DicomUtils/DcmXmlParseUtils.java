package com.dcmmanagesystem.util.DicomUtils;

import com.dcmmanagesystem.util.common.FileUtil;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.io.DicomOutputStream;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @Auther: tangweiyang
 * @Date: 2019/7/20 10:51
 * @Description: dcm文件获取标签属性 ，在TAG类可以查看所有属性
 */
public class DcmXmlParseUtils {
    /**
     * 病人的姓名等属性用*覆盖，避免信息泄露
     */
    private static String replaceTxt = "**********";

    /***
     * dcm文件脱敏
     * @param pathFile
     */
    public static void modifyTag(String pathFile) {
        try {
            File file = new File(pathFile);
            DicomInputStream dis = new DicomInputStream(file);
            Attributes fmi = dis.readFileMetaInformation();
            Attributes attrs = dis.readDataset(-1, -1);

            attrs.setString(Tag.PatientName, VR.PN, replaceTxt);
            attrs.setString(Tag.InstitutionName, VR.PN, replaceTxt);
            attrs.setString(Tag.InstitutionAddress, VR.PN, replaceTxt);
            DicomOutputStream dos = new DicomOutputStream(file);
            dos.writeDataset(fmi, attrs);
            dos.flush();
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*

                System.out.println("Series Instance UID:" + attrs.getString(Tag.SeriesInstanceUID));
                System.out.println("Study Instance UID:" + attrs.getString(Tag.StudyInstanceUID));
                System.out.println("Patient ID:" + attrs.getString(Tag.PatientID));
                System.out.println("SOP Instance UID:" + attrs.getString(Tag.SOPInstanceUID));

    */

    /***
     * 获取dcm文件属性组成文件名  /Patient ID/Study Date/RandomNum.dcm
     * @param pathFile
     * @return
     */
    public static String getFileNameByFile(String pathFile) {
        ///Patient ID/Study Instance UID/Series Instance UID/SOP Instance UID.dcm
        try {
            File file = new File(pathFile);
            DicomInputStream dis = new DicomInputStream(file);
            Attributes fmi = dis.readFileMetaInformation();
            Attributes attrs = dis.readDataset(-1, -1);
            String dcmFilePath = attrs.getString(Tag.PatientID).trim() + "/" + attrs.getString(Tag.StudyDate).trim() + "/";
            String fileName = String.valueOf(System.nanoTime()) + new Random().nextInt(10000) + ".dcm";
            return dcmFilePath + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /***
     *获取dicom文件属性
     * @param pathFile
     * @param attr
     * @return
     */
    public static Map<Integer, String> getDcmAttribute(String pathFile, Map<Integer, String> attr) {
        ///Patient ID/Study Instance UID/Series Instance UID/SOP Instance UID.dcm
        try {
            File file = new File(pathFile);
            DicomInputStream dis = new DicomInputStream(file);
            Attributes fmi = dis.readFileMetaInformation();
            Attributes attrs = dis.readDataset(-1, -1);
            for (Integer key : attr.keySet()) {
//                attr.remove(key);
                String value = attrs.getString(key);
                attr.put(key, value);
            }
            return attr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     * 将通过http协议上传的多个文件批量按照/Patient ID/Study Date/RandomNum.dcm保存在basepath路径下，并且脱敏
     * @param basepath
     * @param files
     * @return
     */
    public static List<String> batchDcmFileSave(String basepath, MultipartFile[] files) {
        if (files == null)
            return null;
        List<String> filePaths = new ArrayList<>();
        FileUtil.saveMultiFile(basepath, files);//将所有文件先保存到服务器上,在进行处理
        for (MultipartFile file : files) {
            String filePath = basepath + "/" + file.getOriginalFilename();
            String suffixName = filePath.substring(filePath.lastIndexOf("."));
            if (!suffixName.equalsIgnoreCase(".dcm"))
                continue;
            DcmXmlParseUtils.modifyTag(filePath);//dcm文件脱敏
            String dcmfilepath = DcmXmlParseUtils.getFileNameByFile(filePath);//获取/Patient ID/Study Instance UID/Series Instance UID/SOP Instance UID.dcm
            filePaths.add(dcmfilepath);
        }
        return filePaths;
    }

/*

    public static String getFileNameByFile(String pathFile) {
        ///Patient ID/Study Instance UID/Series Instance UID/SOP Instance UID.dcm
        try {
            File file = new File(pathFile);
            DicomInputStream dis = new DicomInputStream(file);
            Attributes fmi = dis.readFileMetaInformation();
            Attributes attrs = dis.readDataset(-1, -1);
            return attrs.getString(Tag.SOPInstanceUID) + ".dcm";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
*/


    public static void update(String path, String filename) throws DocumentException, IOException {

        Document doc = new SAXReader().read(new File(path + filename));
        @SuppressWarnings("unchecked")
        List<Element> attrsnodes = doc.getRootElement().elements("DicomAttribute");
        for (Element e : attrsnodes) {
            Attribute idAttr = e.attribute("keyword");
            if (idAttr != null) {
                // 脱敏数据
                if ("PatientName".equalsIgnoreCase(idAttr.getValue())) {
                    Element familyName = e.element("PersonName").element("Alphabetic").element("FamilyName");
                    familyName.clearContent();
                    familyName.addText(replaceTxt);
                } else if ("InstitutionName".equalsIgnoreCase(idAttr.getValue())) {
                    Element Value = e.element("Value");
                    Value.clearContent();
                    Value.addText(replaceTxt);
                } else if ("InstitutionAddress".equalsIgnoreCase(idAttr.getValue())) {
                    Element Value = e.element("Value");
                    Value.clearContent();
                    Value.addText(replaceTxt);
                }
            }
        }
        //1.创建输出流通道
        FileOutputStream out = new FileOutputStream(path + "temp_" + filename);
        OutputFormat format = OutputFormat.createPrettyPrint();//设置contact.xml文件格式（俗称：美观格式）
        format.setEncoding("utf-8");//设置编码格式
        //2.创建写出的对象
        XMLWriter write = new XMLWriter(out, format);
        //3.写出对象
        write.write(doc);
        //4.关闭资源
        write.close();
    }
}