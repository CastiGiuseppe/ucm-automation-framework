package be.ucm.cas.nasca.web.test.support;

import be.ucm.cas.nasca.web.test.support.exceptions.DataDrivenFrameworkException;
import com.google.common.io.Files;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.format.CellDateFormatter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

final class ExcelFileUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    private Workbook excel;
    private String excelFile;
    private String sheetName = null;
    private Sheet sheet;
    private String testCaseColumnName = null;
    private int tcColHeader = -1;
    private FormulaEvaluator objFormulaEvaluator;

    public ExcelFileUtils() {
        this.excelFile = null;
    }

    public List<List<String>> setData(String excelResource, String sheetName,
                                      String testCaseHeader, String testCaseName) {
        try {
            return getDataTestCase(excelResource, sheetName, testCaseHeader, testCaseName);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new ArrayList<>();

        }
    }

    public List<List<String>> setData(String excelResource, String sheetName,
                                      String testCaseHeader, String testCaseName, int scenarioid) {
        try {
            List<List<String>> data = getDataTestCase(excelResource, sheetName, testCaseHeader, testCaseName);

            int scenario = scenarioid - 1;

            List<List<String>> dataWithScenarioId = new ArrayList<>();
            dataWithScenarioId.add(data.get(scenario));

            return dataWithScenarioId;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private List<List<String>> getDataTestCase(String excelResource, String sheetName, String testCaseHeader, String testCaseName) throws IOException {
        String excelRsrc = TestData.PATH_EXCEL_DATA + excelResource;

        if (!new File(TestData.TEMP_LOCATION).exists() && new File(TestData.TEMP_LOCATION).mkdir()) {
            LOGGER.info("Répertoire " + TestData.TEMP_LOCATION + " créé");
        }
        String excelWork = TestData.TEMP_LOCATION + "temp.xlsx";
        File excelrsrc = new File(excelRsrc);
        File excelwrk = new File(excelWork);
        Files.copy(excelrsrc, excelwrk);
        this.excelFile = excelWork;
        setSheetName(sheetName);
        setSheetName(getSheetName());
        setTestCaseHeaderName(testCaseHeader);
        List<List<String>> data = getDataUsingTestCaseName(testCaseName);

        if (excelwrk.exists() && excelwrk.delete()) {
            LOGGER.info("Fichier " + excelWork + " supprimé");
        }
        return data;
    }

    private void initWorkbook() throws DataDrivenFrameworkException {
        try {
            this.excel = WorkbookFactory.create(new FileInputStream(this.excelFile));
            objFormulaEvaluator = excel.getCreationHelper().createFormulaEvaluator();
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
            throw new DataDrivenFrameworkException("The Excel File Does Not Exist: " + this.excelFile);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new DataDrivenFrameworkException("Error Occured while reading the Given excel file: " + this.excelFile);
        } catch (InvalidFormatException | IllegalArgumentException e) {
            LOGGER.error(e.getMessage());
            throw new DataDrivenFrameworkException("The given File is Not a Valid excel file: "
                    + this.excelFile
                    + "\nPossible reason: The file might be corrupted");
        }
    }

    private String getCellValue(Cell cellData) {
        try {
            if (cellData == null) {
                return " ";
            }
            if (cellData.getCellType() == 1) {
                return cellData.getRichStringCellValue().getString();
            }
            CellValue cellValue = objFormulaEvaluator.evaluate(cellData);
            if (cellValue.getCellType() == 0) {
                if (DateUtil.isCellDateFormatted(cellData)) {
                    Date date = DateUtil
                            .getJavaDate(cellValue.getNumberValue());

                    String dateFmt = cellData.getCellStyle()
                            .getDataFormatString();

                    return new CellDateFormatter(dateFmt).format(date);
                }
                return NumberToTextConverter.toText(cellValue.getNumberValue());
            }
            if (cellValue.getCellType() == 4) {
                return cellValue.getBooleanValue() + "";
            }
            if (cellData.getCellType() == 2) {
                return cellValue.getStringValue();
            }
            if (cellData.getCellType() == 5) {
                return cellValue.getErrorValue() + "";
            }
            return " ";
        } catch (Exception e) {
            return null;
        }
    }

    private int findHeaderColumnIndex() throws DataDrivenFrameworkException {
        this.sheet = this.excel.getSheet(getSheetName());
        if (this.sheet == null) {
            throw new DataDrivenFrameworkException("The Given Excel File: "
                    + this.excelFile + " Does not contain the provided sheet: "
                    + this.sheetName);
        }
        Row row = this.sheet.getRow(this.sheet.getFirstRowNum());
        if (row == null) {
            throw new DataDrivenFrameworkException(
                    "The given Test Case Header name \""
                            + getTestCaseHeaderName()
                            + "\" could not be found in Excel: "
                            + this.excelFile + " Using Sheet: "
                            + getSheetName());
        }
        for (Cell cell : row) {
            if (cell.getStringCellValue().equals(getTestCaseHeaderName())) {
                this.tcColHeader = cell.getColumnIndex();

                break;
            }
        }
        if (this.tcColHeader == -1) {
            throw new DataDrivenFrameworkException(
                    "The given Test Case Header name \""
                            + getTestCaseHeaderName()
                            + "\" could not be found in Excel: "
                            + this.excelFile + " Using Sheet: "
                            + getSheetName());
        }
        return this.tcColHeader;
    }

    public String getExcelFile() {
        return this.excelFile;
    }

    public void setExcelFile(String excelFile) {
        this.excelFile = excelFile;
    }

    private String getSheetName() {
        return this.sheetName;
    }

    private void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    private String getTestCaseHeaderName() {
        return this.testCaseColumnName;
    }

    private void setTestCaseHeaderName(String testCaseHeader) {
        this.testCaseColumnName = testCaseHeader;
    }

    private List<List<String>> getDataUsingTestCaseName(String testCaseName) {
        try {
            initWorkbook();

            List<List<String>> data = new ArrayList<>();
            this.sheet = this.excel.getSheet(getSheetName());
            this.tcColHeader = findHeaderColumnIndex();
            int totalRows = this.sheet.getLastRowNum();
            int startRow = 0;
            int endCell = 0;
            int x = 0;
            while (x < totalRows) {
                Row row = this.sheet.getRow(x);
                if (row != null && row.getCell(this.tcColHeader) != null && row.getCell(this.tcColHeader).getStringCellValue().equals(testCaseName)) {
                    startRow = x;
                    endCell = row.getLastCellNum();
                    break;
                }
                x++;
            }

            int rowsToRead = totalRows - startRow;
            int y = 0;

            while (y < rowsToRead) {
                List<String> rowData = new ArrayList<>();
                if (this.sheet.getRow(++startRow) != null) {
                    if ((this.sheet.getRow(startRow).getCell(this.tcColHeader) != null)
                            && (!StringUtils
                            .isEmpty(this.sheet.getRow(startRow)
                                    .getCell(this.tcColHeader)
                                    .getStringCellValue()))
                            && (!this.sheet.getRow(startRow)
                            .getCell(this.tcColHeader).getStringCellValue()
                            .equals(testCaseName))) {
                        break;
                    }
                    int z = this.tcColHeader + 1;
                    while (z < endCell) {
                        Cell cellValue = this.sheet.getRow(startRow).getCell(z);
                        String cell = getCellValue(cellValue);

                        rowData.add(cell);
                        z++;
                    }
                    data.add(rowData);
                }
                if (rowsToRead == 0) {
                    break;
                }
                rowsToRead--;

            }
            this.tcColHeader = -1;
            return data;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}