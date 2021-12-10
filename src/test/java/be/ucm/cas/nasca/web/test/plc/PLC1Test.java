package be.ucm.cas.nasca.web.test.plc;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class PLC1Test extends PLCAbstract {

    private static final Map<Integer, String> mapRevision = new LinkedHashMap<>();

    private String periode = null;

    private final Calendar calJour = Calendar.getInstance();

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_PLC.xlsx", "PLC", "PLC", "Plc" + "1", null);
    }

    @BeforeTest
    public void launchPlc() {
        calJour.set(Calendar.DAY_OF_MONTH, 2);
        calJour.set(Calendar.MONTH, Calendar.JANUARY);
        periode = calJour.get(Calendar.YEAR) + "1";
    }

    @Test(dataProvider = "data")
    public void doTest(String id, String libelle, String revision, String type, String natureCotisante,
                       String regime, String dateDebut, String exoReduction, String nationalite, String paysNaissance,
                       String anneeRevenu, String revenu, String typeRevenu, String contratEnCours, String contratDormant,
                       String contratCloture, String origine, String origineTiers, String origineOrganisme, String recuePar,
                       String numeroContrat, String numeroVCS, String natureConvention, String typeInvestissement,
                       String datePriseEffet, String decision, String motifRefus, String statut, String dateSuspension,
                       String motifSuspension, String dateCloture, String motifCloture, String dateReveil, String motifReveil,
                       String reference, String typePlc, String commentaire, String suiteA, String resultat,
                       String messageInformatif, String notification, String profilNonAffilie, String regimeNonAffilie,
                       String exonerationNonAffilie, String reductionNonAffilie, String pensionSurvieNonAffilie, String anneeRevenuNonAffilie,
                       String revenuNonAffilie, String cotisation) {
        plcFromExcel(id, revision, type, natureCotisante, regime, dateDebut, exoReduction, nationalite, paysNaissance,
                anneeRevenu, revenu, typeRevenu, contratEnCours, contratDormant, contratCloture, origine, origineTiers,
                origineOrganisme, recuePar, numeroContrat, numeroVCS, natureConvention, typeInvestissement, datePriseEffet,
                decision, motifRefus, statut, dateSuspension, motifSuspension, dateCloture, motifCloture, dateReveil, motifReveil,
                reference, typePlc, commentaire, suiteA, resultat, messageInformatif, notification, profilNonAffilie, regimeNonAffilie,
                exonerationNonAffilie, reductionNonAffilie, pensionSurvieNonAffilie, anneeRevenuNonAffilie, revenuNonAffilie, cotisation,
                mapRevision, periode, calJour, libelle, Thread.currentThread().getStackTrace()[1].getFileName());
    }
}