package be.ucm.cas.nasca.web.test.profil.change;

import be.ucm.cas.nasca.web.test.enrolement.pp.EnrolementPPTestBase;
import be.ucm.cas.nasca.web.test.profil.ProfilTestBase;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.SeleniumUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProfilChangeMultipleAjoutTest extends ProfilChangeAbstract {

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Profil.xlsx", "chang multiple - ajouter", "Changement multiple - ajouter", "Princip-Compl-MaxiStat-Cess", null);
    }

    @Test(dataProvider = "data")
    public void changeMultipleAjout(String id, String typeEvenement0,
                                    String codeNature0, String codeReduction0, String dateDeb0,
                                    String typeEvenement1, String codeNature1, String codeReduction1,
                                    String dateDeb1, String typeEvenement2, String codeNature2,
                                    String codeReduction2, String dateDeb2,
                                    String controleTrimestre1, String controleCotisation1,
                                    String controleTrimestre2, String controleCotisation2,
                                    String controleTrimestre3, String controleCotisation3,
                                    String jour, String mois, String annee, String pivotdate,
                                    String baremepivot1, String baremepivot2, String baremepivot3, String libelle) {
        initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        DaoFunctionality.getNascaDao().updateDemandeEnEncodage();

        String niss;

        if (codeNature1 != null && codeNature1.contains(TestData.NATURE_PROFILE_MAXI_STATUT)) {
            niss = DaoFunctionality.getNascaDao().getNissMaxiStatutDataSet(
                    null, codeReduction1, dateDeb0);

            if (niss != null) {
                String date = DateUtils.doFormat(TestData.DATE_FORMAT_YEAR,
                        DateUtils.doConvertStringtoDatTimeProfil(dateDeb0)
                                .toDate());
                ProfilTestBase.doModificationLigneProfil(niss, codeNature1,
                        SeleniumUtils.getNatureMenuProfilfromCodeDB(codeNature0), date,
                        codeReduction0);
            }
        }

        Map<Integer, List<String>> mapElement = new LinkedHashMap<>();
        mapElement.put(1, addToListElement(dateDeb1, typeEvenement1, codeNature1, codeReduction1));

        if (typeEvenement2 != null && !StringUtils.isEmpty(typeEvenement2.trim())) {
            mapElement.put(2, addToListElement(dateDeb2, typeEvenement2, codeNature2, codeReduction2));
        }

        if (typeEvenement0.contains("CES")) {

            niss = DaoFunctionality.getNascaDao()
                    .getNissChangementNatureProfilCessation(dateDeb0);
        } else {
            niss = DaoFunctionality.getNascaDao()
                    .getNissChangementNatureProfil(codeNature0, dateDeb0, null,
                            codeReduction0);

        }
        if (niss == null) {
            niss = DaoFunctionality.getNascaDao()
                    .getNissChangementNatureProfil(codeNature0, dateDeb0, null,
                            null);
            String date = DateUtils.doFormat(TestData.DATE_FORMAT_YEAR,
                    DateUtils.doConvertStringtoDatTimeProfil(dateDeb0)
                            .toDate());
            ProfilTestBase.doModificationLigneProfil(niss, SeleniumUtils.getNatureMenuProfilfromCodeDB(codeNature0),
                    SeleniumUtils.getNatureMenuProfilfromCodeDB(codeNature0), date, codeReduction0);
        }

        if (niss != null) {
            ProfilTestBase.doChangementProfilMultipleAjout(niss, mapElement);

            EnrolementPPTestBase.checkCotisationTrimestrePPChangementProfil(
                    niss, controleTrimestre1, TestData.TYPE_CREANCE_ORDINAIRE, controleCotisation1, null, null, false);
            EnrolementPPTestBase.checkCotisationTrimestrePPChangementProfil(
                    niss, controleTrimestre2, TestData.TYPE_CREANCE_ORDINAIRE, controleCotisation2, null, null, false);
            EnrolementPPTestBase.checkCotisationTrimestrePPChangementProfil(
                    niss, controleTrimestre3, TestData.TYPE_CREANCE_ORDINAIRE, controleCotisation3, null, null, false);
        } else {
            logSkip(Thread.currentThread().getStackTrace()[1].getFileName(), TestData.PAS_CAS_TROUVE_DB);
        }

        finishTestExecution();
    }
}