package by.epam.akulich.webparser.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

public class FileGenerator {

    public File generateTmpFile(InputStream inputStream){
        Random random = new Random();
        File file = new File("/" + random.nextInt(Integer.MAX_VALUE) + "tmp.tmp");
        try (OutputStream outputStream = new FileOutputStream(file)) {
            byte[] bytes = new byte[1024];
            int len = inputStream.read(bytes);
            outputStream.write(bytes, 0, len);
        } catch (IOException e) {

        }
        return file;
    }
}
