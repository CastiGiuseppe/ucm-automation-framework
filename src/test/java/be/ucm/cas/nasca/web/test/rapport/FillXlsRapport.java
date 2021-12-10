package be.ucm.cas.nasca.web.test.rapport;

import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.EnumTypeTest;
import be.ucm.cas.nasca.web.test.support.TestBase;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.*;
import java.util.*;

public class FillXlsRapport extends TestBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    private static final int COLONNE_ENVIRONNEMENT = 4;
    private static final int COLONNE_TEMPS_EXECUTION = 5;
    private static final int COLONNE_NBRE_TESTS = 6;
    private static final int COLONNE_NBRE_PASS = 7;
    private static final int COLONNE_NBRE_FAIL = 9;
    private static final int COLONNE_NBRE_ERROR = 11;
    private static final int COLONNE_NBRE_UNKNOWN = 13;
    private static final int COLONNE_NBRE_SKIP = 15;
    private static final int COLONNE_TENDANCE = 17;
    private static final int COLONNE_LIEN = 18;
    private static final int COLONNE_DATE_DEBUT = 19;
    private static final int COLONNE_DATE_FIN = 20;

    private static final int LIGNE_TOTAL = 120;

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    @Test
    public void fillRapport() throws Exception {
        initTest("FillXlsRapport", Thread.currentThread().getStackTrace()[1].getFileName());

        Calendar calJour = Calendar.getInstance();
        String dateFormat = DateUtils.getDateTodayNoFomat();

        if (calJour.get(Calendar.AM_PM) == 0 && calJour.get(Calendar.HOUR) < 12) {
            calJour.add(Calendar.DAY_OF_MONTH, -1);
            dateFormat = DateUtils.doFormat(TestData.DATE_NOFORMAT, calJour.getTime());
        }

        String templateRapport = TestData.FILE_TEMPLATE_PATH + TestData.TEMPLATE_RAPPORT;

        Workbook wb = WorkbookFactory.create(new File(templateRapport));
        Sheet sheet = wb.getSheetAt(0);

        Row row = sheet.getRow(0);

        Cell cell = row.getCell(0);

        cell.setCellValue("Progression des tests système " + DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, DateUtils.getDateFuturOrPass(-1, new Date())));

        File[] listefichiers = new File(TestData.RECAPITULATIF_RAPPORT_TA + dateFormat + "/Rapport").listFiles();

        // Initialise la map contenant le nom de chacun des tests ainsi que sa position dans le rapport
        Map<String, Integer> mapTests = makeMapFromExcel(new File(TestData.FILE_TEMPLATE_PATH + TestData.LISTING_TESTS));

        setTabTendance(wb, sheet);

        int i;
        if (listefichiers != null) {
            for (i = 0; i < listefichiers.length; i++) {
                traitementFichierTxt(wb, sheet, listefichiers[i], mapTests);
            }
        }

        FileOutputStream fileOut = new FileOutputStream(TestData.RECAPITULATIF_RAPPORT_TA + dateFormat + TestData.FILE_RECAPITULATIF_RAPPORT_TA);
        wb.write(fileOut);
        fileOut.close();

        finishTestExecution();
    }

    private void traitementFichierTxt(Workbook wb, Sheet sheet, File listefichier, Map<String, Integer> mapTests) throws IOException {
        if (listefichier.getName().endsWith(".txt")) {
            String test = listefichier.getName().replaceAll(".txt", "");
            String environnement = null;
            double duree = 0;
            double totalTest = 0;
            double pass = 0;
            double fail = 0;
            double error = 0;
            double unknown = 0;
            double skip = 0;
            String lien = null;
            String dateDebut = null;
            String dateFin = null;

            InputStream ips = new FileInputStream(listefichier);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String delims = "[;]";
                String[] elt = ligne.split(delims);
                environnement = elt[0];
                duree = Double.valueOf(elt[1]);
                totalTest = Double.valueOf(elt[2]);
                pass = Double.valueOf(elt[3]);
                fail = Double.valueOf(elt[4]);
                error = Double.valueOf(elt[5]);
                unknown = Double.valueOf(elt[6]);
                skip = Double.valueOf(elt[7]);
                lien = elt[8];
                dateDebut = elt[9];
                dateFin = elt[10];
            }
            br.close();
            setValeur(wb, sheet, mapTests, test, environnement, duree, totalTest, pass, fail, error, unknown, skip, lien, dateDebut, dateFin);
        }
    }

    private void setValeur(Workbook wb, Sheet sheet, Map<String, Integer> mapTests, String test, String environnement, double duree, double totalTest, double pass, double fail, double error, double unknown, double skip, String lien, String dateDebut, String dateFin) {
        if (mapTests.get(test) != null) {
            setValeurXls(wb, mapTests.get(test), sheet, duree, pass, fail, error, unknown, skip, totalTest, environnement, test, lien, dateDebut, dateFin);
        }
    }

    /**
     * Crée une map depuis le listing des test présents dans listingTest.xlsx
     *
     * @param file File
     * @return mapExcel Map<String, Integer>
     */
    private static Map<String, Integer> makeMapFromExcel(File file) throws IOException {
        int cpt = 4;
        // String = Nom du Test  Integer = Position dans Récapitulatif Rapport TA.xlsx
        Map<String, Integer> mapExcel = new LinkedHashMap<>();
        FileInputStream inputStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row nextRow : sheet) {
            Cell cell = nextRow.getCell(0);
            mapExcel.put(cell.getStringCellValue(), cpt);
            cpt++;
        }
        return mapExcel;
    }

    private static void setTabTendance(Workbook wb, Sheet sheet) {
        Map<Integer, Short> colorMap = new HashMap<>();

        colorMap.put(LIGNE_TOTAL + 3, HSSFColor.RED.index);
        colorMap.put(LIGNE_TOTAL + 4, HSSFColor.YELLOW.index);
        colorMap.put(LIGNE_TOTAL + 5, HSSFColor.GREEN.index);
        colorMap.put(LIGNE_TOTAL + 6, HSSFColor.GREY_25_PERCENT.index);
        colorMap.put(LIGNE_TOTAL + 7, HSSFColor.BLUE.index);

        Row row;
        Cell cell;

        for (Map.Entry<Integer, Short> entry : colorMap.entrySet()) {
            row = sheet.getRow(entry.getKey());
            cell = row.getCell(COLONNE_ENVIRONNEMENT);
            setStyle(entry.getValue(), wb, cell);

            row = sheet.getRow(entry.getKey());
            cell = row.getCell(COLONNE_TEMPS_EXECUTION);

            switch (entry.getValue()) {
                case HSSFColor.RED.index:
                    cell.setCellValue("Fail");
                    break;
                case HSSFColor.YELLOW.index:
                    cell.setCellValue("Erreur");
                    break;
                case HSSFColor.GREEN.index:
                    cell.setCellValue("Pass");
                    break;
                case HSSFColor.GREY_25_PERCENT.index:
                    cell.setCellValue("Unknown");
                    break;
                case HSSFColor.BLUE.index:
                    cell.setCellValue("Pas de données");
                    break;
                default:
                    break;
            }

            setStyle(null, wb, cell);
        }
    }

    private static void setStyle(Short color, Workbook wb, Cell cell) {
        CellStyle styleFont = wb.createCellStyle();

        styleFont.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleFont.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleFont.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleFont.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleFont.setBorderTop(HSSFCellStyle.BORDER_THIN);

        if (color != null) {
            styleFont.setFillForegroundColor(color);
        } else {
            styleFont.setFillForegroundColor(HSSFColor.WHITE.index);
        }

        styleFont.setAlignment(CellStyle.ALIGN_LEFT);
        styleFont.setAlignment(CellStyle.ALIGN_LEFT);
        cell.setCellStyle(styleFont);
    }

    private static void setValeurXls(Workbook wb, int indiceRow, Sheet sheet, double duree, double pass,
                                     double fail, double error, double unknown, double skip, double totalTest, String environnement, String test, String lien, String dateDebut, String dateFin) {
        Row row;
        Cell cell;

        row = sheet.getRow(indiceRow);

        // Environnement
        cell = row.getCell(COLONNE_ENVIRONNEMENT);
        cell.setCellValue(environnement);

        // Temps d'exécution
        cell = row.getCell(COLONNE_TEMPS_EXECUTION);
        cell.setCellValue(duree);

        // Nombre de Tests
        cell = row.getCell(COLONNE_NBRE_TESTS);
        cell.setCellValue(totalTest);

        // Pass
        cell = row.getCell(COLONNE_NBRE_PASS);
        cell.setCellValue(pass);

        // Fail
        cell = row.getCell(COLONNE_NBRE_FAIL);
        cell.setCellValue(fail);

        // Error
        cell = row.getCell(COLONNE_NBRE_ERROR);
        cell.setCellValue(error);

        // Unknown
        cell = row.getCell(COLONNE_NBRE_UNKNOWN);
        cell.setCellValue(unknown);

        // Skip
        cell = row.getCell(COLONNE_NBRE_SKIP);
        cell.setCellValue(skip);

        // Tendance
        short color = generateColorTendance(pass / totalTest, fail / totalTest, error / totalTest, unknown / totalTest, skip / totalTest);
        CellStyle styleFont = wb.createCellStyle();
        Font font = wb.createFont();
        styleFont.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleFont.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleFont.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleFont.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleFont.setBorderTop(HSSFCellStyle.BORDER_THIN);
        font.setColor(color);
        styleFont.setFillForegroundColor(color);
        styleFont.setFont(font);
        cell = row.getCell(COLONNE_TENDANCE);
        cell.setCellStyle(styleFont);

        // Lien
        cell = row.getCell(COLONNE_LIEN);
        cell.setCellValue(test);
        CreationHelper createHelper = wb.getCreationHelper();
        XSSFHyperlink link = (XSSFHyperlink) createHelper.createHyperlink(HSSFHyperlink.LINK_FILE);
        link.setAddress(lien);
        cell.setHyperlink(link);

        // Date et heure début Test
        cell = row.getCell(COLONNE_DATE_DEBUT);
        cell.setCellValue(dateDebut);

        // Date et heure début Test
        cell = row.getCell(COLONNE_DATE_FIN);
        cell.setCellValue(dateFin);

        // Force la mise à jour des formules
        try {
            wb.getCreationHelper().createFormulaEvaluator().evaluateAll();
        } catch (RuntimeException e) {
            LOGGER.error("Mise à jour formules KO", e);
        }
    }

    private static short generateColorTendance(double pass, double fail, double error, double unknown, double skip) {
        if (pass >= 0.7) {
            return HSSFColor.GREEN.index;
        } else if (fail >= 0.8) {
            return HSSFColor.RED.index;
        } else if (skip >= 0.9) {
            return HSSFColor.BLUE.index;
        } else if (unknown >= 0.9) {
            return HSSFColor.GREY_25_PERCENT.index;
        } else if (error >= 0.8) {
            return HSSFColor.YELLOW.index;
        } else {
            return HSSFColor.RED.index;
        }
    }
}