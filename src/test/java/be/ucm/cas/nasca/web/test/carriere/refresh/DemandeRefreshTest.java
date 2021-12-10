package be.ucm.cas.nasca.web.test.carriere.refresh;


import be.ucm.cas.nasca.web.test.support.TestData;
import org.openqa.selenium.Keys;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

public class DemandeRefreshTest extends DemandeRefreshTestBase {

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Carriere.xlsx", "DemandeRefresh", "DemandeRefresh", "Refresh", null);
    }

    @Test(dataProvider = "data")
    public void demandeRefreshFromExcel (String id,String type, String niss,String type_niss,String deadline){
        initTest(type, Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();


        doDemandeRefresh(type,niss,type_niss,deadline);

        loadNissOrBce(niss);
        gestionClientPage.clickMenuDemandeRefresh();
        gestionClientPage.clickOngletDemandeRefresh();
        String raison=" ";
        if ("EstimationRequest".equals(type)) {
            raison = "Requête d’estimation";
        }
        if ("InitialLoadError".equals(type)) {
            raison = "Erreur de chargement Initial";
        }
        if ("PensionRequest".equals(type)) {
            raison = "Requête de pension";
        }
        if ("ReloadError".equals(type)) {
            raison = "Refresh Erreur";
        }
        if ("Other".equals(type)) {
            raison = "Autre";
        }

        gestionClientPage.clickSearchResultDemandeRefreshATraiter(raison);
        gestionClientPage.selectPretEnvoi(TestData.ETAT_PRET_ENVOI);
        gestionClientPage.clickBtnTerminerDemandeRefresh();
        gestionClientPage.selectListePretEnvoi(TestData.ETAT_PRET_ENVOI);
        gestionClientPage.clickBtnRechercherDemandeRefresh();
        gestionClientPage.searchResultDemandeRefreshPretEnvoi(raison);

        finishTestExecution();
    }




}