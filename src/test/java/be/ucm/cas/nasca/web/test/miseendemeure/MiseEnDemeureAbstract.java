package be.ucm.cas.nasca.web.test.miseendemeure;

import be.ucm.cas.nasca.web.test.enrolement.pm.EnrolementPMTestBase;
import be.ucm.cas.nasca.web.test.enrolement.pp.EnrolementPPTestBase;
import be.ucm.cas.nasca.web.test.impression.ImpressionTestBase;
import be.ucm.cas.nasca.web.test.rappels.RappelsTestBase;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.RunBatch;
import be.ucm.cas.nasca.web.test.support.TestData;
import be.ucm.cas.nasca.web.test.taches.TachesTestBase;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.util.List;


public abstract class MiseEnDemeureAbstract extends MiseEnDemeureTestBase {

    private static final String NOM_DOCUMENT = "ALL - Recouvrement Mise en Demeure";
    private static final String RECOUVREMENT = "Mise en demeure";

    void miseEnDemeurePMFromExcel(String batch, String requete,
                                  String solidaire, String stadeRecouvrement,
                                  String suspendu, String seuilDepasse,
                                  String procedureRecouvrement,
                                  String notification, String checkRecouvrement, String date) throws ParseException {

        loginNasca();

        String dateParametres = DateUtils.doFormatToString(TestData.DATE_FORMAT_SIMPLE, TestData.DATE_FORMAT_XML, date);

        List<String> params = getBceForMed(requete, stadeRecouvrement, solidaire, suspendu, date);

        if (params != null) {
            String bce = params.get(0);
            String periode = params.get(1);

            String dossierId = DaoFunctionality.getNascaDao().getDossierIkFromBce(bce);

            RappelsTestBase.checkRecouvrementPM(stadeRecouvrement, bce, periode);

            addParametresMiseEnDemeure(dateParametres, Boolean.valueOf(seuilDepasse), TestData.TYPE_PM, Boolean.valueOf(batch));

            if (Boolean.valueOf(batch)) {
                RunBatch.runBatchRecouvrementMed(dossierId, "CS");
            }

            if (Boolean.valueOf(procedureRecouvrement)) {
                EnrolementPMTestBase.checkRecouvrementPM(bce, RECOUVREMENT, periode);

                ImpressionTestBase.checkImpression(bce, TestData.TYPE_BCE, null, null, TestData.TYPE_SORTIE_EXTERNE, TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT, null);
            }

            if (notification != null && !StringUtils.isEmpty(notification.trim())) {
                TachesTestBase.searchAndFinishNotification(bce, notification);
            }

            if (checkRecouvrement != null && !StringUtils.isEmpty(checkRecouvrement.trim())) {
                EnrolementPMTestBase.checkRecouvrementPM(bce, checkRecouvrement, periode);
            }
        } else {
            logSkip(Thread.currentThread().getStackTrace()[1].getFileName(), TestData.PAS_CAS_TROUVE_DB);
        }
    }

    void miseEnDemeurePPFromExcel(String batch, String requete,
                                  String solidaire, String stadeRecouvrement,
                                  String suspendu, String seuilDepasse,
                                  String procedureRecouvrement, String procedureRecouvrementFrais,
                                  String notification, String checkRecouvrement, String date) throws ParseException {
        loginNasca();

        String dateParametres = DateUtils.doFormatToString(TestData.DATE_FORMAT_SIMPLE, TestData.DATE_FORMAT_XML, date);

        List<String> params = getNissForMed(requete, stadeRecouvrement, solidaire, suspendu, date);

        if (params != null) {
            String niss = params.get(0);
            String dossierId = DaoFunctionality.getNascaDao().getDossierIkFromNiss(niss);

            RappelsTestBase.checkRecouvrementPP(stadeRecouvrement, niss);

            addParametresMiseEnDemeure(dateParametres, Boolean.valueOf(seuilDepasse), TestData.TYPE_PP, Boolean.valueOf(batch));

            if (Boolean.valueOf(batch)) {
                RunBatch.runBatchRecouvrementMed(dossierId, "CP");
            }

            if (Boolean.valueOf(procedureRecouvrement)) {
                EnrolementPPTestBase.checkRecouvrementPP(niss, RECOUVREMENT);

                ImpressionTestBase.checkImpression(niss, TestData.TYPE_NISS, null, null, TestData.TYPE_SORTIE_EXTERNE, TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT, null);
            }

            if (Boolean.valueOf(procedureRecouvrementFrais)) {
                EnrolementPPTestBase.checkRecouvrementPP(niss, RECOUVREMENT);

                ImpressionTestBase.checkImpression(niss, TestData.TYPE_NISS, null, null, TestData.TYPE_SORTIE_EXTERNE, TestData.ETAT_A_IMPRIMER, NOM_DOCUMENT, null);
            }

            if (notification != null && !StringUtils.isEmpty(notification.trim())) {
                TachesTestBase.searchAndFinishNotification(niss, notification);
            }

            if (checkRecouvrement != null && !StringUtils.isEmpty(checkRecouvrement.trim())) {
                EnrolementPPTestBase.checkRecouvrementPP(niss, checkRecouvrement);
            }
        } else {
            logSkip(Thread.currentThread().getStackTrace()[1].getFileName(), TestData.PAS_CAS_TROUVE_DB);
        }
    }
}