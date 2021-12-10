package be.ucm.cas.nasca.web.test.affiliation.pp;

import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.RunBatch;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AffiliationPpDepistageRem extends AffiliationPpAbstract {

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Affiliation.xlsx", "Affi PP", "Affiliation PP", "depisrem", null);
    }

    @Test(dataProvider = "data")
    public void affiliationPpExcel(String id, String libelle, String type) throws Exception {
        initTest("Sc." + id + " - " + libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        doBaseDepistage();
        RunBatch.runBatchDepistageREM();
        if (type != null) {
            fillFormDepistageCjtRem(TestData.NISS_NUMBER_CJT_AIDANT);

            fillReponseDepistage(TestData.NISS_NUMBER_CJT_AIDANT, type);

            List<Map<String, Object>> fluxInfo = DaoFunctionality.getNascaDao().getFluxDepistage(TestData.NISS_NUMBER_CJT_AIDANT);

            Assert.assertEquals(fluxInfo.get(0).get("TYPE"), "L0<<<z01");

            switch (type) {
                case "Code H":
                    Assert.assertEquals(fluxInfo.get(0).get(TestData.DONNEES).toString().charAt(18), 'H');
                    break;
                case "Code D":
                    Assert.assertEquals(fluxInfo.get(0).get(TestData.DONNEES).toString().charAt(19), 'D');
                    break;
                case "Code P":
                    Assert.assertEquals(fluxInfo.get(0).get(TestData.DONNEES).toString().charAt(20), 'P');
                    break;
                case "NAC L0":
                case "NAC Q1":
                    traitementQ1(fluxInfo);
                    break;
                default:
                    break;
            }
        } else {
            checkIfConjointAidant(TestData.NISS_NUMBER_CJT_AIDANT, "En traitement");
        }

        finishTestExecution();
    }

    private void traitementQ1(List<Map<String, Object>> fluxInfo) {
        Assert.assertEquals(fluxInfo.get(0).get(TestData.DONNEES).toString().charAt(20), 'P');
        homeMenuPage.clickOngletTaches();
        tachePage.clickBtnResetTache();
        tachePage.fillNumero(TestData.NISS_NUMBER_CJT_AIDANT);
        tachePage.clickBtnDeleteFiltreTache();
        tachePage.clickBtnSearchTache();
        tachePage.clickTraiterTache(TestData.NISS_NUMBER_CJT_AIDANT);
    }
}