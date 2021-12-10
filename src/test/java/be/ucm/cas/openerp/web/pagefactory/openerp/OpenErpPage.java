package be.ucm.cas.openerp.web.pagefactory.openerp;

import be.ucm.cas.nasca.web.test.support.SeleniumUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import be.ucm.cas.openerp.web.test.support.CodaFileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Collection;
import java.util.List;

public class OpenErpPage {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    private final WebDriver driver;

    @FindBy(xpath = "//input[@name='login']")
    private static WebElement username;

    @FindBy(xpath = "//input[@name='password']")
    private static WebElement password;

    @FindBy(xpath = "//button[@name='submit']")
    private static WebElement btnlogin;

    @FindBy(xpath = "//a[@data-menu='135']")
    private WebElement menucomptabilite;

    @FindBy(xpath = "//a[@data-menu='264']")
    private WebElement menuimportCODAfile;

    @FindBy(xpath = "//a[@data-menu='177']")
    private WebElement menurelevebancaires;

    @FindBy(xpath = "//table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/form/input[3]")
    private WebElement btnselectfiletoimport;

    @FindBy(xpath = "//table/tbody/tr[2]/td[2]/span/div/input")
    private WebElement compterecette;

    @FindBy(xpath = "//table/tbody/tr[3]/td[2]/span/div/input")
    private WebElement comptedepense;

    @FindBy(xpath = "//div/footer/button[1]")
    private WebElement btnimporter;

    @FindBy(xpath = "//table/tbody/tr[2]/td/button")
    private WebElement btnclosetaskmodal;

    @FindBy(xpath = "//table[@class='oe_list_content']")
    private WebElement tableresultCODAimport;

    @FindBy(xpath = "//div/header/button[2]")
    private WebElement btnconfirmer;

    public OpenErpPage(WebDriver drv) {
        this.driver = drv;
        PageFactory.initElements(driver, this);
    }

    private void clickBtnConfirmer() {
        btnconfirmer.click();
        SeleniumUtils.isLoading();
    }

    public void clickBtnComptabilite() {
        menucomptabilite.click();
        SeleniumUtils.isLoading();
    }

    public void doLogin(String user, String pass) {
        fillUsername(user);
        fillPassword(pass);
        clickBtnLogin();
        SeleniumUtils.isLoading();
    }

    private void fillUsername(String user) {
        SeleniumUtils.waitForActionLong();
        username.clear();
        username.click();
        username.sendKeys(user);
        SeleniumUtils.isLoading();
    }

    private void fillPassword(String pass) {
        password.clear();
        password.click();
        password.sendKeys(pass);
        SeleniumUtils.isLoading();
    }

    private void clickBtnLogin() {
        btnlogin.submit();
        SeleniumUtils.isLoading();
    }

    public void clickBtnImportFile(String nissorbce, String domitypeCODAConstant) {
        SeleniumUtils.waitForActionCommon();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        Collection<File> path = CodaFileUtils.getPathofCodaFile(nissorbce, domitypeCODAConstant);

        if (path != null) {
            for (File tempfile : path) {
                SeleniumUtils.isLoading();
                menuimportCODAfile.click();
                SeleniumUtils.isLoading();
                js.executeScript("document.getElementsByTagName('INPUT')[14].setAttribute('type','display');");
                btnselectfiletoimport.click();
                StringSelection ss = new StringSelection(tempfile.toString());
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

                Robot robot;
                try {
                    robot = new Robot();
                    robot.keyPress(KeyEvent.VK_CONTROL);
                    robot.keyPress(KeyEvent.VK_V);
                    robot.keyRelease(KeyEvent.VK_V);
                    robot.keyRelease(KeyEvent.VK_CONTROL);
                    SeleniumUtils.waitForActionCommon();
                    robot.keyPress(KeyEvent.VK_ENTER);
                    robot.keyRelease(KeyEvent.VK_ENTER);
                } catch (AWTException e) {
                    LOGGER.error(e.getMessage());
                }
                SeleniumUtils.waitForActionLong();
                compterecette.sendKeys("481910000");
                SeleniumUtils.isLoading();
                comptedepense.sendKeys("442631000");
                btnimporter.click();
                SeleniumUtils.waitForActionLong();
                btnclosetaskmodal.click();
            }
        }
    }

    public void clickResultConfirmCODAimport(String nissorbce, String testDataCODAtypeContstante) {
        boolean breaked = false;
        Collection<File> path = CodaFileUtils.getPathofCodaFile(nissorbce, testDataCODAtypeContstante);
        if (path != null) {
            for (File tempfile : path) {
                SeleniumUtils.isLoading();
                clickBtnComptabilite();
                SeleniumUtils.isLoading();
                menurelevebancaires.click();
                SeleniumUtils.waitForActionLong();

                WebElement table = tableresultCODAimport;

                String tableresultCODAimportId = "//table[@class='oe_list_content']";
                List<WebElement> totalRowCount = table.findElements(By
                        .xpath(tableresultCODAimportId + "/tbody/tr"));

                int rowIndex = 1;
                for (WebElement rowElement : totalRowCount) {
                    if (breaked) {
                        break;
                    }
                    List<WebElement> totalColumnCount = rowElement.findElements(By
                            .xpath("td"));
                    int columnIndex = 1;
                    for (WebElement colElement : totalColumnCount) {
                        String filename = colElement.getText();

                        if (filename.contains(tempfile.getName())) {
                            WebElement etat = driver.findElement(By.xpath(tableresultCODAimportId + "/tbody/tr[" + rowIndex + "]/td[7]"));
                            String etatstr = etat.getText();

                            if (etatstr.contains(TestData.NOUVEAU)) {
                                SeleniumUtils.isLoading();
                            }

                            colElement.click();

                            SeleniumUtils.waitForActionLong();
                            clickBtnConfirmer();
                            SeleniumUtils.isLoading();
                            menurelevebancaires.click();
                            SeleniumUtils.waitForActionLong();
                            path.remove(tempfile);
                            breaked = true;
                            break;

                        }
                        columnIndex = columnIndex + 1;
                    }
                    rowIndex = rowIndex + 1;
                }
                SeleniumUtils.waitForActionLong();
            }
        }
    }
}