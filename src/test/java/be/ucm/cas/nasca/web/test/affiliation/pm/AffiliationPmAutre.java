package be.ucm.cas.nasca.web.test.affiliation.pm;

import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.RunBatch;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Iterator;

public class AffiliationPmAutre extends AffiliationPmTestBase {

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Affiliation.xlsx", "Affi PM", "Affiliation PM", "autre", null);
    }

    @Test(dataProvider = "data")
    public void affiliatioPmTest(String id, String libelle, String forme, String typeTest, String skip) {
        if (!Boolean.valueOf(skip)) {
            initTest("Sc." + id + " - " + libelle, Thread.currentThread().getStackTrace()[1].getFileName());

            String bce;

            loginNasca();

            if ("ASBL".equals(forme)) {
                bce = TestData.BCE_NUMBER_ASBL;
            } else if ("groupement".equals(forme)) {
                bce = TestData.BCE_NUMBER_GROUP;
            } else if ("étrangère".equals(typeTest)) {
                bce = TestData.BCE_NUMBER_ETRANGERE;
            } else {
                bce = TestData.BCE_NUMBER_AUTRE;
            }

            DaoFunctionality.cleanAllDataByIdentite(bce);

            switch (typeTest) {
//                case "eclaircissement" :
//                    doEclaircissement(bce);
//                    break;
                case "étrangère":
                    doAccept(bce);
                    break;
                case "refusée":
                    doRefus(bce, TestData.NACE_CODE, TestData.GERANT);
                    break;
                case "enquête":
                    doEnquete(bce);
                    break;
                default:
                    logFailed("Type de test non valide");
            }

            finishTestExecution();
        }
    }

//    private void doEclaircissement(String bce) {
//        //TODO test à faire après MEP
//        logSkip("Eclaircissement", "Apres MEP");
//        //fillFormPhase1Eclair(bce);
//    }

    private void doAccept(String bce) {
        fillFormPhase1(bce);

        sendSoap(TestData.SOAP_TC_ACCEPT_ROOT_NAME, bce);

        fillFormPhase2AcceptPart1(bce, TestData.NACE_CODE, null, null, false);

        fillFormPhase2AcceptPart2(TestData.ADMINISTRATEUR_DELEGUE);

        checkIfDocExist(bce, TestData.DOC_ACCEPT_AFFI_PM);

        AffiliationPmTestBase.clientCheck(bce, TestData.ACCEPTE);
    }

    private void doRefus(String bce, String nace, String fonction) {
        fillFormPhase1(bce);

        sendSoap(TestData.SOAP_TC_REFUSE_ROOT_NAME, bce);

        fillFormPhase2RefusPart1(bce, nace);

        fillFormPhase2AcceptPart2(fonction);

        checkIfDocExist(bce, TestData.DOC_ACCEPT_AFFI_PM);

        AffiliationPmTestBase.clientCheck(bce, TestData.REFUSE);
    }

    private void doEnquete(String bce) {
        RunBatch.runBatchChangeDate(Calendar.getInstance().get(Calendar.YEAR) + "-07-01");

        loginNasca();

        fillFormPhase1(bce, "01-07-" + Calendar.getInstance().get(Calendar.YEAR));

        sendSoap(TestData.SOAP_TC_ACCEPT_ROOT_NAME, bce);

//        fillFormPhase2Suspend()
    }
}