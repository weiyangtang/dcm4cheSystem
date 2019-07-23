package com.dcmmanagesystem.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:dicomFile.properties")
@ConfigurationProperties(prefix = "dicom")
@Data
public class DcmServer {
    private int port;
    private String localAcceptDirPath;
    private String fileFormat;
    private String binPath;
    private String aet;
    private String uploadPath;
    private String otherfileupload;
}
