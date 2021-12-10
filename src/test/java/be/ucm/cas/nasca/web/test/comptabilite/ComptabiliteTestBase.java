package be.ucm.cas.nasca.web.test.comptabilite;

import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.EnumTypeTest;
import be.ucm.cas.nasca.web.test.support.TestBase;
import be.ucm.cas.nasca.web.test.support.TestData;

import java.util.Date;

public class ComptabiliteTestBase extends TestBase {

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    public static void doAffectationPaiement(String niss, String trimestre,
                                             Boolean cotisation, Boolean majoration, String datePaiement) {
        try {
            rechercherAffectation(niss, datePaiement);
            if (comptabilitePage.isTableCreancesPPTrimestreExist(trimestre)) {
                comptabilitePage.clickTableCreancesPP(trimestre, cotisation, majoration, null);
                terminerAffectation(datePaiement);
            } else {
                logFailed("Affectation paiement, Trimestre:" + trimestre + " inexistant");
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    public static void doAffectationPaiement(String niss, String datePaiement) {
        try {
            rechercherAffectation(niss, datePaiement);
            comptabilitePage.clickTableCreancesPP(DateUtils.getYearNow() + DateUtils.getQuarterNumber(DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, new Date())),true, true, null);
            terminerAffectation(datePaiement);
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    public static void doAffectationPaiementPartielle(String niss, String trimestre,
                                                      Boolean cotisation, Boolean majoration, String datePaiement, String montantAAffecter) {
        try {
            rechercherAffectation(niss, datePaiement);
            if (comptabilitePage.isTableCreancesPPTrimestreExist(trimestre)) {
                comptabilitePage.clickTableCreancesPP(trimestre, cotisation, majoration, montantAAffecter);
                terminerAffectation(datePaiement);
            } else {
                logFailed("Affectation paiement, Trimestre:" + trimestre + " inexistant");
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    public static void doAffectationPaiementTotal(String niss,
                                                  Boolean cotisation, Boolean majoration, String datePaiement) {
        try {
            rechercherAffectation(niss, datePaiement);
            comptabilitePage.clickTableCreancesPP(null, cotisation, majoration, null);
            terminerAffectation(datePaiement);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    private static void rechercherAffectation(String niss, String datePaiement) {
        homeMenuPage.selectEquipe(TestData.COMPTABILITE);

        homeMenuPage.clickOngletComptabiliteEtFinances();
        comptabilitePage.clickMenuPaiementAAffecter();
        comptabilitePage.fillDateComptaDeCustom(datePaiement);
        comptabilitePage.fillDateComptaACustom(datePaiement);
        comptabilitePage.fillMontantDe();
        comptabilitePage.fillMontantA();
        comptabilitePage.clickBtnSearch();
        comptabilitePage.clickAffectPaiementTable();
        nascaPage.clickBtnSuivantWizard();
        comptabilitePage.doFillIdentitePayeur(niss);
        comptabilitePage.clickResultSearchIdentite();
        nascaPage.clickBtnSuivantWizard();
        nascaPage.clickBtnSuivantWizard();
    }

    private static void terminerAffectation(String datePaiement) {
        nascaPage.clickBtnTerminerWizard();
        comptabilitePage.clickMenuRapportEtValidation();
        comptabilitePage.fillDateExtraitRapport(datePaiement);
        comptabilitePage.selectCompteRapport("SSI");
        comptabilitePage.clickBtnSearch();
        comptabilitePage.selectOngletValidation();
        comptabilitePage.clickTableControlePaiements();
        if (comptabilitePage.btnConfirmerControleEnabled()) {
            comptabilitePage.clickTableControlePaiements();
        }
        comptabilitePage.clickBtnConfirmerControle();
        comptabilitePage.clickBtnOuiModal();
        if (nascaPage.isMessageErreur("stacktrace")) {
            logFailed("Affectation paiement ko");
        } else {
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }
}