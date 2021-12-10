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

public class LeveeMajorationsPMTest extends LeveeMajorationAbstract {

    private static List<String> listBceAvecCotisation = new ArrayList<>();
    private static List<String> listBceSansCotisation = new ArrayList<>();

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_LevéeMajorations.xlsx", "Levée majorations", "Levée majorations", "LM PM", null);
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

        String bce = selectionBce(paiementEnAttente,
                getPeriodeMoins3Ans());

        String typeAffilie = TestData.TYPE_PM;
        Map<String, List<String>> periodesEtMontantsMajorationsLM = doLeveeMajoration(
                bce, typeAffilie, getPeriodeMoins3Ans(), getPeriode(),
                origineDemande, demandeRecuePar, typeLeveeMajoration,
                dateEvenement, codebiteurSolidaire, reponseTiers, mediateur,
                paiementEnAttente, document, annulationDebitionErronee, resultat);

        verificationDocument(resultat, typeAffilie, typeLeveeMajoration, bce, periodesEtMontantsMajorationsLM,
                typeLMFlux, modificationLeveeMajoration, validationLeveeMajoration, getPeriode(), getPeriodeMoins3Ans());

        finishTestExecution();
    }

    private String selectionBce(String paiementEnAttente, String periodeMoins3Ans) {
        String bce;

        if (Boolean.valueOf(paiementEnAttente)) {
            if (listBceAvecCotisation.isEmpty()) {
                listBceAvecCotisation = DaoFunctionality.getNascaDao()
                        .getBceForLeveeMajoPMAvecCotisation(
                                periodeMoins3Ans);
            }
            bce = listBceAvecCotisation.get(0);
            listBceAvecCotisation.remove(0);
        } else {
            if (listBceSansCotisation.isEmpty()) {
                listBceSansCotisation = DaoFunctionality.getNascaDao()
                        .getBceForLeveeMajoPMSansCotisation(
                                periodeMoins3Ans);
            }
            bce = listBceSansCotisation.get(0);
            listBceSansCotisation.remove(0);
        }

        return bce;
    }
}