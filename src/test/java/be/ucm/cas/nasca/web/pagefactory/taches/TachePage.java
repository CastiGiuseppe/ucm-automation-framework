package be.ucm.cas.nasca.web.pagefactory.taches;

import be.ucm.cas.nasca.web.test.support.ActionUtils;
import be.ucm.cas.nasca.web.test.support.SeleniumUtils;
import be.ucm.cas.nasca.web.test.support.TableUtils;
import be.ucm.cas.nasca.web.test.support.TestBase;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TachePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    private static final String TABLE_TACHES_ID = "table-taches";

    private final WebDriver driver;

    @FindBy(id = "_formFiltresTaches_filtre_typeTache")
    private static WebElement selectTypeTache;

    @FindBy(id = "searchTacheBtn")
    private static WebElement btnSearchTache;

    @FindBy(id = "resetBtn")
    private static WebElement btnResetBtn;

    @FindBy(xpath = "//div[2]/div[3]/button[1]")
    private static WebElement btnFermerNotificationEnrolement;

    @FindBy(id = "_actionAvis")
    private static WebElement selectActionAvis;

    @FindBy(xpath = "//html/body/div[1]/rightcontent/div[1]/div/ul/li[1]")
    private static WebElement actionNouveau;

    @FindBy(xpath = "//html/body/div[4]/div[1]/ul/li[2]")
    private static WebElement actionNouveauSociete;

    @FindBy(xpath = "//html/body/div[4]/div[3]/div/ul/li/a")
    private static WebElement actionNouveauSocieteAffiliationTransfert;

    @FindBy(id = "_formFiltresTaches_filtre_statutTache")
    private static WebElement selectStatutTache;

    @FindBy(id = "_formFiltresTaches_filtre_typeWorkflow")
    private static WebElement selectTypeWorkflowTache;

    @FindBy(id = "groupeFromId")
    private static WebElement groupeTacheFrom;

    @FindBy(id = "groupeToId")
    private static WebElement groupeTacheTo;

    @FindBy(id = "sousGroupeFromId")
    private static WebElement sousGroupeTacheFrom;

    @FindBy(id = "sousGroupeToId")
    private static WebElement sousGroupeTacheTo;

    @FindBy(id = "_formFiltresTaches_filtre_attribuePar")
    private static WebElement selectAttribueeParTache;

    @FindBy(id = "formFiltresTaches_filtre_urgent")
    private static WebElement radioUrgenteTache;

    @FindBy(id = "tacheInterne")
    private static WebElement radioTacheInterne;

    @FindBy(id = "tacheExterne")
    private static WebElement radioTacheExterne;

    @FindBy(id = "editTacheForm_urgent")
    private static WebElement checkRadioUrgente;

    @FindBy(xpath = "//form/div[5]/div/p/span/input")
    private static WebElement selectAttribueATache;

    @FindBy(id = "rolesPossibletsms")
    private static WebElement selectRolesPossiblesTache;

    @FindBy(id = "rolesPossible")
    private static WebElement selectCandidatsTache;

    @FindBy(xpath = "//div[3]/div/button[2]")
    private static WebElement btnEnregistrerEditTache;

    @FindBy(xpath = "//div[2]/div[1]/img")
    private static WebElement btnAddRoleTache;

    @FindBy(xpath = "//div[2]/div[3]/img")
    private static WebElement btnDeleteRoleTache;

    @FindBy(id = "buttonEmpty_formFiltresTaches_filtre_typeTache")
    private static WebElement btnDeleteTypeTache;

    @FindBy(id = "buttonEmpty_formFiltresTaches_filtre_attribuePar")
    private static WebElement btnDeleteAttribueePar;

    public TachePage(WebDriver drv) {
        this.driver = drv;
        PageFactory.initElements(driver, this);
    }

    public boolean isTacheExist(String arguments) {
        return TableUtils.isElementPresent(TABLE_TACHES_ID, arguments);
    }

    public void fillNumero(String numero) {
        ActionUtils.doJsFill("formFiltresTaches_filtre_dossier", numero);
    }

    public void selectTypetache(String typeTache) {
        ActionUtils.sendInComboWithoutLoading(selectTypeTache, typeTache);
    }

    public void selectActionAvis(String action) {
        ActionUtils.sendInComboWithoutLoading(selectActionAvis, action);
    }

    public void selectStatutTache(String statut) {
        ActionUtils.sendInComboWithoutLoading(selectStatutTache, statut);
    }

    public void selectTypeWorkflow(String typeWorkflow) {
        ActionUtils.sendInComboWithoutLoading(selectTypeWorkflowTache, typeWorkflow);
    }

    public void fillGroupeTache(String groupeTache) {
        groupeTacheFrom.sendKeys(groupeTache);
        groupeTacheTo.sendKeys(groupeTache);
    }

    public void fillSousGroupeTache(String sousGroupeTache) {
        sousGroupeTacheFrom.sendKeys(sousGroupeTache);
        sousGroupeTacheTo.sendKeys(sousGroupeTache);
    }

    public void selectAttribueeParTache(String attribueePar) {
        ActionUtils.sendInComboWithoutLoading(selectAttribueeParTache, attribueePar);
    }

    public void checkRadioUrgent() {
        radioUrgenteTache.click();
    }

    public void deCheckRadioTacheInterne() {
        radioTacheInterne.click();
    }

    public void deCheckRadioTacheExterne() {
        radioTacheExterne.click();
    }

    public void clickBtnSearchTache() {
        ActionUtils.clickAndLoading(btnSearchTache);
    }

    public void clickBtnResetTache() {
        ActionUtils.clickAndLoading(btnResetBtn);
    }

    public boolean isEcranTache() {
        return driver.findElement(By.id(TABLE_TACHES_ID)).isDisplayed();
    }

    public void clickTraiterTache(String element) {
        parsingTableauTache(element, "treatTask");
    }

    public void clickEditerTache(String element) {
        parsingTableauTache(element, "editTask");
    }

    private void parsingTableauTache(String element, String type) {
        WebElement table = driver.findElement(By.id(TABLE_TACHES_ID));

        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + TABLE_TACHES_ID + "']/tbody/tr"));

        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            for (WebElement colElement : totalColumnCount) {
                String colElemconvert = SeleniumUtils.deleteFormat(colElement.getText());
                clickElementTableau(element, type, rowIndex, totalColumnCount, colElemconvert);
            }
            rowIndex = rowIndex + 1;
        }
    }

    private void clickElementTableau(String element, String type, int rowIndex, List<WebElement> totalColumnCount, String colElemconvert) {
        if (colElemconvert.contains(SeleniumUtils.deleteFormat(element))) {
            for (WebElement clickable : totalColumnCount) {
                String pathimg = "//table/tbody/tr[" + rowIndex + "]/*/*[@id='" + type + "']";

                clickable = driver.findElement(By.xpath(pathimg));

                if (clickable.isDisplayed()) {
                    ActionUtils.clickAndLoading(clickable);
                    return;
                }
            }
        }
    }

    public void clickBtnNotificationFermer() {
        ActionUtils.clickAndLoading(btnFermerNotificationEnrolement);
    }

    public void clickNouveauSocieteAffiliationTransfert() {
        ActionUtils.moveToElementAndClickChild(actionNouveau, actionNouveauSociete, actionNouveauSocieteAffiliationTransfert, driver);
    }

    public void fillDateEcheanceTAche(String date) {
        ActionUtils.doJsFill("editTacheForm_dateEcheance", date);
    }

    public void selectAttribueeATache(String attribueeA) {
        ActionUtils.sendInComboWithoutLoading(selectAttribueATache, attribueeA);
    }

    public void clickBtnEnregistrerEditTache() {
        ActionUtils.clickAndLoading(btnEnregistrerEditTache);
    }

    public boolean checkTache(String content) {
        return TableUtils.isElementPresent("table-taches", content);
    }

    public void selectRoleTache(String role) {
        selectRolesPossiblesTache.sendKeys(role);
        selectRolesPossiblesTache.sendKeys(Keys.ENTER);
    }

    public void selectCandidatTache(String role) {
        selectCandidatsTache.sendKeys(role);
        selectCandidatsTache.sendKeys(Keys.ENTER);
    }

    public void clickAddRoleTache() {
        btnAddRoleTache.click();
    }

    public void clickDeleteRoleTache() {
        btnDeleteRoleTache.click();
    }

    public void checkRadioUrgente() {
        checkRadioUrgente.click();
    }

    public Boolean isTacheUrgente() {
        try {
            String tableId = TABLE_TACHES_ID;
            Elements trs = TableUtils.getParsedTable(tableId);
            int trindex = 0;

            for (Element tr : trs) {
                Elements tds = tr.getElementsByTag("td");

                int tdIndex = 1;
                while (tdIndex <= tds.size()) {
                    String rown = "//*[@id='" + tableId + "']/tbody/tr[" + trindex + "]/td[" + tdIndex + "]/img";

                    WebElement row = TestBase.getDriver().findElement(By.xpath(rown));

                    if (tdIndex == 1 && "Urgent".equals(row.getAttribute("title"))) {
                        return true;
                    }
                    tdIndex++;
                }
                trindex++;
            }
            return false;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public void clickBtnDeleteFiltreTache() {
        ActionUtils.clickAndLoading(btnDeleteTypeTache);
        ActionUtils.clickAndLoading(btnDeleteAttribueePar);
    }
}