package be.ucm.cas.nasca.web.pagefactory.affiliation;

import be.ucm.cas.nasca.web.test.support.ActionUtils;
import be.ucm.cas.nasca.web.test.support.SeleniumUtils;
import be.ucm.cas.nasca.web.test.support.TestBase;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AffiliationPmWizardPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    /**
     * Phase 1 - Traitement de la demande
     */

    @FindBy(xpath = ("//form/ul[@class='errorMessage']/li/span"))
    private static WebElement errorMessage;

    @FindBy(id = "searchBtnId")
    private static WebElement btnSearch;

    @FindBy(id = "presenceFormDemiId")
    private static WebElement checkPresenceFormulaireDemission;

    /**
     * Phase 2-Decision Inasti screen
     */

    @FindBy(id = "searchNaceId")
    private static WebElement btnOpenSearchNace;

    @FindBy(id = "searchNaceBtn")
    private static WebElement btnSearchNace;

    @FindBy(id = "table-resRech-nace")
    private static WebElement tableResultNace;

    @FindBy(xpath = ("//div[3]/div/button[2]"))
    private static WebElement btnEnregistrer;

    @FindBy(xpath = ("//div[3]/div/button[2]"))
    private static WebElement btnEnregistrer2;

    @FindBy(id = "updateAffiliationForm1_model_annulationCotisation")
    private static WebElement btnRadioReclamerAttestAssujetTVA;

    @FindBy(id = "updateAffiliationForm1_model_demandeExo")
    private static WebElement btnRadioDemandExo;

    /**
     * Phase 2- Apporteur d'affaires screen
     */

    @FindBy(id = "nomId")
    private static WebElement nom;

    @FindBy(id = "searchBtn")
    private static WebElement btnSearchApporteur;

    @FindBy(id = "table-apporteur")
    private static WebElement tableResultApporteurAffaires;

    /**
     * Creation Reclemation Elements Manquant/selection lettre
     */
    @FindBy(id = "remButton")
    private static WebElement btnREM;

    @FindBy(xpath = ("//li[@id='AFFIPM_DEM_LETTRE']//span[contains(text(),'Lettre de réclamation')]"))
    private static WebElement btnAffiPMLettreReclamation;

    /**
     * Phase 2- Correspondace screen
     */
    @FindBy(id = "selectTypeSiege")
    private static WebElement btnRadioSiegeSocial;

    /**
     * Phase 2- Mandataires
     */
    @FindBy(id = "signaActiviteAddId")
    private static WebElement btnAddIdentiteLiee;

    @FindBy(id = "_actTypeId")
    private static WebElement selectTypeActivite;

    @FindBy(id = "_actFnctId")
    private static WebElement selectFonctionActivite;

    @FindBy(xpath = ("//a[@id='buttonSelect_ilFilSexeId']"))
    private static WebElement btnSelectSex;

    @FindBy(xpath = ("//a[contains(text(),'Masculin')]"))
    private static WebElement sexMasculin;

    @FindBy(id = "btnSearch")
    private static WebElement btnSearchIdentiteLiee;

    @FindBy(id = "btnCreate")
    private static WebElement btnCreation;

    @FindBy(xpath = "//table[@class='table selectable rechercheIdentiteLiee dataTable no-footer']/tbody/tr/td[1]")
    private static WebElement tableResultIdentiteLiee;

    /**
     * Creation Reclemation Elements Manquant/destinataires & elements
     */
    @FindBy(id = "editEltsManquantsId")
    private static WebElement btnModifierElementManquant;

    @FindBy(xpath = "//td/input[@id='annexeLocale0']")
    private static WebElement btnRadioImpressionLocal;

    /**
     * Creation Reclemation Elements Manquant/Calendrier & visualisation
     */

    @FindBy(id = "previsualizationId")
    private static WebElement btnPreviewPdf;

    /**
     * Creation Reclemation Elements Manquant/Document preview
     */
    @FindBy(xpath = "//div[3]/div/button")
    private static WebElement btnClosePreviewPdf;

    @FindBy(id = "radioRetourPrinc")
    private static WebElement btnRadioRetourProcessusPrincipal;

    public AffiliationPmWizardPage(WebDriver drv) {
        PageFactory.initElements(drv, this);
    }

    public void clickBtnRetourProcessusPrincipal() {
        ActionUtils.clickAndLoading(btnRadioRetourProcessusPrincipal);
    }

    public void clickBtnReclamationElementManquant() {
        ActionUtils.clickAndLoading(btnREM);
    }

    public void selectTypeActivite() {
        ActionUtils.sendInCombo(selectTypeActivite, "Mandataire de société");
    }

    public void selectFonctionActivite(String fonction) {
        ActionUtils.sendInComboWithoutLoading(selectFonctionActivite, fonction);
    }

    public void doFillDatedebutActiviteAuto() {
        ActionUtils.doJsFill("actDteDebId", new SimpleDateFormat(TestData.DATE_FORMAT_SIMPLE).format(new Date()));
    }

    public void clickBtnAddMandataire() {
        ActionUtils.clickAndLoading(btnAddIdentiteLiee);
    }

    public void doFillIdentiteLiee() {
        ActionUtils.doJsFill("ilFilNomId", SeleniumUtils.generateRandomString());
        ActionUtils.doJsFill("ilFilPrenomId", SeleniumUtils.generateRandomString());
        btnSelectSex.click();
        sexMasculin.click();
        ActionUtils.clickAndLoading(btnSearchIdentiteLiee);
        ActionUtils.clickAndLoading(btnCreation);
    }

    public void doFillIdentiteLiee(String niss) {
        ActionUtils.doJsFill("ilFilNissId", niss);
        ActionUtils.clickAndLoading(btnSearchIdentiteLiee);
    }

    public void doSelectSiegeSocial() {
        btnRadioSiegeSocial.click();
    }

    public void clickBtnPreviewpdf() {
        ActionUtils.clickAndLoading(btnPreviewPdf);
    }

    public void clickBtnClosePreviewPdf() {
        ActionUtils.clickAndLoading(btnClosePreviewPdf);
    }

    public void clickResultSearchIdentiteLiee() {
        ActionUtils.clickAndLoading(tableResultIdentiteLiee);
    }

    public void clickBtnSelectImpressionLocal() {
        btnRadioImpressionLocal.click();
    }

    public void clickBtnModifElementsManquants() {
        ActionUtils.clickAndLoading(btnModifierElementManquant);
    }

    public void clickBtnLettreReclamation() {
        btnAffiPMLettreReclamation.click();
    }

    public void doFillNom(String nomApporteur) {
        ActionUtils.sendInTextField(nom, nomApporteur);
    }

    public void doFillDateReceptionAuto() {
        ActionUtils.doJsFill("dateReception", new SimpleDateFormat(TestData.DATE_FORMAT_SIMPLE).format(new Date()));
    }

    public void doFillDateReceptionCustom(String date) {
        ActionUtils.doJsFill("dateReception", date);
    }

    public void doFillDateSignatureAuto() {
        ActionUtils.doJsFill("creationAffiliationForm1_model_dateSignature", new SimpleDateFormat(TestData.DATE_FORMAT_SIMPLE).format(new Date()));
    }

    public void doFillDateSignatureCustom(String date) {
        ActionUtils.doJsFill("creationAffiliationForm1_model_dateSignature", date);
    }

    public void doFilLDateSignatureExo(String date) {
        ActionUtils.doJsFill("dateSignature", date);
    }

    public void clickBtnsearchApporteur() {
        ActionUtils.clickAndLoading(btnSearchApporteur);
    }

    private void clickBtnSearchBCE() {
        ActionUtils.clickAndLoading(btnSearch);
    }

    public void clickBtnOpenSearchNACE() {
        ActionUtils.clickAndLoading(btnOpenSearchNace);
    }

    public void doFillcodeNACE(String code) {
        ActionUtils.doJsFill("ilFilCodeId", Integer.toString(Integer.parseInt(code)));
    }

    public void clickBtnsearchNACE() {
        ActionUtils.clickAndLoading(btnSearchNace);
    }

    public void clickBtnEnregistrer() {
        try {
            ActionUtils.clickAndLoading(btnEnregistrer);
        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
            ActionUtils.clickAndLoading(btnEnregistrer2);
        }
    }

    public void fillDateDepot(String date) {
        ActionUtils.doJsFill("dateDepotId", date);
    }

    public void fillDateConstitution(String date) {
        ActionUtils.doJsFill("updateAffiliationForm1_model_dateConstitution", date);
    }

    public void clickRadioDemandeExo() {
        btnRadioDemandExo.click();
    }

    public void clickBtnReclamerAttestAssujetiTVA() {
        try {
            btnRadioReclamerAttestAssujetTVA.click();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void doSearchBCE(String bce) {
        ActionUtils.doJsFill("searchBce", bce);
        clickBtnSearchBCE();
        SeleniumUtils.waitForActionCommon();

        try {
            if (errorMessage.isDisplayed()) {
                forceSearchBce(bce);
            }
        } catch (Exception e) {

        }
    }

    private void forceSearchBce(String bce) {
        ActionUtils.doJsFill("searchBce", bce);
        ActionUtils.clickAndLoading(btnSearch);
        try {
            String error = errorMessage.getText();
            LOGGER.error(error);
            TestBase.logError("Search BCE - Inasti Timetout", null);
        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void doOpenAffiliationPM() {
        ActionUtils.executeJS("TacheAffiliationPm.demarrerProcessus();");
    }

    public void clickSearchResultNace(String contenuresult) {
        ActionUtils.iterateToTable(tableResultNace, "table-resRech-nace", contenuresult);
    }

    public void clickSearchResultApporteurAffaires(String contenuresult) {
        ActionUtils.iterateToTable(tableResultApporteurAffaires, "table-apporteur", contenuresult);
    }

    public void checkPresenceFormulaireDemission() {
        checkPresenceFormulaireDemission.click();
    }
}