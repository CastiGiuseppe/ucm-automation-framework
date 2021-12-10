package be.ucm.cas.nasca.web.test.profilv3;

import be.ucm.cas.nasca.web.test.support.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

public class ProfilV3TrimestrielTest extends ProfilV3TestBase {

    private static final Map<String, List<String>> MAP_NISS = new LinkedHashMap<>();

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_ProfilV3.xlsx", "Trimestriel", "Profil V3", "ProfilV3", null);
    }

    @Test(dataProvider = "data")
    public void selectionDossier(String id, String libelle,
                                 String dateDebut65Ans, String dateFin65Ans, String conjoint, String conjointPensionne,
                                 String natureCotisante, String regime, String regimeDB, String anneesCarriere, String anneesCarriereDB,
                                 String naturePension, String naturePensionDB, String nacAvant, String nacApres,
                                 String nature, String superNature, String limiteArt11, String tache, String skip) {
        if (!Boolean.valueOf(skip)) {
            doSelectionDossierTrimestriel(MAP_NISS, id, libelle, Thread.currentThread().getStackTrace()[1].getFileName(), dateDebut65Ans,
                    dateFin65Ans, nacAvant, conjoint, conjointPensionne, regimeDB, anneesCarriereDB, naturePensionDB);
        }
    }

    @Test(dataProvider = "data", priority = 1)
    public void preparationDossier(String id, String libelle,
                                   String dateDebut65Ans, String dateFin65Ans, String conjoint, String conjointPensionne,
                                   String natureCotisante, String regime, String regimeDB, String anneesCarriere, String anneesCarriereDB,
                                   String naturePension, String naturePensionDB, String nacAvant, String nacApres,
                                   String nature, String superNature, String limiteArt11, String tache, String skip) {
        if (!Boolean.valueOf(skip)) {
            String niss = null;
            String ajoutPension = null;
            if (MAP_NISS.get(id) != null) {
                niss = MAP_NISS.get(id).get(0);
                ajoutPension = MAP_NISS.get(id).get(1);
            }

            if (niss != null) {
                initTest("Sc. " + id + " " + libelle  + " - Préparation", Thread.currentThread().getStackTrace()[1].getFileName());

                loginNasca();

                DaoFunctionality.getNascaDao().updateDateNaissanceForProfilV3(dateDebut65Ans, niss);

                loadNissOrBce(niss);

                SeleniumUtils.isLoading();

                logInfo("Situation avant batch Pension Trimestriel pour "  + niss, "");

                if (Boolean.valueOf(ajoutPension)) {
                    Calendar calJour = Calendar.getInstance();
                    calJour.add(Calendar.YEAR, -1);
                    calJour.set(Calendar.MONTH, Calendar.JANUARY);
                    calJour.set(Calendar.DAY_OF_MONTH, 1);

                    ajoutPension(niss, regime, naturePension, "Non", DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE_WITH_SLASH, new Date()),
                            DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE_WITH_SLASH, calJour.getTime()), anneesCarriere, "TI lui-même", "Principal");
                }

                finishTestExecution();
            }
        }
    }

    @Test(priority = 2)
    public void launchBatch() {
        Calendar calJour = Calendar.getInstance();
        calJour.set(Calendar.DAY_OF_MONTH, 1);

        if (calJour.get(Calendar.MONTH) == Calendar.JANUARY || calJour.get(Calendar.MONTH) == Calendar.FEBRUARY || calJour.get(Calendar.MONTH) == Calendar.MARCH) {
            calJour.set(Calendar.MONTH, Calendar.JANUARY);
        } else if (calJour.get(Calendar.MONTH) == Calendar.APRIL || calJour.get(Calendar.MONTH) == Calendar.MAY || calJour.get(Calendar.MONTH) == Calendar.JUNE) {
            calJour.set(Calendar.MONTH, Calendar.APRIL);
        } else if (calJour.get(Calendar.MONTH) == Calendar.JULY || calJour.get(Calendar.MONTH) == Calendar.AUGUST || calJour.get(Calendar.MONTH) == Calendar.SEPTEMBER) {
            calJour.set(Calendar.MONTH, Calendar.JULY);
        } else {
            calJour.set(Calendar.MONTH, Calendar.OCTOBER);
        }

        RunBatch.runBatchPensionTrimestrielPrepare(DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE_WITH_SLASH, calJour.getTime()));
        RunBatch.runBatchPensionTrimestrielProcessing();
    }

    @Test(dataProvider = "data", priority = 3)
    public void checkResultat(String id, String libelle,
                              String dateDebut65Ans, String dateFin65Ans, String conjoint, String conjointPensionne,
                              String natureCotisante, String regime, String regimeDB, String anneesCarriere, String anneesCarriereDB,
                              String naturePension, String naturePensionDB, String nacAvant, String nacApres,
                              String nature, String superNature, String limiteArt11, String tache, String skip) {
        if (!Boolean.valueOf(skip)) {
            String niss = null;
            if (MAP_NISS.get(id) != null) {
                niss = MAP_NISS.get(id).get(0);
            }

            checkProfil(id, libelle, nature, superNature, limiteArt11, niss, Thread.currentThread().getStackTrace()[1].getFileName());
        }
    }
}