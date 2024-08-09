package com.usingports.merging.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class XmlMergeRequest {
    private final String folderPath;

//    public XmlMergeRequest(String folderPath) {
//        this.folderPath = folderPath;
//    }

    @JsonCreator
    public XmlMergeRequest(@JsonProperty("folderPath") String folderPath) {
        this.folderPath = folderPath;
    }

    public String getFolderPath() {
        return folderPath;
    }
}