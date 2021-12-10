package be.ucm.cas.nasca.web.test.profil.suppression;

import be.ucm.cas.nasca.web.test.profil.ProfilTestBase;
import be.ucm.cas.nasca.web.test.support.DaoFunctionality;
import be.ucm.cas.nasca.web.test.support.DateUtils;
import be.ucm.cas.nasca.web.test.support.SeleniumUtils;
import be.ucm.cas.nasca.web.test.support.TestData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.Iterator;

public class ProfilSuppressionMaxiStatutTest extends ProfilSuppressionAbstract {

    private static final String NATURE_A_SUPPRIMER = TestData.NATURE_PROFILE_MAXI_STATUT;

    @DataProvider(name = "data")
    public Iterator<Object[]> data() {
        return getDataProvider("TA_Profil.xlsx", "supprimer nature", "Supprimer nature", NATURE_A_SUPPRIMER, null);
    }

    @Test(dataProvider = "data")
    public void suppressionMaxiStatut(String id, String typeEvenement1,
                                      String nature1, String reductiontype1, String datedebut1,
                                      String typeEvenement2, String nature2, String reductiontype2,
                                      String datedebut2, String controle1, String cotisation1,
                                      String jour, String mois, String annee, String pivotdate,
                                      String baremePivot1, String libelle) throws ParseException {
        initTest(libelle, Thread.currentThread().getStackTrace()[1].getFileName());

        loginNasca();

        DaoFunctionality.getNascaDao().updateDemandeEnEncodage();

        String niss = DaoFunctionality.getNascaDao().getNissSuppressionNatureProfil(
                typeEvenement1, nature1, reductiontype1, datedebut1,
                typeEvenement2, nature2, reductiontype2, datedebut2);

        if (niss == null) {
            if ("CPR".equals(typeEvenement2)) {
                niss = DaoFunctionality.getNascaDao().getNissChangementNatureProfil(nature2, datedebut1, null, "");

                if (niss != null) {
                    ProfilTestBase.doModificationLigneProfil(niss,
                            SeleniumUtils.getNatureMenuProfilfromCodeDB(nature2), SeleniumUtils.getNatureMenuProfilfromCodeDB(nature1), DateUtils.doFormatToString(TestData.DATE_FORMAT_YEAR, TestData.DATE_FORMAT_XML,
                                    datedebut1), reductiontype1);

                    ProfilTestBase.doChangeProfil(niss, SeleniumUtils.getNatureMenuProfilfromCodeDB(nature2), DateUtils.doFormatToString(TestData.DATE_FORMAT_YEAR, TestData.DATE_FORMAT_XML,
                            datedebut2), reductiontype2);
                }
            } else {
                niss = DaoFunctionality.getNascaDao().getNissChangementNatureProfil(nature1, datedebut1, null, "");

                if (niss != null) {
                    ProfilTestBase.doModificationLigneProfil(niss,
                            SeleniumUtils.getNatureMenuProfilfromCodeDB(nature1), SeleniumUtils.getNatureMenuProfilfromCodeDB(nature1), DateUtils.doFormatToString(TestData.DATE_FORMAT_YEAR, TestData.DATE_FORMAT_XML,
                                    datedebut1), reductiontype1);

                    ProfilTestBase.doChangeProfil(niss, SeleniumUtils.getNatureMenuProfilfromCodeDB(nature2), DateUtils.doFormatToString(TestData.DATE_FORMAT_YEAR, TestData.DATE_FORMAT_XML,
                            datedebut2), reductiontype2);
                }
            }
        }

        suppressionAndCheckCoti(niss, nature2, controle1, cotisation1, NATURE_A_SUPPRIMER);

        finishTestExecution();
    }
}