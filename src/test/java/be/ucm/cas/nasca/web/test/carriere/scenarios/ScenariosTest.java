package be.ucm.cas.nasca.web.test.carriere.scenarios;

import be.ucm.cas.nasca.web.test.revenu.RevenuTestBase;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.SqlData;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



/**
 * Created by sylvie.degraef on 7/06/2017.
 */
public class ScenariosTest extends ScenariosTestBase  {

    private static List<String> listNiss = new ArrayList<>();
    String nna = null;

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Carriere.xlsx", "Scenarios", "Scenarios", "Scenario", null);
    }

    @Test(dataProvider = "data")
    public void scenarioFromExcel (String id,String niss,String nature,String type,String chgtProfil,String pmt,
                                   String pmtPeriode,String pmtNbre,String pmtTrim,String maj,String majLevee,String annulCot,
                                   String typeCess,String dip,String annul,String tftDroit,String ass,String asf,String planFamille,
                                   String regime,String envoi,String revenuMnt,String revenuPresume,String revenuAnnee,String article,
                                   String bareme,String anneeTrim,String info,String pension,String erreurChgt,String demandeRefresh,String spf) {

        initTest(type, Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();


        doScenario(id,niss,nature,type,chgtProfil,pmt,pmtPeriode,pmtNbre,pmtTrim,maj,majLevee,annulCot,
                   typeCess,dip,annul,tftDroit,ass,asf,planFamille,regime,envoi,revenuMnt,revenuPresume,revenuAnnee,article,
                   bareme,anneeTrim,info,pension,erreurChgt,demandeRefresh,spf );

        if ((id.equals("0")  || id.equals("1")) && niss.equals(" ")) {
            if (listNiss.isEmpty()) {
                listNiss = DaoFunctionality.getNascaDao().getNissforMiseAJourNationalite();
            }

            nna = listNiss.get(0);
        } else {
            nna = niss;

        }
        loadNissOrBce(niss);
// changement de nationalité
        if (id.equals("0")  || id.equals("1")) {
            signaletiquePage.clickMenuDetailsIdentite();
            signaletiquePage.clickOuvrirAccordeonFCaracteristiques();
            signaletiquePage.clickBtnModifierCaracteristiques();
            signaletiquePage.selectNationalite("Andorre");
            signaletiquePage.clickBtnEnregistrerCaracteristiques();
        }
// ajout d'un revenu
        if (id.equals("0")  || id.equals("1")) {
          //   clean(SqlData.DELETE_TEMP, listNiss);
            gestionClientPage.clickMenuRevenu();
            gestionClientPage.clickAjouterModifierRevenu();
            gestionClientPage.clickBtnAjoutRevenuWizard();
            RevenuTestBase.fillInRevenu("2015","90000", "15/06/2017", "Présumé", "A régulariser", "Affilié", null);
            //gestionClientPage.clickBtnEnregistrerModalRevenu();
            /*
            signaletiquePage.clickMenuDetailsIdentite();
            signaletiquePage.clickOuvrirAccordeonFCaracteristiques();
            signaletiquePage.clickBtnModifierCaracteristiques();
            signaletiquePage.selectNationalite("Andorre");*/
        }




        /*

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
        */
        finishTestExecution();
    }


}
