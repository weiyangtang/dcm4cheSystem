package com.dcmmanagesystem.controller.Test;

import com.dcmmanagesystem.util.common.FileHttpUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * @Auther: tangweiyang
 * @Date: 2019/7/20 10:20
 * @Description:
 */
@Controller
public class HttpServletTest {
    @GetMapping("/downloadfile")
    public void downloadfile(HttpServletResponse response) {
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        String filePath = bundle.getString("file.tempfile.path");
        // String filePath = "D:\\桌面\\社会实践\\暑期实践\\暑期实践\\“探寻传统文化，寻找文化之春”暑期社会实践.doc";

        try {
            FileHttpUtils.downloadFile(response, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}