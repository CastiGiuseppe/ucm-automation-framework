package be.ucm.cas.nasca.web.test.enrolement.pm;

import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.RunBatch;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.joda.time.DateTime;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class MajorationPMTest extends EnrolementPMTestBase {

    private String dateOperationComptable;

    @BeforeTest
    public void launchMajorationPM() {
        DateTime currentDateTime = new DateTime();
        Date datePlus1Mois = currentDateTime.plusMonths(1).toDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datePlus1Mois);

        dateOperationComptable = DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, calendar.getTime());

        String batchdate = DateUtils.doFormat(TestData.DATE_FORMAT_XML, calendar.getTime());

        RunBatch.runBatchChangeDate(batchdate);

        DaoFunctionality.getNascaDao().initialisationDateEnrolementMajoPM(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.getTime());

        EnrolementPMTestBase.doMajorationsPM();
    }

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Enrôlement.xlsx", "Majoration PM", "Majoration PM", "MajoPM", null);
    }

    @Test(dataProvider = "data")
    public void MajorationPMFromExcel(String id, String libelle,
                                      String typeCotisation, String typeMajoration, String paiement,
                                      String echeance, String resultat) {
        initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        String bce = null;

        switch (paiement) {
            case "Nul":
                bce = DaoFunctionality.getNascaDao()
                        .getBcePaiementNulEnrolMajoPM(echeance, typeCotisation);
                break;
            case "Partiel":
                bce = DaoFunctionality.getNascaDao()
                        .getBcePaiementPartielEnrolMajoPM(echeance,
                                typeCotisation);
                break;
            case "Total":
                bce = DaoFunctionality.getNascaDao()
                        .getBcePaiementTotalEnrolMajoPM(echeance,
                                typeCotisation);
                break;
            default:
                break;
        }

        EnrolementPMTestBase.checkMajorationAnneePM(bce, echeance, dateOperationComptable);

        finishTestExecution();
    }
}