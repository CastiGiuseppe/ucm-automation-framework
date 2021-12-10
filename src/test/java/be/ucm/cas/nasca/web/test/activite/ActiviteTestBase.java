package be.ucm.cas.nasca.web.test.activite;

import be.ucm.cas.nasca.web.test.support.*;

public class ActiviteTestBase extends TestBase {

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    static void fillActiviteEntreprisePP(String niss, String typeActivite,
                                         String profession, String dateDebut, String dateFin,
                                         boolean suppression, String assujetti, String princExercee,
                                         String donneesValides, String commentaire) {
        try {
            loadNissOrBce(niss);
            signaletiquePage.clickMenuActivites();
            signaletiquePage.clickVoirToutesActivites();

            checkSuppressionActivite(niss, suppression);

            signaletiquePage.clickAjouterActivite();
            signaletiquePage.selectTypeActivite(typeActivite);
            signaletiquePage.checkAssujetti(assujetti);
            signaletiquePage.checkDonneesValides(donneesValides);
            signaletiquePage.checkPrincipalementExercee(princExercee);
            signaletiquePage.clickAjouterTypeActivites();
            signaletiquePage.fillDateDebutType(dateDebut);
            signaletiquePage.fillDateFinType(dateFin);
            signaletiquePage.fillCommentTypeActivite(commentaire);
            signaletiquePage.selectProfession(profession);
            signaletiquePage.clickEnregistrerDetailActiviteEntreprisePP();

            if (!signaletiquePage.checkTableTypeActiviteEmpty()) {
                logSuccess("Ajout type activité pour " + niss, "Ajout type activité OK");
                signaletiquePage.clickEnregistrerActivite();

                checkActivite(niss, TestData.ENTREPRISE_PP);
            } else {
                logFailed("Ajout type activité pour " + niss, "Ajout type activité NOK");
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    private static void checkSuppressionActivite(String niss, boolean suppression) {
        if (suppression) {
            signaletiquePage.checkEtSuppressionDonnees("Activite");

            if (signaletiquePage.checkTableActiviteEmpty()) {
                logSuccess("Suppression activité pour " + niss, "Suppression activité OK");
            } else {
                logFailed("Suppression activité pour " + niss, "Suppression activité NOK");
            }
        }
    }

    static void fillActiviteAssimilee(String niss, String typeActivite,
                                      String dateDebut, String dateFin, boolean suppression,
                                      String commentaire, String assujetti, String princExercee,
                                      String donneesValides) {
        try {
            loadNissOrBce(niss);
            signaletiquePage.clickMenuActivites();
            signaletiquePage.clickVoirToutesActivites();

            checkSuppressionActivite(niss, suppression);

            ajoutTypeActivite(typeActivite, dateDebut, dateFin, commentaire, assujetti, princExercee, donneesValides);

            signaletiquePage.clickEnregistrerActivite();

            checkActivite(niss, TestData.ACT_ASSIMILEE);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void fillActiviteAidantDirigeantSociete(String niss,
                                                   String typeActivite, String nom, String dateDebut, String dateFin,
                                                   String profession, boolean suppression, String commentaire,
                                                   String assujetti, String princExercee, String donneesValides) {
        try {
            loadNissOrBce(niss);
            signaletiquePage.clickMenuActivites();
            signaletiquePage.clickVoirToutesActivites();

            checkSuppressionActivite(niss, suppression);

            ajoutTypeActivite(typeActivite, dateDebut, dateFin, commentaire, assujetti, princExercee, donneesValides);

            signaletiquePage.fillNomIdentiteLiee(nom);
            signaletiquePage.clickRechercheIdentiteLiee();
            signaletiquePage.selectIdentiteLiee();
            signaletiquePage.selectNaceProfession(profession);
            signaletiquePage.clickEnregistrerActivite();

            checkActivite(niss, TestData.AIDANT_DIRIGEANT_SOC);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void fillActiviteAidantEntreprisePP(String niss, String typeActivite,
                                               String nissEntreprisePP, String dateDebut, String dateFin, String profession,
                                               boolean suppression, String commentaire, String assujetti, String princExercee, String donneesValides) {
        try {
            loadNissOrBce(niss);
            signaletiquePage.clickMenuActivites();
            signaletiquePage.clickVoirToutesActivites();

            checkSuppressionActivite(niss, suppression);

            signaletiquePage.clickAjouterActivite();
            signaletiquePage.selectTypeActivite(typeActivite);
            signaletiquePage.selectProfession(profession);
            signaletiquePage.fillDateDebut(dateDebut);
            signaletiquePage.fillDateFin(dateFin);
            signaletiquePage.fillCommentActivite(commentaire);
            signaletiquePage.checkAssujetti(assujetti);
            signaletiquePage.checkDonneesValides(donneesValides);
            signaletiquePage.checkPrincipalementExercee(princExercee);
            signaletiquePage.fillNissIdentiteLiee(nissEntreprisePP);
            signaletiquePage.clickRechercheIdentiteLiee();
            signaletiquePage.selectIdentiteLiee();
            signaletiquePage.selectTypeActiviteAidant();
            nascaPage.fermerNotification();
            signaletiquePage.clickEnregistrerActivite();

            checkActivite(niss, TestData.AIDANT_ENTREPRISE_PP);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void fillActiviteAssocieActifSociete(String niss,
                                                String typeActivite, String denomination, String dateDebut, String dateFin,
                                                String profession, boolean suppression, String commentaire,
                                                String assujetti, String princExercee, String donneesValides,
                                                String apportGestion) {
        try {
            loadNissOrBce(niss);
            signaletiquePage.clickMenuActivites();
            signaletiquePage.clickVoirToutesActivites();

            checkSuppressionActivite(niss, suppression);

            ajoutTypeActivite(typeActivite, dateDebut, dateFin, commentaire, assujetti, princExercee, donneesValides);

            signaletiquePage.selectApportGestion(apportGestion);
            signaletiquePage.fillDenominationIdentiteLiee(denomination);
            signaletiquePage.clickRechercheIdentiteLiee();
            signaletiquePage.selectIdentiteLiee();
            signaletiquePage.selectNaceProfession(profession);
            signaletiquePage.clickEnregistrerActivite();

            checkActivite(niss, TestData.ASSOCIE_ACTIF_SOC);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void fillActiviteAutreSociete(String niss, String typeActivite,
                                         String denomination, String dateDebut, String dateFin,
                                         String profession, boolean suppression, String commentaire,
                                         String assujetti, String princExercee, String donneesValides) {
        try {
            loadNissOrBce(niss);
            signaletiquePage.clickMenuActivites();
            signaletiquePage.clickVoirToutesActivites();

            checkSuppressionActivite(niss, suppression);

            signaletiquePage.clickAjouterActivite();
            signaletiquePage.selectTypeActivite(typeActivite);
            signaletiquePage.fillDateDebut(dateDebut);
            signaletiquePage.fillDateFin(dateFin);
            signaletiquePage.checkAssujetti(assujetti);
            signaletiquePage.checkDonneesValides(donneesValides);
            signaletiquePage.checkPrincipalementExercee(princExercee);
            signaletiquePage.fillCommentActivite(commentaire);
            signaletiquePage.fillDenominationIdentiteLiee(denomination);
            signaletiquePage.clickRechercheIdentiteLiee();
            signaletiquePage.selectIdentiteLiee();
            signaletiquePage.selectNaceProfession(profession);
            nascaPage.fermerNotification();
            signaletiquePage.clickEnregistrerActivite();

            checkActivite(niss, TestData.AUTRE_ACTIVITE_SOC);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void fillActiviteMandataireSociete(String niss, String typeActivite,
                                              String fonction, String denomination, String dateDebut,
                                              String dateFin, String profession, boolean suppression,
                                              String commentaire, String assujetti, String princExercee,
                                              String donneesValides, String forme) {
        try {
            loadNissOrBce(niss);
            signaletiquePage.clickMenuActivites();
            signaletiquePage.clickVoirToutesActivites();

            checkSuppressionActivite(niss, suppression);

            signaletiquePage.clickAjouterActivite();
            signaletiquePage.selectTypeActivite(typeActivite);
            signaletiquePage.selectFonction(fonction);
            signaletiquePage.fillDateDebut(dateDebut);
            signaletiquePage.fillDateFin(dateFin);
            signaletiquePage.checkAssujetti(assujetti);
            signaletiquePage.checkDonneesValides(donneesValides);
            signaletiquePage.checkPrincipalementExercee(princExercee);
            signaletiquePage.fillCommentActivite(commentaire);
            signaletiquePage.fillDenominationIdentiteLiee(denomination);
            signaletiquePage.fillFormeJuridiqueIdentiteLiee(forme);
            signaletiquePage.clickRechercheIdentiteLiee();
            signaletiquePage.selectIdentiteLiee();
            signaletiquePage.selectNaceProfession(profession);
            signaletiquePage.clickEnregistrerActivite();

            checkActivite(niss, TestData.MANDATAIRE_SOC + " (" + fonction + ")");
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void fillEditionActiviteEntreprisePP(String niss, String dateDebut, String dateFin,
                                                String commentaire, String actionTypeActivite, String profession) {
        try {
            loadNissOrBce(niss);
            signaletiquePage.clickMenuActivites();
            signaletiquePage.clickVoirToutesActivites();

            if ("Modification".equals(actionTypeActivite)) {
                signaletiquePage.clickEditFirstActiviteEntreprise();
                SeleniumUtils.waitForActionCommon();
                signaletiquePage.fillDateDebutType(dateDebut);
                signaletiquePage.fillDateFinType(dateFin);
                signaletiquePage.fillCommentTypeActivite(commentaire);
                signaletiquePage.clickEnregistrerDetailActiviteEntreprisePP();
            } else {
                signaletiquePage.clickAjouterDetailActivite();
                signaletiquePage.fillDateDebutType(dateDebut);
                signaletiquePage.fillDateFinType(dateFin);
                signaletiquePage.fillCommentTypeActivite(commentaire);
                signaletiquePage.selectProfession(profession);
                signaletiquePage.clickEnregistrerDetailActiviteEntreprisePP();
            }

            checkActivite(niss, TestData.ENTREPRISE_PP);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void fillEditionActiviteAssimilee(String niss,
                                             String typeActivite, String dateDebut, String dateFin,
                                             String commentaire) {
        try {
            loadNissOrBce(niss);
            signaletiquePage.clickMenuActivites();
            signaletiquePage.clickVoirToutesActivites();
            signaletiquePage.clickEditActivite(typeActivite);
            signaletiquePage.fillDateDebut(dateDebut);
            signaletiquePage.fillDateFin(dateFin);
            signaletiquePage.fillCommentActivite(commentaire);
            signaletiquePage.clickEnregistrerActivite();

            checkActivite(niss, TestData.ACT_ASSIMILEE);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void fillEditionActiviteAidantDirigeantSociete(String niss,
                                                          String typeActivite, String dateDebut, String dateFin,
                                                          String profession, String commentaire) {
        try {
            loadNissOrBce(niss);
            signaletiquePage.clickMenuActivites();
            signaletiquePage.clickVoirToutesActivites();

            editerDirigeantOuAutreSociete(typeActivite, dateDebut, dateFin, profession, commentaire);

            checkActivite(niss, TestData.AIDANT_DIRIGEANT_SOC);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void fillEditionActiviteAidantEntreprisePP(String niss, String dateDebut, String dateFin,
                                                      String profession, String commentaire, String actionTypeActivite) {
        try {
            loadNissOrBce(niss);
            signaletiquePage.clickMenuActivites();
            signaletiquePage.clickVoirToutesActivites();

            if ("Modification".equals(actionTypeActivite)) {
                signaletiquePage.clickEditFirstActivite();
                signaletiquePage.fillDateDebut(dateDebut);
                signaletiquePage.fillDateFin(dateFin);
                signaletiquePage.fillCommentActivite(commentaire);
                signaletiquePage.clickEnregistrerDetailActiviteEntreprisePP();
            } else {
                signaletiquePage.clickAjouterDetailActivite();
                signaletiquePage.fillCommentTypeActivite(commentaire);
                signaletiquePage.selectProfession(profession);
                signaletiquePage.clickEnregistrerDetailActiviteEntreprisePP();
            }

            checkActivite(niss, TestData.AIDANT_ENTREPRISE_PP);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void fillEditionActiviteAssocieActifSociete(String niss,
                                                       String typeActivite, String dateDebut, String dateFin,
                                                       String profession, String commentaire, String apportGestion) {
        try {
            loadNissOrBce(niss);
            signaletiquePage.clickMenuActivites();
            signaletiquePage.clickVoirToutesActivites();

            signaletiquePage.clickEditActivite(typeActivite);

            signaletiquePage.fillDateDebut(dateDebut);
            signaletiquePage.fillDateFin(dateFin);
            signaletiquePage.fillCommentActivite(commentaire);
            signaletiquePage.selectApportGestion(apportGestion);
            signaletiquePage.selectNaceProfession(profession);
            signaletiquePage.clickEnregistrerActivite();

            checkActivite(niss, TestData.ASSOCIE_ACTIF_SOC);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void fillEditionActiviteAutreSociete(String niss,
                                                String typeActivite, String dateDebut, String dateFin,
                                                String profession, String commentaire) {
        try {
            loadNissOrBce(niss);
            signaletiquePage.clickMenuActivites();
            signaletiquePage.clickVoirToutesActivites();

            editerDirigeantOuAutreSociete(typeActivite, dateDebut, dateFin, profession, commentaire);

            checkActivite(niss, TestData.AUTRE_ACTIVITE_SOC);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void fillEditionActiviteMandataireSociete(String niss, String fonction, String dateDebut,
                                                     String dateFin, String profession, String commentaire) {
        try {
            loadNissOrBce(niss);
            signaletiquePage.clickMenuActivites();
            signaletiquePage.clickVoirToutesActivites();
            signaletiquePage.clickEditFirstActivite();
            signaletiquePage.selectFonction(fonction);
            signaletiquePage.fillDateDebut(dateDebut);
            signaletiquePage.fillDateFin(dateFin);
            signaletiquePage.fillCommentActivite(commentaire);
            signaletiquePage.selectNaceProfession(profession);
            signaletiquePage.clickEnregistrerActivite();

            checkActivite(niss, TestData.MANDATAIRE_SOC + " (" + fonction + ")");
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    private static void ajoutTypeActivite(String typeActivite, String dateDebut, String dateFin, String commentaire, String assujetti, String princExercee, String donneesValides) {
        signaletiquePage.clickAjouterActivite();
        signaletiquePage.selectTypeActivite(typeActivite);
        signaletiquePage.fillDateDebut(dateDebut);
        signaletiquePage.fillDateFin(dateFin);
        signaletiquePage.fillCommentActivite(commentaire);
        signaletiquePage.checkAssujetti(assujetti);
        signaletiquePage.checkDonneesValides(donneesValides);
        signaletiquePage.checkPrincipalementExercee(princExercee);
    }

    private static void checkActivite(String niss, String typeActivite) {
        if (TableUtils.isElementPresent("table-activites", typeActivite)) {
            logSuccess("Edition activité pour " + niss, "Edition activité OK");
        } else {
            logFailed("Edition activité pour " + niss, "Edition activité NOK");
        }
    }

    private static void editerDirigeantOuAutreSociete(String typeActivite, String dateDebut, String dateFin, String profession, String commentaire) {
        signaletiquePage.clickEditActivite(typeActivite);
        signaletiquePage.fillDateDebut(dateDebut);
        signaletiquePage.fillDateFin(dateFin);
        signaletiquePage.fillCommentActivite(commentaire);
        signaletiquePage.selectNaceProfession(profession);
        signaletiquePage.clickEnregistrerActivite();
    }
}