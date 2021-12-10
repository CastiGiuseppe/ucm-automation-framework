package be.ucm.cas.nasca.web.pagefactory.multidossiers;

import be.ucm.cas.nasca.web.test.support.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MultiDossiersPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    private static final String RESULTAT_STATISTIQUE_ATTENDU = "1";

    private static final String TABLE_DOCUMENT_ID = "table-resRech";
    private static final String TABLE_DEMANDES_ID = "tableDemandes";

    private final WebDriver driver;

    // MENU
    @FindBy(id = "level3Div")
    private static WebElement menuMiseEnAttenteDuCourrier;

    @FindBy(id = "level3-1Div")
    private static WebElement menuAvisPP;

    @FindBy(id = "level3-2Div")
    private static WebElement menuAvisPM;

    @FindBy(id = "level4Div")
    private static WebElement menuRetraitdossier;

    @FindBy(id = "level4-1Div")
    private static WebElement menuRetirerUnDossier;

    @FindBy(id = "level4-2Div")
    private static WebElement menuConsultationRetrait;

    @FindBy(id = "level4-2-1Div")
    private static WebElement menuDossierSorties;

    @FindBy(id = "level4-2-2Div")
    private static WebElement menuDossierDemandes;

    @FindBy(id = "level4-2-3Div")
    private static WebElement menuDossierTirer;

    @FindBy(id = "level5Div")
    private static WebElement menuCourrierEntrant;

    @FindBy(id = "level5-1Div")
    private static WebElement menuCourrierEntrantGestion;

    @FindBy(id = "level5-2Div")
    private static WebElement menuCourrierEntrantStatistiques;

    @FindBy(id = "level6Div")
    private static WebElement menuCodeBarreGed;

    @FindBy(id = "level6-1Div")
    private static WebElement menuGenerationCodeBarreGed;

    // IMPRESSIONS
    @FindBy(id = "searchBtn")
    private static WebElement btnRechercherDocument;

    @FindBy(id = "_typeNumeroId")
    private static WebElement selectTypeNumero;

    @FindBy(id = "_fileSortieId")
    private static WebElement selectTypeSortieDocument;

    @FindBy(id = "buttonEmpty_fileSortieId")
    private static WebElement btnEmptyTypeSortieDocument;

    @FindBy(id = "_etatDocumentId")
    private static WebElement selectEtatDocument;

    @FindBy(id = "buttonEmpty_etatDocumentId")
    private static WebElement btnEmptyEtatDocument;

    @FindBy(id = TABLE_DOCUMENT_ID)
    private static WebElement tableImpression;

    @FindBy(xpath = "//html/body/div[9]/div[3]/div/button")
    private static WebElement btnPreviewFermer;

    @FindBy(id = "viewDocId")
    private static WebElement previewDocument;

    @FindBy(xpath = "//*[@id ='viewDocId']/embed")
    private static WebElement previewDocumentSrc;

    // RETRAIT DE DOSSIER
    @FindBy(id = "numero")
    private static WebElement inputNumeroRetraitDossier;

    @FindBy(xpath = "//form/fieldset/div[1]/div[1]/p/input")
    private static WebElement inputNumeroListeRetraitDossier;

    @FindBy(xpath = "//div[1]/form/div[1]/div/p/input")
    private static WebElement inputIdentifiantCourrierRetraitDossier;

    @FindBy(xpath = "//div[2]/form/div[1]/div/p/input")
    private static WebElement inputIdentifiantUrgentRetraitDossier;

    @FindBy(xpath = "//div[4]/form/div[1]/div/p/input")
    private static WebElement inputIdentifiantUrgentClasseurRetraitDossier;

    @FindBy(xpath = "//div[1]/form/div[4]/button")
    private static WebElement btnEnregistrerCourrierRetraitDossier;

    @FindBy(xpath = "//div[2]/form/div[3]/button")
    private static WebElement btnEnregistrerUrgentRetraitDossier;

    @FindBy(xpath = "//div[4]/form/div[3]/button")
    private static WebElement btnEnregistrerUrgentClasseurRetraitDossier;

    @FindBy(xpath = "//rightcontent/div[2]/div/div/ul/li[1]/a")
    private static WebElement ongletCourrierRetraitDossier;

    @FindBy(xpath = "//rightcontent/div[2]/div/div/ul/li[2]/a")
    private static WebElement ongletUrgentRetraitDossier;

    @FindBy(xpath = "//rightcontent/div[2]/div/div/ul/li[3]/a")
    private static WebElement ongletListeRetraitDossier;

    @FindBy(xpath = "//rightcontent/div[2]/div/div/ul/li[4]/a")
    private static WebElement ongletUrgentClasseurRetraitDossier;

    @FindBy(id = "_retraitDossierVo_userChoisi")
    private static WebElement selectDestinataireRetraitDossier;

    @FindBy(id = "editListeForm_retraitDossierVo_titreListe")
    private static WebElement inputTitreListeRetraitDossier;

    @FindBy(id = "addDossierBtn")
    private static WebElement btnAjoutListeRetraitDossier;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerListeRetraitDossier;

    @FindBy(xpath = "//div[1]/form/ul/li")
    private static WebElement messageCourrierRetraitDossier;

    @FindBy(xpath = "//div[2]/form/ul/li")
    private static WebElement messageUrgentRetraitDossier;

    @FindBy(xpath = "//div[3]/form/ul/li")
    private static WebElement messageListeRetraitDossier;

    @FindBy(xpath = "//div[4]/form/ul/li")
    private static WebElement messageUrgentClasseurRetraitDossier;

    @FindBy(xpath = "//div/div/h3[1]/a")
    private static WebElement openAccordionUrgent;

    @FindBy(xpath = "//div/div/h3[2]/a")
    private static WebElement openAccordionJournalier;

    @FindBy(xpath = "//div/div/h3[4]/a")
    private static WebElement openAccordionListe;

    @FindBy(id = "listeImpressionBtn")
    private static WebElement btnListeImprimeeListRetrait;

    @FindBy(xpath = "//div/div[1]/form/div/div[2]/div/input")
    private static WebElement btnImprimerListUrgentRetrait;

    @FindBy(xpath = "//div/div[2]/form/div/div[2]/div/input")
    private static WebElement btnImprimerListJournalierRetrait;

    @FindBy(id = "printBtn")
    private static WebElement btnImprimerListListeRetrait;

    @FindBy(id = "sortirBtn")
    private static WebElement btnSortirDossierRetrait;

    @FindBy(id = TABLE_DOCUMENT_ID)
    private static WebElement tableResultRetraitDossierConsult;

    @FindBy(id = "lvl_11")
    private static WebElement menuRentrerRetraitDossier;

    // COURRIERS ENTRANTS
    @FindBy(id = "lvl_1")
    private static WebElement menuActionsCourriersEntrants;

    @FindBy(id = "lvl_11")
    private static WebElement menuEncoderCourrier;

    @FindBy(id = "lvl_12")
    private static WebElement menuEncoderCourriersMultiples;

    // search grid in courrier gestion
    @FindBy(id = "searchBtn")
    private static WebElement btnSearchCourrierGrid;

    @FindBy(id = "btnRechercher")
    private static WebElement btnRechercher;

    @FindBy(id = "btnDebloquer")
    private static WebElement btnDebloquer;

    @FindBy(id = "btnBloquer")
    private static WebElement btnBloquer;

    @FindBy(id = "table-blocagess")
    private static WebElement tableblocage;

    // COURRIER ENTRANT POPUP
    @FindBy(id = "_editCourrierForm_encodage_courrierEntrantDto_objet_id")
    private static WebElement selectObjet;

    @FindBy(id = "_editCourrierForm_encodage_courrierEntrantDto_etat")
    private static WebElement selectEtat;

    @FindBy(id = "_editCourrierForm_encodage_courrierEntrantDto_newEtat")
    private static WebElement selectNewEtat;

    @FindBy(id = "_editCourrierForm_encodage_courrierEntrantDto_dateEcheance")
    private static WebElement selectDateEcheance;


    @FindBy(xpath = "//input[contains(@name,'encodage.numero')]")
    private static WebElement numerosearchPopUp;

    @FindBy(id = "numero")
    private static WebElement nisssearch;

    @FindBy(id = "addDossierBtn")
    private static WebElement btnAjoutNumero;

    @FindBy(xpath = "//div[10]/div[3]/div/button[2]")
    private static WebElement btnEnregistrerCourrier;

    @FindBy(id = TABLE_DOCUMENT_ID)
    private static WebElement tableresultcourrier;

    @FindBy(id = "tableStatObjets")
    private static WebElement tableresultstat;

    @FindBy(id = "nombreDossiers")
    private static WebElement nombreDossier;

    @FindBy(id = "_equipeFiltre")
    private static WebElement equipeStatistique;

    @FindBy(id = "_editCourrierMultiForm_encodage_courrierEntrantDto_objet_id")
    private static WebElement selectObjetMultiple;

    @FindBy(id = "_editCourrierMultiForm_encodage_courrierEntrantDto_userDestination_id")
    private static WebElement selectUserDestinationMultiple;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerCourrierMultiple;

    //CODE-BARRE GED
    @FindBy(id = "lvl_1")
    private static WebElement menuActionCodeBarreGED;

    @FindBy(id = "lvl_11")
    private static WebElement menuActionReactiverCodeBarreGED;

    @FindBy(id = "lvl_12")
    private static WebElement menuActionGenererCodeBarreGED;

    @FindBy(id = "_generationCodebarreForm_gedDemande_departement_id")
    private static WebElement selectDepartementCodeBarreGed;

    @FindBy(id = "_niveau2")
    private static WebElement selectTypeCodeBarreGed;

    @FindBy(id = "_niveau3")
    private static WebElement selectSousTypeCodeBarreGed;

    @FindBy(id = "_generationCodebarreForm_gedDemande_document_id")
    private static WebElement selectDocumentCodeBarreGed;

    @FindBy(id = "_generationCodebarreForm_gedDemande_sensDocument")
    private static WebElement selectSensCodeBarreGed;

    @FindBy(xpath = "//*[@id='addBtn']")
    private static WebElement btnAjouterCodeBarreGed;

    @FindBy(xpath = "//*[@id='deleteBtn']")
    private static WebElement btnSupprimerCodeBarreGed;

    public MultiDossiersPage(WebDriver drv) {
        this.driver = drv;
        PageFactory.initElements(driver, this);
    }

    public void fillDateCritereRechercheStatistiqueFin(String content) {
        ActionUtils.doJsFill("dateTo", content);
    }

    public void selectEquipeRechercheStatistique(String equipe) {
        ActionUtils.sendInComboWithoutLoading(equipeStatistique, equipe);
    }

    public void fillDateCritereRechercheStatistiqueDebut(String content) {
        ActionUtils.doJsFill("dateFrom", content);
    }

    public void clickBtnRechercher() {
        ActionUtils.clickAndLoading(btnRechercher);
    }

    public void clickBtnSearchCourrierGrid() {
        ActionUtils.clickAndLoading(btnSearchCourrierGrid);
    }

    public void selectEtat(String etat) {
        ActionUtils.sendInComboWithoutLoading(selectEtat, etat);
    }

    public void selectNewEtat(String etat) {
        ActionUtils.sendInComboWithoutLoading(selectNewEtat, etat);
    }

    public void clickBtnAjoutNumero() {
        ActionUtils.clickAndLoading(btnAjoutNumero);
    }

    public void fillNombreDossiersCourriersMultiples() {
        ActionUtils.sendInTextField(nombreDossier, "002");
    }

    public void fillDateEntreeCourriersMultiples() {
        ActionUtils.doJsFill("dateEntree", DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()));
    }

    public void fillDateEcheance() {
        ActionUtils.doJsFill("dateEcheance", DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()));
    }

    public void clickBtnEnregistrerPopUp() {
        ActionUtils.clickAndLoading(btnEnregistrerCourrier);
    }

    public void clickBtnEnregistrerMultiple() {
        ActionUtils.clickAndLoading(btnEnregistrerCourrierMultiple);
    }

    public void selectObjet(String objet) {
        ActionUtils.sendInComboWithoutLoading(selectObjet, objet);
    }

    public void clickSelectObjetMultiple() {
        ActionUtils.sendInComboWithoutLoading(selectObjetMultiple, "Cotisant");
    }

    public void clickSelectUserDestination() {
        ActionUtils.sendInComboWithoutLoading(selectUserDestinationMultiple, "Castiglione");
    }

    public void clickMenuMiseEnAttenteCourrier(String type) {
        if (type.equals(TestData.TYPE_PP)) {
            ActionUtils.clickChildMenu(menuMiseEnAttenteDuCourrier, menuAvisPP, driver);
            selectTypeNumero("Niss");
        } else {
            ActionUtils.clickChildMenu(menuMiseEnAttenteDuCourrier, menuAvisPM, driver);
            selectTypeNumero("Bce");
        }
    }

    public void clickMenuGenerationCodeBarreGed() {
        ActionUtils.clickChildMenu(menuCodeBarreGed, menuGenerationCodeBarreGed, driver);
    }

    public void clickMenuConsultationDossierSorties() {
        ActionUtils.clickChildFromChildMenu(menuRetraitdossier, menuConsultationRetrait, menuDossierSorties, driver);
    }

    public void clickMenuConsultationDossierDemandes() {
        ActionUtils.clickAndLoading(menuDossierDemandes);
    }

    public void clickMenuDossierATirer() {
        ActionUtils.clickChildFromChildMenu(menuRetraitdossier, menuConsultationRetrait, menuDossierTirer, driver);
    }

    public void clickMenuRetirerUnDossier() {
        ActionUtils.clickChildFromChildMenu(menuRetraitdossier, menuConsultationRetrait, menuRetirerUnDossier, driver);
    }

    public void clickMenuCourrierEntrantGestion() {
        ActionUtils.clickChildMenu(menuCourrierEntrant, menuCourrierEntrantGestion, driver);
    }

    public void clickMenuCourrierEntrantStatistiques() {
        ActionUtils.clickChildMenu(menuCourrierEntrant, menuCourrierEntrantStatistiques, driver);
    }

    public void fillNumeroSearchPopUp(String num) {
        ActionUtils.sendInTextField(numerosearchPopUp, num);
    }

    public void fillNumeroSearch(String num) {
        ActionUtils.sendInTextField(nisssearch, num);
    }

    public void clickEncoderCourrier() {
        ActionUtils.moveToAndClickChild(menuActionsCourriersEntrants, menuEncoderCourrier, driver);
    }

    public void clickEncoderCourriersMultiples() {
        ActionUtils.moveToAndClickChild(menuActionsCourriersEntrants, menuEncoderCourriersMultiples, driver);
    }

    public void clickRentrerRetraitDossier() {
        ActionUtils.moveToAndClickChild(menuActionsCourriersEntrants, menuRentrerRetraitDossier, driver);
    }

    public String getSearchResultBlocageCourrier(String contenuresult) {
        WebElement table = tableblocage;

        String tableId = "table-blocagess";

        List<WebElement> totalRowCount = table.findElements(By
                .xpath("//*[@id='" + tableId + "']/tbody/tr"));

        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            for (WebElement colElement : totalColumnCount) {
                String colElemwthspace = SeleniumUtils.deleteFormat(colElement.getText());
                if (colElemwthspace.contains(contenuresult)) {
                    return colElement.getText();
                }
                columnIndex = columnIndex + 1;

            }
            rowIndex = rowIndex + 1;
        }
        return null;
    }

    public void fillNissorBCE(String nissOrBce) {
        ActionUtils.doJsFill("numeroId", nissOrBce);
    }

    public void fillNissorBCERetraitDossier(String nissOrBce) {
        inputNumeroRetraitDossier.sendKeys(nissOrBce);
    }

    public void fillNissorBCEListeRetraitDossier(String nissOrBce) {
        inputNumeroListeRetraitDossier.sendKeys(nissOrBce);
    }

    public void fillNissorBCECourrierRetraitDossier(String nissOrBce) {
        inputIdentifiantCourrierRetraitDossier.sendKeys(nissOrBce);
    }

    public void fillNissorBCEUrgentRetraitDossier(String nissOrBce) {
        inputIdentifiantUrgentRetraitDossier.sendKeys(nissOrBce);
    }

    public void fillNissorBCEUrgentClasseurRetraitDossier(String nissOrBce) {
        inputIdentifiantUrgentClasseurRetraitDossier.sendKeys(nissOrBce);
    }

    public void fillDestinataireUrgentClasseurRetraitDossier() {
        ActionUtils.sendInComboWithoutLoading(selectDestinataireRetraitDossier, "Castiglione");
    }

    public void fillTitreListeRetraitDossier() {
        ActionUtils.sendInTextField(inputTitreListeRetraitDossier, "Test");
    }

    public void fillDateListeRetraitDossier() {
        Calendar calJour = Calendar.getInstance();
        calJour.add(Calendar.DAY_OF_MONTH, 7);

        ActionUtils.doJsFill("editListeForm_retraitDossierVo_dateRetrait", DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, calJour.getTime()));
    }

    public void clickBtnAddToListeRetraitDossier() {
        ActionUtils.clickAndLoading(btnAjoutListeRetraitDossier);
    }

    public void clickBtnEnregistrerListeRetraitDossier() {
        ActionUtils.clickAndLoading(btnEnregistrerListeRetraitDossier);
    }

    public void clickBtnEnregistrerCourrierRetraitDossier() {
        ActionUtils.clickAndLoading(btnEnregistrerCourrierRetraitDossier);
    }

    public void clickBtnEnregistrerUrgentRetraitDossier() {
        ActionUtils.clickAndLoading(btnEnregistrerUrgentRetraitDossier);
    }

    public void clickBtnEnregistrerUrgentClasseurRetraitDossier() {
        ActionUtils.clickAndLoading(btnEnregistrerUrgentClasseurRetraitDossier);
    }

    public boolean isCourrierRetraitDossier() {
        return messageCourrierRetraitDossier.isDisplayed() && messageCourrierRetraitDossier.getText().contains(TestData.DEMANDE);
    }

    public boolean isUrgentRetraitDossier() {
        return messageUrgentRetraitDossier.isDisplayed() && messageUrgentRetraitDossier.getText().contains(TestData.DEMANDE);
    }

    public boolean isListeRetraitDossier() {
        return messageListeRetraitDossier.isDisplayed() && messageListeRetraitDossier.getText().contains("La liste a été créée avec succès");
    }

    public boolean isUrgentClasseurRetraitDossier() {
        return messageUrgentClasseurRetraitDossier.isDisplayed() && messageUrgentClasseurRetraitDossier.getText().contains(TestData.DEMANDE);
    }

    public void clickOngletCourrierRetraitDossier() {
        ActionUtils.clickAndLoading(ongletCourrierRetraitDossier);
    }

    public void clickOngletUrgentRetraitDossier() {
        ActionUtils.clickAndLoading(ongletUrgentRetraitDossier);
    }

    public void clickOngletListeRetraitDossier() {
        ActionUtils.clickAndLoading(ongletListeRetraitDossier);
    }

    public void clickOngletUrgentClasseurRetraitDossier() {
        ActionUtils.clickAndLoading(ongletUrgentClasseurRetraitDossier);
    }

    public void clickTableAccordionDossierJournalier() {
        ActionUtils.clickAndLoading(openAccordionJournalier);
    }

    public void clickTableAccordionDossierUrgent() {
        ActionUtils.clickAndLoading(openAccordionUrgent);
    }

    public void clickTableAccordionDossierListe() {
        ActionUtils.clickAndLoading(openAccordionListe);
    }

    public void traiterListeDossier() {
        TableUtils.clickElementWithClassColumnIsPresent("tableListingId", "Test", "Traiter la liste");
    }

    public void clickbtnImprimerListeJournalierRetraitDossier() {
        ActionUtils.clickAndLoading(btnImprimerListJournalierRetrait);
    }

    public void clickbtnImprimerListeRetraitDossier() {
        ActionUtils.clickAndLoading(btnImprimerListListeRetrait);
    }

    public void clickbtnImprimerListeUrgentRetraitDossier() {
        ActionUtils.clickAndLoading(btnImprimerListUrgentRetrait);
    }

    public void clickbtnListeImprimeRetraitDossier() {
        ActionUtils.clickAndLoading(btnListeImprimeeListRetrait);
    }

    public void clickbtnSortirDossierRetraitDossier() {
        ActionUtils.clickAndLoading(btnSortirDossierRetrait);
    }

    public void clickBloquer() {
        ActionUtils.clickAndLoading(btnBloquer);
    }

    public void clickDebloquer() {
        ActionUtils.clickAndLoading(btnDebloquer);
    }

    public void selectTypeNumero(String typeNumero) {
        ActionUtils.sendInComboWithoutLoading(selectTypeNumero, typeNumero);
    }

    public void fillNumero(String num) {
        ActionUtils.doJsFill("numeroId", num);
    }

    public void fillDateDebutCreationDocument(String dateDebut) {
        ActionUtils.doJsFill("dateDuId", dateDebut);
    }

    public void fillDateFinCreationDocument(String dateFin) {
        ActionUtils.doJsFill("dateAuId", dateFin);
    }

    public void selectTypeSortie(String typeSortie) {
        ActionUtils.sendInComboWithoutLoading(selectTypeSortieDocument, typeSortie);
    }

    public void clickBtnEmptyTypeSortie() {
        btnEmptyTypeSortieDocument.click();
    }

    public void selectEtatDocument(String etatDocument) {
        selectEtatDocument.clear();
        selectEtatDocument.sendKeys(etatDocument);
        selectEtatDocument.sendKeys(Keys.ARROW_DOWN);
        if ("Imprimé".equals(etatDocument)) {
            selectEtatDocument.sendKeys(Keys.ARROW_DOWN);
        }
        selectEtatDocument.sendKeys(Keys.ENTER);
    }

    public void clickBtnEmptyEtatDocument() {
        btnEmptyEtatDocument.click();
    }

    public void clickBtnRechercherDocument() {
        ActionUtils.clickAndLoading(btnRechercherDocument);
    }

    public boolean isPreviewDisplayed() {
        SeleniumUtils.isLoading();

        Date dateDebut = new Date();

        for (; ; ) {
            Date dateBoucle = new Date();

            long diff = dateBoucle.getTime() - dateDebut.getTime();
            if (diff < 30000) {
                try {
                    previewDocument.isDisplayed();
                    SeleniumUtils.waitForAction(100);
                    return true;
                } catch (NoSuchElementException e) {
                    LOGGER.error(e.getMessage());
                }
            } else {
                return false;
            }
        }
    }

    public void clickBtnPreviewFermer() {
        ActionUtils.clickAndLoading(btnPreviewFermer);
    }

    public boolean isExistOuPreviewDocument(String document, boolean preview) {
        WebElement table = tableImpression;

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + TABLE_DOCUMENT_ID + "']/tbody/tr"));

        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            Boolean x = iterateImpressionColumnTableAndClick(document, preview, rowIndex, totalColumnCount, columnIndex);
            if (x != null) {
                return x;
            }
            rowIndex = rowIndex + 1;
        }
        return false;
    }

    private Boolean iterateImpressionColumnTableAndClick(String document, boolean preview, int rowIndex, List<WebElement> totalColumnCount, int columnIndex) {
        for (WebElement colElement : totalColumnCount) {
            if (colElement.getText().contains(document)) {
                String pathLink = "//table/tbody/tr[" + rowIndex + "]/td[4]/a[1]";

                WebElement clickableLink = driver.findElement(By.xpath(pathLink));

                if (clickableLink.isDisplayed()) {
                    if (preview) {
                        ActionUtils.clickAndLoading(clickableLink);
                    }
                    return true;
                }
                return false;
            }
            columnIndex = columnIndex + 1;

        }
        return null;
    }

    public String getReferenceDocument(String document) {
        WebElement table = tableImpression;

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + TABLE_DOCUMENT_ID + "']/tbody/tr"));

        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            for (WebElement colElement : totalColumnCount) {
                if (colElement.getText().contains(document)) {
                    String pathLink = "//table/tbody/tr[" + rowIndex + "]/td[4]/a[1]";

                    WebElement clickableLink = driver.findElement(By.xpath(pathLink));

                    String reference = clickableLink.getAttribute("title");
                    String[] ref = reference.split("/data");

                    return "/data" + ref[1];
                }
                columnIndex = columnIndex + 1;

            }
            rowIndex = rowIndex + 1;
        }
        return "";
    }

    public boolean isExistDernierDocument(String document) {
        WebElement table = tableImpression;

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + TABLE_DOCUMENT_ID + "']/tbody/tr"));

        int rowIndexLast = 0;

        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            for (WebElement colElement : totalColumnCount) {
                if (colElement.getText().contains(document)) {
                    String pathLink = "//table/tbody/tr[" + rowIndex + "]/td[4]/a[1]";

                    WebElement clickableLink = driver.findElement(By.xpath(pathLink));

                    if (clickableLink.isDisplayed()) {
                        rowIndexLast = rowIndex;
                    }
                }
                columnIndex = columnIndex + 1;

            }
            rowIndex = rowIndex + 1;
        }

        return rowIndexLast == rowIndex - 1;
    }

    public void clickPreviewDernierDocument(String document) {
        WebElement table = tableImpression;

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + TABLE_DOCUMENT_ID + "']/tbody/tr"));

        int rowIndexLast = 0;
        WebElement clickableLastLink = null;

        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            for (WebElement colElement : totalColumnCount) {
                if (colElement.getText().contains(document)) {
                    String pathLink = "//table/tbody/tr[" + rowIndex + "]/td[4]/a[1]";

                    WebElement clickableLink = driver.findElement(By.xpath(pathLink));

                    rowIndexLast = rowIndex;
                    clickableLastLink = clickableLink;
                }
                columnIndex = columnIndex + 1;

            }
            rowIndex = rowIndex + 1;
        }
        if (rowIndexLast == rowIndex - 1 && clickableLastLink != null) {
            ActionUtils.clickAndLoading(clickableLastLink);
        }
    }

    public void clickSearchResultCourrierIn(String content) {
        String contenuresult = DaoFunctionality.getNascaDao().getDossierNumber(content);

        WebElement table = tableresultcourrier;

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + TABLE_DOCUMENT_ID + "']/tbody/tr"));

        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            for (WebElement colElement : totalColumnCount) {
                String colElemwthspace = SeleniumUtils.deleteFormat(colElement.getText());
                if (colElemwthspace.contains(contenuresult)) {
                    return;
                }
                columnIndex = columnIndex + 1;

            }
            rowIndex = rowIndex + 1;
        }
    }

    public boolean clickSearchResultCourrierIn(String objet, String etat) {
        int saut = 0;
        int indiceRechercheEtat = 0;
        switch (etat) {
            case "Entrés":
                saut = 1;
                break;
            case "Encodés":
                saut = 2;
                break;
            default:
                break;
        }

        WebElement table = tableresultstat;

        String tableId = "tableStatObjets";

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + tableId + "']/tbody/tr"));

        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;

            boolean typeIsFind = false;
            for (WebElement colElement : totalColumnCount) {
                if (columnIndex == 1 && colElement.getText().contains(objet)) {
                    typeIsFind = true;
                }
                if (typeIsFind && colElement.getText().equals(RESULTAT_STATISTIQUE_ATTENDU) && indiceRechercheEtat == saut) {
                    return true;
                }
                if (typeIsFind) {
                    indiceRechercheEtat++;
                }

                columnIndex = columnIndex + 1;
            }
            rowIndex = rowIndex + 1;
        }
        return false;
    }

    public void clickTableBtnEditCourrier(String content) {
        String contenuresult = DaoFunctionality.getNascaDao().getDossierNumber(content);

        WebElement table = tableresultcourrier;

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + TABLE_DOCUMENT_ID + "']/tbody/tr"));

        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            for (WebElement colElement : totalColumnCount) {
                String colElemwthspace = SeleniumUtils.deleteFormat(colElement.getText());

                if (colElemwthspace.contains(contenuresult)) {
                    for (WebElement etat : totalColumnCount) {
                        String pathetat = "//table/tbody/tr[" + rowIndex + "]/td[11]";
                        etat = driver.findElement(By.xpath(pathetat));
                        if (!etat.getText().contains(TestData.ETAT_TRAITE)) {
                            for (WebElement clickable : totalColumnCount) {
                                String pathimg = "//table/tbody/tr[" + rowIndex + "]/td[15]/a[1]";

                                clickable = driver.findElement(By.xpath(pathimg));

                                if (clickable.isDisplayed()) {
                                    ActionUtils.clickAndLoading(clickable);
                                    return;
                                }
                                columnIndex = columnIndex + 1;
                            }
                            columnIndex = columnIndex + 1;
                        }
                    }
                }
            }
            rowIndex = rowIndex + 1;
        }
    }

    public void clickTableRetraitDossierTable(String content) {
        WebElement table = tableResultRetraitDossierConsult;

        String tableId = TABLE_DOCUMENT_ID;

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + tableId + "']/tbody/tr"));

        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            for (WebElement colElement : totalColumnCount) {
                String colElemwthspace = SeleniumUtils.deleteFormat(colElement.getText());
                if (colElemwthspace.contains(content)) {
                    String line = "//*[@id='" + tableId + "']/tbody/tr[" + rowIndex + "]/td[1]/input";

                    WebElement clickable = driver.findElement(By.xpath(line));
                    clickable.click();
                    return;
                }
                columnIndex = columnIndex + 1;
            }
            rowIndex = rowIndex + 1;
        }
    }

    public boolean isDossierRentreOuRetrait(String content, boolean rentre) {
        WebElement table = tableResultRetraitDossierConsult;

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + TABLE_DOCUMENT_ID + "']/tbody/tr"));

        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            for (WebElement colElement : totalColumnCount) {
                String colElemwthspace = SeleniumUtils.deleteFormat(colElement.getText());
                if (colElemwthspace.contains(content)) {
                    return !rentre;
                }
                columnIndex = columnIndex + 1;
            }
            rowIndex = rowIndex + 1;
        }
        return rentre;
    }

    public WebDriver getPreviewDocumentAttributSrc() {
        return driver;
    }

    public void fillDateCreation(String date) {
        ActionUtils.doJsFill("dateCreationFrom", date);
    }

    public void selectDepartementCodeBarreGed(String departement) {
        ActionUtils.sendInComboWithoutLoading(selectDepartementCodeBarreGed, departement);
    }

    public void selectTypeCodeBarreGed(String type) {
        ActionUtils.sendInCombo(selectTypeCodeBarreGed, type);
    }

    public void selectSousTypeCodeBarreGed(String sousType) {
        ActionUtils.sendInCombo(selectSousTypeCodeBarreGed, sousType);
    }

    public void selectDocumentCodeBarreGed(String document) {
        ActionUtils.sendInComboWithoutLoading(selectDocumentCodeBarreGed, document);
    }

    public void selectSensCodeBarreGed(String sens) {
        ActionUtils.sendInComboWithoutLoading(selectSensCodeBarreGed, sens);
    }

    public void fillDateDocument() {
        ActionUtils.doJsFill("generationCodebarreForm_gedDemande_dateDocument", DateUtils.getDateToday());
    }

    public void fillNumeroClient(String numClient) {
        ActionUtils.doJsFill("numero", numClient);
    }

    public void clickBtnAjouterCodeBarre() {
        ActionUtils.clickAndLoading(btnAjouterCodeBarreGed);
    }

    public boolean checkDemandeCodeBarre(String numeroClient) {
        return TableUtils.isElementPresent(TABLE_DEMANDES_ID, numeroClient);
    }

    public boolean checkTableDemandeCodeBarreVide() {
        return TableUtils.isTableVide(TABLE_DEMANDES_ID);
    }

    public void clickMenuActionReactiverCodeBarreGED() {
        ActionUtils.moveToAndClickChild(menuActionCodeBarreGED, menuActionReactiverCodeBarreGED, driver);
    }

    public void clickMenuActionGenererCodeBarreGED() {
        ActionUtils.moveToAndClickChild(menuActionCodeBarreGED, menuActionGenererCodeBarreGED, driver);
    }

    public void checkEtSuppressionCodeBarreGED() {
        TableUtils.selectElement(TABLE_DEMANDES_ID);

        if (btnSupprimerCodeBarreGed.isEnabled()) {
            btnSupprimerCodeBarreGed.click();
            SeleniumUtils.clickConfirmAlert();
        }
    }
}