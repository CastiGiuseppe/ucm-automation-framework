package be.ucm.cas.nasca.web.test.profil.change;

import be.ucm.cas.nasca.web.test.support.TestData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

public class ProfilChangeMaxiStatutRDA3Test extends ProfilChangeAbstract {

    private static final String NATURE_DEPART = TestData.NATURE_PROFILE_MAXI_STATUT;

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Profil.xlsx", "changement nature RDA", "Changement nature RDA", NATURE_DEPART + "3", null);
    }

    @Test(dataProvider = "data")
    public void changeMaxiStatutRDA(String id, String codenature,
                                    String regime, String reductiontype, String annee1, String revenu,
                                    String datedebut1, String nature2, String reductiontype2,
                                    String datedebut2, String controle1, String cotisation1, String cotisation1b,
                                    String controle2, String cotisation2, String cotisation2b, String jour, String mois,
                                    String annee, String pivotdate, String baremepivot1, String baremepivot1b,
                                    String baremepivot2, String baremepivot2b, String libelle) {
        initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        changeMaxiStatutRDA(codenature, regime, reductiontype, datedebut1, nature2, reductiontype2,
                datedebut2, controle1, cotisation1, cotisation1b, controle2, cotisation2, cotisation2b, TestData.NATURE_PROFILE_CONJOINT_MAXI_STATUT);

        finishTestExecution();
    }
}