package com.github.julyss2019.maven.plugins.mavenoscoper;

import org.apache.maven.plugins.annotations.Parameter;

import java.util.List;


public class Copyer {
    @Parameter
    private String from;
    @Parameter
    private List<OperatingSystem> operatingSystems;

    public String getFrom() {
        return from;
    }

    public List<OperatingSystem> getOperatingSystems() {
        return operatingSystems;
    }
}
