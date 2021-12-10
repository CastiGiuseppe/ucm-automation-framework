package be.ucm.cas.nasca.web.test.comptabilite;

import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateQuarters;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.joda.time.DateTime;
import org.testng.annotations.Test;

import java.util.Date;

public class ComptabiliteTest extends ComptabiliteTestBase {

    @Test
    public void AffectionPaiement() {
        initTest("Comptabilite", Thread.currentThread().getStackTrace()[1].getFileName());

        DateTime currentDateTime = new DateTime();
        Date yesterday = currentDateTime.minusDays(365).toDate();

        String trimestre = DateQuarters.getQuarterCurrent(yesterday);

        String batchdate = DateUtils.doFormat(TestData.DATE_FORMAT_XML, yesterday);
        String niss = DaoFunctionality.getNascaDao().getNissWithSolde(trimestre.substring(0, 4) + "/" + trimestre.substring(4, 5));
        trimestre = trimestre.substring(0, 4) + "/" + trimestre.substring(4, 5);

        DaoFunctionality.getNascaDao().prepareAffectationComptable(batchdate);
        ComptabiliteTestBase.doAffectationPaiement(niss, trimestre, true, false, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, yesterday));

        yesterday = currentDateTime.minusDays(3).toDate();
        batchdate = DateUtils.doFormat(TestData.DATE_FORMAT_XML, yesterday);
        DaoFunctionality.getNascaDao().updateAffectationComptable(batchdate);
    }
}