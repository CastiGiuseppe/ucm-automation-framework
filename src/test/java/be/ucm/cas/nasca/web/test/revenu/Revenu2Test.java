package be.ucm.cas.nasca.web.test.revenu;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

public class Revenu2Test extends RevenuAbstract {

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Revenu.xlsx", "Revenu", "Revenu/Régul", "Revenu2", null);
    }

    @BeforeTest
    public void initTest() {
        initialisationParametre();
    }

    @Test(dataProvider = "data")
    public void doTest(String id, String libelle, String type, String regime, String nature,
                       String dateDebut, String typeRevenuDepart, String statutRevenuDepart, String revenuDepart,
                       String typeRevenu, String revenu, String sourceRevenu, String statutRevenu, String anneeRevenu,
                       String faireRegul, String courrier, String regulCalculee, String erreur, String anneeCotisation, String montantRegul,
                       String codeActivite, String codeProvisoire, String jour, String mois, String annee, String skip) {
        testRevenu(libelle, type, regime, nature, dateDebut, typeRevenuDepart, statutRevenuDepart, revenuDepart, typeRevenu, revenu, sourceRevenu, statutRevenu, anneeRevenu, faireRegul, courrier, regulCalculee, erreur, anneeCotisation, montantRegul, codeActivite, codeProvisoire, skip, Thread.currentThread().getStackTrace()[1].getFileName());
    }
}