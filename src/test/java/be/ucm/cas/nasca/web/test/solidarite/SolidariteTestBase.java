package be.ucm.cas.nasca.web.test.solidarite;

import be.ucm.cas.nasca.web.test.support.EnumTypeTest;
import be.ucm.cas.nasca.web.test.support.SeleniumUtils;
import be.ucm.cas.nasca.web.test.support.TestBase;
import be.ucm.cas.nasca.web.test.support.TestData;

public class SolidariteTestBase extends TestBase {

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    static void addLienSolidaire(String nissDebiteur,
                                 String nissCodebiteur, String bceDebiteur, String bceCodebiteur,
                                 boolean isNouveauCodebiteur, String raisonNonSolidarite,
                                 String trimestreDebut, String trimestreFin,
                                 String dateDebutPriseEffet, String dateFinPriseEffet, String typeCodebiteur) {
        try {
            String codebiteur;
            String debiteur = loadDebiteur(nissDebiteur, bceDebiteur);
            if (raisonNonSolidarite == null) {
                gestionClientPage.clickRadioPeriodesSolidaires();
            } else {
                gestionClientPage.clickRadioPeriodesNonSolidaires();
            }
            logInfo("Situation avant ajout du lien pour " + debiteur, "");
            gestionClientPage.clickAjoutCodebiteurSolidaire();
            if (!isNouveauCodebiteur) {
                if (nissCodebiteur != null) {
                    gestionClientPage.fillTypeIdentiteCodebiteur("Personne physique");
                    gestionClientPage.fillNissLiaisonCodebiteur(nissCodebiteur);
                    codebiteur = nissCodebiteur;
                } else {
                    gestionClientPage.fillTypeIdentiteCodebiteur("Personne morale");
                    gestionClientPage.fillBceLiaisonCodebiteur(bceCodebiteur);
                    codebiteur = bceCodebiteur;
                }
                gestionClientPage.clickBtnSearchCodebiteur();
                gestionClientPage.clickSearchResultCodebiteur(SeleniumUtils.formatNissOrBceOrNumeroClient(codebiteur));
            } else {
                if (TestData.TYPE_PP.equals(typeCodebiteur)) {
                    gestionClientPage.createCodebiteurPP(TestData.NISS_NUMBER);
                    codebiteur = TestData.NISS_NUMBER;
                    gestionClientPage.clickOngletAdresseIdentiteSolidaire();
                    gestionClientPage.createAdresseCodebiteurPP();
                } else {
                    gestionClientPage.createCodebiteurPM(TestData.BCE_NUMBER);
                    codebiteur = TestData.BCE_NUMBER;
                    gestionClientPage.clickOngletAdresseIdentiteSolidaire();
                    gestionClientPage.createAdresseCodebiteurPM();
                }
            }

            nascaPage.clickBtnSuivantWizard();
            gestionClientPage.clickBtnAddPeriodeSolidarite();

            if (raisonNonSolidarite != null) {
                gestionClientPage.checkPeriodeSolidarite();
                gestionClientPage.selectRaisonNonSolidarite(raisonNonSolidarite);
            }

            gestionClientPage.fillDateDebutPeriodeSolidarite(trimestreDebut);
            if (dateDebutPriseEffet != null) {
                gestionClientPage.fillDateDebutPriseEffetSolidarite(dateDebutPriseEffet);
            }
            if (trimestreFin != null) {
                gestionClientPage.fillDateFinPeriodeSolidarite(trimestreFin);
            }
            if (dateFinPriseEffet != null) {
                gestionClientPage.fillDateFinPriseEffetSolidarite(dateFinPriseEffet);
            }

            gestionClientPage.clickBtnEnregistrer();
            nascaPage.clickBtnOuiModal();
            nascaPage.clickBtnTerminerWizard();

            if (gestionClientPage.checkSolidarite(SeleniumUtils.formatNissOrBceOrNumeroClient(codebiteur), raisonNonSolidarite)) {
                logSuccess("Ajout du lien de solidarité pour " + debiteur, "Solidaire avec " + codebiteur);
            } else {
                logFailed("Ajout du lien de solidarité pour " + debiteur, "Solidaire avec " + codebiteur);
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    static void editLienSolidaire(String nissDebiteur,
                                  String nissCodebiteur, String bceDebiteur, String bceCodebiteur,
                                  String raisonNonSolidarite, String trimestreDebut,
                                  String trimestreFin, String dateDebutPriseEffet,
                                  String dateFinPriseEffet) {
        try {
            String debiteur = loadDebiteur(nissDebiteur, bceDebiteur);

            gestionClientPage.clickRadioToutPeriodesSolidaires();
            logInfo("Situation avant edition du lien pour " + debiteur, "");
            String codebiteur = loadCodebiteur(nissCodebiteur, bceCodebiteur);
            gestionClientPage.clickViewSolidarite(SeleniumUtils.formatNissOrBceOrNumeroClient(codebiteur));
            gestionClientPage.clickModifierCodebiteurSolidaire();

            if (raisonNonSolidarite != null) {
                gestionClientPage.checkPeriodeSolidarite();
                gestionClientPage.selectRaisonNonSolidarite(raisonNonSolidarite);
            } else {
                gestionClientPage.decheckPeriodeSolidarite();
            }

            if (trimestreDebut != null) {
                gestionClientPage.fillDateDebutPeriodeSolidarite(trimestreDebut);
            }
            if (dateDebutPriseEffet != null) {
                gestionClientPage.fillDateDebutPriseEffetSolidarite(dateDebutPriseEffet);
            }
            if (trimestreFin != null) {
                gestionClientPage.fillDateFinPeriodeSolidarite(trimestreFin);
            }
            if (dateFinPriseEffet != null) {
                gestionClientPage.fillDateFinPriseEffetSolidarite(dateFinPriseEffet);
            }

            gestionClientPage.clickBtnEnregistrer();
            if (!gestionClientPage.existModalOuiNonSolidarite()) {
                logFailed("Edition du lien de solidarité pour " + debiteur, "Solidaire avec " + codebiteur);
            } else {
                nascaPage.clickBtnOuiModal();
            }

            if (gestionClientPage.checkSolidarite(SeleniumUtils.formatNissOrBceOrNumeroClient(codebiteur), raisonNonSolidarite)) {
                logSuccess("Edition du lien de solidarité pour " + debiteur, "Solidaire avec " + codebiteur);
            } else {
                logFailed("Edition du lien de solidarité pour " + debiteur, "Solidaire avec " + codebiteur);
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    static void checkSolidaireDe(String nissDebiteur,
                                 String nissCodebiteur, String bceDebiteur, String bceCodebiteur,
                                 boolean isPeriodesNonSolidaires, String raisonNonSolidarite) {
        try {
            String codebiteur;
            String debiteur;

            if (nissCodebiteur != null) {
                loadNissOrBce(nissCodebiteur);
                codebiteur = nissCodebiteur;
            } else {
                loadNissOrBce(bceCodebiteur);
                codebiteur = bceCodebiteur;
            }
            debiteur = loadCodebiteur(nissDebiteur, bceDebiteur);

            gestionClientPage.clickMenuDebiteursPrincipaux();
            if (isPeriodesNonSolidaires) {
                gestionClientPage.clickRadioPeriodesNonSolidaires();
            }

            if (gestionClientPage.checkSolidarite(SeleniumUtils.formatNissOrBceOrNumeroClient(debiteur), raisonNonSolidarite)) {
                logSuccess("Check lien de solidarité pour " + codebiteur, "Solidaire avec " + debiteur);
            } else {
                logFailed("Check lien de solidarité pour " + codebiteur, "Solidaire avec " + debiteur);
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    static void creationCourrierSolidaire(String nissDebiteur,
                                          String nissCodebiteur, String bceDebiteur, String bceCodebiteur) {
        try {
            String codebiteur;
            String debiteur = loadDebiteur(nissDebiteur, bceDebiteur);

            logInfo("Situation avant création courrier pour " + debiteur, "");
            codebiteur = loadCodebiteur(nissCodebiteur, bceCodebiteur);
            gestionClientPage.clickViewSolidarite(SeleniumUtils.formatNissOrBceOrNumeroClient(codebiteur));

            gestionClientPage.clickEnvoyerCourrierCodebiteurSolidaire();
            gestionClientPage.fillSuiteASolidaire("A l’initiative CAS");
            gestionClientPage.clickCheckDebiteurCreationPeriode();
            gestionClientPage.clickCheckCodebiteurCreationPeriode();
            gestionClientPage.clickBtnEnregistrer();
            logSuccess("Création courrier de solidarité pour " + debiteur, "Solidaire avec " + debiteur);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    private static String loadDebiteur(String nissDebiteur, String bceDebiteur) {
        if (nissDebiteur != null) {
            loadNissOrBce(nissDebiteur);
            gestionClientPage.clickMenuCodebiteurs();
            return nissDebiteur;
        } else {
            loadNissOrBce(bceDebiteur);
            gestionClientPage.clickMenuCodebiteurs();
            return bceDebiteur;
        }
    }

    private static String loadCodebiteur(String nissCodebiteur, String bceCodebiteur) {
        if (nissCodebiteur != null) {
            return nissCodebiteur;
        } else {
            return bceCodebiteur;
        }
    }
}