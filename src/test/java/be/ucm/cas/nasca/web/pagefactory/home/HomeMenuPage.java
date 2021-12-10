package be.ucm.cas.nasca.web.pagefactory.home;

import be.ucm.cas.nasca.web.test.support.ActionUtils;
import be.ucm.cas.nasca.web.test.support.SeleniumUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomeMenuPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    @FindBy(xpath = "//*[@id='header_toolbar']/div[1]/h1/a/img[1]")
    private static WebElement nascaDisponible;

    @FindBy(xpath = ("//li[@id='menu1']/a"))
    private static WebElement ongletTaches;

    @FindBy(xpath = ("//li[@id='menu3']/a"))
    private static WebElement ongletGestionClient;

    @FindBy(xpath = ("//li[@id='menu4']/a"))
    private static WebElement ongletComptabiliteEtFinances;

    @FindBy(xpath = ("//li[@id='menu5']/a"))
    private static WebElement ongletMultiDossiers;

    @FindBy(xpath = ("//li[@id='menu7']/a"))
    private static WebElement ongletAdministration;

    @FindBy(id = "rapidSearchField")
    private static WebElement btnMenuSearch;

    @FindBy(xpath = ("//*[@id='header_toolbar']/div[2]/div[1]/div/div[2]/div[1]/span"))
    private static WebElement menuUser;

    @FindBy(xpath = ("//*[@id='header_toolbar']/div[2]/div[1]/div/div[2]/div[2]/ul/li[1]/a"))
    private static WebElement menuUserPreferences;

    @FindBy(xpath = ("//*[@id='header_toolbar']/div[2]/div[1]/div/div[2]/div[2]/ul/li[2]/a"))
    private static WebElement menuUserDeconnexion;

    @FindBy(id = "_preferencesForm_equipeId")
    private static WebElement selectEquipe;

    @FindBy(id = "preferencesForm_glob_btn_save")
    private static WebElement btnEnregistrerPreferences;

    @FindBy(xpath = "html/body/h1")
    private static WebElement erreurHttp404;

    public HomeMenuPage(WebDriver drv) {
        PageFactory.initElements(drv, this);
    }

    public void clickOngletTaches() {
        ActionUtils.clickAndLoading(ongletTaches);
    }

    public void clickOngletGestionClient() {
        ActionUtils.clickAndLoading(ongletGestionClient);
    }

    public void clickOngletComptabiliteEtFinances() {
        ActionUtils.clickAndLoading(ongletComptabiliteEtFinances);
    }

    public void clickOngletMultiDossiers() {
        ActionUtils.clickAndLoading(ongletMultiDossiers);
    }

    public void clickAdministration() {
        ActionUtils.clickAndLoading(ongletAdministration);
    }

    public void doSearch(String search) {
        ActionUtils.sendInTextField(btnMenuSearch, search);
        btnMenuSearch.sendKeys(Keys.ENTER);
        SeleniumUtils.isLoading();
    }

    public boolean isNascaDisponible() {
        try {
            return nascaDisponible.getAttribute("src").contains("/Nasca-Web/images/LogoNasca-bgBlack.png");
        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public boolean isErreurHttp404() {
        try {
            return erreurHttp404.getText().contains("HTTP 404");
        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public void selectEquipe(String equipe) {
        menuUser.click();
        SeleniumUtils.waitForActionCommon();
        ActionUtils.clickAndLoading(menuUserPreferences);
        ActionUtils.sendInComboWithoutLoading(selectEquipe, equipe);
        ActionUtils.clickAndLoading(btnEnregistrerPreferences);
    }

    public void deconnexion() {
        menuUser.click();
        SeleniumUtils.waitForActionCommon();
        ActionUtils.clickAndLoading(menuUserDeconnexion);
    }
}