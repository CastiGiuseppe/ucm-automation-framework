package be.ucm.cas.nasca.web.test.transfertin.pm;

import be.ucm.cas.nasca.web.test.support.*;

import java.util.Calendar;

public class AffiliationPMTransfertInTestBase extends TestBase {

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.TRF_PM;
    }

    static void fillFormPhase1TransfertIn(String bce) {
        try {
            homeMenuPage.clickOngletTaches();
            tachePage.clickBtnResetTache();
            tachePage.clickNouveauSocieteAffiliationTransfert();
            affiliationPmWizardPage.doSearchBCE(bce);
            Calendar caljour = Calendar.getInstance();
            caljour.set(Calendar.MONTH, Calendar.JANUARY);
            caljour.set(Calendar.DAY_OF_MONTH, 15);
            affiliationPmWizardPage.doFillDateReceptionCustom(DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, caljour.getTime()));
            affiliationPmWizardPage.doFillDateSignatureCustom(DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, caljour.getTime()));
            affiliationPmWizardPage.checkPresenceFormulaireDemission();
            logInfo("Phase 1", "Avant terminer");
            nascaPage.clickBtnSuivantWizard();
            nascaPage.clickBtnSuivantWizard();
            nascaPage.clickBtnTerminerWizard();

            if (tachePage.isEcranTache()) {
                logSuccess("Transfert IN PM - Demande créée OK");
            } else {
                logFailed("Transfert IN PM - Demande créée NOK", null);
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + bce, e);
        }
    }

    static void doTraitementDecision() {
        try {
            RunBatch.runBatchDecisionTransfertsInPM();
            logSuccess("Batch Décision Transfert In PM terminée");
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    static void checkIfClient(String bce, String decision) {
        try {
            loadNissOrBce(bce);
            SeleniumUtils.isLoading();

            signaletiquePage.clickBtnOperationAdministrative();
            signaletiquePage.clickRadioFiltreTout();
            signaletiquePage.doFillFiltreTypeDemande(TestData.DECLARATION_AFFI_PM);
            signaletiquePage.clickBtnSearch();

            checkDecision(bce, decision);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + bce, e);
        }
    }

    private static void checkDecision(String bce, String decision) {
        if (signaletiquePage.isDecisionEquals(decision)) {
            logSuccess("Tansfert IN PM - Personne morale " + bce + " - " + decision + " OK");
        } else {
            logFailed("Tansfert IN PM - Personne morale " + bce + " - " + decision + " KO");
        }
    }
}