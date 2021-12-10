package be.ucm.cas.nasca.web.test.cloturedossier.asscontinuee;

import be.ucm.cas.nasca.web.test.carriere.CarriereTestBase;
import be.ucm.cas.nasca.web.test.cloturedossier.ClotureDossierTestBase;
import be.ucm.cas.nasca.web.test.comptabilite.ComptabiliteTestBase;
import be.ucm.cas.nasca.web.test.enrolement.pp.EnrolementPPTestBase;
import be.ucm.cas.nasca.web.test.impression.ImpressionTestBase;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.joda.time.DateTime;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class AssuranceContinueeTest extends ClotureDossierTestBase {

    private static final Map<Integer, String> mapRevision = new LinkedHashMap<>();

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_ClôtureDossier.xlsx", "Assurance Continuée", "Assurance Continuée", "AssCont", null);
    }

    @Test(dataProvider = "data")
    public void assuranceContinueeFromExcel(String id, String libelle, String revision, String type, String nature,
                                            String dateDebut, String rem, String demandeTardive, String origine, String recuPar, String dateCessation,
                                            String destinataire, String texte1, String texte2, String courrier, String dateDecision,
                                            String decision, String raisonRefus, String commentaireAutreRaison, String dateDebutAssCont, String dateFinAssCont,
                                            String typeAssCont, String reference, String commentaire, String suiteA, String annee, String resultat) {
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
                clotureAssuranceContinuee(rem, demandeTardive, origine, recuPar, dateCessation, destinataire, texte1, texte2, courrier, resultat, niss);
                break;
            case "Décision":
                decisionAssuranceContinuee(dateCessation, courrier, dateDecision, decision, raisonRefus, dateDebutAssCont, dateFinAssCont, typeAssCont, niss);
                break;
            case "Annulation":
                doAnnulationAssuranceContinuee(niss);
                break;
            case "InfosCompl":
                doEnvoiInfosComplementairesAssuranceContinuee(niss, origine, recuPar, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), destinataire, texte1, texte2);
                break;
            case "Observation":
                doAjoutObservationAssuranceContinuee(niss, reference, commentaire, suiteA);
                break;
            default:
                break;
        }

        finishTestExecution();
    }

    private void decisionAssuranceContinuee(String dateCessation, String courrier, String dateDecision, String decision, String raisonRefus, String dateDebutAssCont, String dateFinAssCont, String typeAssCont, String niss) {
        doReceptionDecisionAssuranceContinuee(niss, dateDecision, decision, raisonRefus, dateDebutAssCont, dateFinAssCont, dateCessation, typeAssCont, courrier);

        if (Boolean.valueOf(courrier)) {
            ImpressionTestBase.checkImpression(niss, TestData.TYPE_NISS, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), null, null, TestData.ETAT_A_IMPRIMER,
                    "PP - Assurance Continuée", null);
        }

        if (TestData.ACCEPTE.equals(decision)) {
            EnrolementPPTestBase.checkCreanceEtTypeOperationTrimestrePP(niss, "", TestData.COTISATION, TestData.ENROLEMENT_ANNULATION, null);

            // Affectation paiement
            DateTime currentDateTime = new DateTime();
            Date yesterday = currentDateTime.minusDays(3).toDate();
            String batchdate = DateUtils.doFormat(TestData.DATE_FORMAT_XML, yesterday);
            DaoFunctionality.getNascaDao().updateAffectationComptable(batchdate);

            yesterday = currentDateTime.minusDays(1).toDate();
            batchdate = DateUtils.doFormat(TestData.DATE_FORMAT_XML, yesterday);
            DaoFunctionality.getNascaDao().prepareAffectationComptable(batchdate);

            ComptabiliteTestBase.doAffectationPaiement(niss, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR_ONLY, new Date()) + "/1", true, true, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, yesterday));

            CarriereTestBase.checkCarriere(niss, "trimestre", "1", DateUtils.doFormat(TestData.DATE_FORMAT_YEAR_ONLY, new Date()), "4", "03 - Assurance continuée", null, null, null, "true", null);
        }
    }

    private void clotureAssuranceContinuee(String rem, String demandeTardive, String origine, String recuPar, String dateCessation, String destinataire, String texte1, String texte2, String courrier, String resultat, String niss) {
        if (Boolean.valueOf(resultat)) {
            if (Boolean.valueOf(rem)) {
                doAssuranceContinueeAvecREM(niss, origine, recuPar, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), dateCessation, demandeTardive, destinataire, texte1, texte2, courrier);

                ImpressionTestBase.checkImpression(niss, TestData.TYPE_NISS, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), null, null, TestData.ETAT_A_IMPRIMER,
                        "PP - Gestion Dossier et Cessation Activite - REM - Classique", null);
            } else {
                doAssuranceContinueeAvecCessation(niss, origine, recuPar, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), dateCessation, destinataire, texte1, texte2, courrier);
            }

            if (Boolean.valueOf(courrier)) {
                ImpressionTestBase.checkImpression(niss, TestData.TYPE_NISS, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), null, null, TestData.ETAT_A_IMPRIMER,
                        "PP - Assimilation maladie - Demande", null);

                ImpressionTestBase.checkImpression(niss, TestData.TYPE_NISS, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), null, null, TestData.ETAT_A_IMPRIMER,
                        "PP - Assimilation maladie - Demande introduite INASTI", null);
            }
        } else {
            doAssuranceContinueeAvecCessationAvecErreur(niss, origine, recuPar, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), dateCessation, destinataire, texte1, texte2, courrier);
        }
    }
}