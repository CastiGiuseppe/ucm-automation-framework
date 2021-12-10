package be.ucm.cas.nasca.web.test.affiliation.pm;

import be.ucm.cas.nasca.web.test.affiliation.pp.AffiliationPpTestBase;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.SeleniumUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AffiliationPmTest extends AffiliationPmAbstract {

    private final String fileName = Thread.currentThread().getStackTrace()[1].getFileName();

    @Test
    public void affiliationPmAdministrateurDelegueAcceptee() {
        doAffiliationPmAccept("Affiliation PM - Administrateur Delegue - Acceptee", fileName, TestData.ADMINISTRATEUR_DELEGUE);
    }

    @Test
    public void affiliationPmAdministrateurDelegueRefusee() {
        doAffiliationPmRefuse("Affiliation PM - Delegue de Gestion - Refusee", fileName, TestData.ADMINISTRATEUR_DELEGUE);
    }

    @Test
    public void affiliationPmDelegueGestionAcceptee() {
        doAffiliationPmAccept("Affiliation PM - Delegue de Gestion - Acceptee", fileName, TestData.DELEGUE_GESTION);
    }

    @Test
    public void affiliationPmDelegueGestionRefusee() {
        doAffiliationPmRefuse("Affiliation PM - Delegue de Gestion - Refusee", fileName, TestData.DELEGUE_GESTION);
    }

    @Test
    public void affiliationPmGerantAcceptee() {
        initTest("Affiliation PM - Gerant - Acceptee", fileName);

        loginNasca();

        DaoFunctionality.cleanAllDataByIdentite(TestData.NISS_NUMBER_01);
        DaoFunctionality.cleanAllDataByIdentite(TestData.BCE_NUMBER);

        AffiliationPpTestBase.fillFormPhase1(TestData.NISS_NUMBER_01);

        AffiliationPpTestBase.sendSoap(TestData.SOAP_TC_ACCEPT_ROOT_NAME, TestData.NISS_NUMBER_01);

        SeleniumUtils.waitForAction(7500);

        AffiliationPpTestBase.fillFormPhase2Accept();

        DaoFunctionality.getNascaDao().updateEvenementToActive(TestData.NISS_NUMBER_01);

        SeleniumUtils.waitForAction(7500);

        AffiliationPmTestBase.fillFormPhase1(TestData.BCE_NUMBER, null);

        AffiliationPmTestBase.sendSoap(TestData.SOAP_TC_ACCEPT_ROOT_NAME, TestData.BCE_NUMBER);

        AffiliationPmTestBase.fillFormPhase2Accept(TestData.BCE_NUMBER, TestData.NACE_CODE, TestData.GERANT, TestData.NISS_NUMBER_01);

        AffiliationPmTestBase.checkIfDocExist(TestData.BCE_NUMBER, TestData.DOC_ACCEPT_AFFI_PM);

        AffiliationPmTestBase.clientCheck(TestData.BCE_NUMBER, TestData.ACCEPTE);

        finishTestExecution();
    }

    @Test
    public void affiliationPmGerantRefusee() {
        doAffiliationPmRefuse("Affiliation PM - Gerant - Refusee", fileName, TestData.GERANT);
    }

    @BeforeTest
    @AfterTest
    public void cleanInastiHisto() {
        DaoFunctionality.cleanWsInastiExchange();
    }
}