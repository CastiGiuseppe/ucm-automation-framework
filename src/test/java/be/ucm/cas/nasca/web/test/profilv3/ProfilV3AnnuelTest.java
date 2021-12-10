package be.ucm.cas.nasca.web.test.profilv3;

import be.ucm.cas.nasca.web.test.support.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

public class ProfilV3AnnuelTest extends ProfilV3TestBase {

    private static final Map<String, List<String>> MAP_NISS = new LinkedHashMap<>();
    private Calendar calJour;

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_ProfilV3.xlsx", "Annuel", "Profil V3", "ProfilV3", null);
    }

    @BeforeTest()
    public void launchChangeTime() {
        calJour = Calendar.getInstance();
        calJour.set(Calendar.DAY_OF_MONTH, 1);
        calJour.add(Calendar.YEAR, 1);
        calJour.set(Calendar.MONTH, Calendar.JANUARY);

        Calendar calJour2 = Calendar.getInstance();
        calJour2.setTime(calJour.getTime());
        calJour2.add(Calendar.MONTH, 3);

        RunBatch.runBatchChangeDate(DateUtils.doFormat(TestData.DATE_FORMAT_XML, calJour.getTime()));

        DaoFunctionality.getNascaDao().initialisationDateEnrolementMajoPPTrimestrielle(calJour.get(Calendar.YEAR), DateUtils.getQuarterNumber(DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, calJour.getTime())), DateUtils.getQuarterNumber(DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, calJour2.getTime())), calJour.getTime(), calJour2.getTime());
        DaoFunctionality.getNascaDao().initialisationDateEnrolementMajoPPAnnuelle(calJour.get(Calendar.YEAR), calJour2.getTime());
    }

    @Test(dataProvider = "data")
    public void selectionDossier(String id, String libelle, String agePensionAtteint,
                                 String dateDebut65Ans, String naturePension, String naturePensionDB,
                                 String enfantACharge, String regime, String regimeDB, String nacAvant,
                                 String nacApres, String nature, String superNature, String limiteArt11) {
        doSelectionDossierAnnuel(MAP_NISS, id, libelle, Thread.currentThread().getStackTrace()[1].getFileName(), agePensionAtteint, dateDebut65Ans, naturePensionDB, enfantACharge, regimeDB, nacAvant);
    }

    @Test(dataProvider = "data", priority = 1)
    public void preparationDossier(String id, String libelle, String agePensionAtteint,
                                   String dateDebut65Ans, String naturePension, String naturePensionDB,
                                   String enfantACharge, String regime, String regimeDB, String nacAvant,
                                   String nacApres, String nature, String superNature, String limiteArt11) {
        String niss = null;
        if (MAP_NISS.get(id) != null) {
            niss = MAP_NISS.get(id).get(0);
        }

        if (niss != null) {
            initTest("Sc. " + id + " " + libelle  + " - Préparation", Thread.currentThread().getStackTrace()[1].getFileName());

            loginNasca();

            loadNissOrBce(niss);

            SeleniumUtils.isLoading();

            logInfo("Situation avant batch Pension Annuel pour "  + niss, "");

            finishTestExecution();
        }
    }

    @Test(priority = 2)
    public void launchBatch() {
        RunBatch.runBatchPensionAnnuelPrepare(String.valueOf(calJour.get(Calendar.YEAR)));
        RunBatch.runBatchPensionAnnuelProcessing();
    }

    @Test(dataProvider = "data", priority = 3)
    public void checkResultat(String id, String libelle, String agePensionAtteint,
                              String dateDebut65Ans, String naturePension, String naturePensionDB,
                              String enfantACharge, String regime, String regimeDB, String nacAvant,
                              String nacApres, String nature, String superNature, String limiteArt11) {
        String niss = null;
        if (MAP_NISS.get(id) != null) {
            niss = MAP_NISS.get(id).get(0);
        }

        checkProfil(id, libelle, nature, superNature, limiteArt11, niss, Thread.currentThread().getStackTrace()[1].getFileName());
    }
}