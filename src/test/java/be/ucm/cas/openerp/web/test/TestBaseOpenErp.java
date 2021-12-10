package be.ucm.cas.openerp.web.test;

import be.ucm.cas.nasca.web.test.support.ApplicationContext;
import be.ucm.cas.nasca.web.test.support.ReportManager;
import be.ucm.cas.nasca.web.test.support.SeleniumUtils;
import be.ucm.cas.openerp.web.pagefactory.openerp.OpenErpPage;
import be.ucm.cas.openerp.web.test.support.OpenErpUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class TestBaseOpenErp {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    private static WebDriver driver;
    private static OpenErpUtils erputils;

    protected static WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver openerpdriver) {
        TestBaseOpenErp.driver = openerpdriver;
    }

    @BeforeTest
    public static void initializeTest() {
        setOepnErpUtils((OpenErpUtils) ApplicationContext.getAppCtx().getBean(OpenErpUtils.BEAN_NAME));

        try {
            setFirefoxProfile();

            driver.manage().window().setPosition(new Point(0, 0));
            driver.manage().window().maximize();
            driver.get(erputils.getWebUrl());
            doLoginErp();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            ExtentReports extent = ReportManager.getInstance();
            ExtentTest report = extent.startTest("Start Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            report.log(LogStatus.FAIL, "Impossible de demarrer l'initialisation du test: " + e);
            Assert.fail("Test Failed:", e);
            extent.endTest(report);
        }
    }

    private static void setFirefoxProfile() {
        ProfilesIni profileInitialization = new ProfilesIni();
        FirefoxProfile myprofile;
        try {
            myprofile = profileInitialization.getProfile("NascaTest");
            myprofile.setPreference("print.always_print_silent", true);
            SeleniumUtils.waitForActionLong();
            driver = new FirefoxDriver(myprofile);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            myprofile = new FirefoxProfile();
            myprofile.setPreference("print.always_print_silent", true);
            SeleniumUtils.waitForActionLong();
            driver = new FirefoxDriver(myprofile);
        }
    }

    private static void doLoginErp() {
        try {
            OpenErpPage login = new OpenErpPage(driver);
            login.doLogin(erputils.getUser(), erputils.getPwd());

        } catch (Exception e) {
            Assert.fail("Test Failed:", e);
        }
    }

    @AfterTest
    public static void tearDown() {
        driver.close();
        driver.quit();
    }

    public OpenErpUtils getOpenErp() {
        return erputils;
    }

    private static void setOepnErpUtils(OpenErpUtils erputils) {
        TestBaseOpenErp.erputils = erputils;
    }
}