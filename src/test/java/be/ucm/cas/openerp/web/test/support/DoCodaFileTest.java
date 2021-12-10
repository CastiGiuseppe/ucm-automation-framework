package be.ucm.cas.openerp.web.test.support;

import be.ucm.cas.nasca.web.test.support.TestData;
import be.ucm.cas.openerp.web.test.TestBaseOpenErp;
import org.testng.annotations.Test;

public class DoCodaFileTest extends TestBaseOpenErp {

    @Test
    public void testDoCodaFile() throws Exception {
        CodaFileUtils.doCodaFile("81051512792", TestData.CODA_DOMI_REFUND_FILE, "952.20");
        CodaFileUtils.doCodaFile("75111527653", TestData.CODA_DOMI_OK_FILE, "1063.68");
        CodaFileUtils.doCodaFile("80051233748", TestData.CODA_DOMI_OK_FILE, "727.64");
        CodaFileUtils.doCodaFile("88120118964", TestData.CODA_DOMI_NOK_FILE, "1871.03");
        CodaFileUtils.doCodaFile("87091037551", TestData.CODA_DOMI_OK_FILE, "2751.60");
        CodaFileUtils.doCodaFile("60042109479", TestData.CODA_DOMI_REFUND_FILE, "1985.78");

        CodaFileUtils.doCodaFile("68062903184", TestData.CODA_DOMI_OK_FILE, "484.79");
        CodaFileUtils.doCodaFile("90112321394", TestData.CODA_DOMI_NOK_FILE, "727.64");
        CodaFileUtils.doCodaFile("76061517147", TestData.CODA_DOMI_OK_FILE, "727.64");
        CodaFileUtils.doCodaFile("72012316582", TestData.CODA_DOMI_REFUND_FILE, "80.50");
        CodaFileUtils.doCodaFile("56042807003", TestData.CODA_DOMI_NOK_FILE, "978.18");
        CodaFileUtils.doCodaFile("52092317301", TestData.CODA_DOMI_REFUND_FILE, "727.64");
    }
}
