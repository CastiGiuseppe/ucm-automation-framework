package be.ucm.cas.nasca.web.pagefactory.rem;

import be.ucm.cas.nasca.web.test.support.ActionUtils;
import be.ucm.cas.nasca.web.test.support.TableUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemWizardPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    /**
     * REM Gestion client
     */
    @FindBy(xpath = "//button[contains(., 'Enregistrer')]")
    private static WebElement btnEnregistrerREM;

    /**
     * Grid control--REM
     */
    @FindBy(id = "remButton")
    private static WebElement btnREM;

    /**
     * Cessation REM
     */
    @FindBy(xpath = ("//li[@id='CESS_COTIS_LETTRE']//span[contains(text(),'Lettre de réclamation')]"))
    private static WebElement selectLettreREMCessation;

    /**
     * Non-Assujettissement REM
     */
    @FindBy(xpath = ("//li[@id='NA']//span[contains(text(),'Lettre de réclamation')]"))
    private static WebElement selectLettreREMNonAssujettissement;

    /**
     * Mandat Gratuit REM
     */
    @FindBy(xpath = ("//li[@id='MND_GRT']//span[contains(text(),'Lettre de réclamation')]"))
    private static WebElement selectLettreREMMandatGratuit;

    /**
     * Assimilation Maladie REM
     */
    @FindBy(xpath = ("//li[@id='ASSIM']//span[contains(text(),'Lettre de réclamation')]"))
    private static WebElement selectLettreREMAssMaladie;

    /**
     * Assimilation Maladie REM
     */
    @FindBy(xpath = ("//li[@id='ASS_CON']//span[contains(text(),'Lettre de réclamation')]"))
    private static WebElement selectLettreREMAssContinuee;

    /**
     * Affi PP
     */
    @FindBy(xpath = ("//li[@id='PAFFPP_AFFIL_LETTRE']//span[contains(text(), 'Lettre de réclamation')]"))
    private static WebElement btnAfiPPlettreReclamation;

    @FindBy(xpath = ("//li[@id='AFFIPP_CLIENT_LETTRE']//span[contains(text(), 'Lettre de réclamation')]"))
    private static WebElement btnAfiPPlettreReclamationPost;

    /**
     * Creation Reclemation Elements Manquant/destinataires & elements
     */
    @FindBy(id = "editEltsManquantsId")
    private static WebElement btnModifierElementManquant;

    @FindBy(xpath = "//td/input[@id='annexeLocale0']")
    private static WebElement btnRadioImpressionlocal;

    /**
     * Creation Reclemation Elements Manquant/Document preview
     */
    @FindBy(id = "radioRetourPrinc")
    private static WebElement btnRadioRetourProcessusPrincipal;

    @FindBy(id = "radioRetourAccueil")
    private static WebElement btnRadioRetourAccueil;

    public RemWizardPage(WebDriver drv) {
        PageFactory.initElements(drv, this);
    }

    public void clickBtnmodifElementsManquants() {
        ActionUtils.clickAndLoading(btnModifierElementManquant);
    }

    public void clickBtnLettreReclamationPP() {
        try {
            ActionUtils.clickAndLoading(btnAfiPPlettreReclamation);
        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
            ActionUtils.clickAndLoading(btnAfiPPlettreReclamationPost);
        }
    }

    public void clickBtnREM() {
        ActionUtils.clickAndLoading(btnREM);
    }

    public void clickLettreREMCessation() {
        selectLettreREMCessation.click();
    }

    public void clickLettreREMNonAssujettissement() {
        selectLettreREMNonAssujettissement.click();
    }

    public void clickLettreREMMandatGratuit() {
        selectLettreREMMandatGratuit.click();
    }

    public void clickLettreREMAssMaladie() {
        selectLettreREMAssMaladie.click();
    }

    public void clickLettreREMAssContinuee() {
        selectLettreREMAssContinuee.click();
    }

    public void clickBtnSelectImpressionLocal() {
        btnRadioImpressionlocal.click();
    }

    public void clickBtnRetourProcessusPrincipal() {
        btnRadioRetourProcessusPrincipal.click();
    }

    public void clickBtnRetourAccueil() {
        btnRadioRetourAccueil.click();
    }

    public void clickREMSelectElementManquant(String contenu) {
        TableUtils.clickEditElementPlus("table-types-elements", contenu);
    }

    public void clickBtnenregistrerWizard() {
        ActionUtils.clickAndLoading(btnEnregistrerREM);
    }
}