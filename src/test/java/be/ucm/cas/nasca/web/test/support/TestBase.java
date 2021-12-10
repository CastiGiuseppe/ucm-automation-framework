package be.ucm.cas.nasca.web.test.support;

import be.ucm.cas.nasca.web.pagefactory.administration.AdministrationPage;
import be.ucm.cas.nasca.web.pagefactory.affiliation.AffiliationPmWizardPage;
import be.ucm.cas.nasca.web.pagefactory.affiliation.AffiliationPpWizardPage;
import be.ucm.cas.nasca.web.pagefactory.comptabilite.ComptabilitePage;
import be.ucm.cas.nasca.web.pagefactory.gestionclient.GestionClientPage;
import be.ucm.cas.nasca.web.pagefactory.home.HomeMenuPage;
import be.ucm.cas.nasca.web.pagefactory.login.LoginPage;
import be.ucm.cas.nasca.web.pagefactory.multidossiers.MultiDossiersPage;
import be.ucm.cas.nasca.web.pagefactory.nasca.NascaPage;
import be.ucm.cas.nasca.web.pagefactory.rem.RemWizardPage;
import be.ucm.cas.nasca.web.pagefactory.signaletique.SignaletiquePage;
import be.ucm.cas.nasca.web.pagefactory.taches.TachePage;
import be.ucm.cas.nasca.web.test.support.dao.NascaDao;
import com.codeborne.selenide.Condition;
import com.eviware.soapui.model.testsuite.TestRunner;
import com.eviware.soapui.model.testsuite.TestRunner.Status;
import com.jcraft.jsch.SftpException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.joda.time.DateTime;
import org.monte.media.Format;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.monte.screenrecorder.ScreenRecorder.State;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ProxySelector;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static org.monte.media.VideoFormatKeys.*;

public abstract class TestBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());
    private static final SoftAssert softassert = new SoftAssert();

    private static String currentUrl;
    private static RunBatch batch;
    private static WebDriver driver;
    private static JavascriptExecutor jsExec;
    private static LoginPage loginPage;
    private static ExtentReports extent = null;
    private static ExtentTest report;
    private static ScreenRecorder screenRecorder;
    private static UUID uuid;
    private static String machineName;
    private static ProxySelector proxy;
    private static Date dateDebutTest;

    protected static NascaPage nascaPage;
    protected static SignaletiquePage signaletiquePage;
    protected static AffiliationPmWizardPage affiliationPmWizardPage;
    protected static AffiliationPpWizardPage affiliationPpWizardPage;
    protected static HomeMenuPage homeMenuPage;
    protected static TachePage tachePage;
    protected static AdministrationPage administrationPage;
    protected static MultiDossiersPage multiDossiersPage;
    protected static ComptabilitePage comptabilitePage;
    protected static GestionClientPage gestionClientPage;
    protected static RemWizardPage remWizardPage;
    private static TableUtils tableUtils;

    private ApplicationContext aplictxini = new ApplicationContext();

    protected abstract EnumTypeTest getTypeTest();

    @BeforeTest
    public void initializeTest() throws Exception {
        driverCreation();

        EnumTypeTest typeTest = getTypeTest();
        LOGGER.info("typeTest : " + typeTest);
        if (EnumTypeTest.AFFI_PP.equals(typeTest)) {
            DaoFunctionality.cleanAllDataByIdentite(TestData.NISS_NUMBER);
            DaoFunctionality.cleanAllDataByIdentite(TestData.NISS_NUMBER_01);
            DaoFunctionality.cleanAllDataByIdentite(TestData.NISS_NUMBER_CA);
            DateTime currentDateTime = new DateTime();
            DaoFunctionality.getNascaDao().updateAffectationComptable(DateUtils.doFormat(TestData.DATE_FORMAT_XML, currentDateTime.minusDays(3).toDate()));
            DaoFunctionality.getNascaDao().updateExecuteTrueEnrolementPp(DateUtils.getYearNow().substring(0,4), String.valueOf(DateUtils.getQuarterNumber(DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, new Date()))));
        } else if (EnumTypeTest.AFFI_PM.equals(typeTest)) {
            DaoFunctionality.cleanAllDataByIdentite(TestData.BCE_NUMBER);
            DaoFunctionality.cleanAllDataByIdentite(TestData.NISS_NUMBER_01);

            BatchUtils.editScriptJS("/opt/tomcat/webapps/Nasca-Web/scripts/nasca/affiliation/pm/phase1/", "affiliation.create.demande.js", TestData.DATE_PAST_TRUE, TestData.DATE_PAST_FALSE);
            BatchUtils.editScriptJS("/opt/tomcat/webapps/Nasca-Web/scripts/nasca/affiliation/pm/phase1/", "affiliation.create.demande.js", TestData.DATE_PAST_TRUE_BLANK, TestData.DATE_PAST_FALSE_BLANK);
            RunBatch.restartTomcat();
            loginAndRetry();
        } else if (EnumTypeTest.E2E.equals(typeTest)) {
            DaoFunctionality.cleanAllDataByIdentite(TestData.BCE_NUMBER_E2E);
            DaoFunctionality.cleanAllDataByIdentite(TestData.NISS_NUMBER_E2E);
            DaoFunctionality.cleanAllDataByIdentite(TestData.NISS_NUMBER_E2E_CA);
        } else if (EnumTypeTest.AUTRE.equals(typeTest) || EnumTypeTest.TRF_PM.equals(typeTest)) {
            DateTime currentDateTime = new DateTime();
            Date yesterday = currentDateTime.minusDays(3).toDate();
            String batchdate = DateUtils.doFormat(TestData.DATE_FORMAT_XML, yesterday);
            DaoFunctionality.getNascaDao().updateAffectationComptable(batchdate);

            Date tomorrow = currentDateTime.plusMonths(3).toDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(tomorrow);
            DaoFunctionality.getNascaDao().initialisationDateEnrolementMajoPPAnnuelle(calendar.get(Calendar.YEAR), tomorrow);
            DaoFunctionality.getNascaDao().initialisationDateEnrolementMajoPPTrimestrielle(calendar.get(Calendar.YEAR), DateUtils.getQuarterNumber(DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, new Date())), new Date());
            DaoFunctionality.getNascaDao().initialisationDateEnrolementMajoPPTrimestrielle(calendar.get(Calendar.YEAR), DateUtils.getQuarterNumber(DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, tomorrow)), tomorrow);

            DaoFunctionality.getNascaDao().initialisationDatePaiement(new Date());

            if (EnumTypeTest.TRF_PM.equals(typeTest)) {
                BatchUtils.editScriptJS("/opt/tomcat/webapps/Nasca-Web/scripts/nasca/affiliation/pm/phase1/", "affiliation.create.demande.js", TestData.DATE_PAST_TRUE, TestData.DATE_PAST_FALSE);
                BatchUtils.editScriptJS("/opt/tomcat/webapps/Nasca-Web/scripts/nasca/affiliation/pm/phase1/", "affiliation.create.demande.js", TestData.DATE_PAST_TRUE_BLANK, TestData.DATE_PAST_FALSE_BLANK);
                RunBatch.restartTomcat();
                loginAndRetry();
            }
        } else if (EnumTypeTest.ENROLEMENT.equals(typeTest)) {
            DateTime currentDateTime = new DateTime();
            Date yesterday = currentDateTime.minusDays(3).toDate();
            String batchdate = DateUtils.doFormat(TestData.DATE_FORMAT_XML, yesterday);
            DaoFunctionality.getNascaDao().updateAffectationComptable(batchdate);

            BatchUtils.editScriptJS("/opt/tomcat/webapps/Nasca-Web/scripts/nasca/revenus/reguls/edit/", "ajoutModifRevenus.js", TestData.DATE_PAST_TRUE, TestData.DATE_PAST_FALSE);
            BatchUtils.editScriptJS("/opt/tomcat/webapps/Nasca-Web/scripts/nasca/revenus/reguls/edit/", "ajoutModifRevenus.js", TestData.DATE_PAST_TRUE_BLANK, TestData.DATE_PAST_FALSE_BLANK);
            RunBatch.restartTomcat();
            loginAndRetry();
        } else if (EnumTypeTest.LM.equals(typeTest)) {
            // copie fichier pdf dans temp
            FileUtils.copyFile(new File(TestData.FILE_TEMPLATE_PATH + TestData.LM_PDF_FILE), new File(TestData.TMP_FILE + "\\" + TestData.LM_PDF_FILE));

            DateTime currentDateTime = new DateTime();
            Date yesterday = currentDateTime.minusDays(3).toDate();
            String batchdate = DateUtils.doFormat(TestData.DATE_FORMAT_XML, yesterday);
            DaoFunctionality.getNascaDao().updateAffectationComptable(batchdate);
        }

        dateDebutTest = new Date();
        LOGGER.info("Début du test : " + dateDebutTest);
    }

    private void loginAndRetry() {
        try {
            loginNasca();
        } catch (TimeoutException e) {
            LOGGER.error(e.getMessage());
            SeleniumUtils.waitForActionLong();
            loginNasca();
        }
    }

    private static void driverCreation() throws IOException {
        DaoFunctionality doaini = new DaoFunctionality();
        doaini.setNascaDao((NascaDao) ApplicationContext.getAppCtx().getBean(DaoFunctionality.BEAN_NAME_FOR_NASCA_DAO));
        setBatch((RunBatch) ApplicationContext.getAppCtx().getBean(RunBatch.BEAN_NAME_BATCH));

        LogManager.getRootLogger().setLevel(Level.INFO);

        TestData testData = (TestData) ApplicationContext.getAppCtx().getBean("testData");
        String propsRootUrl = testData.getPropsRootUrl();

        TestBase.getBatch();
        LOGGER.info("Environement Name : " + RunBatch.getEnvi());
        setMachineName(InetAddress.getLocalHost().getHostName());
        LOGGER.info("Machine Name : " + TestBase.getMachineName());

        if (!isDriverAlive()) {
            try {
                proxy = ProxySelector.getDefault();
                setProxy(proxy);
                driver = new FirefoxDriver(makeFirefoxProfile());

                String webUrl = doSetCurrentUrl(testData, propsRootUrl);

                driver.get(getCurrentUrl());

                driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
                driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
                driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

                setDriver(driver);

                jsExec = (JavascriptExecutor) driver;
                setJsExec(jsExec);
                loginNascaTryCatch(webUrl);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                initTestError(e);
            }
        }
    }

    private static void loginNascaTryCatch(String webUrl) {
        try {
            loginNasca();
        } catch (TimeoutException e) {
            LOGGER.error(e.getMessage());
            LOGGER.info("Restart Tomcat suite Timeout au login");

            RunBatch.restartTomcat();

            setCurrentUrl(webUrl);

            driver.get(getCurrentUrl());

            driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            setDriver(driver);

            jsExec = (JavascriptExecutor) driver;
            setJsExec(jsExec);

            loginNasca();
        }
    }

    private static String doSetCurrentUrl(TestData testData, String propsRootUrl) {
        driver.manage().window().setPosition(new Point(0, 0));

        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1280, 1024));

        String port = testData.getPropsRootPort();

        port = setPort(port);
        String webUrl = "http://" + propsRootUrl + port + TestData.BASE_URL_END;
        setCurrentUrl(webUrl);

        return webUrl;
    }

    private static void initTestError(Exception e) {
        String name = Thread.currentThread().getStackTrace()[1].getMethodName();

        extent = ReportManager.getInstance();
        report = extent.startTest("Start Test: " + name);
        report.log(LogStatus.ERROR, "Impossible de demarrer l'initialisation du test: " + e.toString());
        Assert.fail("Test Failed:", e);
        extent.endTest(report);
    }

    private static boolean isDriverAlive() {
        try {
            getDriver().getCurrentUrl();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected static void sendSoap(String rootSoapProj, String inastiAnswer, String uniqueId) {
        try {
            Status statussoap = SoapUi.sendSoapRequest(rootSoapProj, inastiAnswer + uniqueId);
            if (statussoap != TestRunner.Status.FINISHED) {
                getReport().log(LogStatus.FAIL, getReport().addScreenCapture(SeleniumUtils.createScreenshot(TestBase.getDriver())));
            } else {
                logSuccess("Soap request sended", "Soap request");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            getReport().log(LogStatus.FAIL, e.toString() + getReport().addScreenCapture(SeleniumUtils.createScreenshot(TestBase.getDriver())));
            Assert.fail("Test Failed:", e);
        }
    }

    protected static void loginNasca() {
        TestBase.getDriver().get(getCurrentUrl());

        SeleniumUtils.waitForActionLong();

        if ("Merci de vous authentifier".equals(driver.getTitle())) {
            loginPage.clickLogin(TestData.NASCA_USER, TestData.NASCA_MDP);
            waitUntilPageIsLoaded("Préférences");
            SeleniumUtils.isLoading();
        }
        if ("Préférences".equals(driver.getTitle())) {
            loginPage.doSelectTeam(TestData.ACCOMPAGNEMENT);
            waitUntilPageIsLoaded("Administration");
            SeleniumUtils.isLoading();
        }
    }

    protected static void setReponsable(String user) {
        homeMenuPage.clickAdministration();
        administrationPage.clickMenuGroupeOrganisation();
        administrationPage.editEquipeAccompagnement();
        administrationPage.setResponsableEquipe(user);
        administrationPage.clickBtnEnregistrerEquipe();
        homeMenuPage.deconnexion();
    }

    private static void waitUntilPageIsLoaded(String title) {
        try {
            $(byText(title)).waitUntil(Condition.exist, 30000);
        } catch (Exception e) {
            logError("La page n'a pas été chargé correctement", e);
        }
    }

    @AfterTest(alwaysRun = true)
    public void tearDown() throws Exception {
        if (!EnumTypeTest.ENROLEMENT.equals(getTypeTest())) {
            DateTime datetim0 = new DateTime();
            Date date0 = datetim0.toDate();
            String batchdate0 = DateUtils.doFormat(TestData.DATE_FORMAT_XML, date0);
            if (isIntMachine() && !(batchdate0.contains(RunBatch.checkDateMachine()))) {
                RunBatch.runBatchChangeDate(batchdate0);
            }
            if (EnumTypeTest.TRF_PM.equals(getTypeTest()) || EnumTypeTest.AFFI_PM.equals(getTypeTest())) {
                BatchUtils.editScriptJS("/opt/tomcat/webapps/Nasca-Web/scripts/nasca/affiliation/pm/phase1/", "affiliation.create.demande.js", TestData.DATE_PAST_FALSE, TestData.DATE_PAST_TRUE);
                RunBatch.restartTomcat();
                loginNasca();
            }

            finishTestAndParseReport();
        }
    }

    private void finishTestAndParseReport() throws UnknownHostException {
        try {
            if (TestBase.screenRecorder.getState() != State.DONE) {
                finishTestExecution();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        if (isVDIMachine()) {
            ParserReport.parserAndWriteFile(dateDebutTest, new Date());
        }

        if (!getTypeTest().toString().contains("SELENIDE") && driver != null) {
            driver.close();
            driver.quit();
        }
    }

    protected void tearDownEnrolement() throws IOException, SftpException {
        DateTime datetim0 = new DateTime();
        Date date0 = datetim0.toDate();
        String batchdate0 = DateUtils.doFormat(TestData.DATE_FORMAT_XML, date0);

        BatchUtils.editScriptJS("/opt/tomcat/webapps/Nasca-Web/scripts/nasca/revenus/reguls/edit/", "ajoutModifRevenus.js", TestData.DATE_PAST_FALSE, TestData.DATE_PAST_TRUE);
        if (isIntMachine() && !(batchdate0.contains(RunBatch.checkDateMachine()))) {
            RunBatch.runBatchChangeDate(batchdate0);
        }

        finishTestAndParseReport();
    }

    public static void startRecording() throws Exception {
        File movie = new File(TestData.MOVIE_LOCATION);
        if (!movie.exists() && movie.mkdir()) {
            LOGGER.info("Répertoire créé " + movie.getPath());
        }

        uuid = UUID.randomUUID();

        setUuid(uuid);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        Rectangle captureSize = new Rectangle(0, 0, width, height);

        GraphicsConfiguration gc = GraphicsEnvironment
                .getLocalGraphicsEnvironment().getDefaultScreenDevice()
                .getDefaultConfiguration();

        TestBase.screenRecorder = new SeleniumScreenRecorder(
                gc,
                captureSize,
                new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey,
                        ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        CompressorNameKey,
                        ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24,
                        FrameRateKey, Rational.valueOf(15), QualityKey, 1.0f,
                        KeyFrameIntervalKey, 15 * 60), new Format(MediaTypeKey,
                MediaType.VIDEO, EncodingKey, "black", FrameRateKey,
                Rational.valueOf(30)), null, movie, uuid.toString());
        TestBase.screenRecorder.start();

        setScreenRecorder(screenRecorder);
    }

    private static void stopRecording() throws IOException {
        String movieurl = "movies/" + getUuid() + ".avi";

        Path path = FileSystems.getDefault().getPath(TestData.MOVIE_LOCATION, getUuid() + ".avi");
        if (TestBase.getScreenRecorder() != null) {
            TestBase.screenRecorder.stop();
        }

        String status = getReport().getRunStatus().toString();
        if (status.contains(LogStatus.PASS.toString()) || status.contains(LogStatus.SKIP.toString())) {
            Files.deleteIfExists(path);
        } else {
            getReport().log(LogStatus.INFO, "Failed Video", "<a href='" + movieurl + "'>-Video link of Test Execution-</a>");
        }
    }

    public static RunBatch getBatch() {
        return batch;
    }

    private static void setBatch(RunBatch batch) {
        TestBase.batch = batch;
    }

    private static String getMachineName() {
        return machineName;
    }

    private static void setMachineName(String machineName) {
        TestBase.machineName = machineName;
    }

    public static WebDriver getDriver() {
        return driver;
    }

    private static void setDriver(WebDriver driver) {
        TestBase.driver = driver;
        loginPage = new LoginPage(driver);
        nascaPage = new NascaPage(driver);
        signaletiquePage = new SignaletiquePage(driver);
        affiliationPmWizardPage = new AffiliationPmWizardPage(driver);
        affiliationPpWizardPage = new AffiliationPpWizardPage(driver);
        homeMenuPage = new HomeMenuPage(driver);
        tachePage = new TachePage(driver);
        administrationPage = new AdministrationPage(driver);
        multiDossiersPage = new MultiDossiersPage(driver);
        comptabilitePage = new ComptabilitePage(driver);
        gestionClientPage = new GestionClientPage(driver);
        remWizardPage = new RemWizardPage(driver);
        tableUtils = new TableUtils(driver);
    }

    public static JavascriptExecutor getJsExec() {
        return jsExec;
    }

    private static void setJsExec(JavascriptExecutor jsExec) {
        TestBase.jsExec = jsExec;
    }

    public static ExtentTest getReport() {
        return report;
    }

    private static void setReport(ExtentTest report) {
        TestBase.report = report;
    }

    public static ExtentReports getExtent() {
        return extent;
    }

    private static void setExtent(ExtentReports extent) {
        TestBase.extent = extent;
    }

    private static UUID getUuid() {
        return uuid;
    }

    private static void setUuid(UUID uuid) {
        TestBase.uuid = uuid;
    }

    private static boolean isIntMachine() {
        return TestBase.getBatch().getMachine().contains("testint");
    }

    private static boolean isVDIMachine() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName().contains("DVW");
    }

    private static String getCurrentUrl() {
        return currentUrl;
    }

    private static void setCurrentUrl(String currentUrl) {
        TestBase.currentUrl = currentUrl;
    }

    private static ScreenRecorder getScreenRecorder() {
        return screenRecorder;
    }

    private static void setScreenRecorder(ScreenRecorder screenRecorder) {
        TestBase.screenRecorder = screenRecorder;
    }

    public static ProxySelector getProxy() {
        return proxy;
    }

    private static void setProxy(ProxySelector proxy) {
        TestBase.proxy = proxy;
    }

    protected static void logFailed(String stepName) {
        logFailed(stepName, "");
    }

    public static void logFailed(String stepName, String message) {
        logAndAssert(stepName, LogStatus.FAIL, message);
    }

    protected static void logSuccess(String stepName) {
        logSuccess(stepName, " ");
    }

    protected static void logSuccess(String stepName, String message) {
        logWithCapture(LogStatus.PASS, stepName, message);
    }

    protected static void logInfo(String stepName, String message) {
        logWithCapture(LogStatus.INFO, stepName, message);
    }

    protected static void logSkip(String stepName, String message) {
        logWithCapture(LogStatus.SKIP, stepName, message);
    }

    public static void logError(String stepName, Exception e) {
        nascaPage.checkStacktrace(stepName, e);
    }

    protected static void logError(String stepName, Throwable e) {
        nascaPage.checkStacktrace(stepName, e);
    }

    public static void logAndAssert(String stepName, LogStatus logStatus, String message) {
        logWithCapture(logStatus, stepName, message);

        softassert.assertFalse(true, "Test Failed " + message);
        try {
            finishTestExecution();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        Assert.fail(stepName);
    }

    private static void logWithCapture(LogStatus logStatus, String stepName, String message) {
        getReport().log(
                logStatus,
                stepName,
                message
                        + getReport().addScreenCapture(
                        SeleniumUtils.createScreenshot(driver)));
    }

    protected static void loadNissOrBce(String nissorbce) {
        homeMenuPage.clickOngletGestionClient();
        if (nissorbce.length() >= 11) {
            gestionClientPage.clickBtnSelectPersonnePhysique();
            gestionClientPage.fillNiss(nissorbce);
        } else if (nissorbce.length() == 9) {
            gestionClientPage.fillNumeroClient(nissorbce);
        } else {
            gestionClientPage.clickBtnSelectPersonneMoral();
            gestionClientPage.fillBce(nissorbce);
        }
        gestionClientPage.clickBtnSearch();
        SeleniumUtils.isLoading();
        logInfo("Resume dossier pour " + nissorbce, "");

    }

    protected static void initTest(String name, String message) {
        extent = ReportManager.getInstance();
        report = extent.startTest(name);
        LOGGER.info("Test:" + name);
        TestBase.getBatch();
        report.assignCategory(RunBatch.getEnvi() + " - " + message);
        TestBase.setReport(report);
        SeleniumUtils.createHtmlforJenkins();
    }

    protected static void finishTestExecution() {
        try {
            if (screenRecorder.getState() != State.DONE) {
                stopRecording();
            }
            if (getReport() != null) {
                getExtent().endTest(getReport());
            }
            if (getExtent() != null) {
                getExtent().flush();
                setExtent(null);
                setReport(null);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        SeleniumUtils.createHtmlforJenkins();
    }

    public static void doReturnToHomeScreen() {
        nascaPage.clickBtnAnnulerEtOuiWizard();
        logError("NISS error INASTI - ERROR:pas de resultat ", null);
    }

    protected static void clickEnter() throws AWTException {
        SeleniumUtils.waitForActionCommon();
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        SeleniumUtils.waitForActionCommon();
    }

    private static String setPort(String port) {
        if (port == null || StringUtils.isEmpty(port)) {
            return "";
        } else {
            return ":" + port;
        }
    }

    private static FirefoxProfile makeFirefoxProfile() {
        System.setProperty("webdriver.firefox.profile", "default");
        FirefoxProfile myprofile = new FirefoxProfile();
        myprofile.setPreference("network.proxy.type", 0);
        myprofile.setPreference("print.always_print_silent", true);
        return myprofile;
    }

    protected Iterator<Object[]> getDataProvider(String excelFileName, String ongletName, String title, String typeTest, Integer scenario) {
        ExcelFileUtils excelFile = new ExcelFileUtils();
        if (scenario != null) {
            return SeleniumTestNGDataHelper.toObjectArrayIterator(excelFile.setData(excelFileName, ongletName, title, typeTest, scenario));
        } else {
            return SeleniumTestNGDataHelper.toObjectArrayIterator(excelFile.setData(excelFileName, ongletName, title, typeTest));
        }
    }
}