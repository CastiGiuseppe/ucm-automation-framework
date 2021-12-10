package be.ucm.cas.nasca.web.test.planapurement;

import be.ucm.cas.nasca.web.test.enrolement.pm.EnrolementPMTestBase;
import be.ucm.cas.nasca.web.test.enrolement.pp.EnrolementPPTestBase;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.EnumTypeTest;
import be.ucm.cas.nasca.web.test.support.TestBase;
import be.ucm.cas.nasca.web.test.support.TestData;

public class PlanApurementTestBase extends TestBase {

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    static String doCreateReportPA(String nissOrBce, String periode, String datePaiement, String observation, String typeAffilie, String lettreConfirmation) {
        try {
            loadNissOrBce(nissOrBce);
            gestionClientPage.clickMenuPlanApurement();
            gestionClientPage.clickMenuAjouterPA();
            gestionClientPage.checkIdentitePA();
            nascaPage.clickBtnSuivantWizard();

            if (TestData.TYPE_PM.equals(typeAffilie)) {
                periode = periode.substring(0, 4);
            }
            gestionClientPage.checkSoldesPeriodiquesPA(periode);
            nascaPage.clickBtnSuivantWizard();
            gestionClientPage.fillDatePaiementReportPA(datePaiement);
            if (Boolean.valueOf(lettreConfirmation)) {
                gestionClientPage.clickCheckLettreConfirmationPA();
            }
            nascaPage.clickBtnSuivantWizard();
            if (Boolean.valueOf(observation)) {
                gestionClientPage.selectTypeObservationPA("Courrier");
                gestionClientPage.fillObservationsPA("Test");
            }
            String soldePA = gestionClientPage.getTotalPA();
            nascaPage.clickBtnTerminerWizard();
            if (gestionClientPage.isPresentTablePlanApurement(TestData.REPORT_DATE_PAIEMENT, TestData.EN_COURS, TestData.OCTROI)) {
                logSuccess("Création d'un plan d'apurement pour " + nissOrBce + " OK");
            } else {
                logFailed("Création d'un plan d'apurement pour " + nissOrBce + " NOK");
            }
            return soldePA;
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + nissOrBce, e);
        }
        return null;
    }

    static void checkReportPA(String typeAffilie, String paiementTotal, String nissOrBce, String periode, String dateMoins10JoursStr, String soldePA) {
        loadNissOrBce(nissOrBce);
        gestionClientPage.clickMenuPlanApurement();
        if (Boolean.valueOf(paiementTotal)) {
            if (gestionClientPage.isPresentTablePlanApurement(TestData.REPORT_DATE_PAIEMENT, TestData.CLOTURE, "Plan avec report respecté")) {
                logSuccess("Plan d'apurement respecté pour " + nissOrBce + " OK");
            } else {
                logFailed("Plan d'apurement respecté pour " + nissOrBce + " NOK");
            }
            if (TestData.TYPE_PP.equals(typeAffilie)) {
                EnrolementPPTestBase.checkCreanceEtTypeOperationTrimestrePP(nissOrBce, periode, TestData.COTISATION, TestData.RECETTE, dateMoins10JoursStr);
            } else {
                EnrolementPMTestBase.checkCreanceEtTypeOperationTrimestrePM(nissOrBce, periode.substring(0, 4), TestData.COTISATION, TestData.RECETTE, dateMoins10JoursStr);
            }
        } else {
            if (gestionClientPage.isPresentTablePlanApurement(TestData.REPORT_DATE_PAIEMENT, TestData.CLOTURE, "Plan avec report non respecté")) {
                logSuccess("Plan d'apurement non respecté pour " + nissOrBce + " OK");
            } else {
                logFailed("Plan d'apurement non respecté pour " + nissOrBce + " NOK");
            }
            if (TestData.TYPE_PP.equals(typeAffilie)) {
                EnrolementPPTestBase.checkSuspensionEtSoldeAnneeTrimestrePP(nissOrBce, periode, "", TestData.PLAN_APUREMENT, soldePA, "");
            } else {
                EnrolementPMTestBase.checkSuspensionEtSoldeAnneeTrimestrePM(nissOrBce, periode.substring(0, 4), "", TestData.PLAN_APUREMENT, soldePA, "");
            }
        }
    }

    static void checkEchelonnementPA(String typeAffilie, String nissOrBce, String periode, String date, String leveeMajoration) {
        loadNissOrBce(nissOrBce);
        gestionClientPage.clickMenuPlanApurement();
        String raison = "Plan soldé";
        if (Boolean.valueOf(leveeMajoration)) {
            raison = "Plan soldé, Levée de Majorations à introduire";
        }
        if (gestionClientPage.isPresentTablePlanApurement(TestData.ECHELONNEMENT, TestData.CLOTURE, raison)) {
            logSuccess("Plan d'apurement soldé pour " + nissOrBce + " OK");
        } else {
            logFailed("Plan d'apurement soldé pour " + nissOrBce + " NOK");
        }
        if (TestData.TYPE_PP.equals(typeAffilie)) {
            EnrolementPPTestBase.checkCreanceEtTypeOperationTrimestrePP(nissOrBce, periode, TestData.COTISATION, TestData.RECETTE, date);
        } else {
            EnrolementPMTestBase.checkCreanceEtTypeOperationTrimestrePM(nissOrBce, periode.substring(0, 4), TestData.COTISATION, TestData.RECETTE, date);
        }
    }

    static void checkAcompteNonPayeEchelonnementPA(String nissOrBce) {
        loadNissOrBce(nissOrBce);
        gestionClientPage.clickMenuPlanApurement();
        if (gestionClientPage.isPresentTablePlanApurement(TestData.ECHELONNEMENT, TestData.EN_DEMANDE, "Demande d’un acompte")) {
            logSuccess("Plan d'apurement acompte non payé pour " + nissOrBce + " OK");
        } else {
            logFailed("Plan d'apurement acompte non payé pour " + nissOrBce + " NOK");
        }
    }

    static void checkSuspension(String typeAffilie, String nissOrBce, String periode, String soldePA) {
        if (TestData.TYPE_PP.equals(typeAffilie)) {
            EnrolementPPTestBase.checkSuspensionEtSoldeAnneeTrimestrePP(nissOrBce, periode, "", TestData.PLAN_APUREMENT, soldePA, "");
        } else {
            EnrolementPMTestBase.checkSuspensionEtSoldeAnneeTrimestrePM(nissOrBce, periode.substring(0, 4), "", TestData.PLAN_APUREMENT, soldePA, "");
        }
    }

    static String doCreateEchelonnementPA(String nissOrBce, String periode, String leveeMajoration, String acompte, String nbreMensualites, String observation, String typeAffilie) {
        try {
            loadNissOrBce(nissOrBce);
            gestionClientPage.clickMenuPlanApurement();
            gestionClientPage.clickMenuAjouterPA();
            gestionClientPage.checkIdentitePA();
            nascaPage.clickBtnSuivantWizard();

            if (TestData.TYPE_PM.equals(typeAffilie)) {
                periode = periode.substring(0, 4);
            }
            if (Boolean.valueOf(leveeMajoration)) {
                gestionClientPage.clickRadioLMRecuePA();
            }
            gestionClientPage.checkSoldesPeriodiquesPA(periode);
            nascaPage.clickBtnSuivantWizard();
            gestionClientPage.clickRadioEchelonnementPA();
            gestionClientPage.clickCheckCalculAutoMensualitePA();
            if (Boolean.valueOf(acompte)) {
                gestionClientPage.fillAcomptePA("30");
                gestionClientPage.fillDateEcheanceAcomptePA(DateUtils.getDateFutur(30, TestData.DATE_FORMAT_SIMPLE));
            }
            gestionClientPage.fillNombreMensualitesPA(nbreMensualites);
            gestionClientPage.fillDatePremiereMensualitePA(DateUtils.getDateFutur(60, TestData.DATE_FORMAT_SIMPLE));
            nascaPage.clickBtnSuivantWizard();
            if (Boolean.valueOf(observation)) {
                gestionClientPage.selectTypeObservationPA("Courrier");
                gestionClientPage.fillObservationsPA("Test");
            }
            String soldePA = gestionClientPage.getTotalPA();
            nascaPage.clickBtnTerminerWizard();
            if (gestionClientPage.isPresentTablePlanApurement(TestData.ECHELONNEMENT, Boolean.valueOf(acompte) ? TestData.EN_DEMANDE : TestData.EN_COURS, Boolean.valueOf(acompte) ? "Demande d’un acompte" : TestData.OCTROI)) {
                logSuccess("Création d'un plan d'apurement pour " + nissOrBce + " OK");
            } else {
                logFailed("Création d'un plan d'apurement pour " + nissOrBce + " NOK");
            }
            return soldePA;
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + nissOrBce, e);
        }
        return null;
    }

    static void checkOctroiEchelonnementPA(String nissOrBce) {
        try {
            loadNissOrBce(nissOrBce);
            gestionClientPage.clickMenuPlanApurement();
            if (gestionClientPage.isPresentTablePlanApurement(TestData.ECHELONNEMENT, TestData.EN_COURS, TestData.OCTROI)) {
                logSuccess("Check octroi d'un plan d'apurement pour " + nissOrBce + " OK");
            } else {
                logFailed("Check octroi d'un plan d'apurement pour " + nissOrBce + " NOK");
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + nissOrBce, e);
        }
    }

    static void checkPaiementAcompteEchelonnementPA(String nissOrBce, String periode, String typeAffilie, String date) {
        if (TestData.TYPE_PP.equals(typeAffilie)) {
            EnrolementPPTestBase.checkCreanceEtTypeOperationTrimestrePP(nissOrBce, periode, TestData.COTISATION, TestData.RECETTE, date);
        } else {
            EnrolementPMTestBase.checkCreanceEtTypeOperationTrimestrePM(nissOrBce, periode.substring(0, 4), TestData.COTISATION, TestData.RECETTE, date);
        }
    }
}