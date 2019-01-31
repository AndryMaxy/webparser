package by.epam.akulich.webparser.generator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class FileGenerator {

    private static Logger LOGGER = LogManager.getLogger(FileGenerator.class.getSimpleName());

    public File generateTmpFile(InputStream inputStream){
        Random random = new Random();
        Path path = Paths.get("/classes/" + random.nextInt(Integer.MAX_VALUE) + "tmp.xml");
        try {
            Files.copy(inputStream, path);
        } catch (IOException e) {
            LOGGER.error("FileGenerator error", e);
        }
        return path.toFile();
    }
}
