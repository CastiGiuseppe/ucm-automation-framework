package be.ucm.cas.nasca.web.test.cloturedossier.assmaladie;

import be.ucm.cas.nasca.web.test.carriere.CarriereTestBase;
import be.ucm.cas.nasca.web.test.cloturedossier.ClotureDossierTestBase;
import be.ucm.cas.nasca.web.test.enrolement.pp.EnrolementPPTestBase;
import be.ucm.cas.nasca.web.test.impression.ImpressionTestBase;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.apache.commons.lang.StringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class AssimilationMaladieTest extends ClotureDossierTestBase {

    private static final Map<Integer, String> mapRevision = new LinkedHashMap<>();

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_ClôtureDossier.xlsx", "Assimilation Maladie", "Assimilation Maladie", "AssMal", null);
    }

    @Test(dataProvider = "data")
    public void assimilationMaladieFromExcel(String id, String libelle, String revision, String type, String nature,
                                             String dateDebut, String rem, String origine, String recuPar, String dateCessation,
                                             String dateIncapacite, String affilieSeulSociete, String pasRadierBCE, String clotureDossier,
                                             String destinataire, String texte1, String texte2, String courrier, String dateDecision,
                                             String decision, String raisonPasEnOrdre, String raisonPasCessation, String raisonIncNonReconnue,
                                             String raisonPoursuiteActivite, String raisonExistanceRevenus, String raisonGerantSocActive, String raisonIncPasAssezLongue,
                                             String raisonActSalariee, String raisonAutreCouvSociale, String raisonPasSuiteDemRens, String raisonAutre,
                                             String dateDebutAssMal, String dateFinAssMal, String dateRepriseActivite, String natureCotisante,
                                             String reference, String commentaire, String suiteA, String annee, String resultat) {
        initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        String niss = getNissForClotureDossier(revision, nature, dateDebut);

        if (niss != null) {
            mapRevision.put(Integer.valueOf(id), niss);
        } else {
            niss = mapRevision.get(Integer.valueOf(revision));
        }

        DaoFunctionality.getNascaDao().deleteCarriereMouvement(niss);

        switch (type) {
            case "Clôture":
                clotureAssimilationMaladie(rem, origine, recuPar, dateCessation, dateIncapacite, affilieSeulSociete, pasRadierBCE, clotureDossier, destinataire, texte1, texte2, courrier, resultat, niss);
                break;
            case "Décision":
                decisionAssimilationMaladie(courrier, dateDecision, decision, raisonPasEnOrdre, raisonPasCessation, raisonIncNonReconnue, raisonPoursuiteActivite, raisonExistanceRevenus, raisonGerantSocActive, raisonIncPasAssezLongue, raisonActSalariee, raisonAutreCouvSociale, raisonPasSuiteDemRens, raisonAutre, dateDebutAssMal, dateFinAssMal, dateRepriseActivite, natureCotisante, niss, clotureDossier);
                break;
            case "Annulation":
                doAnnulationAssimilationMaladie(niss);
                break;
            case "InfosCompl":
                doEnvoiInfosComplementairesAssimilationMaladie(niss, origine, recuPar, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), destinataire, texte1, texte2);
                break;
            case "Observation":
                doAjoutObservationAssimilationMaladie(niss, reference, commentaire, suiteA);
                break;
            default:
                break;
        }

        finishTestExecution();
    }

    private void decisionAssimilationMaladie(String courrier, String dateDecision, String decision, String raisonPasEnOrdre, String raisonPasCessation, String raisonIncNonReconnue, String raisonPoursuiteActivite, String raisonExistanceRevenus, String raisonGerantSocActive, String raisonIncPasAssezLongue, String raisonActSalariee, String raisonAutreCouvSociale, String raisonPasSuiteDemRens, String raisonAutre, String dateDebutAssMal, String dateFinAssMal, String dateRepriseActivite, String natureCotisante, String niss, String clotureDossier) {
        doReceptionDecisionAssimilationMaladie(niss, dateDecision, decision, raisonPasEnOrdre, raisonPasCessation, raisonIncNonReconnue, raisonPoursuiteActivite, raisonExistanceRevenus, raisonGerantSocActive, raisonActSalariee, raisonIncPasAssezLongue, raisonAutreCouvSociale, raisonPasSuiteDemRens, raisonAutre, dateDebutAssMal, dateFinAssMal, dateRepriseActivite, natureCotisante, courrier, clotureDossier);

        if (Boolean.valueOf(courrier)) {
            ImpressionTestBase.checkImpression(niss, TestData.TYPE_NISS, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), null, null, TestData.ETAT_A_IMPRIMER,
                    "PP - Assimilation maladie - Décision", null);
        }

        if (TestData.ACCEPTE.equals(decision)) {
            if (!Boolean.valueOf(clotureDossier)) {
                EnrolementPPTestBase.checkCreanceEtTypeOperationTrimestrePP(niss, "", TestData.COTISATION, TestData.ENROLEMENT_ANNULATION, null);
            } else {
                EnrolementPPTestBase.checkAucuneOperationTrimestrePP(niss, "", DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), null, TestData.COTISATION_PP, TestData.PAS_DE_REGROUPEMENT);
            }

            if (dateFinAssMal != null && !StringUtils.isEmpty(dateFinAssMal.trim())) {
                CarriereTestBase.checkCarriere(niss, "trimestre", "1", dateDebutAssMal.substring(4), "4", "07 - Assurance maladie invalidité", null, null, null, "true", null);
            }
        }
    }

    private void clotureAssimilationMaladie(String rem, String origine, String recuPar, String dateCessation, String dateIncapacite, String affilieSeulSociete, String pasRadierBCE, String clotureDossier, String destinataire, String texte1, String texte2, String courrier, String resultat, String niss) {
        if (Boolean.valueOf(resultat)) {
            if (Boolean.valueOf(rem)) {
                doAssimilationMaladieAvecREM(niss, origine, recuPar, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), dateCessation, clotureDossier, destinataire, texte1, texte2, dateIncapacite, affilieSeulSociete, pasRadierBCE, courrier);

                ImpressionTestBase.checkImpression(niss, TestData.TYPE_NISS, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), null, null, TestData.ETAT_A_IMPRIMER,
                        "PP - Assimilation Maladie Rem", null);
            } else {
                doAssimilationMaladieSansREM(niss, origine, recuPar, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), dateCessation, clotureDossier, destinataire, texte1, texte2, courrier);
            }

            if (Boolean.valueOf(courrier)) {
                ImpressionTestBase.checkImpression(niss, TestData.TYPE_NISS, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), null, null, TestData.ETAT_A_IMPRIMER,
                        "PP - Assimilation maladie - Demande", null);
            }

            ImpressionTestBase.checkImpression(niss, TestData.TYPE_NISS, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), null, null, TestData.ETAT_A_IMPRIMER,
                    "PP - Assimilation maladie - Demande introduite INASTI", null);
        } else {
            doAssimilationMaladieSansREMAvecErreur(niss, origine, recuPar, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), dateCessation, clotureDossier, destinataire, texte1, texte2, courrier);
        }
    }
}