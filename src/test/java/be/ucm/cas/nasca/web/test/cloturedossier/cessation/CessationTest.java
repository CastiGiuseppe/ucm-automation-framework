package be.ucm.cas.nasca.web.test.cloturedossier.cessation;

import be.ucm.cas.nasca.web.test.carriere.CarriereTestBase;
import be.ucm.cas.nasca.web.test.cloturedossier.ClotureDossierTestBase;
import be.ucm.cas.nasca.web.test.enrolement.pp.EnrolementPPTestBase;
import be.ucm.cas.nasca.web.test.impression.ImpressionTestBase;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class CessationTest extends ClotureDossierTestBase {

    private static final Map<Integer, String> mapRevision = new LinkedHashMap<>();

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_ClôtureDossier.xlsx", "Cessation", "Cessation", "Cess", null);
    }

    @Test(dataProvider = "data")
    public void cessationFromExcel(String id, String libelle, String revision, String type,
                                   String dateDebut, String conjointAidant, String rem, String origine, String recuPar, String dateCessation,
                                   String justificationCjt, String commentaireAnnulation, String annulationTrimestrePension, String courrier,
                                   String reference, String commentaire, String suiteA, String annee, String resultat) {
        initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        String niss = null;

        if (revision == null || StringUtils.isEmpty(revision.trim())) {
            if (Boolean.valueOf(conjointAidant)) {
                niss = DaoFunctionality.getNascaDao().getNissforClotureDossierAvecCjtAidantSansExo(dateDebut);
            } else {
                niss = DaoFunctionality.getNascaDao().getNissForClotureDossier(dateDebut, null);
            }
        }

        if (niss != null) {
            mapRevision.put(Integer.valueOf(id), niss);
        } else {
            niss = mapRevision.get(Integer.valueOf(revision));
        }

        switch (type) {
            case "Clôture":
                clotureCessation(rem, origine, recuPar, dateCessation, justificationCjt, annulationTrimestrePension, courrier, resultat, niss);
                break;
            case "Modification":
                doModificationCessation(niss, origine, recuPar, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), dateCessation);
                break;
            case "Annulation":
                doAnnulationCessation(niss, origine, recuPar, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()));
                EnrolementPPTestBase.checkCreanceEtTypeOperationTrimestrePP(niss, "", TestData.COTISATION, TestData.ENROLEMENT, DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, new Date()));
                break;
            case "Observation":
                doAjoutObservationCessation(niss, reference, commentaire, suiteA);
                break;
            default:
                break;
        }

        finishTestExecution();
    }

    private void clotureCessation(String rem, String origine, String recuPar, String dateCessation, String justificationCjt, String annulationTrimestrePension, String courrier, String resultat, String niss) {
        if (Boolean.valueOf(resultat)) {
            if (Boolean.valueOf(rem)) {
                doCessationActiviteAvecREM(niss, origine, recuPar, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), dateCessation, annulationTrimestrePension, courrier, justificationCjt);

                ImpressionTestBase.checkImpression(niss, TestData.TYPE_NISS, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), null, null, TestData.ETAT_A_IMPRIMER,
                        "PP - Gestion Dossier et Cessation Activite - REM - Classique", null);
            } else {
                doCessationActiviteSansREM(niss, origine, recuPar, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), dateCessation, annulationTrimestrePension, courrier, justificationCjt);
            }

            EnrolementPPTestBase.checkCreanceEtTypeOperationTrimestrePP(niss, "", TestData.COTISATION, TestData.ENROLEMENT_ANNULATION, null);

            if (Boolean.valueOf(courrier)) {
                ImpressionTestBase.checkImpression(niss, TestData.TYPE_NISS, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), null, null, TestData.ETAT_A_IMPRIMER,
                        "PP - Cessation activité", null);
            }

            if (Boolean.valueOf(annulationTrimestrePension) == true) {
                CarriereTestBase.checkCarriere(niss, "trimestre", "4", dateCessation.substring(4), "4", "00 - Trimestre annulé", null, null, null, "true", null);
            }
            CarriereTestBase.checkCarriere(niss, "trimestre", "1", DateUtils.doFormat(TestData.DATE_FORMAT_YEAR_ONLY, new Date()), "4", "00 - Trimestre annulé", null, null, null, "true", null);
        } else {
            doCessationActiviteSansREMAvecErreur(niss, origine, recuPar, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), dateCessation);
        }
    }
}