package be.ucm.cas.nasca.web.test.leveemajorations;

import be.ucm.cas.nasca.web.test.comptabilite.ComptabiliteTestBase;
import be.ucm.cas.nasca.web.test.enrolement.pm.EnrolementPMTestBase;
import be.ucm.cas.nasca.web.test.enrolement.pp.EnrolementPPTestBase;
import be.ucm.cas.nasca.web.test.impression.ImpressionTestBase;
import be.ucm.cas.nasca.web.test.support.*;
import be.ucm.cas.nasca.web.test.taches.TachesTestBase;
import org.joda.time.DateTime;

import java.util.*;
import java.util.Map.Entry;

public class LeveeMajorationsTestBase extends TestBase {

    private static final String NOM_DOCUMENT = "All - Levée des majorations";
    private static final String NOM_DOCUMENT_REM = "All - Levée Majorations - Rem";
    private static final String NISS_TIERS = "83020814316";

    private Map<String, List<String>> periodeMajorations = new LinkedHashMap<>();

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.LM;
    }

    Map<String, List<String>> doLeveeMajoration(String nissOrBce, String typeAffilie, String periode1, String periode2, String origineDemande,
                                                String demandeRecuePar, String typeLeveeMajoration, String dateEvenement,
                                                String codebiteurSolidaire, String reponseTiers, String mediateur,
                                                String paiementEnAttente, String document, String annulationDebitionErronee, String resultat) {
        try {
            loadNissOrBce(nissOrBce);
            gestionClientPage.clickMenuLeveeMajorations();

            creationDemandeLM(nissOrBce, typeAffilie, periode1, periode2,
                    origineDemande, demandeRecuePar, typeLeveeMajoration,
                    dateEvenement, codebiteurSolidaire, reponseTiers,
                    mediateur, paiementEnAttente, document,
                    annulationDebitionErronee);

            if (!"REM".equals(resultat)) {
                if (!Boolean.valueOf(paiementEnAttente)) {
                    checkEnTraitement(nissOrBce, typeLeveeMajoration);
                } else {
                    gestionPaiementEnAttente(nissOrBce, typeAffilie, periode1, periode2, typeLeveeMajoration);
                }
            }

            return periodeMajorations;
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + nissOrBce, e);
        }
        return periodeMajorations;
    }

    private void checkEnTraitement(String nissOrBce, String typeLeveeMajoration) {
        if (gestionClientPage.isEnTraitementEtExplicationLM(typeLeveeMajoration, TestData.EN_TRAITEMENT, "Envoyé à l’INASTI")) {
            logSuccess("Levée de majorations pour " + nissOrBce + " Envoyé à l’INASTI OK");
        } else {
            logFailed("Levée de majorations pour " + nissOrBce + " Envoyé à l’INASTI KO");
        }
    }

    private void gestionPaiementEnAttente(String nissOrBce, String typeAffilie,
                                          String periode1, String periode2, String typeLeveeMajoration)
            throws Exception {
        if (gestionClientPage.isEnTraitementEtExplicationLM(typeLeveeMajoration, TestData.EN_TRAITEMENT, "Attente paiement")) {
            logSuccess("Levée de majorations pour " + nissOrBce + " Attente paiement OK");
        } else {
            logFailed("Levée de majorations pour " + nissOrBce + " Attente paiement KO");
        }

        gestionClientPage.clickMenuREM();
        if (TableUtils.isElementPresent("table-reclamations", TestData.LEVEE_MAJORATION)) {
            logSuccess("Levée de majorations pour " + nissOrBce + " REM OK");
        } else {
            logFailed("Levée de majorations pour " + nissOrBce + " REM KO");
        }

        if (TestData.TYPE_PP.equals(typeAffilie)) {
            ImpressionTestBase.checkImpressionDernierDocument(nissOrBce,
                    TestData.TYPE_NISS, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), null, null,
                    TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT_REM);
        } else {
            ImpressionTestBase.checkImpressionDernierDocument(nissOrBce,
                    TestData.TYPE_BCE, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), null, null,
                    TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT_REM);
        }
        DateTime currentDateTime = new DateTime();
        Date yesterday = currentDateTime.minusDays(3).toDate();
        String batchdate = DateUtils.doFormat(TestData.DATE_FORMAT_XML, yesterday);
        DaoFunctionality.getNascaDao().updateAffectationComptable(batchdate);

        yesterday = currentDateTime.minusDays(1).toDate();
        batchdate = DateUtils.doFormat(TestData.DATE_FORMAT_XML, yesterday);
        DaoFunctionality.getNascaDao().prepareAffectationComptable(batchdate);
        ComptabiliteTestBase.doAffectationPaiementTotal(nissOrBce, true, false, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, yesterday));

        try {
            RunBatch.runBatchMajDemandeLM();
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + nissOrBce, e);
        }

        loadNissOrBce(nissOrBce);
        gestionClientPage.clickMenuLeveeMajorations();
        if (gestionClientPage.isEnTraitementEtExplicationLM(typeLeveeMajoration, TestData.EN_TRAITEMENT, "Encodage")) {
            logSuccess("Levée de majorations pour " + nissOrBce + " Encodage OK");
        } else {
            logFailed("Levée de majorations pour " + nissOrBce + " Encodage KO");
        }

        gestionClientPage.clickEncodageLeveeMajoration();

        nascaPage.clickBtnSuivantWizard();
        if (TestData.TYPE_PP.equals(typeAffilie)) {
            gestionClientPage.fillPeriodeAnneeFromLM(periode2);
            gestionClientPage.fillPeriodeTrimestreFromLM("1");
            gestionClientPage.fillPeriodeAnneeToLM(periode2);
            gestionClientPage.fillPeriodeTrimestreToLM("1");
        } else {
            gestionClientPage.fillPeriodeAnneeFromLM(periode1);
            gestionClientPage.fillPeriodeAnneeToLMPM(periode1);
        }
        periodeMajorations = gestionClientPage.getPeriodeMajorationsPMLM();
        nascaPage.clickBtnSuivantWizard();
        BatchUtils.doSendResponseLM(nissOrBce, "/data/nasca/NascaInastiMock/nisseCompanyAcquittalSequoia-RequestAcquittal", TestData.TYPE_PM);
        nascaPage.clickBtnTerminerWizard();

        checkEnTraitement(nissOrBce, typeLeveeMajoration);
    }

    private void creationDemandeLM(String nissOrBce, String typeAffilie,
                                   String periode1, String periode2, String origineDemande,
                                   String demandeRecuePar, String typeLeveeMajoration,
                                   String dateEvenement, String codebiteurSolidaire,
                                   String reponseTiers, String mediateur, String paiementEnAttente,
                                   String document, String annulationDebitionErronee) throws Exception {
        gestionClientPage.clickAjouterDemandeLM();
        if (TestData.TYPE_PP.equals(typeAffilie)) {
            gestionClientPage.selectOrigineDemandeLM(origineDemande);
            gestionClientPage.selectDemandeRecueParLM(demandeRecuePar);
            gestionClientPage.selectTypeDemandeLM(typeLeveeMajoration);
            if (Boolean.valueOf(dateEvenement)) {
                gestionClientPage.fillDateEvenementLM(DateUtils.getDateToday());
            }
            if (Boolean.valueOf(codebiteurSolidaire)) {
                gestionClientPage.checkCodebiteurSolidaireLM();
            }
            if (Boolean.valueOf(reponseTiers)) {
                gestionClientPage.checkReponseATiersLM();
                gestionClientPage.clickBtnRechercherTiersLM();
                gestionClientPage.fillNissTiersLM(NISS_TIERS);
                gestionClientPage.clickBtnRechercherIdentiteTiersLM();
                TableUtils.clickElementIsPresent("DataTables_Table_0", SeleniumUtils.formatNissOrBceOrNumeroClient(NISS_TIERS));
                gestionClientPage.clickOngletAdresseTiersLM();
                TableUtils.clickElementIsPresent("table-adresses-tiers", SeleniumUtils.formatNissOrBceOrNumeroClient(NISS_TIERS));
                gestionClientPage.clickBtnEnregistrerTiersLM();
            }
            if (Boolean.valueOf(mediateur)) {
                gestionClientPage.checkMediateurLM();
            }
            nascaPage.clickBtnSuivantWizard();
            gestionClientPage.fillPeriodeAnneeFromLM(periode2);
            gestionClientPage.fillPeriodeTrimestreFromLM("1");
            gestionClientPage.fillPeriodeAnneeToLM(periode2);
            gestionClientPage.fillPeriodeTrimestreToLM("1");
            gestionClientPage.clickBtnRechercherMajorationsLM();
            gestionClientPage.clickBtnAjouterMajorationsLM();
            if (Boolean.valueOf(paiementEnAttente)) {
                gestionClientPage.checkPaiementEnAttenteLM();
            } else {
                if (gestionClientPage.checkIfCotisationsImpayeesLM(TestData.TYPE_PP)) {
                    nascaPage.clickBtnSuivantWizard();
                    gestionClientPage.clickBtnViderMajorationsLM();
                }
            }
            periodeMajorations = gestionClientPage.getPeriodeMajorationsPPLM();
            nascaPage.clickBtnSuivantWizard();
            gestionClientPage.fillPostScriptumLM("Test");
            ajoutDocumentLM(document);
            if (Boolean.valueOf(annulationDebitionErronee)) {
                gestionClientPage.checkAnnulationDebitionErroneeLM();
            }
            BatchUtils.doSendResponseLM(nissOrBce, "/data/nasca/NascaInastiMock/nisseAcquittal-requestAcquittal", TestData.TYPE_PP);
            nascaPage.clickBtnTerminerWizard();
        } else {
            gestionClientPage.selectOrigineDemandeLM(origineDemande);
            gestionClientPage.selectDemandeRecueParLM(demandeRecuePar);
            gestionClientPage.selectTypeDemandeLM(typeLeveeMajoration);
            if (Boolean.valueOf(dateEvenement)) {
                gestionClientPage.fillDateEvenementLM(DateUtils.getDateToday());
            }
            if (Boolean.valueOf(codebiteurSolidaire)) {
                gestionClientPage.checkCodebiteurSolidaireLM();
            }
            nascaPage.clickBtnSuivantWizard();
            gestionClientPage.fillPeriodeAnneeFromLM(periode1);
            gestionClientPage.fillPeriodeAnneeToLMPM(periode1);
            gestionClientPage.clickBtnRechercherMajorationsLM();
            gestionClientPage.clickBtnAjouterMajorationsLM();
            if (Boolean.valueOf(paiementEnAttente)) {
                gestionClientPage.checkPaiementEnAttenteLM();
            } else {
                if (gestionClientPage.checkIfCotisationsImpayeesLM(TestData.TYPE_PM)) {
                    nascaPage.clickBtnSuivantWizard();
                    gestionClientPage.clickBtnViderMajorationsLM();
                }
            }
            periodeMajorations = gestionClientPage.getPeriodeMajorationsPMLM();
            nascaPage.clickBtnSuivantWizard();
            gestionClientPage.fillPostScriptumLM("Test");
            ajoutDocumentLM(document);
            if (Boolean.valueOf(annulationDebitionErronee)) {
                gestionClientPage.checkAnnulationDebitionErroneeLM();
            }
            BatchUtils.doSendResponseLM(nissOrBce, "/data/nasca/NascaInastiMock/nisseCompanyAcquittalSequoia-RequestAcquittal", TestData.TYPE_PM);
            nascaPage.clickBtnTerminerWizard();
        }
    }

    private void ajoutDocumentLM(String document) {
        if (document != null) {
            gestionClientPage.selectTypeDocumentLM(document);
            gestionClientPage.clickBtnAjouterDocumentLM();
            gestionClientPage.fillPathDocumentLM(TestData.TMP_FILE + "\\" + TestData.LM_PDF_FILE);
            gestionClientPage.clickBtnEnregistrerDocumentLM();
        }
    }

    void verificationRemEtDocument(String typeAffilie,
                                   String typeLeveeMajoration, String nissOrBce) {
        if (gestionClientPage.isEnTraitementEtExplicationLM(typeLeveeMajoration, TestData.EN_TRAITEMENT, "REM en cours")) {
            logSuccess("Levée de majorations pour " + nissOrBce + " Envoyé à l’INASTI OK");
        } else {
            logFailed("Levée de majorations pour " + nissOrBce + " Envoyé à l’INASTI KO");
        }

        if (TestData.TYPE_PP.equals(typeAffilie)) {
            ImpressionTestBase.checkImpressionDernierDocument(nissOrBce,
                    TestData.TYPE_NISS, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), null, null,
                    TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT_REM);
        } else {
            ImpressionTestBase.checkImpressionDernierDocument(nissOrBce,
                    TestData.TYPE_BCE, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), null, null,
                    TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT_REM);
        }
    }

    void receptionFluxInasti(String nissOrBce, String typeAffilie,
                             String typeLmFlux, Map<String, List<String>> periodesEtMontantsMajorationsDecisionLM, String typeLeveeMajoration, String modificationLeveeMajoration, String validationLeveeMajoration, String resultat) {
        if (TestData.TYPE_PP.equals(typeAffilie)) {
            receptionAckEtDecisionPP(nissOrBce, typeLmFlux, periodesEtMontantsMajorationsDecisionLM, resultat);
        } else {
            receptionDecisionPM(nissOrBce, typeLmFlux, periodesEtMontantsMajorationsDecisionLM, resultat);
        }

        if (TestData.PARTIELLE.equals(resultat)) {
            traiterNotificationLM(nissOrBce);
        }

        loadNissOrBce(nissOrBce);
        gestionClientPage.clickMenuLeveeMajorations();
        if (!"REM".equals(resultat)) {
            if (TableUtils.isElementPresent("leveeMajorationTableId", resultat)) {
                logSuccess("Levée de majorations pour " + nissOrBce + " " + resultat + " OK");
            } else {
                logFailed("Levée de majorations pour " + nissOrBce + " " + resultat + " KO");
            }
        }

        if (Boolean.valueOf(modificationLeveeMajoration)) {
            TableUtils.clickElementColumnIsPresent("leveeMajorationTableId", TestData.TRAITEMENT_INASTI, "9", "1");
            gestionClientPage.clickModifierDemandeLM();
            gestionClientPage.clickBtnEnregistrerModificationLM();
            gestionClientPage.clickBtnBackLM();
        }

        if (Boolean.valueOf(validationLeveeMajoration)) {
            TableUtils.clickElementColumnIsPresent("leveeMajorationTableId", TestData.TRAITEMENT_INASTI, "9", "1");
            gestionClientPage.clickValiderDemandeLM();
            SeleniumUtils.clickConfirmAlert();
            gestionClientPage.clickBtnBackLM();

            if (gestionClientPage.isEnTraitementEtExplicationLM(typeLeveeMajoration, TestData.TRAITE, TestData.TRAITEMENT_INASTI)) {
                logSuccess("Levée de majorations pour " + nissOrBce + " Traité décision OK");
            } else {
                logFailed("Levée de majorations pour " + nissOrBce + " Traité décision KO");
            }
        }
    }

    private void receptionDecisionPM(String nissOrBce, String typeLmFlux,
                                     Map<String, List<String>> periodesEtMontantsMajorationsDecisionLM,
                                     String resultat) {
        Map<String, String> map = new LinkedHashMap<>();
        String testCaseName = null;

        map.put("BCE_VALEUR", nissOrBce);
        map.put("DATE_DECISION_VALEUR", DateUtils.getDateTodayXmlFomat() + "T00:00:00.000+01:00");
        map.put("TYPE_VALEUR", typeLmFlux);
        for (Entry<String, List<String>> entry : periodesEtMontantsMajorationsDecisionLM.entrySet()) {
            map.put("ANNEE_VALEUR", SeleniumUtils.deleteFormat(entry.getKey()));
            map.put("MONTANT_VALEUR", entry.getValue().get(0).replace(',', '.'));
        }

        // Envoi HandleDecision
        switch (resultat) {
            case TestData.ACCEPTE:
                testCaseName = TestData.SOAP_TC_LM_PM_ACCEPTED_ROOT_NAME;
                break;
            case TestData.REFUSE:
                testCaseName = TestData.SOAP_TC_LM_PM_REFUSED_ROOT_NAME;
                break;
            default:
                break;
        }
        map.put("ENVIRONNEMENT", ((TestData) ApplicationContext.getAppCtx().getBean("testData")).getPropsRootUrlFuzion());

        ReplaceContentInTestCaseUtils.replace(TestData.SOAP_UI_PROJECT_PM_LM, TestData.TEMP_FILE + "NASCA - SOAP_XML - PM - LM.xml", map);

        TestBase.sendSoap(TestData.TEMP_FILE + "NASCA - SOAP_XML - PM - LM.xml", testCaseName, "");
    }

    private void receptionAckEtDecisionPP(String nissOrBce, String typeLmFlux,
                                          Map<String, List<String>> periodesEtMontantsMajorationsDecisionLM,
                                          String resultat) {
        Map<String, String> map = new LinkedHashMap<>();
        String testCaseName = null;

        map.put("NISS_VALEUR", nissOrBce);
        map.put("DATE_DEMANDE_VALEUR", DateUtils.getDateTodayXmlFomat());
        map.put("TYPE_VALEUR", typeLmFlux);
        for (Entry<String, List<String>> entry : periodesEtMontantsMajorationsDecisionLM.entrySet()) {
            map.put("TRIMESTRE_VALEUR", SeleniumUtils.deleteFormat(entry.getKey()));
            switch (entry.getValue().get(1)) {
                case TestData.TYPE_CREANCE_ORDINAIRE:
                    map.put("TYPE_CONTRIBUTION_VALEUR", "DEFINITIVE");
                    break;
                case TestData.TYPE_CREANCE_REGULARISATION:
                    map.put("TYPE_CONTRIBUTION_VALEUR", "REGULARIZED");
                    break;
                case TestData.TYPE_CREANCE_PRESUMEE:
                    map.put("TYPE_CONTRIBUTION_VALEUR", "PROVISIONAL");
                    break;
                default:
                    break;
            }
            map.put("MONTANT_VALEUR", entry.getValue().get(0).replace(',', '.'));
        }

        // Envoi acquittalAcknowledge
        // TODO enlever commentaire pour ack
//		String projetXml = TestData.SOAP_UI_PROJECT_ROOT_LM_PP + ((TestData) ApplicationContext.getAppCtx().getBean(
//				"testData")).getPropsRootUrl() + ".xml";
//		
//		testCaseName = TestData.SOAP_TC_LM_PP_ACK_OK_ROOT_NAME;
//		
//		ReplaceContentInTestCaseUtils.replace(projetXml, TestData.TEMP_FILE + "NASCA-UATLMPP-" + ((TestData) ApplicationContext.getAppCtx().getBean(
//				"testData")).getPropsRootUrl() + ".xml", map);
//		
//		TestBase.sendSoap(TestData.TEMP_FILE + "NASCA-UATLMPP-", testCaseName, "");

        // Envoi acquittalDecision
        switch (resultat) {
            case TestData.COMPLETE:
                testCaseName = TestData.SOAP_TC_LM_PP_COMP_ROOT_NAME;
                break;
            case TestData.PARTIELLE:
                testCaseName = TestData.SOAP_TC_LM_PP_PART_ROOT_NAME;
                break;
            case "Irrecevable":
                testCaseName = TestData.SOAP_TC_LM_PP_IRREC_ROOT_NAME;
                break;
            case TestData.REFUSE:
                testCaseName = TestData.SOAP_TC_LM_PP_REFUS_ROOT_NAME;
                break;
            default:
                break;
        }
        map.put("ENVIRONNEMENT", ((TestData) ApplicationContext.getAppCtx().getBean("testData")).getPropsRootUrlFuzion());

        ReplaceContentInTestCaseUtils.replace(TestData.SOAP_UI_PROJECT_PP_LM, TestData.TEMP_FILE + "NASCA - SOAP_XML - PP - LM.xml", map);

        TestBase.sendSoap(TestData.TEMP_FILE + "NASCA - SOAP_XML - PP - LM.xml", testCaseName, "");
    }

    private void traiterNotificationLM(String nissOrBce) {
        homeMenuPage.clickOngletTaches();
        tachePage.clickBtnResetTache();
        tachePage.fillNumero(nissOrBce);
        tachePage.clickBtnSearchTache();
        TachesTestBase.searchAndFinishNotification(nissOrBce, "Notification levée de majoration");
    }

    void verificationOperationEtDocumentSuiteDecision(
            String typeAffilie, String resultat, String periode1, String periode2, String nissOrBce) {
        if (TestData.TYPE_PP.equals(typeAffilie)) {
            if (TestData.PARTIELLE.equals(resultat) || TestData.COMPLETE.equals(resultat)) {
                EnrolementPPTestBase.checkCreanceEtTypeOperationTrimestrePP(nissOrBce, periode1, TestData.MAJORATION, "Renonciation", null);
            } else {
                if ("Irrecevable".equals(resultat)) {
                    traiterNotificationLM(nissOrBce);
                } else {
                    // TODO gestion Refusé PP
                }
            }
            ImpressionTestBase.checkImpressionDernierDocument(nissOrBce,
                    TestData.TYPE_NISS, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), null, null,
                    TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT);
        } else {
            if (TestData.ACCEPTE.equals(resultat)) {
                EnrolementPMTestBase.checkCreanceEtTypeOperationTrimestrePM(nissOrBce, periode2, TestData.MAJORATION, "Renonciation", null);
            } else {
                if (TestData.REFUSE.equals(resultat)) {
                    // TODO gestion Refusé PM
                }
            }
            ImpressionTestBase.checkImpressionDernierDocument(nissOrBce,
                    TestData.TYPE_BCE, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), null, null,
                    TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT);
        }
    }

    Map<String, List<String>> verificationSuspensions(String typeAffilie, String resultat,
                                                      String nissOrBce, Map<String, List<String>> periodesEtMontantsMajorationsLM) {
        Map<String, List<String>> periodesEtMontantsMajorationsDecisionLM = new LinkedHashMap<>();

        if (TestData.TYPE_PP.equals(typeAffilie)) {
            for (Entry<String, List<String>> list : periodesEtMontantsMajorationsLM.entrySet()) {
                String periodeSuspension = list.getKey();

                String majo = list.getValue().get(0);

                String majoDecision = "0";
                List<String> decisions = new ArrayList<>();
                if (TestData.PARTIELLE.equals(resultat)) {
                    majoDecision = "2";
                } else if (TestData.COMPLETE.equals(resultat)) {
                    majoDecision = majo;
                }
                EnrolementPPTestBase.checkSuspensionEtSoldeAnneeTrimestrePP(nissOrBce, periodeSuspension, TestData.MAJORATION, TestData.LEVEE_MAJORATION, null, "");

                decisions.add(majoDecision);
                decisions.add(list.getValue().get(1));
                periodesEtMontantsMajorationsDecisionLM.put(periodeSuspension, decisions);
            }

            ImpressionTestBase.checkImpressionDernierDocument(nissOrBce,
                    TestData.TYPE_NISS, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), null, null,
                    TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT);
        } else {
            for (Entry<String, List<String>> list : periodesEtMontantsMajorationsLM.entrySet()) {
                String periodeSuspension = list.getKey();

                String majo = list.getValue().get(0);

                String majoDecision = "0";
                List<String> decisions = new ArrayList<>();
                if (TestData.ACCEPTE.equals(resultat)) {
                    majoDecision = majo;
                }
                EnrolementPMTestBase.checkSuspensionEtSoldeAnneeTrimestrePM(nissOrBce, periodeSuspension, TestData.MAJORATION, TestData.LEVEE_MAJORATION, null, "");

                decisions.add(majoDecision);

                periodesEtMontantsMajorationsDecisionLM.put(periodeSuspension, decisions);
            }

            ImpressionTestBase.checkImpressionDernierDocument(nissOrBce,
                    TestData.TYPE_BCE, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, new Date()), null, null,
                    TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT);
        }
        return periodesEtMontantsMajorationsDecisionLM;
    }
}