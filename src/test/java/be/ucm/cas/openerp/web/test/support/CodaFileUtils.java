package be.ucm.cas.openerp.web.test.support;

import be.ucm.cas.nasca.web.test.support.ApplicationContext;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import be.ucm.cas.nasca.web.test.support.dao.NascaDao;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public final class CodaFileUtils {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    private static final File temp = new File(TestData.TMP_FILE);

    public CodaFileUtils() {
    }

    public static void doCodaFile(String niss, String domitypeCODAConstant, String montant) throws IOException {
        if (!temp.exists() && temp.mkdir()) {
            LOGGER.info("Répertoire créé " + temp.getPath());
        }
        List<String> montants = new ArrayList<>();
        if (montant != null) {
            montants.add(montant);
        } else {
            montants = getMontantforCODAList(niss);
        }

        String iban = getIbanforCODA(niss);

        String communication = "127" + DateUtils.getTodayDateFormatCODA() + iban;

        for (int j = 0; j < montants.size(); j++) {

            FileInputStream fstream = null;
            try {
                fstream = new FileInputStream(TestData.FILE_TEMPLATE_PATH + domitypeCODAConstant);
            } catch (FileNotFoundException e) {
                LOGGER.error(e.getMessage());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            String strLine;
            List<String> myList = new ArrayList<>();

            // Read File Line By Line
            try {
                while ((strLine = br.readLine()) != null) {
                    myList.add(strLine);
                }
            } catch (IOException e2) {
                LOGGER.error(e2.getMessage());
            }

            boucleCodaFile(niss, domitypeCODAConstant, montants, communication, j, myList);

            try {
                FileWriter writer = new FileWriter(TestData.TMP_FILE + "\\"
                        + DateUtils.getDateToday() + "_DOMI_" + niss + "_" + j + ".COD");

                for (String str : myList) {
                    writer.append(str);
                    writer.write("\n");
                }

                writer.close();
            } catch (IOException e1) {
                LOGGER.error(e1.getMessage());
            }
            br.close();
        }
    }

    private static void boucleCodaFile(String niss, String domitypeCODAConstant, List<String> montant, String communication, int j, List<String> myList) {
        int i = 0;
        while (i < myList.size()) {
            switch (i) {
                case 0:
                    myList.set(i, dochange0(myList.get(i)));
                    break;
                case 1:
                    myList.set(i, dochange1(myList.get(i)));
                    break;
                case 2:
                    myList.set(i, dochange2Domi(myList.get(i), communication, montant.get(j)));
                    break;
                case 3:
                    myList.set(i, dochange3Domi(myList.get(i), getTypeMandatforDomi(niss)));
                    break;
                case 4:
                    myList.set(i, dochange4Domi(myList.get(i), getTypeIdentite(niss), getCodeReturnforDomi(domitypeCODAConstant)));
                    break;
                case 5:
                    myList.set(i, dochange5Domi(myList.get(i), getTypeIdentite(niss)));
                    break;
                case 7:
                    myList.set(i, dochange5Domi(myList.get(i), getTypeIdentite(niss)));
                    break;
                case 9:
                    myList.set(i, dochange10Domi(myList.get(i), montant.get(j)));
                    break;
                default:
                    break;
            }
            i++;

        }
    }

    private static String getCodeReturnforDomi(String domitypeCODAConstant) {
        switch (domitypeCODAConstant) {
            case TestData.CODA_DOMI_REFUND_FILE:
                return "3";
            case TestData.CODA_DOMI_NOK_FILE:
                return "2";
            case TestData.CODA_DOMI_OK_FILE:
                return "0";
            default:
                return null;
        }
    }

    private static String dochange10BCE(String ligne, String nissorbce) {
        String newtext = ligne;
        newtext = doReplaceInfoCodaLine9DB(newtext, getMontantforCODA(nissorbce), 22, 37);
        newtext = doReplaceInfoCodaLine9DB(newtext, getMontantforCODA(nissorbce), 37, 52);

        return newtext;
    }

    private static String dochange10Domi(String ligne, String montant) {
        String newtext = ligne;
        newtext = doReplaceInfoCodaLine9DB(newtext, montant, 22, 37);
        newtext = doReplaceInfoCodaLine9DB(newtext, montant, 37, 52);

        return newtext;
    }

    private static String dochange5Domi(String ligne, String identite) {
        String newtext = ligne;
        newtext = doReplaceInfoCodaspace(newtext, "001" + identite, 40, 113);
        return newtext;
    }

    private static String dochange4Domi(String ligne, String identite, String codeReturnforDomi) {
        String newtext = ligne;
        newtext = doReplaceInfoCodaspace(newtext, identite, 47, 82);
        newtext = doReplaceInfoCoda(newtext, codeReturnforDomi, 120, 121);
        return newtext;
    }

    private static String dochange3Domi(String ligne, String typemandat) {
        String newtext = ligne;
        newtext = doReplaceInfoCodaspace(newtext, typemandat, 63, 98);
        return newtext;
    }

    private static String dochange2Domi(String ligne, String communication, String montant) {
        String newtext = ligne;
        newtext = doReplaceInfoCoda(newtext, DateUtils.getTodayDateFormatCODA(), 47, 53);
        newtext = doReplaceInfoCoda(newtext, DateUtils.getTodayDateFormatCODA(), 115, 121);
        newtext = doReplaceInfoCodaspace(newtext, communication, 62, 115);
        newtext = doReplaceInfoCodaDomiLine2(newtext, montant, 32, 47);
        return newtext;
    }

    private static String dochange1(String ligne) {
        String newtext = ligne;
        newtext = doReplaceInfoCoda(newtext, DateUtils.getTodayDateFormatCODA(), 58, 64);
        return newtext;
    }

    private static String dochange0(String ligne) {
        String newtext = ligne;
        newtext = doReplaceInfoCoda(newtext, DateUtils.getTodayDateFormatCODA(), 5, 11);
        return newtext;
    }

    public static String doReplaceInfoCoda(String ligne, String newtext, int debutsubstr, int finsusbstr) {
        String oldtext = ligne.substring(debutsubstr, finsusbstr);
        String form = StringUtils.leftPad(newtext, oldtext.length(), "0");
        return ligne.replaceAll(oldtext, form);
    }

    private static String doReplaceInfoCodaspace(String ligne, String newtext, int debutsubstr, int finsusbstr) {
        String oldtext = ligne.substring(debutsubstr, finsusbstr);

        for (int i = newtext.length(); i < oldtext.length(); i++) {
            newtext += " ";
        }
        return ligne.replaceAll(oldtext, newtext);
    }

    private static String doReplaceInfoCodaLine2(String ligne, String montant, int debutsubstr, int finsusbstr) {
        double value = Double.parseDouble(montant.replace(",", ".")) * 1000;

        Map<String, String> map = makeMapForStringMontant(value, ligne, debutsubstr, finsusbstr);

        return ligne.replaceAll(map.get("oldtext"), map.get("form"));
    }

    private static Map<String, String> makeMapForStringMontant(double value, String ligne, int debutsubstr, int finsusbstr) {
        Map<String, String> map = new LinkedHashMap<>();
        DecimalFormat decimalFormat = new DecimalFormat(".");
        decimalFormat.setGroupingUsed(false);
        decimalFormat.setDecimalSeparatorAlwaysShown(false);

        map.put("stringmontant", decimalFormat.format(value));
        map.put("oldtext", ligne.substring(debutsubstr, finsusbstr));
        map.put("form", StringUtils.leftPad(map.get("stringmontant"), map.get("oldtext").length(), "0"));

        return map;
    }

    private static String doReplaceInfoCodaDomiLine2(String ligne, String montant, int debutsubstr, int finsusbstr) {
        double value = Double.parseDouble(montant.replace(",", "."));

        String stringmmontant = String.valueOf(value).replace(".", "");
        String oldtext = ligne.substring(debutsubstr, finsusbstr);

        String form = StringUtils.leftPad(stringmmontant, oldtext.length(), "0");

        stringmmontant = ligne.replaceAll(oldtext, form);

        return stringmmontant;
    }

    private static String doReplaceInfoCodaLine9DB(String ligne, String montant, int debutsubstr, int finsusbstr) {
        double value = Double.parseDouble(montant.replace(",", "."));

        Map<String, String> map = makeMapForStringMontant(value, ligne, debutsubstr, finsusbstr);

        String form = StringUtils.leftPad(map.get("stringmmontant"), map.get("oldtext").length(), "0");

        return ligne.replaceAll(map.get("oldtext"), form);
    }

    private static String getTypeIdentite(String niss) {
        DaoFunctionality doaini = new DaoFunctionality();
        doaini.setNascaDao((NascaDao) ApplicationContext.getAppCtx().getBean(DaoFunctionality.BEAN_NAME_FOR_NASCA_DAO));
        return DaoFunctionality.getNascaDao().getIdentiteFromMandat(niss);
    }

    private static String getMontantforCODA(String nissorbce) {
        DaoFunctionality doaini = new DaoFunctionality();
        doaini.setNascaDao((NascaDao) ApplicationContext.getAppCtx().getBean(DaoFunctionality.BEAN_NAME_FOR_NASCA_DAO));
        return DaoFunctionality.getNascaDao().getVCSMontant(nissorbce);
    }

    private static String getIbanforCODA(String niss) {
        DaoFunctionality doaini = new DaoFunctionality();
        doaini.setNascaDao((NascaDao) ApplicationContext.getAppCtx().getBean(DaoFunctionality.BEAN_NAME_FOR_NASCA_DAO));
        return DaoFunctionality.getNascaDao().getIbanFromNiss(niss);
    }

    private static String getTypeMandatforDomi(String niss) {
        DaoFunctionality doaini = new DaoFunctionality();
        doaini.setNascaDao((NascaDao) ApplicationContext.getAppCtx().getBean(DaoFunctionality.BEAN_NAME_FOR_NASCA_DAO));

        String typeMandat = DaoFunctionality.getNascaDao().getMandatTypeFromNiss(niss);

        switch (typeMandat) {
            case "B2B":
                typeMandat = "ANBNP154";
                return typeMandat;
            case "B2C":
                typeMandat = "ANDX1154";
                return typeMandat;
            default:
                break;
        }

        return typeMandat;
    }

    private static List<String> getMontantforCODAList(String nissorbce) {
        DaoFunctionality doaini = new DaoFunctionality();
        doaini.setNascaDao((NascaDao) ApplicationContext.getAppCtx().getBean(DaoFunctionality.BEAN_NAME_FOR_NASCA_DAO));

        return DaoFunctionality.getNascaDao().getVcsMontantList(nissorbce);
    }

    private static List<String> getVCSforCODAList(String nissorbce) {
        DaoFunctionality doaini = new DaoFunctionality();
        doaini.setNascaDao((NascaDao) ApplicationContext.getAppCtx().getBean(DaoFunctionality.BEAN_NAME_FOR_NASCA_DAO));

        List<String> vcscodelist = new ArrayList<>();
        List<String> listvcs = DaoFunctionality.getNascaDao().getVCScodeList(nissorbce);

        String prefix = "101";
        if (listvcs != null) {
            for (String listvc : listvcs) {
                String vcs = prefix + listvc;
                vcscodelist.add(vcs);
            }
        }

        return vcscodelist;
    }

    public static Collection<File> getPathofCodaFile(String nissorbce, String typeCODAConstant) {
        File codafiledirectory = new File(TestData.TMP_FILE + "\\");

        switch (typeCODAConstant) {
            case TestData.CODA_VCS_FILE:
                return FileUtils.listFiles(codafiledirectory, new WildcardFileFilter(DateUtils.getDateToday() + "_VCS_" + nissorbce + "*"), null);
            case TestData.CODA_SSTI_FILE:
                return FileUtils.listFiles(codafiledirectory, new WildcardFileFilter(DateUtils.getDateToday() + "_SSTI_" + nissorbce + "*"), null);
            case TestData.CODA_DOMI_OK_FILE:
                return FileUtils.listFiles(codafiledirectory, new WildcardFileFilter(DateUtils.getDateToday() + "_DOMI_" + nissorbce + "*"), null);
            case TestData.CODA_DOMI_NOK_FILE:
                return FileUtils.listFiles(codafiledirectory, new WildcardFileFilter(DateUtils.getDateToday() + "_DOMI_" + nissorbce + "*"), null);
            case TestData.CODA_DOMI_REFUND_FILE:
                return FileUtils.listFiles(codafiledirectory, new WildcardFileFilter(DateUtils.getDateToday() + "_DOMI_" + nissorbce + "*"), null);
            default:
                return new ArrayList<>();
        }
    }

    public static void cleanTmp() {
        for (File f : temp.listFiles()) {
            if (f.getName().endsWith(".COD") && f.delete()) {
                LOGGER.info("Fichier supprimé " + f.getPath());
            }
        }
    }
}