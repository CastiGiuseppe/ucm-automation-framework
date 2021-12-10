package be.ucm.cas.nasca.web.test.revenu;

import be.ucm.cas.nasca.web.test.support.*;
import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;

public class RevenuTestBase extends TestBase {

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    public static void fillInRevenu(String annee, String revenuMontant,
                                    String date, String typeRevenu, String statutRevenu,
                                    String typeSourceRevenu, String dateProfil) {
        if (annee != null) {
            gestionClientPage.fillAnneeRevenu(annee);
        }
        gestionClientPage.fillRevenu(revenuMontant);
        if (date != null) {
            gestionClientPage.fillDateCommunicationRevenuCustom(date);
            gestionClientPage.fillDateDistributionCustom(date);
        } else {
            gestionClientPage.fillDateCommunicationRevenuAuto();
            gestionClientPage.fillDateDistributionRevenuAuto();
        }
        gestionClientPage.selectTypeRevenu(typeRevenu);
        gestionClientPage.selectStatutRevenu(statutRevenu);
        gestionClientPage.selectSourceRevenu(typeSourceRevenu);
        gestionClientPage.checkBoxRevenuAUtiliser();

        if (TestData.TYPE_REVENU_PRESUME.equals(typeRevenu)) {
            gestionClientPage.checkOrigineDeclare();

            if (dateProfil != null && (annee != null && annee.equals(dateProfil.substring(4)))) {
                DateTime dataTime = DateUtils
                        .doConvertStringtoDatTimeProfil(DateUtils.doFormat(
                                TestData.DATE_FORMAT_XML, DateUtils
                                        .doConvertStringtoDatTime(dateProfil)
                                        .toDate()));

                DateQuarters quarter = new DateQuarters(dataTime.toDate());

                switch (quarter.getQuarter()) {
                    case 2:
                        gestionClientPage.deselectCheckBoxTrim1();
                        break;
                    case 3:
                        gestionClientPage.deselectCheckBoxTrim1();
                        gestionClientPage.deselectCheckBoxTrim2();
                        break;
                    case 4:
                        gestionClientPage.deselectCheckBoxTrim1();
                        gestionClientPage.deselectCheckBoxTrim2();
                        gestionClientPage.deselectCheckBoxTrim3();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    static void ajouterRevenuAvecErreur(String niss, String annee, String revenu, String type, String statut, String source, boolean faireRegularisation, String dateCommunication) {
        try {
            loadNissOrBce(niss);

            gestionClientPage.clickMenuRevenu();
            gestionClientPage.clickAjouterModifierRevenu();
            gestionClientPage.clickBtnAjoutRevenuWizard();
            RevenuTestBase.fillInRevenu(annee, revenu, dateCommunication, type, statut, source, null);
            gestionClientPage.clickBtnEnregistrerModalRevenu();
            if (annee.compareTo(DateUtils.getYearNow()) > 0) {
                gestionClientPage.clickBtnEnregistrerModalRevenu();
                if (gestionClientPage.isErrorDate("DateCommunicationRevenu")) {
                    logSuccess("Ajout revenu avec message d'erreur OK");
                } else {
                    logFailed("Ajout revenu avec message d'erreur KO");
                }
            } else {
                if (gestionClientPage.isElementRevenuPresentWizard(annee, revenu, source, type, statut)) {
                    logSuccess("Ajout revenu wizard OK");
                } else {
                    logFailed("Ajout revenu wizard KO");
                }

                if (!faireRegularisation) {
                    gestionClientPage.selectCheckSortirSansRegul();
                } else {
                    nascaPage.clickBtnSuivantWizard();
                    checkMessageErreur();
                }
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    private static void checkMessageErreur() {
        if (nascaPage.isRevenuPasPrisEnCompte()) {
            logSuccess("Ajout revenu avec message d'erreur OK");
        } else {
            if (nascaPage.isMessageErreur("messageWizard")) {
                logSuccess("Ajout revenu avec message d'erreur OK");
            } else {
                if (TableUtils.isTableZeroElement("table-situation")) {
                    logSuccess("Ajout revenu sans régularisation OK");
                } else {
                    logFailed("Ajout revenu sans régularisation KO");
                }
            }
        }
    }

    public static void ajouterRevenuSansErreur(String niss, String annee, String revenu, String type, String statut, String source, boolean faireRegularisation, String dateCommunication, String courrier) {
        try {
            loadNissOrBce(niss);

            gestionClientPage.clickMenuRevenu();
            gestionClientPage.clickAjouterModifierRevenu();
            gestionClientPage.clickBtnAjoutRevenuWizard();
            RevenuTestBase.fillInRevenu(annee, revenu, dateCommunication, type, statut, source, null);
            gestionClientPage.clickBtnEnregistrerModalRevenu();
            gestionClientPage.clickBtnOuiModalRevenu();
            if (gestionClientPage.isElementRevenuPresentWizard(annee, revenu, source, type, statut)) {
                logSuccess("Ajout revenu wizard OK");
            } else {
                logFailed("Ajout revenu wizard KO");
            }

            if (!faireRegularisation) {
                gestionClientPage.selectCheckSortirSansRegul();
            } else {
                nascaPage.clickBtnSuivantWizard();
                nascaPage.clickBtnSuivantWizard();
                if (gestionClientPage.isCourrierDisplayed()) {
                    checkSuiteCourrier(courrier);
                }
                gestionClientPage.clickBtnRadioRetourRevenu();
            }

            nascaPage.clickBtnTerminerWizard();

            if (gestionClientPage.isElementRevenuPresent(annee, revenu, source, type)) {
                logSuccess("Ajout revenu récapitulatif OK");
            } else {
                logFailed("Ajout revenu récapitulatif KO");
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    private static void checkSuiteCourrier(String courrier) {
        if (!Boolean.valueOf(courrier)) {
            gestionClientPage.clickBtnProfilCourrierNon();
        } else {
            gestionClientPage.doSelectTypeCommunication();
            gestionClientPage.doSelectSousForme();
            gestionClientPage.fillDateCourrierDu(DateUtils.getDateToday());
            gestionClientPage.doSelectFilSortie();
        }
    }

    static void editerRevenuAvecErreur(String niss, String annee, String type, String statut, boolean faireRegularisation) {
        try {
            loadNissOrBce(niss);

            gestionClientPage.clickMenuRevenu();
            gestionClientPage.clickAjouterModifierRevenu();
            gestionClientPage.clickRevenuEdition(true, annee, type);
            gestionClientPage.clickClearStatutRevenu();
            gestionClientPage.selectStatutRevenu(statut);
            if (!faireRegularisation) {
                gestionClientPage.clickBtnEnregistrerModalRevenuAvecErreur();
                if (nascaPage.isMessageErreur("messageRevenuWizard")) {
                    logSuccess("Edition revenu avec message d'erreur OK");
                } else {
                    logFailed("Edition revenu avec message d'erreur KO");
                }
            } else {
                gestionClientPage.clickBtnEnregistrerModalRevenu();
                gestionClientPage.clickBtnOuiModalRevenu();
                if (gestionClientPage.isElementRevenuPresentWizard(annee, type, statut)) {
                    logSuccess("Ajout revenu wizard OK");
                } else {
                    logFailed("Ajout revenu wizard KO");
                }
                nascaPage.clickBtnSuivantWizard();
                if (nascaPage.isRevenuPasPrisEnCompte()) {
                    logSuccess("Ajout revenu avec message d'erreur OK");
                } else {
                    logFailed("Ajout revenu avec message d'erreur KO");
                }
            }
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }

    static boolean editerRevenuSansErreur(String niss, String annee, String type, String statut, boolean faireRegularisation) {
        try {
            boolean pasRecalcul = false;

            loadNissOrBce(niss);

            gestionClientPage.clickMenuRevenu();
            gestionClientPage.clickAjouterModifierRevenu();
            gestionClientPage.clickRevenuEdition(true, annee, type);
            if (TestData.TYPE_REVENU_PRESUME.equals(type)) {
                gestionClientPage.checkOrigineDeclare();
            }
            gestionClientPage.selectStatutRevenu(statut);
            gestionClientPage.clickBtnEnregistrerModalRevenu();
            if (gestionClientPage.isElementRevenuPresentWizard(annee, type, statut)) {
                logSuccess("Edition revenu wizard OK");
            } else {
                logFailed("Edition revenu wizard KO");
            }

            if (!faireRegularisation) {
                pasRecalcul = true;
                gestionClientPage.selectCheckSortirSansRegul();
            } else {
                nascaPage.clickBtnSuivantWizard();
                pasRecalcul = nascaPage.isRevenuPasPrisEnCompte();
                nascaPage.clickBtnSuivantWizard();
                if (!pasRecalcul) {
                    gestionClientPage.clickBtnProfilCourrierNon();
                }
                gestionClientPage.clickBtnRadioRetourRevenu();
            }

            nascaPage.clickBtnTerminerWizard();

            if (gestionClientPage.isElementRevenuPresent(annee, statut, type)) {
                logSuccess("Edition revenu récapitulatif OK");
            } else {
                logFailed("Edition revenu récapitulatif KO");
            }

            return pasRecalcul;
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
        return false;
    }

    void initialisationParametre() {
        DateTime currentDateTime = new DateTime();
        Date tomorrow = currentDateTime.plusMonths(3).toDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tomorrow);

        DaoFunctionality.getNascaDao().initialisationDateEnrolementMajoPPTrimestrielle(calendar.get(Calendar.YEAR), DateUtils.getQuarterNumber(DateUtils.doFormat(TestData.DATE_FORMAT_SIMPLE, tomorrow)), tomorrow);
    }
}