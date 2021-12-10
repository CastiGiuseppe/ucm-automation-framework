package be.ucm.cas.nasca.web.test.planapurement;

import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.RunBatch;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.joda.time.DateTime;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PlanApurementEchelonnementPPTest extends PlanApurementAbstract {

    private static List<String> listNiss = new ArrayList<>();

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_PlanApurement.xlsx", "Plan d'apurement Echelonnement", "Plan d'apurement", "PA PP", null);
    }

    @BeforeTest
    public void launchPlanApurementEchelonnement() {
        doBeforeTest();
    }

    @Test(dataProvider = "data")
    public void planApurementFromExcel(String id, String libelle,
                                       String acompte, String paiementAcompte,
                                       String nbreMensualite, String mensualitePaye, String leveeMajoration,
                                       String observation) {
        initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        DateTime datetim0 = new DateTime();
        Date date0 = datetim0.toDate();
        String batchdate0 = DateUtils.doFormat(TestData.DATE_FORMAT_XML, date0);
        RunBatch.runBatchChangeDate(batchdate0);

        loginNasca();

        String nissOrBce = selectNissOrBce();

        String typeAffilie = TestData.TYPE_PP;

        String soldePA = doCreateEchelonnementPA(nissOrBce, getPeriode(), leveeMajoration, acompte, nbreMensualite, observation, typeAffilie);

        checkSuspension(typeAffilie, nissOrBce, getPeriode(), soldePA);

        doPlanApurement(acompte, paiementAcompte, getBatchdatePaiementAcompte(), typeAffilie, nissOrBce, getPeriode(), getDatePaiementAcompteStr(), getBatchdateEcheanceAcompte(),
                mensualitePaye, getBatchdatePaiementMensualite(), getDatePaiementMensualiteStr(), getBatchdateEcheanceMensualite(), leveeMajoration);

        finishTestExecution();
    }

    private String selectNissOrBce() {
        String nissOrBce;
        if (listNiss.isEmpty()) {
            listNiss = DaoFunctionality.getNascaDao().getNissForPlanApurement(getPeriode());
        }
        nissOrBce = listNiss.get(0);
        listNiss.remove(0);

        return nissOrBce;
    }
}