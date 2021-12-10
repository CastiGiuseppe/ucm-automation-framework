package be.ucm.cas.nasca.web.test.cloturedossier;

import be.ucm.cas.nasca.web.test.support.*;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.Date;

public class ClotureDossierTestBase extends TestBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    protected String getNissForClotureDossier(String revision, String nature, String dateDebut) {
        if (revision == null || StringUtils.isEmpty(revision.trim())) {
            return DaoFunctionality.getNascaDao().getNissForClotureDossier(dateDebut, nature);
        } else {
            return null;
        }
    }

    // Cessation
    protected static void doCessationActiviteSansREM(String niss, String origine, String recuPar, String dateDemande, String dateCessation, String annulationTrimestrePension, String courrier, String justificationCjt) {
        try {
            doBaseCessationActivite(niss, origine, recuPar, dateDemande, dateCessation);

            checkJustificationConjoint(justificationCjt);

            doLastStepCessAndFinish(annulationTrimestrePension, courrier);

            checkPresenceEvenementProfil(niss, TestData.CESSATION_18);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    private static void doLastStepCessAndFinish(String annulationTrimestrePension, String courrier) {
        if (Boolean.valueOf(annulationTrimestrePension)) {
            gestionClientPage.checkAnnulationTrimestrePensionCessation();
        }
        if (!Boolean.valueOf(courrier)) {
            gestionClientPage.checkCourrierCessation();
        } else {
            nascaPage.clickBtnSuivantWizard();
        }
        nascaPage.clickBtnTerminerWizard();

        if (nascaPage.isMessageErreur(TestData.MESSAGE)) {
            logFailed(TestData.ERREUR_NASCA, "");
        }
    }

    protected static void doCessationActiviteSansREMAvecErreur(String niss, String origine, String recuPar, String dateDemande, String dateCessation) {
        try {
            doBaseCessationActivite(niss, origine, recuPar, dateDemande, dateCessation);

            nascaPage.clickBtnSuivantWizard();

            if (gestionClientPage.isErrorDate("DateCessationCessation")) {
                logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
            } else {
                logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    protected static void doCessationActiviteAvecREM(String niss, String origine, String recuPar, String dateDemande, String dateCessation, String annulationTrimestrePension, String courrier, String justificationCjt) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuClotureCessation();
            gestionClientPage.clickMenuCessationAvecREM();
            gestionClientPage.selectOrigineREMCessation(origine);
            gestionClientPage.selectRecuParREMCessation(recuPar);
            gestionClientPage.fillDateDemandeREMCessation(dateDemande);
            gestionClientPage.fillDateCessationREMCessation(dateCessation);

            remWizardPage.clickBtnREM();
            remWizardPage.clickLettreREMCessation();
            nascaPage.clickBtnSuivantWizard();
            remWizardPage.clickBtnmodifElementsManquants();
            remWizardPage.clickREMSelectElementManquant("Déclaration de cessation");
            remWizardPage.clickBtnSelectImpressionLocal();
            gestionClientPage.clickBtnEnregistrer();
            nascaPage.clickBtnSuivantWizard();
            remWizardPage.clickBtnRetourProcessusPrincipal();
            nascaPage.clickBtnTerminerWizard();

            nascaPage.clickBtnSuivantWizard();

            gestionClientPage.selectOrigineCessation(origine);
            gestionClientPage.selectRecuParCessation(recuPar);
            gestionClientPage.fillDateDemandeCessation(dateDemande);
            gestionClientPage.fillDateCessationCessation(dateCessation);

            checkJustificationConjoint(justificationCjt);

            doLastStepCessAndFinish(annulationTrimestrePension, courrier);

            if (nascaPage.isMessageErreur(TestData.MESSAGE)) {
                logFailed(TestData.ERREUR_NASCA, "");
            } else {
                checkPresenceEvenementProfil(niss, TestData.CESSATION_18);
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    protected static void doModificationCessation(String niss, String origine, String recuPar, String dateDemande, String dateCessation) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuClotureCessation();
            gestionClientPage.clickMenuCessationModifier();
            doFillModifAnnulCessation(origine, recuPar, dateDemande);
            gestionClientPage.fillDateCessationCessation(dateCessation);

            nascaPage.clickBtnTerminerWizard();

            if (nascaPage.isMessageErreur(TestData.MESSAGE)) {
                logFailed(TestData.ERREUR_NASCA, "");
            } else {
                checkPresenceEvenementProfil(niss, TestData.CESSATION_18);
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    protected static void doAnnulationCessation(String niss, String origine, String recuPar, String dateDemande) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuClotureCessation();
            gestionClientPage.clickMenuCessationAnnuler();
            doFillModifAnnulCessation(origine, recuPar, dateDemande);
            gestionClientPage.fillCommentaireAnnulationCessation("Test");
            nascaPage.clickBtnTerminerWizard();

            if (nascaPage.isMessageErreur(TestData.MESSAGE)) {
                logFailed(TestData.ERREUR_NASCA, "");
            } else {
                checkAbsenceEvenementProfil(niss, TestData.CESSATION_18);
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    protected static void doAjoutObservationCessation(String niss, String reference, String commentaire, String typeMedia) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuClotureCessation();
            gestionClientPage.clickMenuCessationAjouterObservation();
            gestionClientPage.fillReferenceObservationCessation(reference);
            gestionClientPage.fillCommentaireObservationCessation(commentaire);
            gestionClientPage.selectTypeMediaObservationCessation(typeMedia);
            gestionClientPage.clickBtnEnregistrerObservationCessation();

            gestionClientPage.clickMenuObservations();
            Assert.assertTrue(gestionClientPage.isObservationPresente(typeMedia, "Cessation", false));
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    // Succession
    public static void doIntroduireDateDecesSignaletique(String niss, String dateDeces) {
        try {
            loadNissOrBce(niss);
            signaletiquePage.clickMenuDetailsIdentite();
            signaletiquePage.clickOuvrirAccordeonFCaracteristiques();
            signaletiquePage.clickBtnModifierCaracteristiques();
            signaletiquePage.fillDateDeces(dateDeces);
            signaletiquePage.clickBtnEnregistrerCaracteristiques();
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    public static void doIntroduireDateDecesSuccession(String niss, String choix, String dateDeces) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuClotureDeces();
            gestionClientPage.clickMenuSuccessionIntroduireDeces();

            switch (choix) {
                case "DateSignaletique":
                    gestionClientPage.clickBtnRadioDateDecesSignaletiqueSuccession();
                    break;
                case "DateAutre":
                    gestionClientPage.clickBtnRadioDateDecesAutreSuccession();
                    gestionClientPage.fillDateDecesAutreSuccession(dateDeces);
                    break;
                default:
                    break;
            }
            nascaPage.clickBtnTerminerWizard();

            if (nascaPage.isMessageErreur(TestData.MESSAGE)) {
                logFailed(TestData.ERREUR_NASCA, "");
            } else {
                Assert.assertTrue(gestionClientPage.isDecede());
                logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
            }
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    protected static void doIntroduireDateDecesSuccessionAvecErreur(String niss, String dateDeces) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuClotureDeces();
            gestionClientPage.clickMenuSuccessionIntroduireDeces();
            gestionClientPage.clickBtnRadioDateDecesAutreSuccession();
            gestionClientPage.fillDateDecesAutreSuccession(dateDeces);
            nascaPage.clickBtnTerminerWizard();

            if (nascaPage.isMessageErreur(TestData.MESSAGE)) {
                logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
            } else {
                logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    protected static void doAjoutObservationSuccession(String niss, String commentaire, String typeMedia) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuClotureDeces();
            gestionClientPage.clickMenuSuccessionAjouterObservation();
            gestionClientPage.fillCommentaireObservationSuccession(commentaire);
            gestionClientPage.selectTypeMediaObservationSuccession(typeMedia);
            gestionClientPage.clickBtnEnregistrerObservationSuccession();

            gestionClientPage.clickMenuObservations();
            Assert.assertTrue(gestionClientPage.isObservationPresente(typeMedia, "Succession", false));
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    // Non-assujettissement
    protected static String doNonAssujettissementSansREM(String niss, String origine, String recuPar, String dateDemande, String justificationAffiliationFictive, String justificationCjtAidant, String courrier) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuClotureNonAssjettissement();
            String dateAffiliationASupprimer = gestionClientPage.getDateDebutActiviteProfil();

            doFillInfoNonAssu(origine, recuPar, dateDemande, dateAffiliationASupprimer);

            fillEndNonAssujettissement(niss, justificationAffiliationFictive, justificationCjtAidant, courrier);
            return dateAffiliationASupprimer;
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
        return null;
    }

    private static void fillEndNonAssujettissement(String niss, String justificationAffiliationFictive, String justificationCjtAidant, String courrier) {
        checkJustificationConjoint(justificationCjtAidant);

        checkJustificationAffiliationFictive(justificationAffiliationFictive);

        if (!Boolean.valueOf(courrier)) {
            gestionClientPage.checkCourrierNonAssujettissement();
        } else {
            nascaPage.clickBtnSuivantWizard();
        }

        nascaPage.clickBtnTerminerWizard();

        if (nascaPage.isMessageErreur(TestData.MESSAGE)) {
            logFailed(TestData.ERREUR_NASCA, "");
        } else {
            checkPresenceEvenementProfil(niss, "(99) Non assujettissement");
        }
    }

    private static void checkJustificationAffiliationFictive(String justificationAffiliationFictive) {
        switch (justificationAffiliationFictive) {
            case "PasAffiliationFictive":
                gestionClientPage.checkPasAffiliationFictiveNonAssujettissement();
                break;
            case "RadiationApresAffiliation":
                gestionClientPage.checkRadiationApresAffiliationNonAssujettissement();
                break;
            case "NonRenvoiFormulaireAffiliation":
                gestionClientPage.checkNonRenvoiFormaulationAffiliationNonAssujettissement();
                break;
            default:
                break;
        }
    }

    protected static String doNonAssujettissementAvecREM(String niss, String origine, String recuPar, String dateDemande, String justificationAffiliationFictive, String justificationCjtAidant, String courrier) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuClotureNonAssjettissement();
            String dateAffiliationASupprimer = gestionClientPage.getDateDebutActiviteProfil();
            gestionClientPage.clickMenuNonAssujettissementAvecREM();
            gestionClientPage.selectOrigineREMNonAssujettissement(origine);
            gestionClientPage.selectRecuParREMNonAssujettissement(recuPar);
            gestionClientPage.fillDateDemandeREMNonAssujettissement(dateDemande);
            gestionClientPage.fillDateAffiliationASupprimerREMNonAssujettissement(dateAffiliationASupprimer);

            remWizardPage.clickBtnREM();
            remWizardPage.clickLettreREMNonAssujettissement();
            nascaPage.clickBtnSuivantWizard();
            remWizardPage.clickBtnmodifElementsManquants();
            remWizardPage.clickREMSelectElementManquant("Déclaration sur l'honneur");
            remWizardPage.clickBtnSelectImpressionLocal();
            gestionClientPage.clickBtnEnregistrer();
            nascaPage.clickBtnSuivantWizard();
            remWizardPage.clickBtnRetourProcessusPrincipal();
            nascaPage.clickBtnTerminerWizard();

            nascaPage.clickBtnSuivantWizard();

            gestionClientPage.selectOrigineNonAssujettissement(origine);
            gestionClientPage.selectRecuParNonAssujettissement(recuPar);
            gestionClientPage.fillDateDemandeNonAssujettissement(dateDemande);
            gestionClientPage.fillDateAffiliationASupprimerNonAssujettissement(dateAffiliationASupprimer);

            fillEndNonAssujettissement(niss, justificationAffiliationFictive, justificationCjtAidant, courrier);
            return dateAffiliationASupprimer;
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
        return null;
    }

    protected static void doNonAssujettissementSansREMAvecErreur(String niss, String origine, String recuPar, String dateDemande, String justificationAffiliationFictive, String justificationCjtAidant, String courrier) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuClotureNonAssjettissement();
            String dateAffiliationASupprimer = DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE_WITH_SLASH, new Date());

            doFillInfoNonAssu(origine, recuPar, dateDemande, dateAffiliationASupprimer);

            checkJustificationConjoint(justificationCjtAidant);

            checkJustificationAffiliationFictive(justificationAffiliationFictive);

            if (!Boolean.valueOf(courrier)) {
                gestionClientPage.checkCourrierNonAssujettissement();

                nascaPage.clickBtnTerminerWizard();

                Assert.assertTrue(gestionClientPage.isErrorDate("DateAffiliationASupprimerNonAssujettissement"));
                logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
            }
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    protected static void doNonAssujettissementAvecREMAvecErreur(String niss, String origine, String recuPar, String dateDemande) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuClotureNonAssjettissement();
            String dateAffiliationASupprimer = DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE_WITH_SLASH, new Date());
            gestionClientPage.clickMenuNonAssujettissementAvecREM();
            gestionClientPage.selectOrigineREMNonAssujettissement(origine);
            gestionClientPage.selectRecuParREMNonAssujettissement(recuPar);
            gestionClientPage.fillDateDemandeREMNonAssujettissement(dateDemande);
            gestionClientPage.fillDateAffiliationASupprimerREMNonAssujettissement(dateAffiliationASupprimer);

            remWizardPage.clickBtnREM();

            Assert.assertTrue(gestionClientPage.isErrorDate("DateAffiliationASupprimerREMNonAssujettissement"));
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    protected static void doAjoutObservationNonAssujettissement(String niss, String reference, String commentaire, String typeMedia) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuClotureNonAssjettissement();
            gestionClientPage.clickMenuNonAssujettissementAjouterObservation();
            gestionClientPage.fillReferenceObservationNonAssujettissement(reference);
            gestionClientPage.fillCommentaireObservationNonAssujettissement(commentaire);
            gestionClientPage.selectTypeMediaObservationNonAssujettissement(typeMedia);
            gestionClientPage.clickBtnEnregistrerObservationNonAssujettissement();

            gestionClientPage.clickMenuObservations();
            Assert.assertTrue(gestionClientPage.isObservationPresente(typeMedia, "Non assujettissement", false));
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    // Mandat Gratuit
    protected static void doMandatGratuitAvecREM(String niss, String origine, String recuPar, String dateDemande, String dateMandat, String decision, String raisonRefus, String annulationTrimestrePension, String commentaire, String difficultePaiement, String attestationCompl, String declarationAffiliation, String courrier) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuClotureMandatGratuit();
            gestionClientPage.clickMenuMandatGratuitAvecREM();
            gestionClientPage.selectOrigineREMMandatGratuit(origine);
            gestionClientPage.selectRecuParREMMandatGratuit(recuPar);
            gestionClientPage.fillDateDemandeREMMandatGratuit(dateDemande);
            gestionClientPage.fillDateMandatREMMandatGratuit(dateMandat);

            remWizardPage.clickBtnREM();
            remWizardPage.clickLettreREMMandatGratuit();
            nascaPage.clickBtnSuivantWizard();
            remWizardPage.clickBtnmodifElementsManquants();
            remWizardPage.clickREMSelectElementManquant("Déclaration mandataire à titre gratuit");
            remWizardPage.clickBtnSelectImpressionLocal();
            gestionClientPage.clickBtnEnregistrer();
            nascaPage.clickBtnSuivantWizard();
            remWizardPage.clickBtnRetourProcessusPrincipal();
            nascaPage.clickBtnTerminerWizard();

            nascaPage.clickBtnSuivantWizard();

            gestionClientPage.selectOrigineMandatGratuit(origine);
            gestionClientPage.selectRecuParMandatGratuit(recuPar);
            gestionClientPage.fillDateDemandeMandatGratuit(dateDemande);
            gestionClientPage.fillDateCessationMandatGratuit(dateMandat);

            gestionClientPage.selectDecisionMandatGratuit(decision);

            if (raisonRefus != null && !StringUtils.isEmpty(raisonRefus.trim())) {
                gestionClientPage.selectRaisonRefusMandatGratuit(raisonRefus);

                if ("Autre".equals(raisonRefus)) {
                    gestionClientPage.fillCommentaireAutreRaisonRefusMandatGratuit(commentaire);
                }
            }

            doAllCheckMandatGratuit(annulationTrimestrePension, difficultePaiement, attestationCompl, declarationAffiliation, courrier);

            nascaPage.clickBtnTerminerWizard();

            if (nascaPage.isMessageErreur(TestData.MESSAGE)) {
                logFailed(TestData.ERREUR_NASCA, "");
            } else {
                checkDecision(niss, decision);
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    protected static void doMandatGratuitAvecCessation(String niss, String origine, String recuPar, String dateDemande, String dateMandat, String decision, String raisonRefus, String annulationTrimestrePension, String commentaire, String difficultePaiement, String attestationCompl, String declarationAffiliation, String courrier) {
        try {
            doBaseMandatGratuit(niss, origine, recuPar, dateDemande, dateMandat, decision, raisonRefus, commentaire, annulationTrimestrePension, difficultePaiement, attestationCompl, declarationAffiliation, courrier);

            nascaPage.clickBtnTerminerWizard();

            if (nascaPage.isMessageErreur(TestData.MESSAGE)) {
                logFailed(TestData.ERREUR_NASCA, "");
            } else {
                checkDecision(niss, decision);
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    private static void checkDecision(String niss, String decision) {
        if (TestData.ACCEPTE.equals(decision)) {
            checkPresenceEvenementProfil(niss, TestData.CESSATION_18);
        } else {
            checkAbsenceEvenementProfil(niss, TestData.CESSATION_18);
        }
    }

    protected static void checkMenuInactifMandatGratuit(String niss) {
        loadNissOrBce(niss);
        gestionClientPage.clickMenuClotureMandatGratuit();
        try {
            gestionClientPage.clickMenuMandatGratuitAvecCessation();
            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName(), "Menu action actif");
        } catch (NoSuchElementException e) {
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "Menu action inactif");
        }
    }

    protected static void doMandatGratuitAvecCessationAvecErreur(String niss, String origine, String recuPar, String dateDemande, String dateMandat, String decision, String raisonRefus, String annulationTrimestrePension, String commentaire, String difficultePaiement, String attestationCompl, String declarationAffiliation, String courrier) {
        try {
            doBaseMandatGratuit(niss, origine, recuPar, dateDemande, dateMandat, decision, raisonRefus, commentaire, annulationTrimestrePension, difficultePaiement, attestationCompl, declarationAffiliation, courrier);

            nascaPage.clickBtnTerminerWizard();

            Assert.assertTrue(gestionClientPage.isErrorDate("DateCessationMandatGratuit"));
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    protected static void doAjoutObservationMandatGratuit(String niss, String reference, String commentaire, String typeMedia) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuClotureMandatGratuit();
            gestionClientPage.clickMenuMandatGratuitAjouterObservation();
            gestionClientPage.fillReferenceObservationMandatGratuit(reference);
            gestionClientPage.fillCommentaireObservationMandatGratuit(commentaire);
            gestionClientPage.selectTypeMediaObservationMandatGratuit(typeMedia);
            gestionClientPage.clickBtnEnregistrerObservationMandatGratuit();

            gestionClientPage.clickMenuObservations();
            Assert.assertTrue(gestionClientPage.isObservationPresente(typeMedia, "Mandat gratuit", false));
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    // Assimilation Maladie
    protected static void doAssimilationMaladieSansREM(String niss, String origine, String recuPar, String dateDemande, String dateCessation, String clotureDossier, String destinataire, String texte1, String texte2, String courrier) {
        try {
            doBaseAssimMaladie(niss, origine, recuPar, dateDemande, dateCessation, clotureDossier, destinataire, texte1, texte2, courrier);

            nascaPage.clickBtnTerminerWizard();

            if (nascaPage.isMessageErreur(TestData.MESSAGE)) {
                logFailed(TestData.ERREUR_NASCA, "");
            } else {
                checkStatutCloture(TestData.EN_TRAITEMENT);
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    protected static void doAssimilationMaladieSansREMAvecErreur(String niss, String origine, String recuPar, String dateDemande, String dateCessation, String clotureDossier, String destinataire, String texte1, String texte2, String courrier) {
        try {
            doBaseAssimMaladie(niss, origine, recuPar, dateDemande, dateCessation, clotureDossier, destinataire, texte1, texte2, courrier);

            nascaPage.clickBtnTerminerWizard();

            Assert.assertTrue(gestionClientPage.isErrorDate("DateDemandeAssMaladie"));
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    protected static void doAssimilationMaladieAvecREM(String niss, String origine, String recuPar, String dateDemande, String dateCessation, String clotureDossier, String destinataire, String texte1, String texte2, String dateIncapacite, String affilieSeulEnSociete, String affiliePasRadieBCE, String courrier) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuClotureAssimilationMaladie();
            gestionClientPage.clickMenuAssimMaladieAvecRem();
            gestionClientPage.selectOrigineREMAssMaladie(origine);
            gestionClientPage.selectRecuParREMAssMaladie(recuPar);
            gestionClientPage.fillDateDemandeREMAssMaladie(dateDemande);
            gestionClientPage.fillDateCessationREMAssMaladie(dateCessation);
            gestionClientPage.fillDateIncapaciteTravailREMAssMaladie(dateIncapacite);

            if (Boolean.valueOf(affilieSeulEnSociete)) {
                gestionClientPage.checkAffilieSeulEnSocieteREMAssMaladie();
            }
            if (Boolean.valueOf(affiliePasRadieBCE)) {
                gestionClientPage.checkPasRadierBceREMAssMaladie();
            }

            remWizardPage.clickBtnREM();
            remWizardPage.clickLettreREMAssMaladie();
            nascaPage.clickBtnSuivantWizard();
            remWizardPage.clickBtnmodifElementsManquants();
            remWizardPage.clickREMSelectElementManquant("Formulaire demande d?'assimilation maladie");
            remWizardPage.clickBtnSelectImpressionLocal();
            gestionClientPage.clickBtnEnregistrer();
            nascaPage.clickBtnSuivantWizard();
            remWizardPage.clickBtnRetourProcessusPrincipal();
            nascaPage.clickBtnTerminerWizard();

            nascaPage.clickBtnSuivantWizard();

            doFillInfoAssimMaladie(origine, recuPar, dateDemande, dateCessation, clotureDossier, destinataire, texte1, texte2, courrier);

            nascaPage.clickBtnTerminerWizard();

            if (nascaPage.isMessageErreur(TestData.MESSAGE)) {
                logFailed(TestData.ERREUR_NASCA, "");
            } else {
                checkStatutCloture(TestData.EN_TRAITEMENT);
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    protected static void doEnvoiInfosComplementairesAssimilationMaladie(String niss, String origine, String recuPar, String dateDemande, String destinataire, String texte1, String texte2) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuClotureAssimilationMaladie();
            gestionClientPage.clickMenuAssimMaladieEnvoiInfosComplInasti();
            gestionClientPage.selectOrigineInfosAssMaladie(origine);
            gestionClientPage.selectRecuParInfosAssMaladie(recuPar);
            gestionClientPage.fillDateDemandeAssMaladie(dateDemande);

            gestionClientPage.selectDestinaireInfosAssMaladie(destinataire);
            gestionClientPage.fillTexteLibre1InfosAssMaladie(texte1);
            gestionClientPage.fillTexteLibre2InfosAssMaladie(texte2);

            nascaPage.clickBtnTerminerWizard();

            if (nascaPage.isMessageErreur(TestData.MESSAGE)) {
                logFailed(TestData.ERREUR_NASCA, "");
            } else {
                logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    protected static void doAnnulationAssimilationMaladie(String niss) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuClotureAssimilationMaladie();
            gestionClientPage.clickMenuAssimMaladieAnnulerDemandeEnTraitement();
            nascaPage.confirmerAlert();
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    protected static void doReceptionDecisionAssimilationMaladie(String niss, String dateDecision, String decision, String raisonPasEnOrdre, String raisonPasCessation, String raisonIncNonReconnue,
                                                                 String raisonPoursuiteActivite, String raisonExistanceRevenus, String raisonGerantSocActive, String raisonActSalariee,
                                                                 String raisonIncPasAssezLongue, String raisonAutreCouvSociale, String raisonPasSuiteDemRens, String raisonAutre, String dateDebut, String dateFin, String dateReprise, String natureCotisante, String courrier, String clotureDossier) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuClotureAssimilationMaladie();
            gestionClientPage.clickMenuAssimMaladieReceptionDecisionInasti();

            gestionClientPage.fillDateDecisionAssMaladie(dateDecision);
            gestionClientPage.selectDecisionAssMaladie(decision);

            if (!Boolean.valueOf(courrier)) {
                gestionClientPage.checkCourrierDecisionAssMaladie();
            }

            if (TestData.ACCEPTE.equals(decision)) {
                gestionClientPage.selectNatureCotisanteAssMaladie(natureCotisante);
                gestionClientPage.fillDateDebutAssMaladie(dateDebut);
                if (dateFin != null) {
                    gestionClientPage.fillDateFinAssMaladie(dateFin);
                }
                if (dateReprise != null) {
                    gestionClientPage.fillDateRepriseAssMaladie(dateReprise);
                }

                nascaPage.clickBtnTerminerWizard();

                gestionClientPage.clickMenuProfil();
                if (Boolean.valueOf(clotureDossier)) {
                    Assert.assertTrue(gestionClientPage.isEvenementPresentProfil(DateUtils.doFormatToString(TestData.DATE_FORMAT_SIMPLE_WITH_SLASH, TestData.DATE_FORMAT_YEAR, dateFin), TestData.CESSATION_18));
                    logSuccess("Cloture Dossier - Assimilation Maladie (accepte) (Présence 18 dans Profil) pour " + niss + " OK");
                } else {
                    Assert.assertTrue(gestionClientPage.isEvenementPresentProfil(DateUtils.doFormatToString(TestData.DATE_FORMAT_SIMPLE_WITH_SLASH, TestData.DATE_FORMAT_YEAR, dateDebut), "U0"));
                    logSuccess("Cloture Dossier - Assimilation Maladie (accepte) (Présence U0 dans Profil) pour " + niss + " OK");
                }
            } else {
                doCheckRaisonRefusPasOrdreCotisationAssMaladie(raisonPasEnOrdre);
                doCheckRaisonRefusPasCessationAssMaladie(raisonPasCessation);
                doCheckRaisonRefusIncapaciteNonReconnueAssMaladie(raisonIncNonReconnue);
                doCheckRaisonRefusPoursuiteActiviteAssMaladie(raisonPoursuiteActivite);
                doCheckRaisonRefusExistanceRevenusAssMaladie(raisonExistanceRevenus);
                doCheckRaisonRefusGerantSocieteAssMaladie(raisonGerantSocActive);
                doCheckRaisonRefusIncapacitePasAssezLongueAssMaladie(raisonIncPasAssezLongue);
                doCheckRaisonRefusActSalarieeAssMaladie(raisonActSalariee);
                doCheckRaisonRefusAutreCouvSocialeAssMaladie(raisonAutreCouvSociale);
                doCheckRaisonRefusPasSuiteAssMaladie(raisonPasSuiteDemRens);
                doCheckRaisonRefusAutreAssMaladie(raisonAutre);

                nascaPage.clickBtnTerminerWizard();
                Assert.assertTrue(!gestionClientPage.isEvenementPresentProfil(DateUtils.doFormatToString(TestData.DATE_FORMAT_SIMPLE_WITH_SLASH, TestData.DATE_FORMAT_YEAR, dateDebut), "U0"));
                logSuccess("Cloture Dossier - Assimilation Maladie (refuse) (Absence U0 dans Profil) pour " + niss + " OK");
            }
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            if (TestData.ACCEPTE.equals(decision)) {
                if (Boolean.valueOf(clotureDossier)) {
                    logFailed("Cloture Dossier - Assimilation Maladie (accepte) (Absence 18 dans Profil) pour " + niss + " KO");
                } else {
                    logFailed("Cloture Dossier - Assimilation Maladie (accepte) (Absence U0 dans Profil) pour " + niss + " KO");
                }
            } else {
                logFailed("Cloture Dossier - Assimilation Maladie (refuse) (Présence U0 dans Profil) pour " + niss + " KO");
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    private static void doCheckRaisonRefusPasOrdreCotisationAssMaladie(String raisonPasEnOrdre) {
        if (Boolean.valueOf(raisonPasEnOrdre)) {
            gestionClientPage.checkRaisonRefusPasOrdreCotisationAssMaladie();
        }
    }

    private static void doCheckRaisonRefusPasCessationAssMaladie(String raisonPasCessation) {
        if (Boolean.valueOf(raisonPasCessation)) {
            gestionClientPage.checkRaisonRefusPasCessationAssMaladie();
        }
    }

    private static void doCheckRaisonRefusIncapaciteNonReconnueAssMaladie(String raisonIncNonReconnue) {
        if (Boolean.valueOf(raisonIncNonReconnue)) {
            gestionClientPage.checkRaisonRefusIncapaciteNonReconnueAssMaladie();
        }
    }

    private static void doCheckRaisonRefusPoursuiteActiviteAssMaladie(String raisonPoursuiteActivite) {
        if (Boolean.valueOf(raisonPoursuiteActivite)) {
            gestionClientPage.checkRaisonRefusPoursuiteActiviteAssMaladie();
        }
    }

    private static void doCheckRaisonRefusExistanceRevenusAssMaladie(String raisonExistanceRevenus) {
        if (Boolean.valueOf(raisonExistanceRevenus)) {
            gestionClientPage.checkRaisonRefusExistanceRevenusAssMaladie();
        }
    }

    private static void doCheckRaisonRefusGerantSocieteAssMaladie(String raisonGerantSocActive) {
        if (Boolean.valueOf(raisonGerantSocActive)) {
            gestionClientPage.checkRaisonRefusGerantSocieteAssMaladie();
        }
    }

    private static void doCheckRaisonRefusIncapacitePasAssezLongueAssMaladie(String raisonIncPasAssezLongue) {
        if (Boolean.valueOf(raisonIncPasAssezLongue)) {
            gestionClientPage.checkRaisonRefusIncapacitePasAssezLongueAssMaladie();
        }
    }

    private static void doCheckRaisonRefusActSalarieeAssMaladie(String raisonActSalariee) {
        if (Boolean.valueOf(raisonActSalariee)) {
            gestionClientPage.checkRaisonRefusActSalarieeAssMaladie();
        }
    }

    private static void doCheckRaisonRefusAutreCouvSocialeAssMaladie(String raisonAutreCouvSociale) {
        if (Boolean.valueOf(raisonAutreCouvSociale)) {
            gestionClientPage.checkRaisonRefusAutreCouvSocialeAssMaladie();
        }
    }

    private static void doCheckRaisonRefusPasSuiteAssMaladie(String raisonPasSuiteDemRens) {
        if (Boolean.valueOf(raisonPasSuiteDemRens)) {
            gestionClientPage.checkRaisonRefusPasSuiteAssMaladie();
        }
    }

    private static void doCheckRaisonRefusAutreAssMaladie(String raisonAutre) {
        if (Boolean.valueOf(raisonAutre)) {
            gestionClientPage.checkRaisonRefusAutreAssMaladie();
        }
    }

    protected static void doAjoutObservationAssimilationMaladie(String niss, String reference, String commentaire, String typeMedia) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuClotureAssimilationMaladie();
            gestionClientPage.clickMenuAssMaladieAjouterObservation();
            gestionClientPage.fillReferenceObservationAssMaladie(reference);
            gestionClientPage.fillCommentaireObservationAssMaladie(commentaire);
            gestionClientPage.selectTypeMediaObservationAssMaladie(typeMedia);
            gestionClientPage.clickBtnEnregistrerObservationAssMaladie();

            gestionClientPage.clickMenuObservations();
            Assert.assertTrue(gestionClientPage.isObservationPresente(typeMedia, "Assimilation maladie", false));
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    // Assurance Continuée
    protected static void doAssuranceContinueeAvecCessation(String niss, String origine, String recuPar, String dateDemande, String dateCessation, String destinataire, String texte1, String texte2, String courrier) {
        try {
            doBaseAssContinuee(niss, origine, recuPar, dateDemande, dateCessation, destinataire, texte1, texte2, courrier);
            logInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), "avant terminer");
            nascaPage.clickBtnTerminerWizard();

            if (nascaPage.isMessageErreur(TestData.MESSAGE)) {
                logFailed(TestData.ERREUR_NASCA, "");
            } else {
                checkStatutCloture(TestData.EN_TRAITEMENT);
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    protected static void doAssuranceContinueeAvecCessationAvecErreur(String niss, String origine, String recuPar, String dateDemande, String dateCessation, String destinataire, String texte1, String texte2, String courrier) {
        try {
            doBaseAssContinuee(niss, origine, recuPar, dateDemande, dateCessation, destinataire, texte1, texte2, courrier);

            nascaPage.clickBtnTerminerWizard();

            Assert.assertTrue(gestionClientPage.isErrorDate("DateDemandeAssContinuee"));
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    protected static void doAssuranceContinueeAvecREM(String niss, String origine, String recuPar, String dateDemande, String dateCessation, String demandeTardive, String destinataire, String texte1, String texte2, String courrier) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuClotureAssuranceContinuee();
            gestionClientPage.clickMenuAssContinueeAvecRem();
            gestionClientPage.selectOrigineREMAssContinuee(origine);
            gestionClientPage.selectRecuParREMAssContinuee(recuPar);
            gestionClientPage.fillDateDemandeREMAssContinuee(dateDemande);
            gestionClientPage.fillDateCessationREMAssContinuee(dateCessation);
            if (Boolean.valueOf(demandeTardive)) {
                gestionClientPage.checkDemandeTardiveAssContinuee();
                gestionClientPage.fillCommentaireDemandeTardiveAssContinuee("Test");
            }

            remWizardPage.clickBtnREM();
            remWizardPage.clickLettreREMAssContinuee();
            logInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), "step 1");
            nascaPage.clickBtnSuivantWizard();
            remWizardPage.clickBtnmodifElementsManquants();
            remWizardPage.clickREMSelectElementManquant("Déclaration de cessation");
            remWizardPage.clickBtnSelectImpressionLocal();
            gestionClientPage.clickBtnEnregistrer();
            nascaPage.clickBtnSuivantWizard();
            remWizardPage.clickBtnRetourProcessusPrincipal();
            nascaPage.clickBtnTerminerWizard();

            nascaPage.clickBtnSuivantWizard();

            gestionClientPage.selectOrigineAssContinuee(origine);
            gestionClientPage.selectRecuParAssContinuee(recuPar);
            gestionClientPage.fillDateDemandeAssContinuee(dateDemande);
            gestionClientPage.fillDateReelleCessationAssContinuee(dateCessation);

            gestionClientPage.selectDestinaireAssContinuee(destinataire);
            gestionClientPage.fillTexteLibre1AssContinuee(texte1);
            gestionClientPage.fillTexteLibre2AssContinuee(texte2);

            if (!Boolean.valueOf(courrier)) {
                gestionClientPage.checkCourrierDemandeAssCont();
            }
            logInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), "avant terminer");
            nascaPage.clickBtnTerminerWizard();

            if (nascaPage.isMessageErreur(TestData.MESSAGE)) {
                logFailed(TestData.ERREUR_NASCA, "");
            } else {
                checkStatutCloture(TestData.EN_TRAITEMENT);
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    protected static void doEnvoiInfosComplementairesAssuranceContinuee(String niss, String origine, String recuPar, String dateDemande, String destinataire, String texte1, String texte2) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuClotureAssuranceContinuee();
            gestionClientPage.clickMenuAssContinueeEnvoiInfosComplInasti();
            gestionClientPage.selectOrigineInfosAssContinuee(origine);
            gestionClientPage.selectRecuParInfosAssContinuee(recuPar);
            gestionClientPage.fillDateDemandeAssContinuee(dateDemande);

            gestionClientPage.selectDestinaireInfosAssContinuee(destinataire);
            gestionClientPage.fillTexteLibre1InfosAssContinuee(texte1);
            gestionClientPage.fillTexteLibre2InfosAssContinuee(texte2);

            nascaPage.clickBtnTerminerWizard();

            if (nascaPage.isMessageErreur(TestData.MESSAGE)) {
                logFailed(TestData.ERREUR_NASCA, "");
            } else {
                logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    protected static void doAnnulationAssuranceContinuee(String niss) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuClotureAssuranceContinuee();
            gestionClientPage.clickMenuAssContinueeAnnulerDemandeEnTraitement();
            nascaPage.confirmerAlert();
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    protected static void doReceptionDecisionAssuranceContinuee(String niss, String dateDecision, String decision, String raisonRefus, String dateDebut, String dateFin, String dateCessation, String type, String courrier) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuClotureAssuranceContinuee();
            gestionClientPage.clickMenuAssContinueeReceptionDecisionInasti();

            gestionClientPage.fillDateDecisionAssContinuee(dateDecision);
            gestionClientPage.selectDecisionAssContinuee(decision);

            if (!Boolean.valueOf(courrier)) {
                gestionClientPage.checkCourrierDecisionAssCont();
            }
            if (TestData.ACCEPTE.equals(decision)) {
                gestionClientPage.fillDateDebutAssContinuee(dateDebut);
                gestionClientPage.fillDateFinAssContinuee(dateFin);
                gestionClientPage.selectTypeAssContinuee(type);
                nascaPage.clickBtnTerminerWizard();

                gestionClientPage.clickMenuProfil();
                Assert.assertTrue(gestionClientPage.isEvenementPresentProfil(DateUtils.doFormatToString(TestData.DATE_FORMAT_SIMPLE_WITH_SLASH, TestData.DATE_FORMAT_YEAR, dateDebut), "S2"));
                logSuccess("Cloture Dossier - Assurance Continuee (accepte) (Présence S2 dans Profil) pour " + niss + " OK");
            } else {
                gestionClientPage.selectRaisonRefusAssContinuee(raisonRefus);
                gestionClientPage.fillDateCessationAssContinuee(dateCessation);
                nascaPage.clickBtnTerminerWizard();

                if (nascaPage.isMessageErreur(TestData.MESSAGE)) {
                    logFailed(TestData.ERREUR_NASCA, "");
                } else {
                    checkPresenceEvenementProfil(niss, TestData.CESSATION_18);
                }
            }
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logFailed("Cloture Dossier - Assurance Continuee (accepte) (Absence S2 dans Profil) pour " + niss + " KO");
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    protected static void doAjoutObservationAssuranceContinuee(String niss, String reference, String commentaire, String typeMedia) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuClotureAssuranceContinuee();
            gestionClientPage.clickMenuAssContinueeAjouterObservation();
            gestionClientPage.fillReferenceObservationAssContinuee(reference);
            gestionClientPage.fillCommentaireObservationAssContinuee(commentaire);
            gestionClientPage.selectTypeMediaObservationAssContinuee(typeMedia);
            gestionClientPage.clickBtnEnregistrerObservationAssContinuee();

            gestionClientPage.clickMenuObservations();
            Assert.assertTrue(gestionClientPage.isObservationPresente(typeMedia, "Assurance continuée", false));
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    private static void doGoToMandatGratuit(String niss, String origine, String recuPar, String dateDemande, String dateMandat, String decision) {
        loadNissOrBce(niss);
        gestionClientPage.clickMenuClotureMandatGratuit();
        gestionClientPage.clickMenuMandatGratuitAvecCessation();

        gestionClientPage.selectOrigineMandatGratuit(origine);
        gestionClientPage.selectRecuParMandatGratuit(recuPar);
        gestionClientPage.fillDateDemandeMandatGratuit(dateDemande);
        gestionClientPage.fillDateCessationMandatGratuit(dateMandat);

        gestionClientPage.selectDecisionMandatGratuit(decision);
    }

    private static void doCheckMandatGratuit(String raisonRefus, String commentaire, String annulationTrimestrePension, String difficultePaiement, String attestationCompl, String declarationAffiliation, String courrier) {
        if (raisonRefus != null && !StringUtils.isEmpty(raisonRefus.trim())) {
            gestionClientPage.selectRaisonRefusMandatGratuit(raisonRefus);

            if ("Autre".equals(raisonRefus)) {
                gestionClientPage.fillCommentaireAutreRaisonRefusMandatGratuit(commentaire);
            }
        }

        doAllCheckMandatGratuit(annulationTrimestrePension, difficultePaiement, attestationCompl, declarationAffiliation, courrier);
    }

    private static void doBaseMandatGratuit(String niss, String origine, String recuPar, String dateDemande, String dateMandat, String decision, String raisonRefus, String commentaire,
                                            String annulationTrimestrePension, String difficultePaiement, String attestationCompl, String declarationAffiliation, String courrier) {
        doGoToMandatGratuit(niss, origine, recuPar, dateDemande, dateMandat, decision);
        doCheckMandatGratuit(raisonRefus, commentaire, annulationTrimestrePension, difficultePaiement, attestationCompl, declarationAffiliation, courrier);
    }

    private static void docheckAnnulationTrimestrePensionMandatGratuit(String annulationTrimestrePension) {
        if (Boolean.valueOf(annulationTrimestrePension)) {
            gestionClientPage.checkAnnulationTrimestrePensionMandatGratuit();
        }
    }

    private static void docheckDifficultePaiementMandatGratuit(String difficultePaiement) {
        if (Boolean.valueOf(difficultePaiement)) {
            gestionClientPage.checkDifficultePaiementMandatGratuit();
        }
    }

    private static void docheckAttestationComplementaireMandatGratuit(String attestationCompl) {
        if (Boolean.valueOf(attestationCompl)) {
            gestionClientPage.checkAttestationComplementaireMandatGratuit();
        }
    }

    private static void docheckDeclarationAffiliationMandatGratuit(String declarationAffiliation) {
        if (Boolean.valueOf(declarationAffiliation)) {
            gestionClientPage.checkDeclarationAffiliationMandatGratuit();
        }
    }

    private static void docheckCourrierMandatGratuit(String courrier) {
        if (!Boolean.valueOf(courrier)) {
            gestionClientPage.checkCourrierMandatGratuit();
        }
    }

    private static void doAllCheckMandatGratuit(String annulationTrimestrePension, String difficultePaiement, String attestationCompl, String declarationAffiliation, String courrier) {
        docheckAnnulationTrimestrePensionMandatGratuit(annulationTrimestrePension);
        docheckDifficultePaiementMandatGratuit(difficultePaiement);
        docheckAttestationComplementaireMandatGratuit(attestationCompl);
        docheckDeclarationAffiliationMandatGratuit(declarationAffiliation);
        docheckCourrierMandatGratuit(courrier);
    }

    private static void doFillInfoNonAssu(String origine, String recuPar, String dateDemande, String dateAffiliationASupprimer) {
        gestionClientPage.clickMenuNonAssujettissementSansREM();
        gestionClientPage.selectOrigineNonAssujettissement(origine);
        gestionClientPage.selectRecuParNonAssujettissement(recuPar);
        gestionClientPage.fillDateDemandeNonAssujettissement(dateDemande);
        gestionClientPage.fillDateAffiliationASupprimerNonAssujettissement(dateAffiliationASupprimer);
    }

    private static void doBaseAssimMaladie(String niss, String origine, String recuPar, String dateDemande, String dateCessation, String clotureDossier, String destinataire, String texte1, String texte2, String courrier) {
        doGoToAssimMaladie(niss);
        doFillInfoAssimMaladie(origine, recuPar, dateDemande, dateCessation, clotureDossier, destinataire, texte1, texte2, courrier);
    }

    private static void doGoToAssimMaladie(String niss) {
        loadNissOrBce(niss);
        gestionClientPage.clickMenuClotureAssimilationMaladie();
        gestionClientPage.clickMenuAssimMaladieSansRem();
    }

    private static void doFillInfoAssimMaladie(String origine, String recuPar, String dateDemande, String dateCessation, String clotureDossier, String destinataire, String texte1, String texte2, String courrier) {
        gestionClientPage.selectOrigineAssMaladie(origine);
        gestionClientPage.selectRecuParAssMaladie(recuPar);
        gestionClientPage.fillDateDemandeAssMaladie(dateDemande);
        gestionClientPage.fillDateReelleCessationAssMaladie(dateCessation);
        if (Boolean.valueOf(clotureDossier)) {
            gestionClientPage.checkClotureDossierAssMaladie();
        }

        gestionClientPage.selectDestinaireAssMaladie(destinataire);
        gestionClientPage.fillTexteLibre1AssMaladie(texte1);
        gestionClientPage.fillTexteLibre2AssMaladie(texte2);

        doCheckCourrierAssMaladie(courrier);
    }

    private static void doBaseAssContinuee(String niss, String origine, String recuPar, String dateDemande, String dateCessation, String destinataire, String texte1, String texte2, String courrier) {
        doGoToAssContinuee(niss);
        doFillInfoAssContinuee(origine, recuPar, dateDemande, dateCessation, destinataire, texte1, texte2, courrier);
    }

    private static void doCheckCourrierAssMaladie(String courrier) {
        if (!Boolean.valueOf(courrier)) {
            gestionClientPage.checkCourrierDemandeAssMaladie();
        }
    }

    private static void doGoToAssContinuee(String niss) {
        loadNissOrBce(niss);
        gestionClientPage.clickMenuClotureAssuranceContinuee();
        gestionClientPage.clickMenuAssContinueeAvecCessation();
    }

    private static void doFillInfoAssContinuee(String origine, String recuPar, String dateDemande, String dateCessation, String destinataire, String texte1, String texte2, String courrier) {
        gestionClientPage.selectOrigineAssContinuee(origine);
        gestionClientPage.selectRecuParAssContinuee(recuPar);
        gestionClientPage.fillDateDemandeAssContinuee(dateDemande);
        gestionClientPage.fillDateReelleCessationAssContinuee(dateCessation);

        gestionClientPage.selectDestinaireAssContinuee(destinataire);
        gestionClientPage.fillTexteLibre1AssContinuee(texte1);
        gestionClientPage.fillTexteLibre2AssContinuee(texte2);

        if (!Boolean.valueOf(courrier)) {
            gestionClientPage.checkCourrierDemandeAssCont();
        }
    }

    private static void doBaseCessationActivite(String niss, String origine, String recuPar, String dateDemande, String dateCessation) {
        doGoToCessationActivite(niss);
        doFillInfoCessationActivite(origine, recuPar, dateDemande, dateCessation);
    }

    private static void doGoToCessationActivite(String niss) {
        loadNissOrBce(niss);
        gestionClientPage.clickMenuClotureCessation();
        gestionClientPage.clickMenuCessationSansREM();
    }

    private static void doFillInfoCessationActivite(String origine, String recuPar, String dateDemande, String dateCessation) {
        gestionClientPage.selectOrigineCessation(origine);
        gestionClientPage.selectRecuParCessation(recuPar);
        gestionClientPage.fillDateDemandeCessation(dateDemande);
        gestionClientPage.fillDateCessationCessation(dateCessation);
    }

    private static void doFillModifAnnulCessation(String origine, String recuPar, String dateDemande) {
        gestionClientPage.selectOrigineModificationCessation(origine);
        gestionClientPage.selectRecuParModificationCessation(recuPar);
        gestionClientPage.fillDateDemandeCessation(dateDemande);
    }

    private static void checkPresenceEvenementProfil(String niss, String evenement) {
        try {
            Assert.assertTrue(gestionClientPage.isEvenementPresentProfil(evenement));
            logSuccess("Cloture Dossier - Présence " + evenement + " dans Profil pour " + niss);
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logFailed("Cloture Dossier - Absence " + evenement + " dans Profil pour " + niss);
        }
    }

    private static void checkAbsenceEvenementProfil(String niss, String evenement) {
        try {
            Assert.assertTrue(!gestionClientPage.isEvenementPresentProfil(evenement));
            logSuccess("Cloture Dossier - Absence " + evenement + " dans Profil pour " + niss);
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logFailed("Cloture Dossier - Présence " + evenement + " dans Profil pour " + niss);
        }
    }

    private static void checkJustificationConjoint(String justificationCjt) {
        if (!StringUtils.isEmpty(justificationCjt)) {
            switch (justificationCjt) {
                case "Déclaration sur l’honneur":
                    gestionClientPage.checkDeclarationHonneurCessation();
                    break;
                case "Dirigeant d’entreprise":
                    gestionClientPage.checkDirigeantEntrepriseCessation();
                    break;
                case "Droits sociaux":
                    gestionClientPage.checkDroitsSociauxCessation();
                    break;
                case "Cessation conjointe":
                    gestionClientPage.checkCessationConjointeCessation();
                    break;
                default:
                    break;
            }
        }
    }

    private static void checkStatutCloture(String statut) {
        try {
            Assert.assertTrue(TableUtils.isElementPresent("demandeTableId", statut));
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }
}