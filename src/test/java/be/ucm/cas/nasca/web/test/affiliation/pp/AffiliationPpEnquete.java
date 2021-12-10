package be.ucm.cas.nasca.web.test.affiliation.pp;

import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

public class AffiliationPpEnquete extends AffiliationPpTestBase {

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Affiliation.xlsx", "Affi PP", "Affiliation PP", "enquete", null);
    }

    @Test(dataProvider = "data")
    public void affiliationPpExcel(String id, String libelle, String nature, String dpe, String dsg, String suite, String motif) {
        initTest("Sc." + id + " - " + libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        switch (suite) {
            case "EA":
                affiliationEnEnqueteAcceptee(nature, dpe, dsg, suite);
                break;
            case "ER":
                affiliationEnEnqueteRefusee(nature, dpe, dsg, motif);
                break;
            default:
                break;
        }

        finishTestExecution();
    }

    private void affiliationEnEnqueteRefusee(String nature, String dpe, String dsg, String motif) {
        doEnquete(chooseTestDataRefus(motif), TestData.SOAP_TC_REFUSE_ROOT_NAME, nature, dpe, dsg);
        fillFormPhase2AfterSuspendRefuse(TestData.NISS_NUMBER);
        checkIfClientEnquete(TestData.NISS_NUMBER, "Refusé");
        checkIfDocExist(TestData.NISS_NUMBER, TestData.DOC_ENQUETE_AFFI_PP);
        checkIfDocExist(TestData.NISS_NUMBER, TestData.DOC_REFUSE_AFFI_PP);
    }

    private void affiliationEnEnqueteAcceptee(String nature, String dpe, String dsg, String suite) {
        doEnquete(TestData.NISS_NUMBER, TestData.SOAP_TC_ACCEPT_ROOT_NAME, nature, dpe, dsg);
        fillFormPhase2AfterSuspendAccept(TestData.NISS_NUMBER, null, null, nature, suite, null);
        checkIfClientEnquete(TestData.NISS_NUMBER, TestData.ACCEPTE);
        checkIfDocExist(TestData.NISS_NUMBER, TestData.DOC_ENQUETE_AFFI_PP);
        checkIfDocExist(TestData.NISS_NUMBER, TestData.DOC_ACCEPT_AFFI_PP);
    }

    private void doEnquete(String niss, String inastianswer, String nature, String dpe, String dsg) {
        DaoFunctionality.cleanAllDataByIdentite(niss);
        fillFormPhase1(niss, nature, dpe, dsg);
        doSoapAndRetry(TestData.SOAP_TC_SUSPEND_ROOT_NAME, niss);
        fillFormPhase2Suspend(inastianswer, niss);
    }

    private String chooseTestDataRefus(String motif) {
        //TODO Ajouter les motifs de refus (attente feedback Julien)
        switch (motif) {
            case "19":
                return TestData.NISS_NUMBER;
            case ">trim019": //return TestData.TestData.NISS_NUMBER_NUMBER_REFUS_TRIM019;
            case "autrecas": //return TestData.TestData.NISS_NUMBER_NUMMBER_REFUS_AUTRECAS;
            default:
                return TestData.NISS_NUMBER;
        }
    }
}