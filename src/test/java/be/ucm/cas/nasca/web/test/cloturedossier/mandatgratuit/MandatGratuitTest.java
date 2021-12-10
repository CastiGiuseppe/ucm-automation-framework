package be.ucm.cas.nasca.web.test.cloturedossier.mandatgratuit;

import be.ucm.cas.nasca.web.test.carriere.CarriereTestBase;
import be.ucm.cas.nasca.web.test.cloturedossier.ClotureDossierTestBase;
import be.ucm.cas.nasca.web.test.enrolement.pp.EnrolementPPTestBase;
import be.ucm.cas.nasca.web.test.impression.ImpressionTestBase;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateQuarters;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class MandatGratuitTest extends ClotureDossierTestBase {

    private static final Map<Integer, String> mapRevision = new LinkedHashMap<>();

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_ClôtureDossier.xlsx", "Mandat Gratuit", "Mandat Gratuit", "Mdt Gratuit", null);
    }

    @Test(dataProvider = "data")
    public void mandatGratuitFromExcel(String id, String libelle, String revision, String type, String nature,
                                       String dateDebut, String rem, String origine, String recuPar, String dateCessation,
                                       String decision, String raisonRefus, String commentaireRefusAutre, String annulationTrimestrePension,
                                       String courrier, String difficultePaiement, String attestationCompl, String declarationAffiliation,
                                       String reference, String commentaire, String suiteA, String annee, String resultat, String menuInactif) {
        initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        String niss = getNissForClotureDossier(revision, nature, dateDebut);

        DaoFunctionality.getNascaDao().deleteCarriereMouvement(niss);

        if (niss != null) {
            mapRevision.put(Integer.valueOf(id), niss);
        } else {
            niss = mapRevision.get(Integer.valueOf(revision));
        }

        switch (type) {
            case "Clôture":
                clotureMandatGratuit(rem, origine, recuPar, dateCessation, decision, raisonRefus, commentaireRefusAutre, annulationTrimestrePension, courrier, difficultePaiement, attestationCompl, declarationAffiliation, commentaire, resultat, niss, menuInactif);
                break;
            case "Observation":
                doAjoutObservationMandatGratuit(niss, reference, commentaire, suiteA);
                break;
            default:
                break;
        }

        finishTestExecution();
    }

    private void clotureMandatGratuit(String rem, String origine, String recuPar, String dateCessation, String decision, String raisonRefus, String commentaireRefusAutre, String annulationTrimestrePension, String courrier, String difficultePaiement, String attestationCompl, String declarationAffiliation, String commentaire, String resultat, String niss, String menuInactif) {
        if (Boolean.valueOf(resultat)) {
            if (Boolean.valueOf(rem)) {
                doMandatGratuitAvecREM(niss, origine, recuPar, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), dateCessation, decision, raisonRefus, annulationTrimestrePension, commentaireRefusAutre, difficultePaiement, attestationCompl, declarationAffiliation, courrier);

                ImpressionTestBase.checkImpression(niss, TestData.TYPE_NISS, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), null, null, TestData.ETAT_A_IMPRIMER, "PP - Mandat gratuit - REM", null);
            } else {
                doMandatGratuitAvecCessation(niss, origine, recuPar, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), dateCessation, decision, raisonRefus, annulationTrimestrePension, commentaireRefusAutre, difficultePaiement, attestationCompl, declarationAffiliation, courrier);
            }

            if (TestData.ACCEPTE.equals(decision)) {
                EnrolementPPTestBase.checkCreanceEtTypeOperationTrimestrePP(niss, "", TestData.COTISATION, TestData.ENROLEMENT_ANNULATION, null);

                if (Boolean.valueOf(annulationTrimestrePension)) {
                    CarriereTestBase.checkCarriere(niss, "trimestre", DateQuarters.getOnlyNumberQuarter(DateUtils.doConvertStringtoDatTime(dateCessation.replaceAll("/", "")).toDate()), dateCessation.substring(4), "4", "00 - Trimestre annulé", null, null, null, "true", null);
                }
                CarriereTestBase.checkCarriere(niss, "trimestre", "1", DateUtils.doFormat(TestData.DATE_FORMAT_YEAR_ONLY, new Date()), "4", "00 - Trimestre annulé", null, null, null, "true", null);
            } else {
                EnrolementPPTestBase.checkAucuneOperationTrimestrePP(niss, "", DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), TestData.ENROLEMENT, TestData.COTISATION_PP, TestData.PAS_DE_REGROUPEMENT);
            }

            if (Boolean.valueOf(courrier)) {
                ImpressionTestBase.checkImpression(niss, TestData.TYPE_NISS, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), null, null, TestData.ETAT_A_IMPRIMER, "PP - Mandat gratuit", null);
            }
        } else {
            if (Boolean.valueOf(menuInactif)) {
                checkMenuInactifMandatGratuit(niss);
            } else {
                doMandatGratuitAvecCessationAvecErreur(niss, origine, recuPar, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), dateCessation, decision, raisonRefus, annulationTrimestrePension, commentaire, difficultePaiement, attestationCompl, declarationAffiliation, courrier);
            }
        }
    }
}