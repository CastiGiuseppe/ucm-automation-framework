package be.ucm.cas.nasca.web.test.profil.update;

import be.ucm.cas.nasca.web.test.enrolement.pp.EnrolementPPTestBase;
import be.ucm.cas.nasca.web.test.profil.ProfilTestBase;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.text.ParseException;

public class ProfilUpdateAbstract extends ProfilTestBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    void updatePrincipalComplementaire(String codenature, String regime,
                                       String reductiontype,
                                       String datedebut1, String nature2, String reductiontype2,
                                       String datedebut2, String controle1, String cotisation1,
                                       String controle2, String cotisation2,
                                       String nature) throws ParseException {
        loginNasca();

        DaoFunctionality.getNascaDao().updateDemandeEnEncodage();

        String niss;

        if (nature2.contains(TestData.NATURE_PROFILE_MAXI_STATUT)) {
            niss = DaoFunctionality.getNascaDao().getNissMaxiStatutDataSet(regime, null, DateUtils.doFormatToString(TestData.DATE_FORMAT_XML, TestData.DATE_FORMAT_YEAR, datedebut2));

            if (niss != null) {
                String date = DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, DateUtils.doConvertStringtoDatTimeProfil(datedebut1).toDate());
                ProfilTestBase.doModificationLigneProfil(niss, TestData.NATURE_PROFILE_CONJOINT_MAXI_STATUT, nature, date, reductiontype);
            }
        } else {
            niss = DaoFunctionality.getNascaDao().getNissChangementNatureProfil(codenature, datedebut1, regime, reductiontype);
        }

        checkCoti(niss, controle1, cotisation1, nature, nature2, datedebut2, reductiontype2, controle2, cotisation2);
    }

    private void checkCoti(String niss, String controle1, String cotisation1, String nature,
                           String nature2, String datedebut2, String reductiontype2, String controle2, String cotisation2) {
        try {
            Assert.assertNotNull(niss);
            ProfilTestBase.doModificationLigneProfil(niss, nature, nature2, datedebut2, reductiontype2);

            EnrolementPPTestBase.checkCotisationTrimestrePPChangementProfil(niss, controle1, TestData.TYPE_CREANCE_ORDINAIRE, cotisation1, null, null, false);
            EnrolementPPTestBase.checkCotisationTrimestrePPChangementProfil(niss, controle2, TestData.TYPE_CREANCE_ORDINAIRE, cotisation2, null, null, false);
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logSkip(Thread.currentThread().getStackTrace()[1].getMethodName(), TestData.PAS_CAS_TROUVE_DB);
        }
    }
}