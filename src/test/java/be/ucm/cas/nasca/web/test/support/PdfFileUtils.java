package be.ucm.cas.nasca.web.test.support;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public final class PdfFileUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    public PdfFileUtils() {
    }

    public static boolean verifyPDFContent(WebDriver driver, String reqTextInPDF) {
        boolean flag = false;

        PDDocument pdDoc = null;
        COSDocument cosDoc = null;
        String parsedText = null;

        try {
            URL url = new URL(driver.getCurrentUrl());
            BufferedInputStream file = new BufferedInputStream(url.openStream());
            PDFParser parser = new PDFParser(file);

            parser.parse();
            cosDoc = parser.getDocument();
            PDFTextStripper pdfStripper = new PDFTextStripper();
            pdfStripper.setStartPage(1);
            pdfStripper.setEndPage(1);

            pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc);
        } catch (MalformedURLException e2) {
            LOGGER.error(e2.getMessage());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            try {
                if (cosDoc != null)
                    cosDoc.close();
                if (pdDoc != null)
                    pdDoc.close();
            } catch (Exception e1) {
                LOGGER.error(e1.getMessage());
            }
        }

        if (parsedText != null && parsedText.contains(reqTextInPDF)) {
            TestBase.getReport().log(LogStatus.PASS, "PDF content: " + reqTextInPDF);
            flag = true;
        } else {
            TestBase.getReport().log(LogStatus.FAIL, "PDF content: " + reqTextInPDF);
        }

        return flag;
    }
}