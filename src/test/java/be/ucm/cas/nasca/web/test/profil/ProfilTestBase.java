package be.ucm.cas.nasca.web.test.profil;

import be.ucm.cas.nasca.web.test.revenu.RevenuTestBase;
import be.ucm.cas.nasca.web.test.support.EnumTypeTest;
import be.ucm.cas.nasca.web.test.support.TestBase;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ProfilTestBase extends TestBase {

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    protected static void doAjoutExoReductionArticle37Profil(String niss, String dateDebut, String dateFin, String exoReduction, String justification) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuProfil();
            logInfo("Profil pour " + niss, "");
            gestionClientPage.clickExoRecutionArt37();
            gestionClientPage.fillDateDebutFinEvenement(dateDebut, dateFin);
            gestionClientPage.fillReductionType(exoReduction);
            if (justification != null && !StringUtils.isEmpty(justification.trim())) {
                gestionClientPage.fillJustification(doTransformJustificationExo(justification));
            }

            fillNextWizard(niss, "Ajout Exo-réduction Article 37 pour " + niss, true);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    public static void doChangeProfil(String niss, String naturefromCONSTANT,
                                      String date, String reductiontype) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuProfil();
            logInfo("Profil pour " + niss, "");
            gestionClientPage.clickChangementdeNature(naturefromCONSTANT);
            if (date != null) {
                gestionClientPage.fillDateEvenementCustom(date);
            }
            if (reductiontype != null && !StringUtils.isEmpty(reductiontype.trim())) {
                gestionClientPage.fillReductionType(reductiontype);
                gestionClientPage.fillJustificationRandom();
            }
            logInfo("Situation profil pour " + niss, "");
            nascaPage.clickBtnSuivantWizard();
            nascaPage.clickBtnSuivantWizard();
            gestionClientPage.clickBtnProfilCourrierNon();
            nascaPage.clickBtnTerminerWizard();

            if (nascaPage.isMessageErreur(TestData.MESSAGE)) {
                logFailed(TestData.ERREUR_NASCA, "");
            } else {
                logSuccess("Changement de nature pour " + niss + " " + naturefromCONSTANT);
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    protected static void doChangeProfilAndAjoutRevenu(String niss,
                                                       String naturefromCONSTANT, String datedebut2,
                                                       String reductiontype2, Map<Integer, List<String>> mapElementRevenu,
                                                       String dateRevenu, String statut) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuProfil();
            logInfo("Profil pour " + niss, "");
            gestionClientPage.clickChangementdeNature(naturefromCONSTANT);
            gestionClientPage.fillDateEvenementCustom(datedebut2);
            if (reductiontype2 != null && !StringUtils.isEmpty(reductiontype2.trim())) {
                gestionClientPage.fillReductionType(reductiontype2);
                gestionClientPage.fillJustificationRandom();
            }
            logInfo("Situation profil pour " + niss, "");
            nascaPage.clickBtnSuivantWizard();
            for (Entry<Integer, List<String>> entry : mapElementRevenu.entrySet()) {
                List<String> listElem = entry.getValue();
                String typeSource = TestData.SOURCE_REVENU_INASTI_FLUX;
                if (listElem.get(2).equals(TestData.TYPE_REVENU_PRESUME)) {
                    typeSource = TestData.SOURCE_REVENU_AFFILIE;
                }

                gestionClientPage.clickBtnAjoutRevenu();
                ajoutRevenu(datedebut2, dateRevenu, statut, listElem, typeSource);
                gestionClientPage.clickBtnEnregistrerRevenuFromProfil();
            }
            logInfo("Situation revenu pour " + niss, "");
            nascaPage.clickBtnSuivantWizard();
            gestionClientPage.clickBtnProfilCourrierNon();
            nascaPage.clickBtnTerminerWizard();

            if (nascaPage.isMessageErreur(TestData.MESSAGE)) {
                logFailed(TestData.ERREUR_NASCA, "");
            } else {
                logSuccess("Changement de nature pour " + niss + " " + naturefromCONSTANT);
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    private static void ajoutRevenu(String datedebut2, String dateRevenu, String statut, List<String> listElem, String typeSource) {
        if ("2012".equals(listElem.get(0))) {
            RevenuTestBase.fillInRevenu(listElem.get(0), listElem.get(1), "31/12/2014", listElem.get(2), statut, typeSource, datedebut2);
        } else {
            if ("2013".equals(listElem.get(0))) {
                RevenuTestBase.fillInRevenu(listElem.get(0), listElem.get(1), "31/12/2015", listElem.get(2), statut, typeSource, datedebut2);
            } else {
                RevenuTestBase.fillInRevenu(listElem.get(0), listElem.get(1), dateRevenu, listElem.get(2), statut, typeSource, datedebut2);
            }
        }
    }

    public static void doModificationLigneProfil(String niss, String nature1, String nature2, String dateEvenement, String exoReduction) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuProfil();
            logInfo("Profil pour " + niss, "");
            gestionClientPage.clickProfilLigneEvenement(nature1);
            if (dateEvenement != null) {
                gestionClientPage.fillDateProfilEvenement(dateEvenement);
            }
            if (nature2 != null) {
                if (nature2.equals(TestData.NATURE_PROFILE_MAXI_STATUT)) {
                    gestionClientPage.fillNatureEvenement("maxi");
                } else {
                    gestionClientPage.fillNatureEvenement(nature2);
                }
            }
            if (exoReduction != null && !StringUtils.isEmpty(exoReduction.trim())) {
                gestionClientPage.fillReductionTypeChgtProfil(exoReduction);
                gestionClientPage.fillJustificationChgtProfil();
            } else {
                gestionClientPage.fillEmptyReductionTypeChgtProfil();
                gestionClientPage.fillEmptyJustificationChgtProfil();
            }

            fillNextWizard(niss, "Update profil existant pour " + niss + " " + nature1 + " vers " + nature2 + " - " + dateEvenement + " - " + exoReduction, true);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    protected static void doChangementProfilMultipleAjout(String niss, Map<Integer, List<String>> testdataEvenement) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuProfil();
            logInfo("Profil pour " + niss, "");
            gestionClientPage.clickChangementProfilMultiple();

            for (Entry<Integer, List<String>> entry : testdataEvenement.entrySet()) {
                List<String> listElem = entry.getValue();
                gestionClientPage.clickBtnAjouterEvenementProfil();
                boucleListElement(listElem);
                gestionClientPage.clickBtnEnregistrerProfilWizard();
            }

            fillNextWizard(niss, "Changement profil multiple ajout " + niss, true);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    private static void fillNextWizard(String niss, String test, boolean stepRevenu) {
        logInfo("Situation profil pour " + niss, "");
        nascaPage.clickBtnSuivantWizard();
        if (stepRevenu) {
            nascaPage.clickBtnSuivantWizard();
        }
        gestionClientPage.clickBtnProfilCourrierNon();
        nascaPage.clickBtnTerminerWizard();

        if (nascaPage.isMessageErreur(TestData.MESSAGE)) {
            logFailed(TestData.ERREUR_NASCA, "");
        } else {
            logSuccess(test);
        }
    }

    private static void boucleListElement(List<String> listElem) {
        for (int i = 0; i < listElem.size(); i++) {
            if (listElem.get(i) != null && !StringUtils.isEmpty(listElem.get(i).trim())) {
                switch (i) {
                    case 0:
                        gestionClientPage.fillDateProfilEvenement(listElem.get(i));
                        break;
                    case 1:
                        gestionClientPage.fillTypeEvenement(listElem.get(i));
                        break;
                    case 2:
                        gestionClientPage.fillNatureCotisante(listElem.get(i));
                        break;
                    case 3:
                        ajoutExoReduction(listElem, i);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private static void ajoutExoReduction(List<String> listElem, int i) {
        gestionClientPage.fillReductionType(listElem.get(i));
        if (!"Complémentaire".equals(listElem.get(2))) {
            gestionClientPage.fillJustificationRandom();
        }
    }

    protected static void doChangementProfilMultipleSuppression(String niss, Map<Integer, List<String>> testdataEvenement, boolean onlyEvenement) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuProfil();
            logInfo("Profil pour " + niss, "");
            gestionClientPage.clickChangementProfilMultiple();

            for (Entry<Integer, List<String>> entry : testdataEvenement.entrySet()) {
                List<String> listElem = entry.getValue();
                if (onlyEvenement) {
                    gestionClientPage.clickProfilSuppressionLigneEvenement("Principal");
                    gestionClientPage.fillRaisonSuppressionEvenement();
                    gestionClientPage.clickBtnOuiSupprimerEvenement();
                } else {
                    gestionClientPage.clickProfilSuppressionLigneEvenementMultiple(listElem);
                }
            }

            fillNextWizard(niss, "Changement profil multiple " + niss, true);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    protected static void doSuppressionLigneProfil(String niss, String natureASupprimer) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuProfil();
            logInfo("Profil pour " + niss, "");
            if (natureASupprimer != null) {
                gestionClientPage.clickProfilSuppressionLigneEvenement(natureASupprimer);
            } else {
                gestionClientPage.clickProfilSuppressionLigneEvenementFin();
            }
            nascaPage.clickBtnOuiModal();

            logSuccess("Suppression profil pour niss " + niss + " - " + natureASupprimer);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    private static String doTransformJustificationExo(String justification) {
        switch (justification) {
            case "ETU":
                return TestData.ETUDIANT_MOINS_25;
            case "CCO":
                return TestData.CONJOINT_COUVERT;
            case "APE":
                return TestData.A_PENSION;
            case "APS":
                return TestData.A_PENSION_SURVIE;
            case "ENO":
                return TestData.ENSEIGNANT_NOMME_10ES_OK;
            case "MPU":
                return TestData.EST_MANDATAIRE_PUBLIC;
            default:
                break;
        }
        return null;
    }

    public static String doTransformReduction(String reductionType) {
        if (reductionType != null && !StringUtils.isEmpty(reductionType)) {
            switch (reductionType.trim()) {
                case "EXO":
                    return TestData.EXONERATION;
                case "RED":
                    return TestData.REDUCTION;
                default:
                    break;
            }
        }
        return null;
    }
}