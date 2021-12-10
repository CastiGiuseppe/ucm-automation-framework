package be.ucm.cas.nasca.web.test.taches;

import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

public class TachesTest extends TachesTestBase {

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Tâches.xlsx", "Boîte des tâches", "Boîte des tâches", "Tâches", null);
    }

    @BeforeTest
    public void setResponsableBeforeTest() {
        setReponsable("CASTIGLIONE");
    }

    @Test(dataProvider = "data")
    public void gestionTaches(String id,
                              String libelle, String action1, String action2,
                              String statut, String typeWorkflow, String typeTache,
                              String groupe, String sousGroupe, String attribueePar,
                              String tacheInterne, String tacheExterne, String urgente) {
        initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        String niss = TestData.NISS_NUMBER_TACHE;

        DaoFunctionality.cleanAllDataByIdentite(niss);

        if ("Afficher".equals(action1)) {
            doSearchTache(statut, typeWorkflow, typeTache, niss, groupe, sousGroupe, StringUtils.isEmpty(attribueePar.trim()) ? "CASTIGLIONE" : attribueePar , tacheInterne, tacheExterne, urgente);
        } else {
            if ("Modifier".equals(action1)) {
                doEditTache(niss, action2);
            }
        }

        finishTestExecution();
    }

    @AfterTest
    public void setResponsableAfterTest() {
        setReponsable("BOIGELOT");
    }
}