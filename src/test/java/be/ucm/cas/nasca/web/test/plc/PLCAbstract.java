package be.ucm.cas.nasca.web.test.plc;

import be.ucm.cas.nasca.web.test.cloturedossier.ClotureDossierTestBase;
import be.ucm.cas.nasca.web.test.enrolement.pp.EnrolementPPTestBase;
import be.ucm.cas.nasca.web.test.impression.ImpressionTestBase;
import be.ucm.cas.nasca.web.test.profil.ProfilTestBase;
import be.ucm.cas.nasca.web.test.revenu.RevenuTestBase;
import be.ucm.cas.nasca.web.test.signaletique.SignaletiqueTestBase;
import be.ucm.cas.nasca.web.test.support.*;
import be.ucm.cas.nasca.web.test.taches.TachesTestBase;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.Map;

public abstract class PLCAbstract extends PLCTestBase {

    private static final String NOM_DOCUMENT = "Template : PP - PLC - PpPLCAvisApresSignature ; Contexte = Défaut";
    private static final String DECISION_ACCORD = "Accord - Envoyer premier enrôlement";

    void plcFromExcel(String id, String revision, String type, String natureCotisante,
                      String regime, String dateDebut, String exoReduction, String nationalite, String paysNaissance,
                      String anneeRevenu, String revenu, String typeRevenu, String contratEnCours, String contratDormant,
                      String contratCloture, String origine, String origineTiers, String origineOrganisme, String recuePar,
                      String numeroContrat, String numeroVCS, String natureConvention, String typeInvestissement,
                      String datePriseEffet, String decision, String motifRefus, String statut, String dateSuspension,
                      String motifSuspension, String dateCloture, String motifCloture, String dateReveil, String motifReveil,
                      String reference, String typePlc, String commentaire, String suiteA, String resultat,
                      String messageInformatif, String notification, String profilNonAffilie, String regimeNonAffilie,
                      String exonerationNonAffilie, String reductionNonAffilie, String pensionSurvieNonAffilie, String anneeRevenuNonAffilie,
                      String revenuNonAffilie, String cotisation, Map<Integer, String> mapRevision, String periode, Calendar calJour,
                      String libelle, String fileName) {
        initTest(libelle, fileName);

        loginNasca();

        Calendar calFirstDayOfYear = Calendar.getInstance();
        calFirstDayOfYear.set(Calendar.MONTH, Calendar.JANUARY);
        calFirstDayOfYear.set(Calendar.DAY_OF_MONTH, 1);

        String niss = null;
        if (revision == null || StringUtils.isEmpty(revision.trim())) {
            switch (type) {
                case TestData.NOUVEAU:
                case "Nouveau Flux Affilié":
                case "Changement Profil":
                case "Changement Revenu":
                case "Décès Signalétique":
                case "Changement Adresse":
                case "Décès Clôture Dossier":
                case "Flux A020":
                    niss = selectionDossierAndPreparation(natureCotisante, regime, dateDebut, exoReduction, nationalite, paysNaissance, anneeRevenu, revenu, typeRevenu, contratEnCours, contratDormant, contratCloture, calJour, DateUtils.doFormat(TestData.DATE_FORMAT_XML, calFirstDayOfYear.getTime()));
                    break;
                case "Nouveau Flux Non Affilié":
                    niss = TestData.NISS_NUMBER_SIGNA;
                    DaoFunctionality.cleanAllDataByIdentite(niss);
                    break;
                default:
                    break;
            }
        }

        if (niss != null) {
            mapRevision.put(Integer.valueOf(id), niss);
        } else {
            if (revision != null && !StringUtils.isEmpty(revision.trim())) {
                niss = mapRevision.get(Integer.valueOf(revision));
            }
        }

        if (niss != null) {
            switch (type) {
                case TestData.NOUVEAU:
                    creationNouveauContrat(contratEnCours, origine, origineTiers, origineOrganisme, recuePar, numeroContrat, numeroVCS, natureConvention, typeInvestissement, datePriseEffet, decision, motifRefus, statut, resultat, messageInformatif, cotisation, periode, calJour, niss);
                    break;
                case "Suspendre":
                    clotureOuSuspensionContrat(statut, dateSuspension, motifSuspension, periode, niss, "Suspension");
                    break;
                case "Reveiller":
                    reveilContrat(statut, dateReveil, motifReveil, cotisation, periode, niss);
                    break;
                case "Clôturer":
                    clotureOuSuspensionContrat(statut, dateCloture, motifCloture, periode, niss, "Clôture");
                    break;
                case "Modifier":
                    PLCTestBase.modifierDonneesContratPLC(niss, numeroContrat, numeroVCS);
                    break;
                case "Observation":
                    PLCTestBase.ajouterObservationContratPLC(niss, reference, commentaire, typePlc, suiteA);
                    break;
                case "Nouveau Flux Affilié":
                    creationNouveauContratAffilieViaFlux(contratEnCours, numeroContrat, numeroVCS, natureConvention, typeInvestissement, datePriseEffet, decision, motifRefus, statut, notification, cotisation, periode, calJour, niss);
                    break;
                case "Nouveau Flux Non Affilié":
                    creationNouveauContratNonAffilieViaFlux(origine, origineTiers, origineOrganisme, recuePar, numeroContrat, numeroVCS, natureConvention, typeInvestissement, datePriseEffet, decision, motifRefus, statut, resultat, messageInformatif, notification, profilNonAffilie, regimeNonAffilie, exonerationNonAffilie, reductionNonAffilie, pensionSurvieNonAffilie, anneeRevenuNonAffilie, revenuNonAffilie, cotisation, periode, calJour, niss);
                    break;
                case "Changement Adresse":
                    changementAdresse(notification, niss);
                    break;
                case "Changement Profil":
                    changementProfil(notification, calJour, niss);
                    break;
                case "Changement Revenu":
                    interactionsSignaletique(notification, niss);
                    break;
                case "Décès Signalétique":
                    decesViaSignaletique(notification, calJour, niss);
                    break;
                case "Décès Clôture Dossier":
                    decesViaSuccession(notification, calJour, niss);
                    break;
//                case "Flux A020":
//                    traitementFluxA020(niss, notification);
//                    break;
                default:
                    break;
            }
        } else {
            if (revision != null && !StringUtils.isEmpty(revision.trim())) {
                logSkip(Thread.currentThread().getStackTrace()[1].getFileName(), "Test dependant en echec");
            } else {
                logSkip(Thread.currentThread().getStackTrace()[1].getFileName(), TestData.PAS_CAS_TROUVE_DB);
            }
        }

        finishTestExecution();
    }

    private String selectionDossierAndPreparation(String natureCotisante, String regime, String dateDebut, String exoReduction, String nationalite, String paysNaissance, String anneeRevenu, String revenu, String typeRevenu, String contratEnCours, String contratDormant, String contratCloture, Calendar calJour, String dateFirstDayOfYear) {
        String niss = DaoFunctionality.getNascaDao().getNissNouveauContratPLC(natureCotisante, dateDebut, regime, nationalite, contratEnCours, contratDormant, contratCloture, dateFirstDayOfYear);

        if (exoReduction != null && !StringUtils.isEmpty(exoReduction.trim())) {
            String natureProfil = "";
            switch (natureCotisante) {
                case "1":
                    natureProfil = TestData.NATURE_PROFILE_PRINCIPAL;
                    break;
                case "4":
                    natureProfil = TestData.NATURE_PROFILE_COMPLEMENTAIRE;
                    break;
                case "5":
                    natureProfil = TestData.NATURE_PROFILE_MAXI_STATUT;
                    break;
                default:
                    break;
            }

            ProfilTestBase.doModificationLigneProfil(niss, natureProfil, null, null, ProfilTestBase.doTransformReduction(exoReduction));
        }
        if (anneeRevenu != null && !StringUtils.isEmpty(anneeRevenu.trim())) {
            RevenuTestBase.ajouterRevenuSansErreur(niss, anneeRevenu, revenu, typeRevenu, TestData.STATUT_REVENU_A_REGULARISER, TestData.SOURCE_REVENU_INASTI_FLUX, true, "3112" + (calJour.get(Calendar.YEAR) - 1), "false");
        }
        if (paysNaissance != null && !StringUtils.isEmpty(paysNaissance.trim())) {
            SignaletiqueTestBase.doChangePaysNaissance(niss, paysNaissance);
        }
        return niss;
    }

    private void decesViaSuccession(String notification, Calendar calJour, String niss) {
        ClotureDossierTestBase.doIntroduireDateDecesSuccession(niss, "DateAutre", DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, calJour.getTime()));
        interactionsSignaletique(notification, niss);
    }

    private void traitementFluxA020(String niss, String notification) {
        BatchUtils.createAndSendXmlFluxA020(niss);

        RunBatch.runBatchReceptionFluxA020Prepare();
        RunBatch.runBatchReceptionFluxA020Processing();

        TachesTestBase.searchAndFinishNotification(niss, notification);
    }

    private void decesViaSignaletique(String notification, Calendar calJour, String niss) {
        ClotureDossierTestBase.doIntroduireDateDecesSignaletique(niss, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, calJour.getTime()));
        interactionsSignaletique(notification, niss);
    }

    private void interactionsSignaletique(String notification, String niss) {
        RunBatch.runBatchInteractionsSignaletique();
        TachesTestBase.searchAndFinishNotification(niss, notification);
    }

    private void changementProfil(String notification, Calendar calJour, String niss) {
        calJour.set(Calendar.DAY_OF_MONTH, 1);
        calJour.set(Calendar.MONTH, Calendar.JANUARY);

        ProfilTestBase.doChangeProfil(niss, "Complémentaire", DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, calJour.getTime()), "");

        interactionsSignaletique(notification, niss);
    }

    private void changementAdresse(String notification, String niss) {
        SignaletiqueTestBase.doChangeAdresse(niss, "France", "19100", "rue de la loi", "45", "1");
        interactionsSignaletique(notification, niss);
    }

    private void creationNouveauContratNonAffilieViaFlux(String origine, String origineTiers, String origineOrganisme, String recuePar, String numeroContrat, String numeroVCS, String natureConvention, String typeInvestissement, String datePriseEffet, String decision, String motifRefus, String statut, String resultat, String messageInformatif, String notification, String profilNonAffilie, String regimeNonAffilie, String exonerationNonAffilie, String reductionNonAffilie, String pensionSurvieNonAffilie, String anneeRevenuNonAffilie, String revenuNonAffilie, String cotisation, String periode, Calendar calJour, String niss) {
        BatchUtils.createAndSendXmlViaxis(numeroContrat, natureConvention, typeInvestissement, numeroVCS, DateUtils.doFormat(TestData.DATE_FORMAT_XML, calJour.getTime()), niss, "NomTest", "PrénomTest");

        RunBatch.runBatchReceptionFichierViaxis();

        TachesTestBase.searchAndFinishNotificationSansIdentifiant(niss, notification);

        SignaletiqueTestBase.creationPP(niss);

        if (Boolean.valueOf(resultat)) {
            PLCTestBase.creationNouveauContratPLCNonAffilie(niss, origine, origineTiers, origineOrganisme, recuePar, numeroContrat, numeroVCS, natureConvention, typeInvestissement, datePriseEffet, decision, motifRefus, statut, messageInformatif, profilNonAffilie, anneeRevenuNonAffilie, revenuNonAffilie, exonerationNonAffilie, reductionNonAffilie, regimeNonAffilie, pensionSurvieNonAffilie);

            if (DECISION_ACCORD.equals(decision)) {
                EnrolementPPTestBase.checkSoldesCotisationPLCTrimestrePP(niss, periode, cotisation);
                ImpressionTestBase.checkImpression(niss, TestData.TYPE_NISS, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, calJour.getTime()), null, null, TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT, null);
            }
        } else {
            PLCTestBase.creationNouveauContratPLCNonAffilieAvecErreur(niss, origine, origineTiers, origineOrganisme, recuePar, numeroContrat, numeroVCS, natureConvention, typeInvestissement, datePriseEffet, decision, motifRefus, messageInformatif, profilNonAffilie, anneeRevenuNonAffilie, revenuNonAffilie, exonerationNonAffilie, reductionNonAffilie, regimeNonAffilie, pensionSurvieNonAffilie);
        }
    }

    private void creationNouveauContratAffilieViaFlux(String contratEnCours, String numeroContrat, String numeroVCS, String natureConvention, String typeInvestissement, String datePriseEffet, String decision, String motifRefus, String statut, String notification, String cotisation, String periode, Calendar calJour, String niss) {
        BatchUtils.createAndSendXmlViaxis(numeroContrat, natureConvention, typeInvestissement, numeroVCS, DateUtils.doFormat(TestData.DATE_FORMAT_XML, calJour.getTime()), niss, "nom", "prenom");

        RunBatch.runBatchReceptionFichierViaxis();

        if (Boolean.valueOf(contratEnCours)) {
            TachesTestBase.searchAndFinishNotification(niss, notification);
        } else {
            TachesTestBase.searchAndFinishTachePLC(niss, notification, datePriseEffet, decision, motifRefus, statut);

            if (DECISION_ACCORD.equals(decision)) {
                EnrolementPPTestBase.checkSoldesCotisationPLCTrimestrePP(niss, periode, cotisation);
                ImpressionTestBase.checkImpression(niss, TestData.TYPE_NISS, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, calJour.getTime()), null, null, TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT, null);
            }
        }
    }

    private void clotureOuSuspensionContrat(String statut, String date, String motif, String periode, String niss, String type) {
        if (type.equals("Clôture")) {
            PLCTestBase.clotureContratPLC(niss, date, motif, statut);
        } else {
            PLCTestBase.suspensionContratPLC(niss, date, motif, statut);
        }
        EnrolementPPTestBase.checkCreanceEtTypeOperationPLCTrimestrePP(niss, periode, "Cotisation PLC", TestData.ENROLEMENT_ANNULATION, null);
    }

    private void reveilContrat(String statut, String dateReveil, String motifReveil, String cotisation, String periode, String niss) {
        PLCTestBase.reveilContratPLC(niss, dateReveil, motifReveil, statut);
        EnrolementPPTestBase.checkSoldesCotisationPLCTrimestrePP(niss, periode, cotisation);
    }

    private void creationNouveauContrat(String contratEnCours, String origine, String origineTiers, String origineOrganisme, String recuePar, String numeroContrat, String numeroVCS, String natureConvention, String typeInvestissement, String datePriseEffet, String decision, String motifRefus, String statut, String resultat, String messageInformatif, String cotisation, String periode, Calendar calJour, String niss) {
        if (Boolean.valueOf(resultat)) {
            PLCTestBase.creationNouveauContratPLC(niss, origine, origineTiers, origineOrganisme, recuePar, numeroContrat, numeroVCS, natureConvention, typeInvestissement, datePriseEffet, decision, motifRefus, statut, messageInformatif);

            if (DECISION_ACCORD.equals(decision)) {
                EnrolementPPTestBase.checkSoldesCotisationPLCTrimestrePP(niss, periode, cotisation);
                ImpressionTestBase.checkImpression(niss, TestData.TYPE_NISS, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, calJour.getTime()), null, null, TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT, null);
            }
        } else {
            if (Boolean.valueOf(contratEnCours)) {
                PLCTestBase.creationNouveauContratPLCAvecMenuDisabled(niss);
            } else {
                PLCTestBase.creationNouveauContratPLCAvecErreur(niss, origine, origineTiers, origineOrganisme, recuePar, numeroContrat, numeroVCS, natureConvention, typeInvestissement, datePriseEffet, decision, motifRefus, messageInformatif);
            }
        }
    }
}