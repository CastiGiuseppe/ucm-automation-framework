package be.ucm.cas.nasca.web.test.affiliation.pm;

import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.TestData;

public class AffiliationPmAbstract extends AffiliationPmTestBase {

    void doAffiliationPmAccept(String libelle, String fileName, String fonction) {
        initTest(libelle, fileName);

        loginNasca();

        DaoFunctionality.cleanAllDataByIdentite(TestData.BCE_NUMBER);

        AffiliationPmTestBase.fillFormPhase1(TestData.BCE_NUMBER, null);

        AffiliationPmTestBase.sendSoap(TestData.SOAP_TC_ACCEPT_ROOT_NAME, TestData.BCE_NUMBER);

        AffiliationPmTestBase.fillFormPhase2AcceptPart1(TestData.BCE_NUMBER, TestData.NACE_CODE);

        AffiliationPmTestBase.requestReclamationElementManquant();

        AffiliationPmTestBase.fillFormPhase2AcceptPart2AfterApporteur(fonction);

        AffiliationPmTestBase.checkIfDocExist(TestData.BCE_NUMBER, TestData.DOC_ACCEPT_AFFI_PM);

        AffiliationPmTestBase.clientCheck(TestData.BCE_NUMBER, TestData.ACCEPTE);

        finishTestExecution();
    }

    void doAffiliationPmRefuse(String libelle, String filename, String fonction) {
        initTest(libelle, filename);

        loginNasca();

        DaoFunctionality.cleanAllDataByIdentite(TestData.BCE_NUMBER);

        AffiliationPmTestBase.fillFormPhase1(TestData.BCE_NUMBER, null);

        AffiliationPmTestBase.sendSoap(TestData.SOAP_TC_REFUSE_ROOT_NAME, TestData.BCE_NUMBER);

        AffiliationPmTestBase.fillFormPhase2(TestData.BCE_NUMBER, TestData.NACE_CODE, fonction);

        AffiliationPmTestBase.checkIfDocExist(TestData.BCE_NUMBER, TestData.DOC_ACCEPT_AFFI_PM);

        AffiliationPmTestBase.clientCheck(TestData.BCE_NUMBER, "Refusé");

        finishTestExecution();
    }
}