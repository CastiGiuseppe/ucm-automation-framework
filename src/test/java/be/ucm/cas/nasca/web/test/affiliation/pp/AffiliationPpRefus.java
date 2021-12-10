package be.ucm.cas.nasca.web.test.affiliation.pp;

import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.SeleniumUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

public class AffiliationPpRefus extends AffiliationPpTestBase {

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Affiliation.xlsx", "Affi PP", "Affiliation PP", "refus", null);
    }

    @Test(dataProvider = "data")
    public void affiliationPpExcel(String id, String libelle, String nature, String dpe, String dsg, String suite, String motif) {
        initTest("Sc." + id + " - " + libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        DaoFunctionality.cleanAllDataByIdentite(TestData.NISS_NUMBER);

        fillFormPhase1(TestData.NISS_NUMBER, nature, dpe, dsg);

        doSoapAndRetry(TestData.SOAP_TC_REFUSE_ROOT_NAME, TestData.NISS_NUMBER);

        SeleniumUtils.isLoading();

        fillFormPhase2Refuse();

        checkIfClient(TestData.NISS_NUMBER, "Refusé");

        checkIfDocExist(TestData.NISS_NUMBER, TestData.DOC_REFUSE_AFFI_PP);

        finishTestExecution();
    }
}