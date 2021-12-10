package be.ucm.cas.nasca.web.test.affiliation.pm;

import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

public class AffiliationPmExo extends AffiliationPmTestBase {

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Affiliation.xlsx", "Affi PM", "Affiliation PM", "exo", null);
    }

    @Test(dataProvider = "data")
    public void affiliationPmTest(String id, String libelle, String exo, String reponse) {
        initTest("Sc." + id + " - " + libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        DaoFunctionality.cleanAllDataByIdentite(TestData.BCE_NUMBER_AUTRE);
        DaoFunctionality.cleanWsInastiExchange();

        fillFormPhase1(TestData.BCE_NUMBER_AUTRE);

        sendSoap(TestData.SOAP_TC_ACCEPT_ROOT_NAME, TestData.BCE_NUMBER_AUTRE);

        fillFormPhase2AcceptPart1(TestData.BCE_NUMBER_AUTRE, TestData.NACE_CODE, null, null, true);

        fillFormPhase2AcceptPart2(TestData.ADMINISTRATEUR_DELEGUE);

        traitementExoneration(TestData.BCE_NUMBER_AUTRE, exo, reponse);

        checkIfDocExist(TestData.BCE_NUMBER_AUTRE, "PM - Affiliation Transfert IN");

        checkIfDocExist(TestData.BCE_NUMBER_AUTRE, "PM - Exonération");

        AffiliationPmTestBase.clientCheck(TestData.BCE_NUMBER_AUTRE, TestData.ACCEPTE);

        finishTestExecution();
    }
}