package be.ucm.cas.nasca.web.test.blocage;

import be.ucm.cas.nasca.web.test.enrolement.pp.EnrolementPPTestBase;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateQuarters;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import com.jcraft.jsch.SftpException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Calendar;

public class BlocageDossierPPTest extends BlocageDossierAbstract {

    private Calendar calJour;

    private static final String NISS_1 = "83051732372";
    private static final String NISS_2 = "77050707113";
    private static final String NISS_3 = "82082425527";

    private String trimestreEnCours;

    @Test()
    public void blocageEtEnrolement() throws Throwable {
        calJour = Calendar.getInstance();

        calJour.set(Calendar.HOUR_OF_DAY, 0);
        calJour.set(Calendar.MINUTE, 0);
        calJour.set(Calendar.SECOND, 0);
        calJour.set(Calendar.MILLISECOND, 0);

        trimestreEnCours = DateQuarters.getQuarterCurrent(calJour.getTime());

        initTest("Blocage PP - enrolement", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        // Blocage des 3 dossiers
        BlocageTestBase.fillBlocageCourrier(NISS_1, TestData.TYPE_PP);
        EnrolementPPTestBase.deleteCotisationAnneeTrimestrePP(NISS_1, trimestreEnCours);

        BlocageTestBase.fillBlocageCourrier(NISS_2, TestData.TYPE_PP);
        EnrolementPPTestBase.deleteCotisationAnneeTrimestrePP(NISS_2, trimestreEnCours);

        BlocageTestBase.fillBlocageCourrier(NISS_3, TestData.TYPE_PP);
        EnrolementPPTestBase.deleteCotisationAnneeTrimestrePP(NISS_3, trimestreEnCours);

        // Prepare enrolement
        DaoFunctionality.getNascaDao().updateExecuteFalseEnrolementPp(trimestreEnCours.substring(0, 4), trimestreEnCours.substring(4, 5), DateUtils.doFormat(TestData.DATE_FORMAT_XML_WITH_HOUR, calJour.getTime()));
        EnrolementPPTestBase.doEnrolementPPPrepare(NISS_1, DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE_WITH_SLASH, calJour.getTime()));
        EnrolementPPTestBase.doEnrolementPPPrepare(NISS_2, DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE_WITH_SLASH, calJour.getTime()));
        EnrolementPPTestBase.doEnrolementPPPrepare(NISS_3, DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE_WITH_SLASH, calJour.getTime()));

        loginNasca();
        // Enrolement
        EnrolementPPTestBase.doEnrolementPPAutomatique();

        loginNasca();
        // Prepare et génération document
        EnrolementPPTestBase.doGenerationDocumentEnrolementPPBlocage();

        finishTestExecution();
    }

    @Test(priority = 1)
    public void traitementTacheEtDocumentAEnvoyer() throws Throwable {
        doTraitementTacheEtDocument("Blocage PP - Traitement tâche - Document à envoyer", Thread.currentThread().getStackTrace()[1].getFileName(), NISS_1, trimestreEnCours, TestData.AVIS_ENROLEMENT_A_ENVOYER, calJour, TestData.ETAT_A_IMPRIMER);
    }

    @Test(priority = 2)
    public void traitementTacheEtDocumentNePasEnvoyer() throws Throwable {
        doTraitementTacheEtDocument("Blocage PP - Traitement tâche - Document à ne pas envoyer", Thread.currentThread().getStackTrace()[1].getFileName(), NISS_2, trimestreEnCours, TestData.AVIS_ENROLEMENT_NE_PAS_ENVOYER, calJour, TestData.ETAT_ANNULE);
    }

    @Test(priority = 3)
    public void traitementTacheEtDocumentConservatoire() throws Throwable {
        doTraitementTacheEtDocument("Blocage PP - Traitement tâche - Document à envoyer à titre conservatoire", Thread.currentThread().getStackTrace()[1].getFileName(), NISS_3, trimestreEnCours, TestData.AVIS_ENROLEMENT_A_ENVOYER_CONSERVATOIRE, calJour, TestData.ETAT_A_IMPRIMER);
    }

    @Test(priority = 4)
    public void tearDown() throws IOException, SftpException {
        tearDownEnrolement();
    }
}