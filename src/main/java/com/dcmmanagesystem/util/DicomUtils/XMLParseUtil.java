package com.dcmmanagesystem.util.DicomUtils;

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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class XMLParseUtil {

    private static String replaceTxt = "**********";

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

    public static String getTagByFile(String pathFile) {
        ///Patient ID/Study Instance UID/Series Instance UID/SOP Instance UID.dcm
        try {
            File file = new File(pathFile);
            DicomInputStream dis = new DicomInputStream(file);
            Attributes fmi = dis.readFileMetaInformation();
            Attributes attrs = dis.readDataset(-1, -1);
            System.out.println("Series Instance UID:" + attrs.getString(Tag.SeriesInstanceUID));
            System.out.println("Study Instance UID:" + attrs.getString(Tag.StudyInstanceUID));
            System.out.println("Patient ID:" + attrs.getString(Tag.PatientID));
            System.out.println("SOP Instance UID:" + attrs.getString(Tag.SOPInstanceUID));
            System.out.println(attrs.getString(Tag.ImageType));

//            String dcmFilePath = "/" + attrs.getString(Tag.PatientID).trim() + "/" + attrs.getString(Tag.StudyInstanceUID).trim() + "/" + attrs.getString(Tag.SeriesInstanceUID) + "/" + attrs.getString(Tag.SOPInstanceUID).trim() + ".dcm";
//            return "/" + attrs.getString(Tag.PatientID).trim();
            String dcmFilePath = "/" + attrs.getString(Tag.PatientID).trim() + "/" + attrs.getString(Tag.StudyDate).trim()+ "/" + attrs.getString(Tag.SOPInstanceUID).trim() + ".dcm";

            return dcmFilePath;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String getFileNameByFile(String pathFile) {
        ///Patient ID/Study Instance UID/Series Instance UID/SOP Instance UID.dcm
        try {
            File file = new File(pathFile);
            DicomInputStream dis = new DicomInputStream(file);
            Attributes fmi = dis.readFileMetaInformation();
            Attributes attrs = dis.readDataset(-1, -1);
             return attrs.getString(Tag.SOPInstanceUID)+".dcm";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }


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


    public static void main(String[] args) {
        try {
            XMLParseUtil.modifyTag("/home/huax/Desktop/weiyang_tang/DCM_Image/011958333339.dcm");
//7637/1.2.392.200046.100.2.1.47095101944.150601155527/1.2.392.200046.100.2.1.47095101944.150601155527.2/1.2.392.200046.100.2.1.47095101944.150601155527.2.1.1.dcm
            System.out.println(getTagByFile("/home/huax/Desktop/weiyang_tang/DCM_Image/011958333339.dcm"));

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
