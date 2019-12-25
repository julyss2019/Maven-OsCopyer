package com.github.julyss2019.maven.plugins.mavenoscoper;

import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.List;


public class Copyer {
    @Parameter
    private File source;
    @Parameter
    private List<OperatingSystem> operatingSystems;

    public File getSource() {
        return source;
    }

    public List<OperatingSystem> getOperatingSystems() {
        return operatingSystems;
    }
}
