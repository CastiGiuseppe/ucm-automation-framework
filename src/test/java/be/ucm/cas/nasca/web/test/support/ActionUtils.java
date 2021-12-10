package be.ucm.cas.nasca.web.test.support;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;

public final class ActionUtils {

    public ActionUtils() {
    }

    /*
      FILLING ACTIONS
     */

    /**
     * Vide la combobox puis la remplit, envoi fleche du bas puis ENTER et vérifie la présence du loading
     *
     * @param element WebElement
     * @param text    String
     */
    public static void sendInCombo(WebElement element, String text) {
        sendInTextField(element, text);
        element.sendKeys(Keys.ARROW_DOWN);
        element.sendKeys(Keys.ENTER);
        SeleniumUtils.isLoading();
    }

    public static void sendThirdInComboWithoutLoading(WebElement element , String text){
        sendInTextField(element, text);
        element.sendKeys(Keys.ARROW_DOWN);
        element.sendKeys(Keys.ARROW_DOWN);
        element.sendKeys(Keys.ARROW_DOWN);
        element.sendKeys(Keys.ENTER);
    }

    /**
     * Vide la combobox puis la remplit, envoi fleche du bas puis ENTER
     *
     * @param element WebElement
     * @param text    String
     */
    public static void sendInComboWithoutLoading(WebElement element, String text) {
        sendInTextField(element, text);
        element.sendKeys(Keys.ARROW_DOWN);
        element.sendKeys(Keys.ENTER);
    }

    public static void selectInMultipleSelect(WebElement element, String values) {
        Select select = new Select(element);
        select.selectByVisibleText(values);
    }

    /**
     * Vide le textfield puis le remplit
     *
     * @param element WebElement
     * @param text    String
     */
    public static void sendInTextField(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Clique et vérifie la présence du loading
     *
     * @param element WebElement
     */
    public static void clickAndLoading(WebElement element) {
        element.click();
        SeleniumUtils.isLoading();
    }

    /**
     * Clique et wait action long
     *
     * @param element WebElement
     */
    public static void clickAndWaitActionLong(WebElement element) {
        element.click();
        SeleniumUtils.waitForActionLong();
    }

    /**
     * Remplit l'élément via JavaScript
     *
     * @param elementId String
     * @param value     String
     */
    public static void doJsFill(String elementId, String value) {
        TestBase.getJsExec().executeScript(
                "document.getElementById('" + elementId + "').value='"
                        + value + "';");
    }

    /**
     * Coche la checkbox si nécessaire
     *
     * @param check   String
     * @param element WebElement
     */
    public static void doCheck(String check, WebElement element) {
        boolean ecran = true;
        if (element.getAttribute("checked") == null) {
            ecran = false;
        }
        if (("true".equals(check) && !ecran) || ("false".equals(check) && ecran)) {
            element.click();
        }
    }

    /**
     * Remplit le field s'il est affiché
     *
     * @param clickable        WebElement
     * @param row              WebElement
     * @param rowIndex         int
     * @param montantAAffecter String
     */
    public static void fillIfDisplayed(WebElement clickable, WebElement row, int rowIndex, String montantAAffecter) {
        if (!row.getAttribute("class").contains("selected")) {
            clickAndLoading(clickable);
        }

        String lineid = "entitMontantAffecte" + (rowIndex - 1);
        WebElement mnt = TestBase.getDriver().findElement(By.id(lineid));

        mnt.clear();
        mnt.click();
        mnt.sendKeys(montantAAffecter);
        mnt.sendKeys(Keys.RETURN);
    }

    /**
     * Ouvre le menu parent @parentMenu pour cliquer sur le menu enfant @childMenu
     *
     * @param parentMenu WebElement
     * @param childMenu  WebElement
     * @param driver     WebDriver
     */
    public static void clickChildMenu(WebElement parentMenu, WebElement childMenu, WebDriver driver) {
        Actions action = new Actions(driver);
        action.doubleClick(parentMenu).perform();
        SeleniumUtils.waitForActionCommon();
        clickAndLoading(childMenu);
    }

    /**
     * Deplace la souris jusqu'à l'élèment @parentMenu et clique sur son enfant @childMenu
     *
     * @param parentMenu WebElement
     * @param childMenu  WebElement
     * @param driver     Driver
     */
    public static void moveToAndClickChild(WebElement parentMenu, WebElement childMenu, WebDriver driver) {
        Actions actions = new Actions(driver);
        actions.moveToElement(parentMenu).perform();
        SeleniumUtils.waitForActionCommon();
        clickAndLoading(childMenu);
    }

    /**
     * Se déplace vers le parent @parentMenu click et maintient sur l'enfant @parentMenu2 et puis clique sur l'enfant de celui ci @childMenu
     *
     * @param parentMenu  WebElement
     * @param parentMenu2 WebElement
     * @param childMenu   WebElement
     * @param driver      Driver
     */
    public static void moveToClickHoldAndClickChild(WebElement parentMenu, WebElement parentMenu2, WebElement childMenu, WebDriver driver) {
        Actions action = new Actions(driver);
        action.moveToElement(parentMenu).perform();
        SeleniumUtils.waitForActionCommon();
        action.clickAndHold(parentMenu2).perform();
        SeleniumUtils.waitForActionCommon();
        clickAndLoading(childMenu);
    }

    /**
     * Se déplace vers le parent @parentMenu click et maintient sur l'enfant @parentMenu2 et puis clique sur l'enfant de celui ci @childMenu
     *
     * @param parentMenu  WebElement
     * @param parentMenu2 WebElement
     * @param childMenu   WebElement
     * @param driver      Driver
     */
    public static void moveToElementAndClickChild(WebElement parentMenu, WebElement parentMenu2, WebElement childMenu, WebDriver driver) {
        Actions action = new Actions(driver);
        action.moveToElement(parentMenu).perform();
        SeleniumUtils.waitForActionCommon();
        action.moveToElement(parentMenu2).perform();
        SeleniumUtils.waitForActionCommon();
        clickAndLoading(childMenu);
    }

    /**
     * Ouvre le menu parent @parentMenu puis son enfant @parentMenu2 et clique sur l'enfant de celui ci @childMenu
     *
     * @param parentMenu  WebElement
     * @param parentMenu2 WebElement
     * @param childMenu   WebElement
     * @param driver      WebDriver
     */
    public static void clickChildFromChildMenu(WebElement parentMenu, WebElement parentMenu2, WebElement childMenu, WebDriver driver) {
        Actions action = new Actions(driver);
        action.doubleClick(parentMenu).perform();
        SeleniumUtils.waitForActionCommon();
        action.doubleClick(parentMenu2).perform();
        SeleniumUtils.waitForActionCommon();
        clickAndLoading(childMenu);
    }

    /**
     * Ouvre dans l'ordre les menu parents @parentMenu @parentMenu2 @parentMenu3 puis clique sur @childMenu
     *
     * @param parentMenu  WebElement
     * @param parentMenu2 WebElement
     * @param parentMenu3 WebElement
     * @param childMenu   WebElement
     * @param driver      WebDriver
     */
    public static void clickChildFromThirdChildMenu(WebElement parentMenu, WebElement parentMenu2, WebElement parentMenu3, WebElement childMenu, WebDriver driver) {
        Actions action = new Actions(driver);
        action.doubleClick(parentMenu).perform();
        SeleniumUtils.waitForActionCommon();
        action.doubleClick(parentMenu2).perform();
        SeleniumUtils.waitForActionCommon();
        action.doubleClick(parentMenu3).perform();
        SeleniumUtils.waitForActionCommon();
        childMenu.click();
        clickAndLoading(childMenu);
    }

    /*
      TABLE ACTIONS
     */

    /**
     * Itère sur la table passé en argument et click sur l'élément contenuresult
     *
     * @param element       WebElement
     * @param elementId     String
     * @param contenuresult String
     */
    public static void iterateToTable(WebElement element, String elementId, String contenuresult) {
        List<WebElement> totalRowCount = element.findElements(By.xpath("//*[@id='" + elementId + "']/tbody/tr"));

        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            for (WebElement colElement : totalColumnCount) {
                String colElemwthspace = SeleniumUtils.deleteFormat(colElement.getText());
                if (colElemwthspace.contains(contenuresult)) {
                    clickAndLoading(colElement);
                    return;
                }
                columnIndex = columnIndex + 1;
            }
            rowIndex = rowIndex + 1;
        }
    }


    /**
     * Itère sur la table passé en argument et retourne un boolean
     *
     * @param table           WebElement
     * @param tableId         String
     * @param columnIndex1    int
     * @param columnIndex2    int
     * @param columnIndex3    int
     * @param content1        String
     * @param content2        String
     * @param content3        String
     * @return boolean
     */
    public static boolean iterateToTable(WebElement table, String tableId, int columnIndex1, int columnIndex2, int columnIndex3, String content1, String content2, String content3) {
        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + tableId + "']/tbody/tr"));

        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            boolean isContent1 = false;
            boolean isContent2 = false;
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            if (iterateTableColumn(columnIndex1, columnIndex2, columnIndex3, content1, content2, content3, isContent1, isContent2, totalColumnCount, columnIndex)) {
                return true;
            }
            rowIndex = rowIndex + 1;
        }
        return false;
    }

    private static Boolean iterateTableColumn(int columnIndex1, int columnIndex2, int columnIndex3, String content1, String content2, String content3, boolean isContent1, boolean isContent2, List<WebElement> totalColumnCount, int columnIndex) {
        for (WebElement colElement : totalColumnCount) {
            if (columnIndex == columnIndex1) {
                if (colElement.getText().equals(content1)) {
                    isContent1 = true;
                } else {
                    break;
                }
            }
            if (columnIndex == columnIndex2 && isContent1) {
                if (colElement.getText().equals(content2)) {
                    isContent2 = true;
                } else {
                    break;
                }
            } else {
                if (columnIndex == columnIndex3 && isContent1 && isContent2 && colElement.getText().equals(content3)) {
                    return true;
                }
            }
            columnIndex = columnIndex + 1;
        }
        return false;
    }

    /*
     REFRESH ACTIONS
     */

    /**
     * Force le rafraichissement de l'élément
     *
     * @param element WebElement
     */
    public static void refreshElementJS(WebElement element) {
        TestBase.getJsExec().executeScript("$(arguments[0]).change(); return true;", element);
        SeleniumUtils.isLoading();
    }

    /**
     * Exécute script
     *
     * @param script String
     */
    public static void executeJS(String script) {
        TestBase.getJsExec().executeScript(script);
        SeleniumUtils.isLoading();
    }

    public static int executeJSAndReturnInt(String script) {
        int sortie = Integer.valueOf(TestBase.getJsExec().executeScript(script).toString());
        SeleniumUtils.isLoading();
        return sortie;
    }

    public static void doChangeStylenFill(String element, String choix) {
        TestBase.getJsExec().executeScript("document.getElementById('" + element + "').style.display='inline-block'");
        $("#" + element).selectOptionByValue(choix);
    }



    //Recherche le contenu de la table, et retourne le contenu de la colonne d'index columnIndexSortie.
    //Compare les éléments contenu dans la colonne d'index columnIndex à la valeur de contentColumnIndex
    //Compare 1 éléments


}

