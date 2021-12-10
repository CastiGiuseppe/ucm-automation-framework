package be.ucm.cas.nasca.web.test.blocage;

import be.ucm.cas.nasca.web.test.enrolement.pm.EnrolementPMTestBase;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.RunBatch;
import be.ucm.cas.nasca.web.test.support.TestData;
import com.jcraft.jsch.SftpException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Calendar;

public class BlocageDossierPMTest extends BlocageDossierAbstract {

    private final Calendar cal = Calendar.getInstance();

    private static final String BCE_1 = "0468758240";
    private static final String BCE_2 = "0650578604";
    private static final String BCE_3 = "0651747255";

    private String annee = null;

    @BeforeTest
    public void launchBlocagePM() {
        cal.add(Calendar.YEAR, 1);
        RunBatch.runBatchChangeDate(DateUtils.doFormat(TestData.DATE_FORMAT_XML, cal.getTime()));
        annee = DateUtils.doFormat(TestData.DATE_FORMAT_YEAR_ONLY, cal.getTime());

        EnrolementPMTestBase.initialisationDateEnrolementAnnuel(String.valueOf(cal.get(Calendar.YEAR)), DateUtils.doFormat(TestData.DATE_FORMAT_XML, cal.getTime()));
    }

    @Test()
    public void blocagePMEtDocumentAEnvoyer() {
        doBlocagePM(BCE_1, cal, annee, TestData.MONTANT_BILAN_PM, TestData.MONTANT_COTISATION_PM, Thread.currentThread().getStackTrace()[1].getFileName(), "Blocage PM et envoyer le document");
    }

    @Test(priority = 1)
    public void blocagePMEtDocumentNePasEnvoyer() {
        doBlocagePM(BCE_2, cal, annee, TestData.MONTANT_BILAN_PM, TestData.MONTANT_COTISATION_PM, Thread.currentThread().getStackTrace()[1].getFileName(), "Blocage PM et ne pas envoyer le document");
    }

    @Test(priority = 2)
    public void blocagePMEtDocumentAEnvoyerATitreConservatoire() {
        doBlocagePM(BCE_3, cal, annee, TestData.MONTANT_BILAN_PM, TestData.MONTANT_COTISATION_PM, Thread.currentThread().getStackTrace()[1].getFileName(), "Blocage PM et envoyer le document a titre conservatoire");
    }

    @Test(priority = 3)
    public void tearDown() throws IOException, SftpException {
        tearDownEnrolement();
    }
}