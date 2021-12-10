package be.ucm.cas.nasca.web.test.miseendemeure;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.Iterator;

public class MiseEnDemeurePM1Test extends MiseEnDemeureAbstract {

    private String date = null;

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Recouvrement.xlsx", "Mise en demeure", "Mise en demeure", "MED PM1", null);
    }

    @BeforeTest
    public void launchMedPM() {
        date = doTimeMachineChange();
    }

    @Test(dataProvider = "data")
    public void doTest(String id, String libelle, String batch, String requete,
                       String cotisation, String majoration, String frais,
                       String solidaire, String echu, String prescrit, String stadeRecouvrement,
                       String suspendu, String paiementCotisation, String seuilDepasse,
                       String maxiStatutAv1956, String art11, String credit,
                       String procedureRecouvrement, String procedureRecouvrementFrais,
                       String notification, String checkRecouvrement) throws ParseException {

        initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        miseEnDemeurePMFromExcel(batch, requete, solidaire,
                stadeRecouvrement, suspendu, seuilDepasse,
                procedureRecouvrement, notification, checkRecouvrement,
                date);

        finishTestExecution();
    }
}