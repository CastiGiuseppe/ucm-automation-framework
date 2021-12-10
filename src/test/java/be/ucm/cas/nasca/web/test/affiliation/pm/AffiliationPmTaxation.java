package be.ucm.cas.nasca.web.test.affiliation.pm;

import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

public class AffiliationPmTaxation extends AffiliationPmTestBase {

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Affiliation.xlsx", "Affi PM", "Affiliation PM", "taxationnm", null);
    }

    @Test(dataProvider = "data")
    public void affiliationTest(String id, String libelle, String forme, String bce) {
        initTest("Sc." + id + " - " + libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        DaoFunctionality.cleanAllDataByIdentite(bce);
        DaoFunctionality.cleanWsInastiExchange();

        fillFormPhase1(bce);

        sendSoap(TestData.SOAP_TC_ACCEPT_ROOT_NAME, bce);

        fillFormPhase2AcceptPart1(bce, TestData.NACE_CODE, null, null, false);

        if ("SPRL".equals(forme)) {
            fillFormPhase2AcceptPart2(TestData.DELEGUE_GESTION);
        } else {
            fillFormPhase2AcceptPart2(TestData.ADMINISTRATEUR_DELEGUE);
        }

        checkIfDocExist(bce, TestData.DOC_ACCEPT_AFFI_PM);

        AffiliationPmTestBase.clientCheck(bce, TestData.ACCEPTE);

        finishTestExecution();
    }
}