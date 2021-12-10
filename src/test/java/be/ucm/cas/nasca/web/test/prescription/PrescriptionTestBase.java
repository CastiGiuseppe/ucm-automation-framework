package be.ucm.cas.nasca.web.test.prescription;

import be.ucm.cas.nasca.web.test.support.*;

import java.text.ParseException;

public class PrescriptionTestBase extends TestBase {

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    static boolean doPrescription(String nissOrBce, String action, String menu, String typePrescription, String partielleOuTotale, String modifDatePrescription, String periode, String typeAffilie) {
        try {
            loadNissOrBce(nissOrBce);
            switch (menu) {
                case "Gestion":
                    gestionPrescription(nissOrBce, action, typePrescription, partielleOuTotale, modifDatePrescription, periode, typeAffilie);
                    break;
                case "Suivi":
                    suiviPrescription(nissOrBce, action, typePrescription, partielleOuTotale, periode, typeAffilie);
                    break;
                case "Actes interruptifs":
                    actesInterruptifsPrescription(nissOrBce, action, typePrescription);
                    break;
                default:
                    break;
            }
            return false;
        } catch (Exception e) {
            if (e.getCause().toString().contains("org.openqa.selenium.NoSuchElementException") &&
                    (e.getCause().toString().contains("_editTypePrescriptionForm_sousTypeTraitement") || e.getCause().toString().contains("prescrirePartiellementForm_montantPartiel"))) {
                logSkip(Thread.currentThread().getStackTrace()[1].getFileName(), "Test dependant en echec");
            } else {
                logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + nissOrBce, e);
            }
            return true;
        }
    }

    private static void actesInterruptifsPrescription(String nissOrBce, String action, String typePrescription) {
        gestionClientPage.clickMenuPrescriptionActesInterruptifs();
        if ("Ajout".equals(action)) {
            gestionClientPage.clickBtnAjouterActeInterruptifPrescription();
            gestionClientPage.fillDateActeInterruptifPrescription(DateUtils.getDateToday());
            gestionClientPage.selectTypeActeInterruptifPrescription(typePrescription);
            gestionClientPage.clickBtnEnregistrerActeInterruptifPrescription();
            if (gestionClientPage.checkActeInterruptif(typePrescription)) {
                logSuccess(action + " " + typePrescription + " pour " + nissOrBce + " OK");
            } else {
                logFailed(action + " " + typePrescription + " pour " + nissOrBce + " KO");
            }
        } else {
            gestionClientPage.clickSuppressionActeInterruptif();
            if (gestionClientPage.checkTableActeInterruptifVide()) {
                logSuccess(action + " " + typePrescription + " pour " + nissOrBce + " OK");
            } else {
                logFailed(action + " " + typePrescription + " pour " + nissOrBce + " KO");
            }
        }
    }

    private static void suiviPrescription(String nissOrBce, String action, String typePrescription, String partielleOuTotale, String periode, String typeAffilie) {
        gestionClientPage.clickMenuPrescriptionSuivi();
        if ("Modification".equals(action)) {
            if (TestData.TYPE_PP.equals(typeAffilie)) {
                gestionClientPage.checkEntiteComptablePrescripte(periode, "11", "1");
            } else {
                gestionClientPage.checkEntiteComptablePrescripte(periode, "10", "1");
            }
            gestionClientPage.selectModificationTypePrescription(typePrescription);
            gestionClientPage.clickBtnEnregistrerModificationPrescription();
            SeleniumUtils.clickConfirmAlert();
            if (gestionClientPage.isPresentTableSuiviPrescription(periode, typePrescription)) {
                logSuccess(action + " " + typePrescription + " " + partielleOuTotale + " pour " + nissOrBce + " OK");
            } else {
                logFailed(action + " " + typePrescription + " " + partielleOuTotale + " pour " + nissOrBce + " KO");
            }
        } else {
            if (TestData.PARTIELLE.equals(partielleOuTotale)) {
                if (TestData.TYPE_PP.equals(typeAffilie)) {
                    gestionClientPage.clickSupprimerEntiteComptablePrescripte(TestData.COTISATION_PP);
                } else {
                    gestionClientPage.clickSupprimerEntiteComptablePrescripte(TestData.COTISATION_PP);
                }
                gestionClientPage.fillMontantPartielAnnulationPrescription();
                gestionClientPage.clickBtnEnregistrerAnnulationPrescription();
                if (!gestionClientPage.checkTableEntiteNonSoldeesVide()) {
                    logSuccess(action + " " + typePrescription + " " + partielleOuTotale + " pour " + nissOrBce + " OK");
                } else {
                    logFailed(action + " " + typePrescription + " " + partielleOuTotale + " pour " + nissOrBce + " KO");
                }
            } else {
                gestionClientPage.checkEntitesComptablesSuiviPrescription(periode);
                gestionClientPage.clickBtnSupprimerPrescription();
                SeleniumUtils.clickConfirmAlert();
                if (gestionClientPage.checkTableEntiteComptablePrescripteVide()) {
                    logSuccess(action + " " + typePrescription + " " + partielleOuTotale + " pour " + nissOrBce + " OK");
                } else {
                    logFailed(action + " " + typePrescription + " " + partielleOuTotale + " pour " + nissOrBce + " KO");
                }
            }
        }
    }

    private static void gestionPrescription(String nissOrBce, String action, String typePrescription, String partielleOuTotale, String modifDatePrescription, String periode, String typeAffilie) throws ParseException {
        gestionClientPage.clickMenuPrescriptionGestion();
        if (Boolean.valueOf(modifDatePrescription)) {
            gestionClientPage.checkEntitesComptablesSoldeesPrescription(periode);
            gestionClientPage.clickBtnDatePrescription();
            gestionClientPage.fillDatePrescription(DateUtils.getDateFutur(1000, TestData.DATE_FORMAT_SIMPLE));
            gestionClientPage.clickBtnEnregistrerDatePrescription();
            SeleniumUtils.clickConfirmAlert();
        }

        gestionClientPage.checkEntitesComptablesSoldeesPrescription(periode);
        gestionClientPage.clickBtnPrescrirePrescription();
        gestionClientPage.selectTypePrescription(typePrescription);
        gestionClientPage.clickBtnEnregistrerPassagePrescription();
        SeleniumUtils.clickConfirmAlert();

        gestionClientPage.clickMenuPrescriptionSuivi();
        if (gestionClientPage.isPresentTableSuiviPrescription(periode, typePrescription)) {
            logSuccess(action + " " + typePrescription + " " + partielleOuTotale + " pour " + nissOrBce + " OK");
        } else {
            logFailed(action + " " + typePrescription + " " + partielleOuTotale + " pour " + nissOrBce + " KO");
        }
    }
}