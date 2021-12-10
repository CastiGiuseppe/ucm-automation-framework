package be.ucm.cas.openerp.web.test.comptabilite;

import be.ucm.cas.openerp.web.pagefactory.openerp.OpenErpPage;
import be.ucm.cas.openerp.web.test.TestBaseOpenErp;
import be.ucm.cas.openerp.web.test.support.CodaFileUtils;

public class CodaFileImportTest extends TestBaseOpenErp {

    public static void doOpenErpPage() {
        initializeTest();
    }

    public static void doAddCodaOpenErpPage(String nissorbce, String testDataCODAtypeContstante) {
        OpenErpPage erp = new OpenErpPage(getDriver());
        erp.clickBtnComptabilite();
        erp.clickBtnImportFile(nissorbce, testDataCODAtypeContstante);
        erp.clickResultConfirmCODAimport(nissorbce, testDataCODAtypeContstante);
        CodaFileUtils.cleanTmp();
        tearDown();
    }
}
