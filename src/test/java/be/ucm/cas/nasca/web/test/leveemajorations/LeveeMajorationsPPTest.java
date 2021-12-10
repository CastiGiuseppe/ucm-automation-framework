package be.ucm.cas.nasca.web.test.leveemajorations;

import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LeveeMajorationsPPTest extends LeveeMajorationAbstract {

    private static List<String> listNissAvecCotisation = new ArrayList<>();
    private static List<String> listNissSansCotisation = new ArrayList<>();

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_LevéeMajorations.xlsx", "Levée majorations", "Levée majorations", "LM PP", null);
    }

    @BeforeTest
    public void launchLeveeMajorations() {
        doLaunchLm();
    }

    @Test(dataProvider = "data")
    public void leveeMajorationsFromExcel(String id, String libelle,
                                          String origineDemande, String demandeRecuePar,
                                          String typeLeveeMajoration, String dateEvenement,
                                          String codebiteurSolidaire, String reponseTiers, String mediateur,
                                          String paiementEnAttente, String document,
                                          String annulationDebitionErronee, String codeInasti, String typeLMFlux,
                                          String modificationLeveeMajoration,
                                          String validationLeveeMajoration, String resultat) {
        initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        String niss = selectionNiss(paiementEnAttente, getPeriode());

        String typeAffilie = TestData.TYPE_PP;
        Map<String, List<String>> periodesEtMontantsMajorationsLM = doLeveeMajoration(
                niss, typeAffilie, getPeriodeMoins3Ans(), getPeriode(),
                origineDemande, demandeRecuePar, typeLeveeMajoration,
                dateEvenement, codebiteurSolidaire, reponseTiers, mediateur,
                paiementEnAttente, document, annulationDebitionErronee, resultat);

        verificationDocument(resultat, typeAffilie, typeLeveeMajoration, niss, periodesEtMontantsMajorationsLM,
                typeLMFlux, modificationLeveeMajoration, validationLeveeMajoration, getPeriode(), getPeriodeMoins3Ans());

        finishTestExecution();
    }

    private String selectionNiss(String paiementEnAttente, String periode) {
        String niss;
        if (Boolean.valueOf(paiementEnAttente)) {
            if (listNissAvecCotisation.isEmpty()) {
                listNissAvecCotisation = DaoFunctionality.getNascaDao()
                        .getNissForLeveeMajoPPAvecCotisation(periode);
            }
            niss = listNissAvecCotisation.get(0);
            listNissAvecCotisation.remove(0);
        } else {
            if (listNissSansCotisation.isEmpty()) {
                listNissSansCotisation = DaoFunctionality.getNascaDao()
                        .getNissForLeveeMajoPPSansCotisation(periode);
            }
            niss = listNissSansCotisation.get(0);
            listNissSansCotisation.remove(0);
        }
        return niss;
    }
}