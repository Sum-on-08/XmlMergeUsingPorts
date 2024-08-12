package com.usingports.merging.controller;

import com.usingports.merging.service.XmlMergeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
public class XmlMergePartController {
    @Autowired
    private XmlMergeService xmlMergeService;

    @PostMapping("/mergeXmlPart")
    public String mergeXmlPart(@RequestBody List<String> filePaths) {
        List<File> files = filePaths.stream().map(File::new).toList();
        return xmlMergeService.mergeXmlFiles(files);
    }
}