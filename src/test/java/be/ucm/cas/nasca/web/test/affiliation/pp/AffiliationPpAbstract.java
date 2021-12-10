package be.ucm.cas.nasca.web.test.affiliation.pp;

import be.ucm.cas.nasca.web.test.support.BatchUtils;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.RunBatch;
import be.ucm.cas.nasca.web.test.support.TestData;

public abstract class AffiliationPpAbstract extends AffiliationPpTestBase {

    void affiliationPpTardive(String nature, String dpe, String dsg, String suite, String revenu) {
        loginNasca();

        DaoFunctionality.cleanAllDataByIdentite(TestData.NISS_NUMBER);

        fillFormPhase1(TestData.NISS_NUMBER, nature, dpe, dsg);

        doSoapAndRetry(TestData.SOAP_TC_ACCEPT_ROOT_NAME, TestData.NISS_NUMBER);

        fillFormPhase2Accept(null, null, nature, suite, revenu);

        checkIfClient(TestData.NISS_NUMBER, TestData.ACCEPTE);

        checkIfDocExist(TestData.NISS_NUMBER, TestData.DOC_ACCEPT_AFFI_PP);
    }

    void doBaseDepistage() throws Exception {
        loginNasca();

        DaoFunctionality.cleanAllDataByIdentite(TestData.NISS_NUMBER_CJT_AIDEE);
        DaoFunctionality.cleanAllFluxByIdentite(TestData.NISS_NUMBER_CJT_AIDANT);

        fillFormPhase1(TestData.NISS_NUMBER_CJT_AIDEE);

        doSoapAndRetry(TestData.SOAP_TC_ACCEPT_ROOT_NAME, TestData.NISS_NUMBER_CJT_AIDEE);

        fillFormPhase2Accept();

        BatchUtils.doSendFlatFilePPInastiBatch(TestData.FLATFILE_TEMPLATE_DEPISTAGE_CONJOINT, TestData.NISS_NUMBER_CJT_AIDANT);

        RunBatch.runBatchExtractFluxFromFile();
        RunBatch.runBatchAdaptInastiToNasca();
    }
}