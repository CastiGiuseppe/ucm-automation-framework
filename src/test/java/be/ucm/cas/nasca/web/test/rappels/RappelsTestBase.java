package be.ucm.cas.nasca.web.test.rappels;

import be.ucm.cas.nasca.web.test.enrolement.pm.EnrolementPMTestBase;
import be.ucm.cas.nasca.web.test.enrolement.pp.EnrolementPPTestBase;
import be.ucm.cas.nasca.web.test.support.*;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;

public class RappelsTestBase extends TestBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getFileName());

    @Override
    public EnumTypeTest getTypeTest() {
        return EnumTypeTest.AUTRE;
    }

    String doTimeMachineChange(String trimestre, int annee) {
        DateTime datetim0 = new DateTime();
        Date date0 = datetim0.toDate();

        Calendar calJour = Calendar.getInstance();
        calJour.setTime(date0);
        calJour.set(Calendar.DAY_OF_MONTH, 1);
        calJour.add(Calendar.YEAR, annee);

        switch (trimestre) {
            case "1":
                calJour.set(Calendar.MONTH, Calendar.JANUARY);
                break;
            case "2":
                calJour.set(Calendar.MONTH, Calendar.APRIL);
                break;
            case "3":
                calJour.set(Calendar.MONTH, Calendar.JULY);
                break;
            case "4":
                calJour.set(Calendar.MONTH, Calendar.OCTOBER);
                break;
            default:
                break;
        }

        String dateStr = DateUtils.doFormat(TestData.DATE_FORMAT_XML, calJour.getTime());
        RunBatch.runBatchChangeDate(dateStr);

        return dateStr;
    }

    void addParametresRappel(String date, boolean seuil1, boolean seuil2, String type, boolean batch) {
        try {
            if (batch) {
                homeMenuPage.clickAdministration();
                administrationPage.clickMenuRecouvrementRappel();
                if (TestData.TYPE_PP.equals(type)) {
                    administrationPage.clickBtnEditerRecouvrementRappelPP();
                } else {
                    administrationPage.clickBtnEditerRecouvrementRappelPM();
                }
            } else {
                gestionClientPage.clickMenuRecouvrementRappel();
                gestionClientPage.clickMenuRappelALaDemande();
            }

            administrationPage.fillDateDerniereRecetteValideeRecouvrement(date);
            administrationPage.fillDateAPayerPourRecouvrement(DateUtils.getDateFutur(2, TestData.DATE_FORMAT_SIMPLE));
            administrationPage.fillDateEnvoiRecouvrement(DateUtils.getDateFutur(1, TestData.DATE_FORMAT_SIMPLE));
            if (seuil1) {
                administrationPage.fillSeuilMinimumRecouvrement("0");
            } else {
                administrationPage.fillSeuilMinimumRecouvrement("9999999999");
            }
            if (seuil2) {
                administrationPage.fillMontantPivotRecouvrement("0");
            } else {
                administrationPage.fillMontantPivotRecouvrement("9999999999");
            }
            administrationPage.clickBtnEnregistrerRecouvrement();
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
    }

    List<String> getBceForRappels(String requete, String stadeRecouvrement, String solidaire, String suspendu, String date) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            Class<?>[] paramTypes = {String.class, String.class, String.class, String.class};
            Method methode = DaoFunctionality.getNascaDao().getClass().getMethod("getBceForRappelsPM_" + requete, paramTypes);
            list = (List<Map<String, Object>>) methode.invoke(DaoFunctionality.getNascaDao(), stadeRecouvrement, solidaire, suspendu, date);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return getNissOrBceFromList(list);
    }

    List<String> getNissForRappels(String requete, String stadeRecouvrement, String solidaire, String suspendu, String date) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            Class<?>[] paramTypes = {String.class, String.class, String.class, String.class};
            Method methode = DaoFunctionality.getNascaDao().getClass().getMethod("getNissForRappelsPP_" + requete, paramTypes);
            list = (List<Map<String, Object>>) methode.invoke(DaoFunctionality.getNascaDao(), stadeRecouvrement, solidaire, suspendu, date);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return getNissOrBceFromList(list);
    }

    public static List<String> getNissOrBceFromList(List<Map<String, Object>> list) {
        List<String> retour = new ArrayList<>();
        if (list.isEmpty()) {
            return new ArrayList<>();
        } else {
            retour.add((String) list.get(0).get("NUM_IDENTIFIANT"));
            retour.add((String) list.get(0).get("PERIODE"));
            return retour;
        }
    }

    public static void checkRecouvrementPM(String stadeRecouvrement, String bce, String periode) {
        EnrolementPMTestBase.checkRecouvrementPM(bce, getStadeRecouvrement(stadeRecouvrement), periode);
    }

    public static void checkRecouvrementPP(String stadeRecouvrement, String niss) {
        EnrolementPPTestBase.checkRecouvrementPP(niss, getStadeRecouvrement(stadeRecouvrement));
    }

    private static String getStadeRecouvrement(String stadeRecouvrement) {
        switch (stadeRecouvrement) {
            case "RA":
                return "Rappel";
            case "EN":
                return "Enrôlé";
            case "MED":
                return "Mise en demeure";
            case "CTX":
                return "CTX";
            default:
                return null;
        }
    }
}