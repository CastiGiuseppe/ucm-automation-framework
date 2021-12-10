package be.ucm.cas.nasca.web.test.lienresumedossier;

import be.ucm.cas.nasca.web.test.support.EnumTypeTest;
import be.ucm.cas.nasca.web.test.support.TestBase;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.testng.annotations.Test;

public class LienResumeTest extends TestBase {

    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    @Test
    public void testLienCtx() {
        initTest("Résumé lien - Contentieux", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        homeMenuPage.doSearch(TestData.NISS_NUMBER_LIEN_RESUME_DOSSIER);

        gestionClientPage.clickLienCtx();

        gestionClientPage.clickBtnReset();

        logSuccess("Contentieux", "Test lien contentieux success");
        finishTestExecution();
    }

    @Test
    public void testLienIrrec() {
        initTest("Résumé lien - Irrecouvrable", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        homeMenuPage.doSearch(TestData.NISS_NUMBER_LIEN_RESUME_DOSSIER);

        gestionClientPage.clickLienIrrec();

        gestionClientPage.clickBtnReset();

        logSuccess("Irrecouvrable", "Test lien irrecouvrable success");
        finishTestExecution();
    }

    @Test
    public void testLienCodeb() {
        initTest("Résumé lien - Co-débiteurs", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        homeMenuPage.doSearch(TestData.NISS_NUMBER_LIEN_RESUME_DOSSIER);

        gestionClientPage.clickLienCodeb();

        gestionClientPage.clickBtnReset();

        logSuccess("Co-débiteurs", "Test lien lien co-débiteurs success");
        finishTestExecution();
    }

    @Test
    public void testLienDispense() {
        initTest("Résumé lien - Dispense", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        homeMenuPage.doSearch(TestData.NISS_NUMBER_LIEN_RESUME_DOSSIER);

        gestionClientPage.clickLienDispense();

        gestionClientPage.clickBtnReset();

        logSuccess("Dispense", "Test lien dispense success");
        finishTestExecution();
    }

    @Test
    public void testLienPrescr() {
        initTest("Résumé lien - Prescription", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        homeMenuPage.doSearch(TestData.NISS_NUMBER_LIEN_RESUME_DOSSIER);

        gestionClientPage.clickLienPrescr();

        gestionClientPage.clickBtnReset();

        logSuccess("Prescription", "Test lien prescription success");
        finishTestExecution();
    }

    @Test
    public void testLienSolidaire() {
        initTest("Résumé lien - Solidaire", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        homeMenuPage.doSearch(TestData.NISS_NUMBER_LIEN_RESUME_DOSSIER);

        gestionClientPage.clickLienSolidaire();

        gestionClientPage.clickBtnReset();

        logSuccess("Solidaires", "Test lien solidaire success");
        finishTestExecution();
    }

    @Test
    public void testLienRem() {
        initTest("Résumé lien - REM", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        homeMenuPage.doSearch(TestData.NISS_NUMBER_LIEN_RESUME_DOSSIER);

        gestionClientPage.clickLienRem();

        gestionClientPage.clickBtnReset();

        logSuccess("REM", "Test lien REM success");
        finishTestExecution();
    }

    @Test
    public void testLienPlc() {
        initTest("Résumé lien - PLC", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        homeMenuPage.doSearch(TestData.NISS_NUMBER_LIEN_RESUME_DOSSIER);

        gestionClientPage.clickLienPlc();

        gestionClientPage.clickBtnReset();

        logSuccess("PLC", "Test lien lien PLC success");
        finishTestExecution();
    }

    @Test
    public void testLienPlanFamille() {
        initTest("Résumé lien - Plan familliale", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        homeMenuPage.doSearch(TestData.NISS_NUMBER_LIEN_RESUME_DOSSIER);

        gestionClientPage.clickLienPlanFamille();

        gestionClientPage.clickBtnReset();

        logSuccess("Plan familliale", "Test lien plan familliale success");
        finishTestExecution();
    }

    @Test
    public void testLienPasserelle() {
        initTest("Résumé lien - Passerelle", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        homeMenuPage.doSearch(TestData.NISS_NUMBER_LIEN_RESUME_DOSSIER);

        gestionClientPage.clickLienPasserelle();

        gestionClientPage.clickBtnReset();

        logSuccess("Passerelle", "Test lien passerelle success");
        finishTestExecution();
    }

    @Test
    public void testLienMaternite() {
        initTest("Résumé lien - Aide maternité", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        homeMenuPage.doSearch(TestData.NISS_NUMBER_LIEN_RESUME_DOSSIER);

        gestionClientPage.clickLienMaternite();

        gestionClientPage.clickBtnReset();

        logSuccess("Aide maternité", "Test lien d'aide maternité maternite success");
        finishTestExecution();
    }

    @Test
    public void testLienPA() {
        initTest("Résumé lien - Plan d'apurement", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        homeMenuPage.doSearch(TestData.NISS_NUMBER_LIEN_RESUME_DOSSIER);

        gestionClientPage.clickLienPA();

        gestionClientPage.clickBtnReset();

        logSuccess("Plan d'apurement", "Test lien plan d'apurement success");
        finishTestExecution();
    }

    @Test
    public void testLienConjoint() {
        initTest("Résumé lien - Conjoint", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        homeMenuPage.doSearch(TestData.NISS_NUMBER_LIEN_RESUME_DOSSIER);

        gestionClientPage.clickLienConjoint();
        gestionClientPage.clickLienConjoint();

        logSuccess("Conjoint", "Test lien conjoint success");
        finishTestExecution();
    }

    @Test
    public void testLienSociete() {
        initTest("Résumé lien - Société", Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        homeMenuPage.doSearch(TestData.NISS_NUMBER_LIEN_RESUME_DOSSIER);

        gestionClientPage.clickLienSociete();

        logSuccess("Société", "Test lien société success");
        finishTestExecution();
    }
}