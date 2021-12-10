package be.ucm.cas.nasca.web.test.observations;

import be.ucm.cas.nasca.web.test.support.EnumTypeTest;
import be.ucm.cas.nasca.web.test.support.TestBase;

public class ObservationsTestBase extends TestBase {

    private static final String COMMENTAIRE = "Commentaire";
    private static final String REFERENCE = "REF";

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    static void creationObservation(String niss, String typeMedia, String type) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuObservations();
            gestionClientPage.selectTypeMediaObservation(typeMedia);
            gestionClientPage.selectTypeObservation(type);
            gestionClientPage.clickBtnSearchObservations();

            gestionClientPage.clickAjoutObservation();
            gestionClientPage.selectTypeListObservation(type);
            gestionClientPage.selectTypeMediaListObservation(typeMedia);
            gestionClientPage.fillReferenceObservation(REFERENCE + niss + typeMedia + type);
            gestionClientPage.fillCommentaireObservation(COMMENTAIRE + niss + typeMedia + type);
            gestionClientPage.clickBtnEnregistrerObservation();

            if (gestionClientPage.isObservationPresente(typeMedia, type, false)) {
                logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
            } else {
                logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void editionObservation(String niss, String typeMedia, String type) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuObservations();
            gestionClientPage.clickEditionObservationPresente();
            gestionClientPage.selectTypeListObservation(type);
            gestionClientPage.selectTypeMediaListObservation(typeMedia);
            gestionClientPage.fillReferenceObservation(REFERENCE + niss + typeMedia + type);
            gestionClientPage.fillCommentaireObservation(COMMENTAIRE + niss + typeMedia + type);
            gestionClientPage.clickBtnEnregistrerObservation();

            gestionClientPage.selectTypeMediaObservation(typeMedia);
            gestionClientPage.selectTypeObservation(type);
            gestionClientPage.clickBtnSearchObservations();

            if (gestionClientPage.isObservationPresente(typeMedia, type, false)) {
                logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
            } else {
                logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void archivageObservation(String niss, String typeMedia, String type) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuObservations();
            gestionClientPage.selectTypeMediaObservation(typeMedia);
            gestionClientPage.selectTypeObservation(type);
            gestionClientPage.clickBtnSearchObservations();

            gestionClientPage.clickArchivageObservationPresente(typeMedia, type);
            gestionClientPage.clickAlertConfirmArchivageObservation();

            gestionClientPage.clickAccordeonObservationsArchivees();
            if (gestionClientPage.isObservationPresente(typeMedia, type, true)) {
                logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
            } else {
                logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void creationObservationCessation(String niss, String typeMedia) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuClotureCessation();
            gestionClientPage.clickMenuCessationAjouterObservation();
            gestionClientPage.selectTypeMediaObservationCessation(typeMedia);
            gestionClientPage.fillReferenceObservationCessation(REFERENCE + niss + typeMedia);
            gestionClientPage.fillCommentaireObservationCessation(COMMENTAIRE + niss + typeMedia);
            gestionClientPage.clickBtnEnregistrerObservationCessation();

            gestionClientPage.clickMenuObservations();
            if (gestionClientPage.isObservationPresente(typeMedia, "Cessation", false)) {
                logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
            } else {
                logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void checkObservation(String niss, String typeMedia, String type) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuObservations();
            gestionClientPage.selectTypeMediaObservation(typeMedia);
            gestionClientPage.selectTypeObservation(type);
            gestionClientPage.clickBtnSearchObservations();

            if (gestionClientPage.isObservationPresente(typeMedia, type, false)) {
                logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), type);
            } else {
                logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), type);
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }
}