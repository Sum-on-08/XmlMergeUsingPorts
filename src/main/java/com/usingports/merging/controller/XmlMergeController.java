package com.usingports.merging.controller;

import com.usingports.merging.model.XmlMergeRequest;
import com.usingports.merging.model.XmlMergeResponse;
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

    @PostMapping("/mergeXml") //use camelCase
    public XmlMergeResponse mergeXmlFiles(@RequestBody XmlMergeRequest request) {
        String folderPath = (request.getFolderPath());
        logger.info("Received request to merge XML files from folder: {}", folderPath);
        try {
            String mergedXml = loadBalancerService.processXmlFiles(request.getFolderPath());
            XmlMergeResponse responseModel = new XmlMergeResponse(mergedXml);
            return responseModel;
        } catch (Exception e) {
            logger.error("Error processing XML files for folder path: {}", request.getFolderPath(), e);
            XmlMergeResponse responseModel = new XmlMergeResponse("Error processing XML files for folder path: " + request.getFolderPath());
            return responseModel;
        }
    }
}