package be.ucm.cas.nasca.web.test.baremes.pm;

import be.ucm.cas.nasca.web.test.support.*;

public class BaremesPMTestBase extends TestBase {

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    static void ajoutCotisationAnnuelleBaremesPM(String dateDebut, String dateFin) {
        try {
            homeMenuPage.clickAdministration();
            administrationPage.clickMenuBaremesPersonnesMorales();
            administrationPage.clickOngletCotisationsAnnuellesBaremesPersonnesMorales();
            administrationPage.fillDateFromCotisationsAnnuellesBaremesPM(dateDebut);
            administrationPage.fillDateToCotisationsAnnuellesBaremesPM(dateFin);
            administrationPage.clickBtnRechercherCotisationsAnnuellesBaremesPM();

            if (TableUtils.isTableVide("dataTableCotisation")) {
                doAjouterCotisationAnnuelleBareme(dateDebut, dateFin);

                checkAjout(dateDebut, "Cotisation annuelle", "dataTableCotisation");
            } else {
                doAjouterCotisationAnnuelleBareme(dateDebut, dateFin);
                logSuccess("Barèmes PM (Cotisation annuelle) - Ajout Cotisation annuelle impossible", "Barèmes PM (Cotisation annuelle) OK");
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    static void ajoutMajorationBaremesPM(String dateDebut, String dateFinPrec) {
        try {
            homeMenuPage.clickAdministration();
            administrationPage.clickMenuBaremesPersonnesMorales();
            administrationPage.clickOngletMajorationsBaremesPersonnesMorales();

            administrationPage.clickEditionMajorationEnCours();
            administrationPage.fillDateFinValiditeMajoration(dateFinPrec);
            administrationPage.clickBtnEnregistrerBareme();

            if (administrationPage.getDateFinValiditeMajoration().equals(SeleniumUtils.deleteFormat(dateFinPrec))) {
                logSuccess("Barèmes PM (Majoration) - Maj Majoration précédente",
                        "Maj Majoration précédente OK");
            } else {
                logFailed("Barèmes PM (Majoration) - Maj Majoration précédente",
                        "Maj Majoration précédente KO");
            }

            administrationPage.clickBtnAjouterMajorationBaremesPM();
            administrationPage.fillDateDebutValiditeMajoration(dateDebut);
            administrationPage.checkTaux();
            administrationPage.fillValeurTaux();
            administrationPage.clickBtnEnregistrerBareme();

            checkAjout(dateDebut, TestData.MAJORATION, "dataTableMajoration");
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    static void ajoutFraisBaremesPM(String dateDebut, String dateFinPrec, String typeFrais) {
        try {
            homeMenuPage.clickAdministration();
            administrationPage.clickMenuBaremesPersonnesMorales();
            administrationPage.clickOngletFraisBaremesPersonnesMorales();

            rechercherFrais(dateFinPrec, typeFrais);

            if (administrationPage.getDateFinValiditeFrais().equals(SeleniumUtils.deleteFormat(dateFinPrec))) {
                logSuccess("Barèmes PM (" + typeFrais + ") - Maj précédente OK");
            } else {
                logFailed("Barèmes PM (" + typeFrais + ") - Maj précédente KO");
            }

            fillDonneesFrais(dateDebut, typeFrais);

            administrationPage.selectTypeFraisBaremesPM(typeFrais);
            administrationPage.clickBtnRechercherFraisBaremesPM();

            checkAjout(dateDebut, typeFrais, "dataTableFrais");
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    static void ajoutEcheanceBaremesPM(String dateDebut, String dateFinPrec, String typeEcheance) {
        try {
            homeMenuPage.clickAdministration();
            administrationPage.clickMenuBaremesPersonnesMorales();
            administrationPage.clickOngletEcheancesBaremesPersonnesMorales();

            doSelectTypeEcheanceBareme(typeEcheance);

            if (!TableUtils.isTableVide("dataTableEcheance")) {
                doFillDateFinValiditeEcheance(dateFinPrec);

                doSelectTypeEcheanceBareme(typeEcheance);

                if (administrationPage.getDateFinValiditeEcheance().equals(SeleniumUtils.deleteFormat(dateFinPrec))) {
                    logSuccess("Barèmes PM (" + typeEcheance + ") - Maj Echéance OK");
                } else {
                    logFailed("Barèmes PM (" + typeEcheance + ") - Maj Echéance KO");
                }
            }

            fillDonneesEcheance(dateDebut, typeEcheance);

            doSelectTypeEcheanceBareme(typeEcheance);

            checkAjout(dateDebut, "Echéance - " + typeEcheance, "dataTableEcheance");
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    private static void doAjouterCotisationAnnuelleBareme(String dateDebut, String dateFin) {
        administrationPage.clickBtnAjouterCotisationAnnuelleBaremesPM();
        administrationPage.fillDateDebutValiditeCotisationAnnuelle(dateDebut);
        administrationPage.fillDateFinValiditeCotisationAnnuelle(dateFin);
        administrationPage.fillTotalBilanPivot();
        administrationPage.fillCotisationMinimale();
        administrationPage.fillCotisationMaximale();
        administrationPage.clickBtnEnregistrerBareme();
    }

    private static void doSelectTypeEcheanceBareme(String typeEcheance) {
        administrationPage.selectTypeEcheanceBaremesPM(typeEcheance);
        administrationPage.clickBtnRechercherEcheancesBaremesPM();
    }

    private static void doFillDateFinValiditeEcheance(String dateFinPrec) {
        administrationPage.clickEditionEcheanceEnCours();
        administrationPage.fillDateFinValiditeEcheance(dateFinPrec);
        administrationPage.clickBtnEnregistrerBareme();
    }

    private static void checkAjout(String dateDebut, String type, String table) {
        if (TableUtils.isElementPresent(table, dateDebut)) {
            logSuccess("Barèmes PM (" + type + ") - Ajout OK");
        } else {
            logSuccess("Barèmes PM (" + type + ") - Ajout KO");
        }
    }

    private static void rechercherFrais(String dateFinPrec, String typeFrais) {
        administrationPage.selectTypeFraisBaremesPM(typeFrais);
        administrationPage.clickBtnRechercherFraisBaremesPM();

        administrationPage.clickEditionFraisEnCours();
        administrationPage.fillDateFinValiditeFrais(dateFinPrec);
        administrationPage.clickBtnEnregistrerBareme();

        administrationPage.selectTypeFraisBaremesPM(typeFrais);
        administrationPage.clickBtnRechercherFraisBaremesPM();
    }

    private static void fillDonneesFrais(String dateDebut, String typeFrais) {
        administrationPage.clickBtnAjouterFraisBaremesPM();
        administrationPage.fillDateDebutValiditeFrais(dateDebut);
        administrationPage.checkTaux();
        administrationPage.fillValeurTaux();
        administrationPage.selectNatureFraisBaremesPM(typeFrais);
        administrationPage.fillMontantPivotFrais();
        administrationPage.clickBtnEnregistrerBareme();
    }

    private static void fillDonneesEcheance(String dateDebut, String typeEcheance) {
        administrationPage.clickBtnAjouterEcheanceBaremesPM();
        administrationPage.fillDateDebutValiditeEcheance(dateDebut);
        administrationPage.selectContexteEcheanceBaremesPM(typeEcheance);
        administrationPage.fillDelaiEcheanceBaremesPM();
        administrationPage.clickBtnEnregistrerBareme();
    }
}
