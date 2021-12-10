package be.ucm.cas.nasca.web.test.profil.suppression;

import be.ucm.cas.nasca.web.test.support.TestData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.Iterator;

public class ProfilSuppressionComplementaireTest extends ProfilSuppressionAbstract {

    private static final String NATURE_A_SUPPRIMER = TestData.NATURE_PROFILE_COMPLEMENTAIRE;

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Profil.xlsx", "supprimer nature", "Supprimer nature", NATURE_A_SUPPRIMER, null);
    }

    @Test(dataProvider = "data")
    public void suppressionComplementaire(String id, String typeEvenement,
                                          String nature1, String reductiontype1, String datedebut1,
                                          String typeEvenement2, String nature2, String reductiontype2,
                                          String datedebut2, String controle1, String cotisation1,
                                          String jour, String mois, String annee, String pivotdate,
                                          String baremePivot1, String libelle) throws ParseException {
        initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        suppression(typeEvenement, nature1, reductiontype1, datedebut1, typeEvenement2,
                nature2, reductiontype2, datedebut2, controle1, cotisation1, NATURE_A_SUPPRIMER);

        finishTestExecution();
    }
}