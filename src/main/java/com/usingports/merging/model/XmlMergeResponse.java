package com.usingports.merging.model;

import lombok.Getter;

@Getter
public class XmlMergeResponse {
    private String result;

    public XmlMergeResponse(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
