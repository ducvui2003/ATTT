package nlu.fit.leanhduc.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {
    public static void writeStringToFile(String path, String content) throws IOException {
        Files.write(Paths.get(path), content.getBytes());
    }
}
