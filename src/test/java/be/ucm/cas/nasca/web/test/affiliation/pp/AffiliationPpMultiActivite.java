package be.ucm.cas.nasca.web.test.affiliation.pp;

import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

public class AffiliationPpMultiActivite extends AffiliationPpTestBase {

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Affiliation.xlsx", "Affi PP", "Affiliation PP", "multiact", null);
    }

    @Test(dataProvider = "data")
    public void affiliationPpExcel(String id, String libelle, String nature, String dpe, String dsg, String suite, String typeAct1,
                                   String act1, String dtDebutAct1, String typeAct2, String act2, String dtDebutAct2) {
        initTest("Sc." + id + " - " + libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        DaoFunctionality.cleanAllDataByIdentite(TestData.NISS_NUMBER);

        fillFormPhase1Multi(TestData.NISS_NUMBER, nature, dpe, dsg, suite, typeAct1, act1, dtDebutAct1, typeAct2, act2, dtDebutAct2);

        doSoapAndRetry(TestData.SOAP_TC_ACCEPT_ROOT_NAME, TestData.NISS_NUMBER);

        fillFormPhase2Accept();

        checkIfClient(TestData.NISS_NUMBER, TestData.ACCEPTE);

        checkIfDocExist(TestData.NISS_NUMBER, TestData.DOC_ACCEPT_AFFI_PP);

        finishTestExecution();
    }
}