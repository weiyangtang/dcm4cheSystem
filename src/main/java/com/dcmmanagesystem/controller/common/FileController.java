package com.dcmmanagesystem.controller.common;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: tangweiyang
 * @Date: 2019/7/18 08:32
 * @Description:
 */
@Controller
@RequestMapping("/file")
@Api(tags = "项目界面的api")
public class FileController {
    @GetMapping("/view")
    public String fileUploadView() {
        return "common/fileupload";
    }

    @RequestMapping(value = "/dcmfileUploadTest", method = RequestMethod.POST)
    public int dcmfileUpload(HttpServletRequest request, @RequestParam("pid") String pid, @RequestParam("file") MultipartFile[] files){
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            System.out.println(file.getOriginalFilename());
        }
        return 1;
    }
}