package be.ucm.cas.nasca.web.test.plc;

import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.EnumTypeTest;
import be.ucm.cas.nasca.web.test.support.TestBase;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

public class PLCTestBase extends TestBase {

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    public static void creationNouveauContratPLC(String niss, String origine, String origineTiers, String origineOrganisme,
                                                 String recuePar, String numeroContrat, String numeroVCS,
                                                 String natureConvention, String typeInvestissement, String datePriseEffet,
                                                 String decision, String motifRefus, String statut, String messageInformatif) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuNouveauContratPLC();

            fillFirstStep(niss, origine, origineTiers, origineOrganisme, recuePar, numeroContrat, numeroVCS, natureConvention, typeInvestissement, messageInformatif);

            fillSecondStep(datePriseEffet, decision, motifRefus);

            nascaPage.clickBtnTerminerWizard();

            gestionClientPage.clickLienPlc();
            checkStatut(niss, statut);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void creationNouveauContratPLCNonAffilie(String niss, String origine, String origineTiers, String origineOrganisme,
                                                    String recuePar, String numeroContrat, String numeroVCS,
                                                    String natureConvention, String typeInvestissement, String datePriseEffet,
                                                    String decision, String motifRefus, String statut, String messageInformatif,
                                                    String profil, String anneeRevenu, String revenu, String exoneration,
                                                    String reduction, String regime, String pensionSurvie) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuNouveauContratPLC();

            fillFirstStep(niss, origine, origineTiers, origineOrganisme, recuePar, numeroContrat, numeroVCS, natureConvention, typeInvestissement, messageInformatif);

            fillSecondStepNonAffilie(datePriseEffet, decision, motifRefus, profil, anneeRevenu, revenu, exoneration, reduction, regime, pensionSurvie);

            nascaPage.clickBtnTerminerWizard();

            if (nascaPage.isMessageErreur("stacktrace")) {
                logFailed(TestData.ERREUR_NASCA);
            } else {
                gestionClientPage.clickLienPlc();
                checkStatut(niss, statut);
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void creationNouveauContratPLCNonAffilieAvecErreur(String niss, String origine, String origineTiers, String origineOrganisme,
                                                              String recuePar, String numeroContrat, String numeroVCS,
                                                              String natureConvention, String typeInvestissement, String datePriseEffet,
                                                              String decision, String motifRefus, String messageInformatif,
                                                              String profil, String anneeRevenu, String revenu, String exoneration,
                                                              String reduction, String regime, String pensionSurvie) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuNouveauContratPLC();

            fillFirstStep(niss, origine, origineTiers, origineOrganisme, recuePar, numeroContrat, numeroVCS, natureConvention, typeInvestissement, messageInformatif);

            fillSecondStepNonAffilie(datePriseEffet, decision, motifRefus, profil, anneeRevenu, revenu, exoneration, reduction, regime, pensionSurvie);

            isMessageErreur(niss);
            nascaPage.clickBtnAnnulerEtOuiWizard();
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void creationNouveauContratPLCAvecMenuDisabled(String niss) {
        try {
            loadNissOrBce(niss);
            if (gestionClientPage.isMenuNouveauContratPLCDisabled()) {
                logSuccess("Création nouveau contrat PLC avec menu disable pour " + niss + " OK");
            } else {
                logFailed("Création nouveau contrat PLC avec menu disable pour " + niss + " NOK");
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    public static void traiterDemandeNouveauContratPLC(String niss, String datePriseEffet,
                                                       String decision, String motifRefus, String statut) {
        try {
            nascaPage.clickBtnSuivantWizard();

            fillSecondStep(datePriseEffet, decision, motifRefus);

            nascaPage.clickBtnTerminerWizard();

            loadNissOrBce(niss);

            gestionClientPage.clickLienPlc();
            checkStatut(niss, statut);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void creationNouveauContratPLCAvecErreur(String niss, String origine, String origineTiers, String origineOrganisme,
                                                    String recuePar, String numeroContrat, String numeroVCS,
                                                    String natureConvention, String typeInvestissement, String datePriseEffet,
                                                    String decision, String motifRefus, String messageInformatif) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuNouveauContratPLC();

            fillFirstStep(niss, origine, origineTiers, origineOrganisme, recuePar, numeroContrat, numeroVCS, natureConvention, typeInvestissement, messageInformatif);

            fillSecondStep(datePriseEffet, decision, motifRefus);

            isMessageErreur(niss);
            nascaPage.clickBtnAnnulerEtOuiWizard();
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void suspensionContratPLC(String niss, String dateSuspension, String motifSuspension, String statut) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuPLC();
            gestionClientPage.clickViewContratPLC(TestData.NOUVEAU);
            gestionClientPage.clickMenuSuspendreContratPLC();
            gestionClientPage.fillDateSuspensionOuClotureOuReveilContratPLC(dateSuspension);
            gestionClientPage.selectMotifSuspensionOuClotureOuReveilContratPLC(motifSuspension);
            gestionClientPage.checkTrimestreAEnrolerContratPLC();

            gestionClientPage.clickBtnEnregistrerSuspensionOuClotureOuReveilContratPLC();

            checkStatut(niss, statut);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void reveilContratPLC(String niss, String dateReveil, String motifReveil, String statut) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuPLC();
            gestionClientPage.clickViewContratPLC("Dormant");
            gestionClientPage.clickMenuReveillerContratPLC();
            gestionClientPage.fillDateSuspensionOuClotureOuReveilContratPLC(dateReveil);
            gestionClientPage.selectMotifSuspensionOuClotureOuReveilContratPLC(motifReveil);
            gestionClientPage.checkTrimestreAEnrolerContratPLC();
            gestionClientPage.clickBtnEnregistrerSuspensionOuClotureOuReveilContratPLC();

            checkStatut(niss, statut);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void clotureContratPLC(String niss, String dateCloture, String motifCloture, String statut) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuPLC();
            gestionClientPage.clickViewContratPLC(TestData.NOUVEAU);
            gestionClientPage.clickMenuCloturerContratPLC();
            gestionClientPage.fillDateSuspensionOuClotureOuReveilContratPLC(dateCloture);
            gestionClientPage.selectMotifSuspensionOuClotureOuReveilContratPLC(motifCloture);

            gestionClientPage.clickBtnEnregistrerSuspensionOuClotureOuReveilContratPLC();

            checkStatut(niss, statut);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void modifierDonneesContratPLC(String niss, String numeroContrat, String numeroVCS) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuPLC();
            gestionClientPage.clickViewContratPLC(TestData.NOUVEAU);
            gestionClientPage.clickMenuModifierDonneesContratPLC();
            gestionClientPage.fillNumeroModifContratPLC(numeroContrat);
            gestionClientPage.fillNumeroVCSModifContratPLC(numeroVCS);
            gestionClientPage.clickBtnEnregistrerModifierDonneesContratPLC();
            gestionClientPage.clickViewContratPLC(TestData.NOUVEAU);
            gestionClientPage.clickMenuModifierDonneesContratPLC();
            if (gestionClientPage.getNumeroContratPLC().equals(numeroContrat)) {
                logSuccess("Modifier numero contrat PLC pour " + niss + " OK");
            } else {
                logFailed("Modifier numero contrat PLC pour " + niss + " NOK");
            }

            if (gestionClientPage.getNumeroVCSContratPLC().equals(numeroVCS)) {
                logSuccess("Modifier numero VCS contrat PLC pour " + niss + " OK");
            } else {
                logFailed("Modifier numero VCS contrat PLC pour " + niss + " NOK");
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void ajouterObservationContratPLC(String niss, String reference, String commentaire, String type, String suiteA) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuPLC();
            gestionClientPage.clickViewContratPLC(TestData.NOUVEAU);
            gestionClientPage.clickMenuAjouterObservationContratPLC();
            gestionClientPage.fillReferenceObservationContratPLC(reference);
            gestionClientPage.selectTypeObservationContratPLC(type);
            gestionClientPage.fillCommentaireObservationContratPLC(commentaire);
            gestionClientPage.selectMediaObservationContratPLC(suiteA);

            gestionClientPage.clickBtnEnregistrerObservationContratPLC();

            if (gestionClientPage.isObservationPLCPresente(suiteA, type)) {
                logSuccess("Ajouter observation contrat PLC pour " + niss + " OK");
            } else {
                logFailed("Ajouter observation contrat PLC pour " + niss + " NOK");
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    private static void fillFirstStep(String niss, String origine, String origineTiers, String origineOrganisme, String recuePar, String numeroContrat, String numeroVCS, String natureConvention, String typeInvestissement, String messageInformatif) {
        checkMessageInformatif(niss, messageInformatif);

        gestionClientPage.selectOrigineDemandeContratPLC(origine);
        if (origineTiers != null && !StringUtils.isEmpty(origineTiers.trim())) {
            gestionClientPage.selectOrigineDemandeTiersContratPLC(origineTiers);
        }
        if (origineOrganisme != null && !StringUtils.isEmpty(origineOrganisme.trim())) {
            gestionClientPage.selectOrigineDemandeOrganismeContratPLC(origineOrganisme);
        }
        gestionClientPage.selectDemandeRecueParContratPLC(recuePar);
        gestionClientPage.fillNumeroContratPLC(numeroContrat);
        gestionClientPage.fillNumeroVCSContratPLC(numeroVCS);
        gestionClientPage.selectNatureConventionContratPLC(natureConvention);
        gestionClientPage.selectTypeInvestissementContratPLC(typeInvestissement);
        gestionClientPage.fillDateSouscriptionContratPLC(DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()));
        nascaPage.clickBtnSuivantWizard();
    }

    private static void fillSecondStep(String datePriseEffet, String decision, String motifRefus) {
        gestionClientPage.fillDatePriseEffetCalculContratPLC(datePriseEffet);
        gestionClientPage.selectDecisionContratPLC(decision);
        selectRaisonMotifRefus(motifRefus);
    }

    private static void fillSecondStepNonAffilie(String datePriseEffet, String decision, String motifRefus, String profil, String anneeRevenu, String revenu, String exoneration, String reduction, String regime, String pensionSurvie) {
        gestionClientPage.fillDatePriseEffetCalculContratPLC(datePriseEffet);
        gestionClientPage.selectProfilPrescripteurContratPLC(profil);
        gestionClientPage.selectRegimePrescripteurContratPLC(regime);
        if (Boolean.valueOf(exoneration)) {
            gestionClientPage.checkExonerationContratPLC();
        }
        if (Boolean.valueOf(reduction)) {
            gestionClientPage.checkReductionContratPLC();
        }
        if (Boolean.valueOf(pensionSurvie)) {
            gestionClientPage.checkPensionSurvieContratPLC();
        }
        gestionClientPage.fillAnneeRevenuSouscripteurContratPLC(anneeRevenu);
        gestionClientPage.fillRevenuSouscripteurContratPLC(revenu);
        gestionClientPage.selectDecisionContratPLC(decision);
        selectRaisonMotifRefus(motifRefus);
    }

    private static void checkMessageInformatif(String niss, String messageInformatif) {
        if (messageInformatif != null && !StringUtils.isEmpty(messageInformatif.trim())) {
            if (gestionClientPage.checkMessageInformatif(messageInformatif)) {
                logSuccess("Message informatif nouveau contrat PLC pour " + niss + " OK");
            } else {
                logFailed("Message informatif nouveau contrat PLC pour " + niss + " KO");
            }
        }
    }

    private static void checkStatut(String niss, String statut) {
        if (gestionClientPage.checkContratPLC(statut)) {
            logSuccess("Statut " + statut + " contrat PLC pour " + niss + " OK");
        } else {
            logFailed("Statut " + statut + " contrat PLC pour " + niss + " KO");
        }
    }

    private static void isMessageErreur(String niss) {
        if (gestionClientPage.isMessageErreurContratPLC()) {
            logSuccess("Création nouveau contrat PLC avec erreur pour " + niss + " OK");
        } else {
            logFailed("Création nouveau contrat PLC avec erreur pour " + niss + " KO");
        }
    }

    private static void selectRaisonMotifRefus(String motifRefus) {
        if (motifRefus != null && !StringUtils.isEmpty(motifRefus.trim())) {
            gestionClientPage.selectMotifRefusContratPLC(motifRefus);
        } else {
            gestionClientPage.clickBtnCalculerContratPLC();
        }
    }
}