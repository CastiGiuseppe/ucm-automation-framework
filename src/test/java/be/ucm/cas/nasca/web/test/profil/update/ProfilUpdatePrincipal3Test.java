package be.ucm.cas.nasca.web.test.profil.update;

import be.ucm.cas.nasca.web.test.support.TestData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.Iterator;

public class ProfilUpdatePrincipal3Test extends ProfilUpdateAbstract {

    private static final String NATURE_DEPART = TestData.NATURE_PROFILE_PRINCIPAL;

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Profil.xlsx", "modification nature", "Modification nature", NATURE_DEPART + "3",null);
    }

    @Test(dataProvider = "data")
    public void updatePrincipal(String id, String codenature, String regime,
                                String reductiontype, String annee1, String revenu,
                                String datedebut1, String nature2, String reductiontype2,
                                String datedebut2, String controle1, String cotisation1,
                                String controle2, String cotisation2, String jour, String mois,
                                String annee, String pivotdate, String baremePivot1,
                                String baremePivot2, String libelle) throws ParseException {
        initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        updatePrincipalComplementaire(codenature, regime, reductiontype, datedebut1, nature2, reductiontype2, datedebut2,
                controle1, cotisation1, controle2, cotisation2, NATURE_DEPART);

        finishTestExecution();
    }
}