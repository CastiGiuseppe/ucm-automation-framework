package be.ucm.cas.nasca.web.test.affiliation.pp;

import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

public class AffiliationPpAcceptee extends AffiliationPpTestBase {

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Affiliation.xlsx", "Affi PP", "Affiliation PP", "acceptnm", null);
    }

    @Test(dataProvider = "data")
    public void AffiliationPpTest(String id, String libelle, String nature, String dpe, String dsg, String suite, String revenu, String niss) {
        initTest("Sc." + id + " - " + libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        DaoFunctionality.cleanAllDataByIdentite(niss);
        DaoFunctionality.cleanWsInastiExchange();

        fillFormPhase1(niss, nature, dpe, dsg);

        doSoapAndRetry(TestData.SOAP_TC_ACCEPT_ROOT_NAME, niss);

        fillFormPhase2Accept(null, null, nature, suite, revenu);

        checkIfClient(niss, TestData.ACCEPTE);

        checkIfDocExist(niss, TestData.DOC_ACCEPT_AFFI_PP);

        finishTestExecution();
    }
}