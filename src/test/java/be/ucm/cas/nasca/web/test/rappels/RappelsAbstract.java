package be.ucm.cas.nasca.web.test.rappels;

import be.ucm.cas.nasca.web.test.enrolement.pm.EnrolementPMTestBase;
import be.ucm.cas.nasca.web.test.enrolement.pp.EnrolementPPTestBase;
import be.ucm.cas.nasca.web.test.impression.ImpressionTestBase;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.RunBatch;
import be.ucm.cas.nasca.web.test.support.TestData;
import be.ucm.cas.nasca.web.test.taches.TachesTestBase;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.util.List;

public abstract class RappelsAbstract extends RappelsTestBase {

    private static final String NOM_DOCUMENT = "ALL - Recouvrement Rappel";

    void rappelsPPFromExcel(String batch, String requete,
                            String solidaire, String suspendu,
                            String stadeRecouvrement, String seuil1Depasse,
                            String seuil2Depasse,
                            String procedureRecouvrement, String procedureRecouvrement1,
                            String procedureRecouvrement2, String procedureMajoEtCourrier,
                            String envoiCourrier, String envoiRECC, String notification, String checkRecouvrement,
                            String date) throws ParseException {
        loginNasca();

        String dateParametres = DateUtils.doFormatToString(TestData.DATE_FORMAT_SIMPLE, TestData.DATE_FORMAT_XML, date);

        List<String> params = getNissForRappels(requete, stadeRecouvrement, solidaire, suspendu, date);

        if (params != null) {
            String niss = params.get(0);
            String dossierId = DaoFunctionality.getNascaDao().getDossierIkFromNiss(niss);

            checkRecouvrementPP(stadeRecouvrement, niss);

            addParametresRappel(dateParametres, Boolean.valueOf(seuil1Depasse), Boolean.valueOf(seuil2Depasse), TestData.TYPE_PP, Boolean.valueOf(batch));

            if (Boolean.valueOf(batch)) {
                RunBatch.runBatchRecouvrementRappel(dossierId, "CP");
            }

            if (Boolean.valueOf(procedureRecouvrement) ||
                    Boolean.valueOf(procedureRecouvrement1) ||
                    Boolean.valueOf(procedureRecouvrement2) ||
                    Boolean.valueOf(procedureMajoEtCourrier)) {
                EnrolementPPTestBase.checkRecouvrementPP(niss, "Rappel");
            }

            doCheckImpressionAndNotif(procedureMajoEtCourrier, niss, TestData.TYPE_NISS, envoiCourrier, envoiRECC, notification);

            if (checkRecouvrement != null && !StringUtils.isEmpty(checkRecouvrement.trim())) {
                EnrolementPPTestBase.checkRecouvrementPP(niss, checkRecouvrement);
            }
        } else {
            logSkip(Thread.currentThread().getStackTrace()[1].getFileName(), TestData.PAS_CAS_TROUVE_DB);
        }
    }

    void rappelsPMFromExcel(String batch, String requete,
                            String solidaire, String suspendu,
                            String stadeRecouvrement, String seuil1Depasse,
                            String seuil2Depasse,
                            String procedureRecouvrement, String procedureRecouvrement1,
                            String procedureRecouvrement2, String procedureMajoEtCourrier,
                            String envoiCourrier, String envoiRECC, String notification, String checkRecouvrement,
                            String date) throws ParseException {

        loginNasca();

        String dateParametres = DateUtils.doFormatToString(TestData.DATE_FORMAT_SIMPLE, TestData.DATE_FORMAT_XML, date);

        List<String> params = getBceForRappels(requete, stadeRecouvrement, solidaire, suspendu, date);

        if (params != null) {
            String bce = params.get(0);
            String periode = params.get(1);
            String dossierId = DaoFunctionality.getNascaDao().getDossierIkFromBce(bce);

            checkRecouvrementPM(stadeRecouvrement, bce, periode);

            addParametresRappel(dateParametres, Boolean.valueOf(seuil1Depasse), Boolean.valueOf(seuil2Depasse), TestData.TYPE_PM, Boolean.valueOf(batch));

            if (Boolean.valueOf(batch)) {
                RunBatch.runBatchRecouvrementRappel(dossierId, "CS");
            }

            if (Boolean.valueOf(procedureRecouvrement) ||
                    Boolean.valueOf(procedureRecouvrement1) ||
                    Boolean.valueOf(procedureRecouvrement2) ||
                    Boolean.valueOf(procedureMajoEtCourrier)) {
                EnrolementPMTestBase.checkRecouvrementPM(bce, "Rappel", periode);
            }

            doCheckImpressionAndNotif(procedureMajoEtCourrier, bce, TestData.TYPE_BCE, envoiCourrier, envoiRECC, notification);

            if (checkRecouvrement != null && !StringUtils.isEmpty(checkRecouvrement.trim())) {
                EnrolementPMTestBase.checkRecouvrementPM(bce, checkRecouvrement, periode);
            }
        } else {
            logSkip(Thread.currentThread().getStackTrace()[1].getFileName(), TestData.PAS_CAS_TROUVE_DB);
        }
    }

    private void doCheckImpressionAndNotif(String procedureMajoEtCourrier, String bceorniss, String type, String envoiCourrier, String envoiRECC, String notification) {
        if (Boolean.valueOf(procedureMajoEtCourrier)) {
            ImpressionTestBase.checkImpression(bceorniss, type, null, null, TestData.TYPE_SORTIE_EXTERNE, TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT, null);
        }

        if (Boolean.valueOf(envoiCourrier)) {
            ImpressionTestBase.checkImpression(bceorniss, type, null, null, TestData.TYPE_SORTIE_EXTERNE, TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT, null);
        }

        if (Boolean.valueOf(envoiRECC)) {
            ImpressionTestBase.checkImpression(bceorniss, type, null, null, TestData.TYPE_SORTIE_EXTERNE, TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT, null);
        }

        if (notification != null && !StringUtils.isEmpty(notification.trim())) {
            TachesTestBase.searchAndFinishNotification(bceorniss, notification);
        }
    }
}