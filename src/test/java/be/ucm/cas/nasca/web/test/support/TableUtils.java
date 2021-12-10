package be.ucm.cas.nasca.web.test.support;

import bsh.StringUtil;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.List;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class TableUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    public TableUtils(WebDriver driver) {
        WebDriverRunner.setWebDriver(driver);
    }

    /**
     * Vérifie si le tableau @tableId contient bien la mention TestData.AUCUN_ELEMENT_A_AFFICHER
     *
     * @param tableid String
     * @return boolean
     */
    public static boolean isTableVide(String tableid) {
        try {
            Assert.assertTrue($("#" + tableid)
                    .find(byText(TestData.AUCUN_ELEMENT_A_AFFICHER))
                    .exists());
            LOGGER.debug("Le tableau est vide");
            return true;
        } catch (AssertionError e) {
            LOGGER.debug("Le tableau n'est pas vide");
            return false;
        }
    }

    /**
     * Vérifie si la taille du tableau @tableId contient 0 élements
     *
     * @param tableid String
     * @return boolean
     */
    public static boolean isTableZeroElement(String tableid) {
        try {
            Assert.assertTrue($("#" + tableid).findElementsByXPath("/tbody").size() == 0);
            LOGGER.debug("Le tableau est vide");
            return true;
        } catch (AssertionError e) {
            LOGGER.debug("Le tableau n'est pas vide");
            return false;
        }
    }

    /**
     * Vérifie si un élément @élément se trouve bien dans le tableau @tableId
     *
     * @param tableId String
     * @param element String
     * @return boolean
     */
    public static boolean isElementPresent(String tableId, String element) {
        try {
            Assert.assertTrue($("#" + tableId)
                    .should(Condition.exist)
                    .find(byText(element))
                    .exists());
            LOGGER.debug("L'élément est présent dans le tableau");
            return true;
        } catch (AssertionError e) {
            LOGGER.debug("L'élément ne se trouve pas dans le tableau");
            return false;
        }
    }

    /**
     * Vérifie si l'élément est présent (grace à isElementPresent), s'il est présent l'action est effectuée
     *
     * @param tableId String
     * @param element String (Si l'lément est un NISS ou un BCE il est impératif de le formater avant de le passer à la fonction)
     */
    public static void clickElementIsPresent(String tableId, String element) {
        Assert.assertTrue(isElementPresent(tableId, element));
        $("#" + tableId)
                .should(Condition.exist)
                .find(byText(element))
                .hover()
                .click();
        LOGGER.debug("Click on element : " + element);
    }
    public static void clickFirstRow(String tableId) {
        $("#" + tableId)
                .findElementByXPath("//tbody/tr[1]")
                .click();
        SeleniumUtils.isLoading();
        LOGGER.debug("Click on first row");
    }

    /**
     * Clique sur le bouton d'édition de la ligne si l'élément se retrouve bien dans le tableau
     *
     * @param tableId String
     * @param element String
     */
    public static void clickEditElement(String tableId, String element) {
        Assert.assertTrue(isElementPresent(tableId, element));
        $("#" + tableId)
                .should(Condition.exist)
                .$(byText(element))
                .parent()
                .find(by("src", "/Nasca-Web/images/icons/16x16/pencil.png"))
                .hover()
                .click();
        SeleniumUtils.isLoading();
        LOGGER.debug("Open edition on element : " + element);
    }

    /**
     * Clique sur le bouton de visualisation de la ligne si l'élément se retrouve bien dans le tableau
     *
     * @param tableId String
     * @param element String
     */
    public static void clickViewElement(String tableId, String element) {
        Assert.assertTrue(isElementPresent(tableId, element));
        $("#" + tableId)
                .should(Condition.exist)
                .$(byText(element))
                .parent()
                .find(by("src", "/Nasca-Web/images/icons/16x16/bended-arrow-down.png"))
                .click();
        SeleniumUtils.isLoading();
        LOGGER.debug("Open edition on element : " + element);
    }

    /**
     * Clique sur le bouton d'ajout de la ligne si l'élément se retrouve bien dans le tableau
     *
     * @param tableId String
     * @param element String
     */
    public static void clickEditElementPlus(String tableId, String element) {
        Assert.assertTrue(isElementPresent(tableId, element));
        $("#" + tableId)
                .should(Condition.exist)
                .$(byText(element))
                .parent()
                .find(by("src", "/Nasca-Web/images/basic/add.png"))
                .hover()
                .click();
        SeleniumUtils.isLoading();
        LOGGER.debug("Click on ajout : " + element);
    }

    /**
     * Clique sur le premier bouton d'édition se trouvant dans le tableau
     *
     * @param tableId String
     */
    public static void clickFirstEdit(String tableId) {
        $("#" + tableId)
                .should(Condition.exist)
                .find(by("src", "/Nasca-Web/images/icons/16x16/pencil.png"))
                .hover()
                .click();
        SeleniumUtils.isLoading();
        LOGGER.debug("Open edition on first line");
    }

    public static void clickElementColumnIsPresent(String tableId, String element, String columntoClick, String idspecific) {
        try {
            iterateOnTab(tableId, element, columntoClick, getParsedTable(tableId), idspecific);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void clickEditElementIsPresent(String tableId, String element) {
        if (isElementPresent(tableId, element)) {
            $("#" + tableId)
                    .find(byText(element))
                    .parent()
                    .find(by("src", "/Nasca-Web//images/icons/16x16/pencil2.png"))
                    .click();
            SeleniumUtils.isLoading();
        }
    }

    /**
     * Clique sur la checkbox de la ligne
     *
     * @param tableId String
     */
    public static void selectElement(String tableId) {
        if (!isTableVide(tableId)) {
            $("#" + tableId)
                    .find(by("type", "checkbox"))
                    .click();
        }
    }

    /**
     * Clique sur le bouton contenant le titre passé en paramètre pour la ligne correspondante à l'élément
     *
     * @param tableId String
     * @param element String
     * @param titre   String
     */
    public static void clickElementWithClassColumnIsPresent(String tableId, String element, String titre) {
        $("#" + tableId)
                .find(byText(element))
                .should(Condition.exist)
                .parent()
                .find(byTitle(titre))
                .hover()
                .click();
        SeleniumUtils.isLoading();
    }



    /**
     * Clique sur le bouton contenant le titre passé en paramètre pour la ligne correspondante à l'élément
     *
     * @param tableId String
     * @param titre   String
     */
    public static void clickElementWithClassColumnIsPresent(String tableId, String titre) {
        $("#" + tableId)
                .find(byTitle(titre))
                .hover()
                .click();
        SeleniumUtils.isLoading();
    }

    public static String getTextfromColumnTable(String tableId, String element, String column, boolean lessOneIndex) {
        try {
            Elements trs = getParsedTable(tableId);
            int trindex = 0;
            int lessIndex = 1;
            if (lessOneIndex) {
                trindex = -1;
                lessIndex = 0;
            }
            String rown = iterateTable(tableId, element, column, trs, trindex, lessIndex);
            if (rown != null) {
                return rown;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
        return null;
    }

    private static String iterateTable(String tableId, String element, String column, Elements trs, int trindex, int lessIndex) {
        for (Element tr : trs) {
            Elements tds = tr.getElementsByTag("td");
            for (Element td : tds) {
                if (SeleniumUtils.deleteFormat(td.text()).contains(element)) {
                    String rown = "//*[@id='" + tableId + "']/tbody/tr[" + (trindex - lessIndex) + "]/td[" + column + "]";
                    return TestBase.getDriver().findElement(By.xpath(rown)).getText();
                }
            }
            trindex++;
        }
        return null;
    }

    public static Elements getParsedTable(String tableId) {
        Document doc = Jsoup.parse(TestBase.getDriver().getPageSource(), "", Parser.xmlParser());
        return doc.getElementById(tableId).getElementsByTag("tr");
    }

    public static void clickElementSuppressionEvtChgtMultiple(String xpath, List<String> elements, String columntoClick, String idspecific) {
        try {
            String exlSet;
            Boolean conditionning = null;
            int trindex = 1;
            int tdindex = 1;

            List<WebElement> elemtr;

            List<WebElement> elemtable = TestBase.getDriver().findElements(By.xpath(xpath));

            for (WebElement elemtri : elemtable) {
                elemtr = elemtri.findElements(By.xpath("//tbody/tr"));

                for (int l = 0; l < elemtr.size(); l++) {
                    for (int i = 0; i < elements.size(); i++) {

                        if (conditionning != null && !conditionning) {
                            break;
                        }
                        exlSet = SeleniumUtils.deleteFormat(elements.get(i));
                        switch (i) {
                            case 0:
                                conditionning = checkConditionning(xpath, exlSet, trindex, tdindex);
                                break;
                            case 1:
                                conditionning = checkConditionning(xpath, exlSet, trindex, tdindex);
                                break;
                            case 2:
                                conditionning = checkConditionning(xpath, exlSet, trindex, tdindex);
                                break;
                            case 3:
                                if (checkConditionning(xpath, exlSet, trindex, tdindex)) {
                                    ActionUtils.clickAndLoading(TestBase.getDriver().findElement(By.xpath(xpath + "/tbody/tr[" + (trindex) + "]/td[" + columntoClick + "]/a[" + idspecific + "]")));
                                    return;
                                }
                            default:
                                break;
                        }
                        tdindex++;
                    }
                    conditionning = true;
                    tdindex = 1;
                    trindex++;
                }
                trindex++;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static Boolean checkConditionning(String xpath, String exlSet, int trindex, int tdindex) {
        WebElement row = TestBase.getDriver().findElement(By.xpath(xpath + "/tbody/tr[" + trindex + "]/td[" + tdindex + "]"));
        return checkTableContentCondition(row.getText(), exlSet);
    }

    private static boolean checkTableContentCondition(String text, String exlSet) {
        return !StringUtils.isEmpty(text) && SeleniumUtils.deleteFormat(text).contains(exlSet);
    }

    public static boolean isElementSolidaritePresent(String tableId, String nissorbce, String causeNonSolidaire) {
        try {
            Assert.assertTrue($("#" + tableId)
                    .should(Condition.exist)
                    .find(byText(nissorbce))
                    .parent()
                    .find(byText(causeNonSolidaire == null ? "" : causeNonSolidaire))
                    .exists());
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }

    private static void iterateOnTab(String tableId, String element, String column, Elements trs, String idspecific) {
        int trindex = 0;
        for (Element tr : trs) {
            Elements tds = tr.getElementsByTag("td");

            for (Element td : tds) {
                if (SeleniumUtils.deleteFormat(td.text()).contains(SeleniumUtils.deleteFormat(element))) {
                    String rown = "//*[@id='" + tableId + "']/tbody/tr[" + trindex + "]/td[" + column + "]";
                    String rownEnd = idspecific != null ? "/a[" + idspecific + "]" : "";
                    ActionUtils.clickAndLoading(TestBase.getDriver().findElement(By.xpath(rown + rownEnd)));
                    return;
                }
            }
            trindex++;
        }
    }

    //Recherche dans un tableau et click de la checkbox

    //Recherche le contenu de la table, et retourne le contenu de la colonne d'index columnIndexSortie.
    //Compare les éléments contenu dans la colonne d'index columnIndex à la valeur de contentColumnIndex
    //Compare 1 éléments

    public static String searchIntable(WebElement table, String tableId, int columnIndex1, int columnIndexSortie, String contentColumnIndex1) {
        //recherche dans la table le contenu de chaque case, ligne par ligne, colonne par colonne.
        SeleniumUtils.waitForActionLong();
        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + tableId + "']/tbody/tr"));
        String sortie = null;
        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            //boolean isType = false;
            //boolean isCreance = false;
            //compte le nbre de colonne
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            //x = iterateTableColumn(columnIndex1, columnIndex2, type, creance, isType, isCreance, totalColumnCount, columnIndex);
            sortie = searchInThisRow(columnIndex1, columnIndexSortie , contentColumnIndex1, totalColumnCount, columnIndex);
            if (sortie != null) {
                return sortie;
            }

            rowIndex = rowIndex + 1;

        }
        return null;
    }


    //Recherche le contenu de la table, et retourne le contenu de la colonne d'index columnIndexSortie.
    //Compare les éléments contenu dans la colonne d'index columnIndex à la valeur de contentColumnIndex
    //Compare 2 éléments

    public static String searchIntable(WebElement table, String tableId, int columnIndex1, int columnIndex2, int columnIndexSortie, String contentColumnIndex1, String contentColumnIndex2) {
        //recherche dans la table le contenu de chaque case, ligne par ligne, colonne par colonne.
        SeleniumUtils.waitForActionLong();
        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + tableId + "']/tbody/tr"));
        String sortie = null;
        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            //boolean isType = false;
            //boolean isCreance = false;
            //compte le nbre de colonne
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            //x = iterateTableColumn(columnIndex1, columnIndex2, type, creance, isType, isCreance, totalColumnCount, columnIndex);
            sortie = searchInThisRow(columnIndex1, columnIndex2, columnIndexSortie , contentColumnIndex1, contentColumnIndex2, totalColumnCount, columnIndex);
            if (sortie != null) {
                return sortie;
            }

            rowIndex = rowIndex + 1;

        }
        return null;
    }

    //Recherche le contenu de la table, et retourne le contenu de la colonne d'index columnIndexSortie.
    //Compare les éléments contenu dans la colonne d'index columnIndex à la valeur de contentColumnIndex
    //Compare 3 éléments

    public static String searchIntable(WebElement table, String tableId, int columnIndex1, int columnIndex2, int columnIndex3, int columnIndexSortie, String contentColumnIndex1, String contentColumnIndex2, String contentColumnIndex3) {
        //recherche dans la table le contenu de chaque case, ligne par ligne, colonne par colonne.
        SeleniumUtils.waitForActionLong();
        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + tableId + "']/tbody/tr"));
        String sortie = null;
        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            //boolean isType = false;
            //boolean isCreance = false;
            //compte le nbre de colonne
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            //x = iterateTableColumn(columnIndex1, columnIndex2, type, creance, isType, isCreance, totalColumnCount, columnIndex);
            sortie = searchInThisRow(columnIndex1, columnIndex2, columnIndex3, columnIndexSortie , contentColumnIndex1, contentColumnIndex2, contentColumnIndex3, totalColumnCount, columnIndex);
            if (sortie != null) {
                return sortie;
            }

            rowIndex = rowIndex + 1;

        }
        return null;
    }


    //Compare le contenu des colonnes d'index columnIndex au contentColumnIndex et renvoie le contenu de la colonne d'index columnIndexSortie
    //1 comparaison
    private static String searchInThisRow(int columnIndex1, int columnIndexSortie, String contentColumnIndex1, List<WebElement> totalColumnCount, int columnIndex) {
        String sortie = null;
        Boolean isColumn1 = false;
        //Boolean isColumn2 = false;
        // recherche dans la ligne le contenu des colonnes index 1 et 2, si il trouve, renvoie le contenu de la colonne indexSortie
        for (WebElement colElement : totalColumnCount) {
            if (columnIndex == columnIndexSortie) {
                sortie = colElement.getText();
            }
            if (columnIndex == columnIndex1) {
                if (colElement.getText().equals(contentColumnIndex1)) {
                    isColumn1 = true;
                } else {
                    break;
                }
            }
            /*
            if (columnIndex == columnIndex2 && isColumn1) {
                if (colElement.getText().equals(contentColumnIndex2)) {
                    isColumn2 = true;
                } else {
                    break;
                }
            }
            */
            columnIndex = columnIndex + 1;
        }
        if (isColumn1)
            return sortie;
        return null;
    }

    //Compare le contenu des colonnes d'index columnIndex au contentColumnIndex et renvoie le contenu de la colonne d'index columnIndexSortie
    //2 comparaison
    private static String searchInThisRow(int columnIndex1, int columnIndex2, int columnIndexSortie, String contentColumnIndex1, String contentColumnIndex2, List<WebElement> totalColumnCount, int columnIndex) {
        String sortie = null;
        Boolean isColumn1 = false;
        Boolean isColumn2 = false;
        // recherche dans la ligne le contenu des colonnes index 1 et 2, si il trouve, renvoie le contenu de la colonne indexSortie
        for (WebElement colElement : totalColumnCount) {
            if (columnIndex == columnIndexSortie) {
                sortie = colElement.getText();
            }
            if (columnIndex == columnIndex1) {
                if (colElement.getText().equals(contentColumnIndex1)) {
                    isColumn1 = true;
                } else {
                    break;
                }
            }
            if (columnIndex == columnIndex2 && isColumn1) {
                if (colElement.getText().equals(contentColumnIndex2)) {
                    isColumn2 = true;
                } else {
                    break;
                }
            }
            columnIndex = columnIndex + 1;
        }
        if (isColumn1 && isColumn2)
            return sortie;
        return null;
    }

    //Compare le contenu des colonnes d'index columnIndex au contentColumnIndex et renvoie le contenu de la colonne d'index columnIndexSortie
    //3 comparaison
    private static String searchInThisRow(int columnIndex1, int columnIndex2, int columnIndex3, int columnIndexSortie, String contentColumnIndex1, String contentColumnIndex2, String contentColumnIndex3, List<WebElement> totalColumnCount, int columnIndex) {
        String sortie = null;
        Boolean isColumn1 = false;
        Boolean isColumn2 = false;
        Boolean isColumn3 = false;
        // recherche dans la ligne le contenu des colonnes index 1 et 2, si il trouve, renvoie le contenu de la colonne indexSortie
        for (WebElement colElement : totalColumnCount) {
            if (columnIndex == columnIndexSortie) {
                sortie = colElement.getText();
            }
            if (columnIndex == columnIndex1) {
                if (colElement.getText().equals(contentColumnIndex1)) {
                    isColumn1 = true;
                } else {
                    break;
                }
            }
            if (columnIndex == columnIndex2 && isColumn1) {
                if (colElement.getText().equals(contentColumnIndex2)) {
                    isColumn2 = true;
                } else {
                    break;
                }
            }
            if (columnIndex == columnIndex3 && isColumn1 && isColumn2) {
                if (colElement.getText().equals(contentColumnIndex2)) {
                    isColumn3 = true;
                } else {
                    break;
                }
            }
            columnIndex = columnIndex + 1;
        }
        if (isColumn1 && isColumn2 && isColumn3)
            return sortie;
        return null;
    }



    /*
    * Cherche toute les lignes qui contiennent la valeur contentColumn dans la colonne columnIndex et
    * coche la checkbox au début de toute les lignes
    *
    */
    public static void checkboxInTable(WebElement table, String tableId, int columnIndex1, String contentColumnIndex1) {
        //recherche dans la table le contenu de chaque case, ligne par ligne, colonne par colonne.
        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + tableId + "']/tbody/tr"));
        String sortie =null;
        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            //boolean isType = false;
            //boolean isCreance = false;
            //compte le nbre de colonne
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            //x = iterateTableColumn(columnIndex1, columnIndex2, type, creance, isType, isCreance, totalColumnCount, columnIndex);
            checkboxThisRow(table, columnIndex1, tableId, contentColumnIndex1, totalColumnCount, columnIndex, rowIndex);

            rowIndex = rowIndex + 1;

        }
    }

    /*
    * Cherche toute les lignes qui contiennent les 2 valeur contentColumn dans les 2 colonne columnIndex et
    * coche la checkbox au début de toute les lignes correspondante
    *
    */

    public static void checkboxInTable(WebElement table, String tableId, int columnIndex1, int columnIndex2, String contentColumnIndex1, String contentColumnIndex2) {
        //recherche dans la table le contenu de chaque case, ligne par ligne, colonne par colonne.
        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + tableId + "']/tbody/tr"));
        String sortie =null;
        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            //boolean isType = false;
            //boolean isCreance = false;
            //compte le nbre de colonne
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            //x = iterateTableColumn(columnIndex1, columnIndex2, type, creance, isType, isCreance, totalColumnCount, columnIndex);
            checkboxThisRow(table, columnIndex1, columnIndex2, tableId, contentColumnIndex1, contentColumnIndex2, totalColumnCount, columnIndex, rowIndex);

            rowIndex = rowIndex + 1;

        }
    }

    /*
    * Cherche toute les lignes qui contiennent les 2 valeur contentColumn dans les 2 colonne columnIndex et
    * coche la checkbox au début de toute les lignes
    *
    */

    public static void checkboxInTable(WebElement table, String tableId, int columnIndex1, int columnIndex2, int columnIndex3, String contentColumnIndex1, String contentColumnIndex2,String contentColumnIndex3) {
        //recherche dans la table le contenu de chaque case, ligne par ligne, colonne par colonne.
        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + tableId + "']/tbody/tr"));
        String sortie =null;
        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            //boolean isType = false;
            //boolean isCreance = false;
            //compte le nbre de colonne
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            //x = iterateTableColumn(columnIndex1, columnIndex2, type, creance, isType, isCreance, totalColumnCount, columnIndex);
            checkboxThisRow(table, columnIndex1, columnIndex2, columnIndex3, tableId, contentColumnIndex1, contentColumnIndex2, contentColumnIndex3, totalColumnCount, columnIndex, rowIndex);

            rowIndex = rowIndex + 1;

        }
    }


    //S'arrête après avoir check une seule checkbox dont la ligne a une valeur inférieure à celle donnée.
    //prend 1 element en entree
    public static void checkboxsingleBoxInTableLowerThan(WebElement table, String tableId, int columnIndex1, int contentColumnIndex1) {
        //recherche dans la table le contenu de chaque case, ligne par ligne, colonne par colonne.
        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + tableId + "']/tbody/tr"));
        Boolean found = false;
        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            //boolean isType = false;
            //boolean isCreance = false;
            //compte le nbre de colonne
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            //x = iterateTableColumn(columnIndex1, columnIndex2, type, creance, isType, isCreance, totalColumnCount, columnIndex);
            found = checkboxThisRowLowerThan(table, columnIndex1, tableId, contentColumnIndex1, totalColumnCount, columnIndex, rowIndex);
            if (found)
                return;
            rowIndex = rowIndex + 1;

        }
    }

    //S'arrête après avoir check une seule checkbox.
    //prend 1 element en etnree
    public static void checkboxsingleBoxInTable(WebElement table, String tableId, int columnIndex1, String contentColumnIndex1) {
        //recherche dans la table le contenu de chaque case, ligne par ligne, colonne par colonne.
        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + tableId + "']/tbody/tr"));
        Boolean found = false;
        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            //boolean isType = false;
            //boolean isCreance = false;
            //compte le nbre de colonne
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            //x = iterateTableColumn(columnIndex1, columnIndex2, type, creance, isType, isCreance, totalColumnCount, columnIndex);
            found = checkboxThisRow(table, columnIndex1, tableId, contentColumnIndex1, totalColumnCount, columnIndex, rowIndex);
            if (found)
                return;
            rowIndex = rowIndex + 1;

        }
    }

    //S'arrête après avoir check une seule checkbox.
    //prend 2 element en etnree
    public static void checkboxsingleBoxInTable(WebElement table, String tableId, int columnIndex1, int columnIndex2, String contentColumnIndex1, String contentColumnIndex2) {
        //recherche dans la table le contenu de chaque case, ligne par ligne, colonne par colonne.
        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + tableId + "']/tbody/tr"));
        Boolean found = false;
        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            //boolean isType = false;
            //boolean isCreance = false;
            //compte le nbre de colonne
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            //x = iterateTableColumn(columnIndex1, columnIndex2, type, creance, isType, isCreance, totalColumnCount, columnIndex);
            found = checkboxThisRow(table, columnIndex1, columnIndex2, tableId, contentColumnIndex1, contentColumnIndex2, totalColumnCount, columnIndex, rowIndex);
            if (found)
                return;
            rowIndex = rowIndex + 1;

        }
    }


    //S'arrête après avoir check une seule checkbox.
    //prend 3 element en etnree
    public static void checkboxsingleBoxInTable(WebElement table, String tableId, int columnIndex1, int columnIndex2, int columnIndex3, String contentColumnIndex1, String contentColumnIndex2, String contentColumnIndex3) {
        //recherche dans la table le contenu de chaque case, ligne par ligne, colonne par colonne.
        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + tableId + "']/tbody/tr"));
        Boolean found = false;
        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            //boolean isType = false;
            //boolean isCreance = false;
            //compte le nbre de colonne
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            //x = iterateTableColumn(columnIndex1, columnIndex2, type, creance, isType, isCreance, totalColumnCount, columnIndex);
            found = checkboxThisRow(table, columnIndex1, columnIndex2, columnIndex3, tableId, contentColumnIndex1, contentColumnIndex2, contentColumnIndex3, totalColumnCount, columnIndex, rowIndex);
            if (found)
                return;
            rowIndex = rowIndex + 1;

        }
    }

    private static Boolean checkboxThisRowLowerThan(WebElement table, int columnIndex1, String tableId, int contentColumnIndex1, List<WebElement> totalColumnCount, int columnIndex, int rowIndex) {
        //String sortie = null;
        //Boolean isColumn1 = false;
        //Boolean isColumn2 = false;
        for (WebElement colElement : totalColumnCount) {
            if (columnIndex == columnIndex1) {
                String valueToCompare = colElement.getText();
                valueToCompare = StringUtils.substring(valueToCompare, 0, (valueToCompare.length()-2));
                valueToCompare = valueToCompare.replace(',','.');

                if (Float.parseFloat(valueToCompare) < contentColumnIndex1) {
                    WebElement checkbox = table.findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[1]/input"));
                    checkbox.click();
                    return true;
                } else {
                    break;
                }
            }
            /*if (columnIndex == columnIndex2 && isColumn1) {
                if (colElement.getText().equals(contentColumnIndex2)) {
                    isColumn2 = true;
                } else {
                    break;
                }
            }*/
            columnIndex = columnIndex + 1;
        }

        return false;
        /*if (isColumn1 && isColumn2)
            return sortie;
        return null;*/
    }


    //coche la case si la ligne contien le la valeur ContentColumnIndex dans la colonne columnIndex
    private static Boolean checkboxThisRow(WebElement table, int columnIndex1, String tableId, String contentColumnIndex1, List<WebElement> totalColumnCount, int columnIndex, int rowIndex) {
        //String sortie = null;
        //Boolean isColumn1 = false;
        //Boolean isColumn2 = false;
        for (WebElement colElement : totalColumnCount) {
            if (columnIndex == columnIndex1) {
                if (colElement.getText().equals(contentColumnIndex1)) {
                    WebElement checkbox = table.findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[1]/input"));
                    checkbox.click();
                    return true;
                } else {
                    break;
                }
            }
            /*if (columnIndex == columnIndex2 && isColumn1) {
                if (colElement.getText().equals(contentColumnIndex2)) {
                    isColumn2 = true;
                } else {
                    break;
                }
            }*/
            columnIndex = columnIndex + 1;
        }

        return false;
        /*if (isColumn1 && isColumn2)
            return sortie;
        return null;*/
    }


    //coche la case si la ligne contien les valeurs ContentColumnIndex dans les colonne columnIndex
    private static Boolean checkboxThisRow(WebElement table, int columnIndex1, int columnIndex2, String tableId, String contentColumnIndex1, String contentColumnIndex2, List<WebElement> totalColumnCount, int columnIndex, int rowIndex) {
        //String sortie = null;
        Boolean isColumn1 = false;
        Boolean isColumn2 = false;


        for (WebElement colElement : totalColumnCount) {
            if (columnIndex == columnIndex1) {
                if (colElement.getText().equals(contentColumnIndex1)) {
                    isColumn1 = true;
                } else {
                    break;
                }
            }

            if (columnIndex == columnIndex2 && isColumn1) {
                if (colElement.getText().equals(contentColumnIndex2)) {
                    isColumn2 = true;
                } else {
                    break;
                }
            }

            if (isColumn1 && isColumn2) {
                WebElement checkbox = table.findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[1]/input"));
                checkbox.click();
                return true;
            }
            columnIndex = columnIndex + 1;
        /*if (isColumn1 && isColumn2)
            return sortie;
        return null;*/
        }

        return false;
    }

    //coche la case si la ligne contien les valeurs ContentColumnIndex dans les colonne columnIndex
    private static Boolean checkboxThisRow(WebElement table, int columnIndex1, int columnIndex2, int columnIndex3, String tableId, String contentColumnIndex1, String contentColumnIndex2, String contentColumnIndex3, List<WebElement> totalColumnCount, int columnIndex, int rowIndex) {
        //String sortie = null;
        Boolean isColumn1 = false;
        Boolean isColumn2 = false;
        Boolean isColumn3 = false;


        for (WebElement colElement : totalColumnCount) {
            if (columnIndex == columnIndex1) {
                if (colElement.getText().equals(contentColumnIndex1)) {
                    isColumn1 = true;
                } else {
                    break;
                }
            }

            if (columnIndex == columnIndex2 && isColumn1) {
                if (colElement.getText().equals(contentColumnIndex2)) {
                    isColumn2 = true;
                } else {
                    break;
                }
            }

            if (columnIndex == columnIndex3 && isColumn1 && isColumn2) {
                if (colElement.getText().equals(contentColumnIndex2)) {
                    isColumn3 = true;
                } else {
                    break;
                }
            }

            if (isColumn1 && isColumn2 && isColumn3) {
                WebElement checkbox = table.findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[1]/input"));
                checkbox.click();
                return true;
            }
            columnIndex = columnIndex + 1;
        /*if (isColumn1 && isColumn2)
            return sortie;
        return null;*/
        }
        return false;
    }


    private static boolean clickTraiterInLine(WebElement table, int columnIndex1, String tableId, String contentColumnIndex1, List<WebElement> totalColumnCount, int columnIndex, int columnIndexClick, int rowIndex, String idBouton){

        for (WebElement colElement : totalColumnCount) {
            if (columnIndex == columnIndex1) {
                if (colElement.getText().equals(contentColumnIndex1)) {
                    String bouton = "//*[@id='" + tableId + "']/tbody/tr[" + rowIndex + "]/td[" + columnIndexClick + "]/" + idBouton;

                    WebElement boutonTraiter = table.findElement(By.xpath(bouton));
                    boutonTraiter.click();
                    return true;
                } else {
                    break;
                }
            }

            columnIndex = columnIndex + 1;
        }
        return false;
    }

    public static boolean clickTraiterInTable(WebElement table, String tableId, int columnIndex1, String contentColumnIndex1, int columnIndexClick, String idBouton) {
        //recherche dans la table le contenu de chaque case, ligne par ligne, colonne par colonne.
        String xpathTable = "//*[@id='" + tableId + "']/tbody/tr";
        List<WebElement> totalRowCount = table.findElements(By.xpath(xpathTable));
        boolean sortie = false;
        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            //boolean isType = false;
            //boolean isCreance = false;
            //compte le nbre de colonne
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            int columnIndex = 1;
            //x = iterateTableColumn(columnIndex1, columnIndex2, type, creance, isType, isCreance, totalColumnCount, columnIndex);
            sortie = clickTraiterInLine(table, columnIndex1, tableId, contentColumnIndex1, totalColumnCount, columnIndex, columnIndexClick , rowIndex, idBouton);

            if(sortie)
                return sortie;

            rowIndex = rowIndex + 1;

        }
        return sortie;
    }

    public static Boolean stateboxsingleBoxInTable(WebElement table, String tableId) {
        //recherche dans la table le contenu de chaque case, ligne par ligne, colonne par colonne.
        List<WebElement> totalRowCount = table.findElements(By.xpath("//*[@id='" + tableId + "']/tbody/tr"));
        Boolean found = false;
        int rowIndex = 1;
        for (WebElement rowElement : totalRowCount) {
            //boolean isType = false;
            //boolean isCreance = false;
            //compte le nbre de colonne
            List<WebElement> totalColumnCount = rowElement.findElements(By.xpath("td"));
            //x = iterateTableColumn(columnIndex1, columnIndex2, type, creance, isType, isCreance, totalColumnCount, columnIndex);
            WebElement checkbox = table.findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[1]/input"));

            if (checkbox.getAttribute("checked") != null);
            else
               return false;
            rowIndex = rowIndex + 1;

        }
        return true;
    }


}