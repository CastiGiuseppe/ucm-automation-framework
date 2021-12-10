package be.ucm.cas.nasca.web.test.blocage;

import be.ucm.cas.nasca.web.test.enrolement.pm.EnrolementPMTestBase;
import be.ucm.cas.nasca.web.test.enrolement.pp.EnrolementPPTestBase;
import be.ucm.cas.nasca.web.test.impression.ImpressionTestBase;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import be.ucm.cas.nasca.web.test.taches.TachesTestBase;

import java.util.Calendar;

public abstract class BlocageDossierAbstract extends BlocageTestBase {

    private static final String NOM_DOCUMENT_PM = "PM - Enrôlement - Avis d'échéance";
    private static final String NOM_DOCUMENT_PP = "PP - Enrôlement Avis";

    void doBlocagePM(String bce, Calendar cal, String annee, String montantBilan, String cotisation, String fileName, String libelle) {
        initTest(libelle, fileName);

        loginNasca();

        EnrolementPMTestBase.updateDateEnrolementAnnuelExecute(String.valueOf(cal.get(Calendar.YEAR)));

        BlocageTestBase.fillBlocageCourrier(bce, TestData.TYPE_PM);

        EnrolementPMTestBase.doEnrolementPMAvecBlocage(bce, annee, DateUtils.doFormat(TestData.DATE_FORMAT_XML, cal.getTime()), montantBilan, cotisation);

        loginNasca();

        EnrolementPMTestBase.checkCotisationAnneePM(bce, annee);

        ImpressionTestBase.checkImpression(bce, TestData.TYPE_BCE, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, cal.getTime()), null, null, TestData.ETAT_SUSPENDU, NOM_DOCUMENT_PM, null);

        TachesTestBase.searchAndFinishTacheEnrolementAnnuel(bce, TestData.AVIS_ENROLEMENT_A_ENVOYER);

        ImpressionTestBase.checkImpression(bce, TestData.TYPE_BCE, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, cal.getTime()), null, null, TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT_PM, null);

        BlocageTestBase.fillDeblocageCourrier(bce, TestData.TYPE_PM);

        finishTestExecution();
    }

    void doTraitementTacheEtDocument(String libelle, String fileName, String niss1, String trimestreEnCours, String action, Calendar calJour, String etatImpression) {
        initTest(libelle, fileName);

        loginNasca();

        EnrolementPPTestBase.checkCotisationAnneeTrimestrePP(niss1, trimestreEnCours);

        ImpressionTestBase.checkImpression(niss1, TestData.TYPE_NISS, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, calJour.getTime()), null, null, TestData.ETAT_SUSPENDU, NOM_DOCUMENT_PP, null);

        TachesTestBase.searchAndFinishTacheEnrolementTrimestriel(niss1, action);

        EnrolementPPTestBase.doGenerationAvisEcheanceDebloque();

        ImpressionTestBase.checkImpression(niss1, TestData.TYPE_NISS, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, calJour.getTime()), null, null, etatImpression, NOM_DOCUMENT_PP, null);

        BlocageTestBase.fillDeblocageCourrier(niss1, TestData.TYPE_PP);

        finishTestExecution();
    }
}
