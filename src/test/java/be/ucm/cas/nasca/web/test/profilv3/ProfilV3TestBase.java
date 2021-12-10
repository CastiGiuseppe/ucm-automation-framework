package be.ucm.cas.nasca.web.test.profilv3;

import be.ucm.cas.nasca.web.test.support.*;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class ProfilV3TestBase extends TestBase {

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    void doSelectionDossierTrimestriel(Map<String, List<String>> mapNiss, String id, String libelle, String fileName,
                                       String dateDebut65Ans, String dateFin65Ans, String nac,
                                       String conjoint, String conjointPensionne, String regimeDB, String anneesCarriereDB,
                                       String naturePensionDB) {
        String niss;
        String ajoutPension = "false";

        if (!Boolean.valueOf(conjoint) && !Boolean.valueOf(conjointPensionne)) {
            niss = DaoFunctionality.getNascaDao().getNissSansCjtForProfilV3Trimestriel(dateDebut65Ans, dateFin65Ans, nac);
            if (niss == null) {
                if ("O0".equals(nac) || "F0".equals(nac) || "E0".equals(nac) || "X0".equals(nac)) {
                    niss = DaoFunctionality.getNascaDao().getNissSansCjtForProfilV3Trimestriel(dateDebut65Ans, dateFin65Ans, "A_");
                    ajoutPension = "true";
                }
            } else {
                if (regimeDB != null && !StringUtils.isEmpty(regimeDB.trim())) {
                    DaoFunctionality.getNascaDao().updateCaracteristiquesPensionForProfilV3(StringUtils.isEmpty(regimeDB.trim()) ? null : regimeDB,
                            StringUtils.isEmpty(anneesCarriereDB.trim()) ? null : anneesCarriereDB, StringUtils.isEmpty(naturePensionDB.trim()) ? null : naturePensionDB,
                            Boolean.valueOf(conjointPensionne) ? "CJT" : "TLM", "F", niss);
                }
            }
        } else if (Boolean.valueOf(conjoint) && !Boolean.valueOf(conjointPensionne)) {
            niss = DaoFunctionality.getNascaDao().getNissAvecCjtNonPensionneForProfilV3Trimestriel(dateDebut65Ans, dateFin65Ans, nac);
            if (niss != null && regimeDB != null && !StringUtils.isEmpty(regimeDB.trim())) {
                DaoFunctionality.getNascaDao().updateCaracteristiquesPensionForProfilV3(StringUtils.isEmpty(regimeDB.trim()) ? null : regimeDB,
                        StringUtils.isEmpty(anneesCarriereDB.trim()) ? null : anneesCarriereDB, StringUtils.isEmpty(naturePensionDB.trim()) ? null : naturePensionDB,
                        Boolean.valueOf(conjointPensionne) ? "CJT" : "TLM", "F", niss);
            }
        } else {
            niss = DaoFunctionality.getNascaDao().getNissAvecCjtPensionneForProfilV3Trimestriel(dateDebut65Ans, dateFin65Ans, nac);
            if (niss == null) {
                niss = DaoFunctionality.getNascaDao().getNissAvecCjtNonPensionneForProfilV3Trimestriel(dateDebut65Ans, dateFin65Ans, nac);
                if (niss != null && regimeDB != null && !StringUtils.isEmpty(regimeDB.trim())) {
                    DaoFunctionality.getNascaDao().updateCaracteristiquesPensionForProfilV3(StringUtils.isEmpty(regimeDB.trim()) ? null : regimeDB,
                            StringUtils.isEmpty(anneesCarriereDB.trim()) ? null : anneesCarriereDB, StringUtils.isEmpty(naturePensionDB.trim()) ? null : naturePensionDB,
                            Boolean.valueOf(conjointPensionne) ? "CJT" : "TLM", "F", niss);
                }
            }
        }

        addNissToMapOrSkipTest(mapNiss, id, libelle, fileName, niss, ajoutPension);
    }

    void doSelectionDossierAnnuel(Map<String, List<String>> mapNiss, String id, String libelle, String fileName,
                                  String agePensionAtteint, String dateDebut65Ans, String naturePensionDB, String enfantACharge,
                                  String regimeDB, String nacAvant) {
        String niss;

        if (Boolean.valueOf(agePensionAtteint)) {
            niss = DaoFunctionality.getNascaDao().getNissAgePensionAtteintForProfilV3Annuel(dateDebut65Ans, nacAvant, regimeDB, naturePensionDB, enfantACharge);

            if (niss == null) {
                niss = DaoFunctionality.getNascaDao().getNissAgePensionAtteintForProfilV3Annuel(dateDebut65Ans, nacAvant, "IDP", "RTE", enfantACharge);

                if (niss != null) {
                    DaoFunctionality.getNascaDao().updateCaracteristiquesPensionForProfilV3(StringUtils.isEmpty(regimeDB.trim()) ? null : regimeDB,
                            "P45", StringUtils.isEmpty(naturePensionDB.trim()) ? null : naturePensionDB,
                            "TLM", enfantACharge, niss);
                }
            }
        } else {
            niss = DaoFunctionality.getNascaDao().getNissPasAtteintAgePensionForProfilV3Annuel(dateDebut65Ans, nacAvant, regimeDB, naturePensionDB, enfantACharge);

            if (niss == null) {
                niss = DaoFunctionality.getNascaDao().getNissPasAtteintAgePensionForProfilV3Annuel(dateDebut65Ans, nacAvant, "IDP", "RTE", enfantACharge);

                if (niss != null) {
                    DaoFunctionality.getNascaDao().updateCaracteristiquesPensionForProfilV3(StringUtils.isEmpty(regimeDB.trim()) ? null : regimeDB,
                            "P45", StringUtils.isEmpty(naturePensionDB.trim()) ? null : naturePensionDB,
                            "TLM", enfantACharge, niss);
                }
            }
        }

        addNissToMapOrSkipTest(mapNiss, id, libelle, fileName, niss, null);
    }

    private void addNissToMapOrSkipTest(Map<String, List<String>> mapNiss, String id, String libelle, String fileName, String niss, String ajoutPension) {
        if (niss != null) {
            List<String> params = new ArrayList<>();
            params.add(niss);
            params.add(ajoutPension);
            mapNiss.put(id, params);
        } else {
            initTest("Sc. " + id + " " + libelle + " - Sélection NISS", fileName);

            logSkip(Thread.currentThread().getStackTrace()[1].getFileName(), TestData.PAS_CAS_TROUVE_DB);

            finishTestExecution();
        }
    }

    void checkProfil(String id, String libelle, String nature, String superNature, String limiteArt11, String niss, String fileName) {
        if (niss != null) {
            initTest("Sc. " + id + " " + libelle + " - Check résultat", fileName);

            loginNasca();

            try {
                loadNissOrBce(niss);
                gestionClientPage.clickRaccourciResumeChangementProfil();
                logInfo("Check Profil", "");
                TableUtils.clickFirstRow("profilEvenementsTableId");
                if (gestionClientPage.getLibelleNatureProfil().equals(nature)) {
                    logSuccess("Nature " + nature + " pour " + niss + " OK");

                    if (gestionClientPage.getLibelleSuperNatureProfil().equals(superNature)) {
                        logSuccess("Super nature " + superNature + " pour " + niss + " OK");

                        if (limiteArt11 != null && StringUtils.isEmpty(limiteArt11.trim())) {
                            if (gestionClientPage.getLibelleLimiteArt11Profil().contains(limiteArt11)) {
                                logSuccess("Limite Article 11 " + limiteArt11 + " pour " + niss + " OK");
                            } else {
                                logFailed("Limite Article 11 " + limiteArt11 + " pour " + niss + " KO");
                            }
                        }
                    } else {
                        logFailed("Supernature " + superNature + " pour " + niss + " KO");
                    }
                } else {
                    logFailed("Nature " + nature + " pour " + niss + " KO");
                }
            } catch (Exception e) {
                logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
            } catch (AssertionError e) {
                logFailed("Nature " + nature + " pour " + niss + " KO");
            }

            finishTestExecution();
        }
    }

    static void ajoutPension(String niss, String regime, String nature, String enfantACharge, String dateDemande, String datePriseCours, String infoCarriere, String detenteur, String natureProfil) {
        try {
            loadNissOrBce(niss);
            gestionClientPage.clickMenuDonneesPension();
            gestionClientPage.clickMenuAjouterPension();
            gestionClientPage.fillDateDemandePension(dateDemande);
            gestionClientPage.fillDatePriseCoursPension(datePriseCours);
            gestionClientPage.selectRegimePension(regime);
            gestionClientPage.selectNaturePension(nature);
            gestionClientPage.selectInfoCarrierePension(infoCarriere);
            gestionClientPage.selectDetenteurPension(detenteur);
            gestionClientPage.selectEnfantAChargePension(enfantACharge);
            nascaPage.clickBtnSuivantWizard();
            gestionClientPage.fillNatureEvenement(natureProfil);
            gestionClientPage.clickBtnEnregistrerProfilWizard();
            nascaPage.clickBtnTerminerWizard();

            if (!TableUtils.isTableVide("table-pensions")) {
                logSuccess("Ajout pension OK");
            } else {
                logFailed("Ajout pension KO");
            }

        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }
}