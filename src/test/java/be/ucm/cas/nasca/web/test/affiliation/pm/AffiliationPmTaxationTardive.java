package be.ucm.cas.nasca.web.test.affiliation.pm;

import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

public class AffiliationPmTaxationTardive extends AffiliationPmTestBase {

    private static final String DEBUT_ANNEE_DDMMYYYY = "01-01-";
    private static final String DEBUT_ANNEE_YYYYMMDD = "-01-01";

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Affiliation.xlsx", "Affi PM", "Affiliation PM", "tardive", null);
    }

    @Test(dataProvider = "data")
    public void affiliationPmTest(String id, String libelle, String annee, String enrolement, String bareme, String finAnnee) {
        initTest("Sc." + id + " - " + libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        DaoFunctionality.cleanAllDataByIdentite(TestData.BCE_NUMBER_AUTRE);
        DaoFunctionality.cleanWsInastiExchange();

        //TODO doAffi en fonction du type de test
        doAffi(DEBUT_ANNEE_DDMMYYYY + annee);
//        } else {
//            DatePicker impossible à modifier pour la date décision JIRA 6046
//            3	Taxation d'une PM apres l'enrolement => echeance en fonction de la DPE	X	après	après	true
//            4	Taxation d'une PM apres l'enrolement => echeance en fonction de la DPE	X	après	après	false
//            5	Taxation d'une PM avant la reception des baremes	X	X	avant
//            6	Taxation d'une PM avant l'enrolement => echeance le 30/06	X	avant	après	X

//            if (enrolement.equals("après") && bareme.equals("après")) {
//                checkEnro(Integer.valueOf(DateUtils.getYearNow().substring(0, 4)), finAnnee);
//            } else {
//                if (bareme.equals("avant")) {
//                    checkAvantBareme(Integer.valueOf(DateUtils.getYearNow().substring(0, 4)));
//                if (bareme.equals("après")) {
//                    checkAvantEnro(Integer.valueOf(DateUtils.getYearNow().substring(0, 4)));
//                }
//            }
        finishTestExecution();
    }

    private void doAffi(String affiDate) {
        fillFormPhase1(TestData.BCE_NUMBER_AUTRE, affiDate);

        sendSoap(TestData.SOAP_TC_ACCEPT_ROOT_NAME, TestData.BCE_NUMBER_AUTRE);

        fillFormPhase2AcceptPart1(TestData.BCE_NUMBER_AUTRE, TestData.NACE_CODE, affiDate, affiDate, false);

        fillFormPhase2AcceptPart2(TestData.GERANT);

        AffiliationPmTestBase.clientCheck(TestData.BCE_NUMBER_AUTRE, TestData.ACCEPTE);

        checkIfDocExist(TestData.BCE_NUMBER_AUTRE, TestData.DOC_ACCEPT_AFFI_PM);
    }

//    private void checkEnro(int yearNow, String finAnnee)  {
//        int yearMin =  yearNow-5;
//
//        while(yearNow > yearMin) {
//            if(!DaoFunctionality.getNascaDao().checkIfEnroEmpty(yearNow, "T")) {
//                if ("false".equals(finAnnee)) {
//                    RunBatch.runBatchChangeDate(yearNow + DEBUT_ANNEE_YYYYMMDD);
//                    refreshPageSelenide();
//                    doAffi(DEBUT_ANNEE_DDMMYYYY + yearNow);
//                } else {
//                    RunBatch.runBatchChangeDate(yearNow + "-12-31");
//                    refreshPageSelenide();
//                    doAffi("01-12-" + yearNow);
//                }
//            }
//            yearNow++;
//        }
//
//        logSkip("Recherche après enro", "date après enro non trouvée");
//    }
//
//    private void checkAvantBareme(int yearNow) {
//        int yearMax = yearNow+5;
//
//        while (yearNow < yearMax) {
//            if (DaoFunctionality.getNascaDao().checkIfBaremeEmpty(String.valueOf(yearNow))) {
//                RunBatch.runBatchChangeDate(yearNow + DEBUT_ANNEE_YYYYMMDD);
//                refreshPageSelenide();
//                doAffi(DEBUT_ANNEE_DDMMYYYY + yearNow);
//                break;
//            }
//            yearNow++;
//        }
//
//       logSkip("Recherche avant barème", "date avant barème non trouvée");
//    }
//
//    private void checkAvantEnro(int yearNow) {
//        int yearMax = yearNow+5;
//
//        while(yearNow > yearMax) {
//            boolean enro = DaoFunctionality.getNascaDao().checkIfEnroEmpty(yearNow, "F"),
//                    bareme = DaoFunctionality.getNascaDao().checkIfBaremeEmpty(String.valueOf(yearNow));
//
//            if(enro && !bareme) {
//                RunBatch.runBatchChangeDate(yearNow + DEBUT_ANNEE_YYYYMMDD);
//                refreshPageSelenide();
//                doAffi(DEBUT_ANNEE_DDMMYYYY + yearNow);
//            } else {
//                if(!enro) {
//                    RunBatch.runBatchChangeDate(yearNow + DEBUT_ANNEE_YYYYMMDD);
//                    refreshPageSelenide();
//                    addBareme();
//                    doAffi(DEBUT_ANNEE_DDMMYYYY + yearNow);
//                }
//            }
//            yearNow++;
//        }
//    }
}