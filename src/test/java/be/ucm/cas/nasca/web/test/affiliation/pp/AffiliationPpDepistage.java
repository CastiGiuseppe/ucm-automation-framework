package be.ucm.cas.nasca.web.test.affiliation.pp;

import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AffiliationPpDepistage extends AffiliationPpAbstract {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Affiliation.xlsx", "Affi PP", "Affiliation PP", "depisnorem", null);
    }

    @Test(dataProvider = "data")
    public void affiliationPpExcel(String id, String libelle, String type) throws Exception {
        initTest("Sc." + id + " - " + libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        doBaseDepistage();

        fillFormDepistageCjtNoRem(TestData.NISS_NUMBER_CJT_AIDANT, type);

        LOGGER.info("Get flux depistage - Begin");
        List<Map<String, Object>> fluxInfo = DaoFunctionality.getNascaDao().getFluxDepistage(TestData.NISS_NUMBER_CJT_AIDANT);
        LOGGER.info("Get flux depistage - End");

        LOGGER.info("Get flux depistage - Check type flux begin");
        checkTypeFlux(fluxInfo);
        LOGGER.info("Get flux depistage - Check type flux end");

        LOGGER.info("Get flux depistage - Check donnee flux begin");
        doCheckDonnee(type, fluxInfo);
        LOGGER.info("Get flux depistage - Check donnee flux end");

        finishTestExecution();
    }

    private void doCheckDonnee(String type, List<Map<String, Object>> fluxInfo) {
        switch (type) {
            case "Code H":
                checkFlux(fluxInfo.get(0).get(TestData.DONNEES).toString().charAt(18), 'H');
                break;
            case "Code D":
                checkFlux(fluxInfo.get(0).get(TestData.DONNEES).toString().charAt(19), 'D');
                break;
            case "Code P":
                checkFlux(fluxInfo.get(0).get(TestData.DONNEES).toString().charAt(20), 'P');
                break;
            case "NAC L0":
            case "NAC Q1":
                checkFluxNac(fluxInfo.get(0).get(TestData.DONNEES).toString().charAt(20), 'P');
                break;
            default:
                break;
        }
    }

    private void checkTypeFlux(List<Map<String, Object>> fluxInfo) {
        try {
            Assert.assertEquals(fluxInfo.get(0).get("TYPE"), "L001");
            logSuccess("Check flux equals L001");
        } catch (AssertionError e) {
            logFailed("Flux incorrect - not equals L001");
        }
    }

    private void checkFlux(char charFlux, char charReference) {
        try {
            Assert.assertEquals(charFlux, charReference);
            logSuccess("Dépistage Ok");
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logFailed("Dépistage KO", e.getMessage());
        }
    }

    private void checkFluxNac(char charFlux, char charReference) {
        try {
            Assert.assertEquals(charFlux, charReference);
            homeMenuPage.clickOngletTaches();
            tachePage.clickBtnDeleteFiltreTache();
            tachePage.clickBtnResetTache();

            tachePage.clickTraiterTache(TestData.NISS_NUMBER_CJT_AIDANT);
            logSuccess("Dépistage Ok");
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logFailed("Dépistage KO", e.getMessage());
        }
    }
}