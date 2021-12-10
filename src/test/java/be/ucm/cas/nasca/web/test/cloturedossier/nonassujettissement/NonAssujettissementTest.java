package be.ucm.cas.nasca.web.test.cloturedossier.nonassujettissement;

import be.ucm.cas.nasca.web.test.carriere.CarriereTestBase;
import be.ucm.cas.nasca.web.test.cloturedossier.ClotureDossierTestBase;
import be.ucm.cas.nasca.web.test.enrolement.pp.EnrolementPPTestBase;
import be.ucm.cas.nasca.web.test.impression.ImpressionTestBase;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateQuarters;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import be.ucm.cas.nasca.web.test.taches.TachesTestBase;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class NonAssujettissementTest extends ClotureDossierTestBase {

    private static final Map<Integer, String> mapRevision = new LinkedHashMap<>();

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_ClôtureDossier.xlsx", "Non Assujettissement", "Non Assujettissement", "NonAssuj", null);
    }

    @Test(dataProvider = "data")
    public void nonAssujettissementFromExcel(String id, String libelle, String revision, String type,
                                             String dateDebut, String affilieCjtAidant, String dateDebutActivite, String conjointAidant, String conjointAidantExo, String rem,
                                             String origine, String recuPar, String justificationCjt, String justificationAffiFictive,
                                             String courrier, String reference, String commentaire, String suiteA,
                                             String annee, String resultat) {
        initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        String niss = null;

        if (revision == null || StringUtils.isEmpty(revision.trim())) {
            if (Boolean.valueOf(affilieCjtAidant)) {
                niss = DaoFunctionality.getNascaDao().getNissforClotureDossierCjtAidant(dateDebut);
            } else {
                if (Boolean.valueOf(conjointAidant)) {
                    niss = selectionConjointAidant(dateDebut, conjointAidantExo);
                } else {
                    niss = DaoFunctionality.getNascaDao().getNissForClotureDossier(dateDebut, null);
                }
            }
        }

        if (niss != null) {
            mapRevision.put(Integer.valueOf(id), niss);
        } else {
            niss = mapRevision.get(Integer.valueOf(revision));
        }

        switch (type) {
            case "Clôture":
                clotureNonAssujettissement(conjointAidant, conjointAidantExo, rem, origine, recuPar, justificationCjt, justificationAffiFictive, courrier, resultat, niss);
                break;
            case "Observation":
                doAjoutObservationNonAssujettissement(niss, reference, commentaire, suiteA);
                break;
            default:
                break;
        }

        finishTestExecution();
    }

    private String selectionConjointAidant(String dateDebut, String conjointAidantExo) {
        String niss;
        if (!Boolean.valueOf(conjointAidantExo)) {
            niss = DaoFunctionality.getNascaDao().getNissforClotureDossierAvecCjtAidantSansExo(dateDebut);
        } else {
            niss = DaoFunctionality.getNascaDao().getNissforClotureDossierAvecCjtAidantAvecExo(dateDebut);
        }
        return niss;
    }

    private void clotureNonAssujettissement(String conjointAidant, String conjointAidantExo, String rem, String origine, String recuPar, String justificationCjt, String justificationAffiFictive, String courrier, String resultat, String niss) {
        if (Boolean.valueOf(resultat)) {
            String dateAffiliation;
            if (Boolean.valueOf(rem)) {
                dateAffiliation = doNonAssujettissementAvecREM(niss, origine, recuPar, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), justificationAffiFictive, justificationCjt, courrier);

                ImpressionTestBase.checkImpression(niss, TestData.TYPE_NISS, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), null, null, TestData.ETAT_A_IMPRIMER,
                        "PP - NonAssujettissement REM", null);
            } else {
                dateAffiliation = doNonAssujettissementSansREM(niss, origine, recuPar, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), justificationAffiFictive, justificationCjt, courrier);
            }

            if (Boolean.valueOf(conjointAidantExo)) {
                TachesTestBase.searchAndFinishNotification(niss, "Non-assujettissement avec conjoint bénéficiant d’une exonération/réduction");
            } else {
                if (Boolean.valueOf(conjointAidant)) {
                    TachesTestBase.searchAndFinishNotification(niss, "Non assujettissement dossier conjoint aidé.");
                }
            }

            EnrolementPPTestBase.checkCreanceEtTypeOperationTrimestrePP(niss, "", TestData.COTISATION, TestData.ENROLEMENT_ANNULATION, null);

            if (Boolean.valueOf(courrier)) {
                ImpressionTestBase.checkImpression(niss, TestData.TYPE_NISS, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), null, null, TestData.ETAT_A_IMPRIMER,
                        "PP - NonAssujettissement", null);
            }

            // check trimestre du début d'affiliation
            CarriereTestBase.checkCarriere(niss, "trimestre", DateQuarters.getOnlyNumberQuarter(DateUtils.doConvertStringtoDatTime(dateAffiliation.replaceAll("/", "")).toDate()), dateAffiliation.substring(6), "4", "00 - Trimestre annulé", null, null, null, "true", null);

            // check trimestre précédent à la date du jour
            CarriereTestBase.checkCarriere(niss, "trimestre", DateQuarters.getOnlyNumberQuarter(DateUtils.getDateFuturOrPass(-180, new Date())), DateUtils.doFormat(TestData.DATE_FORMAT_YEAR_ONLY, DateUtils.getDateFuturOrPass(-180, new Date())), "4", "00 - Trimestre annulé", null, null, null, "true", null);
        } else {
            if (Boolean.valueOf(rem)) {
                doNonAssujettissementAvecREMAvecErreur(niss, origine, recuPar, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()));
            } else {
                doNonAssujettissementSansREMAvecErreur(niss, origine, recuPar, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), justificationAffiFictive, justificationCjt, courrier);
            }
        }
    }
}