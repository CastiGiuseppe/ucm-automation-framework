package be.ucm.cas.nasca.web.test.profil.suppression;

import be.ucm.cas.nasca.web.test.enrolement.pp.EnrolementPPTestBase;
import be.ucm.cas.nasca.web.test.profil.ProfilTestBase;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.SeleniumUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;

public abstract class ProfilSuppressionAbstract extends ProfilTestBase {

    void suppression(String typeEvenement,
                     String nature1, String reductiontype1, String datedebut1,
                     String typeEvenement2, String nature2, String reductiontype2,
                     String datedebut2, String controle1, String cotisation1,
                     String natureASupprimer) throws ParseException {
        loginNasca();

        DaoFunctionality.getNascaDao().updateDemandeEnEncodage();

        String niss = DaoFunctionality.getNascaDao().getNissSuppressionNatureProfil(
                typeEvenement, nature1, reductiontype1, datedebut1,
                typeEvenement2, nature2, reductiontype2, datedebut2);

        if (!"CES".equals(typeEvenement2)) {
            niss = modifProfil(niss, nature1, datedebut1, reductiontype1, nature2, datedebut2, reductiontype2);
        }

        suppressionAndCheckCoti(niss, nature2, controle1, cotisation1, natureASupprimer);
    }

    void suppressionAndCheckCoti(String niss, String nature2, String controle1, String cotisation1, String natureASupprimer) {
        if (niss != null) {
            if (StringUtils.isEmpty(nature2.trim())) {
                ProfilTestBase.doSuppressionLigneProfil(niss, null);
                EnrolementPPTestBase.checkCotisationTrimestrePPChangementProfil(niss, controle1, TestData.TYPE_CREANCE_ORDINAIRE, cotisation1, null, null, false);
            } else {
                ProfilTestBase.doSuppressionLigneProfil(niss, natureASupprimer);
                EnrolementPPTestBase.checkCotisationTrimestrePPChangementProfil(niss, controle1, TestData.TYPE_CREANCE_ORDINAIRE, cotisation1, null, null, false);
            }
        } else {
            logSkip(Thread.currentThread().getStackTrace()[1].getMethodName(), TestData.PAS_CAS_TROUVE_DB);
        }
    }

    private String modifProfil(String niss, String nature1, String datedebut1, String reductiontype1, String nature2, String datedebut2, String reductiontype2) throws ParseException {
        if (niss == null) {
            niss = DaoFunctionality.getNascaDao().getNissChangementNatureProfil(nature1, datedebut1, null, "");

            if (niss != null) {
                ProfilTestBase.doModificationLigneProfil(niss, SeleniumUtils.getNatureMenuProfilfromCodeDB(nature1), null, null, reductiontype1);
                ProfilTestBase.doChangeProfil(niss, SeleniumUtils.getNatureProfilfromCodeDB(nature2), DateUtils.doFormatToString(TestData.DATE_FORMAT_YEAR,
                        TestData.DATE_FORMAT_XML,
                        datedebut2),
                        reductiontype2);
            }
        }
        return niss;
    }
}