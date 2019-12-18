package com.github.julyss2019.maven.plugins.mavenoscoper;

import org.apache.maven.plugins.annotations.Parameter;

public class OperatingSystem {
    @Parameter
    private String os;
    @Parameter
    private String to;
    @Parameter(defaultValue = "false")
    private boolean overwrite;

    public boolean isOverwrite() {
        return overwrite;
    }

    public String getOs() {
        return os;
    }

    public String getTo() {
        return to;
    }
}
