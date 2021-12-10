package be.ucm.cas.nasca.web.test.enrolement.pp;

import be.ucm.cas.nasca.web.test.carriere.CarriereTestBase;
import be.ucm.cas.nasca.web.test.plc.PLCTestBase;
import be.ucm.cas.nasca.web.test.profil.ProfilTestBase;
import be.ucm.cas.nasca.web.test.revenu.RevenuTestBase;
import be.ucm.cas.nasca.web.test.support.*;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public abstract class EnrolementPPAbstract extends EnrolementPPTestBase {

    private Calendar calJour;
    private String date60Ans;
    private String date65Ans;
    private File fichierXml;

    void doLaunchEnrolement(String trimestre, int mois) {
        calJour = Calendar.getInstance();

        int anneeEnCours = calJour.get(Calendar.YEAR);
        int anneeEnCoursPlus1An = calJour.get(Calendar.YEAR) + 1;
        String annee = String.valueOf(anneeEnCoursPlus1An);

        if (DaoFunctionality.getNascaDao().notExistBaremesPp(String.valueOf(anneeEnCoursPlus1An))) {
            DaoFunctionality.getNascaDao().updateBaremesPp(String.valueOf(anneeEnCours) + "-12-31");
            DaoFunctionality.getNascaDao().insert2016BaremesPp(String.valueOf(anneeEnCoursPlus1An) + "-01-01");
        }

        calJour.set(Calendar.YEAR, Integer.valueOf(annee));
        calJour.add(Calendar.YEAR, -60);
        calJour.set(Calendar.DAY_OF_MONTH, 1);
        calJour.set(Calendar.MONTH, 0);

        setDate60Ans(DateUtils.doFormat(TestData.DATE_FORMAT_XML, calJour.getTime()));

        calJour.set(Calendar.YEAR, Integer.valueOf(annee));
        calJour.add(Calendar.YEAR, -65);
        calJour.set(Calendar.DAY_OF_MONTH, 1);
        calJour.set(Calendar.MONTH, 0);

        setDate65Ans(DateUtils.doFormat(TestData.DATE_FORMAT_XML, calJour.getTime()));

        calJour = Calendar.getInstance();
        calJour.set(Calendar.MONTH, mois);
        calJour.set(Calendar.DAY_OF_MONTH, 10);
        calJour.set(Calendar.YEAR, Integer.valueOf(annee));
        calJour.set(Calendar.HOUR_OF_DAY, 0);
        calJour.set(Calendar.MINUTE, 0);
        calJour.set(Calendar.SECOND, 0);
        calJour.set(Calendar.MILLISECOND, 0);

        RunBatch.runBatchChangeDate(DateUtils.doFormat(TestData.DATE_FORMAT_XML, calJour.getTime()));

        DaoFunctionality.getNascaDao().deleteEnrolementTrimestrielBlocage();
        DaoFunctionality.getNascaDao().deleteDateEnrolementPp(annee, trimestre);
        DaoFunctionality.getNascaDao().insertDateEnrolementPp(annee, trimestre, DateUtils.doFormat(TestData.DATE_FORMAT_XML_WITH_HOUR, calJour.getTime()));

        Calendar calMaj = Calendar.getInstance();
        calMaj.setTime(calJour.getTime());
        calMaj.add(Calendar.MONTH, 3);

        DaoFunctionality.getNascaDao().initialisationDateEnrolementMajoPPAnnuelle(calMaj.get(Calendar.YEAR), calMaj.getTime());
        DaoFunctionality.getNascaDao().initialisationDateEnrolementMajoPPTrimestrielle(calMaj.get(Calendar.YEAR), DateUtils.getQuarterNumber(DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, calMaj.getTime())), calMaj.getTime());

        DaoFunctionality.getNascaDao().initialisationDatePaiement(calJour.getTime());
    }

    void doSelectionNiss(String id, String libelle,
                         String booleanDate60ans, String booleanDate65ans, String nature,
                         String regime, String dateDebut1,
                         String article11,
                         String exoReduction,
                         String skip, Map<String, List<String>> mapNiss,
                         String date60Ans, String date65Ans, String fileName, String anneeRevenu, String typeRevenu) {
        if (!Boolean.valueOf(skip)) {
            String niss;
            String ajoutExoReduction = null;
            niss = DaoFunctionality.getNascaDao().getNissForEnrolementPP(booleanDate60ans, date60Ans, nature, regime, dateDebut1, exoReduction, article11, calJour, booleanDate65ans, date65Ans, anneeRevenu, typeRevenu, mapNiss);

            if (niss == null) {
                // Sans Exo-réduction
                niss = DaoFunctionality.getNascaDao().getNissForEnrolementPP(booleanDate60ans, date60Ans, nature, regime, dateDebut1, null, article11, calJour, booleanDate65ans, date65Ans, anneeRevenu, typeRevenu, mapNiss);
                if (niss != null) {
                    ajoutExoReduction = "true";
                }
            }

            if (niss == null && "9".equals(nature)) {
                niss = DaoFunctionality.getNascaDao().getNissForEnrolementPP(booleanDate60ans, date60Ans, nature, regime, dateDebut1, exoReduction, null, calJour, booleanDate65ans, date65Ans, anneeRevenu, typeRevenu, mapNiss);
            }

            if (niss == null && !StringUtils.isEmpty(article11.trim())) {
                niss = DaoFunctionality.getNascaDao().getNissForEnrolementPP(booleanDate60ans, date60Ans, nature, regime, dateDebut1, exoReduction, null, calJour, booleanDate65ans, date65Ans, anneeRevenu, typeRevenu, mapNiss);
            }

            if (niss != null) {
                List<String> params = new ArrayList<>();
                params.add(niss);
                params.add(ajoutExoReduction);
                mapNiss.put(id, params);
            } else {
                initTest(libelle + " - Sélection NISS", fileName);

                logSkip(Thread.currentThread().getStackTrace()[1].getFileName(), TestData.PAS_CAS_TROUVE_DB);

                finishTestExecution();
            }
        }
    }

    void doPreparationProfilEtRevenus(String id, String libelle,
                                      String typeRevenu, String revenu,
                                      String anneeRevenu,
                                      String exoReduction,
                                      String skip,
                                      Map<String, List<String>> mapNiss, Calendar calJour, String provisoire, String fileName, String nature, String regime, String cotisantPLC, String article11) {
        if (!Boolean.valueOf(skip)) {
            String niss = null;
            String ajoutExoReduction = null;
            if (mapNiss.get(id) != null) {
                niss = mapNiss.get(id).get(0);
                ajoutExoReduction = mapNiss.get(id).get(1);
            }

            if (niss != null) {
                initTest(libelle + " - Préparation Profil et Revenus", fileName);

                loginNasca();

                EnrolementPPTestBase.deleteCotisationAnneeTrimestrePP(niss, DateQuarters.getQuarterCurrent(calJour.getTime()));

                String natureProfil = "";
                switch (nature) {
                    case "1":
                        natureProfil = TestData.NATURE_PROFILE_PRINCIPAL;
                        break;
                    case "4":
                        natureProfil = TestData.NATURE_PROFILE_COMPLEMENTAIRE;
                        break;
                    case "5":
                        natureProfil = TestData.NATURE_PROFILE_CONJOINT_MAXI_STATUT;
                        break;
                    case "6":
                        natureProfil = TestData.NATURE_PROFILE_CONJOINT_MINI_STATUT;
                        break;
                    case "8":
                        natureProfil = TestData.NATURE_PROFILE_AGE_PENSION;
                        break;
                    case "9":
                        natureProfil = TestData.NATURE_PROFILE_BENEFICIE_PENSION;
                        break;
                    default:
                        break;
                }

                if (provisoire != null && !StringUtils.isEmpty(provisoire.trim())) {
                    changementProfilProvisoire(exoReduction, calJour, provisoire, niss, natureProfil);
                }

                if (ajoutExoReduction != null) {
                    ProfilTestBase.doModificationLigneProfil(niss, natureProfil, null, null, exoReduction);
                }

                String nissRevenu = niss;

                if ("6".equals(nature) && (typeRevenu != null && !StringUtils.isEmpty(typeRevenu.trim()))) {
                    nissRevenu = DaoFunctionality.getNascaDao().getNissCjtFromNiss(niss);
                    int year = calJour.get(Calendar.YEAR);
                    year = year - 3;
                    ProfilTestBase.doModificationLigneProfil(nissRevenu, null, null, "0101" + year, null);
                }

                if ("RDE".equals(regime) && (typeRevenu == null || StringUtils.isEmpty(typeRevenu.trim()))) {
                    int year = calJour.get(Calendar.YEAR);
                    year = year - 3;
                    ProfilTestBase.doModificationLigneProfil(niss, natureProfil, null, "0101" + year, exoReduction);
                }

                if (typeRevenu != null && TestData.TYPE_REVENU_PRESUME.equals(typeRevenu)) {
                    RevenuTestBase.ajouterRevenuSansErreur(nissRevenu, anneeRevenu, revenu, typeRevenu, TestData.STATUT_REVENU_A_REGULARISER, TestData.SOURCE_REVENU_AFFILIE, true, "3112" + (calJour.get(Calendar.YEAR) - 1), "false");
                } else if (typeRevenu != null && TestData.TYPE_REVENU_FISCAL.equals(typeRevenu)) {
                    RevenuTestBase.ajouterRevenuSansErreur(nissRevenu, anneeRevenu, revenu, typeRevenu, TestData.STATUT_REVENU_A_REGULARISER, TestData.SOURCE_REVENU_CONTRIBUTIONS, true, "3112" + (calJour.get(Calendar.YEAR) - 1), "false");
                }

                if (cotisantPLC != null && !StringUtils.isEmpty(cotisantPLC.trim())) {
                    PLCTestBase.creationNouveauContratPLC(niss, "Communication du client", null, null,
                            "Téléphone", "111111111111", "",
                            "Ordinaire", "Crest", "01/01/" + calJour.get(Calendar.YEAR),
                            "Accord - Envoyer premier enrôlement", null, "Nouveau", null);
                }

                if (article11 != null && !StringUtils.isEmpty(article11.trim())) {
                    logInfo("Mise à jour article 11 en DB", "avant");
                    DaoFunctionality.getNascaDao().updateMontantPlancherEvenement(article11, niss);
                    loadNissOrBce(niss);
                    logInfo("Mise à jour article 11 en DB", "après");
                }

                finishTestExecution();
            }
        }
    }

    private void changementProfilProvisoire(String exoReduction, Calendar calJour, String provisoire, String niss, String natureProfil) {
        int year = calJour.get(Calendar.YEAR);

        if ("2".equals(provisoire)) {
            year = year - 1;
        }

        if ("3".equals(provisoire)) {
            year = year - 2;
        }
        ProfilTestBase.doModificationLigneProfil(niss, natureProfil, null, "0101" + year, exoReduction);
    }

    void doLaunchBatch(Map<String, List<String>> mapNiss, Calendar calJour) {
        loginNasca();
        // Prepare enrolement
        for (String key : mapNiss.keySet()) {
            String niss = mapNiss.get(key).get(0);

            if (niss != null) {
                EnrolementPPTestBase.doEnrolementPPPrepare(niss, DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE_WITH_SLASH, calJour.getTime()));
            }
        }

        loginNasca();
        // Enrolement
        EnrolementPPTestBase.doEnrolementPPAutomatique();

        loginNasca();
        // Prepare et génération document
        EnrolementPPTestBase.doGenerationDocumentEnrolementPPPrepareEtGeneration();

        try {
            fichierXml = BatchUtils.getFileFromServer(DaoFunctionality.getNascaDao().getReferenceFromPrintQueue("PP - Enrôlement Avis"));
        } catch (Exception e) {
            logFailed("Problème lors de la récupération du fichier xml " + e);
        }
    }

    void doCheckEnrolement(String id, String libelle,
                           String cotisation,
                           String skip, Map<String, List<String>> mapNiss,
                           String trimestre, Calendar calJour, String fileName, String codeActivite, String revenuCommunique, String revenuAdapte, String revenuCompose, String codeProvisoire, String anneeReference, String revenuContribution, String revenuBase) {
        if (!Boolean.valueOf(skip)) {
            String niss = null;
            if (mapNiss.get(id) != null) {
                niss = mapNiss.get(id).get(0);
            }

            if (niss != null) {
                initTest(libelle + " - Check Enrolement", fileName);

                loginNasca();

                EnrolementPPTestBase.checkCotisationEnrolee(niss, calJour.get(Calendar.YEAR) + trimestre, cotisation);

                checkDocumentEnrolement(cotisation, niss, fichierXml);

                CarriereTestBase.checkCarriere(niss, "trimestre", trimestre, String.valueOf(calJour.get(Calendar.YEAR)), "4", codeActivite, revenuCommunique, revenuAdapte, revenuCompose, codeProvisoire, anneeReference, revenuContribution, revenuBase);

                finishTestExecution();
            }
        }
    }

    private void checkDocumentEnrolement(String cotisation, String niss, File fichierXml) {
        if (fichierXml != null) {
            if ("0".equals(cotisation)) {
                if (!ParserXmlUtils.parserXmlFile(fichierXml, "Glbl_AffiNiss", niss)) {
                    logSuccess("Document d'enrôlement n'est pas présent dans le fichier XML (cotisation à 0)");
                } else {
                    logFailed("Document d'enrôlement est présent dans le fichier XML (cotisation à 0)");
                }
            } else {
                if (ParserXmlUtils.parserXmlFile(fichierXml, "Glbl_AffiNiss", niss)) {
                    logSuccess("Document d'enrôlement est présent dans le fichier XML");
                } else {
                    logFailed("Document d'enrôlement n'est pas présent dans le fichier XML");
                }
            }
        } else {
            logFailed("Document XML absent sur le serveur");
        }
    }

    String getDate60Ans() {
        return this.date60Ans;
    }

    private void setDate60Ans(String date60Ans) {
        this.date60Ans = date60Ans;
    }

    String getDate65Ans() {
        return this.date65Ans;
    }

    private void setDate65Ans(String date65Ans) {
        this.date65Ans = date65Ans;
    }

    Calendar getCalJour() {
        return this.calJour;
    }

    public void setCalJour(Calendar calJour) {
        this.calJour = calJour;
    }
}
