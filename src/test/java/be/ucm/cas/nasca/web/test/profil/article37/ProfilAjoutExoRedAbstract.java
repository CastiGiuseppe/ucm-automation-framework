package be.ucm.cas.nasca.web.test.profil.article37;

import be.ucm.cas.nasca.web.test.enrolement.pp.EnrolementPPTestBase;
import be.ucm.cas.nasca.web.test.profil.ProfilTestBase;
import be.ucm.cas.nasca.web.test.revenu.RevenuTestBase;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.apache.commons.lang.StringUtils;

public abstract class ProfilAjoutExoRedAbstract extends ProfilTestBase {

    void ajoutExoReductionArcticle37(String codenature, String regime, String reductiontype,
                                     String datedebut1,
                                     String reductiontype2, String raisonExoRedution, String datedebut2,
                                     String datefin2, String controle1, String cotisation1, String cotisation1b,
                                     String controle2, String cotisation2, String cotisation2b,
                                     String nature) {
        loginNasca();

        DaoFunctionality.getNascaDao().updateDemandeEnEncodage();

        String niss = DaoFunctionality.getNascaDao().getNissChangementNatureProfil(codenature, datedebut1, regime, reductiontype);

        if (niss == null) {
            niss = DaoFunctionality.getNascaDao().getNissChangementNatureProfil(codenature, datedebut1, regime, "");

            ProfilTestBase.doModificationLigneProfil(niss, nature, null, null, reductiontype);
        }

        if (niss != null) {
            RevenuTestBase.ajouterRevenuSansErreur(niss, controle1.substring(0,4), "0", TestData.TYPE_REVENU_FISCAL, TestData.STATUT_REVENU_A_REGULARISER, TestData.SOURCE_REVENU_CONTRIBUTIONS, true, null, "false");

            ProfilTestBase.doAjoutExoReductionArticle37Profil(niss, datedebut2, datefin2, reductiontype2, raisonExoRedution);

            EnrolementPPTestBase.checkCotisationTrimestrePPChangementProfil(niss, controle1, TestData.TYPE_CREANCE_ORDINAIRE, cotisation1, cotisation1b, null, true);
            if (controle2 != null && !StringUtils.isEmpty(controle2.trim())) {
                EnrolementPPTestBase.checkCotisationTrimestrePPChangementProfil(niss, controle2, TestData.TYPE_CREANCE_ORDINAIRE, cotisation2, cotisation2b, null, false);
            }
        } else {
            logSkip(Thread.currentThread().getStackTrace()[1].getMethodName(), TestData.PAS_CAS_TROUVE_DB);
        }
    }
}