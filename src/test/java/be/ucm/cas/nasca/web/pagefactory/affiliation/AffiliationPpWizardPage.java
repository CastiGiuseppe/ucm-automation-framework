package be.ucm.cas.nasca.web.pagefactory.affiliation;

import be.ucm.cas.nasca.web.test.support.ActionUtils;
import be.ucm.cas.nasca.web.test.support.SeleniumUtils;
import be.ucm.cas.nasca.web.test.support.TestBase;
import be.ucm.cas.nasca.web.test.support.TestData;
import com.codeborne.selenide.Condition;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.codeborne.selenide.Selenide.$;

public class AffiliationPpWizardPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    private final WebDriver driver;

    /**
     * Identification
     */
    @FindBy(id = "searchNiss")
    private static WebElement searchniss;

    @FindBy(id = "searchBtnId")
    private static WebElement btnsearch;

    @FindBy(xpath = ("//form/ul[@class='errorMessage']/li/span"))
    private static WebElement errormessageniss;

    @FindBy(id = "searchBtn")
    private static WebElement btnsearchaffiliation;

    @FindBy(xpath = ".//*[@id='creationAffiliationForm1']/div[1]/div/fieldset/p")
    private static WebElement personneAffilieDejaAffilie;

    @FindBy(id = "dejaOui")
    private static WebElement checkContinuerAffiliation;

    /**
     * Transfert Wizard
     */
    @FindBy(id = "signaActiviteAddId")
    private static WebElement btnaddActivity;

    @FindBy(xpath = ("//div[@id='wizardTransfertInId']/div[2]/div[2]/button[2]"))
    private static WebElement btnNextWizardTransfert;

    /**
     * Activite et assujetissement
     */
    @FindBy(id = "_actTypeId")
    private static WebElement selectTypeActivity;

    @FindBy(id = "_actFnctId")
    private static WebElement selectFonctionActivity;

    @FindBy(id = "ilFilDenomId")
    private static WebElement denominationActivite;

    @FindBy(xpath = ("//table[@id='DataTables_Table_1']/tbody/tr/td[3]"))
    private static WebElement searchcellresultsociete;

    /**
     * Activite Form Creation identite
     */

    @FindBy(id = "ilFilNomId")
    private static WebElement nomidentiteliee;

    @FindBy(id = "ilFilPrenomId")
    private static WebElement prenomidentiteliee;

    @FindBy(xpath = ("//a[@id='buttonSelect_ilFilSexeId']"))
    private static WebElement btnselectsex;

    @FindBy(xpath = ("//a[contains(text(),'Masculin')]"))
    private static WebElement sexmasculin;

    // table-act-ent-prof
    @FindBy(id = "btnSearch")
    private static WebElement btnsearchidentiteliee;

    @FindBy(id = "btnCreate")
    private static WebElement btncreation;

    @FindBy(xpath = ("//div[13]/div[3]/div/button[2]"))
    private static WebElement btnenregistrerprofessionidentiteliee;

    @FindBy(xpath = ("//div[12]/div[3]/div/button[2]"))
    private static WebElement btnenregistrerprofessionidentiteliee2;

    @FindBy(id = "table-act-ent-prof-ADDid")
    private static WebElement ajoutDetailAtivity;

    @FindBy(xpath = ("//div[11]/div[3]/div/button[2]"))
    private WebElement btnsaveajoutactivity;

    @FindBy(xpath = ("//div[10]/div[3]/div/button[2]"))
    private WebElement btnsaveajoutactivity2;

    @FindBy(id = ("_actProfProfessionToEdit"))
    private static WebElement selectProfession;

    @FindBy(id = ("_actNaceProfessionId"))
    private static WebElement selectNaceProfession;

    @FindBy(xpath = ("//div[13]/div[3]/div/button[2]"))
    private static WebElement btnenregistrerproffession;

    @FindBy(xpath = ("//div[12]/div[3]/div/button[2]"))
    private static WebElement btnenregistrerproffession2;

    @FindBy(id = "_natureCotisante")
    private static WebElement selectNatureCotisante;

    @FindBy(id = "_qualite")
    private static WebElement selectQualite;

    /**
     * Donnees INASTI conjoint
     */
    @FindBy(id = "_creationAffiliationForm3_model_situationConjoint")
    private static WebElement selectSituationConjoint;

    /**
     * Donnees INASTI conjoint
     */

    @FindBy(id = "radioSuiteTache")
    private static WebElement checkSuiteTache;

    /**
     * Consultation Inasti
     */
    @FindBy(id = "soumissionOui")
    private static WebElement btnSoumissionInasti;

    @FindBy(id = "envoiOui")
    private static WebElement btnyescourrier;

    /**
     * Reponse INASTI
     */
    @FindBy(xpath = ("//form[@id='decisionAffiliationForm1']/div/fieldset/font"))
    private static WebElement inastireponse;

    @FindBy(xpath = ("//form[@id='decisionAffiliationForm1']/div[2]/fieldset/div[2]/input[2]"))
    private static WebElement btncontinueaffiliation;

    /**
     * Apporteur d'affaires
     */
    @FindBy(id = "nomId")
    private static WebElement nomsearch;

    @FindBy(id = "_redacteurId")
    private static WebElement nomRedacteur;

    @FindBy(xpath = ("//table[@id='table-apporteur']/tbody/tr/td[3]"))
    private static WebElement searchcellresultapporteur;

    /**
     * Gestion du profil
     */
    @FindBy(id = "boutonCreationEvenement")
    private static WebElement btnAjoutEventProfil;

    @FindBy(id = "_typeEvenementID")
    private static WebElement typeEventProfil;

    @FindBy(id = "_natureCotisantesId")
    private static WebElement txtFieldNatureProfil;

    @FindBy(id = "_profilExoReductionId")
    private static WebElement exoReduction;

    @FindBy(id = "_profilJustificationId")
    private static WebElement profilJustification;

    @FindBy(xpath = "//button[contains(.,'Enregistrer')]")
    private static WebElement btnSauvegarderChgtProfil;

    @FindBy(xpath = "//a[@class='profiledit']")
    private static WebElement btnEditProfil;

    @FindBy(id = "profilDateValiditeId")
    private static WebElement dateChangementProfil;

    /**
     * Revenu Simple & compte bancaires
     */
    @FindBy(id = "revPresume")
    private static WebElement btnrevenupresume;

    @FindBy(id = "gestionRevenu")
    private static WebElement btnGestionRevenu;

    /**
     * Gestion Revenu
     */
    @FindBy(xpath = "//*[@id='accordionTableRevenu']/h3/a")
    private static WebElement accordeonRevenu;

    @FindBy(xpath = "//a[@class='revenuEdit']")
    private static WebElement btnModifRevenu;

    @FindBy(id = "montant")
    private static WebElement revenu;

    @FindBy(id = "montantRevenu")
    private static WebElement montantRevenu;

    @FindBy(id = "ajouterRevenuId")
    private static WebElement btnAjouterRevenu;

    @FindBy(id = "_annee")
    private static WebElement anneeRevenu;

    @FindBy(id = "_type")
    private static WebElement typeRevenu;

    @FindBy(id = "_statutFull")
    private static WebElement statutRevenu;

    @FindBy(id = "_sourceFull")
    private static WebElement sourceRevenu;

    @FindBy(id = "radioPaye")
    private static WebElement radioPaye;

    @FindBy(id = "radioDeclare")
    private static WebElement radioDeclare;

    /**
     * Donnees familiales
     */

    @FindBy(xpath = ("//a[@id='buttonSelect_encodageAffiliationForm4_model_situationCohabitant']/span[2]"))
    private static WebElement btncohabitantsituation;

    @FindBy(xpath = ("//a[contains(text(),'Informations non connues')]"))
    private static WebElement btncohabitantsituationinfo;

    @FindBy(id = "encodageAffiliationForm4_model_personnePhysique_aed")
    private static WebElement btnaffiliationppconfirmation1;

    /**
     * Taxation
     */
    @FindBy(xpath = ("//div[9]/form/div[3]/div[1]/button"))
    private static WebElement btnSimulerCalcul;

    @FindBy(xpath = "//*[@id='taxationsTable']/tbody/tr/td")
    private static WebElement tabTaxation;

    @FindBy(xpath = ("//table[@id='taxationsTable']/tbody/tr/td[9]"))
    private static WebElement searchcellresulttaxation;

    @FindBy(xpath = ("//fieldset/div[2]/input[2]"))
    private static WebElement btnsuitetaches;

    public AffiliationPpWizardPage(WebDriver drv) {
        this.driver = drv;
        PageFactory.initElements(driver, this);
    }

    public void dofillNom(String nom) {
        ActionUtils.sendInTextField(nomsearch, nom);
    }

    public void dofillRedacteur(String nom) {
        ActionUtils.sendInComboWithoutLoading(nomRedacteur, nom);
    }

    public void clickSelectFonction(String fonction) {
        ActionUtils.sendInCombo(selectFonctionActivity, fonction);
    }

    public void doFillDenomination(String activite) {
        denominationActivite.sendKeys(activite);
        denominationActivite.sendKeys(Keys.ENTER);
        SeleniumUtils.isLoading();
    }

    public void clickResultSociete() {
        ActionUtils.clickAndLoading(searchcellresultsociete);
    }

    public static boolean getResponseAccept() {
        return AffiliationPpWizardPage.inastireponse.getText().contains("ACCEPT");
    }

    public static boolean getResponseRefuse() {
        return AffiliationPpWizardPage.inastireponse.getText().contains("REFUS");
    }

    public static boolean getResponseSuspend() {
        return AffiliationPpWizardPage.inastireponse.getText().contains("ENQU");
    }

    public void doFillDateDebutActiviteCustom(String date) {
        ActionUtils.doJsFill("actDteDebId", date);
    }

    public void doFillDateDebutProfessionAuto() {
        ActionUtils.doJsFill("actProfDateDebutToEdit", new SimpleDateFormat(TestData.DATE_FORMAT_SIMPLE).format(new Date()));
    }

    public void doFillChoicefamilialPP() {
        try {
            btncohabitantsituation.click();
            btncohabitantsituationinfo.click();
        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
            try {
                btnaffiliationppconfirmation1.click();
            } catch (NoSuchElementException e2) {
                LOGGER.error(e2.getMessage());
            }
        }
    }

    public void doFillDateDebutProfessionCustom(String dateCustom) {
        ActionUtils.doJsFill("actProfDateDebutToEdit", dateCustom);
    }

    public void doFillDateDebutRepriseAuto() {
        ActionUtils.doJsFill("dateDebRep", new SimpleDateFormat(TestData.DATE_FORMAT_SIMPLE).format(new Date()));
    }

    public void doFillDateDebutRepriseCustom(String dateCustom) {
        ActionUtils.doJsFill("dateDebRep", dateCustom);
    }

    public void clickBtnAjoutChgtProfil() {
        ActionUtils.clickAndLoading(btnAjoutEventProfil);
    }

    public void doFillDateEventProfilCustom(String dateCustom) {
        ActionUtils.doJsFill("profilDateValiditeId", dateCustom);
        ActionUtils.refreshElementJS(dateChangementProfil);
    }

    public void doAddChangementProfil(String typeChangement) {
        ActionUtils.sendInComboWithoutLoading(typeEventProfil, typeChangement);
    }

    public void doFillNatureProfil(String nature) {
        ActionUtils.sendInCombo(txtFieldNatureProfil, nature);
    }

    public void chooseExoRed(String choix) {
        ActionUtils.sendInCombo(exoReduction, choix);
    }

    public void doFillJustification(String choix) {
        ActionUtils.sendInCombo(profilJustification, choix);
    }

    public void clickBtnSauvergardeChgtprofil() {
        ActionUtils.clickAndLoading(btnSauvegarderChgtProfil);
    }

    public void clickBtnEditProfil() {
        ActionUtils.clickAndLoading(btnEditProfil);
    }

    public void selectProfession(String content) {
        ActionUtils.sendInComboWithoutLoading(selectProfession, content);
    }

    public void selectNaceProfession(String content) {
        ActionUtils.sendInComboWithoutLoading(selectNaceProfession, content);
    }

    private void clickBtnsearchniss() {
        ActionUtils.clickAndLoading(btnsearch);
    }

    public void doSearchNISS(String niss) {
        ActionUtils.sendInTextField(searchniss, niss);
        clickBtnsearchniss();
        forceSearchNiss(niss);
    }

    public void checkPersonneDejaAffilie() {
        if (personneAffilieDejaAffilie.getText().equals("Cette personne est actuellement affiliée auprès de la CAS de l’UCM")) {
            checkContinuerAffiliation.click();
        }
    }

    private void forceSearchNiss(String niss) {
        SeleniumUtils.isLoading();
        try {
            String error = errormessageniss.getText();
            LOGGER.error(error);
        } catch (NoSuchElementException e) {
            return;
        }

        ActionUtils.sendInTextField(driver.findElement(By.id("searchNiss")), niss);
        btnsearch.click();
        SeleniumUtils.waitForAction(15500);

        try {
            SeleniumUtils.waitForAction(8000);
            String error = errormessageniss.getText();
            LOGGER.error("Probleme recherche/ INASTI TIMEOUT");
            TestBase.getReport().log(LogStatus.ERROR, "INASTI TIMEOUT", error);
            TestBase.doReturnToHomeScreen();
        } catch (NoSuchElementException e) {

        }
    }

    public void clickBtnRevenuPresume() {
        btnrevenupresume.click();
    }

    public void clickBtnGestionRevenu() {
        btnGestionRevenu.click();
    }

    public void clickBtnModifRevenu() {
        ActionUtils.clickAndLoading(btnModifRevenu);
    }

    public void clickBtnAjouterRevenu() {
        ActionUtils.clickAndLoading(btnAjouterRevenu);
    }

    public void doFillRevenu(String montant) {
        revenu.clear();
        if (montant != null) {
            revenu.sendKeys(montant);
        }
        revenu.sendKeys(Keys.ENTER);
    }

    public void doFillMontantRevenu(String montant) {
        ActionUtils.sendInTextField(montantRevenu, montant);
    }

    public void doFillDateCommRevenu() {
        ActionUtils.doJsFill("dateCommunication", new SimpleDateFormat(TestData.DATE_FORMAT_SIMPLE).format(new Date()));
    }

    public void doFillDateDistributionRevenu() {
        ActionUtils.doJsFill("dateDistribution", new SimpleDateFormat(TestData.DATE_FORMAT_SIMPLE).format(new Date()));
    }

    public void doFillAnnee(String annee) {
        ActionUtils.sendInComboWithoutLoading(anneeRevenu, annee);
    }

    public void doFillType(String type) {
        ActionUtils.sendInCombo(typeRevenu, type);
    }

    public void clickRadioOrigine(String origine) {
        if ("payé".equals(origine)) {
            radioPaye.click();
        } else {
            radioDeclare.click();
        }
    }

    public void doFillStatut(String statut) {
        ActionUtils.sendInComboWithoutLoading(statutRevenu, statut);
    }

    public void doFillSource(String source) {
        ActionUtils.sendInComboWithoutLoading(sourceRevenu, source);
    }

    public void clickSearchResult() {
        searchcellresultapporteur.click();
    }

    public void clickBtnSearch() {
        ActionUtils.clickAndLoading(btnsearchaffiliation);
    }

    public void clickBtnContinueAffiliation() {
        ActionUtils.clickAndLoading(btncontinueaffiliation);
    }

    public void selectNatureCotisante(String nature) {
        ActionUtils.sendInCombo(selectNatureCotisante, nature);
    }

    public void selectQualite(String qualite) {
        ActionUtils.sendInComboWithoutLoading(selectQualite, qualite);
    }

    public void clickBtnSaveProfesssion() {
        try {
            ActionUtils.clickAndLoading(btnenregistrerproffession);
        } catch (NoSuchElementException e) {
            ActionUtils.clickAndLoading(btnenregistrerproffession2);
        }
    }

    public void clickBtnSaveProfessionIdentiteLiee() {
        try {
            ActionUtils.clickAndLoading(btnenregistrerprofessionidentiteliee);
        } catch (NoSuchElementException e) {
            ActionUtils.clickAndLoading(btnenregistrerprofessionidentiteliee2);
        }
    }

    public void selectTypeActivite(String activity) {
        try {
            ActionUtils.sendInCombo(selectTypeActivity, activity);
        } catch (NoSuchElementException e) {

        }
    }

    public void clickBtnAddActivity() {
        ActionUtils.clickAndLoading(btnaddActivity);
    }

    public void clickBtnSaveAddActivity() {
        try {
            ActionUtils.clickAndLoading(btnsaveajoutactivity);
        } catch (NoSuchElementException e) {
            ActionUtils.clickAndLoading(btnsaveajoutactivity2);
        }
    }

    public void doOpenAffiliationPP() {
        ActionUtils.executeJS("TacheAffiliationPp.demarrerProcessus();");
    }

    public void doAddDetailActivity() {
        ActionUtils.clickAndLoading(ajoutDetailAtivity);
    }

    public void doFillDateSignatureAuto() {
        ActionUtils.doJsFill("model_dateSignature", new SimpleDateFormat(TestData.DATE_FORMAT_SIMPLE).format(new Date()));
    }

    public void doFillDateSignatureCustom(String date) {
        ActionUtils.doJsFill("model_dateSignature", date);
    }

    public void doFillDateCessationAuto() {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MONTH, 6);
        Date date = new Date(now.getTimeInMillis());

        ActionUtils.doJsFill("dateCessation", new SimpleDateFormat(TestData.DATE_FORMAT_SIMPLE).format(date));
    }

    public void clickBtnNextWizardTransfert() {
        ActionUtils.clickAndLoading(btnNextWizardTransfert);
    }

    public void selectSituationConjoint() {
        try {
            ActionUtils.sendInComboWithoutLoading(selectSituationConjoint, "Informations non connues");
        } catch (Exception e) {

        }
    }

    public void checkSuiteTache() {
        try {
            checkSuiteTache.click();
        } catch (NoSuchElementException e) {

        }
    }

    public void clickBtnSoumissionInasti() {
        ActionUtils.clickAndLoading(btnSoumissionInasti);
    }

    public void clickBtnOuiCourrierEnvoi() {
        btnyescourrier.click();
    }

    public void clickBtnSimulationCalcul() {
        ActionUtils.clickAndLoading(btnSimulerCalcul);
    }

    private boolean getResultSimulationCalcul(String montant) {
        return searchcellresulttaxation.getText().contains(montant);
    }

    public boolean isAnyTaxation() {
        return !(TestData.AUCUN_ELEMENT_A_AFFICHER.equals(tabTaxation.getText()) || getResultSimulationCalcul("0,00"));
    }

    public void clickBtnToTaches() {
        btnsuitetaches.click();
    }

    public void doFillIdentiteLiee() {
        ActionUtils.sendInTextField(nomidentiteliee, SeleniumUtils.generateRandomString());
        ActionUtils.sendInTextField(prenomidentiteliee, SeleniumUtils.generateRandomString());
        btnselectsex.click();
        sexmasculin.click();
        ActionUtils.clickAndLoading(btnsearchidentiteliee);
        ActionUtils.clickAndLoading(btncreation);
    }

    public boolean checkIfGestionRevenus() {
        try {
            return accordeonRevenu.getText().contains("Revenus");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSoapFallr() {
        return $("#fallr").exists();
    }

    public void clickBtnOkFallr() {
        $("#fallr-button-button1")
                .should(Condition.exist)
                .hover()
                .click();
    }
}