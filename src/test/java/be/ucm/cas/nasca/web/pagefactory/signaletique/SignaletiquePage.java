package be.ucm.cas.nasca.web.pagefactory.signaletique;

import be.ucm.cas.nasca.web.test.support.*;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.mustache.Value;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SignaletiquePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    private static final String TABLE_ACTIVITES = "table-activites";

    private final WebDriver driver;

    /**
     * Menu Gestion client
     */
    @FindBy(id = "COTDiv")
    private static WebElement menuCotisant;

    @FindBy(xpath = "//div[1]/div/ul/li")
    private static WebElement menuActions;

    @FindBy(xpath = "//div[4]/div/ul/li[1]/a")
    private static WebElement menuCreationPP;

    @FindBy(xpath = "//div[4]/div/ul/li[2]/a")
    private static WebElement menuCreationPM;

    /**
     * Menu signaletique
     */
    @FindBy(id = "SIGDiv")
    private static WebElement menuSignaletique;

    @FindBy(id = "lvl_1")
    private static WebElement menuActionSuspension;

    @FindBy(id = "lvl_10")
    private static WebElement menuCreationSuspension;

    @FindBy(id = "SIG_IDEDiv")
    private static WebElement menuDetailsIdentite;

    @FindBy(id = "SIG_ADRDiv")
    private static WebElement menuAdresse;

    @FindBy(id = "SIG_BANDiv")
    private static WebElement menuDonneesBancaire;

    @FindBy(id = "SIG_CONDiv")
    private static WebElement menuContact;

    @FindBy(id = "SIG_LPPDiv")
    private static WebElement menuLiensPP;

    @FindBy(id = "SIG_ACPDiv")
    private static WebElement menuActivites;

    /**
     * Details Identite
     */
    @FindBy(id = "btnEditCaractPP")
    private static WebElement btnModifierCaracteristiques;

    @FindBy(xpath = "//*[@id='accordionPP']/h3[1]/span")
    private static WebElement accordeonCaracteristiques;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerCaracteristiques;

    @FindBy(id = "_paysId")
    private static WebElement paysNaissance;

    @FindBy(id = "_editCaractForm_identitePP_codeNationalite")
    private static WebElement selectNationalite;


    //TELEPHONE
    @FindBy(id = "table-coordonnees")
    private static WebElement tableCoordoneesTel;

    @FindBy(id = "btnAddCooTel")
    private static WebElement btnAjouterTel;

    @FindBy(id = "prefCooId2")
    private static WebElement btnPrefere;

    @FindBy(id = "_codeMediaId2")
    private static WebElement typeMedia;

    @FindBy(id = "btnDelCooId")
    private static WebElement btnSupprimerTelephone;

    @FindBy(xpath = "//*[@id='table-coordonnees']/tbody/tr[1]/td[1]")
    private static WebElement tableTelephoneEmpty;

    //Autres coordonnées mail/site
    @FindBy(id = "btnAddCooAut")
    private static WebElement btnAjouterAutresCoordonees;

    @FindBy(id = "infoCooId2")
    private static WebElement mailAdresse;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerEmail;

    @FindBy(id = "btnDelCoo2Id")
    private static WebElement btnSupprimerEmail;

    @FindBy(id = "table-coordonnees-autre")
    private static WebElement tableCoordoneesAutres;

    @FindBy(xpath = "//*[@id='table-coordonnees-autre']/tbody/tr[1]/td[1]")
    private static WebElement tableEmailEmpty;

    /**
     * Adresse
     */

    @FindBy(id = "btnAddAdr")
    private static WebElement btnAjouterAdresse;

    /**
     * Compte bancaire
     */
    @FindBy(id = "btnAddCpt")
    private static WebElement btnAddCompteBancaire;

    @FindBy(id = "refreshBic")
    private static WebElement imgRefreshBic;

    @FindBy(id = "commentCptId2")
    private static WebElement commentaireCompteBancaire;

    @FindBy(id = "prefCptBanId2")
    private static WebElement btnPrefereCompteBancaire;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerCompteBancaire;

    @FindBy(id = "table-comptes")
    private static WebElement tableComptesBancaires;

    @FindBy(id = "btnDelCptId")
    private static WebElement btnSupprimerCompteBancaire;

    @FindBy(xpath = "//*[@id='table-comptes']/tbody/tr[1]/td[1]")
    private static WebElement tableCompteBancaireEmpty;

    /**
     * Adresse GRID/MODAL
     */

    @FindBy(id = "buttonSelect_typeAdresseId2")
    private static WebElement btnopentypeAdresse;

    @FindBy(xpath = ("//a[contains(text(),'Légale adaptée')]"))
    private static WebElement adresseTypeLegaleAdapte;

    @FindBy(xpath = ("//a[contains(text(),'Siège social adapté')]"))
    private static WebElement adresseTypeSiegeSocialAdapte;

    @FindBy(id = "_paysId")
    private static WebElement paysAdresse;

    @FindBy(id = "_localiteId")
    private static WebElement adresseLocalite;

    @FindBy(id = "_regimeLiguistiqueAdrId2")
    private static WebElement langue;

    @FindBy(id = "rueAdrId2")
    private static WebElement adresseRue;

    @FindBy(id = "numeroAdrId2")
    private static WebElement adresseNumero;

    @FindBy(id = "boiteAdrId2")
    private static WebElement adresseBoite;

    @FindBy(id = "fallr-button-button1")
    private static WebElement btnOuiModal;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerAdresse;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerTelephone;

    /**
     * Activités
     */
    @FindBy(id = "signaActiviteAddId")
    private static WebElement btnAjouterActivite;

    @FindBy(id = "signaActProfAddId")
    private static WebElement btnAjouterDetailActivite;

    @FindBy(id = "signaActiviteDelId")
    private static WebElement btnSupprimerActivite;

    @FindBy(id = "signaActViewAllId")
    private static WebElement btnVoirToutesActivites;

    @FindBy(xpath = "//*[@id='table-activites']/tbody/tr[1]/td[1]")
    private static WebElement tableActiviteEmpty;

    @FindBy(id = "_actTypeId")
    private static WebElement selectTypeActivite;

    @FindBy(id = "actAssujettiId")
    private static WebElement checkAssujetti;

    @FindBy(id = "actPrpId")
    private static WebElement checkPrincExercee;

    @FindBy(id = "actDonValId")
    private static WebElement checkDonneesValides;

    @FindBy(xpath = "//*[@id='table-act-ent-prof']/tbody/tr[1]")
    private static WebElement tableTypeActiviteEmpty;

    @FindBy(id = "table-act-ent-prof-ADDid")
    private static WebElement btnAjouterTypeActivites;

    @FindBy(id = "actComId")
    private static WebElement commentActivite;

    @FindBy(id = "actProfCommentaireToEdit")
    private static WebElement commentTypeActivite;

    @FindBy(id = "ilFilNomId")
    private static WebElement nomIdentiteLiee;

    @FindBy(id = "ilFilPrenomId")
    private static WebElement prenomIdentiteLiee;

    @FindBy(id = "_ilFilSexeId")
    private static WebElement sexeIdentiteLiee;

    @FindBy(id = "ilFilDenomId")
    private static WebElement denominationIdentiteLiee;

    @FindBy(id = "_ilFilFoJuId")
    private static WebElement formeJuridiqueIdentiteLiee;

    @FindBy(id = "btnSearch")
    private static WebElement btnRechercheIdentiteLiee;

    @FindBy(id = "btnCreate")
    private static WebElement btnCreerIdentiteLiee;

    @FindBy(xpath = "//div[4]/div/div/div/table/tbody/tr[1]")
    private static WebElement selectIdentiteLiee;

    @FindBy(xpath = "//*[@id='table-act-ent-prof']/tbody/tr[1]")
    private static WebElement selectTypeActiviteAidant;

    @FindBy(id = "_actProfProfessionToEdit")
    private static WebElement selectProfession;

    @FindBy(id = "_actNaceProfessionId")
    private static WebElement selectNace;

    @FindBy(id = "_actFnctId")
    private static WebElement selectFonction;

    @FindBy(id = "_actAppGestId")
    private static WebElement selectApportGestion;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerActivite;

    @FindBy(xpath = "//div[5]/div[3]/div/button[2]")
    private static WebElement btnEnregistrerDetailActiviteEntreprisePP1;

    @FindBy(xpath = "//div[6]/div[3]/div/button[2]")
    private static WebElement btnEnregistrerDetailActiviteEntreprisePP2;

    @FindBy(xpath = "//div[7]/div[3]/div/button[2]")
    private static WebElement btnEnregistrerDetailActiviteEntreprisePP3;

    @FindBy(xpath = "//div[8]/div[3]/div/button[2]")
    private static WebElement btnEnregistrerDetailActiviteEntreprisePP4;

    @FindBy(xpath = "//div[9]/div[3]/div/button[2]")
    private static WebElement btnEnregistrerDetailActiviteEntreprisePP5;

    /*
     * Création identité
     */
    @FindBy(id = "_creationSignaletiqueForm1_model_civilite")
    private static WebElement civiliteIdentitePP;

    @FindBy(id = "_creationSignaletiqueForm1_model_formeJuridiqueValide_codeFormeJuridique")
    private static WebElement formeJuridiqueIdentitePM;

    @FindBy(id = "nomIdentite")
    private static WebElement nomIdentitePP;

    @FindBy(id = "prenom")
    private static WebElement prenomIdentitePP;

    @FindBy(id = "_creationSignaletiqueForm1_model_codeNationalite")
    private static WebElement nationaliteIdentitePP;

    @FindBy(xpath = "//div[6]/div[3]/div/button[2]")
    private static WebElement btnEnregistrerNationalite;


    @FindBy(id = "_creationSignaletiqueForm1_model_languePref")
    private static WebElement languePrefereeIdentite;

    @FindBy(id = "_creSexeId")
    private static WebElement sexeIdentitePP;

    @FindBy(id = "btnAddIdentifiant")
    private static WebElement btnAddIdentifiantIdentite;

    @FindBy(id = "buttonSelect_typeIdentifiantId2")
    private static WebElement btnSelectTypeIdentifiant;

    @FindBy(xpath = ("//a[contains(text(),'NISS')]"))
    private static WebElement selectTypeIdentifiantNiss;

    @FindBy(xpath = ("//a[contains(text(),'Numéro d’entreprise (BCE)')]"))
    private static WebElement selectTypeIdentifiantBce;

    @FindBy(xpath = "//div[9]/div[3]/div/button[2]")
    private static WebElement btnEnregistrerIdentifiantIdentite;

    @FindBy(id = "btnAddAdresse")
    private static WebElement btnAjoutAdresseIdentifiantPP;

    /**
     * Contacts
     */
    @FindBy(id = "btnAddCtc")
    private static WebElement btnAjouterContact;

    @FindBy(xpath = "//table/tbody/tr/td[9]/a/img")
    private static WebElement btnEditerContact;

    @FindBy(id = "btnDelCtcId")
    private static WebElement btnSupprimerContacts;

    @FindBy(xpath = "//*[@id='table-contacts']/tbody/tr[1]/td[1]")
    private static WebElement tableContactsEmpty;

    @FindBy(id = "_typeContactId")
    private static WebElement selectTypeContact;

    @FindBy(id = "mandateCtcId")
    private static WebElement checkMandate;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerContact;

    /**
     * Cohabitation Légale
     */
    @FindBy(id = "btnAddCo")
    private static WebElement btnAjouterCohabitationLegale;

    @FindBy(id = "btnDelCohId")
    private static WebElement btnSupprimerCohabitationLegale;

    @FindBy(xpath = "//*[@id='table-cohabitation']/tbody/tr[1]/td[1]")
    private static WebElement tableCohabitationLegaleEmpty;

    @FindBy(id = "_finCohabId")
    private static WebElement selectRaisonFinCohabitationLegale;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerCohabitationLegale;

    /**
     * Etat civil
     */
    @FindBy(id = "btnAddEc")
    private static WebElement btnAjouterEtatCivil;

    @FindBy(xpath = "//table/tbody/tr/td[8]/a/img")
    private static WebElement btnEditerEtatCivil;

    @FindBy(id = "btnDelEciId")
    private static WebElement btnSupprimerEtatCivil;

    @FindBy(xpath = "//*[@id='table-etat-civil']/tbody/tr[1]/td[1]")
    private static WebElement tableEtatCivilEmpty;

    @FindBy(id = "commentLienId")
    private static WebElement commentaireEtatCivil;

    @FindBy(id = "_codeEtatCivil")
    private static WebElement selectEtatCivil;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerEtatCivil;

    /**
     * Operation administrative
     */
    @FindBy(id = "ui-id-3")
    private static WebElement btnOpeAdmin;

    @FindBy(id = "filtreSyntheseTout")
    private static WebElement radioFiltreTout;

    @FindBy(id = "searchBtn")
    private static WebElement btnSearch;

    @FindBy(id = "_filtreTypeDemande")
    private static WebElement fieldFiltreDemande;

    @FindBy(id = "RECDiv")
    private static WebElement menuRecouvrement;

    @FindBy(id = "REC_SUSDiv")
    private static WebElement menuSuspensionRecouvrement;

    @FindBy(id = "_createSuspensionManuelle2_model_causeSuspension")
    private static WebElement comboCauseSuspension;

    @FindBy(id = "createSuspensionManuelle2_model_justification")
    private static WebElement textJustificationSuspension;

    @FindBy(id = "dateRevision")
    private static WebElement dateSuspension;

    @FindBy(id = "periodeDe")
    private static WebElement debPeriodeSuspension;

    @FindBy(id = "periodeA")
    private static WebElement finPeriodeSuspension;

    @FindBy(xpath = "//div/fieldset[1]/div[1]/div[2]/input[1]")
    private static WebElement identiteSelectionee;

    @FindBy(xpath = "//div/fieldset[1]/div[1]/div[2]/input[2]")
    private static WebElement toutDebiteur;

    @FindBy(xpath = "//div/table/thead/tr/th[1]/input")
    private static WebElement checkAllSoldesPeriodique;

    @FindBy(xpath = "//tbody/tr[1]/td[1]/input")
    private static WebElement checkFirstSoldesPeriodique;

    @FindBy(xpath = "//div/fieldset[1]/div[2]/div[1]/input")
    private static WebElement aRevoirUtilisateur;

    @FindBy(xpath = "//div/fieldset[1]/div[3]/div/input")
    private static WebElement finAutomatique;

    @FindBy(xpath = "//div/fieldset[2]/div[1]/div[1]/input[1]")
    private static WebElement suspensionFuture;

    @FindBy(xpath = "//div/fieldset[2]/div[1]/div[1]/input[2]")
    private static WebElement nonSuspensionFuture;

    @FindBy(xpath = "//div/fieldset[2]/div[1]/div[3]/input[1]")
    private static WebElement ordinaireEtRegularisation;

    @FindBy(xpath = "//div/fieldset[2]/div[1]/div[3]/input[2]")
    private static WebElement ordinaire;

    @FindBy(xpath = "//div/fieldset[2]/div[1]/div[3]/input[3]")
    private static WebElement regularisation;


    /**
     * faillite
     */
    @FindBy(id = "DECDiv")
    private static WebElement menuDeclarationCreance;

    @FindBy(id = "DEC_FAIDiv")
    private static WebElement menuFaillite;

    @FindBy(id = "lvl_1")
    private static WebElement menuActionsFaillite;

    @FindBy(id = "lvl_11")
    private static WebElement menuItemCreerFaillite;

    @FindBy(name = "menuCompleterFaillite")
    private static WebElement menuItemCompleterFaillite;

    @FindBy(id = "_tribunalSelectId")
    private static WebElement selectTribunalCommerce;

    @FindBy(id = "typeDeclarationSelectedIdAVE")
    private static WebElement selectDeclarationAveu;

    @FindBy(id = "typeDeclarationSelectedIdCIT")
    private static WebElement selectDeclarationCitation;

    @FindBy(id = "typeDeclarationSelectedIdIND")
    private static WebElement selectDeclarationIndéterminée;

    @FindBy(id = "typeFailliteSelectedIdAVEU")
    private static WebElement selectFailliteAveu;

    @FindBy(id = "typeFailliteSelectedIdCITATION")
    private static WebElement selectFailliteCitation;

    @FindBy(id = "typeFailliteSelectedIdINDETERMINE")
    private static WebElement selectFailliteIndéterminée;

    @FindBy(id = "buttonSelect_tribunalSelectId")
    private static WebElement selectFailliteTribunalCommerce;

    @FindBy(id = "_curateurSelectId")
    private static WebElement selectCurateur;

    @FindBy(id ="gererEcFailliteId")
    private static WebElement gestionFaillite;

    @FindBy(id ="cbAllFailliteSoldes")
    private static WebElement selectPeriode;

    @FindBy(id = "cbAllFailliteFrais")
    private static WebElement selectFrais;

    @FindBy(xpath = "html/body/div[9]/div[3]/div/button[2]")
    private static WebElement enregistrerDetail;

    @FindBy(xpath = "html/body/div[10]/div[3]/div/button[2]")
    private static WebElement enregistrerDetailRegule;

    @FindBy(id = "createActionForm3_courrierCommentaireWebVo_commentaire")
    private static WebElement commentaireFaillite;

    @FindBy(id = "checkBoxIdGenererDocument")
    private static WebElement selectGenererCourrier;

    @FindBy(id = "createActionForm3_courrierCommentaireWebVo_docAImprimerWebVos_0__isSelectionne")
    private static WebElement selectDeclarationCessation;

    @FindBy(id = "createActionForm3_courrierCommentaireWebVo_docAImprimerWebVos_1__isSelectionne")
    private static WebElement selectDispense;

    @FindBy(id = "createActionForm3_courrierCommentaireWebVo_docAImprimerWebVos_2__isSelectionne")
    private static WebElement selectAssurance;

    @FindBy(id = "createActionForm3_courrierCommentaireWebVo_documentTextAImprimer")
    private static WebElement contenuCourrier;

    @FindBy(xpath = "//*[@id='failliteHistoriquesTableId']/tbody/tr/td[9]/a[1]")
    private static WebElement btnEditerFaillite;

    @FindBy(xpath = "//*[@id='failliteHistoriquesTableId']/tbody/tr/td[9]/a")
    private static WebElement  btnEditerSuspension;

    @FindBy(id = "updateButton")
    private static WebElement btnModifierFaillite;

    @FindBy(id = "tableFailliteSoldes")
    private static WebElement contenuTablePeriode;

    @FindBy(id = "tableFailliteFrais")
    private static WebElement contenuTableFrais;

    @FindBy(id = "excusabiliteIdEXCUSABLE")
    private static WebElement selectClotureExcusable;

    @FindBy(id = "excusabiliteIdINEXCUSABLE")
    private static WebElement selectClotureInexcusable;

    @FindBy(id = "excusabiliteIdINDETERMINE")
    private static WebElement selectClotureIndeterminee;

    @FindBy(id = "relanceCurateurOui")
    private static WebElement selectRelanceCurateurOui;

    @FindBy(id = "relanceCurateurNon")
    private static WebElement selectRelanceCurateurNon;

    @FindBy(xpath = "html/body/div[8]/div[3]/div/button[2]")
    private static WebElement btnEnregistrerModifFaillite;

    @FindBy(id = "ui-id-10")
    private static WebElement ongletCommentairesFaillite;

    @FindBy(id = "addCommentaireBtn")
    private static WebElement btnAjoutCommentaireFaillite;

    @FindBy(id = "commentaireId")
    private static WebElement zoneCommentaireFaillite;

    @FindBy(id = "_contact")
    private static WebElement selectContactCommentaireFaillite;

    @FindBy(xpath = "html/body/div[8]/div[3]/div/button[2]")
    private static WebElement btnEnregistrerCommentaireFaillite;

    @FindBy(xpath = "//*[@id='rechercheCommentaireTableId']/tbody/tr/td[5]/a")
    private static WebElement btnSuppressionCommentaire;

    @FindBy(id = "lvl_12")
    private static WebElement menuItemTerminerFaillite;

    @FindBy(id = "lvl_13")
    private static WebElement menuItemReactiverFaillite;

    @FindBy(id = "ui-id-870")
    private static WebElement ongletDetailDeclaration;

    @FindBy(id = "ui-id-18")
    private static WebElement ongletDetailDeclarationFaillite;

    @FindBy(xpath =  "//*[@id='tabDetailCreances']/tbody/tr/td[8]/a")
    private static WebElement btnDetailCreance;

    @FindBy(id = "_typeIndicateursFrais")
    private static WebElement selectTypeFrais;

    @FindBy(id = "editButtonFrais")
    private static WebElement btnEditerFrais;

    @FindBy(xpath = "//div[11]/div/button[2]")
    private static WebElement btnEnregistrerDetailCreanceFaillite;

    @FindBy(id = "menu1")
    private static WebElement menuTaches;

    @FindBy(id = "editFiche")
    private static WebElement iconeTache;

    @FindBy(id = "resetBtn")
    private static WebElement btnReinitialiser;

    @FindBy(id = "_formFiltresTaches_filtre_statutTache")
    private static WebElement selectStatutTache;

    @FindBy(id = "_formFiltresTaches_filtre_typeTache")
    private static WebElement selectTypeTache;

    @FindBy(id = "searchTacheBtn")
    private static WebElement btnRechercherTache;

    @FindBy(xpath = "html/body/div[1]/leftcontent/div[3]/div")
    private static WebElement retrecirMenu;

    /*
    * Suspension recouvrement
    * */

    @FindBy(id ="entitesComptablesTable")
    public static WebElement tableEntiteComptable;

    @FindBy(id = "CPTDiv")
    private static WebElement menuComptes;

    @FindBy(id = "CPTSOLDiv")
    private static WebElement menuComptesSoldes;

    @FindBy(id = "ecPeriode")
    private static WebElement tableSoldePeriodique;

    @FindBy(id = "//tbody/tr[$rowIndex]/td[1]/input")
    private static WebElement checkBoxSoldePeriodique;

    @FindBy(xpath = "//div[2]/div/form/div[1]/div[1]/p/select/option")
    private static WebElement comboboxComptesCotisantSoldeCodebit;

    @FindBy(xpath = "//*[@id='wizardCreateSuspensionId']/div[2]/div[1]/button")
    private static WebElement boutonAnnulerSuspension;

    @FindBy(xpath = "//*[@id='fallr-button-button1']")
    private static WebElement boutonConfirmationAnnulerSuspension;

    @FindBy(id = "table-taches")
    public static WebElement tableTache;

    @FindBy(xpath = "//*[@id='wizardCreateSuspensionId']/div[2]/div[3]/button[2]")
    public static WebElement boutonQuitterSuspension;

    @FindBy (id = "tableDemandes")
    public static WebElement tableSuspensionManuelle;

    @FindBy(xpath = "//*[@id='accordion']/div[1]/div[2]/span[2]")
    public static WebElement causeSuspensionObservation;

    @FindBy(xpath = "//*[@id='accordion']/div[1]/div[2]/span[4]")
    public static WebElement justificationSuspensionObservation;

    @FindBy (id = "dsm_lvl_10")
    private static WebElement menuLeverSuspension;

    @FindBy (id = "dsm_lvl_11")
    private static WebElement menuModifierSuspension;

    @FindBy (id = "dsm_lvl_1")
    private static WebElement menuActionLeverSuspension;

    @FindBy (id = "backBtn")
    public static WebElement boutonRetourAnnulerSuspension;

    @FindBy (xpath = "//button[@class='submit-ui-button ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only']")
    public static WebElement boutonEnregistrerAnnulerRecette;


    @FindBy (id = "operationsComptablesTable")
    public static WebElement tableOperationComptable;

    @FindBy (id = "backButton")
    public static WebElement boutonRetourOpération;

    @FindBy (id = "resetBtn")
    public static WebElement boutonReinitialiserTaches;

    @FindBy (id="editSuspensionManuelle_model_justification")
    public static WebElement textJustificationModifier;


    public SignaletiquePage(WebDriver drv) {
        this.driver = drv;
        PageFactory.initElements(driver, this);
    }

    public void clickCreationIdentitePP() {
        ActionUtils.moveToAndClickChild(menuActions, menuCreationPP, driver);
    }

    public void clickCreationIdentitePM() {
        ActionUtils.moveToAndClickChild(menuActions, menuCreationPM, driver);
    }

    public void clickBtnPrefere() {
        if (btnPrefere.isSelected()) {
            return;
        }
        btnPrefere.click();
    }

    public void clickOuvrirAccordeonFCaracteristiques() {
        accordeonCaracteristiques.click();
    }

    public void clickBtnModifierCaracteristiques() {
        ActionUtils.clickAndLoading(btnModifierCaracteristiques);
    }

    public void fillDateDeces(String dateDeces) {
        if (dateDeces != null && !StringUtils.isEmpty(dateDeces.trim())) {
            ActionUtils.doJsFill("dateDeces", dateDeces);
        } else {
            ActionUtils.doJsFill("dateDeces", DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()));
        }
    }

    public void selectPaysNaissance(String pays) {
        ActionUtils.sendInComboWithoutLoading(paysNaissance, pays);
    }


    public void clickBtnEnregistrerCaracteristiques() {
        ActionUtils.clickAndLoading(btnEnregistrerCaracteristiques);
    }

    public void clickBtnSelectTypeAdresseLegal() {
        btnopentypeAdresse.click();
        SeleniumUtils.waitForActionCommon();
        adresseTypeLegaleAdapte.click();
    }

    public void clickBtnSelectTypeAdresseLegalPM() {
        btnopentypeAdresse.click();
        SeleniumUtils.waitForActionCommon();
        adresseTypeSiegeSocialAdapte.click();
    }

    public void selectPays(String pays) {
        ActionUtils.sendInCombo(paysAdresse, pays);
    }

    public void clickLocaliteBelge(String localite) {
        ActionUtils.sendInCombo(adresseLocalite, localite);
    }

    public void clickLocaliteEtrangere(String localite) {
        ActionUtils.sendInTextField(adresseLocalite, localite);
    }

    public void clickLangue(String langueTestData) {
        ActionUtils.sendInComboWithoutLoading(langue, langueTestData);
    }

    public void fillAdresse(String rue, String numero, String boite) {
        ActionUtils.sendInTextField(adresseRue, rue);
        ActionUtils.sendInTextField(adresseNumero, numero);
        ActionUtils.sendInTextField(adresseBoite, boite);
    }

    public void clickBtnEnregistrerAdresse() {
        ActionUtils.clickAndLoading(btnEnregistrerAdresse);
    }

    public void clickBtnEnregistrerTel() {
        ActionUtils.clickAndLoading(btnEnregistrerTelephone);
    }

    public void clickBtnEnregistrerEmail() {
        ActionUtils.clickAndLoading(btnEnregistrerEmail);
    }

    public void fillEmail(String email) {
        if (typeMedia.isEnabled()) {
            ActionUtils.sendInComboWithoutLoading(typeMedia, TestData.MEDIA_EMAIL);
        }

        mailAdresse.clear();
        mailAdresse.sendKeys(email);
    }

    public void fillNumeroTel(String tel) {
        if (typeMedia.isEnabled()) {
            ActionUtils.sendInComboWithoutLoading(typeMedia, TestData.MEDIA_TELEPHONE);
        }

        ActionUtils.doJsFill("infoCooId2", tel);
    }

    public void clickBtnAddAdress() {
        ActionUtils.clickAndLoading(btnAjouterAdresse);
    }

    public void clickMenuDetailsIdentite() {
        ActionUtils.clickChildMenu(menuSignaletique, menuDetailsIdentite, driver);
    }

    public void clickMenuDonneesBancaires() {
        ActionUtils.clickChildMenu(menuSignaletique, menuDonneesBancaire, driver);
    }

    public void clickMenuAdresse() {
        ActionUtils.clickChildMenu(menuSignaletique, menuAdresse, driver);
    }

    public void clickMenuContact() {
        ActionUtils.clickChildMenu(menuSignaletique, menuContact, driver);
    }

    public void clickMenuActivites() {
        ActionUtils.clickChildMenu(menuSignaletique, menuActivites, driver);
    }

    public void clickMenuLiensPP() {
        ActionUtils.clickChildMenu(menuSignaletique, menuLiensPP, driver);
    }

    public void clickOuiModalAdresse() {
        ActionUtils.clickAndLoading(btnOuiModal);
    }

    public void checkEtSuppressionDonnees(String type) {
        String tableId = null;
        WebElement btnSupprimer = null;

        switch (type) {
            case TestData.MEDIA_TELEPHONE:
                tableId = "table-coordonnees";
                btnSupprimer = btnSupprimerTelephone;
                break;
            case TestData.MEDIA_EMAIL:
                tableId = "table-coordonnees-autre";
                btnSupprimer = btnSupprimerEmail;
                break;
            case "Compte bancaire":
                tableId = "table-comptes";
                btnSupprimer = btnSupprimerCompteBancaire;
                break;
            case "Contact":
                tableId = "table-contacts";
                btnSupprimer = btnSupprimerContacts;
                break;
            case "Activite":
                tableId = TABLE_ACTIVITES;
                btnSupprimer = btnSupprimerActivite;
                break;
            case "Cobabitation légale":
                tableId = "table-cohabitation";
                btnSupprimer = btnSupprimerCohabitationLegale;
                break;
            case "Etat civil":
                tableId = "table-etat-civil";
                btnSupprimer = btnSupprimerEtatCivil;
                break;
            default:
                break;
        }

        TableUtils.selectElement(tableId);

        if (btnSupprimer.isEnabled()) {
            btnSupprimer.click();
            SeleniumUtils.clickConfirmAlert();
        }
    }

    public boolean checkTableTelephoneEmpty() {
        return TestData.AUCUN_ELEMENT_A_AFFICHER.equals(tableTelephoneEmpty.getText());
    }

    public void clickTableBtnEditMedia(String typeMedia) {
        WebElement table = null;
        String tableId = null;
        String indice = null;
        WebElement btnAjout = null;

        switch (typeMedia) {
            case TestData.MEDIA_TELEPHONE:
                table = tableCoordoneesTel;
                tableId = "table-coordonnees";
                indice = "7";
                btnAjout = btnAjouterTel;
                break;
            case TestData.MEDIA_EMAIL:
                table = tableCoordoneesAutres;
                tableId = "table-coordonnees-autre";
                indice = "8";
                btnAjout = btnAjouterAutresCoordonees;
                break;
            default:
                break;
        }

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + tableId + "']/tbody/tr"));

        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            if (iterateMediaColumnTable(typeMedia, indice, rowIndex, totalColumnCount, columnIndex)) {
                return;
            }
            rowIndex = rowIndex + 1;
        }

        ActionUtils.clickAndLoading(btnAjout);
    }

    private boolean iterateMediaColumnTable(String typeMedia, String indice, int rowIndex, List<WebElement> totalColumnCount, int columnIndex) {
        for (WebElement colElement : totalColumnCount) {
            String colElemwthspace = SeleniumUtils.deleteFormat(colElement.getText());
            if (colElemwthspace.contains(typeMedia))
                for (WebElement clickable : totalColumnCount) {
                    String pathimg = "//table/tbody/tr[" + rowIndex + "]/td[" + indice + "]/a";

                    clickable = driver.findElement(By.xpath(pathimg));

                    if (clickable.isDisplayed()) {
                        clickable.click();
                        return true;
                    }
                    columnIndex = columnIndex + 1;

                }
            columnIndex = columnIndex + 1;
        }
        return false;
    }

    public boolean checkTableEmailEmpty() {
        return TestData.AUCUN_ELEMENT_A_AFFICHER.equals(tableEmailEmpty.getText());
    }

    public void clickTableBtnEditCompteBancaire() {
        WebElement table = tableComptesBancaires;

        String tableId = "table-comptes";

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + tableId + "']/tbody/tr"));

        int rowIndex = 1;
        while (rowIndex <= totalRowCount.size()) {
            String pathimg = "//table/tbody/tr[" + rowIndex + "]/td[10]/a";

            WebElement clickable = driver.findElement(By.xpath(pathimg));

            if (clickable.isDisplayed()) {
                clickable.click();
                return;
            }
            rowIndex = rowIndex + 1;
        }

        ActionUtils.clickAndLoading(btnAddCompteBancaire);
    }

    public boolean checkTableCompteBancaireEmpty() {
        return TestData.AUCUN_ELEMENT_A_AFFICHER.equals(tableCompteBancaireEmpty.getText());
    }

    public void clickBtnPrefereCompteBancaire() {
        if (btnPrefereCompteBancaire.isSelected()) {
            return;
        }
        btnPrefereCompteBancaire.click();
    }

    public void clickBtnEnregistrerCompteBancaire() {
        ActionUtils.clickAndLoading(btnEnregistrerCompteBancaire);
    }

    public void fillCommentaire(String commentaire) {
        commentaireCompteBancaire.clear();
        commentaireCompteBancaire.sendKeys(commentaire);
    }

    public void fillDateFinValiditeCompteBancaire() {
        Calendar calJour = Calendar.getInstance();
        calJour.add(Calendar.DATE, 1);
        ActionUtils.doJsFill("dtFinValCptBancId2", DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, calJour.getTime()));
    }

    public void fillNumeroIban(String compte) {
        ActionUtils.doJsFill("ibanCptBanId2", compte);
    }

    public void clickBtnAddCompteBancaire() {
        ActionUtils.clickAndLoading(btnAddCompteBancaire);
    }

    public void refreshBic() {
        ActionUtils.clickAndLoading(imgRefreshBic);
    }

    public void clickAjouterActivite() {
        ActionUtils.clickAndLoading(btnAjouterActivite);
    }

    public void clickAjouterDetailActivite() {
        ActionUtils.clickAndLoading(btnAjouterDetailActivite);
    }

    public void clickVoirToutesActivites() {
        ActionUtils.clickAndLoading(btnVoirToutesActivites);
    }

    public void clickEditActivite(String typeActivite) {
        TableUtils.clickEditElement(TABLE_ACTIVITES, typeActivite);
    }

    public void clickEditFirstActivite() {
        TableUtils.clickFirstEdit(TABLE_ACTIVITES);
    }

    public void clickEditFirstActiviteEntreprise() {
        TableUtils.clickFirstEdit("table-act-ent");
    }

    public boolean checkActivite(String typeActivite) {
        return TableUtils.isElementPresent(TABLE_ACTIVITES, typeActivite);
    }

    public void selectTypeActivite(String type) {
        ActionUtils.sendInCombo(selectTypeActivite, type);
    }

    public void checkAssujetti(String check) {
        ActionUtils.doCheck(check, checkAssujetti);
    }

    public void checkPrincipalementExercee(String check) {
        ActionUtils.doCheck(check, checkPrincExercee);
    }

    public void checkDonneesValides(String check) {
        ActionUtils.doCheck(check, checkDonneesValides);
    }

    public void clickAjouterTypeActivites() {
        ActionUtils.clickAndLoading(btnAjouterTypeActivites);
    }

    public void clickEnregistrerActivite() {
        ActionUtils.clickAndLoading(btnEnregistrerActivite);
    }

    public void selectProfession(String profession) {
        if (profession != null) {
            ActionUtils.sendInCombo(selectProfession, profession);
        }
    }

    public void selectNaceProfession(String profession) {
        if (profession != null) {
            SeleniumUtils.waitForActionCommon();
            ActionUtils.sendInCombo(selectNace, profession);
        }
    }

    public void selectFonction(String fonction) {
        if (fonction != null) {
            ActionUtils.sendInCombo(selectFonction, fonction);
        }
    }

    public void fillDateDebut(String dateDebut) {
        if (dateDebut != null) {
            ActionUtils.doJsFill("actDteDebId", dateDebut);
        } else {
            ActionUtils.doJsFill("actDteDebId", DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()));
        }
    }

    public void fillDateFin(String dateFin) {
        if (dateFin != null && !StringUtils.isEmpty(dateFin.trim())) {
            ActionUtils.doJsFill("actDteFinId", dateFin);
        }
    }

    public void fillDateDebutType(String dateDebut) {
        if (dateDebut != null) {
            ActionUtils.doJsFill("actProfDateDebutToEdit", dateDebut);
        } else {
            ActionUtils.doJsFill("actProfDateDebutToEdit", DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()));
        }
    }

    public void fillDateFinType(String dateFin) {
        if (dateFin != null && !StringUtils.isEmpty(dateFin.trim())) {
            ActionUtils.doJsFill("actProfDateFinToEdit", dateFin);
        }
    }

    public void fillCommentActivite(String commentaire) {
        if (commentaire != null && !StringUtils.isEmpty(commentaire.trim())) {
            commentActivite.clear();
            commentActivite.sendKeys(commentaire);
        }
    }

    public void fillCommentTypeActivite(String commentaire) {
        if (commentaire != null && !StringUtils.isEmpty(commentaire.trim())) {
            commentTypeActivite.clear();
            commentTypeActivite.sendKeys(commentaire);
        }
    }

    public void fillNomIdentiteLiee(String nom) {
        nomIdentiteLiee.sendKeys(nom);
    }

    public void fillPrenomIdentiteLiee(String prenom) {
        prenomIdentiteLiee.sendKeys(prenom);
    }

    public void fillSexeIdentiteLiee(String sexe) {
        ActionUtils.sendInComboWithoutLoading(sexeIdentiteLiee, sexe);
    }

    public void fillNissIdentiteLiee(String niss) {
        ActionUtils.doJsFill("ilFilNissId", niss);
    }

    public void fillDenominationIdentiteLiee(String denomination) {
        denominationIdentiteLiee.sendKeys(denomination);
    }

    public void fillFormeJuridiqueIdentiteLiee(String forme) {
        if (forme != null) {
            ActionUtils.sendInComboWithoutLoading(formeJuridiqueIdentiteLiee, forme);
        }
    }

    public void clickRechercheIdentiteLiee() {
        ActionUtils.clickAndLoading(btnRechercheIdentiteLiee);
    }

    public void clickCreerIdentiteLiee() {
        ActionUtils.clickAndLoading(btnCreerIdentiteLiee);
    }

    public void selectIdentiteLiee() {
        ActionUtils.clickAndLoading(selectIdentiteLiee);
    }

    public void selectTypeActiviteAidant() {
        ActionUtils.clickAndLoading(selectTypeActiviteAidant);
    }

    public void selectApportGestion(String apportGestion) {
        ActionUtils.sendInComboWithoutLoading(selectApportGestion, apportGestion);
    }

    public void clickEnregistrerDetailActiviteEntreprisePP() {
        try {
            ActionUtils.clickAndLoading(btnEnregistrerDetailActiviteEntreprisePP5);
        } catch (NoSuchElementException e1) {
            LOGGER.error(e1.getMessage());
            try {
                ActionUtils.clickAndLoading(btnEnregistrerDetailActiviteEntreprisePP4);
            } catch (NoSuchElementException e2) {
                LOGGER.error(e2.getMessage());
                try {
                    ActionUtils.clickAndLoading(btnEnregistrerDetailActiviteEntreprisePP3);
                } catch (NoSuchElementException e3) {
                    LOGGER.error(e3.getMessage());
                    try {
                        ActionUtils.clickAndLoading(btnEnregistrerDetailActiviteEntreprisePP2);
                    } catch (NoSuchElementException e4) {
                        LOGGER.error(e4.getMessage());
                        ActionUtils.clickAndLoading(btnEnregistrerDetailActiviteEntreprisePP1);
                    }
                }
            }
        }
    }

    public boolean checkTableActiviteEmpty() {
        return TestData.AUCUN_ELEMENT_A_AFFICHER.equals(tableActiviteEmpty.getText());
    }

    public boolean checkTableTypeActiviteEmpty() {
        return TestData.AUCUN_ELEMENT_A_AFFICHER.equals(tableTypeActiviteEmpty.getText());
    }

    public boolean isMenuCotisantDisplayed() {
        try {
            menuCotisant.isDisplayed();
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public void fillCiviliteIdentitePP(String civilite) {
        ActionUtils.sendInComboWithoutLoading(civiliteIdentitePP, civilite);
    }

    public void fillFormeJuridiqueIdentitePM(String forme) {
        ActionUtils.sendInComboWithoutLoading(formeJuridiqueIdentitePM, forme);
    }

    public void fillLanguePrefereeIdentite(String langue) {
        ActionUtils.sendInComboWithoutLoading(languePrefereeIdentite, langue);
    }

    public void fillNationaliteIdentitePP(String nationalite) {
        ActionUtils.sendInComboWithoutLoading(nationaliteIdentitePP, nationalite);
    }

    public void selectNationalite(String nationalite) {
        ActionUtils.sendInCombo(selectNationalite, nationalite);
    }

    public void clickBtnEnregistrerNationalite() {
        ActionUtils.clickAndLoading(btnEnregistrerNationalite);
    }

    public void fillSexeIdentitePP(String sexe) {
        ActionUtils.sendInComboWithoutLoading(sexeIdentitePP, sexe);
    }

    public void fillDateNaissanceIdentitePP(String dateNaissance) {
        ActionUtils.doJsFill("dateNaiss", dateNaissance);
    }

    public void fillNomIdentitePP(String nom) {
        nomIdentitePP.clear();
        nomIdentitePP.sendKeys(nom);
    }

    public void fillPrenomIdentitePP(String prenom) {
        prenomIdentitePP.clear();
        prenomIdentitePP.sendKeys(prenom);
    }

    public void clickBtnAjoutIdentifiantIdentite() {
        ActionUtils.clickAndLoading(btnAddIdentifiantIdentite);
    }

    public void fillTypeIdentifiant(String type) {
        btnSelectTypeIdentifiant.click();
        SeleniumUtils.waitForActionCommon();
        if ("NISS".equals(type)) {
            selectTypeIdentifiantNiss.click();
        } else {
            selectTypeIdentifiantBce.click();
        }
    }

    public void fillNumIdentifiant(String num) {
        ActionUtils.doJsFill("numIdentifiantId2", num);
    }

    public void clickBtnEnregistrerIdentifiantIdentite() {
        ActionUtils.clickAndLoading(btnEnregistrerIdentifiantIdentite);
    }

    public void clickBtnAjoutTelephone() {
        ActionUtils.clickAndLoading(btnAjouterTel);
    }

    public void clickBtnAjoutEmail() {
        ActionUtils.clickAndLoading(btnAjouterAutresCoordonees);
    }

    public void clickBtnAjoutAdresseIdentifiantPP() {
        ActionUtils.clickAndLoading(btnAjoutAdresseIdentifiantPP);
    }

    public void selectTypeContact(String type) {
        ActionUtils.sendInComboWithoutLoading(selectTypeContact, type);
    }

    public void checkMandate() {
        checkMandate.click();
    }

    public boolean checkTableContactEmpty() {
        return TestData.AUCUN_ELEMENT_A_AFFICHER.equals(tableContactsEmpty.getText());
    }

    public void clickBtnEnregistrerContact() {
        ActionUtils.clickAndLoading(btnEnregistrerContact);
    }

    public void clickAjouterContact() {
        ActionUtils.clickAndLoading(btnAjouterContact);
    }

    public void clickEditerContact() {
        ActionUtils.clickAndLoading(btnEditerContact);
    }

    public void selectRaisonFinCohabitationLegale(String raison) {
        ActionUtils.sendInComboWithoutLoading(selectRaisonFinCohabitationLegale, raison);
    }

    public boolean checkTableCohabitationLegaleEmpty() {
        return TestData.AUCUN_ELEMENT_A_AFFICHER.equals(tableCohabitationLegaleEmpty.getText());
    }

    public void clickBtnEnregistrerCohabitationLegale() {
        ActionUtils.clickAndLoading(btnEnregistrerCohabitationLegale);
    }

    public void clickAjouterCohabitationLegale() {
        ActionUtils.clickAndLoading(btnAjouterCohabitationLegale);
    }

    public void fillDateDebutValiditeCohabitationLegale(String date) {
        ActionUtils.doJsFill("dtDebutValLienId", date);
    }

    public void fillDateFinValiditeCohabitationLegale(String date) {
        ActionUtils.doJsFill("dtFinValLienId", date);
    }

    public void fillDateDebutValiditeEtatCivil(String date) {
        ActionUtils.doJsFill("dtDebutValLienId", date);
    }

    public void selectEtatCivil(String etatCivil) {
        ActionUtils.sendInCombo(selectEtatCivil, etatCivil);
    }

    public boolean checkEtatCivil(String etatCivil) {
        return TableUtils.isElementPresent("table-etat-civil", etatCivil);
    }

    public boolean checkCohabitationLegale(String raison) {
        return TableUtils.isElementPresent("table-cohabitation", raison);
    }

    public boolean checkContact(String typeContact) {
        return TableUtils.isElementPresent("table-contacts", typeContact);
    }

    public void fillCommentaireEtatCivil(String commentaire) {
        commentaireEtatCivil.clear();
        commentaireEtatCivil.sendKeys(commentaire);
    }

    public boolean checkTableEtatCivilEmpty() {
        return TestData.AUCUN_ELEMENT_A_AFFICHER.equals(tableEtatCivilEmpty.getText());
    }

    public void clickBtnEnregistrerEtatCivil() {
        ActionUtils.clickAndLoading(btnEnregistrerEtatCivil);
    }

    public void clickAjouterEtatCivil() {
        ActionUtils.clickAndLoading(btnAjouterEtatCivil);
    }

    public void clickEditerEtatCivil() {
        ActionUtils.clickAndLoading(btnEditerEtatCivil);
    }

    public void clickBtnOperationAdministrative() {
        ActionUtils.clickAndLoading(btnOpeAdmin);
    }

    public void clickRadioFiltreTout() {
        radioFiltreTout.click();
    }

    public void doFillFiltreTypeDemande(String demande) {
        ActionUtils.sendInComboWithoutLoading(fieldFiltreDemande, demande);
    }

    public boolean isDecisionEquals(String decision) {
        return TableUtils.isElementPresent("table-synthese", decision);
    }

    public void clickBtnSearch() {
        ActionUtils.clickAndLoading(btnSearch);
    }

    /* Suspension Methodes

     */

    public void clickSuspension() {
        ActionUtils.clickChildMenu(menuRecouvrement, menuSuspensionRecouvrement, driver);
    }

    public void clickAjouterSuspension() {
        ActionUtils.moveToAndClickChild(menuActionSuspension, menuCreationSuspension, driver);
    }

    public void clickLeverSuspension() {
        ActionUtils.moveToAndClickChild(menuActionLeverSuspension, menuLeverSuspension, driver);
    }

    public void clickModifierSuspension() {
        ActionUtils.moveToAndClickChild(menuActionLeverSuspension, menuModifierSuspension, driver);
    }

    public int lengthCombobox(String xpath){
        List<WebElement> elements = driver.findElements(By.xpath(xpath));

        return elements.size();

    }

    public void clickAjouterCauseSuspension(String causeSuspension) {
        ActionUtils.sendInComboWithoutLoading(comboCauseSuspension, causeSuspension);
    }

    public void clickAjouterJustificationSuspension(String justificationSuspension) {
        ActionUtils.sendInTextField(textJustificationSuspension, justificationSuspension);
    }

    public void modifierJustificationSuspension(String justificationSuspensionModif){
        ActionUtils.sendInTextField(textJustificationModifier, justificationSuspensionModif);

    }

    public void clickDateSuspension(String datePeriodeSuspension) {
        ActionUtils.sendInTextField(dateSuspension, datePeriodeSuspension);
    }

    public void clickPeriodeDebSuspension(String dateDebPeriodeSuspension) {
        ActionUtils.sendInTextField(debPeriodeSuspension, dateDebPeriodeSuspension);
    }

    public void clickPeriodeFinSuspension(String dateFinPeriodeSuspension) {
        ActionUtils.sendInTextField(finPeriodeSuspension, dateFinPeriodeSuspension);
    }

    public void clickCheckboxAllSoldesperiodique() {
        checkAllSoldesPeriodique.click();
    }

    public void clickCheckboxFirstSoldePeriodique() {
        checkFirstSoldesPeriodique.click();
    }


    public String clickCheckboxOrdinaireSingleSoldesPeriodique(String creance, String type){
        String periode;
        List<WebElement> totalRowCount = tableSoldePeriodique.findElements(By.xpath("//*[@id='" + "entitesComptablesTable" + "']/tbody/tr"));
        periode = TableUtils.searchIntable(tableSoldePeriodique, "entitesComptablesTable", 3, 4, 2, creance, type);
        TableUtils.checkboxInTable(tableSoldePeriodique, "entitesComptablesTable", 2, periode);

        return periode;
    }

    public void clickCheckboxOrdinaireSoldesPeriodique(String type){
        //String periode = null;
        //List<WebElement> totalRowCount = tableSoldePeriodique.findElements(By.xpath("//*[@id='" + "entitesComptablesTable" + "']/tbody/tr"));
        //periode = TableUtils.searchIntable(tableSoldePeriodique, "entitesComptablesTable", 3, 4, 2, creance, type);
        TableUtils.checkboxInTable(tableSoldePeriodique, "entitesComptablesTable", 4, type);
    }

    public void clickCheckboxSingleRegulSoldesPeriodique(String creance, String type){
        TableUtils.checkboxsingleBoxInTable(tableSoldePeriodique, "entitesComptablesTable", 3, 4, creance, type);

    }

    public void clickCheckboxSingleSoldesPeriodique(String creance){
        TableUtils.checkboxsingleBoxInTable(tableSoldePeriodique, "entitesComptablesTable", 3, creance);

    }

    public void clickCheckboxSingleFutCotConcerneSoldesPeriodique(String periode){
        TableUtils.checkboxsingleBoxInTable(tableSoldePeriodique, "entitesComptablesTable", 2, periode);

    }

    public void clickCheckboxSingleSoldesECPartielle(String periodeEC){
        TableUtils.checkboxsingleBoxInTable(tableSoldePeriodique, "entitesComptablesTable", 2, periodeEC);

    }

    /*public void clickCheckboxMultSoldesPeriodique(String creance){
        TableUtils.checkboxInTable(tableSoldePeriodique, "entitesComptablesTable", 3, creance);

    }*/

    public void clickCheckboxMultMajSoldesPeriodique(String creance){
        TableUtils.checkboxInTable(tableSoldePeriodique, "entitesComptablesTable", 3, creance);

    }

    public void clickCheckboxSingleMajSoldesPeriodique(String creance){
        TableUtils.checkboxsingleBoxInTable(tableSoldePeriodique, "entitesComptablesTable", 3, creance);

    }

    public void clickCheckboxMultRegulSoldesPeriodique(String creance, String type){
        TableUtils.checkboxInTable(tableSoldePeriodique, "entitesComptablesTable", 3, 4, creance, type);

    }

    public void clickRadioIdentité(String applicationIdentite){
        boolean application = Boolean.parseBoolean(applicationIdentite);

        if(application)
            identiteSelectionee.click();
        else
            toutDebiteur.click();

    }

    public void clickRadioaRevoir(String aRevoir ){


        if(aRevoir.equals("true"))
            aRevoirUtilisateur.click();
        else
            if(aRevoir.equals("false"))
                finAutomatique.click();


    }

    public void clickRadioSuspensionFuture(Boolean suspension){
        if(suspension)
            suspensionFuture.click();
        else
            nonSuspensionFuture.click();
    }

    public void clickRadioTypeCotisation(String typeCotisation){
        if (!typeCotisation.equals("")) {
            switch (typeCotisation) {
                /*case "Ordinaire et régularisation":
                    ordinaireEtRegularisation.click();
                    break;*/
                case "Ordinaire":
                    ordinaire.click();
                    break;
                case "Régularisation":
                    regularisation.click();
                    break;

            }
        }
    }

    public boolean checkValueSuspension(){
        return (dateSuspension.isDisplayed());
    }

    public void annulerSuspension(){
        boutonAnnulerSuspension.click();
        SeleniumUtils.waitForAction(10000);
        boutonConfirmationAnnulerSuspension.click();

    }

    public void quitterSuspension(){
        boutonQuitterSuspension.click();
    }

    public void clickRetourLeverSuspension(){
        boutonRetourAnnulerSuspension.click();
        SeleniumUtils.waitForActionCommon();
    }

    public void clickEnregistrerRecetteOperation(){
        boutonEnregistrerAnnulerRecette.click();
        SeleniumUtils.waitForActionLong();

    }

    public void clickRetourRecetteOperation(){
        boutonRetourOpération.click();
        SeleniumUtils.waitForActionLong();

    }

    public void clickBoutonReinitialiserTache(){
        boutonReinitialiserTaches.click();
        SeleniumUtils.waitForActionCommon();

    }


    /*public void clickSoldes() {
        ActionUtils.clickChildMenu(menuComptes, , driver);
    }*/

    /**
     * faillite méthode
     */
    public void clickMenuFaillite() {
        ActionUtils.clickChildMenu(menuDeclarationCreance, menuFaillite, driver);
    }

    public void clickAjoutCreance(){
        ActionUtils.moveToAndClickChild(menuActionsFaillite, menuItemCreerFaillite , driver);
    }

    public void clickCompleteCreance(){
        ActionUtils.moveToAndClickChild(menuActionsFaillite,menuItemCompleterFaillite,driver);
    }

    public void selectTribunalCommerce(String tribunalCommerce){
        ActionUtils.sendInComboWithoutLoading(selectTribunalCommerce, tribunalCommerce);
    }

    public void selectRadioAveu(){
        if(!selectDeclarationAveu.isSelected())
            selectDeclarationAveu.click();
    }

    public void selectRadioCitation(){
        if(!selectDeclarationCitation.isSelected())selectDeclarationCitation.click();
    }

    public void selectRadioIndeterminee(){
        if(!selectDeclarationIndéterminée.isSelected())selectDeclarationIndéterminée.click();
    }

    public void selectFailliteAveu(){
        if(!selectFailliteAveu.isSelected())
            selectFailliteAveu.click();
    }

    public void selectFailliteCitation(){
        if(!selectFailliteCitation.isSelected())selectFailliteCitation.click();
    }

    public void selectFailliteIndeterminee(){
        if(!selectFailliteIndéterminée.isSelected())selectFailliteIndéterminée.click();
    }

    public void fillDateJugementDeclaratif(String dateJugement){
      ActionUtils.doJsFill("dcrFailliteDateJugement" , dateJugement);
    }

    public void selectCurateur(String curateur){
        ActionUtils.sendInComboWithoutLoading(selectCurateur, curateur);
    }

    public void clickGestionFaillite(){
        ActionUtils.clickAndLoading(gestionFaillite);
    }

    public void selectPeriodeFrais(String periode,String frais){
        boolean p = Boolean.parseBoolean(periode);
        boolean f = Boolean.parseBoolean(frais);
        if(p){
            selectPeriode.click();
        }
        if(f){
            selectFrais.click();
        }
    }

    public void clickEnregistrerDetail(){
        ActionUtils.clickAndLoading(enregistrerDetail);
    }

    public void clickEnregistrerDetailRegule(){
        ActionUtils.clickAndLoading(enregistrerDetailRegule);
    }

    public void fillCommentaireFaillite(String commentaires){
        ActionUtils.sendInTextField(commentaireFaillite, commentaires);
    }

    public void selectImpressionCourrierFaillite(String generer,String declaration, String dispense,String assurance) {
        boolean g = Boolean.parseBoolean(generer);
        boolean dec = Boolean.parseBoolean(declaration);
        boolean  disp = Boolean.parseBoolean(dispense);
        boolean a = Boolean.parseBoolean(assurance);
        if(!g){
            selectGenererCourrier.click();
        }
        if(!dec){
            selectDeclarationCessation.click();
        }
        if(!disp) {
            selectDispense.click();
        }
        if(!a){
            selectAssurance.click();
        }
    }

    public void fillContenuCourrier(String texte) {
        if(contenuCourrier.isEnabled()){
            if(texte != null || !StringUtils.isEmpty(texte.trim()))
            ActionUtils.sendInTextField(contenuCourrier, texte);
        }
    }

    public void clickEditerFaillite(){
        ActionUtils.clickAndLoading(btnEditerFaillite);
    }

    public void clickModifFaillite(){
        ActionUtils.clickAndLoading(btnModifierFaillite);
    }

    public void fillModifDateJugementDeclaratif(String dateJugement){
        ActionUtils.doJsFill("dcrFailliteDateDeclaration" , dateJugement);
    }

    public void fillDateJugementCloture(String dateCloture){
        if(dateCloture != null )
        {
            ActionUtils.doJsFill("dcrFailliteDateCloture" , dateCloture);
        }
    }

    public void selectRadioClotureExcusable(){
        selectClotureExcusable.click();
    }

    public void selectRadioClotureInexcusable(){
        selectClotureInexcusable.click();
    }

    public void selectRadioClotureIndeterminee(){
        selectClotureIndeterminee.click();
    }

    public void selectTypeRelance(String relancePrevue,String dateRelance,String pasRelance) {
        boolean relP = Boolean.parseBoolean(relancePrevue);
        boolean pR = Boolean.parseBoolean(pasRelance);
        if(relP) {
            selectRelanceCurateurOui.click();
            ActionUtils.doJsFill("dcrFailliteDateRelance" , dateRelance);
        }
        if (pR){
            selectRelanceCurateurNon.click();
        }
    }

    public void clickEnregistrerModif(){
        btnEnregistrerModifFaillite.click();
    }

    public void clickOngletCommentaire(){ ongletCommentairesFaillite.click(); }

    public void clickBtnAjoutCommentaire(){ btnAjoutCommentaireFaillite.click(); }

    public void fillAjoutCommentaireFaillite(String commentaire){
        if(commentaire != null || !StringUtils.isEmpty(commentaire.trim()))
            ActionUtils.sendInTextField(zoneCommentaireFaillite, commentaire);
    }

    public void selectTypeContactCommentaireFaillite(String contact){
        ActionUtils.sendInComboWithoutLoading(selectContactCommentaireFaillite,contact);
    }

    public void clickEnregistrerCommentaireFaillite(){ btnEnregistrerCommentaireFaillite.click();}

    public void clickBtnSuppressionCommentaire(){ btnSuppressionCommentaire.click(); }

    public void clickTerminaisonFaillite(){
        ActionUtils.moveToAndClickChild(menuActionsFaillite, menuItemTerminerFaillite , driver);
    }

    public void clickReactiverFaillite(){
        ActionUtils.moveToAndClickChild(menuActionsFaillite, menuItemReactiverFaillite , driver);
    }

    public void clickEditerSuspensionFaillite() {btnEditerSuspension.click();}

    public void clickOngletDetailDeclarationFaillite() {ongletDetailDeclarationFaillite.click();}

    public void clickDetailDeclaration(){ ActionUtils.clickAndLoading(btnDetailCreance);}

    public void selectNouvelleRegule(){
        ActionUtils.executeJS("var array  = document.getElementsByName('deleteButton'); " +
                "for(var i = 0; i < array.length ;i++){array[i].parentNode.parentElement.firstElementChild.children[4].checked = true;};");
    }

    public void selectRefusCreance(String creance) {
        ActionUtils.sendInComboWithoutLoading(selectTypeFrais, creance);
    }

    public void selectDeclarationCreance(String creance) {
        ActionUtils.sendInComboWithoutLoading(selectTypeFrais, creance);
    }

    public void selectRetraitCreance(String creance) {
        ActionUtils.sendInComboWithoutLoading(selectTypeFrais, creance);
    }

    public void clickEditerDetailDeclaration() {btnEditerFrais.click();}

    public void clickEnregistrerDetailDeclaration() {ActionUtils.clickAndLoading(btnEnregistrerDetailCreanceFaillite);}

    public void clickTache(){
        ActionUtils.clickAndLoading(menuTaches);
    }

    public void clickReinitialiser(){
        ActionUtils.clickAndLoading(btnReinitialiser);
    }

    public void selectStatusTache(){
        ActionUtils.sendInComboWithoutLoading(selectStatutTache,"Clôturée");
    }

    public void selectTypeDeTache() {
        ActionUtils.sendThirdInComboWithoutLoading( selectTypeTache, "Faillite");
    }

    public void clickRechercheTache(){
        ActionUtils.clickAndLoading(btnRechercherTache);
    }

    public void clickResumeTache(){
        ActionUtils.clickAndLoading(iconeTache);
    }

    public void fenetreComplete(){
        retrecirMenu.click();
    }
}