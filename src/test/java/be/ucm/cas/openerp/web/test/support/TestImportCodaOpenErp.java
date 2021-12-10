package be.ucm.cas.openerp.web.test.support;

import be.ucm.cas.nasca.web.test.support.RunBatch;
import be.ucm.cas.nasca.web.test.support.TestData;
import be.ucm.cas.openerp.web.test.comptabilite.CodaFileImportTest;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.IOException;

public class TestImportCodaOpenErp {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    @Test
    public void testimportDomi() throws IOException {

        String niss = "94061754687";

        try {
            CodaFileImportTest.doOpenErpPage();
            CodaFileUtils.doCodaFile(niss, TestData.CODA_DOMI_NOK_FILE, null);
            CodaFileImportTest.doAddCodaOpenErpPage(niss, TestData.CODA_DOMI_NOK_FILE);
            RunBatch.runBatchSynchroOERP();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}