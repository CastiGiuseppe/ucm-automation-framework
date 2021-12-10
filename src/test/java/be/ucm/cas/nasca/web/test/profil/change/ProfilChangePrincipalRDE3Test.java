package be.ucm.cas.nasca.web.test.profil.change;

import be.ucm.cas.nasca.web.test.support.TestData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

public class ProfilChangePrincipalRDE3Test extends ProfilChangeAbstract {

    private static final String NATURE_DEPART = TestData.NATURE_PROFILE_PRINCIPAL;

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Profil.xlsx", "changement nature RDE", "Changement nature RDE", NATURE_DEPART + "3", null);
    }

    @Test(dataProvider = "data")
    public void changePrincipalRDE(String id, String codenature,
                                   String reductiontype, String datedebut1, String annee1,
                                   String revenu1, String typeRevenu1, String annee2, String revenu2,
                                   String typeRevenu2, String annee3, String revenu3,
                                   String typeRevenu3, String annee4, String revenu4,
                                   String typeRevenu4, String nature2, String reductiontype2,
                                   String datedebut2, String controle1, String cotisationOrd1,
                                   String cotisationRegul1, String jour, String mois, String annee,
                                   String pivotdate, String sourceEW, String libelle) {
        initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        changeRDE(codenature, reductiontype, datedebut1, annee1, revenu1, typeRevenu1, annee2, revenu2,
                typeRevenu2, annee3, revenu3, typeRevenu3, annee4, revenu4, typeRevenu4, nature2, reductiontype2,
                datedebut2, controle1, cotisationOrd1, cotisationRegul1, NATURE_DEPART);

        finishTestExecution();
    }
}