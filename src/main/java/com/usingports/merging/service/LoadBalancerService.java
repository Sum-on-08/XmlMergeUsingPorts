package com.usingports.merging.service;

import com.usingports.merging.config.ServiceConfig;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoadBalancerService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ServiceConfig serviceConfig;
    @Autowired
    private Logger logger;

    public String processXmlFiles(String folderPath) throws IOException {
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
        String url = "http://localhost:" + port + "/mergeXmlPart";
        logger.info("Sending to port " + port + " to " + url);
//        List<String> filePaths = files.stream()
//                .map(File::getPath)
//                .toList(); covert to string format
        return restTemplate.postForObject(url, files, String.class);
    }

    private String finalMerge(String firstHalf, String secondHalf) throws IOException {
        String xmlContent = " <merged>" + firstHalf + secondHalf + "</merged>";
        logger.info(xmlContent);
        return xmlContent.replaceAll("\\s+", "");
    }
}
