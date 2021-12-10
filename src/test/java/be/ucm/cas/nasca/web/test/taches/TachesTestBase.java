package be.ucm.cas.nasca.web.test.taches;

import be.ucm.cas.nasca.web.test.plc.PLCTestBase;
import be.ucm.cas.nasca.web.test.support.*;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;

public class TachesTestBase extends TestBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    public static void searchAndFinishTacheEnrolementTrimestriel(String niss, String action) {
        try {
            homeMenuPage.clickOngletTaches();
            tachePage.clickBtnResetTache();
            tachePage.clickBtnDeleteFiltreTache();
            tachePage.fillNumero(niss);
            tachePage.selectTypetache("Enrôlement trimestriel - Envoi manuel de l’avis");
            tachePage.clickBtnSearchTache();
            tachePage.clickTraiterTache(niss);

            tachePage.selectActionAvis(action);
            tachePage.clickBtnNotificationFermer();
            logSuccess("Clôture de la tâche Enrôlement trimestriel - Envoi manuel de l’avis");
        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
            logFailed("Notification Tache Enrôlement trimestriel - Envoi manuel de l’avis absente");
        } catch (Exception e1) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e1);
        }
    }

    public static void searchAndFinishTacheEnrolementAnnuel(String bce, String action) {
        try {
            homeMenuPage.clickOngletTaches();
            tachePage.clickBtnResetTache();
            tachePage.clickBtnDeleteFiltreTache();
            tachePage.fillNumero(bce);
            tachePage.selectTypetache("Enrôlement annuel - Envoi courrier");
            tachePage.clickBtnSearchTache();
            tachePage.clickTraiterTache(bce);

            tachePage.selectActionAvis(action);
            tachePage.clickBtnNotificationFermer();
            logSuccess("Clôture de la tâche Enrôlement annuel - Envoi courrier");
        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
            logFailed("Notification Tache Enrôlement annuel - Envoi courrier absente");
        } catch (Exception e1) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + bce, e1);
        }
    }

    public static void searchAndFinishTachePLC(String niss, String typeTache, String datePriseEffet, String decision, String motifRefus, String statut) {
        try {
            homeMenuPage.clickOngletTaches();
            tachePage.clickBtnResetTache();
            tachePage.clickBtnDeleteFiltreTache();
            tachePage.fillNumero(niss);
            tachePage.selectTypetache(typeTache);
            tachePage.clickBtnSearchTache();
            tachePage.clickTraiterTache(niss);
            PLCTestBase.traiterDemandeNouveauContratPLC(niss, datePriseEffet, decision, motifRefus, statut);
            logSuccess("Clôture de la tâche " + typeTache);
        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
            logFailed("Notification Tache " + typeTache + " absente");
        } catch (Exception e1) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e1);
        }
    }

    public static void searchAndFinishNotificationSansIdentifiant(String numero, String typeTache) {
        try {
            homeMenuPage.clickOngletTaches();
            tachePage.clickBtnResetTache();
            tachePage.clickBtnDeleteFiltreTache();
            tachePage.fillNumero("");
            tachePage.selectTypetache(typeTache);
            tachePage.clickBtnSearchTache();
            tachePage.clickTraiterTache(typeTache);

            tachePage.clickBtnNotificationFermer();
            logSuccess("Clôture de la tâche notification " + typeTache);
        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
            logFailed("Notification Tache " + typeTache + " absente");
        } catch (Exception e1) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + numero, e1);
        }
    }

    public static void searchAndFinishNotification(String numero, String typeTache) {
        try {
            homeMenuPage.clickOngletTaches();
            tachePage.clickBtnResetTache();
            tachePage.fillNumero(numero);
            tachePage.clickBtnDeleteFiltreTache();
            tachePage.selectTypetache(typeTache);
            tachePage.clickBtnSearchTache();
            tachePage.clickTraiterTache(numero);

            tachePage.clickBtnNotificationFermer();
            logSuccess("Clôture de la tâche notification " + typeTache);
        } catch (NoSuchElementException e) {
            LOGGER.error(e.getMessage());
            logFailed("Notification Tache " + typeTache + " absente");
        } catch (Exception e1) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + numero, e1);
        }
    }

    static void doSearchTache(String statut, String typeWorkflow,
                              String typeTache, String niss, String groupe,
                              String sousGroupe, String attribueePar, String tacheInterne,
                              String tacheExterne, String urgente) {
        try {
            //Création d'une tâche Affiliation PP
            homeMenuPage.clickOngletTaches();
            tachePage.clickBtnResetTache();
            affiliationPpWizardPage.doOpenAffiliationPP();
            affiliationPpWizardPage.doSearchNISS(niss);
            nascaPage.clickBtnQuitterWizard();

            getAndUpdateTacheDB(statut, niss, tacheExterne, urgente);

            if (!StringUtils.isEmpty(statut.trim())) {
                tachePage.selectStatutTache(statut);
            }
            if (!StringUtils.isEmpty(typeWorkflow.trim())) {
                tachePage.selectTypeWorkflow(typeWorkflow);
            }
            if (!StringUtils.isEmpty(typeTache.trim())) {
                tachePage.selectTypetache(typeTache);
            }
            tachePage.fillNumero(niss);
            if (Boolean.valueOf(groupe)) {
                tachePage.fillGroupeTache(niss.substring(1, 2));
            }
            if (Boolean.valueOf(sousGroupe)) {
                tachePage.fillSousGroupeTache(niss.substring(7, 9));
            }
            if (!StringUtils.isEmpty(attribueePar.trim())) {
                tachePage.selectAttribueeParTache(attribueePar);
            }
            if (Boolean.valueOf(urgente)) {
                tachePage.checkRadioUrgent();
            }
            if (!Boolean.valueOf(tacheInterne)) {
                tachePage.deCheckRadioTacheInterne();
            }
            if (!Boolean.valueOf(tacheExterne)) {
                tachePage.deCheckRadioTacheExterne();
            }
            tachePage.clickBtnSearchTache();
            if (tachePage.isTacheExist(SeleniumUtils.formatNissOrBceOrNumeroClient(niss))) {
                logSuccess("Tâche présente OK");
            } else {
                logFailed("Tâche présente KO");
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    private static void getAndUpdateTacheDB(String statut, String niss, String tacheExterne, String urgente) {
        String tacheIk = DaoFunctionality.getNascaDao().getTacheIkFromNiss(niss);

        String statutDb = getStatutDB(statut);

        String urgenteDB = "F";
        if (Boolean.valueOf(urgente)) {
            urgenteDB = "T";
        }

        String notificationDB = "F";
        if (Boolean.valueOf(tacheExterne)) {
            notificationDB = "T";
        }

        DaoFunctionality.getNascaDao().updateTache(urgenteDB, statutDb, notificationDB, tacheIk);
    }

    private static String getStatutDB(String statut) {
        String statutDb;
        switch (statut) {
            case "En cours":
                statutDb = "EN_COURS";
                break;
            case "Cloturée":
                statutDb = "CLOTUREE";
                break;
            case "Suspendue":
                statutDb = "SUSPENDUE";
                break;
            default:
                statutDb = "A_TRAITER";
                break;
        }
        return statutDb;
    }

    static void doEditTache(String niss, String action2) {
        try {
            //Création d'une tâche Affiliation PP
            homeMenuPage.clickOngletTaches();
            tachePage.clickBtnResetTache();
            affiliationPpWizardPage.doOpenAffiliationPP();
            affiliationPpWizardPage.doSearchNISS(niss);
            nascaPage.clickBtnQuitterWizard();

            tachePage.fillNumero(niss);
            tachePage.selectAttribueeParTache("CASTIGLIONE");
            tachePage.clickBtnSearchTache();

            tachePage.clickEditerTache(niss);

            switch (action2) {
                case "Date d'échéance":
                    editionDateEcheance();
                    break;
                case "Attribution":
                    editionAttribution();
                    break;
                case "Rôle":
                    editionRole(niss);
                    break;
                case "Urgente":
                    editionUrgente();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    private static void editionUrgente() {
        tachePage.checkRadioUrgente();
        tachePage.clickBtnEnregistrerEditTache();
        if (tachePage.isTacheUrgente()) {
            logSuccess("Tâche - modifification Urgente OK");
        } else {
            logFailed("Tâche - modifification Urgente KO");
        }
    }

    private static void editionRole(String niss) {
        tachePage.selectRoleTache("ROLE_NASCA_CAS_VALIDATEUR");
        tachePage.clickAddRoleTache();
        tachePage.clickBtnEnregistrerEditTache();

        tachePage.clickEditerTache(niss);
        tachePage.selectCandidatTache("ROLE_NASCA_CAS_VALIDATEUR");
        tachePage.clickDeleteRoleTache();
        tachePage.clickBtnEnregistrerEditTache();

        logSuccess("Tâche - modifification rôle OK");
    }

    private static void editionAttribution() {
        tachePage.selectAttribueeATache("ASSAFI");
        tachePage.clickBtnEnregistrerEditTache();

        if (tachePage.checkTache("ASSAFI Khaled")) {
            logSuccess("Tâche - modifification attribution OK");
        } else {
            logFailed("Tâche - modifification attribution KO");
        }
    }

    private static void editionDateEcheance() throws ParseException {
        tachePage.fillDateEcheanceTAche(DateUtils.getDateFutur(1, TestData.DATE_FORMAT_SIMPLE_WITH_SLASH));
        tachePage.clickBtnEnregistrerEditTache();

        if (tachePage.checkTache(DateUtils.getDateFutur(1, TestData.DATE_FORMAT_SIMPLE_WITH_SLASH))) {
            logSuccess("Tâche - modifification date échéance OK");
        } else {
            logFailed("Tâche - modifification date échéance KO");
        }
    }
}