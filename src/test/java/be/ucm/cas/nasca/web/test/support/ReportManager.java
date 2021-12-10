package be.ucm.cas.nasca.web.test.support;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.NetworkMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public final class ReportManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    private static final File report = new File(TestData.REPORT_LOCATON);
    private static final File temp = new File(TestData.TEMP_LOCATION);
    private static final File image = new File(TestData.IMAGE_LOCATION);
    private static final File video = new File(TestData.MOVIE_LOCATION);
    private static String reportName;
    private static String reportLocation;
    private static String reportFolder;

    private static ExtentReports extent;

    public ReportManager() {
    }

    public static String getReportFolder() {
        return reportFolder;
    }

    private static void setReportFolder(String reportFolder) {
        ReportManager.reportFolder = reportFolder;
    }

    public static ExtentReports getInstance() {
        if (TestBase.getExtent() == null) {
            if (!report.exists() && temp.mkdir()) {
                LOGGER.info("Répertoire créé " + temp.getPath());
            }

            cleanReport();

            reportName = "TestReport.html";

            reportLocation = TestData.REPORT_LOCATON + reportName;
            setReportFolder(TestData.REPORT_LOCATON);

            extent = new ExtentReports(reportLocation, false, DisplayOrder.OLDEST_FIRST, NetworkMode.ONLINE);

            extent.loadConfig(new File("src/main/resources/report/extent-config.xml"));

            setReportName(reportName);
            setReportLocation(reportLocation);

            extent.addSystemInfo("Selenium Version", "3.0.1")
                    .addSystemInfo("Test", "Seleniem Test execution")
                    .addSystemInfo("OS", "Windows 7 Professionnel");
            setReport(extent);
            try {
                TestBase.startRecording();
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return extent;
    }

    private static void setReport(ExtentReports report) {
        ReportManager.extent = report;
    }

    private static void cleanReport() {
        Calendar calJour = Calendar.getInstance();
        calJour.set(Calendar.HOUR, 0);
        calJour.set(Calendar.AM_PM, Calendar.AM);
        calJour.set(Calendar.MINUTE, 0);
        calJour.set(Calendar.SECOND, 0);
        calJour.set(Calendar.MILLISECOND, 0);
        calJour.add(Calendar.DAY_OF_MONTH, -TestData.REPORT_ITERATIONS);

        fileExists(report, calJour);
        fileExists(image, calJour);
        fileExists(video, calJour);
    }

    private static void fileExists(File files, Calendar calJour) {
        File[] listOfFiles;

        if (files.exists()) {
            listOfFiles = files.listFiles();
            if (listOfFiles != null) {
                boucleDeleteFile(calJour, listOfFiles);
            }
        }
    }

    private static void boucleDeleteFile(Calendar calJour, File[] listOfFiles) {
        for (File file : listOfFiles) {
            Date dateFile = new Date(file.lastModified());

            if (dateFile.before(calJour.getTime()) && file.delete()) {
                LOGGER.info(TestData.FICHIER_SUPPRIME + file.getPath());
            }
        }
    }

    private static void setReportName(String reportName) {
        ReportManager.reportName = reportName;
    }

    private static void setReportLocation(String reportLocation) {
        ReportManager.reportLocation = reportLocation;
    }
}