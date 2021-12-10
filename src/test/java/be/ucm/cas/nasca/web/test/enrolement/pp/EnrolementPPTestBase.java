package be.ucm.cas.nasca.web.test.enrolement.pp;

import be.ucm.cas.nasca.web.test.support.*;
import com.thoughtworks.selenium.SeleniumException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.math.BigDecimal;
import java.util.Date;

public class EnrolementPPTestBase extends TestBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    private static final BigDecimal ZERO_VALUE = BigDecimal.ZERO;

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.ENROLEMENT;
    }

    public static void doEnrolementPPPrepare(String niss, String dateExecution) {
        String numeroDossier = DaoFunctionality.getNascaDao().getNumeroFromNissUnique(niss);

        RunBatch.runBatchEnrolementPPAutomatiquePrepare(numeroDossier, dateExecution);
    }

    public static void doEnrolementPPAutomatique() {
        RunBatch.runBatchEnrolementPPAutomatique();
    }

    static void doGenerationDocumentEnrolementPPPrepareEtGeneration() {
        RunBatch.runBatchGenerationDocumentEnrolementPPPrepareEtGeneration(true, true, false);
    }

    public static void doGenerationDocumentEnrolementPPBlocage() {
        RunBatch.runBatchGenerationDocumentEnrolementPPPrepareEtGeneration(false, false, true);
    }

    public static void doGenerationAvisEcheanceDebloque() {
        RunBatch.runBatchGenerationDocumentAvisDebloque();
    }

    static void doMajorationsTrimestriellesPP(String niss, String date) {
        RunBatch.runBatchMajorationPPTrimestriel(niss, date);
    }

    static void doMajorationsAnnuellesPP(String niss, String date) {
        RunBatch.runBatchMajorationPPAnnuel(niss, date);
    }

    public static void deleteCotisationAnneeTrimestrePP(String niss, String trimestre) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuCompteCotisantSoldes();
            gestionClientPage.deleteCotisationByAnneeTrimestre(trimestre);
            logSuccess("Suppression cotisation après enrôlement OK");
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    public static void checkCotisationAnneeTrimestrePP(String niss, String trimestre) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuCompteCotisantSoldes();
            String cotisation = gestionClientPage.getFromTableCotisationByAnneeTrimestre(trimestre);

            Assert.assertNotNull(cotisation);
            logSuccess("Cotisation après enrôlement: " + niss, cotisation);
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            searchOperationComtpable(trimestre);
            checkOperationTrimestre(trimestre, niss);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    private static void checkOperationTrimestre(String trimestre, String nissorbce) {
        try {
            Assert.assertTrue(gestionClientPage.isExistOperationTrimestre(trimestre));
            logSuccess("Cotisation apres Enrolement");
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            String blob = DaoFunctionality.getNascaDao().getBlobAfterEnrolementFailed(nissorbce);
            logFailed("Cotisation NOK après enrôlement PP Verifier erreur business: ", blob);
        }
    }

    public static void checkCreanceEtTypeOperationTrimestrePP(String niss, String periode, String creance, String typeOperation, String dateOperation) {
        try {
            searchOperationComptableDateOp(niss, periode, dateOperation, true);
            boolean check = false;
            for (int i = 0; i < 3; i++) {
                if (checkAssertionOperation(creance, typeOperation)) {
                    check = true;
                }
            }
            if (check) {
                logSuccess("Check operation " + creance + " " + typeOperation + " OK");
            } else {
                logFailed("Check operation " + creance + " " + typeOperation + " KO");
            }
        } catch (SeleniumException e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    public static void checkCreanceEtTypeOperationAnnuleeTrimestrePP(String niss, String periode, String dateOperation) {
        try {
            searchOperationComptableDateOp(niss, periode, dateOperation, false);

            if (TableUtils.isTableVide("operationsComptablesTable")) {
                logSuccess("Check operation annulée OK");
            } else {
                logFailed("Check operation annulée KO");
            }
        } catch (SeleniumException e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    private static boolean checkAssertionOperation(String creance, String typeOperation) {
        try {
            Assert.assertTrue(gestionClientPage.isExistOperationCreanceEtType(creance, typeOperation, false));
            return true;
        } catch (AssertionError e) {
            LOGGER.info("L'opération n'existe pas");
        }
        return false;
    }

    public static boolean checkEnrolementFaitTrimestrePP(String niss, String periode, String creance, String typeOperation) {
        try {
            loadNissOrBce(niss);
            searchOperationComtpable(periode);
            return gestionClientPage.isExistOperationCreanceEtType(creance, typeOperation, false);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
        return false;
    }

    public static void checkSuspensionEtSoldeAnneeTrimestrePP(String niss, String periode, String creance, String suspension, String solde, String typeCreance) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuCompteCotisantSoldes();
            gestionClientPage.clickAffichageSoldeElementsSoldesMajorationsRegroupees(periode);
            gestionClientPage.filtreTableOperations(creance, typeCreance, suspension, "", TestData.TYPE_PP);
            if (solde != null) {
                checkSuspensionSoldeNull(solde, suspension, niss);
            } else {
                checkSuspensionSoldeNotNull(suspension, niss);
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    private static void checkSuspensionSoldeNull(String solde, String suspension, String nissorbce) {
        try {
            Assert.assertEquals(SeleniumUtils.deleteFormat(solde), SeleniumUtils.deleteFormat(gestionClientPage.getInputSoldeTotal()));
            logSuccess("Check Suspension " + suspension + " pour " + nissorbce + " (montant du plan égal au solde) OK");
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logFailed("Check Suspension " + suspension + " pour " + nissorbce + " (montant du plan " + solde + " différent du solde " + SeleniumUtils.deleteFormat(gestionClientPage.getInputSoldeTotal()) + ") KO");
        }
    }

    private static void checkSuspensionSoldeNotNull(String suspension, String nissorbce) {
        try {
            Assert.assertTrue(gestionClientPage.isSuspensionPP(suspension) || gestionClientPage.isSuspensionPP("MULTIPLE"));
            logSuccess("Check Suspension " + suspension + " pour " + nissorbce + " OK");
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logFailed("Check Suspension " + suspension + " pour " + nissorbce + " KO");
        }
    }

    static void checkMajorationPP(String niss, String trimestre, String dateComptable, String typeMajoration) {
        try {
            BigDecimal majorations = getMajoration(niss, trimestre, dateComptable, typeMajoration);
            Assert.assertNotEquals(majorations.compareTo(ZERO_VALUE), 0);
            logSuccess("Check majoration " + typeMajoration + " après enrôlement OK");
        } catch (AssertionError e) {
            logFailed("Check majoration " + typeMajoration + " après enrôlement KO");
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void checkPasMajorationPP(String niss, String trimestre, String dateComptable, String typeMajoration) {
        searchOperationComptable(niss, trimestre, TestData.ENROLEMENT, TestData.MAJORATION_PP, TestData.PAS_DE_REGROUPEMENT, dateComptable);
        if (TableUtils.isTableVide("operationsComptablesTable")) {
            logSuccess("Check pas majoration " + typeMajoration + " après enrôlement OK");
        } else {
            try {
                BigDecimal majorations = getMajoration(niss, trimestre, dateComptable, typeMajoration);
                Assert.assertNull(majorations);
                logSuccess("Check pas majoration " + typeMajoration + " après enrôlement OK");
            } catch (AssertionError e) {
                logFailed("Check pas majoration " + typeMajoration + " après enrôlement KO");
            } catch (Exception e) {
                logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
            }
        }
    }

    private static void checkCotisation(BigDecimal cotisationOperation, String cotisation, String cotisationb, boolean checkTotal) {
        if (cotisationOperation != null) {
            if ("0".equals(cotisation) && cotisationOperation.compareTo(ZERO_VALUE) < 0) {
                logSuccess("Cotisation OK", "cotisation NASCA:0.0" + System.getProperty("line.separator") + "cotisation test:" + cotisation);
            } else {
                BigDecimal cotSansSigne = cotisationOperation;
                if (cotisationOperation.compareTo(new BigDecimal(0)) < 0) {
                    cotSansSigne = cotisationOperation.negate();
                }
                if (SeleniumUtils.transformStringToBigDecimal(cotisation).equals(cotSansSigne)) {
                    logSuccess("Cotisation OK", "cotisation NASCA:" + cotSansSigne.toString() + System.getProperty("line.separator") + "cotisation test:" + cotisation);
                } else {
                    if (cotisationb != null && !StringUtils.isEmpty(cotisationb.trim())) {
                        if ("0".equals(cotisationb) && cotisationOperation.compareTo(ZERO_VALUE) < 0) {
                            logSuccess("Cotisation OK", "cotisation NASCA:0.0" + System.getProperty("line.separator") + "cotisation test:" + cotisationb);
                        } else {
                            cotSansSigne = cotisationOperation;
                            if (cotisationOperation.compareTo(new BigDecimal(0)) < 0) {
                                cotSansSigne = cotisationOperation.negate();
                            }
                            if (SeleniumUtils.transformStringToBigDecimal(cotisationb).equals(cotSansSigne)) {
                                logSuccess("Cotisation OK", "cotisation NASCA:" + cotSansSigne.toString() + System.getProperty("line.separator") + "cotisation test:" + cotisationb);
                            } else {
                                if (checkTotal) {
                                    logFailed("Cotisation NOK", "cotisation NASCA:" + cotSansSigne.toString() + System.getProperty("line.separator") + "cotisation test:" + cotisationb);
                                } else {
                                    throw new AssertionError();
                                }
                            }
                        }
                    } else {
                        if (checkTotal) {
                            logFailed("Cotisation NOK", "cotisation NASCA:" + cotSansSigne.toString() + System.getProperty("line.separator") + "cotisation test:" + cotisation);
                        } else {
                            throw new AssertionError();
                        }
                    }
                }
            }
        } else {
            if (checkTotal) {
                logFailed("Cotisation NOK", "cotisation NASCA:null" + System.getProperty("line.separator") + "au lieu de:" + cotisation);
            } else {
                throw new AssertionError();
            }
        }
    }

    public static void checkCotisationTrimestrePPChangementProfil(String niss, String trimestre, String typeCreance, String cotisation, String cotisationb, String codenature, boolean checkTotal) {
        BigDecimal cotisationOperation = BigDecimal.ZERO;

        try {
            if (trimestre != null) {
                searchOperationComptable(niss, trimestre, TestData.ENROLEMENT, TestData.COTISATION_PP, TestData.PAS_DE_REGROUPEMENT, null);

                if (checkTotal) {
                    cotisationOperation = gestionClientPage.getMontantOperationsFromFoot();
                } else {
                    cotisationOperation = gestionClientPage.getFromTableOperationCotisationByAnnee(trimestre, typeCreance, codenature);
                }

                if (cotisation == null || StringUtils.isEmpty(cotisation.trim())) {
                    checkCotisationNullOuZero(cotisationOperation);
                } else {
                    checkCotisation(cotisationOperation, cotisation, cotisationb, checkTotal);
                }
            }
        } catch (AssertionError e) {
            if (!checkTotal) {
                try {
                    cotisationOperation = gestionClientPage.getMontantOperationsFromFoot();

                    if (cotisation == null || StringUtils.isEmpty(cotisation.trim())) {
                        checkCotisationNullOuZero(cotisationOperation);
                    } else {
                        checkCotisation(cotisationOperation, cotisation, cotisationb, true);
                    }
                } catch (AssertionError e1) {
                    LOGGER.error(e1.getMessage());
                    logFailed("Cotisation NOK", "cotisation NASCA:" + cotisationOperation.toString() + System.getProperty("line.separator") + "au lieu de:null");
                }
            } else {
                LOGGER.error(e.getMessage());
                logFailed("Cotisation NOK", "cotisation NASCA:" + cotisationOperation.toString() + System.getProperty("line.separator") + "au lieu de:null");
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    private static void checkCotisationNullOuZero(BigDecimal cotisationOperation) {
        if (cotisationOperation == null || cotisationOperation.compareTo(ZERO_VALUE) < 0) {
            logSuccess("Cotisation OK", "cotisation NASCA:null" + System.getProperty("line.separator") + "cotisation test:null");
        } else {
            Assert.assertEquals(cotisationOperation.compareTo(ZERO_VALUE), 0);
            logSuccess("Cotisation OK", "cotisation NASCA:null" + System.getProperty("line.separator") + "cotisation test:null");
        }
    }

    static void checkCotisationEnrolee(String niss, String trimestre, String cotisation) {
        try {
            searchCotisation(niss, trimestre);
            Assert.assertEquals(SeleniumUtils.transformStringToBigDecimal(gestionClientPage.getSoldeTotal()), SeleniumUtils.transformStringToBigDecimal(cotisation));
            logSuccess("Cotisation OK", "cotisation NASCA:" + cotisation);
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logFailed("Cotisation NOK", "cotisation NASCA:" + gestionClientPage.getSoldeTotal() + " - Cotisation Test:" + cotisation);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    public static void checkRecouvrementPP(String niss, String recouvrement) {
        try {
            searchCotisation(niss, "");
            Assert.assertTrue(gestionClientPage.isRecouvrementPP(recouvrement));
            logSuccess("Check Recouvrement " + recouvrement + " pour " + niss + " OK");
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logFailed("Check Recouvrement " + recouvrement + " pour " + niss + " KO");
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    public static void checkAucuneOperationTrimestrePP(String niss, String trimestre, String dateComptable, String typeOperation, String creanceOperation, String typeRegroupementOperation) {
        try {
            searchOperationComptable(niss, trimestre, typeOperation, creanceOperation, typeRegroupementOperation, dateComptable);
            Assert.assertTrue(gestionClientPage.isTableOperationVide());
            logSuccess("Check pas d'operation comptable pour " + niss + " OK");
        } catch (AssertionError e) {
            if (SeleniumUtils.transformStringToBigDecimal(gestionClientPage.getSoldeOperations()).compareTo(ZERO_VALUE) < 0) {
                logSuccess("Check pas d'operation comptable pour " + niss + " OK");
            } else {
                LOGGER.error(e.getMessage());
                logFailed("Check pas d'operation comptable pour " + niss + " KO");
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    public static void checkCreanceEtTypeOperationPLCTrimestrePP(String niss, String periode, String creance, String typeOperation, String dateOperation) {
        try {
            searchOperationComptableDateOpPlc(niss, periode, dateOperation);

            if (gestionClientPage.isExistOperationCreanceEtType(creance, typeOperation, true)) {
                logSuccess("Check operation PLC " + creance + " " + typeOperation + " OK");
            } else {
                gestionClientPage.clickBtnNextOperation();
                if (gestionClientPage.isExistOperationCreanceEtType(creance, typeOperation, true)) {
                    logSuccess("Check operation PLC " + creance + " " + typeOperation + " OK");
                } else {
                    gestionClientPage.clickBtnNextOperation();
                    checkOperationPLCCreanceEtTypeExistance(creance, typeOperation);
                }
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    private static void checkOperationPLCCreanceEtTypeExistance(String creance, String typeOperation) {
        try {
            Assert.assertTrue(gestionClientPage.isExistOperationCreanceEtType(creance, typeOperation, true));
            logSuccess("Check operation PLC " + creance + " " + typeOperation + " OK");
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logFailed("Check operation PLC " + creance + " " + typeOperation + " KO");
        }
    }

    public static void checkSoldesCotisationPLCTrimestrePP(String niss, String trimestre, String cotisation) {
        String cotisationNasca = "";
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuComptePLCSoldes();
            cotisationNasca = gestionClientPage.getFromTableCotisationPLCByTrimestre(trimestre);
            Assert.assertEquals(SeleniumUtils.transformStringToBigDecimal(cotisation), SeleniumUtils.transformStringToBigDecimal(cotisationNasca));
            logSuccess("Cotisation PLC OK", "cotisation NASCA:" + cotisationNasca + System.getProperty("line.separator") + "cotisation test:" + cotisation);
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logFailed("Cotisation PLC NOK", "cotisation NASCA:" + cotisationNasca + System.getProperty("line.separator") + "cotisation test:" + cotisation);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    private static BigDecimal getMajoration(String nissorbce, String trimestre, String dateComptable, String typeMajoration) {
        searchOperationComptable(nissorbce, trimestre, TestData.ENROLEMENT, TestData.MAJORATION_PP, TestData.PAS_DE_REGROUPEMENT, dateComptable);
        return gestionClientPage.getFromTableMajorationsByAnnee(trimestre, typeMajoration);
    }

    private static void searchOperationComptable(String nissorbce, String trimestre, String typeOperation, String creanceOperation, String typeRegroupementOperation, String dateComptable) {
        loadNissOrBce(nissorbce);
        gestionClientPage.clickMenuCompteCotisantOperations();
        gestionClientPage.fillPeriodeOperationComptable(trimestre);
        gestionClientPage.doSelectTypeOperation(typeOperation);
        gestionClientPage.doSelectCreanceOperation(creanceOperation);
        gestionClientPage.doSelectTypeRegroupementOperation(typeRegroupementOperation);
        if (dateComptable != null) {
            gestionClientPage.fillDateValeurComptable(dateComptable);
        }
        gestionClientPage.clickBtnSearch();
    }

    private static void searchOperationComptableDateOp(String nissorbce, String periode, String dateOperation, boolean afficherOpAnnulees) {
        loadNissOrBce(nissorbce);
        gestionClientPage.clickMenuCompteCotisantOperations();
        doFillInfoOperationComptable(periode, dateOperation, afficherOpAnnulees);
    }

    private static void searchOperationComptableDateOpPlc(String nissorbce, String periode, String dateOperation) {
        loadNissOrBce(nissorbce);
        gestionClientPage.clickMenuComptePLCOperations();
        doFillInfoOperationComptable(periode, dateOperation, true);
    }

    private static void doFillInfoOperationComptable(String periode, String dateOperation, boolean afficherOpAnnulees) {
        gestionClientPage.fillPeriodeOperationComptable(periode);
        if (dateOperation == null) {
            gestionClientPage.fillDateValeurComptable(DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()));
        } else {
            gestionClientPage.fillDateValeurComptable(dateOperation);
        }
        if (!afficherOpAnnulees) {
            gestionClientPage.checkOperationsAnnulees();
        }
        gestionClientPage.clickBtnSearch();
    }

    private static void searchCotisation(String nissorbce, String trimestre) {
        loadNissOrBce(nissorbce);
        SeleniumUtils.isLoading();
        logSuccess("Niss utilise pour le test d'enrolement : " + nissorbce);
        gestionClientPage.clickMenuCompteCotisantSoldes();
        gestionClientPage.fillPeriodeFromSoldes(trimestre);
        gestionClientPage.fillPeriodeToSoldes(trimestre);
        gestionClientPage.selectTypeAffichageSolde(TestData.PAS_DE_REGROUPEMENT);
        gestionClientPage.clickBtnSearch();
    }

    private static void searchOperationComtpable(String trimestre) {
        gestionClientPage.clickMenuCompteCotisantOperations();
        gestionClientPage.fillPeriodeOperationComptable(trimestre);
        gestionClientPage.clickBtnSearch();
    }
}