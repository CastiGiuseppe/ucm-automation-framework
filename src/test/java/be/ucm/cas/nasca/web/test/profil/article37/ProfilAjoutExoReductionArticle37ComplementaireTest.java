package be.ucm.cas.nasca.web.test.profil.article37;

import be.ucm.cas.nasca.web.test.support.TestData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

public class ProfilAjoutExoReductionArticle37ComplementaireTest extends ProfilAjoutExoRedAbstract {

    private static final String NATURE_DEPART = TestData.NATURE_PROFILE_COMPLEMENTAIRE;

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Profil.xlsx", "ajout exo", "Ajout exo-réduction", NATURE_DEPART, null);
    }

    @Test(dataProvider = "data")
    public void ajoutExoReductionComplementaire(String id, String codenature, String regime, String reductiontype,
                                                String annee1, String revenu, String datedebut1, String nature2,
                                                String reductiontype2, String raisonExoRedution, String datedebut2,
                                                String datefin2, String controle1, String cotisation1, String cotisation1b,
                                                String controle2, String cotisation2, String cotisation2b, String jour, String mois,
                                                String annee, String pivotdate, String baremePivot1, String baremePivot1b,
                                                String baremePivot2, String baremePivot2b, String libelle) {
        initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        ajoutExoReductionArcticle37(codenature, regime, reductiontype, datedebut1, reductiontype2,
                raisonExoRedution, datedebut2, datefin2, controle1, cotisation1, cotisation1b, controle2, cotisation2, cotisation2b, NATURE_DEPART);

        finishTestExecution();
    }
}