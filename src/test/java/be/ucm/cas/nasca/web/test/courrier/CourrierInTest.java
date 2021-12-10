package be.ucm.cas.nasca.web.test.courrier;

import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.RunBatch;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.testng.annotations.Test;

import java.util.Date;

public class CourrierInTest extends CourrierTestBase {

    private static final String NUM_DOSSIER1 = "991001659";
    private static final String NUM_DOSSIER2 = "830208143";
    private static final String NUM_DOSSIER3 = "750222271";
    private static final String NUM_DOSSIER4 = "861110321";
    private static final String NUM_DOSSIER5 = "791103163";
    private static final String NUM_DOSSIER6 = "850110103";

    @Test
    public void verifierStatistiqueCourierIn() {
        initTest("Courrier entrant - statistiqueCourierIn", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        String object = TestData.OBJET_COTI_COUVERTURE_DOSSIER;
        String etat = TestData.ETAT_A_TRAITER;

        DaoFunctionality.getNascaDao().deleteCinStatistique(DateUtils.doFormat(TestData.DATE_FORMAT_XML, new Date()));

        CourrierTestBase.doCreationCourrierSimple(NUM_DOSSIER1, object, etat);

        RunBatch.runBatchStatCourrier(DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE_WITH_SLASH, new Date()));

        loginNasca();

        CourrierTestBase.doVerificationStatistique(NUM_DOSSIER1, object, "Entrés");

        finishTestExecution();
    }

    @Test
    public void encodageCourrierSimple() {
        initTest("Courrier entrant - Encodage simple", Thread.currentThread().getStackTrace()[1].getFileName());
        loginNasca();

        CourrierTestBase.doCreationCourrierSimple(NUM_DOSSIER1);
        CourrierTestBase.doTraitementCourrier(NUM_DOSSIER1);

        finishTestExecution();
    }

    @Test
    public void encodageCourrierMultiple() {
        initTest("Courrier entrant - Encodage multiple", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        CourrierTestBase.doCreationCourrierMultiple();

        finishTestExecution();
    }

    @Test
    public void gestionCodeBarreGED() {
        initTest("Creation Code-Barre GED", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        DaoFunctionality.getNascaDao().deleteAllGedDemande();

        CourrierTestBase.doCreationCodeBarreGED(NUM_DOSSIER2);

        CourrierTestBase.doGenerationCodeBarreGED(NUM_DOSSIER2);

        CourrierTestBase.doReactiverCodeBarreGED(NUM_DOSSIER2);

        CourrierTestBase.doSuppressionCodeBarreGED();

        finishTestExecution();
    }

    @Test
    public void gestionRetraitDossierOngletCourrier() {
        initTest("Gestion Retrait Dossier via onglet Courrier", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        CourrierTestBase.doDemandeRetraitDossier(NUM_DOSSIER3, "Courrier");

        CourrierTestBase.doSortirRetraitDossierJournalier(NUM_DOSSIER3);

        finishTestExecution();
    }

    @Test
    public void gestionRetraitDossierOngletUrgent() {
        initTest("Gestion Retrait Dossier via onglet Urgent", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        CourrierTestBase.doDemandeRetraitDossier(NUM_DOSSIER4, "Urgent");

        CourrierTestBase.doSortirRetraitDossierUrgent(NUM_DOSSIER4);

        finishTestExecution();
    }

    @Test
    public void gestionRetraitDossierOngletListe() {
        initTest("Gestion Retrait Dossier via onglet Liste", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        CourrierTestBase.doDemandeRetraitDossier(NUM_DOSSIER5, "Liste");

        CourrierTestBase.doSortirRetraitDossierListe(NUM_DOSSIER5);

        finishTestExecution();
    }

    @Test
    public void gestionRetraitDossierOngletUrgentClasseur() {
        initTest("Gestion Retrait Dossier via onglet Urgent classeur", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        CourrierTestBase.doDemandeRetraitDossier(NUM_DOSSIER6, "Urgent classeur");

        CourrierTestBase.doSortirRentrertDossierUrgentClasseur(NUM_DOSSIER6);

        finishTestExecution();
    }
}