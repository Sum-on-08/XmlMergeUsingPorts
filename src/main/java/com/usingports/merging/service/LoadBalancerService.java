package com.usingports.merging.service;

import com.usingports.merging.config.ServiceConfig;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Service
public class LoadBalancerService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ServiceConfig serviceConfig;
    @Autowired
    private Logger logger;

    public String processXmlFiles(String folderPath) {
        List<File> xmlFiles = getXmlFiles(folderPath);
        int midPoint = xmlFiles.size() / 2;

        List<File> firstHalf = xmlFiles.subList(0, midPoint);
        List<File> secondHalf = xmlFiles.subList(midPoint, xmlFiles.size());

        String mergedFirstHalf = sendToPort(firstHalf, serviceConfig.getFirstPort());
        String mergedSecondHalf = sendToPort(secondHalf, serviceConfig.getSecondPort());
        logger.info("Processing for final merge");
        String FinalResult = finalMerge(mergedFirstHalf, mergedSecondHalf);
        logger.info("Process completed successfully");
        return FinalResult;
    }

    private List<File> getXmlFiles(String folderPath) {
        File folder = new File(folderPath);
        return Arrays.asList(folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml")));
    }

    private String sendToPort(List<File> files, int port) {
        String url = "http://localhost:" + port + "/merge-xml-part";
        logger.info("Sending to port " + port + " to " + url);
        return restTemplate.postForObject(url, files, String.class);
    }

    private String finalMerge(String firstHalf, String secondHalf) {
        String xmlContent = "<merged>" + firstHalf + secondHalf + "</merged>";
        return xmlContent;
    }
}
