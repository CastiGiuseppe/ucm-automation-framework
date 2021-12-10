package be.ucm.cas.nasca.web.test.carriere;

import be.ucm.cas.nasca.web.test.support.EnumTypeTest;
import be.ucm.cas.nasca.web.test.support.TableUtils;
import be.ucm.cas.nasca.web.test.support.TestBase;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class CarriereTestBase extends TestBase {

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    public static void checkCarriere(String niss, String selection, String trimestre, String annee, String typeAffichage, String codeActivite, String revenu, String codeProvisoire, String anneeReference, String checkAutresDonnees, String typeRevenu) {
        try {
            homeMenuPage.clickOngletGestionClient();
            loadNissOrBce(niss);
            gestionClientPage.clickMenuConsultationCarriere();
            gestionClientPage.clickOuvrirAccordeonCritereRechercheConsultationCarriere();
            gestionClientPage.checkSelectionCarriere(selection);
            if ("annee".equals(selection)) {
                gestionClientPage.selectAnneeRechercheCarriere(annee);
            } else if ("trimestre".equals(selection)) {
                gestionClientPage.selectAnneeTrimestreRechercheCarriere(annee, trimestre);
            }
            gestionClientPage.checkSelectionTypeAffichageCarriere(typeAffichage);
            gestionClientPage.clickBtnRechercheConsultationCarriere();
            gestionClientPage.clickOuvrirAccordeonCritereRechercheConsultationCarriere();
            if (gestionClientPage.getCodeActiviteFromCarriere().equals(codeActivite)) {
                logSuccess("Check code activite pour " + niss, "Code activite OK (" + codeActivite + ")");
            } else {
                logFailed("Check code activite pour " + niss, "Code activite KO (" + codeActivite + ")");
            }

            Calendar calJour = Calendar.getInstance();
            calJour.setTime(new Date());

            if (!Boolean.valueOf(checkAutresDonnees)) {
                if ("Présumé".equals(typeRevenu)) {
                    if (gestionClientPage.getRevenuContributionFromCarriere() == null) {
                        logSuccess("Check revenu contribution pour " + niss, "Revenu contribution OK (aucun revenu)");
                    } else {
                        logFailed("Check revenu contribution pour " + niss, "Revenu contribution KO (aucun revenu)");
                    }
                } else {
                    if (revenu != null) {
                        if (!anneeReference.equals(calJour.get(Calendar.YEAR))) {
                            if (gestionClientPage.getRevenuContributionFromCarriere().equals(BigDecimal.valueOf(Double.valueOf(revenu)).setScale(2))) {
                                logSuccess("Check revenu contribution pour " + niss, "Revenu contribution OK (" + revenu + ")");
                            } else {
                                logFailed("Check revenu contribution pour " + niss, "Revenu contribution KO (" + revenu + ")");
                            }
                        }
                    } else {
                        if (gestionClientPage.getRevenuContributionFromCarriere() == null) {
                            logSuccess("Check revenu contribution pour " + niss, "Revenu contribution OK (" + revenu + ")");
                        } else {
                            logFailed("Check revenu contribution pour " + niss, "Revenu contribution KO (" + revenu + ")");
                        }
                    }
                }

                if (gestionClientPage.getAnneeReferenceFromCarriere().equals(anneeReference)) {
                    logSuccess("Check annee reference pour " + niss, "Annee reference OK (" + anneeReference + ")");
                } else {
                    logFailed("Check annee reference pour " + niss, "Annee reference KO (" + anneeReference + ")");
                }

                if (gestionClientPage.getCodeProvisoireFromCarriere() == Boolean.valueOf(codeProvisoire)) {
                    logSuccess("Check code provisoire pour " + niss, "Code provisoire OK (" + codeProvisoire + ")");
                } else {
                    logFailed("Check code provisoir pour " + niss, "Code provisoirn KO (" + codeProvisoire + ")");
                }
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    public static void checkCarriere(String niss, String selection, String trimestre, String annee, String typeAffichage, String codeActivite, String revenuCommunique, String revenuAdapte, String revenuCompose, String codeProvisoire, String anneeReference, String revenuContribution, String revenuBase) {
        try {
            homeMenuPage.clickOngletGestionClient();
            loadNissOrBce(niss);
            gestionClientPage.clickMenuConsultationCarriere();
            gestionClientPage.clickOuvrirAccordeonCritereRechercheConsultationCarriere();
            gestionClientPage.checkSelectionCarriere(selection);
            if ("annee".equals(selection)) {
                gestionClientPage.selectAnneeRechercheCarriere(annee);
            } else if ("trimestre".equals(selection)) {
                gestionClientPage.selectAnneeTrimestreRechercheCarriere(annee, trimestre);
            }
            gestionClientPage.checkSelectionTypeAffichageCarriere(typeAffichage);
            gestionClientPage.clickBtnRechercheConsultationCarriere();
            gestionClientPage.clickOuvrirAccordeonCritereRechercheConsultationCarriere();
            if (gestionClientPage.getCodeActiviteFromCarriere().equals(codeActivite)) {
                logSuccess("Check code activite pour " + niss, "Code activite OK (" + codeActivite + ")");
            } else {
                logFailed("Check code activite pour " + niss, "Code activite KO (" + codeActivite + ")");
            }

            if (revenuCommunique != null && !StringUtils.isEmpty(revenuCommunique.trim())) {
                if (gestionClientPage.getRevenuCommuniqueFromCarriere().equals(BigDecimal.valueOf(Double.valueOf(revenuCommunique)).setScale(2))) {
                    logSuccess("Check revenu communique pour " + niss, "Revenu communique OK (" + revenuCommunique + ")");
                } else {
                    logFailed("Check revenu communique pour " + niss, "Revenu communique KO (" + revenuCommunique + ")");
                }
            } else {
                if (gestionClientPage.getRevenuCommuniqueFromCarriere() == null) {
                    logSuccess("Check revenu communique pour " + niss, "Revenu communique OK (" + revenuCommunique + ")");
                } else {
                    logFailed("Check revenu communique pour " + niss, "Revenu communique KO (" + revenuCommunique + ")");
                }
            }

            if (revenuAdapte != null && !StringUtils.isEmpty(revenuAdapte.trim())) {
                if (gestionClientPage.getRevenuAdapteFromCarriere().equals(BigDecimal.valueOf(Double.valueOf(revenuAdapte)).setScale(2))) {
                    logSuccess("Check revenu adapte pour " + niss, "Revenu adapte OK (" + revenuAdapte + ")");
                } else {
                    logFailed("Check revenu adapte pour " + niss, "Revenu adapte KO (" + revenuAdapte + ")");
                }
            } else {
                if (gestionClientPage.getRevenuAdapteFromCarriere() == null) {
                    logSuccess("Check revenu adapte pour " + niss, "Revenu adapte OK (" + revenuAdapte + ")");
                } else {
                    logFailed("Check revenu adapte pour " + niss, "Revenu adapte KO (" + revenuAdapte + ")");
                }
            }

            if (revenuCompose != null && !StringUtils.isEmpty(revenuCompose.trim())) {
                if (gestionClientPage.getRevenuComposeFromCarriere().equals(BigDecimal.valueOf(Double.valueOf(revenuCompose)).setScale(2))) {
                    logSuccess("Check revenu compose pour " + niss, "Revenu compose OK (" + revenuCompose + ")");
                } else {
                    logFailed("Check revenu compose pour " + niss, "Revenu compose KO (" + revenuCompose + ")");
                }
            } else {
                if (gestionClientPage.getRevenuComposeFromCarriere() == null) {
                    logSuccess("Check revenu compose pour " + niss, "Revenu compose OK (" + revenuCompose + ")");
                } else {
                    logFailed("Check revenu compose pour " + niss, "Revenu compose KO (" + revenuCompose + ")");
                }
            }

            if (gestionClientPage.getCodeProvisoireFromCarriere() == Boolean.valueOf(codeProvisoire)) {
                logSuccess("Check code provisoire pour " + niss, "Code provisoire OK (" + codeProvisoire + ")");
            } else {
                logFailed("Check code provisoir pour " + niss, "Code provisoirn KO (" + codeProvisoire + ")");
            }

            if (gestionClientPage.getAnneeReferenceFromCarriere().equals(anneeReference)) {
                logSuccess("Check annee reference pour " + niss, "Annee reference OK (" + anneeReference + ")");
            } else {
                logFailed("Check annee reference pour " + niss, "Annee reference KO (" + anneeReference + ")");
            }

            if (revenuContribution != null && !StringUtils.isEmpty(revenuContribution.trim())) {
                if (gestionClientPage.getRevenuContributionFromCarriere().equals(BigDecimal.valueOf(Double.valueOf(revenuContribution)).setScale(2))) {
                    logSuccess("Check revenu contribution pour " + niss, "Revenu contribution OK (" + revenuContribution + ")");
                } else {
                    logFailed("Check revenu contribution pour " + niss, "Revenu contribution KO (" + revenuContribution + ")");
                }
            } else {
                if (gestionClientPage.getRevenuContributionFromCarriere() == null) {
                    logSuccess("Check revenu contribution pour " + niss, "Revenu contribution OK (" + revenuContribution + ")");
                } else {
                    logFailed("Check revenu contribution pour " + niss, "Revenu contribution KO (" + revenuContribution + ")");
                }
            }

            if (revenuBase != null && !StringUtils.isEmpty(revenuBase.trim())) {
                if (gestionClientPage.getRevenuBaseFromCarriere().equals(BigDecimal.valueOf(Double.valueOf(revenuBase)).setScale(2))) {
                    logSuccess("Check revenu base pour " + niss, "Revenu base OK (" + revenuBase + ")");
                } else {
                    logFailed("Check revenu base pour " + niss, "Revenu base KO (" + revenuBase + ")");
                }
            } else {
                if (gestionClientPage.getRevenuBaseFromCarriere() == null) {
                    logSuccess("Check revenu base pour " + niss, "Revenu base OK (" + revenuBase + ")");
                } else {
                    logFailed("Check revenu base pour " + niss, "Revenu base KO (" + revenuBase + ")");
                }
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    public static void checkAucuneCarriere(String niss, String selection, String trimestre, String annee, String typeAffichage) {
        try {
            homeMenuPage.clickOngletGestionClient();
            loadNissOrBce(niss);
            gestionClientPage.clickMenuConsultationCarriere();
            gestionClientPage.clickOuvrirAccordeonCritereRechercheConsultationCarriere();
            gestionClientPage.checkSelectionCarriere(selection);
            if ("annee".equals(selection)) {
                gestionClientPage.selectAnneeRechercheCarriere(annee);
            } else if ("trimestre".equals(selection)) {
                gestionClientPage.selectAnneeTrimestreRechercheCarriere(annee, trimestre);
            }
            gestionClientPage.checkSelectionTypeAffichageCarriere(typeAffichage);
            gestionClientPage.clickBtnRechercheConsultationCarriere();

            if (TableUtils.isTableVide("carPensionTable")) {
                logSuccess("Check aucune carriere " + niss, "Aucune carriere OK ");
            } else {
                logFailed("Check aucune carriere " + niss, "Aucune carriere KO ");
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }
}