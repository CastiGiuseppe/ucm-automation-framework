package be.ucm.cas.nasca.web.test.signaletique;

import be.ucm.cas.nasca.web.test.support.EnumTypeTest;
import be.ucm.cas.nasca.web.test.support.SeleniumUtils;
import be.ucm.cas.nasca.web.test.support.TestBase;
import be.ucm.cas.nasca.web.test.support.TestData;

public class SignaletiqueTestBase extends TestBase {

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    public static void doChangePaysNaissance(String niss, String pays) {
        try {
            loadNissOrBce(niss);
            signaletiquePage.clickMenuDetailsIdentite();
            signaletiquePage.clickOuvrirAccordeonFCaracteristiques();
            signaletiquePage.clickBtnModifierCaracteristiques();
            signaletiquePage.selectPaysNaissance(pays);
            signaletiquePage.clickBtnEnregistrerCaracteristiques();
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    public static void doChangeAdresse(String niss, String pays, String codePostal, String rue, String numero, String boite) {
        try {
            loadNissOrBce(niss);
            signaletiquePage.clickMenuAdresse();
            signaletiquePage.clickBtnAddAdress();
            signaletiquePage.clickBtnSelectTypeAdresseLegal();
            signaletiquePage.clickOuiModalAdresse();
            signaletiquePage.selectPays(pays);
            if ("Belgique".equals(pays)) {
                signaletiquePage.clickLocaliteBelge(codePostal);
            } else {
                signaletiquePage.clickLocaliteEtrangere(codePostal);
            }
            signaletiquePage.fillAdresse(rue, numero, boite);
            signaletiquePage.clickBtnEnregistrerAdresse();

            logSuccess("Changement d'adresse: " + niss);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void doChangeTel(String niss, String telnumber, boolean suppression) {
        try {
            loadNissOrBce(niss);
            signaletiquePage.clickMenuDetailsIdentite();
            if (suppression) {
                signaletiquePage.checkEtSuppressionDonnees("Téléphone");

                if (signaletiquePage.checkTableTelephoneEmpty()) {
                    logSuccess("Suppression téléphone pour " + niss, "Suppression téléphone OK");
                } else {
                    logFailed("Suppression téléphone pour " + niss, "Suppression téléphone NOK");
                }
            }
            signaletiquePage.clickTableBtnEditMedia("Téléphone");
            signaletiquePage.fillNumeroTel(telnumber);
            signaletiquePage.clickBtnPrefere();
            signaletiquePage.clickBtnEnregistrerTel();

            logSuccess("Changement de numero de tel pour " + niss + ": " + telnumber);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void doChangeEmail(String niss, String email, boolean suppression) {
        try {
            loadNissOrBce(niss);
            signaletiquePage.clickMenuDetailsIdentite();
            if (suppression) {
                signaletiquePage.checkEtSuppressionDonnees("Email");

                if (signaletiquePage.checkTableEmailEmpty()) {
                    logSuccess("Suppression email pour " + niss, "Suppression email OK");
                } else {
                    logFailed("Suppression email pour " + niss, "Suppression email NOK");
                }
            }
            signaletiquePage.clickTableBtnEditMedia("Email");
            signaletiquePage.fillEmail(email);
            signaletiquePage.clickBtnPrefere();
            signaletiquePage.clickBtnEnregistrerEmail();

            logSuccess("Changement d'email pour " + niss + ": " + email);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    public static void creationPP(String niss) {
        try {
            homeMenuPage.clickOngletGestionClient();

            signaletiquePage.clickCreationIdentitePP();

            //Onglet 1 Caractéristiques & Identifiants
            signaletiquePage.fillCiviliteIdentitePP("Monsieur");
            signaletiquePage.fillNomIdentitePP("Ronaldo");
            signaletiquePage.fillPrenomIdentitePP("Cristiano");
            signaletiquePage.fillNationaliteIdentitePP("Belgique");
            signaletiquePage.fillSexeIdentitePP(TestData.MASCULIN);
            signaletiquePage.fillDateNaissanceIdentitePP("15111975");
            signaletiquePage.fillLanguePrefereeIdentite(TestData.LANGUE_FR);

            signaletiquePage.clickBtnAjoutIdentifiantIdentite();
            signaletiquePage.fillTypeIdentifiant("NISS");
            signaletiquePage.fillNumIdentifiant(niss);
            signaletiquePage.clickBtnEnregistrerIdentifiantIdentite();

            nascaPage.clickBtnSuivantWizard();
            //Onglet 2 Coordonnées & adresses
            //Téléphone
            signaletiquePage.clickBtnAjoutTelephone();
            signaletiquePage.fillNumeroTel("071743246");
            signaletiquePage.clickBtnEnregistrerTel();

            //Email
            signaletiquePage.clickBtnAjoutEmail();
            signaletiquePage.fillEmail("test@ucm.be");
            signaletiquePage.clickBtnEnregistrerEmail();

            //Adresse
            signaletiquePage.clickBtnAjoutAdresseIdentifiantPP();
            signaletiquePage.clickBtnSelectTypeAdresseLegal();
            ajoutAdresse();

            nascaPage.clickBtnSuivantWizard();

            //Compte bancaire
            ajoutCompteBancaire();

            nascaPage.clickBtnTerminerWizard();

            loadNissOrBce(niss);

            logSuccess("Création identité PP pour " + niss);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    private static void ajoutCompteBancaire() {
        signaletiquePage.clickBtnAddCompteBancaire();
        signaletiquePage.fillNumeroIban("BE77001610786242");
        signaletiquePage.refreshBic();
        signaletiquePage.fillCommentaire("Test selenium");
        signaletiquePage.clickBtnPrefereCompteBancaire();
        signaletiquePage.clickBtnEnregistrerCompteBancaire();
    }

    static void creationPM(String bce) {
        try {
            homeMenuPage.clickOngletGestionClient();

            signaletiquePage.clickCreationIdentitePM();

            //Onglet 1 Caractéristiques & Identifiants
            signaletiquePage.fillFormeJuridiqueIdentitePM("SPRL");
            signaletiquePage.fillNomIdentitePP("SPRL TEST");
            signaletiquePage.fillLanguePrefereeIdentite(TestData.LANGUE_FR);

            signaletiquePage.clickBtnAjoutIdentifiantIdentite();
            signaletiquePage.fillTypeIdentifiant("BCE");
            signaletiquePage.fillNumIdentifiant(bce);
            signaletiquePage.clickBtnEnregistrerIdentifiantIdentite();

            nascaPage.clickBtnSuivantWizard();
            //Onglet 2 Coordonnées & adresses
            //Téléphone
            signaletiquePage.clickBtnAjoutTelephone();
            signaletiquePage.fillNumeroTel("071743246");
            signaletiquePage.clickBtnEnregistrerTel();

            //Email
            signaletiquePage.clickBtnAjoutEmail();
            signaletiquePage.fillEmail("test@ucm.be");
            signaletiquePage.clickBtnEnregistrerEmail();

            //Adresse
            signaletiquePage.clickBtnAjoutAdresseIdentifiantPP();
            signaletiquePage.clickBtnSelectTypeAdresseLegalPM();
            ajoutAdresse();

            nascaPage.clickBtnSuivantWizard();

            //Compte bancaire
            ajoutCompteBancaire();

            nascaPage.clickBtnTerminerWizard();

            loadNissOrBce(bce);

            logSuccess("Création identité PM pour " + bce);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + bce, e);
        }
    }

    private static void ajoutAdresse() {
        signaletiquePage.clickLocaliteBelge("1000");
        signaletiquePage.clickLangue(TestData.LANGUE_FR);
        signaletiquePage.fillAdresse("rue de la loi", "45", "1");
        signaletiquePage.clickBtnEnregistrerAdresse();
    }

    static void doChangeCompteBancaire(String niss, String compte, boolean suppression) {
        try {
            loadNissOrBce(niss);
            signaletiquePage.clickMenuDonneesBancaires();
            if (suppression) {
                signaletiquePage.checkEtSuppressionDonnees("Compte bancaire");

                if (signaletiquePage.checkTableCompteBancaireEmpty()) {
                    logSuccess("Suppression compte bancaire pour " + niss, "Suppression compte bancaire OK");
                } else {
                    logFailed("Suppression compte bancaire pour " + niss, "Suppression compte bancaire NOK");
                }
                signaletiquePage.clickBtnAddCompteBancaire();
                signaletiquePage.fillNumeroIban(compte);
                signaletiquePage.refreshBic();
                signaletiquePage.fillCommentaire("Test selenium");
                signaletiquePage.clickBtnPrefereCompteBancaire();
            } else {
                if (!signaletiquePage.checkTableCompteBancaireEmpty()) {
                    signaletiquePage.clickTableBtnEditCompteBancaire();
                    signaletiquePage.fillCommentaire("Test selenium modif");
                    signaletiquePage.fillDateFinValiditeCompteBancaire();
                }
            }

            signaletiquePage.clickBtnEnregistrerCompteBancaire();

            logSuccess("Changement de compte bancaire pour " + niss + " " + compte);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void doChangeContact(String niss, String typeContact, boolean suppression) {
        try {
            loadNissOrBce(niss);
            signaletiquePage.clickMenuContact();

            if (suppression) {
                signaletiquePage.checkEtSuppressionDonnees("Contact");
                if (signaletiquePage.checkTableContactEmpty()) {
                    logSuccess("Suppression de contact pour " + niss, "Suppression de contact OK");

                    signaletiquePage.clickAjouterContact();
                    signaletiquePage.selectTypeContact(typeContact);
                    signaletiquePage.checkMandate();
                    signaletiquePage.fillNomIdentiteLiee("ROBERT");
                    signaletiquePage.clickRechercheIdentiteLiee();
                    signaletiquePage.selectIdentiteLiee();
                    signaletiquePage.clickBtnEnregistrerContact();

                    checkContact(niss, typeContact);
                } else {
                    logFailed("Suppression de contact pour " + niss + " " + typeContact, "Suppression de contact NOK");
                }
            } else {
                signaletiquePage.clickEditerContact();
                signaletiquePage.selectTypeContact(typeContact);
                signaletiquePage.clickBtnEnregistrerContact();

                checkContact(niss, typeContact);
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    private static void checkContact(String niss, String typeContact) {
        if (signaletiquePage.checkContact(typeContact)) {
            logSuccess("Check contact pour " + niss + " " + typeContact);
        } else {
            logFailed("Check contact pour " + niss + " " + typeContact);
        }
    }

    static void doChangeCohabitationLegaleSansRaison(String niss, String niss2) {
        try {
            loadNissOrBce(niss);
            signaletiquePage.clickMenuLiensPP();

            signaletiquePage.checkEtSuppressionDonnees(TestData.ETAT_CIVIL);
            signaletiquePage.checkEtSuppressionDonnees(TestData.COHABITATION_LEGALE);

            if (signaletiquePage.checkTableCohabitationLegaleEmpty()) {
                logSuccess("Suppression de cohabitation légale");

                signaletiquePage.clickAjouterCohabitationLegale();
                signaletiquePage.fillNomIdentiteLiee(SeleniumUtils.generateRandomString());
                signaletiquePage.fillPrenomIdentiteLiee(SeleniumUtils.generateRandomString());
                signaletiquePage.fillSexeIdentiteLiee(TestData.MASCULIN);
                signaletiquePage.fillNissIdentiteLiee(niss2);
                signaletiquePage.clickRechercheIdentiteLiee();
                signaletiquePage.clickCreerIdentiteLiee();
                signaletiquePage.clickBtnEnregistrerCohabitationLegale();

                if (signaletiquePage.checkCohabitationLegale(SeleniumUtils.formatNissOrBceOrNumeroClient(niss2))) {
                    logSuccess("Ajout de cohabitation légale");
                } else {
                    logFailed("Ajout de cohabitation légale");
                }
            } else {
                logFailed("Suppression de cohabitation légale", "");
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void doChangeCohabitationLegaleAvecRaison(String niss, String dateDebut, String dateFin, String raison, String niss2) {
        try {
            loadNissOrBce(niss);
            signaletiquePage.clickMenuLiensPP();

            signaletiquePage.checkEtSuppressionDonnees(TestData.ETAT_CIVIL);
            signaletiquePage.checkEtSuppressionDonnees(TestData.COHABITATION_LEGALE);

            if (signaletiquePage.checkTableCohabitationLegaleEmpty()) {
                logSuccess("Suppression de cohabitation légale");

                signaletiquePage.clickAjouterCohabitationLegale();
                signaletiquePage.fillDateDebutValiditeCohabitationLegale(dateDebut);
                signaletiquePage.fillDateFinValiditeCohabitationLegale(dateFin);
                signaletiquePage.selectRaisonFinCohabitationLegale(raison);
                signaletiquePage.fillNomIdentiteLiee(SeleniumUtils.generateRandomString());
                signaletiquePage.fillPrenomIdentiteLiee(SeleniumUtils.generateRandomString());
                signaletiquePage.fillSexeIdentiteLiee(TestData.MASCULIN);
                signaletiquePage.fillNissIdentiteLiee(niss2);
                signaletiquePage.clickRechercheIdentiteLiee();
                signaletiquePage.clickCreerIdentiteLiee();
                signaletiquePage.clickBtnEnregistrerCohabitationLegale();

                if (signaletiquePage.checkCohabitationLegale(raison)) {
                    logSuccess("Ajout de cohabitation légale");
                } else {
                    logFailed("Ajout de cohabitation légale");
                }
            } else {
                logFailed("Suppression de cohabitation légale", "");
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void doChangeEtatCivilSansDate(String niss, String etatCivil, boolean suppression) {
        try {
            loadNissOrBce(niss);
            signaletiquePage.clickMenuLiensPP();

            if (suppression) {
                signaletiquePage.checkEtSuppressionDonnees(TestData.ETAT_CIVIL);
                signaletiquePage.checkEtSuppressionDonnees(TestData.COHABITATION_LEGALE);

                if (signaletiquePage.checkTableEtatCivilEmpty()) {
                    logSuccess("Suppression d'état civil pour " + niss, "Suppression d'état civil OK");

                    signaletiquePage.clickAjouterEtatCivil();
                    signaletiquePage.selectEtatCivil(etatCivil);
                    signaletiquePage.clickBtnEnregistrerEtatCivil();

                    checkEtatCivil(niss, etatCivil);
                } else {
                    logFailed("Suppression d'état civil pour " + niss + " " + etatCivil, "Suppression d'état civil pour NOK");
                }
            } else {
                signaletiquePage.clickEditerEtatCivil();
                signaletiquePage.fillCommentaireEtatCivil("Test");
                signaletiquePage.clickBtnEnregistrerEtatCivil();

                checkEtatCivil(niss, etatCivil);
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    private static void checkEtatCivil(String niss, String etatCivil) {
        if (signaletiquePage.checkEtatCivil(etatCivil)) {
            logSuccess("Check état civil pour " + niss + " " + etatCivil);
        } else {
            logFailed("Check état civil pour " + niss + " " + etatCivil);
        }
    }

    static void doChangeEtatCivilAvecDate(String niss, String niss2, String etatCivil, boolean delete) {
        try {
            loadNissOrBce(niss);
            signaletiquePage.clickMenuLiensPP();

            if (delete) {
                signaletiquePage.checkEtSuppressionDonnees(TestData.ETAT_CIVIL);
                signaletiquePage.checkEtSuppressionDonnees(TestData.COHABITATION_LEGALE);

                if (signaletiquePage.checkTableEtatCivilEmpty()) {
                    logSuccess("Suppression d'état civil pour " + niss, "Suppression d'état civil OK");

                    signaletiquePage.clickAjouterEtatCivil();
                    signaletiquePage.fillDateDebutValiditeEtatCivil("01012015");
                    signaletiquePage.selectEtatCivil(etatCivil);
                    signaletiquePage.fillNomIdentiteLiee(SeleniumUtils.generateRandomString());
                    signaletiquePage.fillPrenomIdentiteLiee(SeleniumUtils.generateRandomString());
                    signaletiquePage.fillSexeIdentiteLiee(TestData.MASCULIN);
                    signaletiquePage.fillNissIdentiteLiee(niss2);
                    signaletiquePage.clickRechercheIdentiteLiee();
                    signaletiquePage.clickCreerIdentiteLiee();

                    signaletiquePage.clickBtnEnregistrerEtatCivil();

                    checkEtatCivil(niss, etatCivil);
                } else {
                    logFailed("Suppression d'état civil pour " + niss + " " + etatCivil, "Suppression d'état civil pour NOK");
                }
            } else {
                signaletiquePage.clickAjouterEtatCivil();
                signaletiquePage.fillDateDebutValiditeEtatCivil("01012016");
                signaletiquePage.selectEtatCivil(etatCivil);
                signaletiquePage.clickBtnEnregistrerEtatCivil();

                checkEtatCivil(niss, etatCivil);
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }
}