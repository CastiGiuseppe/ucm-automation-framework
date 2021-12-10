package be.ucm.cas.nasca.web.test.support;

import be.ucm.cas.nasca.web.test.support.dao.NascaDao;
import be.ucm.cas.openerp.web.test.support.OpenErpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class RunBatch {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    public static final String BEAN_NAME_BATCH = "batchRunner";

    private static SftpConnection sftp;
    private static String hostUrl;
    private static String user;
    private static String pwd;
    private static String machine;
    private static String envi;

    public static String getHostUrl() {
        return hostUrl;
    }

    public static void setHostUrl(String hostUrl) {
        RunBatch.hostUrl = hostUrl;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        RunBatch.user = user;
    }

    public static String getPwd() {
        return pwd;
    }

    public static void setPwd(String pwd) {
        RunBatch.pwd = pwd;
    }

    public String getMachine() {
        return machine;
    }

    public static void setMachine(String machine) {
        RunBatch.machine = machine;
    }

    public static String getEnvi() {
        return envi;
    }

    public static void setEnvi(String envi) {
        RunBatch.envi = envi;
    }

    public SftpConnection getSftp() {
        return sftp;
    }

    public void setSftp(SftpConnection sftp) {
        RunBatch.sftp = sftp;
    }

    public static void connectMachine(String host, String user, String pwd) {
        try {
            SftpConnection.createSession(host, user, pwd);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    // Restart server tomcat
    public static void restartTomcat() {
        connectBatchMachine();
        commandShutdownTomcat();

        connectBatchMachine();
        commandStartupTomcat();

        SeleniumUtils.waitForAction(100000);
    }

    private static void commandShutdownTomcat() {
        try {
            sftp.runCommand("./shutdown.sh");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static void commandStartupTomcat() {
        try {
            sftp.runCommand("./startup.sh");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    // Batch Enrôlement PM
    public static void runBatchEnrolementPM(String bcenumber) {
        connectBatchMachine();
        batchFileTraitementScript();

        DaoFunctionality.getNascaDao().deleteFichierInastiContributions();
        BatchUtils.deleteXmlBatch("000_019_Contributions_");

        connectBatchMachine();
        batchEnrolementPMPrepare(bcenumber);

        connectBatchMachine();
        batchEnrolementPMProcessing();
    }

    private static void batchEnrolementPMPrepare(String bcenumber) {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh enrolementPmAutomatiquePrepare " + bcenumber);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static void batchEnrolementPMProcessing() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh enrolementPmAutomatiqueProcessing");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static void batchFileTraitementScript() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh traitementXmlContribution");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    // Batch Enrôlement PP Génération Document
    public static void runBatchGenerationDocumentEnrolementPP(String numeroDossier, String dateExecution, boolean avis, boolean exo, boolean blocage) {
        connectBatchMachine();
        batchGenerateDocumentEnrolementPPPrepareManuel(numeroDossier, dateExecution);

        connectBatchMachine();
        batchGenerateDocumentEnrolementPPPrepare();

        generationDocumentEnrolementPP(avis, exo, blocage);
    }

    public static void runBatchGenerationDocumentEnrolementPPPrepareManuel(String numeroDossier, String dateExecution) {
        connectBatchMachine();
        batchGenerateDocumentEnrolementPPPrepareManuel(numeroDossier, dateExecution);
    }

    public static void runBatchGenerationDocumentEnrolementPPPrepareEtGeneration(boolean avis, boolean exo, boolean blocage) {
        connectBatchMachine();
        batchGenerateDocumentEnrolementPPPrepare();

        generationDocumentEnrolementPP(avis, exo, blocage);
    }

    private static void generationDocumentEnrolementPP(boolean avis, boolean exo, boolean blocage) {
        if (avis) {
            connectBatchMachine();
            batchGenerateDocumentEnrolementPP();
        }

        if (exo) {
            connectBatchMachine();
            batchGenerateDocumentEnrolementExoPP();
        }

        if (blocage) {
            connectBatchMachine();
            batchGenerateDocumentEnrolementBlocagePP();
        }
    }

    public static void runBatchGenerationDocumentAvisDebloque() {
        connectBatchMachine();
        batchGenerateAvisEcheanceDebloque();
    }

    private static void batchGenerateAvisEcheanceDebloque() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh generationAvisEcheancePp");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static void batchGenerateDocumentEnrolementPPPrepareManuel(String numero, String dateExecution) {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh documentPpAutomatiquePrepareManuel " + dateExecution + " " + numero);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static void batchGenerateDocumentEnrolementPPPrepare() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh generationDocumentEnrolementPpPrepare");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static void batchGenerateDocumentEnrolementPP() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh generationDocumentEnrolementPp");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static void batchGenerateDocumentEnrolementExoPP() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh generationDocumentEnrolementExoPp");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static void batchGenerateDocumentEnrolementBlocagePP() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh generationDocumentEnrolementBlocagePp");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    // Batch Enrôlement PP
    public static void runBatchEnrolementPP(String numeroDossier, String dateExecution) {
        connectBatchMachine();
        batchEnrolementPPAutomatiquePrepare(numeroDossier, dateExecution);

        connectBatchMachine();
        batchEnrolementPPAutomatique();
    }

    public static void runBatchEnrolementPPAutomatiquePrepare(String numeroDossier, String dateExecution) {
        connectBatchMachine();
        batchEnrolementPPAutomatiquePrepare(numeroDossier, dateExecution);
    }

    private static void connectBatchMachine() {
        BatchUtils.initContent();
        connectMachine(getHostUrl(), getUser(), getPwd());
    }

    public static void runBatchEnrolementPPAutomatique() {
        connectBatchMachine();
        batchEnrolementPPAutomatique();
    }

    private static void batchEnrolementPPAutomatiquePrepare(String numero, String dateExecution) {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh enrolementPpAutomatiquePrepareManuel " + dateExecution + " " + numero);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static void batchEnrolementPPAutomatique() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh enrolementPpAutomatique");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    // Batch déblocage avis d'échéance
    public static void runBatchGenerationAvisEcheanceDebloque() {
        connectBatchMachine();
        batchGenerationAvisEcheanceDebloque();
    }

    private static void batchGenerationAvisEcheanceDebloque() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh generationAvisEcheancePp");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    // Batch Majoration PP
    public static void runBatchMajorationPPTrimestriel(String niss, String date) {
        connectBatchMachine();
        batchMajorationsPP(niss, "PPT", date);
    }

    public static void runBatchMajorationPPAnnuel(String niss, String date) {
        connectBatchMachine();
        batchMajorationsPP(niss, "PPA", date);
    }

    private static void batchMajorationsPP(String niss, String type, String date) {
        try {
            DaoFunctionality daoini = new DaoFunctionality();
            daoini.setNascaDao((NascaDao) ApplicationContext.getAppCtx().getBean(DaoFunctionality.BEAN_NAME_FOR_NASCA_DAO));
            List<Map<String, Object>> numeroId = DaoFunctionality.getNascaDao().getNumerosfromNiss(niss);

            for (Map<String, Object> featureService : numeroId) {
                for (Map.Entry<String, Object> entry : featureService.entrySet()) {
                    sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh enrolementMajorations " + type + " " + date + " true " + entry.getValue().toString() );
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    // Batch Majoration PM
    public static void runBatchMajorationPM() {
        connectBatchMachine();
        batchMajorationsPM();
    }

    private static void batchMajorationsPM() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh enrolementMajorations PM true");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    // Batch Synchro OERP
    public static void runBatchSynchroOERP() {
        BatchUtils.iniContentErp();
        connectMachine(OpenErpUtils.getServerurl(), OpenErpUtils.getServeruser(), OpenErpUtils.getServerpass());
        batchSynchroOERP();
    }

    private static void batchSynchroOERP() {
        try {
            sftp.runCommandWithResult("./synchro_oerp/scripts/runBatch.sh");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    // Batch Rappel Paiement
    public static void runBatchRappelPaiements() {
        connectBatchMachine();
        batchRappelPaiements();
    }

    private static void batchRappelPaiements() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh rappel");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    // Batch Traitement fichier plat
    public static void runBatchExtractFluxFromFile() {
        connectBatchMachine();
        batchExtractFluxFromFile();
    }

    public static void runBatchAdaptInastiToNasca() {
        connectBatchMachine();
        batchAdaptInastiToNascaPrepare();

        connectBatchMachine();
        batchAdaptInastiToNascaProcessing();
    }

    private static void batchExtractFluxFromFile() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh extractFluxFromFile /data/"
                    + TestBase.getBatch().getMachine() + "/fluxin");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static void batchAdaptInastiToNascaPrepare() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh adaptInastiToNascaPrepare");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static void batchAdaptInastiToNascaProcessing() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh adaptInastiToNascaProcessing");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    // Batch Transfert IN PM
    public static void runBatchDecisionTransfertsInPM() {
        connectBatchMachine();
        batchDecisionTransfertsInPM();
    }

    private static void batchDecisionTransfertsInPM() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh traitementXmlDecisionTransfertsIn");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    // Batch Domiciliations
    public static void runBatchDomiciliations() {
        connectBatchMachine();
        batchDomiciliations();
    }

    private static void batchDomiciliations() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh domiciliations");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    // Batch Assimillation Maladie
    public static void runBatchRappelInastiAssiMaladie() {
        //document de rappel vers l'inasti a verifier ??  pour toutes les demandes assi.maladie avec date demande n-6mois et toujours pas de decision
        connectBatchMachine();
        batchRappelInastiAssiMaladie();
    }

    public static void runBatchRappelInastiAssiMaladie(Date date) {
        //batch avec argument date pour pouvoir bypasser la periode de date demande assi.maladie +6mois
        //document de rappel vers l'inasti a verifier ??  pour toutes les demandes assi.maladie avec date demande n-6mois et toujours pas de decision
        connectBatchMachine();
        batchRappelInastiAssiMaladie(date);
    }

    public static void runBatchNotificationInastiAssiMaladie() {
        //creation de notification pour toutes les demandes assi.maladie pour lequel un rappel a deja ete envoyé avec date envoi rappel n-6mois et toujours pas de decision
        connectBatchMachine();
        batchNotificationInastiAssiMaladie();
    }

    public static void runBatchNotificationInastiAssiMaladie(Date date) {
        //batch avec argument date pour pouvoir bypasser la periode de date demande assi.maladie +12mois
        //creation de notification pour toutes les demandes assi.maladie pour lequel un rappel a deja ete envoyé avec date envoi rappel n-6mois et toujours pas de decision
        connectBatchMachine();
        batchNotificationInastiAssiMaladie(date);
    }

    private static void batchRappelInastiAssiMaladie() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh rappelInastiAssimMaladie true");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static void batchRappelInastiAssiMaladie(Date date) {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh rappelInastiAssimMaladie true " + DateUtils.doFormat(TestData.DATE_NOFORMAT, date));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static void batchNotificationInastiAssiMaladie() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh notificationRappelInastiAssimMaladie true");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static void batchNotificationInastiAssiMaladie(Date date) {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh notificationRappelInastiAssimMaladie true " + DateUtils.doFormat(TestData.DATE_NOFORMAT, date));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    // Batch Changement Temps
    public static String checkDateMachine() {
        String date = null;
        connectBatchMachine();
        try {
            date = sftp.runCommandGetResult("date +%Y-%m-%d");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        SeleniumUtils.waitForActionCommon();
        return date;
    }

    public static void runBatchChangeDate(String date) {
        if (!RunBatch.checkDateMachine().contains(date)) {
            connectBatchMachine();
            batchChangeDate(date);
        }
    }

    // Batch dépistage
    public static void runBatchDepistageREM() {
        connectBatchMachine();
        batchDepistageCjtAidantEnvoiRem();
    }

    private static void batchChangeDate(String date) {
        try {
            sftp.runCommandWithResult("./changeDate.sh " + date);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        SeleniumUtils.waitForActionCommon();
    }

    // Batch dépistage
    private static void batchDepistageCjtAidantEnvoiRem() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh depistageCjtAidantEnvoiRem");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    // Batch Levée Majorations
    public static void runBatchMajDemandeLM() {
        connectBatchMachine();
        batchMajDemandeLmPaiement();
    }

    private static void batchMajDemandeLmPaiement() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh majDemandeLmPaiement");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    // Recouvrement Rappel
    public static void runBatchRecouvrementRappel(String dossierId, String typeDossier) {
        connectBatchMachine();
        batchRecouvrementRappel(dossierId, typeDossier);
    }

    private static void batchRecouvrementRappel(String dossierId, String typeDossier) {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh recouvrementRappel " + typeDossier + " false " + dossierId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void runBatchStatCourrier(String date) {
        connectBatchMachine();
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh calculStatistiquesCourrierEntrant  " + date);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    // Recouvrement Mise en Demeure
    public static void runBatchRecouvrementMed(String dossierId, String typeDossier) {
        connectBatchMachine();
        batchRecouvrementMed(dossierId, typeDossier);
    }

    private static void batchRecouvrementMed(String dossierId, String typeDossier) {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh recouvrementMed  " + typeDossier + " false " + dossierId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    // PLC
    public static void runBatchReceptionFichierViaxis() {
        connectBatchMachine();
        batchReceptionFichierViaxis();
    }

    private static void batchReceptionFichierViaxis() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh extractContractXmlInfosViaxis");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void runBatchInteractionsSignaletique() {
        connectBatchMachine();
        batchInteractionsSignaletique();
    }

    private static void batchInteractionsSignaletique() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh interactionsSignaletique");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void runBatchAnnuelCotisationsPlc() {
        connectBatchMachine();
        batchCalculAnnuelCotisationsPlcPrepare();

        connectBatchMachine();
        batchCalculAnnuelCotisationsPlc();
    }

    private static void batchCalculAnnuelCotisationsPlcPrepare() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh calculAnnuelCotisationsPlcPrepare");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static void batchCalculAnnuelCotisationsPlc() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh calculAnnuelCotisationsPlc");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void runBatchMiseAJourStatutDifferePlc() {
        connectBatchMachine();
        batchMiseAJourStatutDifferePlc();
    }

    private static void batchMiseAJourStatutDifferePlc() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh changementStatutContrat");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    // Flux A020
    public static void runBatchReceptionFluxA020Prepare() {
        connectBatchMachine();
        BatchReceptionFluxA020Prepare();
    }

    private static void BatchReceptionFluxA020Prepare() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh receptionFluxA020Prepare");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void runBatchReceptionFluxA020Processing() {
        connectBatchMachine();
        BatchReceptionFluxA020Processing();
    }

    private static void BatchReceptionFluxA020Processing() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh receptionFluxA020Processing");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    // Batch Plan Apurement
    public static void runBatchPlanApurementControleAcompte() {
        connectBatchMachine();
        batchPlanApurementControleAcompte();
    }

    private static void batchPlanApurementControleAcompte() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh batchPlanApurementControleAcompte");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void runBatchPlanApurementControleEcheance() {
        connectBatchMachine();
        batchPlanApurementControleEcheance();
    }

    private static void batchPlanApurementControleEcheance() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh batchPlanApurementControleEcheance");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void runBatchPlanApurementControleReport() {
        connectBatchMachine();
        batchPlanApurementControleReport();
    }

    private static void batchPlanApurementControleReport() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh batchPlanApurementControleReport");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void runBatchGenerationDocPlanApurement() {
        connectBatchMachine();
        batchGenerationDocPlanApurement();
    }

    private static void batchGenerationDocPlanApurement() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh generationDocPlanApurement");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    // Batch Profil V3
    public static void runBatchPensionTrimestrielPrepare(String trimestre) {
        connectBatchMachine();
        batchPensionTrimestrielPrepare(trimestre);
    }

    private static void batchPensionTrimestrielPrepare(String trimestre) {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh pensionBatchTrimestrielPrepare " + trimestre);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void runBatchPensionTrimestrielProcessing() {
        connectBatchMachine();
        batchPensionTrimestrielProcessing();
    }

    private static void batchPensionTrimestrielProcessing() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh pensionBatchTrimestrielProcessing");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void runBatchPensionAnnuelPrepare(String annee) {
        connectBatchMachine();
        batchPensionAnnuelPrepare(annee);
    }

    private static void batchPensionAnnuelPrepare(String annee) {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh pensionBatchAnnuelPrepare " + annee);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void runBatchPensionAnnuelProcessing() {
        connectBatchMachine();
        batchPensionAnnuelProcessing();
    }

    private static void batchPensionAnnuelProcessing() {
        try {
            sftp.runCommandWithResult("./Sources/nasca-batch/scripts/runBatch.sh pensionBatchAnnuelProcessing");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}