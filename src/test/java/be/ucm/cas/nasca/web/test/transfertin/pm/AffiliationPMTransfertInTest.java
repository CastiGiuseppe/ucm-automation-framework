package be.ucm.cas.nasca.web.test.transfertin.pm;

import be.ucm.cas.nasca.web.test.support.BatchUtils;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AffiliationPMTransfertInTest extends AffiliationTransfertInPMAbstract {

    private final String fileName = Thread.currentThread().getStackTrace()[1].getFileName();

    @BeforeTest
    public void deleteXml() {
        DaoFunctionality.getNascaDao().updateDateFinValiditeBaremePM();
        BatchUtils.deleteXmlBatch("AffiliationDecisionNewSif");
    }

    @Test
    public void AffiliationPMTransfertInSAAccepte() {
        doTransfert("Transfert IN PM - SA - Acceptee", fileName, TestData.BCE_NUMBER_TRANSFERT_SA, TestData.ACCEPTE);
    }

    @Test
    public void AffiliationPMTransfertInSARefuse() {
        doTransfert("Transfert IN PM - SA - Refusee", fileName, TestData.BCE_NUMBER_TRANSFERT_SA, TestData.REFUSE);
    }

    @Test
    public void AffiliationPMTransfertInSCSAccepte() {
        doTransfert("Transfert IN PM - SCS - Acceptee", fileName, TestData.BCE_NUMBER_TRANSFERT_SCS, TestData.ACCEPTE);
    }

    @Test
    public void AffiliationPMTransfertInSCSRefuse() {
        doTransfert("Transfert IN PM - SCS - Refusee", fileName, TestData.BCE_NUMBER_TRANSFERT_SCS, TestData.REFUSE);
    }

    @Test
    public void AffiliationPMTransfertInSPRLAccepte() {
        doTransfert("Transfert IN PM - SPRL - Accepte", fileName, TestData.BCE_NUMBER_TRANSFERT_SPRL, TestData.ACCEPTE);
    }

    @Test
    public void AffiliationPMTransfertInSPRLRefuse() {
        doTransfert("Transfert IN PM - SPRL - Refusee", fileName, TestData.BCE_NUMBER_TRANSFERT_SPRL, TestData.REFUSE);
    }
}