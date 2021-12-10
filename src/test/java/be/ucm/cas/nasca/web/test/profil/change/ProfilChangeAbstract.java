package be.ucm.cas.nasca.web.test.profil.change;

import be.ucm.cas.nasca.web.test.comptabilite.ComptabiliteTestBase;
import be.ucm.cas.nasca.web.test.enrolement.pp.EnrolementPPTestBase;
import be.ucm.cas.nasca.web.test.profil.ProfilTestBase;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.SeleniumUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.*;

public abstract class ProfilChangeAbstract extends ProfilTestBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    void changeRDA(String codenature,
                   String regime, String reductiontype,
                   String datedebut1, String nature2, String reductiontype2,
                   String datedebut2, String controle1, String cotisation1, String cotisation1b, String controle2, String cotisation2, String cotisation2b,
                   String nature) {
        loginNasca();

        DaoFunctionality.getNascaDao().updateDemandeEnEncodage();

        String niss;

        if (nature2.contains(TestData.NATURE_PROFILE_MAXI_STATUT)) {
            niss = DaoFunctionality.getNascaDao().getNissMaxiStatutDataSet(regime, null, datedebut1);

            doModificationProfil(niss, datedebut1, reductiontype, TestData.NATURE_PROFILE_CONJOINT_MAXI_STATUT, nature);
        } else {
            niss = DaoFunctionality.getNascaDao().getNissChangementNatureProfil(codenature, datedebut1, regime, reductiontype);
        }

        niss = searchAndModifProfil(niss, codenature, datedebut1, regime, nature, reductiontype);

        checkCoti(niss, nature2, datedebut2, reductiontype2, controle1, cotisation1, cotisation1b, controle2, cotisation2, cotisation2b);
    }

    private void doModificationProfil(String niss, String datedebut1, String reductiontype, String nature1, String nature2) {
        if (niss != null) {
            String date = DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, DateUtils.doConvertStringtoDatTimeProfil(datedebut1).toDate());
            ProfilTestBase.doModificationLigneProfil(niss, nature1, nature2, date, reductiontype);
        }
    }

    private void checkCoti(String niss, String nature2, String datedebut2, String reductiontype2, String controle1,
                           String cotisation1, String cotisation1b, String controle2, String cotisation2, String cotisation2b) {
        if (niss != null) {
            ProfilTestBase.doChangeProfil(niss, nature2, datedebut2, reductiontype2);

            EnrolementPPTestBase.checkCotisationTrimestrePPChangementProfil(niss, controle1, TestData.TYPE_CREANCE_ORDINAIRE, cotisation1, cotisation1b, null, false);
            if (controle2 != null && !StringUtils.isEmpty(controle2.trim())) {
                EnrolementPPTestBase.checkCotisationTrimestrePPChangementProfil(niss, controle2, TestData.TYPE_CREANCE_ORDINAIRE, cotisation2, cotisation2b, null, false);
            }
        } else {
            logSkip(Thread.currentThread().getStackTrace()[1].getMethodName(), TestData.PAS_CAS_TROUVE_DB);
        }
    }

    void changeRDE(String codenature,
                   String reductiontype, String datedebut1, String annee1,
                   String revenu1, String typeRevenu1, String annee2, String revenu2,
                   String typeRevenu2, String annee3, String revenu3,
                   String typeRevenu3, String annee4, String revenu4,
                   String typeRevenu4, String nature2, String reductiontype2,
                   String datedebut2, String controle1, String cotisationOrd1,
                   String cotisationRegul1,
                   String nature) {
        loginNasca();

        DaoFunctionality.getNascaDao().updateDemandeEnEncodage();

        String niss;

        DateTime datePlus3Ans = DateUtils.doConvertStringtoDatTimeProfil(datedebut1).plusYears(3);

        String datePlus3AnsStr = DateUtils.doFormat(TestData.DATE_FORMAT_XML, datePlus3Ans.toDate());

        if (nature2.contains(TestData.NATURE_PROFILE_MAXI_STATUT)) {
            niss = DaoFunctionality.getNascaDao().getNissMaxiStatutDataSet(null, null, datedebut1);
            doModificationProfil(niss, datedebut1, reductiontype, TestData.NATURE_PROFILE_MAXI_STATUT, nature);
        } else {
            niss = DaoFunctionality.getNascaDao().getNissChangementNatureProfil(codenature, datePlus3AnsStr, null, reductiontype);
        }

        if (niss == null) {
            niss = DaoFunctionality.getNascaDao().getNissChangementNatureProfil(codenature, datePlus3AnsStr, null, "");

            ProfilTestBase.doModificationLigneProfil(niss, SeleniumUtils.getNatureMenuProfilfromCodeDB(codenature), null, null, reductiontype);
        }

        Map<Integer, List<String>> mapElementRevenu = new LinkedHashMap<>();

        boolean revenuPresume = traitementAnnee(annee1, revenu1, typeRevenu1, mapElementRevenu, annee2, revenu2, typeRevenu2,
                annee3, revenu3, typeRevenu3, annee4, revenu4, typeRevenu4);

        if (niss == null) {
            niss = DaoFunctionality.getNascaDao().getNissChangementNatureProfil(codenature, datedebut1, null, "");

            ProfilTestBase.doModificationLigneProfil(niss, nature, null, null, reductiontype);
        }

        doChangementProfil(niss, nature, datedebut1, datedebut2, reductiontype, revenuPresume, nature2, reductiontype2,
                mapElementRevenu, controle1, cotisationOrd1, cotisationRegul1);
    }

    void changeMaxiStatutRDA(String codenature,
                             String regime, String reductiontype,
                             String datedebut1, String nature2, String reductiontype2,
                             String datedebut2, String controle1, String cotisation1, String cotisation1b,
                             String controle2, String cotisation2, String cotisation2b,
                             String nature) {
        loginNasca();

        DaoFunctionality.getNascaDao().updateDemandeEnEncodage();

        String niss = DaoFunctionality.getNascaDao().getNissChangementNatureProfil(codenature, datedebut1, regime, reductiontype);

        niss = searchAndModifProfil(niss, codenature, datedebut1, regime, nature, reductiontype);

        checkCoti(niss, nature2, datedebut2, reductiontype2, controle1, cotisation1, cotisation1b, controle2, cotisation2, cotisation2b);
    }

    private String searchAndModifProfil(String niss, String codenature, String datedebut1, String regime, String nature, String reductiontype) {
        if (niss == null) {
            niss = DaoFunctionality.getNascaDao().getNissChangementNatureProfil(codenature, datedebut1, regime, "");

            ProfilTestBase.doModificationLigneProfil(niss, nature, null, null, reductiontype);
        }
        return niss;
    }

    void doChangementProfil(String niss, String nature, String datedebut1, String datedebut2, String reductiontype, boolean revenuPresume,
                            String nature2, String reductiontype2, Map<Integer, List<String>> mapElementRevenu, String controle1, String cotisationOrd1,
                            String cotisationRegul1) {
        try {
            Assert.assertNotNull(niss);
            ProfilTestBase.doModificationLigneProfil(niss, nature, null, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, DateUtils
                    .doConvertStringtoDatTimeProfil(datedebut1)
                    .toDate()), reductiontype);

            int cotisationsNonSoldees = Integer.valueOf(DaoFunctionality.getNascaDao().countEntiteComptableCotisationsNonSoldees(niss));

            traitementAffectionPaiement(revenuPresume, cotisationsNonSoldees, niss);

            ProfilTestBase.doChangeProfilAndAjoutRevenu(niss, nature2, datedebut2, reductiontype2, mapElementRevenu, null, TestData.STATUT_REVENU_A_REGULARISER);

            EnrolementPPTestBase.checkCotisationTrimestrePPChangementProfil(niss, controle1, TestData.TYPE_CREANCE_ORDINAIRE, cotisationOrd1, null, null, false);
            if (cotisationRegul1 != null && !StringUtils.isEmpty(cotisationRegul1.trim())) {
                if (revenuPresume) {
                    EnrolementPPTestBase.checkCotisationTrimestrePPChangementProfil(niss, controle1, TestData.TYPE_CREANCE_PRESUMEE, cotisationRegul1, null, null, false);
                } else {
                    EnrolementPPTestBase.checkCotisationTrimestrePPChangementProfil(niss, controle1, TestData.TYPE_CREANCE_REGULARISATION, cotisationRegul1, null, null, false);
                }
            }
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logSkip(Thread.currentThread().getStackTrace()[1].getMethodName(), TestData.PAS_CAS_TROUVE_DB);
        }
    }

    boolean traitementAnnee(String annee1, String revenu1, String typeRevenu1,
                            Map<Integer, List<String>> mapElementRevenu, String annee2, String revenu2,
                            String typeRevenu2, String annee3, String revenu3, String typeRevenu3, String annee4,
                            String revenu4, String typeRevenu4) {
        boolean revenuPresume = false;
        if (annee1 != null && !StringUtils.isEmpty(annee1.trim())) {
            revenuPresume = isRevenuPresume(typeRevenu1);
            mapElementRevenu.put(1, addToListElement(annee1, revenu1, typeRevenu1));
        }

        if (annee2 != null && !StringUtils.isEmpty(annee2.trim())) {
            revenuPresume = isRevenuPresume(typeRevenu2);
            mapElementRevenu.put(2, addToListElement(annee2, revenu2, typeRevenu2));
        }

        if (annee3 != null && !StringUtils.isEmpty(annee3.trim())) {
            revenuPresume = isRevenuPresume(typeRevenu3);
            mapElementRevenu.put(3, addToListElement(annee3, revenu3, typeRevenu3));
        }

        if (annee4 != null && !StringUtils.isEmpty(annee4.trim())) {
            revenuPresume = isRevenuPresume(typeRevenu4);
            mapElementRevenu.put(4, addToListElement(annee4, revenu4, typeRevenu4));
        }

        return revenuPresume;
    }

    private void traitementAffectionPaiement(boolean revenuPresume, int cotisationsNonSoldees, String niss) {
        if (revenuPresume && cotisationsNonSoldees > 0) {
            DateTime currentDateTime = new DateTime();
            Date yesterday = currentDateTime.minusDays(3).toDate();
            String batchdate = DateUtils.doFormat(TestData.DATE_FORMAT_XML, yesterday);
            DaoFunctionality.getNascaDao().updateAffectationComptable(batchdate);

            yesterday = currentDateTime.minusDays(1).toDate();
            batchdate = DateUtils.doFormat(TestData.DATE_FORMAT_XML, yesterday);
            DaoFunctionality.getNascaDao().prepareAffectationComptable(batchdate);

            ComptabiliteTestBase.doAffectationPaiementTotal(niss, true, false, DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, yesterday));
        }
    }

    void changeMultipleSuppression(String typeEvenement0,
                                   String codeNature0, String codeReduction0, String dateDeb0,
                                   String typeEvenement1, String codeNature1, String codeReduction1,
                                   String dateDeb1, String typeEvenement2, String codeNature2,
                                   String codeReduction2, String dateDeb2, String typeEvenement3,
                                   String codeNature3, String codeReduction3, String dateDeb3,
                                   String typeEvenement4, String codeNature4, String codeReduction4,
                                   String dateDeb4, String typeEvenement5, String codeNature5,
                                   String codeReduction5, String dateDeb5, String typeEvenement6,
                                   String codeNature6, String codeReduction6, String dateDeb6,
                                   String controleTrimestre1, String controleCotisation1,
                                   String controleCodenature1, String controleTrimestre2,
                                   String controleCotisation2, String controleCodenature2) {
        loginNasca();

        DaoFunctionality.getNascaDao().updateDemandeEnEncodage();

        String niss = null;

        if ((codeNature1 != null && codeNature1.contains(TestData.NATURE_PROFILE_MAXI_STATUT)) ||
                (codeNature2 != null && codeNature2.contains(TestData.NATURE_PROFILE_MAXI_STATUT))) {

            niss = DaoFunctionality.getNascaDao().getNissMaxiStatutDataSet(null, codeReduction1, dateDeb0);

            if (niss != null) {
                String date = DateUtils.doFormat(TestData.DATE_FORMAT_YEAR, DateUtils.doConvertStringtoDatTimeProfil(dateDeb0).toDate());
                if (codeNature1 != null && codeNature1.contains(TestData.NATURE_PROFILE_MAXI_STATUT)) {
                    ProfilTestBase.doModificationLigneProfil(niss, codeNature1, SeleniumUtils.getNatureMenuProfilfromCodeDB(codeNature0), date, codeReduction0);
                } else {
                    ProfilTestBase.doModificationLigneProfil(niss, codeNature2, SeleniumUtils.getNatureMenuProfilfromCodeDB(codeNature0), date, codeReduction0);
                }
            }
        }

        Map<Integer, List<String>> mapElementAjout = new LinkedHashMap<>();

        if (typeEvenement1 != null && !StringUtils.isEmpty(typeEvenement1.trim())) {
            mapElementAjout.put(1, addToListElement(dateDeb1, typeEvenement1, codeNature1, codeReduction1));
        }

        if (typeEvenement2 != null && !StringUtils.isEmpty(typeEvenement2.trim())) {
            mapElementAjout.put(2, addToListElement(dateDeb2, typeEvenement2, codeNature2, codeReduction2));
        }

        if (typeEvenement3 != null && !StringUtils.isEmpty(typeEvenement3.trim())) {
            mapElementAjout.put(3, addToListElement(dateDeb3, typeEvenement3, codeNature3, codeReduction3));
        }

        if (typeEvenement4 != null && !StringUtils.isEmpty(typeEvenement4.trim())) {
            mapElementAjout.put(4, addToListElement(dateDeb4, typeEvenement4, codeNature4, codeReduction4));
        }

        Map<Integer, List<String>> mapElementSuppression = new LinkedHashMap<>();

        mapElementSuppression.put(1, addToListElement(dateDeb5, typeEvenement5, codeNature5, doTransformReduction(codeReduction5)));

        if (typeEvenement6 != null && !org.apache.commons.lang3.StringUtils.isEmpty(typeEvenement6.trim())) {
            mapElementSuppression.put(2, addToListElement(dateDeb6, typeEvenement6, codeNature6, doTransformReduction(codeReduction6)));
        }

        if (niss == null) {
            if (typeEvenement0.contains("CES")) {
                niss = DaoFunctionality.getNascaDao().getNissChangementNatureProfilCessation(dateDeb0);
            } else {
                niss = DaoFunctionality.getNascaDao().getNissChangementNatureProfil(codeNature0, dateDeb0, null, codeReduction0);
            }
        }

        if (niss == null) {
            niss = DaoFunctionality.getNascaDao().getNissChangementNatureProfil(codeNature0, dateDeb0, null, "");

            ProfilTestBase.doModificationLigneProfil(niss, SeleniumUtils.getNatureMenuProfilfromCodeDB(codeNature0), null, null, codeReduction0);
        }

        try {
            Assert.assertNotNull(niss);
            doChangementMultiple(mapElementAjout, mapElementSuppression, niss);

            EnrolementPPTestBase.checkCotisationTrimestrePPChangementProfil(niss, controleTrimestre1, TestData.TYPE_CREANCE_ORDINAIRE, controleCotisation1, null, controleCodenature1, false);
            EnrolementPPTestBase.checkCotisationTrimestrePPChangementProfil(niss, controleTrimestre2, TestData.TYPE_CREANCE_ORDINAIRE, controleCotisation2, null, controleCodenature2, false);
        } catch (AssertionError e) {
            LOGGER.error(e.getMessage());
            logSkip(Thread.currentThread().getStackTrace()[1].getMethodName(), TestData.PAS_CAS_TROUVE_DB);
        }
    }

    private void doChangementMultiple(Map<Integer, List<String>> mapElementAjout, Map<Integer, List<String>> mapElementSuppression, String niss) {
        if (!mapElementAjout.isEmpty()) {
            ProfilTestBase.doChangementProfilMultipleAjout(niss, mapElementAjout);
            ProfilTestBase.doChangementProfilMultipleSuppression(niss, mapElementSuppression, false);
        } else {
            ProfilTestBase.doChangementProfilMultipleSuppression(niss, mapElementSuppression, true);
        }
    }

    List<String> addToListElement(String dateDeb, String typeEvenement, String codeNature, String codeReduction) {
        List<String> listElement = new ArrayList<>();
        listElement.add(dateDeb);
        listElement.add(typeEvenement);
        listElement.add(codeNature);
        listElement.add(codeReduction);
        return listElement;
    }

    private List<String> addToListElement(String annee, String revenu, String typeRevenu) {
        List<String> listElement = new ArrayList<>();
        listElement.add(annee);
        listElement.add(revenu);
        listElement.add(typeRevenu);
        return listElement;
    }

    private Boolean isRevenuPresume(String typeRevenu) {
        return typeRevenu.equals(TestData.TYPE_REVENU_PRESUME);
    }
}