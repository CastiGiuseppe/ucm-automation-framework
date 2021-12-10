package be.ucm.cas.nasca.web.test.affiliation.pp;

import be.ucm.cas.nasca.web.pagefactory.affiliation.AffiliationPpWizardPage;
import be.ucm.cas.nasca.web.test.support.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

public class AffiliationPpTestBase extends TestBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AFFI_PP;
    }

    static void fillFormPhase1(String niss, String nature, String dpe, String dsg, String typeActivite1, String activite1,
                               String dtDebutActivite1, String typeActivite2, String activite2, String dtDebutActivite2) {
        try {
            homeMenuPage.clickOngletTaches();

            affiliationPpWizardPage.doOpenAffiliationPP();
            affiliationPpWizardPage.doSearchNISS(niss);

            nascaPage.clickBtnSuivantWizard();

            doFillTypeActivity(typeActivite1, activite1, dtDebutActivite1);

            if (typeActivite2 != null && !StringUtils.isEmpty(typeActivite2.trim())) {
                affiliationPpWizardPage.clickBtnAddActivity();
                doFillTypeActivity(typeActivite2, activite2, dtDebutActivite2);
            }

            fillEndPhase1(nature, dpe, dsg);

            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void fillFormPhase1(String niss, String nature, String dpe, String dsg) {
        try {
            homeMenuPage.clickOngletTaches();

            affiliationPpWizardPage.doOpenAffiliationPP();
            affiliationPpWizardPage.doSearchNISS(niss);
            affiliationPpWizardPage.checkPersonneDejaAffilie();

            nascaPage.clickBtnSuivantWizard();

            doFillTypeActivity(null, null, null);

            fillEndPhase1(nature, dpe, dsg);

            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void createIdentity(String niss) {
        homeMenuPage.clickOngletTaches();

        affiliationPpWizardPage.doOpenAffiliationPP();
        affiliationPpWizardPage.doSearchNISS(niss);

        nascaPage.clickBtnAnnulerEtOuiWizard();
    }

    public static void fillFormPhase1(String niss) {
        try {
            homeMenuPage.clickOngletTaches();

            affiliationPpWizardPage.doOpenAffiliationPP();
            affiliationPpWizardPage.doSearchNISS(niss);

            nascaPage.clickBtnSuivantWizard();

            doFillTypeActivity(null, null, null);

            fillEndPhase1(null, null, null);

            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void fillFormPhase1Rem(String niss) {
        try {
            homeMenuPage.clickOngletTaches();

            affiliationPpWizardPage.doOpenAffiliationPP();
            affiliationPpWizardPage.doSearchNISS(niss);

            doFillREM("Signature");

            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void fillFormPhase1Multi(String niss, String nature, String dpe, String dsg, String suite, String typeActivite1, String activite1,
                                    String dtDebutActivite1, String typeActivite2, String activite2, String dtDebutActivite2) {
        homeMenuPage.clickOngletTaches();

        affiliationPpWizardPage.doOpenAffiliationPP();
        affiliationPpWizardPage.doSearchNISS(niss);

        nascaPage.clickBtnSuivantWizard();

        doFillTypeActivity(typeActivite1, activite1, dtDebutActivite1);

        if ("cessation".equals(suite)) {
            affiliationPpWizardPage.doFillDateCessationAuto();
        } else {
            if (typeActivite2 != null) {
                affiliationPpWizardPage.clickBtnAddActivity();
                doFillTypeActivity(typeActivite2, activite2, dtDebutActivite2);
            }
        }

        fillEndPhase1(nature, dpe, dsg);
    }

    static void doSoapAndRetry(String inastiAnswer, String niss) {
        sendSoap(inastiAnswer, niss);
        SeleniumUtils.waitForActionLong();
        SeleniumUtils.isLoading();

        if (affiliationPpWizardPage.isSoapFallr()) {
            affiliationPpWizardPage.clickBtnOkFallr();

            tachePage.fillNumero(niss);
            tachePage.clickBtnDeleteFiltreTache();
            tachePage.clickBtnSearchTache();
            retrySoap(inastiAnswer, niss);
            tachePage.clickTraiterTache(niss);
        }
    }

    private static void retrySoap(String inastiAnswer, String niss) {
        if (!tachePage.isTacheExist(niss)) {
            sendSoap(inastiAnswer, niss);

            tachePage.fillNumero(niss);
            tachePage.clickBtnDeleteFiltreTache();
            tachePage.clickBtnSearchTache();
        }
    }

    static void fillFormPhase2Accept(String chgtProfil, String dateChgt, String nature, String suite, String revenu) {
        try {
            SeleniumUtils.isLoading();

            Assert.assertTrue(AffiliationPpWizardPage.getResponseAccept(), "Affiliation PP - Debut Phase 2 - Accepte Ok");
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());

            affiliationPpWizardPage.clickBtnContinueAffiliation();
            nascaPage.clickBtnTerminerWizard();
            nascaPage.clickBtnSuivantWizard();

            // Ecran apporteur d'affaire
            affiliationPpWizardPage.dofillNom("ROBERT");
            affiliationPpWizardPage.clickBtnSearch();
            affiliationPpWizardPage.clickSearchResult();
            affiliationPpWizardPage.dofillRedacteur("KEVIN");
            nascaPage.clickBtnSuivantWizard();

            // Ecran profil
            checkIfExoRed(nature);

            if ("multi".equals(suite)) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.MONTH, Calendar.JANUARY);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                cal.add(Calendar.YEAR, 1);
                checkChghtProfil("complémentaire", DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, cal.getTime()));
                cal.add(Calendar.YEAR, 1);
                checkChghtProfil("principal", DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, cal.getTime()));
                cal.add(Calendar.YEAR, 1);
                checkChghtProfil("complémentaire", DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, cal.getTime()));
            } else {
                checkChghtProfil(chgtProfil, dateChgt);
            }

            nascaPage.clickBtnSuivantWizard();

            // Ecran revenu
            doAddRevenu(revenu);

            // Ecran gestion Revenus
            if (affiliationPpWizardPage.checkIfGestionRevenus()) {
                nascaPage.clickBtnSuivantWizard();
            }

            // Ecran adresse et telephone
            nascaPage.clickBtnSuivantWizard();

            // Ecran Donnée famillial
            if ("accepté".equals(suite) || "rem".equals(suite) || "EA".equals(suite)) {
                affiliationPpWizardPage.doFillChoicefamilialPP();
            }
            nascaPage.clickBtnSuivantWizard();

            if ("H0".equals(nature) || "X0".equals(nature) || "E0".equals(nature)) {
                affiliationPpWizardPage.clickBtnSimulationCalcul();

                if (affiliationPpWizardPage.isAnyTaxation()) {
                    logFailed("Taxation présente");
                }
            }

            affiliationPpWizardPage.clickBtnToTaches();
            nascaPage.clickBtnTerminerWizard();
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
        SeleniumUtils.waitForActionLong();
    }

    public static void fillFormPhase2Accept() {
        try {
            SeleniumUtils.isLoading();

            Assert.assertTrue(AffiliationPpWizardPage.getResponseAccept(), "Affiliation PP - Debut Phase 2 - Accepte Ok");
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());

            affiliationPpWizardPage.clickBtnContinueAffiliation();
            nascaPage.clickBtnTerminerWizard();
            nascaPage.clickBtnSuivantWizard();

            // Ecran apporteur d'affaire
            affiliationPpWizardPage.dofillNom("ROBERT");
            affiliationPpWizardPage.dofillRedacteur("KEVIN");
            affiliationPpWizardPage.clickBtnSearch();
            affiliationPpWizardPage.clickSearchResult();
            nascaPage.clickBtnSuivantWizard();

            // Ecran profil
            nascaPage.clickBtnSuivantWizard();

            // Ecran revenu
            nascaPage.clickBtnSuivantWizard();

            // Ecran adresse et telephone
            nascaPage.clickBtnSuivantWizard();

            // Ecran Donnée famillial
            affiliationPpWizardPage.doFillChoicefamilialPP();
            nascaPage.clickBtnSuivantWizard();

            affiliationPpWizardPage.clickBtnToTaches();
            nascaPage.clickBtnTerminerWizard();
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
        SeleniumUtils.waitForActionLong();
    }

    static void fillFormPhase2Refuse() {
        try {
            SeleniumUtils.waitForActionCommon();
            Assert.assertTrue(AffiliationPpWizardPage.getResponseRefuse(), "Affiliation PP - Debut Phase 2 - Refuse OK");
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
            affiliationPpWizardPage.clickBtnOuiCourrierEnvoi();

            affiliationPpWizardPage.clickBtnToTaches();
            nascaPage.clickBtnTerminerWizard();
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    static void fillFormPhase2Suspend(String inastianswer, String niss) {
        try {
            SeleniumUtils.waitForActionLong();
            Assert.assertTrue(AffiliationPpWizardPage.getResponseSuspend(), "Affiliation PP - Debut Phase 2 - En enquête OK");
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());

            affiliationPpWizardPage.clickBtnOuiCourrierEnvoi();
            affiliationPpWizardPage.clickBtnToTaches();
            nascaPage.clickBtnTerminerWizard();
            sendSoap(inastianswer, niss);
            SeleniumUtils.waitForAction(15000);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void fillFormPhase2AfterSuspendAccept(String niss, String chgtProfil, String dateChangement,
                                                 String nature, String suite, String revenu) {
        try {
            homeMenuPage.clickOngletTaches();
            tachePage.fillNumero(niss);
            tachePage.clickBtnDeleteFiltreTache();
            tachePage.clickBtnSearchTache();
            tachePage.clickTraiterTache(niss);
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
            fillFormPhase2Accept(chgtProfil, dateChangement, nature, suite, revenu);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void fillFormPhase2AfterSuspendRefuse(String niss) {
        try {
            homeMenuPage.clickOngletTaches();
            tachePage.fillNumero(niss);
            tachePage.clickBtnDeleteFiltreTache();
            tachePage.clickBtnSearchTache();
            tachePage.clickTraiterTache(niss);
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
            fillFormPhase2Refuse();
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void fillFormDepistageCjtNoRem(String niss, String type) {
        try {
            homeMenuPage.doSearch(niss);
            gestionClientPage.clickMenuProfilAffiliation();
            gestionClientPage.clickBtnLeverDepistage();
            gestionClientPage.doFillReponseCjt(type);
            nascaPage.clickBtnTerminerWizard();
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void fillFormDepistageCjtRem(String niss) {
        try {
            homeMenuPage.doSearch(niss);
            gestionClientPage.clickMenuREM();
            gestionClientPage.clickBtnEditRemDepistage();
            gestionClientPage.clickCheckBoxRecuGlobal();
            gestionClientPage.clickBtnEnregistrer();
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void fillReponseDepistage(String niss, String type) {
        try {
            homeMenuPage.clickOngletTaches();
            tachePage.clickBtnResetTache();
            tachePage.clickBtnDeleteFiltreTache();
            tachePage.fillNumero(niss);
            tachePage.clickBtnSearchTache();
            tachePage.clickTraiterTache(niss);
            gestionClientPage.doFillReponseCjt(type);
            nascaPage.clickBtnTerminerWizard();
            logSuccess(Thread.currentThread().getStackTrace()[1].getMethodName());
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void doFillREM(String elementManquant) {
        /*
         * Opening REM wizard
         */
        remWizardPage.clickBtnREM();
        logInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), "");

        /*
         * Step 1. Sélection
         */
        remWizardPage.clickBtnLettreReclamationPP();
        nascaPage.clickBtnSuivantWizard();

        /*
         * Step 2. Destinataires & éléments
         */
        remWizardPage.clickBtnmodifElementsManquants();
        remWizardPage.clickREMSelectElementManquant(elementManquant);
        remWizardPage.clickBtnSelectImpressionLocal();
        remWizardPage.clickBtnenregistrerWizard();
        nascaPage.clickBtnSuivantWizard();

        /*
         * Step 3. Calendrier & visualisation
         */
        remWizardPage.clickBtnRetourAccueil();
        nascaPage.clickBtnTerminerWizard();
    }

    private static void fillEndPhase1(String nature, String dpe, String dsg) {
        fillDateDebut(dpe);
        fillDateSignature(dsg);
        chooseNatureCotisante(nature);

        nascaPage.clickBtnSuivantWizard();
        affiliationPpWizardPage.selectSituationConjoint();
        nascaPage.clickBtnSuivantWizard();

        affiliationPpWizardPage.clickBtnSoumissionInasti();

        nascaPage.clickBtnTerminerWizardWithWaitActionLong();
    }

    private static void doFillTypeActivity(String typeActivite, String activite, String dtDebut) {
        if (typeActivite == null || TestData.TYPE_PP.equals(typeActivite) || " ".equals(typeActivite)) {
            affiliationPpWizardPage.selectTypeActivite("Entreprise PP");
            doFillActivity(activite);
            doFillDtDebutActivite(dtDebut);
            affiliationPpWizardPage.clickBtnSaveProfesssion();
            affiliationPpWizardPage.clickBtnSaveAddActivity();
        } else {
            switch (typeActivite) {
                case "mandataire":
                    ajoutActiviteMandataire(typeActivite, activite, dtDebut);
                    break;
                case "aidant":
                    ajoutActiviteAidant(typeActivite, activite, dtDebut);
                    break;
                case "gérant":
                    ajoutActiviteGerant(typeActivite, dtDebut);
                    break;
                default:
                    break;
            }
        }
    }

    private static void ajoutActiviteGerant(String typeActivite, String dtDebut) {
        affiliationPpWizardPage.selectTypeActivite("mandataire");
        affiliationPpWizardPage.clickSelectFonction(typeActivite);
        affiliationPpWizardPage.doFillDateDebutActiviteCustom(dtDebut);
        affiliationPpWizardPage.doFillDenomination("RO");
        affiliationPpWizardPage.clickResultSociete();
        affiliationPpWizardPage.selectNaceProfession("RO");
        affiliationPpWizardPage.clickBtnSaveAddActivity();
    }

    private static void ajoutActiviteAidant(String typeActivite, String activite, String dtDebut) {
        affiliationPpWizardPage.selectTypeActivite(typeActivite);
        affiliationPpWizardPage.doFillDateDebutActiviteCustom(dtDebut);
        affiliationPpWizardPage.doFillIdentiteLiee();
        affiliationPpWizardPage.selectNaceProfession(activite);
        affiliationPpWizardPage.clickBtnSaveAddActivity();
    }

    private static void ajoutActiviteMandataire(String typeActivite, String activite, String dtDebut) {
        affiliationPpWizardPage.selectTypeActivite(typeActivite);
        affiliationPpWizardPage.clickSelectFonction("Admin");
        affiliationPpWizardPage.doFillDateDebutActiviteCustom(dtDebut);
        affiliationPpWizardPage.doFillDenomination(activite);
        affiliationPpWizardPage.clickResultSociete();
        affiliationPpWizardPage.selectNaceProfession(activite);
        affiliationPpWizardPage.clickBtnSaveAddActivity();
    }

    private static void doFillActivity(String activite) {
        if (activite == null || " ".equals(activite)) {
            affiliationPpWizardPage.doAddDetailActivity();
            affiliationPpWizardPage.selectProfession("101101 - Maraîchers > Maraîchers > Maraîcher et horticulteur");
        } else {
            affiliationPpWizardPage.doAddDetailActivity();
            affiliationPpWizardPage.selectProfession(activite);
        }
    }

    private static void doFillDtDebutActivite(String dtDebut) {
        if (dtDebut == null || " ".equals(dtDebut)) {
            affiliationPpWizardPage.doFillDateDebutProfessionAuto();
        } else {
            affiliationPpWizardPage.doFillDateDebutProfessionCustom(dtDebut);
        }
    }

    private static void fillDateDebut(String dateDebut) {
        if (dateDebut == null) {
            affiliationPpWizardPage.doFillDateDebutRepriseAuto();
        } else {
            affiliationPpWizardPage.doFillDateDebutRepriseCustom(dateDebut);
        }
    }

    private static void fillDateSignature(String dateSign) {
        if (dateSign == null) {
            affiliationPpWizardPage.doFillDateSignatureAuto();
        } else {
            affiliationPpWizardPage.doFillDateSignatureCustom(dateSign);
        }
    }

    private static void chooseNatureCotisante(String nature) {
        if (nature == null) {
            affiliationPpWizardPage.selectNatureCotisante("Activité principale");
        } else {
            switch (nature) {
                case "A0":
                case "A9":
                case "H0":
                case "H9":
                    affiliationPpWizardPage.selectNatureCotisante("Activité principale");
                    break;
                case "D0":
                case "D9":
                case "D2":
                    affiliationPpWizardPage.selectNatureCotisante("Activité complémentaire");
                    break;
                case "E0":
                case "E9":
                    affiliationPpWizardPage.selectNatureCotisante("art. 40");
                    break;
                case "L0":
                case "L9":
                case "X0":
                    affiliationPpWizardPage.selectNatureCotisante("Conjoint aidant - Maxi statut");
                    break;
                case "Q1":
                    affiliationPpWizardPage.selectNatureCotisante("Conjoint aidant - Mini statut");
                    break;
                default:
                    break;
            }
        }

        affiliationPpWizardPage.selectQualite("Travailleur indépendant");
    }

    private static void checkChghtProfil(String chgtProfil, String dateChgt) {
        if (chgtProfil != null) {
            affiliationPpWizardPage.clickBtnAjoutChgtProfil();
            affiliationPpWizardPage.doFillDateEventProfilCustom(dateChgt);
            affiliationPpWizardPage.doAddChangementProfil("Changement de profil");
            affiliationPpWizardPage.doFillNatureProfil(chgtProfil);
            affiliationPpWizardPage.clickBtnSauvergardeChgtprofil();
        }
    }

    private static void checkIfExoRed(String nature) {
        if (nature != null) {
            switch (nature) {
                case "H0":
                case "D2":
                case "X0":
                    addExoRed("exo", nature.charAt(0));
                    break;
                case "E9":
                case "H9":
                    addExoRed("red", nature.charAt(0));
                    break;
                default:
                    break;
            }
        }
    }

    private static void addExoRed(String type, char infoNature) {
        affiliationPpWizardPage.clickBtnEditProfil();
        affiliationPpWizardPage.chooseExoRed(type);

        switch (infoNature) {
            case 'E':
                affiliationPpWizardPage.doFillJustification("ind");
                break;
            case 'L':
            case 'X':
            case 'Q':
            case 'H':
                affiliationPpWizardPage.doFillJustification("affi");
                break;
            default:
                break;
        }

        affiliationPpWizardPage.clickBtnSauvergardeChgtprofil();
    }

    private static void doAddRevenu(String revenu) {
        if (revenu != null && !StringUtils.isEmpty(revenu.trim())) {
            switch (revenu) {
                case "mixte":
                    ajoutRevenuMixte();
                    break;
                case "fiscal":
                    ajoutRevenuFiscal();
                    break;
                case "présumé":
                    ajoutRevenuPresume();
                    break;
                default:
                    break;
            }
        } else {
            nascaPage.clickBtnSuivantWizard();
        }
    }

    private static void ajoutRevenuPresume() {
        affiliationPpWizardPage.clickBtnRevenuPresume();
        affiliationPpWizardPage.doFillMontantRevenu("25000");
        nascaPage.clickBtnSuivantWizard();
    }

    private static void ajoutRevenuFiscal() {
        affiliationPpWizardPage.clickBtnGestionRevenu();
        nascaPage.clickBtnSuivantWizard();
        modifRevenuFiscal();
    }

    private static void ajoutRevenuMixte() {
        ajoutRevenuFiscal();
        ajouterRevenu(DateUtils.getPlusYears(1).substring(6, 10), "12000", "présumé", "payé", "A régulariser", "Affilié");
    }

    private static void modifRevenuFiscal() {
        affiliationPpWizardPage.clickBtnModifRevenu();
        affiliationPpWizardPage.doFillDateCommRevenu();
        affiliationPpWizardPage.doFillRevenu("12000");
        SeleniumUtils.isLoading();
    }

    private static void ajouterRevenu(String annee, String revenu, String type, String origine, String statut, String source) {
        affiliationPpWizardPage.clickBtnAjouterRevenu();
        affiliationPpWizardPage.doFillAnnee(annee);
        affiliationPpWizardPage.doFillDateCommRevenu();
        affiliationPpWizardPage.doFillDateDistributionRevenu();
        affiliationPpWizardPage.doFillType(type);
        affiliationPpWizardPage.clickRadioOrigine(origine);
        affiliationPpWizardPage.doFillStatut(statut);
        affiliationPpWizardPage.doFillSource(source);
        affiliationPpWizardPage.doFillRevenu(revenu);
    }

    public static void sendSoap(String inastianswer, String niss) {
        Map<String, String> map = new LinkedHashMap<>();

        map.put("ENVIRONNEMENT", ((TestData) ApplicationContext.getAppCtx().getBean("testData")).getPropsRootUrlFuzion());

        ReplaceContentInTestCaseUtils.replace(TestData.SOAP_UI_PROJECT_PP_AFFILIATION, TestData.TEMP_FILE + "NASCA - SOAP_XML - PP - AFFILIATION.xml", map);

        TestBase.sendSoap(TestData.TEMP_FILE + "NASCA - SOAP_XML - PP - AFFILIATION.xml", inastianswer, niss);

        // TODO activer
        //DaoFunctionality.getNascaDao().updateOpenErpSyncStatusToSentByNiss(niss);
        SeleniumUtils.waitForActionCommon();
    }

    static void checkIfClient(String niss, String decision) {
        try {
            loadNissOrBce(niss);

            signaletiquePage.clickBtnOperationAdministrative();
            signaletiquePage.clickRadioFiltreTout();
            signaletiquePage.doFillFiltreTypeDemande(TestData.DECLARATION_AFFI_PP);
            signaletiquePage.clickBtnSearch();

            checkDecision(niss, decision);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void checkIfConjointAidant(String niss, String decision) {
        try {
            loadNissOrBce(niss);

            signaletiquePage.clickBtnOperationAdministrative();
            signaletiquePage.clickRadioFiltreTout();
            signaletiquePage.doFillFiltreTypeDemande("DEPISTAGE");
            signaletiquePage.clickBtnSearch();

            checkDecision(niss, decision);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void checkIfClientEnquete(String niss, String decision) {
        try {
            loadNissOrBce(niss);

            signaletiquePage.clickBtnOperationAdministrative();
            signaletiquePage.clickRadioFiltreTout();
            signaletiquePage.doFillFiltreTypeDemande(TestData.DECLARATION_AFFI_PP);
            signaletiquePage.clickBtnSearch();

            checkDecision(niss, TestData.DIFFERE);
            checkDecision(niss, decision);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static void checkIfDocExist(String niss, String nomDoc) {
        homeMenuPage.clickOngletMultiDossiers();

        multiDossiersPage.selectTypeNumero("NISS");
        multiDossiersPage.fillNissorBCE(niss);
        multiDossiersPage.clickBtnRechercherDocument();

        try {
            Assert.assertTrue(multiDossiersPage.isExistOuPreviewDocument(nomDoc, false));
            logSuccess(nomDoc + " pour " + niss + " créé.");
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logFailed(nomDoc + " pour " + niss + " non créé.");
        }
    }

    private static void checkDecision(String niss, String decision) {
        try {
            Assert.assertTrue(signaletiquePage.isDecisionEquals(decision));
            logSuccess("Affiliation PP - Personne physique " + niss + " " + decision + " OK");
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logFailed("Affiliation PP - Personne physique " + niss + " " + decision + " KO");
        }
    }
}