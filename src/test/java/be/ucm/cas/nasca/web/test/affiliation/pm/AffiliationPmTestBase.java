package be.ucm.cas.nasca.web.test.affiliation.pm;

import be.ucm.cas.nasca.web.test.support.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.LinkedHashMap;
import java.util.Map;

public class AffiliationPmTestBase extends TestBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AFFI_PM;
    }

    static void fillFormPhase1(String bce, String affidate) {
        try {
            doBaseAffiliation(bce);
            if (affidate != null) {
                affiliationPmWizardPage.doFillDateReceptionCustom(affidate);
            } else {
                affiliationPmWizardPage.doFillDateReceptionAuto();
            }

            if (affidate != null) {
                affiliationPmWizardPage.doFillDateSignatureCustom(affidate);
            } else {
                affiliationPmWizardPage.doFillDateSignatureAuto();
            }
            logInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), "avant terminer");
            nascaPage.clickBtnTerminerWizardWithWaitActionLong();
            SeleniumUtils.waitForActionLong();
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + bce);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + bce, e);
        }
    }

    static void fillFormPhase1(String bce) {
        try {
            homeMenuPage.clickOngletTaches();
            affiliationPmWizardPage.doOpenAffiliationPM();
            affiliationPmWizardPage.doSearchBCE(bce);

            affiliationPmWizardPage.doFillDateSignatureAuto();
            logInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), "avant terminer");

            nascaPage.clickBtnTerminerWizard();
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    private static void doBaseAffiliation(String bce) {
        homeMenuPage.clickOngletTaches();
        tachePage.clickBtnResetTache();
        affiliationPmWizardPage.doOpenAffiliationPM();
        affiliationPmWizardPage.doSearchBCE(bce);
    }

    static void sendSoap(String inastianswer, String bce) {
        Map<String, String> map = new LinkedHashMap<>();

        map.put("ENVIRONNEMENT", ((TestData) ApplicationContext.getAppCtx().getBean("testData")).getPropsRootUrlFuzion());

        ReplaceContentInTestCaseUtils.replace(TestData.SOAP_UI_PROJECT_PM_AFFILIATION, TestData.TEMP_FILE + "NASCA - SOAP_XML - PM - AFFILIATION.xml", map);

        TestBase.sendSoap(TestData.TEMP_FILE + "NASCA - SOAP_XML - PM - AFFILIATION.xml", inastianswer, bce);
    }

    static void fillFormPhase2AcceptPart1(String bce, String nacecode) {
        try {
            searchTacheAndFillPart1(bce, nacecode);
            affiliationPmWizardPage.clickBtnReclamerAttestAssujetiTVA();
            nascaPage.clickBtnSuivantWizard();
            fillApporteurAffaires();
            nascaPage.clickBtnSuivantWizard();
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + bce, e);
        }
    }

    private static void searchTacheAndFillPart1(String bce, String nacecode) {
        searchTacheAndTraiter(bce);
        affiliationPmWizardPage.fillDateDepot(DateUtils.getDateToday());
        affiliationPmWizardPage.fillDateConstitution(DateUtils.getDateToday());
        fillCodeNace(nacecode);
    }


    static void fillFormPhase2RefusPart1(String bce, String nacecode) {
        try {
            searchTacheAndFillPart1(bce, nacecode);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    static void requestReclamationElementManquant() {
        try {
            affiliationPmWizardPage.clickBtnReclamationElementManquant();
            affiliationPmWizardPage.clickBtnLettreReclamation();
            nascaPage.clickBtnSuivantWizard();
            affiliationPmWizardPage.clickBtnModifElementsManquants();
            remWizardPage.clickREMSelectElementManquant("Procuration");
            affiliationPmWizardPage.clickBtnSelectImpressionLocal();
            affiliationPmWizardPage.clickBtnEnregistrer();
            nascaPage.clickBtnSuivantWizard();
            affiliationPmWizardPage.clickBtnPreviewpdf();
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "REM document");
            affiliationPmWizardPage.clickBtnClosePreviewPdf();
            affiliationPmWizardPage.clickBtnRetourProcessusPrincipal();
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "REM");
            nascaPage.clickBtnTerminerWizard();
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    static void fillFormPhase2AcceptPart2(String fonction) {
        try {
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "");

            nascaPage.clickBtnSuivantWizard();
            fillApporteurAffaires();
            nascaPage.clickBtnSuivantWizard();
            nascaPage.clickBtnSuivantWizard();
            nascaPage.clickBtnSuivantWizard();
            fillMandataire(fonction);
            nascaPage.clickBtnTerminerWizard();

            if (nascaPage.isMessageErreur("stacktrace")) {
                logFailed(TestData.ERREUR_NASCA, "");
            } else {
                logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    private static void fillMandataire(String fonction) {
        affiliationPmWizardPage.clickBtnAddMandataire();
        affiliationPmWizardPage.selectTypeActivite();
        affiliationPmWizardPage.selectFonctionActivite(fonction);
        affiliationPmWizardPage.doFillDatedebutActiviteAuto();
        affiliationPmWizardPage.doFillIdentiteLiee();
        affiliationPmWizardPage.clickBtnEnregistrer();
        SeleniumUtils.fermerNotification();
    }

    static void fillFormPhase2AcceptPart2AfterApporteur(String fonction) {
        try {
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName(), "");

            nascaPage.clickBtnSuivantWizard();
            nascaPage.clickBtnSuivantWizard();
            fillMandataire(fonction);
            nascaPage.clickBtnTerminerWizard();
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    static void fillFormPhase2AcceptPart1(String bce, String nacecode, String dateDepot, String dateConstitution, boolean exo) {
        try {
            searchTacheAndTraiter(bce);

            if (dateDepot == null && dateConstitution == null) {
                affiliationPmWizardPage.fillDateDepot(DateUtils.getDateToday());
                affiliationPmWizardPage.fillDateConstitution(DateUtils.getDateToday());
            } else {
                affiliationPmWizardPage.fillDateDepot(dateDepot);
                affiliationPmWizardPage.fillDateConstitution(dateConstitution);
            }

            if (exo) {
                affiliationPmWizardPage.clickRadioDemandeExo();
            }

            fillCodeNace(nacecode);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    private static void searchTacheAndTraiter(String bce) {
        homeMenuPage.clickOngletTaches();
        tachePage.clickBtnResetTache();

        tachePage.fillNumero(bce);
        tachePage.clickBtnDeleteFiltreTache();
        tachePage.clickBtnSearchTache();
        logInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), "avant tache");
        tachePage.clickTraiterTache(bce);
        logInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), "réception décision");
    }

    static void fillFormPhase2(String bce, String nacecode, String fonction) {
        try {
            searchTacheAndTraiter(bce);

            fillCodeNace(nacecode);
            if (!TestData.GERANT.equals(fonction)) {
                affiliationPmWizardPage.clickBtnReclamerAttestAssujetiTVA();
            }
            nascaPage.clickBtnSuivantWizard();
            fillApporteurAffaires();
            nascaPage.clickBtnSuivantWizard();

            nascaPage.clickBtnSuivantWizard();
            affiliationPmWizardPage.doSelectSiegeSocial();
            nascaPage.clickBtnSuivantWizard();
            nascaPage.clickBtnTerminerWizard();

            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + bce);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + bce, e);
        }
    }

    static void fillFormPhase2Accept(String bce, String nacecode, String fonction, String niss) {
        try {
            searchTacheAndTraiter(bce);

            affiliationPmWizardPage.fillDateConstitution(DateUtils.getDateToday());
            affiliationPmWizardPage.fillDateDepot(DateUtils.getDateToday());
            fillCodeNace(nacecode);
            affiliationPmWizardPage.clickBtnReclamerAttestAssujetiTVA();
            nascaPage.clickBtnSuivantWizard();
            fillApporteurAffaires();
            nascaPage.clickBtnSuivantWizard();

            nascaPage.clickBtnSuivantWizard();
            affiliationPmWizardPage.doSelectSiegeSocial();
            nascaPage.clickBtnSuivantWizard();
            affiliationPmWizardPage.clickBtnAddMandataire();
            affiliationPmWizardPage.selectTypeActivite();
            affiliationPmWizardPage.selectFonctionActivite(fonction);
            affiliationPmWizardPage.doFillDatedebutActiviteAuto();
            affiliationPmWizardPage.doFillIdentiteLiee(niss);
            affiliationPmWizardPage.clickResultSearchIdentiteLiee();
            affiliationPmWizardPage.clickBtnEnregistrer();
            SeleniumUtils.fermerNotification();
            nascaPage.clickBtnTerminerWizard();

            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + bce);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + bce, e);
        }
    }

    static void clientCheck(String bce, String decision) {
        try {
            String logSuccessString;
            String logFailedString;

            loadNissOrBce(bce);

            if (TestData.ACCEPTE.equals(decision)) {
                logSuccessString = "Affiliation PM - Personne morale " + bce + " 'est affiliée";
                logFailedString = "Affiliation PM - Personne morale " + bce + " n'est pas affiliée";
            } else {
                logSuccessString = "Affiliation PM - Personne morale " + bce + " n'est affiliée pas";
                logFailedString = "Affiliation PM - Personne morale " + bce + " est affiliée";
            }

            signaletiquePage.clickBtnOperationAdministrative();
            signaletiquePage.clickRadioFiltreTout();
            signaletiquePage.doFillFiltreTypeDemande(TestData.DECLARATION_AFFI_PM);
            signaletiquePage.clickBtnSearch();

            checkDecision(decision, logSuccessString, logFailedString);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + bce, e);
        }
    }

    private static void checkDecision(String decision, String logSuccessString, String logFailedString) {
        try {
            Assert.assertTrue(signaletiquePage.isDecisionEquals(decision));
            logSuccess(logSuccessString);
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logFailed(logFailedString);
        }
    }

    static void checkIfDocExist(String bce, String nomDoc) {
        homeMenuPage.clickOngletMultiDossiers();

        multiDossiersPage.selectTypeNumero("BCE");
        multiDossiersPage.fillNissorBCE(bce);
        multiDossiersPage.clickBtnRechercherDocument();

        if (multiDossiersPage.isExistOuPreviewDocument(nomDoc, false)) {
            logSuccess(nomDoc + " pour " + bce + " créé.");
        } else {
            logFailed(nomDoc + " pour " + bce + " non créé.");
        }
    }

    private static void fillCodeNace(String nacecode) {
        affiliationPmWizardPage.clickBtnOpenSearchNACE();
        affiliationPmWizardPage.doFillcodeNACE(nacecode);
        affiliationPmWizardPage.clickBtnsearchNACE();
        affiliationPmWizardPage.clickSearchResultNace(nacecode);
        affiliationPmWizardPage.clickBtnEnregistrer();
    }

    private static void fillApporteurAffaires() {
        affiliationPmWizardPage.doFillNom("ROBERT");
        affiliationPmWizardPage.clickBtnsearchApporteur();
        affiliationPmWizardPage.clickSearchResultApporteurAffaires("ROBERT");
    }

    static void traitementExoneration(String bce, String exo, String reponse) {
        try {
            homeMenuPage.clickOngletTaches();
            tachePage.clickBtnDeleteFiltreTache();
            tachePage.clickBtnResetTache();

            logInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), "avant tache");
            tachePage.clickTraiterTache(bce);

            affiliationPmWizardPage.doFilLDateSignatureExo(DateUtils.getDateToday());

            switch (exo) {
                case "accepté":
                    affiliationAccepteeExo();
                    break;
                case "refusé":
                    affiliationRefuseeExo(reponse);
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    private static void affiliationAccepteeExo() {
        setChoixSocietePersonne(TestData.EXO_ACCEPT);
        setChoixImmatriculeeBce(TestData.EXO_ACCEPT);
        setChoixMandataireEtAssocActiv(TestData.EXO_ACCEPT);
        SeleniumUtils.waitForActionCommon();
        nascaPage.clickBtnTerminerWizard();
    }

    private static void affiliationRefuseeExo(String reponse) {
        switch (reponse) {
            case "pasSC":
                setChoixImmatriculeeBce(TestData.EXO_REJET);
                setChoixMandataireEtAssocActiv(TestData.EXO_ACCEPT);
                setChoixSocietePersonne(TestData.EXO_ACCEPT);
                break;
            case "pasSdP":
                setChoixSocietePersonne(TestData.EXO_REJET);
                setChoixImmatriculeeBce(TestData.EXO_ACCEPT);
                setChoixMandataireEtAssocActiv(TestData.EXO_ACCEPT);
                break;
            default:
                break;
        }
        SeleniumUtils.waitForActionCommon();
        nascaPage.clickBtnTerminerWizard();
    }

    private static void setChoixSocietePersonne(String choix) {
        choixSocietePersonne(choix, "0");
        choixSocietePersonne(choix, "1");
        choixSocietePersonne(choix, "2");
    }

    private static void setChoixImmatriculeeBce(String choix) {
        choixImmatriculeeBce(choix, "0");
        choixImmatriculeeBce(choix, "1");
        choixImmatriculeeBce(choix, "2");
    }

    private static void setChoixMandataireEtAssocActiv(String choix) {
        choixMandataireEtAssocActiv(choix, "0");
        choixMandataireEtAssocActiv(choix, "1");
        choixMandataireEtAssocActiv(choix, "2");
    }

    private static void choixSocietePersonne(String choix, String annee) {
        String element = "decisionGestionnaireForm_decisions_" + annee + "__societeDePersonnes";
        ActionUtils.doChangeStylenFill(element, choix);
    }

    private static void choixImmatriculeeBce(String choix, String annee) {
        String element = "decisionGestionnaireForm_decisions_" + annee + "__immatriculeeBce";
        ActionUtils.doChangeStylenFill(element, choix);
    }

    private static void choixMandataireEtAssocActiv(String choix, String annee) {
        String element = "decisionGestionnaireForm_decisions_" + annee + "__mandatairesEtAssociesActifs";
        ActionUtils.doChangeStylenFill(element, choix);
    }
}