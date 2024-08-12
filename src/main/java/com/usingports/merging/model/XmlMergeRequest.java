package com.usingports.merging.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
public class XmlMergeRequest {

    private final String folderPath;

    @JsonCreator
    public XmlMergeRequest(@JsonProperty("folderPath") String folderPath) {
        this.folderPath = folderPath;
    }

}