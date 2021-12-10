package be.ucm.cas.nasca.web.test.affiliation.pp;

import be.ucm.cas.nasca.web.test.comptabilite.ComptabiliteTestBase;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

public class AffiliationPpAutre extends AffiliationPpTestBase {

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Affiliation.xlsx", "Affi PP", "Affiliation PP", "autre", null);
    }

    @Test(dataProvider = "data")
    public void affiliationPpExcel(String id, String libelle, String nature, String dpe, String dsg, String suite, String revenu) {
        String niss = TestData.NISS_NUMBER;
        String currentDate = DateUtils.getDateToday();
        String currentDateXml = DateUtils.getDateTodayXmlFomat();

        initTest("Sc. " + id + " - " + libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        DaoFunctionality.cleanAllDataByIdentite(niss);

        switch (suite) {
            case "rem":
                fillFormPhase1Rem(niss);
                checkIfDocExist(niss, TestData.DOC_REM_PRE_AFFI_PP);
                break;
            case "rempost":
                creationREMApresAffiliation(nature, dpe, dsg, niss);
                break;
            case "paiepré":
                creationPaiementAvantAffiliation(nature, dpe, dsg, suite, revenu, niss, currentDate, currentDateXml);
                break;
            case "paiepost":
                creationPaiementApresAffiliation(nature, dpe, dsg, suite, revenu, niss, currentDate, currentDateXml);
                break;
            default:
                break;
        }

        finishTestExecution();
    }

    private void creationPaiementApresAffiliation(String nature, String dpe, String dsg, String suite, String revenu, String niss, String currentDate, String currentDateXml) {
        fillFormPhase1(niss, nature, dpe, dsg);
        doSoapAndRetry(TestData.SOAP_TC_ACCEPT_ROOT_NAME, niss);
        fillFormPhase2Accept(null, null, nature, suite, revenu);
        //DaoFunctionality.getNascaDao().prepareAffectationComptable(currentDateXml);
        //ComptabiliteTestBase.doAffectationPaiement(niss, currentDate);
    }

    private void creationPaiementAvantAffiliation(String nature, String dpe, String dsg, String suite, String revenu, String niss, String currentDate, String currentDateXml) {
        createIdentity(niss);
        DaoFunctionality.getNascaDao().prepareAffectationComptable(currentDateXml);
        ComptabiliteTestBase.doAffectationPaiement(niss, currentDate);
        fillFormPhase1(niss, nature, dpe, dsg);
        doSoapAndRetry(TestData.SOAP_TC_ACCEPT_ROOT_NAME, niss);
        fillFormPhase2Accept(null, null, nature, suite, revenu);
    }

    private void creationREMApresAffiliation(String nature, String dpe, String dsg, String niss) {
        fillFormPhase1(niss, nature, dpe, dsg);
        doSoapAndRetry(TestData.SOAP_TC_ACCEPT_ROOT_NAME, niss);

        affiliationPpWizardPage.clickBtnContinueAffiliation();
        nascaPage.clickBtnTerminerWizard();

        doFillREM("Radiation BCE");
        checkIfDocExist(niss, TestData.DOC_REM_AFFI_PP);
    }
}