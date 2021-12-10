package be.ucm.cas.nasca.web.test.planapurement;

import be.ucm.cas.nasca.web.test.comptabilite.ComptabiliteTestBase;
import be.ucm.cas.nasca.web.test.impression.ImpressionTestBase;
import be.ucm.cas.nasca.web.test.support.*;
import org.joda.time.DateTime;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.*;

public class PlanApurementReportTest extends PlanApurementTestBase {

    private static final String NOM_DOCUMENT_OCTROI = "ALL - Plan apurement octroi";

    private static List<String> listNissAvecCot = new ArrayList<>();
    private static List<String> listBceAvecCot = new ArrayList<>();

    private String periode;
    private String batchdateMoins10Jours;
    private String dateMoins10JoursStr;
    private String batchdateEcheance;

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_PlanApurement.xlsx", "Plan d'apurement Report", "Plan d'apurement", "PA", null);
    }

    @BeforeTest
    public void launchPlanApurementReport() {
        Calendar calJour = Calendar.getInstance();
        calJour.setTime(DateUtils.getMinusYearsMachine(1));
        periode = String.valueOf(calJour.get(Calendar.YEAR)) + "/1";

        calJour = Calendar.getInstance();
        calJour.add(Calendar.DAY_OF_MONTH, 22);
        batchdateMoins10Jours = DateUtils.doFormat(TestData.DATE_FORMAT_XML, calJour.getTime());

        dateMoins10JoursStr = DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, calJour.getTime());

        calJour = Calendar.getInstance();
        calJour.add(Calendar.DAY_OF_MONTH, 31);
        batchdateEcheance = DateUtils.doFormat(TestData.DATE_FORMAT_XML, calJour.getTime());
    }

    @Test(dataProvider = "data")
    public void planApurementFromExcel(String id, String libelle,
                                       String typeAffilie, String paiementTotal,
                                       String lettreConfirmation, String observation) throws ParseException {
        initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        DateTime datetim0 = new DateTime();
        Date date0 = datetim0.toDate();
        String batchdate0 = DateUtils.doFormat(TestData.DATE_FORMAT_XML, date0);
        RunBatch.runBatchChangeDate(batchdate0);

        loginNasca();

        String nissOrBce = selectNissOrBce(typeAffilie);

        String soldePA = doCreateReportPA(nissOrBce, periode, DateUtils.getDateFutur(30, TestData.DATE_FORMAT_SIMPLE), observation, typeAffilie, lettreConfirmation);

        checkSuspension(typeAffilie, nissOrBce, periode, soldePA);

        if (Boolean.valueOf(lettreConfirmation) || Boolean.valueOf(paiementTotal)) {
            RunBatch.runBatchChangeDate(batchdateMoins10Jours);
        }

        runBatchAvantEcheance(lettreConfirmation, nissOrBce, dateMoins10JoursStr, typeAffilie);

        doAffectationComptable(paiementTotal, nissOrBce, typeAffilie);

        runBatchApresEcheance();

        checkReportPA(typeAffilie, paiementTotal, nissOrBce, periode, dateMoins10JoursStr, soldePA);

        finishTestExecution();
    }

    private void runBatchApresEcheance() {
        RunBatch.runBatchChangeDate(batchdateEcheance);
        RunBatch.runBatchPlanApurementControleReport();
        RunBatch.runBatchGenerationDocPlanApurement();
    }

    private void doAffectationComptable(String paiementTotal, String nissOrBce, String typeAffilie) {
        if (Boolean.valueOf(paiementTotal)) {
            // Affectation paiement
            DateTime currentDateTime = new DateTime();
            Date yesterday = currentDateTime.minusDays(3).toDate();
            String batchdate = DateUtils.doFormat(TestData.DATE_FORMAT_XML, yesterday);
            DaoFunctionality.getNascaDao().updateAffectationComptable(batchdate);

            DaoFunctionality.getNascaDao().prepareAffectationComptable(batchdateMoins10Jours);

            if (TestData.TYPE_PP.equals(typeAffilie)) {
                ComptabiliteTestBase.doAffectationPaiement(nissOrBce, SeleniumUtils.deleteFormat(periode), true, false, dateMoins10JoursStr);
            } else {
                ComptabiliteTestBase.doAffectationPaiement(nissOrBce, SeleniumUtils.deleteFormat(periode.substring(0, 4)), true, false, dateMoins10JoursStr);
            }
        }
    }

    private void runBatchAvantEcheance(String lettreConfirmation, String nissOrBce, String dateMoins10JoursStr, String typeAffilie) {
        if (Boolean.valueOf(lettreConfirmation)) {
            RunBatch.runBatchPlanApurementControleReport();
            RunBatch.runBatchGenerationDocPlanApurement();

            DaoFunctionality.getNascaDao().updateAllTypeDocumentImpression();
            if (TestData.TYPE_PP.equals(typeAffilie)) {
                ImpressionTestBase.checkImpressionDernierDocument(nissOrBce, TestData.TYPE_NISS, dateMoins10JoursStr, null, null, TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT_OCTROI);
            } else {
                ImpressionTestBase.checkImpressionDernierDocument(nissOrBce, TestData.TYPE_BCE, dateMoins10JoursStr, null, null, TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT_OCTROI);
            }
        }
    }

    private String selectNissOrBce(String typeAffilie) {
        String nissOrBce;

        if (TestData.TYPE_PP.equals(typeAffilie)) {
            if (listNissAvecCot.isEmpty()) {
                listNissAvecCot = DaoFunctionality.getNascaDao().getNissForPlanApurement(periode);
            }
            nissOrBce = listNissAvecCot.get(0);
            listNissAvecCot.remove(0);
        } else {
            if (listBceAvecCot.isEmpty()) {
                listBceAvecCot = DaoFunctionality.getNascaDao().getBceForPlanApurement(periode.substring(0, 4));
            }
            nissOrBce = listBceAvecCot.get(0);
            listBceAvecCot.remove(0);
        }
        return nissOrBce;
    }
}