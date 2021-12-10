package be.ucm.cas.nasca.web.test.transfertin.pm;

import be.ucm.cas.nasca.web.test.impression.ImpressionTestBase;
import be.ucm.cas.nasca.web.test.support.BatchUtils;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.TestData;

import java.util.Date;

public abstract class AffiliationTransfertInPMAbstract extends AffiliationPMTransfertInTestBase {

    void doTransfert(String libelle, String fileName, String bce, String decision) {
        initTest(libelle, fileName);

        loginNasca();

        DaoFunctionality.cleanAllDataByIdentite(bce);
        DaoFunctionality.getNascaDao().deleteFichierInastiTransfertInPM();

        AffiliationPMTransfertInTestBase.fillFormPhase1TransfertIn(bce);

        if (TestData.ACCEPTE.equals(decision)) {
            BatchUtils.createAndSendXmlTransfertPm(bce, String.valueOf(DateUtils.getNextYearOfToday()), "Approved");
        } else {
            BatchUtils.createAndSendXmlTransfertPm(bce, String.valueOf(DateUtils.getNextYearOfToday()), "Refused");
        }

        AffiliationPMTransfertInTestBase.doTraitementDecision();

        BatchUtils.deleteXmlBatch("AffiliationDecisionNewSif");

        AffiliationPMTransfertInTestBase.checkIfClient(bce, decision);

        String dateDocument = DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date());
        ImpressionTestBase.checkImpression(bce, TestData.TYPE_BCE, dateDocument, null, null, TestData.ETAT_A_IMPRIMER, "PM - Affiliation Transfert IN", null);

        finishTestExecution();
    }
}