package be.ucm.cas.nasca.web.pagefactory.nasca;

import be.ucm.cas.nasca.web.test.support.ActionUtils;
import be.ucm.cas.nasca.web.test.support.SeleniumUtils;
import be.ucm.cas.nasca.web.test.support.TestBase;
import be.ucm.cas.nasca.web.test.support.TestData;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Date;

public class NascaPage {

    private final WebDriver driver;

    @FindBy(id = "loadingId")
    private static WebElement loading;

    @FindBy(id = "stackTracedivId")
    private static WebElement errorstacktracemessage;

    @FindBy(xpath = "//ul[@class='errorMessage']/li")
    private static WebElement errorMessageText;

    @FindBy(xpath = "//*[@id='errorsId']/div/ul/li")
    private static WebElement errorMessageWizard;

    @FindBy(xpath = "//div[2]/form/ul/li")
    private static WebElement errorMessageRevenuWizard;

    @FindBy(xpath = "//*[@id='wizardRevenusRegulsStep2']/div[4]/div")
    private static WebElement errorMessageRevenuCalculWizard;

    @FindBy(className = "jGrowl-close")
    private static WebElement btnFermerNotification;

    @FindBy(id = "fallr-button-button1")
    private static WebElement confirmerAlert;

    /**
     * Boutons wizard
     */
    @FindBy(xpath = "//button[@class='buttonNext']")
    private static WebElement btnSuivantWizard;

    @FindBy(xpath = "//button[@class='buttonFinish']")
    private static WebElement btnTerminerWizard;

    @FindBy(xpath = "//button[@class='buttonCancel']")
    private static WebElement btnAnnulerWizard;

    @FindBy(xpath = ("//button[@class='buttonLeave']"))
    private static WebElement btnQuitterWizard;

    @FindBy(id = "fallr-button-button1")
    private static WebElement btnOuiModal;

    public NascaPage(WebDriver drv) {
        this.driver = drv;
        PageFactory.initElements(driver, this);
    }

    public void isLoading() {
        Date dateDebut = new Date();

        for (; ; ) {
            Date dateBoucle = new Date();

            long diff = dateBoucle.getTime() - dateDebut.getTime();
            if (checkLoadingIsDisplayed(diff)) {
                break;
            }
        }
    }

    private boolean checkLoadingIsDisplayed(long diff) {
        if (diff < 300000) {
            try {
                if (!loading.isDisplayed()) {
                    return true;
                }
            } catch (NoSuchElementException e) {
                return true;
            }
            SeleniumUtils.waitForAction(500);
        } else {
            TestBase.logFailed("Loading NASCA trop long > 300 secondes", null);
        }
        return false;
    }

    /**
     * Vérifie la stacktrace affichée
     * Le paramètre Object doit impérativement être de type Exception ou Throwable
     *
     * @param message   String
     * @param exception Object
     */
    public void checkStacktrace(String message, Object exception) {
        if (isMessageErreur("stacktrace")) {
            if (errorstacktracemessage.getText().contains("Internal Server Error")) {
                TestBase.logFailed("Internal Server Error", null);
            } else {
                if (errorstacktracemessage.getText().contains("Service Temporarily Unavailable")) {
                    TestBase.logFailed("Service Temporarily Unavailable", null);
                } else {
                    TestBase.logFailed("Erreur NASCA " + errorstacktracemessage.getText(), null);
                }
            }
        } else {
            if (isMessageErreur(TestData.MESSAGE)) {
                TestBase.logFailed("Erreur NASCA " + getErrorMessageText(), null);
            } else {
                TestBase.logAndAssert(message, LogStatus.ERROR, exception == null ? "" : exception.toString());
            }
        }
    }

    public void doWait() {
        SeleniumUtils.doAlertAccept();
        isLoading();
    }

    public boolean isMessageErreur(String type) {
        WebElement element = null;
        switch (type) {
            case "stacktrace":
                element = errorstacktracemessage;
                break;
            case TestData.MESSAGE:
                element = errorMessageText;
                break;
            case "messageWizard":
                element = errorMessageWizard;
                break;
            case "messageRevenuWizard":
                element = errorMessageRevenuWizard;
                break;

            default:
                break;
        }
        try {
            if (element.isDisplayed()) {
                return true;
            }
        } catch (NoSuchElementException e) {
            return false;
        }
        return false;
    }

    public boolean isRevenuPasPrisEnCompte() {
        try {
            if (errorMessageRevenuCalculWizard.getText().contains("Les revenus pris en compte pour le calcul n’ont pas d’effet sur le compte client.")) {
                return true;
            }
        } catch (NoSuchElementException e) {
            return false;
        }
        return false;
    }

    private String getErrorMessageText() {
        return errorMessageText.getText();
    }

    public void fermerNotification() {
        try {
            SeleniumUtils.waitForActionCommon();
            btnFermerNotification.click();
            SeleniumUtils.waitForActionLong();
        } catch (NoSuchElementException e) {
        }
    }

    public void confirmerAlert() {
        SeleniumUtils.waitForActionCommon();
        driver.switchTo();
        ActionUtils.clickAndLoading(confirmerAlert);
    }

    public void clickBtnOuiModal() {
        ActionUtils.clickAndLoading(btnOuiModal);
    }

    public void clickBtnSuivantWizard() {
        ActionUtils.clickAndLoading(btnSuivantWizard);
    }

    public void clickBtnAnnulerEtOuiWizard() {
        btnAnnulerWizard.click();
        clickBtnOuiModal();
    }

    public void clickBtnTerminerWizard() {
        ActionUtils.clickAndLoading(btnTerminerWizard);
    }

    public void clickBtnTerminerWizardWithWaitActionLong() {
        ActionUtils.clickAndWaitActionLong(btnTerminerWizard);
    }

    public void clickBtnQuitterWizard() {
        ActionUtils.clickAndLoading(btnQuitterWizard);
    }
}