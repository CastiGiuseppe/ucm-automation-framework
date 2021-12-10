package be.ucm.cas.nasca.web.test.carriere.flux74L;

 
import be.ucm.cas.nasca.web.test.support.*;

import be.ucm.cas.nasca.web.test.taches.TachesTestBase;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import java.util.*;
import java.util.Map.Entry;

import static be.ucm.cas.nasca.web.test.support.EnumTypeTest.AUTRE;

public class Flux74LTestBase extends TestBase {

    private Map<String, List<String>> doFlux74L = new LinkedHashMap<>();


    @Override
    public EnumTypeTest getTypeTest() {
        return AUTRE;
    }

    void doFlux74L(String S74L,
                   String typeNiss, String niss,
                   String requestId, String pensionKind,
                   String debutPension, String family, String spouse,
                   String dateDebut, String activity, String epouse,
                   String debut, String activite, String notification) {
        try {


            if (StringUtils.isEmpty(epouse.trim())){
                receptionFlux74L(S74L, typeNiss, niss, requestId, pensionKind, debutPension, family, spouse, dateDebut, activity, notification);
            } else {
                receptionFlux74L2(S74L, typeNiss, niss, requestId, pensionKind, debutPension, family, spouse, dateDebut, activity, epouse,debut,activite,notification);
            }



        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }



    static void receptionFlux74L(String S74L,
        String typeNiss, String niss,
        String requestId, String pensionKind,
        String debutPension, String family, String spouse,
        String dateDebut, String activity, String notification) {
         Map<String, String> map = new LinkedHashMap<>();

         map.put("S74L", S74L);
         map.put("TYPE_NISS", typeNiss);
         map.put("NISS", niss);
         map.put("REQUEST_ID", requestId);
         map.put("PENSION_KIND", pensionKind);
         map.put("DEBUT_PENSION", debutPension);
         map.put("FAMILY", family);
         map.put("REQUEST_ID", requestId);
         map.put("SPOUSE", spouse);
         map.put("DATE_DEBUT", dateDebut);
         map.put("ACTIVITY", activity);
         map.put("NOTIFICATION", notification);



         map.put("ENVIRONNEMENT", ((TestData) ApplicationContext.getAppCtx().getBean("testData")).getPropsRootUrlFuzion());

         ReplaceContentInTestCaseUtils.replace(TestData.SOAP_UI_PROJECT_PP_ECLIPZ, TestData.TEMP_FILE + "NASCA - SOAP_XML - PP - ECLIPZ.xml", map);

         TestBase.sendSoap(TestData.TEMP_FILE + "NASCA - SOAP_XML - PP - ECLIPZ.xml", "TestCase - SignalChange 74L - 1", "");


     }

    static void receptionFlux74L2(String S74L,
                                 String typeNiss, String niss,
                                 String requestId, String pensionKind,
                                 String debutPension, String family, String spouse,
                                 String dateDebut, String activity, String epouse,
                                 String debut, String activite, String notification) {
        Map<String, String> map = new LinkedHashMap<>();

        map.put("S74L", S74L);
        map.put("TYPE_NISS", typeNiss);
        map.put("NISS", niss);
        map.put("REQUEST_ID", requestId);
        map.put("PENSION_KIND", pensionKind);
        map.put("DEBUT_PENSION", debutPension);
        map.put("FAMILY", family);
        map.put("REQUEST_ID", requestId);
        map.put("SPOUSE", spouse);
        map.put("DATE_DEBUT", dateDebut);
        map.put("ACTIVITY", activity);
        map.put("EPOUSE", epouse);
        map.put("DEBUT", debut);
        map.put("ACTIVITE", activite);
        map.put("NOTIFICATION", notification);



        map.put("ENVIRONNEMENT", ((TestData) ApplicationContext.getAppCtx().getBean("testData")).getPropsRootUrlFuzion());

        ReplaceContentInTestCaseUtils.replace(TestData.SOAP_UI_PROJECT_PP_ECLIPZ, TestData.TEMP_FILE + "NASCA - SOAP_XML - PP - ECLIPZ.xml", map);

        TestBase.sendSoap(TestData.TEMP_FILE + "NASCA - SOAP_XML - PP - ECLIPZ.xml", "TestCase - SignalChange 74L - 2", "");

    }




}