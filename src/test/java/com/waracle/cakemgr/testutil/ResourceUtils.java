package com.waracle.cakemgr.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ResourceUtils {

    private ResourceUtils() {
    }

    public static String readResourceFileToString(String filePath) {
        Path path = Paths.get(filePath);
        Stream<String> lines = null;
        try {
            lines = Files.lines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines.collect(Collectors.joining("\n"));
    }

}