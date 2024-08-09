package com.usingports.merging.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

@Service
public class XmlMergeService {
    @Autowired
    private Logger logger;

    public String mergeXmlFiles(List<File> files) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document mergedDoc = dBuilder.newDocument();
            Element rootElement = mergedDoc.createElement("merged-part");
            mergedDoc.appendChild(rootElement);

            for (File file : files) {
                Document doc = dBuilder.parse(file);
                rootElement.appendChild(mergedDoc.importNode(doc.getDocumentElement(), true));
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(mergedDoc), new StreamResult(writer));

            return writer.getBuffer().toString();
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            logger.error("Failed to merge XML files", e);
            return "<error>Failed to merge XML files</error>";
        }
    }
}
