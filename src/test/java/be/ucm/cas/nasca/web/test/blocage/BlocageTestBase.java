package be.ucm.cas.nasca.web.test.blocage;

import be.ucm.cas.nasca.web.test.support.EnumTypeTest;
import be.ucm.cas.nasca.web.test.support.TestBase;

public class BlocageTestBase extends TestBase {

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.ENROLEMENT;
    }

    static void fillBlocageCourrier(String nissorbcenumber, String type) {
        try {
            doSearchInMultiDossier(type, nissorbcenumber);

            String result = multiDossiersPage.getSearchResultBlocageCourrier(nissorbcenumber);
            if (result != null) {
                logInfo("Blocage Courrier pour " + nissorbcenumber, "Courrier déjà bloqué");
            } else {
                multiDossiersPage.clickBloquer();
                result = multiDossiersPage.getSearchResultBlocageCourrier(nissorbcenumber);
                if (result != null) {
                    logSuccess("Blocage Courrier pour " + nissorbcenumber, "Blocage ok");
                } else {
                    logFailed("Blocage Courrier impossible pour " + nissorbcenumber, "Blocage nok");
                }
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + nissorbcenumber, e);
        }
    }

    static void fillDeblocageCourrier(String nissorbcenumber, String type) {
        try {
            doSearchInMultiDossier(type, nissorbcenumber);

            multiDossiersPage.clickDebloquer();
            String result = multiDossiersPage.getSearchResultBlocageCourrier(nissorbcenumber);

            if (result == null) {
                logSuccess("Deblocage Courrier pour " + nissorbcenumber, "Deblocage ok");
            } else {
                logFailed("Deblocage Courrier impossible pour " + nissorbcenumber, "Deblocage nok");
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + nissorbcenumber, e);
        }
    }

    private static void doSearchInMultiDossier(String type, String nissorbcenumber) {
        homeMenuPage.clickOngletMultiDossiers();

        multiDossiersPage.clickMenuMiseEnAttenteCourrier(type);
        multiDossiersPage.fillNissorBCE(nissorbcenumber);
        multiDossiersPage.clickBtnRechercher();
    }
}