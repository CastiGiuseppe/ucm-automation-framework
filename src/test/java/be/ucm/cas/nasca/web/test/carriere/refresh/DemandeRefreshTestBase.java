package be.ucm.cas.nasca.web.test.carriere.refresh;


import be.ucm.cas.nasca.web.test.support.*;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static be.ucm.cas.nasca.web.test.support.EnumTypeTest.AUTRE;

public class DemandeRefreshTestBase extends TestBase {

    private Map<String, List<String>> doDemandeRefresh = new LinkedHashMap<>();


    @Override
    public EnumTypeTest getTypeTest() {
        return AUTRE;
    }

    void doDemandeRefresh(String type,String niss,String type_niss,String deadline) {
        try {receptionDemandeRefresh(type,niss,type_niss,deadline);
        } catch (Exception e) {
            logError(Thread.currentThread().getStackTrace()[1].getMethodName() + " " + niss, e);
        }
    }


    static void receptionDemandeRefresh(String type,String niss,String type_niss,String deadline)
        {
         Map<String, String> map = new LinkedHashMap<>();

         map.put("REASON", type);
         map.put("TYPE_NISS", type_niss);
         map.put("NISS", niss);
         map.put("DEADLINE", deadline);

         map.put("ENVIRONNEMENT", ((TestData) ApplicationContext.getAppCtx().getBean("testData")).getPropsRootUrlFuzion());

         ReplaceContentInTestCaseUtils.replace(TestData.SOAP_UI_PROJECT_PP_ECLIPZ, TestData.TEMP_FILE + "NASCA - SOAP_XML - PP - ECLIPZ.xml", map);

         TestBase.sendSoap(TestData.TEMP_FILE + "NASCA - SOAP_XML - PP - ECLIPZ.xml", "TestCase - DemandeRefresh", "");


     }

}