package be.ucm.cas.nasca.web.test.miseenveilleuse;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MiseEnVeilleuse1Test extends MiseEnVeilleuseAbstract {

    private static final List<String> LIST_BCE_SOLDE_NUL = new ArrayList<>();
    private static final List<String> LIST_BCE_SOLDE_NEGATIF = new ArrayList<>();
    private static final List<String> LIST_BCE_SOLDE_POSITIF = new ArrayList<>();

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_MiseEnVeilleuse.xlsx", "Mise en veilleuse", "Mise en veilleuse", "MiseEnVeilleuse1", null);
    }

    @Test(dataProvider = "data")
    public void doTest(String id, String libelle,
                       String dateDebutPeriode, String compteSolde, String compteBancaire, String memeEcran,
                       String annee1, String statutDecision1, String motivation1,
                       String initiative, String forme1, String annee2,
                       String statutDecision2, String motivation2, String annee3,
                       String statutDecision3, String motivation3, String annee4,
                       String statutDecision4, String motivation4, String texte1,
                       String texte2, String texte3, String texte4, String texte5,
                       String texteAnnee1, String motifAnnee1, String texteAnnee2,
                       String motifAnnee2, String texteAnnee3, String motifAnnee3,
                       String texteAnnee4, String motifAnnee4, String jour, String mois,
                       String annee, String pivotDate, String skip) {
        if (!Boolean.valueOf(skip)) {
            initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

            miseEnVeilleuseFromExcel(dateDebutPeriode, compteSolde, compteBancaire, memeEcran, annee1,
                    statutDecision1, motivation1, initiative, forme1, annee2, statutDecision2, motivation2,
                    annee3, statutDecision3, motivation3, annee4, statutDecision4, motivation4,
                    LIST_BCE_SOLDE_NUL, LIST_BCE_SOLDE_NEGATIF, LIST_BCE_SOLDE_POSITIF);

            finishTestExecution();
        }
    }
}