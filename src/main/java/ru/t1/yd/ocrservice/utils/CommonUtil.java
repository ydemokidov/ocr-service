package ru.t1.yd.ocrservice.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

public interface CommonUtil {

    static String getResourceDirectoryPath(){
        Path resourceDirectory = Paths.get("src","test","resources");
        return resourceDirectory.toFile().getAbsolutePath();
    }

}
