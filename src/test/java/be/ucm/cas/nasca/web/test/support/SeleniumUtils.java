package be.ucm.cas.nasca.web.test.support;

import com.hp.gagawa.java.elements.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map;

public final class SeleniumUtils extends TestBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    public static void waitForAction(int tps) {
        try {
            Thread.sleep(tps);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void waitForActionCommon() {
        try {
            Thread.sleep(TestData.COMMON_WAIT);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void isLoading() {
        nascaPage.doWait();
    }

    public static void fermerNotification() {
        nascaPage.fermerNotification();
    }

    public static void waitForActionLong() {
        try {
            Thread.sleep(TestData.LONG_WAIT);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void waitForBatch() {
        try {
            Thread.sleep(TestData.BATCH_WAIT);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static String generateRandomString() {
        return RandomStringUtils.random(5, true, false);
    }

    public static int getRandomNumberBetween(int min, int max) {
        Random foo = new Random();
        int randomNumber = foo.nextInt(max - min) + min;
        if (randomNumber == min) {
            return min + 1;
        } else {
            return randomNumber;
        }
    }

    public static String deleteFormat(String args) {
        String decode = StringUtils.deleteWhitespace(args);

        decode = StringUtils.replace(decode, "-", "");
        decode = StringUtils.replace(decode, "/", "");
        decode = StringUtils.replace(decode, ".", "");
        decode = StringUtils.replace(decode, "€", "");

        return decode;
    }

    public static BigDecimal transformStringToBigDecimal(String element) {
        String elementtrfsm = element.replace("€", "");
        elementtrfsm = elementtrfsm.replace(",", ".");
        elementtrfsm = StringUtils.deleteWhitespace(elementtrfsm);

        BigDecimal elemntbdml = new BigDecimal(elementtrfsm);
        return elemntbdml.setScale(2, BigDecimal.ROUND_DOWN);
    }

    public static String getConvertedStringFormat(String nissorbcenumber) {
        String str1 = nissorbcenumber.substring(0, 4);
        String str2 = nissorbcenumber.substring(6, 9);
        return str1 + "Z_" + str2;
    }

    public static String formatNissOrBceOrNumeroClient(String numero) {
        if (numero.length() == 9) {
            return numero.substring(0, 4) + "/" + numero.substring(4, 9);
        } else if (numero.length() == 11) {
            return numero.substring(0, 6) + "-" + numero.substring(6, 9) + "-" + numero.substring(9, 11);
        } else {
            return numero.substring(0, 4) + " " + numero.substring(4, 7) + " " + numero.substring(7, 10);
        }
    }

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    public static String createScreenshot(WebDriver driver) {
        File image = new File(TestData.IMAGE_LOCATION);
        if (!image.exists() && image.mkdir()) {
            LOGGER.info("Répertoire créé " + image.getPath());
        }
        UUID uuid = UUID.randomUUID();
        // generate screenshot as a file object
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            // copy file object to designated location
            FileUtils.copyFile(scrFile, new File(image.toString() + File.separator + uuid + ".png"));
        } catch (IOException e) {
            LOGGER.error("Error while generating screenshot", e);
        }

        return TestData.IMAGE_PATH + uuid + ".png";
    }

    private static boolean isAlertPresent() {
        try {
            TestBase.getDriver().switchTo().alert();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void clickConfirmAlert() {
        nascaPage.confirmerAlert();
    }

    public static void doAlertAccept() {
        if (isAlertPresent()) {
            try {
                TestBase.getDriver().switchTo().alert();
                TestBase.getDriver().switchTo().alert().accept();
                if (isAlertPresent()) {
                    TestBase.getDriver().switchTo().alert();
                    TestBase.getDriver().switchTo().alert().accept();
                }
                TestBase.getDriver().switchTo().defaultContent();
                SeleniumUtils.isLoading();
            } catch (Exception e) {

            }
        }
    }

    private static List<String> getFileFromDirectoryReport() {
        List<String> results = new ArrayList<>();

        File[] files = null;
        File reportFolder = new File(ReportManager.getReportFolder());
        if (reportFolder.exists()) {
            files = reportFolder.listFiles();
            Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
        }
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && !(file.getName().contains("index.html"))) {
                    results.add(file.getName());
                }
            }
        }
        return results;
    }

    public static void createHtmlforJenkins() {
        List<String> filelist = getFileFromDirectoryReport();
        if (filelist != null) {
            File htmlfile = new File(ReportManager.getReportFolder() + "index.html");

            Html html = new Html();
            Head head = new Head();

            html.appendChild(head);

            Title title = new Title();
            title.appendChild(new Text("Report directory Index"));
            head.appendChild(title);

            Body body = new Body();

            html.appendChild(body);

            Div h1 = new Div();
            h1.appendChild(new Text("Report directory file list:"))
                    .setStyle(
                            "Font: 20px Verdana; font-weight:bold;border-radius: 5px;  float: center;  width: auto;  height: auto;  text-align:center;");
            body.appendChild(h1);

            for (String filename : filelist) {
                Div div = new Div();
                div.setId(filename)
                        .setStyle(
                                "border-radius: 5px;  background: #00BFFF;  width: auto;  height: auto;  margin: 1em;  text-align:center;");
                A link = new A();
                link.setHref(filename).setTarget("_blank")
                        .setStyle("text-decoration: none;");

                div.appendChild(link);
                link.appendText(filename);

                body.appendChild(div);
            }

            StringBuilder buildhtml = new StringBuilder();
            buildhtml.append(html.write());
            try {
                FileUtils.writeStringToFile(htmlfile, buildhtml.toString());
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    public static String getNatureProfilfromCodeDB(String natureCode) {
        if (!StringUtils.isEmpty(natureCode)) {
            switch (natureCode) {
                case "1":
                    return TestData.NATURE_PROFILE_PRINCIPAL;
                case "4":
                    return TestData.NATURE_PROFILE_COMPLEMENTAIRE;
                case "5":
                    return "maxi";
                case "2":
                    return "Assurance continuée pension/maladie-invalidité";
                case "6":
                    return "mini";
                case "7":
                    return "Conjoint aidant maxi statut";
                case "8":
                    return "Age pension atteint sans pension";
                case "9":
                    return "Bénéfice d'une pension";
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    public static String getNatureMenuProfilfromCodeDB(String natureCode) {
        if (!StringUtils.isEmpty(natureCode)) {
            switch (natureCode) {
                case "1":
                    return TestData.NATURE_PROFILE_PRINCIPAL;
                case "4":
                    return TestData.NATURE_PROFILE_COMPLEMENTAIRE;
                case "5":
                    return TestData.NATURE_PROFILE_MAXI_STATUT;
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    public static Map<Integer, String> makeMapElementDocuments(String[] arrayText) {
        int i = 0;
        Map<Integer, String> mapElementsDocument = new LinkedHashMap<>();
        for (String s : arrayText) {
            if (s != null && !StringUtils.isEmpty(s.trim())) {
                i++;
                mapElementsDocument.put(i, s);
            }
        }
        return mapElementsDocument;
    }
}