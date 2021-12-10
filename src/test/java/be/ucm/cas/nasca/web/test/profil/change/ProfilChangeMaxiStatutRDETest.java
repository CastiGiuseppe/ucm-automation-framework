package be.ucm.cas.nasca.web.test.profil.change;

import be.ucm.cas.nasca.web.test.profil.ProfilTestBase;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.SeleniumUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.joda.time.DateTime;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProfilChangeMaxiStatutRDETest extends ProfilChangeAbstract {

    private static final String NATURE_DEPART = TestData.NATURE_PROFILE_MAXI_STATUT;

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Profil.xlsx", "changement nature RDE", "Changement nature RDE", NATURE_DEPART, null);
    }

    @Test(dataProvider = "data")
    public void changeMaxiStatutRDE(String id, String codenature,
                                    String reductiontype, String datedebut1, String annee1,
                                    String revenu1, String typeRevenu1, String annee2, String revenu2,
                                    String typeRevenu2, String annee3, String revenu3,
                                    String typeRevenu3, String annee4, String revenu4,
                                    String typeRevenu4, String nature2, String reductiontype2,
                                    String datedebut2, String controle1, String cotisationOrd1,
                                    String cotisationRegul1, String jour, String mois, String annee,
                                    String pivotdate, String sourceEW, String libelle) {
        initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        DaoFunctionality.getNascaDao().updateDemandeEnEncodage();

        DateTime datePlus3Ans = DateUtils.doConvertStringtoDatTimeProfil(datedebut1).plusYears(3);

        String datePlus3AnsStr = DateUtils.doFormat(TestData.DATE_FORMAT_XML, datePlus3Ans.toDate());

        String niss = DaoFunctionality.getNascaDao().getNissChangementNatureProfil(codenature, datePlus3AnsStr, null, "");

        ProfilTestBase.doModificationLigneProfil(niss, SeleniumUtils.getNatureMenuProfilfromCodeDB(codenature), null, null, reductiontype);

        Map<Integer, List<String>> mapElementRevenu = new LinkedHashMap<>();

        boolean revenuPresume = traitementAnnee(annee1, revenu1, typeRevenu1, mapElementRevenu, annee2, revenu2, typeRevenu2,
                annee3, revenu3, typeRevenu3, annee4, revenu4, typeRevenu4);

        if (niss == null) {
            niss = DaoFunctionality.getNascaDao().getNissChangementNatureProfil(codenature, datedebut1, null, "");

            ProfilTestBase.doModificationLigneProfil(niss, NATURE_DEPART, null, null, reductiontype);
        }

        doChangementProfil(niss, codenature, datedebut1, datedebut2, reductiontype, revenuPresume, nature2, reductiontype2,
                mapElementRevenu, controle1, cotisationOrd1, cotisationRegul1);

        finishTestExecution();
    }
}