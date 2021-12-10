package be.ucm.cas.nasca.web.test.cloturedossier.succession;

import be.ucm.cas.nasca.web.test.carriere.CarriereTestBase;
import be.ucm.cas.nasca.web.test.cloturedossier.ClotureDossierTestBase;
import be.ucm.cas.nasca.web.test.enrolement.pp.EnrolementPPTestBase;
import be.ucm.cas.nasca.web.test.gestiondeces.GestionDecesTestBase;
import be.ucm.cas.nasca.web.test.support.DateQuarters;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class SuccessionTest extends ClotureDossierTestBase {

    private static final Map<Integer, String> mapRevision = new LinkedHashMap<>();

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_ClôtureDossier.xlsx", "Succession", "Succession", "Succ", null);
    }

    @Test(dataProvider = "data")
    public void successionFromExcel(String id, String libelle, String revision, String type,
                                    String dateDebut, String typeDate, String dateDeces, String suiteA, String commentaire,
                                    String annee, String resultat, String mouvementsAnnules) throws ParseException {
        initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        String niss = getNissForClotureDossier(revision, null, dateDebut);

        if (niss != null) {
            mapRevision.put(Integer.valueOf(id), niss);
        } else {
            niss = mapRevision.get(Integer.valueOf(revision));
        }

        switch (type) {
            case "Introduire":
                introduireSuccession(typeDate, dateDeces, resultat, niss, mouvementsAnnules);
                break;
            case "Observation":
                doAjoutObservationSuccession(niss, commentaire, suiteA);
                break;
            default:
                break;
        }

        finishTestExecution();
    }

    private void introduireSuccession(String typeDate, String dateDeces, String resultat, String niss, String mouvementsAnnules) throws ParseException {
        if ("DateSignaletique".equals(typeDate)) {
            doIntroduireDateDecesSignaletique(niss, dateDeces);
        }

        if (Boolean.valueOf(resultat)) {
            doIntroduireDateDecesSuccession(niss, typeDate, dateDeces);

            GestionDecesTestBase.doCheckDeces(niss);

            if (gestionClientPage.isEvenementPresentProfil("(21) Décès")) {
                logSuccess("Check Deces dans Profil pour " + niss + " OK");
            } else {
                logFailed("Check Deces dans Profil pour " + niss + " KO");
            }

            if (Boolean.valueOf(mouvementsAnnules) == true) {
                if (EnrolementPPTestBase.checkEnrolementFaitTrimestrePP(niss, DateUtils.getQuarterofDate(DateUtils.doFormatToString(TestData.DATE_FORMAT_SIMPLE, TestData.DATE_FORMAT_SIMPLE_WITH_SLASH, dateDeces)),
                        TestData.COTISATION, TestData.ENROLEMENT)) {
                    EnrolementPPTestBase.checkCreanceEtTypeOperationTrimestrePP(niss, "", TestData.COTISATION, TestData.ENROLEMENT_ANNULATION, DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, new Date()));
                }
            } else {
                EnrolementPPTestBase.checkCreanceEtTypeOperationAnnuleeTrimestrePP(niss,
                        DateUtils.getQuarterofDate(DateUtils.doFormatToString(TestData.DATE_FORMAT_SIMPLE, TestData.DATE_FORMAT_SIMPLE_WITH_SLASH, dateDeces)),
                        DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, new Date()));
            }

            if (dateDeces.equals(DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE_WITH_SLASH, new Date()))) {
                String periode = DateUtils.getQuarterofDate(DateUtils.doFormatToString(TestData.DATE_FORMAT_SIMPLE, TestData.DATE_FORMAT_SIMPLE_WITH_SLASH, dateDeces));

                CarriereTestBase.checkAucuneCarriere(niss, "trimestre", periode.substring(4), periode.substring(0, 4), "4");
            } else {
                String periode = DateUtils.getNextQuarterofDate(DateUtils.doFormatToString(TestData.DATE_FORMAT_SIMPLE, TestData.DATE_FORMAT_SIMPLE_WITH_SLASH, dateDeces));

                // Check trimestre du décès
                CarriereTestBase.checkCarriere(niss, "trimestre", periode.substring(4), periode.substring(0, 4), "4", "00 - Trimestre annulé", null, null, null, "true", null);

                // check trimestre précédent à la date du jour
                CarriereTestBase.checkCarriere(niss, "trimestre", DateQuarters.getOnlyNumberQuarter(DateUtils.getDateFuturOrPass(-180, new Date())), DateUtils.doFormat(TestData.DATE_FORMAT_YEAR_ONLY, new Date()), "4", "00 - Trimestre annulé", null, null, null, "true", null);
            }
        } else {
            doIntroduireDateDecesSuccessionAvecErreur(niss, dateDeces);
        }
    }
}