package ru.t1.yd.ocrservice.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface CommonUtil {

    static String getResourceDirectoryPath() {
        Path resourceDirectory = Paths.get("src", "test", "resources");
        return resourceDirectory.toFile().getAbsolutePath();
    }

    static byte[] concatByteArrays(final byte[] a, final byte[] b) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            outputStream.write(a);
            outputStream.write(b);
        }catch (final IOException e){
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

}
