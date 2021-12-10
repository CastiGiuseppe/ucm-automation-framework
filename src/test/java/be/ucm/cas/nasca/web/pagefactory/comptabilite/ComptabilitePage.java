package be.ucm.cas.nasca.web.pagefactory.comptabilite;

import be.ucm.cas.nasca.web.test.support.ActionUtils;
import be.ucm.cas.nasca.web.test.support.SeleniumUtils;
import be.ucm.cas.nasca.web.test.support.TableUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ComptabilitePage {

    private final WebDriver driver;

    // Menu
    @FindBy(id = "SUI_PAIDiv")
    private static WebElement menuPaiementAAffecter;

    @FindBy(id = "SUI_COD_RAPPDiv")
    private static WebElement menuRapportEtValidation;

    // Grid controle
    @FindBy(id = "confirmerAffectationId")
    private static WebElement btnconfirmerControle;

    @FindBy(id = "fallr-button-button1")
    private static WebElement btnOuiModal;

    @FindBy(id = "_rapportImportCodaForm_compteCas")
    private static WebElement compteRapport;

    @FindBy(xpath = "//*[@id='divTableRapportImportCodaResult_validation']/h3/a")
    private static WebElement ongletValidation;

    @FindBy(id = "gestionPaiementForm_montantDe")
    private static WebElement montantDe;

    @FindBy(id = "gestionPaiementForm_montantA")
    private static WebElement montantA;

    @FindBy(id = "searchBtn")
    private static WebElement buttonSearch;

    // selection identite
    @FindBy(id = "buttonSelect_typeIdentiteListId")
    private static WebElement btnOpenSelectRechercheRype;

    @FindBy(xpath = ("//a[contains(text(),'Personne morale')]"))
    private static WebElement rechercheSelectPm;

    @FindBy(xpath = "//table[@id='DataTables_Table_0']/tbody/tr/td[1]")
    private static WebElement tableresultidentite;

    @FindBy(id = "btnSearch")
    private static WebElement btnSearchIdenteePayeur;

    // affectation sur créances
    @FindBy(id = "creancesTable")
    private static WebElement tablecreances;

    public ComptabilitePage(WebDriver drv) {
        this.driver = drv;
        PageFactory.initElements(driver, this);
    }

    public void clickMenuRapportEtValidation() {
        ActionUtils.clickAndLoading(menuRapportEtValidation);
    }

    public void clickBtnConfirmerControle() {
        btnconfirmerControle.click();
        SeleniumUtils.waitForActionCommon();
    }

    public boolean btnConfirmerControleEnabled() {
        return !btnconfirmerControle.isEnabled();
    }

    public void clickMenuPaiementAAffecter() {
        ActionUtils.clickAndLoading(menuPaiementAAffecter);
    }

    public void fillDateComptaDeCustom(String date) {
        ActionUtils.doJsFill("dateComptableDe", date);
    }

    public void fillDateComptaACustom(String date) {
        ActionUtils.doJsFill("dateComptableA", date);
    }

    public void fillMontantDe() {
        ActionUtils.sendInTextField(montantDe, "0");
    }

    public void fillMontantA() {
        ActionUtils.sendInTextField(montantA, "999999999");
    }

    public void clickBtnSearch() {
        ActionUtils.clickAndLoading(buttonSearch);
    }

    public void clickResultSearchIdentite() {
        tableresultidentite.click();
    }

    public void doFillIdentitePayeur(String nissorbce) {
        if (nissorbce.length() >= 11) {
            ActionUtils.doJsFill("ilFilNissId", nissorbce);
        } else {
            ActionUtils.clickAndLoading(btnOpenSelectRechercheRype);
            ActionUtils.clickAndLoading(rechercheSelectPm);

            ActionUtils.doJsFill("ilFilBceId", nissorbce);
        }
        ActionUtils.clickAndLoading(btnSearchIdenteePayeur);
    }

    public void clickAffectPaiementTable() {
        TableUtils.clickEditElementIsPresent("paiementsEnAttenteTable", "SSI");
    }

    public boolean isTableCreancesPPTrimestreExist(String trimestre) {
        return TableUtils.isElementPresent("creancesTable", trimestre);
    }

    public void clickTableCreancesPP(String trimestre, boolean cotisation, boolean majoration, String montantAAffecter) {
        WebElement table = tablecreances;

        String tableId = "creancesTable";

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + tableId + "']/tbody/tr"));

        int rowIndex = 1;

        while (rowIndex <= totalRowCount.size()) {
            WebElement periode = driver.findElement(By.xpath("//*[@id='" + tableId + "']/tbody/tr[" + rowIndex + "]/td[2]"));
            WebElement creance = driver.findElement(By.xpath("//*[@id='" + tableId + "']/tbody/tr[" + rowIndex + "]/td[3]"));
            WebElement solde = driver.findElement(By.xpath("//*[@id='" + tableId + "']/tbody/tr[" + rowIndex + "]/td[9]"));
            WebElement row = driver.findElement(By.xpath("//*[@id='" + tableId + "']/tbody/tr[" + rowIndex + "]"));

            if (trimestre == null || checkTrimestre(trimestre, periode) &&
                    checkCreance(majoration, creance, TestData.MAJORATION) || checkCreance(cotisation, creance, TestData.COTISATION)) {
                String montant;
                if (montantAAffecter == null) {
                    montant = solde.getText();
                } else {
                    montant = montantAAffecter;
                }
                ActionUtils.fillIfDisplayed(solde, row, rowIndex, montant);
            }
            rowIndex = rowIndex + 1;
        }
    }

    private boolean checkTrimestre(String trimestre, WebElement periode) {
        return trimestre != null && periode.getText().equals(trimestre);
    }

    private boolean checkCreance(boolean booleanCreance, WebElement creance, String valeurCreance) {
        return creance.getText().contains(valeurCreance) && booleanCreance;
    }

    public void clickBtnOuiModal() {
        ActionUtils.clickAndLoading(btnOuiModal);
    }

    public void clickTableControlePaiements() {
        TableUtils.clickElementIsPresent("tableRapportImportCodaResult_validation", "SSI (00068)");
    }

    public void fillDateExtraitRapport(String date) {
        ActionUtils.doJsFill("dateCoda", date);
    }

    public void selectCompteRapport(String compte) {
        ActionUtils.sendInComboWithoutLoading(compteRapport, compte);
    }

    public void selectOngletValidation() {
        ongletValidation.click();
    }
}