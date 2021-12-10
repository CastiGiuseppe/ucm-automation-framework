package be.ucm.cas.nasca.web.test.transfertin.pp;

import be.ucm.cas.nasca.web.test.support.*;

public class AffiliationPPTransfertInTestBase extends TestBase {
    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    static void chargementTransfertInFromFlatFile(String niss, String fileName) {
        try {
            DaoFunctionality.getNascaDao().prepareFluxInInjection(niss);

            BatchUtils.doSendFlatFilePPInastiBatch(fileName, niss);

            RunBatch.runBatchExtractFluxFromFile();
            RunBatch.runBatchAdaptInastiToNasca();
        } catch (Exception e) {
            logError("Demande Transfert In PP - Chargement Demande Transfert In Accepté", e);
        }
    }

    static void traitementDemandeTransfertInEnAttente(String niss) {
        try {
            homeMenuPage.clickOngletTaches();
            tachePage.clickBtnDeleteFiltreTache();
            tachePage.clickBtnResetTache();

            tachePage.clickTraiterTache(niss);
            affiliationPpWizardPage.clickBtnAddActivity();
            affiliationPpWizardPage.selectTypeActivite("Entreprise PP");
            affiliationPpWizardPage.doAddDetailActivity();
            affiliationPpWizardPage.selectProfession("000100 - Divers");
            affiliationPpWizardPage.doFillDateDebutProfessionAuto();
            affiliationPpWizardPage.clickBtnSaveProfessionIdentiteLiee();
            affiliationPpWizardPage.clickBtnSaveAddActivity();
            affiliationPpWizardPage.clickBtnNextWizardTransfert();
            affiliationPpWizardPage.dofillNom("ROBERT");
            affiliationPpWizardPage.dofillRedacteur("KEVIN");
            affiliationPpWizardPage.clickBtnSearch();
            affiliationPpWizardPage.clickSearchResult();
            affiliationPpWizardPage.clickBtnNextWizardTransfert();
            affiliationPpWizardPage.clickBtnNextWizardTransfert();
            affiliationPpWizardPage.selectSituationConjoint();
            nascaPage.clickBtnTerminerWizard();

            logSuccess("Demande Transfert In PP en attente OK");
        } catch (Exception e) {
            logError("Demande Transfert IN PP - Traitement Demande Transfert In en attente", e);
        }
    }

    static void checkIfClient(String niss) {
        try {
            loadNissOrBce(niss);
            if (signaletiquePage.isMenuCotisantDisplayed()) {
                logSuccess("Transfert IN PP - Personne physique " + niss + " est affiliée");
            } else {
                logFailed("Transfert IN PP - Personne physique " + niss + " n'est pas affiliée");
            }
        } catch (Exception e) {
            logError("Check si client", e);
        }
    }

    static void checkIfNotClient(String bce) {
        try {
            loadNissOrBce(bce);

            signaletiquePage.clickBtnOperationAdministrative();
            signaletiquePage.clickRadioFiltreTout();
            signaletiquePage.doFillFiltreTypeDemande(TestData.DECLARATION_AFFI_PP);
            signaletiquePage.clickBtnSearch();

            if (signaletiquePage.isDecisionEquals("refusé")) {
                logSuccess("Affiliation PP - Personne morale " + bce + " n'est pas affiliée");
            } else {
                logFailed("Affiliation PP - Personne morale " + bce + " est affiliée");
            }
        } catch (Exception e) {
            logError("Check si pas client", e);
        }
    }

    static void traitementDemandeTransfertDecision(String niss) {
        try {
            homeMenuPage.clickOngletTaches();
            tachePage.clickBtnDeleteFiltreTache();
            tachePage.clickBtnResetTache();

            tachePage.clickTraiterTache(niss);

            affiliationPpWizardPage.clickBtnNextWizardTransfert();
            affiliationPpWizardPage.dofillRedacteur("KEVIN");
            affiliationPpWizardPage.clickBtnNextWizardTransfert();
            affiliationPpWizardPage.clickBtnNextWizardTransfert();
            affiliationPpWizardPage.selectSituationConjoint();
            affiliationPpWizardPage.checkSuiteTache();
            nascaPage.clickBtnTerminerWizard();

            logSuccess("Demande Transfert In PP décision OK");
        } catch (Exception e) {
            logError("Demande Transfert IN PP - Traitement Demande Transfert In décision", e);
        }
    }
}