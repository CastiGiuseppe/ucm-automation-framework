package be.ucm.cas.nasca.web.test.support;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

public final class ReplaceContentInTestCaseUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    public ReplaceContentInTestCaseUtils() {
    }

    public static void replace(String fileInput, String fileOutput, Map<String, String> map) {
        try {
            String content = FileUtils.readFileToString(new File(fileInput), "UTF-8");

            for (Entry<String, String> entry : map.entrySet()) {
                content = content.replaceAll(entry.getKey(), entry.getValue());
            }

            File tempFile = new File(fileOutput);
            FileUtils.writeStringToFile(tempFile, content, "UTF-8");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}