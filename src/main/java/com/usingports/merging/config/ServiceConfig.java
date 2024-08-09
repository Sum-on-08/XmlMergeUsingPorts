package com.usingports.merging.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    @Value("${service.port.first}")
    private int firstPort;

    @Value("${service.port.second}")
    private int secondPort;

    public int getFirstPort() {
        return firstPort;
    }

    public int getSecondPort() {
        return secondPort;
    }
}