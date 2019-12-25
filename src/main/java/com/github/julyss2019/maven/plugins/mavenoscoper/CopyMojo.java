package com.github.julyss2019.maven.plugins.mavenoscoper;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.*;
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
            java.io.File sourceFile = copyer.getSource();

            if (!sourceFile.exists()) {
                getLog().error(new RuntimeException("文件不存在: " + sourceFile.getAbsolutePath()));
            }

            copyer.getOperatingSystems()
                    .stream()
                    .filter(osConfig -> osConfig.getName().equalsIgnoreCase(OS_NAME))
                    .collect(Collectors.toList()).forEach(os -> {
                        File toFile = os.getDest();

                        if (toFile.isDirectory()) {
                            getLog().error(new RuntimeException("路径必须是文件: " + toFile.getAbsolutePath()));
                        }

                        if (toFile.exists() && !os.isOverwrite()) {
                            getLog().warn("文件未被复制: " + toFile.getAbsolutePath() + ", 因为文件已存在, 且未设置 overwrite 属性");
                            return;
                        }

                        try {
                            copyFile(sourceFile, toFile);
                        } catch (IOException e) {
                            getLog().error(e);
                        }

                        getLog().info("完成复制: " + sourceFile.getAbsolutePath() + " -> " + toFile.getAbsolutePath());
            });
        });
    }

    private static void copyFile(File source, File dest) throws IOException {
        try (InputStream is = new FileInputStream(source); OutputStream os = new FileOutputStream(dest)) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
    }
}
