package be.ucm.cas.nasca.web.test.enrolement.pm;

import be.ucm.cas.nasca.web.test.support.*;

import java.math.BigDecimal;
import java.util.Date;

public class EnrolementPMTestBase extends TestBase {

    private static final BigDecimal ZERO_VALUE = BigDecimal.ZERO;

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    public static void initialisationDateEnrolementAnnuel(String anneeEnCours, String dateEnrol) {
        DaoFunctionality.getNascaDao().initialisationDateEnrolementAnnuel(anneeEnCours, dateEnrol);
    }

    public static void updateDateEnrolementAnnuelExecute(String anneeEnCours) {
        DaoFunctionality.getNascaDao().updateDateEnrolementAnnuelExecute(anneeEnCours);
    }

    public static void doEnrolementPM(String bcenumber, String annee, String datexmlformat, String balance, String cotisation) {
        launchBatchEnro(bcenumber, annee, datexmlformat, balance, cotisation);
        BatchUtils.deleteXmlBatch("Contributions");
    }

    public static void doEnrolementPMAvecBlocage(String bcenumber, String annee, String datexmlformat, String balance, String cotisation) {
        launchBatchEnro(bcenumber, annee, datexmlformat, balance, cotisation);
    }

    private static void launchBatchEnro(String bcenumber, String annee, String datexmlformat, String balance, String cotisation) {
        BatchUtils.createAndSendXmlEnrolPm(bcenumber, annee, datexmlformat, balance, cotisation);
        RunBatch.runBatchEnrolementPM(bcenumber);
    }

    static void doMajorationsPM() {
        RunBatch.runBatchMajorationPM();
    }

    public static void checkCotisationAnneePM(String nissorbce, String annee) {
        try {
            String cotisation = getCotisation(nissorbce, annee);
            if (cotisation != null) {
                logSuccess("Check cotisation après enrôlement OK");
            } else {
                logFailed("Check cotisation après enrôlement KO");
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + nissorbce, e);
        }
    }

    static void checkMajorationAnneePM(String nissorbce, String annee, String dateComptable) {
        try {
            fillOperationComptable(nissorbce, annee);
            gestionClientPage.fillDateValeurComptable(dateComptable);
            gestionClientPage.clickBtnSearch();
            BigDecimal majorations = gestionClientPage.getFromTableMajorationsByAnnee(annee, TestData.MENSUELLE);
            if (majorations.compareTo(ZERO_VALUE) != 0) {
                logSuccess("Check majoration après enrôlement OK");
            } else {
                logFailed("Check majoration après enrôlement KO");
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + nissorbce, e);
        }
    }

    public static void checkSuspensionEtSoldeAnneeTrimestrePM(String nissorbce, String periode, String creance, String suspension, String solde, String typeCreance) {
        try {
            loadNissOrBce(nissorbce);
            gestionClientPage.clickMenuCompteCotisantSoldes();
            gestionClientPage.clickAffichageSoldeElementsSoldesMajorationsRegroupees(periode);
            gestionClientPage.filtreTableOperations(creance, typeCreance, suspension, "", TestData.TYPE_PM);
            if (solde != null) {
                if (SeleniumUtils.deleteFormat(solde).equals(SeleniumUtils.deleteFormat(gestionClientPage.getInputSoldeTotal()))) {
                    logSuccess("Check Suspension " + suspension + " pour " + nissorbce + " (montant du plan égal au solde) OK");
                } else {
                    logFailed("Check Suspension " + suspension + " pour " + nissorbce + " (montant du plan " + solde + " différent du solde " + SeleniumUtils.deleteFormat(gestionClientPage.getInputSoldeTotal()) + ") KO");
                }
            } else {
                if (gestionClientPage.isSuspensionPM(suspension) || gestionClientPage.isSuspensionPM("MULTIPLE")) {
                    logSuccess("Check Suspension " + suspension + " pour " + nissorbce + " OK");
                } else {
                    logFailed("Check Suspension " + suspension + " pour " + nissorbce + " KO");
                }
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + nissorbce, e);
        }
    }

    public static void checkCreanceEtTypeOperationTrimestrePM(String nissorbce, String periode, String creance, String typeOperation, String dateOperation) {
        try {
            fillOperationComptable(nissorbce, periode);
            if (dateOperation == null) {
                gestionClientPage.fillDateValeurComptable(DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()));
            } else {
                gestionClientPage.fillDateValeurComptable(dateOperation);
            }
            gestionClientPage.clickBtnSearch();
            if (gestionClientPage.isExistOperationCreanceEtType(creance, typeOperation, false)) {
                logSuccess("Check operation " + creance + " " + typeOperation + " OK");
            } else {
                logFailed("Check operation " + creance + " " + typeOperation + " KO");
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + nissorbce, e);
        }
    }

    public static void checkRecouvrementPM(String nissorbce, String recouvrement, String periode) {
        try {
            loadNissOrBce(nissorbce);
            gestionClientPage.clickMenuCompteCotisantSoldes();
            gestionClientPage.fillPeriodeFromSoldes(periode);
            gestionClientPage.fillPeriodeToSoldes(periode);
            gestionClientPage.clickBtnSearch();
            if (gestionClientPage.isRecouvrementPM(recouvrement)) {
                logSuccess("Check Recouvrement " + recouvrement + " pour " + nissorbce + " OK");
            } else {
                logFailed("Check Recouvrement " + recouvrement + " pour " + nissorbce + " KO");
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + nissorbce, e);
        }
    }

    private static void fillOperationComptable(String nissorbce, String periode) {
        loadNissOrBce(nissorbce);
        gestionClientPage.clickMenuCompteCotisantOperations();
        gestionClientPage.fillPeriodeOperationComptable(periode);
    }

    private static String getCotisation(String nissorbce, String annee) {
        loadNissOrBce(nissorbce);
        gestionClientPage.clickMenuCompteCotisantSoldes();
        return gestionClientPage.getFromTableCotisationByAnnee(annee);
    }
}