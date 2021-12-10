package be.ucm.cas.nasca.web.test.planapurement;

import be.ucm.cas.nasca.web.test.comptabilite.ComptabiliteTestBase;
import be.ucm.cas.nasca.web.test.impression.ImpressionTestBase;
import be.ucm.cas.nasca.web.test.support.*;
import be.ucm.cas.nasca.web.test.taches.TachesTestBase;
import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;

public abstract class PlanApurementAbstract extends PlanApurementTestBase {

    private static final String NOM_DOCUMENT_OCTROI = "ALL - Plan apurement octroi";
    private static final String NOM_DOCUMENT_CONTROLE = "ALL - Plan apurement controle";
    private static final String INFERIEUR = "inférieur";
    private static final String TOTAL = "total";
    private static final String SUPERIEUR = "supérieur";

    private String periode;
    private String batchdatePaiementAcompte;
    private String datePaiementAcompteStr;
    private String batchdateEcheanceAcompte;
    private String batchdatePaiementMensualite;
    private String datePaiementMensualiteStr;
    private String batchdateEcheanceMensualite;

    void doBeforeTest() {
        Calendar calJour = Calendar.getInstance();
        calJour.setTime(DateUtils.getMinusYearsMachine(1));
        setPeriode(String.valueOf(calJour.get(Calendar.YEAR)) + "/1");

        calJour = Calendar.getInstance();
        calJour.add(Calendar.DAY_OF_MONTH, 20);
        setBatchdatePaiementAcompte(DateUtils.doFormat(TestData.DATE_FORMAT_XML, calJour.getTime()));
        setDatePaiementAcompteStr(DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, calJour.getTime()));

        calJour = Calendar.getInstance();
        calJour.add(Calendar.DAY_OF_MONTH, 40);
        setBatchdateEcheanceAcompte(DateUtils.doFormat(TestData.DATE_FORMAT_XML, calJour.getTime()));

        calJour = Calendar.getInstance();
        calJour.add(Calendar.DAY_OF_MONTH, 50);
        setBatchdatePaiementMensualite(DateUtils.doFormat(TestData.DATE_FORMAT_XML, calJour.getTime()));
        setDatePaiementMensualiteStr(DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, calJour.getTime()));

        calJour = Calendar.getInstance();
        calJour.add(Calendar.DAY_OF_MONTH, 61);
        setBatchdateEcheanceMensualite(DateUtils.doFormat(TestData.DATE_FORMAT_XML, calJour.getTime()));
    }

    void doPlanApurement(String acompte, String paiementAcompte, String batchdatePaiementAcompte, String typeAffilie, String nissOrBce, String periode,
                         String datePaiementAcompteStr, String batchdateEcheanceAcompte, String mensualitePaye, String batchdatePaiementMensualite,
                         String datePaiementMensualiteStr, String batchdateEcheanceMensualite, String leveeMajoration) {
        boolean goToTrtMensualite = false;

        if (Boolean.valueOf(acompte)) {
            DateTime currentDateTime = new DateTime();
            Date yesterday = currentDateTime.minusDays(3).toDate();
            String batchdate = DateUtils.doFormat(TestData.DATE_FORMAT_XML, yesterday);


            switch (paiementAcompte) {
                case INFERIEUR:
                    affectationPaiement(batchdatePaiementAcompte, typeAffilie, nissOrBce, periode, datePaiementAcompteStr, "20", batchdate);
                    break;
                case TOTAL:
                    affectationPaiement(batchdatePaiementAcompte, typeAffilie, nissOrBce, periode, datePaiementAcompteStr, "30", batchdate);
                    break;
                default:
                    break;
            }

            RunBatch.runBatchChangeDate(batchdateEcheanceAcompte);

            RunBatch.runBatchPlanApurementControleAcompte();

            switch (paiementAcompte) {
                case INFERIEUR:
                    TachesTestBase.searchAndFinishNotification(nissOrBce, "Plan d’apurement : acompte erroné");
                    break;
                case TOTAL:
                    checkOctroiEchelonnementPA(nissOrBce);
                    checkPaiementAcompteEchelonnementPA(nissOrBce, periode, typeAffilie, datePaiementAcompteStr);
                    goToTrtMensualite = true;
                    break;
                default:
                    break;
            }
        } else {
            goToTrtMensualite = true;
        }

        if (goToTrtMensualite) {
            RunBatch.runBatchGenerationDocPlanApurement();
            DaoFunctionality.getNascaDao().updateAllTypeDocumentImpression();
            if (TestData.TYPE_PP.equals(typeAffilie)) {
                ImpressionTestBase.checkImpressionDernierDocument(nissOrBce, TestData.TYPE_NISS, null, null, null, TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT_OCTROI);
            } else {
                ImpressionTestBase.checkImpressionDernierDocument(nissOrBce, TestData.TYPE_BCE, null, null, null, TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT_OCTROI);
            }

            checkOctroiEchelonnementPA(nissOrBce);
            if (!"nul".equals(mensualitePaye)) {
                RunBatch.runBatchChangeDate(batchdatePaiementMensualite);

                DateTime currentDateTime = new DateTime();
                Date yesterday = currentDateTime.minusDays(3).toDate();
                String batchdate = DateUtils.doFormat(TestData.DATE_FORMAT_XML, yesterday);
                DaoFunctionality.getNascaDao().updateAffectationComptable(batchdate);

                DaoFunctionality.getNascaDao().prepareAffectationComptable(batchdatePaiementMensualite);

                switch (mensualitePaye) {
                    case INFERIEUR:
                        affectationPartielle(typeAffilie, nissOrBce, periode, datePaiementMensualiteStr);
                        break;
                    case TOTAL:
                    case SUPERIEUR:
                        affectationTotale(typeAffilie, nissOrBce, periode, datePaiementMensualiteStr);
                        break;
                    default:
                        break;
                }
            }

            RunBatch.runBatchChangeDate(batchdateEcheanceMensualite);

            RunBatch.runBatchPlanApurementControleEcheance();
            RunBatch.runBatchGenerationDocPlanApurement();

            switch (mensualitePaye) {
                case INFERIEUR:
                    checkDocument(typeAffilie, nissOrBce);
                    break;
                case TOTAL:
                case SUPERIEUR:
                    checkEchelonnementPA(typeAffilie, nissOrBce, periode, datePaiementMensualiteStr, leveeMajoration);
                    break;
                default:
                    break;

            }
        } else {
            if (Boolean.valueOf(acompte)) {
                checkAcompteNonPayeEchelonnementPA(nissOrBce);
            }
        }
    }

    private void checkDocument(String typeAffilie, String nissOrBce) {
        TachesTestBase.searchAndFinishNotification(nissOrBce, "Plan d’apurement : rappel envoyé");
        DaoFunctionality.getNascaDao().updateAllTypeDocumentImpression();
        if (TestData.TYPE_PP.equals(typeAffilie)) {
            ImpressionTestBase.checkImpressionDernierDocument(nissOrBce, TestData.TYPE_NISS, null, null, null, TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT_CONTROLE);
        } else {
            ImpressionTestBase.checkImpressionDernierDocument(nissOrBce, TestData.TYPE_BCE, null, null, null, TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT_CONTROLE);
        }
    }

    private void affectationTotale(String typeAffilie, String nissOrBce, String periode, String datePaiementMensualiteStr) {
        if (TestData.TYPE_PP.equals(typeAffilie)) {
            ComptabiliteTestBase.doAffectationPaiement(nissOrBce, SeleniumUtils.deleteFormat(periode), true, false, datePaiementMensualiteStr);
        } else {
            ComptabiliteTestBase.doAffectationPaiement(nissOrBce, SeleniumUtils.deleteFormat(periode.substring(0, 4)), true, false, datePaiementMensualiteStr);
        }
    }

    private void affectationPartielle(String typeAffilie, String nissOrBce, String periode, String datePaiementMensualiteStr) {
        if (TestData.TYPE_PP.equals(typeAffilie)) {
            ComptabiliteTestBase.doAffectationPaiementPartielle(nissOrBce, SeleniumUtils.deleteFormat(periode), true, false, datePaiementMensualiteStr, "10");
        } else {
            ComptabiliteTestBase.doAffectationPaiementPartielle(nissOrBce, SeleniumUtils.deleteFormat(periode.substring(0, 4)), true, false, datePaiementMensualiteStr, "10");
        }
    }

    private void affectationPaiement(String batchdatePaiementAcompte, String typeAffilie, String nissOrBce, String periode, String datePaiementAcompteStr, String montantAAffecter, String batchdate) {
        DaoFunctionality.getNascaDao().updateAffectationComptable(batchdate);

        DaoFunctionality.getNascaDao().prepareAffectationComptable(batchdatePaiementAcompte);

        RunBatch.runBatchChangeDate(batchdatePaiementAcompte);

        if (TestData.TYPE_PP.equals(typeAffilie)) {
            ComptabiliteTestBase.doAffectationPaiementPartielle(nissOrBce, SeleniumUtils.deleteFormat(periode), true, false, datePaiementAcompteStr, montantAAffecter);
        } else {
            ComptabiliteTestBase.doAffectationPaiementPartielle(nissOrBce, SeleniumUtils.deleteFormat(periode.substring(0, 4)), true, false, datePaiementAcompteStr, montantAAffecter);
        }
    }

    String getPeriode() {
        return periode;
    }

    private void setPeriode(String periode) {
        this.periode = periode;
    }

    String getBatchdatePaiementAcompte() {
        return batchdatePaiementAcompte;
    }

    private void setBatchdatePaiementAcompte(String batchdatePaiementAcompte) {
        this.batchdatePaiementAcompte = batchdatePaiementAcompte;
    }

    String getDatePaiementAcompteStr() {
        return datePaiementAcompteStr;
    }

    private void setDatePaiementAcompteStr(String datePaiementAcompteStr) {
        this.datePaiementAcompteStr = datePaiementAcompteStr;
    }

    String getBatchdateEcheanceAcompte() {
        return batchdateEcheanceAcompte;
    }

    private void setBatchdateEcheanceAcompte(String batchdateEcheanceAcompte) {
        this.batchdateEcheanceAcompte = batchdateEcheanceAcompte;
    }

    String getBatchdatePaiementMensualite() {
        return batchdatePaiementMensualite;
    }

    private void setBatchdatePaiementMensualite(String batchdatePaiementMensualite) {
        this.batchdatePaiementMensualite = batchdatePaiementMensualite;
    }

    String getDatePaiementMensualiteStr() {
        return datePaiementMensualiteStr;
    }

    private void setDatePaiementMensualiteStr(String datePaiementMensualiteStr) {
        this.datePaiementMensualiteStr = datePaiementMensualiteStr;
    }

    String getBatchdateEcheanceMensualite() {
        return batchdateEcheanceMensualite;
    }

    private void setBatchdateEcheanceMensualite(String batchdateEcheanceMensualite) {
        this.batchdateEcheanceMensualite = batchdateEcheanceMensualite;
    }
}