package be.ucm.cas.nasca.web.test.miseendemeure;

import be.ucm.cas.nasca.web.test.rappels.RappelsTestBase;
import be.ucm.cas.nasca.web.test.support.*;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;

public class MiseEnDemeureTestBase extends TestBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    String doTimeMachineChange() {
        DateTime datetim0 = new DateTime();
        Date date0 = datetim0.toDate();

        Calendar calJour = Calendar.getInstance();
        calJour.setTime(date0);
        calJour.set(Calendar.DAY_OF_MONTH, 1);
        calJour.set(Calendar.MONTH, Calendar.JANUARY);

        String dateStr = DateUtils.doFormat(TestData.DATE_FORMAT_XML, calJour.getTime());
        RunBatch.runBatchChangeDate(dateStr);

        return dateStr;
    }

    void addParametresMiseEnDemeure(String date, boolean seuil, String type, boolean batch) {
        try {
            if (batch) {
                homeMenuPage.clickAdministration();
                administrationPage.clickMenuRecouvrementMiseEnDemeure();
                if (TestData.TYPE_PP.equals(type)) {
                    administrationPage.clickBtnEditerRecouvrementMEDPrincipalPP();
                } else {
                    administrationPage.clickBtnEditerRecouvrementMEDPrincipalPM();
                }
            } else {
                gestionClientPage.clickMenuRecouvrementMiseEnDemeure();
                gestionClientPage.clickMenuMEDPrincipalALaDemande();
            }

            administrationPage.fillDateDerniereRecetteValideeRecouvrement(date);
            administrationPage.fillDateAPayerPourRecouvrement(DateUtils.getDateFutur(2, TestData.DATE_FORMAT_SIMPLE));
            administrationPage.fillDateEnvoiRecouvrement(DateUtils.getDateFutur(1, TestData.DATE_FORMAT_SIMPLE));
            if (seuil) {
                administrationPage.fillSeuilMinimumRecouvrement("0");
            } else {
                administrationPage.fillSeuilMinimumRecouvrement("9999999999");
            }

            administrationPage.clickBtnEnregistrerRecouvrement();
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    List<String> getNissForMed(String requete, String stadeRecouvrement, String solidaire, String suspendu, String date) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            Class<?>[] paramTypes = {String.class, String.class, String.class, String.class};
            Method methode = DaoFunctionality.getNascaDao().getClass().getMethod("getNissForMedPP_" + requete, paramTypes);
            list = (List<Map<String, Object>>) methode.invoke(DaoFunctionality.getNascaDao(), stadeRecouvrement, solidaire, suspendu, date);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return RappelsTestBase.getNissOrBceFromList(list);
    }

    List<String> getBceForMed(String requete, String stadeRecouvrement, String solidaire, String suspendu, String date) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            Class<?>[] paramTypes = {String.class, String.class, String.class, String.class};
            Method methode = DaoFunctionality.getNascaDao().getClass().getMethod("getBceForMedPM_" + requete, paramTypes);
            list = (List<Map<String, Object>>) methode.invoke(DaoFunctionality.getNascaDao(), stadeRecouvrement, solidaire, suspendu, date);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return RappelsTestBase.getNissOrBceFromList(list);
    }
}