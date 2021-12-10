package be.ucm.cas.nasca.web.test.exonerationpm;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

public class ExonerationPM1Test extends ExonerationPMAbstract {

    private static final Map<Integer, String> MAP_REVISION = new LinkedHashMap<>();
    private static final List<String> LIST_BCE_AVEC_CO = new ArrayList<>();
    private static final List<String> LIST_BCE_SANS_CO = new ArrayList<>();

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Exonération.xlsx", "Exonération PM", "Exonération Personne Morale", "ExonérationPM1", null);
    }

    @Test(dataProvider = "data")
    public void doTest(String id, String libelle,
                       String dateDebutPeriode,String trieDate, String caisseOrigine, String revision,
                       String dateSocieteCommercial,String dateSignature,String dateReception,
                       String attention, String mev, String spAnnee1, String bceAnnee1,
                       String maaAnnee1, String spAnnee2, String bceAnnee2,
                       String maaAnnee2, String spAnnee3, String bceAnnee3,
                       String maaAnnee3, String statut, String explicationStatut, String texte1, String texte2, String texte3,
                       String texte4, String texte5, String texte6, String texteAnnee1,
                       String texteAnnee2, String libelleCommun, String statutAutre,
                       String statutAttention, String rrAnnee1, String rrAnnee2,
                       String rrAnnee3, String an1, String an2, String an3, String jour,
                       String mois, String annee, String pivotDate) {
        initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        exonerationPmFromExcel(id, dateDebutPeriode, caisseOrigine, revision,
                dateSocieteCommercial,dateSignature,dateReception, attention, spAnnee1, bceAnnee1,
                maaAnnee1, spAnnee2, bceAnnee2, maaAnnee2, spAnnee3, bceAnnee3, maaAnnee3, statut,
                explicationStatut,
                MAP_REVISION, LIST_BCE_AVEC_CO, LIST_BCE_SANS_CO);

        finishTestExecution();
    }
}