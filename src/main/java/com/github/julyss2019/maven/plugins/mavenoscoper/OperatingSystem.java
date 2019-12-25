package com.github.julyss2019.maven.plugins.mavenoscoper;

import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

public class OperatingSystem {
    @Parameter
    private String name;
    @Parameter
    private File dest;
    @Parameter(defaultValue = "false")
    private boolean overwrite;

    public boolean isOverwrite() {
        return overwrite;
    }

    public String getName() {
        return name;
    }

    public File getDest() {
        return dest;
    }
}
