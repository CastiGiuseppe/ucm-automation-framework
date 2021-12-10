package be.ucm.cas.nasca.web.test.courrier;

import be.ucm.cas.nasca.web.test.support.*;

import java.util.Date;

public class CourrierTestBase extends TestBase {

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    static void doVerificationStatistique(String numeroClient, String object, String etat) {
        try {
            homeMenuPage.clickOngletMultiDossiers();
            multiDossiersPage.clickMenuCourrierEntrantStatistiques();
            //pour les dates du critère de recherche:
            //j'ai du inséré les dates de la veille ainsi que la date du jours a cause d'un bug détecter
            //un jira à été creer et nous attendons que ce bug soit corrigé pour changer les dates avec la date du jour et la date du lendemain

            multiDossiersPage.fillDateCritereRechercheStatistiqueDebut(DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, DateUtils.getDateFuturOrPass(-1, new Date())));
            multiDossiersPage.fillDateCritereRechercheStatistiqueFin(DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, new Date()));
            multiDossiersPage.selectEquipeRechercheStatistique(TestData.ACCOMPAGNEMENT);
            multiDossiersPage.clickBtnSearchCourrierGrid();
            if (multiDossiersPage.clickSearchResultCourrierIn(object, etat)) {
                logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
            } else {
                logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + numeroClient, e);
        }
    }

    static void doCreationCourrierSimple(String numeroClient, String object, String etat) {
        try {
            homeMenuPage.clickOngletMultiDossiers();
            multiDossiersPage.clickMenuCourrierEntrantGestion();
            multiDossiersPage.clickEncoderCourrier();
            multiDossiersPage.fillNumeroSearchPopUp(numeroClient);
            multiDossiersPage.clickBtnAjoutNumero();
            multiDossiersPage.selectObjet(object);
            multiDossiersPage.selectEtat(etat);
            multiDossiersPage.clickBtnEnregistrerPopUp();
            SeleniumUtils.waitForActionCommon();

            doSearchCourrier(numeroClient);
            multiDossiersPage.clickSearchResultCourrierIn(numeroClient);//peut encore vérifié que l'obj et que l'état correspond

            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + numeroClient, e);
        }
    }

    static void doCreationCourrierSimple(String numeroClient) {
        doCreationCourrierSimple(numeroClient, TestData.OBJET_COTI_COURRIER_AFFILIE, TestData.ETAT_EN_COURS);
    }

    static void doCreationCourrierMultiple() {
        try {
            homeMenuPage.clickOngletMultiDossiers();
            multiDossiersPage.clickMenuCourrierEntrantGestion();
            multiDossiersPage.clickEncoderCourriersMultiples();
            multiDossiersPage.fillNombreDossiersCourriersMultiples();
            multiDossiersPage.fillDateEntreeCourriersMultiples();
            multiDossiersPage.clickSelectObjetMultiple();
            multiDossiersPage.clickSelectUserDestination();
            multiDossiersPage.clickBtnEnregistrerMultiple();
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    static void doTraitementCourrier(String numeroClient) {
        try {
            doSearchCourrier(numeroClient);
            multiDossiersPage.clickTableBtnEditCourrier(numeroClient);
            multiDossiersPage.selectNewEtat(TestData.ETAT_TRAITE);
            multiDossiersPage.fillDateEcheance();
            multiDossiersPage.clickBtnEnregistrerPopUp();

            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + numeroClient, e);
        }
    }

    static void doDemandeRetraitDossier(String numeroClient, String typeRetrait) {
        try {
            homeMenuPage.clickOngletMultiDossiers();
            multiDossiersPage.clickMenuRetirerUnDossier();
            switch (typeRetrait) {
                case "Courrier":
                    retraitDossierCourrier(numeroClient);
                    break;
                case "Urgent":
                    retraitDossierUrgent(numeroClient);
                    break;
                case "Liste":
                    retraitDossierListe(numeroClient);
                    break;
                case "Urgent classeur":
                    retraitDossierUrgentClasseur(numeroClient);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + numeroClient, e);
        }
    }

    private static void retraitDossierUrgentClasseur(String numeroClient) {
        multiDossiersPage.clickOngletUrgentClasseurRetraitDossier();
        multiDossiersPage.fillNissorBCEUrgentClasseurRetraitDossier(numeroClient);
        multiDossiersPage.fillDestinataireUrgentClasseurRetraitDossier();
        multiDossiersPage.clickBtnEnregistrerUrgentClasseurRetraitDossier();
        if (multiDossiersPage.isUrgentClasseurRetraitDossier()) {
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } else {
            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    private static void retraitDossierListe(String numeroClient) {
        multiDossiersPage.clickOngletListeRetraitDossier();
        multiDossiersPage.fillTitreListeRetraitDossier();
        multiDossiersPage.fillDateListeRetraitDossier();
        multiDossiersPage.fillNissorBCEListeRetraitDossier(numeroClient);
        multiDossiersPage.clickBtnAddToListeRetraitDossier();
        multiDossiersPage.clickBtnEnregistrerListeRetraitDossier();
        if (multiDossiersPage.isListeRetraitDossier()) {
            checkRetraitDossier(numeroClient);
        } else {
            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    private static void retraitDossierUrgent(String numeroClient) {
        multiDossiersPage.clickOngletUrgentRetraitDossier();
        multiDossiersPage.fillNissorBCEUrgentRetraitDossier(numeroClient);
        multiDossiersPage.clickBtnEnregistrerUrgentRetraitDossier();
        if (multiDossiersPage.isUrgentRetraitDossier()) {
            checkRetraitDossier(numeroClient);
        } else {
            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    private static void retraitDossierCourrier(String numeroClient) {
        multiDossiersPage.clickOngletCourrierRetraitDossier();
        multiDossiersPage.fillNissorBCECourrierRetraitDossier(numeroClient);
        multiDossiersPage.clickBtnEnregistrerCourrierRetraitDossier();
        if (multiDossiersPage.isCourrierRetraitDossier()) {
            checkRetraitDossier(numeroClient);
        } else {
            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    private static void checkRetraitDossier(String numeroClient) {
        logInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), "");
        multiDossiersPage.clickMenuConsultationDossierDemandes();
        multiDossiersPage.fillNissorBCERetraitDossier(numeroClient);
        multiDossiersPage.clickBtnSearchCourrierGrid();
        if (multiDossiersPage.isDossierRentreOuRetrait(numeroClient, false)) {
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } else {
            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    static void doSortirRetraitDossierJournalier(String numeroClient) {
        try {
            homeMenuPage.clickOngletMultiDossiers();
            multiDossiersPage.clickMenuDossierATirer();
            multiDossiersPage.clickTableAccordionDossierJournalier();
            multiDossiersPage.clickbtnImprimerListeJournalierRetraitDossier();
            clickEnter();
            doCheckSortieDossier(numeroClient);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + numeroClient, e);
        }
    }

    private static void checkSortieDossier(String numeroClient) {
        if (multiDossiersPage.isDossierRentreOuRetrait(numeroClient, false)) {
            logInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), "");
            multiDossiersPage.clickTableRetraitDossierTable(numeroClient);
            multiDossiersPage.clickRentrerRetraitDossier();

            if (multiDossiersPage.isDossierRentreOuRetrait(numeroClient, true)) {
                logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
            } else {
                logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
            }
        } else {
            logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    static void doSortirRetraitDossierUrgent(String numeroClient) {
        try {
            homeMenuPage.clickOngletMultiDossiers();
            multiDossiersPage.clickMenuDossierATirer();
            multiDossiersPage.clickTableAccordionDossierUrgent();
            multiDossiersPage.clickbtnImprimerListeUrgentRetraitDossier();
            clickEnter();
            doCheckSortieDossier(numeroClient);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + numeroClient, e);
        }
    }

    static void doSortirRetraitDossierListe(String numeroClient) {
        try {
            homeMenuPage.clickOngletMultiDossiers();
            multiDossiersPage.clickMenuDossierATirer();
            multiDossiersPage.clickTableAccordionDossierListe();
            multiDossiersPage.traiterListeDossier();
            multiDossiersPage.clickbtnImprimerListeRetraitDossier();
            clickEnter();
            doCheckSortieDossier(numeroClient);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + numeroClient, e);
        }
    }

    static void doSortirRentrertDossierUrgentClasseur(String numeroClient) {
        try {
            homeMenuPage.clickOngletMultiDossiers();
            multiDossiersPage.clickMenuConsultationDossierSorties();
            multiDossiersPage.fillNissorBCERetraitDossier(numeroClient);
            multiDossiersPage.clickBtnSearchCourrierGrid();
            checkSortieDossier(numeroClient);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + numeroClient, e);
        }
    }

    static void doCreationCodeBarreGED(String numeroClient) {
        try {
            homeMenuPage.clickOngletMultiDossiers();
            multiDossiersPage.clickMenuGenerationCodeBarreGed();
            multiDossiersPage.selectDepartementCodeBarreGed("CAS");
            multiDossiersPage.selectTypeCodeBarreGed("Cotisant");
            multiDossiersPage.selectSousTypeCodeBarreGed("Affiliation");
            multiDossiersPage.selectDocumentCodeBarreGed("Déclaration d'affiliation");
            multiDossiersPage.selectSensCodeBarreGed("Entrant");
            multiDossiersPage.fillDateDocument();
            multiDossiersPage.fillNumeroClient(numeroClient);
            multiDossiersPage.clickBtnAjouterCodeBarre();

            if (multiDossiersPage.checkDemandeCodeBarre(SeleniumUtils.formatNissOrBceOrNumeroClient(numeroClient))) {
                logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
            } else {
                logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + numeroClient, e);
        }
    }

    static void doGenerationCodeBarreGED(String numeroClient) {
        try {
            homeMenuPage.clickOngletMultiDossiers();
            multiDossiersPage.clickMenuGenerationCodeBarreGed();
            multiDossiersPage.clickMenuActionGenererCodeBarreGED();
            if (!multiDossiersPage.checkDemandeCodeBarre(SeleniumUtils.formatNissOrBceOrNumeroClient(numeroClient))) {
                logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
            } else {
                logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + numeroClient, e);
        }
    }

    static void doReactiverCodeBarreGED(String numeroClient) {
        try {
            homeMenuPage.clickOngletMultiDossiers();
            multiDossiersPage.clickMenuGenerationCodeBarreGed();
            multiDossiersPage.clickMenuActionReactiverCodeBarreGED();
            if (multiDossiersPage.checkDemandeCodeBarre(SeleniumUtils.formatNissOrBceOrNumeroClient(numeroClient))) {
                logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
            } else {
                logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + numeroClient, e);
        }
    }

    static void doSuppressionCodeBarreGED() {
        try {
            homeMenuPage.clickOngletMultiDossiers();
            multiDossiersPage.clickMenuGenerationCodeBarreGed();
            multiDossiersPage.checkEtSuppressionCodeBarreGED();
            if (multiDossiersPage.checkTableDemandeCodeBarreVide()) {
                logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
            } else {
                logFailed(Thread.currentThread().getStackTrace()[1].getMethodName());
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    private static void doSearchCourrier(String numeroClient) {
        homeMenuPage.clickOngletMultiDossiers();
        multiDossiersPage.clickMenuCourrierEntrantGestion();
        multiDossiersPage.fillNumeroSearch(numeroClient);
        multiDossiersPage.fillDateCreation(DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()));
        multiDossiersPage.clickBtnSearchCourrierGrid();
    }

    private static void doCheckSortieDossier(String numeroClient) {
        multiDossiersPage.clickbtnListeImprimeRetraitDossier();
        multiDossiersPage.clickbtnSortirDossierRetraitDossier();
        homeMenuPage.clickOngletMultiDossiers();
        multiDossiersPage.clickMenuConsultationDossierSorties();
        multiDossiersPage.fillNissorBCERetraitDossier(numeroClient);
        multiDossiersPage.clickBtnSearchCourrierGrid();
        checkSortieDossier(numeroClient);
    }
}