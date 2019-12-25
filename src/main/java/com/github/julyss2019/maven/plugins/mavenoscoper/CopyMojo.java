package com.github.julyss2019.maven.plugins.mavenoscoper;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;


@Mojo(name = "copy", defaultPhase = LifecyclePhase.PACKAGE)
public class CopyMojo extends AbstractMojo {
    private static final String OS_NAME = System.getProperty("os.name");
    @Parameter
    private List<Copyer> copyers;

    public void execute() {
        final Log log = getLog();

        log.info("当前系统: " + OS_NAME);

        copyers.forEach(copyer -> {
            java.io.File fromFile = copyer.getFrom();

            if (!fromFile.exists()) {
                getLog().error(new RuntimeException("文件不存在: " + fromFile.getAbsolutePath()));
            }

            copyer.getOperatingSystems()
                    .stream()
                    .filter(osConfig -> osConfig.getName().equalsIgnoreCase(OS_NAME))
                    .collect(Collectors.toList()).forEach(os -> {
                        File toFile = os.getTo();

                        if (toFile.isDirectory()) {
                            getLog().error(new RuntimeException("路径必须是文件: " + toFile.getAbsolutePath()));
                        }

                        if (toFile.exists() && !os.isOverwrite()) {
                            getLog().warn("文件未被复制: " + toFile.getAbsolutePath() + ", 因为文件已存在, 且未设置 overwrite 属性");
                            return;
                        }

                        try {
                            Files.copy(fromFile.toPath(), toFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            getLog().error(e);
                        }

                        getLog().info("完成复制: " + fromFile.getAbsolutePath() + " -> " + toFile.getAbsolutePath());
            });
        });
    }
}
