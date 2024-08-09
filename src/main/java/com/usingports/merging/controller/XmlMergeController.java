package com.usingports.merging.controller;

import com.usingports.merging.model.XmlMergeRequest;
import com.usingports.merging.service.LoadBalancerService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class XmlMergeController {
    @Autowired
    private LoadBalancerService loadBalancerService;
    @Autowired
    private Logger logger;

    @PostMapping("/merge-xml")
    public ResponseEntity<String> mergeXmlFiles(@RequestBody XmlMergeRequest request) {
        String folderPath = request.getFolderPath();
        logger.info("Received request to merge XML files from folder: {}", folderPath);
        try {
            String mergedXml = loadBalancerService.processXmlFiles(request.getFolderPath());
            return ResponseEntity.ok(mergedXml);
        } catch (Exception e) {
            logger.error("Error processing XML files for folder path: {}", request.getFolderPath(), e);
            return ResponseEntity.badRequest().body("Error processing XML files: ");
        }
    }
}